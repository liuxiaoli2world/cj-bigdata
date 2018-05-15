import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon,Pagination,Breadcrumb} from 'antd';
import Wrapper from './Wrapper.js';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post} from 'util/request.js';

export default class Regard extends React.Component{
	constructor(props){
		super(props);
		this.state={
		   
		}
	}

	componentDidMount(){
		
	}

	componentDidUpdate(){
		
	}
	render(){
		return(
			<Wrapper>
				<div className="crumb">
					<img src={require('assets/images/crumb.png')}/>
					<p>当前位置：</p>
					<Breadcrumb separator=">">
					    <Breadcrumb.Item><a href="">首页</a></Breadcrumb.Item>
					    <Breadcrumb.Item>
                            <a href="">
                                {location.hash.includes('respect')?'关于我们':'广告合作'}
                            </a>
                        </Breadcrumb.Item>
					</Breadcrumb>
				</div>
                <div className="regard_head">
                    <img src={require('assets/images/icon8.png')}/>
                    <span>{location.hash.includes('respect')?'关于我们':'广告合作'}</span>
                </div>
                <div className="regard_content">{this.props.children}</div> 

	            
				<div className="clear"></div>
			</Wrapper>
		)
	}
}