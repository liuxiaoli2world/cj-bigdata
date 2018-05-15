import React from "react";
import ReactDOM from "react-dom";
import { Link, hashHistory } from "react-router";
import { Input, Button } from "antd";
import { get, post } from "util/request.js";
import {
	SubEmiter,
	Emiter
} from 'util';

class RightPart extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			clickrankList: [], //点击排行
			recommendedList: [] //推荐阅读
		}
	}
	componentDidMount() {
		//查询点击排行
		get(`book/content/selectByPvRank?contentType=${this.props.type}`)
			.then(res => {
				if (res && res.data) {
					this.setState({
						clickrankList: res.data || []
					})
				}
			})
		//查询推荐阅读
		get(`book/book/queryRec`)
			.then(res => {
				if (res && res.data) {
					this.setState({
						recommendedList: res.data || []
					})
				}
			})
	}

	onClickEvent = (id, e) => {
		let path = hashHistory.getCurrentLocation().pathname;
		if (path.includes('detail')) {
			Emiter.emit('clickChange', id);
		}
	}

	onInputChange = (event) => {
		this.setState({
			searchword: event.target.value
		});
	}
	handleSeachInfo = (e) => {
		let path = hashHistory.getCurrentLocation().pathname;
		if(path.indexOf("detail")>=0){
			hashHistory.push({
				pathname : `/information/${this.props.type}`,
				query: {
					key : this.state.searchword
				}
			})
		}else{
			Emiter.emit('searchwordChange', this.state.searchword);
		}
	}

	searchPressEnter(e){
		if(e.target.className.indexOf("rightInput")>=0){
			this.handleSeachInfo();
		}
	}

	render() {
		let { clickrankList, recommendedList } = this.state;
		return (
			<div>
				<div className="search clearfix">
					<Input key={1} placeholder="搜索新闻..." value={this.state.searchword} onChange={this.onInputChange} onPressEnter={this.searchPressEnter.bind(this)} className="rightInput"/>
					<Button onClick={this.handleSeachInfo.bind(this)}>搜索</Button>
				</div>
				<div className="click_rank">
					<h3>点击排行</h3>
					<ul>
						{
							clickrankList && clickrankList.map((item, i) => {
								return (
									<li className="clearfix" key={i}><Link to={`information/${this.props.type}/clicked/detail/${item.contentId}`}><span>{i + 1}</span><i onClick={this.onClickEvent.bind(this, item.contentId)}>{item.title}</i></Link></li>
								)
							})
						}
					</ul>
				</div>
				<div className="recommended_reading">
					<h3>推荐阅读</h3>
					<ul>
						{
							recommendedList && recommendedList.map((item, i) => {
								return (
									<li className="clearfix" key={i}><Link to={`classify/1/book/${item.bookId}`}>{item && 　item.bookName}</Link></li>
								)
							})
						}
					</ul>
				</div>
			</div>
		)
	}
}

export default RightPart;