var express = require('express');
var webpack = require('webpack');
var webpackConfig = require('./webpack.development');
var https = require('https');
var path = require("path");
var app = express();
var compiler = webpack(webpackConfig);

app.use("/assets",express.static(path.join(__dirname, 'portal','views', 'assets')));

app.use(require('webpack-dev-middleware')(compiler, {
    // publicPath: webpackConfig.output.publicPath,
    noInfo: true,
    stats: {
        colors: true,
    },
}));


app.use(require('webpack-hot-middleware')(compiler));

app.set('port', process.env.PORT || 8102);


app.listen(app.get('port'), (err) => {
    if (err) {
        console.log(err);
    }
    console.log('监听端口:' + app.get('port') + ', 正在构建,请稍后...');
})
