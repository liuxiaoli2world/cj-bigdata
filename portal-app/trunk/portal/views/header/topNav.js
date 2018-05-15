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
import { Carousel, Tabs, Menu } from 'antd';
import { get } from 'views/util/request';

const TabPane = Tabs.TabPane;
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

class TopNav extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			index: -1,
			open: false,
			typeValue: 'drama',
			keyword: '',
			navList: []
		}
	}

	componentWillMount() {
		get('book/menu/queryRoot')
			.then((jsonData) => {
				if (jsonData.meta.success) {
					this.setState({
						navList: this.buildNavListData(jsonData.data)
					});
				}
			})
	}

	componentDidMount() {
		this.setState({
			current: this.props.id
		})
	}

	componentWillReceiveProps(nextProps) {
		if(nextProps.id !== this.props.id) {
			this.setState({
				current: nextProps.id
			})
		}
	}

	buildNavListData(list) {
		let navListData = [];
		for (let i = 0; i < list.length; i++) {
			let item0 = list[i];
			let item = {};
			item.path = { pathname: `classify/${item0.menuId}` };
			item.name = item0.menuName;
			item.id = item0.menuId;
			navListData.push(item);
		}

		return navListData;
	}

	handleClick = (e) => {
		this.setState({
			current: e.key,
		});
	}

	render() {
		const path = hashHistory.getCurrentLocation().pathname;
		const pageName = this.props.pagename;
		return (
			<div>
				{/*--------------导航条-------------------*/}
				<nav>
					<div className='content' style={{ background: 'transparent', color: 'white' }}>
						<Menu mode='horizontal' className="topnav" selectedKeys={[this.state.current]} onClick={this.handleClick}>
							{
								this.state.navList.map((navli, i) => (
									i<10 ?
									<Menu.Item className='menuItem ' style={{ background: '#3d95d5', fontSize: '16px' }} key={navli.id}><Link to={navli.path}>{navli.name}</Link></Menu.Item>
									:null
								))
							}
						</Menu>
					</div>
				</nav>
			</div>
		)
	}
}

export default TopNav;