import React from 'react';
import { Link, hashHistory } from 'react-router';
import { Icon } from 'antd';
import './index.scss';
import { get, post } from 'util/request.js';

export default class WatchingFocus extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			title: '',
			desc: '',
			imagUrl: ''
		};
	}

	componentWillMount() {
		get('book/multifile/queryIndexVideo')
			.then((jsonData) => {
				this.setState({
					...jsonData.data
				});
			});
	}
	//  /

	render() {
		return (
			<div className='watching-focus'>
				<div className='title'>
					<img src={require('assets/images/iconfont-watching.png')} />
					<label className='title-label'>视频/看点</label>
				</div>
				<div className='sep'></div>
				{
					this.state.multifileId ?
						<Link to={{ pathname: '/videoDetail', state: { id: this.state.multifileId, imageUrl: this.state.path } }}>
							<div className='content'>
								<img src={this.state.path} />
								<label>{this.state.title}</label>
							</div>
							<div className='desc'>{this.state.multiDesc && this.state.multiDesc.length < 18 ? this.state.multiDesc : this.state.multiDesc && this.state.multiDesc.substr(0, 18) + '...'}</div>
						</Link>
						:
						<div>
							<div className='content'>
								<img src={this.state.path} />
								<label>{this.state.title}</label>
							</div>
							<div className='desc'>{this.state.multiDesc && this.state.multiDesc.length < 18 ? this.state.multiDesc : this.state.multiDesc && this.state.multiDesc.substr(0, 18) + '...'}</div>
						</div>
				}

			</div>
		)
	}
}