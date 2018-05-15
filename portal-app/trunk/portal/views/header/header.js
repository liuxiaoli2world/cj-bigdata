import React from 'react';
import config from 'config';
import {
	Link,
	hashHistory
} from 'react-router';
import './header.scss';
import {
	autobind
} from 'core-decorators'
import {
	SubEmiter,
	Emiter
} from 'util';
import { Carousel, Tabs, Menu, Dropdown, Icon, Input , Select } from 'antd';
import { get } from 'views/util/request';
import TopNav from './topNav.js';

const TabPane = Tabs.TabPane;
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;
const Option = Select.Option;

class MyMenu extends React.Component {
	constructor(props) {
		super(props);
	}

	subMenuOnClick(id, event) {
		// hashHistory.push('/classify')
	}

	render() {
		return (
			<Menu style={{width:150,marginTop:6,marginLeft:-10}}>
				{
					this.props.data.map((item, index) => (
						(item.children && item.children.length > 0) ?
							<SubMenu key={index} title={<div><Link to={{ pathname: `/classify/${item.id}` }}><span style={{ marginRight: '10px' }}>|</span><span>{item.name}</span></Link></div>} onTitleClick={this.subMenuOnClick.bind(this, item.id)}>
								{
									item.children.map((item1, index1) => (
										<Menu.Item key={index1}><Link to={{ pathname: `/classify/${item1.id}` }}><span style={{ marginRight: '10px' }}>|</span><span>{item1.name}</span></Link></Menu.Item>
									))
								}
							</SubMenu>
							:
							<Menu.Item key={index}><Link to='/searchModule'><span style={{ marginRight: '10px', color: '#3d95d5' }}>|</span><span>{item.name}</span></Link></Menu.Item>
					))
				}
			</Menu>
		)
	}
}

