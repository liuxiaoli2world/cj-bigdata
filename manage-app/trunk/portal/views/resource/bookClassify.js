import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './vipGoodStyle.js';
import './style.scss';
import { Select, Button, Input, Table, Popconfirm, message } from 'antd';
import { get, post } from 'utils/request.js';
import {
    SubEmiter,
    Emiter
} from 'utils';

message.config({
  top: 300,
  duration: 2
});

export default class BookClassify extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            list: [],
            loading: false
        }
        this.columns = [
            {
                key: 'channelId',
                title: '编号',
                dataIndex: 'channelId',
                width: '25%'
            },
            {
                key: 'channelName',
                title: '分类名称',
                dataIndex: 'channelName',
                width: '25%'
            },
            {
                key: 'bookNum',
                title: '数据数量',
                dataIndex: 'bookNum',
                width: '25%'
            },
            {
                key: 'action',
                title: '操作',
                dataIndex: 'action',
                render: (text, record, index) => {
                    return (
                        <div>
                            <Button className='table-btn' onClick={this.add.bind(this, record.channelId)}>修改</Button>
                            <Popconfirm
                                visible={record.visible}
                                onVisibleChange={this.handleVisibleChange.bind(this, index, record)}
                                title="是否确认删除?"
                                onConfirm={() => this.delete(record.channelId, record.bookNum)}
                                onCancel={this.onCancel.bind(this, index)}>
                                <Button style={{ margin: '0 5%' }} className='table-btn'>删除</Button>
                            </Popconfirm>                            
                        </div>
                    )
                }
            },
        ]
    }

    componentDidMount() {
        this.getPageData();
    }

    getPageData = () => {
        this.setState({ loading: true });
        let url = 'book/channel/queryAll';
        get(url).then((jsonData) => {
            const data = jsonData.data;
            this.setState({
                loading: false,
                list: data
            });
        });
    }

    handleVisibleChange = (index, record) => {
        if (record.bookNum > 0) {
            this.state.list[index].visible = false;
            this.setState({
                list: this.state.list
            });
            message.warn('已有图书选定该分类，无法删除该分类。');
        } else {
            this.state.list[index].visible = true;
            this.setState({
                list: this.state.list
            });
        }
    }

    onCancel = (index) => {
        this.state.list[index].visible = false;
        this.setState({
            list: this.state.list
        });
    }

    delete = (id, num) => {
        get(`/book/channel/remove/${id}`)
            .then((jsonData) => {
                if (jsonData.meta && jsonData.meta.success) {
                    return true;
                } else {
                    return false;
                }
            })
            .then((success) => {
                Emiter.emit('refreshBookClassifyList', true);
            });
    }

    add = (id) => {
        let params = {
            operName: 'bookclassify-new',
            id
        };
        Emiter.emit('openPanel', params);
    }

    render() {
        let { listdata } = this.state;
        return (
            <Wrapper>
                <div className='head'>
                    <Button size='large' type='primary' id='addBtn' icon='plus' className='add-btn' onClick={this.add.bind(this, null)}>新增</Button>
                </div>
                <SubEmiter eventName="refreshBookClassifyList" listener={this.getPageData}></SubEmiter>
                <Table className='table'
                    rowKey={record => record.channelId}
                    pagination={false}
                    columns={this.columns}
                    dataSource={this.state.list}
                    loading={this.state.loading}                    
                    bordered={true} />
            </Wrapper>
        );
    }
}