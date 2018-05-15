import styled from 'styled-components';

const Wrapper = styled.div`    
    background: white;
    .top {
        font-size: 14px;
        font-weight: bolder;
        .image {
            width: 188px;
            height: 125px;
            float: left;
            margin: 0 20px 20px 0;
        }
        div {
            line-height: 50px;
            vertical-align: text-top;
        }
        .price {
            color: #57a3db;
        }

    }

    .form {
        width: 1200px;
    }

    .oper {
        float: right;
        margin: 20px 40px;
    }
    .btn {
        border-radius: 2px;
    }

    .page-btn {        
        background: #3d95d5;
        color: white;
        width: 90px;
        height: 32px;
        margin-left: 20px;
    }
    table{
        margin:0 35px 0 0;
        width:calc(100% - 45px);
        text-align:center;
        border-collapse:collapse;
        border:none;
        font-size:14px;
        color:#666;
        td{
            height:68px;
            border:1px solid #ddd;
        }

        .table-btn {
            width: 70px;
            height: 30px;
            border: 1px solid #ddd;
            margin: 10px;
            background: #eee;       
            &:hover{
                background:#f1f9ff;
                border-color:#83c4f3;
                color:#3d95d5;
            }
        }
    }
    .ant-tabs.child-tab{
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