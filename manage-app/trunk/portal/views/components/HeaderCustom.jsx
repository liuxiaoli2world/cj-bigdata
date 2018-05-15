import React, { Component } from 'react';
import { Menu, Icon, Layout, Badge } from 'antd';
const { Header } = Layout;
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;
import { hashHistory } from 'react-router';

class HeaderCustom extends Component {
    quit = () => {
        sessionStorage.setItem('access_token', '');
        localStorage.clear();
        hashHistory.push('/manage/Login');
    }

    render() {
        return (
            <Header style={{ background: '#5a6378', color: 'white', fontSize: '14px', paddingLeft: '290px', height: 65 }}>
                <img src={require("assets/images/title.png")} alt="长江大数据内容发布系统" style={{ width: 209, height: 18 ,verticalAlign:"middle"}} />
                <div style={{ float: 'right' }}>
                    <img src={require("assets/images/user-top.png")} style={{ verticalAlign: 'middle', marginRight: '8px' }} />
                    <span>{localStorage.getItem('buserName')}</span>
                    <span onClick={this.quit} style={{ marginLeft: '8px', cursor: 'pointer' }} >退出</span>
                </div>
            </Header>
        )
    }
}

export default HeaderCustom;