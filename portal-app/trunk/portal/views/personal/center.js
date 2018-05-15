import React from 'react';
import { Link, hashHistory } from 'react-router';
import { Icon, Pagination, Breadcrumb, Button, Input } from 'antd';
import Wrapper from './Wrapper.js';
import { autobind } from 'core-decorators';
import { resizeEvent } from 'util/carousel-helper';
import { get, post } from 'util/request.js';

export default class Center extends React.Component {
    constructor(props) {
        super(props);
        const userId = window.localStorage.getItem('userId');
        this.state = {
            person: {},
            userId: userId
        }
    }

    componentDidMount() {
        if(this.state.userId) {
            get(`user/user/selectPersonalCenter/${this.state.userId}`)
            .then(res => {
                if(res && res.data){
                    this.setState({
                        person: res.data || {}
                    });
                }
            }).catch(e=>{
                console.log(e)
            })
        }else {
            hashHistory.push("/login");
        }
    }

    render() {
        const { person } = this.state
        return (
            <Wrapper>
                <div className="center_head">
                    <img src={require('assets/images/personal/config1.png')} />
                    <div className="vip">个人资料</div>
                </div>
                <ul className="center_content">
                    <li className="center_content_item">
                        用户名 :<span> {person.username}</span>
                    </li>
                    <li className="center_content_item">
                        注册邮箱 ：<span>{person.email}</span>
                    </li>
                    <li className="center_content_item">
                        注册时间 ：<span>{person.createdAt}</span>
                    </li>
                </ul>


            </Wrapper>
        )
    }
}