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
                     
  
                        <ProjectCard :chunkedArray="chunkedArray"></ProjectCard>

                    </div>
                </div>
            </div>


            <Foot></Foot>
        </div>
        <!-- <div class="py-4 container-fluid">
            <div class=" row">
                <div class="col-12">
                    <authors-table />
                </div>
            </div>
            <div class="mt-4 row">
                <div class="col-12">
                    <projects-table />
                </div>
            </div>
           
        </div> -->
        <!-- <ProjectCard></ProjectCard> -->
    </main>
</template>

<script setup>
import { onMounted, reactive, ref, computed } from 'vue';
import { request } from '../request';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import SideNav from './components/SideNav.vue'

import ProjectCard from "./components/ProjectCard.vue"
import { useUserStore } from '../store/userStore';
import { usePaperInfoStore } from '../store/paperinfoStore';
import { useProjectsStore } from '../store/projectsStore'
import { ElNotification } from 'element-plus'
import { CheckLoginStatus, getUserInfoByToken } from '../common'
import { useRouter } from 'vue-router';

const router = useRouter();
const userStore = useUserStore()

const modelLists = reactive([]);
const CALists = reactive([]);
const projectList = reactive([]);
const projectsStore = useProjectsStore()

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
                userID: userStore.userID
            }
        });
        // PaperInfoStore.paperinfos.push(...res.res.records)
        projectList.length = 0
        projectsStore.projectList.length=0
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

// const enterProject = (project) => {
//     currentProjectStore.createdtime = project.createdtime
//     currentProjectStore.lastupdatedtime = project.lastupdatedtime
//     currentProjectStore.projectdescriptions = project.projectdescriptions
//     currentProjectStore.projectid = project.projectid
//     currentProjectStore.projectname = project.projectname
//     currentProjectStore.userid = project.userid
//     router.push({
//         path: '/tools/models',
//         query: {
//             projectid: project.projectid,
//             projectname: project.projectname
//         }
//     })
// }
const itemsPerRow = ref(1);
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
        // 列出该用户的所有project
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

</style>
