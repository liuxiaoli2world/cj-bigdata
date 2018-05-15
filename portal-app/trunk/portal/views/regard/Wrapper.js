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
.regard_head{
    margin-left:105px;
    margin-top:40px;
    width:718px;
    color:#336699;
    font-size:18px;
    line-height:40px;
    border-bottom:1px solid #336699;
    position:relative;
    img{
        position:absolute;
        top:10px;
    }
    span{
       margin-left:30px;
    }
}
.regard_content{
     margin-left:105px;
     margin-bottom:110px;
     width:718px;
     padding-top:58px;
}
.adv_list{
    margin-top:10px;
    line-height:30px;
    .adv_list_item{
        position:relative;
        img{
            position:absolute;
            top:14px;
            left:4px;
        }
        span{
            dispaly:inline-block;
            margin-left:20px;
        }
    }
}
.respect_list{
    margin-top:50px;
    margin-bottom:130px;
    .respect_list_item{
        height:110px;
        width:720px;
        border-top:1px solid #d6d6d6;
        position:relative;
        line-height:25px;
        padding-top:20px;
        img{
            position:absolute;
            top:30px;
            left:4px;
        }
        p{
            padding-left:34px;
        }
    }
    .respect_list_item:last-child{
        border-bottom  :1px solid #d6d6d6;
    }
}


`

export default  Wrapper;