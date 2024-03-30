<template>
    <div class="min-height-300 bg-info position-absolute w-100"></div>
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
                                        <p class="mb-1 font-weight-bolder ">Benchmark Sets</p>
                                        <h5 class="font-weight-bolder">{{ BenchmarkSetCount }}</h5>
                                    </div>
                                </div>
                                <div class="col-4 text-end">
                                    <div class="icon icon-shape bg-gradient-info shadow-primary text-center rounded-circle">
                                        <i class="ni ni-money-coins  opacity-10" aria-hidden="true"></i>
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
                                        <p class="mb-1 font-weight-bolder ">Constrained Models</p>
                                        <h5 class="font-weight-bolder">{{ ConsModelCount }}</h5>
                                        <!-- <i class="fa fa-arrow-up text-info"></i>
                      <span class="text-info font-weight-bolder">+3%</span> since last year -->
                                    </div>
                                </div>
                                <div class="col-4 text-end">
                                    <div
                                        class="icon icon-shape bg-gradient-danger shadow-danger text-center rounded-circle">
                                        <i class="ni ni-world  opacity-10" aria-hidden="true"></i>
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
                                        <p class="mb-1 font-weight-bolder ">Unconstrained Models</p>
                                        <h5 class="font-weight-bolder">{{ UnConsModelCount }}</h5>


                                    </div>
                                </div>
                                <div class="col-4 text-end">
                                    <div
                                        class="icon icon-shape bg-gradient-success shadow-success text-center rounded-circle">
                                        <i class="ni ni-paper-diploma  opacity-10" aria-hidden="true"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>



            <!-- 2. Benchmark Sets Table -->
            <div class="row mt-4">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header pb-0">
                            <h6 class="mb-1 font-weight-bolder ">All Benchmark Sets</h6>
                        </div>
                        <div class="card-body px-0 pt-0 pb-2">
                            <div class="row pt-2">
                                <div class="col-12">
                                    <div class="table-responsive p-0">
                                        <table class="table align-items-center mb-0">
                                            <thead>
                                                <tr>
                                                    <th class=" text-secondary text-center  font-weight-bolder ">
                                                        Benchmark Set Name</th>

                                                    <th class=" text-secondary text-center  font-weight-bolder ">
                                                        Year</th>
                                                    <th
                                                        class="text-center  text-secondary  font-weight-bolder text-center ">
                                                        Num of References</th>
                                                    <th class=" text-secondary text-center  font-weight-bolder  ps-2">
                                                        Num of Models</th>

                                                    <th class=" text-secondary  font-weight-bolder text-center  ps-2">
                                                        Num of Cons Models</th>
                                                    <th class=" text-secondary  font-weight-bolder text-center  ps-2">
                                                        Num of Uncons Models</th>


                                                    <th
                                                        class="text-center  text-secondary  font-weight-bolder text-center ">
                                                        Operation</th>
                                                </tr>
                                            </thead>
                                            <tbody>

                                                <tr v-for="(ben, index) in TableData" :index="index" :key="index">




                                                    <td class="align-middle text-center ">
                                                        <h6 class="mb-0  ">{{ ben.benchmarkset }}</h6>
                                                    </td>

                                                    <td class="align-middle text-center ">
                                                        <h6 class="mb-0  ">{{ ben.maxReferenceYear }}</h6>
                                                    </td>

                                                    <td class="align-middle text-center ">


                                                        <ArgonButton color="success" class="  mb-0"
                                                            @click="showReference(ben.benchmarkset)">
                                                            {{ ben.referenceCount }}
                                                            <span class="bi bi-file-text"></span>
                                                        </ArgonButton>
                                                    </td>

                                                    <td class="align-middle text-center ">
                                                        <p class=" font-weight-bold mb-0 text-center">{{
                                                            ben.unconsModelCount + ben.consModelCount }}</p>
                                                    </td>

                                                    <td class="align-middle text-center ">
                                                        <p class=" font-weight-bold mb-0 text-center">
                                                            {{ ben.consModelCount }}</p>
                                                    </td>

                                                    <td class="align-middle text-center ">
                                                        <p class=" font-weight-bold mb-0 text-center">
                                                            {{ ben.unconsModelCount }}</p>
                                                    </td>



                                                    <td class="align-middle text-center">
                                                        <ArgonButton color="info" class="  mb-0">
                                                            <span class="bi bi-caret-left-fill"></span>
                                                            Details
                                                        </ArgonButton>
                                                    </td>

                                                </tr>





                                            </tbody>
                                        </table>
                                    </div>

                                </div>

                            </div>
                        </div>

                    </div>
                </div>
            </div>


            <Foot></Foot>
        </div>
    </main>
