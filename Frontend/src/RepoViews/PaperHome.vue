
<template>
    <div class="min-height-300 bg-primary position-absolute w-100"></div>
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
                            <h3>Complete Paper List</h3>
                            <p class="text-muted text-sm mb-0">{{ PaperInfoStore.total }} papers found</p>
                            <p v-if="PaperInfoStore.searchKeyWords != ''" class="text-muted text-sm mb-0">Looking for 
                                <argon-badge variant="gradient" color="primary" style="margin: 5px">
                                    {{ PaperInfoStore.searchKeyWords }}
                                </argon-badge>
                                
                            </p>
                        </div>


                        <div style="display:flex;justify-content:start;align-items: center;width:80%;">
                            <div style="width:83.5%;margin: 8px 0px 20px 20px;">
                                <el-input v-model="paginationObj.searchkeywords"
                                    placeholder="Please input keywords to search" @keyup.enter.native="searchPapers"
                                    clearable />
                            </div>
                            <div style="margin: 0 0 10px 10px;">
                                <el-button :icon="Search" circle @click="searchPapers" />
                            </div>
                        </div>

                        <div class="card-body px-0 pt-0 pb-2">
                            <div class="table-responsive pt-4">
                                <table class="table align-items-center mb-0">
                                    <tbody>
                                        <Paper v-for="(item) in PaperInfoStore.paperinfos" :item="item" :userInfo="userInfo"
                                            @DeleteCard="DeleteCard"></Paper>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="width:100%;height: 100%;"
                v-if="PaperInfoStore.paperinfos.length == 0 && paginationObj.searchkeywords != ''">
                <el-result style="width:100%;height: 100%;" icon="warning" :title="'No Papers Found.'"
                    sub-title="please check your search keywords">
                </el-result>
            </div>
            <!-- 分页 -->
            <div v-if="PaperInfoStore.paperinfos.length != 0 && paginationActive == true"
                style="display:flex;justify-content: center;">
                <div>
                    <el-pagination v-model:page-size="paginationObj.pagesize" v-model:pager-count="paginationObj.pagecount"
                        layout="prev, pager, next, jumper" :total="paginationObj.total"
                        @current-change="handleCurrentPageChange" />
                </div>

            </div>
            <div v-else style="margin-top: 60px;">
            </div>

            <Foot></Foot>

        </div>
    </main>
</template>

<script setup>

import { onMounted, reactive, ref, watch } from 'vue';
import { Search } from '@element-plus/icons-vue'
import { ElLoading } from 'element-plus'
import Paper from './components/Paper.vue';
import SideNav from './components/SideNav.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import Foot from '../ComponentCommon/Foot.vue';
import { request } from '../request'
import { useRoute } from 'vue-router'
import { usePaperInfoStore } from '../store/paperinfoStore'
import ArgonBadge from './components/ArgonBadge.vue'
const PaperInfoStore = usePaperInfoStore()

// 接收参数
const route = useRoute()


//分页查询的传递的对象 
let paginationObj = reactive({
    pagesize: 30,
    total: PaperInfoStore.total,
    pagecount: 15,
    pagenum: 1,
    searchkeywords: "",
    typerofPapers: "Combinatorial Testing",

})

let paginationActive = ref(true)

let isPageActive = ref(false)

const handleMouseOverPagiantion = () => { isPageActive.value = true; }
const handleMouseOutPagiantion = () => { isPageActive.value = false }

let countAllPapers=ref(0)

const listAllPapers = () => {
    // 获取所有文章的数据
    request({
        url: '/repo/list/listAllpapers',
        method: 'POST',
        data: paginationObj
    }).then((res) => {
        // console.log(res)
        // PaperInfoStore.paperinfos.length = 0
        // PaperInfoStore.paperinfos.push(...res.listEntityPage.records)

        countAllPapers.value = res.listEntityPage.total
        // PaperInfoStore.total = paginationObj.total

        // console.log(paginationObj)

    }).catch((error) => {
        console.log("错误是", error)
    })
}
onMounted(() => {
    
    // console.log("route.query.paginationActive", route.query.paginationActive)

    // 关闭分页
    if (route.query.paginationActive == '关闭') {
        // console.log("关闭分页")
        paginationActive.value = false
    }

    // console.log("route.query.paginationActive", route.query.paginationActive)
    // console.log("paginationActive.value", paginationActive.value)


})
const handleCurrentPageChange = (val) => {
    let loadingInstance = ElLoading.service({ fullscreen: true })

    paginationObj.pagenum = val
    console.log("当前是第", paginationObj.pagenum, "页")
    request({
        url: '/repo/list/listAllpapers',
        method: 'POST',
        data: paginationObj
    }).then((res) => {
        console.log(res)
        PaperInfoStore.paperinfos.length = 0;
        PaperInfoStore.paperinfos.push(...res.listEntityPage.records)

        paginationObj.pagenum = res.listEntityPage.current
        if (PaperInfoStore.paperinfos.length != 0) {
            setTimeout(() => {
                loadingInstance.close()

            }, 300)
        }
    }).catch((error) => { })
}

const searchPapers = () => {
    // console.log("搜索关键词是:", paginationObj.searchkeywords)
    let loadingInstance = ElLoading.service({ fullscreen: true })


    request({
        url: '/repo/list/searchPapers',
        method: 'POST',
        data: paginationObj,
    }).then((res) => {
        // console.log("搜索的res是", res)
        PaperInfoStore.paperinfos.length = 0
        PaperInfoStore.paperinfos.push(...res.res.records)
        if (PaperInfoStore.paperinfos.length != 0) {

            loadingInstance.close()

        }
        if (res.res.records.length == 0) loadingInstance.close()
        paginationObj.total = res.res.total
    }).catch(
        (errors) => {

        }
    )
}
</script>


<style scoped></style>
