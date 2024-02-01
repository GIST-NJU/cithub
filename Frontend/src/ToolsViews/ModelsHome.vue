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
                            <h3>ModelList List</h3>
                            <p class="text-muted text-sm mb-0">{{ modelStore.modelsList.length }} Models
                                found in project {{ route.query.projectname }}</p>
                        </div>
                        <div style="margin: 60px 0px 0px 20px;">
                        </div>
                        <div class="card-body px-0 pt-0 pb-2">

                            <div v-for="(chunk, index) in chunkedArray" :key="index" class="row"
                                style="margin: 0 0 0 20px;">
                                <div class="col" v-for="(model, colIndex) in chunk" :key="colIndex">

                                    <ModelCard style="margin:5px 5px 5px 5px" :classIcon="getRandomIcon()" :model="model">
                                    </ModelCard>



                                    <!-- <div class="rounded-border">
                                        <div><span>Model Name:</span>{{ model.modelname }}</div>
                                        <div><span>Model Des:</span> {{ model.modeldescriptions }}</div>
                                        <div><span>Model Content:</span> {{ model.modelcontent }}</div>
                                        <div><span>Last updated time :</span>{{ model.lastupdatedtimeFortmat }}</div>
                                        <div><span>Created time :</span>{{ model.createdtimeFortmat }}</div>
                                        <el-button @click="showdialog(model)">Update</el-button>
                                        <el-button>Generate</el-button>
                                        <el-button>Delete</el-button>
                                    </div>
                                    <el-dialog v-model="dialogFormVisible" title="Update Model">
                                        <el-form :model="dialogform">
                                            <el-form-item label="Model ID:">
                                                <el-input disabled v-model="dialogform.modelid" />
                                            </el-form-item>
                                            <el-form-item label="Model Name:">
                                                <el-input v-model="dialogform.modelname" />
                                            </el-form-item>
                                            <el-form-item label="Model Description:">
                                                <el-input autosize type="textarea" v-model="dialogform.modeldescriptions" />
                                            </el-form-item>
                                            <el-form-item label="Model Content:">
                                                <el-input autosize type="textarea" v-model="dialogform.modelcontent" />
                                            </el-form-item>

                                        </el-form>
                                        <template #footer>
                                            <span class="dialog-footer">
                                                <el-button @click="dialogFormVisible = false">Cancel</el-button>
                                                <el-button type="primary" @click="confirmUpdateModel">
                                                    Confirm
                                                </el-button>
                                            </span>
                                        </template>
                                    </el-dialog> -->



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
import { useRoute,useRouter} from 'vue-router';
import { onMounted, reactive, ref, computed } from 'vue';
import { request } from '../request';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import SideNav from './components/SideNav.vue'
import { useUserStore } from '../store/userStore';
import { usePaperInfoStore } from '../store/paperinfoStore';
import { useProjectsStore } from '../store/projectsStore'
import { useModelsStore } from '../store/modelsStore'
import { useCurrentProject } from '../store/currentProject';
import { ElNotification } from 'element-plus'

import ModelCard from './components/ModelCard.vue'


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
    // console.log("route.query.projectid", route.query.projectid)
    try {


        const modelsRes = await request({
            method: "POST",
            url: '/tools/models/listModelsByProjectID',
            data: {
                projectid: route.query.projectid
            }
        });

        // modelLists.push(modelsRes.models);
        // modelLists.length = 0
        // console.log("modelsRes",modelsRes)
        modelStore.modelsList = modelsRes.models
        // console.log("modelStore.modelsList", modelStore.modelsList)




        for (let i = 0; i < modelStore.modelsList.length; i++) {

            const timestamp_created = modelStore.modelsList[i].createdtime
            const timestamp_lastupdated = modelStore.modelsList[i].lastupdatedtime
            const dateObject_created = new Date(timestamp_created);
            const dateObject_lastupdated = new Date(timestamp_lastupdated);

            // 获取可读的时间字符串
            modelStore.modelsList[i].createdtimeFortmat = dateObject_created.toLocaleString();
            modelStore.modelsList[i].lastupdatedtimeFortmat = dateObject_lastupdated.toLocaleString();
        }




    } catch (error) {
        console.error("发生错误", error);
    }
    // console.log("projectList", projectList)
    // console.log("modelLists", modelLists)
    // console.log("CALists", CALists)
};

const itemsPerRow = ref(3);
const chunkedArray = computed(() => {
    const result = [];
    for (let i = 0; i < modelStore.modelsList.length; i += itemsPerRow.value) {
        result.push(modelStore.modelsList.slice(i, i + itemsPerRow.value));
    }
    return result;
});
const dialogform = reactive({
    modelid: '',
    modelname: '',
    modeldescriptions: '',
    modelcontent: '',
    lastupdatedtime: '',
    createdtime: ''


})

const showdialog = (model) => {
    // console.log(model)
    dialogFormVisible.value = true
    dialogform.modelid = model.modelid
    dialogform.modelname = model.modelname
    dialogform.modeldescriptions = model.modeldescriptions
    dialogform.modelcontent = model.modelcontent
    dialogform.createdtime = model.createdtime


}

const confirmUpdateModel = () => {
    // console.log(dialogform)
    // 获取当前时刻的Date对象
    const currentDate = new Date();

    // 将Date对象转换为ISO 8601标准的时间戳字符串
    const currentISO8601Timestamp = currentDate.toISOString();

    dialogform.lastupdatedtime = currentISO8601Timestamp
    // console.log(dialogform)

    request({
        url: '/tools/models/updateModel',
        method: 'POST',
        data: dialogform
    }).then((res) => {
        if (res.UpdateStatus == 'success!') {
            ElNotification({
                title: 'Update Success!',
                message: 'please check the results',
                type: 'success',
            })
            console.log(res)
            // dialogform.modelid = model.modelid
            // dialogform.modelname = model.modelname
            // dialogform.modeldescriptions = model.modeldescriptions
            // dialogform.modelcontent = model.modelcontent
            // dialogform.createdtime = model.createdtime

            dialogFormVisible.value = false
        }
    }).catch((error) => {
        console.log(error)
        ElNotification({
            title: 'Update Error!',
            message: 'please check the results',
            type: 'Error',
        })
    })

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



onMounted(() => {
    listAllModelsByProjectID()
})


</script>


<style scoped>
.rounded-border {
    border: 1px solid #2dce89;
    border-radius: 20px;
    padding: 15px;
    transition: transform 0.3s ease;
    /* 添加过渡效果 */
    /* 可根据需要调整内边距 */
}

.rounded-border:hover {
    transform: scale(1.05);
    /* 鼠标划过时放大 5% */
}
</style>
