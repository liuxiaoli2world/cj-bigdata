import React from 'react';
import { Link, hashHistory } from 'react-router';
import { get, post } from 'util/request.js';
import { Layout, Popover, Input, Collapse, Tree, Button, message, Spin, Icon, Popconfirm } from 'antd';
import BuyModal from '../classify/BuyModal';
import styled from "styled-components";
import './reAntd.scss';
import base64url from 'base64url';
import { SubEmiter, Emiter } from 'util';
import { isTockenValid } from 'util/userTool.js';
const { Header, Content } = Layout;
const Search = Input.Search;
const Panel = Collapse.Panel;
const TreeNode = Tree.TreeNode;
const MyIcon = styled.span`
    margin-left:25px;
    img{
        vertical-align:middle;
    }
`
const List = styled.div`
    position:fixed;
    border:1px solid #ccc;
    box-shadow: 0px 7px 9.9px 0.1px 
    rgba(73, 73, 73, 0.3);
    width:365px;
    height:900px;
    overflow:auto;
    margin:0 auto;
    background:#f4f4f4;
    top:0;
    left: 50px;
    font-size:16px;
    padding: 0 20px;
    .title{
        font-size:32px;
        color:#333;
        text-align:left;
        line-height:34px;
        margin:40px 0 42px 30px;
    }
   
    .item{
        line-height:38px;
        border-bottom:1px dashed #e3e3e3;
    }
    .lockImg{
        opacity: 0;
    }
    .ant-collapse-item.lock{
        .ant-collapse-header{
            p,.arrow:before{
                color: #999;
            }
        }
        .lockImg{
            opacity:1;
            vertical-align:middle;
            margin: -5px 0 0 25px;
        }
    }
    .myList{
        .ant-collapse-item.lock{
            .ant-collapse-header{
                p,.arrow:before{
                    color: #333;
                }
            }
            .lockImg{
                opacity:0;
            }
        }
    }
`
const Mark = styled.div`
    position: absolute;
    left: 50px;
    border:1px solid #ccc;
    width:300px;
    height:400px;
    overflow:auto;
    margin:0 auto;
    background:#f4f4f4;
    top:49px;
    font-size:16px;
    .title{
        font-size:16px;
        color:#333;
        text-align:center;
        line-height:34px;
        margin: 10px 0;
    }
    .markItem{
        line-height: 30px;
        cursor: pointer;
        margin: 0 20px;
        font-size: 14px;
        border-bottom:1px dashed #e3e3e3;
        color: #666;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        padding-right: 20px;
        position: relative;
        &:hover .anticon{
            opacity: .8;
        }
        .anticon{
            position:absolute;
            top: 8px;
            right: 5px;
            opacity:0;
            &.ant-popover-open{
                opacity: .8;
            }
        }
    }
`
const Sj = styled.div`
    position:absolute;
    border-top:1px solid #ccc;
    border-left:1px solid #ccc;
    background:#f4f4f4;
    width:12px;
    height:12px;
    top:34px;
    right:153px;
    transform:rotate(45deg);
    &.mSJ{
        right: 117px;
    }
    
`
export default class ReadBook extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isList: false,
            isMark: false,
            list: [],
            detail: {},
            content: '',
            activeKey: [],
            pageNum: 1,
            total: 1,
            myBook: [],
            myMark: [],
            loading: false,
            isRelease: '',
            isSearch: false
        }
    }

    componentDidMount() {
        const { isbn, id } = this.props.params;
        this.getList(isbn);
        get(`book/book/isRelease/${isbn}`)
            .then((data) => {
                if (data.meta.success) {
                    if (data.data !== 'success') {
                        return Promise.reject('下架');
                    } else {
                        this.setState({
                            isRelease: 1
                        })
                    }
                }
            })
            .then(() => {
                if (id) {
                    this.onSelect(id, 0, 1);
                } else {
                    this.getData(1);
                }
                this.queryMybook(isbn);
                this.getMark();
            })
            .catch((res) => {
                this.setState({
                    isRelease: 0
                })
            })

        document.oncontextmenu = new Function("event.returnValue=false");
        document.onselectstart = new Function("event.returnValue=false");
    }

    //高亮
    markKeyword(keyword) {
        let { content } = this.state;
        keyword = keyword && keyword.trim();
        if (keyword) {
            let regex = new RegExp("(" + keyword + ")(?=[^<>]*<)", "gi");
            let newHtml = content.replace(regex, `<mark className='highlight'>${keyword}</mark>`)
            this.setState({
                detail: Object.assign({}, this.state.detail, { content: newHtml })
            })
        } else {
            this.setState({
                detail: Object.assign({}, this.state.detail, { content })
            })
        }
    }

    showList() {
        this.state.isRelease === 1 &&
            this.setState({
                isList: !this.state.isList,
                isMark: false,
                isSearch: false
            })
    }

    showMark() {
        const _this = this;
        if (this.state.isRelease === 1) {
            isTockenValid(function (isValid) {
                if (isValid == 1) {
                    _this.setState({
                        isList: false,
                        isSearch: false,
                        isMark: !_this.state.isMark
                    })
                } else {
                    window.localStorage.clear();
                    hashHistory.push('/login');
                }
            })
        }
    }

    //获取用户标签
    getMark = () => {
        const { isbn } = this.props.params;
        const _this = this;
        isTockenValid(function (isValid) {
            if (isValid == 1) {
                const userId = window.localStorage.getItem('userId');
                get(`user/bookmark/queryBookMark?isbn=${isbn}&userId=${userId}`)
                    .then((res) => {
                        if (res.meta.success) {
                            _this.setState({
                                myMark: res.data
                            })
                        }
                    })
            } else {
                window.localStorage.clear();
            }
        })

    }

    //添加书签
    addTab() {
        const _this = this;
        if (this.state.isRelease) {
            isTockenValid(function (isValid) {
                if (isValid == 1) {
                    const userId = window.localStorage.getItem('userId');
                    const scrollTop = window.scrollY;
                    const { bookDirId } = _this.state;
                    const data = {
                        bookSectionId: bookDirId,
                        isbn: _this.props.params.isbn,
                        userId: userId,
                        name: _this.state.detail.title,
                        xyz: scrollTop
                    }
                    post(`user/bookmark/add`, data)
                        .then((res) => {
                            if (res.meta.success) {
                                message.success('添加书签成功!');
                                _this.getMark();
                            }
                        })
                } else {
                    window.localStorage.clear();
                    hashHistory.push('/login');
                }
            })
        }
    }
    //跳转书签
    gotoMyMark = (item) => {
        this.onSelect(item.bookSectionId, item.xyz, 0);
    }

    showDelMark = (e) => {
        e.stopPropagation();
    }

    //删除书签
    delMark = (id, e) => {
        get(`user/bookmark/remove/${id}`)
            .then((res) => {
                if (res.meta.success) {
                    message.success('删除书签成功!');
                    this.getMark();
                }
            })
    }

    search = (val) => {
        this.markKeyword(val);
        this.setState({
            isList: false,
            isMark: false
        })
    }

    //所有目录
    getList(isbn) {
        get(`book/bookdir/queryAllByIsbn/${isbn}`)
            .then((res) => {
                if (res.meta.success) {
                    this.setState({
                        list: res.data
                    })
                }
            })

    }
    //用户购买的章节
    queryMybook = (isbn) => {
        const _this = this;
        isTockenValid(function (isValid) {
            if (isValid == 1) {
                const userId = window.localStorage.getItem('userId');
                get(`user/userbook/queryRootDirsOfUser?isbn=${isbn}&userId=${userId}`)
                    .then((res) => {
                        if (res.meta.success) {
                            _this.setState({
                                myBook: res.data
                            })
                        }
                    })
            } else {
                window.localStorage.clear();
            }
        })
    }

    processMenu(data, pid, isTry) {
        const { myBook } = this.state;
        let result = [];
        let temp;
        for (let i = 0; i < data.length; i++) {
            const item = data[i];
            if (item.parentBookDirId == pid) {
                let obj = {
                    dirName: item.dirName,
                    id: item.bookDirId,
                    parentId: item.parentBookDirId,
                    isTry: isTry || item.isTry,
                    fullPrice: item.fullPrice
                };
                temp = this.processMenu(data, item.bookDirId, item.isTry);
                if (pid === 0 && myBook.indexOf(item.bookDirId) >= 0) {
                    obj.isBuy = true;
                }
                if (temp.length > 0) {
                    obj.children = temp;
                    obj.hasLeaf = true;
                }
                result.push(obj);
            }
        }
        return result;
    }

    changeKey = (key) => {
        this.setState({
            activeKey: [key]
        })
    }
    //下一章节获取数据
    getData(pageNum,isRefresh) {
        const {isbn} = this.props.params;
        const _this = this;
        let userId;
        this.setState({
            loading: true
        })
        pageNum = isRefresh ? --pageNum : pageNum;
        isTockenValid(function (isValid) {
            if (isValid == 1) {
                userId = window.localStorage.getItem('userId');
            } else {
                window.localStorage.clear();
            }
            get(`book/booksection/queryByIsbn?pageNum=${pageNum}&pageSize=1&isbn=${isbn}${userId ? `&userId=${userId}` : ``}`)
                .then((res) => {
                    if (res.meta.success && res.data.list.length > 0) {
                        const item = res.data.list[0];
                        const userTry = item.content || item.content === '' ? true : false;
                        const content = item.content ? base64url.decode(item.content) : item.tryContent ? base64url.decode(item.tryContent) : '';
                        const detail = Object.assign({}, item, { content });
                        _this.setState({
                            detail,
                            content,
                            bookDirId: item.bookDirId,
                            userTry,
                            loading: false,
                            isMark: false,
                            activeKey: [item.bookDirId + ''],
                            pageNum: res.data.pageNum,
                            total: res.data.total
                        })
                        $('html, body').animate({
                            scrollTop: 0
                        }, 0);
                    }else{
                        _this.setState({
                            loading: false
                        })
                    }
                })
        })
    }
    //选章节获取数据
    onSelect = (id, xyz, hasLeaf, isLock, e) => {
        const { isbn } = this.props.params;
        const _this = this;
        let userId;
        this.setState({
            loading: true
        })
        if (isLock) return;
        window.scrollTo(0,0);
        isTockenValid(function (isValid) {
            if (isValid == 1) {
                userId = window.localStorage.getItem('userId');
            } else {
                window.localStorage.clear();
            }
            get(`book/booksection/queryByBookDirId?isbn=${isbn}&bookDirId=${id}&hasLeaf=${hasLeaf}${userId ? `&userId=${userId}` : ``}`)
                .then((res) => {
                    if (res.meta.success) {
                        const userTry = res.data.content || res.data.content === '' ? true : false;
                        const content = res.data.content ? base64url.decode(res.data.content) : res.data.tryContent ? base64url.decode(res.data.tryContent) : '';
                        const detail = Object.assign({}, res.data, { content });
                        _this.setState({
                            isList: false,
                            detail,
                            bookDirId: res.data.bookDirId,
                            content,
                            userTry,
                            isMark: false,
                            loading: false,
                            pageNum: res.data.pageNum,
                            total: res.data.total
                        })
                        window.scrollTo(0, xyz);
                    }else{
                        _this.setState({
                            loading: false
                        })
                    }
                })
        })
    }
    prevPage = () => {
        const { isbn } = this.props.params;
        this.getData(this.state.pageNum-1);
    }
    nextPage = () => {
        const { isbn } = this.props.params;
        this.getData(this.state.pageNum+1);
    }

    buyBook = () => {
        Emiter.emit('buyBook');
    }
    showSearch = () => {
        this.setState({
            isSearch : !this.state.isSearch,
            isList: false,
            isMark: false,
        })
    }
    render() {
        const { isList, list,isSearch, detail, activeKey, isMark, loading, myMark, isRelease, pageNum, total } = this.state;
        const isVip = (window.localStorage.getItem('vipStatus') == '会员');
        const loop = data => data.map((item, i) => {
            const isLock = isLock ? isLock : !item.isBuy && !item.isTry && item.fullPrice > 0 && !isVip;
            if (item.children) {
                return <Panel className={isLock ? 'lock' : 'myList'} header={<p>{item.dirName}<img className="lockImg" src={require('assets/images/lock.png')} alt="" /></p>} key={item.id}><Collapse>{loop(item.children)}</Collapse></Panel>;
            }
            return <Panel className={`noChild ${isLock ? 'lock' : 'myList'}`} key={item.id}
                header={<p onClick={this.onSelect.bind(this, item.id, 0, 0, isLock)} >
                    {item.dirName}
                    <img className="lockImg" src={require('assets/images/lock.png')} alt="" />
                </p>}>
            </Panel>;
        });

        return (
            <div>
            <Spin tip="Loading..." spinning={loading} size="large" >
                <SubEmiter eventName="buyOk" listener={this.getData.bind(this,pageNum,true)} />
                
                <Layout className="layout" style={{ height: 'calc(100% - 10px)' }}>
                    <Header className="header" style={{ position: 'fixed', top: '0px', zIndex: '1000', background: '#fcf3ee', padding: '10px 100px 0', width: '1000px', height: 'auto', lineHeight: '0' }}>
                        <div className="tab clearfix" style={{ borderBottom: '1px solid #ccc', padding: '10px 0 18px', position: 'relative' }}>
                            <div className="chapterName left" style={{ lineHeight: '14px' }}>
                                <span>{detail.title}</span>
                            </div>
                            
                        </div>
                    </Header>
                    {isRelease === 1 ?
                        <Content>
                            <div className="read_content" style={{ lineHeight: '40px', backgroundColor: '#fcf3ee', padding: '100px 100px 100px' }} dangerouslySetInnerHTML={{
                                __html: detail.content ? detail.content : ''
                            }}>
                            </div>
                            {!this.state.userTry && !loading ?
                                <div className="tryTips">
                                    <p>{detail.content ? '试读已结束，购买后可继续阅读' : isVip ? '该章节暂无内容，请阅读下一章节' : '暂无试读内容，购买后可阅读'}</p>
                                    {!isVip ?
                                        <div>
                                            <Button onClick={this.buyBook} className="buyBtn">购买</Button>
                                        </div> : null}
                                </div> : null}
                            {this.state.userTry && !detail.content ? 
                                <div className="tryTips">
                                    <p>{`该章节暂无内容${pageNum != total ? '，请阅读下一章节' : '' }`}</p>
                                </div> : null
                            }
                        </Content>
                        : isRelease === 0 ?
                            <Content>
                                <div style={{ backgroundColor: '#fcf3ee', padding: '240px 100px 100px', textAlign: 'center', marginTop: '153px' }}>
                                    <img src={require('assets/images/noRelease.png')} alt="" />
                                </div>
                            </Content>
                            : null}
                    {isRelease === 1 ?
                        <footer className="readBtns">
                            {!loading && pageNum > 1 ?
                            <span className="readBtn" onClick={this.prevPage}>上一节</span>
                            : null 
                            }
                            {!loading ?
                            <span className="readBtn" onClick={this.showList.bind(this)}>目录</span>
                            : null
                            }
                            {!loading && pageNum != total ?
                                <span className="readBtn" onClick={this.nextPage}>下一节</span>
                            : null 
                            }
                            {/* {!loading && pageNum != (total+1) ?
                                <div className="nextPage" style={{ textAlign: 'center', cursor: 'pointer' }} onClick={this.nextPage}>下一节</div>
                                : null} */}
                        </footer> : null}
                    {isRelease === 1 ?
                        <BuyModal isbn={this.props.params.isbn} />
                        : null}
                </Layout>
            </Spin >
            <div className="icons">
                    <Popover content={'目录'} trigger="hover">
                        <MyIcon className={isList?'Iactive' : ''} onClick={this.showList.bind(this)}><img src={require('assets/images/list.png')} alt="" /></MyIcon>
                    </Popover>
                    <Popover content={'我的书签'} trigger="hover">
                        <MyIcon className={isMark?'Iactive' : ''} onClick={this.showMark.bind(this)}><img src={require('assets/images/mytags.png')} alt="" /></MyIcon>
                    </Popover>
                    <Popover content={'加入书签'} trigger="hover">
                        <MyIcon onClick={this.addTab.bind(this)}><img src={require('assets/images/tag.png')} alt="" /></MyIcon>
                    </Popover>
                    <Popover content={'搜索'} trigger="hover">
                        <MyIcon onClick={this.showSearch.bind(this)}><img src={require('assets/images/search.png')} alt="" /></MyIcon>
                    </Popover>
                    <MyIcon onClick={this.getData.bind(this,1)}><img src={require('assets/images/back.png')} alt="" /></MyIcon>
                </div>
                {isList ?
                                <div>
                                    <List>
                                        <div className="title">目录</div>
                                        <Collapse accordion activeKey={activeKey} onChange={this.changeKey}>
                                            {loop(this.processMenu(list, 0))}
                                        </Collapse>
                                    </List>
                                </div>
                                : null
                            }
                            {isMark ?
                                <div>
                                    <Mark>
                                        <div className="title">我的书签</div>
                                        <div>
                                            {myMark.map((item, i) => (
                                                <div className="markItem" onClick={this.gotoMyMark.bind(this, item)} key={i}>
                                                    {item.name ? item.name : `书签${++i}`}
                                                    <Popconfirm title="是否删除书签?" onConfirm={this.delMark.bind(this, item.bookmarkId)} okText="是" cancelText="否">
                                                        <Icon type="delete" onClick={this.showDelMark} />
                                                    </Popconfirm>
                                                </div>
                                            ))}
                                        </div>
                                    </Mark>
                                </div>
                                : null
                            }
                            {
                                isSearch ? 
                                <div className="searchBox">
                                    <Search
                                        placeholder="搜索"
                                        style={{ width: 200 }}
                                        onSearch={(value) => { this.search(value) }}
                                    />
                                </div>
                                :null
                            }
        </div>
        )

    }
}