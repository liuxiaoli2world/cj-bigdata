var webpack = require('webpack');
var path = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var appName = require('./package').name;
var appName = '长江大数据';
module.exports = {

    /*//https://webpack.github.io/docs/webpack-dev-server.html#webpack-dev-server-cli
    devServer: {
      contentBase: './dist', //Content base
      inline: true, //Enable watch and live reload
      host: 'localhost',
      port: 8080
    },
    // http://webpack.github.io/docs/configuration.html#devtool
    devtool: 'source-map',*/

    entry: {
        app: [
		'babel-polyfill',
		__dirname + '/portal/index'
		]
    },
    output: {
        // path.join 路径结合、合并.
        // path.resolve 获取绝对路径.
        path: path.resolve(__dirname, '_dist'), // 内存中生成文件的路径
        // publicPath: '/', // 绝对地址,资源存放路径
        filename: '[name]_[hash:8].js',
    },
   
    externals: {
       
    },
    resolve: {
        root: [
            __dirname + '/portal',
            __dirname + '/node_modules',
            __dirname,
        ],
         alias:{
             "assets" : path.resolve(__dirname, 'portal','views','assets'),
             "util" : path.resolve(__dirname, 'portal','views','util')
        },
        extensions: ['', '.js', '.jsx', '.ts', '.tsx']
    },
    module: {
        loaders: [
        { 
		  test: require.resolve('jquery'), 
		  loader: 'expose?$!expose?jQuery'
		},
        {
            test: /\.jsx?$/,
            loaders: ['babel'],
            exclude: /node_modules|extplugins/
        }, {
            test: /\.css$/,
            loaders: [
				'style?sourceMap', 
				'css'
			]
        }, {
            test: /\.less$/,
            loaders: [
				'style?sourceMap',
				'css', 
    
				'less'
			]
         }, {
            test: /\.scss$/,
            loaders: [
				'style?sourceMap', 
				'css', 
          
				'sass?sourceMap'
			]
        }, {
            test: /\.(jpe?g|png|gif|svg|ico)/i,
            loader: 'url-loader?limit=8192&name=images/[hash:8].[name].[ext]',
             exclude: /node_modules/
        }, {
            test: /\.(ttf|eot|svg|woff|woff2|gexf|xml)/,
            loader: 'file',
             exclude: /node_modules/
        }, {
            test: /\.(eot)/,
            loader: 'url-loader?limit=100000',
            //  exclude: /node_modules/
        },{
            test: /\.(pdf)/,
            loader: 'file',
             exclude: /node_modules/
        }, {
            test: /\.(swf|xap)/,
            loader: 'file',
             exclude: /node_modules/
        }],
    },
    plugins: [
		
        new HtmlWebpackPlugin({
            title: appName,
            template: __dirname + '/portal/index.html',
            favicon: __dirname + '/portal/favicon.ico',
            inject: false,
            minify: {
                html5: true,
                collapseWhitespace: true,
                // conservativeCollapse: true,
                removeComments: true,
                removeTagWhitespace: true,
                removeEmptyAttributes: true,
                removeStyleLinkTypeAttributes: true,
            }
        }),
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
            'window.jQuery':'jquery',
            'window.$': 'jquery'
        }),
		new webpack.ProvidePlugin({
			'fetch': 'imports?this=>global!exports?global.fetch!whatwg-fetch'
		})
    ],
};