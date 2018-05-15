import React from 'react';
import { Wrapper } from './style.js';
import { Breadcrumb, Icon, Pagination, Input, Button } from 'antd';
import { get } from 'views/util/request';
import { hashHistory, Link } from 'react-router';

export default class Expert extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            readings: []
        };
    }

    componentWillMount() {
        get('expert/expert/selectRecommendExpertAndComposition')
            .then((jsonData) => {
                if (jsonData.meta && jsonData.meta.success) {
                    const data = jsonData.data || {};
                    this.setState({
                        id: data.expertId,
                        name: data.realName,
                        imageUrl: data.imageUrl,
                        content: data.expertDesc,
                    });
                }
            });

        get('book/book/queryRec')
            .then((jsonData) => {
                if (jsonData.meta && jsonData.meta.success) {
                    this.setState({
                        readings: jsonData.data || []
                    });
                }
            });
    }
    render() {
        return (
            <Wrapper>
                {/*面包屑*/}
                <Breadcrumb separator='>'>
                    <Breadcrumb.Item><Icon type='pushpin-o' />当前位置:</Breadcrumb.Item>
                    <Breadcrumb.Item href='/'>首页</Breadcrumb.Item>
                    <Breadcrumb.Item>专家学者</Breadcrumb.Item>
                </Breadcrumb>
                <div className='content'>
                    <span className='left-content'>
                        <div className='recommand-expert'>
                            {
                                this.state.id ?
                                    <Link to={`/expert/detail/${this.state.id}`} >
                                        <div className='head'>推荐学者</div>
                                        <div className='recommand-content'>
                                            <img className='img' src={this.state.imageUrl} />
                                            <div className='name'>{this.state.name}</div>
                                            <div className='desc'>{this.state.content && this.state.content.length < 20 ? this.state.content : this.state.content && this.state.content.substr(0, 20) + '...'}</div>
                                        </div>
                                    </Link>
                                    :
                                    <div>
                                        <div className='head'>推荐学者</div>
                                        <div className='recommand-content'>
                                            <img className='img' src={this.state.imageUrl} />
                                            <div className='name'>{this.state.name}</div>
                                            <div className='desc'>{this.state.content && this.state.content.length < 20 ? this.state.content : this.state.content && this.state.content.substr(0, 20) + '...'}</div>
                                        </div>
                                    </div>
                            }

                        </div>
                        <div className='recommand-reading'>
                            <div className='head'>推荐阅读</div>
                            <ul className='reading-list'>
                                {
                                    this.state.readings.map((item, index) => (
                                        <Link key={index} to={`classify/1/book/${item.bookId}`} ><li className='reading-item'>{item.bookName && item.bookName.length < 11 ? item.bookName : item.bookName && item.bookName.substr(0, 10) + '...'}</li></Link>
                                    ))
                                }
                            </ul>
                        </div>
                    </span>
                    <span className='right-content'>
                        {this.props.children}
                    </span>
                </div>
            </Wrapper>
        )
    }
}