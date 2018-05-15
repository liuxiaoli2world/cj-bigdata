import React from 'react';
import config from 'config';
import {
    Link,
    hashHistory
} from 'react-router';
import './index.scss';
import {
    autobind
} from 'core-decorators'
import {
    SubEmiter,
    Emiter
} from 'util';
import { Carousel, Tabs, Menu, Dropdown, Icon, Input, Button, Form, Checkbox, message } from 'antd';
import { get, post } from 'views/util/request';
import Blogroll from '../blogroll/blogroll.js';
const FormItem = Form.Item;

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            confirmDirty: false,
            autoCompleteResult: [],
            str: '',
            color0: this.getColor(),
            color1: this.getColor(),
            color2: this.getColor(),
            color3: this.getColor(),
            username: '',
            email: ''
        }
    }

    componentDidMount() {
        this.props.form.setFieldsValue({
            agreement: true
        });
        this.rnd_str(4, true, true, true);
    }

    handleSubmit = (e) => {
        e.preventDefault();
        const seqNum = this.props.form.getFieldValue('seqNum')
        this.props.form.validateFields((err, values) => {
            if (!err) {
                if (values.agreement) {
                    const { email, password, username, seqNum } = values;
                    this.Register({
                        email, password, username, seqNum
                    })
                }
            }
        });
    }

    Register(value) {
        post(`user/user/add`, value)
            .then(res => {
                if (res.meta.success) {
                    message.success("注册成功");
                    setTimeout(function(){
                        hashHistory.push('login');
                    },1000)
                }
            })
    }

    checkEmail = (rule, value, callback) => {
        if (value) {
            get(`user/user/selectByEmail?email=${value}`)
                .then((jsonData) => {
                    if (!jsonData.meta.success) {
                        callback('邮箱已经存在！');
                    } else {
                        callback()
                    }
                })
        } else {
            callback()
        }
    }

    checkName = (rule, value, callback) => {
        if (value) {
            get(`user/user/selectByUserName?username=${value}`)
                .then((jsonData) => {
                    if (!jsonData.meta.success) {
                        callback('用户名已经存在！');
                    } else {
                        callback()
                    }
                })
        } else {
            callback()
        }
    }

    checkSeqNum = (rule, value, callback) => {
        if (value) {
            get(`user/organization/selectBySqeNum?seqNum=${value}`)
                .then(jsonData => {
                    if (!jsonData.meta.success) {
                        callback('机构编码不存在！');
                    } else {
                        callback()
                    }
                })
        } else {
            callback()

        }
    }

    handleReset = () => {
        this.props.form.resetFields();
        this.props.form.setFieldsValue({
            agreement: true
        });
    }
    handleConfirmBlur = (e) => {
        const value = e.target.value;
        this.setState({ confirmDirty: this.state.confirmDirty || !!value });
    }

    inputOnChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        this.setState({
            [name]: value
        });
    }

    checkPassword = (rule, value, callback) => {
        const form = this.props.form;
        if (value && value !== form.getFieldValue('password')) {
            callback('两次输入的密码不一致！');
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
    //检测验证码
    checkIdentify = (rule, value, callback) => {
        if (value && value.toLowerCase() !== this.state.str.toLowerCase()) {
            callback('验证码输入错误！');
        } else {
            callback();
        }
    }
    // //改变验证码
    // changeCode=()=>{
    //     this.setState({str:Math.random().toString(36).substr(2).substring(0,4)})
    // }

    //改变验证码
    rnd_str = (str_0, str_1, str_2, str_3) => {
        var Seed_array = new Array();
        var seedary;
        var i;

        Seed_array[0] = ''
        Seed_array[1] = 'A B C D E F G H I J K L M N O P Q R S T U V W X Y Z';
        Seed_array[2] = 'a b c d e f g h i j k l m n o p q r s t u v w x y z';
        Seed_array[3] = '0 1 2 3 4 5 6 7 8 9';


        if (!str_1 && !str_2 && !str_3) { str_1 = true; str_2 = true; str_3 = true; }

        if (str_1) { Seed_array[0] += Seed_array[1]; }
        if (str_2) { Seed_array[0] += ' ' + Seed_array[2]; }
        if (str_3) { Seed_array[0] += ' ' + Seed_array[3]; }

        Seed_array[0] = Seed_array[0].split(' ');
        seedary = ''
        for (i = 0; i < str_0; i++) {
            seedary += Seed_array[0][Math.round(Math.random() * (Seed_array[0].length - 1))]
        }
        this.setState({ str: seedary })
        // this.getColor()
        this.setState({
            color0: this.getColor(),
            color1: this.getColor(),
            color2: this.getColor(),
            color3: this.getColor(),
            color4: this.getColor(),
        })

    }
    //生成随机颜色
    getColor = () => {
        var colorElements = '0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f';
        var colorArray = colorElements.split(',');
        var color = '#';
        for (var i = 0; i < 6; i++) {
            color += colorArray[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    changeNum = (val) => {
        val += '';
        val = val.replace(/\D/g, '')
        return val;
    }


    render() {
        const { getFieldDecorator } = this.props.form;

        const formItemLayout = {
            labelCol: {
                xs: { span: 24 },
                sm: { span: 6 },
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 14 },
            },
        };
        const tailFormItemLayout = {
            wrapperCol: {
                xs: {
                    span: 24,
                    offset: 0,
                },
                sm: {
                    span: 14,
                    offset: 6,
                },
            },
        };

        return (
            <div className='login-page'>
                <div><img src={require('assets/images/login/blue.png')} /></div>
                <div className='content clearfix'>
                    <div className='content_head'>
                        会员注册
                        <span>已有账号？<Link to='login'>立即登录</Link>  </span>
                    </div>
                    <Form onSubmit={this.handleSubmit}>
                        <FormItem label='邮箱' {...formItemLayout}>
                            {getFieldDecorator('email', {
                                validateTrigger: 'onBlur',
                                rules: [
                                    { type: 'email', message: '您输入的不是邮箱格式！' },
                                    { required: true, message: '邮箱不能为空！' },
                                    { validator: this.checkEmail }
                                ]
                            })(

                                <Input name='email' onChange={this.inputOnChange} />

                                )}
                            请输入您的常用邮箱，以便密码丢失时找回密码
                        </FormItem>
                        <FormItem label='用户名' {...formItemLayout}>
                            {getFieldDecorator('username', {
                                validateTrigger: 'onBlur',
                                rules: [
                                    { required: true, message: '用户名不能为空！' },
                                    { pattern: /^[\u4e00-\u9fa50-9a-zA-Z_]{4,24}$/, message: '请输入4-24位字符！' },
                                    { validator: this.checkName }
                                ]
                            })(

                                <Input name='username' onChange={this.inputOnChange} />

                                )}
                            4-24位字符，可使用中英文、数字、下划线
                        </FormItem>
                        <FormItem label='密码' {...formItemLayout}>
                            {getFieldDecorator('password', {
                                rules: [{ required: true, message: '密码不能为空！' }, { validator: this.checkConfirm }, { pattern: /^\w{6,16}$/, message: '请输入6-16位字符！' }]
                            })(

                                <Input type='password' />

                                )}
                            6-16位字符，区分大小写
                        </FormItem>
                        <FormItem label='确认密码' {...formItemLayout}>
                            {getFieldDecorator('confirm', {
                                rules: [{ required: true, message: '确认密码不能为空！' }, { validator: this.checkPassword }]
                            })(

                                <Input type='password' onBlur={this.handleConfirmBlur} />

                                )}
                            请再次输入密码
                        </FormItem>
                        <FormItem label='机构编码' {...formItemLayout}>
                            {getFieldDecorator('seqNum', {
                                normalize: this.changeNum,
                                validateTrigger: 'onBlur',
                                rules: [{ validator: this.checkSeqNum }],
                            })(
                                <Input />
                                )}
                        </FormItem>
                        <FormItem label='验证码' {...formItemLayout}>
                            {getFieldDecorator('identify', {
                                rules: [{ required: true, message: '验证码不能为空！' }, { validator: this.checkIdentify }]
                            })(
                                <Input className='verify_input' />
                                )}
                            <div className='verify'>
                                <span style={{ color: this.state.color0 }}>{this.state.str[0]}</span>
                                <span style={{ color: this.state.color1 }}>{this.state.str[1]}</span>
                                <span style={{ color: this.state.color2 }}>{this.state.str[2]}</span>
                                <span style={{ color: this.state.color3 }}>{this.state.str[3]}</span>
                            </div>
                            <a onClick={() => { this.rnd_str(4, true, true, true) }}>看不清？换一张</a>
                        </FormItem>
                        <FormItem {...tailFormItemLayout} style={{ marginBottom: 8 }}>
                            {getFieldDecorator('agreement', {
                                valuePropName: 'checked',
                                rules: [{ required: true, message: '请阅读并同意《长江大数据用户注册协议》！' }]
                            })(
                                <Checkbox>我已阅读并同意 <a href=''>《长江大数据用户注册协议》</a></Checkbox>
                                )}
                        </FormItem>
                        <FormItem style={{ padding: '26px 0 0 25%', width: '100%' }}>
                            <Button className='btn' type='primary' htmlType='submit'>注册</Button>
                            <Button className='btn' type='primary' onClick={this.handleReset}>重置</Button>
                        </FormItem>
                    </Form>

                </div>
                <div className='bottom_img'><img src={require('assets/images/bottom.png')} /></div>
                <Blogroll />

            </div>
        )
    }
}
const WrappedRegister = Form.create()(Register);

class AddRegister extends React.Component {
    render() {
        return (
            <div>
                <WrappedRegister />
            </div>
        )
    }
}

export default AddRegister