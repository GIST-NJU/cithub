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
                        <h3 style="margin: 20px 0px 0px 40px;">Tools Categories</h3>
                        <p style="margin-left: 45px;" class="text-muted text-sm mb-0"> Cithub Provides a variety of tools of Combinatorial Testing.</p>

                        <div class="card-header pb-0">

                        </div>



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

<style scoped></style>
