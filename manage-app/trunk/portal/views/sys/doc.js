import React from 'react';
import { Link, hashHistory } from 'react-router';
import { get, post } from 'utils/request.js';
import { Table, Menu, Icon, Input, Upload, Button, message, Select, Pagination ,Popconfirm} from 'antd';
import config from '../../config/index.js';
import { getParams } from 'utils/commonTool.js';
import { SubEmiter, Emiter } from 'utils';
import './style.scss';

const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;
const Option = Select.Option;

class NestedTable extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			columns: [
				{ title: '分类名称', width: 400, key: '///', className: 'parent', render: (record) => (<Input value={record.name} size="large" className="parentInput" onChange={this.inputChange.bind(this, record)} onBlur={this.inputOnBlur.bind(this, record, null)} />) },
				{ title: '分类图片', width: 400, key: 'Action1', render: (record) => { return <span style={{ display: 'inline-block' }}> <Upload className='logoBtn' fileList={record.fileList} listType='picture' style={{ float: 'left' }} onChange={this.uploadImg.bind(this, record)} multiple={false} action={config.imageUrl} accept='.png,.jpg,.gif'>{record.fileList.length < 1 ? <Button icon='plus' /> : null}</Upload>{record.fileList.length < 1 ? <span className="tips">(图片限制700px*150px)</span> : null}</span> } },
				{ title: '添加子目录', width: 400, key: 'Action2', render: (record) => { return <span style={{cursor: 'pointer'}} onClick={this.addChild.bind(this, record)}><img className="childImg" src={require('assets/images/child.png')} />添加子分类</span> } },
				{
					title: '操作', key: 'Action3', render: (record) => (
						record.name ?
							<Popconfirm title="确定删除该分类?" onConfirm={this.deleteChild.bind(this, record, null)} okText="是" cancelText="否">
								<span className="delClass" style={{cursor:"pointer"}}>
										删除
								</span>
							</Popconfirm>
							: null
					)
				},
				{ title: <span onClick={this.closeList}><Icon type="close" /></span>, key: 'Action4', render: (record) => <span style={{ opacity: 0 }}><Icon type="close" /></span> }
			],
			eKeys: []
		};
	}

	componentWillMount() {
		this.setState({
			data: this.props.data,
			isAdd: true
		});
	}

	componentWillReceiveProps(nextprops) {
		this.setState({
			data: nextprops.data,
			isAdd: true
		});
	}

	addChild = (record) => {
		const index = record.second ? record.second.length : 0;
		if (index == 0 || record.second[record.second.length - 1].id) {
			record.second.push({ name: '', key: `${record.id}-${index}` });
			this.updateData(record);
		} else {
			message.warning('有未保存的新分类！');
		}
		this.setState({
			eKeys: [record.key]
		})
	}

	inputChange = (record, e) => {
		record.name = e.target.value;
		this.updateData(record);
	}

	inputOnBlur = (record, record2, e) => {
		const name = e.target.value;
		if (!name) {
			return;
		}
		let isModify = false;
		let params = {};
		if (!record2) {
			// 新增一级分类
			if (record.id) {
				isModify = true;
				params.menuId = record.id;
			}
		} else {
			// 新增二级分类
			if (record2.id) {
				isModify = true;
				params.menuId = record2.id;
			} else {
				params.parentMenuId = record.id;
			}
		}

		params.menuName = name;

		let baseUrl;
		if (isModify) {
			baseUrl = 'book/menu/modify';
		} else {
			baseUrl = 'book/menu/add'
		}
		post(`${baseUrl}`, params)
			.then(jsonData => {
				// this.getAllData();
				Emiter.emit('refreshData');
			})
	}

	uploadImg = (record, e) => {
		let fileList = e.fileList;
		let url = '';
		fileList = fileList.map((file) => {
			if (file.response) {
				url = file.response.data;
				file.url = file.response.data;
				this.updateImg(record, url);
			}
			return file;
		});
		if (e.file.status === 'removed') {
			this.updateImg(record, url);
		}
		record.fileList = fileList;
		this.updateData(record);
	}

	updateImg = (record, url) => {
		const params = {
			menuId: record.id,
			imageUrl: url
		};
		post('book/menu/modify', params)
			.then(jsonData => {
				// this.getAllData();
				Emiter.emit('refreshData');
			})
	}

	childInputChange = (record, record2, e) => {
		record2.name = e.target.value;
		this.updateData(record, record2);
	}

	deleteChild = (record, record2) => {
		let data = this.state.data;
		let menuId;
		if (!record2) {
			menuId = record.id;

		} else {
			menuId = record2.id;
		}
		menuId &&
			get(`book/menu/remove/${menuId}`)
				.then(jsonData => {
					if (jsonData.meta && jsonData.meta.success) {
						if(jsonData.data=='删除成功!'){
							message.success('删除成功')
							Emiter.emit('refreshData');
						}else{
							message.error(jsonData.data)
						}
					}
				});
	}

	updateData = (record, record2) => {
		//Object.prototype.toString.call(record) !== '[object Array]' ||
		if (!record || record.length < 1) {
			return;
		}
		let data = this.state.data;
		// let {data} = this.props;
		if (record.id) {
			// 修改一级分类
			for (let i = 0, len = data.length; i < len; i++) {
				const record0 = data[i];
				if (record0.id === record.id) {
					const data2 = record.second || [];
					if (record2) {
						if (!record2.id) {
							// 新增二级分类
							const index = data2.findIndex((val) => val.key === record2.key);
							if (index >= 0) {
								data2[index] = record2
							} else {
								data2.push(record2);
							}
						} else {
							// 修改二级分类
							for (let j = 0, len = data2.length; j < len; j++) {
								const record20 = data2[j];
								if (record20.id === record2.id) {
									record20.name = record2.name;
									break;
								}
							}
						}
					}
					record0.name = record.name;
					record0.url = record.url;
					break;
				}
			}
		}
		this.setState({
			data: data
		});
	}

	handleAdd = () => {
		let { isAdd, data } = this.state;
		if (isAdd) {
			const newData = {
				name: '',
				fileList: [],
				key: Date.parse(new Date()) / 1000
			};
			data.push(newData);
			this.setState({
				data,
				isAdd: false
			});
		} else {
			message.warning('有未保存的新分类！');
		}
	}

	handleChange = (expanded, record) => {
		if (expanded) {
			this.setState({
				eKeys: [record.key]
			})
		} else {
			this.setState({
				eKeys: []
			})
		}
	}

	closeList = () => {
		Emiter.emit('hideList');
	}

	componentDidUpdate() {
		const { isAdd } = this.state;
		!isAdd && $('.ant-table-body').scrollTop($('.ant-table-body')[0].scrollHeight);
	}

	render() {
		const expandedRowRender = (record) => {
			const columns = [
				{
					key: '***', className: 'child', width: 1200, render: (record2) => (
						<div>
							<span className="line"></span>
							<Input className="childInput" size="large" value={record2.name} onChange={this.childInputChange.bind(this, record, record2)} onBlur={this.inputOnBlur.bind(this, record, record2)} />
						</div>
					)
				},
				{
					key: 'operation',
					render: (record2) => (
						record2.name ?
						<Popconfirm title="确定删除该子分类?" onConfirm={this.deleteChild.bind(this, record, record2)} okText="是" cancelText="否">
							<span className="delClass" style={{cursor:"pointer"}}>
								删除
          				</span>
					  </Popconfirm>
						: null
					)
				},
				{ title: <span onClick={this.closeList}><Icon type="close" /></span>, key: 'Action4', render: (record) => <span style={{ opacity: 0 }}><Icon type="close" /></span> }
			];

			return (
				<Table
					columns={columns}
					dataSource={record.second}
					pagination={false}
					showHeader={false}
				/>
			);
		};
		return (
			<Table
				className={`sysTable ${this.props.fade} ${this.state.data.length===0?'noData':''}`}
				style={{ height: '655px' }}
				columns={this.state.columns}
				expandedRowRender={expandedRowRender}
				expandedRowKeys={this.state.eKeys}
				onExpand={this.handleChange}
				dataSource={this.state.data}
				footer={() => (<div style={{ height: '50px', paddingTop: '11px' }}><Button type="primary" style={{ float: 'left' }} onClick={this.handleAdd}>添加新分类</Button></div>)}
				pagination={false}
				scroll={{ y: 555 }}
			/>
		);
	}
}

