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
                        <div class="card-header pb-0">
                            <h3>Institutions List</h3>
                            <p class="text-muted text-sm mb-0">{{ InstitutionStore.InstitutionArray.length }} Institutions
                                found</p>
                        </div>
                        <div style="margin: 60px 0px 0px 20px;">
                            <h6> Institutions Name</h6>
                        </div>
                        <div class="card-body px-0 pt-0 pb-2">

                            <div v-for="(chunk, index) in chunkedArray" :key="index" class="row"
                                style="margin: 0 0 0 20px;">
                                <div class="col" v-for="(institution, colIndex) in chunk" :key="colIndex"
                                    @click="searchByInstitutions(institution)"><span>{{ institution }}</span>
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

<script  setup>
import SideNav from './components/SideNav.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import pinia from '../store/store'
import Foot from '../CustomizedComponents/Foot.vue';
import { useInstitutionStore } from '../store/institutionStore'
import { onMounted } from 'vue';
import { ref, computed, reactive } from 'vue';
import { usePaperInfoStore } from '../store/paperinfoStore'
import { request } from '../request';
import { useRouter } from 'vue-router';

const router = useRouter();
const PaperInfoStore = usePaperInfoStore(pinia)

const InstitutionStore = useInstitutionStore(pinia)
const itemsPerRow = ref(3);

const chunkedArray = computed(() => {
    const result = [];
    for (let i = 0; i < InstitutionStore.InstitutionArray.length; i += itemsPerRow.value) {
        result.push(InstitutionStore.InstitutionArray.slice(i, i + itemsPerRow.value));
    }
    return result;
});

let searchObj = reactive({
    pagesize: 200,
    total: '',
    pagercount: 15,
    pagenum: 1,
    searchkeywords: "",
    typerofPapers: PaperInfoStore.TypeofPapers,

})




const searchByInstitutions = (institution) => {
    // console.log(institution)
    searchObj.searchkeywords = institution;
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.searchKeyWords = institution

    request({
        url: 'repo/list/searchByInstitutions',
        method: 'POST',
        data: searchObj
    }).then((res) => {
        // console.log("searchByInstitutions", res)
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

onMounted(() => {
    // console.log(InstitutionStore.InstitutionArray.length)
    // 获取所有institutions
    listAllInstitutions()
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
</style>
