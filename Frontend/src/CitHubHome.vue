<template>
  <div class="cit d-flex flex-column h-100">
    <main class="flex-shrink-0">
      <!-- Navigation-->
      <nav class="cit-navbar cit-navbar-expand-lg bg-white py-3">
        <div class="container px-5">
          <a class="cit-navbar-brand" href="https://gist.nju.edu.cn"><span class="fw-bolder text-primary">GIST</span></a>
          <div v-if="userStore.loginFlag === true" class="cit-navbar-collapse">
            <ul class="cit-navbar-nav ms-auto mb-2 mb-lg-0 small fw-bolder">
              <li class="nav-item"><a class="cit-nav-link" >Hello, {{ userStore.name }}</a></li>
              <li class="nav-item"><a class="cit-nav-link" @click="SignOut" style="cursor:pointer">Log out</a></li>
            </ul>
          </div>
        </div>
      </nav>

      <!-- Header-->
      <header class="py-5">
        <div class="cit-container px-5 pb-5">
          <div class="cit-row gx-5 align-items-center">
            <div class="col-xxl-5">
              <!-- Header text content-->
              <div class="text-center text-xxl-start">
                <h1 class="display-3 fw-bolder"><span class="text-gradient d-inline">CitHub</span></h1>
                <div class="fs-3 fw-light text-muted mb-5">An open platform for the study and use of Combinatorial Interaction Testing (CIT)</div>
                <div v-if="userStore.loginFlag === false"
                     class="d-grid gap-3 d-sm-flex justify-content-sm-center justify-content-xxl-start mb-3">
                  <a @click="jumpToUser('login')"
                     class="cit-btn cit-btn-primary cit-btn-lg px-5 py-3 me-sm-3 fs-6 fw-bolder">Log in</a>
                  <a @click="jumpToUser('register')"
                     class="cit-btn cit-btn-outline-dark cit-btn-lg px-5 py-3 fs-6 fw-bolder">Sign up</a>
                </div>
              </div>
            </div>
            <div class="col-xxl-7">
              <!-- Header profile picture-->
              <div class="d-flex justify-content-center mt-5 mt-xxl-0">
                <div class="profile bg-gradient-primary-to-secondary">
                  <img class="profile-img" src="../public/images/index.png" alt="..." />
                </div>
              </div>
            </div>
          </div>
        </div>
      </header>

      <!-- Feature Section-->
      <section class="services bg-light py-5">
        <div class="cit-container px-5 pb-3">
          <div class="cit-row gx-5 justify-content-center">
            <div class="col-12">
              <div class="text-center my-5">
                <h2 class="display-5 fw-bolder"><span class="text-gradient d-inline">Services</span></h2>
                <p class="lead fw-light mb-4">Combinatorial Interaction Testing (CIT) is a potentially powerful testing
                  technique that systematically tests the <em>combinations</em> of various kinds of factors in software
                  systems. CitHub provides collections of research papers and benchmark test models for the study of CIT,
                  as well as web based tools for performing the different testing activities of CIT.
                </p>
              </div>
            </div>

            <div class="col-sm-12 col-md-4">
              <div class="icon-box">
                <a @click="jumpToRepo" style="cursor:pointer">
                  <div class="icon"><i class="bi bi-book-half"></i></div>
                  <h4 class="">Repository</h4>
                </a>
                <p class="lead fw-light">Collection of research papers in the combinatorial interaction testing field</p>
                <div class="d-grid gap-2 col-6 mx-auto">
                  <button type="button" @click="jumpToRepo"
                          class="cit-btn cit-btn-outline-dark cit-btn-lg px-2 py-2 me-sm-3 fs-6 fw-bolder">Visit</button>
                </div>
              </div>
            </div>

            <div class="col-sm-12 col-md-4">
              <div class="icon-box">
                <a @click="jumpToTools" style="cursor:pointer">
                  <div class="icon"><i class="bi bi-wrench-adjustable-circle-fill"></i></div>
                  <h4 class="">Tools</h4>
                </a>
                <p class="lead fw-light">Automated testing tools for combinatorial interaction testing activities</p>
                <div class="d-grid gap-2 col-6 mx-auto">
                  <button type="button" @click="jumpToTools"
                          class="cit-btn cit-btn-outline-dark cit-btn-lg px-2 py-2 me-sm-3 fs-6 fw-bolder">Visit</button>
                </div>
              </div>
            </div>

            <div class="col-sm-12 col-md-4">
              <div class="icon-box">
                <a @click="jumpToBenchmark" style="cursor:pointer">
                  <div class="icon"><i class="bi bi-stack"></i></div>
                  <h4 class="">Benchmark</h4>
                </a>
                <p class="lead fw-light">Collection of test models for evaluating combinatorial interaction testing algorithms</p>
                <div class="d-grid gap-2 col-6 mx-auto">
                  <button type="button" @click="jumpToBenchmark"
                          class="cit-btn cit-btn-outline-dark cit-btn-lg px-2 py-2 me-sm-3 fs-6 fw-bolder">Visit</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>

    <!-- Footer-->
    <footer class="bg-white py-4 mt-auto">
      <div class="container px-5">
        <div class="row align-items-center justify-content-between flex-column flex-sm-row">
          <div class="col-auto"><div class="small m-0">Copyright &copy; GIST 2024</div></div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { useModuleStore } from './store/module';
import { useRouter, useRoute } from 'vue-router';
import { onMounted, ref } from 'vue';
import pinia from './store/store'
import { useUserStore } from './store/userStore';
import {CheckLogin} from './request'
const userStore = useUserStore(pinia)
const router = useRouter()
const route = useRoute()
const moduleStore = useModuleStore(pinia)

const jumpToRepo = () => {
  moduleStore.CurrentSubSystem='Repository'
  moduleStore.CurrentSubSystemRoute='Repository_Home'
  moduleStore.CurrentModule = 'Home'
  moduleStore.CurrentModuleDetails = ''
  moduleStore.CurrentRoute = 'Repository_Home'
  router.push({
    name: 'Repository_Home'
  })
}
const jumpToTools = () => {
  moduleStore.CurrentSubSystem='Tools'
  moduleStore.CurrentSubSystemRoute='Tools_Models'
  moduleStore.CurrentModule = 'Home'
  moduleStore.CurrentModuleDetails = ''
  moduleStore.CurrentRoute = 'Tools_Models'
  router.push({
   name:'Tools_Models'
  })
}
const jumpToBenchmark = () => {
  moduleStore.CurrentSubSystem='Benchmark'
  moduleStore.CurrentSubSystemRoute='Benchmark_Home'
  moduleStore.CurrentModule = 'Home'
  moduleStore.CurrentModuleDetails = ''
  moduleStore.CurrentRoute = 'Benchmark_Home'
  router.push({
    path: '/benchmark/home'
  })
}

const jumpToUser = (value) => {
  // console.log(value)
  if (value === 'login') {
    router.push({
      name: 'UserLogin'
    })
  }
  if (value === 'register') {
    router.push({
      name: 'UserRegister'
    })
  }
}

const SignOut = () => {
  localStorage.setItem("userToken", '')
  userStore.userID='',
  userStore.userToken= '',
  userStore.usertype='',
  userStore.name='',
  userStore.email='',
  userStore.institution='',
  userStore.loginFlag=false,
  userStore.account= '',
  router.push({
    path: '/'
  })
}

onMounted(async () => {
  await CheckLogin()
})
</script>



<style src="../public/cithub.home.min.css" scoped>

</style>