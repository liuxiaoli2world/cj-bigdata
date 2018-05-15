module.exports=[
	//登录、注册
	{
		action:"post",
		url: '/api/login',
		type: 'json',
		result: {
			"a": "098f6bcd4621d373cade4e832627b4f6",
			"username": "administrator",
			"role": "admin"
		},
	   timeout: 1000//模拟网络延迟
	  
	},
	{
		action:"get",
		url: '/api/self',
		type: 'json',
		result: {
			 "s": "098f6bcd4621d373cade4e832627b4f6",
			"username": "administrator",
			"role": "admin",
		},
	   timeout: 1500
	},
	
]

