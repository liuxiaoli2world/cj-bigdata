import styled from 'styled-components';

export const Wrapper = styled.div`
    width: 1000px;
    margin: 20px auto;

    .detail{
        .title{
            text-align:center;
            position: relative;
            margin: 20px 0;
            .name{
                font-size:26px;
                color:#333;
                margin: 15px 0 5px 0;
            }
            .sub-name {
                text-align: center;
            }
            .date,.times{
                font-size:12px;
                color:#999;
            }
            .times {
                margin-left: 20px;
            }
        }
        .operate{
            position:absolute;
            top: 6px;
            right: 11px;
            width: 90px;
            background: #3d95d5;
            border-radius: 4px;
            padding : 5px;
            color:#000;
            font-size:12px;
            // margin: 18px 10px 18px 0;
            cursor: pointer;
            .down-button {
                width: 20px;
                height: 20px;
                border-radius: 10px;
                color: #3d95d5;
                text-align: center;
                padding-left:3px;
                font-weight:bolder;
                margin-right: 5px;
                padding-top:0;
            }
            .text {
                color: white;
            }
        }
        .preview {
            text-align: center;
            margin: 40px 0 60px 0;
            overflow: hidden;
            position: relative;
            height: 100px;
            .button {
                width: 28px;
                height:  55px;
                vertical-align: top;
                color: #d6d6d6;
                border: 1px solid #d6d6d6;
                border-radius: 0;
                position: absolute;   
                top: 22px;             
            }
            .preview-list-container {
                overflow: hidden;
                display:inline-block;
                width: 880px;
                text-align: left;
                font-size: 0;
                .resource-name{
                    height: 44px;
                    margin-bottom: 8px;
                    font-size: 14px;
                }
                .resource-v{
                    font-size: 12px;
                }
            }
           
            .pre-button {
                left: 10px;
            }
            .next-button {
                right: 10px;
            }
            .preview-item {
                display: inline-block;
                width: 190px;
                height: 100px;
                margin: 0 15px;
                background: #f2f2f2;
                box-shadow: inset 0 0 0 1px #d9d9d9;
                text-align: left;
                padding: 15px 10px;
                cursor:pointer;
                &.pactive{
                    box-shadow: inset 0 0 0 2px #108ee9;
                    background: #fff;
                }
            }
        }
    }
`;