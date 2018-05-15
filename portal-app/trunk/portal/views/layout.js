import React from 'react';
import {Link} from 'react-router';
import './reset.css';
import './layout.scss';
import Header from 'portal/views/header/header';
/**
 * 这个文件定义页面的结构
 */
class Layout extends React.Component{
	constructor(props){
		super(props)
	}

    render(){
		const {pathname} = this.props.location;
		const style = (pathname.indexOf('/read')>=0) ? {minHeight:'100%',backgroundColor:'#fcf3ee',marginBottom:'10px'} : {}
		if(pathname.indexOf('/read')>=0) {
			$('html').css('backgroundColor','#f4e5d0');
        	$('body').css({'backgroundColor':'#f4e5d0','height':'calc(100% - 10px)'});
		} else {
			$('html').css('backgroundColor','');
        	$('body').css({'backgroundColor':'','height':''});
		}
	    return (
        	<div style={style}>
				{pathname.indexOf('/read')<0 ?
					<Header id={this.props.params.menuId}/>
				 : null }
	            <div>
		            {this.props.children}
	            </div>
				{pathname.indexOf('/read')<0 ?
	            <footer>
	          		<div className='footer_head'>
	          			<div className='content'>
	          				<Link to='regard/respect'>关于我们</Link>
	          				<span>|</span>
	          				<Link to='regard/adv'>广告合作</Link>
	          				<span>|</span>
							<a style={{color:'white','display':'inline-block'}} target='_blank' href='http://shang.qq.com/email/stop/email_stop.html?qq=3077937385&sig=706e196be799e2f1e34571bc557a5f5f019101a04b6afd5b&tttt=1'>客服客服</a>
						</div>
	          		</div>
	          		<div className='footer_con'>
	          			<div className='content'>
	          				<div className='left copyright'>
	          					鄂ICP备15005036号-1 长江出版社（武汉）有限公司<br/>电话总机：027-82820210 投稿信箱：cjpress@163.com 
	          				</div>
	          				<div className='left footer_logo'></div>
	          			</div>
	          		</div>
	            </footer>
				: null }
	        </div>
	    )
    }
}

export default Layout;