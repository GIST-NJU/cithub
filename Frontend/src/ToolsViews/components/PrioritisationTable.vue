<template>
  <div class="card">
    <div class="card-header pb-0">
      <h5>{{ prioritisationStore.PrioritisationList.length }} Prioritisations found.</h5>
    </div>
    <div style="width:100%;margin-bottom: 5px;text-align: center;">
      <ArgonButton style="float: right;margin-right: 3%;" color="info" variant="gradient" @click="showdialogNew">
        <span class="ni ni-fat-add ni-lg me-1" />
        Generate New Prioritisation
      </ArgonButton>
    </div>
    <!-- New Prioritisation -->
    <el-dialog v-model="dialogFormVisibleNew" title="Convert TestSuite into:">
      <el-form :model="dialogformNewPrioritisation" label-position="right" label-width="160px">
        <el-form-item label="Prioritisation Name:">
          <el-input v-model="dialogformNewPrioritisation.prioritisationname" />
        </el-form-item>
        <el-form-item label="Prioritisation Desc:">
          <el-input autosize type="textarea" v-model="dialogformNewPrioritisation.prioritisationdescriptions" />
        </el-form-item>
        <el-form-item label="Algorithm:">
          <el-select v-model="AlgorithmChosed" class="m-2"
            placeholder="Select a test plan for converting test suite into." clearable>
            <el-option v-for="item in AlgorithmOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
      </el-form>

      <div v-if="AlgorithmChosed == 'SortArray'">
        <el-form-item label="Weight">
          <el-input v-model="Weight" type="textarea" :autosize="{ minRows: 4 }"
            placeholder="Please input the Weight of parameters and using `,`  to split each Weight, eg: 3,1,2 for three parameters.">
          </el-input>
        </el-form-item>
      </div>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleNew = false">Cancel</el-button>
          <el-button type="primary" @click="confirmNewPrioritisation">
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
              <th class=" text-secondary text-xxs font-weight-bolder opacity-7">Prioritisation</th>
              <th class=" text-secondary text-xxs font-weight-bolder opacity-7 ps-2">
                <ArgonBadge>Algorithm</ArgonBadge>
              </th>
              <th class="text-center  text-secondary text-xxs font-weight-bolder opacity-7">
                <ArgonBadge color="primary">Time</ArgonBadge>
              </th>
              <th class="text-center  text-secondary text-xxs font-weight-bolder opacity-7">
                created time
              </th>
              <th class="text-center  text-secondary text-xxs font-weight-bolder opacity-7">
                last updated time
              </th>
              <th class="text-center  text-secondary text-xxs font-weight-bolder opacity-7">
                Operation
              </th>

            </tr>
          </thead>
          <tbody>

            <tr v-for="(prioritisation, index) in prioritisationStore.PrioritisationList" :key="index">

              <td>
                <div class="d-flex px-2 py-1">
                  <div class="d-flex flex-column justify-content-center  ">
                    <h6 class="mb-0 text-sm">{{ prioritisation.prioritisationname }}</h6>
                    <p class="text-xs text-secondary mb-0">{{ prioritisation.prioritisationdescriptions }}</p>
                  </div>
                </div>
              </td>
              <td class="align-middle text-center ">
                <p class="text-xs font-weight-bold mb-0 ">{{ prioritisation.algorithm }}</p>
              </td>
              <td class="align-middle text-center ">
                <p class="text-xs font-weight-bold mb-0">{{ prioritisation.time }}</p>
              </td>


              <td class="align-middle text-center ">
                <p class="text-xs font-weight-bold mb-0">{{ prioritisation.createdtimeFortmat }}</p>
              </td>

              <td class="align-middle text-center">
                <p class="text-xs font-weight-bold mb-0">{{ prioritisation.lastupdatedtimeFortmat }}</p>
              </td>


              <td class="align-middle text-center">
                <a class="btn btn-link text-dark px-3 mb-0" @click="showContentFlag[index] = !showContentFlag[index]">
                  <i class="fas fa-book-open text-primary me-2" aria-hidden="true"></i>Details
                </a>

                <el-popconfirm title="Are you sure to delete this project?" confirm-button-text="Yes"
                  @confirm="confirmDelete(prioritisation)">
                  <template #reference>
                    <a class="btn btn-link text-danger text-gradient px-3 mb-0">
                      <i class="far fa-trash-alt me-2" aria-hidden="true"></i>Delete
                    </a>
                  </template>
                </el-popconfirm>

              </td>
            </tr>

            <tr v-auto-animate v-for="(prioritisation, index) in prioritisationStore.PrioritisationList"
              :key="`content-${index}`">
              <td v-if="showContentFlag[index]" colspan="6">
                <div>
                  <h6>Prioritisation Result of `{{ prioritisation.prioritisationname }}` </h6>
                  <el-input disabled v-model="prioritisation.ResultPreview" autosize type="textarea"
                    placeholder="Please input" />
                </div>
              </td>
            </tr>


          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>

