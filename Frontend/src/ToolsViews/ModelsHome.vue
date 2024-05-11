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
                            <h4>Complete Models List</h4>
                            <h6>{{ modelStore.modelsList.length }} Models Found</h6>

                        </div>

                        <!-- New Model  -->
                        <div style="width:100%;margin-bottom: 5px;text-align: center;">
                            <ArgonButton style="float: right;margin-right: 3%;" color="success" variant="gradient"
                                @click="showdialogNew">
                                <span class="ni ni-fat-add ni-lg me-1" />
                                New Model
                            </ArgonButton>
                        </div>

                        <el-dialog v-model="dialogFormVisibleNew" title="New Model">
                            <el-form :model="dialogformNewModel" label-position="right" label-width="150px">

                                <el-form-item label="Model Name:">
                                    <el-input v-model="dialogformNewModel.modelname" />
                                </el-form-item>

                                <el-form-item label="Model Description:">
                                    <el-input autosize type="textarea" v-model="dialogformNewModel.modeldescriptions" />
                                </el-form-item>

                            </el-form>


                            <hr class="my-3 horizontal white" />

                            <p>Cithub currently support 3 methodes of modelling to create CIT model</p>
                            <p>Please choose one of the following modelling method to
                                <ArgonButton class="mx-2" size="sm" color="success"> New Model </ArgonButton>
                                .
                            </p>
                            <!-- 手动建模 -->

                            <div style="display:flex;flex-direction: row;justify-content: space-around;">
                                <div v-bind:class="{ 'selected-category': selectedModellingType === 'Manual' }"
                                    class="category" @click="chooseModellingType('Manual')">
                                    <el-card class="box-card" style="height: 100%;">
                                        <template #header>
                                            <div class="card-header" style="padding: 0px 0px 0px 0px;">
                                                <h6>Manual Modelling</h6>
                                            </div>
                                        </template>
                                        <div class="categoryDes">Modelling by provided user-friendly GUI interface to
                                            create
                                            Cithub Format model.
                                        </div>
                                    </el-card>
                                </div>

                                <!-- 导入已有模型 -->

                                <div v-bind:class="{ 'selected-category': selectedModellingType === 'Imported' }"
                                    class="category" @click="chooseModellingType('Imported')">
                                    <el-card class="box-card" style="height: 100%;">
                                        <template #header>
                                            <div class="card-header" style="padding: 0px 0px 0px 0px;">
                                                <h6>Import Existed Model</h6>
                                            </div>
                                        </template>
                                        <div class="categoryDes">Modelling by transfering existed models of
                                            ACTS/CASA/PICT/CtWedge format to Cithub Format model.
                                        </div>
                                    </el-card>
                                </div>

                                <!-- LLM 建模 -->
                                <div v-bind:class="{ 'selected-category': selectedModellingType === 'LLM' }"
                                    class="category" @click="chooseModellingType('LLM')">
                                    <el-card class="box-card" style="height: 100%;">
                                        <template #header>
                                            <div class="card-header" style="padding: 0px 0px 0px 0px;">
                                                <h6>LLM Modelling</h6>

                                            </div>
                                        </template>
                                        <div class="categoryDes">Modelling by large language model to assist you to
                                            extract
                                            CIT model from SUT corpus.
                                        </div>
                                    </el-card>
                                </div>
                            </div>
                            <div>
                                <!-- Imported model convert into cithub model -->
                                <el-form v-if="dialogformNewModel.modeltype == 'Imported'" label-position="right"
                                    label-width="150px">
                                    <el-form-item label="Import Model Type: ">
                                        <el-select v-model="ImportModelTypeChosed" class="m-2"
                                            placeholder="Select an tool for import related type of model">
                                            <el-option v-for="item in AlgorithmOptions" :key="item.value"
                                                :label="item.label" :value="item.value" />
                                        </el-select>
                                    </el-form-item>

                                    <!-- Acts format reader -->
                                    <div v-if="ImportModelTypeChosed == 'ACTS Format Reader'">


                                        <el-form-item label="ACTS model: ">
                                            <el-input v-model="ModelConversionForm.file" type="textarea"
                                                :autosize="{ minRows: 10 }" placeholder="Note:
1.Input an ACTS format model, Cithub will convert it into Cithub format.
2.For example, ACTS format model should look like this:

[System]
Name: Phone
[Parameter]
emailViewer(boolean): TRUE, FALSE
textLines(int): 25,26,27,28,29,30
display(enum): 16MC, 8MC, BW
[Constraint]
emailViewer => textLines > 28


">
                                            </el-input>
                                        </el-form-item>


                                    </div>


                                    <!-- PICT format reader -->
                                    <div v-if="ImportModelTypeChosed == 'PICT Format Reader'">



                                        <el-form-item label="PICT model: ">
                                            <el-input v-model="ModelConversionForm.model_file" type="textarea"
                                                :autosize="{ minRows: 10 }" placeholder='Note:
1.Cithub will convert your PICT format model into Cithub format
2.For example, PICT model should look like this:

emailViewer: TRUE, FALSE
textLines: 25, 26, 27, 28, 29, 30
display: 16MC, 8MC, BW
IF [emailViewer] = "TRUE" THEN [textLines] > 29;

