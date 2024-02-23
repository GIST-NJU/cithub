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
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-body pb-0 mb-4">
              <h5>Distribution of Fields</h5>
              <p class="text-muted">The distribution of different CIT research fields</p>
              <div class="charts">
                <div class="echarts" id="ChartField" ref="ChartField" style="height:320%; width:100%"></div>
              </div>
            </div>
          </div>
        </div>
      </div>


      <!-- 3. developement of fields -->
      <div class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-body pb-0 mb-4">
              <h5>Development of Fields</h5>
              <p class="text-muted">The relative proportions of each CIT research field for each year</p>
              <div class="charts">
                <div class="echarts" id="ChartDevelopField" ref="ChartDevelopField" style="height:420%; width:100%"></div>
              </div>
            </div>
          </div>
        </div>
      </div>


      <!-- 4. distribution of scholars -->
      <div class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-body pb-0 mb-4">
              <h5>Distribution of Scholars</h5>
              <p class="text-muted">The distribution of scholars accross the world</p>
              <div class="charts">
                <div class="echarts" id="ChartDistributionScholars" ref="ChartDistributionScholars"
                  style="height:520%; width:100%"></div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 5. new institutions  -->
      <div class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-body pb-0 mb-4">
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
import "echarts/map/js/world.js";
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
    },

  },
  grid: {
    left: '3%',
    right: '2%',
    bottom: '3%',
    containLabel: true
  },
  legend: {
    data: ['# cumulative publications', '# annual publications'],
    right: '10',  // 调整图例位置，可根据需要调整
    top: '10',    // 调整图例位置，可根据需要调整
    orient: 'vertical'  // 设置图例的排列方向为垂直
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
    type: 'value',
    interval: 200 // 设置刻度的间隔，可以根据需要调整

  },
  series: [
    {
      name: '# cumulative publications',
      data: [],
      type: 'line',
      itemStyle: { color: '#2dce89' },
      label: {
        // show: true,  // 初始状态下显示数据值
        position: 'top'
      }


    },
    {
      name: '# annual publications',
      data: [],
      type: 'line',
      smooth: true,
      lineStyle: { color: 'red' },
      label: {
        // show: true,  // 初始状态下显示数据值
        position: 'top'
      }


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
    let tempYear = []
    let tempDataAnnual = []  // 存储每年的数据
    let tempDataCumulative = []  // 存储累加的数据
    let totalPapers = 0;

    for (var i = 0; i < res.countEachYear.length; i++) {
      tempYear.push(res.countEachYear[i].year)
      totalPapers += res.countEachYear[i].RecordCount;
      tempDataAnnual.push(res.countEachYear[i].RecordCount);  // 每年的数据
      tempDataCumulative.push(totalPapers);  // 累加的数据
    }

    ChartNumberOption.xAxis.data = tempYear
    ChartNumberOption.series[0].data = tempDataCumulative  // 累加的数据
    ChartNumberOption.series[1].data = tempDataAnnual  // 每年的数据



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
    data: ['Model', 'Generation', 'Optimization', 'Diagnosis', 'Evaluation', 'Application', 'Other'],
    orient: 'vertical',
    left: 'right',
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
        fontSize: 12

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

  request({
    url: "repo/list/countField",
    method: 'POST',
    data: {
      info: PaperInfoStore.TypeofPapers
    }
  }).then((res) => {
    // console.log("field", res)
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
        ChartFieldOption.series[0].data.push(tempObj)
      }
    }
    let myChart = echarts.init(ChartField.value);
    myChart.setOption(ChartFieldOption)
    myChart.resize()
  }).catch((err) => { })
}


