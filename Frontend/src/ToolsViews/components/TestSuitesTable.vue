<template>
  <div class="card">
    <div class="card-header pb-0">
      <h5 v-if="props.index">Model #{{ props.index + 1 }}</h5>
      <h5 v-if="route.query.index">Model #{{ parseInt(route.query.index) + 1 }}</h5>
      <h6>Model Name: {{ props.model.modelname }}</h6>
      <h7>Model Description: {{ props.model.modeldescriptions }}</h7>
      <h7>{{ props.model.NumOfTestSuites }} testsuties found.</h7>
      <h4 class="text-center">TestSuites List of Model <i> {{ props.model.modelname }}</i></h4>

      <!-- <div class="row">

        <div class="col-2">
          <h6>Model Name</h6>
          <argon-input v-model="props.model.modelname" type="text" readonly />
        </div>

        <div class="col-2">
          <h6> Num of params</h6>
          <argon-input v-model="props.model.NumofParams" type="text" readonly />
        </div>

        <div class="col-2">
          <h6> Num of Cons</h6>
          <argon-input v-model="props.model.NumofCons" type="text" readonly />
        </div>

      </div>

      <div class="row">
        <div class="col-12">
          <h6>Model Description</h6>
          <argon-input v-model="props.model.modeldescriptions" type="text" readonly />
        </div>
      </div>

      <div class="row">
        <h6> Operation</h6>

        <div class="col-2">
          <ArgonButton full-width color="primary" variant="gradient" @click="EnterModels(currentModel.currentModel)">
            <span class="fas fa-book-open text-white me-2" />
            Edit Model
          </ArgonButton>
        </div>

      </div> -->


    </div>





    <!-- New test suites -->
    <el-dialog v-model="dialogFormVisibleNew" title="New Test Suite">
      <el-form :model="dialogformNewTestSuites" label-position="right" label-width="170px">

        <el-form-item label="Test Suite Name:">
          <el-input v-model="dialogformNewTestSuites.testsuitesname" />
        </el-form-item>
        <el-form-item label="Test Suite Description:">
          <el-input autosize type="textarea" v-model="dialogformNewTestSuites.testsuitesdescriptions" />
        </el-form-item>
        <el-form-item label="Algorithm: ">
          <el-select v-model="AlgorithmChosed" class="m-2" placeholder="Select an Algorithm for generating">
            <el-option v-for="item in AlgorithmOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleNew = false">Cancel</el-button>
          <el-button type="primary" @click="confirmGenerateNewTestSuites">
            Confirm
          </el-button>
        </span>
      </template>
    </el-dialog>
    <div class="card-body px-0 pt-0 pb-2">
      <div class="table-responsive p-0">
        <table class="table align-items-center mb-0">
          <thead>
            <tr>
              <th class="text-secondary  font-weight-bolder opacity-7">Test Suites</th>
              <th class=" text-center  text-secondary  font-weight-bolder opacity-7 ps-2">
                <ArgonBadge>Algorithm</ArgonBadge>
              </th>

              <th class=" text-center  text-secondary  font-weight-bolder opacity-7 ps-2">
                <ArgonBadge color="danger">Strength</ArgonBadge>
              </th>

              <th class="text-center  text-secondary  font-weight-bolder opacity-7">
                <ArgonBadge color="info">Size</ArgonBadge>
              </th>
              <th class="text-center  text-secondary  font-weight-bolder opacity-7">
                <ArgonBadge color="primary">Time</ArgonBadge>
              </th>
              <th class="text-center  text-secondary  font-weight-bolder opacity-7">
                created time
              </th>
              <th class="text-center  text-secondary  font-weight-bolder opacity-7">
                last updated time
              </th>
              <th class="text-center  text-secondary  font-weight-bolder opacity-7">
                Operation
              </th>

            </tr>
          </thead>
          <tbody>
            <tr class="cardTR" v-for="(testSuite, index) in props.testSuites">
              <td>
                <div class="d-flex px-3 py-1">
                  <div class="d-flex flex-column justify-content-center">
                    <h6 class=" ">{{ testSuite.testsuitesname }}</h6>
                    <p class="text-xs text-secondary mb-0">{{ testSuite.testsuitesdescriptions }}</p>
                  </div>
                </div>
              </td>
              <td>
                <p class=" text-center font-weight-bold mb-0">{{ testSuite.generationtool }}</p>
              </td>

              <td>
                <p class=" text-center font-weight-bold mb-0">{{ testSuite.strength }}</p>
              </td>

              <td class="align-middle text-center text-sm">
                <p class=" font-weight-bold mb-0">{{ testSuite.size }}</p>
              </td>
              <td class="align-middle text-center text-sm">
                <p class=" font-weight-bold mb-0">{{ testSuite.generationtime }}</p>
              </td>
              <td class="align-middle text-center text-sm">
                <p class=" font-weight-bold mb-0">{{ testSuite.createdtimeFormat }}</p>
              </td>
              <td class="align-middle text-center">
                <p class=" font-weight-bold mb-0">{{ testSuite.lastupdatedtimeFormat }}</p>
              </td>
              <td class="align-middle text-center">

                <div class="d-flex justify-content-center">

                  <div class="mx-2">
                    <argon-button @click="EnterTestSuiteDetails(testSuite, index)" color="success" variant="gradient">
                      <i class="fa fa-thumbtack me-2" aria-hidden="true"></i>
                      {{ getActionButtonText(route.name) }}
                    </argon-button>

                  </div>
                  <div>
                    <el-popconfirm title="Are you sure to delete this project?" confirm-button-text="Yes"
                      @confirm="confirmDelete(testSuite)">
                      <template #reference>
                        <argon-button color="danger" variant="gradient"><i class="far fa-trash-alt me-2"
                            aria-hidden="true"></i>Delete</argon-button>
                      </template>
                    </el-popconfirm>
                  </div>
                </div>

              </td>
            </tr>

          </tbody>
        </table>
        <div class="col-12 px-4 my-4">
          <ArgonButton full-width color="success" variant="gradient" @click="showdialogNew">
            <span class="ni ni-fat-add ni-lg me-1" />
            Generate New TestSuite
          </ArgonButton>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

