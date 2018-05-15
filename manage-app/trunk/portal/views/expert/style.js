import styled from 'styled-components';

const Wrapper = styled.div`
    background: white;
    .left{
        float: left;
    }
    .right{
        float: right
    }
    .sper {
        height: 60px;
        background: #f3f4f9;
    }
    .tab {
        padding: 0 0 30px 0;
        // margin-top: -32px;
    }
    .header {
        padding: 0 0 30px 0;
        .search{
            margin-left:18px;
        }
        .addbtn{
            margin-right:40px;
        }
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
    .box{
        display: inline-block;
        width: 307px;
        height: 238px;
        border: 1px solid #ccc;
        margin: 0 20px 20px 0;
        position: relative;
        .suggestImg{
            position: absolute;
            top: 0;
            right: 0;
        }
        .head-img{
            float:left;
            width:105px;
            height:130px;
            margin:15px;
            img{
                width:100%;
                height:100%;
            }
        }
        .info{
            float:left;
            width:156px;
            padding-top:15px;
            .content-head{
                font-size:14px;
                color:#333;
                word-wrap:break-word;
            }
            .content-desc{
                font-size:12px;
                color:#999;
                padding-top:10px;
                line-height:20px;
            }
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
                width: 70px;
                height: 32px;
                border-radius: 0;
                margin-left: 15px;
                text-align:center;
            }
            Button:first-child {            
                margin-left: 0;
                padding:0;
            }
        }
    }
    .expertComp{
        .expertInfo{
            position:relative;
            margin: 10px 35px 30px 0;
            border: 1px solid #ddd;
            padding: 20px;
            .logo{
                width:194px;
                height:240px;
                margin-right:56px;
                img{
                    width:100%;
                    height:100%;
                }
            }
            .info{
                width:calc(100% - 300px);
                font-size:12px;
                line-height:20px;
                color:#666;
                .name{
                    color:#3d95d5;
                    font-size:20px;
                    line-height:50px;
                }
                .expertDesc{
                    margin-top:8px;
                }
                .descContent{
                    width: 65%;
                }
            }
            .edit{
                position:absolute;
                right:20px;
                bottom:20px;
            }
        }
        
    }
    table{
        margin:0 34px 0 0;
        width:calc(100% - 34px);
        text-align:center;
        border-collapse:collapse;
        border:none;
        font-size:14px;
        color:#666;
        td{
            height:68px;
            .btn{
                border: 1px solid #ccc;
                background: #eee;
                font-size: 12px;
                color: #666;
                &:hover{
                    background: #f1f9ff;
                    border-color: #83c4f3;
                    color: #3d95d5;
                }
            }
            .btn:first-child{
                margin-right: 20px;
            }
        }
    }
    .composition td{
        border: 1px solid #ddd;
    }
    .ant-tabs:not(.ant-tabs-vertical) > .ant-tabs-content > .ant-tabs-tabpane{
        padding: 14px 0 0 34px;
    }
    .ant-tabs-bar{
        margin-bottom:16px;
    }
    .expertForm{
        padding-top:14px;
    }
    .domainDesc{
        width: 100%;
        border: 1px solid #d4d4d4;
    }
    .ant-form-item-with-help{
        margin-bottom:3px;
    }

    .ant-table-wrapper{
        margin-right:34px;
    }
    
    
    .ant-table-bordered .ant-table-thead > tr > th, .ant-table-bordered .ant-table-tbody > tr > td{
        background:#ffffff;
    }
    .ant-table-bordered .ant-table-header > table, .ant-table-bordered .ant-table-body > table, .ant-table-bordered .ant-table-fixed-left table, .ant-table-bordered .ant-table-fixed-right table{
        border:1px solid #cccccc;
        border-right:0 ;
        border-bottom:0;
    }
    .ant-table-bordered .ant-table-thead > tr > th{
        border-bottom:1px solid #cccccc;
    }
    .ant-table-bordered .ant-table-tbody > tr > td{
        border-right:1px solid #cccccc;
    }
    .ant-table-bordered .ant-table-thead > tr > th{
        border-right:1px solid #cccccc;
    }
    .ant-table-placeholder{
        border-bottom:1px solid #cccccc;
    }
    .ant-table-bordered.ant-table-empty .ant-table-placeholder{
        border-left:1px solid #cccccc;
        border-right:1px solid #cccccc;
    }
`

export default Wrapper;