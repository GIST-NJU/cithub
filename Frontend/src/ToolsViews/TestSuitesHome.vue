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
                            <!-- 模型预览 -->
                            <h3>Model</h3>
                            <el-input disabled v-model="modelPreview" autosize type="textarea" placeholder="Please input" />
<!-- 

                            <h3>Test suites List</h3>
                            <p class="text-muted text-sm mb-0"> test suites
                                found in Model </p> -->
                        </div>
                        <!-- <div style="margin: 30px 0px 0px 10px;">
                        </div> -->
                        <div class="card-body px-0 pt-0 pb-2">
                            <TestSuitesTable :testSuitesResArray="testSuitesStore.testSuitesList"></TestSuitesTable>

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
import { request } from '../request';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import SideNav from './components/SideNav.vue'
import TestSuitesTable from './components/TestSuitesTable.vue'
import { useUserStore } from '../store/userStore';
import { usePaperInfoStore } from '../store/paperinfoStore';
import { useProjectsStore } from '../store/projectsStore'
import { useModelsStore } from '../store/modelsStore'
import { useCurrentProject } from '../store/currentProject';
import { ElNotification } from 'element-plus'
import { useTestSuitesStore } from '../store/testSuitesStore'
const testSuitesStore = useTestSuitesStore()
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
    modelObject.system = model.modelname
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

}




onMounted(() => {

    // 加载模型到预览区域
    listModelInfoByModelID()
    // 加载所有ca
})
</script>


<style scoped></style>
