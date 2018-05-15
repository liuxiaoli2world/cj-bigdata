import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import './style.css';
import { Tabs, Select, DatePicker, Button, Form, Input, message } from 'antd';
import { SubEmiter, Emiter } from '../utils/index.js';
import InfoList from './List';
import Classify from './classify';
import EditExpert from './EditExpert';
import Composition from './Composition';
import { get, post } from '../utils/request';
const FormItem = Form.Item;
const TabPane = Tabs.TabPane;
const Option = Select.Option;

export default class Expert extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			activeKey: '1',
			panel: [],
			classList: [],
			staticPanels: []
		};
	}

	componentDidMount () {
		this.getDomain()
		this.initStaticPanel()
	}

	getDomain = () => {
		get(`expert/domain/selectAllDomain?pageNum=1&pageSize=100`)
		.then((res) => {
			if(res.meta.success){
				this.setState({
					classList: res.data.list
				})
			}
		})
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
		if (allowMap[111]) {
			staticPanels.push(
				<TabPane tab='专家管理' key='1' closable={false}>
					<InfoList domain={this.state.classList}/>
				</TabPane>
			);
		}
		if (allowMap[112]) {
			staticPanels.push(
				<TabPane tab='专家分类' key='2' closable={false}>
					<Classify domain={this.state.classList}/>
				</TabPane>
			)
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

	add = ({operName,info }) => {
		let title, content, key;
		switch (operName) {
			case 'expert-new':
				title = '专家新增';
				content = <EditExpert domain={this.state.classList}/>;
				key = '3';
				break;
			case 'expert-modify':
				title = '专家修改';
				content = <EditExpert info={info} domain={this.state.classList}/>;
				key = '4';
				break;
			case 'class-new':
				title = '专家分类新增';
				content = <EditClass />;
				key = '5';
				break;
			case 'class-modify':
				title = '专家分类修改';
				content = <EditClass info={info} />;
				key = '6';
				break;
			case 'composition':
				title = '学术著作';
				content = <Composition info={info} />;
				key = '7';
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
			case '3':
			case '4':
			case '7':
				activeKey = '1';
				break;
			case '5':
			case '6':
				activeKey = '2';
				break;
			default:
				activeKey = '1';
				break;
		}	

		this.setState({
			panel: [],
			activeKey: activeKey,
		});
	}

	render() {
		return (
			<Wrapper>
				<SubEmiter eventName='openPanel' listener={this.add}></SubEmiter>
				<SubEmiter eventName='closePanel' listener={this.remove}></SubEmiter>
				<SubEmiter eventName='refreshClass' listener={this.getDomain}></SubEmiter>
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

class ClassForm extends React.Component {
	constructor(props) {
		super(props);
	}

	componentDidMount() {
		if (this.props.info) {
			let { name, domainDesc } = this.props.info;
			domainDesc = domainDesc ? domainDesc : '';
			this.props.form.setFieldsValue({
				name, domainDesc
			})
		}
	}

	cancel() {
		Emiter.emit("closePanel",'5')
	}

	save = (e) => {
		e.preventDefault();
		const form = this.props.form;
		let url = `expert/domain/insertDomain`;
		this.props.form.validateFields((err, values) => {
			if (!err) {
				if (this.props.info) {
					values.domainId = this.props.info.domainId;
					url = `expert/domain/updateDomain`;
				}
				post(url, values)
					.then((res) => {
						if (res.meta.success) {
							message.success("专家分类添加成功");
							this.cancel();
							Emiter.emit("addClassOk", { "status": true })
							Emiter.emit("refreshClass")
						}
					})
			}
		});
	}

	render() {
		const { getFieldDecorator } = this.props.form;
		const formItemLayout = {
			labelCol: { span: 1 },
			wrapperCol: { span: 5 }
		};

		return (
			<Form onSubmit={this.save}>
				<FormItem
					label="分类名称"
					{...formItemLayout}
				>
					{getFieldDecorator('name', {
						rules: [{ required: true, message: '请输入分类名称!' },{
							max : 50 , message : "分类名称不能超过50位字符！"
						}],
					})(
						<Input />
						)}
				</FormItem>
				<FormItem
					{...formItemLayout}
					label="内容简介"
				>
					{getFieldDecorator('domainDesc',{
						max : 1500 , message : "内容简介不能超过1500位字符！"
					})(
						<textarea className='domainDesc'></textarea>
					)}
				</FormItem>
				<FormItem
					wrapperCol={{ span: 4, offset: 1 }}
					className={'editButtons'}
				>
					<Button type="primary" htmlType="submit" className="save-btn">保存</Button>
					<Button onClick={this.cancel.bind(this)}>取消</Button>
				</FormItem>
			</Form>
		);
	}

}

class EditClass extends React.Component {
	render() {
		const EditClass = Form.create()(ClassForm);
		return (
			<EditClass info={this.props.info} />
		)
	}
}