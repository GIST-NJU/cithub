<template>
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
                            <!-- 模型预览 -->
                            <h3>Model</h3>
                            <el-input disabled v-model="currentModel.currentModel.modelCithub" autosize type="textarea"
                                placeholder="Please input" />
                        </div>
                        <hr class="my-3 horizontal dark" />
                        <div class="card-body">
                            <h3 style="text-align:center;">TestSuite Info</h3>
                            <hr class="my-3 horizontal white" />


                            <div class="col-md-3">
                                <h4> TestSuite Name</h4>
                                <argon-input v-model="currentTestSuite.currentTestSuites.testsuitesname" type="text" />
                            </div>
                            <div class="col-md-10">
                                <h4> TestSuite Description</h4>
                                <argon-input type="text"
                                    v-model="currentTestSuite.currentTestSuites.testsuitesdescriptions" />
                            </div>
                            <hr class="my-3 horizontal white" />

                            <div style="display: flex;">
                                <div>
                                    <span style="margin-left:5px">
                                        Algorithm:</span>

                                    <argon-badge style="margin-left:5px" variant="gradient" color="info">{{
                                        currentTestSuite.currentTestSuites.algorithm }}</argon-badge>
                                </div>

                                <div>
                                    <span style="margin-left:15px">
                                        Strength:</span>
                                    <argon-badge class="statics" variant="gradient" color="warning">
                                        {{ currentModel.currentModel.strength }}</argon-badge>

                                </div>


                                <div>
                                    <span style="margin-left:15px">Time:</span>
                                    <argon-badge class="statics" variant="gradient" color="primary">
                                        {{ currentTestSuite.currentTestSuites.time }}</argon-badge>

                                </div>
                                <div>
                                    <span style="margin-left:15px">Size:</span>
                                    <argon-badge class="statics" variant="gradient">{{
                                        currentTestSuite.currentTestSuites.size }} </argon-badge>

                                </div>


                            </div>

                        </div>
                        <hr class="my-3 horizontal dark" />

                        <div style="display:flex;justify-content: space-around;margin-left: 5px;">

                            <div class="col-md-2 button-container">
                                <a class="btn btn-link text-dark px-3 mb-0"
                                    @click="TestSuitePreviewFlag = !TestSuitePreviewFlag">
                                    <i class="fas fa-book-open text-primary me-2" aria-hidden="true"></i>Show TestSuite
                                </a>
                            </div>


                            <div class="col-md-2 button-container">

                                <a class="btn btn-link text-dark px-3 mb-0"
                                    @click="EvaluationPreviewFlag = !EvaluationPreviewFlag">
                                    <i class="fas fa-book-reader text-success me-2" aria-hidden="true"></i>Show Evaluation
                                </a>
                            </div>


                            <div class="col-md-2.5 button-container">

                                <a class="btn btn-link text-dark px-3 mb-0"
                                    @click="TestSuitePreviewFlag = !TestSuitePreviewFlag">
                                    <i class="fas fa-blender-phone text-info me-2" aria-hidden="true"></i>Show
                                    Prioritisation
                                </a>
                            </div>


                            <div class="col-md-2 button-container">

                                <a class="btn btn-link text-dark px-3 mb-0"
                                    @click="TestSuitePreviewFlag = !TestSuitePreviewFlag">
                                    <i class="fas fa-caravan text-warning me-2" aria-hidden="true"></i>Show Reduction
                                </a>
                            </div>


                        </div>



                        <!-- test suites -->
                        <div v-auto-animate class="card-header pb-0">
                            <div v-if="TestSuitePreview != ''">
                                <div v-show="!TestSuitePreviewFlag">
                                    <h6>TestSuites</h6>
                                    <el-input disabled v-model="TestSuitePreview" autosize type="textarea"
                                        placeholder="Please input" />
                                </div>
                            </div>
                            <div v-else>
                                <div v-show="!TestSuitePreviewFlag">
                                    <h6>Sorry, There is no TestSuite right now</h6>
                                </div>

                            </div>

                        </div>

                        <!-- evaluations -->

                        <div class="card-header">
                            <div v-show="!EvaluationPreviewFlag">
                                <EvaluationsTable></EvaluationsTable>
                            </div>




                        </div>






                    </div>
                </div>
            </div>



            <Foot></Foot>
        </div>
    </main>
</template>
  
<script setup>

import { onMounted, reactive, ref, computed, defineProps } from 'vue';

import { useModelsStore } from '../store/modelsStore'
import { useRoute, useRouter } from 'vue-router';
import { request } from '../request';
import { ElNotification } from 'element-plus'
import ArgonBadge from '../ComponentCommon/ArgonBadge.vue';
import ArgonInput from '../ComponentCommon/ArgonInput.vue';
import ArgonButton from '../ComponentCommon/ArgonButton.vue';
import EvaluationsTable from './components/EvaluationsTable.vue'
import { useTestSuitesStore } from '../store/testSuitesStore'
import { useCurrentModel } from '../store/currentModel'
import { useCurrentTestSuitesStore } from '../store/currentTestSuite'
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import SideNav from './components/SideNav.vue'


const currentModel = useCurrentModel()
const testSuitesStore = useTestSuitesStore()
const currentTestSuite = useCurrentTestSuitesStore()
const route = useRoute()

const TestSuitePreview = ref('')
const TestSuitePreviewFlag = ref(true)

const EvaluationPreview = ref('')
const EvaluationPreviewFlag = ref(true)


onMounted(async () => {
    TestSuitePreview.value = JSON.parse(currentTestSuite.currentTestSuites.testsuitescontents)
    TestSuitePreview.value = JSON.stringify(TestSuitePreview.value, null, 6)



})
</script>

<style scoped>
.statics {

    margin: 0px 5px 0px 5px;
}

.button-container:hover {
    transform: scale(1.2);
    /* 在悬停时放大 1.1 倍 */
    transition: transform 0.3s ease;
    /* 添加过渡效果 */
}
</style>
  