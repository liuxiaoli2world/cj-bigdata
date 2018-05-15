import styled from 'styled-components';

const Wrapper = styled.div`
    background: white;
    .sper {
        height: 60px;
        background: #f3f4f9;
    }
    .tab {
        padding: 0 0 30px 0;
    } 
    .left {
        float: left;
    }
    .right {
        float: right;
    }
    .ant-tabs-bar{
        margin-bottom: 16px;
    }
    .ant-tabs:not(.ant-tabs-vertical) > .ant-tabs-content > .ant-tabs-tabpane{
        padding: 14px 0 0 34px;
    }
    .ant-form-item-label{
        width:82px;
    }
    .ant-col-14{
        width:57.3%;
    }

`

export default Wrapper;