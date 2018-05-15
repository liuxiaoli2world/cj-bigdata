import styled from 'styled-components';

const Wrapper = styled.div`    
    background: white;
    .left {
        float: left;
    }
    .right {
        float: right;
    }
    .head {
        margin: 0 0 30px 0;
    }
    .second-dir-type {
        margin-left: 10px;
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
        border: 0;
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
    .classify-tag {
        //margin-left: 10px;
        //border: 1px solid #3d95d5;
        color: #3d95d5;
        //padding: 2px 4px;
    }
    .content-head {
        height: 36px;
        font-size: 14px;
        margin: 8px 0;
    }
    .content-desc {
        font-size: 12px;
        margin: 10px 0;
    }
    .newBtn {
        margin-right: 20px;
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
        }
        Button:first-child {            
            margin-right: 15px;
        }
    }
`

export default Wrapper;