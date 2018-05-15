import React from "react";
import {Link,hashHistory} from "react-router";
import config from 'config';
import { get, post , formPost} from 'views/util/request';
import {Input, Button, Form,message} from "antd";
import Blogroll from '../blogroll/blogroll.js';

const FormItem = Form.Item;

class ResetFormWrap extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            confirmDirty: false,
            loading : false,
        }
    }

    componentDidMount(){
        
    }

    handleConfirmBlur = (e) => {
    const value = e.target.value;
    this.setState({ confirmDirty: this.state.confirmDirty || !!value });
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

    handleSubmit = (e)=>{
        let _this = this;
        e.preventDefault();
        this.setState({
            loading : true
        })
        this.props.form.validateFields((err, values) => {
            if (!err) {
                let param = {
                    ec : _this.props.ec,
                    password : values.password
                }
                formPost(`user/user/restPass`,param)
                .then(res=>{
                    if(res&&res.meta&&res.meta.success){
                        message.success("修改成功")
                        this.setState({
                            loading : false
                        })
                        hashHistory.push("/login");
                    }
                }).catch(e=>{
                    message.error("修改失败")
                    this.setState({
                        loading : false
                    })
                })
            }else{
                message.error("修改失败")
                this.setState({
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
        return(
            <Form onSubmit={this.handleSubmit}>
                <FormItem label='密 码' {...formItemLayout}>
                    {getFieldDecorator('password', {
                        rules: [
                            { required: true, message: '密码不能为空！' }, 
                            { validator: this.checkConfirm }, 
                            { pattern: /^\w{6,16}$/, message: '请输入6-16位字符！' }
                        ]
                    })(

                        <Input type='password' />

                        )}
                    6-16位字符，区分大小写
                </FormItem>
                <FormItem label='确认密码' {...formItemLayout}>
                    {getFieldDecorator('confirm', {
                        rules: [
                            { required: true, message: '确认密码不能为空！' }, 
                            { validator: this.checkPassword }
                        ]
                    })(

                        <Input type='password' onBlur={this.handleConfirmBlur} />

                        )}
                    请再次输入密码
                </FormItem>
                <FormItem style={{ padding: '6px 0 0 33.33%', width: '100%' }}  >
                    <Button className='btn makesure' type='primary' htmlType='submit' loading={this.state.loading}>确认修改</Button>
                </FormItem>
            </Form>
        )
    }
}
const ResetForm = Form.create()(ResetFormWrap);

class ResetPwd extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            
        }
    }

    componentDidMount(){
        console.log(this.props.location.search.split("?ec=")[1])
        this.setState({
            ec : this.props.location.search.split("?ec=")[1]
        })
    }

    handleCallback = (param)=>{
        
    }
    
    render(){
        let {ec} = this.state;
        return (
            <div className='login-page'>
                <div><img src={require('assets/images/login/blue.png')} /></div>
                <div className='content clearfix'>
                    <div className='content_head'>
                        修改密码
                        <span>已有账号？<Link to='login'>立即登录</Link>  </span>
                    </div>
                    <div>
                        <ResetForm callbackParent={this.handleCallback} ec={ec}/>
                    </div>
                </div>
                <div className='bottom_img'><img src={require('assets/images/bottom.png')} /></div>
                <Blogroll />
            </div>
        )
    }
}

export default ResetPwd;