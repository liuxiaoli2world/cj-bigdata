import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './vipGoodStyle.js';
import './style.scss';
import { Select, Button, Input, Table, Popconfirm } from 'antd';
import { get, post } from 'utils/request.js';
import {
    SubEmiter,
    Emiter
} from 'utils';

export default class VipGood extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentPage: 1,
            list: [],
            loading: false,
            pagination: {
                total: 0,
                pageSize: 10,
                showQuickJumper: true
            }
        }
        this.columns = [
            {
                key: 'name',
                title: '商品名称',
                dataIndex: 'name',
                width: '25%'
            },
            {
                key: 'duration',
                title: '会员时长',
                dataIndex: 'duration',
                width: '25%'
            },
            {
                key: 'price',
                title: '单价',
                dataIndex: 'price',
                width: '25%'
            },
            {
                key: 'action',
                title: '操作',
                dataIndex: 'action',
                render: (text, record, index) => {
                    return (
                        <div>
                            <Button className='table-btn' onClick={this.add.bind(this, record)}>修改</Button>
                            <Popconfirm title="是否确认删除?" onConfirm={() => this.delete(record.vipId)}>
                                <Button style={{ margin: '0 5%' }} className='table-btn'>删除</Button>
                            </Popconfirm>
                        </div>
                    )
                }
            },
        ]
    }

    componentWillMount() {
        this.getPageData();
    }

    handleTableChange = (pagination, filters, sorter) => {
        const pager = { ...this.state.pagination };
        pager.current = pagination.current;
        this.setState({
            pagination: pager,
        });
        this.getPageData({
            results: pagination.pageSize,
            page: pagination.current,
            sortField: sorter.field,
            sortOrder: sorter.order,
            ...filters,
        });
    }

    getPageData = (params = { page: 1 }) => {
        this.setState({ loading: true });
        let url = `user/vip/queryAll?pageNum=${params.page}&pageSize=${this.state.pagination.pageSize}`;
        get(url).then((jsonData) => {
            const data = jsonData.data;
            const pagination = { ...this.state.pagination };
            pagination.total = data.total;
            this.setState({
                loading: false,
                list: data.list,
                pagination
            });
        });
    }

    delete = (id) => {
        get(`user/vip/remove/${id}`)
            .then((jsonData) => {
                if (jsonData.meta && jsonData.meta.success) {
                    return true;
                } else {
                    return false;
                }
            })
            .then((success) => {
                Emiter.emit('refreshVipGoodsList', true);
            });
    }

    add = (obj) => {
        let params = {
            operName: 'vipgood-new'
        };
        if (obj) {
            let { vipId: id, ...others } = obj;
            params.id = id;
            params.info = others;
        }
        Emiter.emit('openPanel', params);
    }

    render() {
        let { listdata } = this.state;
        return (
            <Wrapper>
                <div className='head'>
                    <Button size='large' type='primary' icon='plus' onClick={this.add.bind(this, null)} className='add-btn'>新增</Button>
                </div>
                <SubEmiter eventName="refreshVipGoodsList" listener={this.getPageData.bind(this, {page: 1})}></SubEmiter>
                <Table className='table'
                    rowKey={record => record.vipId}
                    columns={this.columns}
                    dataSource={this.state.list}
                    pagination={this.state.pagination}
                    loading={this.state.loading}
                    onChange={this.handleTableChange}
                    bordered={true} />
            </Wrapper>
        );
    }
}