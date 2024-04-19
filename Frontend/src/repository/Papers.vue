
<template>
  <div class="min-height-300 bg-primary position-absolute w-100"></div>
  <SideNav></SideNav>
  <main class="main-content position-relative border-radius-lg ">
    <Navbar></Navbar>

    <div class="container-fluid py-4">

      <!-- 针对scholars的card，显示scholar的基本信息 -->
      <div v-if="route.query.module == 'Scholars'" class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-header pb-0">

              <div style="margin: 0px 0px 5px 5px; display: flex; align-items: center;">
                <ArgonButton color="secondary" size="sm" @click="goBack">
                  <span class="ni ni-bold-left  me-1"> Back </span>
                </ArgonButton>
              </div>

              <h4 class="text-center">Scholar Info</h4>
              <hr class="my-3 horizontal dark" />


              <div class="row">

                <div class="col-md-4">
                  <h6>Name</h6>
                  <argon-input v-model="currentAuthorStore.CurrentAuthor.name" type="text" :disabled="'disabled'" />
                </div>

                <div class="col-md-3">
                  <h6>Institution Category</h6>
                  <argon-input v-model="currentAuthorStore.CurrentAuthor.category" type="text" :disabled="'disabled'" />
                </div>

              </div>

              <div class="row">

                <div class="col-md-6">
                  <h6>Institution</h6>
                  <argon-input v-model="currentAuthorStore.CurrentAuthor.institution" type="text"
                    :disabled="'disabled'" />
                </div>

                <div class="col-md-4">
                  <h6>Country</h6>
                  <argon-input v-model="currentAuthorStore.CurrentAuthor.country" type="text" :disabled="'disabled'" />
                </div>
              </div>

              <div v-if="currentAuthorStore.CurrentAuthor.email != '' || currentAuthorStore.CurrentAuthor.homepage != ''"
                class="row">

                <div v-if="currentAuthorStore.CurrentAuthor.email != ''" class="col-md-6">
                  <h6>Email</h6>
                  <argon-input v-model="currentAuthorStore.CurrentAuthor.email" type="text" :disabled="'disabled'" />
                </div>

                <div v-if="currentAuthorStore.CurrentAuthor.homepage != ''" class="col-md-6">
                  <h6>homepage</h6>
                  <argon-input v-model="currentAuthorStore.CurrentAuthor.homepage" type="text" :disabled="'disabled'" />
                </div>

              </div>

              <div class="row">
                <div class="col-md-2">
                  <h6>Total Papers: </h6>
                  <argon-input v-model="PaperInfoStore.total" type="text" :disabled="'disabled'" />
                </div>

                <div class="col-md-6">
                  <h6>Research Field : </h6>
                  <div style="display: flex;">
                    <div v-for="(field, index) in FieldArray">
                      <p style="margin:10px 0 0 5px" :class="getResearchFieldColor(field)">{{ field }}</p>
                    </div>
                  </div>
                </div>

              </div>

              <div class="row">
                <!-- <h6 class="text-center">Statistics of {{ currentAuthorStore.CurrentAuthor.name }}</h6> -->
                <hr class="my-3 horizontal dark" />
                <!-- 该作者每年发表的论文数量 -->
                <div class="col-md-5">
                  <div class="charts">
                    <div class="echarts" id="chartScholar" ref="chartScholar" style="height:540%; width:100%"></div>
                  </div>
                </div>

                <!-- 该作者的合作关系图 -->

                <div class="col-md-7">
                  <div class="charts">
                    <div class="echarts" id="chartScholarPartnership" ref="chartScholarPartnership"
                      style="height:640%; width:100%"></div>
                  </div>
                </div>

              </div>


            </div>




            <div class="card-body ps-1">
              <div class="table-responsive">
                <table class="table mb-0">
                  <tbody>
                  </tbody>
                </table>
              </div>
            </div>



          </div>
        </div>
      </div>

      <!-- 通用的card -->
      <div class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-header pb-0">
              <h4 v-if="moduleStore.CurrentModule == 'Complete Paper List'">Complete Paper List</h4>


              <!-- search author -->
              <h4 v-if="route.query.module == 'Scholars'">{{ currentAuthorStore.CurrentAuthor.name }}'s Papers </h4>


              <!-- search field -->
              <div v-if="route.query.module == 'Fields'">
                <h4>Papers from Research Field of <span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span>
                </h4>
              </div>

              <!-- search Topics -->
              <div v-if="route.query.module == 'Topics'">
                <h4>Papers related to Topic <span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span></h4>
              </div>

              <!-- search institution -->
              <div v-if="route.query.module == 'Institutions'">
                <h4> <span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span> 's Papers
                </h4>
              </div>

              <!-- search country -->
              <div v-if="route.query.module == 'Country'">
                <h4><span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span>'s Papers
                </h4>
              </div>


              <!-- search Venue -->
              <div v-if="route.query.module == 'Venues'">
                <h4>Papers published in <span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span>
                </h4>
              </div>

              <!-- search Keywords -->
              <div v-if="route.query.module == 'Search'">
                <h4>Search KeyWords : <span style="color: #2dce89">{{ PaperInfoStore.searchKeyWords }}</span>
                </h4>
              </div>

              <!-- Benchmark -->

              <div v-if="route.query.module == 'Benchmark'"
                style="margin: 0px 0px 5px 5px; display: flex; align-items: center;">
                <ArgonButton color="secondary" size="sm" @click="goBack">
                  <span class="ni ni-bold-left  me-1"> Back To Benchmark </span>
                </ArgonButton>
              </div>

              <div v-if="route.query.module == 'Benchmark'">
                <div v-if="moduleStore.CurrentRoute == 'Home' && moduleStore.CurrentSubSystem=='Benchmark'">
                  <h4>Papers related to Benchmark Set <span style="color: #2dce89">{{
                    PaperInfoStore.searchKeyWords }}</span>
                  </h4>
                </div>
                <div v-if="moduleStore.CurrentRoute == 'Benchmark_Models'">
                  <h4>Papers related to Model <span style="color: #2dce89">{{ moduleStore.CurrentModuleDetails }}</span>
                  </h4>
                </div>

              </div>

              <p class="text-muted mb-0"><span class="badge bg-success">{{
                PaperInfoStore.total }}</span> papers found
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


            <div style="width:100%;height: 100%;"
              v-if="PaperInfoStore.paperinfos.length == 0 && PaginationStore.searchkeywords != ''">
              <el-result style="width:100%;height: 100%;" icon="warning" :title="'No Papers Found.'"
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

