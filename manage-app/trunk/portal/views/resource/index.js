import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import './style.css';
import '../global.css';
import { Tabs, Select, DatePicker, Button, Input } from 'antd';
const TabPane = Tabs.TabPane;
const Option = Select.Option;
import { get, post } from 'utils/request.js';
import {
	SubEmiter,
	Emiter
} from 'utils';
import BookManage from './bookManage.js';
import BookNew from './bookNew.js';
import BookModify from './bookModify.js';
import ResourceManage from './resourceManage.js';
import ResourceNew from './resourceNew.js';
import ResourceModify from './resourceModify.js';
import BookClassify from './bookClassify.js';
import BookClassifyNew from './bookClassifyNew.js';
import VipGood from './vipGood.js';
import VipGoodNew from './vipGoodNew.js';

export default class Resource extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			activeKey: '1',
			panel: [],
			staticPanels: []
		};
	}

	componentWillMount() {
		this.initStaticPanel();
	}

	initStaticPanel = () => {
		let staticPanels = [];
		let allowMap = {};
		const allowMapString = localStorage.getItem('ballowMap');
		if (!allowMapString) {
			return;
		} else {
			allowMap = JSON.parse(allowMapString);
		}
		if (allowMap[118]) {
			staticPanels.push(
				<TabPane tab='图书管理' key='1' closable={false}>
					<BookManage />
				</TabPane>
			);
		}
		if (allowMap[120]) {
			staticPanels.push(
				<TabPane tab='资源管理' key='2' closable={false}>
					<ResourceManage />
				</TabPane>
			)
		}
		if (allowMap[119]) {
			staticPanels.push(
				<TabPane tab='图书分类' key='3' closable={false}>
					<BookClassify />
				</TabPane>
			);
		}
		if (allowMap[121]) {
			staticPanels.push(
				<TabPane tab='会员商品' key='4' closable={false}>
					<VipGood />
				</TabPane>
			);
		}
		this.setState({
			staticPanels,
			activeKey: staticPanels.length>0 ? staticPanels[0].key : 1
		});
	}

	onChange = (activeKey) => {
		this.setState({ activeKey });
		if(activeKey == 3) {
			Emiter.emit('refreshBookClassifyList',true)
		}
	}

	onEdit = (targetKey, action) => {
		this[action](targetKey);
	}

	add = ({ operName, id, info }) => {
		let title, content, key;
		switch (operName) {
			case 'book-new':
				title = '图书新增';
				content = <BookNew />;
				key = '5';
				break;
			case 'book-modify':
				title = '图书修改';
				content = <BookModify id={id} info={info} />;
				key = '6';
				break;
			case 'resource-new':
				title = '资源新增';
				content = <ResourceNew />;
				key = '7';
				break;
			case 'resource-modify':
				title = '资源修改';
				content = <ResourceModify id={id} data={info} />;
				key = '8';
				break;
			case 'bookclassify-new':
				title = `图书分类${id ? '修改' : '新增'}`;
				content = <BookClassifyNew id={id} />;
				key = '9';
				break;
			case 'vipgood-new':
				title = `会员商品${id ? '修改' : '新增'}`;
				content = <VipGoodNew id={id} data={info} />;
				key = '10';
				break;
		}
		this.genelPanel({
			title: title,
			content: content,
			key: key
		});
	}

	genelPanel = (tabParam) => {
		let panel = [];
		let { title, content, key } = tabParam;
		panel.push({ title, content, key });
		this.setState({
			panel: panel,
			activeKey: key
		});
	}

	remove = (targetKey) => {
		let activeKey;
		switch (targetKey) {
			case '5':
			case '6':
				activeKey = '1';
				break;
			case '7':
			case '8':
				activeKey = '2';
				break;
			case '9':
				activeKey = '3';
				break;
			case '10':
				activeKey = '4';
				break;
			default:
				activeKey = '1';
				break;
		}

		this.setState({
			panel: [],
			activeKey: activeKey
		});
	}

	onTabClick = (targetKey) => {
		if (targetKey === '1') {
		}
	}

	render() {
		const isShow = false;
		return (
			<Wrapper>
				<SubEmiter eventName='openPanel' listener={this.add}></SubEmiter>
				<SubEmiter eventName='closePanel' listener={this.remove}></SubEmiter>
				<Tabs type='card'
					className='tab'
					onChange={this.onChange}
					activeKey={this.state.activeKey}
					type='editable-card'
					onEdit={this.onEdit}
					hideAdd
					onTabClick={this.onTabClick.bind(this)}>
					{
						this.state.staticPanels.map((item) => item)
					}
					{
						this.state.panel.map((item, index) => (
							<TabPane tab={item.title} key={item.key}>
								{item.content}
							</TabPane>
						))
					}
				</Tabs>
			</Wrapper>
		)
	}
}