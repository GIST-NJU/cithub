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
              <ul style="margin: 0px 0px 0px 0px" class="nav nav-tabs">
                <li class="nav-item" :class="{ 'show': activeTab === 'Scholars' }">
                  <a class="nav-link" @click="setActiveTab('Scholars')">
                    <span>Scholars</span> <span v-if="activeTab == 'Scholars'">
                      <ArgonBadge variant="gradient" color="success" style="color:white"># {{
                        AuthorStore.authorsArray.length }}</ArgonBadge>
                    </span>
                  </a>
                </li>
                <li class="nav-item" :class="{ 'show': activeTab === 'Institutions' }">
                  <a class="nav-link" @click="setActiveTab('Institutions')">
                    <span>Institutions</span> <span v-if="activeTab == 'Institutions'">
                      <ArgonBadge variant="gradient" color="success" style="color:white"># {{
                        InstitutionStore.InstitutionArray.length }}</ArgonBadge>
                    </span>
                  </a>
                </li>
                <li class="nav-item" :class="{ 'show': activeTab === 'Country' }">
                  <a class="nav-link" @click="setActiveTab('Country')">
                    <span>Country</span> <span v-if="activeTab == 'Country'">
                      <ArgonBadge variant="gradient" color="success" style="color:white"># {{
                        CountryStore.CountryArray.length }}</ArgonBadge>
                    </span>

                  </a>
                </li>
              </ul>

              <div style="margin:10px 0px 10px 10px"></div>
              <div v-if="activeTab === 'Scholars'">
                <!-- 在此处渲染分组后的结果 -->
                <div v-for="(names, letter) in sortedGroupedNames" :key="letter">
                  <ArgonBadge variant="gradient" color="primary" style="font-size: 18px;">{{ letter }}</ArgonBadge>
                  <div v-for="(chunk, index) in chunkedNames(names, 4)" :key="index" class="row"
                    style="margin:10px 0px 10px 10px">
                    <div class="col" v-for="name in chunk" :key="name" @click="searchBy(name)">
                      <span>{{ name }}</span>
                    </div>
                  </div>
                </div>
              </div>


            </div>


            <div v-if="activeTab === 'Institutions'" class="card-body px-0 pt-0 pb-2">

              <!-- 在此处渲染分组后的结果 -->

              <div>
                <ArgonBadge style="margin:0px 0px 0px 15px;font-size: 18px;" variant="gradient" color="primary">Academia
                </ArgonBadge>
                <div v-for="(insObj, index) in chunkedNames(InstitutionStore.Academia, 2)" :key="index" class="row"
                  style="margin:10px 0px 10px 10px">
                  <div class="col" v-for="ins in insObj" :key="name" @click="searchBy(ins.institution)">
                    <span>{{ ins.institution }}</span>
                  </div>
                </div>
              </div>

              <div style="margin:10px 0px 10px 10px"></div>

              <div>
                <ArgonBadge style="margin:0px 0px 0px 15px;font-size: 18px;" variant="gradient" color="primary">Industry
                </ArgonBadge>
                <div v-for="(insObj, index) in chunkedNames(InstitutionStore.Industry, 2)" :key="index" class="row"
                  style="margin:10px 0px 10px 10px">
                  <div class="col" v-for="ins in insObj" :key="name" @click="searchBy(ins.institution)">
                    <span>{{ ins.institution }}</span>
                  </div>
                </div>
              </div>
            </div>


            <div v-if="activeTab === 'Country'" class="card-body px-0 pt-0 pb-2">

              <!-- 在此处渲染分组后的结果 -->
              <div>
                <div v-for="(cous, index) in chunkedNames(CountryStore.CountryArray, 5)" :key="index" class="row"
                  style="margin:10px 0px 10px 10px">
                  <div class="col" v-for="cou in cous" :key="name" @click="searchBy(cou)">
                    <span>{{ cou }}</span>
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

<script  setup>
import SideNav from './components/SideNav.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import Foot from '../CustomizedComponents/Foot.vue';
import ArgonBadge from '../CustomizedComponents/ArgonBadge.vue';
import ArgonButton from '../CustomizedComponents/ArgonButton.vue';
import { onMounted } from 'vue';
import { ref, computed, reactive, watch } from 'vue';
import { usePaperInfoStore } from '../store/RepositoryStore/paperinfoStore'
import { request } from '../request';
import { useRouter } from 'vue-router';
import pinia from '../store/store';
import { useAuthorStore } from '../store/RepositoryStore/authorStore'
import { listAllScholars, listAllInstitutions, listAllCountry } from './commonFunction';
import { useModuleStore } from '../store/module';
import { useInstitutionStore } from '../store/RepositoryStore/institutionStore'
import { useCurrentAuthorStore } from '../store/RepositoryStore/currentAuthorStore'
import { ElNotification } from 'element-plus'
import { useCountryStore } from '../store/RepositoryStore/CountryStore'
import { ElLoading } from 'element-plus'
import { usePaginationStore } from '../store/RepositoryStore/paginationStore'

