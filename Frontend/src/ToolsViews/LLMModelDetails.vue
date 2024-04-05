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
                                <hr class="my-3 horizontal white" />
                                <ArgonButton color="success" size="lg" full-width @click="extractCITModel"><span
                                        class="ni ni-fat-add ni-lg me-1" />Use LLM to extract CIT
                                    model now!</ArgonButton>
                            </div>

                            <div v-if="startExtractingFlag" class="col-md-10">
                                <hr class="my-3 horizontal white" />
                                <div v-if="stopExtractingFlag == false" style="display:flex;margin-bottom: 3px;">
                                    <div class="spinner-border">
                                    </div>
                                    <h5 style="margin:3px 0px 0px 5px">Extracting CIT model...(if may takes a few minutes,
                                        please wait.)</h5>
                                </div>

                                <div v-if="stopExtractingFlag" style="display:flex;margin-bottom: 3px;">
                                    <ArgonBadge color="danger"
                                        style="display: flex;justify-content: center;align-items: center;">Error
                                    </ArgonBadge>
                                    <h5 style="margin:0px 0px 0px 5px">Extracting CIT model failed...</h5>

                                </div>


                                <h6>Real-time tokens consumption</h6>
                                <ArgonBadge>prompt tokens:</ArgonBadge><span style="margin-left:5px">{{ prompt_tokens
                                }}</span>
                                <ArgonBadge style="margin-left:5px" color="info">completion tokens:</ArgonBadge><span
                                    style="margin-left:5px">{{ completion_tokens }}</span>
                                <ArgonBadge style="margin-left:5px" color="warning">Total tokens:</ArgonBadge><span
                                    style="margin-left:5px">{{ prompt_tokens + completion_tokens }}</span>

                                <hr class="my-3 horizontal white" />
                                <el-input v-model="ProcessPreview" type="textarea" disabled
                                    :autosize="{ minRows: 10 }"></el-input>
                                <hr class="my-3 horizontal white" />

                                <div v-if="inExtractingFlag">
                                    <h4> Extracted Parameters and Values:</h4>
                                    <el-input v-model="ParametersExtractedPreview" type="textarea" disabled
                                        :autosize="{ minRows: 10 }"
                                        :placeholder="inExtractingFlag ? 'Parameters and Values are extracting now, the results of extraction will be displayed here, please wait for them......' : Final_PandV_List_extracted"></el-input>
                                    <hr class="my-3 horizontal white" />
                                    <h4> Extracted Constraints:</h4>
                                    <el-input v-model="ConsExtractedPreview" type="textarea" disabled
                                        :autosize="{ minRows: 10 }"
                                        :placeholder="inExtractingFlag ? 'Constraints are extracting now, the results of extraction will be displayed here, please wait for it......' : Cons_extracted"></el-input>
                                    <hr class="my-3 horizontal white" />

                                    <ArgonButton color="primary" size="lg" @click="SaveModel"><span
                                            class="ni ni-fat-add ni-lg me-1" />Save Extrated Model</ArgonButton>

                                    <ArgonButton style="margin-left:5px" color="warning" size="lg" @click="extractCITModel">
                                        <span class="ni ni-fat-add ni-lg me-1" />Regenerate
                                    </ArgonButton>
                                </div>
                            </div>


                            <div v-if="model.paramsvalues" class="col-md-10">
                                <hr class="my-3 horizontal white" />
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
import Foot from '../CustomizedComponents/Foot.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import ArgonButton from '../CustomizedComponents/ArgonButton.vue';
import SideNav from './components/SideNav.vue'
import ArgonBadge from '../CustomizedComponents/ArgonBadge.vue';
import ModelsTable from './components/ModelsTable.vue'
import pinia from '../store/store'
import { useUserStore } from '../store/userStore';
import { usePaperInfoStore } from '../store/paperinfoStore';
import { useProjectsStore } from '../store/projectsStore'
import { useModelsStore } from '../store/modelsStore'
import { useCurrentProject } from '../store/currentProject';
import { useLLMmodellingStore } from '../store/LLMmodellingStore';
import { useCurrentModel } from '../store/currentModel';
import { ElNotification } from 'element-plus'
import ArgonInput from '../CustomizedComponents/ArgonInput.vue';
import toolsInfo from "../CustomizedComponents/tools_info.json";
import {
    systemContent,
    prompt_Cons_4_ConvertToJson,
    prompt_Cons_3,
    prompt_Cons_2,
    prompt_Cons_1_Answer,
    prompt_Cons_1,
    prompt_V_ConvertToJson_UserInput_NoBoundary,
    prompt_V_UserInput_NoBoundary,
    prompt_V_ConvertToJson_UserInput_YesBoundary,
    prompt_V_UserInput_YesBoundary,
    prompt_V_ConvertToJson_UserInput_Boundary,
    prompt_V_UserInput_Boundary,
    prompt_V_ConvertToJson_Enumerated_NoBoundary,
    prompt_V_Enumerated_NoBoundary,
    prompt_V_ConvertToJson_Enumerated_YesBoundary,
    prompt_V_Enumerated_YesBoundary,
    prompt_V_Enumerated_Boundary,
    prompt_P,
    prompt_P_ConvertToJson,
    prompt_P_ValueType,
    prompt_P_ValueType_ConvertToJson,
    prompt_V_Enumerated_Specified,
    prompt_V_ConvertToJson_Enumerated,
    prompt_V_Enumerated_ListAllSpecifyValues,
    prompt_V_ConvertToJson_Enumerated_ListAllSpecifyValues,
    prompt_V_ConvertToJson_Enumerated_Boundary
} from './Prompts'
import OpenAI from "openai";


const route = useRoute()
const router = useRouter()
const currentModel = useCurrentModel(pinia)
const llmFormStore = useLLMmodellingStore(pinia)
const model = reactive({})

const ParamsAndValuesPreivew = ref('')
const ConsPreview = ref('')
const ParametersExtractedPreview = ref('')
const ConsExtractedPreview = ref('')
const ProcessPreview = ref('')

