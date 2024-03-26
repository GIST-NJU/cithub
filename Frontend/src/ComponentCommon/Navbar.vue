
<template>
  <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl " id="navbarBlur"
    data-scroll="false">
    <div class="container-fluid py-1 px-3">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
          <router-link :to="{ name: 'Cithub' }" class="breadcrumb-item text-sm text-white" aria-current="page">
            CitHub
          </router-link>
          <!-- <li class="breadcrumb-item text-sm" @click="jumpToCithub" @mouseover="isHovered = true"
            @mouseleave="isHovered = false" style="cursor:pointer;" id="liCithub">
            <span v-show="isHovered" class="opacity-5 text-white hovered">CitHub</span>
            <span v-show="!isHovered" class="opacity-5 text-white">CitHub</span>
          </li> -->
          <router-link :to="{ name: moduleLink }" @click="handleRouterLinkClick" :exact="true"
            class="breadcrumb-item text-sm text-white" aria-current="page">
            {{ moduleStore.CurrentModule }}
          </router-link>

          <!-- <router-link :to="{name:moduleLink}" :exact="true" class="breadcrumb-item text-sm text-white" aria-current="page">
            {{ moduleStore.CurrentModule }}
          </router-link> -->


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

const moduleLink = ref('');

const jumpToCithub = () => {
  // 这里要增加登录状态的判断
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
    if (moduleStore.CurrentModule == 'Repository') { moduleLink.value = 'Repository_Home' }
    if (moduleStore.CurrentModule == 'Complete Paper List') { moduleLink.value = 'Repository_Papers' }
    if (moduleStore.CurrentModule == 'Scholars') { moduleLink.value = 'Repository_Scholars' }
    if (moduleStore.CurrentModule == 'Fields') { moduleLink.value = 'Repository_Fields' }
    if (moduleStore.CurrentModule == 'Venues') { moduleLink.value = 'Repository_Venues' }
    if (moduleStore.CurrentModule == 'Statistics') { moduleLink.value = 'Repository_Statistics' }
  }

  else {
    let newValue = route.query.module
    if (newValue == 'Scholars' || 'Institutions' || 'Country') { moduleLink.value = 'Repository_Scholars' }
    if (newValue == 'Fields' ) { moduleLink.value = 'Repository_Fields' }
    if (newValue == 'Venues') { moduleLink.value = 'Repository_Venues' }
  }


  // console.log("moduleLink.value", moduleLink.value)

})
</script>


<style scoped>
#liCithub span {
  transition: color 0.3s, opacity 0.3s, text-decoration 0.3s;
}

#liCithub span.hovered:hover {
  text-decoration: underline;
  color: white;
  opacity: 1;
}
</style>

