import styled from "styled-components";

export const Wrapper = styled.div`
    width: 1000px;
    margin: 0 auto;
    .gray{
        color: #999;
    }
    .content{
        .banner{
            margin-bottom: 30px;
        }
        .main{
            .lists{
                width: 520px;
                .titleBar{
                    font-size: 20px;
                    font-weight: bold;
                    color:#336699;
                    border-bottom: 1px solid #336699;
                    width: 520px;
                    padding-bottom: 10px;
                    img{
                        vertical: middle;
                        margin: 0 10px 3px 20px;
                    }
                }
                .books{
                    .searchWrapper{
                        font-size: 14px;
                        margin-top: 16px;
                        input{
                            width: 180px;
                            height: 30px;
                            border:1px solid #ccc;
                            margin: 0 20px 0 10px;
                            border-radius:0;
                        }
                        button {
                            width: 58px;
                            height: 30px;
                            background: #E1EDFB;
                            border:1px solid #ccc;
                            color: #333;
                        }
                    }
                }
            }
            .aside_rt{
                margin-left:28px;
                .tab{
                    width:236px;
                    height:40px;
                    line-height:40px;
                    padding-left:60px;
                    margin-top:4px;
                    color:#3d95d5;
                    font-size:18px;
                    cursor: pointer;
                    &:first-child{
                        margin-top:0;
                    }
                    &.book{
                        background:url('assets/images/ibook_1.png') no-repeat 20px center;
                        background-color:#e1edfb;
                        &.active{
                            background:url('assets/images/ibook.png') no-repeat 20px center;
                            background-color:#3d95d5;
                            color:#fff;
                        }
                    }
                    &.journal{
                        background:url('assets/images/journal_1.png') no-repeat 20px center;
                        background-color:#e1edfb;
                        &.active{
                            background:url('assets/images/journal.png') no-repeat 20px center;
                            background-color:#3d95d5;
                            color:#fff;
                        }
                    }
                    &.images{
                        background:url('assets/images/image_1.png') no-repeat 20px center;
                        background-color:#e1edfb;
                        &.active{
                            background:url('assets/images/image.png') no-repeat 20px center;
                            background-color:#3d95d5;
                            color:#fff;
                        }
                    }
                }
                .fiximg{
                    margin-top:20px;
                    width:236px;
                    height:100px;
                    img{
                        width:100%;
                        height:100%;
                    }
                }
            }
        }
        .noData{
            text-align:center;
            margin:150px 0;
        }
        .basic{
            position:relative;
            width:786px;
            background:#eee;
            border:1px solid #ccc;
            padding:20px;
            font-size:14px;
            margin-bottom:50px;
            .info{
                p{
                    padding-top:6px;
                    .blue{
                        color:#3d95d5;
                    }
                    &:first-child{
                        padding-top:0;
                    }
                }
            }
            .buttons{
                position:absolute;
                right:140px;
                top:120px;
            }
            .cover{
                position:absolute;
                right:20px;
                top:20px;
                background:#fff;
                padding:2px 4px;
                border:1px solid #ccc;
                img{
                    width:82px;
                    height:122px;
                }
            }
        }
        .detail_intro,.list{
            width: 786px;
        }
        .list>.listBox{
            padding: 8px 0 8px 20px;
            line-height: 30px;
            div{
                cursor: pointer;
            }
        }
        .detail_intro{
            .intro_content{
                font-size: 14px;
                line-height: 24px;
                padding: 15px 20px;
                margin-bottom: 40px;
                color: #999;
            }
        }
        .journal{
            .jourTitle{
                font-size:20px;
                font-weight:bold;
                color:#336699;
                border-bottom:1px solid #3f93cd;
                padding-bottom:5px;
                margin-bottom:28px;
                img{
                    vertical-align: middle;
                    margin: 0 8px 3px 25px;
                }
            }
            .journal_intro{
                width:786px;
                padding-left:20px;
                font-size:14px;
                margin-bottom:50px;
                line-height:24px;
                color: #999;
                .blue{
                    color:#3d95d5;
                }
            }
            .info{
                padding-left:20px;
                line-height:30px;
                .blue{
                    color:#3d95d5;
                }
            }
            .download{
                margin:46px 0 76px 0;
            }
        }
        .pagenation{
            margin: 30px 0 50px 0;
            text-align:center;
            position: relative;
            width: 600px;
            .ant-pagination-item{
                border-radius:0;
            }
            .ant-pagination-item-active{
                background:#3d95d5;
                border-color:#3d95d5;
            }
            .ant-pagination-options-quick-jumper input{
                border-radius: 0;
            }
        }
        .ant-pagination-prev{border:0 none;}
        .ant-pagination-next{border:0 none;}
        .ant-pagination-prev a:after{
            content:"上一页";
            font-size:16px;
            color:#666666;
        }
        .ant-pagination-next a:after{
            content:"下一页";
            font-size:16px;
            color:#666666;
        }
       
    }
    .noRelease{
        text-align: center;
        img{
            margin-right: 15px;
        }
    }
    .img_detail{
        .img_title{
            text-align:center;
            .img_name{
                font-size:26px;
                color:#333;
                margin: 15px 0 5px 0;
            }
            .img_date{
                font-size:12px;
                color:#999;
            }
        }
        .operate{
            color:#000;
            font-size:12px;
            text-align:right;
            margin: 18px 10px 18px 0;
            .full{
                display:inline-block;
                img{
                    vertical-align: middle;
                    margin-bottom: 2px;
                }
            }
        }
        .preview{
            position:relative;
            margin: 30px 0 100px 10px;
            .pre_images,.next_images{
                position:relative;
                margin-top:-8px;
                img{
                    cursor: pointer;
                }
                .border1,.border2,.border3{
                    position:absolute;
                    width:98px;
                    height:98px;
                    border:1px solid #eee;
                    background:#fff;
                }
                .border1{
                    z-index:10;
                    left:2px;
                    top:2px;
                    img{
                        margin:5px;
                    }
                    .noPic{
                        margin:27px;
                    }
                }
                .border2{
                    z-index:5;
                    left:4px;
                    top:4px;
                }
                .border3{
                    z-index:0;
                    left:6px;
                    top:6px;
                }
            }
            .next_images{
                left:806px;
                width:102px;
            }
        }
        .imglist{
            margin-right:36px;
            &.firstImg{
                margin-left:198px;
            }
            &.select{
                border: 1px solid #3c93d8;
                padding: 4px;
                margin-top:-4px;
            }
        }
    }
    .ant-timeline{
        margin: 12px 0 0 20px;
    }
    .ant-timeline-item{
        padding: 0 0 32px;
        .ant-timeline-item-head{
            width:18px;
            height:18px;
        }
        &.active{
            .ant-timeline-item-head{
                background:#3d95d5;
            }
        }
    }
    .ant-timeline-item-tail{
        left:8px;
        border-left:2px solid #3d95d5;
    }
    .prevButton, .nextButton{
        position:absolute;
        top:15px;
        width:28px;
        height:56px;
        border: 1px solid #d6d6d6;
    }
    .prevButton{
        left:136px;
        background:url('assets/images/left.png') no-repeat center;
    }
    .nextButton{
        right:152px;
        background:url('assets/images/right.png') no-repeat center;
    }
    .ant-carousel .slick-prev, .ant-carousel .slick-next {
        top: calc(100% + 55px);
        width:28px;
        height: 56px;
        border:1px solid #d6d6d6;
    }
    .ant-carousel .slick-prev,.ant-carousel .slick-prev:hover, .ant-carousel .slick-prev:focus{
        left:136px;
        background:url('assets/images/left.png') no-repeat center;
    }
    .ant-carousel .slick-next,.ant-carousel .slick-next:hover,.ant-carousel .slick-next:focus{
        
    }
`;