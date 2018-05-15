import createHashHistory from 'history/lib/createHashHistory';

let hashHistory = createHashHistory();
let resizeEvent = document.createEvent("Event");
resizeEvent.initEvent("resize", true, true);

export {
	hashHistory,
	resizeEvent
}