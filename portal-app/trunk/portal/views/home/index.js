import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon,Affix} from 'antd';
import './index.scss';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post} from 'util/request.js';
import TitleBar from './TitleBar';
import Blogroll from '../blogroll/blogroll.js';
import LeftNav from './leftNav.js';
import HotWords from './hotWords.js';
import WatchingFocus from './watchingFocus.js';
import SuggestionBox from './suggestionBox.js';
import News from './news.js';
import Special from './special.js';
import History from './history.js';
import Experts from './experts.js';

export default class Home extends React.Component{
	constructor(props){
		super(props);
		this.state = {
			show: false
		};
	}

	componentDidMount(){
		window.scrollTo(0,0);
		$(window).trigger('resize');
	}

	componentDidUpdate(){
		window.dispatchEvent(resizeEvent);
	}

	togglePanel() {
		this.setState({
			show: !this.state.show
		});
	}

	render(){
		let path = hashHistory.getCurrentLocation().pathname;
		let display = this.state.show ? 'inline-block' : 'none';
		return(
			<div className='home-content over-flow'>
				<div className='column1 over-flow'>
					<LeftNav />
					<WatchingFocus />
					<HotWords />
					<div className='left-image1'></div>
					<div className='left-image2'></div>
					<SuggestionBox />
				</div>
				<div className='column2 over-flow'>
					<News />
					<Special />
					<div className='middle-logo'></div>
					<History />
					<Experts />
				</div>
				<div className='bottom-logo'></div>			
				<Blogroll />
				<Affix style={{ position: 'fixed', top: '50%',marginTop:-61, right: '0'}}>
					<span onClick={this.togglePanel.bind(this)} style={{'cursor':'pointer',float:'left',padding:'20px 10px 0 10px',borderRadius:'8px 0 0 8px',width:'30px',height:'123px',background:'#efefef',color:'#3d95d5',fontSize:'14px'}} className='affix-title'>
						在线客服
					</span>
					<span style={{display:display,width:'150px',height:'123px',background:'#3d95d5',textAlign:'center'}} className='affix-content'>
						<div style={{color:'white',fontSize:'12px',lineHeight:'45px',height:'45px'}}>在线客服</div>
						<div style={{textAlign:'left',background:'white',borderRadius:'8px',width: '120px',height: '35px',margin: 'auto', backgroundColor: '#fff',border: '1px solid #ddd',color: '#0093dd',lineHeight: '35px',cursor: 'pointer'}}>
							<img src={require('assets/images/online.png')} style={{float:'left',margin:'8px 10px',width:'20px',height:'20px'}}/>
							<a style={{color:'#3d95d5','display':'inline-block'}} target='_blank' href='http://wpa.qq.com/msgrd?v=3&uin=3077937385&site=qq&menu=yes'>在线客服</a>
						</div>
						<div style={{clear:'both',marginBottom:'20px',color:'white',fontSize:'12px',lineHeight:'45px',height:'45px'}}>在线时间:8:30-17:30</div>
					</span>
				</Affix>
			</div>
		)
	}
}