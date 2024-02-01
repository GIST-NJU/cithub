import { createRouter, createWebHistory } from "vue-router";
// import Dashboard from "../views/Dashboard.vue";
// import Tables from "../views/Tables.vue";
// import Billing from "../views/Billing.vue";
// import VirtualReality from "../views/VirtualReality.vue";
// import RTL from "../views/Rtl.vue";
// import Profile from "../views/Profile.vue";
// import Signup from "../views/Signup.vue";
// import Signin from "../views/Signin.vue";

import Cithub from '../CithubHomeView.vue'

// Repo
import RepoRouterView from '../RepoViews/RepoRouterView.vue'
import PaperHome from '../RepoViews/PaperHome.vue'
import RepoHome from '../RepoViews/RepoHome.vue'
import InstitutionsHome from '../RepoViews/InstitutionsHome.vue'
import Scholars from '../RepoViews/ScholarsHome.vue'
import Category from '../RepoViews/CategoryHome.vue'
import Venue from '../RepoViews/VenueHome.vue'
import Charts from '../RepoViews/ChartsHome.vue'
import ChartsDisplay from '../RepoViews/ChartsDisplay.vue'



// Tools
import ToolsRouterView from '../ToolsViews/ToolsRouterView.vue'
import ToolsHome from '../ToolsViews/ToolsHome.vue'
import ModelsHome from '../ToolsViews/ModelsHome.vue'
import ModelsDetails from '../ToolsViews/ModelsDetails.vue'
import ModelsGeneration from '../ToolsViews/ModelsGeneration.vue'

// Benchmark
import BenchmarkRouterView from '../BenchmarkViews/BenchmarkRouterView.vue'
import BenchmarkHome from '../BenchmarkViews/BenchmarkHome.vue'

// User
import UserRouterView from '../UserViews/UserRouterView.vue'
import UserHome from '../UserViews/UserHome.vue'
import UserLogin from '../UserViews/UserLogin.vue'
import UserRegister from '../UserViews/UserRegister.vue'


const routes = [
  {
    path: "/",
    name: "Cithub",
    component: Cithub,
    meta: { title: 'Cithub' }
  },

  // Repo
  {
    path: '/repo',
    component: RepoRouterView,
    meta: { title: 'Repository' },
    children: [
      {
        // RepoHome
        path: "home",
        name: 'RepoHome',
        component: RepoHome,
        meta: { title: 'Repository' }
      },
      {
        path: "Papers",
        name: "Papers",
        component: PaperHome,
        meta: { title: 'Papers' }
      },
      {
        path: "Institutions",
        name: "Institutions",
        component: InstitutionsHome,
        meta: { title: 'Institutions' }
      },
      {
        path: "Scholars",
        name: "Scholars",
        component: Scholars,
        meta: { title: 'Scholars' }
      },
      {
        path: "Category",
        name: "Category",
        component: Category,
        meta: { title: 'Category' }
      },
      {
        path: "Venue",
        name: "Venue",
        component: Venue,
        meta: { title: 'Venue' }
      },
      {
        path: "Charts",
        name: "Charts",
        component: Charts,
        meta: { title: 'Charts' }
      },
      {
        path: "ChartsDisplay",
        name: "ChartsDisplay",
        component: ChartsDisplay,
        meta: { title: 'ChartsDisplay' }
      },
    ]
  },

  // Tools
  {
    path: '/tools',
    component: ToolsRouterView,
    meta: { title: 'Tools' },
    children: [
      {
        // ToolsHome
        path: "home",
        name: 'algorithmHome',
        component: ToolsHome,
        meta: { title: 'Tools' }
      },
      {
        // modelsHome
        path: "models",
        name: 'modelsHome',
        component: ModelsHome,
        meta: { title: 'Models' }
      },
      {
        // modelsDetails
        path: "modelsDetails",
        name: 'modelsDetails',
        component: ModelsDetails,
        meta: { title: 'ModelsDetails' }
      },
      {
        // modelsGeneration
        path: "modelsGeneration",
        name: 'modelsGeneration',
        component: ModelsGeneration,
        meta: { title: 'modelsGeneration' }
      },


    ]
  },
  // Benchmark
  {
    path: '/benchmark',
    component: BenchmarkRouterView,
    meta: { title: 'Benchmark' },
    children: [
      {
        // BenchmarkHome
        path: "home",
        name: 'benchmarkHome',
        component: BenchmarkHome,
        meta: { title: 'Benchmark' }
      },

    ]
  },


  // User
  {
    path: '/user',
    component: UserRouterView,
    meta: { title: 'User' },
    children: [
      {
        path: "login",
        name: 'UserLogin',
        component: UserLogin,
        meta: { title: 'UserLogin' }
      },
      {
        path: "register",
        name: 'UserRegister',
        component: UserRegister,
        meta: { title: 'UserRegister' }
      },

    ]
  },



];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  linkActiveClass: "active",
});

router.beforeEach((to, from, next) => {
  window.document.title = to.meta.title
  next()
})

export default router;