class DocList extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			result: [],
			type: 'book',
			pageSize: 8,
			info: null,
			currentPage: 1,
			total:0
		};
	}
	
	componentWillReceiveProps (nextprops) {
		const {activeKey} = nextprops;
		const info = {
			parentMenuId: 1,
			id: activeKey
		}
		if(activeKey){
			this.getData({parentMenuId:1,id:activeKey} , 1 , this.state.type)
			this.setState({
				info
			})
		}
	}

	handleClick = (e) => {
		const { info } = e.item.props;
		this.setState({
			info
		})
		this.getData(info, 1, this.state.type);
	}
	onOpenChange = (openKeys) => {
		const defaultKeys = this.state.openKeys ? this.state.openKeys : [this.props.openKey + ''];
		const latestOpenKey = openKeys.find(key => !(defaultKeys.indexOf(key) > -1));
		const latestCloseKey = defaultKeys.find(key => !(openKeys.indexOf(key) > -1));
		let nextOpenKeys = [];
		if (latestOpenKey) {
			nextOpenKeys = this.getAncestorKeys(latestOpenKey).concat(latestOpenKey);
		}
		if (latestCloseKey) {
			nextOpenKeys = this.getAncestorKeys(latestCloseKey);
		}
		this.setState({ openKeys: nextOpenKeys });
	}

	getAncestorKeys = (key) => {
		const map = {
		};
		return map[key] || [];
	}

	handleTypeChange = (val) => {
		this.setState({
			type: val
		});
		this.getData(this.state.info, 1, val);
	}

	getData(info, pageNum, type) {
		if (info) {
			const { pageSize } = this.state;
			let url;
			switch(type) {
				case 'book':
					url = `book/book/queryBy?parentMenuId=${info.parentMenuId}&childMenuId=${info.id}`
					break;
				case 'jour':
					url = `book/content/selectPeriodicalByMeauId?menuPId=${info.parentMenuId}&menuId=${info.id}`
					break;
				case 'pic':
					url = `book/multifile/queryBy?parentMenuId=${info.parentMenuId}&childMenuId=${info.id}&multiType=pic`
					break;
				default:
					url = `book/book/queryBy?parentMenuId=${info.parentMenuId}&childMenuId=${info.id}`
					break;
			}
			get(`${url}&pageNum=${pageNum}&pageSize=${pageSize}`)
				.then((data) => {
					window.scrollTo(0, 0);
					if (data && data.data && data.meta.success) {
						const result = data.data.list || [];
						this.setState({
							result,
							selectId: info.id,
							currentPage: data.data.pageNum,
							total: data.data.total
						})
					}
				})
		}
	}

	onPageChange = (num) => {
        this.getData(this.state.info,num,this.state.type);
	}
	
	edit = () => {
		Emiter.emit('showList');
	}

	render() {
		const { data, activeKey, openKey } = this.props;
		const { result, selectId } = this.state;
		return (
			<div className="clearfix doc">
				<Menu mode="inline"
					style={{ width: 200, float: 'left', flex: '0 0 200px', marginRight: '30px', minHeight: '700px', background: '#e7ecf4' }}
					onClick={this.handleClick}
					selectedKeys={selectId ? [this.state.selectId + ''] : [activeKey + ''] }
					openKeys={this.state.openKeys ? this.state.openKeys : [openKey + '']}
					onOpenChange={this.onOpenChange}
					header={'header'}
				>
					<MenuItemGroup key="g1" title={<div><Icon type="bars" /><span>总类目</span><img src={require('assets/images/edit.png')} alt="" onClick={this.edit} /></div>}>
						{data.map((item, i) => {
							if (!item.second) {
								return <Menu.Item key={item.id} style={{ fontSize: '14px' }} info={item}><span className="iblock"></span><span>{item.name}</span></Menu.Item>
							} else {
								return (
									<SubMenu key={item.id} title={<span><span className="iblock"></span>{item.name}</span>} >
										{item.second.map((list, j) => (
											<Menu.Item key={list.id} info={list}>— {list.name}</Menu.Item>
										))}
									</SubMenu>
								)
							}
						})}
					</MenuItemGroup>
				</Menu>
				<div>
					<div className="header">
						<span className='label-name'>文献类型：</span>
						<Select className='select order-type' defaultValue='book' style={{ width: 120 }} onSelect={this.handleTypeChange} size={'large'}>
							<Option value='book'>图书</Option>
							<Option value='jour'>期刊</Option>
							<Option value='pic'>图片</Option>
						</Select>
					</div>
					{result.length > 0 ?
						<div className="docContent">
							<div>
							{result.map((item, i) => (
								<Content key={i} {...item} />
							))}
							</div>
							<Pagination className="pagenation" showQuickJumper current={this.state.currentPage} total={this.state.total} pageSize={this.state.pageSize} onChange={this.onPageChange} />

						</div> :
						<div className="nodata">
							<img src={require('assets/images/no-data.png')} alt="" />
						</div>
					}
				</div>
			</div>
		)
	}
}

