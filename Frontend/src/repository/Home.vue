
<template>
  <div class="min-height-300 bg-primary position-absolute w-100"></div>
  <SideNav></SideNav>
  <main class="main-content position-relative border-radius-lg ">
    <Navbar></Navbar>

    <div class="container-fluid py-4">
      <!-- 1. basic statistic done -->
      <div class="row">
        <div class="col-xl-4 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="mb-1 font-weight-bolder text-lg">Research Papers</p>
                    <h5 class="font-weight-bolder">{{ totalPapersCount }}</h5>
                    <!-- <i class="fa fa-arrow-up text-info"></i>
                    <span class="text-info font-weight-bolder">+3%</span> since last year -->
                  </div>
                </div>
                <div class="col-4 text-end">
                  <div class="icon icon-shape bg-gradient-primary shadow-primary text-center rounded-circle">
                    <i class="ni ni-money-coins text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-4 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="mb-1 font-weight-bolder text-lg">Scholars</p>
                    <h5 class="font-weight-bolder">{{ totalScholarsCount }}</h5>
                    <!-- <i class="fa fa-arrow-up text-info"></i>
                    <span class="text-info font-weight-bolder">+3%</span> since last year -->
                  </div>
                </div>
                <div class="col-4 text-end">
                  <div class="icon icon-shape bg-gradient-danger shadow-danger text-center rounded-circle">
                    <i class="ni ni-world text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-4 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="mb-1 font-weight-bolder text-lg">Institutions</p>
                    <h5 class="font-weight-bolder">{{ totalInstitutionsCounth }}</h5>
                    <!-- <i class="fa fa-arrow-up text-info"></i>
                    <span class="text-info font-weight-bolder">+3%</span> since last year -->

                  </div>
                </div>
                <div class="col-4 text-end">
                  <div class="icon icon-shape bg-gradient-success shadow-success text-center rounded-circle">
                    <i class="ni ni-paper-diploma text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 2. search form done -->
      <div class="row mt-4">
        <div class="col-12">
          <div class="card">
            <div class="card-body pb-0">
              <h5>Find Papers</h5>
              <div class="row pt-2">
                <div class="col-10">
                  <input v-model="PaginationStore.searchkeywords" type="text" class="form-control"
                    placeholder="Name of paper, scholar, publication venue, ...">
                </div>
                <div class="col-2">
                  <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary" @click="searchPapers">Search</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 3. other information  -->
      <div class="row mt-4">
        <!-- 3.1 cumulative number of publications done -->
        <div class="col-lg-8 mb-lg-0 mb-4">
          <div class="card z-index-2 h-100">
            <div class="card-header pt-3 bg-transparent">
              <h5>Cumulative Number of Publications</h5>
              <p class="text-muted">From 1985 to 2024</p>
            </div>
            <div class="card-body">
              <Bar id="my-chart-id" :options="chartOptions" :data="chartData" />
              <!-- div 元素，用 ref 属性绑定了一个名为 main 的虚拟 DOM 引用，这个引用会在 <script> 中使用。 -->
              <div class="charts">
                <div class="echarts" id="chart" ref="chart" style="height:340%; width:100%"></div>
              </div>
            </div>
          </div>
        </div>

        <!-- 3.2 recent papers -->
        <!-- <div class="col-lg-4">
          <div class="card card-carousel overflow-hidden h-100 p-0">
            <div id="carouselExampleCaptions" class="carousel slide h-100" data-bs-ride="carousel">
              <div class="carousel-inner border-radius-lg h-100">
                <div class="carousel-item h-100 active" style="background-image: url('../assets/img/carousel-1.jpg');
                                      background-size: cover;">
                  <div class="carousel-caption d-none d-md-block bottom-0 text-start start-0 ms-5">
                    <div class="icon icon-shape icon-sm bg-white text-center border-radius-md mb-3">
                      <i class="ni ni-camera-compact text-dark opacity-10"></i>
                    </div>
                    <h5 class="text-white  mb-1">Recent Papers</h5>
                    <p class="text-white mb-1">There’s nothing I really wanted to do in life that I
                      wasn’t able to get good at.
                    </p>
                  </div>
                </div>
                <div class="carousel-item h-100" style="background-image: url('../assets/img/carousel-2.jpg');
                                      background-size: cover;">
                  <div class="carousel-caption d-none d-md-block bottom-0 text-start start-0 ms-5">
                    <div class="icon icon-shape icon-sm bg-white text-center border-radius-md mb-3">
                      <i class="ni ni-bulb-61 text-dark opacity-10"></i>
                    </div>
                    <h5 class="text-black mb-1">Faster way to create web pages</h5>
                    <p class="text-black mb-1">That’s my skill. I’m not really specifically talented
                      at
                      anything except for the
                      ability to learn.</p>
                  </div>
                </div>
              </div>
              <button class="carousel-control-prev w-5 me-3" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
              </button>
              <button class="carousel-control-next w-5 me-3" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
              </button>
            </div>
          </div>
        </div> -->

        <div class="col-lg-4">
          <div class="card card-carousel overflow-hidden h-100 p-0">
            <div id="carouselExampleCaptions" class="carousel slide h-100" data-bs-ride="carousel">
              <div class="carousel-inner border-radius-lg h-100">

                <div v-for="(item, index) in carouselItems" :key="index"
                  :class="{ 'carousel-item': true, 'h-100': true, 'active': index === 0 }"
                  :style="{ 'background-image': 'url(' + item.image + ')', 'background-size': 'cover' }">
                  <div class="carousel-caption d-none d-md-block bottom-0 text-start start-0 ms-5">
                    <h4 class="text-white ">Recent paper # {{ index + 1 }}</h4>
                    <h6 :class="item.titleClass">{{ item.title }}</h6>
                    <p :class="item.titleClass">{{ item.abbr }} {{ item.year }}</p>
                    <p :class="item.titleClass">
                      <ArgonBadge color="primary">{{ item.field }}</ArgonBadge>
                    </p>
                  </div>
                </div>
                

              </div>
              <button class="carousel-control-prev w-5 me-3" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
              </button>
              <button class="carousel-control-next w-5 me-3" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
              </button>
            </div>
          </div>
        </div>


      </div>

      <Foot></Foot>
    </div>
  </main>
