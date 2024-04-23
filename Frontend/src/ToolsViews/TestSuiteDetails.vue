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
                    <div class="row">
                        <div class="col-2">

                            <ArgonButton class="mb-2" color="secondary" size="sm" @click="goBack" full-width>
                                <span class="ni ni-bold-left  me-1"> </span>Back
                            </ArgonButton>

                        </div>

                        <div class="col-2">

                            <ArgonButton class="mb-2" color="info" size="sm" full-width
                                @click="TestTableListFlag = !TestTableListFlag">
                                Model {{ currentModel.currentModel.modelname }}'s TestSuites List
                            </ArgonButton>

                        </div>
                    </div>

                    <!-- TestSuite Table for current model -->


                    <div>
                        <div v-if="TestTableListFlag" class="card">
                            <div v-for="(obj, index) in testSuitesStore.testSuitesList" class="card-body px-0 pt-0 pb-0">
                                <TestSuitesTable :testSuites="obj['testSuites']" :model="obj['model']">
                                </TestSuitesTable>
                            </div>
                        </div>
                    </div>

                    <div class="card" v-if="!currentTestSuite.currentTestSuites.testsuitesname">


                        <div class="card-body pb-0">
                            <div class="row">
                                <h3 class="text-center">Please Choose one of the testsuites From the above TestSuites Table.
                                </h3>
                            </div>
                        </div>

                    </div>

                    <div class="row my-2" v-if="currentTestSuite.currentTestSuites.testsuitesname">
                        <div class="col-6">

                            <!-- Basic Info -->
                            <div class="card">
                                <div class="card-header pb-0">
                                    <h3 class="text-center">TestSuite Info</h3>
                                </div>
                                <div class="card-body">

                                    <div class="row">
                                        <h5>Basic Info</h5>
                                        <div class="col-4">
                                            <h6> TestSuite Name</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.testsuitesname"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-8">
                                            <h6>TestSuite Description</h6>
                                            <argon-input type="text"
                                                v-model="currentTestSuite.currentTestSuites.testsuitesdescriptions"
                                                readonly />
                                        </div>
                                    </div>

                                    <hr class="my-3 horizontal white" />

                                    <div class="row">
                                        <h5>Generation Info</h5>
                                        <div class="col-3">
                                            <h6> Generation Tool</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.generationtool"
                                                type="text" readonly />
                                        </div>


                                        <div class="col-2">
                                            <h6>TestSuite Size</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.size" type="text"
                                                readonly />
                                        </div>

                                        <div class="col-3">
                                            <h6>Generation Time</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.generationtime"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-4 mt-4 pt-2">
                                            <ArgonButton full-width color="success" @click="showdialogNew">
                                                <span class="bi bi-arrow-clockwise"></span>Re-Generation
                                            </ArgonButton>
                                        </div>

                                    </div>

                                    <hr class="my-3 horizontal white" />

                                    <div class="row" v-if="currentTestSuite.currentTestSuites.reductiontool">
                                        <h5>Reduction Info</h5>
                                        <div class="col-3">
                                            <h6> Reduction Tool</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.reductiontool"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-3">
                                            <h6>Size after Reduction</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.sizeafterreduction"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-3">
                                            <h6>Reduction Time</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.reductiontime"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-3  mt-4 pt-2">
                                            <ArgonButton full-width color="primary" @click="showdialogNewReduction">
                                                {{ getButtonName('Reduction') }}
                                            </ArgonButton>
                                        </div>

                                    </div>

                                    <div class="row" v-else>
                                        <h6>TestSuite {{ currentTestSuite.currentTestSuites.testsuitesname }} has not been
                                            Reducted, Click the Button below to Reduct.
                                        </h6>
                                        <div class="col-12 mt-4 pt-2">
                                            <ArgonButton full-width color="primary" @click="showdialogNewReduction">
                                                {{ getButtonName('Reduction') }}
                                            </ArgonButton>
                                        </div>

                                    </div>

                                    <hr class="my-3 horizontal white" />

                                    <div class="row" v-if="currentTestSuite.currentTestSuites.prioritisationtool">
                                        <h5>Prioritisation Info</h5>
                                        <div class="col-4">
                                            <h6> Prioritisation Tool</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.prioritisationtool"
                                                type="text" readonly />
                                        </div>


                                        <div class="col-4">
                                            <h6>Prioritisation Time</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.prioritisationtime"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-4 mt-4 pt-2">
                                            <ArgonButton full-width color="warning" @click="showdialogNewPrioritization">
                                                {{ getButtonName('Prioritisation') }}
                                            </ArgonButton>
                                        </div>

                                    </div>


                                    <div class="row" v-else>
                                        <h6>TestSuite {{ currentTestSuite.currentTestSuites.testsuitesname }} has not been
                                            Prioritised, Click the Button below to Prioritise.</h6>
                                        <div class="col-12 mt-4 pt-2">
                                            <ArgonButton full-width color="warning" @click="showdialogNewPrioritization">
                                                {{ getButtonName('Prioritisation') }}

                                            </ArgonButton>
                                        </div>

                                    </div>



                                    <hr class="my-3 horizontal white" />


                                    <div class="row" v-if="currentTestSuite.currentTestSuites.evaluationtool">
                                        <h5>Evaluation Info</h5>
                                        <div class="col-4">
                                            <h6> Evaluation Tool</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.evaluationtool"
                                                type="text" readonly />
                                        </div>


                                        <div class="col-4">
                                            <h6>Evaluation Time</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.evaluationtime"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-4  mt-4 pt-2">
                                            <ArgonButton full-width color="info" @click="showdialogNewEvaluation">Evaluation
                                            </ArgonButton>
                                        </div>


                                        <div class="col-12  mt-4 pt-2">
                                            <h1>coverage checker chart</h1>
                                        </div>

                                    </div>

                                    <div class="row" v-else>
                                        <h6> TestSuite {{ currentTestSuite.currentTestSuites.testsuitesname }} has not been
                                            Evaluated, Click the Button below to Evaluate
                                        </h6>
                                        <div class="col-12 mt-4 pt-2">
                                            <ArgonButton full-width color="info" @click="showdialogNewEvaluation">Evaluation
                                            </ArgonButton>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>

                        <div class="col-6">
                            <div class="card">
                                <div class="card-body">


                                    <h1>测试活动调用顺序图</h1>



                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- TestSuite Card -->
                    <!-- show testsuite in the form of table -->
                    <hr class="my-3 horizontal dark" />

                    <div class="card" v-if="currentTestSuite.currentTestSuites.testsuitesname">
                        <div class="card-header pb-0">
                            <h5 class="mb-0">TestSutie of
                                {{ currentTestSuite.currentTestSuites.testsuitesname }}</h5>
                        </div>


                        <div class="card-body">


                            <hr class="my-3 horizontal white" />

                            <div class="row">
                                <div class="col-12">
                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <!-- Caption -->
                                        <div>

                                        </div>

                                        <!-- 下拉框 -->
                                        <div class="btn-group">
                                            <button type="button" class="btn btn-secondary dropdown-toggle"
                                                data-bs-toggle="dropdown" aria-expanded="false">
                                                Operations
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li><a class="dropdown-item" @click="ExportTestSuite">Export
                                                        TestSuite</a></li>
                                                <li><a class="dropdown-item" @click="showdialogNewConversion">Convert
                                                        into Test Plan</a>
                                                </li>
                                                <li><a class="dropdown-item" @click="showdialogNewEvaluation">Evaluation</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <!-- 表格 -->
                                    <table class="table-secondary table-bordered" style="width: 100%;">
                                        <thead>
                                            <tr>
                                                <th class="text-center">#</th>
                                                <th v-for="(parameter, index) in currentModel.currentModel.PandVOBJ"
                                                    :key="index" class="text-center">
                                                    {{ parameter.Parameter }}
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr v-for="(row, rowIndex) in result" :key="rowIndex">
                                                <td class="text-center">{{ rowIndex + 1 }}</td>
                                                <td v-for="(value, colIndex) in row" :key="colIndex" class="text-center">
                                                    {{ value }}
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>








                        </div>


                    </div>

                </div>



                <!-- 点击按钮展开的对话框 -->
                <!-- Regenerate test suites -->
                <el-dialog v-model="dialogFormVisibleNew" title="Generate a new testSuite based on current model">
                    <el-form :model="dialogformNewTestSuites" label-position="left" label-width="170px">

                        <!-- <el-form-item label="Test Suite Name:">
                            <el-input v-model="dialogformNewTestSuites.testsuitesname" />
                        </el-form-item>
                        <el-form-item label="Test Suite Description:">
                            <el-input autosize type="textarea" v-model="dialogformNewTestSuites.testsuitesdescriptions" />
                        </el-form-item> -->

                        <el-form-item label="Strength: ">
                            <el-select clearable style="margin: 0 0 0 0;padding: 0;width: 250px" v-model="strength"
                                class="m-2" placeholder="Select a covering strength">
                                <el-option v-for="item in StrengthOptions" :key="item.value" :label="item.label"
                                    :value="item.value" />
                            </el-select>
                        </el-form-item>
                        <el-form-item label="Generation Tool: ">
                            <el-select v-model="AlgorithmChosed" class="m-2" placeholder="Select an Tool for generating"
                                @change="handleAlgorithmChange">
                                <el-option v-for="item in AlgorithmOptions" :key="item.value" :label="item.label"
                                    :value="item.value" />
                            </el-select>
                        </el-form-item>

                        <!-- 在此显示 对应 AlgorithmChosed 的 tool.description -->

                        <el-form-item label="Tool Description: ">
                            <el-input readonly v-model="AlgorithmDescription" :autosize="{ minRows: 2, maxRows: 12 }"
                                type="textarea" placeholder="The Description of Tool Choosen Will Be Displayed Here" />
                        </el-form-item>

                    </el-form>

                    <div v-if="inAPIcall" class="row" style="margin: 0px 0px 0px 30px;">
                        <div class="spinner-border  text-success col-4" role="status">
                        </div>
                        <div class="col-8" style="margin: 5px 0px 0px 0px;">
                            <h6>CitHub is calling the API of {{ AlgorithmChosed }}, Please wait a while...</h6>
                        </div>
                    </div>

                    <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="dialogFormVisibleNew = false">Cancel</el-button>
                            <el-button type="primary" @click="confirmGenerateNewTestSuites">
                                Confirm
                            </el-button>
                        </span>
                    </template>
                </el-dialog>


                <!-- New Prioritisation -->
                <el-dialog v-model="dialogFormVisibleNewPrioritization" title="Choose a Tool for Prioritization:">
                    <el-form :model="dialogformNewTestSuites" label-position="left" label-width="170px">
                        <el-form-item label="Tool:">
                            <el-select v-model="AlgorithmChosedPrioritization" class="m-2"
                                @change="handleAlgorithmOptionsPrioritizationChange"
                                placeholder="Select a tool for prioritising current testsuite." clearable>
                                <el-option v-for="item in AlgorithmOptionsPrioritization" :key="item.value"
                                    :label="item.label" :value="item.value" />
                            </el-select>
                        </el-form-item>

                        <!-- 在此显示 对应 AlgorithmChosed 的 tool.description -->

                        <el-form-item label="Tool Description: ">
                            <el-input readonly v-model="AlgorithmDescriptionPrioritization"
                                :autosize="{ minRows: 2, maxRows: 12 }" type="textarea"
                                placeholder="The Description of Tool Choosen Will Be Displayed Here" />
                        </el-form-item>

                        <div v-if="AlgorithmChosedPrioritization == 'SortArray'">
                            <!-- <el-form-item label="Weight:">
                                    <el-input v-model="Weight" type="textarea" :autosize="{ minRows: 4 }" placeholder="Note:
