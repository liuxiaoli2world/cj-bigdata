import React from "react";
import styled from "styled-components";
import {ListWrapper} from "./style.js";
import {hashHistory,Link} from "react-router";

export default class BookItems extends React.Component{
	constructor(props){
		super(props);
	}

	componentDidMount(){
		
	}
    
	render(){
        const {mode, items, id, childId} = this.props;
        return(
			<ListWrapper>
                <ul className="clearfix">
                    {items.map((item,i) => 
                        <li key={i} className={`clearfix ${mode}`}>
                            {(mode=='book') ? (
                                <div className="cover left">
                                    <div className="imgbox">
                                        <img src={item.bookImages.length>0 ? item.bookImages[0].imageUrl : ''} alt=""/>
                                    </div>
                                </div>
                            ) : null }
                            <div className="intro left">
                                {(mode=='image') ? (
                                    <div>
                                        <img width="240" height="200" src={item.path ? item.path : require(`assets/images/img_cover.png`)} alt=""/>
                                    </div>
                                ) : null}
                                <Link to={{pathname:`classify/${childId}/${mode}/${item.bookId || item.contentId || item.multifileId}`}} target = '_blank'><div className="title">{item.bookName||item.title}</div></Link>
                                {(mode=='journal') ? (
                                    <div className="content">{item.contentDesc || '暂无简介'}</div>
                                ) : null}
                                {(mode!='image') ? (
                                    <div className="other">
                                        作者：<span className="author">{item.author ? `${item.author.replace(/，|\；|\;/g, ",").split(',').slice(0,2).join(',')}${item.author.split(',').length>1 ? '等':''}` : '佚名'}</span> 
                                        出版时间：<span className="time">{item.releaseDate ? item.releaseDate.substring(0,10) : item.createdAt ? item.createdAt.substring(0,10) :'未知'}</span>
                                    </div>
                                ) : null}
                                {(mode!='image') ? (
                                    <div className="key">中文关键词：<span className="keywords">{item.keyword ? `${item.keyword.split(',').slice(0,3).join(' ')}` : '暂无关键词'}</span></div>
                                ) : null}
                            </div>
                        </li>
                    )}
                </ul>
			</ListWrapper>
		)
	}
}