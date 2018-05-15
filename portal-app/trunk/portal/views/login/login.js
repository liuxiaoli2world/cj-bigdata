import React from 'react';
import config from 'config';
import {
	Link,
	hashHistory
} from 'react-router';
import './index.scss';
import {
	autobind
} from 'core-decorators'
import {
	SubEmiter,
	Emiter
} from 'util';
import { Carousel, Tabs, Menu, Dropdown, Icon, Input, Button,message } from 'antd';
import { get, post , formPost} from 'views/util/request';
import Blogroll from '../blogroll/blogroll.js';
import base64url from 'base64url';

const TabPane = Tabs.TabPane;
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

class Login extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			email: '',
			password: ''
		}
	}

	inputOnchangeEvent(event) {
		const name = event.target.name;
		const value = event.target.value;
		this.setState({
			[name]: value
		});
	}

	loginClickEvent() {
		const url = 'user/user/login';
		const params = {
			searchStr: this.state.email,
			password: this.state.password,
			from: 'index'
		};
		formPost(url, params)
			.then((jsonData) => {
				if (jsonData.meta && jsonData.meta.success) {
					sessionStorage.setItem('access_token', jsonData.data);
					const data = jsonData.data.split('.')[1];
					var s = base64url.decode(data);
					let sObj = JSON.parse(s);
					const name = sObj.user_name;
					const id = sObj.user_id
					window.localStorage.setItem('userId', id);
					window.localStorage.setItem('userName', name);
					window.localStorage.setItem('tocken', jsonData.data);
					window.localStorage.setItem('vipStatus', sObj.vip_status);
					window.localStorage.setItem('role', sObj.role||'');
					Emiter.emit('loginChange', { name, id });
					hashHistory.push('/');
				}else {
					message.info(jsonData.meta.message);
				}
			});
	}

	render() {

		return (
			<div className='login-page'>
				<div><img src={require('assets/images/login/blue.png')} /></div>
				<div className='content clearfix'>
					<div className='content_img'>
						<img src={require('assets/images/login/picture.png')} />
						<div className='content_login'>
							<div className='content_login_header'>会员登录</div>
							<ul className='content_login_content'>
								<li className='content_login_content_item'>
									用&nbsp;&nbsp;户 ：<Input name='email' value={this.state.email} onChange={this.inputOnchangeEvent.bind(this)} placeholder="请输入用户名或邮箱" onPressEnter={this.loginClickEvent.bind(this)}/>
								</li>
								<li className='content_login_content_item'>
									密&nbsp;&nbsp;码 ：<Input name='password' type="password" value={this.state.password} onChange={this.inputOnchangeEvent.bind(this)}  placeholder="请输入密码" onPressEnter={this.loginClickEvent.bind(this)}/>
								</li>
								<li className='content_login_content_radio'>
									<Input className='square' type='checkbox' value='' />两周内免登录
									<Link to="/findpwd" className="forgetPwd" style={{marginLeft:22,color:"#3d95d5"}}>忘记密码</Link>
                                </li>
								<li className='content_login_content_button'>
									<Button onClick={this.loginClickEvent.bind(this)}>登录</Button>
								</li>
								<li className='content_login_content_register'>
									没有账号？<Link to='register'>立即注册</Link>
								</li>
							</ul>
						</div>
					</div>

				</div>
				<div className='bottom_img'><img src={require('assets/images/bottom.png')} /></div>
				<Blogroll />

			</div>
		)
	}
}

export default Login;