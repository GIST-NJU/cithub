<template>
  <div class="min-height-300 bg-primary position-absolute w-100"></div>
  <SideNav></SideNav>
  <main class="main-content position-relative border-radius-lg ">
    <Navbar></Navbar>

    <div class="container-fluid py-4">
      <div class="row">
        <div class="col-12">
          <div class="card mb-4" style="height:100%">
            <h3 style="margin: 20px 0px 0px 40px;">Research Topics</h3>
            <div class="card-header pb-0">
              <div style="display:flex;flex-direction: row;;">
                <div v-for="(category, index) in CategoryArray_1" class="category"
                  @click=selectCategory(category.category)>
                  <el-card class="box-card" style="height: 100%;">
                    <template #header>
                      <div class="card-header" style="padding: 10px 0px 0px 10px;">
                        <h5>{{ category.category }}</h5>
                      </div>
                    </template>
                    <div class="categoryDes">{{ category.des }}
                    </div>
                  </el-card>
                </div>
              </div>
              <div style="display:flex;flex-direction: row;;">
                <div v-for="(category, index) in CategoryArray_2" class="category"
                  @click=selectCategory(category.category)>
                  <el-card class="box-card" style="height: 100%;">
                    <template #header>
                      <div class="card-header" style="padding: 10px 0px 0px 10px;">
                        <h5>{{ category.category }}</h5>
                      </div>
                    </template>
                    <div class="categoryDes">{{ category.des }}
                    </div>
                  </el-card>
                </div>
              </div>

              <div style="display:flex;flex-direction: row;;">
                <div v-for="(category, index) in CategoryArray_3" class="category"
                  @click=selectCategory(category.category)>
                  <el-card class="box-card" style="height: 100%;">
                    <template #header>
                      <div class="card-header" style="padding: 10px 0px 0px 10px;">
                        <h5>{{ category.category }}</h5>
                      </div>
                    </template>
                    <div class="categoryDes">{{ category.des }}
                    </div>
                  </el-card>
                </div>


                <div class="category">

                </div>


                <div class="category">

                </div>
              </div>















            </div>


            <div style="height: 500px;"></div>
          </div>
        </div>
      </div>

      <Foot></Foot>

    </div>
  </main>
</template>

<script  setup>
import SideNav from './components/SideNav.vue';
import Navbar from '../ComponentCommon/Navbar.vue';

import Foot from '../ComponentCommon/Foot.vue';
import { useInstitutionStore } from '../store/institutionStore'
import { onMounted } from 'vue';
import { ref, computed, reactive } from 'vue';
import { usePaperInfoStore } from '../store/paperinfoStore'
import { request } from '../request';
import { useRouter } from 'vue-router';
const router = useRouter();
const PaperInfoStore = usePaperInfoStore()

const CategoryArray_1 = reactive([
  { "category": "Model", "des": "Studies on identifying parameters, values, and the interrelations of parameters of software under test. It is the fundamental activity of combinatorial testing." },
  { "category": "Generation", "des": "Studies on generating as small test suites as possible. It is the most popular field in combinatorial testing. The many proposed techniques consist of mathematical methods, greedy methods, and search based methods." },
  { "category": "Optimization", "des": "Studies on improving the test suite by prioritization, minimization and selection techniques." },

])
const CategoryArray_2 = reactive([

  { "category": "Fault Diagnosis", "des": "Studies on locating the concrete failure causing interactions." },
  { "category": "Evaluation", "des": "Studies on measuring the effectiveness of CT and comparing CT with other testing methods." },
  { "category": "Application", "des": "Studies on applying, improving, and popularizing CT and its procedures in real world." },

])
const CategoryArray_3 = reactive([

  { "category": "Other", "des": "Studies that do not belong to any of the above categories." }
])

let searchObj = reactive({
  pagesize: 200,
  total: '',
  pagercount: 15,
  pagenum: 1,
  searchkeywords: "",
  typerofPapers: PaperInfoStore.TypeofPapers,

})

const selectCategory = (category) => {
  if (category == "Fault Diagnosis") category = "Diagnosis"
  searchObj.searchkeywords = category
  PaperInfoStore.searchKeyWords = category
  PaperInfoStore.paperinfos.length = 0
  request({
    url: 'repo/list/searchByField',
    method: 'POST',
    data: searchObj
  }).then((res) => {
    // console.log("searchByInstitutions", res)
    PaperInfoStore.paperinfos.push(...res.res.records)
    PaperInfoStore.total = res.res.total
  }).catch(() => { })

  router.push({
    path: '/repo/Papers',
    query: {
      paginationActive: '关闭'
    }

  })


}

</script>



<style scoped>
.col span {
  transition: font-size 0.3s ease;
  /* 添加过渡效果 */
}

.col:hover span {
  font-size: calc(15px + 5px);
  /* 这里使用 calc 计算新的 font-size，16px 为默认值 */
  cursor: pointer;
  color: rgb(94, 114, 228);
  text-decoration: underline;
}

.category {
  flex: 1;
  margin: 10px;
  transition: transform 0.3s ease;
  /* 添加过渡效果 */
}

.category:hover {
  transform: scale(1.1);
  /* 划过时放大 */
  cursor: pointer;
}


.categoryDes {
  font-size: larger;
}</style>
