<template>
    <div class="min-height-300 bg-primary position-absolute w-100"></div>
    <SideNav></SideNav>
    <main class="main-content position-relative border-radius-lg ">
        <Navbar></Navbar>


        <div class="container-fluid py-4">
            <div class="row">
                <div class="col-12">
                    <div class="card mb-4">
                        <div class="card-header pb-0">
                            <h3>Venues List</h3>
                            <p class="text-muted text-sm mb-0">{{ VenueStore.VenueArray.length }} Venues
                                found</p>
                        </div>
                        <div style="margin: 60px 0px 0px 20px;">

                        </div>
                        <div class="card-body px-0 pt-0 pb-2">
                            <h6 class="VenueType"> Venues</h6>
                            <div v-for="(chunk, index) in chunkedArray" :key="index" class="row"
                                style="margin: 0 0 0 20px;">
                                <div class="col" v-for="(venue, colIndex) in chunk" :key="colIndex"
                                    @click="searchByAbbr(venue)"><span>{{ venue }}</span>
                                </div>
                            </div>
                            <h6 class="VenueType"> Book</h6>
                            <div v-for="(chunk, index) in chunkedArrayBook" :key="index" class="row"
                                style="margin: 0 0 0 20px;">
                                <div class="col" v-for="(venue, colIndex) in chunk" :key="colIndex"
                                    @click="searchByBookTitle(venue)"><span>{{ venue }}</span>
                                </div>
                            </div>

                            <h6 class="VenueType"> Phd thesis</h6>
                            <div v-for="(chunk, index) in chunkedArrayPhd" :key="index" class="row"
                                style="margin: 0 0 0 20px;">
                                <div class="col" v-for="(venue, colIndex) in chunk" :key="colIndex"
                                    @click="searchByBookTitle(venue)"><span>{{ venue }}</span>
                                </div>
                            </div>

                            <h6 class="VenueType"> Other</h6>
                            <div v-for="(chunk, index) in chunkedArrayOther" :key="index" class="row"
                                style="margin: 0 0 0 20px;">
                                <div class="col" v-for="(venue, colIndex) in chunk" :key="colIndex"
                                    @click="searchByBookTitle(venue)"><span>{{ venue }}</span>
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
import Navbar from '../ComponentCommon/Navbar.vue';

import Foot from '../ComponentCommon/Foot.vue';
import { useVenueStore } from '../store/venueStore'
import { onMounted } from 'vue';
import { ref, computed, reactive } from 'vue';
import { usePaperInfoStore } from '../store/paperinfoStore'
import { request } from '../request';
import { useRouter } from 'vue-router';

const router = useRouter();
const PaperInfoStore = usePaperInfoStore()

const VenueStore = useVenueStore()
const itemsPerRow = ref(6);
const itemsPerRowBook = ref(2);
const itemsPerRowPhd = ref(2);
const itemsPerRowOther = ref(2);

const chunkedArray = computed(() => {
    const result = [];
    for (let i = 0; i < VenueStore.VenueArray.length; i += itemsPerRow.value) {
        result.push(VenueStore.VenueArray.slice(i, i + itemsPerRow.value));
    }
    return result;
});

const chunkedArrayBook = computed(() => {
    const result = [];
    for (let i = 0; i < VenueStore.VenueArrayBook.length; i += itemsPerRowBook.value) {
        result.push(VenueStore.VenueArrayBook.slice(i, i + itemsPerRowBook.value));
    }
    return result;
});
const chunkedArrayPhd = computed(() => {
    const result = [];
    for (let i = 0; i < VenueStore.VenueArrayPhd.length; i += itemsPerRowPhd.value) {
        result.push(VenueStore.VenueArrayPhd.slice(i, i + itemsPerRowPhd.value));
    }
    return result;
});
const chunkedArrayOther = computed(() => {
    const result = [];
    for (let i = 0; i < VenueStore.VenueArrayOther.length; i += itemsPerRowOther.value) {
        result.push(VenueStore.VenueArrayOther.slice(i, i + itemsPerRowOther.value));
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


const searchByBookTitle = (booktitle) => {
    // console.log("booktitleVenue是", booktitle)
    searchObj.searchkeywords = booktitle
    PaperInfoStore.searchKeyWords = booktitle;
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.total = 0
    request({
        url: "/repo/list/searchByBooktitle",
        method: 'POST',
        data: searchObj
    }).then((res) => {
        // console.log("res是", res)
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


const searchByAbbr = (venue) => {
    // console.log("venue是", venue)
    searchObj.searchkeywords = venue
    PaperInfoStore.searchKeyWords = venue;
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.total = 0
    request({
        url: "/repo/list/searchByAbbr",
        method: 'POST',
        data: searchObj
    }).then((res) => {
        // console.log("res是", res)
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

const searchByVenues = (venue) => {
    // console.log(institution)
    searchObj.searchkeywords = venue;
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.searchKeyWords = venue

    request({
        url: 'repo/list/searchByVenues',
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
})
</script>



<style scoped>
.col span {
    transition: font-size 0.3s ease;
    /* 添加过渡效果 */
}

.col:hover span {
    font-size: calc(12px + 6px);
    /* 这里使用 calc 计算新的 font-size，16px 为默认值 */
    cursor: pointer;
    color: rgb(94, 114, 228);
    text-decoration: underline;
}

.VenueType {
    margin-left: 20px;
}
</style>
