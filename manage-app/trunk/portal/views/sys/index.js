import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import './style.scss';
import '../global.css';
import { Tabs, Select, DatePicker, Button, Input } from 'antd';
const TabPane = Tabs.TabPane;
const Option = Select.Option;
import { get, post } from 'utils/request.js';
import Doc from './doc.js';
import Permission from './permission.js';
import EditPermission from './editPermission';
import HotWords from './hotWords';
import {
  SubEmiter,
  Emiter
} from 'utils';

export default class Sys extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			activeKey: '1',
			panel: [],
			staticPanels: []
		};
	}

	onChange = (activeKey) => {
		this.setState({ activeKey });
	}

	onEdit = (targetKey, action) => {
		this[action](targetKey);
	}

	add = ({operName,info }) => {
		let title, content, key;
		switch (operName) {
			case 'permission-new':
				title = '权限新增';
				content = <EditPermission/>;
				key = '5';
				break;
			case 'permission-modify':
				title = '权限修改';
				content = <EditPermission info={info}/>;
				key = '6';
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
		switch(targetKey) {
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

	initStaticPanel = () => {
		let staticPanels = [];
		let allowMap = {};
		const allowMapString = localStorage.getItem('ballowMap');
		if (!allowMapString) {
			return;
		} else {
			allowMap = JSON.parse(allowMapString);
		}
		if (allowMap[115]) {
			staticPanels.push(
				<TabPane tab='文献分类' key='1' closable={false}>
					<Doc/>
				</TabPane>
			);
		}
		if (allowMap[116]) {
			staticPanels.push(
				<TabPane tab='权限分组' key='2' closable={false}>
					<Permission/>
				</TabPane>
			)
		}
		if (allowMap[117]) {
			staticPanels.push(
				<TabPane tab='热词设置' key='3' closable={false}>
					<HotWords />
				</TabPane>
			);
		}
		this.setState({
			staticPanels,
			activeKey: staticPanels.length>0 ? staticPanels[0].key : 1
		});
	}

	componentDidMount(){
		this.initStaticPanel();
	}
	render() {
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
					>
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