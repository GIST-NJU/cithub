import axios from 'axios'
export function request(config) {
  // 1.创建axios的实例
  const instance = axios.create({
    baseURL: "http://localhost:8090/api",
    // baseURL自动拼接到url前面
    // 注意！axios发送请求给的是代理服务器！！
    // 代理服务器
    // -  在本地开发时由Vite自动配置，ip地址 端口号和主机一样 
    // -  部署到服务器时，代理服务器是Nginx，在Nginx的配置文件里写转发的语法
    // 所以，在本地开发时 axios发送请求给代理服务器的Baseurl是 baseURL: "http://localhost:8892/api",
    // 部署到服务器时，axios发送请求给代理服务器的baseurl就是baseURL: "http://210.28.135.32:9020/api",

    // 服务器url
    // baseURL: "http://210.28.135.32/api"

    // baseURL: "http://210.28.135.32:9020/api",

  //本地url
    // `timeout` 指定请求超时的毫秒数。
    // 如果请求时间超过 `timeout` 的值，则请求会被中断
    // 默认值是 `0` (永不超时)
    // timeout: 5000,

  })
  // 2.1.请求拦截的作用
  instance.interceptors.request.use(config => {
    return config
  }, err => {
    console.log(err);
  })
  // 2.2.响应拦截
  instance.interceptors.response.use(res => {
    // console.log("响应拦截器res是",res)
    return res.data
  }, err => {
    console.log(err);
  })
  // 3.发送真正的网络请求
  return instance(config)
}