import { onMounted, reactive, ref, computed, defineProps } from 'vue';
import ArgonBadge from '../../ComponentCommon/ArgonBadge.vue'
import ArgonButton from '../../ComponentCommon/ArgonButton.vue';
import { useModelsStore } from '../../store/modelsStore'
import { routerKey, useRoute, useRouter } from 'vue-router';
import { request } from '../../request';
import { ElNotification } from 'element-plus'
import { useTestSuitesStore } from '../../store/testSuitesStore'
import { usePrioritisationStore } from '../../store/prioritisationStore'
import toolsInfo from "../../ComponentCommon/tools_info.json";
import { useCurrentModel } from '../../store/currentModel'
import { useCurrentTestSuitesStore } from '../../store/currentTestSuite'
import pinia from '../../store/store'
const router = useRouter();
const currentModel = useCurrentModel(pinia)
const testSuitesStore = useTestSuitesStore(pinia)
const prioritisationStore = usePrioritisationStore(pinia)
const currentTestSuite = useCurrentTestSuitesStore(pinia)
const route = useRoute()
const functionHead = ref('')
const functionBody = ref('')
const Weight = ref('')

const listAllPrioritisationByTestSuitesID = async () => {
  try {
    const PrioritisationRes = await request({
      method: "POST",
      url: '/tools/prioritisation/listPrioritisationByTestSuitesID',
      data: {
        testsuitesid: currentTestSuite.currentTestSuites.testsuitesid
      }
    });
    prioritisationStore.PrioritisationList = PrioritisationRes.prioritisations

    for (let i = 0; i < prioritisationStore.PrioritisationList.length; i++) {

      const timestamp_created = prioritisationStore.PrioritisationList[i].createdtime
      const timestamp_lastupdated = prioritisationStore.PrioritisationList[i].lastupdatedtime
      const dateObject_created = new Date(timestamp_created);
      const dateObject_lastupdated = new Date(timestamp_lastupdated);


      // 获取可读的时间字符串
      prioritisationStore.PrioritisationList[i].createdtimeFortmat = dateObject_created.toLocaleString();
      prioritisationStore.PrioritisationList[i].lastupdatedtimeFortmat = dateObject_lastupdated.toLocaleString();


      prioritisationStore.PrioritisationList[i].ResultPreview = JSON.stringify(JSON.parse(prioritisationStore.PrioritisationList[i].prioritisationcontents), null, 6)
    }



  } catch (error) {
    console.error("发生错误", error);
  }

};

const showContentFlag = reactive({});


const dialogFormVisibleNew = ref(false)
const dialogformNewPrioritisation = reactive({
  prioritisationname: '',
  prioritisationdescriptions: '',
  lastupdatedtime: '',
  createdtime: '',
  testsuitesid: route.query.TestSuiteID
})
const showdialogNew = () => {
  dialogFormVisibleNew.value = true
  dialogformNewPrioritisation.modelid = route.query.modelid
}

const confirmNewPrioritisation = async () => {
  // 获取当前时刻的Date对象
  const currentDate = new Date();
  dialogformNewPrioritisation.lastupdatedtime = currentDate
  dialogformNewPrioritisation.createdtime = currentDate
  // 构造发送给Prioritisation的data对象
  let Obj = {}
  let testsuite = JSON.parse(currentTestSuite.currentTestSuites.testsuitescontents).testsuite
  Obj.testsuite = testsuite
  Obj.parameter = JSON.parse(currentModel.currentModel.paramsvalues).length

  let WeightArray = Weight.value.split(',').map(Number)

  Obj.weight = WeightArray
  Obj = JSON.stringify(Obj)
  for (const tool of AlgorithmOptions) {
    if (tool.value == AlgorithmChosed.value) {
      try {
        const PrioritisationRes = await request({
          // 这里记得改回去，在校外无法用校内服务器
          url: tool.url,
          // url: 'http://localhost:8304',
          method: 'POST',
          // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
          headers: {
            'Content-Type': 'text/plain'
          },
          data: Obj
        })
        // console.log("PrioritisationRes",PrioritisationRes)
        const newPrioritisationRes = await request({
          url: "/tools/prioritisation/NewPrioritisation",
          method: "POST",
          data:
          {
            prioritisationname: dialogformNewPrioritisation.prioritisationname,
            prioritisationdescriptions: dialogformNewPrioritisation.prioritisationdescriptions,
            lastupdatedtime: dialogformNewPrioritisation.lastupdatedtime,
            createdtime: dialogformNewPrioritisation.createdtime,
            testsuitesid: currentTestSuite.currentTestSuites.testsuitesid,
            prioritisationdContents: JSON.stringify(PrioritisationRes),
            time: PrioritisationRes.time,
            algorithm: AlgorithmChosed.value
          }
        })

        if (newPrioritisationRes.NewStatus == 'success!') {
          listAllPrioritisationByTestSuitesID()
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
      catch (err) { }
    }



  }
}
  const confirmDelete = (prioritisation) => {
    request({
      url: '/tools/prioritisation/DeleteByPrioritisationID',
      method: 'POST',
      data: {
        Prioritisationid: prioritisation.prioritisationid
      }
    }).then((res) => {
      if (res.DeleteStatus == 'success!') {
        listAllPrioritisationByTestSuitesID()
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


  const AlgorithmChosed = ref('')
  const AlgorithmOptions = reactive([])
  const listAllPrioritisationOptions = () => {
    for (const tool of toolsInfo.RECORDS) {
      if (tool.type == "Prioritisation") {
        AlgorithmOptions.push({ "value": tool.title, "label": tool.title, "url": tool.url })
      }
    }
  }






  onMounted(async () => {

    // 加载当前testSuites的 Prioritisation 信息
    await listAllPrioritisationByTestSuitesID()

    // 加载所有Prioritisation选项
    await listAllPrioritisationOptions()



  })
</script>
