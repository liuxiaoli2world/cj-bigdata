import React from 'react';
import { Link, hashHistory } from 'react-router';
import { Card } from 'antd';
import './index.scss';
import Wrapper from './wrapper.js';
import { get, post } from 'utils/request.js';
import { resizeEvent } from 'utils/carousel-helper';
// 引入 ECharts 主模块
var echarts = require('echarts/lib/echarts');
//var zrender = require('zrender');
// 引入柱状图
require('echarts/lib/chart/bar');
require('echarts/lib/chart/line');
// 引入提示框和标题组件
require('echarts/lib/component/tooltip');
require('echarts/lib/component/title');

class HomeCard extends React.Component {
	render() {
		return (
			<Card className="homeCard">
				<div className="left-content">
					<span>{this.props.title}</span>
					<br />
					<span style={{ fontSize: 30 }}>{this.props.count}</span>
				</div>
				<div className="right-content">
					<img src={this.props.imgUrl} />
					<p style={{ fontSize: 12 }}>{this.props.desc}</p>
				</div>
			</Card>
		)
	}
}

export default class Home extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			bookSum: '',
			totalContent: '',
			totalPV: ''
		};
	}

	componentDidMount() {
		window.scrollTo(0, 0);
		$(window).trigger('resize');
		this.requestData();
	}

	requestData = () => {
		// 一周粉丝增长量
		const req1 = get('user/user/selectUserForIndex');
		// 实时总浏览量
		const req2 = post('book/content/getTotalPv');
		// 实时文章的总数量
		const req3 = post('book/content/getTotalContent');
		// 实时书籍数量
		const req4 = get('book/book/queryBookSum');
		// 查询多媒体项目
		const req5 = get('book/multiitem/querySum');
		Promise.all([req1, req2, req3, req4, req5])
			.then(data => {
				const userWeek = data[0].data;
				const totalPV = data[1].data;
				const totalContent = data[2].data;
				const bookSum = data[3].data;
				const multiItem = data[4].data;
				this.setState({
					totalPV, totalContent, bookSum
				});
				data[4].meta.success && this.processLeftChartData(multiItem);
				data[0].meta.success && this.processRightChartData(userWeek);
			});

	}

	processLeftChartData = (data) => {
		this.drawLeftChart(data);
	}

	processRightChartData = (data) => {
		let dataX = [], dataY = [];
		for (let i = 0, len = data.length; i < len; i++) {
			const item = data[i];
			dataX.push(item.date);
			dataY.push(item.countUser);
		}
		this.drawRightChart(dataX, dataY)
	}

	drawLeftChart(data) {
		// 基于准备好的dom，初始化echarts实例
		let leftChart = echarts.init(document.getElementById('leftChart'));
		// 绘制图表
		leftChart.setOption({
			// title: {
			// 	text: '',
			// 	subtext: ''
			// },
			backgroundColor: 'white',
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: ['图片数量', '音频数量', '视频数量']
			},
			calculable: true,
			xAxis: [
				{
					type: 'category',
					data: [],
					axisLine: {
						lineStyle: {
							color: '#ccc'
						}
					},

				}
			],
			yAxis: [
				{
					type: 'value',
					axisLine: {
						lineStyle: {
							color: '#ccc'
						}
					},
					splitLine: {
						lineStyle: {
							color: "#f2f2f2"
						}
					},
					axisLabel : {
						formatter : function(value,index){
							if(!Number.isInteger(value)){
								return "";
							}else{
								return value;
							}
						}
					}
				}
			],
			series: [
				{
					name: '图片数量',
					type: 'bar',
					data: [data.picNum],
					color: ['#fd9dca'],
					barWidth: 50,
					//barGap: '3'
				},
				{
					name: '音频数量',
					type: 'bar',
					data: [data.musicNum],
					color: ['#edc58c'],
					barWidth: 50,
					barGap: '1.5'
				},
				{
					name: '视频数量',
					type: 'bar',
					data: [data.videoNum],
					color: ['#6fe2ec'],
					barWidth: 50,
					//barGap: '3'
				}
			]
			// 		series: [{
			// 			name: '',
			// 			type: 'bar',
			// 			data: [5, 20, 36],
			// 			barWidth: 50,
			// 			//配置样式
			// 			itemStyle: {   
			// 				//通常情况下：
			// 				normal:{  
			// 　　　　　　　　　　　//每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
			// 					color: function (params){
			// 						var colorList = ['#fd9dca','#edc58c','#6fe2ec'];
			// 						return colorList[params.dataIndex];
			// 					}
			// 				},
			// 				//鼠标悬停时：
			// 				emphasis: {
			// 					shadowBlur: 10,
			// 					shadowOffsetX: 0,
			// 					shadowColor: 'rgba(0, 0, 0, 0.5)'
			// 				}
			// 			},
			// 	　　　　　//控制边距　
			// 			grid: {
			// 					left: '0%',
			// 					right:'10%',
			// 					containLabel: true,
			// 			}
			// 		}]
		});
	}

	drawRightChart(dataX, dataY) {

		// let zrColor = require('zrender/tool/color');
		// let fillColor = zrColor.getLinearGradient(
		// 	0, 200, 0, 400,
		// 	[[0, 'rgba(255,0,0,0.8)'],[0.8, 'rgba(255,255,255,0.1)']]
		// )

		// 基于准备好的dom，初始化echarts实例
		let rightChart = echarts.init(document.getElementById('rightChart'));
		// 绘制图表
		rightChart.setOption({
			// title: {
			// 	text: '折线图堆叠'
			// },
			backgroundColor: 'white',
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: ['一周粉丝增长量'],
				show: true
			},
			grid: {
				left: '3%',
				right: '4%',
				bottom: '3%',
				containLabel: true,
				show: true,
				backgroundColor: '#393f4f'
			},
			toolbox: {
				feature: {
					saveAsImage: {}
				}
			},
			xAxis: {
				type: 'category',
				boundaryGap: false,
				// data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
				data: dataX,
				axisLine: {
					lineStyle: {
						color: '#717580'
					}
				},
				splitLine: {
					show: true,
					lineStyle: {
						color: "rgba(255,255,255,0.2)"
					}
				}
			},
			yAxis: {
				type: 'value',
				axisLine: {
					lineStyle: {
						color: '#717580'
					}
				},
				splitLine: {
					show: true,
					lineStyle: {
						color: "rgba(255,255,255,0.2)"
					}
				},
				axisLabel : {
					formatter : function(value,index){
						if(!Number.isInteger(value)){
							return "";
						}else{
							return value;
						}
					}
				}
			},
			series: [
				{
					name: '一周粉丝增长量',
					type: 'line',
					stack: '总量',
					// data: [1, 0, 0, 0, 0, 0,2],
					data: dataY,
					backgroundColor: '#393f4f',
					smooth: true,
					symbolSize: 4,
					itemStyle: {
						normal: {
							areaStyle: {
								// 区域图，纵向渐变填充
								color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
									offset: 0,
									color: '#5eade9'
								}, {
									offset: 1,
									color: '#c37dd3'
								}])
							},
							color: '#717580'
						}
					}
				}
			]
		});
	}

	componentDidUpdate() {
		window.dispatchEvent(resizeEvent);
	}

	render() {
		let path = hashHistory.getCurrentLocation().pathname;
		return (
			<Wrapper>
				<div className="top">
					<HomeCard count={this.state.bookSum||0} title='书籍数量/本' desc='BOOKS' imgUrl={require('assets/images/home-book.png')} />
					<HomeCard count={this.state.totalContent||0} title='文章数量/章' desc='ARTICLES' imgUrl={require('assets/images/home-article.png')} />
					<HomeCard count={this.state.totalPV||0} title='实时浏览量' desc='VIEWS' imgUrl={require('assets/images/home-view.png')} />
				</div>
				<div style={{ position: "relative" }}>
					<div id="leftChart" className="left-chart"></div>
					<div className="left_legend">
						<span className="pic"></span>图片数量<span className="audio"></span>音频数量<span className="video"></span>视频数量
					</div>
				</div>
				<div style={{ position: "relative" }}>
					<div id="rightChart" className="right-chart"></div>
					<div className="right_legend">
						<span className="progress"></span>一周粉丝增长量
					</div>
				</div>
			</Wrapper>
		)
	}
}