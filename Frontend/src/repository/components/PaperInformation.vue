
<template>
  <div class="row">
    <div class="col-12">
      <div class="card mb-4">
        <div class="card-body pb-0">
          <h5>{{ currentPaperStore.currentPaper.title }}
          </h5>
          <p class="text-muted mb-0"><a href="">{{ FirstAuthorName }}</a>,{{ OtherAuthorName }}</p>
          <p class="text-muted mb-2">{{ currentPaperStore.currentPaper.booktitle }}</p>
          <p>
            <span v-if="TagArray.length!=0" class="badge rounded-pill bg-primary">research field</span>&nbsp;
              <span v-for="(tag, index) in TagArray" :key="index" class="badge rounded-pill bg-info" style="margin-right:5px">{{ tag }}</span>
          </p>

        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-12">
      <div class="card mb-4">
        <div class="card-body pb-0">
          <h5>Abstract</h5>
          <p>
            {{ currentPaperStore.currentPaper.paperabstract }}
            
          </p>
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-12">
      <div class="card mb-4">
        <div class="card-body pb-0">
          <h5>Paper Citation Relationships</h5>
          <p>...</p>
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-12">
      <div class="card mb-4">
        <div class="card-body pb-0">
          <h5>BibTex</h5>
          <pre class="mb-0">
{{ '@' + currentPaperStore.currentPaper.type + '{' + FirstAuthorName + '.' + currentPaperStore.currentPaper.id + ','
  + '\n  author={' + currentPaperStore.currentPaper.author + '},'
  + '\n  title={' + currentPaperStore.currentPaper.title + '},'
  + '\n  booktitle={' + currentPaperStore.currentPaper.booktitle + '},'
  + '\n  pages={' + currentPaperStore.currentPaper.pages + '},'
  + '\n  year={' + currentPaperStore.currentPaper.year + '},'
  + `\n  doi={` + currentPaperStore.currentPaper.doi + `}`
  + `\n }` }}

<!-- @inproceedings{FirstAuthorFamilyName.PaperID,
  author={Chuan Luo, Qiyuan Zhao, Shaowei Cai, Hongyu Zhang, Chunming Hu},
  title={SamplingCA: effective and efficient sampling-based pairwise testing for highly configurable software systems},
  booktitle={SIGSOFT},
  pages={1185--1197},
  year={2022},
  doi={10.1145/3540250.3549155},
} -->
          </pre>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, reactive, ref, onMounted } from 'vue'
import pinia from '../../store/store'
import { useCurrentPaper } from '../../store/currentPaper'
import { useRoute, useRouter } from 'vue-router';
import { request } from '../../request'

const currentPaperStore = useCurrentPaper(pinia)
const route = useRoute()
const FirstAuthorName = ref('')
const OtherAuthorName = ref('')
const TagArray = reactive([])
const listPaperInfoByPaperID = async () => {
  try {
    const res = await request({
      url: 'repo/list/listPaperInfoByPaperID',
      method: 'POST',
      data: {
        PaperID: route.query.paperid
      }
    });

    currentPaperStore.currentPaper.abbr = res.res.abbr
    currentPaperStore.currentPaper.id = res.res.id
    currentPaperStore.currentPaper.author = res.res.author
    currentPaperStore.currentPaper.booktitle = res.res.booktitle
    currentPaperStore.currentPaper.doi = res.res.doi
    currentPaperStore.currentPaper.field = res.res.field
    currentPaperStore.currentPaper.institution = res.res.institution
    currentPaperStore.currentPaper.no = res.res.no
    currentPaperStore.currentPaper.pages = res.res.pages
    currentPaperStore.currentPaper.paperabstract = res.res.paperabstract
    currentPaperStore.currentPaper.publisher = res.res.publisher
    currentPaperStore.currentPaper.tag = res.res.tag
    currentPaperStore.currentPaper.timeStamp = res.res.timeStamp
    currentPaperStore.currentPaper.title = res.res.title
    currentPaperStore.currentPaper.type = res.res.type
    currentPaperStore.currentPaper.vol = res.res.vol
    currentPaperStore.currentPaper.year = res.res.year
    let authorsArray = currentPaperStore.currentPaper.author.split(',')
    FirstAuthorName.value = authorsArray.shift();
    OtherAuthorName.value = authorsArray.join(',')
    console.log(" currentPaperStore.currentPaper.tag", currentPaperStore.currentPaper.tag)
    let tempTagArray = currentPaperStore.currentPaper.tag.split(',')
    TagArray.push(...tempTagArray)
  } catch (err) {
    console.error(err);
  }
}
onMounted(() => {

  listPaperInfoByPaperID()

})
</script>


<style scoped>
.bibtex {}
</style>
