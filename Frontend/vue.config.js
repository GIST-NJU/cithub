module.exports = {
  publicPath: './',
  lintOnSave:false,//关闭eslint语法检查
  devServer: {
    port:8090,
    proxy: {
      '/api':{
        // target: 'http://localhost:9088',
        target: 'http://localhost:9172',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          // '^/api'是一个正则表达式，表示要匹配请求的url中，全部'http://localhost:8000/api' 转接为 http://localhost:8000/
          '^/api': '',
        }
      }
    }
  },
  configureWebpack:{
    module: {
        rules: [
            {
                test: /\.mjs$/,
                include: /node_modules/,
                type: "javascript/auto"
            },
        ]
    }
}

  

};