1.Please input the Weight of parameters using `,`  to split each Weight, eg: 3,1,2 for three parameters.
2.For each parameter, you need to input a corresponding weight.eg: three parameters have to input three weight
                                    ">
                                    </el-input>
                                </el-form-item> -->

                            <el-form-item>
                                <div class=row>
                                    <h5>Please input a corresponding Weight for each Parameter</h5>
                                    <div class="row">
                                        <div class="col-4">
                                            <h6>Parameter</h6>
                                        </div>
                                        <div class="col-2">
                                            <h6>Weight</h6>
                                        </div>
                                    </div>
                                    <div class="row " v-for="(Param, index) in currentModel.currentModel.PandVOBJ">
                                        <div class="col-4">
                                            <el-input readonly v-model="Param.Parameter"></el-input>
                                        </div>
                                        <div class="col-4">
                                            <el-input-number v-model="Weight[index]" :min="0" placeholder="Integer" />
                                        </div>
                                    </div>
                                    <!-- {{ Weight }} -->

                                </div>
                            </el-form-item>

                        </div>



                    </el-form>

                    <div v-if="inAPIcall" class="row" style="margin: 15px 0px 0px 40px;">
                        <div class="spinner-border  text-success col-4" role="status">
                        </div>
                        <div class="col-8" style="margin: 5px 0px 0px 0px;">

                            <h6>CitHub is calling the API of {{ AlgorithmChosedPrioritization }}, Please wait a while...
                            </h6>

                        </div>
                    </div>

                    <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="dialogFormVisibleNewPrioritization = false">Cancel</el-button>
                            <el-button type="primary" @click="confirmNewPrioritisation">
                                Confirm
                            </el-button>
                        </span>
                    </template>
                </el-dialog>


                <!-- New Reduction -->
                <el-dialog v-model="dialogFormVisibleNewReduction" title="Choose a Tool for Reduction:">
                    <el-form :model="dialogformNewTestSuites" label-position="right" label-width="140px">
                        <el-form-item label="Tool:">
                            <el-select v-model="AlgorithmChosedReduction" class="m-2"
                                @change="handleAlgorithmOptionsReductionChange"
                                placeholder="Select a tool for reducting current testsuite." clearable>
                                <el-option v-for="item in AlgorithmOptionsReduction" :key="item.value" :label="item.label"
                                    :value="item.value" />
                            </el-select>
                        </el-form-item>

                        <el-form-item label="Tool Description: ">
                            <el-input readonly v-model="AlgorithmDescriptionReduction"
                                :autosize="{ minRows: 2, maxRows: 12 }" type="textarea"
                                placeholder="The Description of Tool Choosen Will Be Displayed Here" />
                        </el-form-item>
                    </el-form>

                    <div v-if="inAPIcall" class="row" style="margin: 15px 0px 0px 40px;">
                        <div class="spinner-border  text-success col-4" role="status">
                        </div>
                        <div class="col-8" style="margin: 5px 0px 0px 5px;">
                            <h6>CitHub is calling the API of {{ AlgorithmChosedReduction }}, Please wait a while...
                            </h6>
                        </div>
                    </div>


                    <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="dialogFormVisibleNewReduction = false">Cancel</el-button>
                            <el-button type="primary" @click="confirmNewReduction">
                                Confirm
                            </el-button>
                        </span>
                    </template>
                </el-dialog>


                <!-- New Test Plan -->
                <el-dialog v-model="dialogFormVisibleNewConversion" title="Convert TestSuite into:">
                    <el-form :model="dialogformNewTestSuites" label-position="left" label-width="150px">
                        <el-form-item label="Tools of Conversion">
                            <el-select v-model="AlgorithmChosedConversion" class="m-2"
                                placeholder="Select a tool for converting test suite into." clearable>
                                <el-option v-for="item in AlgorithmOptionsConversion" :key="item.value" :label="item.label"
                                    :value="item.value" />
                            </el-select>
                        </el-form-item>
                        <div v-if="AlgorithmChosedConversion == 'JUnit Test Plan'">
                            <el-form-item label="Function Head">
                                <el-input v-model="functionHead" type="textarea" autosize
                                    placeholder="Please input function Head right here, eg:'public void test()'"></el-input>
                            </el-form-item>
                            <el-form-item label="Function Body">
                                <el-input v-model="functionBody" type="textarea" autosize
                                    placeholder="Please input function Body right here, eg:''CIT cit= new CIT(&p2) cit.execuse(&p3) cit.process(&p1)'">
                                </el-input>
                            </el-form-item>
                        </div>


                        <div v-if="AlgorithmChosedConversion == 'NL Test Plan'">

                            <el-form-item label="Output Model Format">
                                <el-input v-model="outputModel" type="textarea" :autosize="{ minRows: 4 }"
                                    placeholder="Please input the Output Model format you want right here and use `&#` as placeholder, eg:'first,we should do &#,then open &#,finally,enter in the browser address bar with &#'. All the `&#` will be replaced by the actual value of parameters.">
                                </el-input>
                            </el-form-item>

                        </div>


                    </el-form>

                    <div v-if="inAPIcall" class="row" style="margin: 15px 0px 0px 40px;">
                        <div class="spinner-border  text-success col-4" role="status">
                        </div>
                        <div class="col-8" style="margin: 5px 0px 0px 5px;width: 100%;">
                            <h6>CitHub is calling the API of {{ AlgorithmChosedConversion }} to convert the testsuite
                                into test plan, Please wait a while...
                            </h6>
                        </div>
                    </div>

                    <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="dialogFormVisibleNewConversion = false">Cancel</el-button>
                            <el-button type="primary" @click="confirmNewConversion">
                                Confirm
                            </el-button>
                        </span>
                    </template>
                </el-dialog>


                <!-- New Evaluation -->
                <el-dialog v-model="dialogFormVisibleNewEvaluation" title="New Evaluation">
                    <el-form :model="dialogformNewTestSuites">
                        <el-form-item label="Tool: ">
                            <el-select v-model="AlgorithmChosedEvaluation" class="m-2"
                                placeholder="Select an Tool for evaluating">
                                <el-option v-for="item in AlgorithmOptionsEvaluation" :key="item.value" :label="item.label"
                                    :value="item.value" />
                            </el-select>
                        </el-form-item>
                    </el-form>


                    <div v-if="inAPIcall" class="row" style="margin: 15px 0px 0px 20px;">
                        <div class="spinner-border text-success col-1" role="status">
                        </div>
                        <div class="col-11" style="margin: 5px 0px 0px 5px;">
                            <h6>Cithub is calling the API of {{ AlgorithmChosedEvaluation }} to evaluate, Please wait a
                                while...
                            </h6>
                        </div>
                    </div>

                    <div ref="lineChartContainer" class="line-chart-container">
                        <div ref="lineChart" style="width: 600px; height: 400px;"></div>
                    </div>

                    <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="dialogFormVisibleNewEvaluation = false">Cancel</el-button>
                            <el-button type="primary" @click="confirmNewEvaluation">
                                Confirm
                            </el-button>
                        </span>
                    </template>
                </el-dialog>





            </div>
            <Foot></Foot>
        </div>
    </main>
