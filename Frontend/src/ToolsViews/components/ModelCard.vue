<template>
  <div class="card">
    <div class="p-3 mx-4 text-center card-header">
      <div :class="icon_bg" class="icon icon-shape icon-lg bg-gradient-success shadow text-center border-radius-lg">
        <i class="opacity-10" :class="classIcon" aria-hidden="true"></i>
      </div>
    </div>
    <div class="p-3 pt-0 text-center card-body">
      <h5 class="mb-0 text-center">{{ props.model.modelname }}</h5>
      <!-- <span class="text-xs">{{ modeldescriptions }}</span> -->
      <hr class="my-3 horizontal dark" />
      <h6>{{ props.model.modeldescriptions }}</h6>
      <hr class="my-3 horizontal dark" />


      <a class="btn btn-link text-dark px-3 mb-0" @click="EnterModels(props.model)">
        <i class="fas fa-book-open text-primary me-2" aria-hidden="true"></i>Enter
      </a>

      <a class="btn btn-link text-dark px-3 mb-0" @click="Generation(props.model)">
        <i class="fas fa-print text-success  me-2" aria-hidden="true"></i>Generation
      </a>

      <el-popconfirm title="Are you sure to delete this project?" confirm-button-text="Yes"
        @confirm="confirmDelete(props.model)">
        <template #reference>
          <!-- <el-button>Delete</el-button> -->
          <a class="btn btn-link text-danger text-gradient px-3 mb-0">
            <i class="far fa-trash-alt me-2" aria-hidden="true"></i>Delete
          </a>
        </template>
      </el-popconfirm>

      <hr class="my-3 horizontal dark" />
      <div style="display: flex;flex-direction: column; align-items: flex-start;">
        <p class="text-xs">
          <argon-badge variant="gradient" color="warning" style="margin: 5px">
            Strength:
          </argon-badge>{{ props.model.strength }}
        </p>
        <p class="text-xs">
          <argon-badge variant="gradient" color="success" style="margin: 5px">
            Updated time:
          </argon-badge>{{ props.model.lastupdatedtimeFortmat }}
        </p>
        <p class="text-xs">
          <argon-badge variant="gradient" color="primary" style="margin: 5px">
            Created time:
          </argon-badge>{{ props.model.createdtimeFortmat }}
        </p>
      </div>


    </div>
  </div>
</template>

<script setup>
import ArgonBadge from '../../ComponentCommon/ArgonBadge.vue'
import ArgonButton from '../../ComponentCommon/ArgonButton.vue';
import { useModelsStore } from '../../store/modelsStore'
import { useRoute, useRouter } from 'vue-router';
import { request } from '../../request';
import { ElNotification } from 'element-plus'
import { onMounted } from 'vue';
const route = useRoute()
const router = useRouter()
const modelStore = useModelsStore()

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
})
const EnterModels = (model) => {
  router.push({
    path: '/tools/modelsDetails',
    query:
      { modelid: model.modelid }
  })
}

const Generation = (model) => {
  router.push({
    path: '/tools/modelsGeneration',
    query:
      { modelid: model.modelid }
  })
}

const confirmDelete = (model) => {


  console.log("model", model)
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
  // console.log("model",props.model)
})
</script>
<style scoped>
.card {
  transition: transform 0.3s ease-in-out;
}

.card:hover {
  transform: scale(1.04);
  cursor: pointer;
}
</style>