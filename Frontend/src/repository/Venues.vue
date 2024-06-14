<template>
  <div class="min-height-300 bg-primary position-absolute w-100"></div>
  <SideNav></SideNav>
  <main class="main-content position-relative border-radius-lg ">
    <Navbar></Navbar>

    <div class="container-fluid py-4">


      <div class="row" @click="handleDtClick">
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
import Navbar from '../CustomizedComponents/Navbar.vue';

import Foot from '../CustomizedComponents/Foot.vue';
import { useVenueStore } from '../store/RepositoryStore/venueStore'
import { onMounted } from 'vue';
import { ref, computed, reactive } from 'vue';
import { usePaperInfoStore } from '../store/RepositoryStore/paperinfoStore'
import { request } from '../request';
import { useRouter } from 'vue-router';
import pinia from '../store/store';
import { useModuleStore } from '../store/module';
import { ElLoading } from 'element-plus'
import { usePaginationStore } from '../store/RepositoryStore/paginationStore'
import ArgonButton from '../CustomizedComponents/ArgonButton.vue';

const PaginationStore = usePaginationStore(pinia)
const router = useRouter();
const PaperInfoStore = usePaperInfoStore(pinia)
const moduleStore = useModuleStore(pinia)
const VenueStore = useVenueStore(pinia)





const handleDtClick = async (event) => {
  if (event.target.tagName == 'DT') {
    let loadingInstance = ElLoading.service({ fullscreen: true })

    moduleStore.CurrentModuleDetails = event.target.textContent;

    PaginationStore.pagenum = 1
    PaginationStore.pagesize = 25
    PaginationStore.searchkeywords = event.target.textContent;
    PaginationStore.column = 'abbr'

    PaperInfoStore.paginationOffset = 0
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.searchKeyWords = event.target.textContent;

    try {

      const searchByVenueRes = await request({
        url: 'repo/list/searchBy',
        method: 'POST',
        data:
        {
          pagesize: PaginationStore.pagesize,
          searchkeywords: PaginationStore.searchkeywords,
          column: PaginationStore.column,
          pagenum: PaginationStore.pagenum,
        }
      })
      PaperInfoStore.paperinfos.push(...searchByVenueRes.res.records)
      PaperInfoStore.total = searchByVenueRes.res.total
      PaginationStore.total = searchByVenueRes.res.total

      loadingInstance.close()
      router.push({
        path: '/repository/papers',
        query: {
          module: 'Venues',
        }
      })




    } catch (error) {

    }

  }

};
onMounted(() => {
  let loadingInstance = ElLoading.service({ fullscreen: true })

  moduleStore.CurrentSubSystem='Repository'
  moduleStore.CurrentSubSystemRoute='Repository_Home'
  
  moduleStore.CurrentRoute = 'Repository_Venues'
  moduleStore.CurrentModule = 'Venues'

  loadingInstance.close()

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

dl dt {
  transition: font-size 0.3s ease;
}

dl dt:hover {
  transform: scale(1.03);
  /* 这里使用 calc 计算新的 font-size，16px 为默认值 */
  cursor: pointer;
  color: rgb(94, 114, 228);
  text-decoration: underline;
}
</style>
