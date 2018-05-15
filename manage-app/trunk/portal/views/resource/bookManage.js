import React from 'react';
import { Link, hashHistory } from 'react-router';
import { Wrapper, Box } from './bookStyle.js';
import './style.css';
import { Select, Button, Input, Icon, Pagination, Popconfirm, message, Modal, Upload, Spin } from 'antd';
import { get, post } from 'utils/request.js';
import {
    SubEmiter,
    Emiter
} from 'utils';
import config from '../../config/index.js';
const Option = Select.Option;

export default class BookManage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            list: [],
            firstDir: [],
            secondDir: [{ menuId: '-1', menuName: '全部' }],
            first: '-1',
            second: '-1',
            channelId: 0,
            currentPage: 1,
            total: 0,
            pageSize: 10,
            classList: [],
            expertClassify: '',
            keyword: '',
            visible: false,
            fileList: [],
        }
    }

    componentDidMount() {
        this.getData();
        this.getClass();
        this.queryRootMenu();
    }

    queryRootMenu = () => {
        get('book/menu/queryRoot')
            .then((jsonData) => {
                this.setState({
                    firstDir: [{ menuId: '-1', menuName: '全部' }, ...(jsonData.data || [])]
                });
            })
    }

    getClass = () => {
        get(`book/channel/queryAll`)
            .then((res) => {
                if (res && res.meta && res.meta.success) {
                    this.setState({
                        classList: res.data
                    })
                }
            })
    }

    querySecondDir = (parentMenuId, menuId) => {
        get(`book/menu/queryChild/${parentMenuId}`)
            .then((jsonData) => {
                const data = jsonData.data;
                this.setState({
                    secondDir: [{ menuId: '-1', menuName: '全部' }, ...(jsonData.data || [])],
                    second: '-1',
                    first: parentMenuId
                });
            })
    }

    onSelect = (value, option) => {
        this.querySecondDir(value);
    }

    onSelect2 = (value, option) => {
        this.setState({
            second: value
        })
    }

    add = () => {
        let params = {
            operName: 'book-new',
            id: null
        };
        Emiter.emit('openPanel', params);
    }

    getData = (pageNum = 1) => {
        const { channelId, first, second, keyword } = this.state;
        let url = `book/book/queryBy?${Date.parse(new Date())}&pageNum=${pageNum}&pageSize=${this.state.pageSize}${first != -1 ? `&parentMenuId=${first}` : ''}${second != -1 ? `&childMenuId=${second}` : ''}${keyword ? `&keyword=${keyword}` : ''}${channelId != 0 ? `&channelId=${channelId}` : ''}`;

        // 作者角色的用户进去需要根据realName过滤
        const role = localStorage.getItem('brole');
        const realName = localStorage.getItem('brealName');
        if (role === 'ROLE_AUTHOR') {
            url += `&realname=${realName}`;
        }

        get(url)
            .then((res) => {
                if (res.meta.success) {
                    const data = res.data;
                    this.setState({
                        list: data.list || [],
                        currentPage: data.pageNum,
                        total: data.total
                    })
                }
            })
    }

    keyword = (e) => {
        this.setState({
            keyword: e.target.value
        })
    }

    handleChange = (val) => {
        this.setState({
            channelId: val
        })
    }

    onPageChange = (num) => {
        this.getData(num);
    }

    showModal = () => {
        this.setState({
            visible: true,
            fileList: []
        })
    }

    handleCancel = () => {
        this.setState({
            visible: false
        })
    }

    handleFileChange = ({ file, fileList }) => {
        if (file.status == 'done') {
            if(file.response.meta.success){
                message.success('图书上传成功');
                this.getData();
            }else{
                message.error(file.response.meta.message);
            }
            this.handleCancel();
        } else if (file.status === 'error') {
            this.handleCancel();
            message.error(`图书上传失败`);
        }
    }

    beforeUpload = (file) => {
        const multiType = this.props.multiType;
        const filetypes = [".rar", ".zip"];
        let isFormat = false;
        if (file.name) {
            const fileend = file.name.substring(file.name.indexOf("."));
            for (let i = 0; i < filetypes.length; i++) {
                if (filetypes[i] == fileend) {
                    isFormat = true;
                    this.setState({
                        visible: true
                    })
                    break;
                }
            }
        }
        if (!isFormat) {
            message.error(`只能上传zip或rar格式文件!`);
        }
        return isFormat;
    }

    render() {
        const { classList } = this.state;
        const size = 'large';
        const props = {
            action: config.xmlUrl,
            onChange: this.handleFileChange,
            multiple: false,
            // fileList: this.state.fileList,
            beforeUpload: this.beforeUpload,
            //accept: '.xml'
        };
        return (
            <Wrapper>
                <div className="header clearfix">
                    <span className="label-name">图书分类：</span>
                    <Select className='select order-type' defaultValue="全部" size={size} style={{ width: 120 }} onChange={this.handleChange.bind(this)}>
                        <Option value={'0'}>{"全部"}</Option>
                        {
                            classList.map((item, i) => (
                                <Option key={item.channelId + ''} value={item.channelId + ''}>{item.channelName}</Option>
                            ))
                        }
                    </Select>
                    <span className="label-name">分类标签：</span>
                    <Select onSelect={this.onSelect} defaultValue="全部" size={size} style={{ width: 120, marginRight: '10px' }}>
                        {
                            this.state.firstDir.map((item, index) => (
                                <Option key={index} value={item.menuId + ''}>{item.menuName}</Option>
                            ))
                        }
                    </Select>
                    <Select value={this.state.second} size={size} style={{ width: 180 }} onSelect={this.onSelect2}>
                        {
                            this.state.secondDir.map((item, index) => (
                                <Option key={index} value={item.menuId + ''}>{item.menuName}</Option>
                            ))
                        }
                    </Select>
                    <span className="label-name">书名：</span>
                    <Input onChange={this.keyword} size={size} style={{ width: 180 }}></Input>
                    <Button className="search label-name" type="primary" onClick={this.getData.bind(this, 1)} size={size}>查询</Button>
                    <Button className="addbtn right" type="primary" onClick={this.add} size={size}><Icon type="plus" />新增</Button>
                    <Upload className="search right" name='file' {...props} showUploadList={false}>
                        <Button size="large" type="primary">
                            <Icon type="upload" />上传XML图书
                        </Button>
                    </Upload>
                    {/* <Button className="search right" type="primary" size={size} onClick={this.showModal}>上传XML图书</Button> */}
                </div>

                <SubEmiter eventName="bookRefresh" listener={this.getData}></SubEmiter>
                {
                    this.state.list.length > 0 ?
                        <div>
                            <div className='table'>
                                {
                                    this.state.list.map((item, index) => (
                                        <Content key={index} {...item} />
                                    ))
                                }
                            </div>
                            <Pagination className="pagenation" showQuickJumper current={this.state.currentPage} total={this.state.total} pageSize={this.state.pageSize} onChange={this.onPageChange} />
                        </div>
                        :
                        <div className="noData">
                            <img src={require('assets/images/no-data.png')} alt="" />
                        </div>
                }
                <Modal title={[
                    
                ]}
                    visible={this.state.visible}
                    footer={[
                    ]}
                    wrapClassName='uploading-box-modal'
                    onCancel={this.handleCancel}
                    closable={false}
                    maskClosable={false}>
                    <div>
                        <Spin tip="上传中" size="large"/>
                    </div>
                </Modal>
            </Wrapper>
        );
    }
}

