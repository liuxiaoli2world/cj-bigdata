import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import { Tabs, Select, DatePicker, Button, Input, Pagination } from 'antd';
const TabPane = Tabs.TabPane;
const Option = Select.Option;
import { get, post, formPost } from 'utils/request.js';

class Orders extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			currentPage: 1,
			total: 0,
			pageSize: 5,
			type: '',
			orderNo: '',
			orderDate: '',
			orderList: []
		};
	}

	componentWillMount() {
		this.getPageData();
	}

	getPageData = (pageNum = 1) => {
		let url = 'order/order/queryBy';
		let params = {
			pageNum: pageNum,
			pageSize: this.state.pageSize,
			orderType: this.state.type,
			condition: this.state.orderNo,
			orderDate: this.state.orderDate,
			scene : "bg" ,
		};
		const str = `pageNum=${pageNum}&pageSize=${this.state.pageSize}&orderType=${this.state.type}&condition=${this.state.orderNo}&orderDate=${this.state.orderDate}`;
		const role = localStorage.getItem('brole');
		const userId = localStorage.getItem('buserId');
		if (role === 'ROLE_AUTHOR') {
			params.userId = userId;
		}

		formPost(url, params)
			.then((jsonData) => {
				const data = jsonData.data;
				this.setState({
					orderList: data.list || [],
					currentPage: data.pageNum,
					total: data.total
				});
			});
	}

	queryBtnClick = (event) => {
		this.getPageData();
	}

	onPageChange = (num) => {
		this.getPageData(num);
	}

	typeChange = (value, option) => {
		this.setState({
			type: value
		});
	}

	handleChange = (event) => {
		this.setState({
			orderNo: event.target.value
		});
	}

	onChange = (event, value) => {
		this.setState({
			orderDate: value
		});
	}
	openOrder = (type, id, event) => {
		this.props.add(type, id);
	}
	render() {
		return (
			<div className='tab-content'>
				<div className='head'>
					<span className='label-name'>订单类型：</span>
					<Select size='large' className='select order-type' value={this.state.type} style={{ width: 120 }} onChange={this.typeChange}>
						<Option value=''>全部</Option>
						<Option value='book'>图书订单</Option>
						<Option value='vip'>会员订单</Option>
					</Select>
					<span className='label-name'>收货人/订单号：</span>
					<Input size='large' className="order-name" onChange={this.handleChange} />
					<span className='label-name'>下单时间：</span>
					<DatePicker size='large' className='datePicker' onChange={this.onChange} />
					<Button size='large' type='primary' onClick={this.queryBtnClick} className="query-btn">查询</Button>
				</div>
				{
					this.state.orderList.length > 0 ?
						<div>
							<div className='table'>
								<div className='table-head'>
									<span className='column1'>商品类型</span>
									<span className='column2'>订单类型</span>
									<span className='column3'>单价（元）</span>
									<span className='column4'>数量</span>
									<span className='column5'>金额（元）</span>
									<span className='column6'>状态</span>
									<span className='column7'>购买用户</span>
									<span className='column8'>操作</span>
								</div>
								{
									this.state.orderList.map((item, index) => (
										<div key={index} className='table-body-item'>
											<div className='table-body-item-row1'>
												<span className='row1-column1'>订单编号：{item.orderNo}</span>
												<span className='row1-column2'>创建时间：{item.createdAt}</span>
											</div>
											<div className='table-body-item-row2'>
												<span className='row2 column1'>
													<img className='row2-img' src={item.imageUrl} />
													{item.goodsName}
												</span>
												<span className='row2 column2'>{item.goodsType === 'book' ? '图书订单' : '会员订单'}</span>
												<span className='row2 column3'>{item.unitPrice}</span>
												<span className='row2 column4'>{item.num}</span>
												<span className='row2 column5'><i>{item.amount}
													{
														item.goodsType === 'book' && (parseFloat(item.unitPrice) * parseFloat(item.num) - (parseFloat(item.amount) || 0)) > 0 ?
															<strong>（全本优惠 {(parseFloat(item.unitPrice) * parseFloat(item.num) - (parseFloat(item.amount) || 0)).toFixed(2)}元）</strong>
															: null
													}
												</i></span>
												<span className='row2 column6'>{item.payStatus==0?"待支付":"已完成"}</span>
												<span className='row2 column7'><img src={require('assets/images/buyer.png')} />{item.username}</span>
												<span className='row2 column8'><Button className='btn' onClick={this.openOrder.bind(this, item.goodsType, item.orderId)}>订单详情</Button></span>
											</div>
										</div>
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

class BookRightContent extends React.Component {
	render() {
		return (
			<div className="right content">
				<div>
					购买{this.props.fullPrice == '1' ? '全本' : '章节'}
					<div className="sepr"></div>
				</div>
				<div className="dir">
					{
						this.props.list.map((item, index) => (
							<div key={index}>{item.dirName}</div>
						))
					}
				</div>
			</div>
		)
	}
}

class VipRightContent extends React.Component {
	render() {
		return (
			<div className="right content">
				<div>
					会员信息明细
					<div className="sepr"></div>
				</div>
				<div>购买时长：{this.props.duration}个月</div>
			</div>
		)
	}
}

class Order extends React.Component {
	constructor(props) {
		super(props);
		this.state = {

		};
	}

	render() {
		return (
			<div className="vip-out">
				<div className="head"><img src={require('assets/images/vip.png')} />{this.props.title}</div>
				<div className="top">
					<div className="left content">
						<div className="content-head">
							基本信息
							<div className="sepr"></div>
						</div>
						<div className="left-column">
							<div>订单编号：{this.props.orderNo}</div>
							<div>订单金额：{this.props.amount}</div>
							<div>购买用户：{this.props.username}</div>
						</div>
						<div className="right-column">
							<div>提交时间：{this.props.createdAt}</div>
							<div>交付状态：{this.props.payStatus==0?"待支付":"已完成"}</div>
						</div>
					</div>
					{this.props.rightContent}
				</div>
				<div className="table-head">
					<span className="column1">商品详情</span>
					<span className="column2">单价（元）</span>
					<span className="column3">数量</span>
					<span className="column4">总价（元）</span>
					<span className="column5">订单状态</span>
				</div>
				<div className="table-content">
					<span className="column1"><img src={this.props.imageUrl} />{this.props.goodsName}</span>
					<span className="column2">{this.props.unitPrice}</span>
					<span className="column3">{this.props.num}</span>
					<span className="column4">{this.props.amount}</span>
					<span className="column5">{this.props.payStatus==0?"待支付":"已完成"}</span>
				</div>
			</div>
		)
	}
}

export default class DataAnalaysis extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			activeKey: '1',
			panel: [],
			bookOderDisplay: false,
			vipOderDisplay: false
		};
	}

	onChange = (activeKey) => {
		this.setState({ activeKey });
	}

	onEdit = (targetKey, action) => {
		this[action](targetKey);
	}

	add = (type, id) => {
		this.queryDetail(id, type);
	}

	queryDetail = (id, type) => {
		const self = this;
		get(`order/order/query?orderId=${id}&type=${type}`)
			.then((jsonData) => {
				const data = jsonData.data;
				return data;
			}).then((data) => {
				const { bookDirNames, ...props } = data;
				if (type === 'book') {
					this.genelPanel({
						title: '图书订单详情',
						content: <Order rightContent={<BookRightContent list={data.bookDirNames} />} title='图书订单详情' {...props} />,
						key: '2'
					});
				} else {
					this.genelPanel({
						title: '会员订单详情',
						content: <Order rightContent={<VipRightContent {...props} />} title='会员订单详情' {...props} />,
						key: '3'
					});
				}
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
				{/* <div className='sper'></div> */}
				<Tabs type='card'
					className='tab'
					hideAdd
					onChange={this.onChange}
					activeKey={this.state.activeKey}
					type='editable-card'
					onEdit={this.onEdit}
					onTabClick={this.onTabClick.bind(this)}>
					<TabPane tab='订单管理' key='1' closable={false}>
						<Orders add={this.add} />
					</TabPane>
					{
						this.state.panel.map((item, index) => (
							<TabPane tab='订单详情' key={item.key}>
								{item.content}
							</TabPane>
						))
					}
				</Tabs>
			</Wrapper>
		)
	}
}