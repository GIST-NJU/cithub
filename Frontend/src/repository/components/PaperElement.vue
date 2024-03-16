
<template >

  
  <tr :class="{ 'paper': true, 'table-active': isHovered }" @click="enterPaperInfo(props.item)"
    @mouseover="handleMouseOver" @mouseleave="handleMouseLeave">
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
          </p>
        </div>
      </div>
      <div class="align-bottom text-end">


        <span style="margin-left: 3px;"> </span>


      </div>
    </td>
  </tr>
</template>

<script setup>
import { defineProps, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useCurrentPaper } from '../../store/currentPaper'
import pinia from '../../store/store'

const router = useRouter()
const props = defineProps({
  item: Object,
  index: Number,
})
const currentPaperStore = useCurrentPaper(pinia)

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
const enterPaperInfo = (paper) => {
  router.push({
    path: '/repository/paperInfo',
    query:
      { paperid: paper.id }
  })
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
