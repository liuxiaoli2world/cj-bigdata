import styled, {keyframes} from "styled-components";

const showModal = keyframes `
0%{
    opacity:0;
    transform:scale(0);
}
100%{
    opacity:1;
    transform:scale(1);
}
`

const Wrapper = styled.div `
.ant-tabs-bar{
    height:60px;
    padding-top:21px;
    padding-left:34px;
    border-bottom:1px solid #cccccc;
    margin-bottom:0;
}
.ant-tabs.ant-tabs-card > .ant-tabs-bar .ant-tabs-nav-container{
    height:39px;
}
.ant-tabs.ant-tabs-card > .ant-tabs-bar .ant-tabs-tab{
    padding:0;
    width:120px;
    height:38px;
    text-align:center;
    line-height:38px;
    border-radius:0;
    margin-right:0;
    background:#f3f4f9;
    color:#333333;
    border-color:#f3f4f9;
}
.ant-tabs.ant-tabs-card > .ant-tabs-bar .ant-tabs-tab-active{
    height:39px;
    background:#ffffff;
    color:#333333;
    border-color:#cccccc;
}
`;

//----用户管理-----
const UserWrap = styled.div `
background:#ffffff;
padding:0 34px;
.search{
    color:#666666;
    padding:30px 0;
    .ant-select-selection--single{
        width:88px;
        height:32px;
        margin-right:20px;
    }
    .ant-select-selection__rendered{
        line-height:32px;
    }
    .ant-input{
        height:32px;
        line-height:32px;
        width:120px;
        margin-right:20px;
    }
    .ant-btn{
        width:60px;
        height:32px;
        border-radius:2px;
        padding:0;
        background:#3d95d5;
        color:#ffffff;
        border:0 none;
    }
}
.userlist-table{
    .ant-pagination-prev a:after{
        content:"\uE605";
        height:32px;
        line-height:32px;
    }
    .ant-pagination-next a:after{
        content:"\uE604";
        height:32px;
        line-height:32px;
    }
    .ant-table-pagination{
        // margin:100px 0 100px;
        margin:80px 0 80px;
    }
    .ant-pagination-item{
        min-width:32px;
        height:32px;
        line-height:32px;
        border-radius:0;
        margin-right:0;
        float:left;
        font-size:12px;
    }
    .ant-pagination-jump-prev, .ant-pagination-jump-next,.ant-pagination-prev, .ant-pagination-next{
        min-width:32px;
        height:32px;
        line-height:32px;
        border-radius:0;
        margin-right:0;
        float:left;
    }
    .ant-pagination-options-quick-jumper input{
        height:32px;
        line-height:32px;
        border-radius:0;
        width:40px;
    }
    .ant-pagination-item-active{
        background:#3D95D5;
        border-color:#3D95D5;
    }
    .ant-table-bordered .ant-table-thead > tr > th, .ant-table-bordered .ant-table-tbody > tr > td{
        text-align:center;
        color:#666666;
        border-color:#cccccc;
        height:66px;
        padding:0;
        font-size:14px;
    }
    .ant-table{
        border-radius:2px 2px 0 0;
        table{
            border-color:#cccccc;
        }
    }
    .ant-table-thead > tr > th{
        background:#ffffff;
    }
    .ant-table-tbody > tr > td{
        .ant-btn{
            background:#eeeeee;
            border-radius:2px;
            border-color:#cccccc;
            color:#666666;
            padding:0;
            width:20.2%;
            // width:80px;
            height:32px;
            line-height:30px;
            &:hover{
                background:#f1f9ff;
                border-color:#83c4f3;
                color:#3d95d5;
            }
        }
    }
    .ant-table-thead > tr.ant-table-row-hover > td, .ant-table-tbody > tr.ant-table-row-hover > td, .ant-table-thead > tr:hover > td, .ant-table-tbody > tr:hover > td{
        background:#ffffff;
    }
    .ant-table-bordered.ant-table-empty .ant-table-placeholder{border-color:#cccccc;}
}
.editInspector,.userDetails{
    position:fixed;
    left:0;
    top:0;
    width:100%;
    height:100%;
    z-index:9999;
    .mask{
        width:100%;
        height:100%;
        background:rgba(57,63,79,.2);
    }
    h3{
        height:42px;
        line-height:42px;
        color:#ffffff;
        padding-left:48px;
        font-size:16px;
        font-weight:normal;
    }
}
//-- 设置质检员 -- 
.editInspector{
    .contentwrap{
        position:absolute;
        left:47%;
        top:37%;
        width:290px;
        // animation:${showModal} 0.2s linear;
        transform-origin:0 0 0;
        h3{
            background:#393f4f url("images/info.png")  no-repeat 18px center;
        }
        .infoText{
            background:#ffffff;
            text-align:center;
            p{
                padding:38px 0;
                font-size:16px;
                color:#666666;
            }
            &>div{
                padding-bottom:54px;
            }
            .ant-btn{
                width:84px;
                height:32px;
                line-height:30px;
                padding:0;
                color:#666666;
                border-radius:2px;
                border:1px solid #cccccc;
                font-size:14px;
                &:first-child{
                    background:#3d95d5;
                    color:#ffffff;
                    border:0 none;
                    margin-right:20px;
                }
            }
        }
    }
}
//-用户详情-
.userDetails{
    .contentwrap{
        position:absolute;
        left:40%;
        width:520px;
    }
    h3{
        background:#393f4f url("images/user.png")  no-repeat 18px center;
        .anticon{
            float:right;
            margin:14px 34px 0 0;
            cursor:pointer;
        }
    }
    ul{
        background:#f2f2f2;
        padding:0 20px;
        font-size:14px;
        color:#666666;
        padding-top:7px;
        padding-bottom:44px;
        li{
            line-height:40px;
            padding-left:10px;
            &.line{
                height:1px;
                line-height:1;
                border-bottom:1px dotted #cccccc;
                margin:7px 0;
                width:100%;
            }
        }
        table{
            margin-top:7px;
            border:1px solid #cccccc;
            background:#ffffff;
            width:460px;
            border-collapse:collapse;
            td{
                border:1px solid #cccccc;
                text-align:center;
                width:33.33%;
                color:#666666;
                font-size:12px;
                height:50px;
            }
            tr:first-child{
                td{font-size:14px;}
            }
        }
        .abnormal-operation{
            color:#cd1919;
            img{
                vertical-align:middle;
                margin-right:12px;
            }
            .ant-radio-group{
                color:#333333;
                margin-left:34px;
                line-height:62px;
                label{
                    font-size:14px;
                }
                span.ant-radio + *{
                    padding-left:5px;
                    padding-right:14px;
                }
            }
            .ant-btn{
                width:60px;
                height:32px;
                padding:0;
                color:#ffffff;
                background:#3d95d5;
                border:0 none;
                border-radius:2px;
                margin-left:201px;
                font-size:14px;
            }
        }
    }
    
}
`;