</template>

<script  setup>
import { useRoute, useRouter } from 'vue-router';
import { onMounted, reactive, ref, computed, watch, onBeforeMount } from 'vue';
import { request } from '../request';
import Foot from '../CustomizedComponents/Foot.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import SideNav from './components/SideNav.vue'
import ArgonBadge from '../CustomizedComponents/ArgonBadge.vue';
import ArgonButton from '../CustomizedComponents/ArgonButton.vue'
import pinia from '../store/store'
import { useUserStore } from '../store/userStore';
import { useModelsStore } from '../store/ToolsStore/modelsStore'
import TestSuitesTable from './components/TestSuitesTable.vue'
import ArgonInput from '../CustomizedComponents/ArgonInput.vue';
import { useCurrentModel } from '../store/ToolsStore/currentModel'
import { ElNotification } from 'element-plus'
import { useTestSuitesStore } from '../store/ToolsStore/testSuitesStore'
import { useCurrentTestSuitesStore } from '../store/ToolsStore/currentTestSuite'
import { listAllTestSuitesByModelID, listModelInfoByModelID, CitHubModel, CitHubTestSuite } from './commonFunction.js';
import toolsInfo from "../CustomizedComponents/tools_info.json";
import * as echarts from 'echarts'
import { ElLoading } from 'element-plus'