// 3. development of Fields
const ChartDevelopField = ref()
let ChartDevelopFieldOption = reactive({

  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    },
    formatter: function (params) {
      let tooltipContent = params[0].name + '<br/>';

      params.forEach(function (item) {
        // console.log("item", item)
        tooltipContent += '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:' + item.color + '"></span>' +
          item.seriesName + ': ' + item.value + '% (' + ChartDevelopFieldOption.series[item.seriesIndex].count[item.dataIndex] + ')<br/>';
      });

      return tooltipContent;
    },




  },
  legend: {
    data: ['Model', 'Generation', 'Optimization', 'Diagnosis', 'Evaluation', 'Application', 'Other'],
    top: 'bottom',
    padding: 0,
    textStyle: {
      fontSize: 12 // 添加字体大小配置
    }
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    data: [],
    axisLabel: {
      showMaxLabel: true,
      interval: 0,
      rotate: 15,
    }
  },
  yAxis: {
    type: 'value',
    max: 100,
    name: 'ratio of publications',
    nameLocation: 'middle',
    nameGap: 40,
    nameTextStyle: {
      fontSize: 14,
    }

  },
  series: [
    {
      name: 'Model',
      type: 'bar',
      stack: '总量',
      itemStyle: {
        normal: {
          color: '#6ab0b8', // 设置颜色

        }
      },
      data: [],
      count: []
    },
    {
      name: 'Generation',
      type: 'bar',
      stack: '总量',
      itemStyle: {
        normal: {
          color: '#7fae90' // 设置颜色
        }
      },
      data: [],
      count: []


    },
    {
      name: 'Optimization',
      type: 'bar',
      stack: '总量',
      itemStyle: {
        normal: {
          color: '#d53a35' // 设置颜色
        }
      },
      data: [],
      count: []


    },
    {
      name: 'Diagnosis',
      type: 'bar',
      stack: '总量',
      itemStyle: {
        normal: {
          color: '#334b5c' // 设置颜色
        }
      },
      data: [],
      count: []


    },
    {
      name: 'Evaluation',
      type: 'bar',
      stack: '总量',
      itemStyle: {
        normal: {
          color: '#9fdabf' // 设置颜色
        }
      },
      data: [],
      count: []


    },
    {
      name: 'Application',
      type: 'bar',
      stack: '总量',
      itemStyle: {
        normal: {
          color: '#de9325' // 设置颜色
        }
      },
      data: [],
      count: []

    },
    {
      name: 'Other',
      type: 'bar',
      stack: '总量',
      itemStyle: {
        normal: {
          color: '#e98f6f' // 设置颜色
        }
      },
      data: [],
      barMinHeight: '100%'

    }
  ]
});
const initChartDevelopField = () => {
  request({
    url: "repo/list/listCountFieldAnnual",
    method: 'POST',
    data: {
      info: PaperInfoStore.TypeofPapers
    }
  }).then((res) => {
    // console.log("res", res)

    let data = res.result


    // 创建一个对象，用于存储每个年份的类别计数
    const yearlyCategoryCounts = {};

    // 遍历每个对象
    data.forEach(entry => {
      const year = entry.year;
      yearlyCategoryCounts[year] = yearlyCategoryCounts[year] || {};

      for (const [category, count] of Object.entries(entry)) {
        // 跳过 "year" 键和空白值
        if (category === "year" || !count) {
          continue;
        }

        yearlyCategoryCounts[year][category] = (yearlyCategoryCounts[year][category] || 0) + parseInt(count);
      }
    });

    // 创建一个对象，用于存储每个类别的数组，数值数组
    const categoryArrays = {};

    // 遍历每个年份的数据
    for (const [year, categories] of Object.entries(data)) {
      // 遍历每个类别
      for (const [category, count] of Object.entries(categories)) {
        // 如果 categoryArrays 中还没有该类别的数组，就创建一个空数组
        categoryArrays[category] = categoryArrays[category] || [];

        // 将该年份的数据值添加到对应类别的数组中
        categoryArrays[category].push(count);
      }
    }

    // 计算每个年份的总数
    const yearlyTotals = {};
    for (const [year, categoryCounts] of Object.entries(yearlyCategoryCounts)) {
      yearlyTotals[year] = Object.values(categoryCounts).reduce((acc, count) => acc + count, 0);
    }


    // 计算每个类别在本年度的占比
    const yearlyCategoryPercentages = {};
    for (const [year, categoryCounts] of Object.entries(yearlyCategoryCounts)) {
      yearlyCategoryPercentages[year] = {};

      for (const [category, count] of Object.entries(categoryCounts)) {
        const total = yearlyTotals[year];
        const percentage = total > 0 ? (count / total) * 100 : 0;
        yearlyCategoryPercentages[year][category] = percentage.toFixed(2);
      }
    }



    // 创建一个对象，用于存储每个类别的百分比数组，百分比数组
    const categoryPercentagesArray = {};

    // 遍历每个年份的每个类别的百分比
    for (const [year, categoryPercentages] of Object.entries(yearlyCategoryPercentages)) {
      for (const [category, percentage] of Object.entries(categoryPercentages)) {
        categoryPercentagesArray[category] = categoryPercentagesArray[category] || [];
        categoryPercentagesArray[category].push(parseFloat(percentage));
      }
    }

    let years = res.result.map(function (item) { return parseInt(item.year); });

    // 提取数值数组
    let modelsNum = categoryArrays["model"] || [];
    let generationsNum = categoryArrays["generation"] || [];
    let optimizationsNum = categoryArrays["optimization"] || [];
    let diagnosesNum = categoryArrays["diagnosis"] || [];
    let evaluationsNum = categoryArrays["evaluation"] || [];
    let applicationsNum = categoryArrays["application"] || [];
    let othersNum = categoryArrays["other"] || [];

    // 提取百分比数组

    let models = categoryPercentagesArray["model"] || [];
    let generations = categoryPercentagesArray["generation"] || [];
    let optimizations = categoryPercentagesArray["optimization"] || [];
    let diagnoses = categoryPercentagesArray["diagnosis"] || [];
    let evaluations = categoryPercentagesArray["evaluation"] || [];
    let applications = categoryPercentagesArray["application"] || [];
    let others = categoryPercentagesArray["other"] || [];

    // 将数组给配置对象

    ChartDevelopFieldOption.xAxis.data = years;
    ChartDevelopFieldOption.series[0].data = models;
    ChartDevelopFieldOption.series[1].data = generations;
    ChartDevelopFieldOption.series[2].data = optimizations;
    ChartDevelopFieldOption.series[3].data = diagnoses;
    ChartDevelopFieldOption.series[4].data = evaluations;
    ChartDevelopFieldOption.series[5].data = applications;
    ChartDevelopFieldOption.series[6].data = others;

    ChartDevelopFieldOption.series[0].count = modelsNum;
    ChartDevelopFieldOption.series[1].count = generationsNum;
    ChartDevelopFieldOption.series[2].count = optimizationsNum;
    ChartDevelopFieldOption.series[3].count = diagnosesNum;
    ChartDevelopFieldOption.series[4].count = evaluationsNum;
    ChartDevelopFieldOption.series[5].count = applicationsNum;
    ChartDevelopFieldOption.series[6].count = othersNum;

    let myChart = echarts.init(ChartDevelopField.value);
    myChart.setOption(ChartDevelopFieldOption)
    myChart.resize()
  }).catch((err) => { })
}

