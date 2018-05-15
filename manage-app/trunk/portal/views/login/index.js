import React from 'react';
import Wrapper from './style.js';
import { Input, Button, Icon, message } from 'antd';
import { get, post, formPost } from 'utils/request.js';
import { hashHistory } from 'react-router';
import base64url from 'base64url';

export default class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userName: '',
            password: '',
            disabled: true
        };
    }
    emitEmpty = () => {
        this.userNameInput.focus();
        this.setState({ userName: '' });
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });

        if (this.state.userName && this.state.password) {
            this.setState({
                disabled: false
            });
        }
    }

    login = () => {
        this.setState({
            disabled: true
        });
        const url = 'user/user/login';
        const params = {
            searchStr: this.state.userName,
            password: this.state.password,
            from: 'admin'
        };
        formPost(url, params)
            .then((jsonData) => {
                if (jsonData.meta && jsonData.meta.success) {
                    const data = jsonData.data.split('.')[1];
                    var s = base64url.decode(data);
                    let sObj = JSON.parse(s);
                    let userId = sObj.user_id;
                    let userName = sObj.user_name;
                    let realName = sObj.real_name;
                    let role = sObj.role;
                    this.getAllows(userId);
                    sessionStorage.setItem('access_token', jsonData.data);
                    localStorage.setItem('btocken', jsonData.data);
                    localStorage.setItem('buserId', userId);
                    localStorage.setItem('buserName', userName);
                    localStorage.setItem('brole', role);
                    localStorage.setItem('brealName', realName);
                } else {
                    message.info(jsonData.meta.message);
                    this.setState({
                        disabled: false
                    });
                }
            });
    }

    getAllows = (userId) => {
        get(`user/allow/findByUserId/${userId}`)
            .then(jsonData => {
                const allows = jsonData.data;
                let allowMap = {};
                for (let i = 0, len = allows.length; i < len; i++) {
                    let item = allows[i];
                    allowMap[item.allowId] = item.isAllows;
                }
                localStorage.setItem('ballowMap', JSON.stringify(allowMap));
                hashHistory.push('/manage/home');
            });
    }


    componentDidMount(){
        let scale = 500/527;
        let iWidth = $(".login-content-wrap").width();
        $(".login-content").height(scale * iWidth )
        window.addEventListener("resize",this.onWindowResize);
    }

    componentWillUnmount(){
        window.removeEventListener("resize",this.onWindowResize)
    }

    onWindowResize = () => {
        let scale = 500/527;
        let iWidth = $(".login-content-wrap").width();
        $(".login-content").height(scale * iWidth )
    }
    render() {
        const { userName, password } = this.state;
        return (
            <Wrapper>
                <div className="head">
                    <img src={require('assets/images/login-title.png')} className="title-img" />
                </div>
                <div className="login-content-wrap">
                <div className="login-content">
                    <img src={require('assets/images/login_1.png')} className="head-img" />
                    <p>长江大数据内容发布系统</p>
                    <Input
                        placeholder="输入用户名"
                        className="user"
                        prefix={<img src={userName?require("assets/images/login-user_focus.png"):require('assets/images/login-user.png')} />}
                        value={userName}
                        onChange={this.onChange}
                        name="userName"
                        onPressEnter = {this.login}
                    />
                    <Input
                        placeholder="输入密码"
                        className="password"
                        prefix={<img src={password?require("assets/images/icon-password-focus.png"):require('assets/images/icon-password.png')} />}
                        value={password}
                        name="password"
                        type="password"
                        onChange={this.onChange}
                        onPressEnter = {this.login}
                    />
                    <Button onClick={this.login}>登录</Button>
                    {/* {
                        userName ?
                            <Button  onClick={this.login}>登录</Button>
                            :
                            <Button disabled onClick={this.login}>登录</Button>
                    } */}
                </div>
                </div>
            </Wrapper>
        )
    }
}