import { hashHistory } from 'react-router';
import config from '../../config/index.js';
import {message} from 'antd';

export default function request(method, url, body) {
  const baseUrl = config.baseUrl;
  url = baseUrl + url;
  method = method.toUpperCase();
  let contentType = 'application/json';
  if (method === 'GET') {
    body = undefined;
  } else if (method === 'FORM-POST') {
    let str = '';
    for (let prop in body) {
      str += `${prop}=${body[prop]}&`;
    }
    body = str.substr(0, str.length-1);
    contentType = 'application/x-www-form-urlencoded';
    method = 'POST';
  } else {
    body = body && JSON.stringify(body);
  }

  return fetch(url, {
    method,
    headers: {
      'Content-Type': contentType,
      'Accept': 'application/json',
      'Access-Token': sessionStorage.getItem('access_token') || ''
    },
    body
  })
    .then((res) => {
      // if (res.status != 200) {
      //   return res.json();
      // } else {
      //   const token = res.headers.get('access-token');
        
      //   if (token) {
      //     sessionStorage.setItem('access_token', token);
      //   }
        return res.json();
      // }
    })
    .then((res) => {
      if(res.meta){
        return res;
      }else{
        message.error('服务器错误')
        console.log(res);
        return {meta:{success:false}}
      }
    })
}

export const get = url => request('GET', url);
export const post = (url, body) => request('POST', url, body);
export const put = (url, body) => request('PUT', url, body);
export const del = (url, body) => request('DELETE', url, body);
export const formPost = (url, body) => request('FORM-POST', url, body);
