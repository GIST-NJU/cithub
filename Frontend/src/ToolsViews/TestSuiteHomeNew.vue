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
                    <div class="card">
                        <div class="card-header pb-0">
                            <h3 class="text-center">Model info</h3>
                            <div class="row">
                                <div class="col-md-2">
                                    <h6> Model Name</h6>
                                    <argon-input v-model="currentModel.currentModel.modelname" type="text"
                                        :disabled="'disabled'" />
                                </div>

                                <div class="col-md-2">
                                    <h6> Num of params</h6>
                                    <argon-input v-model="currentModel.currentModel.NumofParams" type="text"
                                        :disabled="'disabled'" />
                                </div>

                                <div class="col-md-2">
                                    <h6> Num of Cons</h6>
                                    <argon-input v-model="currentModel.currentModel.NumofCons" type="text"
                                        :disabled="'disabled'" />
                                </div>
                                <div class="col-md-12">
                                    <h6> Model Description</h6>
                                    <argon-input type="text" v-model="model.modeldescriptions" :disabled="'disabled'" />
                                </div>

                                <div class="col-3">
                                    <ArgonButton :class="{ 'borderGlow': !AbstractModelPreviewFlag }"
                                        :color="AbstractModelPreviewFlag ? 'success' : 'danger'"
                                        @click="AbstractModelPreviewFlag = !AbstractModelPreviewFlag">
                                        {{ AbstractModelPreviewFlag ? 'Show Model' : 'Hide Model' }}
                                    </ArgonButton>


                                </div>
                                <div class="col-3">
                                    <ArgonButton :class="{ 'borderGlow': !CithubModelPreviewFlag }"
                                        :color="CithubModelPreviewFlag ? 'success' : 'danger'"
                                        @click="CithubModelPreviewFlag = !CithubModelPreviewFlag">
                                        {{ CithubModelPreviewFlag ? 'Show Cithub Model' : 'Hide Cithub Model' }}
                                    </ArgonButton>

                                </div>

                                <div class="col-12" v-auto-animate>
                                    <!-- 抽象模型预览 -->
                                    <div v-if="!AbstractModelPreviewFlag">
                                        <hr class="my-3 horizontal white" />
                                        <h5>Parameters and Values</h5>
                                        <el-input disabled v-model="AbstractModelParamsValuesPreview" autosize
                                            type="textarea" placeholder="Please input" />
                                        <hr class="my-3 horizontal white" />
                                        <h5>Constraints (Forbidden Tuples)</h5>
                                        <el-input disabled v-model="AbstractModelConstraintsPreview" autosize
                                            type="textarea" placeholder="Please input" />
                                    </div>
                                    <!-- Cithub模型预览 -->
                                    <div v-if="!CithubModelPreviewFlag">
                                        <hr class="my-3 horizontal white" />
                                        <el-input disabled v-model="currentModel.currentModel.modelCithub" autosize
                                            type="textarea" placeholder="Please input" />
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div style="width:100%;height:25px"></div>
                    </div>
                    <hr class="my-3 horizontal dark" />
                    <div class="card" v-if="currentTestSuite.currentTestSuites">
                        <div class="card-header pb-0">
                            <div class="row">
                                <h3 class="text-center">TestSuite info</h3>

                                <div class="col-md-3">
                                    <h6> TestSuite Name</h6>
                                    <argon-input v-model="currentTestSuite.currentTestSuites.testsuitesname" type="text"
                                        :disabled="'disabled'" />
                                </div>

                                <div class="col-md-2">
                                    <h6> Algorithm</h6>
                                    <argon-input v-model="currentTestSuite.currentTestSuites.algorithm" type="text"
                                        :disabled="'disabled'" />
                                </div>

                                <div class="col-md-2">
                                    <h6> Strength</h6>
                                    <argon-input v-model="currentModel.currentModel.strength" type="text"
                                        :disabled="'disabled'" />
                                </div>

                                <div class="col-md-2">
                                    <h6> Size</h6>
                                    <argon-input v-model="currentTestSuite.currentTestSuites.size" type="text"
                                        :disabled="'disabled'" />
                                </div>

                                <div class="col-md-2">
                                    <h6> Time</h6>
                                    <argon-input v-model="currentTestSuite.currentTestSuites.time" type="text"
                                        :disabled="'disabled'" />
                                </div>


                                <div class="col-md-12">
                                    <h6> Model Description</h6>
                                    <argon-input type="text"
                                        v-model="currentTestSuite.currentTestSuites.testsuitesdescriptions"
                                        :disabled="'disabled'" />
                                </div>
                            </div>
                            <hr class="my-3 horizontal white" />
                            <div class="row">

                                <!-- show testsuite in the form of table -->
                                <div class="col-3">
                                    <ArgonButton :class="{ 'borderGlow': !TestSuitePreviewFlag }"
                                        :color="TestSuitePreviewFlag ? 'success' : 'danger'"
                                        @click="TestSuitePreviewFlag = !TestSuitePreviewFlag">
                                        {{ TestSuitePreviewFlag ? 'Show TestSuite' : 'Hide TestSuite' }}
                                    </ArgonButton>
                                </div>

                                <!-- Regenerate test suite -->
                                <div class="col-3">
                                    <ArgonButton :class="{ 'borderGlow': !TestSuitePreviewFlag }" color="success"
                                        @click="showdialogNew">Regeneration
                                    </ArgonButton>
                                </div>

                                <!-- prioritize test suite  -->
                                <div class="col-3">
                                    <ArgonButton :class="{ 'borderGlow': !TestSuitePreviewFlag }" color="success"
                                        @click="showdialogNewPrioritization">Prioritisation
                                    </ArgonButton>
                                </div>

                                <!-- reduct test suite  -->
                                <div class="col-3">
                                    <ArgonButton :class="{ 'borderGlow': !TestSuitePreviewFlag }" color="success"
                                        @click="showdialogNewReduction">Reduction
                                    </ArgonButton>
                                </div>
                            </div>

                            <hr class="my-3 horizontal white" />


                            <div v-auto-animate>

                                <div v-if="!TestSuitePreviewFlag">
                                    <hr class="my-3 horizontal white" />


                                    <div class="row">
                                        <div class="col-12">
                                            <div class="d-flex justify-content-between align-items-center mb-3">
                                                <!-- Caption -->
                                                <div>
                                                    <h5 class="mb-0">Below are the testsuite generated for Model `{{
                                                        currentModel.currentModel.modelname }}`</h5>
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
                                                        <li><a class="dropdown-item"
                                                                @click="showdialogNewConversion">Convert into Test Plan</a>
                                                        </li>
                                                        <li><a class="dropdown-item"
                                                                @click="showdialogNewEvaluation">Evaluation</a></li>
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
                                                        <td v-for="(value, colIndex) in row" :key="colIndex"
                                                            class="text-center">
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
                    </div>


                    <div class="card" v-if="!currentTestSuite.currentTestSuites">


                        <div class="card-header pb-0">
                            <div class="row">
                                <h6 class="text-center">There is no testsuite for Model `{{
                                    currentModel.currentModel.modelname }}` now, Please click the button below to generate
                                    one.</h6>
                                <ArgonButton color="success" @click="showdialogNew">Generation
                                </ArgonButton>
                                <hr class="my-3 horizontal white" />
                            </div>
                        </div>

                    </div>

                    <!-- New test suites -->
                    <el-dialog v-model="dialogFormVisibleNew" title="Generate a new testSuite based on current model">
                        <el-form :model="dialogformNewTestSuites" label-position="right" label-width="170px">

                            <el-form-item label="Test Suite Name:">
                                <el-input v-model="dialogformNewTestSuites.testsuitesname" />
                            </el-form-item>
                            <el-form-item label="Test Suite Description:">
                                <el-input autosize type="textarea"
                                    v-model="dialogformNewTestSuites.testsuitesdescriptions" />
                            </el-form-item>
                            <el-form-item label="Strength: ">
                                <el-select clearable style="margin: 0 0 0 0;padding: 0;width: 250px" v-model="strength"
                                    class="m-2" placeholder="Select a covering strength">
                                    <el-option v-for="item in StrengthOptions" :key="item.value" :label="item.label"
                                        :value="item.value" />
                                </el-select>
                            </el-form-item>
                            <el-form-item label="Algorithm: ">
                                <el-select v-model="AlgorithmChosed" class="m-2"
                                    placeholder="Select an Algorithm for generating">
                                    <el-option v-for="item in AlgorithmOptions" :key="item.value" :label="item.label"
                                        :value="item.value" />
                                </el-select>
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
                        <el-form :model="dialogformNewTestSuites" label-position="left" label-width="80px">
                            <el-form-item label="Algorithm:">
                                <el-select v-model="AlgorithmChosedPrioritization" class="m-2"
                                    placeholder="Select a tool for prioritising current testsuite." clearable>
                                    <el-option v-for="item in AlgorithmOptionsPrioritization" :key="item.value"
                                        :label="item.label" :value="item.value" />
                                </el-select>
                            </el-form-item>


                            <div v-if="AlgorithmChosedPrioritization == 'SortArray'">
                                <el-form-item label="Weight:">
                                    <el-input v-model="Weight" type="textarea" :autosize="{ minRows: 4 }" placeholder="Note:
