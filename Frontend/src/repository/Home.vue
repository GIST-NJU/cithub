
<template>
  <div class="min-height-300 bg-primary position-absolute w-100"></div>
  <SideNav></SideNav>
  <main class="main-content position-relative border-radius-lg ">
    <Navbar></Navbar>

    <div class="container-fluid py-4">
      <!-- 1. basic statistic -->
      <div class="row">
        <div class="col-xl-4 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="mb-1 font-weight-bolder text-lg">Research Papers</p>
                    <h5 class="font-weight-bolder">{{ paginationObj.total }}</h5>
                    <i class="fa fa-arrow-up text-info"></i>
                    <span class="text-info font-weight-bolder">+3%</span> since last year
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
                    <h5 class="font-weight-bolder">{{
                      AuthorStore.authorsArray.length }}</h5>
                    <i class="fa fa-arrow-up text-info"></i>
                    <span class="text-info font-weight-bolder">+3%</span> since last year
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
                    <h5 class="font-weight-bolder">{{ InstitutionStore.InstitutionArray.length }}</h5>
                    <i class="fa fa-arrow-up text-info"></i>
                    <span class="text-info font-weight-bolder">+3%</span>
                    since last year
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

      <!-- 2. search form -->
      <div class="row mt-4">
        <div class="col-12">
          <div class="card">
            <div class="card-body pb-0">
              <h5>Find Papers</h5>
              <div class="row pt-2">
                <div class="col-10">
                  <input type="text" class="form-control" placeholder="Name of paper, scholar, publication venue, ...">
                </div>
                <div class="col-2">
                  <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary">Search</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 3. other information -->
      <div class="row mt-4">
        <!-- 3.1 cumulative number of publications -->
        <div class="col-lg-8 mb-lg-0 mb-4">
          <div class="card z-index-2 h-100">
            <div class="card-header pt-3 bg-transparent">
              <h5>Cumulative Nunmber of Publications</h5>
              <p class="text-muted">From 1985 to 2024</p>
            </div>
            <div class="card-body">
              <Bar id="my-chart-id" :options="chartOptions" :data="chartData"/>
              <!-- div 元素，用 ref 属性绑定了一个名为 main 的虚拟 DOM 引用，这个引用会在 <script> 中使用。 -->
              <div class="charts">
                <div class="echarts" id="chart" ref="chart" style="height:340%; width:100%"></div>
              </div>
            </div>
          </div>
        </div>

        <!-- 3.2 recent papers -->
        <!-- TODO: js does not work -->
        <div class="col-lg-4">
          <div class="card card-carousel overflow-hidden h-100 p-0">
            <div id="carouselExampleCaptions" class="carousel slide h-100" data-bs-ride="carousel">
              <div class="carousel-inner border-radius-lg h-100">
                <div class="carousel-item h-100 active" style="background-image: url('../assets/img/carousel-1.jpg');
                                      background-size: cover;">
                  <div class="carousel-caption d-none d-md-block bottom-0 text-start start-0 ms-5">
                    <div class="icon icon-shape icon-sm bg-white text-center border-radius-md mb-3">
                      <i class="ni ni-camera-compact text-dark opacity-10"></i>
                    </div>
                    <h5 class="text-black  mb-1">Get started with Argon</h5>
                    <p class="text-black mb-1">There’s nothing I really wanted to do in life that I
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
        </div>
      </div>

      <Foot></Foot>
    </div>
  </main>

  <!-- TODO: js does not work -->
  <!-- leave this as a feature, or remove it? -->
  <div class="fixed-plugin">
    <a class="fixed-plugin-button text-dark position-fixed px-3 py-2">
      <i class="fa fa-cog py-2"> </i>
    </a>
    <div class="card shadow-lg">
      <div class="card-header pb-0 pt-3 ">
        <div class="float-start">
          <h5 class="mt-3 mb-0">Argon Configurator</h5>
          <p>See our dashboard options.</p>
        </div>
        <div class="float-end mt-4">
          <button class="btn btn-link text-dark p-0 fixed-plugin-close-button">
            <i class="fa fa-close"></i>
          </button>
        </div>
        <!-- End Toggle Button -->
      </div>
      <hr class="horizontal dark my-1">
      <div class="card-body pt-sm-3 pt-0 overflow-auto">
        <!-- Sidebar Backgrounds -->
        <div>
          <h6 class="mb-0">Sidebar Colors</h6>
        </div>
        <a href="javascript:void(0)" class="switch-trigger background-color">
          <div class="badge-colors my-2 text-start">
            <span class="badge filter bg-gradient-primary active" data-color="primary"
              onclick="sidebarColor(this)"></span>
            <span class="badge filter bg-gradient-dark" data-color="dark" onclick="sidebarColor(this)"></span>
            <span class="badge filter bg-gradient-info" data-color="info" onclick="sidebarColor(this)"></span>
            <span class="badge filter bg-gradient-success" data-color="success" onclick="sidebarColor(this)"></span>
            <span class="badge filter bg-gradient-warning" data-color="warning" onclick="sidebarColor(this)"></span>
            <span class="badge filter bg-gradient-danger" data-color="danger" onclick="sidebarColor(this)"></span>
          </div>
        </a>
        <!-- Sidenav Type -->
        <div class="mt-3">
          <h6 class="mb-0">Sidenav Type</h6>
          <p class="text-sm">Choose between 2 different sidenav types.</p>
        </div>
        <div class="d-flex">
          <button class="btn bg-gradient-primary w-100 px-3 mb-2 active me-2" data-class="bg-white"
            onclick="sidebarType(this)">White</button>
          <button class="btn bg-gradient-primary w-100 px-3 mb-2" data-class="bg-default"
            onclick="sidebarType(this)">Dark</button>
        </div>
        <p class="text-sm d-xl-none d-block mt-2">You can change the sidenav type just on desktop view.</p>
        <!-- Navbar Fixed -->
        <div class="d-flex my-3">
          <h6 class="mb-0">Navbar Fixed</h6>
          <div class="form-check form-switch ps-0 ms-auto my-auto">
            <input class="form-check-input mt-1 ms-auto" type="checkbox" id="navbarFixed" onclick="navbarFixed(this)">
          </div>
        </div>
        <hr class="horizontal dark my-sm-4">
        <div class="mt-2 mb-5 d-flex">
          <h6 class="mb-0">Light / Dark</h6>
          <div class="form-check form-switch ps-0 ms-auto my-auto">
            <input class="form-check-input mt-1 ms-auto" type="checkbox" id="dark-version" onclick="darkMode(this)">
          </div>
        </div>
        <a class="btn bg-gradient-dark w-100" href="https://www.creative-tim.com/product/argon-dashboard">Free
          Download</a>
        <a class="btn btn-outline-dark w-100"
          href="https://www.creative-tim.com/learning-lab/bootstrap/license/argon-dashboard">View
          documentation</a>
        <div class="w-100 text-center">
          <a class="github-button" href="https://github.com/creativetimofficial/argon-dashboard" data-icon="octicon-star"
            data-size="large" data-show-count="true"
            aria-label="Star creativetimofficial/argon-dashboard on GitHub">Star</a>
          <h6 class="mt-3">Thank you for sharing!</h6>
          <a href="https://twitter.com/intent/tweet?text=Check%20Argon%20Dashboard%20made%20by%20%40CreativeTim%20%23webdesign%20%23dashboard%20%23bootstrap5&amp;url=https%3A%2F%2Fwww.creative-tim.com%2Fproduct%2Fargon-dashboard"
            class="btn btn-dark mb-0 me-2" target="_blank">
            <i class="fab fa-twitter me-1" aria-hidden="true"></i> Tweet
          </a>
          <a href="https://www.facebook.com/sharer/sharer.php?u=https://www.creative-tim.com/product/argon-dashboard"
            class="btn btn-dark mb-0 me-2" target="_blank">
            <i class="fab fa-facebook-square me-1" aria-hidden="true"></i> Share
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import * as echarts from 'echarts'
import SideNav from './components/SideNav.vue';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import { onMounted, reactive, ref, } from 'vue';
import { request } from '../request';
import { useRouter } from 'vue-router';
import { useModuleStore } from '../store/module';
const userStore = useUserStore()
const moduleStore = useModuleStore()

