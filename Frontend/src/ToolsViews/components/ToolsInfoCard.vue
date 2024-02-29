<template>
  <div class="card">
    <div class="card-header pb-0 px-3">
      <ArgonBadge :color="color" variant="gradient" size="lg" ><h5 class="mb-0 text-dark">{{ props.toolType }}</h5></ArgonBadge>
    </div>
    <div class="card-body pt-4 p-3">
      <ul class="list-group">

        <li v-for="(tool, index) in props.toolsArray" :key="index"
          class="list-group-item border-0 d-flex p-4 mb-2 bg-gray-100 border-radius-lg">
          <div class="d-flex flex-column">
            <h6 class="mb-3 text ">{{ tool.title }}</h6>
            <span class="mb-2 text-xs">
              Description:
              <span class="text-dark font-weight-bold ms-sm-2">{{ tool.description }}</span>
            </span>
            <span class="mb-2 text-xs">
              URL:
              <span class="text-dark ms-sm-2 font-weight-bold">{{ tool.url }}</span>
            </span>
            <!-- <span class="mb-2 text-xs">
              Github Repository:
              <span class="text-dark ms-sm-2 font-weight-bold">{{ tool.repository }}</span>
            </span> -->
            <span v-if="tool.docker_image" class="mb-2 text-xs">
              DockerImage Repository:
              <span class="text-dark ms-sm-2 font-weight-bold">{{ tool.docker_image }}</span>
            </span>
            <span class="text-xs">
              tag:
              <!-- <ArgonBadge>{{ tool.tags }}</ArgonBadge> -->
              <span class="text-dark ms-sm-2 font-weight-bold"> {{ tool.tags }}</span>
            </span>
            <div class="ms-auto text-end">
              <a class="btn btn-link text-dark px-3 mb-0" @click="showFormat(index)">
                <ArgonBadge color="success"> <i class="fas fa-pencil-alt text-success me-2"
                    aria-hidden="true"></i>Input/Outpu Format of API call</ArgonBadge>
              </a>
            </div>
            <div v-auto-animate>
              <div v-if="showFormatFlag[index]">
                <h6>Input format:</h6>
                <el-input disabled v-model="tool.input_format" autosize type="textarea" placeholder="Please input" />
                <h6>Output format:</h6>
                <el-input disabled v-model="tool.output_format" autosize type="textarea" placeholder="Please input" />
              </div>
            </div>
          </div>

        </li>


      </ul>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref, computed } from 'vue';
import ArgonBadge from '../../ComponentCommon/ArgonBadge.vue'
onMounted(() => {
  // console.log("props.toolsArray", props.toolsArray)
})

const showFormatFlag = reactive({});

const showFormat = (toolId) => {
  showFormatFlag[toolId] = !showFormatFlag[toolId];
};


const props = defineProps({
  toolType: String,
  toolsArray: Array,
  color:String,

})
</script>
