import React from "react";
import {Link,hashHistory} from "react-router";
import {get,post} from "utils/request.js";
import {SubEmiter,Emiter} from "utils/index.js";
import {Input,Button,Table,Icon,Form,Popconfirm,message} from "antd";
import {OrgWrap} from "./style.js";

const FormItem = Form.Item;

class InfoFormWrap extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            formlist : [],
            loading : false
        }
    }

    componentWillReceiveProps(props){
        if(props.type == "orgadd" || props.type == "orgedit"){
            let num = 0;
            let name = "";
            num = props.type == "orgadd" ? props.orgnum : props.editInfo && props.editInfo.seqNum;
            name = props.type == "orgadd" ? "" : props.editInfo && props.editInfo.name;
            this.setState({
                formlist : [
                    {label:"机构名称",errormsg:"机构名称不能为空！",required:true,id:"orgName",el:<Input />,initialValue:name},
                    {label:"机构编号",errormsg:"",required:false,id:"orgNumber",el:<Input disabled={true}/>,initialValue:num},
                ]
            })
            if(!props.reset){
                this.props.form.resetFields();
            }
        }else if(props.type == "user"){
            this.setState({
                formlist : [
                    {label:"账号前缀",errormsg:"账号前缀不能为空！",required:true,id:"accountPrefix",el:<Input disabled={props.accountPrefix?true:false}/>,initialValue:props.accountPrefix?props.accountPrefix:""},
                    {label:"用户数量",errormsg:"用户数量不能为空！",required:true,id:"userNum",el:<Input />},
                ],
                orgid : props.orgId,
            })
            if(!props.see){
                this.props.form.resetFields();
            }
        }
    }
    
    //改变机构编号
    changeNum = (e)=>{
        e.preventDefault();
        let text = "";
        let s = Math.floor(Math.random()*9)+1;
        for(var i=0;i<4;i++){
            text += Math.floor(Math.random()*10)
        }
        this.props.form.setFieldsValue({
            orgNumber :s + text
        })
    }

    //保存
    handleSubmit = (e)=>{
        e.preventDefault();
        this.setState({ loading : true })
        this.props.form.validateFields((err, values) => {
            if (!err) {
                //新增机构
                if(this.props.type=="orgadd"){
                    let org = {
                        name : values.orgName,
                        seqNum :　parseInt(values.orgNumber)
                    }
                    //判断机构编号是否存在
                    get(`user/organization/selectBySqeNum?seqNum=${org.seqNum}`)
                    .then(res=>{
                        if(res && res.meta && res.meta.success){
                            message.error("该机构编号已存在，请更换！")
                            this.setState({ loading : false })
                            return false;
                        }else{
                            post(`user/organization/add`,org)
                            .then(res=>{
                                if(res && res.meta && res.meta.success){
                                    this.props.callbackparent("success");
                                    this.props.form.resetFields();
                                    message.success("添加成功");
                                    this.setState({ loading : false })
                                }else{
                                    message.error("添加失败");
                                    this.setState({ loading : false })
                                }
                            }).catch(e=>{
                                console.log(e)
                                message.error("添加失败");
                                this.setState({ loading : false })
                            })
                        }
                    }).catch(e=>{
                        console.log(e);
                        message.error("服务超时");
                    })
                //修改机构信息
                }else if(this.props.type=="orgedit"){
                    post(`user/organization/modify?id=${this.props.editInfo && this.props.editInfo.organizationId}&name=${values.orgName}`)
                    .then(res=>{
                        if(res && res.meta && res.meta.success){
                            this.props.callbackparent("success");
                            this.props.form.resetFields();
                            message.success("修改成功");
                            this.setState({ loading : false })
                        }else{
                            message.error("修改失败");
                            this.setState({ loading : false })
                        }
                    }).catch(e=>{
                        console.log(e)
                        message.error("修改失败");
                        this.setState({ loading : false })
                    })
                }else{
                //批量新增
                    get(`user/organization/insertbatch?preName=${values.accountPrefix}&num=${values.userNum}&id=${this.state.orgid}`)
                    .then(res=>{
                        if(res && res.meta && res.meta.success){
                            this.props.callbackparent("success");
                            this.props.form.resetFields();
                            message.success("批量新增成功");
                            this.setState({ loading : false })
                        }else{
                            message.error("批量新增失败");
                            this.setState({ loading : false })
                        }
                    }).catch(e=>{
                        console.log(e)
                        message.error("批量新增失败");
                        this.setState({ loading : false })
                    })
                }
            }else{
                this.setState({ loading : false })
            }
        });
    }


    render(){
        const { getFieldDecorator} = this.props.form;
        const {formlist} = this.state;
        return (
            <Form onSubmit={this.handleSubmit.bind(this)} className="info-form">
                {
                    formlist.map((item,i)=>{
                        return (
                            <FormItem label={item.label} key={i}>
                                {getFieldDecorator(item.id, {
                                    initialValue : item.initialValue ? item.initialValue : "",
                                    rules: [
                                        { required: item.required, message: item.errormsg },
                                    ],
                                })(
                                    item.el
                                )}
                                <a href="javascript:void(0);" style={{display:item.label=="机构编号" && this.props.type=="orgadd"?"inline-block":"none"}} onClick={this.changeNum}>换一个</a>
                            </FormItem>
                        )
                    })
                }
                <FormItem>
                    <Button type="primary" htmlType="submit" loading={this.state.loading}>保存</Button>
                </FormItem>
            </Form>
        )
    }
}

