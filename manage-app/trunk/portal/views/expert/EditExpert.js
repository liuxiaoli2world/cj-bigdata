import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import { Form, Upload, Input, Tooltip, Icon, Cascader, Select, Row, Col, Checkbox, Button, Radio, DatePicker, message } from 'antd';
import data from './cityData.js';
import moment from 'moment';
import { get, post } from '../utils/request';
import config from '../../config/index.js';
import { SubEmiter, Emiter } from '../utils/index';
const FormItem = Form.Item;
const Option = Select.Option;
const MonthPicker = DatePicker.MonthPicker;
const RadioGroup = Radio.Group;

class ExpertForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            confirmDirty: false,
            classList: [],
            content: '',
            showCover: false,
        }
    }
    componentDidMount() {
        this.props.form.setFieldsValue({
            isRecommend: 0
        })

        if (this.props.info) {
            let { birthday, duty, expertDesc, imageUrl, isRecommend, nation, professionalField, realName, region, domainId } = this.props.info;
            birthday = moment(birthday, 'YYYY-MM')
            region = region ? region.split(',') : '';
            imageUrl = [{ url: imageUrl, uid: 1 }];
            domainId += '';
            this.props.form.setFieldsValue({
                birthday, duty, domainId, expertDesc, imageUrl, isRecommend, nation, professionalField, realName, region
            })
            this.setState({
                showCover: imageUrl.length > 0 ? true : false,
            })
        }
    }

    checkPassword = (rule, value, callback) => {
        const form = this.props.form;
        if (value && value !== form.getFieldValue('password')) {
            callback('密码不一致!');
        } else {
            callback();
        }
    }

    checkConfirm = (rule, value, callback) => {
        const form = this.props.form;
        if (value && this.state.confirmDirty) {
            form.validateFields(['confirm'], { force: true });
        }
        callback();
    }

    handleConfirmBlur = (e) => {
        const value = e.target.value;
        this.setState({ confirmDirty: this.state.confirmDirty || !!value });
    }

    normFile = (e) => {
        this.setState({
            showCover: e.fileList.length > 0 ? true : false,
        })
        if (Array.isArray(e)) {
            return e;
        }
        return e && e.fileList;
    }

    cancel() {
        Emiter.emit("closePanel", '3')
    }

    save = (e) => {
        e.preventDefault();
        const form = this.props.form;
        let url = `expert/expert/insertExpert`;
        let msg = '专家新增成功'
        this.props.form.validateFields((err, values) => {
            if (!err) {
                values.birthday = values.birthday.format("YYYY-MM-DD hh:mm:ss");
                if (values.region) {
                    values.region = values.region.join(',')
                }
                values.password = values.password ? values.password : '123456';
                values.imageUrl = (values.imageUrl && values.imageUrl.length > 0 && values.imageUrl[0].response) ?
                    values.imageUrl[0].response.data :
                    (values.imageUrl && values.imageUrl.length > 0 && values.imageUrl[0].url) ?
                        values.imageUrl[0].url :
                        null;
                if (this.props.info) {
                    values.expertId = this.props.info.expertId;
                    url = `expert/expert/updateExpert`;
                    msg = '专家修改成功'
                }
                post(url, values)
                    .then((res) => {
                        if (res && res.meta && res.meta.success) {
                            message.success(msg);
                            this.cancel();
                            Emiter.emit("addExpertOk", { "status": true })
                        } else {
                            if (res && res.meta && res.meta.message) {
                                message.error(res.meta.message);
                            }
                        }
                    })
            }
        });
    }
    render() {
        const { getFieldDecorator } = this.props.form;
        const { content } = this.state;
        const formItemLayout = {
            labelCol: { span: 1 },
            wrapperCol: { span: 5 }
        };
        const DateConfig = {
            rules: [{ type: 'object'}],
        };
        return (
            <Form onSubmit={this.save} className="expertForm">
                {!this.props.info ?
                    <span>
                        <FormItem
                            label="登录名"
                            {...formItemLayout}
                        >
                            {getFieldDecorator('loginName', {
                                rules: [{ required: true, message: '请输入登录名!' }, {
                                    max: 50, message: "登录名不能超过50位字符！"
                                }],
                            })(
                                <Input />
                                )}
                        </FormItem>

                        <FormItem
                            {...formItemLayout}
                            wrapperCol={{ span: 11 }}
                            label="登录密码"
                        >
                            <Row gutter={10}>
                                <Col span={11}>
                                    {getFieldDecorator('password', {
                                        rules: [{
                                            validator: this.checkConfirm,
                                        }],
                                    })(
                                        <Input type="password" size="large"/>
                                        )}
                                </Col>
                                <Col span={5}>
                                    <span className='extraTips'>登录密码默认为123456</span>
                                </Col>
                            </Row>
                        </FormItem>
                        <FormItem
                            {...formItemLayout}
                            label="确认密码"
                        >
                            {getFieldDecorator('confirm', {
                                rules: [{
                                    validator: this.checkPassword,
                                }],
                            })(
                                <Input type="password" onBlur={this.handleConfirmBlur} />
                                )}
                        </FormItem>
                    </span>
                    : null
                }
                <FormItem
                    label="姓名"
                    {...formItemLayout}
                >
                    {getFieldDecorator('realName', {
                        rules: [{ required: true, message: '请输入姓名!' }, {
                            max: 50, message: "姓名不能超过50位字符!"
                        }],
                    })(
                        <Input />
                        )}
                </FormItem>
                <FormItem
                    label="民族"
                    {...formItemLayout}
                >
                    {getFieldDecorator('nation', {
                        rules: [{ required: true, message: '请输入民族!' }, {
                            max: 10, message: "民族不能超过10位字符!"
                        }],
                    })(
                        <Input />
                        )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="出生年月"
                >
                    {getFieldDecorator('birthday')(
                        <MonthPicker format="YYYY-MM" />
                    )}
                </FormItem>
                <FormItem
                    label="职务"
                    {...formItemLayout}
                >
                    {getFieldDecorator('duty', {
                        rules: [{
                            max: 20, message: "职务不能超过20位字符!"
                        }],
                    })(
                        <Input />
                        )}
                </FormItem>
                <FormItem
                    label="专业领域"
                    {...formItemLayout}
                >
                    {getFieldDecorator('professionalField', {
                        rules: [{ max: 30, message: "专业领域不能超过30位字符!" }]
                    })(
                        <Input />
                        )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="专家推荐"
                >
                    {getFieldDecorator('isRecommend', {
                        rules: [{ required: true, message: '请选择是否推荐!' }],
                    })(
                        <RadioGroup>
                            <Radio value={1}>是</Radio>
                            <Radio value={0}>否</Radio>
                        </RadioGroup>
                        )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="分类"
                >
                    {getFieldDecorator('domainId', {
                        rules: [{ required: true, message: '请选择专家分类!' }],
                    })(
                        <Select placeholder="请选择">
                            {
                                this.props.classList.map((item, i) => (
                                    <Option key={item.domainId} value={item.domainId + ''}>{item.name}</Option>
                                ))
                            }
                        </Select>
                        )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="头像"
                    extra="建议图片上传尺寸为500像素*900像素"
                >
                    {getFieldDecorator('imageUrl', {
                        valuePropName: 'fileList',
                        getValueFromEvent: this.normFile,
                        rules: [{ required: true, message: "请上传头像！" }]
                    })(
                        <Upload name="file" action={config.imageUrl} listType="picture">
                            <Button disabled={this.state.showCover}>
                                <Icon type="upload" /> 上传图片
                        </Button>
                        </Upload>
                        )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="所属地区"
                >
                    {getFieldDecorator('region')(
                        <Cascader options={data} placeholder='请选择' />
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="内容简介"
                >
                    {getFieldDecorator('expertDesc', {
                        max: 1500, message: "内容简介不能超过1500位字符!"
                    })(
                        <Input type="textarea" />
                        )}
                </FormItem>
                <FormItem
                    wrapperCol={{ span: 4, offset: 1 }}
                    className={'editButtons'}
                >
                    <Button type="primary" htmlType="submit" className="save-btn">保存</Button>
                    <Button onClick={this.cancel.bind(this)}>取消</Button>
                </FormItem>
            </Form>
        );
    }
}


export default class EditExpert extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            classList: ''
        }
    }
    getDomain = () => {
        get(`expert/domain/selectAllDomain?pageNum=1&pageSize=100`)
            .then((res) => {
                if (res.meta.success) {
                    this.setState({
                        classList: res.data.list
                    })
                }
            })
    }

    render() {
        const EditExpert = Form.create()(ExpertForm);
        return (
            <SubEmiter eventName='refreshClass' listener={this.getDomain}>
                <EditExpert info={this.props.info} classList={this.state.classList ? this.state.classList : this.props.domain} />
            </SubEmiter>
        )
    }
}