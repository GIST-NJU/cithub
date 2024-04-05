
<template >
  <tr>
    <td class="align-top">
      <p class="ps-3">{{ props.index + 1 }}</p>
    </td>
    <td>
      <div class="d-flex ps-2">
        <div class="d-flex flex-column justify-content-center">
          <p class="mb-0">
            {{ props.item.author }}<br>
            <strong>{{ props.item.title }}</strong><br>
            <em>{{ props.item.booktitle }}</em>
            <template v-if="props.item.type === 'article'">
              <template v-if="props.item.abbr">
                ({{ props.item.abbr }}),
              </template>
              <template v-if="props.item.vol">
                {{ props.item.vol }}
              </template>
              <template v-if="props.item.no">
                ({{ props.item.no }}):
              </template>
              <template v-if="props.item.pages && props.item.pages != 'not found'">
                {{ props.item.pages }}
              </template>
            </template>

            <template v-if="props.item.type === 'inproceedings'">
              <template v-if="props.item.abbr">
                ({{ props.item.abbr }}),
              </template>
              <template v-if="props.item.year">
                {{ props.item.year }}
              </template>
              <template v-if="props.item.pages && props.item.pages != 'not found'">
                :{{ props.item.pages }}
              </template>
            </template>
          </p>
        </div>
      </div>
      <div style="display: flex; justify-content:space-between;margin:10px 0 0 0">

        <div>
          <!-- <p v-if="moduleStore.CurrentRoute=='Repository_Papers'" :class="getResearchFieldColor(props.item.field)"> {{ props.item.field }}</p> -->
          <p :class="getResearchFieldColor(props.item.field)"> {{ props.item.field }}</p>
        </div>

        <div class="d-flex justify-content-between mt-3">
          <div style="margin: 0px 5px 0px 0px;"> <argon-button @click="JumpToDoi(props.item.doi)" size="sm"
              color="success"> doi</argon-button></div>
          <div> <argon-button @click="enterPaperInfo(props.item)" size="sm" color="primary"> detailed </argon-button>
          </div>
        </div>



      </div>

    </td>
  </tr>
</template>

<script setup>
import { defineProps, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useCurrentPaper } from '../../store/currentPaper'
import ArgonButton from '../../CustomizedComponents/ArgonButton.vue';
import pinia from '../../store/store'
import { useModuleStore } from '../../store/module';
import { ElNotification } from 'element-plus'
const moduleStore = useModuleStore(pinia)
const router = useRouter()
const props = defineProps({
  item: Object,
  index: Number,
})

let BibTexFlag = ref(false)
let AbsFlag = ref(false)
let doiFlag = ref(false)
const isHovered = ref(false);

const handleMouseOver = () => {
  isHovered.value = true;
}

const handleMouseLeave = () => {
  isHovered.value = false;
}

const JumpToDoi = (doi) => {
  if (doi == 'not found') {
    ElNotification({
      title: 'Sorry...',
      message: 'We do not find the doi of this paper.',
      type: 'error',
    })
  }
  {  // 拼接DOI链接
    const doiLink = 'http://dx.doi.org/' + doi;
    // 打开新窗口
    window.open(doiLink);
  }

}

const enterPaperInfo = (paper) => {
  router.push({
    path: '/repository/paperInfo',
    query:
      { paperid: paper.id }
  })
}

const getResearchFieldColor = (field) => {
  switch (field) {
    case 'Model':
      return 'badge bg-success';
    case 'Generation':
      return 'badge bg-primary';
    case 'Optimization':
      return 'badge bg-warning';
    case 'Diagnosis':
      return 'badge bg-danger';
    case 'Evaluation':
      return 'badge bg-dark';
    case 'Application':
      return 'badge bg-info';
    case 'Other':
      return 'badge bg-secondary';
  }
}

</script>


<style scoped>
.paper {
  transition: transform 0.3s ease;
  /* 添加过渡效果 */
}

.paper:hover {
  transform: scale(1.01);
  cursor: pointer;

}
</style>
