import React from 'react';
import { Link, hashHistory } from 'react-router';
import { Icon } from 'antd';
import './index.scss';
import { get, post } from 'util/request.js';
let zrender = require('assets/zrender-2.1.0/src/zrender.js');
let TextShape = require('assets/zrender-2.1.0/src/shape/Text.js');
export default class HotWords extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			zr: null
		};
	}

	componentDidMount() {
		var zr = zrender.init(document.getElementById('zrender-content'));
		var width = Math.ceil(zr.getWidth());
		var height = Math.ceil(zr.getHeight());

		this.state.zr = zr;
		this.state.width = width;
		this.state.height = height;
		this.refreshUI();
	}

	refreshUI() {
		const zr = this.state.zr;
		const width = this.state.width;
		const height = this.state.height;
		const textFonts = this.state.textFonts;
		zr.clear();
		var colorIdx = 0;

		get('book/hotword/selectRandom')
			.then((jsonData) => {
				const wordObjList = jsonData.data;
				let words = [];
				for (let i = 0, len = wordObjList.length; i < len; i++) {
					words.push(wordObjList[i].name);
				}

				let sourceTextFonts = ['normal 12px 微软雅黑', 'normal 14px 微软雅黑', 'normal 12px 微软雅黑', 'normal 20px 微软雅黑', 'normal 14px 微软雅黑', 'normal 18px 微软雅黑', 'normal 16px 微软雅黑', 'normal 18px 微软雅黑'];
				let sourceColors = ['#db6da3', '#f39800', '#713318', '#5f52a0', '#96d35f', '#1bbc9b', '#0068b7', '#3fbfee']

				let colors = this.getRandomArray(sourceColors);
				let textFonts = this.getRandomArray(sourceTextFonts);
				let rotations = [10, 20, 20, 15, 18, 24, 18, 11];
				for (let i = 0; i < 8; i++) {
					zr.addShape(new TextShape({
						style: {
							// x: 40,
							x:(Math.random()+1)*50,
							y: 2 + (i * 22),
							brushType: 'fill',
							color: colors[i],
							text: words[i],
							textFont: textFonts[i],
							textAlign: 'center',
							textBaseline: 'top'
						},
						highlightStyle: {
							brushType: 'fill',
							color: 'blue'
						},
						draggable: false,
						clickable: true,
						//rotation: [rotations[i]+40],

						// 可自带任何有效自定义属性
						_name: words[i],
						onclick: function (params) {
							hashHistory.push(`searchModule/desc/${params.target._name}`);
						},
					}));
				}

				// 绘画
				zr.render();
			});
	}

	getRandomArray = (source) => {
		let results = [];
		let f = function (colorArray) {
			const length = colorArray.length;
			const index = Math.floor(Math.random() * length);
			let color = colorArray.splice(index, 1);
			results.push(color);
			if (colorArray.length > 0) {
				f(colorArray);
			}
		}
		f(source);
		return results;
	}

	render() {
		return (
			<div className='hot-words'>
				<div className='title'>
					<img src={require('assets/images/hotwords.png')} />
					<span className='title-label'>热词</span>
					<img className='refresh' src={require('assets/images/refresh.png')} onClick={this.refreshUI.bind(this)} />
				</div>
				<div className='sep'></div>
				<div id='zrender-content' className='content'>待完成</div>
			</div>
		)
	}
}