class Header extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			index: -1,
			open: false,
			searchType: 'desc',
			keyword: '',
			list: [],
			loginName: '登录',
			registerName: '注册'
		}
	}

	componentWillMount() {
		get('book/menu/queryAll')
			.then((jsonData) => {
				const data = this.processMenu(jsonData.data || []);
				this.setState({
					list: data
				});
			});

		this.setDefaultValue();
	}

	setDefaultValue = () => {
		const userId = window.localStorage.getItem('userId');
		const name = window.localStorage.getItem('userName');
		if (userId && name) {
			this.setState({
				userId: userId,
				loginName: name + '欢迎您！',
				registerName: '退出'
			});
		}
	}

	// 处理数据组装成树形
	processMenu(list) {
		let parentList = [];
		let childrenList = [];
		let regist = {};
		// 1.子节点和父节点分开
		for (let i = 0, len = list.length; i < len; i++) {
			const item0 = list[i];
			const menuId = item0.menuId;
			const parentMenuId = item0.parentMenuId;
			let item = {};
			item.id = item0.menuId;
			item.name = item0.menuName;
			item.imgUrl = item0.imageUrl;
			if (parentMenuId == 0) {
				// 父节点
				// 记下父节点的索引，方便下一步查找
				regist[menuId] = parentList.length;
				item.children = [];
				parentList.push(item);
			} else {
				item.parentMenuId = item0.parentMenuId;
				// 子节点				
				childrenList.push(item);
			}
		}
		for (let i = 0, len = childrenList.length; i < len; i++) {
			const item = childrenList[i];
			const index = regist[item.parentMenuId] || 0;
			let children = parentList[index].children || [];
			children.push(item);
		}
		return parentList;
	}

	textOnChange(event) {
		const keyword = event.target.value;
		this.setState({
			keyword: keyword
		});

		// 搜索条件变化
		Emiter.emit('keywordChange', keyword);
	}

	handleChange(event) {
		this.setState({
			searchType: event
		});
	}

	onSearchBtnClickEvent(e) {
		// 在分类页面时不跳转 需要单独处理
		let path = hashHistory.getCurrentLocation().pathname;
		if (path.includes('searchModule')) {
			var params = {
				type: this.state.searchType,
				keyword: this.state.keyword
			};
			Emiter.emit('searchChange', params);
		}else{
			hashHistory.push(`/searchModule/${this.state.searchType}/${this.state.keyword}`)
		}
	}

	handlePressEnter(e){
		if(e.target.className.indexOf("searchInput")>=0){
			this.onSearchBtnClickEvent();
		}
	}

	loginBtnClick(name, event) {
		if (name === '登录') {
			hashHistory.push('login');
		} else {
			hashHistory.push(`personal/center`);
		}
	}
	loginChange(params) {
		if (params) {
			this.setState({
				userId: params.id,
				loginName: params.name + '欢迎您！',
				registerName: '退出'
			})
		} else {
			this.setState({
				loginName: '登录',
				registerName: '注册'
			});
		}
	}

	registerBtnClick(name, event) {
		if (name === '注册') {
			hashHistory.push('register');
		} else {
			// 退出登录
			window.localStorage.clear();
			// window.localStorage.setItem('userId', '');
			// window.localStorage.setItem('userName', '');
			// sessionStorage.setItem('access_token', '');
			this.setState({
				loginName: '登录',
				registerName: '注册'
			});
			Emiter.emit('unloginChange');
			hashHistory.push("/login");
		}
	}

	render() {
		const path = hashHistory.getCurrentLocation().pathname || '';
		const needSeacrh = (path.includes('login') || path.includes('register')) || path.includes('findpwd') || path.includes('resetpwd')? false : true;
		const needTopNav = (path.includes('login') || path.includes('register')) || path.includes("personal") || path.includes('findpwd') || path.includes('resetpwd') ? false : true;
		const pageName = this.props.pagename;
		const menu = (
			<MyMenu data={this.state.list} />
		);
		return (
			<div className='header-page'>
				{/*--------------头部-------------------*/}
				<SubEmiter eventName="loginChange" listener={this.loginChange.bind(this)}></SubEmiter>
				<header>
					<div className='headContent clearfix'>
						<span className='login'>
							<span className='logo-item' onClick={this.loginBtnClick.bind(this, this.state.loginName)}>{this.state.loginName}</span>
							<span className='logo-item' onClick={this.registerBtnClick.bind(this, this.state.registerName)}>{this.state.registerName}</span>
						</span>
						<Link to='/'><span className='logo left'></span></Link>
						<span className='welcome'>
							<div>欢迎来到长江大数据</div>
							<div>{`${new Date().getFullYear()}年${new Date().getMonth() + 1}月${new Date().getDate()}日！`}</div>
						</span>
					</div>
					{
						needSeacrh ?
							<div className='headTopImage'></div>
							: null
					}
					{
						needSeacrh ?
							<div className='search-head'>
								<span className='dropdown'>
									<Dropdown overlay={menu} placement="bottomLeft">
										<a className='ant-dropdown-link' href='#'>
											全部文献分类 <Icon type='down' />
										</a>
									</Dropdown>
								</span>
								<span className='searchModule'>
									<Select defaultValue="desc" onChange={this.handleChange.bind(this)} style={{color:"#999999"}} className='selectInput'>
										<Option value="desc">全文</Option>
										<Option value="author">作者</Option>
										<Option value="title">篇名</Option>
									</Select>
									{/* <select className='selectInput' defaultValue='desc' onChange={this.handleChange.bind(this)} style={{paddingLeft:4,color:"#999999"}}>
										<option value='desc'>全文</option>
										<option value='author'>作者</option>
										<option value='title'>篇名</option>
									</select> */}
									<Input key={2} className='searchInput' type='text' onChange={this.textOnChange.bind(this)} onPressEnter={this.handlePressEnter.bind(this)}/>
									<div className='searchBtn'  onClick={this.onSearchBtnClickEvent.bind(this)} ><Icon type="search" /><span>搜索</span></div>
								</span>
								{/* <a href="javascript:;" className="kuaku">跨库选择高级搜索</a> */}
							</div>
							: null
					}
				</header>

				{/*--------------导航条-------------------*/}
				{
					needTopNav ?
						<TopNav id={this.props.id}/>
						: null
				}
			</div>
		)
	}
}

export default Header;