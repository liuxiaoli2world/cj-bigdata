import React from 'react';
import { Link, hashHistory } from 'react-router';
import styled from 'styled-components';
import { get, post } from 'utils/request.js';
import { getParams } from 'utils/commonTool.js';
import config from '../../config/index.js';
import Editor from '../components/Editor.js';
import {
    Form, Input, Select, Radio,message,
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
            oldId: '',
            showCover : false,
        };
    }
    componentDidMount() {
        this.refreshPage();
    }

    refreshPage = () => {
        if(!this.props.id) {
            return;
        }

        let {name,price,duration,imageUrl} = this.props.info;
        let imgList;
        if (imageUrl) {
            const params = getParams(imageUrl);
            imgList = [{
                uid: -2,
                status: 'done',
                url: params.url,
                thumbUrl: params.url,
                name: params.name,
                response: {
                    data: imageUrl
                }
            }];
        } else {
            imgList = [];
        }

        this.props.form.setFieldsValue({
            name,price,duration,imgList
        });
        this.setState({
            showCover : imgList.length > 0 ? true :false
        })
    }

    handleSubmit = (e) => {
        const self = this;
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                let {name,price,duration,imgList} = values;
                const imageUrl = values.imgList && values.imgList.length > 0 && values.imgList[0].response.data || null;
                const params = {name,price,duration};
                let postUrl = '';
                params.imageUrl = imageUrl;
                if (this.props.id) {
                    params.vipId = this.props.id;
                    postUrl = 'user/vip/modify';
                } else {
                    postUrl = 'user/vip/add';
                }
                post(postUrl, params)
                    .then((jsonData) => {
                        if (jsonData.meta && jsonData.meta.success) {
                            Emiter.emit('closePanel', '10');
                            Emiter.emit('refreshVipGoodsList', false);
                        } else {
                            message.info(jsonData.meta.message);
                        }
                    });
            }
        });
    }

    onTypeSelect = (value, option) => {
        this.setState({
            type: value
        });
    }

    cancel = (e) => {
        Emiter.emit('closePanel', '10');
    }

    imgFile = (e) => {
        this.setState({
            showCover : e.fileList.length > 0 ? true : false
        })
        if (Array.isArray(e)) {
            return e;
        }

        return e && e.fileList;
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
                        label="商品名称"
                    >
                        {getFieldDecorator('name', {
                            rules: [{ required: true, message: '请输入商品名称!' }]
                        })(
                            <Input />
                            )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="会员时长"
                    >
                        {getFieldDecorator('duration', {
                            rules: [
                                { required: true, message: '请选择时长!' },
                            ],
                        })(
                            <Select onSelect={this.onTypeSelect}>
                                <Option value='1'>1个月</Option>
                                <Option value='12'>1年</Option>
                            </Select>
                            )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="单价"
                    >
                        {getFieldDecorator('price', {
                            rules: [{ required: true, message: '请输入单价!' }]
                        })(
                            <Input type='number'/>
                            )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="封面"
                    >
                        {getFieldDecorator('imgList', {
                            valuePropName: 'fileList',
                            getValueFromEvent: this.imgFile,
                            rules: [{ required: true, message: '请上传封面!' }]
                        })(
                            <Upload name="file" action={config.imageUrl} listType="picture" accept=".jpg, .png, .gif">
                                <Button disabled={this.state.showCover}>
                                    <Icon type="upload" /> 上传图片
                            </Button>
                            </Upload>
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


export default class VipGoodNew extends React.Component {
    render() {
        const WrappedForm = Form.create()(MyForm);
        return (
            <WrappedForm id={this.props.id} info={this.props.data} />
        )
    }
}