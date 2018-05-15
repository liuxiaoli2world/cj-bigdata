import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon,Pagination,Breadcrumb,message} from 'antd';
import Wrapper from './Wrapper.js';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post} from 'util/request.js';
import { isTockenValid } from 'util/userTool.js';
import {SubEmiter,Emiter} from 'util';

export default class Personal extends React.Component{
	constructor(props){
		super(props);
		this.state={
			person:{},
		}
	}

	getInfo(){
		const userId = window.localStorage.getItem('userId');
		get(`user/user/selectPersonalCenter/${userId}`).then(res=>{
			if(res && res.data){
				this.setState({
					person : res.data || {},
				})
			}
		})
	}

	componentDidMount(){
		this.getInfo();
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

	onPaysuccess = ()=>{
		this.getInfo();
	}
	
	render(){
		const {person} = this.state;
		const path = hashHistory.getCurrentLocation().pathname;
		return(
			<Wrapper>
				<SubEmiter eventName="paysuccess" listener={this.onPaysuccess} />
				<div className="crumb">
					<img src={require('assets/images/crumb.png')}/>
					<p>当前位置：</p>
					<Breadcrumb separator=">">
					    <Breadcrumb.Item><Link to="/">首页</Link></Breadcrumb.Item>
					    <Breadcrumb.Item><Link to="/personal/center" style={{fontWeight:"normal"}}>个人中心</Link></Breadcrumb.Item>
					</Breadcrumb>
				</div>
				<ul className="nav">
					<li className="nav_detail">
						<div className="picture">
							 <img src={require("assets/images/userimg.png")} alt="用户头像"/> 
						</div>
						<p>{person.username}</p>
						{
							person.vipStatus && person.vipStatus !== "普通用户"?
							<div className="member">
								<img src={person.vipStatus =="过期会员"?require('assets/images/personal/vip_gray.png'):require('assets/images/personal/vip.png')}/>
								<p>{person.endDate}{person.vipStatus =="过期会员"? '会员已到期' : '到期' }</p>
								<div className="oval" onClick={this.toVip}><Link onClick={this.pushRoute.bind(this,"personal")}>续费</Link></div>
							</div>
							:
							<div className="member">
								<div className="opened" onClick={this.toVip}><Link onClick={this.pushRoute.bind(this,"personal")}>开通VIP</Link></div>
								<p>开通“VIP”即可享受免费阅读本站所有图书特权</p>
							</div>
						}
					</li>
					<a onClick={this.pushRoute.bind(this,"personal/center")}>
						{
							path.indexOf('center')>0?
							<li className="nav_list_copy" onClick={this.toCenter}>
								<img src={require('assets/images/personal/material_copy.png')}/>
								个人资料
							</li>:
							<li className="nav_list" onClick={this.toCenter}>
								<img src={require('assets/images/personal/material.png')}/>
								个人资料
							</li>
						}
					</a>
					<a onClick={this.pushRoute.bind(this,"personal/modify")}>
					{
						path.indexOf('modify')>0?
						<li className="nav_list_copy" onClick={this.toModify}>
							<img src={require('assets/images/personal/change.png')}/>
							修改资料
						</li>:
						<li className="nav_list" onClick={this.toModify}>
							<img src={require('assets/images/personal/change_copy.png')}/>
							修改资料
						</li>
					}
					</a>
					<a onClick={this.pushRoute.bind(this,"personal/safty")}>
					{
						path.indexOf('safty')>0?
						<li className="nav_list_copy" onClick={this.toSafty}>
							<img src={require('assets/images/personal/safe.png')}/>
							账户安全	
						</li>:
						<li className="nav_list" onClick={this.toSafty}>
							<img src={require('assets/images/personal/safe_copy.png')}/>
							账户安全	
						</li>
					}
					</a>
					<a onClick={this.pushRoute.bind(this,"personal/indent")}>
					{
						path.indexOf('indent')>0 || path.indexOf('detail')>0?
						<li className="nav_list_copy" onClick={this.toIndent}>
							<img src={require('assets/images/personal/indent_copy.png')}/>
							订单管理
						</li>:
						<li className="nav_list" onClick={this.toIndent}>
							<img src={require('assets/images/personal/indent.png')}/>
							订单管理
						</li>
					}
					</a>
				</ul>
				<div className="content">{this.props.children}</div>
				<div className="clear"></div>
			</Wrapper>
		)
	}
}