//----机构管理----
const OrgWrap = styled.div `
padding:0 34px;
background:#ffffff;
.head{
    padding:30px 0;
    font-size:12px;
    color:#666666;
    .ant-input{
        width:160px;
        height:32px;
        margin-right:20px; 
    }
    .ant-btn{
        background:#3d95d5;
        color:#ffffff;
        border-radius:2px;
        border:0 none;
        padding:0;
        height:32px;
        &:first-of-type{
            width:60px;
        }
        &:last-of-type{
             width:90px;
             float:right;
             font-size:14px;
             img{
                 vertical-align:middle;
                 margin-right:6px;
                 margin-top:-4px;
             }
        }
    }
}
.org-table{
    .ant-pagination-prev a:after{
        content:"\uE605";
        height:32px;
        line-height:32px;
    }
    .ant-pagination-next a:after{
        content:"\uE604";
        height:32px;
        line-height:32px;
    }
    .ant-table-pagination{
        margin:80px 0 80px;
    }
    .ant-pagination-item{
        min-width:32px;
        height:32px;
        line-height:32px;
        border-radius:0;
        margin-right:0;
        float:left;
        font-size:12px;
    }
    .ant-pagination-jump-prev, .ant-pagination-jump-next,.ant-pagination-prev, .ant-pagination-next{
        min-width:32px;
        height:32px;
        line-height:32px;
        border-radius:0;
        margin-right:0;
        float:left;
    }
    .ant-pagination-options-quick-jumper input{
        height:32px;
        line-height:32px;
        border-radius:0;
        width:40px;
    }
    .ant-pagination-item-active{
        background:#3D95D5;
        border-color:#3D95D5;
    }
    .ant-table-bordered .ant-table-thead > tr > th, .ant-table-bordered .ant-table-tbody > tr > td{
        text-align:center;
        color:#666666;
        border-color:#cccccc;
        height:66px;
        padding:0;
        font-size:14px;
    }
    .ant-table{
        border-radius:2px 2px 0 0;
        table{
            border-color:#cccccc;
        }
    }
    .ant-table-thead > tr > th{
        background:#ffffff;
    }
    .ant-table-tbody > tr > td{
        .ant-btn{
            background:#eeeeee;
            border-radius:2px;
            border-color:#cccccc;
            color:#666666;
            padding:0;
            width:13.4%;
            // width:80px;
            height:32px;
            line-height:30px;
            margin-left:20px;
            &:first-child{margin-left:0}
            &:hover{
                background:#f1f9ff;
                border-color:#83c4f3;
                color:#3d95d5;
            }
        }
    }
    .ant-table-thead > tr.ant-table-row-hover > td, .ant-table-tbody > tr.ant-table-row-hover > td, .ant-table-thead > tr:hover > td, .ant-table-tbody > tr:hover > td{
        background:#ffffff;
    }
}

.add-org,.add-user,.user-details{
    position:fixed;
    left:0;
    top:0;
    width:100%;
    height:100%;
    z-index:9999;
}
.org-info,.account-info,.info-msg{
    position:absolute;
    left:45%;
    top:50%;
    margin-top:-140px; 
}
.org-info>div,.account-info>div{
    background:#f2f2f2;padding-bottom:62px;position:relative;
}
.mask{
    background:rgba(57,63,79,.2);
    width:100%;
    height:100%;
}
h3.title{
    height:42px;
    padding-left:48px;
    color:#ffffff;
    line-height:42px;
    font-size:16px;
    font-weight:normal;
    box-sizing:border-box;
    .anticon{
        float:right;
        margin:14px 22px 0 0;
        cursor:pointer;
    }
}
//--机构新增 --
.add-org{
    .org-info{
        width:360px;
        .title{
            background:#393f4f url("images/add-org.png") no-repeat 18px center;
        }
        .ant-form-item-label{
            width:110px;
        }
        .ant-form-item:first-child .ant-form-item-control-wrapper{
            width:200px;
        }
        .ant-form-item:nth-child(2) .ant-form-item-control-wrapper{
            .ant-input{width:70px;}
            a{
                color:#3d95d5;
                font-size:14px;
                &:hover,&:focus{
                    text-decoration:none;
                }
            }
        }
        .ant-form-item:last-child{
            margin-left:110px;
            .ant-btn-primary{width:70px;}
        }
        .ant-input[disabled]{
            background:#f2f2f2;
            color:#333333;
            cursor:initial;
            border:0 none;
            font-size:14px;
        }
    }
}
//--批量新增用户--
.add-user{
    .account-info{
        width:340px;
        .title{
            background:#393f4f url("images/add-user.png") no-repeat 18px center;      
        }
    }
    .ant-input[disabled]{
        background:#ececec;
        color:#666666;
        border-color:#cccccc;
    }
}
.info-form{
    padding-top:30px;
    .ant-form-item-label{
        float:left;
        width:120px;
        label{
            font-size:14px;
            color:#666666;
        }
    }
    .ant-form-item-control-wrapper{
        float:left;
        width:150px;
    }
    .ant-form-item{
        margin-bottom:20px;
        &:last-child{
            padding-top:10px;
            margin-left:120px;
            margin-bottom:0;
        }
        .ant-btn-primary{
            background:#3d95d5;
            border:0 none;
            width:60px;
            height:32px;
            padding:0;
            border-radius:2px;
        }
        .has-error .ant-form-explain, .has-error .ant-form-split{
            height:0;
            font-size:12px;
        }
    }
    .ant-input{
        font-size:14px;
    }
}
//用户详情
.user-details{
    .info-msg{
        width:290px;
        .title{
            background:#393f4f url("images/info.png") no-repeat 18px center;      
        }
        &>div{
            background:#ffffff;
            text-align:center;
            padding-bottom:54px;
            p{
                font-size:16px;
                color:#666666;
                line-height:90px;
            }
            .ant-btn{
                width:84px;
                height:32px;
                padding:0;
                background:#ffffff;
                border:1px solid #cccccc;
                color:#666666;
                border-radius:2px;
                font-size:14px;
                &.add-btn{
                    background:#3d95d5;
                    border:0 none;
                    color:#ffffff;
                    margin-right:20px;
                }
            }
        }
    }
}
.ant-btn.ant-btn-loading:not(.ant-btn-circle):not(.ant-btn-circle-outline){
    padding-left:6px;
}
`;

