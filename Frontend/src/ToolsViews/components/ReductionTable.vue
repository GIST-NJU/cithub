<template>
  <div class="card">
    <div class="card-header pb-0">
      <h5>{{ ReductionStore.ReductionList.length }} Reductions found.</h5>
    </div>
    <div style="width:100%;margin-bottom: 5px;text-align: center;">
      <ArgonButton style="float: right;margin-right: 3%;" color="warning" variant="gradient" @click="showdialogNew">
        <span class="ni ni-fat-add ni-lg me-1" />
        Generate New Reduction
      </ArgonButton>
    </div>
    <!-- New Reduction -->
    <el-dialog v-model="dialogFormVisibleNew" title="New Reduction into:">
      <el-form :model="dialogformNewReduction" label-position="right" label-width="140px">
        <el-form-item label="Reduction Name:">
          <el-input v-model="dialogformNewReduction.Reductionname" />
        </el-form-item>
        <el-form-item label="Reduction Desc:">
          <el-input autosize type="textarea" v-model="dialogformNewReduction.Reductiondescriptions" />
        </el-form-item>
        <el-form-item label="Algorithm:">
          <el-select v-model="AlgorithmChosed" class="m-2"
            placeholder="Select a test plan for converting test suite into." clearable>
            <el-option v-for="item in AlgorithmOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
      </el-form>


      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleNew = false">Cancel</el-button>
          <el-button type="primary" @click="confirmNewReduction">
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
              <th class=" text-secondary text-xxs font-weight-bolder opacity-7 ">Reduction</th>
              <th class=" text-secondary text-xxs font-weight-bolder opacity-7 ps-2 text-center">
                <ArgonBadge>Algorithm</ArgonBadge>
              </th>
              <th class="text-center  text-secondary text-xxs font-weight-bolder opacity-7">
                <ArgonBadge color="primary">Time</ArgonBadge>
              </th>
              <th class="text-center  text-secondary text-xxs font-weight-bolder opacity-7">
                <ArgonBadge color="info">Size</ArgonBadge>
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

            <tr v-for="(Reduction, index) in ReductionStore.ReductionList" :key="index">

              <td>
                <div class="d-flex px-2 py-1">
                  <div class="d-flex flex-column justify-content-center  ">
              <td class="align-middle text-center  ">
                <h6 class="mb-0  text-sm">{{ Reduction.reductionname }}</h6>
                <p class="text-xs text-secondary mb-0">{{ Reduction.reductiondescriptions }}</p>
              </td>

      </div>
    </div>
    </td>
    <td class="align-middle text-center  ">
      <p class="text-xs font-weight-bold mb-0 ">{{ Reduction.algorithm }}</p>
    </td>
    <td class="align-middle text-center ">
      <p class="text-xs font-weight-bold mb-0">{{ Reduction.time }}</p>
    </td>
    <td class="align-middle text-center ">
      <p class="text-xs font-weight-bold mb-0">{{ Reduction.size }}</p>
    </td>


    <td class="align-middle text-center ">
      <p class="text-xs font-weight-bold mb-0">{{ Reduction.createdtimeFortmat }}</p>
    </td>

    <td class="align-middle text-center">
      <p class="text-xs font-weight-bold mb-0">{{ Reduction.lastupdatedtimeFortmat }}</p>
    </td>


    <td class="align-middle text-center">
      <a class="btn btn-link text-dark px-3 mb-0" @click="showContentFlag[index] = !showContentFlag[index]">
        <i class="fas fa-book-open text-primary me-2" aria-hidden="true"></i>Details
      </a>

      <el-popconfirm title="Are you sure to delete this project?" confirm-button-text="Yes"
        @confirm="confirmDelete(Reduction)">
        <template #reference>
          <a class="btn btn-link text-danger text-gradient px-3 mb-0">
            <i class="far fa-trash-alt me-2" aria-hidden="true"></i>Delete
          </a>
        </template>
      </el-popconfirm>

    </td>
    </tr>

    <tr v-auto-animate v-for="(Reduction, index) in ReductionStore.ReductionList" :key="`content-${index}`">
      <td v-if="showContentFlag[index]" colspan="6">
        <div>
          <h6>Reduction Result of `{{ Reduction.reductionname }}` </h6>
          <el-input disabled v-model="Reduction.ResultPreview" autosize type="textarea" placeholder="Please input" />
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
import { useReductionStore } from '../../store/reductionStore'
import toolsInfo from "../../ComponentCommon/tools_info.json";
import { useCurrentModel } from '../../store/currentModel'
import { useCurrentTestSuitesStore } from '../../store/currentTestSuite'
const router = useRouter();
const currentModel = useCurrentModel()
const testSuitesStore = useTestSuitesStore()
const ReductionStore = useReductionStore()
const currentTestSuite = useCurrentTestSuitesStore()
const route = useRoute()
const functionHead = ref('')
const functionBody = ref('')
const Weight = ref('')

