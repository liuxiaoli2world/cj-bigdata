export default function commonTool(s) {
  let getParams = (s) => {
    const datas = s.split('?');
    const url = datas[0];
    const name = datas[1] && getQueryString(datas[1], 'name') || '';
    const size = datas[1] && getQueryString(datas[1], 'size') || '';
    return {
      size, name, url
    }
  };

  let getQueryString = (s, name) => {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = s.match(reg);
    if (r != null) return unescape(r[2]); return null;
  } 
  return getParams(s);
}

export const getParams = s => commonTool(s);