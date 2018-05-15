import React from 'react';
import { Link } from 'react-router';
import { Carousel, Icon, Button, message } from 'antd';
import { get, post } from '../util/request';
// require('./html5media.min.js')
import videojs from 'video.js'
// require('video.js/dist/video.min.js')
require('video.js/dist/video-js.min.css')

export default class Video extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            videos: [],
            currentVideo: {},
            currentIndex: 0,
            videoStartIndex: 0,
            size: 4,
            videoOffset: 0
        };
    }

    componentDidMount() {
        this.video = videojs('videoID');
        
        $('#videoID').bind('contextmenu',function() { return false; });
    }
    
    componentWillMount() {
        const id = this.props.id;
        get(`book/multiitem/queryIndexVideoList?multifileId=${id}`)
            .then((jsonData) => {
                this.setState({
                    videos: jsonData.data || [],
                    currentVideo: this.getCurrentVideo(),
                    currentIndex: 0
                });
                this.play();
            });
    }

    componentWillUnmount() {
        if (this.video) {
          this.video.dispose()
        }
    }

    downLoadBtnEvent = (event) => {
        window.location = this.getCurrentVideo().path;
    }

    play = () => {
        const src = this.getCurrentVideo().path;
        // const video = this.refs.myVideo;
        this.video.src(src);
        this.video.load();
        // this.refs.myVideo.load();
        // this.refs.myVideo.play();
    }

    prePage = () => {
        let { currentIndex } = this.state;
        if (currentIndex > 0) {
            --currentIndex;
            const offset = this.state.videoOffset + parseInt(currentIndex / 3) * 220;
            this.setState({
                currentIndex,
                currentVideo: this.state.videos[currentIndex],
                videoOffset: offset
            })
            

        } else {
            message.warning('已经是第一条了！')
        }
    
    }

    nextPage = () => {
        let { currentIndex } = this.state;
        if (currentIndex < this.state.videos.length - 1) {
            ++currentIndex;
            const offset = this.state.videoOffset - parseInt(currentIndex / 4) * 220;
            this.setState({
                currentIndex,
                currentVideo: this.state.videos[currentIndex],
                videoOffset: offset
            })
        } else {
            message.warning('已经是最后一条了！')
        }

    }

    getCurrentVideo = () => {
        return this.state.videos[this.state.currentIndex] || {};
    }

    itemClick(index, event) {
        this.state.currentIndex = index;
        this.setState({
            currentIndex: index,
            currentVideo: this.getCurrentVideo()
        });
    }

    render() {
        const cur = this.getCurrentVideo();
        const width = Math.max(220 * parseInt(this.state.videos.length), 880);
        const offset = this.state.videoOffset;
        return (
            <div className='detail clearfix'>
                <div className='title'>
                    <p className='name'>{cur.name}</p>
                    { cur.isDownload ?
                    <div className='operate'>
                        <a href={cur.path} download={cur.name}>
                        <Button className='down-button' icon='arrow-down'></Button>
                        <span className='text'>视频下载</span>
                        </a>
                    </div>
                    : null    
                    }
                </div>
                <div style={{width: 1000,height: 547}}>
                    {
                        cur.path ? 
                        <video ref='myVideo' id={`videoID`} preload='auto' className='video-js videoID' width='100%' height='100%' controls preload src={cur.path} >
                        </video>
                        :<video ref='myVideo' id={`videoID`} preload='auto' className='video-js videoID' width='100%' height='100%' controls preload >
                        </video>
                    }
                    {/* <video ref='myVideo' id={`videoID`} preload='auto' className='video-js videoID' width='100%' height='100%' controls preload src={cur.path||''} >
                    </video> */}
                </div>
                <div className='preview'>
                    <Button className='button pre-button' icon='left' onClick={this.prePage}></Button>
                    <Button className='button next-button' icon='right' onClick={this.nextPage}></Button>
                    <span className='preview-list-container'>
                        <span className='preview-list' style={{ width: `${width}px`, display: 'inline-block', transform: `translateX(${offset}px)`, transition: '.5s' }}>
                            {
                                this.state.videos.map((item, index) => (
                                    <span key={index} className={`preview-item ${index === this.state.currentIndex ? 'pactive' : ''}`} onClick={this.itemClick.bind(this, index)}>
                                        <div className='resource-name'>{item.name}</div>
                                        <div className='resource-v'>{Math.round(item.size/1024/1024,2)}Mb</div>
                                    </span>
                                ))
                            }
                        </span>
                    </span>
                </div>
            </div>
        )
    }
}