<script setup>
import * as echarts from 'echarts'
import { onMounted, reactive, ref, watch } from 'vue';
import { Search } from '@element-plus/icons-vue'
import { ElLoading } from 'element-plus'
import Paper from './components/PaperElement.vue';
import PaperInfo from './components/PaperInformation.vue';
import SideNav from './components/SideNav.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import Foot from '../CustomizedComponents/Foot.vue';
import { request } from '../request'
import { useRoute } from 'vue-router'
import { usePaperInfoStore } from '../store/paperinfoStore'
import ArgonBadge from './components/ArgonBadge.vue'
import ArgonInput from '../CustomizedComponents/ArgonInput.vue';
import ArgonButton from '../CustomizedComponents/ArgonButton.vue';
import pinia from '../store/store';
import { listAllPapers, listAllScholars, listAllInstitutions, listallVenue } from './commonFunction';
import { useCurrentAuthorStore } from '../store/currentAuthorStore'
import { useModuleStore } from '../store/module';
import { usePaginationStore } from '../store/paginationStore'
const PaginationStore = usePaginationStore(pinia)
const PaperInfoStore = usePaperInfoStore(pinia)
const moduleStore = useModuleStore(pinia)
const currentAuthorStore = useCurrentAuthorStore(pinia)


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
  // 其他查询的翻页
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
const getResearchFieldColor = (field) => {
  switch (field) {
    case 'Model':
      return 'badge bg-success';
    case 'Generation':
      return 'badge bg-primary';
    case 'Optimization':
      return 'badge bg-warning';
    case 'Diagnosis':
      return 'badge bg-danger';
    case 'Evaluation':
      return 'badge bg-dark';
    case 'Application':
      return 'badge bg-info';
    case 'Other':
      return 'badge bg-secondary';
  }
}
let FieldArray = reactive([])


