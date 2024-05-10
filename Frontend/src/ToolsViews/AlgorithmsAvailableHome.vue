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
                        <p style="margin-left: 45px;" class="text-muted text-md mb-0">Cithub Provides a variety of algorithms (tools) of
                            Combinatorial Testing for using. Following are the categories of algorithms
                            available now.</p>

                        <!-- <p style="margin-left: 45px;" class="text-muted text-md mb-0">Don't know how to use? <a
                                style="cursor:pointer" @click="jumpToHelps"><i
                                    class="ni ni-send text-info text-lg opacity-10"></i><span
                                    class="nav-link-text ms-1 p">Help</span></a></p> -->

                        <div class="row" style="margin:10px 0px 0px 20px">
                            <div class="col-4">
                                <CategoryCard class="category" :class="{ 'borderGlow': showGenerationFlag }"
                                    :value="'Generation'" :percentage="toolsArrayGeneration.length + ' tools included '"
                                    :iconClass="'ni ni-world'" :iconBackground="'bg-gradient-success'" directionReverse
                                    @click="showGenerationFlag = !showGenerationFlag; closeOtherFlags('showGenerationFlag');moduleStore.CurrentModuleDetails = 'Generation'">
                                </CategoryCard>
                            </div>


                            <div class="col-4">
                                <CategoryCard class="category" :class="{ 'borderGlow': showPrioritisationFlag }"
                                    :value="'Prioritisation'"
                                    :percentage="toolsArrayPrioritisation.length + ' tools included'"
                                    :iconClass="'ni ni-umbrella-13'" :iconBackground="'bg-gradient-warning'"
                                    directionReverse @click="showPrioritisationFlag = !showPrioritisationFlag; closeOtherFlags('showPrioritisationFlag');moduleStore.CurrentModuleDetails = 'Prioritisation'">
                                </CategoryCard>
                            </div>

                            <div class="col-4">
                                <CategoryCard class="category" :class="{ 'borderGlow': showSelectionReductionFlag }"
                                    :value="'Reduction'"
                                    :percentage="toolsArraySelectionReduction.length + ' tools included'"
                                    :iconClass="'ni ni-zoom-split-in'" :iconBackground="'bg-gradient-primary'"
                                    directionReverse @click="showSelectionReductionFlag = !showSelectionReductionFlag; closeOtherFlags('showSelectionReductionFlag');moduleStore.CurrentModuleDetails = 'Reduction'">
                                </CategoryCard>
                            </div>

                            <div class="col-4">
                                <CategoryCard class="category" :class="{ 'borderGlow': showOtherFlag }" :value="'Diagnosis'"
                                    :percentage="toolsArrayOther.length + ' tools included'" :iconClass="'ni ni-sound-wave'"
                                    :iconBackground="'bg-gradient-danger'" directionReverse
                                    @click="showOtherFlag = !showOtherFlag; closeOtherFlags('showOtherFlag');moduleStore.CurrentModuleDetails = 'Diagnosis'">
                                </CategoryCard>
                            </div>


                            <div class="col-4">
                                <CategoryCard class="category" :class="{ 'borderGlow': showEvaluationFlag }"
                                    :value="'Evaluation'" :percentage="toolsArrayEvaluation.length + ' tools included'"
                                    :iconClass="'ni ni-cart'" :iconBackground="'bg-gradient-info'" directionReverse
                                    @click="showEvaluationFlag = !showEvaluationFlag; closeOtherFlags('showEvaluationFlag');moduleStore.CurrentModuleDetails = 'Evaluation'">
                                </CategoryCard>
                            </div>





                        </div>


                        <div  class="card-header pb-0">
                            <!-- Generation -->
                            <ToolsInfoCard v-if="showGenerationFlag" :toolType="'Generation'" :color="'success'"
                                :toolsArray="toolsArrayGeneration"></ToolsInfoCard>


                            <!-- Evaluation -->
                            <ToolsInfoCard v-if="showEvaluationFlag" :toolType="'Evaluation'" :color="'info'"
                                :toolsArray="toolsArrayEvaluation"></ToolsInfoCard>
                            <!-- Prioritisation -->
                            <ToolsInfoCard v-if="showPrioritisationFlag" :toolType="'Prioritisation'" :color="'warning'"
                                :toolsArray="toolsArrayPrioritisation">
                            </ToolsInfoCard>
                            <!-- SReduction -->
                            <ToolsInfoCard v-if="showSelectionReductionFlag" :toolType="'Reduction'"
                                :color="'primary'" :toolsArray="toolsArraySelectionReduction">
                            </ToolsInfoCard>
                            <!-- Other -->
                            <ToolsInfoCard v-if="showOtherFlag" :toolType="'Diagnosis'" :color="'danger'"
                                :toolsArray="toolsArrayOther">
                            </ToolsInfoCard>

                        </div>



                    </div>
                </div>
            </div>


            <Foot></Foot>
        </div>

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
            case 'Reduction':
                toolsArraySelectionReduction.push(tool);
                break;
            case 'Diagnosis':
                toolsArrayOther.push(tool)
                break;
        }
    }
}

const jumpToHelps = () => {
    router.push({ name: 'HelpCenter' })
}

const closeOtherFlags = (flagName) => {
    const flags = ['showGenerationFlag', 'showFormatConversionFlag', 'showEvaluationFlag', 'showPrioritisationFlag', 'showSelectionReductionFlag', 'showOtherFlag'];
    flags.forEach(flag => {
        if (flag !== flagName) {
            const flagRef = eval(flag);
            flagRef.value = false;
        }
    });
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
