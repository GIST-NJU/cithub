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
                        <!-- 抽象模型预览 -->

                        <div class="card-header pb-0">
                            <ArgonBadge type="lg" :class="{ 'borderGlow': !AbstractModelPreviewFlag }" variant="gradient"
                                color="primary" @click="AbstractModelPreviewFlag = !AbstractModelPreviewFlag"> Abstract
                                Model Prview
                            </ArgonBadge>

                            <ArgonBadge style="margin-left:10px" type="lg"
                                :class="{ 'borderGlow': !CithubModelPreviewFlag }" variant="gradient" color="info"
                                @click="CithubModelPreviewFlag = !CithubModelPreviewFlag">
                                Cithub Model
                                Prview
                            </ArgonBadge>
                        </div>
                        <div v-auto-animate class="card-header pb-0">
                            <!-- 抽象模型预览 -->
                            <div v-if="!AbstractModelPreviewFlag">
                                <h4>Abstract Model</h4>
                                <hr class="my-3 horizontal white" />
                                <h5>Parameters and Values</h5>
                                <el-input disabled v-model="AbstractModelParamsValuesPreview" autosize type="textarea"
                                    placeholder="Please input" />
                                <hr class="my-3 horizontal white" />
                                <h5>Constraints</h5>
                                <el-input disabled v-model="AbstractModelConstraintsPreview" autosize type="textarea"
                                    placeholder="Please input" />
                            </div>
                            <!-- Cithub模型预览 -->
                            <div v-if="!CithubModelPreviewFlag">
                                <h4>Cithub Model</h4>
                                <hr class="my-3 horizontal white" />
                                <el-input disabled v-model="currentModel.currentModel.modelCithub" autosize type="textarea"
                                    placeholder="Please input" />
                            </div>


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
                                    <argon-badge style="margin-left:5px" variant="gradient"
                                        color="info">Algorithm</argon-badge>
                                    <span style="margin-left:5px">{{currentTestSuite.currentTestSuites.algorithm }}</span>
                                </div>

                                <div>
                                    <argon-badge class="statics" variant="gradient" color="warning">
                                        Strength </argon-badge>
                                    <span style="margin-left:5px">
                                         {{ currentModel.currentModel.strength }}</span>
                      

                                </div>


                                <div>
                                    <argon-badge class="statics" variant="gradient" color="primary">
                                        Time</argon-badge>
                                    <span style="margin-left:5px">{{ currentTestSuite.currentTestSuites.time }}</span>
                                  

                                </div>
                                <div>
                                    <argon-badge class="statics" variant="gradient"> Size</argon-badge>

                                    <span style="margin-left:5px">{{
                                        currentTestSuite.currentTestSuites.size }}</span>

                                </div>


                            </div>

                        </div>
                        <hr class="my-3 horizontal dark" />

                        <div style="display:flex;justify-content: space-around;margin: 0px 0px 5px 5px ;height:40px;">


                            <div class="col-md-2 button-container" :class="{ 'borderGlow': !TestSuitePreviewFlag }">
                                <a class="btn btn-link text-dark px-3 mb-0"
                                    @click="TestSuitePreviewFlag = !TestSuitePreviewFlag">
                                    <i class="fas fa-book-open text-primary me-2" aria-hidden="true"></i> TestSuite
                                </a>
                            </div>


                            <div class="col-md-2 button-container" :class="{ 'borderGlow': !EvaluationPreviewFlag }">

                                <a class="btn btn-link text-dark px-3 mb-0"
                                    @click="EvaluationPreviewFlag = !EvaluationPreviewFlag">
                                    <i class="fas fa-book-reader text-success me-2" aria-hidden="true"></i> Evaluation
                                </a>
                            </div>


                            <div class="col-md-2 button-container" :class="{ 'borderGlow': !PrioritisationPreviewFlag }">

                                <a class="btn btn-link text-dark px-3 mb-0"
                                    @click="PrioritisationPreviewFlag = !PrioritisationPreviewFlag">
                                    <i class="fas fa-blender-phone text-info me-2" aria-hidden="true"></i>
                                    Prioritisation
                                </a>
                            </div>


                            <div class="col-md-2 button-container" :class="{ 'borderGlow': !ReductionPreviewFlag }">
                                <a class="btn btn-link text-dark px-3 mb-0"
                                    @click="ReductionPreviewFlag = !ReductionPreviewFlag">
                                    <i class="fas fa-caravan text-warning me-2" aria-hidden="true"></i> Reduction
                                </a>
                            </div>
                            <div class="col-md-2 button-container" :class="{ 'borderGlow': !ConversionPreviewFlag }">

                                <a class="btn btn-link text-dark px-3 mb-0"
                                    @click="ConversionPreviewFlag = !ConversionPreviewFlag">
                                    <i class="fas fa-truck-monster text-secondary me-2" aria-hidden="true"></i> Conversion
                                </a>
                            </div>


                        </div>



                        <!-- test suites -->
                        <div v-auto-animate class="card-header pb-0">
                            <div v-if="TestSuitePreview != ''">
                                <div v-if="!TestSuitePreviewFlag">
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

                        <div v-auto-animate class="card-header">
                            <div v-if="!EvaluationPreviewFlag">
                                <EvaluationsTable></EvaluationsTable>
                            </div>
                        </div>
                        <!-- Prioritisation -->
                        <div v-auto-animate class="card-header">
                            <div v-if="!PrioritisationPreviewFlag">
                                <PrioritisationTable></PrioritisationTable>
                            </div>
                        </div>
                        <!-- Reduction -->
                        <div v-auto-animate class="card-header">
                            <div v-if="!ReductionPreviewFlag">
                                <ReductionTable></ReductionTable>
                            </div>
                        </div>

                        <!-- conversion -->
                        <div v-auto-animate class="card-header">
                            <div v-if="!ConversionPreviewFlag">
                                <ConversionTable></ConversionTable>
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
import ArgonBadge from '../CustomizedComponents/ArgonBadge.vue';
import ArgonInput from '../CustomizedComponents/ArgonInput.vue';
import ArgonButton from '../CustomizedComponents/ArgonButton.vue';
import EvaluationsTable from './components/EvaluationsTable.vue'
import ConversionTable from './components/ConversionTable.vue'
import PrioritisationTable from './components/PrioritisationTable.vue'
import ReductionTable from './components/ReductionTable.vue'
import pinia from '../store/store'
import { useTestSuitesStore } from '../store/testSuitesStore'
import { useCurrentModel } from '../store/currentModel'
import { useCurrentTestSuitesStore } from '../store/currentTestSuite'
import Foot from '../CustomizedComponents/Foot.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import SideNav from './components/SideNav.vue'


