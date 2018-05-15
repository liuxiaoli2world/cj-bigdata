import React from "react";
import {Link, hashHistory} from "react-router";
import {get, post} from "utils/request.js";
import {Input,Radio,Button,message} from "antd";
import {ExceptionWrap} from "./style.js";

const RadioGroup = Radio.Group;

class ExceptionSetting extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            Ipnum : "",
            sendEmail : "1",
            errorMsg : "",
            error : false,
            loading : false,
        }
    }
    componentDidMount(){
        get(`user/exceptionlogin/query`)
        .then(res=>{
            if(res && res.data){
                this.setState({
                    Ipnum : res.data.ipNum || "",
                    sendEmail : `${res.data.isSendEmail}`,
                    id : res.data.exceptionLoginId,
                })
            }
        })
    }
    //改变登录Ip个数
    handleChangenum = (e)=>{
        if(e.target.value==""){
            this.setState({
                error : true,
                errorMsg : "登录IP个数不能设置为空!",
            })
        }else if(!/^\d*$/.test(e.target.value)){
            this.setState({
                error : true,
                errorMsg : "登录IP个数只能为数字!",
            })
        }else{
            this.setState({
                error : false,
            })
        }
        this.setState({
            Ipnum : e.target.value
        })
    }

    //是否发送邮件
    handleChangeEmail = (e)=>{
        this.setState({
            sendEmail : e.target.value
        })
    }

    //保存
    handleSubmit = (e)=>{
        e.preventDefault();
        if(this.state.error){
            return false;
        }else{
            this.setState({ loading : true })
            let param = {
                "ipNum" : parseInt(this.state.Ipnum),
                "isSendEmail" : parseInt(this.state.sendEmail),
                "exceptionLoginId" : this.state.id
            }
            post(`user/exceptionlogin/modify`,param)
            .then(res=>{
                if(res && res.meta && res.meta.success){
                    this.setState({ loading : false});
                    message.success("设置成功");
                }
            }).catch(e=>{
                message.error('服务超时');
            })
        }
    }
    render(){
        return (
            <ExceptionWrap>
                <div>
                    <label className="label-message">登录IP个数：</label>
                    <Input value={this.state.Ipnum} onChange={this.handleChangenum} className={this.state.error?"error":null}/>
                    <span className="info-message">( IP超过该数量时提示异常 )</span>
                    <p className="error-message" style={{display:this.state.error?"block":"none"}}>{this.state.errorMsg}</p>
                </div>
                <div>
                    <label className="label-message">是否发送邮件：</label>
                    <RadioGroup value={this.state.sendEmail} onChange={this.handleChangeEmail}>
                        <Radio value="1">是</Radio>
                        <Radio value="0">否</Radio>
                    </RadioGroup>
                    <span className="info-message">( 用户封号时 )</span>
                </div>
                <Button onClick={this.handleSubmit} loading={this.state.loading}>保存</Button>
            </ExceptionWrap>
        )
    }
}

export default ExceptionSetting;