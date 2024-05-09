<template>
  <div class="card">
    <div class="card-header pb-0">
      <h5 v-if="props.index">Model #{{ props.index + 1 }}</h5>
      <h5 v-if="route.query.index">Model #{{ parseInt(route.query.index) + 1 }}</h5>
      <h6>Model Name: {{ props.model.modelname }}</h6>
      <h7>Model Description: {{ props.model.modeldescriptions }}</h7>
      <h7>{{ props.model.NumOfTestSuites }} testsuties found.</h7>


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



    <div v-if="props.testSuites.length != 0" class="card-body px-0 pt-0 pb-2">
      <h4 class="text-center">TestSuites List of Model <i> {{ props.model.modelname }}</i></h4>
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
                    <el-popconfirm width="300" title="Are you sure to delete this TestSuite?" confirm-button-text="Yes"
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

    <div v-else class="card-body px-0 pt-0 pb-2">
      <el-result icon="warning" title="No TestSuite for this Model"
        sub-title="Please click the following button to create a TestSuite">
      </el-result>
      <div class="col-12 px-4 my-4">
        <ArgonButton full-width color="success" variant="gradient" @click="showdialogNew">
          <span class="ni ni-fat-add ni-lg me-1" />
          Generate New TestSuite
        </ArgonButton>
      </div>

    </div>




    <!-- New test suites -->
    <el-dialog v-model="dialogFormVisibleNew" title="Generate a new testSuite based on current model">
      <el-form :model="dialogformNewTestSuites" label-position="left" label-width="170px">

        <el-form-item label="TestSuite Name: ">
          <el-input v-model="dialogformNewTestSuites.testsuitesname" clearable
            placeholder="Please input the name of the new TestSuite " />
        </el-form-item>

        <el-form-item label="TestSutie Description: ">
          <el-input v-model="dialogformNewTestSuites.testsuitesdescriptions" clearable
            placeholder="Please input the Description for the new TestSuite " />
        </el-form-item>

        <el-form-item label="Strength: ">
          <el-select clearable style="margin: 0 0 0 0;padding: 0;width: 250px" v-model="strength" class="m-2"
            placeholder="Select a covering strength">
            <el-option v-for="item in StrengthOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="Generation Tool: ">
          <el-select v-model="AlgorithmChosed" class="m-2" placeholder="Select an Tool for generating"
            @change="handleAlgorithmChange">
            <el-option v-for="item in AlgorithmOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>

        <!-- 在此显示 对应 AlgorithmChosed 的 tool.description -->

        <el-form-item label="Tool Description: ">
          <el-input readonly v-model="AlgorithmDescription" :autosize="{ minRows: 2, maxRows: 12 }" type="textarea"
            placeholder="The Description of Tool Choosen Will Be Displayed Here" />
        </el-form-item>

      </el-form>

      <div v-if="inAPIcall" class="row" style="margin: 0px 0px 0px 30px;">
        <div class="spinner-border  text-success col-4" role="status">
        </div>
        <div class="col-8" style="margin: 5px 0px 0px 0px;">
          <h6>CitHub is calling the API of {{ AlgorithmChosed }}, Please wait a while...
          </h6>
        </div>
      </div>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleNew = false">Cancel</el-button>
          <el-button type="primary" @click="confirmGenerateNewTestSuites">
            Confirm
          </el-button>
        </span>
      </template>
    </el-dialog>


  </div>
</template>

<script setup>

import { onMounted, reactive, ref, computed, defineProps, defineEmits } from 'vue';
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
import { listAllTestSuitesByModelID, CitHubModel } from '../commonFunction.js';

const emits = defineEmits(['scrollToTestSuiteInfo']);
const router = useRouter();
const route = useRoute()
const props = defineProps({
  model: Object,
  testSuites: Object,
  index: Number,
  AlgorithmOptions: Object
})
// const testSuitesStore = useTestSuitesStore(pinia)
// const currentModel = useCurrentModel(pinia)
const testSuitesStore = useTestSuitesStore(pinia)
const currentTestSuite = useCurrentTestSuitesStore(pinia)
const modelStore = useModelsStore(pinia)
const currentModel = useCurrentModel(pinia)

const inAPIcall = ref(false)

const strength = ref()
const StrengthOptions = [
  {
    value: '2',
    label: '2',
  },
  {
    value: '3',
    label: '3',
  },
  {
    value: '4',
    label: '4',
  },
  {
    value: '5',
    label: '5',
  },
  {
    value: '6',
    label: '6',
  },
]

const AlgorithmChosed = ref('');
const AlgorithmOptions = reactive([]);
const AlgorithmDescription = ref('');

const listAllGenerationAlgorithm = () => {
  for (const tool of toolsInfo.RECORDS) {
    if (tool.type == "Generation" && !tool.title.includes("GUI")) {
      AlgorithmOptions.push({ "value": tool.title, "label": tool.title, "url": tool.url, "description": tool.description });
    }
  }
};

const handleAlgorithmChange = () => {
  const selectedAlgorithm = AlgorithmOptions.find(option => option.value === AlgorithmChosed.value);
  if (selectedAlgorithm) {
    AlgorithmDescription.value = selectedAlgorithm.description;
  } else {
    AlgorithmDescription.value = '';
  }
};

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
  dialogformNewTestSuites.modelid = route.query.modelid
}



