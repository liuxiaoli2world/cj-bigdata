import React from 'react';
import './index.scss';
import {Link,hashHistory} from 'react-router';

export default class TitleBar extends React.Component{
	constructor(props){
		super(props);
	}

	render() {
		const path = hashHistory.getCurrentLocation().pathname;

		var moreState = false,moreState1 = true;
		if(path.indexOf("expert/list") >= 0){
			moreState = true;
		}
		if(this.props.path.indexOf("special") >= 0){
			moreState = true;
			moreState1 = false;
		}

		return (
			<div style={{position:'relative','fontSize':'16px','color':'#3d95d5',}} className="title">
				<Link style={{float:'right',display: moreState ?"none":"block"}} to={{pathname:this.props.path}}><span style={{fontSize:'12px','color':'#999999'}}>更多>></span></Link>
			    <a style={{color:'red',float:'right',display:  moreState1 ?"none":"block"}} href={this.props.path}><span style={{fontSize:'12px','color':'#999999'}}>更多>></span></a>
				<div style={{fontWeight:"bold"}} className="main-title">
					<img style={{verticalAlign:'top'}} src={this.props.imageUrl} />
					<span style={{marginLeft:'8px'}}>{this.props.name}</span>
				</div>
				<div style={{borderTop:'1px solid #3d95d5'}} className="line"></div>
			</div>
		)
	}
}
