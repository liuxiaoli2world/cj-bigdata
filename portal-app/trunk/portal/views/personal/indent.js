import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon,Pagination,Breadcrumb,message,Modal,Button,Popconfirm } from 'antd';
import Wrapper from './Wrapper.js';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post,formPost} from 'util/request.js';
import QRCode from 'qrcode.react';
import { isTockenValid } from 'util/userTool.js';
import {SubEmiter,Emiter} from 'util';

export default class Indent extends React.Component{
	constructor(props){
		super(props);
		this.state={
            indentList:[],
            total : 0,
            pageSize : 4,
            current : 1,
            visible:false,       
            min:"00",
            sec:"00",
            qrcode : ""
		}
    }
    
    getList(pagenum){
        let userId = window.localStorage.getItem("userId");
        let _json = {
            pageNum : pagenum,
            pageSize : 4,
            userId : userId,
            scene : "portal"
        };
        let {current} = this.state;
        formPost(`order/order/queryBy`,_json)
        .then(res=>{
            if(res && res.data){
                this.setState({
                    indentList : res.data.list || [],
                    total : res.data.total,
                    current : pagenum
                })
            }
        }).catch(e=>{
            this.setState({
                indentList : [],
            })
        })
    }

	componentDidMount(){
        this.getList(1);
    }
    
    //页码切换
    onChange = (page,pagesize)=>{
        const _this = this;
        isTockenValid(function(isValid){
            if(isValid == "0"){
                message.info("您的登录信息已失效，请重新登录。")
                Emiter.emit('loginChange');
                hashHistory.push("/login");
                window.localStorage.clear();
            }else{
                _this.getList(page);
            }
        })
    }

    //删除订单
    deleteOrder = (num)=>{
        let _that = this;
        isTockenValid(function(isValid){
            if(isValid == "0"){
                message.info("您的登录信息已失效，请重新登录。")
                Emiter.emit('loginChange');
                hashHistory.push("/login");
                window.localStorage.clear();
            }else{
               post(`order/order/remove?orderNo=${num}&scene=portal`)
                .then(res=>{
                    if(res && res.meta && res.meta.success){
                        message.config({
                            top:"40%"
                        })
                        message.success("删除成功");
                        _that.getList(1);
                    }
                }).catch(e=>{
                    message.error("删除失败");
                })
            }
        })
    }

    //关闭弹出框
    handleCancel = () => {
       this.setState({
            visible: false,
            min: '00',
            sec: '00'
       })
        clearInterval(this.timer);
        clearTimeout(this.layouttimer);
        Emiter.emit('paysuccess');
    }

    addZero = (val) => {
        if (val < 10) {
            val = '0' + val;
        }
        return val;
    }

    //去支付
    addOrder = (id,type)=>{
        let _that = this;
        isTockenValid(function(isValid){
            if(isValid == "0"){
                message.info("您的登录信息已失效，请重新登录。")
                Emiter.emit('loginChange');
                hashHistory.push("/login");
                window.localStorage.clear();
            }else{
                get(`order/order/query?orderId=${id}&type=${type}`)
                .then(res=>{
                    if (res && res.meta && res.meta.success) {
                        _that.setState({
                            qrcode: res.data.payCodeUrl,
                            orderNo: res.data.orderNo,
                            visible:true,
                            goodstype : type
                        })
                        let time = res.data.residueTime;
                        _that.timer = setInterval(() => {
                            if (time > 0) {
                                const min = _that.addZero(parseInt(time / 60));
                                let sec = _that.addZero(time % 60);
                                _that.setState({
                                    min,
                                    sec
                                })
                                time--
                            } else {
                                _that.handleCancel();
                            }
                        }, 1000);
                         _that.layouttimer = setTimeout(() => {
                            _that.queryResult(1);
                        }, 30000)
                    }
                }).catch(e=>{
                    message.error("服务超时");
                })
            }
        })
    }
    //支付成功
    queryResult = (i)=>{
        let _that = this;
        const {goodstype} = this.state;
        get(`order/wxpay/payFinish/${_that.state.orderNo}`)
        .then((res) => {
            if (res.data && res.data.trade_state && res.data.trade_state === 'SUCCESS') {
                message.success('支付成功');
                goodstype=='vip' && window.localStorage.setItem('vipStatus','会员');
                _that.handleCancel();
                _that.getList(1);
            } else {
                switch (i) {
                    case 0:
                        message.error('支付未完成，请继续完成支付！')
                        break;
                    case 1:
                        setTimeout(() => {
                            _that.queryResult(2)
                        }, 270000)
                        break;
                    case 2:
                        setTimeout(() => {
                            _that.queryResult(3)
                        }, 1500000)
                        break;
                    case 3:
                        _that.handleCancel();
                        break;
                }
            }
        })
    }