const PaginationStore = usePaginationStore(pinia)
const currentAuthorStore = useCurrentAuthorStore(pinia)
const moduleStore = useModuleStore(pinia)
const AuthorStore = useAuthorStore(pinia)
const InstitutionStore = useInstitutionStore(pinia)
const router = useRouter();
const PaperInfoStore = usePaperInfoStore(pinia)
const CountryStore = useCountryStore(pinia)
const activeTab = ref('Scholars'); // Set the default active tab

const setActiveTab = (tabName) => {
  activeTab.value = tabName;
  moduleStore.CurrentModuleDetails = ''
};

// Watch for changes in activeTab
watch(activeTab, (newValue, oldValue) => {
  moduleStore.CurrentModule = newValue
});




const searchBy = async (value) => {

  console.log("当前选中的是", activeTab.value)
  console.log("待搜索的值是", value)

  let loadingInstance = ElLoading.service({ fullscreen: true })
  moduleStore.CurrentModule=activeTab.value
  moduleStore.CurrentModuleDetails = value

  PaginationStore.pagenum = 1
  PaginationStore.pagesize = 25
  PaginationStore.searchkeywords = value

  PaperInfoStore.paginationOffset = 0
  PaperInfoStore.paperinfos.length = 0
  PaperInfoStore.searchKeyWords = value

  if (activeTab.value == 'Scholars') {
    try {

      PaginationStore.column = 'author'
      const searchByAuthorRes = await request({
        url: 'repo/list/searchBy',
        method: 'POST',
        data: {
          pagesize: PaginationStore.pagesize,
          searchkeywords: PaginationStore.searchkeywords,
          column: PaginationStore.column,
          pagenum: PaginationStore.pagenum,
        }
      })
      // console.log("searchByAuthorRes", searchByAuthorRes)
      PaperInfoStore.paperinfos.push(...searchByAuthorRes.res.records)
      PaperInfoStore.total = searchByAuthorRes.res.total
      PaginationStore.total = searchByAuthorRes.res.total


      const getAuthorInfoRes = await request({
        url: 'repo/author/listAuthorByname',
        method: 'POST',
        data: {
          searchkeywords: PaginationStore.searchkeywords,
        }
      })
      currentAuthorStore.CurrentAuthor.category = getAuthorInfoRes.authors.category
      currentAuthorStore.CurrentAuthor.country = getAuthorInfoRes.authors.country
      currentAuthorStore.CurrentAuthor.email = getAuthorInfoRes.authors.email
      currentAuthorStore.CurrentAuthor.homepage = getAuthorInfoRes.authors.homepage
      currentAuthorStore.CurrentAuthor.institution = getAuthorInfoRes.authors.institution
      currentAuthorStore.CurrentAuthor.name = getAuthorInfoRes.authors.name
      loadingInstance.close()
      router.push({
        path: '/repository/papers',
        query: {
          module: 'Scholars',
        }
      })
    } catch (error) {

    }
  }


  if (activeTab.value == 'Institutions') {

    try {

      PaginationStore.column = 'institution'
      const searchByInstitutionRes = await request({
        url: 'repo/list/searchBy',
        method: 'POST',
        data: {
          pagesize: PaginationStore.pagesize,
          searchkeywords: PaginationStore.searchkeywords,
          column: PaginationStore.column,
          pagenum: PaginationStore.pagenum,
        }
      })
      // console.log("searchByInstitutionRes", searchByInstitutionRes)
      PaperInfoStore.paperinfos.push(...searchByInstitutionRes.res.records)
      PaperInfoStore.total = searchByInstitutionRes.res.total
      PaginationStore.total = searchByInstitutionRes.res.total


      loadingInstance.close()
      router.push({
        path: '/repository/papers',
        query: {
          module: 'Institutions',
        }
      })
    } catch (error) {

    }
  }

  if (activeTab.value == 'Country') {
    try {

      PaginationStore.column = 'country'
      const searchByCountryRes = await request({
        url: 'repo/list/searchByCountry',
        method: 'POST',
        data: {
          pagesize: PaginationStore.pagesize,
          searchkeywords: PaginationStore.searchkeywords,
          column: PaginationStore.column,
          pagenum: PaginationStore.pagenum,
        }
      })
      // console.log("searchByCountryRes", searchByCountryRes)
      PaperInfoStore.paperinfos.push(...searchByCountryRes.res)
      PaperInfoStore.total = searchByCountryRes.total
      PaginationStore.total = searchByCountryRes.total


      loadingInstance.close()
      router.push({
        path: '/repository/papers',
        query: {
          module: 'Country',
        }
      })
    } catch (error) {

    }

  }




}

