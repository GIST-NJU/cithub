
<template>
  <div class="min-height-300 bg-primary position-absolute w-100"></div>
  <SideNav></SideNav>
  <main class="main-content position-relative border-radius-lg ">
    <Navbar></Navbar>

    <div class="container-fluid py-4">
      <div class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-header pb-0">
              <h3>Complete Paper List</h3>
              <p class="text-muted mb-0"><span class="badge bg-success">{{ PaperInfoStore.total }}</span> papers found</p>
            </div>

            <div class="card-body ps-1">
              <div class="table-responsive">
                <table class="table mb-0">
                  <tbody>
                    <Paper v-for="(item,index) in PaperInfoStore.paperinfos" :key="index" :item="item" :index="index" :userInfo="userInfo"
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
            layout="prev, pager, next, jumper" :total="paginationObj.total" @current-change="handleCurrentPageChange" />
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
import Paper from './components/PaperElement.vue';
import PaperInfo from './components/PaperInformation.vue';
import SideNav from './components/SideNav.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import Foot from '../ComponentCommon/Foot.vue';
import { request } from '../request'
import { useRoute } from 'vue-router'
import { usePaperInfoStore } from '../store/paperinfoStore'
import ArgonBadge from './components/ArgonBadge.vue'
import pinia from '../store/store';
import { listAllPapers, listAllScholars, listAllInstitutions, listallVenue } from './commonFunction';

const PaperInfoStore = usePaperInfoStore(pinia)

import { useModuleStore } from '../store/module';
const moduleStore = useModuleStore(pinia)

// 接收参数
const route = useRoute()


//分页查询的传递的对象 
let paginationObj = reactive({
  pagesize: 30,
  total: PaperInfoStore.total,
  pagecount: 8,
  pagenum: 1,
  searchkeywords: "",
  typerofPapers: "Combinatorial Testing",

})

let paginationActive = ref(true)

let isPageActive = ref(false)

const handleMouseOverPagiantion = () => { isPageActive.value = true; }
const handleMouseOutPagiantion = () => { isPageActive.value = false }

let countAllPapers = ref(0)



onMounted(() => {
  moduleStore.CurrentModule = 'Complete Paper List'

  // TODO: 打开该页面时发送 HTTP request 请求论文列表

  // TODO: 用英文 variable 和英文注释
  // 关闭分页

  // 需要关闭分页，说明是通过搜索切换到Papers
  if (route.query.paginationActive == '关闭') {
    console.log("关闭分页")
    paginationActive.value = false
  }
    // 不需要关闭分页，说明是初始化papers
  else{
    // 获取所有paper
    console.log("获取所有paper")
    listAllPapers(paginationObj)
  }

    // // 获取所有scholars
    // listAllScholars()
    // // 获取所有institutions
    // listAllInstitutions()
    // // 获取所有Venue
    // listallVenue()

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
