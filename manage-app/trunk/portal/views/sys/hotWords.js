import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import './style.scss';
import { Button, Input, Form, message, Icon } from 'antd';
import { get, post } from 'utils/request.js';
import {
    SubEmiter,
    Emiter
} from 'utils';
const FormItem = Form.Item;

export default class HotWord extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            wordsList: []
        }
    }

    componentDidMount() {
        this.queryAllHotWords();
    }

    queryAllHotWords = () => {
        get('book/hotword/selectAllHotWord')
            .then(jsonData => {
                if (jsonData.meta && jsonData.meta.success) {
                    this.setState({
                        wordsList: jsonData.data || []
                    });
                }
            });
    }

    onChange = (e) => {
        this.setState({
            hotwordName: e.target.value
        })
    }

    addHotWord = () => {
        post('book/hotword/insertHotWord', { name: this.state.hotwordName })
            .then(jsonData => {
                if (jsonData.meta && jsonData.meta.success) {
                    this.queryAllHotWords();
                }
            });
    }
    deleteHotWord = (id) => {
        get(`book/hotword/deleteHotWord/${id}`, { name: this.state.hotwordName })
            .then(jsonData => {
                if (jsonData.meta && jsonData.meta.success) {
                    this.queryAllHotWords();
                }
            });
    }
    render() {
        return (
            <div className='hotWords'>
                <div className='head'>新增热词：<Input className='input' value={this.state.hotwordName} onChange={this.onChange} />
                    <Button className='btn' onClick={this.addHotWord}>保存</Button>
                </div>
                <div className='sepr'></div>
                <ul className='item-list'>
                    {
                        this.state.wordsList.map((item, index) => {
                            let background = '';
                            switch (index % 4) {
                                case 0:
                                    background = '#6f83b9';
                                    break;
                                case 1:
                                    background = '#ec8a54';
                                    break;
                                case 2:
                                    background = '#61a3e1';
                                    break;
                                case 3:
                                    background = '#50b975';
                                    break;
                                default:
                                    background = '#6f83b9';
                            }
                            return (<li key={index} className='item' style={{ background: background}}><span className='name'>{item.name}</span><span onClick={this.deleteHotWord.bind(this, item.hotWordId)}><Icon className='close' type='close' /></span></li>)
                        })
                    }
                </ul>
            </div>
        );
    }
}