import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import './style.css';
import { Tabs, Select, Button, Input, Pagination, Popconfirm } from 'antd';
import Detail from './detail.js';
import { get, post } from 'utils/request.js';
const TabPane = Tabs.TabPane;
const Option = Select.Option;
import {
	SubEmiter,
	Emiter
} from 'utils';

class List extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			type: 'notice',
			currentPage: 1,
			pageSize: 10,
			list: [],
			total: 0,
			keyword: ''
		};
	}

	componentWillMount() {
		this.getPageData();
	}

	getPageData(pageNum = 1, method) {
		let url;
		if (method !== 'search') {
			url = `book/content/queryAll?pageNum=${pageNum}&pageSize=${this.state.pageSize}&flag=${this.state.type}`;
		} else {
			url = `book/content/selectContentByCondition?pageNum=${pageNum}&pageSize=${this.state.pageSize}&contentType=${this.state.type}&condition=${this.state.keyword}`;
		}
		get(url)
			.then((jsonData) => {
				const data = jsonData.data;
				this.setState({
					list: data.list,
					currentPage: data.pageNum,
					total: data.total
				});
			});
	}

	onPageChange = (num) => {
		this.getPageData(num);
	}
	onChange = (event) => {
	}
	openDetail = (id, type, event) => {
		this.props.add(id, type);
	}
	delete = (id, event) => {
		get(`book/content/remove/${id}`)
			.then((jsonData) => {

			})
			.then(() => {
				this.getPageData(this.state.currentPage);
			});
	}

	handleChange = (e) => {
		this.setState({
			keyword: e.target.value
		});
	}
	handleTypeChange = (value) => {
		this.setState({
			type: value
		});
	}

	query = () => {
		const type = this.state.type;
		const keyword = this.state.keyword;
		this.getPageData(1, 'search');
	}

	render() {
		return (
			<div className='tab-content'>
				<div className='head'>
					<span className='label-name'>分类：</span>
					<Select size='large' className='select order-type' value={this.state.type} style={{ width: 120 }} onSelect={this.handleTypeChange}>
						<Option value='notice'>通知公告</Option>
						<Option value='headline'>长江要闻</Option>
						<Option value='history'>人文历史</Option>
						<Option value='periodical'>期刊</Option>
					</Select>
					<span className='label-name'>关键词：</span>
					<Input size='large' className='input' value={this.state.keyword} onChange={this.handleChange} />
					<Button size='large' type='primary' className='label-name' onClick={this.query}>查询</Button>
					<Button size='large' type='primary' className='right newBtn ant-btn-lg' icon="plus" onClick={this.openDetail.bind(this, '', 'add')} >新增</Button>
				</div>
				<SubEmiter eventName="refresh" listener={this.query}></SubEmiter>
				{
					this.state.list.length > 0 ?
						<div>
							<div className='table'>
								{
									this.state.list.map((item, index) => (
										<Content key={index} {...item} openModify={this.openDetail} delete={this.delete} />
									))
								}
							</div>
							<Pagination className="pagenation" showQuickJumper current={this.state.currentPage} total={this.state.total} pageSize={this.state.pageSize} onChange={this.onPageChange} />
						</div>
						:
						<div className="noData">
							<img src={require('assets/images/no-data.png')} alt="" />
						</div>
				}

			</div>
		);
	}
}

class Content extends React.Component {
	constructor(props) {
		super(props);
	}

	openModify = (id, type, event) => {
		this.props.openModify(id, type, event);
	}

	delete = (id, event) => {
		this.props.delete(id, event);
	}

	render() {
		return (
			<span className='box'>
				<div className='box-head'>
					<img className="head-img" src={this.props.imageUrl ? this.props.imageUrl : require('assets/images/no-img.png')} alt="" />
					<div className='content-head'>{this.props.title}</div>
					<div className='content-desc'>{this.props.contentDesc && this.props.contentDesc.length < 15 ? this.props.contentDesc : this.props.contentDesc && this.props.contentDesc.substring(0, 14) + '...'}</div>
					<div>
						<div className='left'>
							<img className="time-img" src={require('assets/images/time.png')} alt="" />
							{this.props.createdAt}
						</div>
						<div className='right'>
							<img className="view-img" src={require('assets/images/view.png')} alt="" />
							{this.props.pv}
						</div>
					</div>
				</div>
				<div className='foot'>
					<Button onClick={this.openModify.bind(this, this.props.contentId, 'modify')}>修改</Button>
					<Popconfirm title="是否确认删除?" onConfirm={() => this.delete(this.props.contentId)}>
						<Button>删除</Button>
					</Popconfirm>
				</div>
			</span>
		)
	}
}

export default class ContentList extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			activeKey: '1',
			panel: []
		};
	}

	onChange = (activeKey) => {
		this.setState({ activeKey });
	}

	onEdit = (targetKey, action) => {
		this[action](targetKey);
	}

	add = (id, type) => {
		if (type === 'add') {
			this.genelPanel({
				title: '内容新增',
				content: <Detail type='add' title='内容新增' cancel={this.cancel} />,
				key: '2'
			});
			this.setState({ cancelKey: '2' });
		} else {
			this.genelPanel({
				title: '内容修改',
				content: <Detail type='modify' id={id} title='内容修改' cancel={this.cancel} />,
				key: '3'
			});
			this.setState({ cancelKey: '3' });
		}
	}

	cancel = () => {
		this.remove(this.state.cancelKey);
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
		let activeKey = this.state.activeKey;
		this.setState({
			panel: [],
			activeKey: '1'
		});
	}

	onTabClick = (targetKey) => {
		if (targetKey === '1') {
		}
	}

	render() {
		return (
			<Wrapper>
				<Tabs type='card'
					className='tab'
					hideAdd
					onChange={this.onChange}
					activeKey={this.state.activeKey}
					type='editable-card'
					onEdit={this.onEdit}
					onTabClick={this.onTabClick.bind(this)}>
					<TabPane tab='内容管理' key='1' closable={false}>
						<List add={this.add} />
					</TabPane>
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