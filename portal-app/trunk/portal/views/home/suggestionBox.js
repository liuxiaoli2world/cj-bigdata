import React from 'react';
import { Link, hashHistory } from 'react-router';
import { Modal, Button,Input,message } from 'antd';
import './index.scss';
import { get, post } from 'util/request.js';

export default class SuggestionBox extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			visible: false
		}
	}
	showModal = () => {
		this.setState({
			visible: true
		});
	}
	handleOk = () => {
		const pass = this.checkBeforePost();
		if(!pass) {
			return;
		}
		this.setState({
			confirmLoading: true,
		});

		// TODO 后台提交
		post('user/idea/add', {idea: this.state.message, userEmail:this.state.email})
		.then((jsonData) => {
			if(jsonData.meta && jsonData.meta.success) {
				this.setState({
					visible: false,
					confirmLoading: false
				});
				message.info('提交成功！');
			}    
		})
	}

	checkBeforePost() {
		let info = '';
		if(!this.state.message) {
			info = '请输入您的意见！';
		}

		if(!this.state.email) {
			info = '请输入您的邮箱';
		}else {
			if(!this.checkEmailAddress(this.state.email)) {
				info = '请输入正确的邮箱地址！';
			}
		}
		
		if(info) {
			message.info(info);
			return false;
		}else {
			return true;
		}
	}
	// 校验邮箱地址是否合法
	checkEmailAddress(addr) {
		var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/; 
		return reg.test(addr);		
	}

	handleCancel = () => {
		this.setState({
			visible: false
		});
	}

	onChangeHandle = (event) => {
		const name = event.target.name;
		const value = event.target.value;
		this.setState({
			[name]: value
		});
	}

	render() {
		return (
			<div className='suggestion-box'>
				<div className='title'>
					<img src={require('assets/images/iconfont-circularemail1.png')} />
					<span className='title-label' style={{fontWeight:"bold"}}>意见箱</span>
				</div>
				<div className='sep'></div>
				<div className='content'>请大家踊跃参加，积极建言献策。主要征求对本单位及所属各部门各方面的意见和建议以及其他方面需要反映的问题。</div>
				<img className='box' src={require('assets/images/messageBox.png')} onClick={this.showModal}/>

				<Modal title={[
								<img key={10} src={require('assets/images/suggestion-header.png')} style={{float:'left',width:'20px'}}/>,
								<p key={11} style={{fontSize:'16px',color:'white',marginLeft:'30px'}}>意见箱</p>
						]}
					visible={this.state.visible}
					footer={[
						<Button key={1}  style={{borderRadius:'0',background:'#3f93cd',margin:'0 20px 30px 0',color:'white'}} size='large' loading={this.state.loading} onClick={this.handleOk}>提交</Button>,
						<Button key={2}  style={{borderRadius:'0',background:'#3f93cd',margin:'0 0 30px 0',color:'white'}} size='large' onClick={this.handleCancel}>取消</Button>,
					]}
					wrapClassName='suggestion-box-modal'
					closable={false}
					maskClosable={false}>
					<p style={{fontSize:'14px',margin:'0 0 10px 0'}}><span style={{color:'red'}}>*</span>您的意见：</p>
					<Input name='message' style={{borderRadius:'0'}} type='textarea' rows={4}  placeholder='请描述您的问题' value={this.state.message} onChange={this.onChangeHandle}/>
					<p style={{fontSize:'14px',margin:'20px 0 10px 0'}}><span style={{color:'red'}}>*</span>您的邮箱：</p>
					<Input name='email' style={{borderRadius:'0'}} placeholder='请输入邮箱地址'  value={this.state.email} onChange={this.onChangeHandle}/>
				</Modal>
			</div>
		)
	}
}