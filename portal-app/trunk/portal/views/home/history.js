import React from 'react';
import { Link, hashHistory } from 'react-router';
import { Tabs, Icon, Modal } from 'antd';
import './index.scss';
import { get, post } from 'util/request.js';
import TitleBar from './TitleBar.js';
const TabPane = Tabs.TabPane;

export default class History extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			list: [],
			visible: false,
			zoom: 100
		};
	}

	componentWillMount() {
		get('book/content/selectAllHistory')
			.then((jsonData) => {
				this.setState({
					list: jsonData.data || []
				});
			});
	}

	showModal = () => {
		this.setState({
			visible: true
		});
		document.body.style.overflow='hidden';
		document.body.style.height='100%';
		document.documentElement.style.overflow='hidden'
	}

	handleCancel = () => {
		this.setState({
			visible: false
		});
		document.body.style.overflow='auto';
		document.body.style.height='100%';
		document.documentElement.style.overflow='auto'
	}

	bigimg = (event) => {
		let {zoom} = this.state;
		// var newColor = (parseInt(this.state.background.substr(1), 16) + 
		if(event.deltaY<0){
			zoom+=5
		}else if(event.deltaY>0&&zoom>=50){
			zoom-=5
		}
		this.setState({
			zoom
		})
		// newColor = '#' + newColor.substr(newColor.length-6).toUpperCase();
		// this.setState({background: newColor});
	}

	render() {
		const { zoom, visible } = this.state;
		const width = 9 * zoom;
		const height = 4.06 * zoom;
		const marHeight = (document.body.clientHeight - height)/2;
		
 		return (
			<div className='history over-flow clearfix'>
				<img className='image left' src={require('assets/images/map.jpg')} onClick={this.showModal}></img>
				<span className='content left'>
					<TitleBar path='information/history' imageUrl={require('assets/images/iconfont-history.png')} name='人文历史/长江流域' />
					<Tabs
						defaultActiveKey='1'
						tabPosition='top'
					>
						{
							this.state.list.map((item, index) => (
								<TabPane tab={item.title} key={index + 1}>
									<div className='history-img' style={{backgroundImage: `url(${item.imageUrl})`,backgroundSize: '243px 48px' }}/>
									<div className='history-title'>{item.title}</div>
									<div className='history-desc'>{item.contentDesc && item.contentDesc.length < 44 ? item.contentDesc : item.contentDesc && item.contentDesc.substr(0, 44) + '...'}<Link to={`information/history/clicked/detail/${item.contentId}`}><span className='detail'>「详情」</span></Link></div>
								</TabPane>
							))
						}
					</Tabs>
				</span>
				{
					visible ? 
					
					<div className="mask" onClick={this.handleCancel} onWheel={this.bigimg}>
						<img style={{ width: `${width}px`, height: `${height}px`, marginTop: `${marHeight}px`}} src={require('assets/images/map1.jpg')} onWheel={this.bigimg}/>
					</div>

					: null

				}
				
			</div>
		)
	}
}