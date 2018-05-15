import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './resouceManageModifyStyle.js';
import './style.scss';
import { Tabs, Select, Button, Input, Icon, Popconfirm, Modal, Upload, message } from 'antd';
const TabPane = Tabs.TabPane;
const Option = Select.Option;
import { get, post } from 'utils/request.js';
import ResourceNew from './resourceNew.js';
import {
    SubEmiter,
    Emiter
} from 'utils';
import { getParams } from 'utils/commonTool.js';
import config from '../../config/index.js';

class FileList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            list: [],
            loading: false,
            visible: false,
            fileList: [],
            multiType: 'pic',
            showCover : false,
        }
    }

    componentDidMount() {
        this.refreshUI(this.props.id);
    }

    componentWillReceiveProps(params) {
        this.refreshUI(params.id);
    }

    refreshUI = (id) => {
        this.setState({
            multifileId: id
        });
        this.getData(id);
    }

    getData(id0) {
        const id = id0 || this.props.id;
        if (!id) {
            return;
        }
        get(`book/multiitem/queryItems?multifileId=${id}&multiType=video&from=admin`)
            .then((res) => {
                if (res.meta.success) {
                    this.setState({
                        list: res.data || []
                    })
                }
            })
    }

    onDel(id) {
        get(`book/multiitem/remove/${id}`)
            .then((res) => {
                if (res.meta.success) {
                    message.success('删除成功');
                    this.getData();
                } else {
                    message.error('删除失败');
                }
            })
    }

    up = (item, i) => {
        if (i === 0) {
            return;
        }

        const list = this.state.list;
        const preList = list.slice(0, i - 1);
        const pre = list[i - 1];
        const nextList = list.slice(i + 1);
        this.exchangeRank(item, pre);
        const result = [...preList, item, pre, ...nextList];
        this.setState({
            list: result
        });
    }

    down = (item, i) => {
        const list = this.state.list;
        const len = list.length;
        if (i === len - 1) {
            return;
        }

        const preList = list.slice(0, i);
        const next = list[i + 1];
        const nextList = list.slice(i + 2);
        this.exchangeRank(item, next);
        const result = [...preList, next, item, ...nextList];
        this.setState({
            list: result
        });
    }

    exchangeRank = (item1, item2) => {
        const temp = item1.rank;
        item1.rank = item2.rank;
        item2.rank = temp;
    }

    save = (item) => {
        const list = this.state.list || [];
        let result = [];
        for (let i = 0, len = list.length; i < len; i++) {
            const { multiitemId, rank } = { ...list[i] };
            result.push({ multiitemId, rank });
        }
        // 保存排序
        result.length > 0 && post('book/multiitem/modifyRank', result).then((jsonData) => {
            if (jsonData.meta && jsonData.meta.success) {
                message.info('保存成功');
            }
        });
    }

    showModal = (item) => {
        let fileList;
        const path = item && item.path;
        let type = '';
        if (path) {
            const params = getParams(path);
            fileList = [{
                uid: -2,
                status: 'done',
                url: params.url,
                thumbUrl: params.url,
                name: params.name,
                size: params.size,
                response: {
                    data: path
                }
            }];
            type = 'modify';
        } else {
            fileList = [];
        }
        this.setState({
            visible: true,
            fileList: fileList,
            name: item && item.name,
            multiitemId: item && item.multiitemId || '',
            type: type,
            path: item.path,
            size: item.size,
            showCover : fileList.length > 0 ? true :false,
        });
    }

    handleOk = () => {
        const pass = this.checkBeforePost();
        if (!pass) {
            return;
        }
        this.setState({
            loading: true,
        });

        let { multifileId, multiitemId, name, size, path } = { ...this.state };
        let url;
        let params = { multifileId, multiitemId, name, size, path };

        if (this.state.type === 'modify') {
            url = 'book/multiitem/modify';
        } else {
            url = 'book/multiitem/add';
        }
        post(url, params)
            .then((jsonData) => {
                if (jsonData.meta && jsonData.meta.success) {
                    this.setState({
                        visible: false,
                        loading: false
                    });
                    message.info('提交成功！');
                    this.getData();
                } else {
                    message.info('提交失败！');
                    this.setState({
                        loading: false
                    });
                }
            })
    }

    checkBeforePost() {
        let info = '';
        if (!this.state.fileList || this.state.fileList.length < 1) {
            info = '请上传文件';
            message.info(info);
            return false;
        } else {
            return true;
        }
    }

    handleCancel = () => {
        this.setState({
            visible: false
        });
    }

    onChangeHandle = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        this.setState({
            [name]: value
        });
    }

    beforeUpload = (file) => {
        const multiType = this.props.multiType;
        let type, tip;
        switch (multiType) {
            case 'pic':
                type = 'image/';
                tip = '图片'
                break;
            case 'video':
                type = 'video/';
                tip = '视频'
                break;
            case 'music':
                type = 'audio/';
                tip = '音频'
                break;
            case 'txt':
                type = 'text/plain';
                tip = 'txt'
                break;
            case 'pdf':
                type = 'application/pdf';
                tip = 'pdf'
                break;
            default:

        }
        const isFormat = file.type.includes(type);
        if (!isFormat) {
            message.error(`只能上传${tip}格式文件!`);
        }
        const isLt2M = file.size / 1024 / 1024 < 1000;
        if (!isLt2M) {
            message.error('文件大小不能超过1000M!');
        }
        return isFormat && isLt2M;
    }

    handleFileChange = ({ file, fileList }) => {
        let params = {
            fileList: fileList,
            size: file.size,
            path: file.response && file.response.data,
            showCover : fileList.length > 0 ? true :false
        }
        if(!this.state.name) {
            params.name = file.name;
        }
        this.setState(params);
    }

    render() {
        const { list } = this.state;
        const length = list && list.length || 0;
        const props = {
            action: config.imageUrl,
            onChange: this.handleFileChange,
            multiple: false,
            beforeUpload: this.beforeUpload,
        };
        return (
            <div className="expertBox">
                <div>
                    <div className='oper'>
                        <Button size='large' type='primary' className="page-btn" icon='plus' onClick={this.showModal}>上传</Button>
                        <Button size='large' type='primary' className="page-btn" onClick={this.save}>保存</Button>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <td width='150'>序号</td>
                                <td width='200'>文件信息</td>
                                <td width='150'>大小</td>
                                <td>操作</td>
                            </tr>
                        </thead>
                        <tbody >
                            {
                                list.map((item, i) => (
                                    <tr key={i}>
                                        <td>{i + 1}</td>
                                        <td>{item.name}</td>
                                        <td>{item.size}kb</td>
                                        <td>
                                            <Button className="btn table-btn" type="submit" value="Submit" onClick={this.showModal.bind(this, item)}>修改</Button>
                                            {
                                                (i == 0) ? <Button className="btn table-btn" disabled onClick={this.up.bind(this, item, i)}>上移</Button>
                                                    : <Button className="btn table-btn" onClick={this.up.bind(this, item, i)}>上移</Button>
                                            }
                                            {
                                                (i == length - 1) ? <Button className="btn table-btn" disabled onClick={this.down.bind(this, item, i)}>下移</Button>
                                                    : <Button className="btn table-btn" onClick={this.down.bind(this, item, i)}>下移</Button>
                                            }
                                            <Popconfirm title="是否删除文件?" onConfirm={this.onDel.bind(this, item.multiitemId)} okText="是" cancelText="否">
                                                <Button className="btn table-btn del" type="reset" value="Reset">删除</Button>
                                            </Popconfirm>
                                        </td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                    <Modal title={[
                        <img key={10} src={require('assets/images/upload.png')} style={{ float: 'left', width: '20px' }} />,
                        <p key={11} style={{ fontSize: '16px', color: 'white', marginLeft: '30px' }}>文件上传</p>
                    ]}
                        visible={this.state.visible}
                        footer={[
                            <Button key={1} style={{ margin: '0 20px 30px 0' }} size='large' type='primary' loading={this.state.loading} onClick={this.handleOk}>确定</Button>,
                        ]}
                        wrapClassName='upload-box-modal'
                        onCancel={this.handleCancel}
                        closable={true}
                        maskClosable={true}>
                        <div>
                            <span style={{ fontSize: '14px', margin: '0 0 10px 0', display: 'inline-block', width: '100px', textAlign: 'right' }}>文件信息：</span>
                            <Input name='name' style={{ borderRadius: '0', display: 'inline-block', width: '200px' }} rows={4} value={this.state.name} onChange={this.onChangeHandle} />
                        </div>
                        <div>
                            <span style={{ fontSize: '14px', margin: '20px 0 10px 0', display: 'inline-block', width: '100px', textAlign: 'right' }}><span style={{ color: 'red' }}>*</span>资源上传：</span>
                            <Upload name='file' {...props} fileList={this.state.fileList}>
                                <Button disabled={this.state.showCover}>
                                    <Icon type="upload" />上传
                                </Button>
                            </Upload>
                        </div>

                    </Modal>
                </div>
            </div>
        );
    }
}

export default class ResourceModify extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            title: ''
        };
    }

    componentWillMount() {
        const id = this.props.id;
        // this.queryDetail(id);
    }

    componentWillReceiveProps(params) {
        //    this.queryDetail(params.id);
    }

    queryDetail(id) {
        let url;
        url = `book/multifile/query/${id}`;

        get(url)
            .then((jsonData) => {
                const data = jsonData.data;
                // this.setState({...data});
            });
    }

    onTabClick = (targetKey) => {
        if (targetKey === '1') {
        }
    }

    render() {
        const { path, title, fullPrice, multifileId, multiType } = this.props.data;
        return (
            <Wrapper>
                <div className='top clearfix'>
                    <img className='image' src={path} />
                    <div>{title}</div>
                    <div>单价：<span className='price'>{fullPrice||0}</span>元</div>
                </div>
                <Tabs type='card'
                    className='child-tab'
                >
                    <TabPane tab='基本信息' key='1' closable={false}>
                        <ResourceNew id={this.props.id} info={this.props.data} />
                    </TabPane>
                    <TabPane tab='文件' key='2' closable={false}>
                        <FileList id={multifileId} multiType={multiType} />
                    </TabPane>
                </Tabs>
            </Wrapper>
        )
    }
}