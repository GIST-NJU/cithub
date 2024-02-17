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
              <h3>Selected Publication Venues</h3>
              <p class="text-muted">Major software engineering journals and conferences in which CIT studies are
                published.</p>
            </div>
            <div class="card-body">
              <h5>Journals</h5>
              <dl class="row">
                <dt class="col-sm-1">TSE</dt>
                <dd class="col-sm-11">IEEE Transactions on Software Engineering</dd>
                <dt class="col-sm-1">TOSEM</dt>
                <dd class="col-sm-11">ACM Transactions on Software Engineering and Methodology</dd>
                <dt class="col-sm-1">EMSE</dt>
                <dd class="col-sm-11">Empirical Software Engineering</dd>
                <dt class="col-sm-1">IST</dt>
                <dd class="col-sm-11">Information and Software Technology</dd>
                <dt class="col-sm-1">JSS</dt>
                <dd class="col-sm-11">Journal of Systems and Software</dd>
                <dt class="col-sm-1">STVR</dt>
                <dd class="col-sm-11">Software Testing, Verification and Reliability</dd>
              </dl>

              <h5>Conferences and Workshops</h5>
              <dl class="row">
                <dt class="col-sm-1">ICSE</dt>
                <dd class="col-sm-11">International Conference on Software Engineering</dd>
                <dt class="col-sm-1">FSE</dt>
                <dd class="col-sm-11">ACM SIGSOFT Symposium on the Foundation of Software Engineering</dd>
                <dt class="col-sm-1">ASE</dt>
                <dd class="col-sm-11">International Conference on Automated Software Engineering</dd>
                <dt class="col-sm-1">ISSTA</dt>
                <dd class="col-sm-11">International Symposium on Software Testing and Analysis</dd>
                <dt class="col-sm-1">SANER</dt>
                <dd class="col-sm-11">International Conference on Software Analysis, Evolution, and Reengineering</dd>
                <dt class="col-sm-1">ISSRE</dt>
                <dd class="col-sm-11">International Symposium on Software Reliability Engineering</dd>
                <dt class="col-sm-1">ICST</dt>
                <dd class="col-sm-11">International Conference on Software Testing, Verification and Validation</dd>
                <dt class="col-sm-1">IWCT</dt>
                <dd class="col-sm-11">International Workshop on Combinatorial Testing</dd>
              </dl>
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
import pinia from '../store/store';
const router = useRouter();
const PaperInfoStore = usePaperInfoStore(pinia)

const VenueStore = useVenueStore(pinia)
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
