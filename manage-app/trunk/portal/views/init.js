import { hashHistory } from 'react-router';
import React from 'react';

export default class Init extends React.Component {
    constructor(props) {
        debugger;
        super(props);
    }

    componentDidMount() {
        debugger;
        this.jump();
    }

    // 如果登录了就去首页，否则去登录页面
    jump = () => {
        const isValid = localStorage.getItem('bisValid');
        if (isValid === '1') {
            hashHistory.push('/manage/home');
        } else {
            hashHistory.push('/manage/login');
        }
    }

    render() {

        return (
            <div></div>
        );
    }
}