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
                            <h3>Projects List</h3>
                            <p class="text-muted text-sm mb-0">{{ projectsStore.projectList.length }} Projects
                                found</p>
                        </div>
                        <div style="margin: 60px 0px 0px 20px;">
                        </div>
                        <div class="card-body px-0 pt-0 pb-2">

                            <!-- <el-descriptions :title="project.projectname" direction="vertical" size="default">
                                <el-descriptions-item label="Description">{{ project.projectdescriptions
                                }}</el-descriptions-item>
                                <el-descriptions-item label="Last Updated time">{{ project.lastupdatedtime
                                }}</el-descriptions-item>
                                <el-descriptions-item label="Created time" :span="2">{{ project.createdtime
                                }}</el-descriptions-item>


                            </el-descriptions> -->

                            <div v-for="(chunk, index) in chunkedArray" :key="index" class="row"
                                style="margin: 0 0 0 20px;">
                                <div class="col" v-for="(project, colIndex) in chunk" :key="colIndex">
                                    <div class="rounded-border">
                                        <div><span>Project Name:</span>{{ project.projectname }}</div>
                                        <div><span>Project Des:</span> {{ project.projectdescriptions }}</div>
                                        <div><span>Last updated time :</span>{{ project.lastupdatedtimeFormat }}</div>
                                        <div><span>Created time :</span>{{ project.createdtimeFormat }}</div>
                                        <el-button @click="enterProject(project)">Enter</el-button>
                                        <el-button @click="showdialog(project)">Update</el-button>
                                        <el-button>Delete</el-button>
                                    </div>
                                </div>
                                <el-dialog v-model="dialogFormVisible" title="Update Project">
                                    <el-form :model="dialogform">
                                        <el-form-item label="Project ID:">
                                            <el-input disabled v-model="dialogform.projectid" />
                                        </el-form-item>
                                        <el-form-item label="Project Name:">
                                            <el-input v-model="dialogform.projectname" />
                                        </el-form-item>
                                        <el-form-item label="Project Description:">
                                            <el-input autosize type="textarea" v-model="dialogform.projectdescriptions" />
                                        </el-form-item>

                                        <!-- <el-form-item label="Zones" :label-width="formLabelWidth">
                                                    <el-select v-model="form.region" placeholder="Please select a zone">
                                                        <el-option label="Zone No.1" value="shanghai" />
                                                        <el-option label="Zone No.2" value="beijing" />
                                                    </el-select>
                                                </el-form-item> -->
                                    </el-form>
                                    <template #footer>
                                        <span class="dialog-footer">
                                            <el-button @click="dialogFormVisible = false">Cancel</el-button>
                                            <el-button type="primary" @click="confirmUpdateProject">
                                                Confirm
                                            </el-button>
                                        </span>
                                    </template>
                                </el-dialog>
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
import { onMounted, reactive, ref, computed } from 'vue';
import { request } from '../request';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import SideNav from './components/SideNav.vue'
import { useUserStore } from '../store/userStore';
import { usePaperInfoStore } from '../store/paperinfoStore';
import { useProjectsStore } from '../store/projectsStore'
import { useRouter } from 'vue-router';
import { useCurrentProject } from '../store/currentProject';
import { ElNotification } from 'element-plus'
import { CheckLoginStatus, getUserInfoByToken } from '../common'

const router = useRouter();
const currentProjectStore = useCurrentProject()
const userStore = useUserStore()
const projectList = reactive([]);
const modelLists = reactive([]);
const CALists = reactive([]);
const projectsStore = useProjectsStore()
const dialogTableVisible = ref(false)
const dialogFormVisible = ref(false)
const dialogform = reactive({
    projectid: '',
    projectname: '',
    projectdescriptions: '',
    lastupdatedtime: '',
    createdtime: ''


})

const showdialog = (project) => {
    // console.log(model)
    dialogFormVisible.value = true
    dialogform.projectid = project.projectid
    dialogform.projectname = project.projectname
    dialogform.projectdescriptions = project.projectdescriptions
    dialogform.createdtime = project.createdtime

}