const listAllReductionByTestSuitesID = async () => {
  try {
    const ReductionRes = await request({
      method: "POST",
      url: '/tools/reduction/listReductionByTestSuitesID',
      data: {
        testsuitesid: currentTestSuite.currentTestSuites.testsuitesid
      }
    });
    ReductionStore.ReductionList = ReductionRes.Reductions

    for (let i = 0; i < ReductionStore.ReductionList.length; i++) {

      const timestamp_created = ReductionStore.ReductionList[i].createdtime
      const timestamp_lastupdated = ReductionStore.ReductionList[i].lastupdatedtime
      const dateObject_created = new Date(timestamp_created);
      const dateObject_lastupdated = new Date(timestamp_lastupdated);


      // 获取可读的时间字符串
      ReductionStore.ReductionList[i].createdtimeFortmat = dateObject_created.toLocaleString();
      ReductionStore.ReductionList[i].lastupdatedtimeFortmat = dateObject_lastupdated.toLocaleString();


      ReductionStore.ReductionList[i].ResultPreview = JSON.stringify(JSON.parse(ReductionStore.ReductionList[i].reductioncontents), null, 6)
    }

    console.log("ReductionStore.ReductionList", ReductionStore.ReductionList)


  } catch (error) {
    console.error("发生错误", error);
  }

};

const showContentFlag = reactive({});


const dialogFormVisibleNew = ref(false)
const dialogformNewReduction = reactive({
  reductionname: '',
  reductiondescriptions: '',
  lastupdatedtime: '',
  createdtime: '',
  testsuitesid: route.query.TestSuiteID
})
const showdialogNew = () => {
  dialogFormVisibleNew.value = true
  dialogformNewReduction.modelid = route.query.modelid
}

const confirmNewReduction = async () => {
  // 获取当前时刻的Date对象
  const currentDate = new Date();
  dialogformNewReduction.lastupdatedtime = currentDate
  dialogformNewReduction.createdtime = currentDate
  // 构造发送给Reduction的data对象
  let Obj = {}
  let testsuite = JSON.parse(currentTestSuite.currentTestSuites.testsuitescontents).testsuite
  Obj.testsuite = testsuite

  const parsedData = JSON.parse(currentModel.currentModel.paramsvalues)
  // 移除 row_index 属性
  const tableDataTmp = parsedData.map(item => {
    const { row_index, ...rest } = item;
    return rest;
  });
  let tempArray = []
  let param_count = 0
  for (let i = 0, len = tableDataTmp.length; i < len; i++) {
    if (tableDataTmp[i].Value != '') { tempArray.push(tableDataTmp[i].Value.split(',').length) }
    if (tableDataTmp[i].Parameter != '') { param_count = param_count + 1 }
  }
  Obj.parameter = param_count
  Obj.values = tempArray.map(Number)
  Obj.strength = currentModel.currentModel.strength
  Obj = JSON.stringify(Obj)
  // console.log("OBJ",Obj)
  try {
    const ReductionRes = await request({
      // url:tool.url 这里记得改回去，在校外无法用校内服务器
      url: 'http://localhost:8305',
      method: 'POST',
      // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
      headers: {
        'Content-Type': 'text/plain'
      },
      data: Obj
    })
    // console.log("ReductionRes",ReductionRes)
    const newReductionRes = await request({
      url: "/tools/reduction/NewReduction",
      method: "POST",
      data:
      {
        Reductionname: dialogformNewReduction.Reductionname,
        Reductiondescriptions: dialogformNewReduction.Reductiondescriptions,
        lastupdatedtime: dialogformNewReduction.lastupdatedtime,
        createdtime: dialogformNewReduction.createdtime,
        testsuitesid: currentTestSuite.currentTestSuites.testsuitesid,
        ReductiondContents: JSON.stringify(ReductionRes),
        time: ReductionRes.time,
        size: ReductionRes.size,
        algorithm: AlgorithmChosed.value
      }
    })

    if (newReductionRes.NewStatus == 'success!') {
      listAllReductionByTestSuitesID()
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
const confirmDelete = (Reduction) => {
  request({
    url: '/tools/reduction/DeleteByReductionID',
    method: 'POST',
    data: {
      Reductionid: Reduction.Reductionid
    }
  }).then((res) => {
    if (res.DeleteStatus == 'success!') {
      listAllReductionByTestSuitesID()
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
const listAllReductionOptions = () => {
  for (const tool of toolsInfo.RECORDS) {
    if (tool.type == "SelectionReduction") {
      AlgorithmOptions.push({ "value": tool.title, "label": tool.title, "url": tool.url })
    }
  }
}






onMounted(async () => {

  // 加载当前testSuites的 Reduction 信息
  await listAllReductionByTestSuitesID()

  // 加载所有Reduction选项
  await listAllReductionOptions()



})
</script>
