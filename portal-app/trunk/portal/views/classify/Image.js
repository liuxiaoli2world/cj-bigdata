import React from "react";
import { Link } from "react-router";
import { Carousel, Icon, message, Button } from 'antd';
import { get, post } from '../util/request';
import { SubEmiter, Emiter } from 'util';

export default class Image extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            index: 0,
            images: [],
            siblings: []
        }
    }

    onChange = (a) => {
        this.setState({
            index: a
        })
    }

    handleKeyDown = (e) => {
        switch (e.keyCode) {
            case 37:
                this.handlePrev();
                break
            case 39:
                this.handleNext();
                break

            default:
                break

        }
    }

    handlePrev = () => {
        let { index, images } = this.state;
        if (index > 0) {
            --index;
            this.refs.Carousel.refs.slick.slickGoTo(index)
            this.setState({
                index
            })
        }
    }

    handleNext = () => {
        let { index, images } = this.state;
        if (index < images.length - 1) {
            ++index;
            this.refs.Carousel.refs.slick.slickGoTo(index)
            this.setState({
                index: index
            })
        } else {
            message.warning('已经是最后一张了')
        }
    }

    handleFullScreen = () => {
        this.requestFullScreen(this.refs.screen)
    }

    handleChange(item) {
        const { images } = this.state;
        let k = images.findIndex((val) => val == item);
        this.refs.Carousel.refs.slick.slickGoTo(k)
    }

    requestFullScreen(element) {
        // 判断各种浏览器，找到正确的方法
        let requestMethod = element.requestFullScreen || //W3C
            element.webkitRequestFullScreen ||    //Chrome等
            element.mozRequestFullScreen; //FireFox
        if (requestMethod) {
            requestMethod.call(element);
        } else if (element.msRequestFullscreen) { //IE11
            element.msRequestFullscreen();
        }
        else if (typeof window.ActiveXObject !== "undefined") {//for Internet Explorer
            let wscript = new ActiveXObject("WScript.Shell");
            if (wscript !== null) {
                wscript.SendKeys("{F11}");
            }
        }
    }

    handleToggle(id) {
        if (id) {
            this.getData(id);
        } else {
            message.warning('没有相册啦！')
        }
    }

    componentDidMount() {
        const { id } = this.props.params;
        Emiter.emit('detailTitle', 3);
        this.getData(id);
        window.addEventListener('keyup', this.handleKeyDown)
    }

    getData(id) {
        const { menuId } = this.props.params;
        get(`book/multiitem/selectPicsById/${id}`)
            .then((res) => {
                if (res.meta.success) {
                    this.setState({
                        images: res.data
                    })

                }
            })
        menuId != 0 &&
        get(`book/multifile/selectFirstAndLastPic?menuId=${menuId}&multifileId=${id}`)
            .then((res) => {
                if (res.meta && res.meta.success) {
                    this.setState({
                        siblings: res.data
                    })
                }
            })
        this.refs.Carousel && this.refs.Carousel.refs.slick.slickGoTo(0)
    }

    render() {
        const settings = {
            dots: false
        };
        let { images, index, siblings } = this.state;
        const start = (parseInt(index / 5) * 5);
        return (
            <div className="img_detail clearfix">
                <div className="img_title">
                    <p className="img_name">{images[index] ? images[index].name.split(/(.*)\.[^.]+/)[1] : ''}</p>
                    <p className="img_date">{images[index] ? images[index].createdAt : ''}</p>
                </div>
                <div className="operate">
                    <span>{++index}/{images.length}</span> 支持←→键翻阅图片&nbsp;&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;&nbsp;<span className="full" onClick={this.handleFullScreen}><img src={require('assets/images/full.png')} alt="" />全屏观看</span>
                </div>

                <div ref="screen" style={{ width: '980px', height: '740px', margin: '0 auto' }}>
                    {images.length > 0 ?
                        <Carousel ref="Carousel" afterChange={this.onChange} {...settings} effect="fade">
                            {images.map((item, i) => (
                                <div key={i} style={{ width: '980px', height: '740px' }}>
                                    <img className="img_prize" src={item.path} width='100%' height='100%' />
                                </div>
                            ))}
                        </Carousel> : null}
                </div>
                <div className="preview">

                    <ul className="prize_img_replace clearfix">

                        <li className="pre_images" onClick={this.handleToggle.bind(this, siblings[0] ? siblings[0].multifileId : null)}>
                            <div className="border1">
                                {siblings[0] ?
                                    <img width="86" height="86" src={siblings[0].path} alt="" />
                                    :
                                    <img className="noPic" width="42" height="42" src={require('assets/images/noPic.png')} alt="暂无相册" />
                                }
                            </div>
                            <div className="border2">
                            </div>
                            <div className="border3">
                            </div>
                        </li>
                        <li className="prevButton" onClick={this.handlePrev}></li>
                        {images.slice(start, start + 5).map((item, i) => {
                            return (
                                <li className={`left imglist ${(i === 0) ? 'firstImg' : ''} ${(start + i + 1 === index) ? 'select' : ''}`} key={i} onClick={this.handleChange.bind(this, item)}>
                                    <img width="86" height="86" className="img_prize_replace" src={item.path} />
                                </li>
                            )
                        })}
                        <li className="nextButton" onClick={this.handleNext}></li>
                        <li className="next_images" style={{ marginLeft: '64px' }} onClick={this.handleToggle.bind(this, siblings[1] ? siblings[1].multifileId : null)}>
                            <div className="border1">
                                {siblings[1] ?
                                    <img width="86" height="86" src={siblings[1].path} alt="" />
                                    :
                                    <img className="noPic" width="42" height="42" src={require('assets/images/noPic.png')} alt="暂无相册" />
                                }
                            </div>
                            <div className="border2">
                            </div>
                            <div className="border3">
                            </div>
                        </li>
                    </ul>
                    { images[index-1] && images[index-1].isDownload ?
                        <Button type='primary' size='large' className="dlBtn"><a href={images[index-1].path} download={images[index-1].name}><Icon type="download" />下载</a></Button>
                        : null
                    }
                </div>
            </div>
        )
    }
}