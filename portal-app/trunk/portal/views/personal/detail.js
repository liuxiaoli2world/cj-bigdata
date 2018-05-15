import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon,Pagination,Breadcrumb} from 'antd';
import Wrapper from './Wrapper.js';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post} from 'util/request.js';

export default class IndentDetail extends React.Component{
	constructor(props){
		super(props);
		this.state={
           humanityList:{},
		}
	}

	componentDidMount(){
       get(`order/order/query?orderId=${this.props.params.id}&type=${this.props.params.type}`)
       .then(res=>{
            if(res && res.data){
                this.setState({
                    humanityList : res.data || {}
                })
            }
       })
	}

	render(){
        const {humanityList} = this.state
		return(
			<Wrapper>
				<div className="indent_head">
                    <img src={require('assets/images/personal/config1.png')}/>
                    <div>订单详情</div>
                </div>
                <div className="basic_information">
                    <h6>基本信息</h6>
                    <table>
                        {
                             this.props.params.type == "book"?
                             <tbody>
                                <tr>
                                    <td className="table_property">订单号</td>
                                    <td className="table_container">{humanityList.orderNo}</td>
                                </tr>
                                <tr>
                                    <td className="table_property">提交时间</td>
                                    <td className="table_container">{humanityList.createdAt}</td>
                                </tr>
                                <tr>
                                    <td className="table_property">订单金额</td>
                                    <td className="table_container">{humanityList.amount}</td>
                                </tr>
                                <tr>
                                    <td className="table_property">支付状态</td>
                                    <td className="table_container">{humanityList.payStatus==0?"待支付":"支付成功"}</td>
                                </tr>
                            </tbody>
                            :
                            <tbody>
                                <tr>
                                    <td className="table_property">订单号</td>
                                    <td className="table_container">{humanityList.orderNo}</td>
                                </tr>
                                <tr>
                                    <td className="table_property">提交时间</td>
                                    <td className="table_container">{humanityList.createdAt}</td>
                                </tr>
                                <tr>
                                    <td className="table_property">订单金额</td>
                                    <td className="table_container">{humanityList.amount}</td>
                                </tr>
                                <tr>
                                    <td className="table_property">支付状态</td>
                                    <td className="table_container">{humanityList.payStatus==0?"待支付":"支付成功"}</td>
                                </tr>
                                <tr>
                                    <td className="table_property">账户信息</td>
                                    <td className="table_container">{humanityList.email}</td>
                                </tr>
                                <tr>
                                    <td className="table_property">购买用户</td>
                                    <td className="table_container">{humanityList.username}</td>
                                </tr>
                                
                            </tbody>
                        }
                        
                    </table>
                </div>
                <div className="buy_section">
                    <h6>{this.props.params.type == "book"?"购买章节":"会员信息明细"}</h6>
                    <table>
                        {
                            this.props.params.type == "book"?
                            <tbody>
                                {
                                    humanityList.bookDirNames && humanityList.bookDirNames.map((item,i)=>{
                                        return(
                                            <tr key={i}>
                                                <td className={i%2==0?'':'odd'}>{item.dirName}</td>
                                            </tr>
                                        )
                                    })
                                }
                            </tbody>
                            :
                            <tbody>
                                <tr>
                                    <td>购买时长：{humanityList.num}{humanityList.duration=="1"?"个月":"年"}</td>
                                </tr>
                            </tbody>
                        }
                        
                    </table>
                </div>
			</Wrapper>
		)
	}
}