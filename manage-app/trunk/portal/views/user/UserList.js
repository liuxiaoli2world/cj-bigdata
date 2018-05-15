import React from 'react';
import {Link, hashHistory} from 'react-router';
import {get, post} from 'utils/request.js';
import {Input,Button,Select,Table,Popconfirm,Icon,Radio,message} from "antd";
import {UserWrap} from "./style.js";

const Option = Select.Option;
const RadioGroup = Radio.Group;

class UserList extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            listdata : [],         //用户列表
            enable : "all",        //账号状态条件
            vipStatus : "all",     //会员状态条件
            org : "",              //机构条件
            pagination : {
                total : 1,
                pageSize : 7 ,
                showQuickJumper : true
            },
            visible : false ,         //设置质检员提示是否可见
            show : false ,            //用户详情是否可见
            tableloading : true,     //表格加载中
            userdetails　: {} ,       //用户详情
            ban : true ,             //封号与否
            btnLoading : false
        }
        this.columns = [
            {
                key : "number",
                title : "编号",
                dataIndex : "number",
                width : "6.4%"
            },
            {
                key : "username",
                title : "用户名",
                dataIndex : "username",
                width : "11.8%"
            },
            {
                key : "organizationName",
                title : "机构名称",
                dataIndex : "organizationName",
                width : "13.2%",
                render:(text)=>text?text:"--"
            },
            {
                key : "enable",
                title : "是否可用",
                dataIndex : "enable",
                width : "11.5%",
                render:text=>text==0?"不可用":"可用"
            },
            {
                key : "vipStatus",
                title : "会员状态",
                dataIndex : "vipStatus",
                width : "11.5%"
            },
            {
                key : "lastLoginTime",
                title : "最近登录时间",
                dataIndex : "lastLoginTime",
                width : "11.8%",
                render:(text)=><span style={{display:"inline-block",width:"70px",fontSize:12}}>{text?text:"--"}</span>
            },
            {
                key : "countIp",
                title : "登录IP个数",
                dataIndex : "countIp",
                width : "9%",
                render:(text,record)=><div style={{color:record.isException　&& record.isException!=0?"#cd1919":"#666666",paddingLeft:record.isException && record.isException!=0?20:0}}>{text}<img src={require("assets/images/abnormal.png")} alt="异常" style={{verticalAlign:"middle",margin:"-4px 0 0 10px",display:record.isException &&　record.isException!=0?"inline-block":"none"}} /></div>
            },
            {
                key : "action",
                title : "操作",
                dataIndex : "action",
                render:(text,record)=>{
                    return (
                        <div>
                            <Button onClick={this.showDetails.bind(this,record.userId,record.key)}>详情</Button>
                            <Button style={{marginLeft:'8%'}} onClick={this.editInspector.bind(this,record.key,record.userId,record.isInspector)}>{record.isInspector=="1"?"取消质检员":"设置质检员"}</Button>
                        </div>
                    )
                }
            },
        ]
    }

    //获取用户列表
    getUserList(pagenum,enable,vipStatus,org,username){
        let {pagination} = this.state;
        let num = 7*(pagenum-1);
        let url = "";
        url = `user/user/queryBy?pageNum=${pagenum}&pageSize=7${enable?`&enable=${enable}`:""}${vipStatus?`&vipStatus=${vipStatus}`:""}${org?`&organization=${org}`:""}${username?`&username=${username}`:""}`;
        get(url)
        .then(res=>{
            if(res && res.data){
                pagination.total = res.data.total;
                pagination.current = pagenum;
                if(res.data.list && res.data.list.length!=0){
                    res.data.list.map((item,i)=>{
                        item.key = i;
                        item.number = i+1+num;     //编号
                    })
                }
                this.setState({
                    tableloading : false,
                    pagination,
                    listdata : res.data.list || [],
                    beforeEnable : enable ,
                    beforeVipstatus : vipStatus ,
                    beforeOrg : org,
                    beforeName : username,
                })
            }
        }).catch(e=>{
            this.setState({
                tableloading : false,
                listdata : []
            })
        })
    }

    componentDidMount(){
        this.getUserList(1)
    }

    //表格变化
    tableChange = (page)=>{
        let {pagination,beforeEnable,beforeVipstatus,beforeOrg,beforeName} = this.state;
        pagination.current = page.current;
        this.getUserList(page.current,beforeEnable=="all"?null:beforeEnable,beforeVipstatus=="all"?null:beforeVipstatus,beforeOrg?beforeOrg:null,beforeName?beforeName:null);
        this.setState({
            pagination,
            tableloading : true
        })
    }
    //账号状态筛选
    accountStatusChange = (value)=>{
        this.setState({ enable : value })
    }

    //会员状态筛选
    memberStatusChange = (value)=>{
        this.setState({ vipStatus : value })
    }
    
    //机构名称筛选
    changeOrgName = (e)=>{
        this.setState({ org : e.target.value })
    }

    //用户名筛选
    usernameChange = (e)=>{
        this.setState({ userName : e.target.value })
    }

    //查询
    searchUser = ()=>{
        let {enable, vipStatus, org, pagination, userName} = this.state;
        pagination.current = 1;
        this.getUserList(1,enable=="all"?null:enable,vipStatus=="all"?null:vipStatus,org?org:null,userName?userName:null);
    }
    
    //设置质检员
    editInspector = (key,id,status)=>{
        this.setState({
            editIndex :key,           //操作的用户索引
            editId : id,              //操作的用户id
            visible : true,                         
            inspectorStatus : status      //操作的用户的质检员的状态
        })
    }
    //确定设置|取消质检员
    sureEditinspector = ()=>{
        let {listdata,editIndex,editId} = this.state;
        post(`user/user/modifyInspector?id=${editId}`)
        .then(res=>{
            if(res && res.meta && res.meta.success){
                listdata[editIndex].isInspector = res.data;
                this.setState({
                    listdata,
                    visible : false
                })
            }
        }).catch(e=>{
            message.error("设置失败");
            this.setState({
                visible : false
            })
        })
    }
    //取消设置|取消质检员
    cancelEditinspector = ()=>{
        this.setState({
            visible : false
        })
    }

    //确定删除用户
    confirmDelete = (userid)=>{
        get(`user/user/remove/${userid}`)
        .then(res=>{
            if(res && res.meta && res.meta.success){
                let {pagination,enable,vipStatus,org} = this.state;
                message.success("删除成功");
                this.getUserList(pagination.current,enable=="all"?null:enable,vipStatus=="all"?null:vipStatus,org?org:null);
            }
        }).catch(e=>{
             message.error("删除失败");
        })
    }

    //查看用户详情
    showDetails = (userid,key)=>{
        this.setState({
            userdetails : {
                createdAt:"请求中",
                email:"请求中",
                enable:"请求中",
                endDate:"请求中",
                isException:"请求中",
                loginRecords:[],
                organizationName:"请求中",
                username:"请求中",
                vipStatus:"请求中"
            },
            show : true,
            banIndex : key
        })
        get(`user/user/queryLoginRecord/${userid}`)
        .then(res=>{
            if(res && res.data){
                this.setState({ 
                    userdetails : res.data || {},
                })
            }
        }).catch(e=>{
            this.setState({
                userdetails : {
                    createdAt:"请求失败",
                    email:"请求失败",
                    enable:"请求失败",
                    endDate:"请求失败",
                    isException:"请求失败",
                    loginRecords:[],
                    organizationName:"请求失败",
                    username:"请求失败",
                    vipStatus:"请求失败"
                },
            })
        })
    }
    //封号
    handleBan = (e)=>{
        this.setState({ ban : e.target.value == "yes" ? true : false})
    }
    //关闭用户详情
    closeDetails = ()=>{
        this.setState({
            show : false,
        })
    }
    //保存封号操作
    saveoperation = ()=>{
        this.setState({
            btnLoading : true
        })
        if(this.state.ban){
            get(`user/user/unactiveUser/${this.state.userdetails.userId}`)
            .then(res=>{
                if(res && res.meta && res.meta.success){
                    let {listdata} = this.state;
                    listdata[this.state.banIndex].enable = 0;  //设置列表中当前操作对象的状态为不可用
                    this.setState({
                        show : false,
                        btnLoading : false,
                        listdata
                    })
                    message.success("封号成功");

                }
            }).catch(e=>{
                message.error("封号失败");
            })
        }else{
            get(`user/user/activeUser/${this.state.userdetails.userId}`)
            .then(res=>{
                if(res && res.meta && res.meta.success){
                    let {listdata} = this.state;
                    listdata[this.state.banIndex].enable = 1; //设置列表中当前操作对象的状态为可用
                    this.setState({
                        show : false,
                        btnLoading : false,
                        listdata
                    })
                    message.success("激活成功");
                }
            }).catch(e=>{
                message.error("激活失败");
            })
        }
    }
    render(){
        let {listdata,inspectorStatus,show,userdetails} = this.state;
        return (
            <UserWrap>
                <div className="search">
                    账号状态：
                    <Select defaultValue="all" onChange={this.accountStatusChange.bind(this)}>
                        <Option value="all">全部</Option>
                        <Option value="1">可用</Option>
                        <Option value="0">不可用</Option>
                    </Select>
                    会员状态：
                    <Select defaultValue="all" onChange={this.memberStatusChange.bind(this)}>
                        <Option value="all">全部</Option>
                        <Option value="0">普通用户</Option>
                        <Option value="1">会员</Option>
                        <Option value="2">已过期</Option>
                    </Select>
                    机构：
                    <Input onChange={this.changeOrgName}/>
                    用户名：
                    <Input onChange={this.usernameChange}/>
                    <Button onClick={this.searchUser}>查询</Button>
                </div>
                <div className="userlist-table">
                    <Table columns={this.columns} dataSource={listdata} pagination={this.state.pagination} bordered={true} loading={this.state.tableloading} onChange={this.tableChange}/>
                </div>
                {/*设置|取消质检员*/}
                <div className="editInspector" style={{display:this.state.visible?"block":"none"}}>
                    <div className="mask"></div>
                    <div className="contentwrap">
                        <h3>提示</h3>
                        <div className="infoText">
                            <p>{inspectorStatus=="1"?"是否取消该用户质检员资格?":"是否确定将该用户设为质检员?"}</p>
                            <div>
                                <Button onClick={this.sureEditinspector}>确定</Button>
                                <Button onClick={this.cancelEditinspector}>取消</Button>
                            </div>
                        </div>
                    </div>
                </div>
                {/* 用户详情 */}
                <div className="userDetails" style={{display:show?"block":"none"}}>
                    <div className="mask"></div>
                    <div className="contentwrap" style={{top:userdetails.isException && userdetails.isException==1?"11%":"20%"}}>
                        <h3>用户详情<Icon type="close" onClick={this.closeDetails}/></h3>
                        <ul>
                            <li>用户名称：{userdetails.username}</li>
                            <li>用户邮箱：{userdetails.email}</li>
                            <li>创建时间：{userdetails.createdAt}</li>
                            <li className="line"></li>
                            <li>机构名称：{userdetails.organizationName}</li>
                            <li>账号状态：{userdetails.enable==1?"可用":(userdetails.enable==0?"不可用":userdetails.enable)}</li>
                            <li>会员状态：{userdetails.vipStatus}</li>
                            <li>到期时间：{userdetails.endDate}</li>
                            <li className="line"></li>
                            <li>
                                最近登录：
                                <div style={{maxHeight:208,overflowY:"auto",overflowX:"hidden"}}>
                                <table>
                                    <tbody>
                                        <tr><td>登录时间</td><td>登录IP</td><td>登陆地址</td></tr>
                                        {
                                            userdetails.loginRecords && userdetails.loginRecords.map((item,i)=>{
                                                return (
                                                    <tr key={i}><td>{item.loginTime}</td><td>{item.loginIp}</td><td>{item.address?item.address:"--"}</td></tr>
                                                )
                                            })
                                        }
                                    </tbody>
                                </table>
                                </div>
                                <div className="abnormal-operation" style={{display:userdetails.isException && userdetails.isException==1?"block":"none"}}>
                                    <img src={require("assets/images/abnormal.png")} alt="异常" />
                                    提醒：该账号异常登录，是否对其进行封号？
                                    <RadioGroup onChange={this.handleBan} defaultValue="yes">
                                        <Radio value="yes">是</Radio>
                                        <Radio value="no">否</Radio>
                                    </RadioGroup>
                                    <Button onClick={this.saveoperation} loading={this.state.btnLoading}>保存</Button>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </UserWrap>
        )
    }
}

export default UserList;