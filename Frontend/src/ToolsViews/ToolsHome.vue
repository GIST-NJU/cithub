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
                    <div class="card mb-4">
                        <h3 style="margin: 20px 0px 0px 40px;">Tools</h3>
                        <p style="margin-left: 45px;" class="text-muted text-sm mb-0"> Cithub Provides a variety of tools of
                            Combinatorial Testing for using.</p>
                        <p style="margin-left: 45px;" class="text-muted text-sm mb-0"> Following are the categories of tools available now.</p>
                        <div class="row" style="margin:10px 0px 0px 20px">
                            <div class="col-lg-3 col-md-6 col-12">
                                <CategoryCard class="category" :value="'Generation'"
                                    :percentage="toolsArrayGeneration.length + ' tools included '"
                                    :iconClass="'ni ni-world'" :iconBackground="'bg-gradient-success'" directionReverse
                                    @click="showGenerationFlag = !showGenerationFlag">
                                </CategoryCard>
                            </div>

                            <div class="col-lg-5 col-md-6 col-12">
                                <CategoryCard class="category" :value="'FormatConversion'"
                                    :percentage="toolsArrayFormatConversion.length + ' tools included'"
                                    :iconClass="'ni ni-paper-diploma'" :iconBackground="'bg-gradient-danger'"
                                    directionReverse @click="showFormatConversionFlag = !showFormatConversionFlag">
                                </CategoryCard>
                            </div>

                            <div class="col-lg-3 col-md-6 col-12">
                                <CategoryCard class="category" :value="'Evaluation'"
                                    :percentage="toolsArrayEvaluation.length + ' tools included'" :iconClass="'ni ni-cart'"
                                    :iconBackground="'bg-gradient-info'" directionReverse
                                    @click="showEvaluationFlag = !showEvaluationFlag">
                                </CategoryCard>
                            </div>
                            <div class="col-lg-3 col-md-6 col-12">
                                <CategoryCard class="category" :value="'Prioritisation'"
                                    :percentage="toolsArrayPrioritisation.length + ' tools included'"
                                    :iconClass="'ni ni-umbrella-13'" :iconBackground="'bg-gradient-warning'"
                                    directionReverse @click="showPrioritisationFlag = !showPrioritisationFlag">
                                </CategoryCard>
                            </div>
                            <div class="col-lg-5 col-md-6 col-12">
                                <CategoryCard class="category" :value="'SelectionReduction'"
                                    :percentage="toolsArraySelectionReduction.length + ' tools included'"
                                    :iconClass="'ni ni-zoom-split-in'" :iconBackground="'bg-gradient-primary'"
                                    directionReverse @click="showSelectionReductionFlag = !showSelectionReductionFlag">
                                </CategoryCard>
                            </div>
                            <div class="col-lg-3 col-md-6 col-12">
                                <CategoryCard class="category" :value="'Other'"
                                    :percentage="toolsArrayOther.length + ' tools included'" :iconClass="'ni ni-sound-wave'"
                                    :iconBackground="'bg-gradient-secondary'" directionReverse
                                    @click="showOtherFlag = !showOtherFlag">
                                </CategoryCard>
                            </div>

                        </div>


                        <div v-auto-animate class="card-header pb-0">
                            <!-- Generation -->
                            <ToolsInfoCard v-if="showGenerationFlag" :toolType="'Generation'"
                                :toolsArray="toolsArrayGeneration"></ToolsInfoCard>
                            <!-- FormatConversion -->
                            <ToolsInfoCard v-if="showFormatConversionFlag" :toolType="'FormatConversion'"
                                :toolsArray="toolsArrayFormatConversion">
                            </ToolsInfoCard>
                            <!-- Evaluation -->
                            <ToolsInfoCard v-if="showEvaluationFlag" :toolType="'Evaluation'"
                                :toolsArray="toolsArrayEvaluation"></ToolsInfoCard>
                            <!-- Prioritisation -->
                            <ToolsInfoCard v-if="showPrioritisationFlag" :toolType="'Prioritisation'"
                                :toolsArray="toolsArrayPrioritisation">
                            </ToolsInfoCard>
                            <!-- SelectionReduction -->
                            <ToolsInfoCard v-if="showSelectionReductionFlag" :toolType="'SelectionReduction'"
                                :toolsArray="toolsArraySelectionReduction">
                            </ToolsInfoCard>
                            <!-- Other -->
                            <ToolsInfoCard v-if="showOtherFlag" :toolType="'Other'" :toolsArray="toolsArrayOther">
                            </ToolsInfoCard>

                        </div>



                    </div>
                </div>
            </div>


            <Foot></Foot>
        </div>
        <!-- <div class="py-4 container-fluid">
            <div class=" row">
                <div class="col-12">
                    <authors-table />
                </div>
            </div>
            <div class="mt-4 row">
                <div class="col-12">
                    <projects-table />
                </div>
            </div>
           
        </div> -->
        <!-- <ProjectCard></ProjectCard> -->
    </main>
