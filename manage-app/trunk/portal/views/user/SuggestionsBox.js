import React from "react";
import {Link,hashHistory} from "react-router";
import {get,post} from "utils/request.js";
import {Select,Input,Button,Popconfirm,Pagination,message} from "antd";
import {Box} from "./style.js";

const Option = Select.Option;


class SuggestionsBox extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            show : -1 ,              //回复框显示
            opinionlist : [],
            total : 1,
            pageSize : 6,
            status : 0 ,             //回复状态
            keywords : "",            //关键词
            btnloading : false
        }
    }

    //获取所有留言
    getOpinionlist(pagenum,status,keyword){
        post(`/user/idea/queryIdeaForAdmin?pageNum=${pagenum}&pageSize=6&status=${status}${keyword?`&keyWord=${keyword}`:''}`)
        .then(res=>{
            if(res && res.data){
                res.data.list && res.data.list.map((item,i)=>{
                    item.userimg = require("assets/images/user-pic.png");
                })
                this.setState({
                    opinionlist : res.data.list || [],
                    total : res.data.total,
                    current : pagenum,
                    beforeStatus : status,
                    beforeKeywords : keyword,

                })
            }
        }).catch(e=>{
            this.setState({
                opinionlist : []
            })
        })
    }

    componentDidMount(){
        this.getOpinionlist(1,0);
    }

    //回复状态变化
    changeStatus = (value)=>{
        this.setState({ 
            status : value 
        })
    }

    //关键词变化
    changeKeywords = (e)=>{
        this.setState({ 
            keywords : e.target.value 
        })
    }

    //查询
    handleSearch = ()=>{
        let {status,keywords} = this.state;
        this.getOpinionlist(1,status,keywords);
    }

    //点击回复
    showReply = (param)=>{
        this.setState({show : param})
    }

    //回复内容
    handleReplychange = (e)=>{
        this.setState({ answer : e.target.value })
    }

    //确定回复
    doReply = (id,i)=>{
        let {opinionlist} = this.state;
        this.setState({ btnloading : true })
        if(this.state.answer){
            post(`user/idea/reply?ideaId=${id}&reply=${this.state.answer}`)
            .then(res=>{
                if(res && res.meta && res.meta.success){
                    opinionlist[i].reply = this.state.answer;
                    this.setState({
                        opinionlist,
                        show : -1,
                        answer : "",
                        btnloading : false,
                    })
                    message.success("回复成功");
                }
            }).catch(e=>{
                console.log(e)
                message.error("服务超时，回复失败");
                this.setState({
                    btnloading : false
                })
            })
        }else{
            this.setState({
                show : -1,
                btnloading : false,
            })
        }
    }

    //确认删除
    handleDelete = (id,i)=>{
        let {opinionlist,status,keywords} = this.state;
        get(`user/idea/remove/${id}`)
        .then(res=>{
            if(res && res.meta && res.meta.success){
                // opinionlist.splice(i,1);
                // this.setState({opinionlist})
                message.success("删除成功");
                this.getOpinionlist(this.state.current,status,keywords)
                
            }
        }).catch(e=>{
            console.log(e)
            // message.error("服务超时，删除失败");
        })
    }

    //页码切换
    pageChange = (page,pageSize)=>{
        let {beforeKeywords,beforeStatus} = this.state;
        this.getOpinionlist(page,beforeStatus,beforeKeywords);
    }
    render(){
        let {opinionlist} = this.state;
        return (
            <Box className="clearfix">
                <div className="search">
                    回复状态：
                    <Select defaultValue="0" onChange={this.changeStatus}>
                        <Option value="0">全部</Option>
                        <Option value="2">已回复</Option>
                        <Option value="1">未回复</Option>
                    </Select>
                    关键词：
                    <Input onChange={this.changeKeywords}/>
                    <Button onClick={this.handleSearch}>查询</Button>
                </div>
                {
                    opinionlist && opinionlist.length>0 ?
                <div>
                <div className="opinion-list">
                    {
                        opinionlist.map((item,i)=>{
                            return (
                                <div className="opinion-item clearfix" key={i}>
                                    <dl className="clearfix">
                                        <dt><img src={item.userimg} alt="用户头像"/></dt>
                                        <dd className="date">发表于{item.createdAt}</dd>
                                        <dd className="email">邮箱：<span>{item.userEmail}</span></dd>
                                        <dd className="con">{item.idea}</dd>
                                    </dl>
                                    <div className="reply-input clearfix" style={{display:this.state.show==i?"block":"none"}}>
                                        <Input type="textarea" onChange={this.handleReplychange}/>
                                        <Button onClick={this.doReply.bind(this,item.ideaId,i)} loading={this.state.btnloading}>回复</Button>
                                    </div>
                                    <div className="reply-content" style={{display:item.reply?"block":"none"}}>
                                        <div className="arrow"><em></em><span></span></div>
                                        {item.reply?item.reply:""}
                                    </div>
                                    <div className="action">
                                        {item.reply?<span style={{color:"#999999"}}>已回复</span>:<a href="javascript:void(0);" onClick={this.showReply.bind(this,i)}>回复</a>}
                                        <Popconfirm title="确定删除该意见？" okText="是" cancelText="否" onConfirm={this.handleDelete.bind(this,item.ideaId,i)}>
                                            <a href="javascript:void(0);">删除</a>
                                        </Popconfirm>
                                    </div>
                                </div>
                            )
                        })
                    }
                </div>
                <Pagination current={this.state.current} total={this.state.total} pageSize={this.state.pageSize} showQuickJumper onChange={this.pageChange}/>
                </div>
                :
                    <div className="noData">
                        <img src={require('assets/images/no-data.png')} alt="" />
                    </div>
                }
            </Box>
        )
    }
}

export default SuggestionsBox;