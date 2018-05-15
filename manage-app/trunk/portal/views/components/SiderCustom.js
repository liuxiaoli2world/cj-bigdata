import React, { Component } from 'react';
import { Layout, Menu, Icon } from 'antd';
import './style.scss';
const { Sider } = Layout;
const SubMenu = Menu.SubMenu;
import { Link, hashHistory } from 'react-router';

class SiderCustom extends Component {
    state = {
        collapsed: false,
        mode: 'inline',
        openKey: '',
        selectedKey: '',
        menus: []
    };

    componentWillMount() {
        this.geneMenu();
    }

    componentDidMount() {
        // const _path = this.props.path;
        // this.setState({
        //     openKey: _path.substr(0, _path.lastIndexOf('/')),
        //     selectedKey: _path
        // });
    }
    componentWillReceiveProps(nextProps) {
        this.onCollapse(nextProps.collapsed);
    }
    onCollapse = (collapsed) => {
        this.setState({
            collapsed,
            mode: collapsed ? 'vertical' : 'inline',
        });
    };
    menuClick = e => {
        this.setState({
            selectedKey: e.key
        });

    };
    openMenu = v => {
        this.setState({
            openKey: v[v.length - 1]
        })
    };

    geneMenu = () => {
        let menus = [];
        let allowMap = {};
        const allowMapString = localStorage.getItem('ballowMap');
        if (!allowMapString) {
            return;
        } else {
            allowMap = JSON.parse(allowMapString);
        }

        let defaultKey = '/manage/home';
        if (allowMap[100]) {
            menus.push(
                <Menu.Item key="/manage/home">
                    <Link to={'/manage/home'}><img src={require('assets/images/icon-home.png')} /><span className="nav-text">首页</span></Link>
                </Menu.Item>
            )
        }
        if (allowMap[101]) {
            menus.push(
                <Menu.Item key="/manage/content">
                    <Link to={'/manage/content'}><img src={require('assets/images/icon-content.png')} /><span className="nav-text">内容管理</span></Link>
                </Menu.Item>
            )
        }
        if (allowMap[102]) {
            menus.push(
                <Menu.Item key="/manage/resource">
                    <Link to={'/manage/resource'}><img src={require('assets/images/icon-resource.png')} /><span className="nav-text">资源管理</span></Link>
                </Menu.Item>
            )
        }
        if (allowMap[103]) {
            menus.push(
                <Menu.Item key="/manage/expert">
                    <Link to={'/manage/expert'}><img src={require('assets/images/icon-expert.png')} /><span className="nav-text">专家管理</span></Link>
                </Menu.Item>
            )
        }
        if (allowMap[104]) {
            menus.push(
                <Menu.Item key="/manage/user">
                    <Link to={'/manage/user'}><img src={require('assets/images/icon-user.png')} /><span className="nav-text">用户管理</span></Link>
                </Menu.Item>
            )
        }
        if (allowMap[105]) {
            menus.push(
                <Menu.Item key="/manage/dataAnalaysis">
                    <Link to={'/manage/dataAnalaysis'}><img src={require('assets/images/icon-data.png')} /><span className="nav-text">数据分析</span></Link>
                </Menu.Item>
            )
        }
        if (allowMap[106]) {
            menus.push(
                <Menu.Item key="/manage/sys">
                    <Link to={'/manage/sys'}><img src={require('assets/images/icon-setting.png')} /><span className="nav-text">系统设置</span></Link>
                </Menu.Item>
            )
        }
        // 设置默认选择标签
        if (menus.length > 0) {
            this.setState({
                menus,
                selectedKey: menus[0].key
            });
            hashHistory.push(menus[0].key);
        }
    }
    render() {
        return (
            <Sider
                trigger={null}
                breakpoint="lg"
                collapsible
                collapsed={this.props.collapsed}
                onCollapse={this.onCollapse}
                width = {230}
            >
                <div className="logo">
                    <img src={require("assets/images/logo_login.png")} alt="log" />
                </div>
                <Menu
                    onClick={this.menuClick}
                    theme="dark"
                    mode={this.state.mode}
                    selectedKeys={[this.state.selectedKey]}
                    openKeys={[this.state.openKey]}
                    onOpenChange={this.openMenu}
                >
                    {
                        this.state.menus.map((item, index) => item)
                    }

                </Menu>
            </Sider>
        )
    }
}

export default SiderCustom;