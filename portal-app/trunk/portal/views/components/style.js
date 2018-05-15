import styled from "styled-components";

export const ListWrapper = styled.div`
    width: 520px;
    color: #333;
    .cover{
        width:116px;
        height:160px;
        border: 1px solid #ccc;
        margin-right:20px;
        .imgbox{
            width: 110px;
            height:150px;
            margin: 5px 3px;
            img{
                width: 100%;
                height: 100%;
            }
        }
    }
    .book,.journal{
        padding: 30px 0;
        border-bottom: 1px dashed #ccc;
        &:last-child{
            border-bottom:none;
        }
        .title{
            padding-left: 14px;
            position: relative;
            height: 54px;
            width: 383px;
        }
        .intro{
            font-size:0;
            .title{
                font-size: 16px;
                color: #3d95d5;
                &:before{
                    position: absolute;
                    display: block;
                    top: 8px;
                    left:0px;
                    width: 4px;
                    height: 4px;
                    background: #3d95d5;
                    content: '';
                    margin: 0 10px 4px 0;
                }
            }
            .other{
                font-size: 14px;
                padding-left: 14px;
                .author{
                    margin-right: 20px;
                }
            }
            .key{
                padding-left: 14px;
                font-size: 14px;
                line-height:30px;
                .keywords{
                    color:#3d95d5;
                }
            }
        }
    }
    .journal{
        .title{
            height: 24px;
            width: 500px;
            margin-bottom:16px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .content{
            font-size:14px;
            width:520px;
            padding:0 5px 26px 14px;
        }
        .other,.key{
            padding:0 5px;
        }
        // .other{
        //     margin-top:26px;
        // }
    }
    .image{
        float:left;
        width: 240px;
        margin: 24px 0 16px 0;
        &:nth-child(2n){
            margin-left: 40px;
        }
        .title{
            text-align:center;
            font-size:14px;
            color:#666;
            margin-top:10px;
        }
    }
`;