    componentWillUnmount(){
        clearInterval(this.timer);
    }

    pushRoute = (url,e)=>{
        e.preventDefault();
        isTockenValid(function(isValid){
            if(isValid == "0"){
                message.info("您的登录信息已失效，请重新登录。")
                Emiter.emit('loginChange');
                hashHistory.push("/login");
                window.localStorage.clear();
            }else{
                hashHistory.push(url);
            }
        })
	}

	render(){
        const {indentList,qrcode,goodstype}=this.state
		return(
			<Wrapper>
				<div className="indent_head">
                    <img src={require('assets/images/personal/config1.png')}/>
                    <div>订单管理</div>
                </div>
                {indentList && indentList.length!==0 ?<div>
                <ul className="indent_title">
                    <li>订单详情</li>
                    <li>单价</li>
                    <li>数量</li>
                    <li>总价</li>
                    <li>全部状态</li>
                </ul>
                {
                    indentList.map((item,i)=>{
                        return(
                            <div className="indent_list" key={i}>
                                <div className="indent_list_head">
                                    订单号：{item.orderNo}&nbsp;&nbsp;&nbsp;&nbsp;
                                    成交时间：{item.createdAt}
                                    <Popconfirm title="是否删除该订单？" okText="是" cancelText="否" onConfirm={this.deleteOrder.bind(this,item.orderNo)}>
                                        <img src={require("assets/images/delete_icon.png")} alt="删除"/>
                                    </Popconfirm>
                                    
                                </div>
                                <ul className="indent_list_content">
                                    <li className="item_name">
                                        <img src={item.imageUrl} alt="商品封面"/>
                                        <span>{item.goodsName.length>30?(item.goodsName.substring(0,30)+"..."):item.goodsName}</span>
                                    </li>
                                    <li className="item">{item.unitPrice}</li>
                                    <li className="item">{item.num}</li>
                                    <li className="item">{item.amount}</li>
                                    <li className="item_state" style={{paddingTop:item.payStatus==0?18:34}}>
                                        {item.payStatus==0?"待支付":"已完成"}
                                        <Link onClick={this.pushRoute.bind(this,`personal/detail/${item.orderId}/${item.goodsType}`)}>订单详情</Link>
                                        <Link to="" style={{display:item.payStatus==0?"block":"none"}} className="paynow" onClick={this.addOrder.bind(this,item.orderId,item.goodsType)}>立即支付</Link>
                                        </li>

                                </ul>
                            </div>
                        )
                    })
                }
                <div className="pagenation">
					<Pagination showQuickJumper total={this.state.total} onChange={this.onChange} pageSize={this.state.pageSize} current={this.state.current}/>
				</div>
                </div>
                : <div style={{textAlign:"center",marginTop:68}}><img src={require("assets/images/noorder.png")} alt="暂无订单" /></div>}
                <Modal title={[
                    <img key={10} src={require('assets/images/suggestion-header.png')} style={{ float: 'left', width: '20px' }} />,
                    <p key={11} style={{ fontSize: '16px', color: 'white', marginLeft: '30px' }}>支付VIP会员</p>
                ]}
                    visible={this.state.visible}
                    maskClosable={false}
                    onCancel={this.handleCancel.bind(this)}
                    footer={null}
                    wrapClassName='buybox-modal personal'>
                    <div className="back">
                        {/*支付*/}
                        <div className="orderTips">订单已生成，请在<span className="endTime">{`${this.state.min}:${this.state.sec}`}</span>分钟内完成支付</div>
                        <div className="qrcode">
                            <QRCode value={qrcode} size={140} />
                        </div>
                        <div className="wechatPay">
                            <img src={require('assets/images/personal/wechat.png')} alt=""/>
                        </div>
                        <div className="footer">
                            <div className="warning">
                                <img src={require('assets/images/tips.png')} alt="" />
                            </div>
                            <p>支付成功后，请点击“支付成功”，{goodstype=="vip"?"即可享受会员权益":"即可阅读该图书"}。</p>
                            <p>支付失败后，请重新扫码支付。</p>
                            <div className="buttons">
                                <Button className="btn-ok" onClick={this.queryResult.bind(this, 0)}>支付成功</Button>
                                <Button className="btn-back" onClick={this.handleCancel}>返回</Button>
                            </div>
                        </div>
                    </div>
                </Modal>
			</Wrapper>
		)
	}
}