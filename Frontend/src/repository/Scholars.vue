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
              <h3>Scholars</h3>
              <p class="text-muted mb-0"><span class="badge bg-success">{{ AuthorStore.authorsArray.length }}</span>
                scholars across <span class="badge bg-success">XXX</span> institutions found</p>
            </div>
            <div style="margin: 60px 0px 0px 20px;">
              <h6> Scholars Name</h6>
            </div>
            <div class="card-body px-0 pt-0 pb-2">

              <div v-for="(chunk, index) in chunkedArray" :key="index" class="row" style="margin: 0 0 0 20px;">
                <div class="col" v-for="(author, colIndex) in chunk" :key="colIndex" @click="searchByAuthor(author)">
                  <span>{{ author }}</span>
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
import { onMounted } from 'vue';
import { ref, computed, reactive } from 'vue';
import { usePaperInfoStore } from '../store/paperinfoStore'
import { request } from '../request';
import { useRouter } from 'vue-router';
import pinia from '../store/store';
import { useAuthorStore } from '../store/authorStore'
import {  listAllScholars } from './commonFunction';
const AuthorStore = useAuthorStore(pinia)
const router = useRouter();
const PaperInfoStore = usePaperInfoStore(pinia)

const itemsPerRow = ref(5);

const chunkedArray = computed(() => {
  const result = [];
  for (let i = 0; i < AuthorStore.authorsArray.length; i += itemsPerRow.value) {
    result.push(AuthorStore.authorsArray.slice(i, i + itemsPerRow.value));
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

const searchByAuthor = (authorname) => {
  searchObj.searchkeywords = authorname;
  PaperInfoStore.paperinfos.length = 0
  PaperInfoStore.searchKeyWords = authorname

  request({
    url: 'repo/list/searchByAuthor',
    method: 'POST',
    data: searchObj
  }).then((res) => {
    // console.log("searchByAuthor的res", res)
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
let paginationObj = reactive({
  pagesize: 30,
  total: 0,
  pagercount: 15,
  pagenum: 1,
  searchkeywords: "",
  typerofPapers: "Combinatorial Testing",
})
onMounted(() => {

    // 获取所有scholars
    listAllScholars()



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
