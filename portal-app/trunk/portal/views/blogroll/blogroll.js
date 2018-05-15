import React from 'react';
import config from 'config';
import { Link } from 'react-router';
import Header from '../header/header.js';
import { get, post } from '../util/request';
import './blogroll.scss';

class Blogroll extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			links: [
				{
					'friendlyLinkId': 20,
					'name': '水利部长江水利委员会',
					'url': 'http://www.cjw.gov.cn/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 23,
					'name': '长江流域水资源保护局',
					'url': 'http://www.ywrp.gov.cn/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 24,
					'name': '水文局',
					'url': 'http://www.cjh.com.cn/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 25,
					'name': '长江科学院',
					'url': 'http://www.crsri.cn/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 26,
					'name': '水利部中国科学院水工程生态研究所',
					'url': 'http://www.ihe.ac.cn/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 27,
					'name': '陆水试验枢纽管理局',
					'url': 'http://www.lshui.com/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 28,
					'name': '长江水利水电开发总公司',
					'url': 'http://www.crhdc.com.cn/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 29,
					'name': '长江工程建设局',
					'url': 'http://jsj.crhdc.com.cn/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 30,
					'name': '综合管理中心',
					'url': 'http://www.cjwzgzx.com/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 31,
					'name': '长江医院（血吸虫病防治监测中心）',
					'url': 'http://www.cjwcjyy.com/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 32,
					'name': '长江流域水土保持监测中心站',
					'url': 'http://www.cjstbcjc.org/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 33,
					'name': '长江勘测设计规划研究院',
					'url': 'http://www.cjwsjy.com.cn/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 34,
					'name': '汉江集团（丹江口水利枢纽管理局）',
					'url': 'http://www.hjgrp.com/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				},
				{
					'friendlyLinkId': 36,
					'name': '南水北调中线水源有限公司',
					'url': 'http://www.zxsygs.com/',
					'createdBy': null,
					'createdAt': null,
					'updatedBy': null,
					'updatedAt': null
				}
			]
		}
	}
	componentDidMount() {
		// get(`site/pressfriendlylink/selectAllPressFriendlyLink`)
		// 	.then((data) => {
		// 		if(data.meta.success){
		// 			this.setState({
		// 				links:data.data
		// 			})
		// 		}
		// 	})
	}
	render() {
		const links = this.state.links;
		const len = links.length - 1;
		return (
			<div>
				<div id='blogroll_main'>
					<div className='content'>
						<fieldset>
							<legend style={{margin:'0 auto',textAlign:"center"}}>友情链接</legend>
							<div className='links'>
								{
									links.map((link, i) => {
										return (
											<span key={i}><span><a href={link.url} title={link.name}>{link.name}</a></span><span className='line'>{i !== len ? '|' : ''}</span></span>
										)
									})
								}
							</div>
						</fieldset>
					</div>
				</div>
			</div>
		)
	}
}

export default Blogroll;
