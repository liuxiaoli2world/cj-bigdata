import React from 'react';
import { Link, hashHistory } from 'react-router';
import Wrapper from './style.js';
import './style.css';
import { get, post } from 'utils/request.js';
import config from '../../config/index.js';
import Editor from '../components/Editor.js';
import {
  Form, Input, Select, Radio,
  Button, Upload, Icon, Row, Col, DatePicker
} from 'antd';
import moment from 'moment';
const FormItem = Form.Item;
const Option = Select.Option;
const RadioButton = Radio.Button;
const RadioGroup = Radio.Group;
import {
  SubEmiter,
  Emiter
} from 'utils';

class MyForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      firstDir: [],
      secondDir: [],
      type: 'headline',
      show : true,
      showCover : true,
    };
    this.index = 1;
  }

  componentDidMount() {
    this.queryRootMenu();
  }

  queryRootMenu = () => {
    get('book/menu/queryRoot')
      .then((jsonData) => {
        this.setState({
          firstDir: [{ menuId: '-1', menuName: '无' }, ...(jsonData.data || [])],
          sourcePList: jsonData.data || [],
          secondDir : [{ menuId: '-1', menuName: '无' }]
        });

        this.props.form.setFieldsValue({
          parentMenuId: '-1',
          menuId : "-1"
        });
      }).then(() => {
        this.queryDetail();
      });
  }

  queryDetail = () => {
    const self = this;
    const id = this.props.id;
    if (!id) {
      return;
    }

    get(`book/content/query/${id}/admin`)
      .then((jsonData) => {
        const data = jsonData.data;
        let { contentType, contentDesc, source, title, imageUrl, author, isOriginal, keyword, body, parentMenuId, menuId, contentAccssoryList, createdAt } = data;
        let imgList;
        if (imageUrl) {
          const params = self.getParams(imageUrl);
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

        const fileList = this.fromContentAccssoryListToFileList(contentAccssoryList);

        this.setState({
          type: contentType,
          show: fileList.length > 0 ? false : true,
          showCover : imgList.length > 0 ? false : true,
        });
        // 如果没有一级分类，默认“无”
        parentMenuId = parentMenuId ? parentMenuId + '' : '-1';
        menuId = menuId ? menuId + '' : '-1';
        this.setPvalue(contentType);
        let values = { contentType, contentDesc, source, title, author, isOriginal, keyword, parentMenuId, imgList, body };
        if (contentType === 'periodical') {
          values.fileList = fileList;
        }
        this.props.form.setFieldsValue(values);
        createdAt && this.props.form.setFieldsValue({
          createdAt: moment(createdAt, 'YYYY-MM-DD hh:mm:ss')
        })
        Emiter.emit('editcontext', { 'con': body });
        return { parentMenuId, menuId, contentType };
      }).then((params) => {
        this.querySecondDir(params.parentMenuId, params.menuId, params.contentType == 'periodical' ? true : false);
      });
  }

  fromContentAccssoryListToFileList = (contentAccssoryList) => {
    let fileList = [];
    for (let i = 0, len = contentAccssoryList.length; i < len; i++) {
      const contentAccssory = contentAccssoryList[i];
      let file = {
        uid: i,
        status: 'done',
        url: contentAccssory.accessoryUrl,
        response: {
          data: contentAccssory.accessoryUrl
        },
        name: contentAccssory.name,
        thumbUrl: contentAccssory.accessoryUrl
      };
      fileList.push(file);
    }
    return fileList;
  }

  getParams = (s) => {
    const datas = s.split('?');
    const url = datas[0];
    const name = datas[1] && this.getQueryString(datas[1], 'name') || '';
    const size = datas[1] && this.getQueryString(datas[1], 'size') || '';
    return {
      size, name, url
    }
  }

  getQueryString(s, name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = s.match(reg);
    if (r != null) return unescape(r[2]); return null;
  }

  fromFileListToContentAccssoryList = (fileList) => {
    if (!fileList) {
      return [];
    }
    let contentAccssoryList = [];
    for (let i = 0, len = fileList.length; i < len; i++) {
      const file = fileList[i];
      const params = this.getParams(file.response.data);
      let contentAccssory = {
        accessoryUrl: params.url,
        name: params.name,
        size: params.size
      };
      contentAccssoryList.push(contentAccssory);
    }
    return contentAccssoryList;
  }

  querySecondDir = (parentMenuId, menuId, isP) => {
    if (!parentMenuId || parentMenuId == '-1') {
      this.setState({
        secondDir: [{ menuId: '-1', menuName: '无' }]
      });
      this.props.form.setFieldsValue({
        menuId: '-1'
      });
    } else {
      get(`book/menu/queryChild/${parentMenuId}`)
        .then((jsonData) => {
          const data = jsonData.data;
          this.setState({
            secondDir: isP ? jsonData.data || [] : [...(jsonData.data || [])]
          });
          // 默认选择“无”
            if (isP) {
                this.props.form.setFieldsValue({
                    parentMenuId: parentMenuId || this.state.sourcePList[0].menuId + '',
                    menuId: menuId || data[0] && data[0].menuId + '' 
                });
            } 
            else {
                this.props.form.setFieldsValue({
                    menuId: menuId || data[0] && data[0].menuId + ''
                });
                // this.props.form.setFieldsValue({
                //     menuId: data[0] && data[0].menuId + '' || '-1'
                // });
            }
        });
    }
  }

  handleSubmit = (e) => {
    const self = this;
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        let { contentType, contentDesc, source, title, author, isOriginal, keyword, body, parentMenuId, menuId, createdAt } = values;
        createdAt = createdAt ? createdAt.format("YYYY-MM-DD hh:mm:ss") : '';
        const contentAccssoryList = this.fromFileListToContentAccssoryList(values.fileList);
        const imageUrl = values.imgList && values.imgList.length > 0 && values.imgList[0].response.data || null;
        const params = { contentType, contentDesc, source, title, author, isOriginal, keyword, body, contentAccssoryList, imageUrl, createdAt };
        let postUrl = '';
        parentMenuId = parentMenuId == '-1' ? '' : parentMenuId;
        menuId = menuId == '-1' ? '' : menuId;
        if (this.props.id) {
          params.contentId = this.props.id;
          postUrl = `book/content/modify?menuPId=${parentMenuId}&menuId=${menuId}`;
        } else {
          postUrl = `book/content/add?menuPId=${parentMenuId}&menuId=${menuId}`;
        }
        post(postUrl, params)
          .then((jsonData) => {
            if (jsonData.meta && jsonData.meta.success) {
              self.props.cancel();
              Emiter.emit('refresh');
            } else {

            }
          });
      }
    });
  }

  setPvalue = (value) => {
    if (value === 'periodical') {
      this.setState({
        firstDir: [...this.state.sourcePList]
      });
      this.querySecondDir(this.state.sourcePList[0].menuId+'', '', true);
    } else {
      this.setState({
        firstDir: [{ menuId: '-1', menuName: '无' }, ...this.state.sourcePList],
      });
    }
  }

  onTypeSelect = (value, option) => {
    this.setPvalue(value);
    this.setState({
      type: value
    });
    
  }
  onSelect = (value, option) => {
    this.querySecondDir(value, '', false);
  }

  EditorChange = (html) => {
    this.props.form.setFieldsValue({
      body: html
    })
  }

  cancel = (e) => {
    this.props.cancel();
  }

  normFile = (e) => {
    let show;
    if (e.fileList.length > 0) {
      show = false;
    } else {
      show = true;
    }
    this.setState({
      show: show
    });
    if (Array.isArray(e)) {
      return e;
    }
    return e && e.fileList;
  }

  imgFile = (e) => {
    let showCover;
    if(e.fileList.length > 0){
      showCover = false;
    }else{
      showCover = true;
    }
    this.setState({ showCover : showCover })
    if (Array.isArray(e)) {
      return e;
    }
    return e && e.fileList;
  }

  handleSecondDir = (rule, value, callback) => {
    const { getFieldValue } = this.props.form;
    const pMenuId = getFieldValue('parentMenuId');
    if (pMenuId !== '-1' && value === '-1') {
      callback('请选择二级分类！')
    }

    // Note: 必须总是返回一个 callback，否则 validateFieldsAndScroll 无法响应
    callback();
  }

  render() {
    const { getFieldDecorator } = this.props.form;
    const formItemLayout = {
      labelCol: { span: 2 },
      wrapperCol: { span: 6 },
    };

    return (
      <Form onSubmit={this.handleSubmit} className='form'>
        <FormItem
          {...formItemLayout}
          label="栏目"
        >
          {getFieldDecorator('contentType', {
            rules: [
              { required: true, message: '请选择一个栏目!' },
            ],
            initialValue: "notice"
          })(
            <Select onSelect={this.onTypeSelect}>
              <Option value="notice">通知公告</Option>
              <Option value="headline">长江要闻</Option>
              <Option value="periodical">期刊</Option>
              <Option value="history">人文历史</Option>
            </Select>
            )}
        </FormItem>

        <Row gutter={8}>
          <Col span={4}>
            <FormItem
              {...formItemLayout}
              labelCol={{ span: 12 }}
              wrapperCol={{ span: 12 }}
              label="分类标签"
            >
              {getFieldDecorator('parentMenuId', {
                rules: [
                  { required: true, message: '请选择一个分类!' },
                ],
              })(
                <Select onSelect={this.onSelect} >
                  {
                    this.state.firstDir.map((item, index) => (
                      <Option key={index} value={item.menuId + ''}>{item.menuName}</Option>
                    ))
                  }
                </Select>
                )}
            </FormItem>
          </Col>
          <Col span={4}>
            <FormItem
              {...formItemLayout}
              wrapperCol={{ span: 22 }}
            >
              {getFieldDecorator('menuId', {
                rules: [
                  { required: true, message: '请选择一个分类!' }, {
                    validator: this.handleSecondDir
                  }
                ],
              })(
                <Select>
                  {
                    this.state.secondDir.map((item, index) => (
                      <Option key={index} value={item.menuId + ''}>{item.menuName}</Option>
                    ))
                  }
                </Select>
                )}
            </FormItem>
          </Col>
        </Row>

        <FormItem
          {...formItemLayout}
          label="标题"
        >
          {getFieldDecorator('title', {
            rules: [{ required: true, message: '请输入标题!' },{
              max : 100 , message : "标题不能超过30字，请重新输入！"
            }]
          })(
            <Input />
            )}
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="作者"
        >
          {getFieldDecorator('author', {
            rules: [{ required: false },{
              max : 30 , message : "作者不能超过30字，请重新输入！"
            }]
          })(
            <Input />
            )}
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="来源"
        >
          {getFieldDecorator('source', {
            rules: [{ required: false },{
               max : 30 , message : "来源不能超过30字，请重新输入！"
            }]
          })(
            <Input />
            )}
        </FormItem>
        
        <FormItem
          {...formItemLayout}
          label="发表时间"
        >
          {getFieldDecorator('createdAt')(
              <DatePicker format="YYYY-MM-DD hh:mm:ss" />
          )}
        </FormItem>
        
        <FormItem
          {...formItemLayout}
          label="简介"
        >
          {getFieldDecorator('contentDesc', {
            rules: [{ required: false },{
              max : 300 , message : "简介不能超过300字，请重新输入！"
            }]
          })(
            <Input type="textarea" rows={5} autosize={true} />
            )}
        </FormItem>


        <FormItem
          {...formItemLayout}
          label="是否原创"
        >
          {getFieldDecorator('isOriginal',{
            initialValue : 1
          })(
            <RadioGroup>
              <Radio value={1}>是</Radio>
              <Radio value={0}>否</Radio>
            </RadioGroup>
          )}
        </FormItem>
        <FormItem
          {...formItemLayout}
          wrapperCol={{ span: 10 }}
          label="关键字"
        >
          <Row gutter={25}>
            <Col span={15}>
              {getFieldDecorator('keyword', {
                rules: [{ required: false },{
                  max : 50 , message : "关键字不能超过50字，请重新输入！"
                }]
              })(
                <Input />
                )}
            </Col>
            <Col span={7}>
              关键字'，'隔开
          </Col>
          </Row>

        </FormItem>
        {
          this.state.type === 'periodical' ?
            <FormItem
              {...formItemLayout}
              label="资源上传"
            >
              {getFieldDecorator('fileList', {
                valuePropName: 'fileList',
                getValueFromEvent: this.normFile,
              })(
                <Upload name="file" action={config.imageUrl}>
                  {
                    this.state.show ?
                      <Button><Icon type="upload" />上传</Button>
                      : <Button disabled><Icon type="upload" />上传</Button>
                  }
                </Upload>
                )}
            </FormItem>
            :
            null
        }
        <FormItem
          {...formItemLayout}
          label="封面"
        >
          {getFieldDecorator('imgList', {
            valuePropName: 'fileList',
            getValueFromEvent: this.imgFile,
          })(
            <Upload name="file" action={config.imageUrl} listType="picture" accept=".jpg, .png, .gif" >
              {
                this.state.showCover ?
                  <Button>
                    <Icon type="upload" /> 上传图片
                  </Button>
                  :<Button disabled>
                    <Icon type="upload" /> 上传图片
                  </Button>
              }
            </Upload>
            )}
        </FormItem>
        <FormItem label="正文"
          {...formItemLayout}
          wrapperCol={{ span: 10 }}
          id="bookdetail">
          {getFieldDecorator('body', {
            rules: [{ required: true, message: '正文不能为空！' },{
               max : 20000, message : "输入内容不能超过20000字，请重新输入！"
            }]
          })(
            <Editor EditorChange={this.EditorChange} i={`a${++this.index}`} style={{ width: '150%' }} />
            )}
        </FormItem>
        <FormItem
          wrapperCol={{ span: 4, }} style={{ marginLeft: 80 }}>
          <Button type="primary" htmlType="submit" className="btn save-btn">保存</Button>
          <Button type="default" className="btn cancel-btn" onClick={this.cancel}>取消</Button>
        </FormItem>
      </Form>
    );
  }
}

export default class Detail extends React.Component {
  render() {
    const WrappedForm = Form.create()(MyForm);
    return (
      <WrappedForm id={this.props.id} cancel={this.props.cancel} />
    )
  }
}