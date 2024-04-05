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
                        <h3 style="margin: 20px 0px 0px 40px;">{{ route.query.charts }}</h3>

                        <div class="card-header pb-0">
                            <div ref="chart" style="width: 100%; height: 600%">
                            </div>
                        </div>


                    </div>
                </div>
            </div>
            <el-dialog v-model="dialogVisible" title="Choose what you want:" width="30%">
                <el-radio-group v-model="Choosed" class="ml-4">
                    <el-radio label="Papers">Papers of {{ countryChoosen }} </el-radio>
                    <el-radio label="Authors">Authors of {{ countryChoosen }}</el-radio>
                </el-radio-group>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dialogVisible = false">Cancel</el-button>
                        <el-button type="primary" @click="ListByRadio">
                            Confirm
                        </el-button>
                    </span>
                </template>
            </el-dialog>
            <!-- <div style="height: 200px;"></div> -->
            <Foot></Foot>

        </div>
    </main>
</template>

<script  setup>
import SideNav from './components/SideNav.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';

import Foot from '../CustomizedComponents/Foot.vue';
import { useInstitutionStore } from '../store/institutionStore'
import { onMounted } from 'vue';
import { ref, computed, reactive } from 'vue';
import { usePaperInfoStore } from '../store/paperinfoStore'
import { request } from '../request';
import { useRouter, useRoute } from 'vue-router';
import * as echarts from 'echarts'
import "echarts/map/js/world.js";
import pinia from '../store/store'
import { useAuthorStore } from '../store/authorStore';
import lineStyle from "echarts/src/model/mixin/lineStyle";
const router = useRouter();
const route = useRoute()
const PaperInfoStore = usePaperInfoStore(pinia)
const AuthorStore=useAuthorStore(pinia)



let paginationObj_Charts = reactive({
    pagesize: 100,
    total: 0,
    pagercount: 15,
    pagenum: 1,
    searchkeywords: "",
    typerofPapers: "Combinatorial Testing",

})

const chart = ref();

// Bar chart
let echartsOptions_NUMBER_OF_PUBLICATION = reactive({

    xAxis: {
        type: 'category',
        data: [],
        name: 'year',
        axisLabel: {
            showMaxLabel: true,
            interval: 0,
            rotate: 90,
            fontSize: 14
        },
        nameTextStyle: {
            fontSize: 14 // 添加字体大小配置
        }
    },
    yAxis: {
        type: 'value',
        name: 'Num of Papers',
        fontSize: 14,
        nameTextStyle: {
            fontSize: 14 // 添加字体大小配置
        }

    },
    series: [
        {
            data: [],
            type: 'line',
            label: {
                show: true,
                fontSize: 14,
                position: 'outside',
            },
            lineStyle: {
                color: '#f53636' // 设置折线图的颜色
            },
        },
        {
            data: [],
            type: 'bar',
            itemStyle: { color: '#2dce89' }
        }
    ],
    tooltip: { // 设置触发方式为 item，即在数据项上触发
        trigger: 'item',
        // 使用 formatter 函数来自定义提示框的内容
        formatter: function (params) { // params 是一个包含数据信息的对象，具体可以参考 echarts 的文档 
            // 这里返回一个字符串，表示提示框的内容 
            return "Paper of " + params.name + " : " + params.value;
        },
        // trigger: 'axis',
        axisPointer: { type: 'cross' }
    }
})

// Pie chart
let echartsOptions_DISTRIBUTION_OF_FIELD = reactive({

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

// Map chart
let echartsOptions_SCHOLAR_IN_THE_WORLD = reactive(
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
                if (isNaN(params.value)) return "Country: " + params.name + '<br>' + " Nums of Paper: " + 0;
                return "Country: " + params.name + '<br>' + " Nums of Paper: " + params.value;
            },
        }
    }
)



