<template>
    <tr>
        <td class="align-top">
            <p class="ps-3">{{ props.index + 1 }}</p>
        </td>
        <td>


            <div class="d-flex ps-2">
                <div class="d-flex flex-column justify-content-center">
                    <p class="mb-0">
                        <span>Model Name: </span><strong>{{ props.item.modelname }}</strong><br>
                        <span>Model Description: </span><em>{{ props.item.modeldescription }}</em><br>
                        <!-- <span>Num of Parameters: </span><strong>{{ props.item.parameter }}</strong><br>
                        <span>Max Value Domain: </span><strong>{{ props.item.maximumvalue }}</strong><br>
                        <span>Num of Constraints : </span><strong>{{ props.item.cons }}</strong><br> -->
                        <span>Benchmark Set: </span><strong>{{ props.item.benchmarkset }}</strong><br>
                        <span>Reference: </span><strong>{{ props.item.reference }}</strong><br>
                        <span>Reference Year : </span><strong>{{ props.item.referenceyear }}</strong><br>
                        <span>Reference Research Field: </span><strong :class="getResearchFieldColor(props.item.field)">
                            {{ props.item.field }}
                        </strong>
                    </p>
                </div>
            </div>

            <div class="d-flex justify-content-between mt-3">
                <div>
                    <p :class="getModelTypeColor(props.item.modeltype)">{{ props.item.modeltype }} Model</p>
                </div>
                <div class="d-flex">
                    <div class="me-2">
                        <ArgonButton color="primary" @click="JumpToReference(props.item.reference, props.item.modelname)">
                            Reference
                        </ArgonButton>
                    </div>
                    <div>
                        <ArgonButton color="info" @click="JumpToModelDetails(props.item)">Model Details</ArgonButton>
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
import ArgonInput from '../../CustomizedComponents/ArgonInput.vue';
import pinia from '../../store/store'
import { request } from '../../request'
import { ElLoading } from 'element-plus'
import { useModuleStore } from '../../store/module';
import {getResearchFieldColor} from '../commonFunction'
import { ElNotification } from 'element-plus'
import { usePaginationStore } from '../../store/paginationStore'
import { usePaperInfoStore } from '../../store/paperinfoStore'




const PaperInfoStore = usePaperInfoStore(pinia)
const PaginationStore = usePaginationStore(pinia)
const moduleStore = useModuleStore(pinia)
const router = useRouter()
const props = defineProps({
    item: Object,
    index: Number,
})






const getModelTypeColor = (modeltype) => {
    switch (modeltype) {
        case 'unconstrained':
            return 'badge bg-success';
        case 'constrained':
            return 'badge bg-danger';
    }
}

const JumpToReference = async (reference, modelname) => {
    let loadingInstance = ElLoading.service({ fullscreen: true })
    moduleStore.CurrentModule = 'Benchmark Models'
    moduleStore.CurrentRoute = 'Benchmark_Models'
    console.log("modelname", modelname)
    moduleStore.CurrentModuleDetails = modelname
    try {
        PaginationStore.pagenum = 1
        PaginationStore.pagesize = 25
        PaginationStore.searchkeywords = ''
        PaginationStore.column = ''
        PaperInfoStore.paperinfos.length = 0

        const JumpToReferenceRes = await request(
            {
                url: 'repo/list/searchBy',
                method: 'POST',
                data:
                {
                    pagenum: PaginationStore.pagenum,
                    pagesize: PaginationStore.pagesize,
                    searchkeywords: reference,
                    column: 'title'

                }
            }
        )
        console.log("JumpToReferenceRes", JumpToReferenceRes)
        PaperInfoStore.paperinfos.push(...JumpToReferenceRes.res.records);
        PaginationStore.total = JumpToReferenceRes.res.records.length;
        PaperInfoStore.total = PaginationStore.total;

        router.push({
            path: '/repository/papers',
            query: {
                module: 'Benchmark',
            }
        })

        loadingInstance.close()
    } catch (error) {
        console.log("错误!", error)
        loadingInstance.close()
    }
}

const JumpToModelDetails = (modelInfo) => {
    // currentBenchmarkModel.model = modelInfo
    router.push({
    path: '/benchmark/modelinfo',
    query:
      { modelid: modelInfo.modelid }
  })

}
</script>
  
  
<style scoped></style>
  