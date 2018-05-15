import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import './style.css';
import { Button, Input, Form, message } from 'antd';
import { get, post } from 'utils/request.js';
import {
    SubEmiter,
    Emiter
} from 'utils';
const FormItem = Form.Item;

export default class ListEdit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            info: { id: [] }
        }
    }

    componentDidMount() {
    }

    getList = (info) => {
        this.setState({
            info
        })
    }

    delete = () => {
        const { info } = this.state;
        get(`book/bookdir/remove/${info.id[0]}`)
            .then((res) => {
                if(res.meta.success){
                    message.success('删除成功');
                    Emiter.emit('addListOk');
                    Emiter.emit('resetForm1');
                }else{
                    message.error('删除失败');
                }
            })
    }

    render() {
        const buttonStyle = {
            height: '32px',
            width: '90px',
            fontSize: '14px',
            margin: '9px 0',
            borderRadius: '2px'
        }
        const { info } = this.state;
        return (
            <div>
                <SubEmiter eventName="addList" listener={this.getList} />
                <div className="listTitle clearfix">
                    <div className="left title">{info.id.length > 0 ? '编辑' : '新增'}</div>
                    {info.id.length > 0 ?
                        <Button className="add right" type="primary" style={buttonStyle} onClick={this.delete}>删除节点</Button>
                        : null
                    }
                </div>
                <div className="form">
                    <EditList isbn={this.props.isbn} info={info} />
                </div>
            </div>
        );
    }
}

class EditForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            parentBookDirId: 0
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.info != this.props.info) {
            this.setData(nextProps.info)
        }
    }

    resetForm = () => {
        this.props.form.resetFields();
    }

    setData = (info) => {
        if (info.id.length > 0) {
            get(`book/bookdir/query/${info.id[0] - 0}`)
                .then((res) => {
                    if (res && res.meta && res.meta.success) {
                        let { bak1, dirName, dirDesc, bookDirId, parentBookDirId } = res.data;
                        bak1 = (parentBookDirId == 0) ? '根目录' : bak1;
                        this.props.form.setFieldsValue({
                            bak1, dirName, dirDesc
                        })
                        this.setState({
                            parentBookDirId,
                            bookDirId,
                            parentName: dirName
                        })
                    }
                })
        } else {
            if (info.parentId) {
                this.props.form.resetFields()
                this.props.form.setFieldsValue({bak1:this.state.parentName})
                this.setState({
                    parentBookDirId: info.parentId[0] || 0,
                    bookDirId: 0,
                    // parentName:''
                })
            } else {
                this.props.form.resetFields();
                this.props.form.setFieldsValue({bak1:'根目录'})
                this.setState({
                    parentBookDirId: 0,
                    bookDirId: 0,
                    parentName:'根目录'
                })
            }
        }
    }

    handleSubmit = (e) => {
        const { isbn } = this.props;
        e.preventDefault();
        const { parentBookDirId, bookDirId } = this.state;
        this.props.form.validateFields((err, values) => {
            if (!err) {
                delete values.bak1;
                let url = `book/bookdir/add`;
                let msg = '新增成功';
                values.parentBookDirId = parentBookDirId - 0;
                if (bookDirId) {
                    url = `book/bookdir/modify`;
                    msg = '修改成功';
                    values.bookDirId = bookDirId - 0;
                }
                values.isbn = isbn;
                post(url, values)
                    .then((res) => {
                        if (res.meta.success) {
                            message.success(msg);
                            this.props.form.resetFields();
                            Emiter.emit('addListOk');
                        }else{
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
                <SubEmiter eventName="resetForm1" listener={this.resetForm} />
                <FormItem
                    {...formItemLayout}
                    label="父级目录"
                >
                    {getFieldDecorator('bak1')(
                        <Input disabled />
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="目录名称"
                >
                    {getFieldDecorator('dirName', {
                        rules: [
                            { required: true, message: '请填写目录名称' },
                        ]
                    })(
                        <Input />
                        )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="简介"
                >
                    {getFieldDecorator('dirDesc')(
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