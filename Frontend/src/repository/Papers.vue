
<template>
  <div class="min-height-300 bg-primary position-absolute w-100"></div>
  <SideNav></SideNav>
  <main class="main-content position-relative border-radius-lg ">
    <Navbar></Navbar>

    <div class="container-fluid py-4">

      <div v-if="route.query.module == 'Scholars'" class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-header pb-0">
              <h1>card1</h1>
            </div>




            <div class="card-body ps-1">
              <div class="table-responsive">
                <table class="table mb-0">
                  <tbody>
                    <h1>card1</h1>
                  </tbody>
                </table>
              </div>
            </div>



          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-header pb-0">
              <h3 v-if="moduleStore.CurrentModule == 'Complete Paper List'">Complete Paper List</h3>
              <!-- search author -->
              <h3 v-if="route.query.module == 'Scholars'">{{ currentAuthorStore.CurrentAuthor.name }}'s Papers </h3>
              <div>
                <!-- <p v-if="route.query.module == 'Scholars'" class="text-muted mb-0"><span>Scholar :</span> <span>{{
                  currentAuthorStore.CurrentAuthor.name }} </span> </p> -->
                <p v-if="route.query.module == 'Scholars'" class="text-muted mb-0"><span> Institution : </span> <span>{{
                  currentAuthorStore.CurrentAuthor.institution }} , {{ currentAuthorStore.CurrentAuthor.country }}</span>
                </p>
              </div>

              <!-- search field -->
              <div v-if="route.query.module == 'Fields'">
                <h3>Papers from Research Field of <span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span>
                </h3>
              </div>

              <!-- search Tag -->
              <div v-if="route.query.module == 'Tag'">
                <h3>Papers related to <span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span></h3>
              </div>

              <!-- search institution -->
              <div v-if="route.query.module == 'Institutions'">
                <h3> <span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span> 's Papers
                </h3>
              </div>

              <!-- search country -->
              <div v-if="route.query.module == 'Country'">
                <h3><span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span>'s Papers
                </h3>
              </div>


              <!-- search Venue -->
              <div v-if="route.query.module == 'Venues'">
                <h3>Papers published in <span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span>
                </h3>
              </div>

              <!-- search Keywords -->
              <div v-if="route.query.module == 'Search'">
                <h3>Search KeyWords : <span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span>
                </h3>
              </div>

              <p class="text-muted mb-0"><span class="badge bg-success">{{ PaperInfoStore.total }}</span> papers found
              </p>
            </div>




            <div class="card-body ps-1">
              <div class="table-responsive">
                <table class="table mb-0">
                  <tbody>
                    <Paper v-for="(item, index) in PaperInfoStore.paperinfos" :key="index" :item="item"
                      :index="index + PaperInfoStore.paginationOffset" :userInfo="userInfo"></Paper>
                  </tbody>
                </table>
              </div>
            </div>

            <!-- 分页 -->
            <!-- <div v-if="PaperInfoStore.paperinfos.length != 0 && paginationActive == true" -->
            <div v-if="PaperInfoStore.paperinfos.length != 0" style="display:flex;justify-content: center;">
              <div>
                <el-pagination v-model:page-size="PaginationStore.pagesize"
                  v-model:pager-count="PaginationStore.pagecount" layout="prev, pager, next, jumper"
                  :total="PaginationStore.total" @current-change="handleCurrentPageChange" />
              </div>
            </div>
            
            <div v-else style="margin-top: 60px;">
            </div>

            <div style="margin-top: 30px;"></div>
          </div>
        </div>
      </div>

      <div style="width:100%;height: 100%;"
        v-if="PaperInfoStore.paperinfos.length == 0 && PaginationStore.searchkeywords != ''">
        <el-result style="width:100%;height: 100%;" icon="warning" :title="'No Papers Found.'"
          sub-title="please check your search keywords">
        </el-result>
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
import { useCurrentAuthorStore } from '../store/currentAuthorStore'
import { useModuleStore } from '../store/module';
import { usePaginationStore } from '../store/paginationStore'
const PaginationStore = usePaginationStore(pinia)
const currentAuthorStore = useCurrentAuthorStore(pinia)
const PaperInfoStore = usePaperInfoStore(pinia)
const moduleStore = useModuleStore(pinia)

// 接收参数
const route = useRoute()






const handleCurrentPageChange = async (val) => {
  let loadingInstance = ElLoading.service({ fullscreen: true });

  PaginationStore.pagenum = val;
  // console.log("当前是第", PaginationStore.pagenum, "页");
  // 通过任意关键词查询的翻页
  // console.log("moduleStore.CurrentModule 是",moduleStore.CurrentModule)
  if (moduleStore.CurrentModule == 'Search') {
    try {
      const searchRes = await request({
        url: "/repo/list/searchPapers",
        method: 'POST',
        data: {
          pagesize: PaginationStore.pagesize,
          searchkeywords: PaginationStore.searchkeywords,
          pagenum: PaginationStore.pagenum,
        }
      })
      // console.log("searchRes", searchRes)
      PaperInfoStore.paperinfos.length = 0;
      PaperInfoStore.paperinfos.push(...searchRes.res.records)

      PaginationStore.pagenum = searchRes.res.current;

      const paginationOffset = (PaginationStore.pagenum - 1) * PaginationStore.pagesize;
      // console.log("计算偏移量偏移量",paginationOffset)
      PaperInfoStore.paginationOffset = paginationOffset;
      // console.log("全局偏移量",PaperInfoStore.paginationOffset)

    } catch (error) {

    }
  }
  // 其他查询的翻译
  else {
    try {
      const res = await request({
        url: '/repo/list/searchBy',
        method: 'POST',
        data: {
          pagenum: PaginationStore.pagenum,
          pagesize: PaginationStore.pagesize,
          searchkeywords: PaginationStore.searchkeywords,
          column: PaginationStore.column
        }
      });

      // console.log("handleCurrentPageChange中的res", res);
      PaperInfoStore.paperinfos.length = 0;
      PaperInfoStore.paperinfos.push(...res.res.records);

      PaginationStore.pagenum = res.res.current;

      // console.log("PaginationStore.pagenum",PaginationStore.pagenum)
      // console.log("PaginationStore.pagesize",PaginationStore.pagesize)
      // 计算偏移量
      const paginationOffset = (PaginationStore.pagenum - 1) * PaginationStore.pagesize;
      // console.log("计算偏移量偏移量",paginationOffset)
      // 更新全局偏移量
      PaperInfoStore.paginationOffset = paginationOffset;

      // console.log("全局偏移量",PaperInfoStore.paginationOffset)

    } catch (error) {
      console.error(error);
    }
  }

  loadingInstance.close();


}

const DetailedModule = ['Scholars', 'Institutions', 'Country', 'Venues', 'Fields', 'Tag', 'Search', 'Repository']
onMounted(async () => {
  let loadingInstance = ElLoading.service({ fullscreen: true })

  if (DetailedModule.includes(moduleStore.CurrentModule) && moduleStore.CurrentModuleDetails != '') {
    // console.log("moduleStore.CurrentModule的值不变依然是", moduleStore.CurrentModule)
  }
  else {
    // console.log("设置为Complete Paper List")
    moduleStore.CurrentModule = 'Complete Paper List'
    await listAllPapers({
      pagenum: PaginationStore.pagenum,
      pagesize: PaginationStore.pagesize,
    })

    // console.log(" moduleStore.CurrentModule", moduleStore.CurrentModule)
  }

  loadingInstance.close()


})
</script>


<style scoped></style>
