import React from 'react';
import { Wrapper } from './style';
import { Breadcrumb, Icon, Carousel } from 'antd';
import { hashHistory, Link } from 'react-router';
import Video from './video.js';

export default class VideoDetail extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        let path = hashHistory.getCurrentLocation().pathname;
        const {id, imageUrl} = this.props.location.state;
        return (
            <Wrapper>
                {/*面包屑*/}
                <Breadcrumb separator='>'>
                     <Breadcrumb.Item><Icon type='pushpin-o' />当前位置:</Breadcrumb.Item>
                    <Breadcrumb.Item href='/'>首页</Breadcrumb.Item>
                    <Breadcrumb.Item>视频导航</Breadcrumb.Item>
                </Breadcrumb>
                <Video id={id} imageUrl={imageUrl}/>
            </Wrapper>
        )
    }
}