import React from 'react';
import {Link, hashHistory} from 'react-router';
import {get, post} from 'utils/request.js';
import {SubEmiter,Emiter} from "utils/index.js";
import {Input,Button,Table,message} from "antd";
import {DetailWrap} from "./style.js";


class UserDetails extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            data : [],
            pagination : {
                total : 1,
                pageSize : 6,
                showQuickJumper : true
            },
            loading : false,
            selectUser : []
        }
        this.columns = [
            {
                title : "编号",
                dataIndex : "oridinal",
                key : "oridinal",
                width: "10%" ,
            },
            {
                title : "用户名",
                dataIndex : "username",
                key : "username",
                width: "8%" ,
            },
            {
                title : "邮箱",
                dataIndex : "email",
                key : "email",
                width: "16%" ,
                render:text=><span>{text?text:"--"}</span>
            },
            {
                title : "创建时间",
                dataIndex : "createdAt",
                key : "createdAt",
                width: "12%" ,
                render:text=><span style={{display:"inline-block",width:"70px",fontSize:12}}>{text}</span>
            },
            {
                title : "是否可用",
                dataIndex : "enable",
                key : "enable",
                width: "10%" ,
                render:text=><span>{text==1?"可用":"不可用"}</span>
            },
            {
                title : "会员状态",
                dataIndex : "vipStatus",
                key : "vipStatus",
                width: "10%" ,
            },
            {
                title : "最近登录时间",
                dataIndex : "lastLoginTime",
                key : "lastLoginTime",
                width: "16%" ,
                render:text=><span style={{display:"inline-block",width:"70px",fontSize:12}}>{text?text:"--"}</span>
            },
            {
                title : "登录IP个数",
                dataIndex : "countIp",
                key : "countIp",
                render:(text,record)=><div style={{paddingLeft:record.isException==1?20:0}}><span style={{color:record.isException==1?"#cd1919":"#666666"}}>{text}</span><img src={require("assets/images/abnormal.png")} alt="异常" style={{display:record.isException==1?"inline-block":"none"}}/></div>
            },
        ]
        this.rowSelection = {
            onChange: (selectedRowKeys, selectedRows) => {
                this.setState({
                    selectUser : selectedRowKeys,
                })
            },
        }
    }

    getUserList(pagenum){
        get(`user/user/selectUserOrganization?pageNum=${pagenum}&pageSize=6&sqeNum=${this.props.orgnum}`)
        .then(res=>{
            if(res && res.data){
                let {pagination} = this.state;
                pagination.total = res.data.total;
                pagination.current = pagenum;
                res.data.list && res.data.list.map((item,i)=>{
                    item.key = item.userId;
                    item.oridinal = i+1+(pagenum-1)*6;
                })
                this.setState({
                    data : res.data.list || [],
                    pagination,
                })
            }
        }).catch(e=>{
            this.setState({
                data : [],
            })
        })
    }

    componentDidMount(){
        this.getUserList(1)  
    }

    tableChange = (page)=>{
        this.getUserList(page.current);
    }
    //设置会员时间
    setMouth = (e)=>{
        this.setState({ month : e.target.value })
    }
    //保存
    handleSave = ()=>{
        let _that = this;
        _that.setState({ loading : true })
        if(_that.state.month && _that.state.month!== "0"){
            if(!/^[1-9]\d*$/.test(_that.state.month)){
                message.error("会员时长应为正整数，请重新输入！")
                 _that.setState({ loading : false })
                return false;
            }else{
                if(_that.state.selectUser.length!=0){
                    get(`user/user/insertVipBatch?month=${_that.state.month}&ids=${_that.state.selectUser}`)
                    .then(res=>{
                        if(res && res.meta && res.meta.success){
                            _that.setState({ loading : false })
                            Emiter.emit("close",{data:"123"});
                            message.success("设置成功！");
                        }
                    }).catch(e=>{
                        console.log(e)
                        _that.setState({ loading : false })
                        message.error("服务超时，设置失败！");
                    })
                }else{
                    _that.setState({ loading : false })
                    message.error("请选择用户！");
                    return false;
                }
            }
        }else{
            _that.setState({ loading : false })
            message.error("请设置会员时长！");
            return false;
        }
        
    }

    render(){
        let {data} = this.state;
        return (
            <DetailWrap>
                <div className="container">
                    <Table rowSelection={this.rowSelection} columns={this.columns} dataSource={data} pagination={this.state.pagination} onChange={this.tableChange}/>
                    <footer>
                        设置会员时长：<Input onChange={this.setMouth}/>个月
                        <Button onClick={this.handleSave} loading={this.state.loading}>保存</Button>
                    </footer>
                </div>
            </DetailWrap>
        )
    }
}

export default UserDetails;