const testSuitesStore = useTestSuitesStore(pinia)
const currentModel = useCurrentModel(pinia)
const currentTestSuite = useCurrentTestSuitesStore(pinia)
const router = useRouter();
const route = useRoute()


const TestTableListFlag = ref(false)


// 将覆盖表显示为Html表格
const result = ref([]);
const getValueFromArrays = async (objArray, indexArray) => {
    // console.log("objArray",objArray)
    // console.log("indexArray",indexArray)
    result.value.length = 0
    indexArray.forEach((indexes) => {
        let values = indexes.map((index, i) => {
            const parameter = objArray[i].Parameter;
            const valueArray = objArray[i].Value;

            if (valueArray && index < valueArray.length) {
                return valueArray[index];
            } else {
                return null; // 处理超出索引范围的情况
            }
        });

        result.value.push(values);
    });
};
const StrengthOptions = [
    {
        value: '2',
        label: '2',
    },
    {
        value: '3',
        label: '3',
    },
    {
        value: '4',
        label: '4',
    },
    {
        value: '5',
        label: '5',
    },
    {
        value: '6',
        label: '6',
    },
]
const strength = ref()


const inAPIcall = ref(false)



// ---------------For generation-----------------------
const AlgorithmChosed = ref('');
const AlgorithmOptions = reactive([]);
const AlgorithmDescription = ref('');

const listAllGenerationAlgorithm = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "Generation" && !tool.title.includes("GUI")) {
            AlgorithmOptions.push({ "value": tool.title, "label": tool.title, "url": tool.url, "description": tool.description });
        }
    }
};
const handleAlgorithmChange = () => {
    const selectedAlgorithm = AlgorithmOptions.find(option => option.value === AlgorithmChosed.value);
    if (selectedAlgorithm) {
        AlgorithmDescription.value = selectedAlgorithm.description;
    } else {
        AlgorithmDescription.value = '';
    }
};

const dialogFormVisibleNew = ref(false)
const dialogformNewTestSuites = reactive({
    testsuitesname: currentTestSuite.currentTestSuites.testsuitesname,
    testsuitesdescriptions: currentTestSuite.currentTestSuites.testsuitesdescriptions,
    lastupdatedtime: '',
    createdtime: '',
    modelid: route.query.modelid
})
const showdialogNew = () => {
    dialogFormVisibleNew.value = true
    dialogformNewTestSuites.modelid = route.query.modelid
}

