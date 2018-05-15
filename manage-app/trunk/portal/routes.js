import * as React from 'react';
import {
  Route,
  IndexRoute,
  Redirect,
  IndexRedirect,
  hashHistory
} from 'react-router';

import Init from './views/init.js';
import Login from './views/login';
import MyLayout from './views/layout.js';
import Home from './views/home';
import ContentList from './views/content';
import Resource from './views/resource';
import Expert from './views/expert';
import User from './views/user';
import DataAnalaysis from './views/dataAnalaysis';
import Sys from './views/sys';
import { isTockenValid } from './views/utils/userTool.js';

const validErrorRoute = function (nextState, replace) {
  if (window.location.href == window.location.origin + '/#/' || window.location.href == window.location.origin + '/manage') {
    window.location.href = window.location.href + '/#/manage/';
  }

  const pathArray = ['/manage', '/manage/Login', '/manage/home', '/manage/content', '/manage/resource', '/manage/expert', '/manage/user', '/manage/dataAnalaysis', '/manage/sys'];
  const href = window.location.href;
  const origin = window.location.origin;
  const index = href.indexOf('#');
  const pathname = href.substr(index + 1);
  isTockenValid(function (isValid) {
    if (href !== origin + '/#/manage' && pathArray.indexOf(pathname) > -1 && localStorage.getItem('bisValid') === '0') {
      window.location.href = window.location.origin + '/#/manage/Login';
    }
  });
}

export default (
  <Route path='/' onEnter={validErrorRoute}>
    <Route path='/manage' component={Init} />
    <Route path='/manage/Login' component={Login} />
    <Route component={MyLayout}>
      <Route path='/manage/home' component={Home} />
      <Route path='/manage/content' component={ContentList} />
      <Route path='/manage/resource' component={Resource} />
      <Route path='/manage/expert' component={Expert} />
      <Route path='/manage/user' component={User} />
      <Route path='/manage/dataAnalaysis' component={DataAnalaysis} />
      <Route path='/manage/sys' component={Sys} />
    </Route>
    <IndexRoute component={Init} />
  </Route>
);