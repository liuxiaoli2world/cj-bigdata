import styled from 'styled-components';

const Wrapper = styled.div`
font-size:14px;
.clear{
  clear:both;
}
.crumb{
//   margin-top:22px;
  font-size:12px;
  padding:0;
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
    .ant-breadcrumb-separator{
       margin:0 4px; 
    }
}
//导航
.nav{
    float:left;
    width:186px;
    // height:426px;
    // border:1px solid #cccccc;
    margin-top:30px;
    margin-bottom:76px;
    a{
        color:#3d95d5;
    }
    .nav_detail{
        // height:242px;
        border:1px solid #e5e5e5;
        border-bottom:0 none;
        .picture{
            width:110px;
            height:110px;
            border:2px solid #639cff;
            border-radius:110px;
            margin:14px auto auto 38px;
            text-align:center;
            line-height:103px;
            img{
               width:100px;
               height:100px;
               border-radius:100px; 
            }
        }
        p{
            padding-top:10px;
            color:#3d95d5;
            text-align:center;
        }
        .member{
            // border:1px solid #679eff;
            text-align:center;
            padding-top:8px;
            p{
                color:#d3d3d3;
                font-size:12px;
                padding:10px 12px;
                line-height:20px;
            }
            .oval{
                display:inline-block;
                width:55px;
                height:22px;
                background:#eef1f5;
                border-radius:10px;
                text-align:center;
                line-height:22px;
                margin-bottom:20px;
                a{
                    color:#3d95d5;
                    font-size:14px;
                    font-weight:normal;
                }
            }
            .opened{
                display:inline-block;
                width:70px;
                height:22px;
                background:#eef1f5;
                border-radius:10px;
                text-align:center;
                line-height:22px;
                a{color:#f1035a;font-size:14px;}
            }
        }
    }
    .nav_list{
        position:relative;
        height:42px;
        text-align:center;
        line-height:42px;
        background:#f5f5f5;
        border-bottom:1px solid #ffffff;
        img{
            width:18px;
            height:18px;
            position:absolute;
            top:12px;
            left:36px;
        }
    }
    .nav_list_copy{
        position:relative;
        height:42px;
        text-align:center;
        line-height:42px;
        background:#3d95d5;
        color:#ffffff;
        border-bottom:1px solid #ffffff;
        img{
            width:18px;
            height:18px;
            position:absolute;
            top:12px;
            left:36px;
        }
    }
}

//内容
.content{
    float:left;
    margin:30px auto auto 20px;
}
 .indent_head,
 .center_head,
 .modify_head,
 .safty_head{
     position:relative;
     height:30px;
     width:786px;
     border-bottom:1px solid #3d95d5;
    //  line-height:20px;
     padding-left:20px;
     div{
        display:inline-block;
        margin-left:16px;
        font-size:20px;
        color:#336699;
        font-weight:bold;
        line-height:18px;
     }
 }
    .safty_head{
        line-height:24px;
        &>div{
            line-height:0;
        }
    }
 .indent_title{
     margin-top:15px;
     height:34px;
     width:786px;
     background:#f5f5f5;
     text-align:center;
     line-height:34px;
     color:#33333;
     li{
         display:inline-block;
         width:114px;
         //border-right:1px solid black;
     }
     li:last-child{
         width:144px;
         //border-right:none;
     }
     li:first-child{
         width:296px;
     }
 }
 .indent_list{
     width:786px;
     height:152px;
     border:1px solid #e5e5e5;
     margin-top:15px;
     .indent_list_head{
         height:34px;
         background:#f5f5f5;
         line-height:34px;
         padding-left:20px;
         color:#666666;
         img{
             float:right;
             margin:9px 21px 0 0;
             cursor:pointer;
         }
     }
     .indent_list_content{
        height:118px;
        width:786px;
        text-align:center;
        color:#33333;
        .item_name{
            float:left;
            width:296px;
            height:100%;
            border-right:1px solid #f1f1f1;
            position:relative;
            color:#333333;
            img{
                 width:55px;
                 height:80px;
                 margin:20px 0 0 20px;
                 float:left;
             }
            span{
                float:left;
                display:inline-block;
                width:216px;
                padding:0 28px;
                margin-top:26px;
                word-wrap:break-word;
                text-align:left;
            }
        }
        .item_state{
            float:left;
            width:144px;
            height:100%;
            color:#aaaaaa;
            line-height:30px;
            a{
                color:#333333;
                display:block;
                line-height:1;
            }
            .paynow{
                width:78px;
                height:28px;
                line-height:28px;
                border:1px solid #ff4200;
                border-radius:4px;
                font-size:14px;
                color:#ff612a;
                margin-left:32px;
                margin-top:6px;
            }
        }
        .item{
            line-height:118px;
            float:left;
            width:114px;
            height:100%;
            border-right:1px solid #f1f1f1;
            color:#aaaaaa;
         }
     }
 }

 .basic_information{
     padding-top:20px;
     table{
         margin-top:16px;
         width:786px;
         height:160px;
         border:1px solid #f2f2f2;
         .table_property{
             padding-left:30px;
             text-align:left;
             height:40px;
             width:120px;
             border:1px solid #f2f2f2;
         }
         .table_container{
             padding-left:30px;
             text-align:left;
             border:1px solid #f2f2f2;
         }
     }
 }
 .buy_section{
     margin-top:40px;
     padding-top:20px;
     margin-bottom:70px;
     table{
        width:786px;
        border:1px solid #f2f2f2;
        margin-top:16px;    
        td{
            padding-left:30px;
            height:40px;
            //border:1px solid #f2f2f2;
        }
        .odd{
            background:#f2f2f2;
        }
     }
 }

 .member_head{
     position:relative;
     height:30px;
     width:786px;
     border-bottom:1px solid #3d95d5;
     padding-left:20px;
     .vip{
        display:inline-block;
        margin-left:16px;
        font-size:20px;
        color:#336699;
        font-weight:bold;
        line-height:18px;
     }
     .orga{
         color:#ff6e00;
         font-size:14px;
         position:relative;
         .orga_member{
             display:inline-block;
            margin-left:10px;
         }
         .popup{
            position:absolute;
            right:0;
            top:34px;
            font-size: 12px; 
            line-height:24px;
            padding-top:20px;
            color:#999; 
            display: inline-block;   
            height: 88px;   
            width: 271px;   
            text-align: center;   
            border-radius: 8px;   
            margin-left: 32px;   
            vertical-align: top;   
            background-color: rgb(255, 255, 255);
            box-shadow: 0px 2px 14px 0px rgba(0, 0, 0, 0.2);
            span{
               color:#ff6e00; 
            }
         }
         .arrow {   
            width: 0px;   
            height: 0px;   
            border-left: 10px solid transparent;  
            border-right: 10px solid transparent;    
            border-bottom: 10px solid;   
            position: absolute;   
            margin-left: 220px;   
            margin-top: -30px;   
            border-bottom-color:rgb(255, 255, 255);
        }   
     }
     
 }
 .member_content,
 .center_content{
     width:786px;
     .member_content_item,
     .center_content_item{
         height:94px;
         width:100%;
         line-height:94px;
         border-bottom:1px solid #ebeef5;
         padding-left:50px;
         span{
             color:#3d95d5;
         }
        .ant-btn{transition:none}
         .off{
             height:40px;
             width:87px;
             margin-left:18px;
             border:1px solid #cfcfcf;
             background:#f5f5f5;
             color:#555;
             border-radius:2px;
             span{
                 color:#555;
             }
         }
         .on{
             height:40px;
             width:87px;
             margin-left:18px;
             border:0 none;
              border-radius:2px;
             background:url('assets/images/personal/on.png') no-repeat 50% 50% ;
             span{
                 color:3d95d5;
             }
         }
         .ant-input,.ant-input-number{
             width:70px;
             height:40px;
             border-radius:2px;
             margin-left:18px;
             margin-right:18px;
         }
         .ant-input-number-input{
             height:38px;
             line-height:38px;
         }   
     }
     .pay .ant-btn{
         width:100px;
         height:40px;
         background:#3d95d5;
         border:0 none;
         border-radius:2px;
         font-size:16px;
         font-weight:bold;
         span{color:#ffffff}
     }
     .member_content_last_item{
         width:100%;
         padding-top:30px;
         padding-left:50px;
         span{
             font-size:20px;
             color:#ff6600;
             margin-left:18px;
             margin-right:10px;
         }
         .code{
             padding-left:90px;
             margin-top:10px;
             .square{
                 width:152px;
                 height:152px;
                 margin-bottom:16px;
             }
         }
         .weChat{
             padding-left:90px;
             margin-top:16px;
             margin-bottom:82px;
         }
     }
 }
 .modify_content{
     padding-top:10px;
     .modify_content_item{
         padding-top:40px;
         span{
             display:inline-block;
             width:86px;
             text-align:right;
             font-size:14px;
             color:#333333;
         }
         .ant-input{
             height:40px;
             width:360px;
             border-radius:2px;
             margin-left:20px;
             font-size:14px;
         }
     }
     .modify_content_button_item{
         height:60px;
         padding-top:50px;
         padding-left:220px;
         .ant-btn{
             width:100px;
             height:40px;
             background:#3d95d5;
             color:#ffffff;
             border-radius:2px;
             font-size:14px;
             border:0 none;
         }
     }
 }

   .ant-pagination{
      text-align:center;
      padding:70px 0;
      .ant-pagination-item{
          border-radius:0;
          margin-right:0;
      }
      .ant-pagination-item-active{
          background:#3d95d5;
          border-color:#3d95d5;
      }
        .ant-pagination-prev, .ant-pagination-jump-prev, .ant-pagination-jump-next{
            margin-right:0;
            border-radius:0;
        }
        .ant-pagination-next{
            border-radius:0;
        }
        .ant-pagination-options-quick-jumper input{
            border-radius:0;
        }
  }
   .ant-pagination-prev{border-radius:0;};
   .ant-pagination-next{border-radius:0;}

.indent_list + .pagenation >.ant-pagination{
    text-align:right;
}


`

export default  Wrapper;