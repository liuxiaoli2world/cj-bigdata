var webpack = require('webpack');
var merge = require('@ersinfotech/merge');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var path = require("path");
var webpackConfig = require('./webpack.config');
var CopyWebpackPlugin = require('copy-webpack-plugin');

process.env.NODE_ENV = 'production';

module.exports = merge(webpackConfig, {
  plugins: [
	new webpack.optimize.OccurenceOrderPlugin(),
    new ExtractTextPlugin('[name]_[contenthash].css', {
      allChunks: true,
    }),
    new webpack.optimize.UglifyJsPlugin({
      output: {
        comments: false,  // remove all comments
      },
      compress: {
        warnings: false,
      }
    }),
     new CopyWebpackPlugin([
            // {output}/file.txt
            { 
              from: path.join(__dirname,"portal","views","assets"),
              to:"assets"
           }

     ]),
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': JSON.stringify(process.env.NODE_ENV || 'production')
    }),
  ],
});
