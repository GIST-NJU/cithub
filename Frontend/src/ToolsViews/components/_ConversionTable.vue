<template>
  <div class="card">
    <div class="card-header pb-0">
      <h5>{{ conversionStore.ConversionList.length }} Conversions found.</h5>
    </div>
    <div style="width:100%;margin-bottom: 5px;text-align: center;">
      <ArgonButton style="float: right;margin-right: 3%;" color="secondary" variant="gradient" @click="showdialogNew">
        <span class="ni ni-fat-add ni-lg me-1" />
        Generate New Test Plan
      </ArgonButton>
    </div>
    <!-- New Test Plan -->
    <el-dialog v-model="dialogFormVisibleNew" title="Convert TestSuite into:">
      <el-form :model="dialogformNewConversion" label-position="right" label-width="140px">
        <el-form-item label="Conversion Name:">
          <el-input v-model="dialogformNewConversion.conversionname" />
        </el-form-item>
        <el-form-item label="Conversion Desc:">
          <el-input autosize type="textarea" v-model="dialogformNewConversion.conversiondescriptions" />
        </el-form-item>
        <el-form-item label="Test Plan:">
          <el-select v-model="AlgorithmChosed" class="m-2"
            placeholder="Select a test plan for converting test suite into." clearable>
            <el-option v-for="item in AlgorithmOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <div v-if="AlgorithmChosed == 'JUnit Test Plan'">
          <el-form-item label="Function Head">
            <el-input v-model="functionHead" type="textarea" autosize
              placeholder="Please input function Head right here, eg:'public void test()'"></el-input>
          </el-form-item>
          <el-form-item label="Function Body">
            <el-input v-model="functionBody" type="textarea" autosize
              placeholder="Please input function Body right here, eg:''CIT cit= new CIT(&p2) cit.execuse(&p3) cit.process(&p1)'">
            </el-input>
          </el-form-item>
        </div>


        <div v-if="AlgorithmChosed == 'NL Test Plan'">

          <el-form-item label="Output Model Format">
            <el-input v-model="outputModel" type="textarea" :autosize="{ minRows: 4 }"
              placeholder="Please input the Output Model format you want right here and use `&#` as placeholder, eg:'first,we should do &#,then open &#,finally,enter in the browser address bar with &#'. All the `&#` will be replaced by the actual value of parameters.">
            </el-input>
          </el-form-item>

        </div>


      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleNew = false">Cancel</el-button>
          <el-button type="primary" @click="confirmNewConversion">
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
              <th class=" text-secondary text-xxs font-weight-bolder opacity-7">Conversion</th>
              <th class=" text-secondary text-xxs font-weight-bolder opacity-7 ps-2">
                <ArgonBadge>Algorithm</ArgonBadge>
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

            <tr v-for="(conversion, index) in conversionStore.ConversionList" :key="index">

              <td>
                <div class="d-flex px-2 py-1">
                  <div class="d-flex flex-column justify-content-center">
                    <h6 class="mb-0 text-sm">{{ conversion.conversionname }}</h6>
                    <p class="text-xs text-secondary mb-0">{{ conversion.conversiondescriptions }}</p>
                  </div>
                </div>
              </td>
              <td>
                <p class="text-xs font-weight-bold mb-0">{{ conversion.algorithm }}</p>
              </td>


              <td class="align-middle text-center text-sm">
                <p class="text-xs font-weight-bold mb-0">{{ conversion.createdtimeFortmat }}</p>
              </td>

              <td class="align-middle text-center">
                <p class="text-xs font-weight-bold mb-0">{{ conversion.lastupdatedtimeFortmat }}</p>
              </td>


              <td class="align-middle text-center">
                <a class="btn btn-link text-dark px-3 mb-0" @click="showContentFlag[index] = !showContentFlag[index]">
                  <i class="fas fa-book-open text-primary me-2" aria-hidden="true"></i>Details
                </a>

                <el-popconfirm title="Are you sure to delete this project?" confirm-button-text="Yes"
                  @confirm="confirmDelete(conversion)">
                  <template #reference>
                    <a class="btn btn-link text-danger text-gradient px-3 mb-0">
                      <i class="far fa-trash-alt me-2" aria-hidden="true"></i>Delete
                    </a>
                  </template>
                </el-popconfirm>

              </td>
            </tr>

            <tr v-auto-animate v-for="(conversion, index) in conversionStore.ConversionList" :key="`content-${index}`">
              <td v-if="showContentFlag[index]" colspan="6">
                <div>
                  <h6>Conversion Result of `{{ conversion.conversionname }}` </h6>
                  <el-input disabled v-model="conversion.ConversionResultPreview" autosize type="textarea"
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
import ArgonBadge from '../../CustomizedComponents/ArgonBadge.vue'
import ArgonButton from '../../CustomizedComponents/ArgonButton.vue';
import { useModelsStore } from '../../store/modelsStore'
import { routerKey, useRoute, useRouter } from 'vue-router';
import { request } from '../../request';
import { ElNotification } from 'element-plus'
import { useTestSuitesStore } from '../../store/testSuitesStore'
import { useConversionStore } from '../../store/conversionStore'
import toolsInfo from "../../CustomizedComponents/tools_info.json";
import { useCurrentModel } from '../../store/currentModel'
import { useCurrentTestSuitesStore } from '../../store/currentTestSuite'
import pinia from '../../store/store'
const router = useRouter();
const currentModel = useCurrentModel(pinia)
const testSuitesStore = useTestSuitesStore(pinia)
const conversionStore = useConversionStore(pinia)
const currentTestSuite = useCurrentTestSuitesStore(pinia)
const route = useRoute()
const functionHead = ref('')
const functionBody = ref('')
const outputModel = ref('')