import { onMounted, reactive, ref, computed, defineProps } from 'vue';
import ArgonBadge from '../../CustomizedComponents/ArgonBadge.vue'
import ArgonButton from '../../CustomizedComponents/ArgonButton.vue';
import ArgonInput from '../../CustomizedComponents/ArgonInput.vue';
import { useModelsStore } from '../../store/ToolsStore/modelsStore'
import { routerKey, useRoute, useRouter } from 'vue-router';
import { request } from '../../request';
import { ElNotification } from 'element-plus'
import { useTestSuitesStore } from '../../store/ToolsStore/testSuitesStore'
import toolsInfo from "../../CustomizedComponents/tools_info.json";
import { useCurrentTestSuitesStore } from '../../store/ToolsStore/currentTestSuite'
import { useCurrentModel } from '../../store/ToolsStore/currentModel'
import pinia from '../../store/store'
import { ElLoading } from 'element-plus'

const router = useRouter();
const route = useRoute()
const props = defineProps({
  model: Object,
  testSuites: Object,
  index: Number
})
const testSuitesStore = useTestSuitesStore(pinia)
const currentModel = useCurrentModel(pinia)
const currentTestSuite = useCurrentTestSuitesStore(pinia)

const dialogFormVisibleNew = ref(false)
const dialogformNewTestSuites = reactive({
  testsuitesname: '',
  testsuitesdescriptions: '',
  lastupdatedtime: '',
  createdtime: '',
  modelid: route.query.modelid
})
const showdialogNew = () => {
  dialogFormVisibleNew.value = true
}

const getActionButtonText = (routeName) => {
  switch (routeName) {
    case 'TestSuites_Home':
      return 'Enter';
    case 'TestSuiteDetails':

      return 'Choose';
    // 更多情况
    default:
      return 'Enter';
  }
};