//---异常设置----
const ExceptionWrap = styled.div `
background:#ffffff;
padding-bottom:100px;
&>div{
    position:relative;
    line-height:1;
}
label.label-message{
    width:142px;
    text-align:right;
    display:inline-block;
    font-size:14px;
    color:#666666;
    &:before{
        display:inline-block;
        content:"*";
        color:#cd1919;
        margin-right:4px;
        font-family:SimSun;
        font-size:12px;
        line-height:1;
    }
}
.ant-input{
    width:300px;
    height:32px;
    line-height:32px;
    margin:40px 0 30px;
    border-color:#cccccc;
    &.error{
        border-color:#cd1919;
        &.hover,&:focus{
            border-color:#cd1919;
            box-shadow:0 0 0 2px rgba(205, 25, 25,.2);
        }
    }
}
.info-message{
    color:#cd1919;
    font-size:12px;
    margin-left:16px;
}
p.error-message{
    position:absolute;
    left:142px;
    bottom:15px;
    line-height:1;
    color:#cd1919;
}
span.ant-radio + *{
    padding-left:4px;
    padding-right:0;
    font-size:14px;
    color:#333333;
}
.ant-radio-wrapper:first-child{
    margin-right:40px;
}
.ant-btn{
    margin:46px 0 0 142px;
    width:84px;
    height:32px;
    padding:0;
    border-radius:2px;
    background:#3d95d5;
    color:#ffffff;
    border:0 none;
    &:focus,&:hover{
        background:#3d95d5;
        color:#ffffff;
    }
}
.ant-btn.ant-btn-loading:not(.ant-btn-circle):not(.ant-btn-circle-outline){
    padding-left:0;
}
`;