//4
let echartsOptions_FIELD_COUNT_EACH_YEAR = reactive({
  xAxis: {
    type: 'category',
    data: [],
    name: 'Year',
    axisLabel: {
      showMaxLabel: true,
      interval: 0,
      rotate: 90,
      fontSize: 14
    },
    nameTextStyle: {
      fontSize: 14
    }
  },
  yAxis: {
    type: 'value',
    name: 'Num of Papers',
    fontSize: 14,
    nameTextStyle: {
      fontSize: 14
    },
    interval: 5
  },
  legend: {
    orient: 'vertical',
    right: 'right',
    textStyle: {
      fontSize: 14 // 添加字体大小配置
    }
  },
  series: [],  // We will dynamically populate this array based on the response data
  tooltip: {
    trigger: 'item',
    formatter: function (params) {
      return "Paper of " + params.name + " : " + params.value;
    },
    axisPointer: { type: 'cross' }
  }
});

//5
let echartsOptions_TOP_INSTITUTIONS = reactive({
    xAxis: {
  type: 'category',
      data: [],
      name: 'Institution',
      axisLabel: {
    showMaxLabel: true,
        interval: 0,
        rotate: 0,
        fontSize: 10,
        formatter: function (value) {
          const chunkSize = 18;
          const chunks = value.match(new RegExp('.{1,' + chunkSize + '}', 'g'));
          return chunks.join('\n');
        },
  },
  nameTextStyle: {
    fontSize: 14,
  },
},
yAxis: {
  type: 'value',
      name: 'Num of Papers',
      fontSize: 14,
      nameTextStyle: {
    fontSize: 14,
  },
  interval: 5,
},
legend: {
  orient: 'vertical',
      right: 'right',
      textStyle: {
    fontSize: 14,
  },
},
series: [],  // We will dynamically populate this array based on the response data
    tooltip: {
  trigger: 'item',
      formatter: function (params) {
    return "Paper of " + params.name + " : " + params.value;
  },
  axisPointer: { type: 'cross' },
},
}
);



const initChart_NUMBER_OF_PUBLICATION = () => {
    // 对于每一个年份，统计该年份内有多少篇papers，作为series的data
    request({
        url: "repo/list/countEachYear",
        method: 'POST',
        data: {
            info: PaperInfoStore.TypeofPapers
        }

    }).then((res) => {
        // console.log("countEachYear",res)
        let tempCatgory = []
        let tempdata = []
        for (var i = 0; i < res.countEachYear.length; i++) {
            tempCatgory.push(res.countEachYear[i].year)
            tempdata.push(res.countEachYear[i].RecordCount)
        }

        echartsOptions_NUMBER_OF_PUBLICATION.xAxis.data = tempCatgory
        echartsOptions_NUMBER_OF_PUBLICATION.series[0].data = tempdata
        echartsOptions_NUMBER_OF_PUBLICATION.series[1].data = tempdata
        // console.log(echartsOptions)
        // console.log("echartsOptions.xAxis", echartsOptions.xAxis)
        // echarts.registerTheme('customed', vintage)
        let myChart = echarts.init(chart.value);
        myChart.setOption(echartsOptions_NUMBER_OF_PUBLICATION)
        myChart.on('click', ListByYear)
        myChart.resize()


    }).catch(() => { })
}
const ListByYear = (param) => {
    // console.log("ListByYear")
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.searchKeyWords = param.name
    // console.log(param)
    // ChartResultStore.searchKeyWords = param.name;
    // ChartResultStore.paperinfos.length = 0
    // ChartResultStore.total = 0
    // ChartResultStore.TypeofPapers = PaperInfoStore.TypeofPapers
    request({
        url: "repo/list/listBy",
        method: 'POST',
        data: {
            pageInfo: paginationObj_Charts,
            listByKey: echartsOptions_NUMBER_OF_PUBLICATION.xAxis.name,
            listByValue: param.name
        }
    }).then((res) => {
        // console.log("列出的结果是", res)
        PaperInfoStore.paperinfos.push(...res.res.records)
        PaperInfoStore.total = res.res.total

    }).catch(() => { })

    router.push({
        path: '/Repo/Papers',
        query: {
            paginationActive: '关闭'
        }
    })
}

//echarts图表自适应方法
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