import { usePaperInfoStore } from '../store/paperinfoStore'
import { useAuthorStore } from '../store/authorStore'
import { useInstitutionStore } from '../store/institutionStore'
import { useVenueStore } from '../store/venueStore'
import { CheckLoginStatus, getUserInfoByToken } from '../common'
import { useUserStore } from '../store/userStore';

const PaperInfoStore = usePaperInfoStore()
const AuthorStore = useAuthorStore()
const InstitutionStore = useInstitutionStore()
const VenueStore = useVenueStore()
const router = useRouter();

// 分页查询的传递的对象  
let paginationObj = reactive({
  pagesize: 30,
  total: 0,
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
      rotate: 50,
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
  // TODO: 展示 cumulative 而不是 annual number
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
    for (var i = 0; i < res.countEachYear.length; i++) {
      tempYear.push(res.countEachYear[i].year)
      tempData.push(res.countEachYear[i].RecordCount)
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

// TODO: only GET necessary data, but not the comple list
// None of paper list, scholar list, venue list is needed here
const listAllPapers = () => {
  // 获取所有文章的数据
  request({
    url: '/repo/list/listAllpapers',
    method: 'POST',
    data: paginationObj
  }).then((res) => {
    // console.log("listAllPapers",res)
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.searchKeyWords = ''
    PaperInfoStore.paperinfos.push(...res.listEntityPage.records)

    paginationObj.total = res.listEntityPage.total
    // console.log("paginationObj.total",paginationObj.total)

    PaperInfoStore.total = paginationObj.total

    // console.log("paginationObj.total",paginationObj.total)

  }).catch((error) => {
    console.log("错误是", error)
  })
}

const listAllScholars = () => {

  request(
    {
      url: 'repo/author/listallauthor',
      method: 'POST',
      data: {
        obj: "Combinatorial Testing"
      }


    }
  ).then((res) => {
    // console.log("authors是")
    // console.log("收到的authors", res)
    AuthorStore.authorsArray.length = 0
    for (let i = 0, len = res.authors.length; i < len; i++) {
      // authors.push(res.authors[i].authorname)
      AuthorStore.authorsArray.push(res.authors[i].authorname)
    }
    // authors = authors.sort()
    AuthorStore.authorsArray = AuthorStore.authorsArray.sort()

  }).catch((error) => { console.log(error) })

}

const listAllInstitutions = () => {

  request(
    {
      url: 'repo/author/listAllInstitutions',
      method: 'POST',
      data: {
        obj: "Combinatorial Testing"
      }


    }
  ).then((res) => {
    // console.log("收到的Institutions", res)
    InstitutionStore.InstitutionArray.length = 0
    // console.log("res.Institutions.length",res.Institutions.length)
    let len = res.Institutions.length
    for (let i = 0; i < len; i++) {
      // console.log("res.Institutions[i].institution",res.Institutions[i].institution)
      InstitutionStore.InstitutionArray.push(res.Institutions[i].institution)
    }
    // console.log( "  InstitutionStore.InstitutionArray", InstitutionStore.InstitutionArray)
    // authors = authors.sort()
    InstitutionStore.InstitutionArray = InstitutionStore.InstitutionArray.sort()

  }).catch((error) => { console.log(error) })

}

const listallVenue = () => {

  request(
    {
      url: 'repo/list/listallVenue',
      method: 'POST',
      data: {
        obj: "Combinatorial Testing"
      }


    }
  ).then((res) => {

    let tempArray = []
    for (let i = 0, len = res.res.length; i < len; i++) {
      if (res.res[i].abbr == 'Phd' && res.res[i].booktitle.indexOf('not found') == -1) VenueStore.VenueArrayPhd.push(res.res[i].booktitle)
      else if (res.res[i].abbr == 'Book' && res.res[i].booktitle.indexOf('not found') == -1) VenueStore.VenueArrayBook.push(res.res[i].booktitle)
      else if (res.res[i].abbr == 'other' && res.res[i].booktitle.indexOf('not found') == -1) VenueStore.VenueArrayOther.push(res.res[i].booktitle)
      else tempArray.push(res.res[i].abbr)

    }

    tempArray = Array.from(new Set(tempArray))
    for (var i = 0; i < tempArray.length; i++) {
      VenueStore.VenueArray.push(tempArray[i])
    }
    // console.log("VenueStore.VenueArray",VenueStore.VenueArray)
    // console.log("VenueStore.VenueArrayPhd",VenueStore.VenueArrayPhd)
    // console.log("VenueStore.VenueArrayBook",VenueStore.VenueArrayBook)
    // console.log("VenueStore.VenueArrayOther",VenueStore.VenueArrayOther)
    VenueStore.VenueArray = VenueStore.VenueArray.sort()
    VenueStore.VenueArrayPhd = VenueStore.VenueArrayPhd.sort().reverse()
    VenueStore.VenueArrayBook = VenueStore.VenueArrayBook.sort().reverse()
    VenueStore.VenueArrayOther = VenueStore.VenueArrayOther.sort().reverse()
  }).catch((error) => { console.log(error) })

}


onMounted(async () => {
  moduleStore.CurrentModule = 'Repository'

  // TODO: right strategy?
  // 先检查用户是否登录
  let obj = await CheckLoginStatus()
  // console.log("obj.loginStatus", obj.loginStatus)
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

    // 首页折线图
    initChart()
    resizeChart()
    // 获取所有paper
    listAllPapers()
    // 获取所有scholars
    listAllScholars()
    // 获取所有institutions
    listAllInstitutions()
    // 获取所有Venue
    listallVenue()
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


<style scoped>
.charts {
  margin-top: -5rem !important
}
</style>
