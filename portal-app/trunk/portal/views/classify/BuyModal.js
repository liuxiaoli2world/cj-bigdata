import React from "react";
import { Button, Modal, Input, message } from 'antd';
import { get, post } from "util/request.js";
import QRCode from 'qrcode.react';
import { Link, hashHistory } from "react-router";
import './modal.scss'
import { SubEmiter, Emiter } from 'util';
import { isTockenValid } from 'util/userTool.js';
message.config({
    top:"40%"
})

export default class BuyModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isBuy: false,
            visible: false,
            min: '30',
            sec: '00',
            checkList: [],
            list: [],
            checkAll: false,
            qrcode: '',
            detail: {}
        }
    }

    componentDidMount() {
        const { isbn } = this.props;
        isbn && this.getData(isbn);
    }

    componentWillReceiveProps(nextprops) {
        if (nextprops.isbn != this.props.isbn) {
            this.getData(nextprops.isbn,nextprops.root)
        }
    }

    showModal = () => {
        const self = this;
        isTockenValid(function (isValid) {
            if (isValid == 1) {
                self.setState({
                    visible: true,
                });
            } else {
                window.localStorage.clear();
                hashHistory.push('/login');
            }
        })
    }

    getData = (isbn,root) => {
        let list = [];
        get(`book/book/queryByIsbn/${isbn}`)
            .then((res) => {
                if (res.meta.success) {
                    this.setState({
                        detail: res.data || {}
                    })
                }
            })
        root && this.setState({
            list: root
        })
        !root &&
            get(`book/bookdir/queryRootByIsbn/${isbn}`)
                .then((data) => {
                    if (data.meta.success) {
                        list = data.data;
                    }
                })
                .then(() => {
                    this.setState({
                        list
                    })
                })
                .then(() => {
                    this.queryMybook()
                })
    }

    mul(a, b) {
        let c = 0,
            d = a.toString(),
            e = b.toString();
        try {
            c += d.split(".")[1].length;
        } catch (f) { }
        try {
            c += e.split(".")[1].length;
        } catch (f) { }
        return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
    }

    queryMybook = () => {
        const userId = window.localStorage.getItem('userId');
        const { isbn } = this.props;
        const { list } = this.state;
        userId &&
            get(`user/userbook/queryRootDirsOfUser?isbn=${isbn}&userId=${userId}`)
                .then((res) => {
                    if (res.meta.success) {
                        if (res.data.length > 0) {
                            //改
                            list.map((item, i) => {
                                if (res.data.indexOf(item.bookDirId) >= 0) {
                                    item.isBuy = true;
                                }
                            })
                        }
                        this.setState({
                            list
                        })
                    }
                })
    }

    handleCancel = () => {
        this.setState({
            visible: false,
            isBuy: false,
            min: '30',
            sec: '00'
        });
        $('.ant-modal-content').removeClass('active');
        this.timer && clearTimeout(this.timer);
        this.timer1 && clearTimeout(this.timer1);
    }

    handleCheck = (item, i, isCheck) => {
        if (item.isBuy) {
            return
        }
        let { checkList, list } = this.state;
        item.isCheck = !isCheck;
        const index = checkList.findIndex((val) => val == item.bookDirId);
        if (index >= 0) {
            checkList = [
                ...checkList.slice(0, index),
                ...checkList.slice(index + 1)
            ]
        } else {
            checkList.push(item.bookDirId)
        }
        const newList = [
            ...list.slice(0, i),
            item,
            ...list.slice(i + 1)
        ]
        this.setState({
            list: newList,
            checkList,
            checkAll: (checkList.length === list.length),
            isBuy: false
        })
    }

    handleCheckAll = () => {
        let { checkAll, list } = this.state;
        let checkList = [];
        checkAll = !checkAll;
        if (checkAll) {
            list.map((item, i) => {
                if (!item.isBuy) {
                    item.isCheck = true;
                    checkList.push(item.bookDirId);
                }
            })
        } else {
            list.map((item, i) => {
                item.isCheck = false;
            })
        }
        this.setState({
            checkAll,
            list,
            checkList,
            isBuy: false
        })
    }

    addOrder = () => {
        const { checkList, checkAll } = this.state;
        const userId = window.localStorage.getItem('userId');
        if (checkList.length > 0) {
            let bookDirIds = checkList.join(',');
            let isFull = checkAll ? 1 : 0;
            post(`order/order/addBookOrder?userId=${userId}&bookDirIds=${bookDirIds}&isFull=${isFull}`)
                .then((res) => {
                    if (res.meta.success) {
                        this.setState({
                            qrcode: res.data.codeUrl,
                            orderNo: res.data.orderNo,
                            isBuy: true
                        })
                        $('.ant-modal-content').addClass('active');
                        let time = res.data.payOvertime;
                        this.timer = setInterval(() => {
                            if (time > 0) {
                                const min = this.addZero(parseInt(time / 60));
                                let sec = this.addZero(time % 60);
                                this.setState({
                                    min,
                                    sec
                                })
                                time--
                            } else {
                                this.handleCancel();
                            }
                        }, 1000);
                        this.timer1 = setTimeout(() => {
                            this.queryResult(1);
                        }, 30000)
                    }
                })
        }
    }

    addZero = (val) => {
        if (val < 10) {
            val = '0' + val;
        }
        return val;
    }

    queryResult = (i) => {
        get(`order/wxpay/payFinish/${this.state.orderNo}`)
            .then((res) => {
                if (res.data && res.data.trade_state && res.data.trade_state === 'SUCCESS') {
                    message.success('支付成功');
                    this.handleCancel();
                    this.queryMybook();
                    Emiter.emit('buyOk')
                } else {
                    switch (i) {
                        case 0:
                            message.error('支付未完成，请继续完成支付！')
                            break;
                        case 1:
                            setTimeout(() => {
                                this.queryResult(2)
                            }, 270000)
                            break;
                        case 2:
                            setTimeout(() => {
                                this.queryResult(3)
                            }, 1500000)
                            break;
                        case 3:
                            this.handleCancel();
                            break;
                    }
                }
            })
    }

    render() {
        const { isBuy, visible, checkAll, list, qrcode, detail, checkList } = this.state;
        let Cprice = detail.chapterPrice ? this.mul(checkList.length, detail.chapterPrice) : 0;
        Cprice = checkAll ? detail.fullPrice : Cprice;
        return (
            <div className="buyModal">
                <SubEmiter eventName="buyBook" listener={this.showModal.bind(this)}></SubEmiter>
                {/*购买弹出框*/}
                <Modal title={[
                    <img key={10} src={require('assets/images/icon-buy.png')} style={{ float: 'left', width: '20px', marginTop: '3px' }} />,
                    <p key={11} style={{ fontSize: '16px', color: 'white', marginLeft: '30px' }}>{!isBuy ? `购买${detail.bookName ? detail.bookName : ''}` : '支付'}</p>
                ]}
                    visible={visible}
                    maskClosable={false}
                    onCancel={this.handleCancel.bind(this)}
                    footer={null}
                    wrapClassName='buybox-modal'>
                    {!isBuy ?
                        <div className="front" style={{overflow:"hidden"}}>
                            {/*下单*/}
                            <div className="allList" style={{width:"106%",overflow:"auto"}}>
                                {list && list.map((item, i) => {
                                    const isCheck = item.isCheck ? item.isCheck : false;
                                    const isBuy = item.isBuy ? item.isBuy : false;
                                    return (
                                        <div className={`list-item ${isCheck ? 'select' : ''} ${isBuy ? 'isBuy' : ''} `} key={i}>
                                            <span className='checkbox' onClick={this.handleCheck.bind(this, item, i, isCheck)}>
                                                {isCheck ? <img src={require('assets/images/select.png')} alt="" /> : null}
                                                {isBuy ? <img src={require('assets/images/select_1.png')} alt="" /> : null}
                                            </span>
                                            <span className="listname">{item.dirName}</span>
                                        </div>
                                    )
                                }
                                )}
                            </div>
                            <div className="buy clearfix">
                                <div className={`list-item left ${checkAll ? 'select' : ''}`}>
                                    <span className="checkbox" onClick={this.handleCheckAll.bind(this)}></span><span className="listname">购买全本</span>
                                </div>
                                <div className="total left">结算：<span className="price">{`￥${Cprice}元`}</span></div>
                            </div>
                            <div>
                                <Button className="addOrder" type="primary" onClick={this.addOrder}>去支付</Button>
                            </div>
                        </div>
                        :
                        <div className="back">
                            {/*支付*/}
                            <div className="orderTips">订单已生成，请在<span className="endTime">{`${this.state.min}:${this.state.sec}`}</span>分钟内完成支付</div>
                            <div className="qrcode">
                                <QRCode value={qrcode} size={140} />
                            </div>
                            <div className="wechatPay">
                                <img src={require('assets/images/personal/wechat.png')} alt="" />
                            </div>
                            <div className="footer">
                                <div className="warning">
                                    <img src={require('assets/images/tips.png')} alt="" />
                                </div>
                                <p>支付成功后，请点击“支付成功”，即可阅读该图书。</p>
                                <p>支付失败后，请重新扫码支付。</p>
                                <div className="buttons">
                                    <Button className="btn-ok" onClick={this.queryResult.bind(this, 0)}>支付成功</Button>
                                    <Button className="btn-back" onClick={this.handleCancel}>返回</Button>
                                </div>
                            </div>

                        </div>
                    }
                </Modal>
            </div>
        )
    }
}