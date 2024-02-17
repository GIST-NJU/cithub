
<template>
  <tr>
    <!-- TODO: Add index number of each paper -->
    <td class="align-top">
      <p class="ps-3">{{ props.index+1}}</p>
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


        <span class="btn btn-success" data-bs-toggle="collapse" :href="'#Bibtex' + props.item.id" role="button"
          aria-expanded="false" :aria-controls="'Bibtex' + props.item.id">BibTex </span>

        <span style="margin-left: 3px;" class="btn btn-primary" data-bs-toggle="collapse" :href="'#Abstract' + props.item.id" role="button"
          aria-expanded="false" :aria-controls="'Abstract' + props.item.id"> Abstract</span>

        <span style="margin-left: 3px;" class="btn btn-info" data-bs-toggle="collapse" :href="'#doi' + props.item.id" role="button"
          aria-expanded="false" :aria-controls="'doi' + props.item.id">doi </span>
        <!-- <span @click="BibTexFlag = !BibTexFlag">BibTex </span>
        <span @click="AbsFlag = !AbsFlag"> | Abstract | </span>
        <span @click="doiFlag = !doiFlag"> DOI&nbsp;&nbsp;&nbsp;&nbsp; </span> -->

      </div>
    </td>
  </tr>



  <tr class="collapse" :id="'Bibtex' + props.item.id" >
    <td colspan="2">
      <div style="width: 100%; margin-bottom: 3px; margin-left: 8px;">
        <div class="bibtex">
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
    </td>
  </tr>

  <tr class="collapse" :id="'Abstract' + props.item.id">
    <td colspan="2">
      <div class="bibtex">
        {{ item.paperabstract }}
      </div>
    </td>
  </tr>

  <tr class="collapse" :id="'doi' + props.item.id">
    <td colspan="2">
      <div class="bibtex">
        <div>{{ item.doi }}</div>
      </div>
    </td>
  </tr>

  
</template>

<script setup>
import { defineProps, reactive, ref } from 'vue'
const props = defineProps({
  item: Object,
  index: Number,
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
  width: 100%;
  height: auto;
  padding: 15px;
  white-space: pre-wrap;
  position: relative;
}
</style>