// 统计作者每年发表的论文折线图
const chartScholar = ref();
let ChartScholarYearlyOption = reactive({
  title: {
    text: currentAuthorStore.CurrentAuthor.name + '\'s annual publications', // 标题文本
    left: 'center', // 标题水平居中
    textStyle: {
      fontSize: 18, // 标题字体大小
      fontWeight: 'bold' // 标题字体粗细
    }
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    },

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
      rotate: 40,
    }
  },
  yAxis: {
    name: '# publications',
    nameLocation: 'center',
    nameTextStyle: {
    },
    type: 'value',
  },
  series: [
    {
      name: '# annual publications',
      data: [],
      type: 'line',
      itemStyle: {
        color: '#2dce89' // 设置点的颜色
      },
      symbolSize: 8, // 设置点的大小为8
      symbol: 'emptyCircle',// 设置点的形状为空心圆
      label: {
        // show: true,  // 初始状态下显示数据值
        position: 'top'
      }

    },
  ]
})
const initScholarYearChart = async () => {
  // console.log("初始化每年发表的论文统计图")


  try {

    const res = await request({
      url: "repo/list/countEachYearScholars",
      method: 'POST',
      data: {
        author: currentAuthorStore.CurrentAuthor.name
      }
    });

    // console.log("countEachYearScholars res", res)
    let tempYear = []
    let tempData = []
    for (let i = 0; i < res.res.length; i++) {
      tempYear.push(res.res[i].year)
      tempData.push(res.res[i].RecordCount)

    }
    // 更新 ChartScholarYearlyOption 中的 xAxis.data 和 series[0].data
    ChartScholarYearlyOption.xAxis.data = tempYear;
    ChartScholarYearlyOption.series[0].data = tempData;

    let myChart = echarts.init(chartScholar.value);
    myChart.setOption(ChartScholarYearlyOption)
    myChart.resize()

  } catch (error) {

  }



}

// 统计作者的合作关系图
const chartScholarPartnership = ref();
let PartnershipAuthorArray = reactive([])
let ChartScholarPartnershipOption = reactive({
  title: {
    text: 'Author Partnership ', // 图表标题
    left: 'center' // 标题居中
  },
  tooltip: {
    trigger: 'item',
    formatter: function (params) {
      // console.log("params",params)
      if (params.dataType === 'edge') {
        // 修改tooltip，显示边的源节点、目标节点和权重
        return params.data.source + ' > ' + params.data.target + '<br>Times of co-work : ' + params.data.weight;
      }
      if (params.dataType === 'node') { return params.name + '<br>Times of co-work :' + params.data.weight }
    }
  },
  series: [
    {
      type: 'graph',
      layout: 'force',
      roam: true,
      label: {
        show: true,
        color: 'black',
        position: 'top', // 设置标签位置为节点上方
        fontSize: 15
      },
      force: {
        repulsion: 800, // 控制节点之间的排斥力
        layoutAnimation: false,
      },
      edgeSymbol: ['', 'arrow'],

      animation: false, // 取消动画
      data: [
        {
          name: currentAuthorStore.CurrentAuthor.name, // 中心节点
          symbol: 'diamond', // 中心节点形状
          symbolSize: 20, // 设置中心节点大小为30
          itemStyle: {
            color: '#f5365c' // 中心节点颜色
          }
        },

      ],
      // 添加边
      links: []
    }
  ]
})


