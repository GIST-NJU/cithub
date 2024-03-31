<template>
    <tr>
        <td class="align-top">
            <p class="ps-3">{{ props.index + 1 }}</p>
        </td>
        <td>
          <div>
            <div class="row">
                <div class="col-md-2">
                    <h6> Model Name</h6>
                    <argon-input v-model="props.item.modelname" type="text" :disabled="'disabled'" />
                </div>
                <div class="col-md-2">
                    <h6> Num of Parameters</h6>
                    <argon-input v-model="props.item.parameter" type="text" :disabled="'disabled'" />
                </div>
                <div class="col-md-2">
                    <h6> Max Value Domain</h6>
                    <argon-input v-model="props.item.maximumvalue" type="text" :disabled="'disabled'" />
                </div>
                <div class="col-md-2">
                    <h6> Num of Constraints</h6>
                    <argon-input v-model="props.item.cons" type="text" :disabled="'disabled'" />
                </div>

                <div class="col-2">
                    <h6>Benchmark Set</h6>
                    <argon-input v-model="props.item.benchmarkset" type="text" :disabled="'disabled'" />
                </div>
            </div>

            <div class="row">
                <div class="col-10">
                    <h6> Model Description</h6>
                    <argon-input v-model="props.item.modeldescription" type="text" :disabled="'disabled'" />
                </div>
            </div>
            <div class="row">
                <div class="col-8">
                    <h6> Reference</h6>
                    <argon-input v-model="props.item.reference" type="text" :disabled="'disabled'" />
                </div>



                <div class="col-2">
                    <h6> Reference Year</h6>
                    <argon-input v-model="props.item.referenceyear" type="text" :disabled="'disabled'" />
                </div>

                <!-- <div class="col-2">
                    <h6> Reference Research Field </h6> <span :class="getResearchFieldColor(props.item.field)">
                        {{ props.item.field }}
                    </span>
                </div> -->
            </div>
          </div>

            <!-- <div class="d-flex ps-2">
                <div class="d-flex flex-column justify-content-center">
                    <p class="mb-0">
                        <span>Model Name: </span><strong>{{ props.item.modelname }}</strong><br>
                        <span>Model Description: </span><em>{{ props.item.modeldescription }}</em><br>
                        <span>Num of Parameters: </span><strong>{{ props.item.parameter }}</strong><br>
                        <span>Max Value Domain: </span><strong>{{ props.item.maximumvalue }}</strong><br>
                        <span>Num of Constraints : </span><strong>{{ props.item.cons }}</strong><br>
                        <span>Benchmark Set: </span><strong>{{ props.item.benchmarkset }}</strong><br>
                        <span>Year : </span><strong>{{ props.item.referenceyear }}</strong><br>
                        <span>Reference: </span><strong>{{ props.item.reference }}</strong><br>
                        <span>Reference Research Field: </span><strong :class="getResearchFieldColor(props.item.field)">
                            {{ props.item.field }}
                        </strong>
                    </p>
                </div>
            </div> -->

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
                        <ArgonButton color="info">Model Details</ArgonButton>
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
import ArgonButton from '../../ComponentCommon/ArgonButton.vue';
import ArgonInput from '../../ComponentCommon/ArgonInput.vue';
import pinia from '../../store/store'
import { request } from '../../request'
import { ElLoading } from 'element-plus'
import { useModuleStore } from '../../store/module';
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

const getModelTypeColor = (modeltype) => {
    switch (modeltype) {
        case 'unconstrained':
            return 'badge bg-success';
        case 'constrained':
            return 'badge bg-danger';
    }
}

const JumpToReference = async (reference,modelname) => {
    let loadingInstance = ElLoading.service({ fullscreen: true })
    moduleStore.CurrentModule = 'Benchmark Models'
    moduleStore.CurrentRoute = 'Benchmark_Models'
    console.log("modelname",modelname)
    moduleStore.CurrentModuleDetails =  modelname 
    console.log("moduleStore.CurrentModuleDetails!!!",moduleStore.CurrentModuleDetails)
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
        console.log("JumpToReferenceRes",JumpToReferenceRes)
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
        console.log("错误!",error) 
        loadingInstance.close()
    }
}

</script>
  
  
<style scoped></style>
  