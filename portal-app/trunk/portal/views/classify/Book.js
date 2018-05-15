import React from "react";
import TabBox from '../components/TabBox';
import TitleBar from '../components/TitleBar';
import BuyModal from '../classify/BuyModal';
import Aside from './Aside';
import { Button, Modal, Input, message } from 'antd';
import { get, post } from "util/request.js";
import { Link, hashHistory } from "react-router";
import { SubEmiter, Emiter } from 'util';
import QRCode from 'qrcode.react';
import './modal.scss'
import { isTockenValid } from 'util/userTool.js';

export default class Book extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            sugInfo: [],
            sugExpert: [],
            detail: {},
            list: [],
            checkList: [],
            isBuy: false,
            myBook: [],
            isRelease: true
        }
    }

    showModal = () => {
        Emiter.emit('buyBook');
    }

    queryMybook = (detail) => {
        let myBook = [];
        const _this = this;
        detail = detail ? detail : this.state.detail;
        isTockenValid(function (isValid) {
            if (isValid == 1) {
                const userId = window.localStorage.getItem('userId');
                get(`user/userbook/queryRootDirsOfUser?isbn=${detail.isbn}&userId=${userId}`)
                    .then((res) => {
                        if (res.meta.success) {
                            if (res.data.length > 0) {
                                myBook = res.data;
                                detail.list.map((item, i) => {
                                    if (res.data.indexOf(item.bookDirId) >= 0) {
                                        item.isBuy = true;
                                    }
                                })
                            }
                            _this.setState({
                                detail,
                                myBook
                            })
                        }
                    })
            } else {
                _this.setState({
                    detail,
                    myBook
                })
                window.localStorage.clear();
            }
            
        })
    }

    addVip = () => {
        isTockenValid(function (isValid) {
            if (isValid != 1) {
                hashHistory.push(`/login`);
                window.localStorage.clear();
            }else{
                hashHistory.push('/personal');
            }
        })
    }

    read = (item) => {
        if (item.isTry || item.isBuy || window.localStorage.getItem('vipStatus') == '会员'||this.state.detail.fullPrice == 0) {
            window.open(`#/read/${item.isbn}/${item.bookDirId}`);
        } else {
            this.showModal();
        }
    }

    componentDidMount() {
        const bookId = this.props.params.id;
        let isbn, detail = {}, list;
        Emiter.emit('detailTitle',1)
        get(`book/book/isReleaseById/${bookId}`)
            .then((data) => {
                if (data.meta.success) {
                    if (data.data !== 'success') {
                        return Promise.reject('下架');
                    }
                }
            })
            .then((res) => {
                get(`book/book/query/${bookId}`)
                    .then((data) => {
                        if (data.meta.success) {
                            detail = data.data;
                            isbn = detail.isbn;
                        }
                    })
                    .then(() => {
                        isbn &&
                        get(`book/bookdir/queryRootByIsbn/${isbn}`)
                            .then((data) => {
                                if (data.meta.success) {
                                    detail.list = data.data;
                                }
                            })
                            .then(() => {
                                this.queryMybook(detail)
                            })
                    })
            })
            .catch((res) => {
                this.setState({
                    isRelease: false
                })
            })
    }

    render() {
        const { sugInfo, sugExpert, detail, checkList, isBuy, myBook, isRelease } = this.state;
        const role = window.localStorage.getItem('role');
        const vipStatus = window.localStorage.getItem('vipStatus');
        const buttonStyle = {
            fontSize: '14px',
            width: '90px',
            height: '30px',
            marginRight: '30px'
        };
        return (
            <div className="classifyContent clearfix">
                <SubEmiter eventName="buyOk" listener={this.queryMybook}></SubEmiter>
                {/*左边标签栏*/}
                <Aside />
                {/*右边主体内容*/}
                { isRelease ? 
                <div className="content left">
                    <div className="basic">
                        <div className="info">
                            <p>图书：<span className="blue">{detail.bookName}</span></p>
                            <p>作者：<span>{detail.author ? `${detail.author.replace(/，/g, ",").split(',').slice(0, 5).join(',')}等` : '佚名'}</span></p>
                            <p>总价：<span className="blue" style={{marginRight: '12px'}}>{detail.fullPrice && `￥${detail.fullPrice}`}</span>  单价：<span className="blue">{detail.chapterPrice && `￥${detail.chapterPrice}`}</span></p>
                            <p>出版日期：<span>{detail.releaseDate ? detail.releaseDate.slice(0,10) : '未知'}</span></p>
                            <p>关键词：<span>{detail.keyword ? `${detail.keyword.split(',').slice(0, 3).join(',')}等` : '暂无关键词'}</span></p>
                        </div>
                        <div className="buttons">
                            <Link to={`/read/${detail.isbn}`} target='_blank'>
                                { detail.fullPrice == 0 || myBook.length > 0 || vipStatus == '会员' || role == 'ROLE_INSPECTOR' ? 
                                    <Button type="primary" style={buttonStyle}>阅读</Button> 
                                    : detail.isTry === 1 ?
                                    <Button type="primary" style={buttonStyle}>试读</Button>
                                    : null
                                }
                                {/* <Button type="primary" style={buttonStyle}>
                                    {detail.fullPrice == 0 || myBook.length > 0 || vipStatus == '会员' || role == 'ROLE_INSPECTOR' ? '阅读' : '试读'}
                                </Button> */}
                            </Link>
                            {
                                detail.fullPrice > 0 && (detail.list && detail.list.length > myBook.length) && role != 'ROLE_INSPECTOR' ?
                                    <Button type="primary" style={buttonStyle} onClick={this.showModal}>购买</Button>
                                    : null
                            }
                            {
                                vipStatus != '会员' && role != 'ROLE_INSPECTOR' ?
                                    <Button type="primary" style={buttonStyle} onClick={this.addVip}>开通会员</Button>
                                    : null
                            }
                        </div>
                        <div className="cover">
                            <img src={detail.bookImages && detail.bookImages.length > 0 ? detail.bookImages[0].imageUrl : ''} alt="" />
                        </div>
                    </div>
                    <div className="detail_intro">
                        <TitleBar title='内容简介' />
                        <div className="intro_content">{detail.bookDesc ? detail.bookDesc.replace(/<\/?[^>]*>/g,'') : '暂无内容'}</div>
                    </div>
                    <div className="list">
                        <TitleBar title='目录' />
                        <div className="listBox">
                            {detail.list && detail.list.length > 0 ? detail.list.map((item, i) => (
                                <div key={i} onClick={this.read.bind(this, item)}>{item.dirName}</div>
                            )) : '暂无目录'}
                        </div>
                    </div>
                </div>
                : 
                <div className="noRelease">
                    <img src={require('assets/images/noRelease.png')} alt=""/>
                </div>}
                {/*购买弹出框*/}
                <BuyModal isbn={detail.isbn} root={detail.list} />
            </div>

        )
    }
}