import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import './style.css';
import { Select, Icon, Button, Input, Tree } from 'antd';
import { get, post } from 'utils/request.js';
import {
    SubEmiter,
    Emiter
} from 'utils';

const TreeNode = Tree.TreeNode;

export default class BookList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            list: [],
            selectedKeys: []
        }
    }

    componentDidMount() {
        this.getList(this.props.isbn);
    }

    componentWillReceiveProps(nextProps) {
        this.getList(nextProps.isbn)
    }

    getList(isbn) {
        get(`book/bookdir/queryAllByIsbn/${isbn}`)
            .then((res) => {
                if (res.meta.success) {
                    const list = this.processMenu(res.data, 0);
                    this.setState({
                        list
                    })
                    Emiter.emit('resetForm');
                    this.onSelect([],{})
                }
            })
    }

    findFirst(data) {
        if(!data[0].children){
            return data[0];
        }else{
            return this.findFirst(data[0].children)
        }
    }

    processMenu(data, pid) {
        let result = [];
        let temp;
        for (let i = 0; i < data.length; i++) {
            if (data[i].parentBookDirId == pid) {
                let obj = {
                    dirName: data[i].dirName,
                    id: data[i].bookDirId,
                    parentId: data[i].parentBookDirId,
                    dirDesc: data[i].dirDesc,
                    hasLeaf: false
                };
                temp = this.processMenu(data, data[i].bookDirId);
                if (temp.length > 0) {
                    obj.children = temp;
                    obj.hasLeaf = true;
                }
                result.push(obj);
            }
        }
        return result;
    }

    onSelect = (id, e) => {
        let params = {
            id,
            isbn: this.props.isbn,
            isLeaf: e.hasOwnProperty("isLeaf")  ? e.isLeaf : 
                    (e.selectedNodes && e.selectedNodes[0]) ? e.selectedNodes[0].props.isLeaf : '',
            parentId: (e.selectedNodes && e.selectedNodes[0]) ? e.selectedNodes[0].props.parentId : 0,
        }
        this.setState({
            selectedKeys: id || []
        })
        const { isEdit } = this.props;
        const eventName = this.props.isEdit ? 'addList' : 'editList';
        Emiter.emit(eventName, params);
    }

    addNew = (id) => {
        let params = {
            parentId : id,
            id: [],
            isbn: this.props.isbn
        }
        Emiter.emit('addList', params);
    }

    render() {
        const { list, selectedKeys } = this.state;
        const buttonStyle = {
            height: '32px',
            width: '90px',
            fontSize: '14px',
            marginTop: 'calc(17px / 2)',
            borderRadius: '2px'
        }
        const spanStyle = {
            display: 'inline-block',
            margin: '4px 0 0 8px'
        }
        const loop = data => data.map((item) => {
            if (item.children) {
                return <TreeNode
                    title={<span><img style={{ verticalAlign: 'top' }} src={require('assets/images/folder.png')} alt="" /><span style={spanStyle}>{item.dirName}</span></span>}
                    key={item.id}
                    parentId={item.parentId}
                    isLeaf={item.hasLeaf}>{loop(item.children)}
                </TreeNode>;
            }
            return <TreeNode
                title={<span><img style={{ verticalAlign: 'top' }} src={require('assets/images/file.png')} alt="" /><span style={spanStyle}>{item.dirName}</span></span>}
                key={item.id}
                parentId={item.parentId}
                isLeaf={item.hasLeaf} />;
        });
        return (
            <div>
                <SubEmiter eventName="addListOk" listener={this.getList.bind(this,this.props.isbn)} />
                <div className="listTitle clearfix">
                    <div className="left title">总目录</div>
                    {this.props.isEdit ?
                        <Button className="add right" type="primary" style={buttonStyle} onClick={this.addNew.bind(this, selectedKeys)}><Icon type="plus" />新增</Button>
                        : null
                    }
                </div>
                {list.length>0 ?
                <Tree onSelect={this.onSelect}
                    selectedKeys={selectedKeys}
                >
                    {loop(list)}
                </Tree>
                : 
                <div className="noData">
                    <img src={require('assets/images/no-data.png')} alt=""/>
                </div>
                }
            </div>
        );
    }
}