<template>
  <div class="card">
    <!-- <div class="card-header pb-0 px-3">
      <h6 class="mb-0">Billing Information</h6>
    </div> -->

    <div style="width:100%;margin-bottom: 5px;text-align: center;">
      <div style="margin-right: 3%;margin-top:5px;float: right;">
        <ArgonButton full-width color="success" variant="gradient" @click="showdialogNew">
          <span class="ni ni-fat-add ni-lg me-1" />
          New Project
        </ArgonButton>
      </div>
      <!-- new Project -->
      <el-dialog v-model="dialogFormVisibleNew" title="New Project">
        <el-form :model="dialogformNew">

          <el-form-item label="Project Name:">
            <el-input v-model="dialogformNew.projectname" />
          </el-form-item>
          <el-form-item label="Project Description:">
            <el-input autosize type="textarea" v-model="dialogformNew.projectdescriptions" />
          </el-form-item>


        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisibleNew = false">Cancel</el-button>
            <el-button type="primary" @click="confirmNewProject">
              Confirm
            </el-button>
          </span>
        </template>
      </el-dialog>
    </div>

    <div v-for="(chunk, index) in props.chunkedArray" :key="index" class="row" style="margin: 0 0 0 20px;">

      <div class="col" v-for="(project, colIndex) in chunk" :key="colIndex">


        <div class="card-body pt-4 p-3">
          <ul class="list-group">
            <li class="list-group-item border-0 d-flex p-4 mb-2 bg-gray-100 border-radius-lg">
              <div class="d-flex flex-column">
                <h6 class="mb-3 text-sm">{{ project.projectname }}</h6>
                <span class="mb-2 text-xs">
                  Description:
                  <span class="text-dark font-weight-bold ms-sm-2">{{ project.projectdescriptions }}</span>
                </span>
                <span class="mb-2 text-xs">
                  Num of Models:
                  <span class="text-dark font-weight-bold ms-sm-2">{{ project.NumOfModels }}</span>
                </span>
                <span class="mb-2 text-xs">
                  Last updated time :
                  <span class="text-dark ms-sm-2 font-weight-bold">{{ project.lastupdatedtimeFormat }}</span>
                </span>
                <span class="text-xs">
                  Created time :
                  <span class="text-dark ms-sm-2 font-weight-bold">{{ project.createdtimeFormat }}</span>
                </span>
              </div>
              <div class="ms-auto text-end">
                <a class="btn btn-link text-dark px-3 mb-0" @click="enterProject(project)">
                  <i class="fas fa-book-open text-success me-2" aria-hidden="true"></i>Project Details
                </a>
                <a class="btn btn-link text-dark px-3 mb-0" @click="showdialogUpdate(project, index)">
                  <i class="fas fa-pencil-alt text-primary me-2" aria-hidden="true"></i>Edit Project
                </a>

                <el-popconfirm title="Are you sure to delete this project?" confirm-button-text="Yes"
                  @confirm="confirmDelete(project)">
                  <template #reference>
                    <!-- <el-button>Delete</el-button> -->
                    <a class="btn btn-link text-danger text-gradient px-3 mb-0">
                      <i class="far fa-trash-alt me-2" aria-hidden="true"></i>Delete
                    </a>
                  </template>
                </el-popconfirm>

              </div>
            </li>

          </ul>
        </div>

      </div>

    </div>


    <!-- Update Project -->
    <el-dialog v-model="dialogFormVisibleUpdate" title="Update Project">
      <el-form :model="dialogformUpdate">
        <el-form-item label="Project ID:">
          <el-input disabled v-model="dialogformUpdate.projectid" />
        </el-form-item>
        <el-form-item label="Project Name:">
          <el-input v-model="dialogformUpdate.projectname" />
        </el-form-item>
        <el-form-item label="Project Description:">
          <el-input autosize type="textarea" v-model="dialogformUpdate.projectdescriptions" />
        </el-form-item>

      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleUpdate = false">Cancel</el-button>
          <el-button type="primary" @click="confirmUpdateProject">
            Confirm
          </el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { defineProps, reactive, ref } from 'vue'
import { request } from '../../request';
import { useCurrentProject } from '../../store/currentProject';
import { ElNotification } from 'element-plus'
import ArgonButton from '../../ComponentCommon/ArgonButton.vue';
import { useUserStore } from '../../store/userStore'
import { useProjectsStore } from '../../store/projectsStore'
import { useRouter } from 'vue-router';
import { async } from 'q';
const router = useRouter();
const currentProjectStore = useCurrentProject()
const dialogFormVisibleNew = ref(false)
const dialogFormVisibleUpdate = ref(false)
const userStore = useUserStore()


const props = defineProps({
  chunkedArray: Object,
})
const dialogformUpdate = reactive({
  projectid: '',
  projectname: '',
  projectdescriptions: '',
  lastupdatedtime: '',
  createdtime: ''


})
const dialogformNew = reactive({
  projectname: '',
  projectdescriptions: '',
  lastupdatedtime: '',
  createdtime: '',
  userid: userStore.userID
})
const showdialogNew = () => {

  dialogFormVisibleNew.value = true
  dialogformNew.userid = userStore.userID

}
const currentProjectIndex = ref()

