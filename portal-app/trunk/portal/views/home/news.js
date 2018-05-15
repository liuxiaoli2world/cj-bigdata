import React from 'react';
import { Link, hashHistory } from 'react-router';
import { Carousel, Tabs, Icon } from 'antd';
import './index.scss';
import { get, post } from 'util/request.js';
const TabPane = Tabs.TabPane;
export default class News extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			notices: [],
			news: []
		};
	}

	componentWillMount() {
		get('book/content/queryAll?pageNum=1&pageSize=6&flag=notice')
			.then((jsonData) => {
				this.setState({
					notices: jsonData.data.list || []
				});
			});

		get('book/content/queryAll?pageNum=1&pageSize=6&flag=headline')
			.then((jsonData) => {
				this.setState({
					news: jsonData.data.list || []
				});
			});
	}

	render() {
		let containinfo = {
			mainadvs: this.state.notices
		};
		let notices = this.state.notices;
		if (!notices || notices.length < 4) {
			return null;
		}
		return (
			<div className='news over-flow clearfix'>
				{/*--------------banner-------------------*/}
				<span className='images left'>
					<Carousel autoplay>
						<div className='carousel-item'>
							<Link to={`information/notice/detail/${containinfo.mainadvs && containinfo.mainadvs[0] && containinfo.mainadvs[0].contentId}`} target={'_blank'}><img src={containinfo.mainadvs && containinfo.mainadvs[0] && containinfo.mainadvs[0].imageUrl} /></Link>
							<div className='mask'>{containinfo.mainadvs[0].title}</div>
						</div>
						<div className='carousel-item'>
							<Link to={`information/notice/detail/${containinfo.mainadvs && containinfo.mainadvs[1] && containinfo.mainadvs[1].contentId}`} target={'_blank'}><img src={containinfo.mainadvs && containinfo.mainadvs[1] && containinfo.mainadvs[1].imageUrl} /></Link>
							<div className='mask'>{containinfo.mainadvs[1].title}</div>
						</div>
						<div className='carousel-item'>
							<Link to={`information/notice/detail/${containinfo.mainadvs && containinfo.mainadvs[2] && containinfo.mainadvs[2].contentId}`} target={'_blank'}><img src={containinfo.mainadvs && containinfo.mainadvs[2] && containinfo.mainadvs[2].imageUrl} /></Link>
							<div className='mask'>{containinfo.mainadvs[2].title}</div>
						</div>
						<div className='carousel-item'>
							<Link to={`information/notice/detail/${containinfo.mainadvs && containinfo.mainadvs[3] && containinfo.mainadvs[3].contentId}`} target={'_blank'}><img src={containinfo.mainadvs && containinfo.mainadvs[3] && containinfo.mainadvs[3].imageUrl} /></Link>
							<div className='mask'>{containinfo.mainadvs[3].title}</div>
						</div>
					</Carousel>
				</span>
				{/*--------------本社公告 长江要闻-------------------*/}
				<span className='news-content over-flow left'>
					<Tabs defaultActiveKey='1'>
						<TabPane tab='通知公告' key='1'>
							<ul>
								{
									this.state.notices.map(function (item, i) {
										return (<li key={i}><Link to={`information/notice/detail/${item.contentId}`} target='_blank'>{item.title && item.title.length < 16 ? item.title : item.title.substr(0, 15) + '...'}</Link></li>)
									})
								}
							</ul>
							<Link to='information/notice' className='more' target='_blank'>更多>></Link>
						</TabPane>
						<TabPane tab='长江要闻' key='2'>
							<ul>
								{
									this.state.news.map(function (item, i) {
										return (<li key={i}><Link to={`information/headline/detail/${item.contentId}`} target='_blank'>{item.title && item.title.length < 16 ? item.title : item.title.substr(0, 15) + '...'}</Link></li>)
									})
								}
							</ul>
							<Link to='information/headline' className='more' target='_blank'>更多>></Link>
						</TabPane>
					</Tabs>
				</span>
			</div>
		)
	}
}