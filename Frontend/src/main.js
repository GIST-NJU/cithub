import { createApp } from "vue";
import App from "./App.vue";
import store from "./store";
import { createPinia } from 'pinia'
import router from "./router";


import "./assets/css/nucleo-icons.css";
import "./assets/css/nucleo-svg.css";

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import ArgonDashboard from "./argon-dashboard";
import bootstrap from 'bootstrap'
import "bootstrap-icons/font/bootstrap-icons.css";



const pinia = createPinia()
const appInstance = createApp(App);
appInstance.use(ElementPlus)
appInstance.use(store);
appInstance.use(pinia)
appInstance.use(router);
appInstance.use(ArgonDashboard);
appInstance.mount("#app");