</template>

<script setup>
import * as echarts from 'echarts'
import SideNav from './components/SideNav.vue';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import ArgonBadge from '../ComponentCommon/ArgonBadge.vue';
import { onMounted, reactive, ref, } from 'vue';
import { request } from '../request';
import { useRouter } from 'vue-router';
import { useModuleStore } from '../store/module';
import pinia from '../store/store';
import { listAllPapers, listAllScholars, listAllInstitutions, listallVenue } from './commonFunction';
import { usePaperInfoStore } from '../store/paperinfoStore'
import { useAuthorStore } from '../store/authorStore'
import { useInstitutionStore } from '../store/institutionStore'
import { useVenueStore } from '../store/venueStore'
import { useUserStore } from '../store/userStore';
import { ElLoading } from 'element-plus'
import { usePaginationStore } from '../store/paginationStore'

const PaginationStore = usePaginationStore(pinia)
const userStore = useUserStore(pinia)
const moduleStore = useModuleStore(pinia)
const PaperInfoStore = usePaperInfoStore(pinia)
const AuthorStore = useAuthorStore(pinia)
const InstitutionStore = useInstitutionStore(pinia)
const VenueStore = useVenueStore(pinia)
const router = useRouter();



// 搜索查询对象
let searchObj = reactive({
  pagesize: 800,
  total: '',
  pagercount: 15,
  pagenum: 1,
  searchkeywords: "",
  typerofPapers: "Combinatorial Testing",

})

// the cumulative chart in the home page
const chart = ref();
let echartsOptions = reactive({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  grid: {
    left: '3%',
    right: '2%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    name: 'year',
    nameLocation: 'center',
    nameTextStyle: {
      padding: [20, 0, 0, 0]
    },
    type: 'category',
    data: [],
    axisLabel: {
      showMaxLabel: true,
      interval: 0,
      rotate: 45,
    }
  },
  yAxis: {
    name: '# publications',
    nameLocation: 'center',
    nameTextStyle: {
      padding: [0, 0, 15, 0]
    },
    type: 'value'
  },
  series: [
    {
      name: '# publications',
      data: [],
      type: 'bar',
      itemStyle: { color: '#2dce89' }
    }
  ]
})

