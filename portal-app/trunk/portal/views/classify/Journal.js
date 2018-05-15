import React from "react";
import TabBox from '../components/TabBox';
import TitleBar from '../components/TitleBar';
import Aside from './Aside';
import { Button } from 'antd';
import {get,post} from "util/request.js";
import { SubEmiter, Emiter } from 'util';

export default class Book extends React.Component{
    constructor(props){
        super(props);
        this.state={
            sugInfo:[],
            sugExpert:[],
            detail:{}
        }
    }

    componentDidMount() {
        const id = this.props.params.id;
        Emiter.emit('detailTitle',2);
        get(`book/content/query/${id}/index`)
            .then((data) => {
                if(data.meta.success){
                    this.setState({
                        detail : data.data
                    })
                }
            })
    }

    render(){
        const {sugInfo,sugExpert,detail} = this.state;
        const buttonStyle = {
            fontSize:'16px',
            width:'90px',
            height:'30px',
            margin:'0 auto',
            display:'block'
        };
        return (
            <div className="classifyContent clearfix">
                {/*左边标签栏*/}
                <Aside />
                {/*右边主体内容*/}
                <div className="content left">
                    <div className="journal">
                        <div className="jourTitle">
                            <img src={require('assets/images/icon-text.png')} alt=""/>
                            {detail.title ? detail.title : ''}
                        </div>
                        <div className="journal_intro">
                            <div className="intro_content" dangerouslySetInnerHTML={{__html: `<span class="blue">【摘要】&nbsp;</span>${detail.body ? detail.body.replace(/<\/?[^>]*>/g,'') : ''}`}}>
                                
                            </div>
                        </div>
                        <div className="info">
                            <p>来源：<span className="blue">{detail.source ? detail.source : '暂无来源'}</span></p>
                            <p>作者：<span className="blue">{detail.author ? detail.author : '佚名'}</span></p>
                            <p>出版日期：<span>{detail.createdAt ? detail.createdAt.substring(0,7) : '未知'}</span></p>
                            <p>关键词：<span className="blue">{detail.keyword ? detail.keyword : '暂无关键词'}</span></p>
                        </div>
                        {detail.contentAccssoryList&&detail.contentAccssoryList.length>0 ?
                        <div className="download">
                            <Button type="primary" style={buttonStyle}><a href={detail.contentAccssoryList[0].accessoryUrl} download="">下载</a></Button>
                        </div>
                        : null }
                    </div>
                </div>
            </div>
        )
    }
}