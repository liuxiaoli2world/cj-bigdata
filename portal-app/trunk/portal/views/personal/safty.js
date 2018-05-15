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
            beforePwd : "",               //原密码
            newPwd : "",                  //新密码
            makesurePwd : "",             //确认密码
            errormsg1 : "",
            errormsg2 : "",
            errormsg3 : "",
        }
        message.config({ top:"40%" })
	}

    changeBeforepwd = (e)=>{
        this.setState({ 
            beforePwd:e.target.value ,
            error : false
        })
    }
	changeNewpwd = (e)=>{
        this.setState({ 
            newPwd:e.target.value,
            nochange : false,
            same : false,
        })
    }
    makeSurepwd = (e)=>{
        this.setState({ 
            makesurePwd:e.target.value ,
            same : false
        })
    }

   savePwdchange = ()=>{
       let _that = this;
       isTockenValid(function(isValid){
            if(isValid == "0"){
                message.info("您的登录信息已失效，请重新登录。")
                Emiter.emit('loginChange');
                hashHistory.push("/login");
                window.localStorage.clear();
            }else{
                let {beforePwd, newPwd, makesurePwd, error, nochange, same} = _that.state;
                if(beforePwd == ""){
                    _that.setState({
                        errormsg1 : "原密码不能为空！",
                        error : true,
                    })
                    return false;
                }else if(newPwd == ""){
                    _that.setState({
                        errormsg2 : "新密码不能为空！",
                        nochange : true,
                    })
                    return false;
                }else if(newPwd !== makesurePwd){
                    _that.setState({ 
                        same :true ,
                        errormsg3 : "两次输入的新密码不一致，请重新输入！"
                    })
                    return false;
                }else if(newPwd == beforePwd){
                    _that.setState({ 
                        nochange : true ,
                        errormsg2 : "新密码与原密码一致，请重新输入！"
                    })
                    return false;
                }else if(!/^\w{6,16}$/.test(_that.state.newPwd)){
                    _that.setState({ 
                        nochange : true ,
                        errormsg2 : "密码为6~16位字符，不能包含非法字符，请重新输入！"
                    });
                    return false;
                }else{
                    const userId = window.localStorage.getItem('userId');
                    get(`user/user/updatePassword?userId=${userId}&oldPassword=${beforePwd}&newPassword=${newPwd}`)
                    .then(res=>{
                        if(res && res.meta && res.meta.success){
                            message.success("修改成功");
                            Emiter.emit('loginChange');
                            hashHistory.push("/login");
                            window.localStorage.clear();
                            //重置表单
                            _that.setState({
                                beforePwd : "",
                                newPwd : "",
                                makesurePwd : "",
                            })
                        }else if(res && res.meta && res.meta.message == "输入的密码错误"){
                            _that.setState({ 
                                error : true ,
                                errormsg1 : "原密码输入错误，请重新输入！",
                            })
                            return false;
                        }else{
                            message.error("修改密码失败!")
                        }
                    }).catch(e=>{
                        console.log(e)
                        message.error("修改密码失败!")
                    })
                }
            }
        })
   }
	render(){
        let {beforePwd,newPwd,makesurePwd,errormsg1,errormsg2,errormsg3} = this.state;
		return(
			<Wrapper>
				<div className="safty_head">
                    <img src={require('assets/images/personal/safty.png')}/>
                    <div className="vip">账户安全</div>
                </div>
                <ul className="modify_content">
                    <li className="modify_content_item">
                        <span>原密码 :</span>
                        <Input onChange={this.changeBeforepwd} type="password" value={this.state.beforePwd}/>
                        <p style={{color:"red",marginLeft:108,position:"absolute",display:this.state.error?"block":"none"}}>{errormsg1}</p>
                    </li>
                    <li className="modify_content_item">
                        <span>新密码 :</span>
                        <Input onChange={this.changeNewpwd} type="password" value={this.state.newPwd}/>
                        <p style={{color:"red",marginLeft:108,position:"absolute",display:this.state.nochange?"block":"none"}}>{errormsg2}</p>
                    </li>
                    <li className="modify_content_item">
                        <span>确认密码 :</span>
                        <Input onChange={this.makeSurepwd} type="password" value={this.state.makesurePwd}/>
                        <p style={{color:"red",marginLeft:108,position:"absolute",display:this.state.same?"block":"none"}}>{errormsg3}</p>
                    </li>
                    <li className="modify_content_button_item">
                        <Button onClick={this.savePwdchange}>保存</Button>
                    </li>
                </ul>
			</Wrapper>
		)
	}
}