const startExtractingFlag = ref(false)
const inExtractingFlag = ref(false)
const endExtractingFlag = ref(false)
const stopExtractingFlag = ref(false)

const Final_PandV_List_extracted = reactive()
const Cons_extracted = reactive()






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
const prompt_tokens = ref(0)
const completion_tokens = ref(0)

const extractCITModel = async () => {
    ProcessPreview.value += 'All extracting process will be displayed here, Please waiting a moment for the API calling result..............\n'
    ProcessPreview.value += '----------------------------------------Extracting Parameters and Values----------------------------------------\n'
    startExtractingFlag.value = true
    inExtractingFlag.value = true
    stopExtractingFlag.value = false
    const openai = new OpenAI(
        {
            apiKey: model.apikey,
            baseURL: model.baseurl,
            dangerouslyAllowBrowser: true
        }
    );
    try {
        let messages_P = [
            { "role": "system", "content": systemContent() },
            { "role": "user", "content": prompt_P(model.semanticstype, model.semantics) }
        ]

        let response = await openai.chat.completions.create({
            messages: messages_P,
            model: model.llmmodel,
            temperature: 0.7
        });
        prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
        completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

        ProcessPreview.value += `\n首次生成P集合:\n${response.choices[0].message.content}\n`;

        messages_P.push({ "role": "user", "content": "Check your response again and identify the parameters that may be missing.(Note:parameters are the factors like user inputs/attributes/conditions/choices/button and etc." })
        response = await openai.chat.completions.create(
            {
                temperature: 0.7,
                messages: messages_P,
                model: model.llmmodel,
            }
        )
        prompt_tokens.value += response.usage.prompt_tokens
        completion_tokens.value += response.usage.completion_tokens

        console.log("二次生成P集合:")
        console.log(response.choices[0].message.content)
        console.log("\n token使用情况", response.usage)
        console.log("api调用结束原因", response.choices[0].finish_reason)

        ProcessPreview.value += `\n二次生成P集合:\n${response.choices[0].message.content}\n`;

        messages_P.push({ "role": "assistant", "content": response.choices[0].message.content })
        messages_P.push({ "role": "user", "content": "List all the parameters we have now." })


        response = await openai.chat.completions.create(
            {
                temperature: 0.7,
                messages: messages_P,
                model: model.llmmodel
            }
        )
        prompt_tokens.value += response.usage.prompt_tokens
        completion_tokens.value += response.usage.completion_tokens

        console.log("两次识别得到的P集合:")
        console.log(response.choices[0].message.content)
        console.log("\n token使用情况", response.usage)
        console.log("api调用结束原因", response.choices[0].finish_reason)

        messages_P.push({ "role": "assistant", "content": response.choices[0].message.content })

        ProcessPreview.value += `\n两次识别得到的P集合:\n${response.choices[0].message.content}\n}`;

        // 得到最后的JSON object
        messages_P.push({ "role": "user", "content": prompt_P_ConvertToJson() })

        response = await openai.chat.completions.create(
            {
                temperature: 0.7,
                messages: messages_P,
                model: model.llmmodel,
                response_format: { "type": "json_object" },
            }

        )

        prompt_tokens.value += response.usage.prompt_tokens
        completion_tokens.value += response.usage.completion_tokens

        console.log("最后得到的P集合,Json Object")
        console.log(response.choices[0].message.content)
        let parameters_list_NoValueType = JSON.parse(response.choices[0].message.content)
        console.log("parameters_list_NoValueType", parameters_list_NoValueType)
        console.log("\n token使用情况", response.usage)

        console.log("api调用结束原因", response.choices[0].finish_reason)

        //  Gpt可能会不按照`prompt_P_ConvertToJson`里指定的格式回复，而是生成一个新key叫`parameters`，这个parameters key对应的value才是想要的值，做处理
        if (parameters_list_NoValueType.hasOwnProperty('parameters')) {
            console.log("Gpt抽风了，处理......,处理后的参数集合是");
            let temp = parameters_list_NoValueType.parameters;
            parameters_list_NoValueType = temp;
        }

        console.log(`共识别出 ${parameters_list_NoValueType.length} 个参数，如下：`);

        //  对list中的每一个param 获得它的valuetype
        let parameters_list_temp = []
        let parameters_list = []
        for (const param of parameters_list_NoValueType) {
            let messages_ValueType = [
                { "role": "system", "content": systemContent() },
                { "role": "user", "content": prompt_P_ValueType(model.semanticstype, model.semantics, param.param, param.reasons) }
            ]
            response = await openai.chat.completions.create(
                {
                    temperature: 0.7,
                    messages: messages_ValueType,
                    model: model.llmmodel
                }
            )
            prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
            completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

            console.log("---------------对参数", param.param, "识别出的valuetype是---------------")
            console.log(response.choices[0].message.content)
            console.log("\n token使用情况", response.usage)
            console.log("api调用结束原因", response.choices[0].finish_reason)

            ProcessPreview.value += `\n"---------------对参数", ${param.param}, "识别出的valuetype是---------------"\n${response.choices[0].message.content}\n}`;

            messages_ValueType.push({ "role": "assistant", "content": response.choices[0].message.content })
            messages_ValueType.push({ "role": "user", "content": prompt_P_ValueType_ConvertToJson(param.param, param.reasons) })

            response = await openai.chat.completions.create(
                {
                    temperature: 0.7,
                    messages: messages_ValueType,
                    model: model.llmmodel,
                    // 要求回复Json格式
                    response_format: { "type": "json_object" },
                }
            )
            prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
            completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

            console.log("---------------对参数", param.param, "得到的回复是---------------")
            console.log(response.choices[0].message.content)
            console.log("\n token使用情况", response.usage)
            console.log("api调用结束原因", response.choices[0].finish_reason)

            parameters_list.push(JSON.parse(response.choices[0].message.content))

            ProcessPreview.value += `\n"---------------对参数", ${param.param}, "识别出的valuetype是---------------"\n${response.choices[0].message.content}\n`;

        }

        console.log("共识别出", len(parameters_list), "个参数，如下：")
        ProcessPreview.value += `\n"共识别出", ${len(parameters_list)}, "个参数，如下："\n${response.choices[0].message.content}\n`;
        for (const param of parameters_list) {
            console.log(param)
            console.log(param.param, param.valuetype)
            ProcessPreview.value += `\n${param}\n`
            ProcessPreview.value += `\n${param.param}, ${param.valuetype}\n`
        }

        // 确定Boolean型参数的取值，直接就是True False
        let PandV_List_Boolean = []
        for (const param of parameters_list) {
            // 先识别参数取值类型为Boolean的参数的取值
            if (param.valuetype.toLowerCase().includes("boolean")) {
                PandV_List_Boolean.push({
                    param: param.param,
                    valuetype: param.valuetype,
                    values: [true, false],
                    param_reasons: param.param_reasons,
                    valuetype_reasons: param.valuetype_reasons
                });
            }
        }

        console.log("\n得到所有的Boolean类型的 P V\n")
        for (const param of PandV_List_Boolean) {
            console.log(param)
            ProcessPreview.value += `\n${param}\n`
        }


        // 再确定Enumerated 型参数的取值
        let PandV_List_Enumerated = []
        for (const param of parameters_list) {
            if (param.valuetype.toLowerCase().includes("enumerate")) {
                let messages_Enumerated = [
                    { "role": "system", "content": systemContent() },
                    {
                        "role": "user",
                        "content": prompt_V_Enumerated_Specified(model.semanticstype, model.semantics, param.param, param.param_reasons, param.valuetype_reasons)
                    }
                ];

                response = await openai.chat.completions.create({
                    temperature: 0.7,
                    messages: messages_Enumerated,
                    model: model.llmmodel
                });

                prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                messages_Enumerated.push({ "role": "assistant", "content": response.choices[0].message.content });
                messages_Enumerated.push({ "role": "user", "content": prompt_V_ConvertToJson_Enumerated(model.semanticstype) });

                response = await openai.chat.completions.create({
                    temperature: 0.7,
                    messages: messages_Enumerated,
                    model: model.llmmodel,
                    response_format: { "type": "json_object" }
                });

                prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                console.log(`对于enumerated类型的参数 ${param.param}，它的参数取值是不是全部在corpus中specified的回答是`);
                console.log(response.choices[0].message.content);

                ProcessPreview.value += `\n对于enumerated类型的参数 ${param.param}，它的参数取值是不是全部在corpus中specified的回答是\n`
                ProcessPreview.value += `\nresponse.choices[0].message.content\n`

                messages_Enumerated.push({ "role": "assistant", "content": response.choices[0].message.content });

                let answerJsonForAllValues = JSON.parse(response.choices[0].message.content);

                // 如果回答是Yes，该enumerated参数的参数取值都在corpus出现了

                if (answerJsonForAllValues.answer.toLowerCase().includes('yes')) {
                    console.log(`${param.param} ${param.valuetype} 的参数取值全部出现在了corpus中`);
                    ProcessPreview.value += `\n${param.param} ${param.valuetype} 的参数取值全部出现在了corpus中\n`
                    messages_Enumerated.push({ "role": "user", "content": prompt_V_Enumerated_ListAllSpecifyValues(model.semanticstype, param.param) });

                    response = await openai.chat.completions.create({
                        temperature: 0.7,
                        messages: messages_Enumerated,
                        model: model.llmmodel
                    });

                    console.log(`对于enumerated类型的参数 ${param.param}，其所有参数取值都specified了，这些参数取值是：`);
                    ProcessPreview.value += `\n对于enumerated类型的参数 ${param.param}，其所有参数取值都specified了，这些参数取值是：\n`

                    console.log(response.choices[0].message.content);
                    ProcessPreview.value += `${response.choices[0].message.content}`

                    messages_Enumerated.push({ "role": "assistant", "content": response.choices[0].message.content });
                    console.log("\n token使用情况", response.usage);
                    prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                    completion_tokens.value = completion_tokens.value + response.usage.completion_tokens
                    console.log("\n api调用结束原因", response.choices[0].finish_reason);

                    messages_Enumerated.push({
                        "role": "user",
                        "content": prompt_V_ConvertToJson_Enumerated_ListAllSpecifyValues(model.semanticstype, param.param, param.param_reasons, param.valuetype, param.valuetype_reasons)
                    });

                    response = await openai.chat.completions.create({
                        temperature: 0.7,
                        messages: messages_Enumerated,
                        model: model.llmmodel,
                        response_format: { "type": "json_object" }
                    });


                    let EnumeratedJsonSpecified = JSON.parse(response.choices[0].message.content);
                    console.log(`对于enumerated类型的参数 ${param.param}，其参数取值在corpus中都specified了，得到的最终的Json是`, EnumeratedJsonSpecified);
                    ProcessPreview.value += `对于enumerated类型的参数 ${param.param}，其参数取值在corpus中都specified了，得到的最终的Json是, ${EnumeratedJsonSpecified}\n`
                    PandV_List_Enumerated.push(EnumeratedJsonSpecified);

                    console.log("\n token使用情况", response2.usage);
                    prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                    completion_tokens.value = completion_tokens.value + response.usage.completion_tokens
                    console.log("\n api调用结束原因", response2.choices[0].finish_reason);
                }

                // 如果回答是No，该enumerated参数的参数取值没有都在corpus出现
                if (answerJsonForAllValues.answer.toLowerCase().includes('no')) {
                    console.log(`${param.param} ${param.valuetype} 的参数取值没有全部specify在corpus中，判断其是否有boundary`);
                    ProcessPreview.value += `\n${param.param} ${param.valuetype} 的参数取值没有全部specify在corpus中，判断其是否有boundary\n`

                    // 判断有没有boundary
                    messages_Enumerated.push({ "role": "user", "content": prompt_V_Enumerated_Boundary(model.semanticstype, param.param) });

                    response = await openai.chat.completions.create({
                        temperature: 0.7,
                        messages: messages_Enumerated,
                        model: model.llmmodel
                    });

                    messages_Enumerated.push({ "role": "assistant", "content": response.choices[0].message.content });

                    messages_Enumerated.push({ "role": "user", "content": prompt_V_ConvertToJson_Enumerated_Boundary(param.param) });
                    prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                    completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                    response = await openai.chat.completions.create({
                        temperature: 0.7,
                        messages: messages_Enumerated,
                        model: model.llmmodel,
                        response_format: { "type": "json_object" }
                    });

                    messages_Enumerated.push({ "role": "assistant", "content": response.choices[0].message.content });
                    console.log(`对于enumerated类型的参数 ${param.param}，有没有boundary的回答是`);
                    ProcessPreview.value += `\n对于enumerated类型的参数 ${param.param}，有没有boundary的回答是\n`

                    let answerJsonForBoundary = JSON.parse(response.choices[0].message.content);
                    ProcessPreview.value += `\n${JSON.parse(response.choices[0].message.content)}\n`

                    console.log("\n token使用情况", response.usage);

                    prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                    completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                    console.log("\n api调用结束原因", response.choices[0].finish_reason);

                    // 有boundary 划分为 empty，boundary？，in boundary，out boundary，然后从中取值，取值取empty、Boundary？、in b close、o b close、in b random、out b random，总计6（5）个取值
                    if (answerJsonForBoundary.answer.toLowerCase().includes('yes')) {
                        console.log("有boundary");
                        ProcessPreview.value += `\n有boundary\n`
                        messages_Enumerated.push({ "role": "user", "content": prompt_V_Enumerated_YesBoundary(model.semanticstype, param.param) });

                        // Assuming an asynchronous function client.chat.completions.create is used
                        response = await openai.chat.completions.create({
                            temperature: 0.7,
                            messages: messages_Enumerated,
                            model: model.llmmodel
                        });

                        console.log(`对于enumerated类型的参数有boundary ${param.param}，给出boundary本身，比boundary小但接近，比boundary大但接近，比boundary小，比boundary大，共计5种值`);
                        ProcessPreview.value += `\n对于enumerated类型的参数有boundary ${param.param}，给出boundary本身，比boundary小但接近，比boundary大但接近，比boundary小，比boundary大，共计5种值\n`
                        console.log(response.choices[0].message.content);
                        ProcessPreview.value += `\n${response.choices[0].message.content}\n`

                        messages_Enumerated.push({ "role": "assistant", "content": response.choices[0].message.content });
                        console.log("\n token使用情况", response.usage);

                        prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                        completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                        console.log("\n api调用结束原因", response.choices[0].finish_reason);

                        messages_Enumerated.push({
                            "role": "user",
                            "content": prompt_V_ConvertToJson_Enumerated_YesBoundary(param.param, param.param_reasons, param.valuetype, param.valuetype_reasons)
                        });

                        prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                        completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                        response = await openai.chat.completions.create({
                            temperature: 0.7,
                            messages: messages_Enumerated,
                            model: model.llmmodel,
                            response_format: { "type": "json_object" }
                        });
                        prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                        completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                        let EnumeratedJsonYesBoundary = JSON.parse(response.choices[0].message.content);
                        EnumeratedJsonYesBoundary.Empty = 'Empty';
                        console.log(`对于enumerated类型的参数有boundary ${param.param}，最终的Json是`);
                        ProcessPreview.value += `\n对于enumerated类型的参数有boundary ${param.param}，最终的Json是\n`

                        console.log(EnumeratedJsonYesBoundary);
                        ProcessPreview.value += `\nEnumeratedJsonYesBoundary\n`

                        PandV_List_Enumerated.push(EnumeratedJsonYesBoundary);
                    }

                    //  如果没有明显的boundary，则分为三类，empty、valid、invalid。取值取empty、random valid，random invalid总计3个类。

                    if (answerJsonForBoundary.answer.toLowerCase().includes('no')) {
                        console.log(`${param.param} ${param.valuetype} 的参数取值没有全部specify在corpus中，判断其是否有boundary`);
                        ProcessPreview.value += `\n${param.param} ${param.valuetype} 的参数取值没有全部specify在corpus中，判断其是否有boundary\n`

                        // 判断有没有boundary
                        messages_Enumerated.push({ "role": "user", "content": prompt_V_Enumerated_Boundary(model.semanticstype, param.param) });

                        // Assuming an asynchronous function client.chat.completions.create is used
                        response = await openai.chat.completions.create({
                            temperature: 0.7,
                            messages: messages_Enumerated,
                            model: model.llmmodel
                        });

                        messages_Enumerated.push({ "role": "assistant", "content": response.choices[0].message.content });
                        prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                        completion_tokens.value = completion_tokens.value + response.usage.completion_tokens
                        messages_Enumerated.push({ "role": "user", "content": prompt_V_ConvertToJson_Enumerated_Boundary(param.param) });

                        // Assuming an asynchronous function client.chat.completions.create is used
                        response = await openai.chat.completions.create({
                            temperature: 0.7,
                            messages: messages_Enumerated,
                            model: model.llmmodel,
                            response_format: { "type": "json_object" }
                        });

                        messages_Enumerated.push({ "role": "assistant", "content": response.choices[0].message.content });
                        console.log(`对于enumerated类型的参数 ${param.param} 有没有boundary的回答是`);
                        ProcessPreview.value += `\n对于enumerated类型的参数 ${param.param} 有没有boundary的回答是\n`

                        let answerJsonForBoundary = JSON.parse(response.choices[0].message.content);
                        console.log("\n token使用情况", response.usage);
                        prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                        completion_tokens.value = completion_tokens.value + response.usage.completion_tokens
                        console.log("\n api调用结束原因", response.choices[0].finish_reason);

                        // 有boundary 划分为 empty，boundary？，in boundary，out boundary，然后从中取值，取值取empty、Boundary？、in b close、o b close、in b random、out b random，总计6（5）个取值
                        if (answerJsonForBoundary.answer.toLowerCase().includes('yes')) {
                            console.log("有boundary");
                            ProcessPreview.value += `\n有boundary\n`

                            messages_Enumerated.push({ "role": "user", "content": prompt_V_Enumerated_YesBoundary(model.semanticstype, param.param) });

                            // Assuming an asynchronous function client.chat.completions.create is used
                            response = await openai.chat.completions.create({
                                temperature: 0.7,
                                messages: messages_Enumerated,
                                model: model.llmmodel
                            });

                            console.log(`对于enumerated类型的参数有boundary ${param.param}，给出boundary本身，比boundary小但接近，比boundary大但接近，比boundary小，比boundary大，共计5种值`);
                            ProcessPreview.value += `\n对于enumerated类型的参数有boundary ${param.param}，给出boundary本身，比boundary小但接近，比boundary大但接近，比boundary小，比boundary大，共计5种值\n`

                            console.log(response.choices[0].message.content);
                            ProcessPreview.value += `\nresponse.choices[0].message.content\n`
                            messages_Enumerated.push({ "role": "assistant", "content": response.choices[0].message.content });
                            console.log("\n token使用情况", response.usage);
                            prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                            completion_tokens.value = completion_tokens.value + response.usage.completion_tokens
                            console.log("\n api调用结束原因", response.choices[0].finish_reason);

                            messages_Enumerated.push({
                                "role": "user",
                                "content": prompt_V_ConvertToJson_Enumerated_YesBoundary.format(
                                    param.param,
                                    param.param_reasons,
                                    param.valuetype,
                                    param.valuetype_reasons
                                )
                            });

                            // Assuming an asynchronous function client.chat.completions.create is used
                            response = await openai.chat.completions.create({
                                temperature: 0.7,
                                messages: messages_Enumerated,
                                model: model.llmmodel,
                                response_format: { "type": "json_object" }
                            });

                            console.log("\n token使用情况", response.usage);
                            prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                            completion_tokens.value = completion_tokens.value + response.usage.completion_tokens
                            console.log("\n api调用结束原因", response.choices[0].finish_reason);
                            let EnumeratedJsonYesBoundary = JSON.parse(response.choices[0].message.content);
                            EnumeratedJsonYesBoundary.Empty = 'Empty';
                            console.log(`对于enumerated类型的参数有boundary ${param.param} 最终的Json是`);
                            ProcessPreview.value += `\n对于enumerated类型的参数有boundary ${param.param} 最终的Json是\n`
                            ProcessPreview.value += `\nEnumeratedJsonYesBoundary\n`
                            console.log(EnumeratedJsonYesBoundary);
                            PandV_List_Enumerated.push(EnumeratedJsonYesBoundary);
                        }

                        // 如果没有明显的boundary，则分为三类，empty、valid、invalid。取值取empty、random valid，random invalid总计3个类。
                        if (answerJsonForBoundary.answer.toLowerCase().includes('no')) {
                            console.log("没有boundary，划为empty valid invalid三类");
                            ProcessPreview.value += `\n没有boundary，划为empty valid invalid三类\n`

                            messages_Enumerated.push({ "role": "user", "content": prompt_V_Enumerated_NoBoundary(model.semanticstype, param.param) });

                            // Assuming an asynchronous function client.chat.completions.create is used
                            response = await openai.chat.completions.create({
                                temperature: 0.7,
                                messages: messages_Enumerated,
                                model: model.llmmodel
                            });

                            prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                            completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                            console.log(`对于enumerated类型的参数有没有boundary ${param.param}，给出一些valid和invalid取值是`);
                            ProcessPreview.value += `\n对于enumerated类型的参数有没有boundary ${param.param}，给出一些valid和invalid取值是\n`

                            console.log(response.choices[0].message.content);
                            ProcessPreview.value += `\n${response.choices[0].message.content}\n`

                            messages_Enumerated.push({ "role": "assistant", "content": response.choices[0].message.content });
                            console.log("\n token使用情况", response.usage);

                            console.log("\n api调用结束原因", response.choices[0].finish_reason);

                            messages_Enumerated.push({
                                "role": "user",
                                "content": prompt_V_ConvertToJson_Enumerated_NoBoundary.format(
                                    param.param,
                                    param.param_reasons,
                                    param.valuetype,
                                    param.valuetype_reasons
                                )
                            });

                            // Assuming an asynchronous function client.chat.completions.create is used
                            response = await openai.chat.completions.create({
                                temperature: 0.7,
                                messages: messages_Enumerated,
                                model: model.llmmodel,
                                response_format: { "type": "json_object" }
                            });

                            prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                            completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                            console.log("\n token使用情况", response.usage);

                            console.log("\n api调用结束原因", response.choices[0].finish_reason);
                            let EnumeratedJsonNoBoundary = JSON.parse(response.choices[0].message.content);
                            EnumeratedJsonNoBoundary.Empty = 'Empty';
                            console.log(`对于enumerated类型的参数没有boundary",${param.param},"最终的Json是`)
                            ProcessPreview.value += `\n对于enumerated类型的参数没有boundary",${param.param},"最终的Json是\n`
                            console.log(EnumeratedJsonNoBoundary)
                            ProcessPreview.value += `\n${EnumeratedJsonNoBoundary}\n`
                            PandV_List_Enumerated.push(EnumeratedJsonNoBoundary)
                        }




                    }
                }
            }
        }

        console.log("得到所有的Enumerated类型的 P V");
        PandV_List_Enumerated.forEach(param => console.log(param));
        ProcessPreview.value += `\n${PandV_List_Enumerated}\n`

        //  再确定UserInput类型的参数的取值
        let PandV_List_UserInput = []
        for (const param of parameters_list) {
            if (param.valuetype.toLowerCase().includes("userinput")) {
                let messages_UserInput = [
                    { "role": "system", "content": systemContent() },
                    // # 判断有没有boundary
                    { "role": "user", "content": prompt_V_UserInput_Boundary(model.semanticstype, model.semantics, param.param, param.valuetype) }
                ]

                response = await openai.chat.completions.create({
                    temperature: 0.7,
                    messages: messages_UserInput,
                    model: model.llmmodel
                });
                console.log("对于", param.valuetype, "类型的参数", param.param, "有没有boundary的回答是")
                ProcessPreview.value += `\n对于", ${param.valuetype}, "类型的参数",${param.param}, "有没有boundary的回答是\n`

                prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                messages_UserInput.push({ "role": "assistant", "content": response.choices[0].message.content })
                messages_UserInput.push({ "role": "user", "content": prompt_V_ConvertToJson_UserInput_Boundary(param.param) })

                response = await openai.chat.completions.create({
                    temperature: 0.7,
                    messages: messages_UserInput,
                    model: model.llmmodel
                });

                prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                console.log(response.choices[0].message.content)
                messages_UserInput.push({ "role": "assistant", "content": response.choices[0].message.content })
                let answerJsonForBoundary = JSON.parse(response.choices[0].message.content)
                console.log("\n token使用情况", response.usage)
                console.log("\n api调用结束原因", response.choices[0].finish_reason)

                //  有boundary 划分为 empty，boundary？，in boundary，out boundary，然后从中取值，取值取empty、Boundary？、in b close、o b close、in b random、out b random，总计6（5）个取值
                if (answerJsonForBoundary.answer.toLowerCase().includes('yes')) {
                    console.log("有boundary");
                    ProcessPreview.value += `\n有boundary\n`

                    messages_UserInput.push({ "role": "user", "content": prompt_V_UserInput_YesBoundary(model.semanticstype, param.param, param.valuetype) });

                    // Assuming an asynchronous function client.chat.completions.create is used
                    response = await openai.chat.completions.create({
                        temperature: 0.7,
                        messages: messages_UserInput,
                        model: model.llmmodel
                    });

                    prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                    completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                    console.log(`对于 ${param.valuetype} 类型的参数有boundary ${param.param}，给出boundary本身，比boundary小但接近，比boundary大但接近，比boundary小，比boundary大，共计5种值`);
                    ProcessPreview.value += `\n对于 ${param.valuetype} 类型的参数有boundary ${param.param}，给出boundary本身，比boundary小但接近，比boundary大但接近，比boundary小，比boundary大，共计5种值\n`
                    console.log();
                    ProcessPreview.value += `\n ${response.choices[0].message.content}\n`

                    messages_UserInput.push({ "role": "assistant", "content": response.choices[0].message.content });
                    console.log("\n token使用情况", response.usage);

                    console.log("\n api调用结束原因", response.choices[0].finish_reason);

                    messages_UserInput.push({
                        "role": "user",
                        "content": prompt_V_ConvertToJson_UserInput_YesBoundary.format(
                            param.param,
                            param.param_reasons,
                            param.valuetype,
                            param.valuetype_reasons
                        )
                    });

                    // Assuming an asynchronous function client.chat.completions.create is used
                    response = await openai.chat.completions.create({
                        temperature: 0.7,
                        messages: messages_UserInput,
                        model: model.llmmodel,
                        response_format: { "type": "json_object" }
                    });

                    prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                    completion_tokens.value = completion_tokens.value + response.usage.completion_tokens
                    console.log("\n token使用情况", response.usage);
                    console.log("\n api调用结束原因", response.choices[0].finish_reason);
                    let UserInputJsonYesBoundary = JSON.parse(response.choices[0].message.content);
                    UserInputJsonYesBoundary.Empty = 'Empty';
                    console.log(`对于 ${param.valuetype} 类型的参数有boundary ${param.param} 最终的Json是`);
                    ProcessPreview.value += `\n 对于 ${param.valuetype} 类型的参数有boundary ${param.param} 最终的Json是\n`
                    console.log(UserInputJsonYesBoundary);
                    ProcessPreview.value += `\n ${UserInputJsonYesBoundary}\n`
                    PandV_List_UserInput.push(UserInputJsonYesBoundary);
                }


                // 如果没有明显的boundary，则分为三类，empty、valid、invalid。取值取empty、random valid，random invalid总计3个类。
                if (answerJsonForBoundary.answer.toLowerCase().includes('no')) {
                    console.log("没有boundary，划为empty valid invalid三类");
                    ProcessPreview.value += `\n没有boundary，划为empty valid invalid三类\n`

                    messages_UserInput.push({ "role": "user", "content": prompt_V_UserInput_NoBoundary(model.semanticstype, param.param, param.valuetype) });


                    response = await openai.chat.completions.create({
                        temperature: 0.7,
                        messages: messages_UserInput,
                        model: model.llmmodel
                    });

                    prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                    completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

                    console.log(`对于 ${param.valuetype} 类型的参数有没有boundary ${param.param}，给出一些valid和invalid取值是`);
                    ProcessPreview.value += `\n对于 ${param.valuetype} 类型的参数有没有boundary ${param.param}，给出一些valid和invalid取值是\n`

                    console.log(response.choices[0].message.content);
                    ProcessPreview.value += `\nresponse.choices[0].message.content\n`

                    messages_UserInput.push({ "role": "assistant", "content": response.choices[0].message.content });
                    console.log("\n token使用情况", response.usage);

                    console.log("\n api调用结束原因", response.choices[0].finish_reason);

                    messages_UserInput.push({
                        "role": "user",
                        "content": prompt_V_ConvertToJson_UserInput_NoBoundary.format(
                            param.param,
                            param.param_reasons,
                            param.valuetype,
                            param.valuetype_reasons
                        )
                    });

                    response = await openai.chat.completions.create({
                        temperature: 0.7,
                        messages: messages_UserInput,
                        model: model.llmmodel
                    });

                    console.log(`对于 ${param.valuetype} 类型的参数没有boundary ${param.param} 最终的Json是`);
                    ProcessPreview.value += `\n对于 ${param.valuetype} 类型的参数没有boundary ${param.param} 最终的Json是\n`

                    let UserInputJsonNoBoundary = JSON.parse(response.choices[0].message.content);
                    UserInputJsonNoBoundary.Empty = 'Empty';
                    console.log(UserInputJsonNoBoundary);
                    ProcessPreview.value += `\n ${UserInputJsonNoBoundary} \n`

                    console.log("\n token使用情况", response.usage);
                    prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
                    completion_tokens.value = completion_tokens.value + response.usage.completion_tokens
                    console.log("\n api调用结束原因", response2.choices[0].finish_reason);
                    PandV_List_UserInput.push(UserInputJsonNoBoundary);
                }

            }
        }

        console.log("得到所有的UserInput类型的 P V")
        ProcessPreview.value += `\n得到所有的UserInput类型的 P V\n`
        for (const param of PandV_List_UserInput) {
            console.log(param)
            ProcessPreview.value += `\nparam\n`
        }


        console.log("总的参数数量是", parameters_list.length, "Boolean类型的参数数量是", PandV_List_Boolean.length, "Enumerated类型的参数数量是", PandV_List_Enumerated.length, "UserInput类型的参数数量是", PandV_List_UserInput.length)
        ProcessPreview.value += `\n 总的参数数量是", ${parameters_list.length}, "Boolean类型的参数数量是", ${PandV_List_Boolean.length}, "Enumerated类型的参数数量是", ${PandV_List_Enumerated.length}, "UserInput类型的参数数量是", ${PandV_List_UserInput.length} \n`

        let Final_PandV_List = [];
        // 整合 Boolean
        console.log("----------------------------------------------------整合Boolean----------------------------------------------------------------");
        ProcessPreview.value += `\n----------------------------------------------------整合Boolean---------------------------------------------------------------- \n`
        for (const boolparam of PandV_List_Boolean) {
            console.log(boolparam);
            ProcessPreview.value += `\n ${boolparam} \n`
            Final_PandV_List.push({ 'param': boolparam.param, 'valuetype': boolparam.valuetype, 'values': boolparam.values });
        }

        // 整合 Enumerated
        console.log("----------------------------------------------------整合Enumerated----------------------------------------------------------------");
        ProcessPreview.value += `\n----------------------------------------------------整合Enumerated---------------------------------------------------------------- \n`
        for (const enumParam of PandV_List_Enumerated) {
            console.log(enumParam);
            ProcessPreview.value += `\n ${enumParam} \n`

            // All values specified
            if (enumParam.values) {
                Final_PandV_List.push({ 'param': enumParam.param, 'valuetype': enumParam.valuetype, 'values': enumParam.values });
            }

            // Yes Boundary
            else if (enumParam.boundary_values) {
                const values_merged = [
                    ...enumParam.boundary_values,
                    ...enumParam.slightly_below_boundary_values,
                    ...enumParam.slightly_above_boundary_values,
                    ...enumParam.greater_than_boundary_values,
                    ...enumParam.less_than_boundary_values,
                    ...enumParam.Empty
                ];
                Final_PandV_List.push({ 'param': enumParam.param, 'valuetype': enumParam.valuetype, 'values': values_merged });
            }

            // No Boundary
            else if (enumParam.valid_values) {
                const values_merged = [
                    ...enumParam.valid_values,
                    ...enumParam.invalid_values,
                    ...enumParam.Empty
                ];
                Final_PandV_List.push({ 'param': enumParam.param, 'valuetype': enumParam.valuetype, 'values': values_merged });
            }
        }

        console.log("-------------------------------------------------------整合UserInput-------------------------------------------------------------");
        ProcessPreview.value += `\n -------------------------------------------------------整合UserInput------------------------------------------------------------- \n`

        for (const userInputParam of PandV_List_UserInput) {
            console.log(userInputParam);
            ProcessPreview.value += `\n ${userInputParam} \n`

            // Yes Boundary
            if (userInputParam.boundary_values) {
                const values_merged = [
                    ...userInputParam.boundary_values,
                    ...userInputParam.slightly_below_boundary_values,
                    ...userInputParam.slightly_above_boundary_values,
                    ...userInputParam.greater_than_boundary_values,
                    ...userInputParam.less_than_boundary_values,
                    ...userInputParam.Empty
                ];
                Final_PandV_List.push({ 'param': userInputParam.param, 'valuetype': userInputParam.valuetype, 'values': values_merged });
            }

            // No Boundary
            else if (userInputParam.valid_values) {
                const values_merged = [
                    ...userInputParam.valid_values,
                    ...userInputParam.invalid_values,
                    ...userInputParam.Empty
                ];
                Final_PandV_List.push({ 'param': userInputParam.param, 'valuetype': userInputParam.valuetype, 'values': values_merged });
            }
        }

        console.log("-------------------------------------------------------整合完毕,得到P V-------------------------------------------------------------");
        ProcessPreview.value += `\n -------------------------------------------------------整合完毕,得到P V------------------------------------------------------------- \n`

        console.log("整合后的Param数量是", Final_PandV_List.length);
        ProcessPreview.value += `\n整合后的Param数量是", ${Final_PandV_List.length} \n`

        for (const param of Final_PandV_List) {
            console.log(param);
            ProcessPreview.value += `\n ${param}\n`

        }


        // 提取约束
        console.log("-------------------------------------------------------识别约束-------------------------------------------------------------")
        ProcessPreview.value += `\n -------------------------------------------------------识别约束-------------------------------------------------------------\n `
        let messages_Cons = [
            { "role": "system", "content": systemContent() },
            { "role": "user", "content": prompt_Cons_1(model.semanticstype, model.semantics, Final_PandV_List) }]


        response = await openai.chat.completions.create({
            temperature: 0.7,
            messages: messages_Cons,
            model: model.llmmodel
        });

        prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
        completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

        console.log(response.choices[0].message.content);

        ProcessPreview.value += `\n${response.choices[0].message.content} \n `

        console.log("\n token使用情况", response.usage);

        console.log("\n api调用结束原因", response.choices[0].finish_reason);

        messages_Cons.push({ "role": "assistant", "content": response.choices[0].message.content });

        messages_Cons.push({ "role": "user", "content": prompt_Cons_1_Answer() });

        response = await openai.chat.completions.create({
            temperature: 0.7,
            messages: messages_Cons,
            model: model.llmmodel,
            response_format: { "type": "json_object" },
        });

        console.log("识别约束，先read semantics和Final_PandV_List的结果", response.choices[0].message.content);
        ProcessPreview.value += `\n 识别约束，先read semantics和Final_PandV_List的结果", ${response.choices[0].message.content} \n `

        messages_Cons.push({ "role": "assistant", "content": response.choices[0].message.content });

        const AnswerJson = JSON.parse(response.choices[0].message.content);
        console.log("\n token使用情况", response.usage);

        prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
        completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

        console.log("\n api调用结束原因", response.choices[0].finish_reason);

        if (AnswerJson.answer === 'done') {
            messages_Cons.push({ "role": "user", "content": prompt_Cons_2(model.semanticstype) });
            response = await openai.chat.completions.create({
                temperature: 0.7,
                messages: messages_Cons,
                model: model.llmmodel
            });

            console.log("识别到的约束是：");
            ProcessPreview.value += `\n 识别到的约束是：\n `

            console.log(response.choices[0].message.content);
            ProcessPreview.value += `\n ${response.choices[0].message.content}\n `

            messages_Cons.push({ "role": "assistant", "content": response.choices[0].message.content });
            console.log("\n token使用情况", response.usage);

            prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
            completion_tokens.value = completion_tokens.value + response.usage.completion_tokens

            console.log("\n api调用结束原因", response.choices[0].finish_reason);

            console.log("对每条约束的解释：");
            ProcessPreview.value += `\n 对每条约束的解释：\n `

            messages_Cons.push({ "role": "user", "content": prompt_Cons_3() });
            response = await openai.chat.completions.create({
                temperature: 0.7,
                messages: messages_Cons,
                model: model.llmmodel
            });

            console.log(response.choices[0].message.content);
            ProcessPreview.value += `\n ${response.choices[0].message.content} \n `
            messages_Cons.push({ "role": "assistant", "content": response.choices[0].message.content });
            console.log("\n token使用情况", response.usage);
            prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
            completion_tokens.value = completion_tokens.value + response.usage.completion_tokens
            console.log("\n api调用结束原因", response.choices[0].finish_reason);

            console.log("---------------------------------------识别出的约束Json obj---------------------------------------");
            ProcessPreview.value += `\n ---------------------------------------识别出的约束Json obj--------------------------------------\n `

            messages_Cons.push({ "role": "user", "content": prompt_Cons_4_ConvertToJson() });
            response = await openai.chat.completions.create({
                temperature: 0.7,
                messages: messages_Cons,
                model: model.llmmodel,
                response_format: { "type": "json_object" },
            });

            console.log(response.choices[0].message.content);
            ProcessPreview.value += `\n ${response.choices[0].message.content} \n `

            console.log("\n token使用情况", response.usage);
            prompt_tokens.value = prompt_tokens.value + response.usage.prompt_tokens
            completion_tokens.value = completion_tokens.value + response.usage.completion_tokens
            console.log("\n api调用结束原因", response.choices[0].finish_reason);
            console.log("得到的约束的Json Obj：");
            ProcessPreview.value += `\n 得到的约束的Json Obj： \n `

            let Cons_list = JSON.parse(response.choices[0].message.content);
            for (const cons of Cons_list) {
                console.log(cons);
                ProcessPreview.value += `\n ${cons} \n `

            }
        }

        console.log("--------------------------------PandV List------------------------------------");
        ProcessPreview.value += `\n --------------------------------PandV List------------------------------------ \n `

        for (const param of Final_PandV_List) {
            console.log(param);
            ProcessPreview.value += `\n ${param} \n `

        }
        console.log("--------------------------------Cons-------------------------------------------");
        ProcessPreview.value += `\n  --------------------------------Cons------------------------------------------- \n `

        for (const cons of Cons_list) {
            console.log(cons);
            ProcessPreview.value += `\n ${cons} \n `

        }
        inExtractingFlag.value = false
        endExtractingFlag.value = true
        Final_PandV_List_extracted = Final_PandV_List
        Cons_extracted = Cons_list


    }
    catch (err) {
        ElNotification({
            title: 'Extract Failed!',
            message: 'Please check the reasons!',
            type: 'error',
            duration: 0
        });

        stopExtractingFlag.value = true;
        console.log("err", err);

        const errorMessage = String(err);

        if (errorMessage.includes("Connection error")) {
            ProcessPreview.value = `Extract Model from SUT corpus failed\nOpenAI ${errorMessage}`;
        } else {
            ProcessPreview.value = `Extract Model from SUT corpus failed\n ${errorMessage}`;
        }
    }


}

const SaveModel = async () => {
    if (endExtractingFlag.value) {
        const currentDate = new Date();
        const saveExtractedModel = await request({
            url: '/tools/models/SaveModel',
            method: 'POST',
            data: {
                modelid: route.query.modelid,
                modelname: model.modelname,
                modeldescriptions: model.modeldescriptions,
                strength: 0,
                ParametersAndValues: JSON.stringify(Final_PandV_List_extracted),
                Cons: JSON.stringify(Cons_extracted),
                lastupdatedtime: currentDate
            }
        })

        if (saveExtractedModel.SaveModelStatus == 'success') {

            ElNotification({
                title: 'Save Success!',
                type: 'success',
            })
            await listModelInfoByModelID()
        }

    }
    else {
        ElNotification({
            title: 'Save Failed!',
            message: 'Extracting, please wait...',
            type: 'error',
            duration: 0
        })
    }

}

onMounted(async () => {
    await listModelInfoByModelID()

})
</script>