import styled from 'styled-components';

export const Wrapper = styled.div`
    margin: 20px 0;
    .content {
        margin: 20px 0;
    }
    .left-content {
        float: left;
        width: 242px;
        height: 500px;     
        .recommand-expert {
            border: 1px solid #ccc;
            .head {
                background: #e1edfb;
                font-size: 18px;
                color: #3d95d5;
                padding: 5px 20px;
            }
            .recommand-content {
                height: 130px;
                &:hover{
                    .desc{
                        color: #3d95d5;
                    }
                }
            }
            .img {
                width: 75px;
                height: 110px; 
                margin: 6px 10px 10px 17px;
                float: left;
                border: 1px solid #ccc;
                padding: 2px;
            }
            .name {
                margin-top: 10px;
                font-size: 16px;
                color: #3d95d5;
            }
            .desc {
                margin: 30px 20px 10px 10px;
                color: #999;
                font-size: 14px;
            }
        }
        .recommand-reading {
            border: 1px solid #ccc;
            margin-top: 10px;
            .head {
                background: #e1edfb;
                font-size: 18px;
                color: #3d95d5;
                padding: 5px 20px;
            }
            .reading-list {
                list-style:square inside none;
            }
            .reading-item {
                margin: 10px;
                color: #999;
            }
        }
    }

    .right-content {
        display: inline-block;
        margin-left: 38px;
        width: 720px;
        min-height: 500px;
        .title{
            margin-bottom:15px;
        }
        .main-title{
            height:30px;
            line-height:24px;
            color:#336699;
            img{
                margin-top:4px;
            }
        }
        .line{
            border-top:1px solid #336699;
        }
        .list-content {
            // margin-left:-20px;
            .list-item {            
                display: inline-block;
                width: 220px;
                margin: 30px 10px;
                overflow: hidden;
            }
            .img {
                border: 1px solid #ccc;
                padding: 2px;
                width: 92px;
                height: 144px; 
                display: inline-block;
            }
            .item-content {
                display: inline-block;
                width: 100px;
                height: 144px;
                margin-left: 10px ;
                .name {
                    font-size: 16px;
                    color: #3d95d5;
                }
                .desc {
                    margin: 10px 20px 10px 0;
                    font-size: 14px;
                    color:#333333;
                }
            }
        }
    }

    .expert-detail {
        .base-info {
            margin: 20px 0 40px 0;
            padding: 10px 20px 20px 20px;
            border: 1px solid #ccc;
            background: #eee;
            .color {
                color: #3d95d5;
            }
            .realName,
            .birthday,.nation,.duty,.professionalField {
                margin: 10px 0;
                color: # 666;
            }
            .image {
                width: 90px;
                height: 130px;
                float: right;
                margin-top: 20px;
            }
        }
        .expertDesc {
            margin: 20px 0 60px 0;
        }
        .list {
            margin: 20px 0;
            a{
                color:#999;
                &:hover{
                    color: #3d95d5;  
                }
            }
        }
    }

    .input {
        margin: 0;
    }
    .input input{
        display: inline-block;
        width:270px;
        line-height:30px;
        font-size:14px;
        color: #999;
        margin-right:14px;
        border-radius: 0;
        padding:4px 10px;
        height:30px;
        border-color:#cccccc;
        float:left;
        &:focus,&:active{
            border-color:#3d95d5;
        }
    }
    .searchBtn {
        height: 30px;
        width: 57px;
        color: white;
        background: #3d95d5;
        margin-left:20px;
        border-radius: 0;
        border:0 none;
        float:left;
    }    
    .pagenation{
        margin: 40px 0;
        text-align: center; 
    }
    .ant-pagination{
        text-align:center;
        padding:70px 0;
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
    .ant-pagination-prev, .ant-pagination-next{
        border:0 none;
    }
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
`;