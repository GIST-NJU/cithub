import axios from 'axios'
import router from './router/index'
import { ElNotification } from 'element-plus'
import pinia from './store/store'
import { useUserStore } from './store/userStore'


const userStore = useUserStore(pinia)


// 获取用户的登录状态，重新创建一个axios实例来调用，避免形成循环调用！
// 这个axios实例也用在User模块
export function requestAuth(config) {
  // 1.创建axios的实例
  const instance = axios.create({
    baseURL: process.env.VUE_APP_BASE_URL,
    // baseURL: "http://localhost:8090/api",
    // baseURL: "http://210.28.135.32/CitHubAPI",
  })
  // 2.1.请求拦截的作用
  instance.interceptors.request.use(config => {
    return config
  }, err => {
    // console.log(err);
  })
  // 2.2.响应拦截
  instance.interceptors.response.use(res => {
    // console.log("响应拦截器res是",res)
    return res.data
  }, err => {
    // console.log(err);
  })
  // 3.发送真正的网络请求
  return instance(config)
}

// 检查用户登录状态
export const CheckLoginStatus = async () => {
  let tokenForVerify = localStorage.getItem("userToken");

  if (tokenForVerify) {
    try {
      const AuthRES = await requestAuth({
        url: 'user/users/VerifyToken',
        method: 'GET',
        params: {
          tokenForVerify
        }
      });

      // console.log("AuthRES", AuthRES)
      if (AuthRES.verifstatus === 'success') {
        // console.log("验证token成功");
        return { "loginStatus": true, "token": tokenForVerify };
      } else {
        // console.log("该token无效，返回false");
        return { "loginStatus": false, "token": tokenForVerify }
      }
    } catch (error) {
      console.error("请求出错", error);
      // 处理请求错误
      return { "loginStatus": false, "token": tokenForVerify }
    }
  } else {

    // console.log("该token无效，返回false");
    return { "loginStatus": false, "token": tokenForVerify }
  }
};

// 通过token 获得user信息
export const getUserInfoByToken = async (token) => {
  try {
    const res = await requestAuth({
      url: "user/users/getUserInfoByToken",
      method: 'GET',
      params: {
        userToken: token
      }
    });

    // 在这里处理获取到的用户信息
    return {
      "account": res.account,
      "userToken": res.userToken,
      "usertype": res.usertype,
      "name": res.name,
      "userid": res.userid,
      "email": res.email,
      "institution": res.institution
    }


  } catch (error) {
    console.error("获取用户信息错误", error);
    throw error; // 抛出错误，以便在调用方进行处理
  }
};

// 封装用户登录判断逻辑的函数
export const CheckLogin = async () => {
  // 逻辑：
  // 使用 userToken 检查用户是否处于登录状态，由服务器检查。
  // 若处于登录状态则加载用户信息！
  // 若未登录则直接push到登录界面

  let LoginStatusObj = await CheckLoginStatus()
  userStore.loginFlag = LoginStatusObj.loginStatus
  if (userStore.loginFlag) {
    // console.log("已登录！加载用户信息！")
    let userobj = await getUserInfoByToken(LoginStatusObj.token)
    userStore.userID = userobj.userid
    userStore.account = userobj.account
    userStore.userToken = userobj.userToken
    userStore.usertype = userobj.usertype
    userStore.name = userobj.name
    userStore.email = userobj.email
    userStore.institution = userobj.institution
    userStore.loginFlag = true
    // console.log("userStore",userStore)
  }
  // console.log("router.currentRoute.value.name ",router.currentRoute.value.name )
  // 在这里添加条件判断，如果要跳转的页面是 'CitHub_Home' 则执行跳转
  let PagesArray=['CitHub_Home','TestSuites_Home','Tools_Models','Repository_Home','Benchmark_Home']
  if (PagesArray.includes(router.currentRoute.value.name)) {
    router.push({
      name: router.currentRoute.value.name
    })
  }
  else {
    console.log("未登录！")
    router.push({
      name: 'UserLogin'
    })
  }
}


export function request(config) {
  // 1.创建axios的实例
  const instance = axios.create({
    baseURL: process.env.VUE_APP_BASE_URL,
    // baseURL: "http://localhost:8090/api",
    // baseURL: "http://210.28.135.32/CitHubAPI",

  })

  // 2.1.请求拦截的作用
  instance.interceptors.request.use(async config => {
    // 获取用户登录状态,CheckLoginStatus通过另一个axios实例发送请求，避免了循环调用
    let isLoggedIn = await CheckLoginStatus();
    // console.log("请求拦截器",isLoggedIn)
    // 如果用户已登录，则正常发送请求
    if (isLoggedIn.loginStatus) {
      let userobj = await getUserInfoByToken(isLoggedIn.token)
      // console.log("用户已登录！获取用户信息！",userobj)
      userStore.userID = userobj.userid
      userStore.account = userobj.account
      userStore.userToken = userobj.userToken
      userStore.usertype = userobj.usertype
      userStore.name = userobj.name
      userStore.email = userobj.email
      userStore.institution = userobj.institution
      return config;
    } else {
      // 若用户未登录则跳转到登录页面
      // console.log("用户未登录！跳转到登录界面！")
      ElNotification({
        title: 'Please Sign in',
        message: 'please Sign in for using cithub',
        type: 'warning',
        position: 'top-left',
      })
      router.push(
        {
          name: 'UserLogin'
        }
      )
    }
  }, err => {
    console.log(err);
  });

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