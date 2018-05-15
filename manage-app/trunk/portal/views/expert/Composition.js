import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import {
    Form, Upload, Input, message, Icon, Cascader,
    Select, Row, Col, Button, DatePicker,
    Modal, Spin
} from 'antd';
import data from './cityData.js';
import moment from 'moment';
import './modal.scss';
import { get, post } from '../utils/request';
const FormItem = Form.Item;
const Option = Select.Option;
const MonthPicker = DatePicker.MonthPicker;

export default class ExpertForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            periodical: [],
            visible: false,
            checkList: [],
            checkAll: false,
            compositions: [],
            loading: false,
            bloading: false
        }
    }

    componentDidMount() {
        this.getComposition(this.props.info.expertId);
    }

    componentWillReceiveProps(nextProps) {
        this.getComposition(nextProps.info.expertId);
    }

    getComposition(id) {
        get(`expert/composition/selectCompositionByExpertId/${id}`)
            .then((res) => {
                let checkList = [];
                if (res.meta.success) {
                    res.data.map((item, i) => {
                        checkList.push(item.compositionLink - 0);
                    })

                    this.setState({
                        checkList,
                        compositions: res.data
                    })
                }
            })
    }

    handleCheck(item, i, isCheck) {
        let { checkList, periodical } = this.state;
        item.isCheck = !isCheck;
        const index = checkList.findIndex((val) => val == item.contentId);
        if (index >= 0) {
            checkList = [
                ...checkList.slice(0, index),
                ...checkList.slice(index + 1)
            ]
        } else {
            checkList.push(item.contentId)
        }
        const list = [
            ...periodical.slice(0, i),
            item,
            ...periodical.slice(i + 1)
        ]
        this.setState({
            periodical: list,
            checkList,
            checkAll: (checkList.length === periodical.length)
        })
    }

    handleCheckAll() {
        let { checkAll, periodical } = this.state;
        let checkList = [];
        checkAll = !checkAll;
        if (checkAll) {
            periodical.map((item, i) => {
                item.isCheck = true;
                checkList.push(item.contentId);
            })
        } else {
            periodical.map((item, i) => {
                item.isCheck = false;
            })
        }
        this.setState({
            checkAll,
            periodical,
            checkList
        })
    }

    handleCancel = () => {
        this.setState({
            visible: false,
            loading: false
        });
    }

    edit = () => {
        let { checkList } = this.state;
        let checks = [];
        this.setState({
            loading: true
        })
        get(`expert/composition/selectPeriodicalByExpertId/${this.props.info.expertId}`)
            .then((res) => {
                if (res.meta.success && res.data.length > 0) {
                    res.data.map((item, i) => {
                        if (checkList.length == 0) {
                            item.isCheck = true;
                            checks.push(item.contentId);
                        } else {
                            if (checkList.find((val) => val == item.contentId)) {
                                item.isCheck = true;
                            }
                        }
                    })
                    checkList = checks.length > 0 ? checks : checkList;
                    this.setState({
                        visible: true,
                        periodical: res.data,
                        checkList,
                        loading: false,
                        checkAll: (checkList.length === res.data.length)
                    })
                } else {
                    this.setState({
                        loading: false
                    })
                    message.warning('暂无文献！')
                }
            })
    }

    save = () => {
        const data = { "ids": this.state.checkList.join(',') };
        this.setState({
            bloading: true
        });
        post(`expert/composition/insertComposition?expertId=${this.props.info.expertId}`, this.state.checkList.length > 0 ? data : {})
            .then((res) => {
                if (res && res.meta && res.meta.success) {
                    message.success('著作编辑成功');
                    this.handleCancel();
                    this.getComposition(this.props.info.expertId);
                }
                this.setState({
                    bloading: false
                });
            })
    }

    render() {
        const { imageUrl, realName, birthday, nation, duty, professionalField, expertDesc } = this.props.info;
        const { periodical, checkList, checkAll, compositions } = this.state;
        return (
            <div className="expertComp">
                <div className="expertInfo clearfix">
                    <div className="left logo">
                        <img src={imageUrl} alt="" />
                    </div>
                    <div className="left info">
                        <div className="name">{realName}</div>
                        <div className="birth">出生年月：{birthday}</div>
                        <div className="nation">民族：{nation}</div>
                        <div className="duty">主要成就：{duty}</div>
                        <div className="professionalField">专业领域：{professionalField}</div>
                        <div className="expertDesc">专家简介：</div>
                        <div className="descContent">{expertDesc}</div>
                    </div>
                    <Button className="edit" onClick={this.edit} size='large' type="primary">编辑</Button>
                </div>
                {compositions.length > 0 ?
                    <div className="composition">
                        <table>
                            <tbody>
                                <tr>
                                    <td width="35%">著作名称</td>
                                    <td width="65%">链接URL</td>
                                </tr>
                            </tbody>
                            {compositions.map((item, i) => (
                                <tbody key={i}>
                                    <tr>
                                        <td width="35%">{item.compositionName}</td>
                                        {/*地址要改  */}
                                        <td width="65%">{`http://www.cjpress.com.cn:8181/bdportal/#/classify/1/journal/${item.compositionLink}`}</td>
                                    </tr>
                                </tbody>))}
                        </table>
                    </div> : null
                }

                {/*购买弹出框*/}
                <Spin spinning={this.state.loading} size="large">

                    <Modal title={[
                        <img key={10} src={require('assets/images/edit-icon.png')} style={{ float: 'left', width: '20px' }} />,
                        <p key={11} style={{ fontSize: '16px', color: 'white', marginLeft: '30px' }}>编辑</p>
                    ]}
                        visible={this.state.visible}
                        maskClosable={true}
                        onCancel={this.handleCancel.bind(this)}
                        footer={[
                            <div key={12}>
                                <Button onClick={this.save} size="large" type="primary" loading={this.state.bloading}>保存</Button>
                                <Button onClick={this.handleCancel} size="large" type="default">取消</Button>
                            </div>
                        ]}
                        wrapClassName='buybox-modal'>
                        <div className="buy">
                            <div className={`list-item`}>
                                <span className="checkbox" onClick={this.handleCheckAll.bind(this)}>
                                    {checkAll ?
                                        <img src={require('assets/images/select.png')} alt="" />
                                        : null
                                    }
                                </span>
                                <span className="listname">全选</span>
                            </div>
                        </div>
                        <div className="allList">
                            {periodical && periodical.length > 0 ?
                                periodical.map((item, i) => {
                                    const isCheck = item.isCheck ? item.isCheck : false;
                                    return (
                                        <div className={`list-item`} key={i}>
                                            <span className='checkbox' onClick={this.handleCheck.bind(this, item, i, isCheck)}>
                                                {isCheck ?
                                                    <img src={require('assets/images/select.png')} alt="" />
                                                    : null
                                                }
                                            </span>
                                            <span className="listname">{item.title}</span>
                                        </div>
                                    )
                                })
                                : <div>暂无文献</div>
                            }
                        </div>
                    </Modal>
                </Spin>

            </div>
        )
    }
}