
<template>
  <div class="min-height-300 bg-info position-absolute w-100"></div>
  <SideNav></SideNav>
  <main class="main-content position-relative border-radius-lg ">
    <Navbar></Navbar>

    <div class="container-fluid py-4">

      <div class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-header pb-0">


              <!-- 2. search Model  -->
              <div class="row mb-4">
                <div class="col-12">
                  <h5>Find Model</h5>
                  <div class="row pt-2">
                    <div class="col-8">
                      <input v-model="PaginationStore.searchkeywords" type="text" class="form-control"
                        placeholder="Fuzzy search by Name of Models, Benchmark Set, Reference , ...">
                    </div>
                    <div class="col-3">
                      <ArgonButton color="success" full-width @click="FuzzySearch">
                        <span class="bi bi-search"></span>
                        Search
                      </ArgonButton>
                    </div>
                    <div class="col-1">
                      <ArgonButton color="secondary" full-width @click="clearSearch">
                        <span class="bi bi-x-circle"></span>
                        Clear
                      </ArgonButton>
                    </div>

                  </div>
                </div>
              </div>

              <h4 class="mb-4"
                v-if="moduleStore.CurrentModule == 'Benchmark Models' && BenchmarkModels.searchKeyWords == ''">Complete
                Benchmark Models List</h4>
              <div v-if="BenchmarkModels.searchKeyWords != ''" class="mb-2">
                <!-- <ArgonButton color="secondary" size="sm" @click="goBack">
                  <span class="ni ni-bold-left  me-1"> Back</span>
                </ArgonButton> -->
                <h4 class="mt-2">Search KeyWords : <span style="color: #2dce89">{{
                  BenchmarkModels.searchKeyWords }}</span>
                </h4>
              </div>


              <p class="text-muted "><span class="badge bg-info">{{ BenchmarkModels.total }}</span> Combinatorial
                Testing Models found</p>

            </div>

            <div class="card-body ps-1">
              <div>
                <table class="table mb-0">
                  <tbody>

                    <!-- Models Card here -->
                    <Model v-for="(item, index) in BenchmarkModels.models" :key="index" :item="item"
                      :index="index + BenchmarkModels.paginationOffset" :userInfo="userInfo"></Model>

                  </tbody>
                </table>
              </div>
            </div>

            <!-- 分页 -->
            <div v-if="BenchmarkModels.models.length != 0" style="display:flex;justify-content: center;">
              <div>
                <el-pagination v-model:page-size="PaginationStore.pagesize"
                  v-model:pager-count="PaginationStore.pagecount" layout="prev, pager, next, jumper"
                  :total="PaginationStore.total" @current-change="handleCurrentPageChange" />
              </div>
            </div>


            <div style="margin-top: 30px;"></div>

            <div style="width:100%;height: 100%;" v-if="BenchmarkModels.models.length == 0">
              <el-result style="width:100%;height: 100%;" icon="error" :title="'No Models Found.'"
                sub-title="please check your search keywords">
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
import * as echarts from 'echarts'
import { onMounted, reactive, ref, watch } from 'vue';
import { Search } from '@element-plus/icons-vue'
import { ElLoading } from 'element-plus'
import SideNav from './components/SideNav.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import Foot from '../CustomizedComponents/Foot.vue';
import { request } from '../request'
import { useRoute, useRouter } from 'vue-router'
import ArgonBadge from '../CustomizedComponents/ArgonBadge.vue'
import ArgonInput from '../CustomizedComponents/ArgonInput.vue';
import ArgonButton from '../CustomizedComponents/ArgonButton.vue';
import Model from './components/ModelCard.vue'
import pinia from '../store/store';
import { useModuleStore } from '../store/module';
import { usePaginationStore } from '../store/RepositoryStore/paginationStore'
import { useBenchmarkModelsStore } from '../store/BenchmarkStore/BenchmarkModelsStore'
import { listAllBenchmarkModels, searchModel, clearSearch } from './commonFunction'
const PaginationStore = usePaginationStore(pinia)
const BenchmarkModels = useBenchmarkModelsStore(pinia)

const moduleStore = useModuleStore(pinia)
const router = useRouter()



const goBack = () => {
  window.history.back();
}



const handleCurrentPageChange = async (val) => {
  let loadingInstance = ElLoading.service({ fullscreen: true });
  PaginationStore.pagenum = val;
  // 搜索翻页
  if (PaginationStore.searchkeywords != '') {
    console.log("搜索翻页")
    try {

      const res = await request({
        url: 'benchmark/search',
        method: 'POST',
        data: {
          pagenum: PaginationStore.pagenum,
          pagesize: PaginationStore.pagesize,
          searchkeywords: PaginationStore.searchkeywords,
        }
      });

      // console.log("res", res);
      BenchmarkModels.models.length = 0;
      BenchmarkModels.models.push(...res.res.records);
      PaginationStore.pagenum = res.res.current;
      PaginationStore.total = res.res.total;

      BenchmarkModels.total = res.res.total;

      // 计算偏移量
      const paginationOffset = (PaginationStore.pagenum - 1) * PaginationStore.pagesize;
      // console.log("计算偏移量偏移量",paginationOffset)
      // 更新全局偏移量
      BenchmarkModels.paginationOffset = paginationOffset;

      loadingInstance.close();

    } catch (error) {
      loadingInstance.close();


    }
  }
  // 普通翻页
  else {
    try {
      const res = await request({
        url: 'benchmark/listBenchmarkModels',
        method: 'POST',
        data: {
          pagenum: PaginationStore.pagenum,
          pagesize: PaginationStore.pagesize,

        }
      });

      // console.log("handleCurrentPageChange中的res", res);
      BenchmarkModels.models.length = 0;
      BenchmarkModels.models.push(...res.res.records);

      PaginationStore.pagenum = res.res.current;

      // 计算偏移量
      const paginationOffset = (PaginationStore.pagenum - 1) * PaginationStore.pagesize;
      // console.log("计算偏移量偏移量",paginationOffset)
      // 更新全局偏移量
      BenchmarkModels.paginationOffset = paginationOffset;



      loadingInstance.close();
    } catch (error) {
      loadingInstance.close();
    }

  }
}


// 模糊搜索
const FuzzySearch = async() => {
  let loadingInstance = ElLoading.service({ fullscreen: true });
  PaginationStore.column = ''
  moduleStore.CurrentModule = 'Benchmark Search'

  await searchModel();
  loadingInstance.close();
}

onMounted(async () => {

  let loadingInstance = ElLoading.service({ fullscreen: true })
  if (BenchmarkModels.searchKeyWords == '') {
    await listAllBenchmarkModels()

  }
  loadingInstance.close()


})


</script>


<style scoped></style>