1.Please input the Weight of parameters using `,`  to split each Weight, eg: 3,1,2 for three parameters.
2.For each parameter, you need to input a corresponding weight.eg: three parameters have to input three weight
                                    ">
                                    </el-input>
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
                    <el-dialog v-model="dialogFormVisibleNewReduction" title="New Reduction:">
                        <el-form :model="dialogformNewTestSuites" label-position="right" label-width="140px">
                            <el-form-item label="Algorithm:">
                                <el-select v-model="AlgorithmChosedReduction" class="m-2"
                                    placeholder="Select a tool for reducting current testsuite." clearable>
                                    <el-option v-for="item in AlgorithmOptionsReduction" :key="item.value"
                                        :label="item.label" :value="item.value" />
                                </el-select>
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
                                    <el-option v-for="item in AlgorithmOptionsConversion" :key="item.value"
                                        :label="item.label" :value="item.value" />
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
                            <el-form-item label="Algorithm: ">
                                <el-select v-model="AlgorithmChosedEvaluation" class="m-2"
                                    placeholder="Select an Algorithm for evaluating">
                                    <el-option v-for="item in AlgorithmOptionsEvaluation" :key="item.value"
                                        :label="item.label" :value="item.value" />
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
            </div>
            <Foot></Foot>
        </div>
    </main>
