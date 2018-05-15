import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon,Pagination,Breadcrumb} from 'antd';
import Wrapper from './Wrapper.js';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post} from 'util/request.js';

export default class Humanity extends React.Component{
	constructor(props){
		super(props);
		this.state={
			humanityList:[],
			rankingList:[]
		}
	}

	componentDidMount(){
		get('book/content/queryAll?pageNum=1&pageSize=10&flag=history').then(res=>{
			this.setState({
				humanityList: res.data && res.data.list || []
			});
		});
		get('book/content/selectByPvRank?contentType=history')
		.then(res=>{
			this.setState({rankingList:res.data});
		});
		
	}

	componentDidUpdate(){
		
	}
	onChange=(pageNumber)=> {
	}

	render(){
		let path = hashHistory.getCurrentLocation().pathname;
		const {humanityList,rankingList} = this.state
		return(
			<Wrapper>
				<div className="crumb">
					<img src={require('assets/images/crumb.png')}/>
					<p>当前位置：</p>
					<Breadcrumb separator=">">
					    <Breadcrumb.Item><a href="">首页</a></Breadcrumb.Item>
					    <Breadcrumb.Item><a href="">人文历史</a></Breadcrumb.Item>
					</Breadcrumb>
				</div>
				
				<ul className="humanity">
				{
					humanityList && humanityList.map((item,i)=>{
						return(
							<li className="humanity_list" key={i}>
								<img className='' src={item.imageUrl}/>
								<span className="humanity_list_container">
									<div className="humanity_list_name">{item.title}</div>
									<div className="humanity_list_content">{item.body&&item.body.length<50?item.body:item.body&&item.body.substr(0,50)+'...'}</div>
									<div className="humanity_list_date">{item.publicationDate}</div>
								</span>
							</li>
							)
					})
				}
				</ul>
				<div className="ranking">
					<div className="ranking_list">
						<div className="ranking_head">点击排行</div>
						<ul className="ranking_content">
						{
							rankingList && rankingList.map((item,i)=>{
								return(
									<li className="ranking_content_list" key={i}><div className="square">{++i}</div><Link to={'humanityBody/'+item.contentId}>{item.title}</Link></li>
								)
								
							})
						}
						</ul>
					</div>
					<div className="ranking_list">
						<div className="ranking_head">推荐阅读</div>
						<ul className="ranking_content">
						{
							rankingList && rankingList.map((item,i)=>{
								return(
									<li className="reading_recommend_list" key={i}><div className="gray_square"/><Link to={'humanityBody/'+item.contentId}>{item.content}</Link></li>
								)
								
							})
						}
						</ul>
					</div>
				</div>
				<div className="pagenation">
					<Pagination showQuickJumper defaultPageSize={6} defaultCurrent={1} total={60} onChange={this.onChange} />
				</div>
				<div className="clear"></div>
			</Wrapper>
		)
	}
}