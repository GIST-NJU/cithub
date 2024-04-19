
<template>
    <aside class="sidenav bg-white navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-4 "
        id="sidenav-main">
        <div class="sidenav-header">
            <i class="fas fa-times p-3 cursor-pointer text-secondary opacity-5 position-absolute end-0 top-0 d-none d-xl-none"
                aria-hidden="true" id="iconSidenav"></i>
            <div class="navbar-brand m-0">
                <img src="../../assets/img/logo-ct-dark.png" class="navbar-brand-img h-100" alt="main_logo">

                <span class="ms-1 font-weight-bold text-lg">CitHub Tools</span>
            </div>
        </div>
        <hr class="horizontal dark mt-0">
        <div class="w-auto " id="sidenav-collapse-main">
            <ul class="navbar-nav">



                <!-- <li class="nav-item" @click="jumpToProjects" style=" cursor:pointer;">
                    <a :class="['nav-link', isActivePapers ? 'active' : '']" @mouseover="handleMouseOverPapers"
                        @mouseout="handleMouseOutPapers">
                        <div
                            class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <i class="ni ni-calendar-grid-58 text-warning text-lg opacity-10"></i>
                        </div>
                        <span class="nav-link-text ms-1 p">Projects</span>
                    </a>
                </li> -->
                
                <li class="nav-item" style=" cursor:pointer;">
                    <a  @click.prevent="jumpToModels" :class="['nav-link', { 'active': isRouteActive('Tools_Models') }]" 
                        >
                        <div
                            class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <i class="ni ni-credit-card text-danger text-lg opacity-10"></i>
                        </div>
                        <span class="nav-link-text ms-1 p">Models</span>
                    </a>
                </li>

                <li class="nav-item"  style=" cursor:pointer;">
                    <a  @click.prevent="jumpToTestSuite" :class="['nav-link', { 'active': isRouteActive('Benchmark_Home') }]" >
                        <div
                            class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <i class="fas fa-print text-success text-lg opacity-10"></i>
                        </div>
                        <span class="nav-link-text ms-1 p">TestSuites</span>
                    </a>
                </li>
                <!-- <li class="nav-item" @click="jumpToTestSuitesDetails" style=" cursor:pointer;">
                    <a :class="['nav-link', isActiveInstitutions ? 'active' : '']" @mouseover="handleMouseOverInstitutions"
                        @mouseout="handleMouseOutInstitutions">
                        <div
                            class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">

                            <i class="ni ni-atom  text-dark text-lg opacity-10" aria-hidden="true"></i>
                        </div>
                        <span class="nav-link-text ms-1 p">Evaluation</span>
                    </a>
                </li>
                <li class="nav-item" @click="jumpToTestSuitesDetails" style=" cursor:pointer;">
                    <a :class="['nav-link', isActiveVenue ? 'active' : '']" @mouseover="handleMouseOverVenue"
                        @mouseout="handleMouseOutVenue">
                        <div
                            class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">

                            <i class="ni ni-building text-danger text-lg opacity-10" aria-hidden="true"></i>
                        </div>
                        <span class="nav-link-text ms-1 p">Prioritisation</span>
                    </a>
                </li>

                <li class="nav-item" @click="jumpToTestSuitesDetails" style=" cursor:pointer;">
                    <a :class="['nav-link', isActiveCharts ? 'active' : '']" @mouseover="handleMouseOverCharts"
                        @mouseout="handleMouseOutCharts">
                        <div
                            class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">

                            <i class="ni ni-chart-pie-35 text-success text-lg opacity-10" aria-hidden="true"></i>
                        </div>
                        <span class="nav-link-text ms-1 p">Reduction</span>
                    </a>
                </li> -->

                <li class="nav-item"  style=" cursor:pointer;">
                    <a  @click.prevent="jumpToAlgorithmsAvailableHome" :class="['nav-link', { 'active': isRouteActive('AlgorithmsAvailableHome') }]" 
                        >
                        <div
                            class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <i class="ni ni-tv-2 text-primary text-lg opacity-10"></i>
                        </div>
                        <span class="nav-link-text ms-1 p">Algorithms Available</span>
                    </a>
                </li>

                <li class="nav-item" style=" cursor:pointer;">
                    <a   @click="jumpToHelps" :class="['nav-link', { 'active': isRouteActive('HelpCenter') }]" >
                        <div
                            class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <i class="ni ni-send text-info text-lg opacity-10"></i>
                        </div>
                        <span class="nav-link-text ms-1 p">Help</span>
                    </a>
                </li>

            </ul>
        </div>
    </aside>
</template>

<script  setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import { useCurrentModel } from '../../store/currentModel'
import { useCurrentProject } from '../../store/currentProject';
import { useCurrentTestSuitesStore } from '../../store/currentTestSuite';
import { ElNotification } from 'element-plus'
import pinia from '../../store/store'
import { useModuleStore } from '../../store/module';

const router = useRouter();
const currentModel = useCurrentModel(pinia)
const moduleStore = useModuleStore(pinia)


const isRouteActive = (routeName) => {
    return moduleStore.CurrentRoute === routeName;
}

const jumpToAlgorithmsAvailableHome = () => {
    moduleStore.CurrentModule = 'Tools'
    moduleStore.CurrentModuleDetails = 'Algorithms Available'
    moduleStore.CurrentRoute = 'AlgorithmsAvailableHome'
    router.push({ name: 'AlgorithmsAvailableHome' })
}



const jumpToModels = () => {
    moduleStore.CurrentModule = 'Tools'
    moduleStore.CurrentModuleDetails = ''
    moduleStore.CurrentRoute = 'Tools_Models'
    router.push({ name: 'Tools_Models' })


}

const jumpToTestSuite = () => {

        router.push({
            path: '/tools/TestSuitesHomeNew',
            query:
                { modelid: currentModel.currentModel.modelid }
        })



}

const jumpToHelps = () => {
    moduleStore.CurrentModule = 'Tools'
    moduleStore.CurrentModuleDetails = 'Help'
    moduleStore.CurrentRoute = 'HelpCenter'
    router.push({ name: 'HelpCenter' })
}


</script>


<style scoped></style>
