import React from 'react';
import { Link, hashHistory } from 'react-router';
import styled from 'styled-components';
import { get, post } from 'utils/request.js';
import { getParams } from 'utils/commonTool.js';
import config from '../../config/index.js';
import Editor from '../components/Editor.js';
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
        const show = this.props.info && this.props.info.multiType === 'video' ? true : false;
        this.state = {
            firstDir: [],
            secondDir: [],
            type: 'headline',
            oldId: '',
            showRecommond: show,
            showCover: false,
        };
    }
    componentDidMount() {
        this.queryRootMenu();
        this.props.info && this.refreshPage();
    }

    refreshPage = () => {
        let { multiType, isDownload, fullPrice, detail, title, path, scene, multiDesc, pmenuId, cmenuId } = this.props.info;
        let imgList;
        if (path) {
            const params = getParams(path);
            imgList = [{
                uid: -2,
                status: 'done',
                url: params.url,
                thumbUrl: params.url,
                name: params.name,
                response: {
                    data: path
                }
            }];
        } else {
            imgList = [];
        }
        pmenuId += '';
        cmenuId += '';
        this.querySecondDir(pmenuId, cmenuId);
        multiDesc = multiDesc || '';
        isDownload = parseInt(isDownload);
        scene = parseInt(scene);
        this.props.form.setFieldsValue({
            multiType, isDownload, fullPrice, title, imgList, multiDesc, scene, pmenuId, cmenuId
        });
        Emiter.emit('editcontext', { 'con': detail });
        this.setState({ showCover: imgList.length > 0 ? true : false })
    }

    queryRootMenu = () => {
        get('book/menu/queryRoot')
            .then((jsonData) => {
                this.setState({
                    firstDir: jsonData.data || []
                });
            })
    }

    querySecondDir = (parentMenuId, menuId) => {
        get(`book/menu/queryChild/${parentMenuId}`)
            .then((jsonData) => {
                const data = jsonData.data;
                this.setState({
                    secondDir: data || []
                });
                // 默认选择“无”
                this.props.form.setFieldsValue({
                    cmenuId: menuId || data[0].menuId + ''
                });
            });
    }

    onSelect = (value, option) => {
        this.querySecondDir(value);
    }
    // queryRootMenu = () => {
    //     get('book/menu/queryRoot')
    //         .then((jsonData) => {
    //             jsonData.meta.success &&
    //                 this.setState({
    //                     firstDir: [...(jsonData.data || [])]
    //                 });
    //         }).then(() => {
    //             this.querySecondDir(this.props.info.pmenuId);
    //         }).then(() => {
    //             this.refreshPage();
    //         })
    // }

    // querySecondDir = (pmenuId) => {
    //     if (!pmenuId) {
    //         return;
    //     }
    //     get(`book/menu/queryChild/${pmenuId}`)
    //         .then((jsonData) => {
    //             if (jsonData.meta.success) {
    //                 const data = jsonData.data;
    //                 this.setState({
    //                     secondDir: [...(jsonData.data || [])]
    //                 });
    //             }
    //         });
    // }

    handleSubmit = (e) => {
        const self = this;
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                let { multiType, isDownload, fullPrice, detail, title, path, scene, multiDesc, pmenuId, cmenuId } = values;
                const imageUrl = values.imgList && values.imgList.length > 0 && values.imgList[0].response.data || null;
                fullPrice = fullPrice ? fullPrice : 0;
                const menuId = cmenuId;
                const params = { multiType, isDownload, fullPrice, detail, title, scene, multiDesc, menuId, path };
                let postUrl = '';
                params.path = imageUrl;
                if (this.props.id) {
                    params.multifileId = this.props.id;
                    postUrl = 'book/multifile/modify';
                } else {
                    postUrl = 'book/multifile/add';
                }
                post(postUrl, params)
                    .then((jsonData) => {
                        if (jsonData.meta && jsonData.meta.success) {
                            Emiter.emit('closePanel', '7');
                            Emiter.emit('refreshResourceList', false);
                        } else {

                        }
                    });
            }
        });
    }

    onTypeSelect = (value, option) => {
        let show;
        if (value === 'video') {
            show = true;
        } else {
            show = false;
        }
        this.setState({
            type: value,
            showRecommond: show
        });
    }

    EditorChange = (html) => {
        this.props.form.setFieldsValue({
            detail: html
        })
    }

    cancel = (e) => {
        Emiter.emit('closePanel', '7');
    }

    imgFile = (e) => {
        let showCover;
        if (e.fileList.length > 0) {
            showCover = true;
        } else {
            showCover = false;
        }
        this.setState({ showCover: showCover })
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

        const isDisabled = this.props.id ? true : false;

        return (
            <Wrapper>
                <Form onSubmit={this.handleSubmit} className='form'>
                    <FormItem
                        {...formItemLayout}
                        label="资源类型"
                    >
                        {getFieldDecorator('multiType', {
                            rules: [
                                { required: true, message: '请选择一个类型!' },
                            ],
                            initialValue: "pic"
                        })(
                            isDisabled ?
                                <Select onSelect={this.onTypeSelect} disabled>
                                    <Option value='pic'>图片</Option>
                                    <Option value='music'>音频</Option>
                                    <Option value='video'>视频</Option>
                                    <Option value='pdf'>pdf文件</Option>
                                    <Option value='txt'>txt文件</Option>
                                </Select>
                                :
                                <Select onSelect={this.onTypeSelect}>
                                    <Option value='pic'>图片</Option>
                                    <Option value='music'>音频</Option>
                                    <Option value='video'>视频</Option>
                                    <Option value='pdf'>pdf文件</Option>
                                    <Option value='txt'>txt文件</Option>
                                </Select>
                            )}
                    </FormItem>

                    <Row gutter={5}>
                        <Col span={4}>
                            <FormItem
                                {...formItemLayout}
                                wrapperCol={{ span: 14 }}
                                label="分类标签"
                            >
                                {getFieldDecorator('pmenuId', {
                                    rules: [
                                        { required: true, message: '请选择一个分类!' },
                                    ],
                                })(
                                    <Select onSelect={this.onSelect} placeholder={'请选择'}>
                                        {this.state.firstDir.map((item, index) => (
                                            <Option key={index} value={item.menuId + ''}>{item.menuName}</Option>
                                        ))}
                                    </Select>
                                    )}
                            </FormItem>
                        </Col>

                        <Col span={4}>
                            <FormItem
                                {...formItemLayout}
                                wrapperCol={{ span: 22 }}
                            >
                                {getFieldDecorator('cmenuId', {
                                    rules: [
                                        { required: true, message: '请选择一个分类!' },
                                    ],
                                })(
                                    <Select placeholder={'请选择'}>
                                        {this.state.secondDir.map((item, index) => (
                                            <Option key={index} value={item.menuId + ''}>{item.menuName}</Option>
                                        ))}
                                    </Select>
                                    )}
                            </FormItem>
                        </Col>
                    </Row>

                    <FormItem
                        {...formItemLayout}
                        label="资源标题"
                    >
                        {getFieldDecorator('title', {
                            rules: [{ required: true, message: '请输入标题!' },{
                                max : 30 , message : "标题不能超过30字，请重新输入！"
                            }]
                        })(
                            <Input />
                            )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="资源简介"
                    >
                        {getFieldDecorator('multiDesc', {
                            rules: [{ required: false },{
                                max : 300 , message : "资源简介不能超过300字，请重新输入！"
                            }]
                        })(
                            <Input type="textarea" rows={5} autosize={true} />
                            )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="单价"
                    >
                        {getFieldDecorator('fullPrice')(
                            <Input type='number' />
                        )}
                    </FormItem>
                    {
                        this.state.showRecommond ? <FormItem
                            {...formItemLayout}
                            label="首页推荐"
                        >
                            {getFieldDecorator('scene', {
                                initialValue: 0
                            })(
                                <RadioGroup>
                                    <Radio value={1}>是</Radio>
                                    <Radio value={0}>否</Radio>
                                </RadioGroup>
                                )}
                        </FormItem>
                            : null
                    }

                    <FormItem
                        {...formItemLayout}
                        label="提供下载"
                    >
                        {getFieldDecorator('isDownload', {
                            rules: [{ required: true, message: '请选择!' }],
                            initialValue: 0
                        })(
                            <RadioGroup>
                                <Radio value={1}>是</Radio>
                                <Radio value={0}>否</Radio>
                            </RadioGroup>
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
                    <FormItem label="资源详情"
                        {...formItemLayout}
                        wrapperCol={{ span: 10 }}
                        id="bookdetail">
                        {getFieldDecorator('detail', {
                            rules: [{ required: false, message: '正文' },{
                                max : 500 , message : "资源详情不能超过500字，请重新输入！"
                            }]
                        })(
                            <Editor EditorChange={this.EditorChange} i={`${Date.parse(new Date()) / 1000}`} style={{ width: '150%' }} />
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


export default class ResourceNew extends React.Component {
    render() {
        const WrappedForm = Form.create()(MyForm);
        return (
            <WrappedForm id={this.props.id} info={this.props.info} />
        )
    }
}