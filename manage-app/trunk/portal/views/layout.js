import React from 'react';
import {Link} from 'react-router';
import { Layout } from 'antd';
const { Content, Footer } = Layout;
import SiderCustom from './components/SiderCustom';
import HeaderCustom from './components/HeaderCustom';

/**
 * 这个文件定义页面的结构
 */
class MyLayout extends React.Component{
	constructor(props) {
		super(props);
	}
   
    render(){
        return (
        	 <Layout className="ant-layout-has-sider">
              <SiderCustom path={this.props.location.pathname} />
              <Layout>
                <HeaderCustom />
                <Content style={{background:'#f3f4f9'}}>
                  {this.props.children}
                </Content>
              </Layout>
            </Layout>
	    )
    }
}

export default MyLayout;