import React from "react";
import ReactDOM from "react-dom";
import { Link, hashHistory } from "react-router";
import RightPart from "./RightPart.js";
import { Input, Button } from "antd";
import styled from "styled-components";
import "./information.scss";
import { get, post } from "util/request.js";
import {
	SubEmiter,
	Emiter
} from 'util';



class Detail extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			detail: {}
		}
	}
	componentDidMount() {
		this.queryDetail(this.props.params.id);
	}

	queryDetail = (id) => {
		get(`book/content/query/${id}/index`)
			.then(res => {
				if (res && res.data) {
					this.setState({
						detail: res.data || {}
					})
				}
			})
	}

	clickChange = (id) => {
		this.queryDetail(id);
	}

	render() {
		const path = hashHistory.getCurrentLocation().pathname;
		let { detail } = this.state;
		return (
			<div className="clearfix" id="information_details">
				<SubEmiter eventName="clickChange" listener={this.clickChange.bind(this)}></SubEmiter>
				<div className="crumb">
					<img src={require("assets/images/crumb.png")} alt="面包屑指针" />
					当前位置：
					<Link to="/">首页</Link>
					<span style={{ margin: "0 6px" }}>></span>
					<Link to={this.props.params.title == "notice" ? '/information/notice' : this.props.params.title == 'history' ? '/information/history' : '/information/headline'} style={{ display: path.includes("clicked") ? "none" : "inline" }}>{this.props.params.title == "notice" ? "通知公告" : this.props.params.title == 'history' ? '人文历史' : "长江要闻"}</Link>
					<span style={{ display: path.includes("clicked") ? "none" : "inline", margin: "0 6px" }}>></span>
					<span>正文</span>
				</div>
				<div className="con">
					<div className="left l_panel">
						<div style={{textAlign:"center",width:"100%"}}><h2>{detail && detail.title}</h2></div>
						<div className="sub_title">{detail && detail.createdAt}<span>来源：{detail && detail.source?detail.source : "未知"}</span>作者：{detail && detail.author?detail.author:"佚名"}</div>
						<div className="article">
							<div dangerouslySetInnerHTML={{ __html: detail && detail.body }}></div>
						</div>
						<div className="bot clearfix">
							<span className="left">标签：{detail && detail.parentMenuName || detail.menuName?((detail.parentMenuName?detail.parentMenuName:"") + (detail.menuName?`，${detail.menuName}`:"")):"暂无标签"}</span>
						</div>
					</div>
					<div className="right right_panel">
						<RightPart type={this.props.params.title} />
					</div>
				</div>
			</div>
		)
	}
}

export default Detail;
