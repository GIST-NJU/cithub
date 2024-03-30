<template>
  <div class="card">
    <!-- <div class="card-header pb-0">
      <h5>Models List</h5>
    </div> -->
    <div class="card-body px-0 pt-0 pb-2">
      <div class="table-responsive p-0">
        <table class="table align-items-center mb-0">
          <thead>
            <tr>
              <th class=" text-secondary text-xxs font-weight-bolder opacity-7">Model </th>
              <th class=" text-secondary text-xxs font-weight-bolder opacity-7">Type </th>
              <th class=" text-secondary text-xxs font-weight-bolder opacity-7 ">Strength</th>
              <th class=" text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Num of TestSuites</th>
              <th class="text-center  text-secondary text-xxs  opacity-7">Created time
              </th>
              <th class="text-center  text-secondary text-xxs  opacity-7">Last Updated
                time</th>
              <th class="text-secondary opacity-7 text-center">Operation</th>
            </tr>
          </thead>
          <tbody>

            <tr class="cardTR" v-for="(model, index) in props.model">

              <td>
                <div class="d-flex px-2 py-1">
                  <div class="d-flex flex-column justify-content-center">
                    <h6 class="mb-0 text-sm">{{ model.modelname }}</h6>
                    <p class="text-xs text-secondary mb-0">{{ model.modeldescriptions }}</p>
                  </div>
                </div>
              </td>
              <td>
                <p class="text-xs font-weight-bold mb-0 text-center">{{ model.modeltype }}</p>
              </td>
              <td>
                <p v-if="model.modeltype != 'LLM'" class="text-xs font-weight-bold mb-0 text-center">{{ model.strength }}
                </p>
                <p v-if="model.modeltype == 'LLM'" class="text-xs font-weight-bold mb-0 text-center">\
                </p>

              </td>
              <td>
                <p v-if="model.modeltype != 'LLM'" class="text-xs font-weight-bold mb-0 text-center">{{
                  model.NumOfTestSuites }}</p>
                <p v-if="model.modeltype == 'LLM'" class="text-xs font-weight-bold mb-0 text-center">\</p>
              </td>
              <td class="align-middle text-center text-sm">
                <!-- <span class="badge badge-sm bg-gradient-success">Online</span> -->
                <p class="text-xs font-weight-bold mb-0">{{ model.createdtimeFortmat }}</p>

              </td>
              <td class="align-middle text-center">
                <p class="text-xs font-weight-bold mb-0">{{ model.lastupdatedtimeFortmat }}</p>
              </td>
              <td class="align-middle text-center">
                <div class="d-flex justify-content-between align-items-center">
                  <div>
                    <ArgonButton color="primary" variant="gradient" @click="EnterModels(props.model)">
                      <span class="fas fa-book-open text-white me-2" />
                      Edit Model
                    </ArgonButton>
                    <ArgonButton color="success" variant="gradient" style="margin-left: 10px;"
                      @click="TestSuite(props.model)">
                      <span class="fas fa-print text-white me-2" />
                      TestSuite
                    </ArgonButton>
                  </div>
                  <div>
                    <el-popconfirm title="Are you sure to delete this project?" confirm-button-text="Yes"
                      @confirm="confirmDelete(props.model)">
                      <template #reference>
                        <ArgonButton color="danger" variant="gradient" @click="confirmDelete(props.model)">
                          <span class="far fa-trash-alt text-white me-2" />
                          Delete
                        </ArgonButton>
                      </template>
                    </el-popconfirm>
                  </div>
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
import { onMounted, reactive, ref, computed } from 'vue';
import ArgonBadge from '../../ComponentCommon/ArgonBadge.vue'
import ArgonButton from '../../ComponentCommon/ArgonButton.vue';
import { useModelsStore } from '../../store/modelsStore'
import { useCurrentModel } from '../../store/currentModel'
import { useRoute, useRouter } from 'vue-router';
import { request } from '../../request';
import { ElNotification } from 'element-plus'
import {listAllModelsByUserID} from '../../ToolsViews/common'
import pinia from '../../store/store'

const props = defineProps({
  model: Object,
})
const route = useRoute()
const router = useRouter()
const modelStore = useModelsStore(pinia)
const currentModel = useCurrentModel(pinia)

const EnterModels = (model) => {
  router.push({
    path: '/tools/modelsDetails',
    query:
      { modelid: model.modelid }
  })
}

const TestSuite =(model)=>{
    // 记录下当前的model
  currentModel.currentModel = {}
  currentModel.currentModel = model
  if (currentModel.currentModel.paramsvalues != null) {
    // 将model转成Cithub格式的model,并添加到currentModel中
    loadModel(model)
    router.push({
      path: '/tools/TestSuitesHomeNew',
      query:
        { modelid: model.modelid }
    })
  }
  else {
    ElNotification({
      title: 'Enter Generation Error!',
      message: 'Please create a model first!',
      type: 'error',
    })
  }
}

const confirmDelete = (model) => {

request({
  url: '/tools/models/DeleteByModelID',
  method: 'POST',
  data: {
    modelid: model.modelid
  }
}).then((res) => {
  if (res.DeleteStatus == 'success!') {

    // 实时更新页面数据
    listAllModelsByUserID()
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

// 返回parameterName在tableData中的索引
// value在该parameterName中的索引
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
// 将model转成 cithub格式的model
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



onMounted(() => {
  console.log()
})
</script>

<style scoped>
</style>