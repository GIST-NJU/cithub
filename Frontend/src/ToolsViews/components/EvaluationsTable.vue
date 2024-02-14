<template>
  <div class="card">
    <div class="card-header pb-0">
      <h5>{{ evaluationStore.EvaluationList.length }} Evaluations found.</h5>
    </div>
    <div style="width:100%;margin-bottom: 5px;text-align: center;">
      <ArgonButton style="float: right;margin-right: 3%;" color="success" variant="gradient" @click="showdialogNew">
        <span class="ni ni-fat-add ni-lg me-1" />
        Generate New Evaluation
      </ArgonButton>
    </div>
    <!-- New Evaluation -->
    <el-dialog v-model="dialogFormVisibleNew" title="New Evaluation">
      <el-form :model="dialogformNewEvaluation">
        <el-form-item label="Evaluation Name:">
          <el-input v-model="dialogformNewEvaluation.evaluationname" />
        </el-form-item>
        <el-form-item label="Evaluation Description:">
          <el-input autosize type="textarea" v-model="dialogformNewEvaluation.evaluationdescriptions" />
        </el-form-item>
        <el-form-item label="Algorithm: ">
          <el-select v-model="AlgorithmChosed" class="m-2" placeholder="Select an Algorithm for evaluating">
            <el-option v-for="item in AlgorithmOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleNew = false">Cancel</el-button>
          <el-button type="primary" @click="confirmNewEvaluation">
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
              <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Evaluation</th>
              <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">
                <ArgonBadge>Algorithm</ArgonBadge>
              </th>

              <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                <ArgonBadge color="primary">TIME</ArgonBadge>
              </th>
              <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                created time
              </th>
              <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                last updated time
              </th>
              <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                Operation
              </th>

            </tr>
          </thead>
          <tbody>

            <tr v-for="(evaluation, index) in evaluationStore.EvaluationList" :key="index">

              <td>
                <div class="d-flex px-2 py-1">
                  <div class="d-flex flex-column justify-content-center">
                    <h6 class="mb-0 text-sm">{{ evaluation.evaluationname }}</h6>
                    <p class="text-xs text-secondary mb-0">{{ evaluation.evaluationdescriptions }}</p>
                  </div>
                </div>
              </td>
              <td>
                <p class="text-xs font-weight-bold mb-0">{{ evaluation.algorithm }}</p>
              </td>

              <td class="align-middle text-center text-sm">
                <p class="text-xs font-weight-bold mb-0">{{ evaluation.time }}</p>
              </td>
              <td class="align-middle text-center text-sm">
                <p class="text-xs font-weight-bold mb-0">{{ evaluation.createdtimeFortmat }}</p>
              </td>

              <td class="align-middle text-center">
                <p class="text-xs font-weight-bold mb-0">{{ evaluation.lastupdatedtimeFortmat }}</p>
              </td>


              <td class="align-middle text-center">
                <!-- "Details" 按钮 -->
                <a class="btn btn-link text-dark px-3 mb-0" @click="showContentFlag[index] = !showContentFlag[index]">
                  <i class="fas fa-book-open text-primary me-2" aria-hidden="true"></i>Details
                </a>

                <!-- 删除按钮 -->
                <el-popconfirm title="Are you sure to delete this project?" confirm-button-text="Yes"
                  @confirm="confirmDelete(evaluation)">
                  <template #reference>
                    <a class="btn btn-link text-danger text-gradient px-3 mb-0">
                      <i class="far fa-trash-alt me-2" aria-hidden="true"></i>Delete
                    </a>
                  </template>
                </el-popconfirm>

              </td>
            </tr>

            <!-- 显示 Evaluation Result 的行 -->
            <tr  v-auto-animate v-for="(evaluation, index) in evaluationStore.EvaluationList" :key="`content-${index}`">
              <td v-if="showContentFlag[index]" colspan="6">
                <div >
                  <h6>Evaluation Result of `{{ evaluation.evaluationname }}` </h6>
                  <el-input disabled v-model="evaluation.EvaluationResultPreview" autosize type="textarea"
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
import { useEvaluationStore } from '../../store/evaluationStore'
import toolsInfo from "../../ComponentCommon/tools_info.json";
import { useCurrentModel } from '../../store/currentModel'
import { useCurrentTestSuitesStore } from '../../store/currentTestSuite'
const router = useRouter();
const currentModel = useCurrentModel()
const testSuitesStore = useTestSuitesStore()
const evaluationStore = useEvaluationStore()
const currentTestSuite = useCurrentTestSuitesStore()
const route = useRoute()

