import React from "react";
import {Link,hashHistory} from "react-router";
import config from 'config';
import { get, post , formPost} from 'views/util/request';
import {Input, Button, Form,} from "antd";
import Blogroll from '../blogroll/blogroll.js';

const FormItem = Form.Item;

class FindPwdWrap extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            confirmDirty: false,
            autoCompleteResult: [],
            str: '',
            color0: this.getColor(),
            color1: this.getColor(),
            color2: this.getColor(),
            color3: this.getColor(),
            loading : false
        }
    }

    componentDidMount(){
        this.rnd_str(4, true, true, true);
    }

    checkEmail = (rule, value, callback) => {
        if (value) {
            get(`user/user/selectByEmail?email=${value}`)
                .then((jsonData) => {
                    if (!jsonData.meta.success) {
                        callback();
                    } else {
                        callback('邮箱不存在！')
                    }
                })
        } else {
            callback()
        }
    }

    //检测验证码
    checkIdentify = (rule, value, callback) => {
        if (value && value.toLowerCase() !== this.state.str.toLowerCase()) {
            callback('验证码输入错误！');
        } else {
            callback();
        }
    }


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

    handleSubmit = (e)=>{
        let _this = this;
        e.preventDefault();
        this.setState({
            loading : true
        })
        this.props.form.validateFields((err, values) => {
            if (!err) {
                post(`user/user/sendPassLink?email=${values.email}`)
                .then(res=>{
                    if(res&&res.meta&&res.meta.success){
                        _this.setState({
                            loading : false
                        })
                        this.props.callbackParent({
                            isSend : true,
                            email : values.email
                        })
                    }else{
                        _this.setState({
                            loading : false
                        })
                    }
                }).catch(e=>{
                    console.log(e)
                })
            }else{
                _this.setState({
                    loading : false
                })
            }
        });
    }

    render(){
        const { getFieldDecorator } = this.props.form;
        
        const formItemLayout = {
            labelCol: {
                xs: { span: 24 },
                sm: { span: 8 },
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
                        <Input name='email'/>
                        )}
                    请输入您的常用邮箱，以便找回密码
                </FormItem>
                
                <FormItem label='验证码' {...formItemLayout}>
                    {getFieldDecorator('identify', {
                        rules: [
                            { required: true, message: '验证码不能为空！' }, 
                            { validator: this.checkIdentify }]
                    })(
                        <Input className='verify_input' />
                        )}
                    <div className='verify left'>
                        <span style={{ color: this.state.color0 }}>{this.state.str[0]}</span>
                        <span style={{ color: this.state.color1 }}>{this.state.str[1]}</span>
                        <span style={{ color: this.state.color2 }}>{this.state.str[2]}</span>
                        <span style={{ color: this.state.color3 }}>{this.state.str[3]}</span>
                    </div>
                    <a onClick={() => { this.rnd_str(4, true, true, true) }}>看不清？换一张</a>
                </FormItem>
                <FormItem style={{ padding: '6px 0 0 33.33%', width: '100%' }}>
                    <Button className='btn nextstep' type='primary' htmlType='submit' loading={this.state.loading}>下一步</Button>
                </FormItem>
            </Form>
        )
    }
}

const FindPwdform = Form.create()(FindPwdWrap);

class FindPassword extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            isSend : false,
        }
    }

    componentDidMount(){
    }

    handleCallback = (param)=>{
        let email = param.email;
        let email_num = email.split("@")[0];
        let email_suffix = email.split("@")[1];
        let str = '';
        str  = email_num.substr(0,3) + "***" + email_num.substr(email_num.length-3) +"@" + email_suffix;
        this.setState({
            isSend : param.isSend,
            email : str,
            allEmail : email
        })
    }
    handleLogin = ()=>{
        hashHistory.push("/login");
    }
    handleSendEmail = ()=>{
        let {allEmail} = this.state;
        post(`user/user/sendPassLink?email=${allEmail}`)
        .then(res=>{
            if(res&&res.meta&&res.meta.success){
                console.log("发送成功")
            }else{
                console.log("发送失败")
            }
        }).catch(e=>{
            console.log(e)
        })
    }
    render(){
        let {isSend,email} = this.state;
        return (
            <div className='login-page'>
                <div><img src={require('assets/images/login/blue.png')} /></div>
                <div className='content clearfix'>
                    <div className='content_head'>
                        {isSend ? '找回密码' : '忘记密码'}
                        <span>已有账号？<Link to='login'>立即登录</Link>  </span>
                    </div>
                    {
                        !isSend ? <FindPwdform  callbackParent={this.handleCallback}/>
                        :
                        <div className='content_middle' >
                            <p className='first-line'>我们已向您的注册邮箱{email}发送了</p>
                            <p className='second-line'>一封密码找回邮件  请您注意接收邮件</p>
                            <div><button onClick={this.handleLogin}>确认</button></div>
                            <p className='last-line'>请注意查收邮件，并按照邮件中的提示操作，完成安全认证。没有收到？<Link to="" onClick={this.handleSendEmail}> 重新发送</Link></p>
                        </div>
                    }
                </div>
                <div className='bottom_img'><img src={require('assets/images/bottom.png')} /></div>
                <Blogroll />
            </div>
        )
    }
}

export default FindPassword;
