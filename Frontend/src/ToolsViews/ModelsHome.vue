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
                            <h3>Models List</h3>

                            <h6>{{ modelStore.modelsList.length }} Models
                                found in project {{ route.query.projectname }}</h6>

                        </div>

                        <div style="width:100%;margin-bottom: 5px;text-align: center;">
                            <ArgonButton style="float: right;margin-right: 3%;" color="success" variant="gradient"
                                @click="showdialogNew">
                                <span class="ni ni-fat-add ni-lg me-1" />
                                New Model
                            </ArgonButton>
                        </div>
                        <el-dialog v-model="dialogFormVisibleNew" title="New Model">
                            <el-form :model="dialogformNewModel">

                                <el-form-item label="Model Name:">
                                    <el-input v-model="dialogformNewModel.modelname" />
                                </el-form-item>
                                <el-form-item label="Model Description:">
                                    <el-input autosize type="textarea" v-model="dialogformNewModel.modeldescriptions" />
                                </el-form-item>

                            </el-form>
                            <hr class="my-3 horizontal white" />

                            <p>Cithub currently support 3 methodes of modelling to create CIT model</p>
                            <p>Please choose one of the following modelling method to<ArgonBadge style="margin-left:3px"
                                    floating='true' color="success"> New Model</ArgonBadge>
                                .</p>

                            <div style="display:flex;flex-direction: row;justify-content: space-around;">
                                <div v-bind:class="{ 'selected-category': selectedModellingType === 'Manual' }"
                                    class="category" @click="chooseModellingType('Manual')">
                                    <el-card class="box-card" style="height: 100%;">
                                        <template #header>
                                            <div class="card-header" style="padding: 0px 0px 0px 0px;">
                                                <h6>Manual Modelling</h6>
                                            </div>
                                        </template>
                                        <div class="categoryDes">Modelling by provided user-friendly GUI interface to create
                                            Cithub Format model.
                                        </div>
                                    </el-card>
                                </div>
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
                                <div v-bind:class="{ 'selected-category': selectedModellingType === 'LLM' }"
                                    class="category" @click="chooseModellingType('LLM')">
                                    <el-card class="box-card" style="height: 100%;">
                                        <template #header>
                                            <div class="card-header" style="padding: 0px 0px 0px 0px;">
                                                <h6>LLM Modelling</h6>

                                            </div>
                                        </template>
                                        <div class="categoryDes">Modelling by large language model to assist you to extract
                                            CIT model from SUT corpus.
                                        </div>
                                    </el-card>
                                </div>
                            </div>



                            <template #footer>
                                <span class="dialog-footer">
                                    <el-button @click="dialogFormVisibleNew = false">Cancel</el-button>
                                    <el-button type="primary" @click="confirmNewModel">
                                        Confirm
                                    </el-button>
                                </span>
                            </template>
                        </el-dialog>
                        <div class="card-body px-0 pt-0 pb-2">

                            <!-- 废弃使用Card -->
                            <!-- <div v-for="(chunk, index) in chunkedArray" :key="index" class="row"
                                style="margin: 0 0 0 20px;">
                                <div class="col" v-for="(model, colIndex) in chunk" :key="colIndex">

                                    <ModelCard style="margin:5px 5px 5px 5px" :classIcon="getRandomIcon()" :model="model">
                                    </ModelCard>
                                </div>
                            </div> -->

                            <!-- 换成Table显示 -->
                            <div class="card-header">
                                <ModelsTable :model="modelStore.modelsList"></ModelsTable>
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
import { useRoute, useRouter } from 'vue-router';
import { onMounted, reactive, ref, computed } from 'vue';
import { request } from '../request';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import ArgonButton from '../ComponentCommon/ArgonButton.vue';
import SideNav from './components/SideNav.vue'
import ArgonBadge from '../ComponentCommon/ArgonBadge.vue';
import ModelsTable from './components/ModelsTable.vue'
import { useUserStore } from '../store/userStore';
import { usePaperInfoStore } from '../store/paperinfoStore';
import { useProjectsStore } from '../store/projectsStore'
import { useModelsStore } from '../store/modelsStore'
import { useCurrentProject } from '../store/currentProject';
import { ElNotification } from 'element-plus'

// import ModelCard from './components/ModelCard.vue'


