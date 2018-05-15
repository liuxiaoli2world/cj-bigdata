import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon,Pagination,Breadcrumb} from 'antd';
import Wrapper from './style.js';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post} from 'util/request.js';
import TitleBar from '../components/TitleBar.js';

export default class ExpertDetail extends React.Component{
 	    constructor(props) {
		super(props);
		this.state = {
			results: []
		};
	}
	
	componentWillMount() {
		const id = this.props.params.id;
		this.getData(id);
	}

	componentWillReceiveProps(nextprops){
		const id = nextprops.params.id;
		if(id!==this.props.params.id){
			this.getData(id);
		}
	}

	getData = (id) => {
		get(`expert/expert/selectExpertAndComposition/${id}`)
		.then((jsonData)=> {			
			if(jsonData.meta && jsonData.meta.success) {
				const data = jsonData.data || {};
				this.setState({
					id: data.expertId,
					realName: data.realName,
					birthday: data.birthday,
					nation: data.nation,
					duty: data.duty,
					expertDesc: data.expertDesc,
					professionalField: data.professionalField,
					imageUrl: data.imageUrl,
					results: data.expertComposition || []
				});
			}
		});
	}

	render(){
		return(
			<div className='expert-detail'>
				 <div style={{position:'relative',margin:'0 12px 12px 0','fontSize':'16px','color':'#336699'}}>
					<div>
						<img style={{verticalAlign:'middle'}} src={require('assets/images/recomend.png')} />
						<span style={{marginLeft:'8px'}}>专家学者</span>
					</div>
					<div style={{borderTop:'1px solid #336699',margin:'6px auto'}}></div>
				</div>
				 <div className='base-info'>
					 <img className='image' src={this.state.imageUrl} />
					 <div className='realName'>作者姓名：<span className='color'>{this.state.realName}</span></div>
					 <div className='birthday'>出生年月：{this.state.birthday||'未知'}</div>
					 <div className='nation'>民族：{this.state.nation||'未知'}</div>
					 <div className='duty'>主要成就：{this.state.duty||'暂无成就'}</div>
					 <div>专业领域：<span className='color'>{this.state.professionalField||'未知'}</span></div>
				 </div>
				 <TitleBar title='专家简介'/>

				 <div className='expertDesc'>{this.state.expertDesc}</div>
				 <TitleBar title='学术著作'/>

				 <div className='list'>
					 {
						 this.state.results.map((item, index) => (
							 <div key={index}><Link to={`/classify/1/journal/${item.compositionLink}`}>*{item.compositionName&&item.compositionName.length<50?item.compositionName:item.compositionName&&item.compositionName.substr(0,50)+'...'}</Link></div>
						 ))
					 }
				 </div>
            </div>
		)
	}
}






