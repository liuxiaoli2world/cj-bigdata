import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import { Button, Input, Form, Radio, Modal, InputNumber, message } from 'antd';
import Editor from '../components/Editor.js';
import { get, post } from 'utils/request.js';
import base64url from 'base64url';
import {
    SubEmiter,
    Emiter
} from 'utils';
const FormItem = Form.Item;
const RadioGroup = Radio.Group;

export default class ListEdit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            info: {},
            visible: false,
            tryLength: 300
        }
    }

    editTry = (params) => {
        let { tryContent, tryLength, bookSectionId } = params;
        this.setState({
            visible: true,
            tryLength,
            tryContent,
            bookSectionId
        });
    }

    changeNum = (val) => {
        val += '';
        val = val.replace(/\D/g, '')
        this.setState({
            tryLength: val
        })
    }

    saveTry = () => {
        const { bookSectionId, tryLength } = this.state;
        post(`book/booksection/updateTry?bookSectionId=${bookSectionId}&tryLength=${tryLength}`)
            .then((res) => {
                if (res.meta.success) {
                    message.success('设置试读成功')
                    this.setState({
                        tryContent: res.data
                    })
                    Emiter.emit("setTryOk", { tryLength, tryContent: res.data })
                }
            })
    }

    handleCancel = () => {
        this.setState({
            visible: false,
            loading: false
        });
    }

    getData(id) {
        get(`book/bookdir/query/${id}`)
            .then((res) => {
                if (res.meta.success) {
                    this.setState({
                        info: res.data
                    })
                }
            })
    }

    render() {
        const buttonStyle = {
            height: '32px',
            width: '90px',
            fontSize: '14px',
            marginLeft: '30px',
            borderRadius: '2px'
        }
        const { visible, tryLength, tryContent } = this.state;
        return (
            <div>
                <SubEmiter eventName="openModal" listener={this.editTry} />
                <div className="form">
                    <EditList isTry={this.props.isTry}/>
                </div>

                {/*试读弹出框*/}

                <Modal title={[
                    <p key={11} style={{ fontSize: '16px', color: 'white', marginLeft: '30px' }}>试读内容</p>
                ]}
                    visible={visible}
                    maskClosable={true}
                    onCancel={this.handleCancel.bind(this)}
                    footer={null}
                    wrapClassName='trybox-modal'>
                    {/*<Spin spinning={this.state.loading} size="large">*/}
                    <div className="tryNum">
                        <span className="label">试读字数：</span>
                        <InputNumber className="numInput"
                            onChange={this.changeNum}
                            value={tryLength}
                        />
                        <span>(默认为章节内容前三百字)</span>
                        <Button type="primary" style={buttonStyle} onClick={this.saveTry}>保存</Button>
                    </div>
                    <div>
                        <span className="label">试读内容：</span>
                        <div className="try-content" dangerouslySetInnerHTML={{ __html: tryContent }}></div>
                    </div>
                    {/*</Spin>*/}
                </Modal>
            </div>
        );
    }
}

class EditForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLeaf: false,
            parentId: 0,
            isTry: 0
        }
    }

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                delete values.contentLength;
                delete values.title;
                post(`book/booksection/updateSelective`, values)
                    .then((res) => {
                        if (res.meta.success) {
                            message.success('编辑成功')
                        }
                    })
            }
        });
    }

    changeTry = (e) => {
        this.setState({
            isTry: e.target.value
        })
    }

    componentDidMount() {

    }

    setTry = () => {
        const { tryContent, tryLength, bookSectionId } = this.state;
        Emiter.emit('openModal', { tryContent, tryLength, bookSectionId });
    }

    setTryOk = (params) => {
        this.setState(params)
    }
    getList = (params) => {
        this.setState({
            isLeaf: params.isLeaf,
            parentId: params.parentId
        })
        let newContent;
        if (params.id.length > 0) {
            get(`book/booksection/modifyByBookDirId?BookDirId=${params.id[0] - 0}`)
                .then((res) => {
                    if (res && res.meta && res.meta.success) {
                        let { title, isbn, contentLength, isTry, content, tryContent, tryLength, bookSectionId, bookDirId } = res.data;
                        newContent = base64url.decode(content);
                        tryContent = tryContent ? base64url.decode(tryContent) : '';
                        this.props.form.setFieldsValue({
                            title, content, bookDirId, isbn, bookSectionId
                        });
                        if (params.parentId == 0) {
                            this.props.form.setFieldsValue({ isTry })
                            this.setState({
                                isTry,
                                tryContent,
                                tryLength,
                                bookSectionId
                            })

                        }
                    }
                }).then(() => {
                    Emiter.emit("editcontext", { "con": newContent })
                })
        }else{
            this.props.form.resetFields();
            Emiter.emit("editcontext", { "con": '' })
            this.setState({
                bookSectionId: ''
            })
        }

    }

    resetForm = () => {
        this.props.form.resetFields();
        Emiter.emit("editcontext", { "con": '' })
    }
    EditorChange = (html) => {
        const toHtml = this.refs.toHtml;
        toHtml.innerHTML = this.return2Br(this.escape2Html(html.replace(/<.*?>/ig, "")));
        const contentLength = toHtml.innerHTML.length;
        this.props.form.setFieldsValue({
            content: html,
            contentLength
        })
    }

    return2Br(str) {
        return str.replace(/\r?\n/g, "");
    }

    escape2Html(str) {
        var arrEntities = { 'lt': '<', 'gt': '>', 'nbsp': ' ', 'amp': '&', 'quot': '"', 'ldquo': '“', 'rdquo': '”' };
        return str.replace(/&(lt|gt|nbsp|amp|quot|ldquo|rdquo);/ig, function (all, t) { return arrEntities[t]; });
    }

    render() {
        const { parentId, isLeaf, isTry, bookSectionId } = this.state;
        const { getFieldDecorator } = this.props.form;
        const formItemLayout = {
            labelCol: { span: 1 },
            wrapperCol: { span: 12 }
        };
        return (
            <Form onSubmit={this.handleSubmit}>
                <SubEmiter eventName="editList" listener={this.getList} />
                <SubEmiter eventName="setTryOk" listener={this.setTryOk} />
                <SubEmiter eventName="resetForm" listener={this.resetForm} />
                <div style={{ display: 'none' }} ref="toHtml"></div>
                <FormItem className="hidden">
                    {getFieldDecorator('bookDirId')(
                        <Input type="hidden" />
                    )}
                </FormItem>
                <FormItem className="hidden">
                    {getFieldDecorator('bookSectionId')(
                        <Input type="hidden" />
                    )}
                </FormItem>
                <FormItem className="hidden">
                    {getFieldDecorator('isbn')(
                        <Input type="hidden" />
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="文章标题"
                >
                    {getFieldDecorator('title')(
                        <Input disabled />
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="文章字数"
                >
                    {getFieldDecorator('contentLength')(
                        <Input disabled />
                    )}
                </FormItem>
                {this.props.isTry == 1 && parentId == 0 ?
                    <FormItem
                        {...formItemLayout}
                        label="允许试读"
                    >
                        {getFieldDecorator('isTry')(
                            <RadioGroup onChange={this.changeTry}>
                                <Radio value={1}>是</Radio>
                                <Radio value={0}>否</Radio>
                            </RadioGroup>
                        )}
                        {isTry ?
                            <Button onClick={this.setTry}>设置试读内容</Button>
                            : null}
                    </FormItem>
                    : null
                }
                <FormItem label="文章内容"
                    {...formItemLayout}
                    wrapperCol={{ span: 10 }}
                    id="bookdetail">
                    {getFieldDecorator('content', {
                        rules: [{ required: true, message: '正文' }]
                    })(
                        <Editor EditorChange={this.EditorChange} disabled={isLeaf ? true : false} i={`b${Date.parse(new Date()) / 1000}`} className="editor" style={{ width: '300%' }} />
                        )}
                </FormItem>
                {this.props.form.getFieldValue('bookDirId') ?
                    <FormItem>
                        <Button type="primary" htmlType="submit" style={{ marginLeft: '80px' }}>保存</Button>
                    </FormItem>
                    : null}
            </Form>
        );
    }
}

const EditList = Form.create()(EditForm);