const EnterTestSuiteDetails = (testsuite, index) => {
  // console.log("testsuite",testsuite)
  let loadingInstance = ElLoading.service({ fullscreen: true })

  // 在进入 TestSuite Detail时 给 currentTestSuite.currentTestSuites 赋值
  for (let key in testsuite) {
    if (testsuite.hasOwnProperty(key)) {
      currentTestSuite.currentTestSuites[key] = testsuite[key];
    }
  }
// console.log(" EnterTestSuiteDetails currentTestSuite.currentTestSuites",currentTestSuite.currentTestSuites)

  if (route.name == 'TestSuiteDetails') {
    ElNotification({
      title: 'Switch Success!',
      message: 'Now working on TestSuite <br>' + currentTestSuite.currentTestSuites.testsuitesname,
      type: 'success',
      dangerouslyUseHTMLString: true
    })

  }

  // 在 TestSuites_Home 才 切换页面，在 Details 页面 只切换数据 ，不切换页面
  if (route.name == 'TestSuites_Home') {
    router.push(
      {
        path: '/tools/TestSuiteDetails',
        query: {
          testsuitesid: currentTestSuite.currentTestSuites.testsuitesid,
          modelid: currentTestSuite.currentTestSuites.modelid,
          index: index
        }
      }
    )
  }

  // console.log("route", route.name)
  loadingInstance.close()


}

const confirmGenerateNewTestSuites = async () => {
  // 获取当前时刻的Date对象
  const currentDate = new Date();
  dialogformNewTestSuites.lastupdatedtime = currentDate
  dialogformNewTestSuites.createdtime = currentDate

  if (props.model.modelname == "") {
    ElNotification({
      title: 'New TestSuites fail!',
      message: 'Model Name can not be empty!',
      type: 'error',
    })
  }
  else {
    // 发送请求，对 model 得到 test suites
    for (const tool of AlgorithmOptions) {
      if (tool.value == AlgorithmChosed.value) {
        try {
          const TestSuitesRes = await request({
            // 这里记得改回去，在校外无法用校内服务器
            url: tool.url,
            // url: 'http://localhost:8300',
            method: 'POST',
            // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
            headers: {
              'Content-Type': 'text/plain'
            },
            data: props.model.modelCithub
          })


          const newTestSuitesRes = await request({
            url: "/tools/testSuites/NewTestSuites",
            method: "POST",
            data:
            {
              testsuitesname: dialogformNewTestSuites.testsuitesname,
              testsuitesdescriptions: dialogformNewTestSuites.testsuitesdescriptions,
              lastupdatedtime: dialogformNewTestSuites.lastupdatedtime,
              createdtime: dialogformNewTestSuites.createdtime,
              modelid: dialogformNewTestSuites.modelid,
              testsuitescontents: JSON.stringify(TestSuitesRes),
              time: TestSuitesRes.time,
              size: TestSuitesRes.size,
              algorithm: AlgorithmChosed.value
            }
          })
          // console.log("newTestSuitesRes", newTestSuitesRes)

          if (newTestSuitesRes.NewStatus == 'success!') {

            listAllTestSuitesByModelID()
            ElNotification({
              title: 'Save Success!',
              type: 'success',
            })
            dialogFormVisibleNew.value = false
          }
          else {
            ElNotification({
              title: 'Save fail!',
              type: 'error',
            })
            dialogFormVisibleNew.value = false

          }
        }
        catch (err) {
          console.log("err", err)
          ElNotification({
            title: 'Save fail!',
            type: 'error',
          })
          dialogFormVisibleNew.value = false


        }
      }
    }
  }


}
const confirmDelete = (testSuites) => {
  // console.log("testSuites", testSuites)
  request({
    url: '/tools/testSuites/DeleteByTestSuitesID',
    method: 'POST',
    data: {
      testsuitesid: testSuites.testsuitesid
    }
  }).then((res) => {
    if (res.DeleteStatus == 'success!') {
      listAllTestSuitesByModelID()
      // 实时更新页面数据
      ElNotification({
        title: 'Delete Success!',
        message: 'please check the results',
        type: 'success',
      })

    }
  }).catch((error) => {
    // console.log(error)
    ElNotification({
      title: 'Delete Error!',
      message: 'please check the results',
      type: 'error',
    })
  })
}


onMounted(async () => {

  // 让选中的行高亮
  try {
    // console.log("TestSuiteTable的数据加载 testsuites", props.testSuites)
    // console.log("props.model", props.model)

  } catch (error) {
    console.log("error in TestSuitesTable", error)

  }

})
</script>


<style scoped>
.highlighted-row {
  border: 2px solid red;
  /* 定义高亮样式 */
}
</style>