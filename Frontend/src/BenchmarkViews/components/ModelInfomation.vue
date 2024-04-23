<template>
    <div class="min-height-300 bg-info position-absolute w-100"></div>
    <SideNav></SideNav>
    <main class="main-content position-relative border-radius-lg ">
        <Navbar></Navbar>

        <div class="container-fluid py-4">

            <div class="row">
                <div class="col-12">
                    <div class="card mb-4">
                        <div class="card-body m-3">
                            <div class="row">

                                <div class="row">
                                    <div class="col-2">

                                        <ArgonButton full-width class="mb-2" color="secondary" size="sm" @click="goBack">
                                            <span class="ni ni-bold-left  me-1"> Back </span>
                                        </ArgonButton>

                                    </div>

                                    <div class="col-2">

                                        <ArgonButton full-width class="mb-2" color="success" size="sm"
                                            @click="DownLoadDialogTableVisible = true">
                                            <span class="bi bi-file-earmark-arrow-down  "> Dowload Model </span>
                                        </ArgonButton>

                                    </div>

                                    <el-dialog v-model="DownLoadDialogTableVisible"
                                        title="Select Model Format for downloading">
                                        <el-checkbox-group v-model="chosenModels" @change="handleModelFormatChange">
                                            <el-checkbox v-for="model in ModelFormats" :key="model" :label="model"
                                                :value="model">{{
                                                    model }}</el-checkbox>
                                        </el-checkbox-group>
                                        <p>Note: </p>
                                        <p>1. <b>acts</b> format is used by Tools acts, cagen, ctlog, coffee4j and jcunit.
                                            <b>casa</b> format is used by Tool medici.
                                        </p>
                                        <p>2. <b>casa</b> and <b>jenny</b> format use 2 files to describe CT model
                                            respectively.</p>
                                        <template #footer>
                                            <div class="dialog-footer">

                                                <ArgonButton color="secondary" size="sm" class="me-2"
                                                    @click="DownLoadDialogTableVisible = false">Cancel</ArgonButton>

                                                <ArgonButton color="success" size="sm" @click="DownloadModel"> Download
                                                </ArgonButton>
                                            </div>
                                        </template>
                                    </el-dialog>

                                </div>



                                <div class="col-2">
                                    <h6> Model Name</h6>
                                    <argon-input v-model="currentBenchmarkModel.model.modelname" type="text"
                                        readonly />
                                </div>
                                <div class="col-2">
                                    <h6> Num of Parameters</h6>
                                    <argon-input v-model="currentBenchmarkModel.model.parameter" type="text"
                                        readonly />
                                </div>
                                <div class="col-2">
                                    <h6> Max Value Domain</h6>
                                    <argon-input v-model="currentBenchmarkModel.model.maximumvalue" type="text"
                                        readonly />
                                </div>
                                <div class="col-2">
                                    <h6> Num of Constraints</h6>
                                    <argon-input v-model="currentBenchmarkModel.model.cons" type="text"
                                        readonly />
                                </div>

                                <div class="col-2">
                                    <h6>Benchmark Set</h6>
                                    <argon-input v-model="currentBenchmarkModel.model.benchmarkset" type="text"
                                        readonly />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-10">
                                    <h6> Model Description</h6>
                                    <argon-input v-model="currentBenchmarkModel.model.modeldescription" type="text"
                                        readonly />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <h6> Reference</h6>
                                    <argon-input v-model="currentBenchmarkModel.model.reference" type="text"
                                        readonly />
                                </div>



                                <div class="col-2">
                                    <h6> Reference Year</h6>
                                    <argon-input v-model="currentBenchmarkModel.model.referenceyear" type="text"
                                        readonly />
                                </div>

                                <div class="col-2">
                                    <h6> Reference Research Field</h6>
                                    <span :class="getResearchFieldColor(currentBenchmarkModel.model.field)"> {{
                                        currentBenchmarkModel.model.field }}</span>
                                </div>

                            </div>


                        </div>

                    </div>

                    <div class="row">
                        <div class="col-6">
                            <div class="card mb-4">
                                <div v-if="currentBenchmarkModel.model.valid" class="card-body pb-0 mb-4">
                                    <h5>Distribution of the Valid/Invalid Search Space</h5>
                                    <p class="text-muted">The distribution of search space of Model {{
                                        currentBenchmarkModel.model.modelname }}</p>
                                    <div class="charts">
                                        <div class="echarts" id="ChartModelSpace" ref="ChartModelSpace"
                                            style="height:560%; width:100%"></div>
                                    </div>
                                </div>

                                <div v-else class="card-body pb-0 mb-4">
                                    <el-result style="width:100%;height: 100%;" icon="error"
                                        :title="'Unable to display the distribution of search space of Model ' + '`' + currentBenchmarkModel.model.modelname + '`'"
                                        :sub-title="currentBenchmarkModel.model.modelname + ' is too large/has too many constraints, we can\'t calculate the valid search space of it. '">
                                    </el-result>
                                </div>

                            </div>
                        </div>

                        <div class="col-6">
                            <div class="card mb-4">
                                <div class="card-body pb-0 mb-4">
                                    <div class="row  ">
                                        <div class="col-4 p-0 m-0">
                                            <div style="display:flex" class="align-items-center mb-2">
                                                <div><span>Strength: </span></div>

                                                <div class="col-6"> <el-select v-model="strength" class="m-2" @change="showTable"
                                                        placeholder="Select a covering strength">
                                                        <el-option v-for="item in StrengthOptions" :key="item.value"
                                                            :label="item.label" :value="item.value" />
                                                    </el-select></div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <h6>Size/Time of 9 Greedy tools for generating {{ strength }}-way covering array on
                                            this model </h6>
                                        <el-table :data="tableData" border style="width: 100%">
                                            <el-table-column prop="Tool" label="Tool" />
                                            <el-table-column prop="Size" label="Size" />
                                            <el-table-column prop="Time" label="Time" />
                                        </el-table>
                                    </div>

                                    <div class="row mt-3">
                                        <p style="text-align:left;color:rgb(0, 0, 0);"> <b>`-1`</b> means the tool can't
                                            generate CA within specified time(1000s).
                                        </p>
                                        <p style="text-align:left;color:rgb(0, 0, 0);"> <b>`-2`</b> means the tool itself
                                            crashed.
                                        </p>
                                        <p style="text-align:left;color:rgb(0, 0, 0);"> <b>`-9`</b> means the tool can't
                                            generate CA in limited memory usage(16GB).
                                        </p>
                                    </div>


                                </div>
                            </div>
                        </div>


                    </div>

                </div>
            </div>





            <Foot></Foot>

        </div>
    </main>
