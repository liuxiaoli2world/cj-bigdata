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
    .tab-content {
        background: white;
        span {
            display: inline-block;
        }
        .table {
            border: 1px solid #ccc;
            border-top: 0;  
            text-align: center;
            margin-right: 20px;
        }
        .column1 {
            width: 30%; 
            border-left: 0;       
        }
        .column2 {
            width: 8%;   
        }
        .column3 {
            width: 8%;   
        }
        .column4 {
            width: 8%;   
        }
        .column5 {
            width: 8%;   
            strong {
                color: #db4544; 
                font-weight: normal; 
            }
            i{
                display:inline-block;
                line-height:1.5;
                width:100%;
                word-wrap:break-word;
            }
        }
        .column6 {
            width: 8%;   
        }
        .column7 {
            width: 20%;   
            img {
                vertical-align: middle;
            }
        }
        .column8 {
            width: 10%
        }
        .head {
            margin: 0 0 30px 0;
            .select, .datePicker {
                margin-right: 20px;
            } 
            .order-name {
                width: 200px;
                margin-right: 20px;
            }
            .query-btn {
                margin-left: 20px;
                background: #3d95d5;
                color: white;
                border-radius: 2px;
                border: 0;
            }
        }

        .table-head {   
            background: #5a6378;
            color: white;
            line-height: 50px;
            border-top: 1px solid ##ccc; 
            font-size: 16px; 
        }

        .table-body-item {
            .table-body-item-row1 {
                line-height: 44px;
                border: 1px solid ##ccc;
                border-left: 0;
                border-right: 0;
                background: #f2f2f2;
                text-align: left;
                color: ##ccc;
            }
            .table-body-item-row2 {
                line-height: 136px;
                .column1 {                
                    display:inline-block; 
                    vertical-align:middle;
                    text-align: left;  
                }
                .row2-img{
                    vertical-align:middle;  
                    margin-right: 10px;                
                    width: 56px;
                    height: 80px;   
                }
                span:not(:first-child){
                    border-left: 1px solid #f2f2f2;
                }
                .row2.column1{
                    border-left: 0;
                    padding-left: 20px;
                }
                .btn {
                    width: 76px;
                    height: 36px;
                    background: #3d95d5;
                    color: white;
                    border-radius: 2px;
                    border: 0;
                }
            }
        
            .row1-column1 {
                margin-right: 134px;
            }
            .row1-column1,.column1{
                padding-left: 96px;
            }
            .row2.column1 {
                padding-left: 0;
            }
        }
    }
    
    .vip-out {  
        width: 1605px;
        height: 860px;
        font-size: 14px;
        background: #f2f2f2;
        .head {
            line-height: 56px;
            background: #f2f2f2;
            color: #999;
            font-size: 16px;
            img {
                display: inline-block;
                height: 19px;
                width: 23px;
                margin: 0 15px 0 40px;
                vertical-align: text-top;
            }
        }
        .top {
            background: url('images/order-bg.png') no-repeat;
            border-right: 1px solid #d9d9d9;
            width: 100%;
            overflow: hidden;
            height: 293px;
        }          
        .left {  
            float: left;              
        }
        .right {
            float: right;
        }
        .content {
            width: 40%;
            line-height: 40px;
            margin: 40px 75px;
            .sepr {
                border-top: 1px dotted #c2c2c2;
                margin: 10px 0;
            }
            .left-column {
                width: 40%;
                margin: 0 20px;
                float: left;  
            }
            .right-column {
                width: 50%;
                float: right;
            }
            .dir {
                width: 544px;
                height: 170px;
                background: #f3f4f9;
                padding: 20px;
                overflow: auto;
                font-size: 12px;
                div {
                    line-height: 25px;
                }
            }
        }
        .table-head {
            background: white;
            line-height: 50px;
            border: 1px solid #d9d9d9;
            color:  #666;
            margin-top: 56px;
            span {
                display: inline-block;
                text-align: center;
            }
        }
        .table-content {
            background: white;
            border: 1px solid #d9d9d9;
            margin-top: 20px;
            line-height: 80px;
            img{
                width: 54px;
                height: 80px;
                vertical-align: middle;
                margin-right: 20px;
            }
            span {
                display: inline-block;
                padding: 30px 0;
                text-align: center;
            }
        }
        .column1 {
            width: 26%;
        }
        .column2,.column3,.column4 {
            width: 16%;
            border-left: 1px solid #d9d9d9;
        }
        .column5{
            border-left: 1px solid #d9d9d9;
            width: 24%;
        }
    }
     .ant-tabs:not(.ant-tabs-vertical) > .ant-tabs-content{
        padding: 14px 0 0 34px;
    }
    .ant-tabs-bar{
        margin-bottom:16px;
    }
`

export default Wrapper;