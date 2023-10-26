module.exports = {
    devServer: {
      port:8090,
      proxy: {
        '/api':{
          target: 'http://localhost:8000',
          changeOrigin: true,
          ws: true,
          pathRewrite: {
            // '^/api'是一个正则表达式，表示要匹配请求的url中，全部'http://localhost:8000/api' 转接为 http://localhost:8000/
            '^/api': '',
          }
        }
      }
    }
    
  
  };
  