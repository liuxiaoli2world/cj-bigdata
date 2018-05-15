import React from 'react';
import { Link, hashHistory } from 'react-router';
import { Icon } from 'antd';
import './index.scss';
import { get, post } from 'util/request.js';
import TitleBar from './TitleBar.js';

export default class Experts extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			articles: [],
			experts: []
		};
	}

	componentWillMount() {
		get('expert/expert/selectRecommendExpertAndComposition')
			//get('expert.json')
			.then((jsonData) => {
				if (jsonData.meta && jsonData.meta.success) {
					const data = jsonData.data || {};
					this.setState({
						id: data.expertId,
						name: data.realName,
						imageUrl: data.imageUrl,
						content: data.expertDesc,
						research: data.professionalField,
						articles: data.expertComposition && data.expertComposition.slice(0, 5) || []
					});
				}
			});
		post(`expert/expert/selectByClassifyAndRealName?pageNum=1&pageSize=12&expertClassify=${encodeURI('专家沙龙')}`)
		// get('expert/expert/queryAll?pageNum=1&pageSize=12')
			//get('experts.json')
			.then((jsonData) => {
				if (jsonData.meta && jsonData.meta.success) {
					this.setState({
						experts: jsonData.data.list || []
					});
				}
			});
	}

	render() {
		return (
			<div className='experts over-flow'>
				<span className='recommend over-flow'>
					<TitleBar path='/expert/list' imageUrl={require('assets/images/recomend.png')} name='专家推荐' />
					<div className='line1'>
						<img className='image label-name' src={this.state.imageUrl} />
						<span className='content label-content'>
							<div className='position'>{this.state.name}</div>
							<span className='name'>
								{
									this.state.id ? <Link to={`/expert/detail/${this.state.id}`} >{this.state.name}</Link>
										: null
								}
							</span>
							<span className='content'>{this.state.content&&this.state.content.length<50?this.state.content:this.state.content&&this.state.content.substr(0,50)+'...'}</span><span className='detail'>
								{
									this.state.id ? <Link to={`/expert/detail/${this.state.id}`} >「详情」</Link>
										: null
								}
							</span>
						</span>
					</div>
					<div className='line2'>
						<span className='label-name'>研究方向：</span>
						<span className='label-content font16'>{this.state.research}</span>
					</div>
					<div className='line3'>
						<span className='label-name'>论述集纳：</span>
						<span className='label-content list'>
							{
								this.state.articles.map((item, index) => (
									<div key={index} className='article'><Link to={`/classify/1/journal/${item.compositionLink}`}><img className='article-pre' src={require('assets/images/dot.png')}/>{item.compositionName && item.compositionName.length < 20 ? item.compositionName : item.compositionName && item.compositionName.substr(0, 20) + '...'}</Link></div>
								))
							}
						</span>
					</div>
				</span>
				<span className='salon over-flow'>
					<TitleBar path='/expert/list' imageUrl={require('assets/images/salon.png')} name='专家沙龙' />
					{
						this.state.experts.map((item, index) => (
							<div key={index} className='expert-item'>
								<span className='expert-name'><Link to={`/expert/detail/${item.expertId}`} >{item.realName}</Link></span>
								<span className='expert-desc'><Link to={item.expertComposition.length>0 ? `/classify/1/journal/${item.expertComposition[0].compositionLink}`:''} >{item.expertComposition.length>0 ? item.expertComposition[0].compositionName :'暂无著作'}</Link></span>
							</div>
						))
					}
				</span>
			</div>
		)
	}
}