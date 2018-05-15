import styled from 'styled-components';

const Wrapper = styled.div`
font-size:14px;
.clear{
  clear:both;
}
.crumb{
  margin-top:22px;
  font-size:12px;
  img{
    width:14px;
    height:13px;
    margin-right:2px;
  }
  p{
    display:inline-block;
  }
  .ant-breadcrumb{
    display:inline-block;
  }
}
.humanity,
.ranking,
.article{
  float:left;
}
.humanity_list{
  height:148px;
  width:715px;
  border-bottom:1px dashed #cccccc;
  margin-top:27px;

  img{
     margin-right: 20px;
     width:170px;
     height:110px;
     float:left;
     border:1px solid #eeeeee;                  
  }
  .humanity_list_container{
    height:110px;
  }
  .humanity_list_name{
    color:#3d95d5;
    font-size:18px;
  }
  .humanity_list_content{
    margin-top:24px;
    color:#333333;
  }
  .humanity_list_date{
    color:#999999;
  }
}
.ranking{
    float:right;
   // margin-top:20px;
    margin-left:40px;
    width:244px;
    .ant-input{
        width:192px;
        height:22px;
        border-radius:0;
        display:inline-block;
        margin-right:6px;
    }
    .ant-btn{
      height:22px;
      width:46px;
      border-radius:0;
      background:#3d95d5;
      color:#ffffff;
      padding-left:9px;
      border:none;
      display:inline-block;
    }
    .ranking_list{
      width:100%;
      height:303px;
      margin-top:12px;
      border:1px solid #cccccc;
      .ranking_head{
        width:100%;
        height:38px;
        background:#e1edfb;
        color:#3d95d5;
        font-size:16px;
        line-height:38px;
        padding-left:12px;
      }
      .ranking_content{
        margin-top:10px;
        padding-left:12px;
        padding-right:4px;
        font-size:16px;
      }
      .ranking_content_list{
        line-height:30px;
        color:#999999;
        white-space:nowrap;
        overflow:hidden;
        text-overflow:ellipsis;
        a{color:#999999;}
      }
      .reading_recommend_list{
        line-height:30px;
        color:#999999;
        white-space:nowrap;
        overflow:hidden;
        text-overflow:ellipsis;
        a{color:#999999;}
      }
      .square{
        text-align:center;
        line-height:14px;
        font-size:10px;
        height:14px;
        width:14px;
        background:#3d95d5;
        color:#ffffff;
        display:inline-block;
        margin-right:6px;
      }
      .gray_square{
        height:4px;
        width:4px;
        background:#cccccc;
        display:inline-block;
        margin-right:12px;
      }
    }
  }
  .pagenation{
    margin:20px;
    display:inline-block;
  }

  .article{
    width:710px;
    .article_header{
      margin-top:16px;
      height:120px;
      border-bottom:1px dashed #cccccc;
      color:#a7a7a7;
      font-size:12px;
      h1{
        text-align:center;
        color:#3d95d5;
        font-size:20px;
        padding-top:36px;
        margin-bottom:30px;
      }
      p{
        display:inline-block;
        a{
          color:#999999;
        }
        .blue{
          color:#3d95d5;
        }
      }
      // p:last-child{
      //   float:right;
      // }
    }
    .article_body{
      padding-top:40px;
      padding-bottom:44px;    
      border-bottom:1px dashed #cccccc;
      line-height: 32px;
    }
    .article_body_small{
      padding-top:40px;
      padding-bottom:44px;    
      border-bottom:1px dashed #cccccc;
      font-size:12px;
    }
    .article_body_big{
      padding-top:40px;
      padding-bottom:44px;    
      border-bottom:1px dashed #cccccc;
      font-size:16px;
    }
    .article_footer{
      height:94px;
      line-height:94px;
      color:#a7a7a7;
      font-size:12px;
      p{
        display:inline-block;
      }
      p:last-child{
        float:right;
      }
    }
  }

   .ant-pagination{
      text-align:center;
      padding:70px 0;
      .ant-pagination-item{
          border-radius:0;
      }
      .ant-pagination-item-active{
          background:#3d95d5;
          border-color:#3d95d5;
      }
      .ant-pagination-options-quick-jumper input{
          border-radius: 0;
      }
  }
  .ant-pagination-prev{border:0 none;};
  .ant-pagination-next{border:0 none;}
  .ant-pagination-prev a:after{
      content:"上一页";
      font-size:16px;
      color:#666666;
  }
  .ant-pagination-next a:after{
      content:"下一页";
      font-size:16px;
      color:#666666;
  }


`

export default  Wrapper;