const initChart = () => {

  // 对于每一个年份，统计该年份内有多少篇papers，作为series的data
  request({
    url: "repo/list/countEachYear",
    method: 'POST',
    data: {
      info: PaperInfoStore.TypeofPapers
    }

  }).then((res) => {
    // console.log("countEachYear",res)
    let tempYear = []
    let tempData = []
    let totalPapers = 0; // 新增一个变量用于累加总篇数
    for (var i = 0; i < res.countEachYear.length; i++) {
      tempYear.push(res.countEachYear[i].year)
      totalPapers += res.countEachYear[i].RecordCount; // 累加总篇数
      tempData.push(totalPapers); // 将累加的总篇数添加到tempData中
    }
    echartsOptions.xAxis.data = tempYear
    echartsOptions.series[0].data = tempData

    let myChart = echarts.init(chart.value);
    myChart.setOption(echartsOptions)
    myChart.on('click', ListByYear)
    myChart.resize()
  }).catch(() => { })
}

const resizeChart = () => {
  const myChart = echarts.init(chart.value);
  window.addEventListener(
    "resize",
    () => {
      myChart.resize();
    },
    false
  );
}




const searchPapers = async () => {
  let loadingInstance = ElLoading.service({ fullscreen: true })
  PaginationStore.pagenum = 1
  PaginationStore.pagesize = 25

  PaperInfoStore.paginationOffset = 0
  PaperInfoStore.paperinfos.length = 0
  PaperInfoStore.searchKeyWords = PaginationStore.searchkeywords

  moduleStore.CurrentModule = 'Search'
  moduleStore.CurrentModuleDetails=PaginationStore.searchkeywords

  const searchRes = await request({
    url: "/repo/list/searchPapers",
    method: 'POST',
    data: {
      pagesize: PaginationStore.pagesize,
      searchkeywords: PaginationStore.searchkeywords,
      pagenum: PaginationStore.pagenum,
    }
  })
  // console.log("searchkeywords", searchRes)
  PaperInfoStore.paperinfos.push(...searchRes.res.records)
  PaperInfoStore.total = searchRes.res.total
  PaginationStore.total = searchRes.res.total


  loadingInstance.close()
  router.push({
    path: '/repository/papers',
    query: {
      module: 'Search',
    }
  })
}

const totalPapersCount = ref(0)
const totalScholarsCount = ref(0)
const totalInstitutionsCounth = ref(0)


const carouselItems = reactive([]);
const listRecentPapers = async () => {
  try {
    const listRecentPapersRes = await request({
      url: 'repo/list/listRecentPapers',
      method: 'POST',
      data: searchObj
    })
    // console.log("listRecentPapers", listRecentPapersRes)
    carouselItems.push(...listRecentPapersRes.res)
    for (let i = 0; i < carouselItems.length; i++) {
      carouselItems[i].image = 'images/carousel-' + ((i) % 3 + 1) + '.jpg'
      carouselItems[i].titleClass = 'text-white mb-1'

    }
  } catch (error) {

  }
}

onMounted(async () => {
  let loadingInstance = ElLoading.service({ fullscreen: true })
  moduleStore.CurrentModule = 'Repository'
  if(PaginationStore.searchkeywords!="") {PaginationStore.searchkeywords=""}

  // 首页折线图
  initChart()
  resizeChart()

  // 获取papers总数
  const countTotalPapersRes = await request({
    url: "repo/list/countTotalPapers",
    method: 'POST',
    data: {
      typerofPapers: "Combinatorial Testing"
    }
  })
  // console.log("countTotalPapersRes", countTotalPapersRes)
  totalPapersCount.value = countTotalPapersRes.total

  // 获取Scholars总数
  const countTotalScholarsRes = await request({
    url: "repo/author/countTotalScholars",
    method: 'POST',
    data: {
      typerofPapers: "Combinatorial Testing"
    }
  })
  // console.log("countTotalScholarsRes", countTotalScholarsRes)
  totalScholarsCount.value = countTotalScholarsRes.total

  // 获取Institutions总数
  const countTotalInstitutionsRes = await request({
    url: "repo/author/countTotalInstitutions",
    method: 'POST',
    data: {
      typerofPapers: "Combinatorial Testing"
    }
  })
  // console.log("countTotalInstitutionsRes", countTotalInstitutionsRes)
  totalInstitutionsCounth.value = countTotalInstitutionsRes.total

  await listRecentPapers()
  loadingInstance.close()

})
</script>


<style scoped>
.charts {
  margin-top: -5rem !important
}
</style>
