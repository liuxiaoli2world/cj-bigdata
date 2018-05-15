import React from "react";
import { Wrapper } from "./style";
import { Breadcrumb, Icon } from 'antd';
import { hashHistory, Link } from "react-router";
import { SubEmiter, Emiter } from 'util/index.js';

export default class CWrapper extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            menuName: '',
            type: '',
            title: ''
        }
    }

    changeMenu(params) {
        this.setState({
            menuName: params,
            title: ''
        })
    }

    changeType(params) {
        this.setState({
            type: params
        })
    }

    changeTitle(param) {
        const title = param == 1 ? '图书' : param == 2 ? '期刊' : '图片';
        this.setState({
            title
        })
    }

    render() {
        const { menuName, type, title } = this.state;
        return (
            <Wrapper>
                {/*面包屑*/}
                <SubEmiter eventName="changeMenu" listener={this.changeMenu.bind(this)}></SubEmiter>
                <SubEmiter eventName="changeType" listener={this.changeType.bind(this)}></SubEmiter>
                <SubEmiter eventName="detailTitle" listener={this.changeTitle.bind(this)}></SubEmiter>
                <Breadcrumb separator=">" style={{ padding: '20px 0' }}>
                    <Breadcrumb.Item><Link to='/'><Icon type="pushpin-o" />当前位置:首页</Link></Breadcrumb.Item>
                    <Breadcrumb.Item>{menuName}</Breadcrumb.Item>
                    <Breadcrumb.Item>{type}</Breadcrumb.Item>
                    {title ? <Breadcrumb.Item>{title}</Breadcrumb.Item> : null}
                    {title ? <Breadcrumb.Item>{`${title}导航`}</Breadcrumb.Item> : null}
                </Breadcrumb>
                {this.props.children}
            </Wrapper>
        )
    }
}