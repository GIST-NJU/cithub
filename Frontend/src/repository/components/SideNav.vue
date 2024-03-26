
<template>
  <aside class="sidenav bg-white navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-4 "
    id="sidenav-main">
    <div class="sidenav-header">
      <i class="fas fa-times p-3 cursor-pointer text-secondary opacity-5 position-absolute end-0 top-0 d-none d-xl-none"
        aria-hidden="true" id="iconSidenav"></i>
      <div class="navbar-brand m-0">
        <span class="ms-1 font-weight-bold text-lg">CitHub Repositoy</span>
      </div>
    </div>
    <hr class="horizontal dark mt-0">
    <div class="collapse navbar-collapse w-auto " id="sidenav-collapse-main">
      <ul class="navbar-nav">
        <li class="nav-item" style="cursor:pointer;">
          <a @click.prevent="jumpToHome" :class="['nav-link', { 'active': isRouteActive('Repository_Home') }]">
          <!-- <a href="/repository/home" :class="['nav-link', { 'active': isRouteActive('Repository_Home') }]"> -->
            <div
              class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-tv-2 text-primary text-lg opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1 p">Home</span>
          </a>
        </li>

        <li class="nav-item" style="cursor:pointer;">
          <a @click.prevent="jumpToPapers" :class="['nav-link', { 'active': isRouteActive('Repository_Papers') }]">
            <div
              class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-calendar-grid-58 text-success text-lg opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1 p">Papers</span>
          </a>
        </li>

        <li class="nav-item"  style=" cursor:pointer;">
          <a @click.prevent="jumpToScholars" :class="['nav-link', { 'active': isRouteActive('Repository_Scholars') }]">
            <div
              class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-bag-17 text-warning text-lg opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1 p">Scholars</span>
          </a>
        </li>

        <li class="nav-item"  style=" cursor:pointer;">
          <a @click.prevent="jumpToFields" :class="['nav-link', { 'active': isRouteActive('Repository_Fields') }]">
            <div
              class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-credit-card text-danger text-lg opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1 p">Topics</span>
          </a>
        </li>
        <li class="nav-item"  style=" cursor:pointer;">
          <a @click.prevent="jumpToVenues" :class="['nav-link', { 'active': isRouteActive('Repository_Venues') }]">
            <div
              class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">

              <i class="ni ni-building text-info text-lg opacity-10" aria-hidden="true"></i>
            </div>
            <span class="nav-link-text ms-1 p">Venues</span>
          </a>
        </li>

        <li class="nav-item"  style=" cursor:pointer;">
          <a  @click.prevent="jumpToStatistics" :class="['nav-link', { 'active': isRouteActive('Repository_Statistics') }]">
            <div
              class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">

              <i class="ni ni-chart-pie-35 text-success text-lg opacity-10" aria-hidden="true"></i>
            </div>
            <span class="nav-link-text ms-1 p">Statistics</span>
          </a>
        </li>

        <!-- <li class="nav-item">
          <a class="nav-link " href="#">
            <div
              class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-diamond text-primary text-lg opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1 p">Ranks</span>
          </a>
        </li> -->

   
      </ul>
    </div>
  </aside>
</template>

<script  setup>
import { useRouter } from 'vue-router';
import { ref,reactive } from 'vue';
import pinia from '../../store/store'
import { listAllPapers, listAllScholars, listAllInstitutions, listallVenue } from '../commonFunction';
import { useModuleStore } from '../../store/module';
import { usePaperInfoStore } from '../../store/paperinfoStore'

const moduleStore = useModuleStore(pinia)
const PaperInfoStore = usePaperInfoStore(pinia)
const router = useRouter();



const isRouteActive = (routeName) => {
  return router.currentRoute.value.name === routeName;
}

const jumpToHome = () => {
  moduleStore.CurrentModule='Repository'
  moduleStore.CurrentModuleDetails=''
  router.push({ name: 'Repository_Home' });
}
//分页查询的传递的对象 
let paginationObj = reactive({
  pagesize: 20,
  total: PaperInfoStore.total,
  pagecount: 8,
  pagenum: 1,
  searchkeywords: "",
  typerofPapers: "Combinatorial Testing",

})
const jumpToPapers = () => {
  // 每次点击进入Papers都显示所有论文的信息。
  moduleStore.CurrentModule='Complete Paper List'
  moduleStore.CurrentModuleDetails=''
  listAllPapers(paginationObj)
  router.push({ path: '/repository/papers' });
}

const jumpToScholars = () => {
  moduleStore.CurrentModule='Scholars'
  moduleStore.CurrentModuleDetails=''
  router.push({ name: 'Repository_Scholars' })
}

const jumpToFields = () => {
  moduleStore.CurrentModule='Fields'
  moduleStore.CurrentModuleDetails=''

  router.push({ name: 'Repository_Fields' })
}

const jumpToVenues = () => {
  moduleStore.CurrentModule='Venues'

  moduleStore.CurrentModuleDetails=''

  router.push({ name: 'Repository_Venues' })
}

const jumpToStatistics = () => {
  moduleStore.CurrentModule='Statistics'
  moduleStore.CurrentModuleDetails=''
  router.push({ name: 'Repository_Statistics' })
}


</script>


<style scoped></style>
