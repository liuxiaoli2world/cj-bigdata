import React from 'react';
import { Link, hashHistory } from 'react-router';
import { get, post } from 'utils/request.js';
import { SubEmiter, Emiter } from '../utils/index.js';
import { Tabs } from "antd";
import UserList from "./UserList.js";
import ExceptionSetting from "./ExceptionSetting.js";
import SuggestionsBox from "./SuggestionsBox.js";
import OrgManage from "./OrgManage.js";
import UserDetails from "./UserDetails.js";
import { Wrapper } from "./style.js";

const TabPane = Tabs.TabPane;

export default class User extends React.Component {
	constructor(props) {
		super(props);
		this.newTabIndex = 0;
		let panes = [];
		this.state = {
			activeKey: "1",    //当前激活tab面板的key
			panes,
			staticPanels: []
		};
	}

	componentDidMount() {
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
		if (allowMap[107]) {
			staticPanels.push(
				<TabPane tab="用户管理" key="1" closable={false}>
					<UserList />
				</TabPane>
			);
		}
		if (allowMap[108]) {
			staticPanels.push(
				<TabPane tab="机构管理" key="2" closable={false}>
					<OrgManage />
				</TabPane>
			)
		}
		if (allowMap[109]) {
			staticPanels.push(
				<TabPane tab="异常设置" key="3" closable={false}>
					<ExceptionSetting />
				</TabPane>
			);
		}
		if (allowMap[110]) {
			staticPanels.push(
				<TabPane tab="意见箱" key="4" closable={false}>
					<SuggestionsBox />
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
	}

	onEdit = (targetKey, action) => {
		this[action](targetKey);
	}

	add = (orgnum) => {
		let panes = this.state.panes;
		const activeKey = `detail${this.newTabIndex++}`;
		panes.forEach((pane, i) => {
			if (pane.key.indexOf("detail") >= 0) {
				let targetKey = pane.key;
				panes = panes.filter(pane => pane.key !== targetKey);
			}
		});
		panes.push({ title: "用户详情", content: <UserDetails orgnum={orgnum} />, key: activeKey });
		this.setState({ panes, activeKey })
	}

	remove = (targetKey) => {
		let activeKey = this.state.activeKey;
		let lastIndex;
		this.state.panes.forEach((pane, i) => {
			if (pane.key == targetKey) {
				lastIndex = i - 1;
			}
		});
		const panes = this.state.panes.filter(pane => pane.key !== targetKey);
		if (lastIndex >= 0 && activeKey === targetKey) {
			activeKey = panes[lastIndex].key;
		} else {
			activeKey = "2"
		}
		this.setState({
			panes,
			activeKey
		})
	}

	//查看用户详情
	onAddUserdetail = (param) => {
		if (param.see) {
			this.add(param.orgnum);
		}
	}

	onClose = () => {
		const panes = this.state.panes.filter(pane => pane.key !== this.state.activeKey);
		this.setState({
			panes,
			activeKey: '2'
		})
	}


	render() {
		let path = hashHistory.getCurrentLocation().pathname;
		return (
			<Wrapper>
				<SubEmiter eventName="close" listener={this.onClose} />
				<SubEmiter eventName="addUserdetail" listener={this.onAddUserdetail} />
				<Tabs type="editable-card" animated={false} hideAdd onChange={this.onChange} activeKey={this.state.activeKey} onEdit={this.onEdit}>
					{
						this.state.staticPanels.map((item) => item)
					}
					{
						this.state.panes.map((pane) => {
							return (
								<TabPane tab={pane.title} key={pane.key}>{pane.content}</TabPane>
							)
						})
					}
				</Tabs>
			</Wrapper>
		)
	}
}