'>
                                            </el-input>
                                        </el-form-item>


                                    </div>



                                    <!-- CTWedge format reader -->
                                    <div v-if="ImportModelTypeChosed == 'CTWedge Format Reader'">


                                        <el-form-item label="CTWedge model: ">
                                            <el-input v-model="ModelConversionForm.model_file" type="textarea"
                                                :autosize="{ minRows: 10 }" placeholder='Note:
1.Cithub will convert your CTWedge format model into Cithub format.
2.For example, CTWedge format model should look like this:

Model Phone
Parameters:
Boolean emailViewer;
Range textLines [25 .. 30];
Enumerative display {16MC, 8MC, BW};
end
Constraints:
# emailViewer==true => textLines > 28# 
end

'>
                                            </el-input>
                                        </el-form-item>
                                    </div>

                                    <!-- CASA format reader -->
                                    <div v-if="ImportModelTypeChosed == 'CASA Format Reader'">

                                        <el-form-item label="CASA model: ">
                                            <el-input v-model="ModelConversionForm.model_file" type="textarea"
                                                :autosize="{ minRows: 8 }" placeholder='Note:
1.Cithub will convert your CASA format model into Cithub format
2.For example, the CASA format model should look like this:

2
3
2 6 3

'>
                                            </el-input>
                                        </el-form-item>

                                        <el-form-item label="CASA constraints: ">
                                            <el-input v-model="ModelConversionForm.constraints_file" type="textarea"
                                                :autosize="{ minRows: 8 }" placeholder='Note:
1.Also the CASA format constraint will be converted.
2.For example, the constraints should look like this:

4 
2 
- 0 - 2 
2 
- 0 - 3 
2
- 0 - 4 
2 
- 0 - 5

'>
                                            </el-input>
                                        </el-form-item>
                                    </div>
                                </el-form>


                                <!-- LLM Modelling -->
                                <el-form v-if="dialogformNewModel.modeltype == 'LLM'" label-position="right"
                                    label-width="130px">
                                    <el-form-item label="OpenAI API Key:">
                                        <el-input v-model="LLMModellingForm.apikey" type="password" clearable
                                            placeholder="eg: sk-gKzhAQLE1Fh777i0KgOi15NQQGInPH7JK82OVBeCitHubApi">
                                        </el-input>
                                    </el-form-item>
                                    <el-form-item label="Base URL:">
                                        <el-input v-model="LLMModellingForm.baseUrl" type="textarea" clearable
                                            placeholder="(Optional) if you use a proxy, please input baseurl of it here.">
                                        </el-input>
                                    </el-form-item>
                                    <el-form-item label="LLM Model:">
                                        <el-select v-model="LLMModellingForm.LLMmodel" class="m-2" clearable
                                            placeholder="Select an LLM for extracting CIT model.">
                                            <el-option v-for="item in LLMmodelOptions" :key="item.value" :label="item.label"
                                                :value="item.value" />
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="SUT Description:">
                                        <el-input v-model="LLMModellingForm.semanticsType" type="textarea" clearable
                                            :autosize="{ minRows: 5 }" placeholder="A short description for your SUT corpus.
For example, for the SUT corpus in the following Input Box, the description of it is:
`
The withdrawal case study of a ATM application.
`">
                                        </el-input>
                                    </el-form-item>
                                    <el-form-item label="SUT Corpus:">
                                        <el-input v-model="LLMModellingForm.semantics" type="textarea" clearable
                                            :autosize="{ minRows: 15 }" placeholder="Note:                                           
1.Cithub will extract parameters, values and constraints from the corpus of SUT you provided ending with a abstract CIT model.
2.Any form of corpus is available, you could provide it look like this:
`
A withdrawal transaction asks the customer to choose an account type to withdraw from savings-account or checking-account, and to choose an amount from a menu of possible amounts. The system verifies that it has sufficient money on hand to satisfy the request before sending the transaction to the bank. If not, the customer is informed and asked to enter different amount. If the transaction is approved by the bank, the appropriate amount of cash is dispensed by the machine before it issues a receipt. The dispensing of cash is also recorded in the ATM’s log. A withdrawal transaction can be cancelled by the customer by pressing the Cancel key any time prior to choosing the amount. The customer cannot withdraw amount greater than 10000 from his savings account in a single transaction.`
">
                                        </el-input>
                                    </el-form-item>
                                </el-form>
                            </div>




                            <template #footer>
                                <span class="dialog-footer">
                                    <ArgonButton size="sm" color="secondary" @click="dialogFormVisibleNew = false">Cancel
                                    </ArgonButton>
                                    <ArgonButton class="mx-2" size="sm" color="success" @click="confirmNewModel"> New Model
                                    </ArgonButton>
                                </span>
                            </template>
                        </el-dialog>
                        <div class="card-body px-0 pt-0 pb-2">

                            <!-- 废弃ModelCard方案，使用Table方案 -->
                            <!-- 在最后一行添加一个空白的col，确保最后一行只有一个元素时也只占用一个位置 -->

                            <!-- <div v-for="(chunk, rowIndex) in chunkedArray" :key="rowIndex" class="row"
                                style="margin: 0 0 0 20px;">
                                <div class="col" v-for="(model, colIndex) in chunk" :key="colIndex">
                                    <ModelCard style="margin:5px 5px 5px 5px" :model="model"
                                        :index="calculateOriginalIndex(rowIndex, colIndex)">
                                    </ModelCard>
                                </div>
                                <div v-if="isLastRow(rowIndex) && chunk.length === 1" class="col"></div>
                            </div> -->

                            <!-- 使用Table显示Models -->
                            <div v-if="modelStore.modelsList.length != 0" class="card-header">
                                <ModelsTable :model="modelStore.modelsList"></ModelsTable>
                            </div>



                            <el-result v-else icon="warning" title="You have No Models Now"
                                sub-title="Please click the button below to create a Model">
                                <template #extra>
                                    <ArgonButton  color="success" variant="gradient"
                                        @click="showdialogNew">
                                        <span class="ni ni-fat-add ni-lg me-1" />
                                        New Model
                                    </ArgonButton>
                                </template>
                            </el-result>

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
import ModelsTable from './components/ModelsTable.vue'
import ModelCard from './components/ModelCard.vue'
import pinia from '../store/store'

