import { createRouter, createWebHistory } from "vue-router";
// import Dashboard from "../views/Dashboard.vue";
// import Tables from "../views/Tables.vue";
// import Billing from "../views/Billing.vue";
// import VirtualReality from "../views/VirtualReality.vue";
// import RTL from "../views/Rtl.vue";
// import Profile from "../views/Profile.vue";
// import Signup from "../views/Signup.vue";
// import Signin from "../views/Signin.vue";

import HelloWorld from '../views/HelloWorld.vue'

const routes = [
  {
    path: "/",
    name: "/",
    component: HelloWorld,
    
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  linkActiveClass: "active",
});

export default router;
