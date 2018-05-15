import styled from 'styled-components';

const Wrapper = styled.div`    
    background: white;
    .box-head {
        .head-img{
            float:left;
            width:105px;
            height:130px;
            margin:15px;
            margin-top: -1px;
            img{
                width:100%;
                height:100%;
            }
        }
        .info{
            float:left;
            // padding-top:15px;
            .content-head{
                font-size: 14px;
                font-weight: bolder;
                color:#333;
                line-height:24px;
            }
            .fullPrice{
                line-height:24px;
            }
            .time{
                line-height:24px;
            }
            .content-desc{
                font-size:12px;
                color:#999;
                padding-top:4px;
                line-height:20px;
            }
        }
        .price {
            color: #3d95d5;
        }

    }
    .sper {
        clear: both;
        height: 60px;
        background: #f3f4f9;
    }
    .tab {
        padding: 0 0 30px 0;
        // margin-top: -32px;
    } 
    .listContent{
        margin-right: 30px;
        border: 1px solid #ccc;
        .listTitle{
            height: 50px;
            line-height: 50px;
            padding: 0 28px;
            border-bottom: 1px solid #ccc;
            .title{
                font-size:16px;
                color:#333;
            }
        }
        .ant-tree{
            font-size: 14px;
            padding-left:20px;
            height: 528px;
            overflow: auto;
            li span.ant-tree-switcher, .ant-tree li span.ant-tree-iconEle{
                margin-top: 2px;
                line-height:32px;
            }
            li .ant-tree-node-content-wrapper{
                width:calc(100% - 40px);
            }
        }
        .noData{
            height: 528px;
            text-align: center;
            padding-top: 150px;
        }
    }
    .listEditor{
        width: 780px;
        border: 1px solid #ccc;
        .listTitle{
            height: 50px;
            line-height: 50px;
            padding: 0 28px;
            border-bottom: 1px solid #ccc;
            .title{
                font-size:16px;
                color:#333;
            }
        }
        .form{
            font-size: 14px;
            height: 528px;
            padding: 30px 10px;
            overflow: auto;
        }
    }
    .contentEditor{
        
    }
    .ant-tabs.child-tab{
        .bookForm{
            margin-top: -24px;
            padding-top: 10px;
        }
        .ant-tabs-content > .ant-tabs-tabpane{
                padding-left: 15px;
            }
        .ant-tabs-bar{
            padding: 0;
            margin: 5px 34px 30px 15px;
            height: 50px;
            line-height: 50px;
            border: 1px solid #ddd;
            
            .ant-tabs-nav-container{
                height: 50px;
                .ant-tabs-nav{
                    left: -1px;
                }
                .ant-tabs-tab{
                    height: 48px;
                    line-height: 44px;
                    font-size: 16px;
                    border-top: 4px solid transparent;
                    &:before{
                        display: table;
                        content: " ";
                        width: 0px;
                        height: 0px;
                        position: absolute;
                        top: 0;
                        left: 55px;
                        border: 4px solid transparent;
                    }
                    &.ant-tabs-tab-active{
                        height: 49px;
                        border-top-color: #3d95d5;
                        &:before{
                            transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
                            border-top-color: #3d95d5;
                        }
                    }
                }
            }
        }
    }
`

export default Wrapper;