const confirmGenerateNewTestSuites = async () => {
  inAPIcall.value = true
  // 获取当前时刻的Date对象
  const currentDate = new Date();
  dialogformNewTestSuites.lastupdatedtime = currentDate
  dialogformNewTestSuites.createdtime = currentDate
  // console.log("currentModel.currentModel", currentModel.currentModel)

  if (currentModel.currentModel.modelname == "") {
    ElNotification({
      title: 'Generate TestSuites fail!',
      message: 'Model Name can not be empty!',
      type: 'error',
    })
  }

  else {
    // 发送请求，对 model 得到 test suites
    for (const tool of AlgorithmOptions) {
      if (tool.value == AlgorithmChosed.value) {
        try {
          // 构造CitHub Model，调用工具
          currentModel.currentModel.CitHubModel = CitHubModel(currentModel.currentModel, parseInt(strength.value))
          // console.log("generation cithub model", currentModel.currentModel.CitHubModel)

          const TestSuitesRes = await request({
            // 这里记得改回去，在校外无法用校内服务器
            url: tool.url,
            // url: 'http://localhost:8300',
            method: 'POST',
            // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
            headers: {
              'Content-Type': 'text/plain'
            },
            // 转为Json
            data: JSON.stringify(currentModel.currentModel.CitHubModel).replace(/"/g, '')
          })

          // console.log("TestSuitesRes", TestSuitesRes)
          const newTestSuitesRes = await request({
            url: "/tools/New",
            method: "POST",
            data:
            {
              column: 'testsuite',
              testsuitesname: dialogformNewTestSuites.testsuitesname,
              testsuitesdescriptions: dialogformNewTestSuites.testsuitesdescriptions,
              lastupdatedtime: dialogformNewTestSuites.lastupdatedtime,
              createdtime: dialogformNewTestSuites.createdtime,
              modelid: currentModel.currentModel.modelid,

              testsuitescontents: JSON.stringify(TestSuitesRes.testsuite),
              generationtime: TestSuitesRes.time,
              size: TestSuitesRes.size,
              generationtool: AlgorithmChosed.value,
              strength: strength.value
            }
          })
          // console.log("newTestSuitesRes", newTestSuitesRes)

          if (newTestSuitesRes.NewStatus == 'success!') {

            // 实时更新页面数据
            console.log("当前路由", route.name)
            if (route.name == "TestSuite_Details") {
              // 在 TestSuite_Details 页面 更新数据
              await listAllTestSuitesByModelID([currentModel.currentModel])

              // 切换至新生成的 TestSuite Info
              // console.log("testSuitesStore.testSuitesList", testSuitesStore.testSuitesList)
              let CurrentModelTestSuitesLength=testSuitesStore.testSuitesList[0].testSuites.length
              EnterTestSuiteDetails(testSuitesStore.testSuitesList[0].testSuites[CurrentModelTestSuitesLength-1])
            }
            if (route.name == "TestSuites_Home") {
              // 在 TestSuites_Home 页面 更新数据
              await listAllTestSuitesByModelID(modelStore.modelsList)

            }


            ElNotification({
              title: 'Generate Success!',
              type: 'success',
              message: 'Strength ' + currentTestSuite.currentTestSuites.strength + ' test suite' + ' by ' + AlgorithmChosed.value + ' generated success! Please check the results.',

            })
            inAPIcall.value = false
            dialogFormVisibleNew.value = false



          }
          else {
            inAPIcall.value = false
            dialogFormVisibleNew.value = false
            ElNotification({
              title: 'Generate fail!',
              type: 'error',
            })



          }
        }
        catch (err) {
          console.log("err", err)
          inAPIcall.value = false
          dialogFormVisibleNew.value = false
          ElNotification({
            title: 'Generate fail!',
            type: 'error',
          })



        }
      }
    }
  }


}


const confirmDelete = async (testSuites) => {

  // console.log("删除testSuites", testSuites)
  let loadingInstance = ElLoading.service({ fullscreen: true })


  try {
    const DeleteTestSuiteRes = await request({
      url: '/tools/Delete',
      method: 'POST',
      data: {
        column: "testsuite",
        testsuitesid: testSuites.testsuitesid
      }
    })

    if (DeleteTestSuiteRes.DeleteStatus == 'success!') {
      // console.log("当前路由", route.name)
      if (route.name == "TestSuite_Details") {
        // 在 TestSuite_Details 页面 更新数据
        await listAllTestSuitesByModelID([currentModel.currentModel])
        // 清空TestSuite Info 区域的信息
        currentTestSuite.currentTestSuites={}
      }
      if (route.name == "TestSuites_Home") {
        // 在 TestSuites_Home 页面 更新数据
        await listAllTestSuitesByModelID(modelStore.modelsList)

      }
      ElNotification({
        title: 'Delete Success!',
        message: 'please check the results',
        type: 'success',
      })
      loadingInstance.close()


    }

    else {
      ElNotification({
        title: 'Delete Failed!',
        message: 'please check the results',
        type: 'error',
      })
      loadingInstance.close()
    }
  } catch (error) {
    console.log("error", error)
    ElNotification({
      title: 'Delete Failed!',
      message: 'please check the results',
      type: 'error',
    })
    loadingInstance.close()

  }

}


const getActionButtonText = (routeName) => {
  switch (routeName) {
    case 'TestSuites_Home':
      return 'Enter';
    case 'TestSuite_Details':

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

  if (route.name == 'TestSuite_Details') {
    // 发送信号给父组件，通知其滚动屏幕
    emits('scrollToTestSuiteInfo');
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


onMounted(async () => {

  await listAllGenerationAlgorithm()

})
</script>


<style scoped></style>