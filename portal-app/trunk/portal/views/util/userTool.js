import { get, post } from 'util/request.js';
import {SubEmiter,Emiter} from 'util';
const isTockenValid = async function isTockenValid(callBack) {
    let isValid;
    const tocken = window.localStorage.getItem('tocken');
    const userId = window.localStorage.getItem('userId');
    if (tocken) {
        // 根据tocken判断当前登录是否有效
        const repData = await get(`user/user/valiToken?token=${tocken}&id=${userId}&from=index`);
        if (repData.meta && repData.meta.success) {
            isValid = '1';
        } else {
            isValid = '0';
            window.localStorage.clear();
            Emiter.emit('loginChange');
        }
    } else {
        isValid = '0';
    }
    window.localStorage.setItem('isValid', isValid);
    callBack && callBack(isValid);
}
export { isTockenValid };