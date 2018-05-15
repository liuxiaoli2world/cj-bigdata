import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon,Pagination,Breadcrumb,Input,Button} from 'antd';
import Wrapper from './Wrapper.js';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post} from 'util/request.js';

export default class HumanityBody extends React.Component{
	constructor(props){
		super(props);
		this.state={
			article:{},
			rankingList:[],
			html: '我想让它换行显示<br />,我想让它换行显示<br />',
			size:'middle'    //字体大小
		}
	}

	componentDidMount(){
		const contentId = this.props.params.id;
		get('book/content/selectByPvRank?contentType=history')
		.then(res=>{
			this.setState({rankingList:res.data});
		})
		get(`book/content/query/${contentId}`)
		.then(res=>{
			this.setState({article:res.data});
		})
		
	}

	componentDidUpdate(){
		
	}
	//字体大
	toBig=()=>{
		this.setState({size:'big'})
	}
	//字体中
	toMiddle=()=>{
		this.setState({size:'middle'})
	}
	//字体小
	toSmall=()=>{
		this.setState({size:'small'})
	}

	render(){
		let path = hashHistory.getCurrentLocation().pathname;
		const {article,rankingList} = this.state
		return(
			<Wrapper>
				<div className="crumb">
					<img src={require('assets/images/crumb.png')}/>
					<p>当前位置：</p>
					<Breadcrumb separator=">">
					    <Breadcrumb.Item><a href="">首页</a></Breadcrumb.Item>
					    <Breadcrumb.Item><a href="">人文历史</a></Breadcrumb.Item>
					    <Breadcrumb.Item><a href="">正文</a></Breadcrumb.Item>
					</Breadcrumb>
				</div>
				
				<div className="article">
				   <div className="article_header">
				   		<h1>{article.title}</h1>
				   		<p>
				   		{article.createdAt}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   		来源：{article.source}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   		作者：{article.author}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   		</p>
				   		{/*<p>
					   		字体： 
							   		<a className={(this.state.size=='small')?'blue':''} onClick={this.toSmall}>小</a> &nbsp;&nbsp;&nbsp;&nbsp;  
							   		<a className={(this.state.size=='middle')?'blue':''} onClick={this.toMiddle}>中</a> &nbsp;&nbsp;&nbsp;&nbsp;
							   		<a className={(this.state.size=='big')?'blue':''} onClick={this.toBig}>大</a> 
				   		</p>*/}
				   </div>
				   {/*<p style={{'display':(this.state.size=='middle')?'block':'none'}} className="article_body"> {article.body} </p>
				   <p style={{'display':(this.state.size=='small')?'block':'none'}} className="article_body_small"> {article.body} </p>
				   <p style={{'display':(this.state.size=='big')?'block':'none'}} className="article_body_big"> {article.body} </p>*/}
				   <div className="article_body">
					   {article.body}
					</div>				   	 
				   
				   <div className="article_footer">
				   		<p>来源：{article.sourceAll}</p>
				   		<p>责任编辑：{article.editor}</p>
				   </div>
				</div>
				<div className="ranking">
					<div><Input placeholder="搜索新闻"/><Button>搜索</Button></div>
					<div className="ranking_list">
						<div className="ranking_head">点击排行</div>
						<ul className="ranking_content">
						{
							rankingList && rankingList.map((item,i)=>{
								return(
									<li className="ranking_content_list" key={i}><div className="square">{++i}</div><Link to={'humanityBody/'+i}>{item.title}</Link></li>
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
									<li className="reading_recommend_list" key={i}><div className="gray_square"/><Link to={'humanityBody/'+i}>{item.content}</Link></li>
								)
								
							})
						}
						</ul>
					</div>
				</div>
				<div className="clear"></div>
			</Wrapper>
		)
	}
}