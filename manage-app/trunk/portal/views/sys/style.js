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
    .ant-tabs:not(.ant-tabs-vertical) > .ant-tabs-content > .ant-tabs-tabpane{
        padding: 14px 0 0 34px;
    }
    .ant-tabs-bar{
        margin-bottom:16px;
    }
    .ant-menu-submenu-inline > .ant-menu-submenu-title:after{
        opacity: 0;
    }
    .ant-menu-submenu-inline > .ant-menu-submenu-title:before{
        font-family: "anticon" !important;
        font-style: normal;
        vertical-align: baseline;
        text-align: center;
        text-transform: none;
        text-rendering: auto;
        position: absolute;
        transition: transform .3s;
        left: 16px;
        top: 0;
        display: inline-block;
        font-size: 12px;
        transform: scale(0.66666667) rotate(0deg);
        zoom: 1;
        content: "\uE604";
    }
    .ant-menu-submenu-open.ant-menu-submenu-inline > .ant-menu-submenu-title:before{
        transform: rotate(90deg) scale(0.75);
    }
    .ant-menu-item:hover, .ant-menu-item-active,
    .ant-menu:not(.ant-menu-inline) .ant-menu-submenu-open, 
    .ant-menu-submenu-active, .ant-menu-submenu-title:hover{
        color: #fff;
    }
    .doc .ant-menu-root .ant-menu-submenu-active{
        color: rgba(0, 0, 0, 0.65);
    }
    .ant-menu-inline .ant-menu-item:after, .ant-menu-vertical .ant-menu-item:after,.ant-menu-inline, .ant-menu-vertical{
        border:none;
    }
`

export default Wrapper;