const currentModel = useCurrentModel(pinia)
const testSuitesStore = useTestSuitesStore(pinia)
const currentTestSuite = useCurrentTestSuitesStore(pinia)
const route = useRoute()

const AbstractModelPreviewFlag = ref(true)
const AbstractModelParamsValuesPreview = ref('')
const AbstractModelConstraintsPreview = ref('')
const CithubModelPreviewFlag = ref(true)

const TestSuitePreview = ref('')
const TestSuitePreviewFlag = ref(true)

const EvaluationPreviewFlag = ref(true)
const PrioritisationPreviewFlag = ref(true)
const ReductionPreviewFlag = ref(true)

const ConversionPreviewFlag = ref(true)


onMounted(async () => {

    const parsedData = JSON.parse(currentModel.currentModel.paramsvalues)
    // 移除 row_index 属性
    const tableDataTmp = parsedData.map(item => {
        const { row_index, ...rest } = item;
        return rest;
    });
    AbstractModelParamsValuesPreview.value = JSON.stringify(tableDataTmp, null, 6)
    AbstractModelConstraintsPreview.value = JSON.stringify(JSON.parse(currentModel.currentModel.cons), null, 6)

    TestSuitePreview.value = JSON.parse(currentTestSuite.currentTestSuites.testsuitescontents)
    TestSuitePreview.value = JSON.stringify(TestSuitePreview.value, null, 6)



})
</script>

<style scoped>
.statics {

    margin: 0px 5px 0px 40px;
}

.button-container:hover {
    transform: scale(1.01);
    /* 在悬停时放大 1.1 倍 */
    transition: transform 0.3s ease;
    /* 添加过渡效果 */
}

.borderGlow {
    border: 1px solid #2dce89;
    /* 设置边框样式，这里使用蓝色边框作为示例 */
    box-shadow: 0 0 10px #2dce89;
    /* 设置阴影效果，使边框发光 */
}
</style>
  