const listAllEvaluationByTestSuitesID = async () => {
  try {
    const EvaluationRes = await request({
      method: "POST",
      url: '/tools/evaluation/listEvaluationByTestSuitesID',
      data: {
        testsuitesid: currentTestSuite.currentTestSuites.testsuitesid
      }
    });

    // console.log("EvaluationRes.evaluations", EvaluationRes.evaluations)
    evaluationStore.EvaluationList = EvaluationRes.evaluations


    for (let i = 0; i < evaluationStore.EvaluationList.length; i++) {

      const timestamp_created = evaluationStore.EvaluationList[i].createdtime
      const timestamp_lastupdated = evaluationStore.EvaluationList[i].lastupdatedtime
      const dateObject_created = new Date(timestamp_created);
      const dateObject_lastupdated = new Date(timestamp_lastupdated);


      // 获取可读的时间字符串
      evaluationStore.EvaluationList[i].createdtimeFortmat = dateObject_created.toLocaleString();
      evaluationStore.EvaluationList[i].lastupdatedtimeFortmat = dateObject_lastupdated.toLocaleString();


      evaluationStore.EvaluationList[i].EvaluationResultPreview=JSON.stringify(JSON.parse(evaluationStore.EvaluationList[i].evaluationcontents),null,6)
    }



  } catch (error) {
    console.error("发生错误", error);
  }

};

const showContentFlag = reactive({});

const showEvaluationContent = (index) => {
  showContentFlag[index] = !showContentFlag[index];
};
const dialogFormVisibleNew = ref(false)
const dialogformNewEvaluation = reactive({
  evaluationname: '',
  evaluationdescriptions: '',
  lastupdatedtime: '',
  createdtime: '',
  testsuitesid: route.query.TestSuiteID
})
const showdialogNew = () => {
  dialogFormVisibleNew.value = true
  dialogformNewEvaluation.modelid = route.query.modelid
}

