
<template>
  <tr>
    <td>
      <div class="d-flex px-3 py-3">
        <div class="d-flex flex-column justify-content-center">
          <p class="mb-0">
            {{ props.item.author }}<br>
            <strong>{{ props.item.title }}</strong><br>
            <em>{{ props.item.booktitle }}</em>
          </p>
        </div>
      </div>
    </td>
    <td class="align-bottom text-end">
      <span @click="BibTexFlag = !BibTexFlag">BibTex </span>
      <span @click="AbsFlag = !AbsFlag"> | Abstract | </span>
      <span @click="doiFlag = !doiFlag"> DOI&nbsp;&nbsp;&nbsp;&nbsp; </span>
    </td>
  </tr>
  <div v-auto-animate style="margin-bottom:3px;margin-left: 8px;">
    <div class="bibtex" v-if="BibTexFlag">
      <div>{{ '@' + props.item.type + '{' + props.item.author + ':' + props.item.year + '.' + props.item.id + ',\n' }}
      </div>
      <div>{{ ' author={' + props.item.author + '},\n' }}</div>
      <div>{{ ' title={' + props.item.title + '},\n' }}</div>
      <div v-if="props.item.type == 'inproceedings'">{{ ' booktitle={' + props.item.booktitle + '},\n' }}</div>
      <div v-if="props.item.type == 'article'">{{ ' journal={' + props.item.booktitle + '},\n' }}</div>
      <div v-if="props.item.journal != undefined">{{ ' journal={' + props.item.journal + '},' + '\n' }}</div>
      <div v-if="props.item.vol != ''">{{ ' volume={' + props.item.vol + '},\n' }}</div>
      <div v-if="props.item.no != ''">{{ ' number={' + props.item.no + '},\n' }}</div>
      <div>{{ ' pages={' + props.item.pages + '},\n' }}</div>
      <div>{{ ' year={' + props.item.year + '},\n' }}</div>
      <div v-if="props.item.doi != ''">{{ ' doi={' + props.item.doi + '},\n' }}</div>
      <div>{{ ' }' }}</div>
      <div>{{}}</div>
    </div>
  </div>
  <div v-auto-animate style="margin-bottom:3px">
    <div class="bibtex" v-if="AbsFlag">
      <div>{{ item.paperabstract }}</div>
    </div>
  </div>
  <div v-auto-animate style="margin-bottom:3px">
    <div class="bibtex" v-if="doiFlag">
      <div>{{ item.doi }}</div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, reactive, ref } from 'vue'
const props = defineProps({
  item: Object,
})

let BibTexFlag = ref(false)
let AbsFlag = ref(false)
let doiFlag = ref(false)
</script>


<style scoped>
.bibtex {

  margin-top: 3px;
  border: 1px solid rgb(0, 0, 0);
  border-radius: 12px;


  height: auto;
  padding: 15px;
  white-space: pre-wrap;
  position: relative;


}
</style>
