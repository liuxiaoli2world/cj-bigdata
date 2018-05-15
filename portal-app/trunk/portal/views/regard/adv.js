import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon,Pagination,Breadcrumb} from 'antd';
import Wrapper from './Wrapper.js';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post} from 'util/request.js';

export default class Adv extends React.Component{
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
				<p>尊敬的用户，欢迎您关注长江大数据！</p>
                <p style={{paddingLeft:30,marginTop:20}}>您可以通过以下方式联系我们：</p>
                <p style={{marginTop:50}}>长江出版社</p>
                <ul className="adv_list">
                    <li className="adv_list_item">
                        <img src={require('views/assets/images/icon9.png')}/>
                        <span>地址：湖北省武汉市江岸区解放大道1863号长江水利委员会长江出版社</span>
                    </li>
                    <li className="adv_list_item">
                        <img src={require('views/assets/images/icon9.png')}/>
                        <span>邮编：430010</span>
                    </li>
                    <li className="adv_list_item">
                        <img src={require('views/assets/images/icon9.png')}/>
                        <span>咨询电话：027-82820210 邮箱：cjpress@163.com 联系电话：焦老师</span>
                    </li>           
                </ul>
			</Wrapper>
		)
	}
}