const route = useRoute()
const router = useRouter()
const modelStore = useModelsStore()
const projectsStore = useProjectsStore()
const currentProjectStore = useCurrentProject()
const modelLists = reactive([]);
const dialogTableVisible = ref(false)
const dialogFormVisible = ref(false)
const dateObject_created = ref()
const dateObject_lastupdated = ref()
const listAllModelsByProjectID = async () => {
    if (currentProjectStore.projectid) {
        try {

            const modelsRes = await request({
                method: "POST",
                url: '/tools/models/listModelsByProjectID',
                data: {
                    projectid: currentProjectStore.projectid
                }
            });


            modelStore.modelsList = modelsRes.models





            for (let i = 0; i < modelStore.modelsList.length; i++) {

                const timestamp_created = modelStore.modelsList[i].createdtime
                const timestamp_lastupdated = modelStore.modelsList[i].lastupdatedtime
                const dateObject_created = new Date(timestamp_created);
                const dateObject_lastupdated = new Date(timestamp_lastupdated);

                // 获取可读的时间字符串
                modelStore.modelsList[i].createdtimeFortmat = dateObject_created.toLocaleString();
                modelStore.modelsList[i].lastupdatedtimeFortmat = dateObject_lastupdated.toLocaleString();

                // 统计每个Model下拥有的TestSuites的数量
                const testSuitesRes = await request({
                    method: "POST",
                    url: '/tools/testSuites/listTestSuitesByModelID',
                    data: {
                        modelid: modelStore.modelsList[i].modelid
                    }
                });
                modelStore.modelsList[i].NumOfTestSuites = testSuitesRes.TestSuites.length
            }


            ElNotification({
                title: 'Choose a Model',
                message: 'Please choose a model to continue.',
                type: 'info',
            })

        } catch (error) {
            console.error("发生错误", error);
            ElNotification({
                title: 'Need to Choose a Project first',
                message: 'Please choose a Project to continue.',
                type: 'error',
            })
            router.push({
                name: 'ProjectsHome'
            })
        }

    }
    else {
        ElNotification({
            title: 'Need to Choose a Project first',
            message: 'Please choose a Project to continue.',
            type: 'error',
        })
        router.push({
            name: 'ProjectsHome'
        })
    }

};

const itemsPerRow = ref(3);
const chunkedArray = computed(() => {
    const result = [];
    for (let i = 0; i < modelStore.modelsList.length; i += itemsPerRow.value) {
        result.push(modelStore.modelsList.slice(i, i + itemsPerRow.value));
    }
    return result;
});
const dialogFormVisibleNew = ref(false)
const dialogformNewModel = reactive({
    modelname: '',
    modeldescriptions: '',
    modeltype: '',
    lastupdatedtime: '',
    createdtime: '',
    projectID: route.query.projectid
})
const showdialogNew = () => {

    dialogFormVisibleNew.value = true
    dialogformNewModel.projectID = route.query.projectid
}

const confirmNewModel = async () => {
    if (dialogformNewModel.modeltype == '') {
        ElNotification({
            title: 'New Model Error!',
            message: 'Please choose one of the modelling method.',
            type: 'error',
        })
    }
    else {
        // 获取当前时刻的Date对象
        const currentDate = new Date();
        dialogformNewModel.lastupdatedtime = currentDate
        dialogformNewModel.createdtime = currentDate
        try {
            const res = await request({
                url: '/tools/models/NewModel',
                method: 'POST',
                data: dialogformNewModel

            });
            if (res.NewStatus == 'success!') {

                ReloadModels()

                ElNotification({
                    title: 'New Model Success!',
                    message: 'please check the results',
                    type: 'success',
                })
                dialogFormVisibleNew.value = false

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

    }

}




const ReloadModels = async () => {

    try {


        const modelsRes = await request({
            method: "POST",
            url: '/tools/models/listModelsByProjectID',
            data: {
                projectid: route.query.projectid
            }
        });

        modelStore.modelsList = []
        modelStore.modelsList = modelsRes.models
        for (let i = 0; i < modelStore.modelsList.length; i++) {

            const timestamp_created = modelStore.modelsList[i].createdtime
            const timestamp_lastupdated = modelStore.modelsList[i].lastupdatedtime
            const dateObject_created = new Date(timestamp_created);
            const dateObject_lastupdated = new Date(timestamp_lastupdated);

            // 获取可读的时间字符串
            modelStore.modelsList[i].createdtimeFortmat = dateObject_created.toLocaleString();
            modelStore.modelsList[i].lastupdatedtimeFortmat = dateObject_lastupdated.toLocaleString();

            // 统计每个Model下拥有的TestSuites的数量
            const testSuitesRes = await request({
                method: "POST",
                url: '/tools/testSuites/listTestSuitesByModelID',
                data: {
                    modelid: modelStore.modelsList[i].modelid
                }
            });
            modelStore.modelsList[i].NumOfTestSuites = testSuitesRes.TestSuites.length
        }




    } catch (error) {
        console.error("发生错误", error);
    }

}

const iconsArray = [
    'text-white fas fa-landmark',
    'text-white fas fa-kiwi-bird',
    'text-white fas fa-laptop-code',
    'text-white fas fa-laugh-wink',
]; // 替换为你的图标数组

const getRandomIcon = () => {
    const randomIndex = Math.floor(Math.random() * iconsArray.length);
    return iconsArray[randomIndex];
};

const selectedModellingType = ref(null);
const chooseModellingType = (type) => {
    selectedModellingType.value = type;
    dialogformNewModel.modeltype = type
}


onMounted(() => {
    listAllModelsByProjectID()
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