//----意见箱---
const Box = styled.div `
background:#ffffff;
padding:0 34px;
.search{
    font-size:12px;
    color:#666666;
    padding:30px 0;
    .ant-input{
        width:140px;
        height:32px;
        margin-right:20px;
    }
    .ant-select-selection--single{
        width:88px;
        height:32px;
        margin-right:20px;
        font-size:12px;
        color:#333333;
    }
    .ant-select-selection__rendered{
        line-height:32px;
    }
    .ant-btn{
        width:60px;
        height:32px;
        padding:0;
        background:#3d95d5;
        border-radius:2px;
        color:#ffffff;
        border:0 none;
    }
}
.opinion-list{
    padding-bottom:40px;
    .opinion-item{
        padding-top:48px;
        border-bottom:1px dashed #cccccc;
        &:first-child{
            padding-top:5px;
        }
        dl{
            dt{
                width:114px;
                padding-left:30px;
                float:left;
                min-height:96px;
                img{
                    width:55px;
                    height:55px;
                }
            }
            dd{
                &.date{
                    font-size:12px;
                    color:#999999;
                    line-height:16px;
                }
                &.email{
                    font-size:14px;
                    padding-top:12px;
                    line-height:30px;
                    color:#666666;
                    span{color:#3d95d5;}
                }
                &.con{
                    line-height:28px;
                    font-size:14px;
                    color:#666666;
                    word-wrap:break-word;
                    word-break:break-all;
                    padding-left:114px;
                }
            }
        }
        .reply-input{
            padding-left:114px;
            width:${ 1034 / 16.24}%;
            textarea{
                height:78px;
                resize:none;
                float:left;
                word-wrap:break-word;
                word-break:break-all;
            }
            .ant-btn{
                display:block;
                width:70px;
                height:32px;
                padding:0;
                background:#3d95d5;
                color:#ffffff;
                border-radius:2px;
                border:0 none;
                float:right;
                margin-top:10px;
            }
        }
        .reply-content{
            width:${ 920 / 16.24}%;
            height:78px;
            border:1px solid #cccccc;
            position:relative;
            background:#f2f2f2;
            margin-left:114px;
            margin-top:6px;
            padding:0 20px;
            font-size:14px;
            color:#3d95d5;
            line-height:2;
            word-wrap:break-word;
            padding-top:3px;
            .arrow{
                position:absolute;
                top:-21px;
                left:50px;
                *{
                    display:block;
                    border-width:10px;
                    position:absolute;
                    border-style:dashed dashed solid dashed;
                    font-size:0;
                    line-height:0;
                }
                em{
                    border-color:transparent transparent #cccccc;
                }
                span{
                    border-color:transparent transparent #f2f2f2;
                    top:1px;
                }
            }
        }
        .action{
            float:right;
            line-height:48px;
            margin-right:${ 154 / 16.24}%;
            font-size:14px;
            a{
                color:#3d95d5;
                &:last-child{
                    display:inline-block;
                    padding-left:8px;
                    border-left:2px solid #999999;
                    line-height:1;
                    margin-left:8px;
                }
            }
            i{
                width:2px;
                height:16px;
                background:#666666;
                margin:0 8px;
                display:inline-block;
            }
        }
    }
}
.ant-pagination{
    float:right;
    padding-bottom:30px;
}
`;