import { useModelsStore } from '../store/ToolsStore/modelsStore'
import { useLLMmodellingStore } from '../store/ToolsStore/LLMmodellingStore.js';
import { useCurrentModel } from '../store/ToolsStore/currentModel';
import { ElNotification } from 'element-plus'
import { listAllModelsByUserID } from './commonFunction.js'
import toolsInfo from "../CustomizedComponents/tools_info.json";
import { useUserStore } from '../store/userStore';
import { ElLoading } from 'element-plus'
import { useModuleStore } from '../store/module';


const moduleStore = useModuleStore(pinia)
const route = useRoute()
const router = useRouter()
const modelStore = useModelsStore(pinia)
const currentModel = useCurrentModel(pinia)
const llmFormStore = useLLMmodellingStore(pinia)
const userStore = useUserStore(pinia)


// const modelLists = reactive([]);
// const dialogTableVisible = ref(false)
// const dialogFormVisible = ref(false)
// const dateObject_created = ref()
// const dateObject_lastupdated = ref()

const ModelConversionForm = reactive({})
const LLMModellingForm = reactive({
    apikey: 'sk-gKzhAQLE1Fh777i0KgOi15NQQGInPH7JK82OVBeMIPzGopgd',
    semantics: 'A withdrawal transaction asks the customer to choose an account type to withdraw from savings-account or checking-account, and to choose an amount from a menu of possible amounts. The system verifies that it has sufficient money on hand to satisfy the request before sending the transaction to the bank. If not, the customer is informed and asked to enter different amount. If the transaction is approved by the bank, the appropriate amount of cash is dispensed by the machine before it issues a receipt. The dispensing of cash is also recorded in the ATM’s log. A withdrawal transaction can be cancelled by the customer by pressing the Cancel key any time prior to choosing the amount. The customer cannot withdraw amount greater than 10000 from his savings account in a single transaction.',
    semanticsType: 'The withdrawal feature case study of ATM application',
    LLMmodel: 'gpt-3.5-turbo-0125',
    baseUrl: 'https://api.openai-proxy.org',
})


const LLMmodelOptions = [
    {
        value: 'gpt-3.5-turbo-instruct',
        label: 'gpt-3.5-turbo-instruct'
    },
    {
        value: 'gpt-3.5-turbo-0125',
        label: 'gpt-3.5-turbo-0125'
    },
    {
        value: 'gpt-4',
        label: 'gpt-4'
    },
    {
        value: 'gpt-4-32k',
        label: 'gpt-4-32k'
    }
]




// const itemsPerRow = ref(2);
// const chunkedArray = computed(() => {
//     const result = [];
//     for (let i = 0; i < modelStore.modelsList.length; i += itemsPerRow.value) {
//         result.push(modelStore.modelsList.slice(i, i + itemsPerRow.value));
//     }
//     return result;
// });
// const calculateOriginalIndex = (rowIndex, colIndex) => {
//     // 计算原始索引的方法
//     return rowIndex * itemsPerRow.value + colIndex;
// }
// const isLastRow = computed(() => {
//     return rowIndex => rowIndex === chunkedArray.value.length - 1;
// });


const dialogFormVisibleNew = ref(false)
const dialogformNewModel = reactive({
    modelname: '',
    modeldescriptions: '',
    modeltype: '',
    lastupdatedtime: '',
    createdtime: '',
    userid: userStore.userID
})
const showdialogNew = () => {

    dialogFormVisibleNew.value = true
}

