module.exports =function(){
	console.error(this)
	/*imitator({
		action:"all",
		url: '*',
		result:function(req,res){
			res.header("Access-Control-Allow-Origin", "*");
			res.header("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
			res.header("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
			res.header("X-Powered-By",' 3.2.1')
			res.header("Content-Type", "application/json;charset=utf-8");
		    res.send(req.path)
		}
	});
	*/
}