class Content extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		let typeName = '';
		typeName = this.props.contentId ? '期刊' :
				   this.props.bookId ? '图书' :
				   '图片';
		return (
			<span className={`box ${this.props.bookId ? 'book' : ''}`}>
				<div className='box-head'>
					<div className="headLogo">
						<img className="head-img" 
							src={
								this.props.imageUrl ? this.props.imageUrl : 
								this.props.path ? this.props.path :
								this.props.bookImages && this.props.bookImages.length>0? this.props.bookImages[0].imageUrl : 
								require('assets/images/no-img.png')} alt="" />
					</div>
					<div className="docInfo">
						<div className='content-head'>{this.props.title || this.props.bookName}</div>
						<div className='content-type'>资源类型：{typeName}</div>
					</div>
				</div>
			</span>
		)
	}
}

export default class Doc extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			data: [],
			openKey: '',
			activeKey: '',
			show: ''
		};
	}

	componentWillMount() {
		this.getAllData();
	}

	getAllData = () => {
		get('book/menu/queryAll')
			.then((jsonData) => {
				if (jsonData.meta.success) {
					const data = this.processMenu(jsonData.data || []);
					let activeKey;
					if(data.length > 0){
						activeKey = data[0].second.length>0 ? data[0].second[0].id : '';
						this.setState({
							data: data,
							openKey: data[0].id,
							activeKey
						});
					} else {
						this.setState({
							data: [],
							openKey: '',
							activeKey: ''
						})
					}
					
				}
			});
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
			item.key = i;
			item.name = item0.menuName;
			let imgList;
			if (item0.imageUrl) {
				const params = getParams(item0.imageUrl);
				imgList = [{
					uid: -2,
					status: 'done',
					url: params.url,
					thumbUrl: params.url,
					name: params.name,
					response: {
						data: item0.imageUrl
					}
				}];
			} else {
				imgList = [];
			}
			item.fileList = imgList;
			if (parentMenuId == 0) {
				// 父节点
				// 记下父节点的索引，方便下一步查找
				regist[menuId] = parentList.length;
				item.second = [];
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
			let children = parentList[index].second || [];
			children.push(item);
		}
		return parentList;
	}

	add = () => {
		this.setState({
			show: 'up'
		});
	}

	closeModule = () => {
		this.setState({
			show: 'down'
		});
	}

	render() {
		const { data, activeKey, openKey } = this.state;
		return (
			<div>
				<SubEmiter eventName='showList' listener={this.add}></SubEmiter>
				<SubEmiter eventName='hideList' listener={this.closeModule}></SubEmiter>
				<SubEmiter eventName='refreshData' listener={this.getAllData}></SubEmiter>
				<DocList data={data} activeKey={activeKey} openKey={openKey}/>
				<NestedTable fade={this.state.show} data={data} />
			</div>
		)
	}
}