// const searchByInstitution = async (institution) => {
//   try {
//     moduleStore.CurrentModuleDetails = institution
//     searchObj.searchkeywords = institution;
//     PaperInfoStore.paperinfos.length = 0
//     PaperInfoStore.searchKeyWords = institution

//     const searchByInstitutionRes = await request({
//       url: 'repo/list/searchByInstitution',
//       method: 'POST',
//       data: searchObj
//     })
//     PaperInfoStore.paperinfos.push(...searchByInstitutionRes.res)
//     PaperInfoStore.total = searchByInstitutionRes.res.length



//     router.push({
//       path: '/repository/papers',
//       query: {
//         paginationActive: '关闭',
//         module: 'Institution',
//       }
//     })




//   } catch (error) {

//   }
// }

const searchByCountry = async (country) => {
  try {
    moduleStore.CurrentModuleDetails = country
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.searchKeyWords = country

    const searchByCountryRes = await request({
      url: 'repo/list/searchByCountry',
      method: 'POST',
      data: {
        searchkeywords: country
      }
    })
    // console.log("searchByCountryRes", searchByCountryRes)
    PaperInfoStore.paperinfos.push(...searchByCountryRes.res)
    PaperInfoStore.total = searchByCountryRes.res.length



    router.push({
      path: '/repository/papers',
      query: {
        paginationActive: '关闭',
        module: 'Country',
      }
    })




  } catch (error) {

  }
}

const groupNamesByLastWord = (names) => {
  const groupedNames = {};

  names.forEach(name => {
    const words = name.split(' ');
    const lastWord = words[words.length - 1];
    const firstLetter = lastWord.charAt(0).toUpperCase();

    if (!groupedNames[firstLetter]) {
      groupedNames[firstLetter] = [];
    }

    groupedNames[firstLetter].push(name);
  });

  return groupedNames;
};

const chunkedNames = (names, chunkSize) => {
  const result = [];
  const maxNameLength = Math.max(...names.map(name => name.length));

  for (let i = 0; i < names.length; i += chunkSize) {
    const chunk = names.slice(i, i + chunkSize);
    // Fill the last chunk with empty strings to make it the same length
    while (chunk.length < chunkSize) {
      chunk.push(' '.repeat(maxNameLength));
    }
    result.push(chunk);
  }

  return result;
};

const sortedGroupedNames = computed(() => {
  const sortedGroupedNames = {};
  Object.keys(groupNamesByLastWord(AuthorStore.authorsArray)).sort().forEach(letter => {
    sortedGroupedNames[letter] = groupNamesByLastWord(AuthorStore.authorsArray)[letter];
  });
  return sortedGroupedNames;
});







// // 使用 watch 监听 InstitutionArray 的变化
// watch(() => InstitutionStore.InstitutionArray, () => {
//   groupInstitutionByCategory();
// });

onMounted(async () => {
  let loadingInstance = ElLoading.service({ fullscreen: true })

  if (moduleStore.CurrentModule == '') {
    moduleStore.CurrentModule = activeTab.value

  }
  else {
    activeTab.value = moduleStore.CurrentModule
  }

  // 获取所有scholars
  await listAllScholars()
  // 获取所有Institution
  await listAllInstitutions()

  // 获取所有Country
  await listAllCountry()

  loadingInstance.close()


})
</script>



<style scoped>
.nav-item {
  cursor: pointer;
}

/* Inactive tab styles */
.nav-tabs .nav-item.show h3 {
  background-color: #ffffff;
  /* Set the background color for inactive tabs */
  color: #5e72e4;
  /* Set the text color for inactive tabs */

}

/* Active tab styles */
.nav-tabs .nav-item h3 {
  background-color: #ffffff;
  /* Set the background color for active tab */
  color: #8392ab;
  /* Set the text color for active tab */


}



.col span {
  transition: font-size 0.3s ease;
  /* 添加过渡效果 */
}

.col:hover span {
  font-size: calc(15px + 1px);
  /* 这里使用 calc 计算新的 font-size，16px 为默认值 */
  cursor: pointer;
  color: rgb(94, 114, 228);
  text-decoration: underline;
}
</style>
