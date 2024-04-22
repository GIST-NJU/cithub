<template>
    <!-- route.query.projectid :{{ route.query.projectid }} -->
    <div class="min-height-300 bg-success position-absolute w-100"></div>
    <SideNav></SideNav>
    <main class="main-content position-relative border-radius-lg ">
        <!-- Navbar -->
        <Navbar></Navbar>
        <!-- End Navbar -->

        <div class="container-fluid py-4">
            <div class="row">
                <div class="col-12">

                    <div class="card mb-4">
                        <div class="card-header pb-0">
                            <h4>Complete TestSuites List</h4>
                            <h6>Total {{ testSuitesStore.TotalTestSuitesNum }} TestSuites Found </h6>
                        </div>
                        <div v-for="(obj, index) in testSuitesStore.testSuitesList" class="card-body ">
                            <TestSuitesTable :testSuites="obj['testSuites']" :model="obj['model']" :index="index"></TestSuitesTable>
                        </div>


                    </div>
                </div>
            </div>

            <Foot></Foot>
        </div>
    </main>
</template>

<script  setup>
import { useRoute, useRouter } from 'vue-router';
import { onMounted, reactive, ref, computed } from 'vue';
import { request, CheckLogin } from '../request';
import Foot from '../CustomizedComponents/Foot.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import ArgonButton from '../CustomizedComponents/ArgonButton.vue';
import SideNav from './components/SideNav.vue'
import ArgonBadge from '../CustomizedComponents/ArgonBadge.vue';
import TestSuitesTable from './components/TestSuitesTable.vue'
import pinia from '../store/store'
import { useModelsStore } from '../store/ToolsStore/modelsStore'
import { useLLMmodellingStore } from '../store/ToolsStore/LLMmodellingStore.js';
import { ElNotification } from 'element-plus'
import { useUserStore } from '../store/userStore';
import { ElLoading } from 'element-plus'
import { useModuleStore } from '../store/module';
import { listAllModelsByUserID,listAllTestSuitesByModelID } from './commonFunction.js';
import { useTestSuitesStore } from '../store/ToolsStore/testSuitesStore'


const moduleStore = useModuleStore(pinia)
const route = useRoute()
const router = useRouter()
const modelStore = useModelsStore(pinia)
const testSuitesStore = useTestSuitesStore(pinia)




onMounted(async () => {
    let loadingInstance = ElLoading.service({ fullscreen: true })
    moduleStore.CurrentSubSystem = "Tools"
    moduleStore.CurrentSubSystemRoute = "Tools_Models"
    moduleStore.CurrentModule = 'TestSuites'
    moduleStore.CurrentModuleDetails = ''
    moduleStore.CurrentRoute = 'TestSuites_Home'

    try {
        // 检查登录状态
        await CheckLogin()
        // 获取当前用户的所有model
        await listAllModelsByUserID()
        // 获取每个model下的testsuite
        await listAllTestSuitesByModelID(modelStore.modelsList)


        loadingInstance.close()

    } catch (error) {

        loadingInstance.close()

    }



})
</script>


<style scoped></style>