const confirmGenerateNewTestSuites = async () => {
    inAPIcall.value = true
    // 获取当前时刻的Date对象
    const currentDate = new Date();
    dialogformNewTestSuites.lastupdatedtime = currentDate
    dialogformNewTestSuites.createdtime = currentDate
    // console.log("currentModel.currentModel", currentModel.currentModel)

    if (currentModel.currentModel.modelname == "") {
        ElNotification({
            title: 'New TestSuites fail!',
            message: 'Model Name can not be empty!',
            type: 'error',
        })
    }

    else {
        // 发送请求，对 model 得到 test suites
        for (const tool of AlgorithmOptions) {
            if (tool.value == AlgorithmChosed.value) {
                try {
                    // 构造CitHub Model，调用工具
                    currentModel.currentModel.CitHubModel = CitHubModel(currentModel.currentModel, parseInt(strength.value))
                    // console.log("generation cithub model", currentModel.currentModel.CitHubModel)

                    const TestSuitesRes = await request({
                        // 这里记得改回去，在校外无法用校内服务器
                        // url: tool.url,
                        url: 'http://localhost:8300',
                        method: 'POST',
                        // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                        headers: {
                            'Content-Type': 'text/plain'
                        },
                        // 转为Json
                        data: JSON.stringify(currentModel.currentModel.CitHubModel).replace(/"/g, '')
                    })

                    // console.log("TestSuitesRes", TestSuitesRes)
                    const newTestSuitesRes = await request({
                        url: "/tools/Save",
                        method: "POST",
                        data:
                        {

                            column: 'generation',
                            // testsuitesname: dialogformNewTestSuites.testsuitesname,
                            // testsuitesdescriptions: dialogformNewTestSuites.testsuitesdescriptions,
                            lastupdatedtime: dialogformNewTestSuites.lastupdatedtime,
                            // createdtime: dialogformNewTestSuites.createdtime,
                            modelid: currentTestSuite.currentTestSuites.modelid,
                            testsuiteid: currentTestSuite.currentTestSuites.testsuitesid,
                            testsuitescontents: JSON.stringify(TestSuitesRes.testsuite),
                            generationtime: TestSuitesRes.time,
                            size: TestSuitesRes.size,
                            generationtool: AlgorithmChosed.value,
                            strength: strength.value
                        }
                    })
                    // console.log("newTestSuitesRes", newTestSuitesRes)

                    if (newTestSuitesRes.NewStatus == 'success!') {

                        // 更新Testsuite Info区域的信息
                        await UpdateTestSuiteInfo()

                        ElNotification({
                            title: 'Generate Success!',
                            type: 'success',
                            message: 'Strength ' + strength.value + ' test suite' + ' by ' + AlgorithmChosed.value + ' generated success! Please check the results.',

                        })
                        inAPIcall.value = false
                        dialogFormVisibleNew.value = false


                    }
                    else {
                        inAPIcall.value = false
                        dialogFormVisibleNew.value = false
                        ElNotification({
                            title: 'Generate fail!',
                            type: 'error',
                        })



                    }
                }
                catch (err) {
                    console.log("err", err)
                    inAPIcall.value = false
                    dialogFormVisibleNew.value = false
                    ElNotification({
                        title: 'Generate fail!',
                        type: 'error',
                    })



                }
            }
        }
    }


}
// --------------------------------------------------------



// ---------------for Prioritization-----------------------
const dialogFormVisibleNewPrioritization = ref(false)

const showdialogNewPrioritization = () => {
    dialogFormVisibleNewPrioritization.value = true
    dialogformNewTestSuites.modelid = route.query.modelid
}

const AlgorithmChosedPrioritization = ref('')
const AlgorithmOptionsPrioritization = reactive([])
const AlgorithmDescriptionPrioritization = ref('');

const listAllPrioritisationOptions = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "Prioritisation") {
            AlgorithmOptionsPrioritization.push({ "value": tool.title, "label": tool.title, "url": tool.url, "description": tool.description })
        }
    }
}

const handleAlgorithmOptionsPrioritizationChange = () => {
    const selectedAlgorithm = AlgorithmOptionsPrioritization.find(option => option.value === AlgorithmChosedPrioritization.value);
    if (selectedAlgorithm) {
        AlgorithmDescriptionPrioritization.value = selectedAlgorithm.description;
    } else {
        AlgorithmDescriptionPrioritization.value = '';
    }
};

