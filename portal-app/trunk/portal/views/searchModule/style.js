import styled from 'styled-components';

export const Wrapper = styled.div`
    margin: 20px 0;
    .input {
        margin: 20px 0;
    }
    .input input{
        display: inline-block;
        width:270px;
        line-height:30px;
        font-size:14px;
        color: #999;
        margin-right:14px;
        border-radius: 0;
    }
    .searchBtn {
        height: 30px;
        width: 110px;
        color: white;
        background: #3d95d5;
        margin-left:35px;
        border-radius: 0;
    }

    .tag {
        float: right;
        .order-tag {
            background: #eee;
            color: #666;
            font-size: 14px;
            height:26px;
            padding: 4px 8px;
            margin-left: 10px;
            cursor: pointer;
        }
        .order-tag.active{
            background: #e1edfb;
        }
        .order-tag:hover {
            background: #e1edfb;
        }
    }

    .item-title {
        font-size: 16px;
        color: #333;
        margin: 30px 0 20px 0;
        padding-left: 10px;
        position: relative;
        cursor: pointer;
        font-weight: 600;
        &:hover{
            color: #3d95d5;
        }
        &:before{
            position: absolute;
            display: block;
            top: 10px;
            left: 0px;
            width: 4px;
            height: 4px;
            background: #3d95d5;
            content: '';
            margin: 0 10px 4px 0;
        }
    }
    .item-content {
        font-size: 14px;
        margin-bottom: 20px;
        color: #999;
        cursor: pointer;
        p{
            display:none;
            &:first-child{
                overflow:hidden; 
                display:block;
                // text-overflow:ellipsis;
                // display:-webkit-box; 
                // -webkit-box-orient:vertical;
                // -webkit-line-clamp:2; 
            }
        }
    }
    .item-author {
        font-size: 14px;
        margin-bottom: 20px;
        color: #999;
    }
    .item-date-tag {
        font-size: 14px;
        margin-left: 50px;
    }
    .item-keywords {
        font-size: 14px;
        color: #3d95d5;
    }
    .pagenation{
        margin: 40px 0;
        text-align: center; 
    }
    .key{
        font-size: 14px;
        color: #666;
        margin-top: 30px;
        // margin-bottom: -10px;
    }
    .resultBox{
        border-bottom: 1px solid #eee;
        padding-bottom: 30px;
    }
    .highlight2{
        color:red;
        font-weight: 600;
    }
`;