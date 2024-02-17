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

      <!-- 1. number of publications -->
      <div class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-body pb-0 mb-4">
              <h5>Number of Publications</h5>
              <p class="text-muted">The annual and cumulative number of CIT publications</p>
              <div class="charts">
                <div class="echarts" id="ChartNumber" ref="ChartNumber" style="height:360%; width:100%"></div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 2. distribution of fields -->
      <div class="row">
        <div class="col-6">
          <div class="card mb-4">
            <div class="card-body pb-0">
              <h5>Distribution of Fields</h5>
              <p class="text-muted">The distribution of different CIT research fields</p>
              <div class="charts">
                <div class="echarts" id="ChartField" ref="ChartField" style="height:320%; width:100%"></div>
              </div>
            </div>
          </div>
        </div>

        <!-- 3. developement of fields -->
        <div class="col-6">
          <div class="card mb-4">
            <div class="card-body pb-0">
              <h5>Development of Fields</h5>
              <p class="text-muted">The relative proportions of each CIT research field for each year</p>
            </div>
          </div>
        </div>
      </div>

      <!-- 4. distribution of scholars -->
      <div class="row">
        <div class="col-6">
          <div class="card mb-4">
            <div class="card-body pb-0">
              <h5>Distribution of <S></S>cholars</h5>
              <p class="text-muted">The distribution of scholars accross the world
              </p>
            </div>
          </div>
        </div>
        
        <!-- 5. new institutions  -->
        <div class="col-6">
          <div class="card mb-4">
            <div class="card-body pb-0">
              <h5>Number of New Institutions</h5>
              <p class="text-muted">The number of new institutions that contribute to CIT</p>
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
import SideNav from './components/SideNav.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import Foot from '../ComponentCommon/Foot.vue';

import { useInstitutionStore } from '../store/institutionStore'
import { onMounted } from 'vue';
import { ref, computed, reactive } from 'vue';
import { usePaperInfoStore } from '../store/paperinfoStore'
import { request } from '../request';
import { useRouter } from 'vue-router';
import pinia from '../store/store';
const router = useRouter();
const PaperInfoStore = usePaperInfoStore(pinia)

// 1. number of publication
const ChartNumber = ref()
let ChartNumberOption = reactive({
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
      rotate: 40,
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
      name: '# cumulative publications',
      data: [],
      type: 'bar',
      itemStyle: { color: '#2dce89' }
    },
    {
      name: '# annual publications',
      data: [],
      type: 'line',
      smooth: true,
      lineStyle: { color: '#8392ab' }
    }
  ]
})

const initChartNumber = () => {
  request({
    url: "repo/list/countEachYear",
    method: 'POST',
    data: {
      info: PaperInfoStore.TypeofPapers
    }
  }).then((res) => {
    // console.log("countEachYear", res)
    let tempYear = []
    let tempData = []
    for (var i = 0; i < res.countEachYear.length; i++) {
      tempYear.push(res.countEachYear[i].year)
      tempData.push(res.countEachYear[i].RecordCount)
    }
    ChartNumberOption.xAxis.data = tempYear
    ChartNumberOption.series[0].data = tempData
    ChartNumberOption.series[1].data = tempData

    let myChart = echarts.init(ChartNumber.value);
    myChart.setOption(ChartNumberOption)
    myChart.resize()
  }).catch(() => { })
}

// 2. distribution of fields
const ChartField = ref()
let ChartFieldOption = reactive({
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    textStyle: {
      fontSize: 14 // 添加字体大小配置
    }
  },
  series: [
    {
      label: {
        show: true,
        position: 'outside',
        formatter: '{b}: {d}%',
        fontSize: 14

      },
      // name: 'Access From',
      type: 'pie',
      radius: '75%',
      data: [],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(21,57,199,0.5)'
        }
      }
    }
  ]
})

const initChartField = () => {
  // TODO: API?
  request({
    url: "repo/list/countField",
    method: 'POST',
    data: {
      info: PaperInfoStore.TypeofPapers
    }
  }).then((res) => {
    console.log("field", res)
    let tempCatgory = []
    let tempdata = []
    for (var i = 0; i < res.countEachField.length; i++) {
        let tempObj = {
            value: '',
            name: '',
        }
        if (res.countEachField[i].field) {
            tempObj.name = res.countEachField[i].field
            tempObj.value = res.countEachField[i].RecordCount
            echartsOptions_DISTRIBUTION_OF_FIELD.series[0].data.push(tempObj)
        }
    }
    let myChart = echarts.init(ChartField.value);
    myChart.setOption(ChartFieldOption)
  }).catch((err) => {})
}


onMounted(() => {
  initChartNumber()
  initChartField()
})



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

.charts {
  margin-top: -3rem !important
}
</style>