// 4. distribution of scholars
const ChartDistributionScholars = ref()
let ChartDistributionScholarsOption = reactive(
  {

    series: [
      {
        name: '国家数据',
        type: 'map',
        map: 'world',
        roam: false,
        data: [],
        zoom: 1, // 设置初始缩放比例
        // 添加 visualMap 属性
      }
    ],
    visualMap: {
      min: 0,
      max: 100,
      inRange: {
        color: ['#E0E0FF', '#5e72e4']
      },
    },
    tooltip: {
      trigger: 'item',
      formatter: function (params) {
        if (isNaN(params.value)) return "Country: " + params.name + '<br>' + " Nums of Scholars: " + 0;
        return "Country: " + params.name + '<br>' + " Nums of Scholars: " + params.value;
      },
    }
  }
);
const initChartDistributionScholars = () => {
  request({
    url: '/repo/author/CountDistributionScholars',
    method: 'POST',
    data: {
      info: PaperInfoStore.TypeofPapers
    }
  })
    .then((res) => {
      console.log("res", res)
      let tempdata = []
      for (var i = 0; i < res.result.length; i++) {
        let tempObj = { name: '', value: 0 }
        tempObj.name = res.result[i].country
        tempObj.value = res.result[i].author_count
        tempdata.push(tempObj)
      }
      ChartDistributionScholarsOption.series[0].data = tempdata

      let myChart = echarts.init(ChartDistributionScholars.value);
      myChart.setOption(ChartDistributionScholarsOption)
      myChart.resize()
    }).catch((err) => {
      console.log(err)
    })

}




onMounted(() => {
  initChartNumber()
  initChartField()
  initChartDevelopField()
  initChartDistributionScholars()
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
