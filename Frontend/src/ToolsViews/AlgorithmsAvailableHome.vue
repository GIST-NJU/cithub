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
                        <p style="margin-left: 45px;" class="text-muted text-md mb-0"> Cithub Provides a variety of tools of
                            Combinatorial Testing for using. Following are the categories of tools
                            available now.</p>
                        <p style="margin-left: 45px;" class="text-muted text-md mb-0">Don't know how to use? <a
                                style="cursor:pointer" @click="jumpToHelps"><i
                                    class="ni ni-send text-info text-lg opacity-10"></i><span
                                    class="nav-link-text ms-1 p">Help</span></a></p>
                        <div class="row" style="margin:10px 0px 0px 20px">
                            <div class="col-lg-3 col-md-6 col-12">
                                <CategoryCard class="category" :class="{ 'borderGlow': showGenerationFlag }"
                                    :value="'Generation'" :percentage="toolsArrayGeneration.length + ' tools included '"
                                    :iconClass="'ni ni-world'" :iconBackground="'bg-gradient-success'" directionReverse
                                    @click="showGenerationFlag = !showGenerationFlag">
                                </CategoryCard>
                            </div>

                            <div class="col-lg-5 col-md-6 col-12">
                                <CategoryCard class="category" :class="{ 'borderGlow': showFormatConversionFlag }"
                                    :value="'FormatConversion'"
                                    :percentage="toolsArrayFormatConversion.length + ' tools included'"
                                    :iconClass="'ni ni-paper-diploma'" :iconBackground="'bg-gradient-danger'"
                                    directionReverse @click="showFormatConversionFlag = !showFormatConversionFlag">
                                </CategoryCard>
                            </div>

                            <div class="col-lg-3 col-md-6 col-12">
                                <CategoryCard class="category" :class="{ 'borderGlow': showEvaluationFlag }"
                                    :value="'Evaluation'" :percentage="toolsArrayEvaluation.length + ' tools included'"
                                    :iconClass="'ni ni-cart'" :iconBackground="'bg-gradient-info'" directionReverse
                                    @click="showEvaluationFlag = !showEvaluationFlag">
                                </CategoryCard>
                            </div>
                            <div class="col-lg-3 col-md-6 col-12">
                                <CategoryCard class="category" :class="{ 'borderGlow': showPrioritisationFlag }"
                                    :value="'Prioritisation'"
                                    :percentage="toolsArrayPrioritisation.length + ' tools included'"
                                    :iconClass="'ni ni-umbrella-13'" :iconBackground="'bg-gradient-warning'"
                                    directionReverse @click="showPrioritisationFlag = !showPrioritisationFlag">
                                </CategoryCard>
                            </div>
                            <div class="col-lg-5 col-md-6 col-12">
                                <CategoryCard class="category" :class="{ 'borderGlow': showSelectionReductionFlag }"
                                    :value="'SelectionReduction'"
                                    :percentage="toolsArraySelectionReduction.length + ' tools included'"
                                    :iconClass="'ni ni-zoom-split-in'" :iconBackground="'bg-gradient-primary'"
                                    directionReverse @click="showSelectionReductionFlag = !showSelectionReductionFlag">
                                </CategoryCard>
                            </div>
                            <div class="col-lg-3 col-md-6 col-12">
                                <CategoryCard class="category" :class="{ 'borderGlow': showOtherFlag }" :value="'Other'"
                                    :percentage="toolsArrayOther.length + ' tools included'" :iconClass="'ni ni-sound-wave'"
                                    :iconBackground="'bg-gradient-secondary'" directionReverse
                                    @click="showOtherFlag = !showOtherFlag">
                                </CategoryCard>
                            </div>

                        </div>


                        <div v-auto-animate class="card-header pb-0">
                            <!-- Generation -->
                            <ToolsInfoCard v-if="showGenerationFlag" :toolType="'Generation'" :color="'success'"
                                :toolsArray="toolsArrayGeneration"></ToolsInfoCard>
                            <!-- FormatConversion -->
                            <ToolsInfoCard v-if="showFormatConversionFlag" :toolType="'FormatConversion'" :color="'danger'"
                                :toolsArray="toolsArrayFormatConversion">
                            </ToolsInfoCard>
                            <!-- Evaluation -->
                            <ToolsInfoCard v-if="showEvaluationFlag" :toolType="'Evaluation'" :color="'info'"
                                :toolsArray="toolsArrayEvaluation"></ToolsInfoCard>
                            <!-- Prioritisation -->
                            <ToolsInfoCard v-if="showPrioritisationFlag" :toolType="'Prioritisation'" :color="'warning'"
                                :toolsArray="toolsArrayPrioritisation">
                            </ToolsInfoCard>
                            <!-- SelectionReduction -->
                            <ToolsInfoCard v-if="showSelectionReductionFlag" :toolType="'SelectionReduction'"
                                :color="'primary'" :toolsArray="toolsArraySelectionReduction">
                            </ToolsInfoCard>
                            <!-- Other -->
                            <ToolsInfoCard v-if="showOtherFlag" :toolType="'Other'" :color="'secondary'"
                                :toolsArray="toolsArrayOther">
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
import Foot from '../CustomizedComponents/Foot.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import SideNav from './components/SideNav.vue'
import ToolsInfoCard from './components/ToolsInfoCard.vue'
import CategoryCard from './components/CategoryCard.vue'
import toolsInfo from "../CustomizedComponents/tools_info.json"
import { useUserStore } from '../store/userStore';
import pinia from '../store/store';
import { useRouter } from 'vue-router';
import { ElLoading } from 'element-plus'
import { useModuleStore } from '../store/module';


const moduleStore = useModuleStore(pinia)
const router = useRouter();
const userStore = useUserStore(pinia)



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
const selectedDivIndex = ref(null);

const handleDivClick = (index) => {
    selectedDivIndex.value = index;
};
const jumpToHelps = () => {
    router.push({ name: 'HelpCenter' })
}

onMounted(async () => {

    let loadingInstance = ElLoading.service({ fullscreen: true })

    try {

        // 加载工具信息
        LoadToolsInfo()

        moduleStore.CurrentSubSystem = "Tools"
        moduleStore.CurrentSubSystemRoute = "Tools_Models"
        moduleStore.CurrentModule = 'Algorithms Available'
        moduleStore.CurrentModuleDetails = ''
        moduleStore.CurrentRoute = 'AlgorithmsAvailableHome'

        loadingInstance.close()

    } catch (error) {

        loadingInstance.close()

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

.borderGlow {
    border: 2px solid #2dce89;
    /* 设置边框样式，这里使用蓝色边框作为示例 */
    box-shadow: 0 0 10px #2dce89;
    /* 设置阴影效果，使边框发光 */
}
</style>