const confirmNewModel = async () => {
    let loadingInstance = ElLoading.service({ fullscreen: true })
    if (dialogformNewModel.modeltype == '') {
        ElNotification({
            title: 'New Model Error!',
            message: 'Please choose one of the modelling method.',
            type: 'error',
        })
        loadingInstance.close()
    } else if (dialogformNewModel.modelname == '') {
        ElNotification({
            title: 'New Model Error!',
            message: 'Model Name can not be empty.',
            type: 'error',
        })
        loadingInstance.close()
    }
    else {
        // 获取当前时刻的Date对象
        const currentDate = new Date();
        dialogformNewModel.lastupdatedtime = currentDate
        dialogformNewModel.createdtime = currentDate
        let ModelFile = ''
        switch (dialogformNewModel.modeltype) {
            case 'Manual':
                try {
                    const res = await request({
                        url: '/tools/NewModel',
                        method: 'POST',
                        data: {
                            column: 'model',
                            modelname: dialogformNewModel.modelname,
                            modeldescriptions: dialogformNewModel.modeldescriptions,
                            modeltype: dialogformNewModel.modeltype,
                            userid: userStore.userID,
                            lastupdatedtime: dialogformNewModel.lastupdatedtime,
                            createdtime: dialogformNewModel.createdtime,
                        }

                    });
                    if (res.NewStatus == 'success!') {
                        // 更新Models table
                        let loadingInstance = ElLoading.service({ fullscreen: true })

                        await listAllModelsByUserID()

                        dialogFormVisibleNew.value = false

                        // 直接跳转至新模型的编辑页面
                        router.push({
                            path: '/tools/modelsDetails',
                            query:
                            {
                                modelid: modelStore.modelsList[modelStore.modelsList.length - 1].modelid,

                            }
                        })
                        loadingInstance.close()
                        ElNotification({
                            title: 'New Model Success!',
                            message: 'please input Parameters and Constraints',
                            type: 'success',
                        })
                    }
                    else {
                        ElNotification({
                            title: 'New Model Error!',
                            message: 'please check the results',
                            type: 'error',
                        })
                    }
                }
                catch (error) {
                    ElNotification({
                        title: 'New Model Error!',
                        message: 'please check the results',
                        type: 'error',
                    })
                }
                break;
            case 'Imported':
                switch (ImportModelTypeChosed.value) {
                    case 'ACTS Format Reader':
                        // 构造发送给ACTS FORMAT READER的obj
                        let obj_ACTS = {}
                        // 先给一个默认的覆盖强度 2
                        obj_ACTS.strength = 2
                        // 对 ModelConversionForm.file 处理 将字符串中的 换行符替换为 &# 再给 ACTS format reader
                        ModelFile = ModelConversionForm.file.replace(/\n/g, '&#')
                        obj_ACTS.file = ModelFile
                        // console.log("obj_ACTS", JSON.stringify(obj_ACTS))
                        for (const tool of toolsInfo.RECORDS) {
                            if (tool.title == ImportModelTypeChosed.value) {
                                try {
                                    const ACTSRes = await request({
                                        // 这里记得改回去，在校外无法用校内服务器
                                        url: tool.url,
                                        // url: 'http://localhost:8310',
                                        method: 'POST',
                                        // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                                        headers: {
                                            'Content-Type': 'text/plain'
                                        },
                                        data: JSON.stringify(obj_ACTS)
                                    })
                                    // console.log("ACTSRes", ACTSRes)

                                    // console.log("inputString ", inputString)
                                    // 将导入的模型加载到table上

                                    // 定义一个正则表达式来匹配[Parameter]和[Constraint]之间的内容
                                    const regex = /\[Parameter\]([\s\S]+?)\[Constraint\]/;

                                    // 使用正则表达式匹配字符串
                                    const match = ModelConversionForm.file.match(regex);

                                    if (match) {
                                        // 获取匹配到的内容
                                        const parameterSection = match[1].trim();

                                        // 分割参数部分为每一行
                                        const parameterLines = parameterSection.split('\n');

                                        // 创建一个数组来存储参数对象
                                        const parameterArray = [];

                                        // 遍历每一行，解析参数
                                        parameterLines.forEach(line => {
                                            const [nameAndType, values] = line.split(':');
                                            const [name, type] = nameAndType.split('(').map(part => part.trim());

                                            // 将值分割为数组
                                            const valuesArray = values.split(',').map(value => value.trim());

                                            // 将参数对象添加到数组中
                                            parameterArray.push({
                                                Parameter: name,
                                                Value: valuesArray.join(','),
                                                ValueArray: valuesArray
                                            });

                                        });

                                        // // 输出生成的JSON数组
                                        // console.log("读取参数和参数取值！")
                                        // console.log("parameterArray", JSON.stringify(parameterArray, null, 2));


                                        // 处理约束部分
                                        const consArray = [];

                                        for (const cons of ACTSRes.constraints) {
                                            const result = getParameterValue(cons, parameterArray);

                                            // 创建包含 Constrain_x 键和结果数组的对象
                                            const key = `Constraint`;
                                            const resultObject = { [key]: result };

                                            // 将对象添加到数组中
                                            consArray.push(resultObject);

                                        }

                                        // 使用 map 方法遍历数组，去掉每个元素中的 ValueArray 键值对
                                        const ParameterValuesArray = parameterArray.map(element => {
                                            // 创建一个新对象，保留 Parameter 和 Value 键值对
                                            const { Parameter, Value } = element;
                                            return { Parameter, Value };
                                        });


                                        // console.log("ParametersAndValues", JSON.stringify(ParameterValuesArray))
                                        // console.log("Cons", JSON.stringify(consArray))


                                        // 新建模型

                                        const NewModelRes = await request({
                                            url: '/tools/New',
                                            method: 'POST',
                                            data: {

                                                column: 'model',
                                                userid: userStore.userID,
                                                modelname: dialogformNewModel.modelname,
                                                modeldescriptions: dialogformNewModel.modeldescriptions,
                                                modeltype: dialogformNewModel.modeltype,
                                                lastupdatedtime: dialogformNewModel.lastupdatedtime,
                                                createdtime: dialogformNewModel.createdtime,
                                                ParametersAndValues: JSON.stringify(ParameterValuesArray),
                                                Cons: JSON.stringify(consArray),
                                            }
                                        })
                                        if (NewModelRes.NewStatus == 'success!') {
                                            // 更新Models table
                                            let loadingInstance = ElLoading.service({ fullscreen: true })

                                            await listAllModelsByUserID()

                                            dialogFormVisibleNew.value = false

                                            // 直接跳转至新模型的编辑页面
                                            router.push({
                                                path: '/tools/modelsDetails',
                                                query:
                                                {
                                                    modelid: modelStore.modelsList[modelStore.modelsList.length - 1].modelid,

                                                }
                                            })

                                            loadingInstance.close()
                                            ElNotification({
                                                title: 'Import ACTS Model Success!',
                                                message: 'please Check the Parameters and Constraints',
                                                type: 'success',
                                            })



                                        }
                                        else {
                                            ElNotification({
                                                title: 'Import ACTS Model Failed!',
                                                type: 'error',
                                            })
                                        }





                                    } else {
                                        console.log('未找到匹配的内容');
                                    }
                                    loadingInstance.close()

                                }
                                catch (err) {
                                    ElNotification({
                                        title: 'New Model Failed!',
                                        message: 'check your inputs!',
                                        type: 'error',
                                    })

                                    loadingInstance.close()
                                }
                            }
                        }


                        break;

                    case 'CASA Format Reader':
                        // 构造发送给PICT Format Reader的obj
                        let obj_casa = {}
                        let constraints_file = ''
                        let model_file = ''
                        constraints_file = ModelConversionForm.constraints_file.replace(/\n/g, '&#')
                        model_file = ModelConversionForm.model_file.replace(/\n/g, '&#')
                        obj_casa.constraints_file = constraints_file
                        obj_casa.model_file = model_file
                        for (const tool of toolsInfo.RECORDS) {
                            if (tool.title == ImportModelTypeChosed.value) {
                                try {
                                    const CASARes = await request({
                                        //  这里记得改回去，在校外无法用校内服务器
                                        url: tool.url,
                                        // url: 'http://localhost:8313',
                                        method: 'POST',
                                        // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                                        headers: {
                                            'Content-Type': 'text/plain'
                                        },
                                        data: JSON.stringify(obj_casa)
                                    })
                                    // console.log("CASARes结果", CASARes)

                                    //由于casa model的特殊性，手动构造其模型
                                    // console.log("inputString", inputString)

                                    const regexNumber = /\b(\d+)\b/;
                                    // 获取覆盖强度
                                    const match = ModelConversionForm.model_file.match(regexNumber);
                                    // 输出匹配到的第一个数字作为覆盖强度
                                    let strength = 0
                                    if (match) {
                                        strength = parseInt(match[1], 10); // 使用parseInt将字符串转换为数字
                                    } else {
                                        console.log("No strength found.");
                                    }

                                    // console.log("strength", strength)
                                    ModelConversionForm.strength = strength
                                    const parameterArray = [];
                                    for (let i = 0, len = CASARes.values.length; i < len; i++) {

                                        let objTemp = {}
                                        objTemp.Parameter = `Parameter_${i + 1}`
                                        const currentElement = CASARes.values[i];
                                        const currentArray = [];

                                        for (let j = 1; j <= currentElement; j++) {
                                            currentArray.push(`value_${j}`);
                                        }
                                        objTemp.ValueArray = currentArray
                                        objTemp.Value = currentArray.join(',')
                                        parameterArray.push(objTemp)
                                    }
                                    // console.log("parameterArray", parameterArray)
                                    // 处理约束部分
                                    const consArray = [];

                                    for (const cons of CASARes.constraints) {
                                        const result = getParameterValue(cons, parameterArray);

                                        // 创建包含 Constrain_x 键和结果数组的对象
                                        const key = `Constraint`;
                                        const resultObject = { [key]: result };

                                        // 将对象添加到数组中
                                        consArray.push(resultObject);

                                    }

                                    // 使用 map 方法遍历数组，去掉每个元素中的 ValueArray 键值对
                                    const ParameterValuesArray = parameterArray.map(element => {
                                        // 创建一个新对象，保留 Parameter 和 Value 键值对
                                        const { Parameter, Value } = element;
                                        return { Parameter, Value };
                                    });
                                    // console.log("consArray",consArray)
                                    // console.log("JSON.stringify(ParameterValuesArray)", JSON.stringify(ParameterValuesArray))


                                    const NewModelRes = await request({
                                        url: '/tools/New',
                                        method: 'POST',
                                        data: {


                                            column: 'model',
                                            userid: userStore.userID,
                                            modelname: dialogformNewModel.modelname,
                                            modeldescriptions: dialogformNewModel.modeldescriptions,
                                            modeltype: dialogformNewModel.modeltype,
                                            lastupdatedtime: dialogformNewModel.lastupdatedtime,
                                            createdtime: dialogformNewModel.createdtime,
                                            ParametersAndValues: JSON.stringify(ParameterValuesArray),
                                            Cons: JSON.stringify(consArray),


                                        }
                                    })
                                    if (NewModelRes.NewStatus == 'success!') {
                                        // 更新Models table
                                        let loadingInstance = ElLoading.service({ fullscreen: true })

                                        await listAllModelsByUserID()

                                        dialogFormVisibleNew.value = false

                                        // 直接跳转至新模型的编辑页面
                                        router.push({
                                            path: '/tools/modelsDetails',
                                            query:
                                            {
                                                modelid: modelStore.modelsList[modelStore.modelsList.length - 1].modelid,

                                            }
                                        })

                                        loadingInstance.close()
                                        ElNotification({
                                            title: 'Import CASA Model Success!',
                                            message: 'please Check the Parameters and Constraints',
                                            type: 'success',
                                        })



                                    }
                                    else {
                                        ElNotification({
                                            title: 'Import CASA Model Failed!',
                                            type: 'error',
                                        })
                                    }



                                }

                                catch (err) {

                                    console.log("err", err)
                                    ElNotification({
                                        title: 'Import CASA Model Failed!',
                                        message: 'check your inputs!',
                                        type: 'error',
                                    })
                                }
                            }
                        }

                        break;

                    case 'CTWedge Format Reader':

                        let obj_ctwedge = {}
                        obj_ctwedge.strength = 2
                        ModelFile = ModelConversionForm.model_file.replace(/\n/g, '&#')
                        obj_ctwedge.model_file = ModelFile
                        for (const tool of toolsInfo.RECORDS) {
                            if (tool.title == ImportModelTypeChosed.value) {
                                try {
                                    const CTWedgeRes = await request({
                                        // 这里记得改回去，在校外无法用校内服务器
                                        url: tool.url,
                                        // url: 'http://localhost:8311',
                                        method: 'POST',
                                        // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                                        headers: {
                                            'Content-Type': 'text/plain'
                                        },
                                        data: JSON.stringify(obj_ctwedge)
                                    })
                                    // console.log("CTWedgeRes结果", CTWedgeRes)
                                    // console.log("inputString是 ", inputString)
                                    // 将导入的模型加载到table上
                                    // 定义一个正则表达式来匹配Parameters:和end之间的内容
                                    const regex = /Parameters:(.*?)(?=end)/s;

                                    // 使用正则表达式匹配字符串
                                    const match = ModelConversionForm.model_file.match(regex);

                                    if (match) {
                                        // 获取匹配到的内容
                                        const parametersContent = match[1].trim();
                                        // console.log("parametersContent", parametersContent);
                                        const lines = parametersContent.trim().split('\n');
                                        // parameterArray
                                        const parameterArray = [];

                                        // 定义正则表达式
                                        const regex = /^(\w+)\s+(\w+)\s*(?:\{([^}]+)\}|(?:\[(\d+)\s*\.\.\s*(\d+)\]))?;$/;

                                        // 遍历每一行并匹配正则表达式
                                        for (const line of lines) {
                                            const match = line.match(regex);

                                            if (match) {
                                                const [, type, name, enumValues, rangeStart, rangeEnd] = match;
                                                const element = {
                                                    type,
                                                    name,
                                                    enumValues: enumValues ? enumValues.split(',').map(value => value.trim()) : null,
                                                    range: rangeStart && rangeEnd ? { start: parseInt(rangeStart), end: parseInt(rangeEnd) } : null,
                                                };
                                                parameterArray.push(element);
                                            }
                                        }
                                        const ParameterAndValues = []
                                        for (const param of parameterArray) {
                                            let PandVtemp = {};
                                            if (param.type === "Boolean") {
                                                PandVtemp.Parameter = param.name;
                                                PandVtemp.Value = 'TRUE,FALSE';
                                                PandVtemp.ValueArray = ['TRUE', 'FALSE'];
                                                ParameterAndValues.push({ ...PandVtemp }); // Use spread operator to create a copy
                                            }
                                            if (param.type === "Range") {
                                                PandVtemp.Parameter = param.name;
                                                PandVtemp.Value = [];

                                                // 生成范围内的所有值
                                                for (let i = param.range.start; i <= param.range.end; i++) {
                                                    PandVtemp.Value.push(i);
                                                }
                                                PandVtemp.Value = PandVtemp.Value.map(String)
                                                PandVtemp.ValueArray = [...PandVtemp.Value]; // Use spread operator to create a copy
                                                PandVtemp.Value = PandVtemp.Value.join(',');
                                                ParameterAndValues.push({ ...PandVtemp });
                                            }
                                            if (param.type === "Enumerative") {
                                                PandVtemp.Parameter = param.name;
                                                PandVtemp.Value = param.enumValues.join(',');
                                                PandVtemp.ValueArray = [...param.enumValues]; // Use spread operator to create a copy
                                                ParameterAndValues.push({ ...PandVtemp });
                                            }
                                        }

                                        // 处理约束部分
                                        const consArray = [];
                                        for (const cons of CTWedgeRes.constraints) {
                                            const result = getParameterValue(cons, ParameterAndValues);

                                            // 创建包含 Constrain_x 键和结果数组的对象
                                            const key = `Constraint`;
                                            const resultObject = { [key]: result };

                                            // 将对象添加到数组中
                                            consArray.push(resultObject);

                                        }

                                        // console.log("consArray", consArray)

                                        // 使用 map 方法遍历数组，去掉每个元素中的 ValueArray 键值对
                                        const ParameterValuesArray = ParameterAndValues.map(element => {
                                            // 创建一个新对象，保留 Parameter 和 Value 键值对
                                            const { Parameter, Value } = element;
                                            return { Parameter, Value };
                                        });
                                        // console.log("JSON.stringify(ParameterValuesArray)",JSON.stringify(ParameterValuesArray))

                                        const NewModelRes = await request({
                                            url: '/tools/New',
                                            method: 'POST',
                                            data: {

                                                column: 'model',
                                                userid: userStore.userID,
                                                modelname: dialogformNewModel.modelname,
                                                modeldescriptions: dialogformNewModel.modeldescriptions,
                                                modeltype: dialogformNewModel.modeltype,
                                                lastupdatedtime: dialogformNewModel.lastupdatedtime,
                                                createdtime: dialogformNewModel.createdtime,
                                                ParametersAndValues: JSON.stringify(ParameterValuesArray),
                                                Cons: JSON.stringify(consArray),
                                            }
                                        })
                                        if (NewModelRes.NewStatus == 'success!') {
                                            // 更新Models table
                                            let loadingInstance = ElLoading.service({ fullscreen: true })

                                            await listAllModelsByUserID()

                                            dialogFormVisibleNew.value = false

                                            // 直接跳转至新模型的编辑页面
                                            router.push({
                                                path: '/tools/modelsDetails',
                                                query:
                                                {
                                                    modelid: modelStore.modelsList[modelStore.modelsList.length - 1].modelid,

                                                }
                                            })

                                            loadingInstance.close()
                                            ElNotification({
                                                title: 'Import CTWedge Model Success!',
                                                message: 'please Check the Parameters and Constraints',
                                                type: 'success',
                                            })





                                        }
                                        else {
                                            ElNotification({
                                                title: 'Import CTWedge Model Failed!',
                                                type: 'error',
                                            })
                                        }


                                    } else {
                                        console.log('未找到匹配的内容');
                                    }
                                }
                                catch (err) {
                                    ElNotification({
                                        title: 'New Model Failed!',
                                        message: 'check your inputs!',
                                        type: 'error',
                                    })
                                }

                            }
                        }

                        break;

                    case 'PICT Format Reader':
                        // console.log("PICT Format Reader")
                        // 构造发送给PICT Format Reader的obj
                        let obj_pict = {}
                        obj_pict.strength = 2
                        ModelFile = ModelConversionForm.model_file.replace(/\n/g, '&#')
                        obj_pict.model_file = ModelFile
                        // console.log("obj", JSON.stringify(obj_pict))
                        for (const tool of toolsInfo.RECORDS) {
                            if (tool.title == ImportModelTypeChosed.value) {
                                try {
                                    const PICTRes = await request({
                                        // 这里记得改回去，在校外无法用校内服务器
                                        url: tool.url,
                                        // url: 'http://localhost:8312',
                                        method: 'POST',
                                        // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                                        headers: {
                                            'Content-Type': 'text/plain'
                                        },
                                        data: JSON.stringify(obj_pict)
                                    })
                                    // console.log("PICTRes结果", PICTRes)


                                    // 将导入的模型加载到table上
                                    // 定义一个正则表达式来匹配Parameters:和end之间的内容
                                    const regexALL = /([^:\n]+:[^:\n]+)/g;

                                    // 使用正则表达式匹配字符串

                                    const lines = ModelConversionForm.model_file.match(regexALL);
                                    if (lines) {
                                        // parameterArray
                                        const parameterArray = [];
                                        // 定义正则表达式
                                        // console.log("lines", lines)
                                        // 遍历每一行并匹配正则表达式
                                        for (const line of lines) {
                                            let tempArray = line.split(':')
                                            let tempObj = {}


                                            tempObj.Parameter = tempArray[0]
                                            tempObj.Value = tempArray[1]
                                            tempObj.ValueArray = tempArray[1].split(',')
                                            parameterArray.push(tempObj)
                                        }
                                        // console.log("parameterArray", parameterArray)

                                        // 处理约束部分
                                        const consArray = [];

                                        for (const cons of PICTRes.constraints) {
                                            const result = getParameterValue(cons, parameterArray);

                                            // 创建包含 Constrain_x 键和结果数组的对象

                                            const key = `Constraint`;
                                            const resultObject = { [key]: result };

                                            // 将对象添加到数组中
                                            consArray.push(resultObject);

                                        }

                                        // 使用 map 方法遍历数组，去掉每个元素中的 ValueArray 键值对
                                        const ParameterValuesArray = parameterArray.map(element => {
                                            // 创建一个新对象，保留 Parameter 和 Value 键值对
                                            const { Parameter, Value } = element;
                                            return { Parameter, Value };
                                        });
                                        // console.log("consArray",consArray)
                                        // console.log("JSON.stringify(ParameterValuesArray)", JSON.stringify(ParameterValuesArray))


                                        const NewModelRes = await request({
                                            url: '/tools/New',
                                            method: 'POST',
                                            data: {

                                                column: 'model',
                                                userid: userStore.userID,
                                                modelname: dialogformNewModel.modelname,
                                                modeldescriptions: dialogformNewModel.modeldescriptions,
                                                modeltype: dialogformNewModel.modeltype,
                                                lastupdatedtime: dialogformNewModel.lastupdatedtime,
                                                createdtime: dialogformNewModel.createdtime,
                                                ParametersAndValues: JSON.stringify(ParameterValuesArray),
                                                Cons: JSON.stringify(consArray),

                                            }
                                        })
                                        if (NewModelRes.NewStatus == 'success!') {

                                            // 更新Models table
                                            let loadingInstance = ElLoading.service({ fullscreen: true })

                                            await listAllModelsByUserID()

                                            dialogFormVisibleNew.value = false

                                            // 直接跳转至新模型的编辑页面
                                            router.push({
                                                path: '/tools/modelsDetails',
                                                query:
                                                {
                                                    modelid: modelStore.modelsList[modelStore.modelsList.length - 1].modelid,

                                                }
                                            })

                                            loadingInstance.close()
                                            ElNotification({
                                                title: 'Import PICT Model Success!',
                                                message: 'please Check the Parameters and Constraints',
                                                type: 'success',
                                            })

                                        }
                                        else {
                                            ElNotification({
                                                title: 'Import PICT Model Failed!',
                                                type: 'error',
                                            })
                                        }



                                    }
                                }
                                catch (err) {
                                    ElNotification({
                                        title: 'Import PICT Model Failed!',
                                        message: 'check your inputs!',
                                        type: 'error',
                                    })
                                }


                            }
                        }

                        break;

                }
                break;
            case 'LLM':

                if (LLMModellingForm.apikey == '' || LLMModellingForm.semantics == '' || LLMModellingForm.semanticsType == '' || LLMModellingForm.LLMmodel == '') {
                    ElNotification({
                        title: 'New LLM Model Error!',
                        message: 'some of your Inputs are empty!',
                        type: 'error',
                    })

                }
                else {

                    const NewModelRes = await request({
                        url: '/tools/NewModel',
                        method: 'POST',
                        data: {
                            userid: userStore.userID,
                            modelname: dialogformNewModel.modelname,
                            modeldescriptions: dialogformNewModel.modeldescriptions,
                            modeltype: dialogformNewModel.modeltype,
                            lastupdatedtime: dialogformNewModel.lastupdatedtime,
                            createdtime: dialogformNewModel.createdtime,

                            strength: ModelConversionForm.strength,

                            apikey: LLMModellingForm.apikey,
                            semantics: LLMModellingForm.semantics,
                            semanticsType: LLMModellingForm.semanticsType,
                            LLMmodel: LLMModellingForm.LLMmodel,
                            baseUrl: LLMModellingForm.baseUrl,
                        }
                    })
                    if (NewModelRes.NewStatus == 'success!') {
                        ElNotification({
                            title: 'New LLM Model Success!',
                            type: 'success',
                        })
                        await listAllModelsByUserID()
                        dialogFormVisibleNew.value = false

                        currentModel.currentModel.modelid = route.query.modelid
                        currentModel.currentModel.modelname = dialogformNewModel.modelname
                        currentModel.currentModel.modeldescriptions = dialogformNewModel.modeldescriptions
                        currentModel.currentModel.strength = ''
                        currentModel.currentModel.paramsvalues = ''
                        currentModel.currentModel.cons = ''
                        currentModel.currentModel.lastupdatedtime = currentDate

                        llmFormStore.llmForm = LLMModellingForm


                    }
                    else {
                        ElNotification({
                            title: 'New LLM Model Failed!',
                            type: 'error',
                        })
                    }


                }

                break;


        }


    }

}