</template>

<script  setup>

import { defineProps, reactive, ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import { useRoute, useRouter } from 'vue-router';
import SideNav from './SideNav.vue';
import Navbar from '../../CustomizedComponents/Navbar.vue';
import Foot from '../../CustomizedComponents/Foot.vue';
import { useCurrentPaper } from '../../store/currentPaper'
import ArgonButton from '../../CustomizedComponents/ArgonButton.vue';
import ArgonInput from '../../CustomizedComponents/ArgonInput.vue';
import pinia from '../../store/store'
import { request } from '../../request'
import { ElLoading } from 'element-plus'
import { listModelInfo, getResearchFieldColor, downloadAndZipFiles } from '../commonFunction'
import { useModuleStore } from '../../store/module';
import { ElNotification } from 'element-plus'
import { usePaginationStore } from '../../store/paginationStore'
import { usePaperInfoStore } from '../../store/paperinfoStore'
import { useBenchmarkModelsStore } from '../../store/BenchmarkModelsStore'
import { usecurrentBenchmarkModelStore } from '../../store/currentBenchmarkModelStore'

const currentBenchmarkModel = usecurrentBenchmarkModelStore(pinia)
const BenchmarkModels = useBenchmarkModelsStore(pinia)
const PaginationStore = usePaginationStore(pinia)
const moduleStore = useModuleStore(pinia)
const router = useRouter()
const route = useRoute()

const strength = ref(2)
const StrengthOptions = [
    {
        value: 2,
        label: '2',
    },
    {
        value: 3,
        label: '3',
    },
    {
        value: 4,
        label: '4',
    },
    {
        value: 5,
        label: '5',
    },
    {
        value: 6,
        label: '6',
    },
]

const tableData = reactive([])
const chosenModels = ref([])
const ModelFormats = ['acts', 'casa', 'cithub', 'jenny', 'pict', 'tcases']

const DownLoadDialogTableVisible = ref(false)

const goBack = () => {
    moduleStore.CurrentModuleDetails = BenchmarkModels.searchKeyWords
    window.history.back();
}

const handleModelFormatChange = (modelFormatArray) => {
    // console.log("chosenModels", chosenModels.value)
    // console.log("modelFormatArray", modelFormatArray)

}


const DownloadModel = () => {
    let loadingInstance = ElLoading.service({ fullscreen: true })
    try {
        // 文件地址数组
        let fileUrls = []
        for (let i = 0; i < chosenModels.value.length; i++) {
            let tempUrl = ''
            if (chosenModels.value[i] == 'casa' || chosenModels.value[i] == 'jenny') {
                for (let j = 2; j <= 6; j++) {
                    tempUrl = 'models/' + chosenModels.value[i] + '/' + j + '-way/' + currentBenchmarkModel.model.modelname + '-' + chosenModels.value[i] + '-' + j + '-way' + '.model'
                    fileUrls.push(tempUrl)
                    tempUrl = 'models/' + chosenModels.value[i] + '/' + j + '-way/' + currentBenchmarkModel.model.modelname + '-' + chosenModels.value[i] + '-' + j + '-way' + '.constraint'
                    fileUrls.push(tempUrl)
                }
            }
            else if (chosenModels.value[i] == 'cithub') {

                tempUrl = 'models/' + chosenModels.value[i] + '/' + currentBenchmarkModel.model.modelname + '.model'
                fileUrls.push(tempUrl)
            }
            else {
                console.log("chosenModels.value[i]", chosenModels.value[i])
                tempUrl = 'models/' + chosenModels.value[i] + '/' + currentBenchmarkModel.model.modelname + '-' + chosenModels.value[i] + '.model'
                console.log("tempUrl", tempUrl)
                fileUrls.push(tempUrl)
            }


        }

        downloadAndZipFiles(fileUrls, currentBenchmarkModel.model.modelname)
        DownLoadDialogTableVisible.value = false
        loadingInstance.close()
        ElNotification({
            title: 'We are downloading',
            message: 'Please wait a while....',
            type: 'success',
        })
    } catch (error) {
        console.log("下载错误", error)
        DownLoadDialogTableVisible.value = false
        loadingInstance.close()

    }


}
const showTable = async () => {
    // console.log("模型名字", currentBenchmarkModel.model.modelname)
    // console.log("覆盖强度", strength.value)
    let loadingInstance = ElLoading.service({ fullscreen: true })
    const getModelResultRes = await request({
        url: '/benchmark/getModelResult',
        method: 'POST',
        data: {
            modelname: currentBenchmarkModel.model.modelname,
            strength: strength.value
        }
    })
    tableData.length = 0
    console.log("getModelResultRes", getModelResultRes)
    for (let i = 0; i < getModelResultRes.res.length; i++) {
        if (getModelResultRes.res[i].tool != 'fastca' && getModelResultRes.res[i].tool != 'casa') {
            let tempObj = {
                Tool: '',
                Size: '',
                Time: ''
            }
            tempObj.Tool = getModelResultRes.res[i].tool
            tempObj.Size = getModelResultRes.res[i].sizebest
            tempObj.Time = getModelResultRes.res[i].timebest
            tableData.push(tempObj)
        }


    }

    loadingInstance.close()
}

const ChartModelSpace = ref()
let ChartModelSpaceOption = reactive({
    tooltip: {
        trigger: 'item',
        formatter: function (params) {
            // console.log("params", params)
            let tooltipContent;
            if (params.data.name == 'valid') {
                tooltipContent = '<span>' + "Valid Search Space: " + params.data.originalValue + " <br> Proportion of Raw Space: " + params.percent + "% </span>"
            }
            else {
                tooltipContent = "<span>" + "Invalid Search Space: " + params.data.originalValue + " <br> Proportion of Raw Space: " + params.percent + "% </span>"

            }

            return tooltipContent;
        },
    },
    legend: {
        data: ['valid', 'invalid'], // 修改为包含 'valid' 和 'invalid' 的图例数据
        orient: 'vertical',
        left: 'right',
        textStyle: {
            fontSize: 15
        },
        formatter: function (name) {
            let data = ChartModelSpaceOption.series[0].data;
            for (let i = 0; i < data.length; i++) {
                if (data[i].name === name) {
                    return `${name}: ${data[i].originalValue}`;
                }
            }
        }
    },
    series: [
        {
            label: {
                show: true,
                position: 'outside',
                formatter: '{b}: {d}%', // 标签的格式化字符串，显示数据的名称和占比
                fontSize: 15
            },
            type: 'pie',
            radius: '75%',
            data: [

            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(21,57,199,0.5)'
                }
            }
        }
    ]
})


