
<template>
    <div class="min-height-300 bg-primary position-absolute w-100"></div>
    <SideNav></SideNav>
    <main class="main-content position-relative border-radius-lg ">
        <!-- Navbar -->
        <Navbar></Navbar>
        <!-- End Navbar -->

        <div class="container-fluid py-4">
            <!-- 1. statistic -->
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
                                        <span class="text-info font-weight-bolder">+3%</span>
                                        since last year
                                    </div>
                                </div>
                                <div class="col-4 text-end">
                                    <div
                                        class="icon icon-shape bg-gradient-primary shadow-primary text-center rounded-circle">
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
                                        <h5 class="font-weight-bolder">{{ AuthorStore.authorsArray.length }}</h5>
                                        <i class="fa fa-arrow-up text-info"></i>
                                        <span class="text-info font-weight-bolder">+3%</span>
                                        since last year
                                    </div>
                                </div>
                                <div class="col-4 text-end">
                                    <div
                                        class="icon icon-shape bg-gradient-danger shadow-danger text-center rounded-circle">
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
                                    <div
                                        class="icon icon-shape bg-gradient-success shadow-success text-center rounded-circle">
                                        <i class="ni ni-paper-diploma text-lg opacity-10" aria-hidden="true"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 2. -->
            <div class="row mt-4">
                <!-- 2.1 cumulative number of publications -->
                <div class="col-lg-7 mb-lg-0 mb-4">
                    <div class="card z-index-2 h-100">
                        <div class="card-header pb-0 pt-3 bg-transparent">
                            <h5>Cumulative nunmber of publications</h5>
                            <p class="text-muted mb-0">From 1985 to 2024</p>
                        </div>
                        <div class="card-body p-3" ref="cardBody">
                            <!-- 图表在这里 -->
                            <!-- div 元素，用 ref 属性绑定了一个名为 main 的虚拟 DOM 引用，这个引用会在 <script> 中使用。 -->
                            <div class="echarts" id="charts" ref="chart" style="height:300%; width:100%"></div>
                        </div>
                    </div>
                </div>

                <!-- 2.2 recent project -->
                <div class="col-lg-5">
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
                                        <p class="text-black mb-1">That’s my skill. I’m not really specifically talented at
                                            anything except for the
                                            ability to learn.</p>
                                    </div>
                                </div>
                            </div>
                            <button class="carousel-control-prev w-5 me-3" type="button"
                                data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                            <button class="carousel-control-next w-5 me-3" type="button"
                                data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
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

<script  setup>
import * as echarts from 'echarts'
import SideNav from './components/SideNav.vue';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import { onMounted, reactive, ref, } from 'vue';
import { request } from '../request';
import { useRouter } from 'vue-router';


import { usePaperInfoStore } from '../store/paperinfoStore'
import { useAuthorStore } from '../store/authorStore'
import { useInstitutionStore } from '../store/institutionStore'
import { useVenueStore } from '../store/venueStore'

const PaperInfoStore = usePaperInfoStore()
const AuthorStore = useAuthorStore()
const InstitutionStore = useInstitutionStore()
const VenueStore = useVenueStore()
const router = useRouter();


//分页查询的传递的对象  
let paginationObj = reactive({
    pagesize: 30,
    total: 0,
    pagercount: 15,
    pagenum: 1,
    searchkeywords: "",
    typerofPapers: "Combinatorial Testing",

})




const chart = ref();
let echartsOptions = reactive({
    xAxis: {
        type: 'category',
        data: [],
        name: 'year',
        axisLabel: {
            showMaxLabel: true,
            interval: 0,
            rotate: 90,
        },

    },
    yAxis: {
        type: 'value',
        name: 'Num of Papers',


    },
    series: [
        {
            data: [],
            type: 'line',
            label: {
                show: true,
                position: 'outside',

            },
            lineStyle: {
                color: '#f5365c' // 设置折线图的颜色
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

// function methd(){}

// const methd =()=>{}


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
        let tempCatgory = []
        let tempdata = []
        for (var i = 0; i < res.countEachYear.length; i++) {
            tempCatgory.push(res.countEachYear[i].year)
            tempdata.push(res.countEachYear[i].RecordCount)
        }

        echartsOptions.xAxis.data = tempCatgory
        echartsOptions.series[0].data = tempdata
        echartsOptions.series[1].data = tempdata
        // console.log(echartsOptions)
        // console.log("echartsOptions.xAxis", echartsOptions.xAxis)
        // echarts.registerTheme('customed', vintage)
        let myChart = echarts.init(chart.value);
        myChart.setOption(echartsOptions)
        myChart.on('click', ListByYear)
        myChart.resize()


    }).catch(() => { })
}
let paginationObj_Charts = reactive({
    pagesize: 100,
    total: 0,
    pagercount: 15,
    pagenum: 1,
    searchkeywords: "",
    typerofPapers: "Combinatorial Testing",

})
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
            color: '#f5365c' // 设置折线图的颜色
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
        path: '/repo/Papers',
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


const listAllPapers = () => {
    // 获取所有文章的数据
    request({
        url: '/repo/list/listAllpapers',
        method: 'POST',
        data: paginationObj
    }).then((res) => {
        // console.log(res)
        PaperInfoStore.paperinfos.length = 0
        PaperInfoStore.searchKeyWords = ''
        PaperInfoStore.paperinfos.push(...res.listEntityPage.records)

        paginationObj.total = res.listEntityPage.total
        PaperInfoStore.total = paginationObj.total

        // console.log(paginationObj)

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


onMounted(() => {
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

})





</script>


<style scoped>
.echarts {
    width: 100%;
    height: 100%;
}
</style>
