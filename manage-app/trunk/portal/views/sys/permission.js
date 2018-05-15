import React from 'react';
import { Link, hashHistory } from 'react-router';
import { Select, Button, Input, Table, Popconfirm, Modal, Checkbox, message } from 'antd';
const CheckboxGroup = Checkbox.Group;
import { get, post } from 'utils/request.js';
import '../global.css';
import {
    SubEmiter,
    Emiter
} from 'utils';

export default class Permission extends React.Component {
    constructor(props) {
        super(props);
        this.options0 = [
            { label: '首页', value: 100 }
        ];
        this.options1 = [
            { label: '内容管理', value: 101 }
        ];
        this.options2 = [
            { label: '资源管理', value: 102, parentId: 0 },
            { label: '图书管理', value: 118, parentId: 102 },
            { label: '图书分类', value: 119 },
            { label: '资源管理', value: 120 },
            { label: '会员商品', value: 121 }
        ];
        this.options3 = [
            { label: '专家管理', value: 103 },
            { label: '专家管理', value: 111 },
            { label: '专家分类', value: 112 }
        ];
        this.options4 = [
            { label: '用户管理', value: 104 },
            { label: '用户管理', value: 107 },
            { label: '机构管理', value: 108 },
            { label: '异常设置', value: 109 },
            { label: '意见箱', value: 110 }
        ];
        this.options5 = [
            { label: '数据分析', value: 105 },
            { label: '订单管理', value: 113 }
        ];
        this.options6 = [
            { label: '系统设置', value: 106 },
            { label: '文献分类', value: 115 },
            { label: '权限分组', value: 116 },
            { label: '热词设置', value: 117 }
        ];
        this.state = {
            currentPage: 1,
            list: [],
            loading: false,
            pagination: {
                total: 0,
                pageSize: 10,
                showQuickJumper: true
            },
            visible: false,
            value0: [],
            value1: [],
            value2: [],
            value3: [],
            value4: [],
            value5: [],
            value6: []
        };
        this.columns = [
            {
                key: 'showName',
                title: '权限名称',
                dataIndex: 'showName',
                width: '25%'
            },
            {
                key: 'countUser',
                title: '权限用户数量',
                dataIndex: 'countUser',
                width: '25%'
            },
            {
                key: 'roleDesc',
                title: '说明',
                dataIndex: 'roleDesc',
                width: '25%'
            },
            {
                key: 'action',
                title: '操作',
                dataIndex: 'action',
                render: (text, record, index) => {
                    return (
                        <div>
                            <Button type='default' className='table-btn' onClick={this.setPermissions.bind(this, record)}>权限设置</Button>
                            {/* <Button className='table-btn' onClick={this.add.bind(this, record)}>修改</Button>
                            <Popconfirm title="是否确认删除?" onConfirm={() => this.delete(record.vipId)}>
                                <Button style={{ margin: '0 5%' }} className='table-btn'>删除</Button>
                            </Popconfirm> */}
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
        let url = `user/role/queryAll?pageNum=${params.page}&pageSize=${this.state.pagination.pageSize}`;
        get(url).then((jsonData) => {
            const data = jsonData.data;
            const pagination = { ...this.state.pagination };
            pagination.total = data.total;
            data.list.shift();
            this.setState({
                loading: false,
                list: data.list,
                pagination
            });
        });
    }

    setPermissions = (item) => {
        this.showModel(item);
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
                Emiter.emit('refreshPermissionsList', true);
            });
    }

    add = (obj) => {
        let params = {
            operName: 'permission-new'
        };
        if (obj) {
            params.info = obj;
            params.operName = 'permission-modify'
        }
        Emiter.emit('openPanel', params);
    }

    showModel = (item) => {
        const id = item.roleId;
        get(`user/allow/findByRoleId/${id}`)
            .then(jsonData => {
                if (jsonData.meta && jsonData.meta.success) {
                    const values = this.processData(jsonData.data);
                    this.setState({
                        visible: true,
                        currentId: id,
                        showName: item.showName,
                        value0: values[100] || [],
                        value1: values[101] || [],
                        value2: values[102] || [],
                        value3: values[103] || [],
                        value4: values[104] || [],
                        value5: values[105] || [],
                        value6: values[106] || []
                    });
                }
            });
    }

    processData = (list) => {
        const firstLevel = {};
        for (let i = 0, len = list.length; i < len; i++) {
            const item = list[i];
            if (item.parentAllowId == 0) {
                firstLevel[item.allowId] = [item.allowId];
            }
        }

        for (let i = 0, len = list.length; i < len; i++) {
            const item = list[i];
            if (item.parentAllowId != 0) {
                firstLevel[item.parentAllowId].push(item.allowId);
            }
        }
        return firstLevel;
    }

    onChange = (index, checkedValues) => {
        switch (index) {
             case 0:
                if (!checkedValues.includes(100) && checkedValues.length > 0) {
                    this.setState({
                        value0: [100, ...checkedValues]
                    });
                } else {
                    this.setState({
                        value0: [...checkedValues]
                    });
                }
                break;
            case 1:
                if (!checkedValues.includes(101) && checkedValues.length > 0) {
                    this.setState({
                        value1: [101, ...checkedValues]
                    });
                } else {
                    this.setState({
                        value1: [...checkedValues]
                    });
                }
                break;

            case 2:
                if (!checkedValues.includes(102) && checkedValues.length > 0) {
                    this.setState({
                        value2: [102, ...checkedValues]
                    });
                } else {
                    this.setState({
                        value2: [...checkedValues]
                    });
                }
                break;
            case 3:
                if (!checkedValues.includes(103) && checkedValues.length > 0) {
                    this.setState({
                        value3: [103, ...checkedValues]
                    });
                } else {
                    this.setState({
                        value3: [...checkedValues]
                    });
                }
                break;
            case 4:
                if (!checkedValues.includes(104) && checkedValues.length > 0) {
                    this.setState({
                        value4: [104, ...checkedValues]
                    });
                } else {
                    this.setState({
                        value4: [...checkedValues]
                    });
                }
                break;
            case 5:
                if (!checkedValues.includes(105) && checkedValues.length > 0) {
                    this.setState({
                        value5: [105, ...checkedValues]
                    });
                } else {
                    this.setState({
                        value5: [...checkedValues]
                    });
                }
                break;
            case 6:
                if (!checkedValues.includes(106) && checkedValues.length > 0) {
                    this.setState({
                        value6: [106, ...checkedValues]
                    });
                } else {
                    this.setState({
                        value6: [...checkedValues]
                    });
                }
                break;
        }
    }

    handleOk = () => {
        debugger;
        const values = [...this.state.value0, ...this.state.value1, ...this.state.value2, ...this.state.value3, ...this.state.value4, ...this.state.value5, ...this.state.value6];
        get(`user/allow/updateAllow?id=${this.state.currentId}&allows=${values.toString()}`)
            .then(jsonData => {
                if (jsonData.meta && jsonData.meta.success) {
                    this.setState({
                        visible: false
                    });
                } else {
                    message.info(jsonData.meta.message);
                }
            });
    }

    handleCancel = () => {
        this.setState({
            visible: false
        });
    }
    render() {
        return (
            <div style={{margin: '0 20px 0 0'}}>
                {/* <div className='head'>
                    <Button icon='plus' onClick={this.add.bind(this, null)} className='btn'>新增</Button>
                </div> */}
                <SubEmiter eventName="refreshPermissionsList" listener={this.getPageData.bind(this, { page: 1 })}></SubEmiter>
                <Table className='table'
                    rowKey={record => record.roleId}
                    columns={this.columns}
                    dataSource={this.state.list}
                    pagination={this.state.pagination}
                    loading={this.state.loading}
                    onChange={this.handleTableChange}
                    bordered={true} />
                <Modal title={[
                    <img key={10} src={require('assets/images/permission.png')} style={{ float: 'left', width: '30px' }} />,
                    <p key={11} style={{ fontSize: '16px', color: 'white', marginLeft: '30px' }}>配置权限</p>
                ]}
                    visible={this.state.visible}
                    footer={[
                        <Button key={1} style={{ border: 0, borderRadius: '2px', background: '#3f93cd', margin: '0 20px 30px 0', color: 'white' }} size='large' loading={this.state.loading} onClick={this.handleOk}>保存</Button>,
                        <Button key={2} style={{ border: 0, borderRadius: '2px', background: '#3f93cd', margin: '0 20px 30px 0', color: 'white' }} size='large' onClick={this.handleCancel}>取消</Button>,
                    ]}
                    wrapClassName='permission-box-modal'
                    onCancel={this.handleCancel}
                    closable={true}
                    maskClosable={true}>
                    <div style={{marginBottom:'20px'}}>权限用户：<Input style={{display:'inline-block',width:'140px'}} value={this.state.showName} disabled/></div>
                    <div className='table'>
                        <CheckboxGroup options={this.options0} value={this.state.value0} onChange={this.onChange.bind(this, 0)} />
                        <CheckboxGroup options={this.options1} value={this.state.value1} onChange={this.onChange.bind(this, 1)} />
                        <CheckboxGroup options={this.options2} value={this.state.value2} onChange={this.onChange.bind(this, 2)} />
                        <CheckboxGroup options={this.options3} value={this.state.value3} onChange={this.onChange.bind(this, 3)} />
                        <CheckboxGroup options={this.options4} value={this.state.value4} onChange={this.onChange.bind(this, 4)} />
                        <CheckboxGroup options={this.options5} value={this.state.value5} onChange={this.onChange.bind(this, 5)} />
                        <CheckboxGroup options={this.options6} value={this.state.value6} onChange={this.onChange.bind(this, 6)} />
                    </div>
                </Modal>
            </div>
        );
    }
}