</template>

<script setup>
import { onMounted, reactive, ref, computed } from 'vue';
import { request } from '../request';
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import SideNav from './components/SideNav.vue'
import ToolsInfoCard from './components/ToolsInfoCard.vue'
import CategoryCard from './components/CategoryCard.vue'
import toolsInfo from "../ComponentCommon/tools_info.json"
import ProjectCard from "./components/ProjectCard.vue"
import { useUserStore } from '../store/userStore';
import { usePaperInfoStore } from '../store/paperinfoStore';
import { useProjectsStore } from '../store/projectsStore'
import { ElNotification } from 'element-plus'
import { CheckLoginStatus, getUserInfoByToken } from '../common'
import { useRouter } from 'vue-router';
const router = useRouter();
const userStore = useUserStore()



const toolsArrayGeneration = reactive([]);
const toolsArrayFormatConversion = reactive([]);
const toolsArrayEvaluation = reactive([]);
const toolsArrayPrioritisation = reactive([]);
const toolsArraySelectionReduction = reactive([]);
const toolsArrayOther = reactive([])

const showGenerationFlag = ref(false)
const showFormatConversionFlag = ref(false)
const showEvaluationFlag = ref(false)
const showPrioritisationFlag = ref(false)
const showSelectionReductionFlag = ref(false)
const showOtherFlag = ref(false)

const LoadToolsInfo = () => {
    for (const tool of toolsInfo.RECORDS) {
        switch (tool.type) {
            case 'Generation':
                toolsArrayGeneration.push(tool);
                break;
            case 'FormatConversion':
                toolsArrayFormatConversion.push(tool);
                break;
            case 'Evaluation':
                toolsArrayEvaluation.push(tool);
                break;
            case 'Prioritisation':
                toolsArrayPrioritisation.push(tool);
                break;
            case 'SelectionReduction':
                toolsArraySelectionReduction.push(tool);
                break;
            default:
                toolsArrayOther.push(tool)
                break;
        }
    }
}

onMounted(async () => {
    // 先检查用户是否登录
    let obj = await CheckLoginStatus()
    if (obj.loginStatus) {

        // 若用户已登录，则获取用户信息！
        let userobj = await getUserInfoByToken(obj.token)
        userStore.userID = userobj.userid
        userStore.account = userobj.account
        userStore.userToken = userobj.userToken
        userStore.usertype = userobj.usertype
        userStore.name = userobj.name
        userStore.email = userobj.email
        userStore.institution = userobj.institution
        LoadToolsInfo()
    }
    else {
        // 若用户未登录则跳转到登录页面
        router.push(
            {
                name: 'UserLogin'
            }
        )
    }


})
</script>

<style scoped>
.category {
    transition: transform 0.3s ease;
    /* 添加过渡效果 */
}

.category:hover {
    transform: scale(1.09);
    /* 划过时放大 */
    cursor: pointer;
}
</style>
