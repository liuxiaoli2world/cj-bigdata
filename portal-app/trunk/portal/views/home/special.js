import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon} from 'antd';
import './index.scss';
import {get,post} from 'util/request.js';
import TitleBar from './TitleBar.js';
import $ from "jquery";

export default class Special extends React.Component{
	constructor(props) {
		super(props);
		const userId = window.localStorage.getItem('userId') || '';
		const name = window.localStorage.getItem('userName')||'';
		this.state = {
			special1: [],
			special2: [],
			userId : userId,
			name : name
		};
	}
	
	getSpecialList(type,pageSize){
		let that = this;
		$.ajax({
			type : "POST",
			url: 'http://www.cjpress.com.cn:8181/remote/ServicesZt.asmx/GetNewsList',
			dataType: 'xml',
			data : {
				"pageIndex" : 1,
				"pageSize" : pageSize,
				"type" : type
			},
			success: function(data){
				var arr = [];
				var arr1 = [];
				$(data).find("ArrayOfNews").find("News").each(function(index,ele){
					var title = $(ele).find("Title").text();
					var createTime = $(ele).find("CreateTime").text();
					var picUrl = $(ele).find("PicUrl").text();
					var linkUrl = $(ele).find("LinkUrl").text();
					var des = $(ele).find("Desc").text();
					if(type == -1){
						if(des.length){
							that.setState({
								playSpecital : {
									imgUrl : picUrl || '',
									title : title || '',
									linkUrl : linkUrl+'&uid='+that.state.userId+'&name='+that.state.name || '',
									des : des || ''
								}
							})
						}else{
							arr.push({
								title : title || '',
								date : createTime || '',
								linkUrl : linkUrl+'&uid='+that.state.userId+'&name='+that.state.name || '',
							})
						}
					}else{
						arr1.push({
							title : title || '',
							date : createTime || '',
							linkUrl : linkUrl+'&uid='+that.state.userId+'&name='+that.state.name || '',
							imgUrl : picUrl || '',
							des : des || ''
						})
					}
					
				})
				if(type == -1){
					that.setState({ special1 : arr || []})
				}else{
					that.setState({
						special2 : arr1 || [],
						special3 : arr1.slice(2,9) || []
					})
				}
			},
			error: function(e){
				console.log(e)
			}
		 });
	}

	componentWillMount() {
		this.getSpecialList(-1,8);
		this.getSpecialList(-2,9);
	}

	render(){
		const {playSpecital,special3,special1,special2} = this.state;
		const cxSpecial = 'http://10.6.100.91:8686/Index.aspx?action=special&uid='+this.state.userId+'&name='+this.state.name;
		const sxSpecial = 'http://10.6.100.91:8686/sanxia.aspx?action=special&uid='+this.state.userId+'&name='+this.state.name;
		return(
			<div className='special over-flow'>
				<span className='special1 left'>
					<TitleBar path={cxSpecial} imageUrl='assets/images/iconfont-special1.png' name='长江三峡工程专题'/>
					<div className="row1">
						<div className="imgwrap"><a href={`${playSpecital && playSpecital.linkUrl}`}  target="_blank"><img src={playSpecital &&　playSpecital.imgUrl} title="专题图片"/></a></div>
						<span className='special1-head'>
							<div className='special1-title'><a href={`${playSpecital && playSpecital.linkUrl}`}  target="_blank">{playSpecital && playSpecital.title}</a></div>
							<div className='special1-desc'>{playSpecital && playSpecital.des && playSpecital.des.length<48?playSpecital && playSpecital.des:playSpecital && playSpecital.des.substr(0,48)+'...'}<a href={`${playSpecital && playSpecital.linkUrl}`}  target="_blank" style={{fontSize:12}}>[详情]</a></div>
						</span>
					</div>
					<div className="row2">
						{
							special1 && special1.map((item, index)=> (
								<span key={index} className='special-item' style={{display:'inline-block'}}>
									<span className='special-img'></span>
									<a href={`${item.linkUrl}`} target="_blank"><span className='special-desc'>{item.title &&item.title.length<24?item.title:item.title && item.title.substr(0,24)+'...'}</span></a>
									<span className='special-date'>{item.date}</span>
								</span>
							))
						}
						
					</div>
				</span>
				<span className='special2 left'>
					<TitleBar path={sxSpecial} imageUrl='assets/images/iconfont-special2.png' name='长江防洪抗旱减灾专题'/>
					<div className="row1 clearfix">						
						<div className='special2-head'>
							<div className="imgwrap"><a href={special2[0] && special2[0].linkUrl} target="_blank"><img src={special2[0] && special2[0].imgUrl} title="专题图片"/></a></div>
							<a href={special2[0] && special2[0].linkUrl} target="_blank"><span className='special2-title'>{special2[0] && special2[0].title && special2[0] && special2[0].title.length>13?special2[0] && special2[0].title.substr(0,13)+'...':special2[0] && special2[0].title}</span></a>
						</div>
						<div className='special2-head'>
							<div className="imgwrap"><a href={special2[1] && special2[1].linkUrl} target="_blank"><img src={special2[1] && special2[1].imgUrl} title="专题图片"/></a></div>
							<a href={special2[1] && special2[1].linkUrl} target="_blank"><span className='special2-title'>{special2[1] && special2[1].title&&special2[1] && special2[1].title.length>13?special2[1] && special2[1].title.substr(0,13)+'...':special2[1] && special2[1].title}</span></a>
						</div>
					</div>
					<div className="row2">
						{
							special3 && special3.map((item, index)=> (
								<span key={index} className='special-item'>
									<a href={`${item.linkUrl}`} target="_blank"><span>{item.title && item.title.length<18?item.title:item.title&&item.title.substr(0,16)+'...'}</span></a>
								</span>
							))
						}
					</div>
				</span>
			</div>
		)
	}
	// render() {
	// 	return (
	// 		<div className='special over-flow'>
	// 			专题
	// 		</div>
	// 	);
	// }
}