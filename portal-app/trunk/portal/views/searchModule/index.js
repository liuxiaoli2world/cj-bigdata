import React from 'react';
import { Wrapper } from './style.js';
import { Breadcrumb, Icon, Pagination, Input, Button } from 'antd';
import { get } from 'views/util/request';
import { hashHistory } from 'react-router';
import { SubEmiter, Emiter } from 'util';

export default class SearchModule extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentNum: 1,
            pageSize: 10,
            total: 30,
            results: [],
            keyword: '长江',
            currentOrderIndex: 0,
            orders: [{ index: 0, name: '相关度' }, { index: 1, name: '出版时间' }, { index: 2, name: '更新时间' }]
        };
    }

    // 根据传递的参数请求数据
    componentWillMount() {
        let state = this.props.params;
        if (!state) {
            this.setState({
                keyword: keyword
            });
            state = {
                keyword: keyword,
                type: 'desc'
            };
        } else {
            if (!state.type) {
                state.type = 'desc';
            }
        }

        this.getPageData(1, state);
    }

    processHightlight = (list) => {
        for (let i = 0, len = list.length; i < len; i++) {
            const item = list[i];
            this.markKeyword(item);
        }
        return list;
    }

 

    markKeyword(item) {
        let keyword = this.state.keyword;
        keyword = keyword && keyword.trim();
        let regex = new RegExp("(" + keyword + ")(?=[^<>]*<)", "gi");
        let regex1 = new RegExp(keyword,"gm")
        let { title, desc,author } = item;
        if (keyword) {
            
            let newTitleHtml = title && title.replace(regex1, `<mark class='highlight'>${keyword}</mark>`)
            item.title = newTitleHtml;
            let newContentHtml = desc && desc.replace(/<[^>]+>/g,"").slice(0,140).replace(regex1, `<mark class='highlight'>${keyword}</mark>`)
            item.desc = newContentHtml;
            let newAuthor = author && author.replace(regex1, `<mark class='highlight'>${keyword}</mark>`)
            item.author = newAuthor;
        }
    }

    // 根据参数组件查询条件
    buildUrl = (num, params) => {
        let url;
        if (params) {
            const { type, keyword } = params;
            this.state.type = type;
            this.state.keyword = keyword;
            // this.setState({ type, keyword });
        }
        url = `book/menusource/selectSearch?pageNum=${num}&pageSize=${this.state.pageSize}&scope=${this.state.type}&keyword=${encodeURI(this.state.keyword&&this.state.keyword.trim())}&from=index`;

        return url;
    }

    getPageData = (num = 1, params) => {
        const url = this.buildUrl(num, params);
        get(url)
            .then((jsonData) => {
                const data = jsonData.data;
                this.setState({
                    results: this.processHightlight(data.list || []),
                    currentNum: data.pageNum,
                    total: data.total
                });
            });
    }

    orderChange(index, event) {
        this.setState({
            currentOrderIndex: index
        });
        this.getPageData(1, params);
    }

    inputOnChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        this.setState({
            [name]: value
        });
    }

    // 搜索按钮事件
    searchBtnClickEvent = (event) => {
        let params = {};
        this.getPageData(1, params);
    }
    pageOnChange = (currentNum) => {
        this.getPageData(currentNum);
    }

    searchChange = (params) => {
        this.getPageData(1, params);
    }

    itemClickEvent = (type, id) => {
        switch (type) {
            case 'book':
                hashHistory.push(`/classify/1/book/${id}`);
                break;
            case 'notice':
            case 'headline':
            case 'history':
                hashHistory.push(`/information/${type}/detail/${id}`);
                break;
            case 'periodical':
                hashHistory.push(`/classify/1/journal/${id}`);
                break;
            case 'multifile':
                hashHistory.push(`/classify/0/image/${id}`)
            default:
                console.error('错误的类型！');
                break;
        }
    }

    render() {
        const currentOrderIndex = this.state.currentOrderIndex;

        return (
            <Wrapper>
                <SubEmiter eventName="searchChange" listener={this.searchChange}></SubEmiter>
                {/*面包屑*/}
                <Breadcrumb separator='>'>
                    <Breadcrumb.Item><Icon type='pushpin-o' style={{marginRight:4}}/>当前位置：</Breadcrumb.Item>
                    <Breadcrumb.Item href='/'>首页</Breadcrumb.Item>
                    <Breadcrumb.Item>检索结果</Breadcrumb.Item>
                </Breadcrumb>
                {/*<div className='input'>
                    <Input name='title' placeholder='标题' onChange={this.inputOnChange} />
                    <Input name='author' placeholder='作者' onChange={this.inputOnChange} />
                    <Input name='keyword' placeholder='关键词' onChange={this.inputOnChange} />
                    <Button className='searchBtn' onClick={this.searchBtnClickEvent}>在结果中查找</Button>
                </div>
                <div>
                    <span className='keyword'>检索词：{this.state.keyword}</span>
                    <span className='tag'>
                        <span className='order-tag-name'>排序：</span>
                        {
                            this.state.orders.map((item, index) => (
                                <span key={index} className={`order-tag ${currentOrderIndex === index ? 'active' : ''}`} onClick={this.orderChange.bind(this, item.index)}>{item.name}</span>
                            ))
                        }

                    </span>
                </div>*/}
                <div className="key">检索词：<span className="highlight2">{this.state.keyword}</span></div>
                {this.state.results && this.state.results.length!==0 ?
                <div>
                {
                     this.state.results.map((item, index) => (
                        <div key={index} onClick={this.itemClickEvent.bind(this, item.type, item.id)} className="resultBox">
                            <div className='item-title' dangerouslySetInnerHTML={{
                                __html: item.title ? item.title : ''
                            }}></div>
                            <div className='item-content' dangerouslySetInnerHTML={{
                                __html: item.desc ? `${item.desc}...` : ''
                            }}></div>
                            <div className='item-tag'>
                                <span className='item-author-tag'>作者：<span className='item-author' dangerouslySetInnerHTML={{
                                __html: item.author ? item.author : '佚名'
                            }}></span></span>
                                <span className='item-date-tag'>出版时间：<span className='item-date'>{item.releaseDate?item.releaseDate.slice(0,10):'未知'}</span></span>
                            </div>
                            {/*<div>中文关键词：<span className='item-keywords'>{item.keywords}</span></div>*/}
                        </div>
                    ))
                }
                <Pagination className='pagenation' showQuickJumper current={this.state.currentNum} defaultCurrent={2} total={this.state.total} onChange={this.pageOnChange} />
                </div> : <div style={{textAlign:"center",marginTop:70,marginBottom:80}}><img src={require("assets/images/nocontent.png")} alt="暂无内容" /></div>}
            </Wrapper>
        )
    }
}