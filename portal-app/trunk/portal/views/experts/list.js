import React from 'react';
import { Wrapper } from './style.js';
import { Breadcrumb, Icon, Pagination, Input, Button } from 'antd';
import { get,post } from 'views/util/request';
import { hashHistory, Link } from 'react-router';
import { SubEmiter, Emiter } from 'util';
import TitleBar from '../home/TitleBar.js';

export default class ExpertList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentNum: 1,
            pageSize: 12,
            total: 0,
            results: []
        };
    }

    // TODO 根据传递的参数请求数据
    componentWillMount() {
        this.getPageData();
    }

    // 根据参数组建查询条件
    buildUrl = (num, params) => {
        let url = '';
        if (params && params.keyword) {
            url = `expert/expert/selectExpertAndCompositionByName?pageNum=${num}&pageSize=${this.state.pageSize}&realName=${encodeURI(params.keyword)}`;
        } else {
            url = `expert/expert/selectByClassifyAndRealName?pageNum=${num}&pageSize=${this.state.pageSize}`;
        }
        return url;
    }

    getPageData = (num = 1, params) => {
        const url = this.buildUrl(num, params);
        if(params && params.keyword){
            get(url)
                .then((res) => {
                    this.processData(res);
                })
        } else {
            post(url)
                .then((res) => {
                    this.processData(res);
                })
        }
    }

    processData = (res) => {
        if (res.meta.success) {
            let data = res.data;
            this.setState({
                results: data.list || [],
                currentNum: data.pageNum,
                total: data.total
            });
        }
    }

    inputOnChange = (event) => {
        const name = event.target.name;
        const value = event.target.value && event.target.value.trim();
        this.setState({
            keyword: value
        });
    }

    // TODO
    searchBtnClickEvent = (event) => {
        let params = {
            keyword: this.state.keyword
        };
        this.getPageData(1, params);
    }
    pageOnChange = (currentNum) => {
        this.getPageData(currentNum);
    }

    render() {
        return (
            <div>
                <TitleBar path='' imageUrl='assets/images/expert-list.png' name='专家学者' />
                <div className='input clearfix'>
                    <Input name='title' placeholder='请输入搜索内容...' onChange={this.inputOnChange} onPressEnter={this.searchBtnClickEvent}/>
                    <Button className='searchBtn' onClick={this.searchBtnClickEvent}>查询</Button>
                </div>
                <div className='list-content'>
                    {
                        this.state.results.map((item, index) => (

                            <span className='list-item' key={index}>
                                <Link to={`/expert/detail/${item.expertId}`}>
                                    <img className='img' src={item.imageUrl} />
                                    <span className='item-content'>
                                        <div className='name'>{item.realName}</div>
                                        <div className='desc'>{item.expertDesc && item.expertDesc.length < 22 ? item.expertDesc : item.expertDesc && item.expertDesc.substr(0, 21) + '...'}</div>
                                    </span>
                                </Link>
                            </span>
                        ))
                    }
                    <Pagination className='pagenation' showQuickJumper pageSize={this.state.pageSize} current={this.state.currentNum} total={this.state.total} onChange={this.pageOnChange} />
                </div>
            </div>
        )
    }
}