import styled from 'styled-components';

const Wrapper = styled.div`
    width: 100%;
    height: 100vh;
    //min-height: 1100px;
    background: url('assets/images/background.png') no-repeat center center;
    background-size:cover;
    text-align: center;
    font-size: 16px;
    .head {   
        padding-top:3.2%;
    }

    .title-img {             
        // margin-top: 60px;
        width:${849/19.2}%;
    }
    .login-content-wrap {
        box-shadow : 0 0 32px rgba(104,24,118,.35);
        display: inline-block;
        //position: absolute;
        margin: 0 auto;
        border-radius: 4px;
        // width: 527px;
        // height: 500px;
        // margin-top: 30px;
        // padding: 0 68px;
        background: #fff;
        width:${527/19.2}%;
        margin-top:${30/10.8}%;
        .login-content{
            width:100%;
            padding:0 ${68/5.27}%;
        }
        p {
            // margin: 10px 0 45px;
            margin:3% 0 11%;
        }
        Input {       
            // lineHeight: 50px;
            // height: 50px;
            height:100%;
            font-size: 14px;
            border-radius: 4px;
        }
        .head-img {
        //    margin-top: 20px;
            margin-top:6%;
           width:${183/3.91}%;
        }
        .ant-input-affix-wrapper{
            height:${58/5}%;
            &:nth-of-type(2){
                margin:4% 0;
            }
        }
        .ant-input-prefix{
            width:5.2%;
            img{
                width:100%;
                max-width:100%;
            }
        }
        .ant-input{
            padding-left:14%;
            font-size:18px;
        }
        
    }
    .ant-input-affix-wrapper .ant-input-prefix{
        left : 5.8%;
    }
    Button {   
        // margin-top: 40px;  
        margin-top:${58/5}%;
        // height: 50px;
        height:${58/5}%;
        width: 100%;
        background: #3e9ade;
        color: #fff;
        font-size: 18px;
        background:-moz-linear-gradient(top, #4faaec,#2886ca);  
        background:-webkit-gradient(linear, 0 0, 0 bottom, from(#4faaec), to(#2886ca));  
        background:-o-linear-gradient(top, #4faaec, #2886ca);
        border:1px solid #236ea4;
    }
`;

export default Wrapper;