const initChart_DISTRIBUTION_OF_FIELD =  () => {

  //
  //   console.log("选择了initChart_DISTRIBUTION_OF_FIELD")
  //   console.log("PaperInfoStore.TypeofPapers",PaperInfoStore.TypeofPapers)

     request({
        url: "repo/list/countField",
        method: 'POST',
        data: {
            info: PaperInfoStore.TypeofPapers
        }

    }).then((res) => {
    //    console.log(res)
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
        let myChart = echarts.init(chart.value);
        myChart.setOption(echartsOptions_DISTRIBUTION_OF_FIELD)
        myChart.on('click', ListByResearchArea)

    }).catch((err)=>{
      console.log("err",err)
    })

}

const ListByResearchArea = (param) => {
    // console.log("listByResearchArea", param.data.name)
    //
    // console.log(param)
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.searchKeyWords = param.data.name
    // ChartResultStore.searchKeyWords = param.data.name;
    // ChartResultStore.paperinfos.length = 0
    // ChartResultStore.total = 0
    // ChartResultStore.TypeofPapers = paperInfoStore.TypeofPapers
    request({
        url: "repo/list/listBy",
        method: 'POST',
        data: {
            pageInfo: paginationObj_Charts,
            listByKey: 'field',
            listByValue: param.data.name
        }
    }).then((res) => {
        // console.log("列出的结果是", res)
        PaperInfoStore.paperinfos.push(...res.res.records)
        PaperInfoStore.total = res.res.total

    }).catch(() => { })
    router.push({
        path: '/Repo/Papers',
        query: {
            paginationActive: '关闭'
        }
    })
}


const dialogVisible = ref(false)
const Choosed = ref('')
const countryChoosen = ref('')



const initChart_SCHOLAR_IN_THE_WORLD = () => {
    request({
        url: '/repo/author/countEachCountry',
        method: 'POST',
        data: {
            info: PaperInfoStore.TypeofPapers
        }
    })
        .then((res) => {
            // console.log("countEachCountry", res)

            let tempdata = []
            for (var i = 0; i < res.countEachCountry.length; i++) {
                let tempObj = { name: '', value: 0 }
                tempObj.name = res.countEachCountry[i].country
                tempObj.value = res.countEachCountry[i].count
                tempdata.push(tempObj)
            }
            echartsOptions_SCHOLAR_IN_THE_WORLD.series[0].data = tempdata

            let myChart = echarts.init(chart.value);
            myChart.setOption(echartsOptions_SCHOLAR_IN_THE_WORLD)
            myChart.on('click', clickMap)
        }).catch(() => { })

}

const clickMap = (params) => {
    dialogVisible.value = true
    countryChoosen.value = params.name

}
let searchObj = reactive({
    pagesize: 200,
    total: '',
    pagercount: 15,
    pagenum: 1,
    searchkeywords: "",
    typerofPapers: PaperInfoStore.TypeofPapers,

})
const ListByRadio = () => {
    // console.log("choose:", Choosed.value)
    PaperInfoStore.searchKeyWords=''
    if (Choosed.value == "Authors") {
        request({
            url: '/repo/author/ListAuthorsByCountry',
            method: 'POST',
            data: {
                country: countryChoosen.value,
                typeofPapers: PaperInfoStore.TypeofPapers
            }
        }).then((res) => {
            // console.log("Authors:",res.AuthorList)
            AuthorStore.authorsArray.length=0
            AuthorStore.authorsArray.push(...res.AuthorList)
            AuthorStore.country = countryChoosen.value
            router.push({
                path: '/Repo/Scholars',

            })

        }).catch(() => { })
    }
    if (Choosed.value == "Papers") {

        PaperInfoStore.searchKeyWords = countryChoosen.value;
        PaperInfoStore.total = 0
        request({
            url: '/repo/author/ListPapersByCountry',
            method: 'POST',
            data: {
                country: countryChoosen.value,
                searchObj
            }
        }).then((res) => {

            // console.log("列出该国家的papers", res)
            PaperInfoStore.paperinfos.length = 0
            PaperInfoStore.paperinfos.push(...res.ListPapersByCountryResult)
            // 这里的数据是对的，地图上的数据是错误的
            PaperInfoStore.total = res.ListPapersByCountryResult.length

            router.push({
                path: '/Repo/Papers',
                query: {
                    paginationActive: '关闭'
                }
            })
        }).catch(() => { })
    }
}

