var webpack = require('webpack');
var merge = require('@ersinfotech/merge');// 合并多个对象为一个新的对象

var webpackConfig = require('./webpack.config');

process.env.NODE_ENV = 'development';
var devConfig = merge(webpackConfig, {
  devtool: 'cheap-module-eval-source-map',
  debug: true,
    entry: {
    app: ['webpack-hot-middleware/client'],
  },
  plugins: [
    new webpack.optimize.OccurenceOrderPlugin(),
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NoErrorsPlugin(),
    new webpack.DefinePlugin({ // 设置环境变量来压缩代码
      'process.env.NODE_ENV': JSON.stringify(process.env.NODE_ENV || 'development')
    }),
  ],
});

//var entry = devConfig.entry.app.pop();
 
 //devConfig.entry.app.push('react-hot-loader/patch',entry);
//devConfig.entry.app.push('react-hot-loader/patch','webpack-hot-middleware/client?reload=true', 'webpack/hot/only-dev-server',entry);

module.exports =devConfig