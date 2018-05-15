import styled from 'styled-components';

const Wrapper = styled.div`    
    background: white;
    .sper {
        height: 60px;
        background: #f3f4f9;
    }
    .tab {
        padding: 0 0 30px 0;
        // margin-top: -32px;
    } 
    .left {
        float: left
    }
    .right {
        float: right
    }
    .head {
        margin: 0 0 30px 0;
    }
    .label-name:not(:first-child) {
        margin-left: 20px;
    }
    .input {
        display: inline-block;
        width: 196px;
    }
    .btn {
        border-radius: 2px;
        background: #3d95d5;
        color: white;
    }
    .box {
        display: inline-block;
        width: 305px;
        height: 380px;
        border: 1px solid #ccc;
        margin: 0 20px 20px 0;
        position: relative;
        overflow: hidden;
    }
    .box-head {
        padding: 16px;
        background: white;
        height: 314px;
    }
    .head-img {
        background: #f8f8f8;
        height: 182px;
        width: 276px;
    }
    .time-img,.view-img {
        margin-right: 10px;
        vertical-align: text-bottom;
    }
    .content-head {
        // height: 36px;
        font-size: 14px;
        margin: 8px 0;
        color: #333333;
        height:40px;
        -webkit-line-clamp:2;
        overflow:hidden;
        text-overflow:ellipsis;
        display:-webkit-box;
        -webkit-box-orient:vertical;
        word-break:break-all;
    }
    .content-desc {
        font-size: 12px;
        margin: 10px 0;
        color:#666666;
    }
    .newBtn {
        margin-right: 34px;
        border: 0 none;
    }
    .foot {
        clear: both;
        position: absolute;
        background: #f8f8f8;
        width: 100%;
        height: 66px;
        line-height: 66px;
        bottom: 0;
        text-align: center;
        Button {
            width: 128px;
            height: 30px;
            border-radius: 2px;
            &:hover{
                background:#f1f9ff;
                border-color:#83c4f3;
                color:#3d95d5;
            }
        }
        Button:first-child {            
            margin-right: 15px;
        }
    }

    .btn {
        //width: 80px;
        height: 30px;
    }
    .cancel-btn {
        margin-left: 20px;
        background: white;
        color: #3d95d5;
    }

    .form {
        width: 1200px;
    }
    .ant-tabs:not(.ant-tabs-vertical) > .ant-tabs-content > .ant-tabs-tabpane {
        padding: 14px 0 0 34px;
    }
    .ant-tabs-bar{
        margin-bottom:16px;
    }
    .ant-form-item{
        margin-bottom:20px;
        .ant-input{
            height:32px;
        }
    }
    .ant-select-selection--single{
        height:32px;
    }
    .ant-select-selection__rendered{
        line-height:30px;
    }
    .ant-input{
        height:32px;
    }
    .head .ant-btn{
        border: 0 none;
        height:32px;
    }
`

export default Wrapper;