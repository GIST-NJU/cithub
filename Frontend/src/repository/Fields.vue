<template>
  <div class="min-height-300 bg-primary position-absolute w-100"></div>
  <SideNav></SideNav>
  <main class="main-content position-relative border-radius-lg ">
    <Navbar></Navbar>

    <div class="container-fluid py-4">
      <div class="row">
        <!-- Research Fields -->
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-header pb-0">
              <h3>Research Fields</h3>
              <p class="text-muted">Each paper is classified into one of the following research fileds, according to its primary contribution.</p>
            </div>
            <div class="card-body">
              <div class="row">
                <div v-for="(each, index) in FiledsInfo" class="col-lg-3 col-sm-6 col-xs-12 card-fields">
                  <div class="card mb-4">
                    <div class="card-body">
                      <h5 class="card-title">{{ each.name }}</h5>
                      <p class="card-text">{{ each.description }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <!-- Topics -->
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-header pb-0">
              <h3>Topics</h3>
              <p class="text-muted">Each paper is assgined a number of tags that are relevant to its research topics.
              </p>
            </div>
            <div class="card-body">
              <dl class="row">
                <dt class="col-sm-1">Topics 1</dt>
                <dd class="col-sm-11">Description ...</dd>
              </dl>
            </div>
          </div>
        </div>
      </div> <!-- end row -->
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
import { useModuleStore } from '../store/module';
import pinia from '../store/store'
const router = useRouter();
const moduleStore = useModuleStore(pinia)
const PaperInfoStore = usePaperInfoStore(pinia)


const FiledsInfo = reactive([
  { 
    "name": "Model", 
    "description": "Studies on identifying parameters, values, constraints, and potential interrelations of software system under test." 
  },
  { 
    "name": "Generation", 
    "description": "Studies on generating as small test suites as possible. Typicall methods include mathematical methods, greedy methods, and search based methods." 
  },
  { 
    "name": "Optimization",
    "description": "Studies on improving the test suite by prioritization, minimization and selection techniques." 
  },
  {
    "name": "Fault Diagnosis", 
    "description": "Studies on locating the concrete failure causing interactions." 
  },
  { 
    "name": "Evaluation", 
    "description": "Studies on measuring the effectiveness of CIT and comparing CIT with other testing methods."
  },
  { 
    "name": "Application", 
    "description": "Studies on applying, improving, and popularizing CT and its procedures in real world." 
  }
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

onMounted(async () => {
  moduleStore.CurrentModule = 'Fields'
})
</script>



<style scoped>
.card-fields:hover {
  transform: scale(1.03);
  cursor: pointer;
}
</style>