const initChart_FIELD_COUNT_EACH_YEAR = () => {
  request({
    url: "/repo/list/countFieldEachYear",
    method: 'POST',
    data: {
      info: PaperInfoStore.TypeofPapers
    }
  }).then((res) => {
    // console.log(res.countFieldEachYear);

    // 获取年份和领域
    let years = Array.from(new Set(res.countFieldEachYear.map(item => item.year)));
    let fields = Array.from(new Set(res.countFieldEachYear.map(item => item.field)));

    // 创建一个新的数组来存储处理后的数据
    let processedData = [];

    // 遍历每个领域和年份
    fields.forEach(field => {
      years.forEach(year => {
        // 检查数据中是否包含当前领域和年份的记录
        let record = res.countFieldEachYear.find(item => item.field === field && item.year === year);
        // 如果记录不存在，则插入一个值为零的数据点
        if (!record) {
          if(field!=="application"){
            processedData.push({
              field: field,
              year: year,
              RecordCount: 0.001
            });
          }
        } else {
          // 否则，将原始记录插入新数组
          processedData.push(record);
        }
      });
    });

    // 输出处理后的数据
    // console.log(processedData);

    // 使用处理后的数据设置图表
    let categories = Array.from(new Set(processedData.map(item => item.year)));
    echartsOptions_FIELD_COUNT_EACH_YEAR.xAxis.data = categories;

    let seriesData = [];
    fields.forEach(field => {
      let fieldData = processedData
          .filter(item => item.field === field && item.year <= 2023)
          .map(item => item.RecordCount || item.RecordCount === 0 ? item.RecordCount : null);

      seriesData.push({
        name: field,
        type: 'line',
        data: fieldData,
        lineStyle: {
          width: 3 // 设置线段宽度，根据需要调整
        },
      });
    });

    echartsOptions_FIELD_COUNT_EACH_YEAR.series = seriesData;

    let myChart = echarts.init(chart.value);
    myChart.setOption(echartsOptions_FIELD_COUNT_EACH_YEAR);
    myChart.on('click', ListByYear)
    myChart.resize();
  }).catch(() => { });
};

const initChart_TOP_INSTITUTIONS = () => {
  // 请求数据
  request({
    url: "/repo/list/topInstitutions",
    method: 'POST',
    data: {
      info: PaperInfoStore.TypeofPapers
    }
  }).then((res) => {
    const topInstitutionsData = res.topInstitutions;

    // 提取机构名称和论文数量
    const institutionNames = topInstitutionsData.slice(1).map(item => item.institution);
    const paperCounts = topInstitutionsData.slice(1).map(item => item.paperCount);

    // 更新 ECharts 配置
    echartsOptions_TOP_INSTITUTIONS.xAxis.data = institutionNames;
    echartsOptions_TOP_INSTITUTIONS.series = [{
      name: 'Num of Papers',
      type: 'bar',
      data: paperCounts,
      barWidth: '50%',
      itemStyle: {
        color: 'rgba(54,79,245,0.74)',
      },
    }];

    // 初始化图表
    let myChart = echarts.init(chart.value);
    myChart.setOption(echartsOptions_TOP_INSTITUTIONS);
    myChart.resize();
  }).catch(() => {
    // 错误处理
  });
};



onMounted(() => {

    // console.log("进入ChartDisplay！！")
    // console.log("route.query.charts是",route.query.charts)
    if (route.query.charts == "Number of Publication") {
        initChart_NUMBER_OF_PUBLICATION()

    }
    if (route.query.charts == "Distribution of Field") {
        initChart_DISTRIBUTION_OF_FIELD()
    }
    if (route.query.charts == "Distributions in the World") {
        initChart_SCHOLAR_IN_THE_WORLD()
    }
    if (route.query.charts =="Relative Proportion of Field"){
      initChart_FIELD_COUNT_EACH_YEAR()
    }
  if(route.query.charts =="Top Institutions"){
    initChart_TOP_INSTITUTIONS()
  }
    resizeChart()
})


</script>



<style scoped></style>
