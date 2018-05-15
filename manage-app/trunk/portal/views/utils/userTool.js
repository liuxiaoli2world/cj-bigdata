import { get, post } from 'utils/request.js';
const isTockenValid = async function isTockenValid(callBack) {
    let isValid;
    const tocken = localStorage.getItem('btocken');
    const userId = localStorage.getItem('buserId');
    if (tocken) {
        // 根据tocken判断当前登录是否有效
        const repData = await get(`user/user/valiToken?token=${tocken}&id=${userId}&from=admin`);
        if (repData.meta && repData.meta.success) {
            isValid = '1';
        } else {
            isValid = '0';
        }
    } else {
        isValid = '0';
    }
    localStorage.setItem('bisValid', isValid);
    callBack && callBack(isValid);
}
export { isTockenValid };