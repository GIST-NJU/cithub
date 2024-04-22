
<template>

</template>

<script  setup>

</script>


<style scoped>

</style>

<!-- <template>
  <div class="card">
    <div class="p-3 mx-4 card-body">
      <h6 class="mb-0">Model#{{ props.index }} : {{ props.model.modelname }}</h6>
      <span class="text-md">{{ props.model.modeldescriptions }}</span>
      <hr class="my-3 horizontal dark" />
      <div class="row">
        <div class="col">
          <argon-badge style="margin: 5px">
            Num of Params
          </argon-badge>{{ props.model.NumofParams }}
        </div>
        <div class="col">
          <argon-badge color="danger" style="margin: 5px">
            Num of Cons
          </argon-badge>{{ props.model.NumofCons }}
        </div>
      </div>
      <div class="row">
        <div class="col">
          <argon-badge color="info" style="margin: 5px">
            Updated time
          </argon-badge>{{ props.model.lastupdatedtimeFortmat }}
        </div>
        <div class="col">
          <argon-badge color="info" style="margin: 5px">
            Created time
          </argon-badge>{{ props.model.createdtimeFortmat }}
        </div>
      </div>
      <hr class="my-3 horizontal dark" />
      <div class="d-flex justify-content-between align-items-center">
        <div>
          <ArgonButton color="primary" variant="gradient" @click="EnterModels(props.model)">
            <span class="fas fa-book-open text-white me-2" />
            Edit Model
          </ArgonButton>
          <ArgonButton color="success" variant="gradient" style="margin-left: 10px;" @click="TestSuite(props.model)">
            <span class="fas fa-print text-white me-2" />
            TestSuite
          </ArgonButton>
        </div>
        <div>
          <el-popconfirm title="Are you sure to delete this project?" confirm-button-text="Yes" @confirm="confirmDelete(props.model)">
            <template #reference>
              <ArgonButton color="danger" variant="gradient" @click="confirmDelete(props.model)">
                <span class="far fa-trash-alt text-white me-2" />
                Delete
              </ArgonButton>
            </template>
          </el-popconfirm>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import ArgonBadge from '../../CustomizedComponents/ArgonBadge.vue'
import ArgonButton from '../../CustomizedComponents/ArgonButton.vue';
import { useModelsStore } from '../../store/modelsStore'
import { useCurrentModel } from '../../store/currentModel'
import { useRoute, useRouter } from 'vue-router';
import { request } from '../../request';
import { ElNotification } from 'element-plus'
import { onMounted } from 'vue';
import pinia from '../../store/store'
const route = useRoute()
const router = useRouter()
const modelStore = useModelsStore(pinia)
const currentModel = useCurrentModel(pinia)
const props = defineProps({
  icon_bg: {
    type: String,
    default: "bg-white",
  },
  classIcon: {
    type: String,
    required: true,
  },
  model: Object,
  index: Object
})
const EnterModels = (model) => {
  router.push({
    path: '/tools/modelsDetails',
    query:
      { modelid: model.modelid }
  })
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
const Generation = (model) => {
  // 记录下当前的model
  currentModel.currentModel = {}
  currentModel.currentModel = model
  if (currentModel.currentModel.paramsvalues != null) {
    // 将model转成Cithub格式的model,并添加到currentModel中
    loadModel(model)
    router.push({
      path: '/tools/TestSuitesHome',
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
      ReloadModels()
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


const ReloadModels = async () => {
  try {
    // 获取当前Project的所有的models
    const modelsRes = await request({
      method: "POST",
      url: '/tools/models/listModelsByProjectID',
      data: {
        projectid: route.query.projectid
      }
    });

    modelStore.modelsList.length = 0
    modelStore.modelsList = modelsRes.models
    for (let i = 0; i < modelStore.modelsList.length; i++) {

      const timestamp_created = modelStore.modelsList[i].createdtime
      const timestamp_lastupdated = modelStore.modelsList[i].lastupdatedtime
      const dateObject_created = new Date(timestamp_created);
      const dateObject_lastupdated = new Date(timestamp_lastupdated);

      // 获取可读的时间字符串
      modelStore.modelsList[i].createdtimeFortmat = dateObject_created.toLocaleString();
      modelStore.modelsList[i].lastupdatedtimeFortmat = dateObject_lastupdated.toLocaleString();
    }






  } catch (error) {
    console.error("发生错误", error);
  }

};
onMounted(() => {


})
</script>
<style scoped>
.card {
  transition: transform 0.3s ease-in-out;
}

.card:hover {
  box-shadow: 0 0 10px #2dce89;
  border: 2px solid #2dce89;
  transform: scale(1.02);
}
</style> -->