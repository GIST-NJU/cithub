import { createRouter, createWebHistory, createWebHashHistory } from "vue-router";
import CitHubHome from '../CitHubHome.vue'

// Repository
import Repository_Router from '../repository/RepositoryRouter.vue'
import Repository_Home from '../repository/Home.vue'
import Repository_Papers from '../repository/Papers.vue'
import Repository_Scholars from '../repository/Scholars.vue'
import Repository_Fields from '../repository/Fields.vue'
import Repository_Venues from '../repository/Venues.vue'
import Repository_Statistics from '../repository/Statistics.vue'
import Repository_PaperInfo from '../repository/components/PaperInformation.vue'

// Tools
import ToolsRouterView from '../ToolsViews/ToolsRouterView.vue'
import ProjectsHome from '../ToolsViews/ProjectsHome.vue'
import AlgorithmsAvailableHome from '../ToolsViews/AlgorithmsAvailableHome.vue'
import HelpCenter from '../ToolsViews/HelpCenter.vue'
import ModelsHome from '../ToolsViews/ModelsHome.vue'
import ModelsDetails from '../ToolsViews/ModelsDetails.vue'
import LLMModelDetails from '../ToolsViews/LLMModelDetails.vue'
import TestSuitesHome from '../ToolsViews/TestSuitesHome.vue'
import TestSuitesHomeNew from '../ToolsViews/TestSuiteHomeNew.vue'
import TestSuiteDetails from '../ToolsViews/TestSuiteDetails.vue'

// Benchmark
import BenchmarkRouterView from '../BenchmarkViews/BenchmarkRouterView.vue'
import BenchmarkHome from '../BenchmarkViews/BenchmarkHome.vue'
import BenchmarkEvaluation from '../BenchmarkViews/BenchmarkEvaluation.vue'
import BenchmarkModels from '../BenchmarkViews/BenchmarkModels.vue'
import BenchmarkModelInfo from '../BenchmarkViews/components/ModelInfomation.vue'

// User
import UserRouterView from '../UserViews/UserRouterView.vue'
import UserLogin from '../UserViews/Login.vue'
import UserRegister from '../UserViews/Signup.vue'


const routes = [
  {
    path: "/",
    name: "CitHub Home",
    component: CitHubHome,
    meta: { title: 'CitHub Home' }
  },
  // Repository
  {
    path: '/repository',
    component: Repository_Router,
    meta: { title: 'Router' },
    children: [
      {
        path: "home",
        name: 'Repository_Home',
        component: Repository_Home,
        meta: { title: 'Home' }
      },
      {
        path: "papers",
        name: "Repository_Papers",
        component: Repository_Papers,
        meta: { title: 'Papers' },
      },
      {
        path: "paperInfo",
        name: "Repository_PaperInfo",
        component: Repository_PaperInfo,
        meta: { title: 'PaperInfo' },
      },
      {
        path: "scholars",
        name: "Repository_Scholars",
        component: Repository_Scholars,
        meta: { title: 'Scholars' }
      },
      {
        path: "fields",
        name: "Repository_Fields",
        component: Repository_Fields,
        meta: { title: 'Fields' }
      },
      {
        path: "venues",
        name: "Repository_Venues",
        component: Repository_Venues,
        meta: { title: 'Venue' }
      },
      {
        path: "statistics",
        name: "Repository_Statistics",
        component: Repository_Statistics,
        meta: { title: 'Statistics' }
      }
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
        path: "AlgorithmsAvailableHome",
        name: 'AlgorithmsAvailableHome',
        component: AlgorithmsAvailableHome,
        meta: { title: 'AlgorithmsAvailableHome' }
      },
      {
        // HelpCenter
        path: "HelpCenter",
        name: 'HelpCenter',
        component: HelpCenter,
        meta: { title: 'HelpCenter' }
      },
      {
        // ProjectsHome
        path: "ProjectsHome",
        name: 'ProjectsHome',
        component: ProjectsHome,
        meta: { title: 'ProjectsHome' }
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
        // LLMModelDetails
        path: "LLMModelDetails",
        name: 'LLMModelDetails',
        component: LLMModelDetails,
        meta: { title: 'LLMModelDetails' }
      },
      {
        // TestSuitesHome
        path: "TestSuitesHome",
        name: 'TestSuitesHome',
        component: TestSuitesHome,
        meta: { title: 'TestSuitesHome' }
      },
      {
        // TestSuitesHome
        path: "TestSuitesHomeNew",
        name: 'TestSuitesHomeNew',
        component: TestSuitesHomeNew,
        meta: { title: 'TestSuitesHomeNew' }
      },
      {
        // TestSuiteDetails
        path: "TestSuiteDetails",
        name: 'TestSuiteDetails',
        component: TestSuiteDetails,
        meta: { title: 'TestSuiteDetails' }
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
        name: 'Benchmark_Home',
        component: BenchmarkHome,
        meta: { title: 'Benchmark Home' }
      },
      {
        // BenchmarkModels
        path: "models",
        name: 'Benchmark_Models',
        component: BenchmarkModels,
        meta: { title: 'Benchmark Models' }
      },
      // Benchmark Model Details
      {
        path: "modelinfo",
        name: "Benchmark_ModelInfo",
        component: BenchmarkModelInfo,
        meta: { title: 'ModelInfo' },
      },
      // Benchmark Evaluation
      {
        path: "evaluation",
        name: "Benchmark_Evaluation",
        component: BenchmarkEvaluation,
        meta: { title: 'Evaluation' },
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
  // history: createWebHistory(process.env.BASE_URL),
  history: createWebHashHistory(),
  // history: createWebHistory(), //去掉路径中的 # 号，不行，打包之后会白屏
  routes,
  linkActiveClass: "active",
});

router.beforeEach((to, from, next) => {
  window.document.title = to.meta.title
  next()
})

export default router;