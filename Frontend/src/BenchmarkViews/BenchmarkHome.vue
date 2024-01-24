<template>
    <div class="min-height-300 bg-info position-absolute w-100"></div>
    <SideNav></SideNav>
    <main class="main-content position-relative border-radius-lg ">
        <!-- Navbar -->
        <Navbar></Navbar>
        <!-- End Navbar -->

        <div class="container-fluid py-4">

            <h1>BENCHmark</h1>
            <Foot></Foot>
        </div>
    </main>
</template>

<script setup>
import { reactive } from 'vue';
import { request } from '../request';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import SideNav from './components/SideNav.vue'

const userID = 1;
const projectList = reactive([]);
const modelLists = reactive([]);
const CALists = reactive([]);


// testAPI通过userID查询该User下所有的projects
// 又遍历每一个project，得到每一个project对应的多个models
// 又遍历每一个model，得到每一个model生成的CA
const testAPI = async () => {
    try {
        // 获取用户当前所有的projects
        const projectsRes = await request({
            method: "POST",
            url: '/tools/projects/listProjectByUserID',
            data: {
                userID: userID
            }
        });
        projectList.length = 0
        projectList.splice(0, projectList.length, ...projectsRes.projectList);

        if (projectList.length !== 0) {
            // console.log("projectList", projectList);
            // console.log("获取每个projectid对应的model");

            // 对projectList中每个project，获取该project下的所有model
            modelLists.length = 0
            for (const project of projectList) {
                const modelsRes = await request({
                    method: "POST",
                    url: '/tools/models/listModelsByProjectID',
                    data: {
                        projectid: project.projectid
                    }
                });

                modelLists.push(modelsRes.models);
            }
        }

        // console.log("modelLists", modelLists);

        if (modelLists.length !== 0) {
            // 对于每一个model，获取它所有的CA
            // console.log("对每一个model获取它的CA");
            CALists.length = 0
            for (const models of modelLists) {
                for (const model of models) {
                    const caRes = await request({
                        method: "POST",
                        url: '/tools/coveringarrays/listCAsByModelID',
                        data: {
                            modelid: model.modelid
                        }
                    });

                    // console.log("对每一个model获取它的CA!!", caRes);
                    if (caRes.CAs.length != 0) CALists.push(caRes)
                }
            }
        }




    } catch (error) {
        console.error("发生错误", error);
    }
    console.log("projectList", projectList)
    console.log("modelLists", modelLists)
    console.log("CALists", CALists)
};
</script>

<style scoped></style>