// getParameterValue 接受两个参数：indexArray 表示要获取的参数和取值的索引数组，parameterData 表示包含参数和取值数组的 JSON 对象数组。函数将返回一个包含参数和对应取值的对象数组。
const getParameterValue = (indexArray, parameterData) => {
    const result = [];
    indexArray.forEach(index => {
        const [paramIndex, valueIndex] = index.split('/');
        const parameter = parameterData[paramIndex];

        if (parameter) {
            const value = parameter.ValueArray[valueIndex];
            result.push({
                Parameter: parameter.Parameter,
                Value: value
            });
        } else {
            console.log(`Invalid index: ${index}`);
        }
    });

    return result;
}





const selectedModellingType = ref(null);
const chooseModellingType = (type) => {
    selectedModellingType.value = type;
    dialogformNewModel.modeltype = type
}

const ImportModelTypeChosed = ref('')
const AlgorithmOptions = reactive([])
const listAllModelReaderAlgorithm = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "FormatConversion" && tool.title.includes("Format Reader")) {
            AlgorithmOptions.push({ "value": tool.title, "label": tool.title, "url": tool.url })
        }
    }

}

onMounted(async () => {
    let loadingInstance = ElLoading.service({ fullscreen: true })
    // 检查用户登录状态
    await CheckLogin()
    moduleStore.CurrentSubSystem = "Tools"
    moduleStore.CurrentSubSystemRoute = "Tools_Models"
    moduleStore.CurrentModule = 'Models'
    moduleStore.CurrentModuleDetails = 'Complete Models List'
    moduleStore.CurrentRoute = 'Tools_Models'
    try {

        // 加载当前用户的所有模型
        await listAllModelsByUserID()
        // 获取所有的模型转换工具
        listAllModelReaderAlgorithm()

        loadingInstance.close()

    } catch (error) {

        loadingInstance.close()

    }



})

</script>


<style scoped>
.rounded-border:hover {
    transform: scale(1.05);
    /* 鼠标划过时放大 5% */
}

.category {
    flex: 1;
    margin: 5px;
    transition: transform 0.3s ease;
    /* 添加过渡效果 */
}

.category:hover {
    transform: scale(1.1);
    /* 划过时放大 */
    cursor: pointer;
}

.selected-category {
    border: 2px solid #2dce89;
    /* 你可以根据需要修改颜色和宽度 */
    box-shadow: 0 0 10px #2dce89;
}
</style>
