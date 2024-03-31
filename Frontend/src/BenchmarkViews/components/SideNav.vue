
<template>
    <aside class="sidenav bg-white navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-4 "
        id="sidenav-main">
        <div class="sidenav-header">
            <i class="fas fa-times p-3 cursor-pointer text-secondary opacity-5 position-absolute end-0 top-0 d-none d-xl-none"
                aria-hidden="true" id="iconSidenav"></i>
            <div class="navbar-brand m-0">
                <img src="../../assets/img/logo-ct-dark.png" class="navbar-brand-img h-100" alt="main_logo">

                <span class="ms-1 font-weight-bold text-lg">CitHub Benchmark</span>
            </div>
        </div>
        <hr class="horizontal dark mt-0">
        <div class="w-auto " id="sidenav-collapse-main">
            <ul class="navbar-nav">

                <li class="nav-item" style="cursor:pointer;">
                    <a @click.prevent="jumpToHome" :class="['nav-link', { 'active': isRouteActive('Benchmark_Home') }]">
                        <div
                            class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <i class="ni ni-tv-2 text-info text-lg opacity-10"></i>
                        </div>
                        <span class="nav-link-text ms-1 p">Benchmark Home</span>
                    </a>
                </li>

                <li class="nav-item" style="cursor:pointer;">
                    <a @click.prevent="jumpToBenchmarkModels"
                        :class="['nav-link', { 'active': isRouteActive('Benchmark_Models') }]">
                        <div
                            class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <i class="ni ni-building text-success text-lg opacity-10"></i>
                        </div>
                        <span class="nav-link-text ms-1 p">Models</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a @click.prevent="jumpToBenchmarkEvaluation"
                        :class="['nav-link', { 'active': isRouteActive('Benchmark_Evaluation') }]">
                        <div
                            class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <i class="ni ni-chart-bar-32 text-danger text-lg opacity-10"></i>
                        </div>
                        <span class="nav-link-text ms-1 p">Evaluation</span>
                    </a>
                </li>
            </ul>
        </div>
    </aside>
</template>

<script  setup>
import { useRouter } from 'vue-router';
import { ref, watch } from 'vue';
import { useModuleStore } from '../../store/module';
import pinia from '../../store/store'
import { ElLoading } from 'element-plus'
import {listAllBenchmarkModels} from '../commonFunction'


const router = useRouter();
const moduleStore = useModuleStore(pinia)
// 监听 moduleStore.CurrentRoute 的变化

watch(() => moduleStore.CurrentRoute, (newRoute, oldRoute) => {
    // 每次路由变化时触发 isRouteActive 方法
    // console.log("moduleStore.CurrentRoute sideNav", moduleStore.CurrentRoute);
    isRouteActive(newRoute);
});
const isRouteActive = (routeName) => {
    return moduleStore.CurrentRoute === routeName;
}

const jumpToHome = () => {
    moduleStore.CurrentModule = 'Benchmark'
    moduleStore.CurrentModuleDetails = ''
    moduleStore.CurrentRoute = 'Benchmark_Home'
    router.push({ name: 'Benchmark_Home' });
}

const jumpToBenchmarkModels = async () => {
    let loadingInstance = ElLoading.service({ fullscreen: true })

    await listAllBenchmarkModels()

    loadingInstance.close()
    router.push({ name: 'Benchmark_Models' });

}

const jumpToBenchmarkEvaluation = () => { }
</script>


<style scoped></style>