const confirmUpdateProject = () => {
    // console.log(dialogform)
    // 获取当前时刻的Date对象
    const currentDate = new Date();

    // 将Date对象转换为ISO 8601标准的时间戳字符串
    const currentISO8601Timestamp = currentDate.toISOString();

    dialogform.lastupdatedtime = currentISO8601Timestamp
    // console.log(dialogform)

    request({
        url: '/tools/projects/updateProject',
        method: 'POST',
        data: dialogform
    }).then((res) => {
        if (res.UpdateStatus == 'success!') {
            ElNotification({
                title: 'Update Success!',
                message: 'please check the results',
                type: 'success',
            })
            // console.log(res)
            // dialogform.modelid = model.modelid
            // dialogform.modelname = model.modelname
            // dialogform.modeldescriptions = model.modeldescriptions
            // dialogform.modelcontent = model.modelcontent
            // dialogform.createdtime = model.createdtime

            dialogFormVisible.value = false
        }
    }).catch((error) => {
        // console.log(error)
        ElNotification({
            title: 'Update Error!',
            message: 'please check the results',
            type: 'Error',
        })
    })

}
// testAPI通过userID查询该User下所有的projects
// 又遍历每一个project，得到每一个project对应的多个models
// 又遍历每一个model，得到每一个model生成的CA
const listAllProjectsByUserID = async () => {
    try {
        // 获取用户当前所有的projects
        const projectsRes = await request({
            method: "POST",
            url: '/tools/projects/listProjectByUserID',
            data: {
                userID: userStore.UserID
            }
        });
        // PaperInfoStore.paperinfos.push(...res.res.records)
        projectList.length = 0
        projectList.splice(0, projectList.length, ...projectsRes.projectList);
        // projectsStore.projectList.length=0
        // projectsStore.projectList.push(projectList)
        projectsStore.projectList = projectList
        // console.log(" projectsStore.projectList", projectsStore.projectList)
        // 将ISO 8601格式的时间戳转换为Date对象


        for (let i = 0; i < projectsStore.projectList.length; i++) {

            const timestamp_created = projectsStore.projectList[i].createdtime
            const timestamp_lastupdated = projectsStore.projectList[i].lastupdatedtime
            const dateObject_created = new Date(timestamp_created);
            const dateObject_lastupdated = new Date(timestamp_lastupdated);

            // 获取可读的时间字符串
            projectsStore.projectList[i].createdtimeFormat = dateObject_created.toLocaleString();
            projectsStore.projectList[i].lastupdatedtimeFormat = dateObject_lastupdated.toLocaleString();
        }

        // if (projectList.length !== 0) {
        //     // console.log("projectList", projectList);
        //     // console.log("获取每个projectid对应的model");

        //     // 对projectList中每个project，获取该project下的所有model
        //     modelLists.length = 0
        //     for (const project of projectList) {
        //         const modelsRes = await request({
        //             method: "POST",
        //             url: '/tools/models/listModelsByProjectID',
        //             data: {
        //                 projectid: project.projectid,

        //             }
        //         });

        //         modelLists.push(modelsRes.models);
        //     }
        // }

        // // console.log("modelLists", modelLists);

        // if (modelLists.length !== 0) {
        //     // 对于每一个model，获取它所有的CA
        //     // console.log("对每一个model获取它的CA");
        //     CALists.length = 0
        //     for (const models of modelLists) {
        //         for (const model of models) {
        //             const caRes = await request({
        //                 method: "POST",
        //                 url: '/tools/coveringarrays/listCAsByModelID',
        //                 data: {
        //                     modelid: model.modelid
        //                 }
        //             });

        //             // console.log("对每一个model获取它的CA!!", caRes);
        //             if (caRes.CAs.length != 0) CALists.push(caRes)
        //         }
        //     }
        // }




    } catch (error) {
        console.error("发生错误", error);
    }
    // console.log("projectList", projectList)
    // console.log("modelLists", modelLists)
    // console.log("CALists", CALists)
};

const enterProject = (project) => {
    currentProjectStore.createdtime = project.createdtime
    currentProjectStore.lastupdatedtime = project.lastupdatedtime
    currentProjectStore.projectdescriptions = project.projectdescriptions
    currentProjectStore.projectid = project.projectid
    currentProjectStore.projectname = project.projectname
    currentProjectStore.userid = project.userid
    router.push({
        path: '/tools/models',
        query: {
            projectid: project.projectid,
            projectname: project.projectname
        }
    })
}
const itemsPerRow = ref(2);
const chunkedArray = computed(() => {
    const result = [];
    for (let i = 0; i < projectsStore.projectList.length; i += itemsPerRow.value) {
        result.push(projectsStore.projectList.slice(i, i + itemsPerRow.value));
    }
    return result;
});

onMounted(async () => {
    // 先检查用户是否登录
    let obj = await CheckLoginStatus()
    if (obj.loginStatus) {

        // 若用户已登录，则获取用户信息！
        let userobj = await getUserInfoByToken(obj.token)
        userStore.userID = userobj.userid
        userStore.account = userobj.account
        userStore.userToken = userobj.userToken
        userStore.usertype = userobj.usertype
        userStore.name = userobj.name
        userStore.email = userobj.email
        userStore.institution = userobj.institution

        // 若用户已登录则正常运行
        listAllProjectsByUserID()


    }
    else {
        // 若用户未登录则跳转到登录页面
        router.push(
            {
                name: 'UserLogin'
            }
        )
    }


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
