import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import './style.scss';
import { Button, Input, Form, message } from 'antd';
import { get, post } from 'utils/request.js';
import {
    SubEmiter,
    Emiter
} from 'utils';
const FormItem = Form.Item;

export default class EditPermission extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
        }
    }

    componentDidMount() {

    }

    render() {
        return (
            <div>
                <div className="form">
                    <EditList info={this.props.info} />
                </div>
            </div>
        );
    }
}

class EditForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.info != this.props.info) {
            this.setData(nextProps.info)
        }
    }

    setData = (info) => {
        get(`user/role/query/${info.roleId}`)
            .then((res) => {
                if (res && res.meta && res.meta.success) {
                    let { roleId, roleName, roleDesc } = res.data;
                    this.props.form.setFieldsValue({
                        roleName, roleDesc
                    })
                    this.setState({
                        roleName, roleDesc
                    })
                }
            });

    }

    handleSubmit = (e) => {
        e.preventDefault();
        const { roleId, roleName, roleDesc } = this.state;
        let params = { roleName, roleDesc };
        this.props.form.validateFields((err, values) => {
            if (!err) {
                let url = `user/role/add`;
                let msg = '新增成功';
                if (roleId) {
                    url = `user/role/modify`
                    params.roleId = roleId;
                    msg = '修改成功';
                }
                post(url, params)
                    .then((res) => {
                        if (res.meta.success) {
                            message.success(msg);
                            Emiter.emit('addListOk');
                        } else {
                            message.error('修改失败')
                        }
                    })
            }
        });
    }

    render() {
        const { getFieldDecorator } = this.props.form;
        const formItemLayout = {
            labelCol: { span: 1 },
            wrapperCol: { span: 12 }
        };
        return (
            <Form onSubmit={this.handleSubmit}>
                <FormItem
                    {...formItemLayout}
                    label="权限名称"
                >
                    {getFieldDecorator('roleName', {
                        rules: [
                            { required: true, message: '请填写权限名称' },
                        ]
                    })(
                        <Input />
                        )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="说明"
                >
                    {getFieldDecorator('roleDesc')(
                        <Input type='textarea' />
                    )}
                </FormItem>
                <FormItem
                >
                    <Button type="primary" htmlType="submit" style={{ marginLeft: '80px' }}>保存</Button>
                </FormItem>
            </Form>
        );
    }
}

const EditList = Form.create()(EditForm);