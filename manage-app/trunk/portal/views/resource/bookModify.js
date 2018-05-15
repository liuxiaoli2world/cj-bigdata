import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './bookModifyStyle.js';
import Box from './bookStyle.js';
import './style.css';
import BookNew from './bookNew';
import BookList from './bookList';
import EditList from './editList';
import EditDetail from './editBookDetail';
import { Tabs, Select, Button, Input } from 'antd';
import { SubEmiter, Emiter } from 'utils';
import { get, post } from 'utils/request.js';
const TabPane = Tabs.TabPane;

export default class BookModify extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            info: ''
        }
    }

    getTry = (params) => {
        this.setState({
            info :params.values
        })
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            info: nextProps.info
        })
    }

    render() {
        const info = this.state.info ? this.state.info : this.props.info;
        const { bookId, bookName, fullPrice, releaseDate, bookDesc, bookImage, imageUrl, isbn, isTry} = info;
        return ( 
            <Wrapper>
                <SubEmiter eventName="bookTry" listener={this.getTry}></SubEmiter>
                <div className='box-head clearfix'>
                    <div className="left head-img">
                        <img src={imageUrl || bookImage} alt="" />
                    </div>
                    <div className="left info">
                        <div className='content-head'>{bookName}</div>
                        <div className="fullPrice">售价：￥<span className="price">{fullPrice}</span></div>
                        <div className="time">更新：{this.props.info.updatedAt}</div>
                        <div className='content-desc'>内容简介：{bookDesc ? (bookDesc.length < 30 ? bookDesc : bookDesc && bookDesc.replace(/<\/?[^>]*>/g,'').substr(0, 30) + '...') : '暂无内容简介'}</div>
                    </div>
                </div>
                <Tabs type='card' className='child-tab'>
                    <TabPane tab='基本信息' key='1' closable={false}>
                        <BookNew info={info}/>
                    </TabPane>
                    <TabPane tab='目录' key='2' closable={false}>
                        <div className="clearfix">
                            <div className="listContent left" style={{width: '780px'}}>
                                <BookList isbn={isbn} isEdit={true}/>
                            </div>
                            <div className="listEditor left">
                                <EditList isbn={isbn}/>
                            </div>
                        </div>
                    </TabPane>
                    <TabPane tab='正文' key='3' closable={false}>
                        <div className="clearfix">
                            <div className="listContent left" style={{width: '500px'}}>
                                <BookList isbn={isbn} isEdit={false}/>
                            </div>
                            <div className="contentEditor left">
                                <EditDetail isbn={isbn} isTry={isTry}/>
                            </div>
                        </div>
                    </TabPane>
                </Tabs>
            </Wrapper>
        );
    }
}