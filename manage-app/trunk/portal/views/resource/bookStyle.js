import styled from 'styled-components';

export const Wrapper = styled.div`
    .header{
        margin: 0 16px 30px 0;
        .label-name:not(:first-child){
            margin-left:20px;
        }
    }
    .addbtn{
        margin: 0 25px 0 30px;
    }
    
`
export const Box = styled.span`
    display: inline-block;
        width: 307px;
        height: 238px;
        border: 1px solid #ccc;
        margin: 0 20px 20px 0;
        position: relative;
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
                line-height: 30px;
                height: 30px;
                width: 100%;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
            .fullPrice{
                .priceNum{
                    color: #3d95d5;
                }
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
`