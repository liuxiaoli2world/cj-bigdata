import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon, Modal, Button,Input,InputNumber } from 'antd';
import './index.scss';
import {get,post} from 'util/request.js';

class NavItem extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			loading: false,
			visible1: false,
			visible3: false,
			visible4: false
		};
	}

	showModal = (index) => {
		this.setState({
			['visible'+index]: true,
		});
	}

	handleOk = (index) => {
		this.setState({
			confirmLoading: true,
		});

		setTimeout(() => {
			this.setState({
				['visible'+index]: false,
				confirmLoading: false,
			});
		}, 2000);
	}

	handleCancel = (index) => {
		this.setState({
			loading: false,
			['visible'+index]: false,
		});
	}
	onClickEvent(jumpUrl,index,event) {
		switch(index) {
			case 0:
			case 2:
			case 5:
			case 6:
				window.open(jumpUrl);
				break;
			case 1:
			case 3:
			case 4:
				this.showModal(index);
				break;
			default:
				console.error('index error');
		}
	}

	// 投稿
	contribute = (event) => {
		this.setState({
			visible1: false
		});
		// TODO 进入作者系统
		window.open('http://www.cjpress.com.cn:8181/bdmanage/#/manage/login');
	}

	// 客服
	openQQsss = (event) => {
		this.setState({
			visible1: false
		});
		//location.open('http://wpa.qq.com/msgrd?V=3&amp;Uin=463040120&amp;Site=在线QQ&amp;Menu=yes');
		//window.open('tencent://message/?uin=10987654321');
		window.open('http://wpa.qq.com/msgrd?v=3&uin=3077937385&site=qq&menu=yes');
	}

	render() {
		return (
			<div className='nav-item' onClick={this.onClickEvent.bind(this,this.props.jumpUrl,this.props.index)}>
				<img className='icon' src={this.props.imgPath} />
				{
					this.props.title.map( (i, index) => ((
						<label className='title' key={index}>{i}</label>
					)))
				}
				{
					this.props.desc.map( (i, index) => ((
						<label className='desc' key={index}>{i}</label>
					)))
				}
				{
					this.props.index<6?	<span className='sep'></span> : null
				}

				<Modal title={[
								<img key={10} src={require('assets/images/contribute.png')} style={{float:'left',width:'20px'}}/>,
								<p key={11} style={{fontSize:'16px',color:'white',marginLeft:'30px'}}>投稿专区</p>
						]}
					visible={this.state.visible1}
					footer={null}
					wrapClassName='box-modal'
					closable={true}
					maskClosable={true}
					onCancel={this.handleCancel.bind(this, 1)}>
					<div style={{textAlign:'center'}}>
						<p style={{fontSize:'14px'}}>已有资格的作者请由此进入</p>
						<Button  style={{borderRadius:'0',background:'#3f93cd',margin:'10px 0 30px 0',color:'white'}} size='large' onClick={this.contribute}>投稿</Button>
						<div style={{borderTop:'1px solid #999',margin:'20px 0'}}></div>
						<p style={{fontSize:'14px',margin:'10px,30px'}}>第一次投稿的作者请联系客服申请投稿资格</p>
						<Button  style={{borderRadius:'0',background:'#3f93cd',margin:'10px 0 30px 0',color:'white'}} size='large' onClick={this.openQQsss}>客服</Button>
					</div>
				</Modal>
				<Modal title={[
								<img key={10} src={require('assets/images/scan.png')} style={{float:'left',width:'20px'}}/>,
								<p key={11} style={{fontSize:'16px',color:'white',marginLeft:'30px'}}>扫描二维码关注数字长江</p>
						]}
					visible={this.state.visible3}
					footer={null}
					wrapClassName='box-modal'
					closable={true}
					maskClosable={true}
					onCancel={this.handleCancel.bind(this, 3)}>					
					<img style={{marginTop:'10px',width:'300px',height:'300px'}} src={require('assets/images/code.png')}/>
				</Modal>
				<Modal title={[
								<img key={10} src={require('assets/images/vertify.png')} style={{float:'left',width:'20px'}}/>,
								<p key={11} style={{fontSize:'16px',color:'white',marginLeft:'30px'}}>防伪验证</p>
						]}
					visible={this.state.visible4}
					footer={[
						<Button key={1} style={{borderRadius:'0',background:'#3f93cd',margin:'0 0 30px 0',color:'white'}} size='large' loading={this.state.loading} onClick={this.handleOk.bind(this,4)}>确认</Button>,
					]}
					wrapClassName='box-modal'
					closable={true}
					maskClosable={true}
					onCancel={this.handleCancel.bind(this, 4)}>
					<div style={{textAlign:'center'}}>
						<p style={{fontSize:'14px',margin:'20px 0 10px 0'}}>请输入防伪验证码</p>
						<InputNumber style={{height:'38px',lineHeight:'38px',display:'inline-block',width:'58px',margin:'10px 10px',borderRadius:'0'}} maxLength={4} min={1} max={9999}/>
						<InputNumber style={{height:'38px',lineHeight:'38px',display:'inline-block',width:'58px',margin:'10px 10px',borderRadius:'0'}} maxLength={4} min={1} max={9999}/>
						<InputNumber style={{height:'38px',lineHeight:'38px',display:'inline-block',width:'58px',margin:'10px 10px',borderRadius:'0'}} maxLength={4} min={1} max={9999}/>
						<InputNumber style={{height:'38px',lineHeight:'38px',display:'inline-block',width:'58px',margin:'10px 10px',borderRadius:'0'}} maxLength={4} min={1} max={9999}/>
					</div>
				</Modal>
			</div>			
		);
	}
}

export default class LeftNav extends React.Component {
	
	render() {
		const navList = [
			{imgPath:require('assets/images/icon1.png'),title:['门户网站'],desc:['海量出版社内容，一','站式搜索'],jumpUrl:'http://10.6.100.107/'},
			{imgPath:require('assets/images/icon2.png'),title:['投稿'],desc:['全方位报道作家的思','想与行为'],jumpUrl:''},
			{imgPath:require('assets/images/icon3.png'),title:['天猫商城'],desc:['最低折扣，最方便快','捷的购买方式'],jumpUrl:'https://cjcbs.tmall.com/shop/view_shop.htm?tracelog=twddp&user_number_id=2752305587'},
			{imgPath:require('assets/images/icon4.png'),title:['微信'],desc:['面向行业网络的知识','发现及共享平台'],jumpUrl:''},
			{imgPath:require('assets/images/icon5.png'),title:['验证中心'],desc:['点一点输密码','辨别真伪轻松搞定'],jumpUrl:''},
			{imgPath:require('assets/images/icon6.png'),title:['大坝安全管理系统'],desc:['细化大坝安全管理业务','掌控工程状态'],jumpUrl:'http://192.168.11.211/h2o'},
			{imgPath:require('assets/images/icon7.png'),title:['重庆嘉陵江防汛调度管理系统'],desc:['预测洪水趋势,指挥抢险救灾'],jumpUrl:'http://61.183.204.42:96/fr/'},
		];
		return(
			<div className='left-nav'>
				{
					navList.map((item, index) => {
						return <NavItem key={index} index={index} {...item}/>;
					})
				}
			</div>
		)
	}
}