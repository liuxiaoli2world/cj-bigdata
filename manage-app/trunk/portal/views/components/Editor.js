import React, { Component, PropTypes } from 'react';
import { SubEmiter, Emiter } from '../utils/index.js';
import { Upload, Modal, Button, message } from 'antd';
import urlConfig from '../../config/index.js';

var $s = require('scriptjs');

export default class CKEditor extends React.Component {
  constructor(props) {
    super(props);
    this.componentDidMount = this.componentDidMount.bind(this);
    this.state = {
      visible: false,
      image: ''
    }
  }

  onEditcontext = (param) => {
    this.default = param && param.con;
    this.setDefault();
  }

  showModal = () => {
    this.setState({
      visible: true,
    });
  }

  handleOk = () => {
    this.state.image && this.editor.insertHtml(`<img style="max-width:100%" src=${this.state.image}>`);
    this.setState({
      visible: false,
    });
  }

  handleCancel = () => {
    this.setState({
      visible: false,
    });
  }

  getData = () => {
    this.props.EditorChange(this.editor.getData());
  }

  setDefault = () => {
    this.editor && this.editor.setData(this.default);
  }

  render() {
    const self = this;
    const config = {
      name: 'file',
      action: urlConfig.imageUrl,
      beforeUpload(file, fileList) {
        if (file.type.indexOf('image/') >= 0) {
          return true;
        } else {
          Modal.error({
            title: '提示',
            content: '请上传图片文件！'
          })
        }
      },
      onChange(info) {
        if (info.file.status !== 'uploading') {
          const res = info.file.response;
          if (res.meta.success) {
            self.setState({
              image: res.data
            })
          }
        }
        if (info.file.status === 'done') {
          message.success(`${info.file.name} file uploaded successfully`);
        } else if (info.file.status === 'error') {
          message.error(`${info.file.name} file upload failed.`);
        }
      },
    };

    const { visible, confirmLoading } = this.state;
    return (
      <SubEmiter eventName="editcontext" listener={this.onEditcontext}>
        <div style={this.props.style ? this.props.style : { width: '100%' }}>
          <textarea name={`editor${this.props.i}`} ></textarea>
          <Modal title="上传图片"
            visible={visible}
            onOk={this.handleOk}
            confirmLoading={confirmLoading}
            onCancel={this.handleCancel}
          >
            <Upload {...config}>
              <Button className="upload">上传图片</Button>
            </Upload>
          </Modal>
        </div>
      </SubEmiter>
    )
  }

  addUploadImage = (theURLElementId) => {
    this.setState({
      visible: true,
    });
  }

  resetEditor(props) {
    const self = this;
    CKEDITOR.replace(`editor${props.i}`);
    const editor = CKEDITOR.instances[`editor${props.i}`];
    self.editor = editor;
    self.editor.on('instanceReady', function () {

      this.addCommand('image', {
        modes: {
          wysiwyg: 1,
          source: 1
        },
        exec: function (editor) {
          self.addUploadImage();
        }
      })
      props.disabled && self.editor.setReadOnly();
    })
    self.setDefault();
    self.editor.on('change', self.getData);
  }

  componentDidMount() {
    const self = this;
    $s('assets/ckeditor/ckeditor.js', function () {
      self.resetEditor(self.props);
      self.setDefault();
    })
  }
  componentWillReceiveProps(nextprops) {
    const self = this;
    if (nextprops.disabled != this.props.disabled) {
      $s('assets/ckeditor/ckeditor.js', function () {
        self.editor.destroy(true);
        self.resetEditor(nextprops);
      })
    }
  }
}