</template>

<script  setup>
import { useRoute, useRouter } from 'vue-router';
import { onMounted, reactive, ref, computed, watch } from 'vue';
import { request } from '../request';
import Foot from '../CustomizedComponents/Foot.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import SideNav from './components/SideNav.vue'
import TestSuitesTable from './components/TestSuitesTable.vue'
import ArgonBadge from '../CustomizedComponents/ArgonBadge.vue';
import ArgonButton from '../CustomizedComponents/ArgonButton.vue'
import pinia from '../store/store'
import { useUserStore } from '../store/userStore';
import { usePaperInfoStore } from '../store/paperinfoStore';
import { useProjectsStore } from '../store/projectsStore'
import { useModelsStore } from '../store/modelsStore'
import { useCurrentProject } from '../store/currentProject';
import ArgonInput from '../CustomizedComponents/ArgonInput.vue';
import { useCurrentModel } from '../store/currentModel'
import { ElNotification } from 'element-plus'
import { useTestSuitesStore } from '../store/testSuitesStore'
import { useCurrentTestSuitesStore } from '../store/currentTestSuite'
import toolsInfo from "../CustomizedComponents/tools_info.json";
import * as echarts from 'echarts'
const testSuitesStore = useTestSuitesStore(pinia)
const currentModel = useCurrentModel(pinia)
const currentTestSuite = useCurrentTestSuitesStore(pinia)
const router = useRouter();
const route = useRoute()
const modelPreview = ref('')
const maxValueDomain = ref(0)
const consPreview = ref('')
const consArray = ref([])
const model = reactive({})
const modelObject = reactive({
    system: "",
    strength: '',
    parameter: '',
    values: '',
    constraints: []

})
const tableData = ref([]);
const constraintsTableData = ref()

const findPosition = (parameterName, value) => {
    for (let i = 0; i < tableData.value.length; i++) {
        const parameter = tableData.value[i].Parameter;
        const values = tableData.value[i].Value.split(',');

        if (parameter === parameterName) {
            const valueIndex = values.indexOf(value);
            if (valueIndex !== -1) {
                return { parameterIndex: i, valueIndex };
            }
        }
    }

    return { parameterIndex: -1, valueIndex: -1 };
}