const InfoForm = Form.create()(InfoFormWrap);

class OrgManage extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            dataSource : [],      //机构列表
            pagination : {
                total : 1,
                pageSize : 7,
                showQuickJumper : true
            },
            orgAdd : false,      //机构新增窗口是否可见
            userAdd : false,     //批量新增用户窗口是否可见
            see : false ,        //用户详情-无会员状态窗口是否可见
            tableloading : true,
            searchName : ""      //查询的机构名称
        }
        this.columns = [
            {
                title:"机构名称",
                dataIndex:"name",
                key:"name",
                width:`${350/16.2}%`
            },
            {
                title:"机构编号",
                dataIndex:"seqNum",
                key:"seqNum",
                width:`${350/16.2}%`
            },
            {
                title:"机构用户数量",
                dataIndex:"countUser",
                key:"countUser",
                width:`${316/16.2}%`
            },
            {
                title:"操作",
                dataIndex:"action",
                key:"action",
                render:(text,record)=>{
                    return (
                        <div>
                            <Button onClick={this.showUserdetails.bind(this,record.countUser,record.seqNum,record.organizationId)}>用户详情</Button>
                            <Button onClick={this.showUseradd.bind(this,record.organizationId,record.seqNum)}>批量新增</Button>
                            <Button onClick={this.editOrg.bind(this,record)}>修改</Button>
                            <Popconfirm title="是否删除该机构?" onConfirm={this.confirm.bind(this,record.organizationId)} okText="是" cancelText="否">
                                <Button>删除</Button>
                            </Popconfirm>
                        </div>
                    )
                }
            },
        ]
    }

    //获取机构列表
    getOrglist(pagenum,name){
        get(`user/organization/queryAll?pageNum=${pagenum}&pageSize=7${name?`&name=${name}`:''}`)
        .then(res=>{
            if(res && res.data){
                let {pagination} = this.state;
                pagination.total = res.data.total;
                pagination.current = pagenum;
                res.data.list && res.data.list.map((item,i)=>{
                    item.key = i
                })
                this.setState({
                    dataSource : res.data.list || [],
                    tableloading : false,  
                    pagination,
                    beforeName : name
                })
            }
        }).catch(e=>{
            this.setState({
                dataSource : [],
                tableloading : false
            })
        })
    }

    componentDidMount(){
        this.getOrglist(1)
    }

    //机构名称变化
    changeName = (e)=>{
        this.setState({ searchName : e.target.value})
    }

    //查询
    handleSearch = ()=>{
        this.setState({
            tableloading : true
        })
        this.getOrglist(1,this.state.searchName);
    }

    //表格变化
    handleTablechange = (page,filters,sorter)=>{
        this.setState({
            tableloading : true
        })
        this.getOrglist(page.current,this.state.beforeName);
    }

    //查看用户详情
    showUserdetails = (usernum,orgnum,id)=>{
        if(usernum == 0){
            this.setState({ see : true, seeid:id,seeOrgnum:orgnum})
        }else{
            Emiter.emit("addUserdetail",{"see":true,"orgnum":orgnum});
        }
    }
    //关闭用户详情
    closeDetails = ()=>{
        this.setState({ see : false })
    }

    //新增机构
    showAddorg = ()=>{
        let text = "";
        let s = Math.floor(Math.random()*9)+1;
        for(var i=0;i<4;i++){
            text += Math.floor(Math.random()*10)
        }
        this.setState({
            orgAdd : true,          
            type : "orgadd",
            orgnum : s + text,         //初始机构编号
        })
    }
    //批量设置用户
    showUseradd = (id,orgnum)=>{
        this.setState({
            userAdd : true,
            see : false ,
            type : "user",
            orgId : id,
        })
        //获取机构前缀
        get(`user/user/selectPreNumById?sqeNum=${orgnum}`)
        .then(res=>{
            if(res && res.data){
                this.setState({
                    accountPrefix : res.data || ""
                })
            }else{
                this.setState({ accountPrefix : "" })
            }
        }).catch(e=>{
            this.setState({ accountPrefix : ""})  
        })
    }
    //修改机构
    editOrg = (param)=>{
        this.setState({
            orgEdit : true,
            type : "orgedit",
            editInfo : param,
        })
    }
    //关闭新增机构
    closeOrgadd = ()=>{
        if(this.state.orgAdd){
            this.setState({ orgAdd : false })
        }else{
            this.setState({ orgEdit : false })
        }
    }
    //关闭批量设置用户
    closeUseradd = ()=>{
        this.setState({ userAdd : false , accountPrefix:"" })
    }
    //删除机构
    confirm = (id)=>{
        get(`user/organization/remove/${id}`)
        .then(res=>{
            if(res && res.meta && res.meta.success){
                let {pagination,beforeName} = this.state; 
                this.getOrglist(pagination.current,beforeName);
                message.success("删除成功");
            }
        }).catch(e=>{
            message.error("服务超时，删除失败");
        })
    }

    //回调函数
    refreshlist = (param)=>{
        let {pagination,beforeName} = this.state;
        if(param == "success"){
            this.getOrglist(pagination.current,beforeName);
             if(this.state.orgAdd){
                this.setState({ orgAdd : false, })
             }else if(this.state.orgEdit){
                this.setState({ orgEdit : false, })
             }else if(this.state.userAdd){
                this.setState({ userAdd : false ,accountPrefix:""})
             }  
        }
    }

    render(){
        let {dataSource,orgAdd,orgEdit,pagination,see} = this.state;
        return (
            <OrgWrap>
                <div className="head">
                    机构名称：<Input onChange={this.changeName}/>
                    <Button onClick={this.handleSearch}>查询</Button>
                    <Button onClick={this.showAddorg}><img src={require("assets/images/add.png")} alt="新增"/>新增</Button>
                </div>
                {/* 机构列表 */}
                <div className="org-table">
                    <Table dataSource={dataSource} columns={this.columns} pagination={pagination} bordered={true} loading={this.state.tableloading} onChange={this.handleTablechange}/>
                </div>
                {/* 机构新增 */}
                <div className="add-org" style={{display:orgAdd || orgEdit?"block":"none"}}>
                    <div className="mask"></div>
                    <div className="org-info">
                        <h3 className="title">机构{orgEdit?"修改":"新增"}<Icon type="close" onClick={this.closeOrgadd}/></h3>
                        <div><InfoForm type={this.state.type} callbackparent={this.refreshlist} orgnum={this.state.orgnum} editInfo={this.state.editInfo} reset={this.state.orgAdd || this.state.orgEdit}/></div>
                    </div>
                </div>
                {/* 批量增加用户 */}
                <div className="add-user" style={{display:this.state.userAdd?"block":"none"}}>
                    <div className="mask"></div>
                    <div className="account-info">
                        <h3 className="title">批量增加用户<Icon type="close"  onClick={this.closeUseradd}/></h3>
                        <div>
                            <InfoForm type={this.state.type} orgId={this.state.orgId} callbackparent={this.refreshlist} accountPrefix={this.state.accountPrefix} see={this.state.userAdd}/>
                        </div>
                    </div>
                </div>
                {/* 用户详情-无会员 */}
                <div className="user-details" style={{display:see?"block":"none"}}>
                    <div className="mask"></div>
                    <div className="info-msg">
                        <h3 className="title">提示<Icon type="close" onClick={this.closeDetails}/></h3>
                        <div>
                            <p>暂无用户信息，您需要</p>
                            <Button className="add-btn" onClick={this.showUseradd.bind(this,this.state.seeid,this.state.seeOrgnum)}>批量新增</Button>
                            <Button onClick={this.closeDetails}>取消</Button>
                        </div>
                    </div>
                </div>
            </OrgWrap>
        )
    }
}

export default OrgManage;