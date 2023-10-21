# 前端

## 前端技术栈

Vue3

Vue-router（用于路由导航）

Vuex（用于状态管理）

axios （用于发送请求，进行前后端交互）

## 前端运行方法

1. 打开CMD切换到Frontend目录下
2. npm install
3. npm run serve
4. 浏览器访问 localhost:8090

## 前端项目结构

在src内写代码，src文件夹内的目录结构与功能如下：

```
src
├─assets（存放静态文件）
│  ├─css
│  ├─fonts
│  ├─img
│  │  ├─icons
│  │  │  └─flags
│  │  ├─illustrations
│  │  ├─logos
│  │  ├─shapes
│  │  └─small-logos
│  ├─js
│  └─scss
│      └─argon-dashboard
│          ├─badges
│          ├─bootstrap
│          │  ├─forms
│          │  ├─helpers
│          │  ├─mixins
│          │  ├─utilities
│          │  └─vendor
│          ├─cards
│          ├─custom
│          ├─forms
│          ├─mixins
│          ├─plugins
│          │  ├─free
│          │  └─pro
│          └─variables
├─components（Vue Argon的组件，可直接使用，文档参见）
├─examples （使用Vue Argon组件的例子，用于参考）
├─router
   └─index.js（定义项目的路由）
├─store（定义Vuex的状态仓库）
└─views（在此写自己的代码：在components文件夹中写组件代码，在components文件夹外写视图代码）
    ├─components
    └─HelloWorld.vue
├─request.js（用于发送axios请求，跨域问题已配置好，直接使用即可）
└─vue.config.js（用于配置vue-cli脚手架）
```

其中，Vue Argon的文档参见[Overview | Vue Argon Dashboard @ Creative Tim (creative-tim.com)](https://www.creative-tim.com/learning-lab/vue/overview/argon-dashboard/)

# 后端

## 后端技术栈

fastapi

sqlite

## 后端运行方法

1. 打开main.py，然后在终端分别执行2~4
2. pip install fastapi
3. pip install uvicorn 
4. python -m uvicorn main:app --reload

## 后端项目结构

```
Backend
├─db.py（数据库操作类）
├─main.py（内有fastapi接口）
└─test.db（数据库文件）
```

# 前后端交互逻辑

1. 前端axios发出请求给代理服务器
2. 代理服务器（vue.config.js内配置）收到请求后，将请求重写，然后转发给后端服务器
3. 后端服务器处理请求，并返回结果
4. 前端收到结果后展示到视图上
