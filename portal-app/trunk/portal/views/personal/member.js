import React from 'react';
import QRCode from 'qrcode.react';
import {Link,hashHistory} from 'react-router';
import {Icon,Pagination,Breadcrumb,Button,Input,Modal,message,InputNumber} from 'antd';
import Wrapper from './Wrapper.js';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post} from 'util/request.js';
import { isTockenValid } from 'util/userTool.js';
import {SubEmiter,Emiter} from 'util';

export default class Member extends React.Component{
	constructor(props){
		super(props);
		this.state={
          user : "",
          hide:true,
          month:true,
          year:false,
          monthPrice : 0,
          yearPrice : 0,
          vipId : 0,
          price:10,           //单价
          duration:1,         //开通时长
          visible:false,       
          min:"30",
          sec:"00",
          qrcode : ""
		}
	}

	componentDidMount(){
        const userid = window.localStorage.getItem("userId");
        const userName = window.localStorage.getItem("userName");
        this.setState({
            user : userName
        })
        //获取用户名
        // get(`user/user/selectPersonalCenter/${userid}`)
        // .then(res=>{
        //     if(res && res.data){
        //         this.setState({ user : res.data.username })
        //     }
        // })
        //获取会员商品单价
        get(`user/vip/queryAll?pageNum=1&pageSize=10`)
        .then(res=>{
            if(res && res.data){
                res.data.list && res.data.list.map((item,i)=>{
                    if(item.duration=="1"){
                        this.setState({
                            monthPrice : item.price,
                            monthId : item.vipId,
                            price : item.price,
                            vipId : item.vipId
                        })
                    }else if(item.duration=="12"){
                        this.setState({
                            yearPrice : item.price,
                            yearId : item.vipId,
                        })
                    }
                })
            }
        })
    }
       

    handleChange=(e)=>{
        this.setState({ duration:e })
    }
    //鼠标移入显示
    appear=()=>{
        this.setState({ hide:false })
    }
    //鼠标移出隐藏
    hide=()=>{
        this.setState({ hide:true })
    }
    //选择按月付费方式
    clickMonth=()=>{
        this.setState({
            month:true,
            year:false,
            price:this.state.monthPrice,
            vipId:this.state.monthId,
        })
    }
    //选择按年付费方式
    clickYear=()=>{
        this.setState({
            year:true,
            month:false,
            price:this.state.yearPrice,
            vipId:this.state.yearId,
        })
    }

    //关闭弹出框
    handleCancel = () => {
       this.setState({
            visible: false,
            min: '30',
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
    addOrder = ()=>{
        let _that = this;
        isTockenValid(function(isValid){
            if(isValid == "0"){
                message.info("您的登录信息已失效，请重新登录。")
                Emiter.emit('loginChange');
                hashHistory.push("/login");
                window.localStorage.clear();
            }else{
                const userId = window.localStorage.getItem("userId");
                post(`order/order/addVipOrder?userId=${userId}&vipId=${_that.state.vipId}&num=${_that.state.duration}`)
                .then(res=>{
                    if (res && res.meta && res.meta.success) {
                        _that.setState({
                            qrcode: res.data.codeUrl,
                            orderNo: res.data.orderNo,
                            visible:true
                        })
                        let time = res.data.payOvertime;
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
                })
            }
        })
    }
    //支付成功
    queryResult = (i)=>{
        let _that = this;
        get(`order/wxpay/payFinish/${_that.state.orderNo}`)
        .then((res) => {
            if (res.data && res.data.trade_state && res.data.trade_state === 'SUCCESS') {
                message.success('支付成功');
                _that.handleCancel();
                hashHistory.push("/personal/indent");
                window.localStorage.setItem('vipStatus','会员')
                Emiter.emit('paysuccess');
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

	render(){
        let {qrcode} = this.state;
		return(
			<Wrapper>
				<div className="member_head">
                    <img src={require('assets/images/personal/member.png')}/>
                    <div className="vip">VIP会员</div>
                    <div className="right orga" onMouseOver={this.appear} onMouseOut={this.hide}>
                        <img src={require('assets/images/personal/orga.png')}/>
                        <div className="orga_member">机构会员</div>
                        <div className="popup" style={{'display':this.state.hide?'none':'block'}}>
                            <div className="arrow" ></div> 
                            如需以机构形式购买会员，请与我们联系。<br/>
                            联系人：<span>焦伟春</span>，联系电话：<span>027-82820210.</span>
                        </div>
                    </div>
                </div>
                <ul className="member_content">
                    <li className="member_content_item">
                        用户ID :<span> {this.state.user}</span>
                    </li>
                    <li className="member_content_item">
                        付费模式 ：
                        <Button className={this.state.month?'on':'off'} onClick={this.clickMonth}>按月付费</Button> 
                        <Button className={this.state.year?'on':'off'} onClick={this.clickYear}>按年付费</Button>
                    </li>
                    <li className="member_content_item">
                        开通时长 ：
                        <InputNumber min={1} step={1}  precision={0} value={this.state.duration} onChange={this.handleChange}/>{this.state.month?"月":(this.state.year?"年":"")}    
                    </li>
                    <li className="member_content_last_item">
                        应付金额 ：<span>{this.state.price*this.state.duration || "0"}</span>元
                    </li> 
                    <li className="member_content_item pay" style={{paddingLeft:142}}>
                        <Button onClick={this.addOrder}>去支付</Button>
                    </li>
                </ul>

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
                            <p>支付成功后，请点击“支付成功”，即可享受会员权益。</p>
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