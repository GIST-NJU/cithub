
<template>
  <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl " id="navbarBlur"
    data-scroll="false">
    <div class="container-fluid py-1 px-3">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
          <router-link :to="{ name: 'Cithub' }" class="breadcrumb-item text-sm text-white" aria-current="page">
            CitHub
          </router-link>

          <router-link :to="{ name: moduleStore.CurrentRoute }" @click="handleRouterLinkClick" :exact="true"
            class="breadcrumb-item text-sm text-white" aria-current="page">
            {{ moduleStore.CurrentModule }}
          </router-link>

  


          <li v-if="moduleStore.CurrentModuleDetails != ''" class="breadcrumb-item text-sm text-white active"
            aria-current="page">{{ moduleStore.CurrentModuleDetails }}</li>
        </ol>
      </nav>


      <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
        <div class="ms-md-auto pe-md-3 d-flex align-items-center">
        </div>
        <ul class="navbar-nav justify-content-end">
          <li class="nav-item d-flex align-items-center">
            <a href="javascript:;" class="nav-link text-white font-weight-bold px-0">
              <i class="fa fa-user me-sm-1"></i>
              <span class="d-sm-inline d-none">{{ userStore.name }} </span>
            </a>
          </li>
          <li class="nav-item d-flex align-items-center">
            <a href="javascript:;" class="nav-link text-white font-weight-bold px-0">
              <span @click="SignOut" class="d-sm-inline d-none"> / Sign Out</span>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useModuleStore } from '../store/module';
import { useUserStore } from '../store/userStore';
import pinia from '../store/store';
const moduleStore = useModuleStore(pinia)
const userStore = useUserStore(pinia)

const isHovered = ref(false);
const router = useRouter();
const route = useRoute();


const jumpToCithub = () => {
  router.push({ name: 'Cithub' });
}
const SignOut = () => {
  localStorage.setItem("userToken", '')
  router.push({
    path: '/'
  })
}
const handleRouterLinkClick = () => {
  moduleStore.CurrentModuleDetails = ''
  // console.log("当前module是",moduleStore.CurrentModule)
}



onMounted(() => {
  console.log("route.query.module", route.query.module)
  if (typeof route.query.module === 'undefined') {
    if (moduleStore.CurrentModule == 'Repository') { moduleStore.CurrentRoute = 'Repository_Home' }
    if (moduleStore.CurrentModule == 'Complete Paper List') { moduleStore.CurrentRoute = 'Repository_Papers' }
    if (moduleStore.CurrentModule == 'Scholars') { moduleStore.CurrentRoute = 'Repository_Scholars' }
    if (moduleStore.CurrentModule == 'Fields') { moduleStore.CurrentRoute = 'Repository_Fields' }
    if (moduleStore.CurrentModule == 'Venues') { moduleStore.CurrentRoute = 'Repository_Venues' }
    if (moduleStore.CurrentModule == 'Statistics') { moduleStore.CurrentRoute = 'Repository_Statistics' }
  }

  else {
    // console.log("route.query.module有值！", route.query.module);
    if (route.query.module == 'Search'|| route.query.module == 'Repository') { 
      moduleStore.CurrentRoute = 'Repository_Home' ;
    }

    if (route.query.module == 'Scholars' || route.query.module == 'Institutions' || route.query.module == 'Country') {
      moduleStore.CurrentRoute = 'Repository_Scholars';
    }
    if (route.query.module == 'Fields' || route.query.module == 'Topics') {
      moduleStore.CurrentRoute = 'Repository_Fields';
    }
    if (route.query.module == 'Venues') {
      moduleStore.CurrentRoute = 'Repository_Venues';
    }
  }

  console.log("得到的moduleStore.CurrentRoute是", moduleStore.CurrentRoute)

})
</script>


<style scoped>


</style>

