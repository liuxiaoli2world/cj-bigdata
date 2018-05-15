import React from "react";
import TabBox from '../components/TabBox';
import BookItems from '../components/BookItems';
import { Timeline, Icon, Pagination, Button ,Input} from 'antd';
import {get,post} from "util/request.js";
import {SubEmiter,Emiter} from 'util/index.js';
/**
 ** 分类模块入口
 */
export default class Classify extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            suggest: [],
            author: [],
            datas:{},
            books:{},
            journal:{},
            images:{},
            types:[],
            index:0,
            id:'',
            childId:'',
            keyword:'',
            imgUrl:'',
            pageSize: 10,
            isFixed: false
        };
    }

    toggleType(i) {
        const {types,id,childId,books,journal,images} = this.state;
        let datas = (types[i]=='图书') ? books  
                  : (types[i]=='期刊') ? journal
                  : images;
        this.setState({
            index:i,
            datas,
            keyword:''
        })
        Emiter.emit('changeType',types[i])
    }

    onChangeHandle = (e) => {
        this.setState({
            keyword: e.target.value
        })
    }

    pageOnChange = (currentNum) => {
        this.getData(currentNum);
    }

    getData(pageNum) {
        const {types, index, keyword,id,childId,pageSize} = this.state;
        let pid = (id==0) ? childId : id;
        let cid = (id==0) ? '' : childId;
        const url = types[index] == '图书' ? `book/book/queryBy?pageNum=${pageNum}&pageSize=${pageSize}&parentMenuId=${pid}&childMenuId=${cid}&isRelease=1` : 
                    types[index] == '期刊' ? `book/content/selectPeriodicalByMeauId?pageNum=${pageNum}&pageSize=${pageSize}&menuPId=${pid}&menuId=${cid}` :
                    `book/multifile/queryBy?pageNum=${pageNum}&pageSize=${pageSize}&parentMenuId=${pid}&childMenuId=${cid}&multiType=pic`
        get(`${url}${keyword ? `&keyword=${encodeURI(keyword)}`:''}`)
            .then((res) => {
                if(res.meta.success){
                    this.setState({
                        datas:res.data
                    })
                }
            })                
    }

    componentDidMount() {
        const {menuId} = this.props.params;
        this.getInfo(menuId);
        get(`book/book/queryRec`)
		    .then(res=>{
			    if(res && res.data ){
				    this.setState({
					    suggest:res.data || []
			    	})
			    }
		    })
        get(`expert/expert/selectAllExpertName`)
            .then(res => {
                if(res && res.data ){
				    this.setState({
					    author:res.data || []
			    	})
			    }
            })
        window.addEventListener('scroll', this.handleScroll);
            
    }
  
    componentWillReceiveProps(nextProps){
        const {menuId} = nextProps.params;
        if(menuId!==this.props.params.menuId){
            this.getInfo(menuId);
        }
    }

    componentWillUnmount() {
        window.removeEventListener('scroll', this.handleScroll);
    }

    handleScroll = (e) => {
        let {isFixed} = this.state;
        if(window.pageYOffset>=430){
            isFixed = true;
        }else{
            isFixed = false; 
        }
        this.setState({
            isFixed
        })
    }

    getInfo = (id) => {
        get(`book/menu/query/${id}`)
            .then((res) => {
                if(res.meta.success){
                    Emiter.emit('changeMenu', res.data.menuName);
                    this.initial(res.data.parentMenuId,res.data.menuId);
                    this.setState({
                        imgUrl: res.data.imageUrl
                    })
                }
            })
    }

    initial(id,childId) {
        let pid = (id==0) ? childId : id;
        let cid = (id==0) ? '' : childId;
        const req1 = get(`book/book/queryBy?pageNum=1&pageSize=${this.state.pageSize}&parentMenuId=${pid}&childMenuId=${cid}&isRelease=1`),
              req2 = get(`book/content/selectPeriodicalByMeauId?pageNum=1&pageSize=${this.state.pageSize}&menuPId=${pid}&menuId=${cid}`),
              req3 = get(`book/multifile/queryBy?pageNum=1&pageSize=${this.state.pageSize}&parentMenuId=${pid}&childMenuId=${cid}&multiType=pic`);
        Promise.all([req1,req2,req3])
            .then((data) => {
                const types = [];
                const books = data[0].data ? data[0].data : {};
                const journal = data[1].data ? data[1].data : {};
                const images = data[2].data ? data[2].data : {};
                (books.list&&books.list.length>0) && types.push('图书');
                (journal.list&&journal.list.length>0) && types.push('期刊');
                (images.list&&images.list.length>0) && types.push('图片');
                let datas = (books.list&&books.list.length>0) ? books : (journal.list&&journal.list.length>0)? journal : (images.list&&images.list.length>0) ? images : {};
                types.length>0 && Emiter.emit('changeType',types[0])
                this.setState({
                    books,
                    types,
                    datas,
                    journal,
                    images,
                    id,
                    childId,
                    index: 0
                })
            })
    }

    render() {
        const {suggest,author,datas,index,types,id,childId,imgUrl,isFixed} = this.state;
        const currentType = types.length>0 ? types[index] : '';
        let icon,mode;
        const style1 = {
            position:'fixed',
            top:'10px'
        }
        switch(currentType){
            case '图书':
                icon = 'icon-book';
                mode = 'book';
                break;
            case '图片':
                icon = 'icon-photo';
                mode = 'image';
                break;
            case '期刊':
                icon = 'icon-qikan';
                mode = 'journal';
                break;
            default:
                icon = '';
                mode = '';
                break;
        }
        return (
                <div className="classifyContent clearfix">
                    {/*左边标签栏*/}
                    <div className="aside left" style={isFixed ? style1 :{}}>
                        <TabBox title={'推荐阅读'} items={suggest} mode={'normal'} linkurl={`/classify/${childId}/book/`} />
                        <TabBox title={'作者/专家'} items={author} mode={'authorList'} linkurl={'/expert/detail/'}/>
                    </div>
                    {/*右边主体内容*/}
                    <div className="content left" style={isFixed?{marginLeft:'206px'}:{}}>
                        <div className="banner">
                            <img src={imgUrl} width="786" height="221" alt=""/>
                        </div>
                        {types.length>0 ?
                            <div className="clearfix main">
                                <div className="left lists">
                                    <div className="titleBar">
                                        <img src={icon && require(`assets/images/${icon}.png`)} alt=""/>
                                        {currentType}
                                    </div>
                                    <div className="books">
                                        {(currentType!='图片') ? (
                                            <div className="searchWrapper">
                                                <span>关键词</span>
                                                <Input type="text" name='keywords' value={this.state.keyword} onChange={this.onChangeHandle} onPressEnter={this.getData.bind(this,1)}/>
                                                <button onClick={this.getData.bind(this,1)}>查询</button>
                                            </div>) : null
                                        }
                                        <BookItems items={datas.list||[]} mode={mode} id={id} childId={childId}/>
                                        <Pagination className='pagenation' showQuickJumper pageSize={datas.pageSize} current={datas.pageNum||1} defaultCurrent={1} total={datas.total||0} onChange={this.pageOnChange} />
                                    </div>
                                </div>
                                <div className="left aside_rt">
                                    <div>
                                        {types.map((type,i) => 
                                            <div className={`tab${(i===index) ? ' active' : ''} ${type=='图书' ? 'book' : type=='期刊' ? 'journal' : 'images'}`} key={i} onClick={this.toggleType.bind(this,i)}>{type}</div>
                                        )}
                                        <div className="fiximg">
                                            <img src={require("assets/images/classify1.png")} width='236' alt=""/>
                                        </div>  
                                        <div className="fiximg">
                                            <img src={require("assets/images/classify2.png")} width='236' alt=""/>
                                        </div>
                                    </div>
                                </div>

                            </div> : 
                            <div className="noData">
                                <img src={require('assets/images/nodata.png')} alt=""/>
                            </div>
                        }
                    </div>
                </div>
        )
    }
}