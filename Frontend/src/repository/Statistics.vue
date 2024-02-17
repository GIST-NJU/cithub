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
            <div class="card-body pb-0">
              <h3>Statistics</h3>
              <p class="text-muted">The development trend and status of combinatorial interaction testing</p>
            </div>
          </div>
        </div>
      </div>

      <!-- 1 -->
      <div class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-body pb-0">
              <h5>Number of Publications</h5>
              <p class="text-muted">The annual and cumulative number of CIT publications</p>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 2 -->
      <div class="row">
        <div class="col-6">
          <div class="card mb-4">
            <div class="card-body pb-0">
              <h5>Distribution of Fields</h5>
              <p class="text-muted">The distribution of different CIT research fields</p>
            </div>
          </div>
        </div>

        <div class="col-6">
          <div class="card mb-4">
            <div class="card-body pb-0">
              <h5>Development of Fields</h5>
              <p class="text-muted">The relative proportions of each CIT research field for each year</p>
            </div>
          </div>
        </div>
      </div>

      <!-- 3 -->
      <div class="row">
        <div class="col-6">
          <div class="card mb-4">
            <div class="card-body pb-0">
              <h5>Number of Publications</h5>
              <p class="text-muted">The distribution of scholars of combinatorial interaction testing  accross the world</p>
            </div>
          </div>
        </div>

        <div class="col-6">
          <div class="card mb-4">
            <div class="card-body pb-0">
              <h5>Number of Publications</h5>
              <p class="text-muted">The annual and cumulative number of combinatorial testing publications</p>
            </div>
          </div>
        </div>
      </div>

      
      <div class="row">
        <div class="col-12">
          <div class="card mb-4" style="height:100%">
            <h3 style="margin: 20px 0px 0px 40px;">Charts List</h3>
            <div class="card-header pb-0">
              <div style="display:flex;flex-direction: row;;">
                <div v-for="(category, index) in CategoryArray_1" class="category" @click=selectCharts(category.category)>
                  <el-card v-if="category != ''" class="box-card" style="height: 100%;">
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
                <div v-for="(category, index) in CategoryArray_2" class="category" @click=selectCharts(category.category)>
                  <el-card v-if="category != ''" class="box-card" style="height: 100%;">
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
                <div v-for="(category, index) in CategoryArray_3" class="category" @click=selectCharts(category.category)>
                  <el-card v-if="category != ''" class="box-card" style="height: 100%;">
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
  { "category": "Number of Publication", "des": "The annual and cumulative number of combinatorial testing publications." },
  { "category": "Distribution of Field", "des": "The distribution of different combinatorial testing research fields." },
  { "category": "Distributions in the World", "des": "The distribution of scholars and papers of combinatorial testing community all over the world." },

])
const CategoryArray_2 = reactive([
  { "category": "Relative Proportion of Field", "des": "The relative proportions of each combinatorial testing research field for each year." },

  { "category": "Number of Institutions", "des": "The number of new institutions that contribute to combinatorial testing community for each year." },

])
const CategoryArray_3 = reactive([

])

let searchObj = reactive({
  pagesize: 200,
  total: '',
  pagercount: 15,
  pagenum: 1,
  searchkeywords: "",
  typerofPapers: PaperInfoStore.TypeofPapers,

})

const selectCharts = (charts) => {

  router.push({
    name: "ChartsDisplay",
    query: {
      charts
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
}
</style>