//用户详情
const DetailWrap = styled.div`
padding:40px 34px 30px;
background:#ffffff;
.container{
    border:1px solid #cccccc;
    position:relative;
    .ant-table-thead > tr > th, .ant-table-tbody > tr > td{
        text-align:center;
        color:#666666;
        height:66px;
        padding:0;
        font-size:14px;
        border-bottom:1px solid #cccccc;
    }
    .ant-table{
        border-radius:2px 2px 0 0;
        table{
            border-color:#cccccc;
        }
    }
    .ant-table-thead > tr > th{
        background:#ffffff;
        &.ant-table-selection-column{
            width:5%;
            padding-left:2%;
        }
    }
    .ant-table-tbody > tr > td{
        &.ant-table-selection-column{
            width:5%;
            padding-left:2%;
        }
        img{
            vertical-align:middle;
            margin-left:10px;
            margin-top:-4px;
        }
    }
    .ant-table-thead > tr.ant-table-row-hover > td, .ant-table-tbody > tr.ant-table-row-hover > td, .ant-table-thead > tr:hover > td, .ant-table-tbody > tr:hover > td{
        background:#ffffff;
    }
    .ant-table-scroll{
        width:101.4%;
    }
    .ant-table-pagination{
        margin:16px 20px;
    }
    
    footer{
        height:60px;
        line-height:60px;
        width:100%;
        background:#f2f2f2;
        border-top:1px solid #cccccc;
        padding-left:58px;
        font-size:14px;
        color:#666666;
        .ant-input{
            width:180px;
            height:32px;
            margin-right:6px;
        }
        .ant-btn{
            width:80px;
            height:32px;
            padding:0;
            background:#3d95d5;
            color:#ffffff;
            border-radius:2px;
            border:0 none;
            font-size:14px;
            float:right;
            margin:14px 44px 0 0;
        }
    }
}
`;

export {Wrapper, UserWrap, ExceptionWrap, Box, OrgWrap, DetailWrap};