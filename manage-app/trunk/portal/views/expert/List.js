import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import { Select, Button, Input, Icon, Pagination, Popconfirm, message } from 'antd';
import { SubEmiter, Emiter } from '../utils/index';
import { get, post } from '../utils/request'
const Option = Select.Option;

export default class InfoList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            list: [],
            currentPage: 1,
            total: 0,
            pageSize: 10,
            classList: [],
            expertClassify: '',
            keyword: ''
        }
    }

    componentDidMount() {
        this.getData();
    }

    getData = (page = 1) => {
        const { expertClassify, keyword, pageSize } = this.state;
        post(`expert/expert/selectByClassifyAndRealName?pageNum=${page}&pageSize=${pageSize}${expertClassify ? `&expertClassify=${expertClassify}` : ''}${keyword ? `&realName=${keyword}` : ''}`)
            .then((res) => {
                if (res.meta.success) {
                    this.setState({
                        list: res.data.list,
                        currentPage: res.data.pageNum,
                        total: res.data.total,
                        pageSize: res.data.pageSize
                    })
                }
            })
    }

    handleChange = (value) => {
        this.setState({
            expertClassify: value == 0 ? '' : value
        })
    }

    onPageChange = (page) => {
        this.getData(page)
    }

    keyword = (e) => {
        this.setState({
            keyword: e.target.value
        })
    }

    add = () => {
        let params = {
            operName: 'expert-new',
            id: null
        };
        Emiter.emit('openPanel', params);
    }

    render() {
        const { domain } = this.props;
        return (
            <div className="expertBox">
                <SubEmiter eventName="addExpertOk" listener= {this.getData.bind(this,1)} />
                <SubEmiter eventName="delExpert" listener= {this.getData.bind(this,1)} />

                <div className="header clearfix">
                    <span className="label-name">分类：</span>
                    <Select className='select order-type' size='large' defaultValue="全部" style={{ width: 120 }} onChange={this.handleChange.bind(this)}>
                        <Option value={'0'}>{"全部"}</Option>
                        {

                            domain.map((item, i) => (
                                <Option key={item.domainId} value={item.name}>{item.name}</Option>
                            ))
                        }
                    </Select>
                    <span className="label-name">名称：</span>
                    <Input onChange={this.keyword} size='large' style={{ width: 120 }}></Input>
                    <Button className="search" size='large' type="primary" onClick={this.getData.bind(this,1)}>查询</Button>
                    <Button className="addbtn right" size='large' type="primary" onClick={this.add}><Icon type="plus" />新增</Button>
                </div>
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
        this.state = {
            isDel: true
        }
    }

    composition = () => {
        let params = {
            operName: 'composition',
            info: this.props
        };
        Emiter.emit('openPanel', params);
        // Emiter.emit("composition",{"info":this.props})
    }

    add = () => {
        let params = {
            operName: 'expert-modify',
            info: this.props
        };
        Emiter.emit('openPanel', params);
        // Emiter.emit("addExpert",{"addExpert":false,"info":this.props})
    }

    del = () => {
        get(`expert/expert/deleteExpert/${this.props.expertId}`)
            .then((data) => {
                if (data.meta.success) {
                    message.success('删除成功')
                    Emiter.emit("delExpert")
                }
            })
    }

    render() {
        const { isDel } = this.state;
        return (
            <span>
                {isDel ?
                    <span className='box'>
                        {this.props.isRecommend==1 ? 
                            <img className="suggestImg" src={require('../assets/images/suggest.png')}/> : null
                        }
                        <div className='box-head clearfix'>
                            <div className="left head-img">
                                <img src={this.props.imageUrl} alt="" />
                            </div>
                            <div className="left info">
                                <div className='content-head'>{this.props.realName}</div>
                                <div className='content-desc'>专家简介:{this.props.expertDesc && this.props.expertDesc.length < 55 ? this.props.expertDesc : this.props.expertDesc && this.props.expertDesc.substr(0, 55) + '...'}</div>
                            </div>
                        </div>
                        <div className='foot'>
                            <Button onClick={this.composition} type="default">学术著作</Button>
                            <Button onClick={this.add} type="default">修改</Button>
                            <Popconfirm title="是否删除该专家?" onConfirm={this.del} okText="是" cancelText="否">
                                <Button className="menu" type="default">删除</Button>
                            </Popconfirm>
                        </div>
                    </span> : null}
            </span>)
    }
}