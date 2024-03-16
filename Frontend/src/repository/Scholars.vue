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
                  <a class="nav-link" @click="setActiveTab('Scholars')" >
                    <span>Scholars</span> <span v-if="activeTab == 'Scholars'">
                      <ArgonBadge variant="gradient" color="success" style="color:white"># {{
                        AuthorStore.authorsArray.length }}</ArgonBadge>
                    </span>
                  </a>
                </li>
                <li class="nav-item" :class="{ 'show': activeTab === 'Institutions' }">
                  <a class="nav-link" @click="setActiveTab('Institutions')" >
                    <span>Institutions</span> <span v-if="activeTab == 'Institutions'">
                      <ArgonBadge variant="gradient" color="success" style="color:white"># {{
                        InstitutionStore.InstitutionArray.length }}</ArgonBadge>
                    </span>
                  </a>
                </li>
                <li class="nav-item" :class="{ 'show': activeTab === 'Country' }">
                  <a class="nav-link" @click="setActiveTab('Country')" >
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
                    <div class="col" v-for="name in chunk" :key="name" @click="searchByAuthor(name)">
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
                  <div class="col" v-for="ins in insObj" :key="name" @click="searchByInstitution(ins.institution)">
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
                  <div class="col" v-for="ins in insObj" :key="name" @click="searchByInstitution(ins.institution)">
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
                  <div class="col" v-for="cou in cous" :key="name" @click="searchByCountry(cou)">
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
import Navbar from '../ComponentCommon/Navbar.vue';
import Foot from '../ComponentCommon/Foot.vue';
import ArgonBadge from '../ComponentCommon/ArgonBadge.vue';
import { onMounted } from 'vue';
import { ref, computed, reactive, watch } from 'vue';
import { usePaperInfoStore } from '../store/paperinfoStore'
import { request } from '../request';
import { useRouter } from 'vue-router';
import pinia from '../store/store';
import { useAuthorStore } from '../store/authorStore'
import { listAllScholars, listAllInstitutions, listAllCountry } from './commonFunction';
import { useModuleStore } from '../store/module';
import { useInstitutionStore } from '../store/institutionStore'
import { useCurrentAuthorStore } from '../store/currentAuthorStore'
import { ElNotification } from 'element-plus'
import { useCountryStore } from '../store/CountryStore'
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
};

// Watch for changes in activeTab
watch(activeTab, (newValue, oldValue) => {
  moduleStore.CurrentModule = newValue
});




let searchObj = reactive({
  pagesize: 1000,
  total: '',
  pagercount: 15,
  pagenum: 1,
  searchkeywords: "",
  typerofPapers: PaperInfoStore.TypeofPapers,

})

const searchByAuthor = async (authorname) => {

  try {
    searchObj.searchkeywords = authorname;
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.searchKeyWords = authorname

    const searchByAuthorRes = await request({
      url: 'repo/list/searchByAuthor',
      method: 'POST',
      data: searchObj
    })
    PaperInfoStore.paperinfos.push(...searchByAuthorRes.res.records)
    PaperInfoStore.total = searchByAuthorRes.res.total


    const getAuthorInfoRes = await request({
      url: 'repo/author/listAuthorByname',
      method: 'POST',
      data: searchObj
    })
    currentAuthorStore.CurrentAuthor.category = getAuthorInfoRes.authors.category
    currentAuthorStore.CurrentAuthor.country = getAuthorInfoRes.authors.country
    currentAuthorStore.CurrentAuthor.email = getAuthorInfoRes.authors.email
    currentAuthorStore.CurrentAuthor.homepage = getAuthorInfoRes.authors.homepage
    currentAuthorStore.CurrentAuthor.institution = getAuthorInfoRes.authors.institution
    currentAuthorStore.CurrentAuthor.name = getAuthorInfoRes.authors.name

    ElNotification({
      title: 'Rendring',
      message: 'Please wait for results',
      type: 'success',
    })

    router.push({
      path: '/repository/papers',
      query: {
        paginationActive: '关闭',
        searchType: 'author',
      }
    })
  } catch (error) {

  }


}

const searchByInstitution = async (institution) => {
  try {
    searchObj.searchkeywords = institution;
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.searchKeyWords = institution

    const searchByInstitutionRes = await request({
      url: 'repo/list/searchByInstitution',
      method: 'POST',
      data: searchObj
    })
    PaperInfoStore.paperinfos.push(...searchByInstitutionRes.res)
    PaperInfoStore.total = searchByInstitutionRes.res.length

    ElNotification({
      title: 'Rendring',
      message: 'Please wait for results',
      type: 'success',
    })

    router.push({
      path: '/repository/papers',
      query: {
        paginationActive: '关闭',
        searchType: 'institution',
      }
    })




  } catch (error) {

  }
}

const searchByCountry = async (country) => {
  try {
    searchObj.searchkeywords = country;
    PaperInfoStore.paperinfos.length = 0
    PaperInfoStore.searchKeyWords = country

    const searchByCountryRes = await request({
      url: 'repo/list/searchByCountry',
      method: 'POST',
      data: searchObj
    })
    PaperInfoStore.paperinfos.push(...searchByCountryRes.res)
    PaperInfoStore.total = searchByCountryRes.res.length

    ElNotification({
      title: 'Rendring',
      message: 'Please wait for results',
      type: 'success',
    })

    router.push({
      path: '/repository/papers',
      query: {
        paginationActive: '关闭',
        searchType: 'country',
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

  moduleStore.CurrentModule = activeTab.value
  // 获取所有scholars
  await listAllScholars()
  // 获取所有Institution
  await listAllInstitutions()

  // 获取所有Country
  await listAllCountry()

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
