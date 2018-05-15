import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon,Pagination,Breadcrumb,Button,Input,message} from 'antd';
import Wrapper from './Wrapper.js';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post} from 'util/request.js';
import { isTockenValid } from 'util/userTool.js';
import {SubEmiter,Emiter} from 'util';


export default class Modify extends React.Component{
	constructor(props){
        super(props);
		this.state={
            name:"",
            email:"",
            sameId : false,
            sameEmail : false,
            errormsgId : "",
            errormsgEmail : "",
        }
        message.config({top:'40%'})
	}

	componentDidMount(){
        const userId = window.localStorage.getItem('userId');
        get(`user/user/selectPersonalCenter/${userId}`).then(res=>{
			if(res && res.data){
				this.setState({
                    name : res.data.username || "",
                    email : res.data.email || "",
				})
			}
		}).catch(e=>{
            this.setState({ 
                name : "",
                email : "",
            })
        })
    }
       
    handleChange(param,e){
        let _that = this;
        if(param=="id"){
            _that.setState({
                name : e.target.value,
                sameId : false,
            })
            if(e.target.value == ""){
                _that.setState({
                    sameId : true,
                    errormsgId : "用户名不能为空！"
                })
            }
        }else if(param=="email"){
            _that.setState({
                email : e.target.value,
                sameEmail : false
            })
            if(e.target.value == ""){
                _that.setState({
                    sameEmail : true,
                    errormsgEmail : "注册邮箱不能为空！"
                })
            }
        }
    }

    //保存
    handleSave = ()=>{
        let _that = this;
        isTockenValid(function(isValid){
            if(isValid == "0"){
                message.info("您的登录信息已失效，请重新登录。")
                Emiter.emit('loginChange');
                hashHistory.push("/login");
                window.localStorage.clear();
            }else{
                let {name,email} = _that.state;
                let reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
                if(_that.state.sameId || _that.state.sameEmail){
                    return false;
                }else if(!/^[\u4e00-\u9fa50-9a-zA-Z_]{4,24}$/.test(name)){
                    _that.setState({
                        sameId : true,
                        errormsgId : "请输入4~24位字符，可为数字、字母、下划线！"
                    })
                    return false;
                }else if(!reg.test(email)){
                    _that.setState({
                        sameEmail : true,
                        errormsgEmail : "请输入正确的邮箱格式！"
                    })
                    return false;
                }else{
                    const userId = window.localStorage.getItem('userId');
                    get(`user/user/updateUserNameorEmail?userId=${userId}&username=${name}&email=${email}`)
                    .then(res=>{
                        if(res && res.meta && res.meta.success){
                            _that.setState({
                                sameId : false,
                                sameEmail : false
                            })
                            message.success("修改成功！")
                            Emiter.emit('loginChange');
                            hashHistory.push("/login");
                            window.localStorage.clear();
                        }else if(res && res.meta && res.meta.message=="修改的用户名存在了"){
                            _that.setState({
                                sameId : true,
                                errormsgId : "该用户名已存在，请重新输入！",
                            })
                            return false;
                        }else if(res && res.meta && res.meta.message=="修改的邮箱存在了"){
                            _that.setState({
                                sameEmail : true,
                                errormsgEmail : "该邮箱已存在，请重新输入！",
                            })
                            return false;
                        }else{
                            message.error("未作出修改");
                            return false;
                        }
                    }).catch(e=>{
                        console.log(e)
                        message.error('修改失败');
                    })
                }
            }
        })
    }
    
	render(){
        const {name,email,errormsgId,errormsgEmail}=this.state
		return(
			<Wrapper>
				<div className="modify_head">
                    <img src={require('assets/images/personal/modify.png')}/>
                    <div className="vip">修改资料</div>
                </div>
                <ul className="modify_content">
                    <li className="modify_content_item">
                        <span>用户名 :</span>
                        <Input value={name} name="name" onChange={this.handleChange.bind(this,"id")} placeholder="请输入用户名"/>
                        <p style={{color:"red",position:"absolute",marginLeft:110,display:this.state.sameId?"block":"none"}}>{errormsgId}</p>
                    </li>
                    <li className="modify_content_item">
                        <span>注册邮箱 :</span>
                        <Input value={email} name="email" onChange={this.handleChange.bind(this,"email")} placeholder="请输入邮箱账号"/>
                        <p style={{color:"red",position:"absolute",marginLeft:110,display:this.state.sameEmail?"block":"none"}}>{errormsgEmail}</p>
                    </li>
                    <li className="modify_content_button_item">
                        <Button onClick={this.handleSave}>保存</Button>
                    </li>
                </ul>
               
              
			</Wrapper>
		)
	}
}