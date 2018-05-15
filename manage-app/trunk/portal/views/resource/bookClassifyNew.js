import React from 'react';
import { Link, hashHistory } from 'react-router';
import styled from 'styled-components';
import { get, post } from 'utils/request.js';
import { getParams } from 'utils/commonTool.js';
import config from '../../config/index.js';
import {
    Form, Input, Select, Radio,
    Button, Upload, Icon, Row, Col
} from 'antd';
const FormItem = Form.Item;
const Option = Select.Option;
const RadioButton = Radio.Button;
const RadioGroup = Radio.Group;
import {
    SubEmiter,
    Emiter
} from 'utils';


const Wrapper = styled.div`
    .form {
        width: 1200px;
    }
`;

class MyForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            firstDir: [],
            secondDir: [],
            type: 'headline',
            oldId: ''
        };
    }
    componentDidMount() {
        this.refreshPage();
    }

    refreshPage = () => {
        const id = this.props.id;
        if(!id) {
            return;
        }

        get(`book/channel/query/${id}`)
        .then((jsonData) => {
            let {channelName, channelDesc} = jsonData.data;
            this.props.form.setFieldsValue({
                channelName, channelDesc
            });
        });
    }

    handleSubmit = (e) => {
        const self = this;
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                let {channelName, channelDesc} = values;
                const params = {channelName, channelDesc};
                let postUrl = '';
                if (this.props.id) {
                    params.channelId = this.props.id;
                    postUrl = 'book/channel/modify';
                } else {
                    postUrl = 'book/channel/add';
                }
                post(postUrl, params)
                    .then((jsonData) => {
                        if (jsonData.meta && jsonData.meta.success) {
                            Emiter.emit('closePanel', '9');
                            Emiter.emit('refreshBookClassifyList', false);
                        } else {

                        }
                    });
            }
        });
    }

    cancel = (e) => {
        Emiter.emit('closePanel', '9');
    }
   
    render() {
        const { getFieldDecorator } = this.props.form;
        const formItemLayout = {
            labelCol: { span: 2 },
            wrapperCol: { span: 6 },
        };

        return (
            <Wrapper>
                <Form onSubmit={this.handleSubmit} className='form'>
                    <FormItem
                        {...formItemLayout}
                        label="分类名称"
                    >
                        {getFieldDecorator('channelName', {
                            rules: [{ required: true, message: '请输入分类名称!' }]
                        })(
                            <Input />
                            )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="分类简介"
                    >
                        {getFieldDecorator('channelDesc')(
                            <Input type='textarea'/>
                            )}
                    </FormItem>
                    <FormItem
                        wrapperCol={{ span: 4, offset: 2 }}
                        className={'editButtons'}
                    >
                        <Button type="primary" htmlType="submit" className="save-btn">保存</Button>
                        <Button type="default" className="cancel-btn" onClick={this.cancel}>取消</Button>
                    </FormItem>
                </Form>
            </Wrapper>
        );
    }
}


export default class BookClassifyNew extends React.Component {
    render() {
        const WrappedForm = Form.create()(MyForm);
        return (
            <WrappedForm id={this.props.id} />
        )
    }
}