const confirmNewEvaluation = async () => {

  // 获取当前时刻的Date对象
  const currentDate = new Date();
  dialogformNewEvaluation.lastupdatedtime = currentDate
  dialogformNewEvaluation.createdtime = currentDate

  // 构造发送给evaluation的data对象
  let evaluationObj = {}
  let ParametersAndValues = JSON.parse(currentModel.currentModel.paramsvalues)
  let tempArray = []
  let param_count = 0
  for (let i = 0, len = ParametersAndValues.length; i < len; i++) {
    if (ParametersAndValues[i].Value != '') { tempArray.push(ParametersAndValues[i].Value.split(',').length) }
    if (ParametersAndValues[i].Parameter != '') { param_count = param_count + 1 }
  }


  evaluationObj.strength = currentModel.currentModel.strength
  evaluationObj.parameter = param_count
  evaluationObj.values = JSON.stringify(tempArray)
  evaluationObj.testsuite = JSON.parse(currentTestSuite.currentTestSuites.testsuitescontents).testsuite
  evaluationObj = JSON.stringify(evaluationObj).replace(/"/g, '')

  // 发送请求，对  test suites 得到 evalution
  for (const tool of AlgorithmOptions) {
    if (tool.value == AlgorithmChosed.value) {
      try {
        const EvaluationRes = await request({
          // url:tool.url 这里记得改回去，在校外无法用校内服务器
          url: 'http://localhost:8301',
          method: 'POST',
          // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
          headers: {
            'Content-Type': 'text/plain'
          },
          data: evaluationObj
        })
        console.log("currentTestSuite.currentTestSuites", currentTestSuite.currentTestSuites)
        console.log("EvaluationRes", EvaluationRes)

        const newEvaluationRes = await request({
          url: "/tools/evaluation/NewEvaluation",
          method: "POST",
          data:
          {
            evaluationname: dialogformNewEvaluation.evaluationname,
            evaluationdescriptions: dialogformNewEvaluation.evaluationdescriptions,
            lastupdatedtime: dialogformNewEvaluation.lastupdatedtime,
            createdtime: dialogformNewEvaluation.createdtime,
            testsuitesid: currentTestSuite.currentTestSuites.testsuitesid,
            EvaluationContents: JSON.stringify(EvaluationRes),
            time: EvaluationRes.time,
            algorithm: AlgorithmChosed.value
          }
        })
        console.log("newEvaluationRes", newEvaluationRes)

        if (newEvaluationRes.NewStatus == 'success!') {
          listAllEvaluationByTestSuitesID()
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
const confirmDelete = (evaluation) => {
  console.log("evaluation", evaluation)
  request({
    url: '/tools/evaluation/DeleteByEvaluationID',
    method: 'POST',
    data: {
      evaluationid: evaluation.evaluationid
    }
  }).then((res) => {
    if (res.DeleteStatus == 'success!') {
      listAllEvaluationByTestSuitesID()
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
const listAllEvaluationAlgorithm = () => {
  for (const tool of toolsInfo.RECORDS) {
    if (tool.type == "Evaluation" && !tool.title.includes("GUI")) {
      AlgorithmOptions.push({ "value": tool.title, "label": tool.title, "url": tool.url })
    }
  }
  // console.log("AlgorithmOptions",AlgorithmOptions)
}
const findPosition = (parameterName, value, tableData) => {
  for (let i = 0; i < tableData.length; i++) {
    const parameter = tableData[i].Parameter;
    const values = tableData[i].Value.split(',');

    if (parameter === parameterName) {
      const valueIndex = values.indexOf(value);
      if (valueIndex !== -1) {
        return { parameterIndex: i, valueIndex };
      }
    }
  }

  return { parameterIndex: -1, valueIndex: -1 };
}
const loadModel = (model) => {
  // 解析参数和参数取值的Json字符串
  const parsedData = JSON.parse(model.paramsvalues)
  // 移除 row_index 属性
  const tableDataTmp = parsedData.map(item => {
    const { row_index, ...rest } = item;
    return rest;
  });

  // 加载约束table
  let tempArray = []
  let param_count = 0
  for (let i = 0, len = tableDataTmp.length; i < len; i++) {
    if (tableDataTmp[i].Value != '') { tempArray.push(tableDataTmp[i].Value.split(',').length) }
    if (tableDataTmp[i].Parameter != '') { param_count = param_count + 1 }

  }
  let modelObject = {
    system: "",
    strength: '',
    parameter: '',
    values: '',
    constraints: []

  }
  // 统计模型基本数据
  if (model.modelname) { modelObject.system = model.modelname }
  modelObject.strength = model.strength
  modelObject.parameter = param_count
  modelObject.values = JSON.stringify(tempArray)


  // 对 tableData 的数据做处理，转成被constraintsTableData
  let tempArrayCons = []
  for (let i = 0, len = tableDataTmp.length; i < len; i++) {
    let tempObj = {}
    tempObj.Parameter = tableDataTmp[i].Parameter
    tempObj.valueArray = tableDataTmp[i].Value.split(',').map((value, index) => ({
      [`Value${index + 1}`]: value
    }));
    tempObj.ValueDomain = tempObj.valueArray.length
    tempArrayCons.push(tempObj)

  }


  let consArray = JSON.parse(model.cons)
  // 对每个约束进行处理，将其转换成 '参数索引'/'取值索引' 的形式
  let consArrayToAPI = []
  for (const constraint of consArray) {
    let consArrayTemp = []
    // 遍历元素中的键值对
    for (const key in constraint) {
      if (Object.hasOwnProperty.call(constraint, key)) {
        const elements = constraint[key];
        // elements 是一个数组，包含了多个键值对
        for (const element of elements) {
          // 在这里访问 Parameter 和 Value
          const parameter = element.Parameter;
          const value = element.Value;

          // 这里可以使用 parameter 和 value 进行其他操作
          // console.log(`Key: ${key}, Parameter: ${parameter}, Value: ${value}`);
          let constempObj = findPosition(parameter, value, tableDataTmp)
          let consString = `\'${constempObj.parameterIndex}/${constempObj.valueIndex}\'`
          consArrayTemp.push(consString)
        }
      }
      consArrayToAPI.push(consArrayTemp)
    }
  }
  modelObject.constraints = consArrayToAPI

  // 将模型数据转换成cithub格式
  currentModel.currentModel.modelCithub = JSON.stringify(modelObject, null, 6).replace(/"/g, '')
}

const model = reactive({})
const listModelInfoByModelID = async () => {
  try {
    const res = await request({
      url: '/tools/models/info/' + route.query.modelid,
      method: 'POST'
    });

    model.modelid = res.models.modelid
    model.strength = res.models.strength
    model.modelname = res.models.modelname
    model.modeldescriptions = res.models.modeldescriptions
    model.paramsvalues = res.models.paramsvalues
    model.cons = res.models.cons
    model.lastupdatedtime = res.models.lastupdatedtime
    model.createdtime = res.models.createdtime

    currentModel.currentModel = {}
    currentModel.currentModel = model
    loadModel(model);
  } catch (err) {
    console.error(err);
  }
}

const EnterTestSuiteDetails = (testsuite) => {
  currentTestSuite.currentTestSuites.testsuitesid = testsuite.testsuitesid
  currentTestSuite.currentTestSuites.testsuitesname = testsuite.testsuitesname
  currentTestSuite.currentTestSuites.testsuitesdescriptions = testsuite.testsuitesdescriptions
  currentTestSuite.currentTestSuites.testsuitescontents = testsuite.testsuitescontents
  currentTestSuite.currentTestSuites.algorithm = testsuite.algorithm
  currentTestSuite.currentTestSuites.modelid = testsuite.modelid
  currentTestSuite.currentTestSuites.size = testsuite.size
  currentTestSuite.currentTestSuites.time = testsuite.time
  currentTestSuite.currentTestSuites.createdtime = testsuite.createdtime
  currentTestSuite.currentTestSuites.lastupdatedtime = testsuite.lastupdatedtime
  currentTestSuite.currentTestSuites.createdtimeFortmat = testsuite.createdtimeFortmat
  currentTestSuite.currentTestSuites.lastupdatedtimeFortmat = testsuite.lastupdatedtimeFortmat
  router.push(
    {
      name: 'TestSuiteDetails',
      query: {
        TestSuiteID: currentTestSuite.currentTestSuites.testsuitesid
      }
    }
  )

}

onMounted(async () => {

  // 加载当前testSuites的Evaluation信息
  await listAllEvaluationByTestSuitesID()

  // 加载所有Evaluation算法
  await listAllEvaluationAlgorithm()



})
</script>