const listAllConversionByTestSuitesID = async () => {
  try {
    const ConversionRes = await request({
      method: "POST",
      url: '/tools/conversion/listConversionByTestSuitesID',
      data: {
        testsuitesid: currentTestSuite.currentTestSuites.testsuitesid
      }
    });
    conversionStore.ConversionList = ConversionRes.conversions

    for (let i = 0; i < conversionStore.ConversionList.length; i++) {

      const timestamp_created = conversionStore.ConversionList[i].createdtime
      const timestamp_lastupdated = conversionStore.ConversionList[i].lastupdatedtime
      const dateObject_created = new Date(timestamp_created);
      const dateObject_lastupdated = new Date(timestamp_lastupdated);


      // 获取可读的时间字符串
      conversionStore.ConversionList[i].createdtimeFortmat = dateObject_created.toLocaleString();
      conversionStore.ConversionList[i].lastupdatedtimeFortmat = dateObject_lastupdated.toLocaleString();


      conversionStore.ConversionList[i].ConversionResultPreview = JSON.stringify(JSON.parse(conversionStore.ConversionList[i].conversioncontents), null, 6)
    }



  } catch (error) {
    console.error("发生错误", error);
  }

};

const showContentFlag = reactive({});


const dialogFormVisibleNew = ref(false)
const dialogformNewConversion = reactive({
  conversionname: '',
  conversiondescriptions: '',
  lastupdatedtime: '',
  createdtime: '',
  testsuitesid: route.query.TestSuiteID
})
const showdialogNew = () => {
  dialogFormVisibleNew.value = true
  dialogformNewConversion.modelid = route.query.modelid
}

