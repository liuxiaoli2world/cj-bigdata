import * as React from 'react';
import {
  Route,
  IndexRoute,
  Redirect,
  IndexRedirect,
  hashHistory
} from 'react-router';

import Layout from './views/layout.js';
import Home from './views/home';
import Information from './views/information/Information.js';
import Detail from './views/information/detail.js'
import SearchModule from './views/searchModule';
import VideoDetail from './views/home/videoDetail.js';
import Humanity from './views/humanity/humanity.js';
import HumanityBody from './views/humanity/humanityBody.js'
import Personal from './views/personal/personal.js';
import Indent from './views/personal/indent.js';
import IndentDetail from './views/personal/detail.js';
import Member from './views/personal/member.js';
import Center from './views/personal/center.js';
import Modify from './views/personal/modify.js';
import Safty from './views/personal/safty.js';
import Login from './views/login/login.js';
import Register from './views/login/register.js';
import Regard from './views/regard/index.js';
import Respect from './views/regard/respect.js';
import Adv from './views/regard/adv.js'
import Expert from './views/experts';
import ExpertList from './views/experts/list.js';
import ExpertDetail from './views/experts/detail.js';
import CWrapper from './views/classify/Wrapper';
import Classify from "./views/classify";
import Book from './views/classify/Book';
import Image from './views/classify/Image';
import Journal from './views/classify/Journal';
import Read from './views/read/read';
import FindPassword from "./views/login/Findpassword";
import ResetPwd from "./views/login/ResetPwd";
import LoginOut from "./views/login/loginOut";

export default (
  <Route path='/' component={Layout}>
    <IndexRoute component={Home}/>
    <Route path = '/searchModule(/:type)(/:keyword)' component = {SearchModule} />	
    <Route path = '/videoDetail' component = {VideoDetail} />
    <Route path = '/information/:title'>
        <IndexRoute component = {Information} />
    		<Route path = "detail/:id" component = {Detail} />
			  <Route path = "clicked/detail/:id" component = {Detail} />
	  </Route>
    <Route path='humanity' component={Humanity}/>
    <Route path='humanitybody/:id' component={HumanityBody}/>
    <Route path='personal' component={Personal}>
      <IndexRoute component={Member}/>
      <Route path='indent' component={Indent}/>
      <Route path='detail/:id/:type' component={IndentDetail}/>
      {/*<Route path='member' component={Member}/>*/}
      <Route path='center' component={Center}/>
      <Route path='modify' component={Modify}/>
      <Route path='safty' component={Safty}/>
    </Route>
    <Route path='login' component={Login}/>
    <Route path='register' component={Register}/>
    <Route path='findpwd' component={FindPassword} />
    <Route path='resetpwd' component={ResetPwd}/>
    <Route path='regard'  component={Regard}>
      <Route path='respect' component={Respect}/>
      <Route path='adv' component={Adv}/>
    </Route>
    <Route path='expert' component={Expert}>
      <Route path = '/expert/list' component = {ExpertList} />
      <Route path = '/expert/detail/:id' component = {ExpertDetail} />
    </Route>
    <Route path="classify/:menuId" component={CWrapper}>
        <Route path="book/:id" component={Book} />
        <Route path="journal/:id" component={Journal} />
        <Route path="image/:id" component={Image} />
        <IndexRoute component={Classify} />
    </Route>
    <Route path='/read/:isbn(/:id)' component={Read}>
    </Route>
    <Route path='loginout' component={LoginOut}>
    </Route>
  </Route>
);