class Content extends React.Component {
    constructor(props) {
        super(props);
    }

    handleRelease = (id, isRelease) => {
        isRelease = isRelease ? 1 : 0;
        post(`book/book/updateRelease?bookId=${id}&isRelease=${isRelease}`)
            .then((res) => {
                if (res && res.meta && res.meta.success) {
                    const msg = isRelease ? '发布成功' : '下架成功';
                    message.success(msg);
                    Emiter.emit("bookRefresh")
                } else {
                    if (res && res.meta && res.meta.message) {
                        message.error(res.meta.message);
                    }
                }
            })
    }

    openModify = (id, info, event) => {
        let params = {
            operName: 'book-modify',
            id,
            info
        };
        Emiter.emit('openPanel', params);
    }

    del = () => {
        get(`book/book/remove/${this.props.isbn}`)
            .then((data) => {
                if (data.meta.success) {
                    message.success('删除成功')
                    Emiter.emit("bookRefresh")
                }
            })
    }

    render() {
        const { bookId, bookName, fullPrice, releaseDate, bookDesc, bookImages, isRelease, updatedAt } = this.props;
        const index = bookImages.findIndex((val, i) => val.imageScene == '1');
        const bookImage = index >= 0 ? bookImages[index].imageUrl : require('assets/images/no-img.png');
        const info = Object.assign({}, this.props, { bookImage: bookImage });
        return (
            <Box className="bookItem">
                <div className='box-head'>
                    <div className="left head-img">
                        <img src={bookImage} alt="" />
                    </div>
                    <div className="left info">
                        <div className='content-head'>{bookName}</div>
                        <div className="fullPrice">售价:￥<span className="priceNum">{fullPrice}</span></div>
                        <div className="time">更新:{updatedAt}</div>
                        <div className='content-desc'>内容简介:{bookDesc && bookDesc.length < 30 ? bookDesc : bookDesc && bookDesc.replace(/<\/?[^>]*>/g,'').substr(0, 30) + '...' || '暂无内容简介'}</div>
                    </div>
                </div>
                <div className='foot'>
                    <Button type="default" onClick={this.handleRelease.bind(this, bookId, !isRelease)}>{isRelease ? '下架' : '发布'}</Button>
                    <Button onClick={this.openModify.bind(this, bookId, info)} type="default">修改</Button>
                    <Popconfirm title="是否删除该图书?" onConfirm={this.del} okText="是" cancelText="否">
                        <Button type="default">删除</Button>
                    </Popconfirm>
                    {/* <Button onClick={this.delete.bind(this, bookId)}>删除</Button> */}
                </div>
            </Box>
        )
    }
}