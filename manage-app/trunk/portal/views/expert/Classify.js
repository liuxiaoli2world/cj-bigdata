import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import { Button, Input, Icon, Pagination, Popconfirm, message ,Table} from 'antd';
import {SubEmiter,Emiter} from '../utils/index';
import {get,post} from '../utils/request'

export default class Classify extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            list:[],
            currentPage:1,
            total:0,
            pageSize:10,
            keyword:''
        }
        this.columns = [{
            title:"编号",
            dataIndex : "num",
            key:"num",
            width:"12.8%",
        },{
            title:"分类名称",
            dataIndex : "name",
            key:"name",
            width:"25.2%",
        },{
            title:"分类简介",
            dataIndex : "domainDesc",
            key:"domainDesc",
            width:"40.3%"
        },{
            title:"操作",
            dataIndex : "operation",
            key:"operation",
            render:(text,record)=>{
                return (
                <div>
                    <Button className="btn modify" type="default" size='large' value="Submit" onClick={this.add.bind(this,record)}>修改</Button>
                    <Popconfirm title="是否删除专家分类?" onConfirm={this.onDel.bind(this,record.domainId)}  okText="是" cancelText="否" placement="topRight">
                        <Button className="btn del" type="default" size='large' value="Reset">删除</Button>
                    </Popconfirm>
                </div>)
            }
        }]
    }

    componentDidMount(){
        this.getData();
    }

    getData = () => {
        const {keyword,pageSize} = this.state;
        get(`expert/domain/selectAllDomain?pageNum=1&pageSize=${pageSize}${keyword?`&name=${keyword}`:''}`)
            .then((res) => {
                if(res.meta.success){
                    for(var i=0;i<res.data.list.length;i++){
                        res.data.list[i].num = i+1
                    }
                    this.setState({
                        list:res.data.list,
                        currentPage: res.data.currentPage,
                        total: res.data.total,
                        pageSize: res.data.pageSize
                    })
                }
            })
    }

    keyword = (e) => {
        this.setState({
            keyword: e.target.value
        })
    }
    onPageChange = () => {

    }

    onDel(id) {
        get(`expert/domain/deleteDomain/${id}`)
            .then((res) => {
                if(res.meta.success){
                    if(res.data=='删除成功!'){
                        message.success('专家分类删除成功');
                        this.getData();
                        Emiter.emit("refreshClass")
                    }else{
                        message.error(res.data)
                    }
                }else{
                    message.error('删除失败');
                }
            })
    }

    add(info,e) {
        let params = info ?
            {
                operName: 'class-modify',
                info
            } :
            {
                operName: 'class-new'
            };
        Emiter.emit('openPanel', params);
    }

    render() {
        const {list} = this.state;
        return (
            <div className="expertBox">
                <SubEmiter eventName="addClassOk" listener= {this.getData.bind(this)} />
                <div className="header clearfix">
					<span className="label-name">分类名称：</span>
					<Input onChange={this.keyword} size='large' style={{ width: 120 }}></Input>
                    <Button className="search" size='large' type="primary" onClick={this.getData}>查询</Button>
                    <Button className="addbtn right"  size='large' type="primary" onClick={this.add.bind(this,'')}><Icon type="plus" />新增</Button>
                </div>
                <div>
                    {/* <table>
                        <tbody>
                        <tr>
                            <td>编号</td>
                            <td>分类名称</td>
                            <td>分类简介</td>
                            <td>操作</td>
                        </tr>
                        </tbody>
                        {list.map((item,i)=>(
                        <tbody key={i}>
                            <tr>
                                <td>{++i}</td>
                                <td>{item.name}</td>
                                <td>{item.domainDesc}</td>
                                <td width='353px'>
                                    <Button className="btn modify" type="default" size='large' value="Submit" onClick={this.add.bind(this,item)}>修改</Button>
                                    <Popconfirm title="是否删除专家分类?" onConfirm={this.onDel.bind(this,item.domainId)}  okText="是" cancelText="否">
                                        <Button className="btn del" type="default" size='large' value="Reset">删除</Button>
                                    </Popconfirm>
                                </td>
                            </tr>
                        </tbody>))
                        }
                    </table> */}
                    <Table columns={this.columns} bordered dataSource={list} pagination={false}/>
                  {/* <Pagination style={{display:this.state.total>this.state.pageSize?'flex':'none',float:'right',padding:"20px 30px 28px 0"}}
                            current={this.state.current} 
                            showQuickJumper pageSize={this.state.pageSize} 
                            total={parseInt(this.state.total)} 
                            onChange={this.pageChange.bind(this)} /> */}
                </div>
            </div>
        );
    }
}