const listModelInfoByModelID = async () => {
    try {
        const res = await request({
            url: '/tools/models/info/' + route.query.modelid,
            method: 'POST'
        });

        model.modelid = res.models.modelid
        model.strength = res.models.strength
        model.modelname = res.models.modelname
        model.modeldescriptions = res.models.modeldescriptions
        model.paramsvalues = res.models.paramsvalues
        model.cons = res.models.cons
        model.lastupdatedtime = res.models.lastupdatedtime
        model.createdtime = res.models.createdtime

        currentModel.currentModel.modelid = res.models.modelid
        currentModel.currentModel.strength = res.models.strength
        currentModel.currentModel.modelname = res.models.modelname
        currentModel.currentModel.modeldescriptions = res.models.modeldescriptions
        currentModel.currentModel.paramsvalues = res.models.paramsvalues
        currentModel.currentModel.cons = res.models.cons
        currentModel.currentModel.lastupdatedtime = res.models.lastupdatedtime
        currentModel.currentModel.createdtime = res.models.createdtime
        loadModel();
    } catch (err) {
        console.error(err);
    }
}

const loadModel = () => {
    // 解析参数和参数取值的Json字符串
    const parsedData = JSON.parse(model.paramsvalues)
    // 移除 row_index 属性
    const tableDataTmp = parsedData.map(item => {
        const { row_index, ...rest } = item;
        return rest;
    });

    // 加载参数和参数取值
    tableData.value = tableDataTmp;

    // 加载约束table
    let tempArray = []
    let param_count = 0
    for (let i = 0, len = tableData.value.length; i < len; i++) {
        if (tableData.value[i].Value != '') { tempArray.push(tableData.value[i].Value.split(',').length) }
        if (tableData.value[i].Parameter != '') { param_count = param_count + 1 }

    }
    // 统计模型基本数据

    if (model.modelname) { modelObject.system = model.modelname }

    modelObject.strength = model.strength
    modelObject.parameter = param_count
    modelObject.values = JSON.stringify(tempArray)


    // 对 tableData 的数据做处理，转成被constraintsTableData
    let tempArrayCons = []
    for (let i = 0, len = tableData.value.length; i < len; i++) {
        let tempObj = {}
        tempObj.Parameter = tableData.value[i].Parameter
        tempObj.valueArray = tableData.value[i].Value.split(',').map((value, index) => ({
            [`Value${index + 1}`]: value
        }));
        tempObj.ValueDomain = tempObj.valueArray.length
        tempArrayCons.push(tempObj)

    }
    constraintsTableData.value = tempArrayCons


    // 得到最大的ValueDomain
    const maxDomain = constraintsTableData.value.reduce((max, item) => {
        return Math.max(max, item.ValueDomain);
    }, -Infinity);


    maxValueDomain.value = maxDomain


    // 将cons显示在cons Preview区域
    consArray.value = JSON.parse(model.cons)
    consPreview.value = JSON.stringify(JSON.parse(model.cons), null, 6).replace(/"/g, '')
    // 对每个约束进行处理，将其转换成 '参数索引'/'取值索引' 的形式
    let consArrayToAPI = []
    for (const constraint of consArray.value) {
        let consArrayTemp = []
        // 遍历元素中的键值对
        for (const key in constraint) {
            if (Object.hasOwnProperty.call(constraint, key)) {
                const elements = constraint[key];
                // elements 是一个数组，包含了多个键值对
                for (const element of elements) {
                    // 在这里访问 Parameter 和 Value
                    const parameter = element.Parameter;
                    const value = element.Value;

                    // 这里可以使用 parameter 和 value 进行其他操作
                    // console.log(`Key: ${key}, Parameter: ${parameter}, Value: ${value}`);
                    let constempObj = findPosition(parameter, value)
                    let consString = `\'${constempObj.parameterIndex}/${constempObj.valueIndex}\'`
                    consArrayTemp.push(consString)
                }
            }
            consArrayToAPI.push(consArrayTemp)
        }
    }
    modelObject.constraints = consArrayToAPI

    // 将模型数据显示在Model Preview区域
    modelPreview.value = JSON.stringify(modelObject, null, 6).replace(/"/g, '')
    currentModel.currentModel.modelCithub = JSON.stringify(modelObject, null, 6).replace(/"/g, '')


}
const listAllTestSuitesByModelID = async () => {
    try {
        const testSuitesRes = await request({
            method: "POST",
            url: '/tools/testSuites/listTestSuitesByModelID',
            data: {
                modelid: route.query.modelid
            }
        });
        testSuitesStore.testSuitesList = testSuitesRes.TestSuites

        for (let i = 0; i < testSuitesStore.testSuitesList.length; i++) {

            const timestamp_created = testSuitesStore.testSuitesList[i].createdtime
            const timestamp_lastupdated = testSuitesStore.testSuitesList[i].lastupdatedtime
            const dateObject_created = new Date(timestamp_created);
            const dateObject_lastupdated = new Date(timestamp_lastupdated);

            // 获取可读的时间字符串
            testSuitesStore.testSuitesList[i].createdtimeFortmat = dateObject_created.toLocaleString();
            testSuitesStore.testSuitesList[i].lastupdatedtimeFortmat = dateObject_lastupdated.toLocaleString();
            testSuitesStore.testSuitesList[i].testsuite = JSON.parse(testSuitesStore.testSuitesList[i].testsuitescontents).testsuite
            testSuitesStore.testSuitesList[i].time = testSuitesStore.testSuitesList[i].time
            testSuitesStore.testSuitesList[i].size = testSuitesStore.testSuitesList[i].size
        }

        currentTestSuite.currentTestSuites = testSuitesStore.testSuitesList[0]
        strength.value = currentModel.currentModel.strength
        dialogformNewTestSuites.testsuitesname = currentTestSuite.currentTestSuites.testsuitesname
        dialogformNewTestSuites.testsuitesdescriptions = currentTestSuite.currentTestSuites.testsuitesdescriptions



    } catch (error) {
        console.error("发生错误", error);
    }

};

const AbstractModelPreviewFlag = ref(true)
const AbstractModelParamsValuesPreview = ref('')
const AbstractModelConstraintsPreview = ref('')
const CithubModelPreviewFlag = ref(true)
const TestSuitePreviewFlag = ref(true)


// result is used for testsuite table display
const result = ref([]);
const getValueFromArrays = (objArray, indexArray) => {
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
        value: '1',
        label: '1',
    },
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
watch(strength, (newStrength, oldStrength) => {

    // console.log("newStrength",newStrength,"strength.value",strength.value)
    model.strength = strength.value
    loadModel()


}, { deep: true });

const inAPIcall = ref(false)
// ---------------for generation-----------------------
const AlgorithmChosed = ref('')
const AlgorithmOptions = reactive([])
const listAllGenerationAlgorithm = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "Generation" && !tool.title.includes("GUI")) {
            AlgorithmOptions.push({ "value": tool.title, "label": tool.title, "url": tool.url })
        }
    }
    // console.log("AlgorithmOptions",AlgorithmOptions)
}
const dialogFormVisibleNew = ref(false)
const dialogformNewTestSuites = reactive({
    testsuitesname: '',
    testsuitesdescriptions: '',
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
                    const TestSuitesRes = await request({
                        // 这里记得改回去，在校外无法用校内服务器
                        url: tool.url,
                        // url: 'http://localhost:8300',
                        method: 'POST',
                        // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                        headers: {
                            'Content-Type': 'text/plain'
                        },
                        data: currentModel.currentModel.modelCithub
                    })


                    const newTestSuitesRes = await request({
                        url: "/tools/testSuites/NewTestSuites",
                        method: "POST",
                        data:
                        {
                            testsuitesname: dialogformNewTestSuites.testsuitesname,
                            testsuitesdescriptions: dialogformNewTestSuites.testsuitesdescriptions,
                            lastupdatedtime: dialogformNewTestSuites.lastupdatedtime,
                            createdtime: dialogformNewTestSuites.createdtime,
                            modelid: dialogformNewTestSuites.modelid,
                            testsuitescontents: JSON.stringify(TestSuitesRes),
                            time: TestSuitesRes.time,
                            size: TestSuitesRes.size,
                            algorithm: AlgorithmChosed.value,
                            strength: strength.value
                        }
                    })
                    // console.log("newTestSuitesRes", newTestSuitesRes)

                    if (newTestSuitesRes.NewStatus == 'success!') {

                        // 重新加载模型信息
                        await listModelInfoByModelID()
                        // 重新加载testsuite
                        await listAllTestSuitesByModelID()
                        // 将 ca 转换成表格显示
                        getValueFromArrays(currentModel.currentModel.PandVOBJ, currentTestSuite.currentTestSuites.testsuite)
                        ElNotification({
                            title: 'Generate Success!',
                            type: 'success',
                            message: 'Strength ' + strength.value + ' test suite' + ' by ' + AlgorithmChosed.value + ' generated success! Please check the results.',
                            
                        })
                        inAPIcall.value = false
                        dialogFormVisibleNew.value = false
                        if (TestSuitePreviewFlag.value == true) {
                            TestSuitePreviewFlag.value = !TestSuitePreviewFlag.value
                        }

                    }
                    else {
                        ElNotification({
                            title: 'Generate fail!',
                            type: 'error',
                        })
                        inAPIcall.value = false
                        dialogFormVisibleNew.value = false


                    }
                }
                catch (err) {
                    ElNotification({
                        title: 'Generate fail!',
                        type: 'error',
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNew.value = false


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
const listAllPrioritisationOptions = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "Prioritisation") {
            AlgorithmOptionsPrioritization.push({ "value": tool.title, "label": tool.title, "url": tool.url })
        }
    }
}
const Weight = ref('')
const confirmNewPrioritisation = async () => {
    inAPIcall.value = true
    // 获取当前时刻的Date对象
    const currentDate = new Date();
    dialogformNewTestSuites.lastupdatedtime = currentDate

    // 构造发送给Prioritisation的data对象
    let Obj = {}
    let testsuite = JSON.parse(currentTestSuite.currentTestSuites.testsuitescontents).testsuite
    Obj.testsuite = testsuite
    Obj.parameter = JSON.parse(currentModel.currentModel.paramsvalues).length

    let WeightArray = Weight.value.split(',').map(Number)

    Obj.weight = WeightArray
    Obj = JSON.stringify(Obj)
    for (const tool of AlgorithmOptionsPrioritization) {
        if (tool.value == AlgorithmChosedPrioritization.value) {
            try {
                const PrioritisationRes = await request({
                    // 这里记得改回去，在校外无法用校内服务器
                    url: tool.url,
                    // url: 'http://localhost:8304',
                    method: 'POST',
                    // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                    headers: {
                        'Content-Type': 'text/plain'
                    },
                    data: Obj
                })
                console.log("PrioritisationRes.testsuite", PrioritisationRes.testsuite)

                const SavePrioritisationRes = await request({
                    url: "/tools/testSuites/NewTestSuites",
                    method: "POST",
                    data:
                    {

                        lastupdatedtime: dialogformNewTestSuites.lastupdatedtime,
                        modelid: dialogformNewTestSuites.modelid,
                        testsuitescontents: JSON.stringify({ "testsuite": PrioritisationRes.testsuite }),
                        time: PrioritisationRes.time,
                        algorithm: AlgorithmChosedPrioritization.value,

                    }
                })

                if (SavePrioritisationRes.NewStatus == 'success!') {

                    // 重新加载模型信息
                    await listModelInfoByModelID()
                    // 重新加载testsuite
                    await listAllTestSuitesByModelID()
                    // 将 ca 转换成表格显示
                    getValueFromArrays(currentModel.currentModel.PandVOBJ, currentTestSuite.currentTestSuites.testsuite)

                    ElNotification({
                        title: 'Prioritize Success!',
                        type: 'success',
                        message: 'Strength ' + strength.value + ' test suite' + ' by ' + AlgorithmChosedPrioritization.value + ' prioritized success! Please check the results.',
                        

                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewPrioritization.value = false
                    if (TestSuitePreviewFlag.value == true) {
                        TestSuitePreviewFlag.value = !TestSuitePreviewFlag.value
                    }
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
const listAllReductionOptions = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "SelectionReduction") {
            AlgorithmOptionsReduction.push({ "value": tool.title, "label": tool.title, "url": tool.url })
        }
    }
}

const confirmNewReduction = async () => {
    inAPIcall.value = true
    // 获取当前时刻的Date对象
    const currentDate = new Date();
    dialogformNewTestSuites.lastupdatedtime = currentDate
    // 构造发送给Reduction的data对象
    let Obj = {}
    let testsuite = JSON.parse(currentTestSuite.currentTestSuites.testsuitescontents).testsuite
    Obj.testsuite = testsuite

    const parsedData = JSON.parse(currentModel.currentModel.paramsvalues)
    // 移除 row_index 属性
    const tableDataTmp = parsedData.map(item => {
        const { row_index, ...rest } = item;
        return rest;
    });
    let tempArray = []
    let param_count = 0
    for (let i = 0, len = tableDataTmp.length; i < len; i++) {
        if (tableDataTmp[i].Value != '') { tempArray.push(tableDataTmp[i].Value.split(',').length) }
        if (tableDataTmp[i].Parameter != '') { param_count = param_count + 1 }
    }
    Obj.parameter = param_count
    Obj.values = tempArray.map(Number)
    Obj.strength = strength.value
    Obj = JSON.stringify(Obj)

    for (const tool of AlgorithmOptionsReduction) {
        if (tool.value == AlgorithmChosedReduction.value) {

            try {
                const ReductionRes = await request({
                    // 这里记得改回去，在校外无法用校内服务器
                    url: tool.url,
                    // url: 'http://localhost:8305',
                    method: 'POST',
                    // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                    headers: {
                        'Content-Type': 'text/plain'
                    },
                    data: Obj
                })
                console.log("ReductionRes", ReductionRes)
                const newReductionRes = await request({
                    url: "/tools/testSuites/NewTestSuites",
                    method: "POST",
                    data:
                    {
                        lastupdatedtime: dialogformNewTestSuites.lastupdatedtime,
                        modelid: dialogformNewTestSuites.modelid,
                        testsuitescontents: JSON.stringify(ReductionRes),
                        time: ReductionRes.time,
                        size: ReductionRes.size,
                        algorithm: AlgorithmChosedReduction.value,
                    }
                })

                if (newReductionRes.NewStatus == 'success!') {

                    // 重新加载模型信息
                    await listModelInfoByModelID()
                    // 重新加载testsuite
                    await listAllTestSuitesByModelID()
                    // 将 ca 转换成表格显示
                    getValueFromArrays(currentModel.currentModel.PandVOBJ, currentTestSuite.currentTestSuites.testsuite)


                    ElNotification({
                        title: 'Reduct Success!',
                        type: 'success',
                        message: 'Strength ' + strength.value + ' test suite' + ' by ' + AlgorithmChosedReduction.value + ' reducted success! Please check the results.',
                        
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewReduction.value = false
                    if (TestSuitePreviewFlag.value == true) {
                        TestSuitePreviewFlag.value = !TestSuitePreviewFlag.value
                    }

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

onMounted(async () => {
    // console.log("TestSuiteHomeNew", "currentModel.currentModel", currentModel.currentModel)
    if (currentModel.currentModel.modelid) {
        const parsedData = JSON.parse(currentModel.currentModel.paramsvalues)
        // 移除 row_index 属性
        const tableDataTmp = parsedData.map(item => {
            const { row_index, ...rest } = item;
            return rest;
        });
        AbstractModelParamsValuesPreview.value = JSON.stringify(tableDataTmp, null, 6)
        AbstractModelConstraintsPreview.value = JSON.stringify(JSON.parse(currentModel.currentModel.cons), null, 6)
        // 加载模型到预览区域
        await listModelInfoByModelID()
        // 加载测试用例集
        await listAllTestSuitesByModelID()
        // 加载所有生成算法
        await listAllGenerationAlgorithm()
        // 加载所有Prioritisation算法
        await listAllPrioritisationOptions()
        // 加载所有Reduction算法
        await listAllReductionOptions()
        // 加载所有 Conversion 工具
        await listAllConversionOptions()
        // 加载所有 Evaluation 工具
        await listAllEvaluationAlgorithm()

        // 将 ca 转换成表格显示
        getValueFromArrays(currentModel.currentModel.PandVOBJ, currentTestSuite.currentTestSuites.testsuite)



    }
    else {

        ElNotification({
            title: 'Need to Choose a Model first',
            message: 'Please choose a Model to continue.',
            type: 'error',
        })
        router.push({ name: 'modelsHome' })
    }

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
