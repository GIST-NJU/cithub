nginx安装及其配置详细教程 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/83890573)

服务器nginx安装路径：

/usr/local/nginx



# 问题解决

1. 所有前端项目，打包后的dist文件夹的所有东西直接放在服务器上，具体目录是/usr/share/nginx目录下，不要在放到docker容器里了，多此一举。
   **前端项目，只是静态页面，直接放在服务器里就行了，不需要放到docker容器里，多此一举！**

2. 所有前端项目，vue-router的history要改成hash模式，会在url里多一个#，但必须这样才能解决。

3. vite.config.js的defineConfig里加上` base: "./",`

4. 前端与后端交互问题，以区块链课程主页为例子：

   区块链前端直接放在 /usr/share/nginx/BlockChain目录下

   1. 区块链前端的axios的baseurl是：`baseURL: "http://210.28.135.32/BlockChainAPI",`

   2. 在服务器的nginx.conf配置文件(/usr/local/nginx/conf)里加上
      ```
              location /BlockChainAPI {
              proxy_pass http://210.28.135.32:9983;
          }
      ```

      等于说，区块链前端的axios发送请求给服务器的nginx，服务器的nginx匹配到之后，作为代理转发给后端`http://210.28.135.32:9983`。

      还是一样的流程：前端axios发送请求给代理服务器->代理服务器转发请求给后端->后端处理请求

      区块链前端axios请求：`http://210.28.135.32/BlockChainAPI`，给服务器的nginx作为代理服务器，nginx代理服务器转发请求给后端http://210.28.135.32:9983。

      

5. nginx的错误日志在/var/log/nginx目录



注意！前端项目不需要再放到docker容器里，所以所有前端项目的dockerfile都不需要了

但是后端项目还是需要放到docker容器里，所以后端项目的dockerfile还需要

总项目的docker-compose.yml文件里的前端项目不需要了，记得删除。





