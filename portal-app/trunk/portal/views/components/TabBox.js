import React from "react";
import {hashHistory,Link} from "react-router";
import "./tabBox.scss";

export default class TabBox extends React.Component{
	constructor(props){
		super(props);
	}

	componentDidMount(){
		
	}

	render(){
		// mode=='normal' 资讯、推荐阅读
		// mode=='authorList' 作者专家
		// mode=='tag' 推荐专家
        let {mode, title, items, linkurl} = this.props;
		let lists = [];
		if(mode=='authorList') {
			for(let key in items[0]){
				lists.push(
					{
						id: key,
						title: items[0][key]
					}
				)
			}
			items = lists;
		}
		return(
			<div className="tabWrapper">
                <div className="title">{title}</div>
                <div className="content">
					{mode!='tag' ?
                    <ul className="clearfix">
						{ 
							items.map((item,i) => 
							<Link to={`${linkurl}${item.bookId || item.id || item.contentId}`} key={i}>
                            	<li  className={`clearfix ${mode}`}>
									<span className="disc left"></span>
									<span className="left name">{item.title || item.bookName}</span> 
									{(mode=="tag")?(<span className="right">>></span>):null}
								</li>
							</Link>)
						}
					</ul> :
					<div className={`clearfix ${mode}`}>
						<Link to={`/expert/detail/${items.expertId}`} >
							<div className="imgBox left">
                            	<img className='img' src={items.imageUrl} />
							</div>
                            <div className='info left'>
								<p className="name">{items.realName}</p>
                                <p className='desc'>{items.expertDesc&&items.expertDesc.length<20?items.expertDesc:items.expertDesc&&items.expertDesc.substr(0,20)+'...'}</p>
							</div>
                        </Link>
					</div>
					}
                </div>
			</div>
		)
	}
}