</template>

<script setup>
import { reactive, onMounted, ref } from 'vue';
import { request, CheckLogin } from '../request';
import ArgonButton from '../ComponentCommon/ArgonButton.vue';
import { ElLoading } from 'element-plus'
import { useRouter } from 'vue-router';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import SideNav from './components/SideNav.vue'
import { useModuleStore } from '../store/module';
import { useUserStore } from '../store/userStore';
import { usePaperInfoStore } from '../store/paperinfoStore'
import { usePaginationStore } from '../store/paginationStore'
import pinia from '../store/store';

const router = useRouter()

const PaginationStore = usePaginationStore(pinia)
const PaperInfoStore = usePaperInfoStore(pinia)
const moduleStore = useModuleStore(pinia)
const userStore = useUserStore(pinia)
const BenchmarkSetCount = ref(0)
const ConsModelCount = ref(0)
const UnConsModelCount = ref(0)
const TableData = reactive([])

// 定义排序函数
const sortByCountSum = (a, b) => {
    const countSumA = a.unconsModelCount + a.consModelCount;
    const countSumB = b.unconsModelCount + b.consModelCount;

    // 按照和的倒序排序
    return countSumB - countSumA;
}
const initBenchMarkHome = async () => {
    try {
        const initBenchMarkHomeRes = await request({
            url: 'benchmark/initBenchMarkHome',
            method: 'POST'
        })
        // console.log("initBenchMarkHomeRes", initBenchMarkHomeRes)
        BenchmarkSetCount.value = initBenchMarkHomeRes.BenchmarkSetCount
        ConsModelCount.value = initBenchMarkHomeRes.constrainedCount
        UnConsModelCount.value = initBenchMarkHomeRes.unconstrainedCount
        TableData.push(...initBenchMarkHomeRes.BenchmarkStats)
        // TableData 按maxReferenceYear 倒序排列
        
        TableData.sort((a, b) => b.maxReferenceYear - a.maxReferenceYear)


    } catch (error) {

    }
}

const showReference = async (benchmark) => {
    console.log("benchmark", benchmark)
    moduleStore.CurrentModuleDetails = benchmark
    moduleStore.CurrentRoute = 'Benchmark_Home'
    let loadingInstance = ElLoading.service({ fullscreen: true })
    try {

        PaginationStore.pagenum = 1
        PaginationStore.pagesize = 25
        PaginationStore.searchkeywords = ''
        PaginationStore.column = ''
        PaperInfoStore.paperinfos.length = 0
        PaperInfoStore.searchKeyWords = benchmark

        const getReferenceTitleRes = await request(
            {
                url: 'benchmark/getReferenceTitle',
                method: 'POST',
                data:
                {
                    benchmarkset: benchmark
                }
            }
        )

        // console.log("getReferenceTitleRes", getReferenceTitleRes)
        PaperInfoStore.paperinfos.push(...getReferenceTitleRes.res);
        PaginationStore.total = getReferenceTitleRes.res.length;
        PaperInfoStore.total = PaginationStore.total;
        router.push({
            path: '/repository/papers',
            query: {
                module: 'Benchmark',
            }
        })

        loadingInstance.close()




    } catch (error) {
        console.log(error)
        loadingInstance.close()

    }

}

onMounted(async () => {
    let loadingInstance = ElLoading.service({ fullscreen: true })
    // 检查用户登录状态
    await CheckLogin()
    moduleStore.CurrentModule = 'Benchmark'
    moduleStore.CurrentModuleDetails = ''
    moduleStore.CurrentRoute = 'Benchmark_Home'
    try {

        await initBenchMarkHome()

        loadingInstance.close()

    } catch (error) {

        loadingInstance.close()

    }


})
</script>

<style scoped></style>