const showdialogUpdate = (project, index) => {
  // console.log("project", project, "index", index)
  currentProjectIndex.value = index
  dialogFormVisibleUpdate.value = true
  dialogformUpdate.projectid = project.projectid
  dialogformUpdate.projectname = project.projectname
  dialogformUpdate.projectdescriptions = project.projectdescriptions
  dialogformUpdate.createdtime = project.createdtime

}

const enterProject = (project) => {
  currentProjectStore.createdtime = project.createdtime
  currentProjectStore.lastupdatedtime = project.lastupdatedtime
  currentProjectStore.projectdescriptions = project.projectdescriptions
  currentProjectStore.projectid = project.projectid
  currentProjectStore.projectname = project.projectname
  currentProjectStore.userid = project.userid
  router.push({
    path: '/tools/models',
    query: {
      projectid: project.projectid,
      projectname: project.projectname
    }
  })
}

const confirmNewProject = async () => {
  // 获取当前时刻的Date对象
  const currentDate = new Date();
  dialogformNew.lastupdatedtime = currentDate
  dialogformNew.createdtime = currentDate
  try {
    const res = await request({
      url: '/tools/projects/NewProject',
      method: 'POST',
      data: dialogformNew

    });
    if (res.NewStatus == 'success!') {

      ReloadProjects()

      ElNotification({
        title: 'New Project Success!',
        message: 'please check the results',
        type: 'success',
      })
      dialogFormVisibleNew.value = false

    }
    else {
      ElNotification({
        title: 'New Project Error!',
        message: 'please check the results',
        type: 'error',
      })
    }
  }
  catch (error) {
    ElNotification({
      title: 'New Project Error!',
      message: 'please check the results',
      type: 'error',
    })
  }

}

const confirmUpdateProject = () => {
  // 获取当前时刻的Date对象
  const currentDate = new Date();

  // 将Date对象转换为ISO 8601标准的时间戳字符串
  const currentISO8601Timestamp = currentDate.toISOString();

  dialogformUpdate.lastupdatedtime = currentISO8601Timestamp
  // console.log(dialogform)

  request({
    url: '/tools/projects/updateProject',
    method: 'POST',
    data: dialogformUpdate
  }).then((res) => {
    if (res.UpdateStatus == 'success!') {
      ElNotification({
        title: 'Update Success!',
        message: 'please check the results',
        type: 'success',
      })


      // 实时更新页面数据
      // props.chunkedArray[currentProjectIndex.value][0].projectname = dialogformUpdate.projectname
      // props.chunkedArray[currentProjectIndex.value][0].projectdescriptions = dialogformUpdate.projectdescriptions
      // const dateObject_created = new Date(props.chunkedArray[currentProjectIndex.value][0].createdtime);

      // props.chunkedArray[currentProjectIndex.value][0].lastupdatedtimeFormat = currentDate.toLocaleString();
      // props.chunkedArray[currentProjectIndex.value][0].createdtimeFormat = dateObject_created.toLocaleString();
      ReloadProjects()
      dialogFormVisibleUpdate.value = false
    }
  }).catch((error) => {
    // console.log(error)
    ElNotification({
      title: 'Update Error!',
      message: 'please check the results',
      type: 'error',
    })
  })

}
const confirmDelete = (project) => {
  console.log("project", project)
  request({
    url: '/tools/projects/DeleteByProjectID',
    method: 'POST',
    data: {
      projectid: project.projectid
    }
  }).then((res) => {
    if (res.DeleteStatus == 'success!') {

      // 实时更新页面数据
      ReloadProjects()
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
const projectList = reactive([]);
const projectsStore = useProjectsStore()

const ReloadProjects = async () => {
  try {
    // 获取用户当前所有的projects
    const projectsRes = await request({
      method: "POST",
      url: '/tools/projects/listProjectByUserID',
      data: {
        userID: userStore.userID
      }
    });

    projectList.length = 0
    projectsStore.projectList.length = 0
    projectList.splice(0, projectList.length, ...projectsRes.projectList);

    projectsStore.projectList = projectList



    for (let i = 0; i < projectsStore.projectList.length; i++) {

      const timestamp_created = projectsStore.projectList[i].createdtime
      const timestamp_lastupdated = projectsStore.projectList[i].lastupdatedtime
      const dateObject_created = new Date(timestamp_created);
      const dateObject_lastupdated = new Date(timestamp_lastupdated);

      // 获取可读的时间字符串
      projectsStore.projectList[i].createdtimeFormat = dateObject_created.toLocaleString();
      projectsStore.projectList[i].lastupdatedtimeFormat = dateObject_lastupdated.toLocaleString();
    }






  } catch (error) {
    console.error("发生错误", error);
  }

};
</script>
<style scoped>
li {
  transition: transform 0.3s ease-in-out;
}

li:hover {
  transform: scale(1.04);
}
</style>