// MiniArray 工具的 权重 Weight
let Weight = reactive([])
const confirmNewPrioritisation = async () => {

    Weight = Array(currentModel.currentModel.PandVOBJ.length).fill(0)

    // console.log("currentModel", currentModel.currentModel)
    // console.log("currentTestSuites", currentTestSuite.currentTestSuites)
    inAPIcall.value = true


    // 构造发送给Prioritisation的 CitHubTestSuite 对象

    currentModel.currentModel.CitHubModel = CitHubModel(currentModel.currentModel, parseInt(currentTestSuite.currentTestSuites.strength))
    currentTestSuite.currentTestSuites.CitHubTestSuite = CitHubTestSuite(currentModel.currentModel.CitHubModel, currentTestSuite.currentTestSuites.testsuitescontents)

    // SortArray的特殊输入 Weight
    let WeightArray = Weight.map(Number)
    currentTestSuite.currentTestSuites.CitHubTestSuite.weight = WeightArray


    // console.log("CithubTestSuite", JSON.stringify(currentTestSuite.currentTestSuites.CitHubTestSuite).replace(/"/g, ''))
    for (const tool of AlgorithmOptionsPrioritization) {
        if (tool.value == AlgorithmChosedPrioritization.value) {
            try {
                const PrioritisationRes = await request({
                    // url: tool.url,
                    // 这里记得改回去，在校外无法用校内服务器
                    url: 'http://localhost:8304',
                    method: 'POST',
                    // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                    headers: {
                        'Content-Type': 'text/plain'
                    },
                    data: JSON.stringify(currentTestSuite.currentTestSuites.CitHubTestSuite).replace(/"/g, '')
                })
                // console.log("PrioritisationRes", PrioritisationRes)

                const SavePrioritisationRes = await request({
                    url: "/tools/Save",
                    method: "POST",
                    data:
                    {
                        column: 'prioritisation',
                        lastupdatedtime: new Date(),
                        modelid: currentTestSuite.currentTestSuites.modelid,
                        testsuiteid: currentTestSuite.currentTestSuites.testsuitesid,
                        testsuitescontents: JSON.stringify(PrioritisationRes.testsuite),
                        prioritisationtime: PrioritisationRes.time,
                        prioritisationtool: AlgorithmChosedPrioritization.value,

                    }
                })

                if (SavePrioritisationRes.NewStatus == 'success!') {

                    // 更新Testsuite Info区域的信息
                    await UpdateTestSuiteInfo()

                    ElNotification({
                        title: 'Prioritize Success!',
                        type: 'success',
                        message: 'Strength ' + strength.value + ' test suite' + ' by ' + AlgorithmChosedPrioritization.value + ' prioritized success! Please check the results.',


                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewPrioritization.value = false

                }
                else {
                    ElNotification({
                        title: 'Prioritize fail!',
                        type: 'error',
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewPrioritization.value = false

                }
            }
            catch (err) {

                ElNotification({
                    title: 'Prioritize fail!',
                    type: 'error',
                    message: err.message
                })
                inAPIcall.value = false
                dialogFormVisibleNewPrioritization.value = false
            }
        }



    }
}
// --------------------------------------------------------


// ---------------for Reduction-----------------------
const dialogFormVisibleNewReduction = ref(false)
const showdialogNewReduction = () => {
    dialogFormVisibleNewReduction.value = true
    dialogformNewTestSuites.modelid = route.query.modelid
}
const AlgorithmChosedReduction = ref('')
const AlgorithmOptionsReduction = reactive([])
const AlgorithmDescriptionReduction = ref('');

const listAllReductionOptions = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "SelectionReduction") {
            AlgorithmOptionsReduction.push({ "value": tool.title, "label": tool.title, "url": tool.url, "description": tool.description })
        }
    }
}

const handleAlgorithmOptionsReductionChange = () => {
    const selectedAlgorithm = AlgorithmOptionsReduction.find(option => option.value === AlgorithmChosedReduction.value);
    if (selectedAlgorithm) {
        AlgorithmDescriptionReduction.value = selectedAlgorithm.description;
    } else {
        AlgorithmDescriptionReduction.value = '';
    }
};

const confirmNewReduction = async () => {
    inAPIcall.value = true

    // 构造发送给 Reduction 的 CitHubTestSuite 对象

    currentModel.currentModel.CitHubModel = CitHubModel(currentModel.currentModel, parseInt(currentTestSuite.currentTestSuites.strength))
    currentTestSuite.currentTestSuites.CitHubTestSuite = CitHubTestSuite(currentModel.currentModel.CitHubModel, currentTestSuite.currentTestSuites.testsuitescontents)
    // console.log("CithubTestSuite", JSON.stringify(currentTestSuite.currentTestSuites.CitHubTestSuite).replace(/"/g, ''))



    for (const tool of AlgorithmOptionsReduction) {
        if (tool.value == AlgorithmChosedReduction.value) {

            try {
                const ReductionRes = await request({
                    // url: tool.url,
                    url: 'http://localhost:8305',
                    method: 'POST',
                    // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                    headers: {
                        'Content-Type': 'text/plain'
                    },
                    data: JSON.stringify(currentTestSuite.currentTestSuites.CitHubTestSuite).replace(/"/g, '')
                })
                // console.log("ReductionRes", ReductionRes)
                const newReductionRes = await request({
                    url: "/tools/Save",
                    method: "POST",
                    data:
                    {
                        column: 'reduction',
                        lastupdatedtime: new Date(),
                        modelid: currentTestSuite.currentTestSuites.modelid,
                        testsuiteid: currentTestSuite.currentTestSuites.testsuitesid,
                        testsuitescontents: JSON.stringify(ReductionRes.testsuite),
                        reductiontime: ReductionRes.time,
                        sizeafterreduction: ReductionRes.size,
                        reductiontool: AlgorithmChosedReduction.value,

                    }
                })

                if (newReductionRes.NewStatus == 'success!') {

                    // 更新Testsuite Info区域的信息
                    await UpdateTestSuiteInfo()


                    ElNotification({
                        title: 'Reduct Success!',
                        type: 'success',
                        message: 'Strength ' + strength.value + ' test suite' + ' by ' + AlgorithmChosedReduction.value + ' reducted success! Please check the results.',

                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewReduction.value = false


                }
                else {
                    ElNotification({
                        title: 'Reduct fail!',
                        type: 'error',
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewReduction.value = false

                }
            }
            catch (err) {
                ElNotification({
                    title: 'Reduct fail!',
                    type: 'error',
                })
                inAPIcall.value = false
                dialogFormVisibleNewReduction.value = false
            }
        }




    }

}
// --------------------------------------------------------


// ---------------for Conversion-----------------------
const dialogFormVisibleNewConversion = ref(false)
const showdialogNewConversion = () => {
    dialogFormVisibleNewConversion.value = true
    dialogformNewTestSuites.modelid = route.query.modelid
}
const AlgorithmChosedConversion = ref('')
const AlgorithmOptionsConversion = reactive([])
const listAllConversionOptions = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "FormatConversion" && !tool.title.includes("Reader")) {
            AlgorithmOptionsConversion.push({ "value": tool.title, "label": tool.title, "url": tool.url })
        }
    }
}
const functionHead = ref('')
const functionBody = ref('')
const outputModel = ref('')
const confirmNewConversion = async () => {

    inAPIcall.value = true
    // 构造发送给Conversion的data对象
    let conversionObj = {}
    let testsuite = JSON.parse(currentTestSuite.currentTestSuites.testsuitescontents).testsuite
    conversionObj.testsuite = testsuite
    let actual_value = []
    let ParamsAndValues = JSON.parse(currentModel.currentModel.paramsvalues)

    // 移除 row_index 属性
    ParamsAndValues = ParamsAndValues.map(item => {
        const { row_index, ...rest } = item;
        return rest;
    });

    for (const pv of ParamsAndValues) {
        let tempArray = pv.Value.split(',')
        actual_value.push(tempArray)
    }
    conversionObj.actual_value = actual_value


    if (AlgorithmChosedConversion.value == 'NL Test Plan') {
        conversionObj.outputModel = outputModel.value
        conversionObj = JSON.stringify(conversionObj)

        for (const tool of AlgorithmOptionsConversion) {
            if (tool.value == AlgorithmChosedConversion.value) {
                try {
                    const ConversionRes = await request({
                        // 这里记得改回去，在校外无法用校内服务器
                        url: tool.url,
                        // url: 'http://localhost:8302',
                        method: 'POST',
                        // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                        headers: {
                            'Content-Type': 'text/plain'
                        },
                        data: conversionObj
                    })
                    // Convert the response data to a Blob
                    const blob = new Blob([ConversionRes.testsuiteOfConversion], { type: 'text/plain' });

                    // Create a link element
                    const downloadLink = document.createElement('a');
                    downloadLink.href = window.URL.createObjectURL(blob);

                    // Set the download attribute and filename
                    downloadLink.download = currentTestSuite.currentTestSuites.testsuitesname + '_' + tool.value + '_result.txt'

                    // Append the link to the document
                    document.body.appendChild(downloadLink);

                    // Trigger a click on the link to start the download
                    downloadLink.click();

                    // Remove the link from the document
                    document.body.removeChild(downloadLink);

                    ElNotification({
                        title: 'Convert Success!',
                        type: 'success',

                    })

                    console.log("ConversionRes", ConversionRes)
                    inAPIcall.value = false
                    dialogFormVisibleNewConversion.value = false

                }
                catch (err) {
                    ElNotification({
                        title: 'Convert fail!',
                        type: 'error',
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewConversion.value = false
                }
            }
        }

    }


    if (AlgorithmChosedConversion.value == 'JUnit Test Plan') {
        conversionObj.functionHead = functionHead.value
        conversionObj.functionBody = functionBody.value
        conversionObj = JSON.stringify(conversionObj)

        for (const tool of AlgorithmOptionsConversion) {
            if (tool.value == AlgorithmChosedConversion.value) {
                try {
                    const ConversionRes = await request({
                        // 这里记得改回去，在校外无法用校内服务器
                        url: tool.url,
                        // url: 'http://localhost:8302',
                        method: 'POST',
                        // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                        headers: {
                            'Content-Type': 'text/plain'
                        },
                        data: conversionObj
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewConversion.value = false
                    // Convert the response data to a Blob
                    const blob = new Blob([ConversionRes.testsuiteOfJUnit], { type: 'text/plain' });

                    // Create a link element
                    const downloadLink = document.createElement('a');
                    downloadLink.href = window.URL.createObjectURL(blob);

                    // Set the download attribute and filename
                    downloadLink.download = currentTestSuite.currentTestSuites.testsuitesname + '_' + tool.value + '_result.txt'

                    // Append the link to the document
                    document.body.appendChild(downloadLink);

                    // Trigger a click on the link to start the download
                    downloadLink.click();

                    // Remove the link from the document
                    document.body.removeChild(downloadLink);
                    ElNotification({
                        title: 'Convert Success!',
                        type: 'success',

                    })

                }
                catch (err) {

                    ElNotification({
                        title: 'Convert fail!',
                        type: 'error',
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewConversion.value = false
                }

            }
        }



    }

}

// --------------------------------------------------------

// -------------------------For Evaluation------------------------------

const dialogFormVisibleNewEvaluation = ref(false)
const showdialogNewEvaluation = () => {
    dialogFormVisibleNewEvaluation.value = true
    dialogformNewTestSuites.modelid = route.query.modelid
}
const AlgorithmChosedEvaluation = ref('')
const AlgorithmOptionsEvaluation = reactive([])
const listAllEvaluationAlgorithm = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "Evaluation" && !tool.title.includes("GUI")) {
            AlgorithmOptionsEvaluation.push({ "value": tool.title, "label": tool.title, "url": tool.url })
        }
    }
}
// 用于画coverage checker的折线图
const lineChart = ref(null);
const confirmNewEvaluation = async () => {
    inAPIcall.value = true
    // 构造发送给evaluation的data对象
    let evaluationObj = {}
    let ParametersAndValues = JSON.parse(currentModel.currentModel.paramsvalues)
    let tempArray = []
    let param_count = 0
    for (let i = 0, len = ParametersAndValues.length; i < len; i++) {
        if (ParametersAndValues[i].Value != '') { tempArray.push(ParametersAndValues[i].Value.split(',').length) }
        if (ParametersAndValues[i].Parameter != '') { param_count = param_count + 1 }
    }


    evaluationObj.strength = currentModel.currentModel.strength
    evaluationObj.parameter = param_count
    evaluationObj.values = JSON.stringify(tempArray)
    evaluationObj.testsuite = JSON.parse(currentTestSuite.currentTestSuites.testsuitescontents).testsuite
    evaluationObj = JSON.stringify(evaluationObj).replace(/"/g, '')

    // 发送请求，对  test suites 得到 evalution
    for (const tool of AlgorithmOptionsEvaluation) {
        if (tool.value == AlgorithmChosedEvaluation.value) {
            try {
                const EvaluationRes = await request({
                    // 这里记得改回去，在校外无法用校内服务器
                    url: tool.url,
                    // url: 'http://localhost:8301',
                    method: 'POST',
                    // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                    headers: {
                        'Content-Type': 'text/plain'
                    },
                    data: evaluationObj
                })
                ElNotification({
                    title: 'Evaluate success!',
                    type: 'success',
                })
                // dialogFormVisibleNewEvaluation.value = false
                inAPIcall.value = false
                console.log("EvaluationRes", EvaluationRes)
                if (AlgorithmChosedEvaluation.value == 'Coverage Checker') {
                    let data = EvaluationRes.coverArray
                    // Convert data to ratio values
                    const ratioData = data.map(value => value / 36);
                    const myChart = echarts.init(lineChart.value);
                    // Define the options for the line chart
                    const options = {
                        tooltip: {
                            trigger: 'axis',
                            formatter: function (params) {
                                const ratio = (params[0].value * 100).toFixed(2) + '%';
                                return `Coverage: ${ratio}`;
                            }
                        },
                        title: {
                            text: 'Changes in Coverage'
                        },
                        xAxis: {
                            type: 'category',
                            data: Array.from({ length: ratioData.length }, (_, i) => i + 1),
                            name: 'Num of Testcase',
                        },
                        yAxis: {
                            type: 'value',
                            min: 0,
                            max: 1,
                            name: 'Ratio of Coverage',
                            axisLabel: {
                                formatter: function (value) {
                                    return (value * 100).toFixed(2) + '%';
                                }
                            }
                        },
                        series: [{
                            type: 'line',
                            data: ratioData,
                            lineStyle: {
                                color: 'green' // Set line color to green
                            },
                        }]
                    };




                    // Set the options to the chart
                    myChart.setOption(options);
                }




            }
            catch (err) {
                console.log("err", err)
                ElNotification({
                    title: 'Evaluate fail!',
                    type: 'error',
                })
                dialogFormVisibleNewEvaluation.value = false
                inAPIcall.value = false


            }
        }
    }

}

// --------------------------Export TestSuite------------------------------
const ExportTestSuite = () => {
    // 将表格数据转换为CSV格式
    const csvContent = generateCSV();
    // 从CSV内容创建Blob
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });

    // 创建用于下载的链接元素
    const downloadLink = document.createElement('a');
    downloadLink.href = window.URL.createObjectURL(blob);
    downloadLink.download = currentTestSuite.currentTestSuites.testsuitesname + '_testSuite.csv'
    // 触发链接上的点击以开始下载
    downloadLink.click();
}
const generateCSV = () => {
    // 从表格数据生成CSV内容
    const header = ['#', ...currentModel.currentModel.PandVOBJ.map(parameter => parameter.Parameter)];
    const rows = result.value.map((row, index) => [index + 1, ...row]);
    const allRows = [header, ...rows];

    return allRows.map(row => row.join(',')).join('\n');
}


// --------------------------------------------------------

const goBack = () => {
    window.history.back();
}


// 监听 currentTestSuite.currentTestSuites.testsuitescontents 的变化
watch(() => currentTestSuite.currentTestSuites.testsuitescontents, (newValue, oldValue) => {
    // console.log("newValue",newValue) 
    // console.log("oldValue",oldValue) 
    // 在值发生变化时执行 getValueFromArrays 函数
    let loadingInstance = ElLoading.service({ fullscreen: true })

    if (typeof newValue != "undefined") {
        getValueFromArrays(currentModel.currentModel.PandVOBJ, newValue);
    }

    loadingInstance.close()

});

const UpdateTestSuiteInfo = async () => {

    // 重新加载模型信息
    await listModelInfoByModelID(currentTestSuite.currentTestSuites.modelid)
    // 重新加载testsuite
    await listAllTestSuitesByModelID([currentModel.currentModel])

    // 更新Testsuite Info区域的信息
    for (let index = 0; index < testSuitesStore.testSuitesList[0].testSuites.length; index++) {
        if (currentTestSuite.currentTestSuites.testsuitesid == testSuitesStore.testSuitesList[0].testSuites[index].testsuitesid) {
            // console.log("相同的 testsuitesid", currentTestSuite.currentTestSuites.testsuitesid)
            for (let key in testSuitesStore.testSuitesList[0].testSuites[index]) {

                if (testSuitesStore.testSuitesList[0].testSuites[index].hasOwnProperty(key)) {
                    currentTestSuite.currentTestSuites[key] = testSuitesStore.testSuitesList[0].testSuites[index][key];
                    // console.log("testSuitesStore.testSuitesList[0].testSuites[index][key]",testSuitesStore.testSuitesList[0].testSuites[index][key])
                    // console.log("currentTestSuite.currentTestSuites[key]",currentTestSuite.currentTestSuites[key])
                }
            }

        }
    }
    // console.log("Generation之后的 currentTestSuite.currentTestSuites",currentTestSuite.currentTestSuites)

    await getValueFromArrays(currentModel.currentModel.PandVOBJ, currentTestSuite.currentTestSuites.testsuitescontents)
}

const getButtonName = (TestActivity) => {
    switch (TestActivity) {
        case 'Reduction':
            if (currentTestSuite.currentTestSuites.reductiontool) {
                return 'Re-Reduction'
            }
            if (!currentTestSuite.currentTestSuites.reductiontool) {
                return 'New Reduction'
            }

            break;

        case 'Prioritisation':
            if (currentTestSuite.currentTestSuites.prioritisationtool) {
                return 'Re-Prioritisation'
            }
            if (!currentTestSuite.currentTestSuites.prioritisationtool) {
                return 'New Prioritisation'
            }
            break;

        default:
            break;
    }

}

onMounted(async () => {

    let loadingInstance = ElLoading.service({ fullscreen: true })
    currentTestSuite.currentTestSuites = {}
    // console.log("testsuitesid", route.query.testsuitesid)
    // console.log("modelid", route.query.modelid)

    //  加载当前模型的 信息 
    await listModelInfoByModelID(route.query.modelid)
    // console.log("currentModel.currentModel", currentModel.currentModel)

    // 加载 Testsuites Table
    await listAllTestSuitesByModelID([currentModel.currentModel])

    // 加载所有生成算法
    await listAllGenerationAlgorithm()

    // 加载所有Prioritisation算法
    await listAllPrioritisationOptions()

    // 加载所有Reduction算法
    await listAllReductionOptions()
    // // 加载所有 Conversion 工具
    // await listAllConversionOptions()
    // // 加载所有 Evaluation 工具
    // await listAllEvaluationAlgorithm()

    // 将 testsuite 转换成表格显示，初始化显示route.query.testsuitesid的testsuite
    if (route.query.testsuitesid) {
        for (let index = 0; index < testSuitesStore.testSuitesList.length; index++) {
            for (let i = 0; i < testSuitesStore.testSuitesList[index].testSuites.length; i++) {
                if (testSuitesStore.testSuitesList[index].testSuites[i].testsuitesid == route.query.testsuitesid) {

                    for (let key in testSuitesStore.testSuitesList[index].testSuites[i]) {
                        if (testSuitesStore.testSuitesList[index].testSuites[i].hasOwnProperty(key)) {
                            currentTestSuite.currentTestSuites[key] = testSuitesStore.testSuitesList[index].testSuites[i][key];
                        }
                    }


                }

            }

        }

        getValueFromArrays(currentModel.currentModel.PandVOBJ, currentTestSuite.currentTestSuites.testsuitescontents)
        console.log("currentTestSuite.currentTestSuites", currentTestSuite.currentTestSuites)
    }
    else {
        currentTestSuite.currentTestSuites = {}
    }
    // 没有选择 TestSuite，TestSuites Table 展开
    if (!currentTestSuite.currentTestSuites.testsuitesname) {
        TestTableListFlag.value = !TestTableListFlag.value
    }

    loadingInstance.close()

})
</script>


<style scoped>
.borderGlow {
    border: 2px solid #2dce89;
    /* 设置边框样式，这里使用蓝色边框作为示例 */
    box-shadow: 0 0 10px #2dce89;
    /* 设置阴影效果，使边框发光 */
}

.line-chart-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
}
</style>
