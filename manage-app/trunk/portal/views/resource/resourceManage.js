import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './resouceManageStyle.js';
import './style.css';
import { Select, Button, Input, Pagination, Popconfirm } from 'antd';
import { get, post } from 'utils/request.js';
import {
    SubEmiter,
    Emiter
} from 'utils';

class List extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentPage: 1,
            pageSize: 10,
            list: [],
            total: 0,
            firstDirValue: '',
            secondDirValue: '',
            keyword: '',
            type: 'pic',
            firstDir: [{ menuId: '', menuName: '全部' }],
            secondDir: [{ menuId: '', menuName: '全部' }]
        };
    }

    componentWillMount() {
        this.queryRootMenu();
        this.getPageData();
    }

    componentDidMount() {
        this.setState({
            firstDirValue: '',
            secondDirValue: ''
        });
    }

    getPageData(pageNum = 1) {
        let url;
        url = `book/multifile/queryBy?pageNum=${pageNum}&pageSize=${this.state.pageSize}&parentMenuId=${this.state.firstDirValue}&childMenuId=${this.state.secondDirValue}&keyword=${this.state.keyword}&multiType=${this.state.type}`;

        get(url)
            .then((jsonData) => {
                const data = jsonData.data;
                this.setState({
                    list: data.list || [],
                    currentPage: data.pageNum,
                    total: data.total
                });
            });
    }

    onPageChange = (num) => {
        this.getPageData(num);
    }

    queryRootMenu = () => {
        get('book/menu/queryRoot')
            .then((jsonData) => {
                this.setState({
                    firstDir: [{ menuId: '', menuName: '全部' }, ...(jsonData.data || [])],
                });
            }).then(() => {
            });
    }

    querySecondDir = (parentMenuId) => {
        if (!parentMenuId) {
            this.setState({
                secondDir: [{ menuId: '', menuName: '全部' }],
                secondDirValue: ''
            });
            return;
        }

        this.setState({
            secondDirValue: ''
        });

        get(`book/menu/queryChild/${parentMenuId}`)
            .then((jsonData) => {
                const data = jsonData.data;
                this.setState({
                    secondDir: [{ menuId: '', menuName: '全部' }, ...(jsonData.data || [])],
                    secondDirValue: ''
                });
            });
    }

    onChange = (event) => {
    }

    handleInputChange = (e) => {
        this.setState({
            keyword: e.target.value
        });
    }
    handleTypeChange = (value, option) => {
        this.setState({
            type: value
        });
    }
    firstDirChange = (value, option) => {
        this.querySecondDir(value);
        this.setState({
            firstDirValue: value
        });
    }
    secondDirChange = (value, option) => {
        this.setState({
            secondDirValue: value
        });
    }

    query = (isCorrentPage) => {
        const type = this.state.type;
        const keyword = this.state.keyword;
        const page = isCorrentPage ? this.state.currentPage : 1;
        this.getPageData(page);
    }

    add = () => {
        let params = {
            operName: 'resource-new',
            id: null
        };
        Emiter.emit('openPanel', params);
    }

    render() {
        return (
            <div className='tab-content'>
                <div className='head'>
                    <span className='label-name'>分类：</span>
                    <Select size='large' className='select order-type' value={this.state.type} style={{ width: 120 }} onSelect={this.handleTypeChange}>
                        <Option value='pic'>图片</Option>
                        <Option value='music'>音频</Option>
                        <Option value='video'>视频</Option>
                        <Option value='pdf'>pdf文件</Option>
                        <Option value='txt'>txt文件</Option>
                    </Select>
                    <span className='label-name'>分类标签：</span>
                    <Select size='large' className='select' value={this.state.firstDirValue} style={{ width: 120 }} onSelect={this.firstDirChange}>
                        {
                            this.state.firstDir.map((item, index) => (
                                <Option key={index} value={item.menuId}>{item.menuName}</Option>
                            ))
                        }
                    </Select>
                    <Select size='large' className='select second-dir-type' value={this.state.secondDirValue} style={{ width: 180 }} onSelect={this.secondDirChange}>
                        {
                            this.state.secondDir.map((item, index) => (
                                <Option key={index} value={item.menuId}>{item.menuName}</Option>
                            ))
                        }
                    </Select>
                    <span className='label-name'>关键词：</span>
                    <Input size='large' className='input' value={this.state.keyword} onChange={this.handleInputChange} />
                    <Button size='large' type='primary' className='label-name' onClick={this.query.bind(this, false)}>查询</Button>
                    <Button size='large' type='primary' className='right newBtn' icon="plus" onClick={this.add} >新增</Button>
                </div>
                <SubEmiter eventName="refreshResourceList" listener={this.query}></SubEmiter>
                {
                    this.state.list.length > 0 ?
                        <div>
                            <div className='table'>
                                {
                                    this.state.list.map((item, index) => (
                                        <Content key={index} {...item} />
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

    modify = (id) => {
        let url;
        url = `book/multifile/query/${id}`;
        get(url)
            .then((jsonData) => {
                const data = jsonData.data;
                let params = {
                    operName: 'resource-modify',
                    id: id,
                    info: data
                };
                Emiter.emit('openPanel', params);
            });
    }

    delete = (id) => {
        get(`book/multifile/remove/${id}`)
            .then((jsonData) => {
                if (jsonData.meta && jsonData.meta.success) {
                    return true;
                } else {
                    return false;
                }
            })
            .then((success) => {
                Emiter.emit('refreshResourceList', true);
            });
    }

    render() {
        let typeName = '';
        switch (this.props.multiType) {
            case 'pic':
                typeName = '图片'
                break;
            case 'music':
                typeName = '音频'
                break;
            case 'video':
                typeName = '视频'
                break;
            case 'pdf':
                typeName = 'pdf文件'
                break;
            case 'txt':
                typeName = 'txt文件'
                break;
            default:
                typeName = '图片'
                break;
        }

        const showTag = this.props.pMenuName + '，' + this.props.cMenuName;

        return (
            <span className='box'>
                <div className='box-head'>
                    <img className="head-img" src={this.props.path ? this.props.path : require('assets/images/no-img.png')} alt="" />
                    <div className='content-head'>{this.props.title}</div>
                    <div className='content-type'>资源类型：{typeName}</div>
                    <div className='content-desc'>分类标签：<span className='classify-tag'>{showTag && showTag.length < 17 ? showTag : showTag && showTag.substr(0, 17) + '...'}</span></div>>
                </div>
                <div className='foot'>
                    <Button type='default' onClick={this.modify.bind(this, this.props.multifileId)}>修改</Button>
                    <Popconfirm title="是否确认删除?" onConfirm={() => this.delete(this.props.multifileId)}>
                        <Button type='default'>删除</Button>
                    </Popconfirm>
                </div>
            </span>
        )
    }
}

export default class ResourceManage extends React.Component {

    render() {
        return (
            <Wrapper>
                <List />
            </Wrapper>
        );
    }
}

