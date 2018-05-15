import styled from 'styled-components';

const Wrapper = styled.div`    
    background: #f4f7ff;
    
    .top {
        height: 300px;
        background: white;
        margin: 52px 25px;
        // padding: 75px 130px;
        padding:75px 7.7%;
        text-align: center;
        .homeCard {
            width: 28%;
            overflow: hidden;
            float: left;
            color: white;
            font-size: 22px;
            vertical-align: middle;
            cursor: default;
            margin-right:7.3%;
            .left-content {
                float: left;
                display: inline-block;
                width: 60%;
                height: 150px;
                padding: 40px 0;
                span:first-child {
                    display: inline-block;                    
                    font-size: 16px;
                }
            }
            .right-content {
                float: left;
                width: 40%;
                height: 150px;
                vertical-align: middle;
                padding: 46px 0;
            }
        }
        .homeCard:first-child {
            .left-content {
                background: #60b8f6;
            }
            .right-content {
                background: #5bafe9;
            }
        }
        .homeCard:nth-child(2) {
            // margin: 0 100px;
            .left-content {
                background: #ce84de;
            }
            .right-content {
                background: #c37dd3;
            }
        }

        .homeCard:nth-child(3) {
            margin-right:0;
            .left-content {
                background: #55da8d;
            }
            .right-content {
                background: #51cf86;
            }
        }
    }
    .left-chart {
        float: left;
        height: 380px;
        width: 35%;
        background: white;
        // margin: 0 25px 10% 25px;
        margin:0 1.5% 0% 1.5%;
        padding-top:40px;
    }
    .right-chart {
        float: left;
        height: 386px;
        width: 57%;
        background: white;
        // margin: 0 25px 10% 48px;
        margin:0 1.5% 0% 2.8%;
        padding-top:6px;
    }
`

export default Wrapper;