const confirmNewConversion = async () => {
  // 获取当前时刻的Date对象
  const currentDate = new Date();
  dialogformNewConversion.lastupdatedtime = currentDate
  dialogformNewConversion.createdtime = currentDate
  // 构造发送给Conversion的data对象
  let conversionObj = {}
  let testsuite = JSON.parse(currentTestSuite.currentTestSuites.testsuitescontents).testsuite
  conversionObj.testsuite = testsuite
  let actual_value = []
  let ParamsAndValues = JSON.parse(currentModel.currentModel.paramsvalues)

  // 移除 row_index 属性
  ParamsAndValues = ParamsAndValues.map(item => {
    const { row_index, ...rest } = item;
    return rest;
  });

  for (const pv of ParamsAndValues) {
    let tempArray = pv.Value.split(',')
    actual_value.push(tempArray)
  }
  conversionObj.actual_value = actual_value


  if (AlgorithmChosed.value == 'NL Test Plan') {
    conversionObj.outputModel = outputModel.value
    conversionObj = JSON.stringify(conversionObj)

    for (const tool of AlgorithmOptions) {
      if (tool.value == AlgorithmChosed.value) {
        try {
          const ConversionRes = await request({
            // 这里记得改回去，在校外无法用校内服务器
            url: tool.url,
            // url: 'http://localhost:8302',
            method: 'POST',
            // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
            headers: {
              'Content-Type': 'text/plain'
            },
            data: conversionObj
          })

          const newConversionRes = await request({
            url: "/tools/conversion/NewConversion",
            method: "POST",
            data:
            {
              conversionname: dialogformNewConversion.conversionname,
              conversiondescriptions: dialogformNewConversion.conversiondescriptions,
              lastupdatedtime: dialogformNewConversion.lastupdatedtime,
              createdtime: dialogformNewConversion.createdtime,
              testsuitesid: currentTestSuite.currentTestSuites.testsuitesid,
              conversionContents: JSON.stringify(ConversionRes.testsuiteOfConversion),
              algorithm: AlgorithmChosed.value
            }
          })

          if (newConversionRes.NewStatus == 'success!') {
            listAllConversionByTestSuitesID()
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


  if (AlgorithmChosed.value == 'JUnit Test Plan') {
    conversionObj.functionHead = functionHead.value
    conversionObj.functionBody = functionBody.value
    conversionObj = JSON.stringify(conversionObj)

    for (const tool of AlgorithmOptions) {
      if (tool.value == AlgorithmChosed.value) {
        try {
          const ConversionRes = await request({
            // 这里记得改回去，在校外无法用校内服务器
            url: tool.url,
            // url: 'http://localhost:8302',
            method: 'POST',
            // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
            headers: {
              'Content-Type': 'text/plain'
            },
            data: conversionObj
          })

          const newConversionRes = await request({
            url: "/tools/conversion/NewConversion",
            method: "POST",
            data:
            {
              conversionname: dialogformNewConversion.conversionname,
              conversiondescriptions: dialogformNewConversion.conversiondescriptions,
              lastupdatedtime: dialogformNewConversion.lastupdatedtime,
              createdtime: dialogformNewConversion.createdtime,
              testsuitesid: currentTestSuite.currentTestSuites.testsuitesid,
              conversionContents: JSON.stringify(ConversionRes.testsuiteOfJUnit),
              algorithm: AlgorithmChosed.value
            }
          })

          if (newConversionRes.NewStatus == 'success!') {
            listAllConversionByTestSuitesID()
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

}





const confirmDelete = (conversion) => {
  request({
    url: '/tools/conversion/DeleteByConversionID',
    method: 'POST',
    data: {
      conversionid: conversion.conversionid
    }
  }).then((res) => {
    if (res.DeleteStatus == 'success!') {
      listAllConversionByTestSuitesID()
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
const listAllConversionOptions = () => {
  for (const tool of toolsInfo.RECORDS) {
    if (tool.type == "FormatConversion" && !tool.title.includes("Reader")) {
      AlgorithmOptions.push({ "value": tool.title, "label": tool.title, "url": tool.url })
    }
  }
}






onMounted(async () => {

  // 加载当前testSuites的Conversion信息
  await listAllConversionByTestSuitesID()

  // 加载所有Conversion选项
  await listAllConversionOptions()



})
</script>