const initChartModelSpace = async () => {
    try {

        let tempObj = {
            value: '',
            name: '',
            originalValue: ''
        }

        tempObj.name = 'valid'
        tempObj.value = parseFloat(currentBenchmarkModel.model.valid)
        tempObj.originalValue = currentBenchmarkModel.model.valid
        ChartModelSpaceOption.series[0].data.push(tempObj);

        tempObj = {
            value: '',
            name: '',
            originalValue: ''
        }

        tempObj.name = 'invalid'
        tempObj.value = parseFloat(currentBenchmarkModel.model.raw) - parseFloat(currentBenchmarkModel.model.valid)
        tempObj.originalValue = (parseFloat(currentBenchmarkModel.model.raw) - parseFloat(currentBenchmarkModel.model.valid)).toExponential()

        ChartModelSpaceOption.series[0].data.push(tempObj);




        let myChart = echarts.init(ChartModelSpace.value);
        myChart.setOption(ChartModelSpaceOption);
        myChart.resize();

    }
    catch (error) {
        console.error(error);
    }
}


onMounted(async () => {
    let loadingInstance = ElLoading.service({ fullscreen: true })
    // console.log("modelid", route.query.modelid)
    try {
        await listModelInfo("modelid", route.query.modelid)
        await initChartModelSpace()
        await showTable()
        moduleStore.CurrentModuleDetails = currentBenchmarkModel.model.modelname
        loadingInstance.close()
        // console.log("BenchmarkModels.models", BenchmarkModels.models)

    } catch (error) {
        console.log("错误", error)
        loadingInstance.close()

    }


})
</script>


<style scoped></style>
