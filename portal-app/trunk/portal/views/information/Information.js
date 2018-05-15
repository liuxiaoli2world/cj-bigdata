import React from "react";
import ReactDOM from "react-dom";
import { Link, hashHistory } from "react-router";
import { Pagination } from "antd";
import RightPart from "./RightPart.js";
import "./information.scss";
import { get, post } from "util/request.js";
import {
	SubEmiter,
	Emiter
} from 'util';

class Information extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			total: 1,
			pageSize: 6,//每页条数
			infoList: [],
			current : 1,
		}
		
	}
	//获取列表
	getInfoList(pagenum) {
		if(this.props.location.search){
			let keyword = this.props.location.search.split("=")[1];
			get(`book/content/selectNewsByCondition?pageNum=1&pageSize=${this.state.pageSize}&contentType=${this.props.params.title}${keyword?`&condition=${keyword}`:''}`)
			.then(res => {
				if (res && res.data && res.data.list) {
					this.setState({
						total: res.data.total,
						infoList: res.data.list || [],
						current :pagenum,
					})
				}
			})
		}else{
			get(`book/content/queryAll?pageNum=${pagenum}&pageSize=${this.state.pageSize}&flag=${this.props.params.title}`)
			.then(res => {
				if (res && res.data && res.data.list) {
					this.setState({
						total: res.data.total,
						infoList: res.data.list || [],
						current :pagenum,
					})
				}
			})
		}
	}

	componentDidMount() {
		this.getInfoList(1);
	}

	//页码切换
	pageChange = (page, pageSize) => {
		this.getInfoList(page);
	}
	
	onSearchwordChange = (param) => {
		get(`book/content/selectNewsByCondition?pageNum=1&pageSize=${this.state.pageSize}&contentType=${this.props.params.title}${param?`&condition=${encodeURI(param)}`:''}`)
			.then(res => {
				if (res && res.data && res.data.list) {
					this.setState({
						total: res.data.total,
						infoList: res.data.list || [],
						current : 1,
					})
				}
			})
	}

	render() {
		let { infoList } = this.state;
		const title = this.props.params.title;
		let titleName;
		switch (title) {
			case 'notice':
				titleName = '通知公告';
				break;
			case 'history':
				titleName = '人文历史';
				break;
			case 'headline':
				titleName = '长江要闻';
				break;
		}
		return (
			<section id="information_main">
				<SubEmiter eventName="searchwordChange" listener={this.onSearchwordChange.bind(this)}></SubEmiter>
				<div className="content">
					<div className="crumb">
						<img src={require("assets/images/crumb.png")} alt="面包屑指针" />
						当前位置：
						<Link to="/">首页</Link>
						<span style={{ margin: "0 6px" }}>></span>
						<span>{titleName}</span>
					</div>
					<div className="con clearfix">
						{
							infoList && infoList.length==0 ?
							<div className="left left_panel" style={{marginBottom:30}}><img src={require("assets/images/nocontent.png")} alt="暂无内容" style={{marginLeft:310,marginTop:50}}/></div>
							:
							<div className="left left_panel">
								<ul className="list">
									{/*通知公告/长江要闻列表*/}
									{
										infoList && infoList.map((item, i) => {
											return (
												<li key={i} className="clearfix" key={i}>
													<div className="left notice_img">
														<Link to={`information/${title}/detail/${item.contentId}`}><img src={item.imageUrl?item.imageUrl:require("assets/images/no-img.png")} alt="通知公告" /></Link>
													</div>
													<div className="left">
														<h3><Link to={`information/${title}/detail/${item.contentId}`}>{item.title}</Link></h3>
														<span>{item.createdAt}</span>
													</div>
												</li>
											)
										})
									}
								</ul>
								<Pagination total={this.state.total} onChange={this.pageChange} defaultCurrent={1} showQuickJumper pageSize={this.state.pageSize} current={this.state.current}/>
							</div>
						}
						<div className="right right_panel">
							<RightPart type={this.props.params.title} />
						</div>
					</div>
				</div>
			</section>
		)
	}
}

export default Information;