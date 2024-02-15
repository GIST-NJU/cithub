<template>
    <div class="min-height-300 bg-success position-absolute w-100"></div>
    <SideNav></SideNav>
    <main class="main-content position-relative border-radius-lg ">
        <!-- Navbar -->
        <Navbar></Navbar>
        <!-- End Navbar -->

        <div class="container-fluid py-4">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-body">
                            <div class="col-md-3">
                                <h4> Model Name</h4>
                                <argon-input v-model="model.modelname" type="text" />
                            </div>

                            <div class="col-md-10">
                                <h4> Model Description</h4>
                                <argon-input type="text" v-model="model.modeldescriptions" />
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <h4> API key</h4>
                                    <argon-input v-model="model.apikey" type="password" />
                                </div>
                                <div class="col-md-3">
                                    <h4> BaseUrl(Proxy)</h4>
                                    <argon-input v-model="model.baseurl" type="text" />
                                </div>

                                <div class="col-md-3">
                                    <h4> LLM</h4>
                                    <argon-input v-model="model.llmmodel" type="text" />
                                </div>
                            </div>


                            <div class="col-md-10">
                                <h4> SUT Description:</h4>
                                <el-input v-model="model.semanticstype" type="textarea" disabled :autosize="{ minRows: 5 }">
                                </el-input>
                                <!-- <argon-input v-model="model.semanticstype" type="text" /> -->
                            </div>

                            <div class="col-md-10">
                                <h4> SUT Corpus:</h4>
                                <el-input v-model="model.semantics" type="textarea" disabled :autosize="{ minRows: 10 }">
                                </el-input>
                            </div>

                            <div v-if="model.paramsvalues == null" class="col-md-10">
                                <hr class="my-3 horizontal white" />
                                <!-- <h4>There is no corresponding CIT model for the above SUT corpus now......</h4> -->
                                <hr class="my-3 horizontal white" />
                                <ArgonButton color="success" size="lg" full-width @click="extractCITModel"><span
                                        class="ni ni-fat-add ni-lg me-1" />Use LLM to extract CIT
                                    model now!</ArgonButton>
                            </div>

                            <hr class="my-3 horizontal white" />
                            <div v-if="model.paramsvalues" class="col-md-10">
                                <el-divider>
                                    <h6>Following is the CIT Model extracted by LLM</h6>
                                </el-divider>

                                <hr class="my-3 horizontal white" />
                                <h4>Parametes and Values</h4>
                                <el-input v-model="ParamsAndValuesPreivew" type="textarea" disabled
                                    :autosize="{ minRows: 10 }"></el-input>
                                <hr class="my-3 horizontal white" />
                                <h4>Constraints</h4>
                                <el-input v-model="ConsPreview" type="textarea" disabled
                                    :autosize="{ minRows: 10 }"></el-input>



                            </div>

                        </div>


                    </div>
                </div>
            </div>

            <Foot></Foot>
        </div>
    </main>
</template>

<script  setup>
import { useRoute, useRouter } from 'vue-router';
import { onMounted, reactive, ref, computed } from 'vue';
import { request } from '../request';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import ArgonButton from '../ComponentCommon/ArgonButton.vue';
import SideNav from './components/SideNav.vue'
import ArgonBadge from '../ComponentCommon/ArgonBadge.vue';
import ModelsTable from './components/ModelsTable.vue'
import { useUserStore } from '../store/userStore';
import { usePaperInfoStore } from '../store/paperinfoStore';
import { useProjectsStore } from '../store/projectsStore'
import { useModelsStore } from '../store/modelsStore'
import { useCurrentProject } from '../store/currentProject';
import { useLLMmodellingStore } from '../store/LLMmodellingStore.js';
import { useCurrentModel } from '../store/currentModel';
import { ElNotification } from 'element-plus'
import ArgonInput from '../ComponentCommon/ArgonInput.vue';
import toolsInfo from "../ComponentCommon/tools_info.json";
import OpenAI from "openai";







const route = useRoute()
const router = useRouter()
const currentModel = useCurrentModel()
const llmFormStore = useLLMmodellingStore()
const model = reactive({})

const ParamsAndValuesPreivew = ref('')
const ConsPreview = ref('')





const listModelInfoByModelID = async () => {
    try {
        const res = await request({
            url: '/tools/models/info/' + route.query.modelid,
            method: 'POST'
        });

        model.modelid = res.models.modelid
        model.strength = res.models.strength
        model.modelname = res.models.modelname
        model.modeldescriptions = res.models.modeldescriptions
        model.paramsvalues = res.models.paramsvalues
        model.cons = res.models.cons
        model.lastupdatedtime = res.models.lastupdatedtime
        model.createdtime = res.models.createdtime

        model.apikey = res.models.apikey
        model.baseurl = res.models.baseurl
        model.llmmodel = res.models.llmmodel
        model.semanticstype = res.models.semanticstype
        model.semantics = res.models.semantics


        ParamsAndValuesPreivew.value = JSON.stringify(JSON.parse(model.paramsvalues), null, 6)
        ConsPreview.value = JSON.stringify(JSON.parse(model.cons), null, 6)

        if (model.paramsvalues == null) {
            ElNotification({
                title: 'Everything is ready!',
                message: 'Hit the button in the bottom to extract CIT model!',
                type: 'success',
                duration: 0
            })
        }

    } catch (err) {
        console.error(err);
    }
}

const extractCITModel = async () => {
    const openai = new OpenAI(
        {
            apiKey:model.apiKey,
            baseURL:model.baseURL,
            dangerouslyAllowBrowser:true
        }
    );
    const completion = await openai.chat.completions.create({
        messages: [{ role: "system", content: "You are a helpful assistant." }],
        model: "gpt-3.5-turbo",
    });

    console.log(completion.choices[0]);
}


onMounted(async () => {
    await listModelInfoByModelID()

})
</script>