const initPartnershipChart = async () => {
  // console.log("初始化合作关系图")
  try {

    const res = await request({
      url: "repo/list/listBy",
      method: 'POST',
      data: {
        column: 'author',
        searchkeywords: currentAuthorStore.CurrentAuthor.name,
      }
    });
    let tempAuthorArray = []
    // console.log("initPartnershipChart res", res)


    for (let i = 0; i < res.res.length; i++) {
      tempAuthorArray.push(...res.res[i].author.split(','))
    }
    for (let i = 0; i < tempAuthorArray.length; i++) {
      tempAuthorArray[i] = tempAuthorArray[i].trim()
    }
    tempAuthorArray = tempAuthorArray.filter(tempAuthorArray => tempAuthorArray !== currentAuthorStore.CurrentAuthor.name);
    // console.log("tempAuthorArray filter", tempAuthorArray)

    // 统计出现次数，即为权重
    let countMap = {};

    tempAuthorArray.forEach(item => {
      countMap[item] = (countMap[item] || 0) + 1;
    });

    // console.log("重复次数统计", countMap);

    tempAuthorArray = [...new Set(tempAuthorArray)];
    // console.log("tempAuthorArray 去重", tempAuthorArray)

    tempAuthorArray = tempAuthorArray.filter(tempAuthorArray => tempAuthorArray !== currentAuthorStore.CurrentAuthor.name);
    PartnershipAuthorArray = tempAuthorArray

    // 将PartnershipAuthorArray数组中的author改造成关系图的节点 data数组 计算节点大小，合作次数越多，节点越大

    let nodeArray = PartnershipAuthorArray.map(name => ({
      name,
      symbol: "circle",
      itemStyle: {
        color: '#2dce89'
      },
      // 根据权重计算节点大小，权重越大节点越小
      symbolSize: calculateSymbolSize(countMap[name]),
      weight: countMap[name]
    }));
    // console.log("nodeArray", nodeArray)
    ChartScholarPartnershipOption.series[0].data.push(...nodeArray)

    // 构造边数组
    let edgeArray = PartnershipAuthorArray.map(name => ({
      source: currentAuthorStore.CurrentAuthor.name, target: name, // 将权重添加到边数据中
      weight: countMap[name]
    }))
    // console.log("edgeArray", edgeArray)
    ChartScholarPartnershipOption.series[0].links.push(...edgeArray)

    let myChart = echarts.init(chartScholarPartnership.value);
    myChart.setOption(ChartScholarPartnershipOption)
    myChart.resize()


  } catch (error) {

  }



}

// 根据权重计算节点大小的函数
const calculateSymbolSize = (weight) => {
  // 根据权重计算节点大小
  // 根据实际情况调整这个函数
  return weight * 3;
}

const goBack = () => {
  window.history.back();
}
const DetailedModule = ['Scholars', 'Institutions', 'Country', 'Venues', 'Fields', 'Topics', 'Search', 'Repository', 'Home','Benchmark Models']
onMounted(async () => {
  // console.log("PaperInfoStore", PaperInfoStore)

  if (DetailedModule.includes(moduleStore.CurrentModule) && moduleStore.CurrentModuleDetails != '') {
    let loadingInstance = ElLoading.service({ fullscreen: true })
    console.log("moduleStore.CurrentModule", moduleStore.CurrentModule)

    // 获取当前学者的所有研究领域
    if (route.query.module == 'Scholars') {
      for (let i = 0; i < PaperInfoStore.paperinfos.length; i++) {
        FieldArray.push(PaperInfoStore.paperinfos[i].field)
      }
      FieldArray = [...new Set(FieldArray)];

      await initScholarYearChart()
      await initPartnershipChart()
    }

    loadingInstance.close()
  }
  else {
    let loadingInstance = ElLoading.service({ fullscreen: true })
    console.log("设置为Complete Paper List")
    moduleStore.CurrentModule = 'Complete Paper List'
    moduleStore.CurrentModuleDetails = ''
    moduleStore.CurrentRoute = 'Repository_Papers'
    PaginationStore.pagenum = 1
    PaginationStore.pagesize = 25
    PaginationStore.searchkeywords = ''
    PaginationStore.column = ''
    PaperInfoStore.paperinfos.length = 0
    // 计算偏移量
    const paginationOffset = (PaginationStore.pagenum - 1) * PaginationStore.pagesize;
    // 更新全局偏移量
    PaperInfoStore.paginationOffset = paginationOffset;
    await listAllPapers()

    loadingInstance.close()


  }




})
</script>


<style scoped></style>
