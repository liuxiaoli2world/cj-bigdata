let userMock = require("./user.mock.js");

module.exports = function(imitator) {
 this.app.all("*",function(req,res,next){
 	//跨域或者避免浏览器禁止同源访问
		 res.header("Access-Control-Allow-Origin", "*");
			res.header("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
			res.header("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
			res.header("X-Powered-By",' 3.2.1')
			res.header("Content-Type", "application/json;charset=utf-8"); 
			next();
 })
 let mockConfig = [].concat(userMock);
  for(let config of mockConfig){
	  imitator(config);
  }
}