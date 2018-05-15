import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import './style.css';
import config from '../../config/index.js';
import {
    Form, Input, Select, Radio, InputNumber, message,
    Button, Upload, Icon, Row, Col, DatePicker
} from 'antd';
import moment from 'moment';
import { get, post } from 'utils/request.js';
import { SubEmiter, Emiter } from 'utils';

const FormItem = Form.Item;
const Option = Select.Option;
const RadioButton = Radio.Button;
const RadioGroup = Radio.Group;

class BookForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            confirmDirty: false,
            classList: [],
            firstDir: [],
            secondDir: [],
            content: '',
            loading: false,
            showCover: false,
            isCprice: true
        }
    }
    componentDidMount() {
        this.queryRootMenu();
        this.getClass();
        if (this.props.info) {
            let { isRecommend, translator, realIsbn, menuId, fullPrice, chapterPrice, 
                isTry, press, keyword, channelId, releaseDate, bookImage, imageUrl,
                bookName, parentMenuId, author, bookDesc } = this.props.info;
            channelId += '';
            if(menuId) menuId += '';
            if(parentMenuId) parentMenuId += '';
            if(bookDesc) bookDesc = bookDesc.replace(/<\/?[^>]*>/g,'')
            this.querySecondDir(parentMenuId, menuId);
            const imgList = [{ url: bookImage||imageUrl, uid: 1 }];
            this.props.form.setFieldsValue({
                isRecommend, translator, realIsbn, menuId, fullPrice, chapterPrice,
                isTry, press, keyword, channelId,
                imgList, bookName, parentMenuId, author, bookDesc
            })
            releaseDate && this.props.form.setFieldsValue({
                releaseDate: moment(releaseDate, 'YYYY-MM-DD')
            })
            this.setState({
                showCover: imgList.length > 0 ? true : false,
                // isCprice: fullPrice ? true : false
            })
        }
    }

    getClass = () => {
        get(`book/channel/queryAll`)
            .then((res) => {
                if (res && res.meta && res.meta.success) {
                    this.setState({
                        classList: res.data
                    })
                }
            })
    }

    queryRootMenu = () => {
        get('book/menu/queryRoot')
            .then((jsonData) => {
                this.setState({
                    firstDir: jsonData.data || []
                });
            })
    }

    querySecondDir = (parentMenuId, menuId) => {
        parentMenuId &&
        get(`book/menu/queryChild/${parentMenuId}`)
            .then((jsonData) => {
                const data = jsonData.data;
                this.setState({
                    secondDir: data || []
                });
                // 默认选择“无”
                this.props.form.setFieldsValue({
                    menuId: menuId || data[0].menuId + ''
                });
            });
    }

    onSelect = (value, option) => {
        this.querySecondDir(value);
    }

    normFile = (e) => {
        let showCover;
        if (e.fileList.length > 0) {
            showCover = true;
        } else {
            showCover = false;
        }
        this.setState({
            showCover: showCover
        })
        if (Array.isArray(e)) {
            return e;
        }
        return e && e.fileList;
    }

    inputOnChange = (e) => {
        if (e) {
            this.setState({
                isCprice: true
            })
        } else {
            this.setState({
                isCprice: false
            })
        }
    }

    checkisbn = (rule, value, callback) => {
        if (value && !this.props.info) {
            post(`book/book/isExist/${value}/`)
                .then((jsonData) => {
                    if (jsonData.meta.success && jsonData.data) {
                        callback('书号已经存在！');
                    } else {
                        callback()
                    }
                })
        } else {
            callback()
        }
    }

    changeNum = (val) => {
        val += '';
        val = val.replace(/\D/g, '')
        return val;
    }

    cancel() {
        Emiter.emit("closePanel", '5')
    }

    save = (e) => {
        e.preventDefault();
        const form = this.props.form;
        let url = `book/book/add`;
        this.props.form.validateFields((err, values) => {
            if (!err) {
                this.setState({
                    loading: true,
                });
                values.releaseDate = values.releaseDate ? values.releaseDate.format("YYYY-MM-DD hh:mm:ss") : '';
                const imageUrl = (values.imgList && values.imgList.length > 0 && values.imgList[0].response) ?
                    values.imgList[0].response.data :
                    (values.imgList && values.imgList.length > 0 && values.imgList[0].url) ?
                        values.imgList[0].url :
                        null;
                values.imageUrl = imageUrl;
                values.isRelease = 0;
                values.isbn = values.realIsbn;
                if (this.props.info) {
                    values.bookId = this.props.info.bookId;
                    url = `book/book/modify`;
                    this.submitValues(url, values)
                }else{
                    post(`book/book/isExist/${values.realIsbn}/${values.bookName}`)
                        .then((jsonData) => {
                            if (jsonData.meta.success && jsonData.data) {
                                return Promise.reject({isExit: true});
                            }
                        })
                        .then(()=>{
                            this.submitValues(url, values)
                        })
                        .catch((e) => {
                            if(e.isExit){
                                message.error('图书已经存在！');
                                this.setState({
                                    loading: false
                                })
                            }
                        })
                }
            }
        });
    }

    submitValues = (url, values) => {
        post(url, values)
            .then((res) => {
                this.setState({
                    loading: false
            })
            if (res && res.meta && res.meta.success) {
                message.success("编辑图书成功");
                if(!this.props.info) {
                    this.cancel();
                }else{
                    Emiter.emit("bookTry",{values})
                }
                Emiter.emit("bookRefresh")
            } else {
                if (res && res.meta && res.meta.message) {
                    message.error(res.meta.message);
                }
            }
        })
    }

    render() {
        const { getFieldDecorator } = this.props.form;
        const { classList, content } = this.state;
        const formItemLayout = {
            labelCol: { span: 2 },
            wrapperCol: { span: 6 }
        };
        const priceLayout = {
            labelCol: { span: 1 },
            wrapperCol: { span: 14 }
        }
        return (
            <Form onSubmit={this.save} className="bookForm">
                <FormItem
                    {...formItemLayout}
                    label="图书分类"
                >
                    {getFieldDecorator('channelId', {
                        rules: [
                            { required: true, message: '请选择一个栏目!' },
                        ],
                    })(
                        <Select onSelect={this.onTypeSelect} placeholder="请选择">
                            {classList.map((item, index) => (
                                <Option key={index} value={item.channelId + ''}>{item.channelName}</Option>
                            ))}
                        </Select>
                        )}
                </FormItem>

                <Row gutter={5}>
                    <Col span={4}>
                        <FormItem
                            {...formItemLayout}
                            wrapperCol={{ span: 14 }}
                            label="分类标签"
                        >
                            {getFieldDecorator('parentMenuId', {
                                rules: [
                                    { required: true, message: '请选择一个分类!' },
                                ],
                            })(
                                <Select onSelect={this.onSelect} placeholder="请选择">
                                    {this.state.firstDir.map((item, index) => (
                                        <Option key={index} value={item.menuId + ''}>{item.menuName}</Option>
                                    ))}
                                </Select>
                                )}
                        </FormItem>
                    </Col>

                    <Col span={4}>
                        <FormItem
                            {...formItemLayout}
                            wrapperCol={{ span: 22 }}
                        >
                            {getFieldDecorator('menuId', {
                                rules: [
                                    { required: true, message: '请选择一个分类!' },
                                ],
                            })(
                                <Select placeholder="请选择">
                                    {this.state.secondDir.map((item, index) => (
                                        <Option key={index} value={item.menuId + ''}>{item.menuName}</Option>
                                    ))}
                                </Select>
                                )}
                        </FormItem>
                    </Col>
                </Row>

                <FormItem
                    label="图书标题"
                    {...formItemLayout}
                >
                    {getFieldDecorator('bookName', {
                        rules: [{ required: true, message: '请输入图书标题!' }, {
                            max: 100, message: "图书标题不能超过100字，请重新输入！"
                        }],
                    })(
                        <Input />
                        )}
                </FormItem>
                <FormItem
                    label="出版社"
                    {...formItemLayout}
                >
                    {getFieldDecorator('press', {
                        rules: [{ required: true, message: '请输入出版社!' }, {
                            max: 20, message: "出版社不能超过20字，请重新输入！"
                        }],
                    })(
                        <Input />
                        )}
                </FormItem>
                <FormItem
                    label="作者"
                    {...formItemLayout}
                >
                    {getFieldDecorator('author', {
                        reules: [{ max: 512, message: "作者不能超过512字，请重新输入！" }]
                    })(
                        <Input />
                        )}
                </FormItem>
                <FormItem
                    label="译者"
                    {...formItemLayout}
                >
                    {getFieldDecorator('translator', {
                        reules: [{ max: 128, message: "译者不能超过128字，请重新输入！" }]
                    })(
                        <Input />
                        )}
                </FormItem>
                <FormItem
                    label="书号"
                    {...formItemLayout}
                >
                    {getFieldDecorator('realIsbn', {
                        normalize: this.changeNum,
                        rules: [
                            { required: true, message: '请输入书号!' }
                        ],
                    })(
                        <Input maxLength={13} disabled={this.props.info ? true : false} />
                        )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="出版时间"
                >
                    {getFieldDecorator('releaseDate')(
                        <DatePicker format="YYYY-MM-DD" />
                    )}
                </FormItem>
                <Row gutter={1}>
                    <Col span={4}>
                        <FormItem
                            label="全本单价"
                            {...priceLayout}
                        >
                            {getFieldDecorator('fullPrice')(
                                <InputNumber min={0} onChange={this.inputOnChange} />
                            )}
                            <span className="ant-form-text">元</span>
                        </FormItem>
                    </Col>
                    {
                        this.state.isCprice ?
                            <Col span={4}>
                                <FormItem
                                    label="章节单价"
                                    {...priceLayout}
                                >
                                    {getFieldDecorator('chapterPrice', {
                                        rules: [
                                            { required: true, message: '请输入章节单价' }
                                        ]
                                    })(
                                        <InputNumber min={0} />
                                        )}
                                    <span className="ant-form-text">元</span>
                                </FormItem>
                            </Col> : null
                    }
                </Row>
                <FormItem
                    {...formItemLayout}
                    label="允许试读"
                >
                    {getFieldDecorator('isTry', {
                        initialValue: 1
                    })(
                        <RadioGroup defaultvalue={0}>
                            <Radio value={1}>是</Radio>
                            <Radio value={0}>否</Radio>
                        </RadioGroup>
                        )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="推荐阅读"
                >
                    {getFieldDecorator('isRecommend', {
                        rules: [{ required: true, message: '请选择是否推荐!' }],
                        initialValue: 0
                    })(
                        <RadioGroup defaultvalue={0}>
                            <Radio value={1}>是</Radio>
                            <Radio value={0}>否</Radio>
                        </RadioGroup>
                        )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    wrapperCol={{ span: 11 }}
                    label="关键字"
                >
                    <Row gutter={5}>
                        <Col span={11}>
                            {getFieldDecorator('keyword', {
                                rules: [{ required: false }, {
                                    max: 30, message: "关键字不能超过30字，请重新输入！"
                                }]
                            })(
                                <Input size='large' />
                                )}
                        </Col>
                        <Col span={5}>
                            关键字'，'隔开
                        </Col>
                    </Row>
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="封面"
                >
                    {getFieldDecorator('imgList', {
                        valuePropName: 'fileList',
                        getValueFromEvent: this.normFile,
                        rules: [{ required: true, message: '请上传图书封面!' }]
                    })(
                        <Upload name="file" action={config.imageUrl} listType="picture" accept=".jpg, .png, .gif">
                            <Button disabled={this.state.showCover}>
                                <Icon type="upload" /> 上传图片
                            </Button>
                        </Upload>
                        )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="图书简介"
                >
                    {getFieldDecorator('bookDesc')(
                        <Input type="textarea" />
                    )}
                </FormItem>

                <FormItem
                    wrapperCol={{ span: 4, offset: 1 }}
                    className={'editButtons'}
                >
                    <Button type="primary" htmlType="submit" className="save-btn" loading={this.state.loading}>保存</Button>
                    <Button onClick={this.cancel.bind(this)}>取消</Button>
                </FormItem>
            </Form>
        );
    }
}


export default class BookNew extends React.Component {
    render() {
        const EditBook = Form.create()(BookForm);
        return (
            <EditBook info={this.props.info} />
        )
    }
}