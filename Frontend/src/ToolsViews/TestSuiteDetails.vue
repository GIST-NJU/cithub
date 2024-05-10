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
                    <div class="row">
                        <div class="col-2">

                            <ArgonButton class="mb-2" color="secondary" size="sm" @click="goBack" full-width>
                                <span class="ni ni-bold-left  me-1"> </span>Back
                            </ArgonButton>

                        </div>

                        <div class="col-4">

                            <ArgonButton class="mb-2" color="info" size="sm" full-width
                                @click="TestTableListFlag = !TestTableListFlag">
                                Model {{ currentModel.currentModel.modelname }}'s TestSuites List
                            </ArgonButton>

                        </div>
                    </div>

                    <!-- TestSuite Table for current model -->


                    <div>
                        <div v-if="TestTableListFlag" class="card mb-2">
                            <div v-for="(obj, index) in testSuitesStore.testSuitesList" class="card-body px-0 pt-0 pb-0">
                                <TestSuitesTable :testSuites="obj['testSuites']" :model="obj['model']"
                                    :AlgorithmOptions="AlgorithmOptions" @scrollToTestSuiteInfo="scrollToTestSuiteInfo">
                                </TestSuitesTable>
                            </div>
                        </div>
                    </div>

                    <div class="card"
                        v-if="testSuitesStore.testSuitesList[0] && !currentTestSuite.currentTestSuites.testsuitesname && testSuitesStore.testSuitesList[0].testSuites.length != 0">
                        <div class="card-body pb-0">
                            <div class="row">
                                <h3 class="text-center">Please Choose one of the testsuites From the above TestSuites List.
                                </h3>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <!-- Basic Info -->
                        <div class="col-6 d-flex align-items-stretch">
                            <div class="card" v-if="currentTestSuite.currentTestSuites.testsuitesname">
                                <div class="card-header pb-0">
                                    <h3 class="text-center" ref="TestSuiteInfo">TestSuite Info</h3>
                                </div>
                                <div class="card-body">

                                    <div class="row">
                                        <h5>Basic Info</h5>
                                        <div class="col-4">
                                            <h6> TestSuite Name</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.testsuitesname"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-8">
                                            <h6>TestSuite Description</h6>
                                            <argon-input type="text"
                                                v-model="currentTestSuite.currentTestSuites.testsuitesdescriptions"
                                                readonly />
                                        </div>
                                    </div>

                                    <hr class="my-3 horizontal white" />

                                    <div class="row">
                                        <h5>Generation Info</h5>
                                        <div class="col-3">
                                            <h6> Generation Tool</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.generationtool"
                                                type="text" readonly />
                                        </div>


                                        <div class="col-2">
                                            <h6 v-if="currentTestSuite.currentTestSuites.sizeafterreduction">Original
                                                Size
                                            </h6>
                                            <h6 v-else="currentTestSuite.currentTestSuites.sizeafterreduction">Size</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.size" type="text"
                                                readonly />
                                        </div>

                                        <div class="col-3">
                                            <h6>Generation Time</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.generationtime"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-4 mt-4 pt-2">
                                            <ArgonButton full-width color="success" @click="showdialogNew">
                                                <span class="bi bi-arrow-clockwise"></span>Re-Generation
                                            </ArgonButton>
                                        </div>

                                    </div>

                                    <hr class="my-3 horizontal white" />

                                    <div class="row" v-if="currentTestSuite.currentTestSuites.reductiontool">
                                        <h5>Reduction Info</h5>
                                        <div class="col-3">
                                            <h6> Reduction Tool</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.reductiontool"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-3">
                                            <h6>Size after Reduction</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.sizeafterreduction"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-3">
                                            <h6>Reduction Time</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.reductiontime"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-3  mt-4 pt-2">
                                            <ArgonButton full-width color="primary" @click="showdialogNewReduction">
                                                {{ getButtonName('Reduction') }}
                                            </ArgonButton>
                                        </div>

                                    </div>

                                    <div class="row" v-else>
                                        <h6>TestSuite {{ currentTestSuite.currentTestSuites.testsuitesname }} has not
                                            been
                                            Reducted, Click the Button below to Reduct.
                                        </h6>
                                        <div class="col-12 mt-4 pt-2">
                                            <ArgonButton full-width color="primary" @click="showdialogNewReduction">
                                                {{ getButtonName('Reduction') }}
                                            </ArgonButton>
                                        </div>

                                    </div>

                                    <hr class="my-3 horizontal white" />

                                    <div class="row" v-if="currentTestSuite.currentTestSuites.prioritisationtool">
                                        <h5>Prioritisation Info</h5>
                                        <div class="col-4">
                                            <h6> Prioritisation Tool</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.prioritisationtool"
                                                type="text" readonly />
                                        </div>


                                        <div class="col-4">
                                            <h6>Prioritisation Time</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.prioritisationtime"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-4 mt-4 pt-2">
                                            <ArgonButton full-width color="warning" @click="showdialogNewPrioritization">
                                                {{ getButtonName('Prioritisation') }}
                                            </ArgonButton>
                                        </div>

                                    </div>


                                    <div class="row" v-else>
                                        <h6>TestSuite {{ currentTestSuite.currentTestSuites.testsuitesname }} has not
                                            been
                                            Prioritised, Click the Button below to Prioritise.</h6>
                                        <div class="col-12 mt-4 pt-2">
                                            <ArgonButton full-width color="warning" @click="showdialogNewPrioritization">
                                                {{ getButtonName('Prioritisation') }}

                                            </ArgonButton>
                                        </div>

                                    </div>

                                    <hr class="my-3 horizontal white" />

                                    <!-- Diagnosis -->
                                    <div class="row" v-if="currentTestSuite.currentTestSuites.diagnosiscontents">
                                        <h5>Dignosis Info</h5>
                                        <div class="col-6">
                                            <h6> Dignosis Tool</h6>
                                            <argon-input v-model="DignosisInfo.Tools" type="text" readonly />
                                        </div>

                                        <div class="col-6  mt-4 pt-2">
                                            <ArgonButton full-width color="danger" @click="showdialogNewDiagnosis">
                                                {{ getButtonName('Dignosis') }}

                                            </ArgonButton>
                                        </div>


                                        <!-- 用表格的形式 展示 故障元组 -->
                                        <h5 class="text-center my-3">Fault Tuples of TestCase Row #
                                            {{
                                                currentTestSuite.currentTestSuites.diagnosiscontents.DiagnosisTestCasesRowIndex
                                                + 1 }}
                                        </h5>
                                        <table class="table table-bordered">
                                            <thead class="table-light">
                                                <tr>
                                                    <!-- 使用 col-auto 类使第一列只占据所需的空间 -->
                                                    <th class="text-center">Index</th>
                                                    <th class="text-center">Fault Tuples</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr v-for="(tuple, index) in currentTestSuite.currentTestSuites.diagnosiscontents.FaultTuples"
                                                    :key="index">
                                                    <td class="text-center">{{ index + 1 }}</td>
                                                    <td class="text-center">{{ tuple }}</td>
                                                </tr>

                                                <tr class="border-0">
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </div>

                                    <div class="row" v-else>
                                        <h6>There is no Diagnosis Result for TestSuite
                                            {{ currentTestSuite.currentTestSuites.testsuitesname }},
                                            Click the Button below to Diagnosis.</h6>
                                        <div class="col-12 mt-4 pt-2">
                                            <ArgonButton full-width color="danger" @click="showdialogNewDiagnosis">
                                                {{ getButtonName('Dignosis') }}

                                            </ArgonButton>
                                        </div>

                                    </div>



                                    <hr class="my-3 horizontal white" />


                                </div>
                            </div>

                            <div class="row my-2">
                                <div class="col-6">

                                </div>

                                <!-- 点击按钮展开的对话框 -->

                                <!-- Regenerate test suites -->
                                <el-dialog v-model="dialogFormVisibleNew"
                                    title="Generate a new testSuite based on current model">
                                    <el-form :model="dialogformNewTestSuites" label-position="left" label-width="170px">

                                        <!-- <el-form-item label="Test Suite Name:">
                            <el-input v-model="dialogformNewTestSuites.testsuitesname" />
                        </el-form-item>
                        <el-form-item label="Test Suite Description:">
                            <el-input autosize type="textarea" v-model="dialogformNewTestSuites.testsuitesdescriptions" />
                        </el-form-item> -->

                                        <el-form-item label="Strength: ">
                                            <el-select clearable style="margin: 0 0 0 0;padding: 0;width: 250px"
                                                v-model="strength" class="m-2" placeholder="Select a covering strength">
                                                <el-option v-for="item in StrengthOptions" :key="item.value"
                                                    :label="item.label" :value="item.value" />
                                            </el-select>
                                        </el-form-item>
                                        <el-form-item label="Generation Tool: ">
                                            <el-select v-model="AlgorithmChosed" class="m-2"
                                                placeholder="Select an Tool for generating" @change="handleAlgorithmChange">
                                                <el-option v-for="item in AlgorithmOptions" :key="item.value"
                                                    :label="item.label" :value="item.value" />
                                            </el-select>
                                        </el-form-item>

                                        <!-- 在此显示 对应 AlgorithmChosed 的 tool.description -->

                                        <el-form-item label="Tool Description: ">
                                            <el-input readonly v-model="AlgorithmDescription"
                                                :autosize="{ minRows: 2, maxRows: 12 }" type="textarea"
                                                placeholder="The Description of Tool Choosen Will Be Displayed Here" />
                                        </el-form-item>

                                    </el-form>

                                    <div v-if="inAPIcall" class="row" style="margin: 0px 0px 0px 30px;">
                                        <div class="spinner-border  text-success col-4" role="status">
                                        </div>
                                        <div class="col-8" style="margin: 5px 0px 0px 0px;">
                                            <h6>CitHub is calling the API of {{ AlgorithmChosed }}, Please wait a while...
                                            </h6>
                                        </div>
                                    </div>

                                    <template #footer>
                                        <span class="dialog-footer">
                                            <el-button @click="dialogFormVisibleNew = false">Cancel</el-button>
                                            <el-button type="primary" @click="confirmGenerateNewTestSuites">
                                                Confirm
                                            </el-button>
                                        </span>
                                    </template>
                                </el-dialog>


                                <!-- New Prioritisation -->
                                <el-dialog v-model="dialogFormVisibleNewPrioritization"
                                    title="Choose a Tool for Prioritization:">
                                    <el-form :model="dialogformNewTestSuites" label-position="left" label-width="170px">
                                        <el-form-item label="Tool:">
                                            <el-select v-model="AlgorithmChosedPrioritization" class="m-2"
                                                @change="handleAlgorithmOptionsPrioritizationChange"
                                                placeholder="Select a tool for prioritising current testsuite." clearable>
                                                <el-option v-for="item in AlgorithmOptionsPrioritization" :key="item.value"
                                                    :label="item.label" :value="item.value" />
                                            </el-select>
                                        </el-form-item>

                                        <!-- 在此显示 对应 AlgorithmChosed 的 tool.description -->

                                        <el-form-item label="Tool Description: ">
                                            <el-input readonly v-model="AlgorithmDescriptionPrioritization"
                                                :autosize="{ minRows: 2, maxRows: 12 }" type="textarea"
                                                placeholder="The Description of Tool Choosen Will Be Displayed Here" />
                                        </el-form-item>

                                        <div v-if="AlgorithmChosedPrioritization == 'SortArray'">
                                            <!-- <el-form-item label="Weight:">
                                    <el-input v-model="Weight" type="textarea" :autosize="{ minRows: 4 }" placeholder="Note:
1.Please input the Weight of parameters using `,`  to split each Weight, eg: 3,1,2 for three parameters.
2.For each parameter, you need to input a corresponding weight.eg: three parameters have to input three weight
                                    ">
                                    </el-input>
                                </el-form-item> -->

                                            <el-form-item>
                                                <div class=row>
                                                    <h5>Please input a corresponding Weight for each Parameter</h5>
                                                    <div class="row">
                                                        <div class="col-4">
                                                            <h6>Parameter</h6>
                                                        </div>
                                                        <div class="col-2">
                                                            <h6>Weight</h6>
                                                        </div>
                                                    </div>
                                                    <div class="row "
                                                        v-for="(Param, index) in currentModel.currentModel.PandVOBJ">
                                                        <div class="col-4">
                                                            <el-input readonly v-model="Param.Parameter"></el-input>
                                                        </div>
                                                        <div class="col-4">
                                                            <el-input-number v-model="Weight[index]" :min="0"
                                                                placeholder="Integer" />
                                                        </div>
                                                    </div>
                                                    <!-- {{ Weight }} -->

                                                </div>
                                            </el-form-item>

                                        </div>



                                    </el-form>

                                    <div v-if="inAPIcall" class="row" style="margin: 15px 0px 0px 40px;">
                                        <div class="spinner-border  text-success col-4" role="status">
                                        </div>
                                        <div class="col-8" style="margin: 5px 0px 0px 0px;">

                                            <h6>CitHub is calling the API of {{ AlgorithmChosedPrioritization }}, Please
                                                wait a
                                                while...
                                            </h6>

                                        </div>
                                    </div>

                                    <template #footer>
                                        <span class="dialog-footer">
                                            <el-button
                                                @click="dialogFormVisibleNewPrioritization = false">Cancel</el-button>
                                            <el-button type="primary" @click="confirmNewPrioritisation">
                                                Confirm
                                            </el-button>
                                        </span>
                                    </template>
                                </el-dialog>


                                <!-- New Reduction -->
                                <el-dialog v-model="dialogFormVisibleNewReduction" title="Choose a Tool for Reduction:">
                                    <el-form :model="dialogformNewTestSuites" label-position="right" label-width="140px">
                                        <el-form-item label="Tool:">
                                            <el-select v-model="AlgorithmChosedReduction" class="m-2"
                                                @change="handleAlgorithmOptionsReductionChange"
                                                placeholder="Select a tool for reducting current testsuite." clearable>
                                                <el-option v-for="item in AlgorithmOptionsReduction" :key="item.value"
                                                    :label="item.label" :value="item.value" />
                                            </el-select>
                                        </el-form-item>

                                        <el-form-item label="Tool Description: ">
                                            <el-input readonly v-model="AlgorithmDescriptionReduction"
                                                :autosize="{ minRows: 2, maxRows: 12 }" type="textarea"
                                                placeholder="The Description of Tool Choosen Will Be Displayed Here" />
                                        </el-form-item>
                                    </el-form>

                                    <div v-if="inAPIcall" class="row" style="margin: 15px 0px 0px 40px;">
                                        <div class="spinner-border  text-success col-4" role="status">
                                        </div>
                                        <div class="col-8" style="margin: 5px 0px 0px 5px;">
                                            <h6>CitHub is calling the API of {{ AlgorithmChosedReduction }}, Please wait a
                                                while...
                                            </h6>
                                        </div>
                                    </div>


                                    <template #footer>
                                        <span class="dialog-footer">
                                            <el-button @click="dialogFormVisibleNewReduction = false">Cancel</el-button>
                                            <el-button type="primary" @click="confirmNewReduction">
                                                Confirm
                                            </el-button>
                                        </span>
                                    </template>
                                </el-dialog>



                                <!-- New Diagnosis -->
                                <el-dialog v-model="dialogFormVisibleNewDiagnosis" title="New Diagnosis">
                                    <el-form :model="dialogformNewTestSuites">
                                        <el-form-item label="Tool: ">
                                            <el-select v-model="AlgorithmChosedDiagnosis" class="m-2"
                                                @change="handleAlgorithmOptionsDiagnosisChange"
                                                placeholder="Select an Tool for Diagnosis">
                                                <el-option v-for="item in AlgorithmOptionsDiagnosis" :key="item.value"
                                                    :label="item.label" :value="item.value" />
                                            </el-select>
                                        </el-form-item>

                                        <el-form-item label="Tool Description: ">
                                            <el-input readonly v-model="AlgorithmDescriptionDiagnosis"
                                                :autosize="{ minRows: 2, maxRows: 12 }" type="textarea"
                                                placeholder="The Description of Tool Choosen Will Be Displayed Here" />
                                        </el-form-item>

                                    </el-form>


                                    <!-- <div v-if="inAPIcall" class="row" style="margin: 15px 0px 0px 20px;">
                                <div class="spinner-border text-success col-1" role="status">
                                </div>
                                <div class="col-11" style="margin: 5px 0px 0px 5px;">
                                    <h6>Cithub is calling the API of {{ AlgorithmChosedEvaluation }} to evaluate, Please
                                        wait a
                                        while...
                                    </h6>
                                </div>
                            </div> -->



                                    <template #footer>
                                        <span class="dialog-footer">
                                            <el-button @click="dialogFormVisibleNewDiagnosis = false">Cancel</el-button>
                                            <el-button type="primary" @click="confirmNewDiagnosis">
                                                Confirm
                                            </el-button>
                                        </span>
                                    </template>
                                </el-dialog>




                                <!-- New Test Plan -->
                                <el-dialog v-model="dialogFormVisibleNewConversion" title="Convert TestSuite into:">
                                    <el-form :model="dialogformNewTestSuites" label-position="left" label-width="150px">
                                        <el-form-item label="Tools of Conversion">
                                            <el-select v-model="AlgorithmChosedConversion" class="m-2"
                                                @change="handleAlgorithmOptionsConversionChange"
                                                placeholder="Select a tool for converting test suite into Test Plan."
                                                clearable>
                                                <el-option v-for="item in AlgorithmOptionsConversion" :key="item.value"
                                                    :label="item.label" :value="item.value" />
                                            </el-select>
                                        </el-form-item>

                                        <el-form-item label="Description: ">
                                            <el-input readonly v-model="AlgorithmDescriptionConversion"
                                                :autosize="{ minRows: 2, maxRows: 12 }" type="textarea"
                                                placeholder="The Description of Tool Choosen Will Be Displayed Here" />
                                        </el-form-item>

                                        <div v-if="AlgorithmChosedConversion == 'JUnit Test Plan'">
                                            <el-form-item label="Function Head">
                                                <el-input v-model="functionHead" type="textarea" autosize
                                                    placeholder="Please input function Head right here, eg:'public void test()'"></el-input>
                                            </el-form-item>
                                            <el-form-item label="Function Body">
                                                <el-input v-model="functionBody" type="textarea" autosize
                                                    placeholder="Please input function Body right here, eg:''CIT cit= new CIT(&p2) cit.execuse(&p3) cit.process(&p1)'">
                                                </el-input>
                                            </el-form-item>
                                        </div>


                                        <div v-if="AlgorithmChosedConversion == 'NL Test Plan'">

                                            <el-form-item label="Output Model Format">
                                                <el-input v-model="outputModel" type="textarea" :autosize="{ minRows: 4 }"
                                                    placeholder="Please input the Output Model format you want right here and use `&#` as placeholder, eg:'first,we should do &#,then open &#,finally,enter in the browser address bar with &#'. All the `&#` will be replaced by the actual value of parameters.">
                                                </el-input>
                                            </el-form-item>

                                        </div>




                                    </el-form>

                                    <div v-if="inAPIcall" class="row" style="margin: 15px 0px 0px 40px;">
                                        <div class="spinner-border  text-success col-4" role="status">
                                        </div>
                                        <div class="col-8" style="margin: 5px 0px 0px 5px;width: 100%;">
                                            <h6>CitHub is calling the API of {{ AlgorithmChosedConversion }} to convert the
                                                testsuite
                                                into test plan, Please wait a while...
                                            </h6>
                                        </div>
                                    </div>

                                    <template #footer>
                                        <span class="dialog-footer">
                                            <el-button @click="dialogFormVisibleNewConversion = false">Cancel</el-button>
                                            <el-button type="primary" @click="confirmNewConversion">
                                                Confirm
                                            </el-button>
                                        </span>
                                    </template>
                                </el-dialog>






                                <div class="col-6">

                                </div>
                            </div>

                        </div>


                        <div class="col-6 d-flex align-items-stretch">
                            <!-- Evaluation -->

                            <div class="card" v-if="currentTestSuite.currentTestSuites.testsuitesname">
                                <div class="card-body">


                                    <div class="row" v-if="currentTestSuite.currentTestSuites.evaluationtool">
                                        <h3 class="text-center">TestSuite Evaluation Info</h3>
                                        <hr class="my-3 horizontal white" />

                                        <div class="col-4">
                                            <h6> Evaluation Tool</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.evaluationtool"
                                                type="text" readonly />
                                        </div>


                                        <div class="col-4">
                                            <h6>Evaluation Time</h6>
                                            <argon-input v-model="currentTestSuite.currentTestSuites.evaluationtime"
                                                type="text" readonly />
                                        </div>

                                        <div class="col-4  mt-4 pt-2">
                                            <ArgonButton full-width color="info" @click="showdialogNewEvaluation">
                                                {{ getButtonName('Evaluation') }}

                                            </ArgonButton>
                                        </div>


                                        <div class="col-12  mt-4 pt-2">
                                            <div id="lineChart" ref="lineChart" style="width: 100%; height: 700%;">
                                            </div>
                                        </div>

                                    </div>

                                    <div class="row" v-else>
                                        <h6> TestSuite {{ currentTestSuite.currentTestSuites.testsuitesname }} has not
                                            been
                                            Evaluated, Click the Button below to Evaluate
                                        </h6>
                                        <div class="col-12 mt-4 pt-2">
                                            <ArgonButton full-width color="info" @click="showdialogNewEvaluation">
                                                {{ getButtonName('Evaluation') }}
                                            </ArgonButton>
                                        </div>

                                    </div>



                                    <!-- New Evaluation -->
                                    <el-dialog v-model="dialogFormVisibleNewEvaluation" title="New Evaluation">
                                        <el-form :model="dialogformNewTestSuites">
                                            <el-form-item label="Tool: ">
                                                <el-select v-model="AlgorithmChosedEvaluation" class="m-2"
                                                    @change="handleAlgorithmOptionsEvaluationChange"
                                                    placeholder="Select an Tool for evaluating">
                                                    <el-option v-for="item in AlgorithmOptionsEvaluation" :key="item.value"
                                                        :label="item.label" :value="item.value" />
                                                </el-select>
                                            </el-form-item>

                                            <el-form-item label="Tool Description: ">
                                                <el-input readonly v-model="AlgorithmDescriptionEvaluation"
                                                    :autosize="{ minRows: 2, maxRows: 12 }" type="textarea"
                                                    placeholder="The Description of Tool Choosen Will Be Displayed Here" />
                                            </el-form-item>

                                        </el-form>


                                        <div v-if="inAPIcall" class="row" style="margin: 15px 0px 0px 20px;">
                                            <div class="spinner-border text-success col-1" role="status">
                                            </div>
                                            <div class="col-11" style="margin: 5px 0px 0px 5px;">
                                                <h6>Cithub is calling the API of {{ AlgorithmChosedEvaluation }} to
                                                    evaluate, Please
                                                    wait a
                                                    while...
                                                </h6>
                                            </div>
                                        </div>


                                        <template #footer>
                                            <span class="dialog-footer">
                                                <el-button
                                                    @click="dialogFormVisibleNewEvaluation = false">Cancel</el-button>
                                                <el-button type="primary" @click="confirmNewEvaluation">
                                                    Confirm
                                                </el-button>
                                            </span>
                                        </template>
                                    </el-dialog>


                                </div>
                            </div>
                        </div>

                    </div>





                    <!-- TestSuite Table Card -->
                    <!-- show testsuite in the form of table -->
                    <hr class="my-3 horizontal dark" />

                    <div class="card" v-if="currentTestSuite.currentTestSuites.testsuitesname" ref="testSuiteTable">
                        <div class="card-header pb-0">
                            <div class="row align-items-center">
                                <div class=col-3></div>
                                <!-- Caption -->
                                <div class="col-6 text-center">
                                    <h3 class="mb-0">TestSuite of {{ currentTestSuite.currentTestSuites.testsuitesname }}
                                    </h3>
                                </div>

                                <!-- Dropdown -->
                                <div class="col-3 d-flex justify-content-end">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-secondary dropdown-toggle"
                                            data-bs-toggle="dropdown" aria-expanded="false">
                                            Operations
                                        </button>
                                        <ul class="dropdown-menu dropdown-menu-end">
                                            <li style="cursor:pointer"><a class="dropdown-item"
                                                    @click="ExportTestSuite">Export TestSuite</a></li>
                                            <li style="cursor:pointer"><a class="dropdown-item"
                                                    @click="showdialogNewConversion">Convert into Test
                                                    Plan</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>



                        </div>


                        <div class="card-body">

                            <div class="row">
                                <div class="col-12">
                                    <div class="col-12">
                                        <div v-if="DignosisRunFlag" class="row mx-2">
                                            <div class="spinner-border  text-success col-4" role="status">
                                            </div>
                                            <div class="col-8 my-2">
                                                <h6>Adaptive Dagnosis is running, Please select a failed testcase below to
                                                    start interacting.
                                                </h6>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- 覆盖表的表格 -->
                                    <table class="table-secondary table-bordered" style="width: 100%;">
                                        <thead>
                                            <tr>
                                                <th class="text-center">#</th>
                                                <th v-for="(parameter, index) in currentModel.currentModel.PandVOBJ"
                                                    :key="index" class="text-center">
                                                    {{ parameter.Parameter }}
                                                </th>
                                                <!-- 使用Bootstrap的栅格系统调整Operation列的宽度 -->
                                                <th v-if="DignosisRunFlag" class="text-center col-2"> Result </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr v-for="(row, rowIndex) in CoveringArray" :key="rowIndex">
                                                <td class="text-center">{{ rowIndex + 1 }}</td>
                                                <td v-for="(value, colIndex) in row" :key="colIndex" class="text-center">
                                                    {{ value }}
                                                </td>
                                                <td v-if="DignosisRunFlag" class="text-center">
                                                    <div class="row">
                                                        <!-- <div class="col">
                                                            <argon-button full-width color="success"
                                                                @click="handleDiagnosisClick(row, true)">Pass</argon-button>
                                                        </div> -->
                                                        <div class="col">
                                                            <argon-button full-width color="danger"
                                                                @click="handleDiagnosisClick(row, false, rowIndex)">Fail</argon-button>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>

                                </div>
                            </div>



                            <el-dialog v-model="DiagnosisNextFlag" width="50%" @close="CloseDiagnosis()"
                                :close-on-click-modal="false" title="Please select the result of the following testcases">
                                <!-- 故障定位过程中 的表格 -->
                                <div v-if="!DiagnosisTestCases[DiagnosisTestCases.length - 1].hasOwnProperty('testcaseResult')"
                                    class="row">
                                    <div class="spinner-border  text-success col-4" role="status">
                                    </div>
                                    <div class="col-10 my-1">
                                        <p>CitHub is waiting for interaction, Choose the result of one of the following
                                            testcases.
                                        </p>
                                    </div>

                                </div>
                                <table class="table table-bordered" style="width: 100%;">

                                    <thead>
                                        <tr>
                                            <th class="text-center">#</th>
                                            <th v-for="(parameter, index) in currentModel.currentModel.PandVOBJ"
                                                :key="index" class="text-center">
                                                {{ parameter.Parameter }}
                                            </th>
                                            <!-- 使用Bootstrap的栅格系统调整Operation列的宽度 -->
                                            <th v-if="DignosisRunFlag && DiagnosisNextFlag" class="text-center col-2">
                                                Result of TestCase </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="(row, rowIndex) in DiagnosisTestCases" :key="rowIndex" :class="{
                                            // 'table-info': row.status == 'new',
                                            // 'table-primary': row.status == 'existed',
                                            'table-success': row.testcaseResult == true,
                                            'table-danger': row.testcaseResult == false
                                        }">

                                            <td class="text-center">
                                                {{ rowIndex + 1 }}
                                            </td>

                                            <!-- 如果row.status为 new/existed 则在对应的行前显示 new/ existed ，否则显示行号 -->

                                            <!-- <td v-if="row.status != 'new' && row.status != 'existed'" class="text-center">
                                                {{ rowIndex + 1 }}
                                            </td>

                                            <td v-if="row.status == 'new'" class="text-center">
                                                <ArgonBadge color="info"> New </ArgonBadge>
                                            </td>
                                            <td v-if="row.status == 'existed'" class="text-center">
                                                <ArgonBadge color="primary"> Existed </ArgonBadge>
                                            </td> -->

                                            <td v-for="(value, colIndex) in row.testcase" :key="colIndex"
                                                class="text-center">
                                                {{ value }}
                                            </td>

                                            <td v-if="DignosisRunFlag && DiagnosisNextFlag && row.hasOwnProperty('testcaseResult')"
                                                class="text-center">
                                                <div class="row">
                                                    <div class="col">
                                                        <ArgonBadge :color="row.testcaseResult ? 'success' : 'danger'">{{
                                                            row.testcaseResult ? ' Pass ' : 'Fail' }}</ArgonBadge>
                                                    </div>
                                                </div>
                                            </td>
                                            <td v-if="DignosisRunFlag && DiagnosisNextFlag && !row.hasOwnProperty('testcaseResult')"
                                                class="text-center">
                                                <div class="row">
                                                    <div class="col">
                                                        <argon-button full-width color="success"
                                                            @click="handleDiagnosisClick(row.testcase, true, rowIndex)">Pass</argon-button>
                                                    </div>
                                                    <div class="col">
                                                        <argon-button full-width color="danger"
                                                            @click="handleDiagnosisClick(row.testcase, false, rowIndex)">Fail</argon-button>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr class="border-0"></tr>
                                    </tbody>
                                </table>
                                <template #footer>
                                    <div class="dialog-footer">
                                        <el-button @click="DiagnosisNextFlag = !DiagnosisNextFlag">Cancel</el-button>
                                    </div>
                                </template>
                            </el-dialog>




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
import { onMounted, reactive, ref, computed, watch, onBeforeMount } from 'vue';
import { request } from '../request';
import Foot from '../CustomizedComponents/Foot.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import SideNav from './components/SideNav.vue'
import ArgonBadge from '../CustomizedComponents/ArgonBadge.vue';
import ArgonButton from '../CustomizedComponents/ArgonButton.vue'
import pinia from '../store/store'
import { useUserStore } from '../store/userStore';
import { useModelsStore } from '../store/ToolsStore/modelsStore'
import TestSuitesTable from './components/TestSuitesTable.vue'
import ArgonInput from '../CustomizedComponents/ArgonInput.vue';
import { useCurrentModel } from '../store/ToolsStore/currentModel'
import { ElNotification } from 'element-plus'
import { useTestSuitesStore } from '../store/ToolsStore/testSuitesStore'
import { useCurrentTestSuitesStore } from '../store/ToolsStore/currentTestSuite'
import { listAllTestSuitesByModelID, listModelInfoByModelID, CitHubModel, CitHubTestSuite } from './commonFunction.js';
import toolsInfo from "../CustomizedComponents/tools_info.json";
import * as echarts from 'echarts'
import { ElLoading } from 'element-plus'
import { nextTick } from 'vue';
import { useModuleStore } from '../store/module';
import { filter } from 'jszip';


const moduleStore = useModuleStore(pinia)
const testSuitesStore = useTestSuitesStore(pinia)
const currentModel = useCurrentModel(pinia)
const currentTestSuite = useCurrentTestSuitesStore(pinia)
const router = useRouter();
const route = useRoute()


const TestTableListFlag = ref(false)


// 将覆盖表显示为Html表格
const CoveringArray = ref([]);
const getValueFromArrays = async (objArray, indexArray) => {
    // console.log("objArray",objArray)
    // console.log("indexArray",indexArray)
    CoveringArray.value.length = 0
    indexArray.forEach((indexes) => {
        let values = indexes.map((index, i) => {
            const parameter = objArray[i].Parameter;
            const valueArray = objArray[i].Value;

            if (valueArray && index < valueArray.length) {
                return valueArray[index];
            } else {
                return null; // 处理超出索引范围的情况
            }
        });

        CoveringArray.value.push(values);
    });
};
const StrengthOptions = [
    {
        value: '2',
        label: '2',
    },
    {
        value: '3',
        label: '3',
    },
    {
        value: '4',
        label: '4',
    },
    {
        value: '5',
        label: '5',
    },
    {
        value: '6',
        label: '6',
    },
]
const strength = ref()


const inAPIcall = ref(false)



// ---------------For Re-generation-----------------------
const AlgorithmChosed = ref('');
const AlgorithmOptions = reactive([]);
const AlgorithmDescription = ref('');

const listAllGenerationAlgorithm = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "Generation" && !tool.title.includes("GUI")) {
            AlgorithmOptions.push({ "value": tool.title, "label": tool.title, "url": tool.url, "description": tool.description });
        }
    }
};
const handleAlgorithmChange = () => {
    const selectedAlgorithm = AlgorithmOptions.find(option => option.value === AlgorithmChosed.value);
    if (selectedAlgorithm) {
        AlgorithmDescription.value = selectedAlgorithm.description;
    } else {
        AlgorithmDescription.value = '';
    }
};

const dialogFormVisibleNew = ref(false)
const dialogformNewTestSuites = reactive({
    testsuitesname: currentTestSuite.currentTestSuites.testsuitesname,
    testsuitesdescriptions: currentTestSuite.currentTestSuites.testsuitesdescriptions,
    lastupdatedtime: '',
    createdtime: '',
    modelid: route.query.modelid
})
const showdialogNew = () => {
    dialogFormVisibleNew.value = true
    dialogformNewTestSuites.modelid = route.query.modelid
}

const confirmGenerateNewTestSuites = async () => {
    inAPIcall.value = true
    // 获取当前时刻的Date对象
    const currentDate = new Date();
    dialogformNewTestSuites.lastupdatedtime = currentDate
    dialogformNewTestSuites.createdtime = currentDate
    // console.log("currentModel.currentModel", currentModel.currentModel)

    if (currentModel.currentModel.modelname == "") {
        ElNotification({
            title: 'Generate TestSuites fail!',
            message: 'Model Name can not be empty!',
            type: 'error',
        })
    }

    else {
        // 发送请求，对 model 得到 test suites
        for (const tool of AlgorithmOptions) {
            if (tool.value == AlgorithmChosed.value) {
                try {
                    // 构造CitHub Model，调用工具
                    currentModel.currentModel.CitHubModel = CitHubModel(currentModel.currentModel, parseInt(strength.value))
                    // console.log("generation cithub model", currentModel.currentModel.CitHubModel)

                    const TestSuitesRes = await request({
                        // 这里记得改回去，在校外无法用校内服务器
                        url: tool.url,
                        // url: 'http://localhost:8300',
                        method: 'POST',
                        // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                        headers: {
                            'Content-Type': 'text/plain'
                        },
                        // 转为Json
                        data: JSON.stringify(currentModel.currentModel.CitHubModel).replace(/"/g, '')
                    })

                    // console.log("TestSuitesRes", TestSuitesRes)
                    const newTestSuitesRes = await request({
                        url: "/tools/Save",
                        method: "POST",
                        data:
                        {

                            column: 'generation',
                            // testsuitesname: dialogformNewTestSuites.testsuitesname,
                            // testsuitesdescriptions: dialogformNewTestSuites.testsuitesdescriptions,
                            lastupdatedtime: dialogformNewTestSuites.lastupdatedtime,
                            // createdtime: dialogformNewTestSuites.createdtime,
                            modelid: currentTestSuite.currentTestSuites.modelid,
                            testsuiteid: currentTestSuite.currentTestSuites.testsuitesid,
                            testsuitescontents: JSON.stringify(TestSuitesRes.testsuite),
                            generationtime: TestSuitesRes.time,
                            size: TestSuitesRes.size,
                            generationtool: AlgorithmChosed.value,
                            strength: strength.value
                        }
                    })
                    // console.log("newTestSuitesRes", newTestSuitesRes)

                    if (newTestSuitesRes.NewStatus == 'success!') {

                        // 更新Testsuite Info区域的信息
                        await UpdateTestSuiteInfo()

                        ElNotification({
                            title: 'Generate Success!',
                            type: 'success',
                            message: 'Strength ' + currentTestSuite.currentTestSuites.strength + ' test suite' + ' by ' + AlgorithmChosed.value + ' generated success! Please check the results.',

                        })
                        inAPIcall.value = false
                        dialogFormVisibleNew.value = false
                        testSuiteTable.value.scrollIntoView({ behavior: 'smooth' });


                    }
                    else {
                        inAPIcall.value = false
                        dialogFormVisibleNew.value = false
                        ElNotification({
                            title: 'Generate fail!',
                            type: 'error',
                        })



                    }
                }
                catch (err) {
                    console.log("err", err)
                    inAPIcall.value = false
                    dialogFormVisibleNew.value = false
                    ElNotification({
                        title: 'Generate fail!',
                        type: 'error',
                    })



                }
            }
        }
    }


}
// --------------------------------------------------------



// ---------------for Prioritization-----------------------
const dialogFormVisibleNewPrioritization = ref(false)

const showdialogNewPrioritization = () => {
    dialogFormVisibleNewPrioritization.value = true
    dialogformNewTestSuites.modelid = route.query.modelid
}

const AlgorithmChosedPrioritization = ref('')
const AlgorithmOptionsPrioritization = reactive([])
const AlgorithmDescriptionPrioritization = ref('');

const listAllPrioritisationOptions = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "Prioritisation") {
            AlgorithmOptionsPrioritization.push({ "value": tool.title, "label": tool.title, "url": tool.url, "description": tool.description })
        }
    }
}

const handleAlgorithmOptionsPrioritizationChange = () => {
    const selectedAlgorithm = AlgorithmOptionsPrioritization.find(option => option.value === AlgorithmChosedPrioritization.value);
    if (selectedAlgorithm) {
        AlgorithmDescriptionPrioritization.value = selectedAlgorithm.description;
    } else {
        AlgorithmDescriptionPrioritization.value = '';
    }
};

// MiniArray 工具的 权重 Weight
let Weight = reactive([])
const confirmNewPrioritisation = async () => {

    Weight = Array(currentModel.currentModel.PandVOBJ.length).fill(0)

    // console.log("currentModel", currentModel.currentModel)
    // console.log("currentTestSuites", currentTestSuite.currentTestSuites)
    inAPIcall.value = true


    // 构造发送给Prioritisation的 CitHubTestSuite 对象

    currentModel.currentModel.CitHubModel = CitHubModel(currentModel.currentModel, parseInt(currentTestSuite.currentTestSuites.strength))
    currentTestSuite.currentTestSuites.CitHubTestSuite = CitHubTestSuite(currentModel.currentModel.CitHubModel, currentTestSuite.currentTestSuites.testsuitescontents)

    // SortArray的特殊输入 Weight
    let WeightArray = Weight.map(Number)
    currentTestSuite.currentTestSuites.CitHubTestSuite.weight = WeightArray


    // console.log("CithubTestSuite", JSON.stringify(currentTestSuite.currentTestSuites.CitHubTestSuite).replace(/"/g, ''))
    for (const tool of AlgorithmOptionsPrioritization) {
        if (tool.value == AlgorithmChosedPrioritization.value) {
            try {
                const PrioritisationRes = await request({
                    url: tool.url,
                    // 这里记得改回去，在校外无法用校内服务器
                    // url: 'http://localhost:8304',
                    method: 'POST',
                    // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                    headers: {
                        'Content-Type': 'text/plain'
                    },
                    data: JSON.stringify(currentTestSuite.currentTestSuites.CitHubTestSuite).replace(/"/g, '')
                })
                // console.log("PrioritisationRes", PrioritisationRes)

                const SavePrioritisationRes = await request({
                    url: "/tools/Save",
                    method: "POST",
                    data:
                    {
                        column: 'prioritisation',
                        lastupdatedtime: new Date(),
                        modelid: currentTestSuite.currentTestSuites.modelid,
                        testsuiteid: currentTestSuite.currentTestSuites.testsuitesid,
                        testsuitescontents: JSON.stringify(PrioritisationRes.testsuite),
                        prioritisationtime: PrioritisationRes.time,
                        prioritisationtool: AlgorithmChosedPrioritization.value,

                    }
                })

                if (SavePrioritisationRes.NewStatus == 'success!') {

                    // 更新Testsuite Info区域的信息
                    await UpdateTestSuiteInfo()

                    ElNotification({
                        title: 'Prioritize Success!',
                        type: 'success',
                        message: 'Strength ' + currentTestSuite.currentTestSuites.strength + ' test suite' + ' by ' + AlgorithmChosedPrioritization.value + ' prioritized success! Please check the results.',


                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewPrioritization.value = false
                    testSuiteTable.value.scrollIntoView({ behavior: 'smooth' });
                }
                else {
                    ElNotification({
                        title: 'Prioritize fail!',
                        type: 'error',
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewPrioritization.value = false

                }
            }
            catch (err) {

                ElNotification({
                    title: 'Prioritize fail!',
                    type: 'error',
                    message: err.message
                })
                inAPIcall.value = false
                dialogFormVisibleNewPrioritization.value = false
            }
        }



    }
}
// --------------------------------------------------------


// ---------------for Reduction-----------------------
const dialogFormVisibleNewReduction = ref(false)
const showdialogNewReduction = () => {
    dialogFormVisibleNewReduction.value = true
    dialogformNewTestSuites.modelid = route.query.modelid
}
const AlgorithmChosedReduction = ref('')
const AlgorithmOptionsReduction = reactive([])
const AlgorithmDescriptionReduction = ref('');

const listAllReductionOptions = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "Reduction") {
            AlgorithmOptionsReduction.push({ "value": tool.title, "label": tool.title, "url": tool.url, "description": tool.description })
        }
    }
}

const handleAlgorithmOptionsReductionChange = () => {
    const selectedAlgorithm = AlgorithmOptionsReduction.find(option => option.value === AlgorithmChosedReduction.value);
    if (selectedAlgorithm) {
        AlgorithmDescriptionReduction.value = selectedAlgorithm.description;
    } else {
        AlgorithmDescriptionReduction.value = '';
    }
};

const confirmNewReduction = async () => {
    inAPIcall.value = true

    // 构造发送给 Reduction 的 CitHubTestSuite 对象

    currentModel.currentModel.CitHubModel = CitHubModel(currentModel.currentModel, parseInt(currentTestSuite.currentTestSuites.strength))
    currentTestSuite.currentTestSuites.CitHubTestSuite = CitHubTestSuite(currentModel.currentModel.CitHubModel, currentTestSuite.currentTestSuites.testsuitescontents)
    // console.log("CithubTestSuite", JSON.stringify(currentTestSuite.currentTestSuites.CitHubTestSuite).replace(/"/g, ''))



    for (const tool of AlgorithmOptionsReduction) {
        if (tool.value == AlgorithmChosedReduction.value) {

            try {
                const ReductionRes = await request({
                    url: tool.url,
                    // url: 'http://localhost:8305',
                    method: 'POST',
                    // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                    headers: {
                        'Content-Type': 'text/plain'
                    },
                    data: JSON.stringify(currentTestSuite.currentTestSuites.CitHubTestSuite).replace(/"/g, '')
                })
                // console.log("ReductionRes", ReductionRes)
                const newReductionRes = await request({
                    url: "/tools/Save",
                    method: "POST",
                    data:
                    {
                        column: 'reduction',
                        lastupdatedtime: new Date(),
                        modelid: currentTestSuite.currentTestSuites.modelid,
                        testsuiteid: currentTestSuite.currentTestSuites.testsuitesid,
                        testsuitescontents: JSON.stringify(ReductionRes.testsuite),
                        reductiontime: ReductionRes.time,
                        sizeafterreduction: ReductionRes.size,
                        reductiontool: AlgorithmChosedReduction.value,

                    }
                })

                if (newReductionRes.NewStatus == 'success!') {

                    // 更新Testsuite Info区域的信息
                    await UpdateTestSuiteInfo()


                    ElNotification({
                        title: 'Reduct Success!',
                        type: 'success',
                        message: 'Strength ' + currentTestSuite.currentTestSuites.strength + ' test suite' + ' by ' + AlgorithmChosedReduction.value + ' reducted success! Please check the results.',

                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewReduction.value = false
                    testSuiteTable.value.scrollIntoView({ behavior: 'smooth' });


                }
                else {
                    ElNotification({
                        title: 'Reduct fail!',
                        type: 'error',
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewReduction.value = false

                }
            }
            catch (err) {
                ElNotification({
                    title: 'Reduct fail!',
                    type: 'error',
                })
                inAPIcall.value = false
                dialogFormVisibleNewReduction.value = false
            }
        }




    }

}
// --------------------------------------------------------


// ---------------for Conversion-----------------------
const dialogFormVisibleNewConversion = ref(false)
const showdialogNewConversion = () => {
    dialogFormVisibleNewConversion.value = true
    dialogformNewTestSuites.modelid = route.query.modelid
}
const AlgorithmChosedConversion = ref('')
const AlgorithmOptionsConversion = reactive([])
const AlgorithmDescriptionConversion = ref('');
const handleAlgorithmOptionsConversionChange = () => {
    const selectedAlgorithm = AlgorithmOptionsConversion.find(option => option.value === AlgorithmChosedConversion.value);
    if (selectedAlgorithm) {
        AlgorithmDescriptionConversion.value = selectedAlgorithm.description;
    } else {
        AlgorithmDescriptionConversion.value = '';
    }
};

const listAllConversionOptions = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "FormatConversion" && !tool.title.includes("Reader")) {
            AlgorithmOptionsConversion.push({ "value": tool.title, "label": tool.title, "url": tool.url, "description": tool.description })
        }
    }
}
const functionHead = ref('eg:`public void test()`')
const functionBody = ref('eg:`CIT cit= new CIT(&p2) cit.execuse(&p3) cit.process(&p1)`')
const outputModel = ref('eg:`first,we should do &#,then open &#,finally,enter in the browser address bar with &#`')
const confirmNewConversion = async () => {

    inAPIcall.value = true
    // 构造发送给Conversion的data对象
    let conversionObj = {}
    conversionObj.testsuite = currentTestSuite.currentTestSuites.testsuitescontents
    // console.log("conversionObj.testsuite",conversionObj.testsuite)
    // console.log("currentModel.currentModel",currentModel.currentModel)
    let actual_value = []
    for (const pv of currentModel.currentModel.PandVOBJ) {
        actual_value.push(pv.Value)
    }
    conversionObj.actual_value = actual_value


    if (AlgorithmChosedConversion.value == 'NL Test Plan') {
        conversionObj.outputModel = outputModel.value
        conversionObj = JSON.stringify(conversionObj)

        for (const tool of AlgorithmOptionsConversion) {
            if (tool.value == AlgorithmChosedConversion.value) {
                try {
                    const ConversionRes = await request({
                        // 这里记得改回去，在校外无法用校内服务器
                        url: tool.url,
                        // url: 'http://localhost:8302',
                        method: 'POST',
                        // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                        headers: {
                            'Content-Type': 'text/plain'
                        },
                        data: conversionObj
                    })
                    // Convert the response data to a Blob
                    const blob = new Blob([ConversionRes.testsuiteOfConversion], { type: 'text/plain' });

                    // Create a link element
                    const downloadLink = document.createElement('a');
                    downloadLink.href = window.URL.createObjectURL(blob);

                    // Set the download attribute and filename
                    downloadLink.download = currentTestSuite.currentTestSuites.testsuitesname + '_' + tool.value + '_result.txt'

                    // Append the link to the document
                    document.body.appendChild(downloadLink);

                    // Trigger a click on the link to start the download
                    downloadLink.click();

                    // Remove the link from the document
                    document.body.removeChild(downloadLink);

                    ElNotification({
                        title: 'Convert Success!',
                        type: 'success',

                    })

                    // console.log("ConversionRes", ConversionRes)
                    inAPIcall.value = false
                    dialogFormVisibleNewConversion.value = false

                }
                catch (err) {
                    ElNotification({
                        title: 'Convert fail!',
                        type: 'error',
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewConversion.value = false
                }
            }
        }

    }


    if (AlgorithmChosedConversion.value == 'JUnit Test Plan') {
        conversionObj.functionHead = functionHead.value
        conversionObj.functionBody = functionBody.value
        conversionObj = JSON.stringify(conversionObj)

        for (const tool of AlgorithmOptionsConversion) {
            if (tool.value == AlgorithmChosedConversion.value) {
                try {
                    const ConversionRes = await request({
                        // 这里记得改回去，在校外无法用校内服务器
                        url: tool.url,
                        // url: 'http://localhost:8303',
                        method: 'POST',
                        // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                        headers: {
                            'Content-Type': 'text/plain'
                        },
                        data: conversionObj
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewConversion.value = false
                    // Convert the response data to a Blob
                    const blob = new Blob([ConversionRes.testsuiteOfJUnit], { type: 'text/plain' });

                    // Create a link element
                    const downloadLink = document.createElement('a');
                    downloadLink.href = window.URL.createObjectURL(blob);

                    // Set the download attribute and filename
                    downloadLink.download = currentTestSuite.currentTestSuites.testsuitesname + '_' + tool.value + '_result.txt'

                    // Append the link to the document
                    document.body.appendChild(downloadLink);

                    // Trigger a click on the link to start the download
                    downloadLink.click();

                    // Remove the link from the document
                    document.body.removeChild(downloadLink);
                    ElNotification({
                        title: 'Convert Success!',
                        type: 'success',

                    })

                }
                catch (err) {

                    ElNotification({
                        title: 'Convert fail!',
                        type: 'error',
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewConversion.value = false
                }

            }
        }



    }

}

// --------------------------------------------------------

// -------------------------For Evaluation------------------------------

const dialogFormVisibleNewEvaluation = ref(false)
const showdialogNewEvaluation = () => {
    dialogFormVisibleNewEvaluation.value = true
    dialogformNewTestSuites.modelid = route.query.modelid
}
const AlgorithmChosedEvaluation = ref('')
const AlgorithmOptionsEvaluation = reactive([])
const AlgorithmDescriptionEvaluation = ref('');
const listAllEvaluationAlgorithm = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "Evaluation" && !tool.title.includes("GUI")) {
            AlgorithmOptionsEvaluation.push({ "value": tool.title, "label": tool.title, "url": tool.url, "description": tool.description })
        }
    }
}
const handleAlgorithmOptionsEvaluationChange = () => {
    const selectedAlgorithm = AlgorithmOptionsEvaluation.find(option => option.value === AlgorithmChosedEvaluation.value);
    if (selectedAlgorithm) {
        AlgorithmDescriptionEvaluation.value = selectedAlgorithm.description;
    } else {
        AlgorithmDescriptionEvaluation.value = '';
    }
};

// 用于画coverage checker的折线图

const lineChart = ref();
let options = reactive({
    tooltip: {
        trigger: 'axis',
        formatter: function (params) {
            // console.log("params",params)
            const name = params[0].name
            const ratio = (params[0].value * 100).toFixed(2) + '%';
            return `Num of Combinations: ${name} <br> Coverage: ${ratio}`;
        }
    },
    title: {
        text: 'Coverage Changes',
        left: 'center', // 居中
        top: 'top', // 顶部对齐
        textAlign: 'center' // 文本居中
    },
    xAxis: {
        type: 'category',
        data: [],
        name: '# of Combinations',
        nameLocation: 'center', //坐标轴名称显示位置，可选值start/middle[或者center]/end
        position: 'bottom', //轴的位置（top/bottom
        nameGap: 25 // 轴名称与轴线之间的距离
    },
    yAxis: {
        type: 'value',
        min: 0,
        max: 1,
        name: 'Ratio of Coverage',
        axisLabel: {
            formatter: function (value) {
                return (value * 100).toFixed(2) + '%';
            }
        }
    },
    series: [{
        type: 'line',
        data: [],
        lineStyle: {
            color: 'green' // Set line color to green
        },
    }]
})
const InitEvaluationChart = async () => {
    await nextTick();
    try {
        // 在这里使用 nextTick 来确保 DOM 元素已经渲染完成
        // 如果 Evaluation 为Coverage Checker 则画图
        // console.log("InitEvaluationChart currentTestSuite.currentTestSuites", currentTestSuite.currentTestSuites)
        if (currentTestSuite.currentTestSuites.evaluationtool == 'Coverage Checker') {
            let data = new Set(currentTestSuite.currentTestSuites.evaluationcontents)
            data = Array.from(data)
            let coverage = Array.from(data).map(num => {
                return num / data[data.length - 1].toFixed(4)
            })
            // console.log("data",data)
            // console.log("coverage",coverage)

            options.xAxis.data = data
            options.series[0].data = coverage

            // console.log("lineChart.value", lineChart.value)
            let myChart = echarts.init(lineChart.value);
            myChart.setOption(options);
            myChart.resize();

        }
    } catch (error) {
        console.log("InitEvaluationChart", error)
    }


}

const confirmNewEvaluation = async () => {
    inAPIcall.value = true

    currentModel.currentModel.CitHubModel = CitHubModel(currentModel.currentModel, parseInt(currentTestSuite.currentTestSuites.strength))
    currentTestSuite.currentTestSuites.CitHubTestSuite = CitHubTestSuite(currentModel.currentModel.CitHubModel, currentTestSuite.currentTestSuites.testsuitescontents)

    // 发送请求，对  test suites 得到 evalution
    for (const tool of AlgorithmOptionsEvaluation) {
        if (tool.value == AlgorithmChosedEvaluation.value) {
            try {
                const EvaluationRes = await request({
                    // 这里记得改回去，在校外无法用校内服务器
                    url: tool.url,
                    // url: 'http://localhost:8301',
                    method: 'POST',
                    // 注意这里headers一定要加上，不然data末尾会出现莫名其妙的:
                    headers: {
                        'Content-Type': 'text/plain'
                    },
                    data: JSON.stringify(currentTestSuite.currentTestSuites.CitHubTestSuite).replace(/"/g, '')
                })
                // console.log("EvaluationRes", EvaluationRes)
                const newEvaluationRes = await request({
                    url: "/tools/Save",
                    method: "POST",
                    data:
                    {
                        column: 'evaluation',
                        lastupdatedtime: new Date(),
                        modelid: currentTestSuite.currentTestSuites.modelid,
                        testsuiteid: currentTestSuite.currentTestSuites.testsuitesid,
                        EvaluationContents: JSON.stringify(EvaluationRes.coverArray),
                        evaluationtime: EvaluationRes.time,
                        evaluationtool: AlgorithmChosedEvaluation.value,

                    }
                })

                if (newEvaluationRes.NewStatus == 'success!') {
                    // 更新Testsuite Info区域的信息
                    await UpdateTestSuiteInfo()


                    ElNotification({
                        title: 'Evaluate Success!',
                        type: 'success',
                        message: 'Please check the evaluation result',

                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewEvaluation.value = false


                }
                else {
                    ElNotification({
                        title: 'Evaluate fail!',
                        type: 'error',
                    })
                    inAPIcall.value = false
                    dialogFormVisibleNewEvaluation.value = false

                }


            }
            catch (err) {
                console.log("err", err)
                ElNotification({
                    title: 'Evaluate fail!',
                    type: 'error',
                })
                dialogFormVisibleNewEvaluation.value = false
                inAPIcall.value = false


            }
        }
    }

}

// --------------------------Diagnosis------------------------------

const dialogFormVisibleNewDiagnosis = ref(false)
const showdialogNewDiagnosis = () => {
    dialogFormVisibleNewDiagnosis.value = true
    dialogformNewTestSuites.modelid = route.query.modelid
}
const AlgorithmChosedDiagnosis = ref('')
const AlgorithmOptionsDiagnosis = reactive([])
const AlgorithmDescriptionDiagnosis = ref('');
const listAllDiagnosisAlgorithm = () => {
    for (const tool of toolsInfo.RECORDS) {
        if (tool.type == "Diagnosis" && !tool.title.includes("GUI")) {
            AlgorithmOptionsDiagnosis.push({ "value": tool.title, "label": tool.title, "url": tool.url, "description": tool.description })
        }
    }
}
const handleAlgorithmOptionsDiagnosisChange = () => {
    const selectedAlgorithm = AlgorithmOptionsDiagnosis.find(option => option.value === AlgorithmChosedDiagnosis.value);
    if (selectedAlgorithm) {
        AlgorithmDescriptionDiagnosis.value = selectedAlgorithm.description;
    } else {
        AlgorithmDescriptionDiagnosis.value = '';
    }
};

// 用于定位 testSuiteTable 的Ref
const testSuiteTable = ref()
// 用于定位 TestSuiteInfo 中的 DiagnosisInfo
const TestSuiteInfo = ref()
// 判断故障定位过程是否结束
const DignosisRunFlag = ref(false)


// 弹出工具执行过程中的 next testcase 的对话框
const DiagnosisNextFlag = ref(false)
// 弹出对话框，选择算法。
const confirmNewDiagnosis = async () => {

    DignosisRunFlag.value = true
    dialogFormVisibleNewDiagnosis.value = false
    testSuiteTable.value.scrollIntoView({ behavior: 'smooth' });

    ElNotification({
        title: 'Diagnosis start.',
        type: 'success',
        message: 'Adaptive Dagnosis is running, Please select a failed testcase below to start interacting.',

    })

}



// 将实际测试用例转化为索引测试用例
const TestCaseToIndexesTestCase = (PandVOBJ, TableTestCase) => {
    return TableTestCase.map(value => {
        for (let i = 0; i < PandVOBJ.length; i++) {
            const parameter = PandVOBJ[i];
            const index = parameter.Value.indexOf(value);
            if (index !== -1) {
                return index + 1;
            }
        }
        return -1; // 如果值在任何参数的参数取值数组中都找不到，则返回 -1
    });
}
// 将索引测试用例转为抽象测试用例
function IndexesTestCaseToTestCase(PandVOBJ, indexesArray) {
    return indexesArray.map((index, parameterIndex) => {
        if (index === -1) {
            return null; // 如果索引为 -1，则对应的值为 null
        } else {
            return PandVOBJ[parameterIndex].Value[index - 1];
        }
    });
}

// 用于存储故障定位最终结果，即故障元组的数组
const FaultTuples = reactive([])
// 初始化故障定位工具
const InitDiagnosisTool = ref(true)
const NextValues = reactive([])
const nextCriteriaArray = reactive([])
const DiagnosisTestCases = reactive([])
const DiagnosisTestCasesRowIndex = ref()
// 点击按钮进行自适应的故障定位
const handleDiagnosisClick = async (testcase, testcaseResult, rowIndex) => {
    // console.log("实际测试用例", testcase)
    let loadingInstance = ElLoading.service({ fullscreen: true })
    let OBJ = {
        init: '',
        testcase: [],
        length: '',
        pass: ''
    }
    if (InitDiagnosisTool.value) {

        OBJ.init = InitDiagnosisTool.value
        // 首次点击，映射为参数取值的索引
        OBJ.testcase = TestCaseToIndexesTestCase(currentModel.currentModel.PandVOBJ, testcase)

        InitDiagnosisTool.value = false

        // 弹出next testcase 对话框
        DiagnosisNextFlag.value = true

        // 记录是对哪一行进行故障定位 即为首次点击的行
        DiagnosisTestCasesRowIndex.value = rowIndex

        // 加载故障定位过程表格的第一行数据行
        DiagnosisTestCases.length = 0
        DiagnosisTestCases.push({ testcase, testcaseResult })


    }
    else {
        // console.log("余下的点击，发送过程请求")
        InitDiagnosisTool.value = false
        // 余下的点击，直接用上一次的next即可
        OBJ.testcase.length = 0
        OBJ.testcase.push(...nextCriteriaArray)
        OBJ.init = InitDiagnosisTool.value

        //    更新点击的new行，删除其他没有点击的new行

        // console.log("总的DiagnosisTestCases",DiagnosisTestCases)
        // console.log("点击的行是",rowIndex)

        DiagnosisTestCases[rowIndex].testcaseResult = testcaseResult
        // 删除点击行的status key
        delete DiagnosisTestCases[rowIndex].status
        // console.log("删除点击行的stauts key之后的DiagnosisTestCases", DiagnosisTestCases)
        // 循环删除所有包含 status key 的元素
        while (true) {
            const index = DiagnosisTestCases.findIndex(item => item.hasOwnProperty('status'));
            if (index === -1) {
                break; // 如果没有找到包含 status key 的元素，则退出循环
            }
            DiagnosisTestCases.splice(index, 1); // 删除找到的元素
        }

        // console.log("再删除所有有 status key 的元素之后的 DiagnosisTestCases", DiagnosisTestCases);


        // console.log("更新最新的testcase的 DiagnosisTestCases", DiagnosisTestCases)

    }
    // 构造发送给 自适应故障定位工具的OBJ

    OBJ.length = OBJ.testcase.length
    OBJ.pass = testcaseResult


    for (const tool of AlgorithmOptionsDiagnosis) {
        if (tool.value == AlgorithmChosedDiagnosis.value) {
            try {
                const DiagnosisRes = await request({
                    // url: tool.url,
                    url: 'http://127.0.0.1:9977/',
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    },
                    data: JSON.stringify(OBJ)

                })
                // console.log("DiagnosisRes", DiagnosisRes)
                // console.log("DiagnosisRes Json", JSON.stringify(DiagnosisRes))

                if (DiagnosisRes.finished) {

                    // 故障定位完成，将结果转换为最小故障元组
                    let ValuesArray = DiagnosisRes.result.map(FaultTuple => {
                        return IndexesTestCaseToTestCase(currentModel.currentModel.PandVOBJ, FaultTuple);
                    });
                    FaultTuples.length = 0
                    FaultTuples.push(...convertTestcaseToFaultTuples(ValuesArray, currentModel.currentModel.PandVOBJ))
                    let diagnosiscontents =
                    {
                        DiagnosisTestCasesRowIndex: DiagnosisTestCasesRowIndex.value,
                        FaultTuples,
                    }
                    // console.log("FaultTuples", FaultTuples)

                    // 保存故障定位的结果
                    const newDiagnosisRes = await request({
                        url: "/tools/Save",
                        method: "POST",
                        data:
                        {

                            column: 'diagnosis',
                            lastupdatedtime: new Date(),
                            modelid: currentTestSuite.currentTestSuites.modelid,
                            testsuiteid: currentTestSuite.currentTestSuites.testsuitesid,
                            diagnosiscontents: JSON.stringify(diagnosiscontents),
                            diagnosistool: AlgorithmChosedDiagnosis.value,
                        }
                    })
                    // console.log("newTestSuitesRes", newTestSuitesRes)

                    if (newDiagnosisRes.NewStatus == 'success!') {

                        // 更新Testsuite Info区域的信息
                        await UpdateTestSuiteInfo()

                        // 故障定位完成 关闭running的提示
                        DignosisRunFlag.value = false

                        // 置位为第一次发送请求
                        InitDiagnosisTool.value = true
                        // 关闭定位过程对话框
                        DiagnosisNextFlag.value = false
                        // console.log("故障定位已完成", DiagnosisRes)

                        // 视窗滚动到显示故障定位的结果
                        TestSuiteInfo.value.scrollIntoView({ behavior: 'smooth' });


                        loadingInstance.close()


                    }
                    else {

                        ElNotification({
                            title: 'Save Failed!',
                            type: 'error',

                        })
                        loadingInstance.close()

                    }




                }
                else {
                    // 故障定位未完成，根据返回的result 重新渲染表格内容让用户点按钮

                    // 先 将 DiagnosisRes.next 数组映射为参数的实际取值的数组
                    NextValues.length = 0
                    NextValues.push(...IndexesTestCaseToTestCase(currentModel.currentModel.PandVOBJ, DiagnosisRes.next))
                    // console.log("下次交互需要知道Pass/Fail的测试用例 NextValues", NextValues)
                    // console.log("参数和参数取值", currentModel.currentModel)
                    // console.log("NextValues 填充Null值后的结果", FillNull(currentModel.currentModel.PandVOBJ, NextValues))


                    // 下面想去除掉 NextValues 填充Null之后 那些不满足约束的testcase
                    let ForbiddenTuplesFilled = []
                    // 首先将所有禁止元组填充Null
                    currentModel.currentModel.ForbiddenTuples.forEach(ForbiddenTuple => {

                        // console.log("对",ForbiddenTuple,"填充Null")
                        // console.log("填充结果为",FillNull(currentModel.currentModel.PandVOBJ, ForbiddenTuple))
                        // FillNull(currentModel.currentModel.PandVOBJ, ForbiddenTuple)
                        ForbiddenTuplesFilled.push(...FillNull(currentModel.currentModel.PandVOBJ, ForbiddenTuple))

                    });
                    // ForbiddenTuplesFilled = Array.from(new Set(ForbiddenTuplesFilled.map(JSON.stringify)), JSON.parse);
                    // console.log("禁止元组的填充结果为", ForbiddenTuplesFilled)

                    // NextValues 减去 ForbiddenTuplesFilled，剩下的即为满足约束的测试用例 NextValuesSat
                    let NextValuesSat = subtractArrays(FillNull(currentModel.currentModel.PandVOBJ, NextValues), ForbiddenTuplesFilled)
                    // console.log("去除不满足约束之后的 NextValuesSat", NextValuesSat)


                    // 根据故障定位表格中的点击情况，动态的更新表格


                    // NextValuesSat 出现在覆盖表中的testcase的status标记为 existed ，未出现在覆盖表中的标记为 new
                    // console.log("覆盖表", CoveringArray.value)

                    NextValuesSat.forEach(nextTestCase => {
                        let found = false;
                        CoveringArray.value.forEach(CATestCase => {
                            if (arraysAreEqual(nextTestCase, CATestCase)) {
                                DiagnosisTestCases.push({
                                    "testcase": nextTestCase,
                                    "status": "existed"
                                });
                                found = true; // 标记为找到匹配的情况
                                // console.log("相等", nextTestCase, CATestCase)
                            }
                        });
                        // 如果未找到匹配的CATestCase，则将nextTestCase标记为新的测试用例
                        if (!found) {
                            DiagnosisTestCases.push({
                                "testcase": nextTestCase,
                                "status": "new"
                            });
                        }
                        // 将 found 重新置为 false，以便下一个测试用例进行判断
                        found = false;
                    });

                    // console.log("划分status之后的DiagnosisTestCases", JSON.stringify(DiagnosisTestCases))

                    // 对DiagnosisTestCases进行排序，已有结果的testcase在最前方，其次是覆盖表中已有的testcase，最后是新的testcase
                    // 提取具有"status"键和没有"status"键的元素

                    const TestCasesWithStatus = DiagnosisTestCases.filter(element => element.hasOwnProperty("status"));
                    const TestCasesWithoutStatus = DiagnosisTestCases.filter(element => !element.hasOwnProperty("status"));

                    // 对具有"status"键的元素进行排序
                    TestCasesWithStatus.sort((a, b) => {
                        // 提取a和b的status属性
                        const statusA = a.status || "new"; // 默认为"new"
                        const statusB = b.status || "new"; // 默认为"new"

                        // 如果a的status为"existed"，b的status为"new"，则a排在b前面
                        if (statusA === "existed" && statusB === "new") {
                            return -1;
                        }
                        // 如果a的status为"new"，b的status为"existed"，则a排在b后面
                        else if (statusA === "new" && statusB === "existed") {
                            return 1;
                        }
                        // 其他情况保持原顺序
                        else {
                            return 0;
                        }
                    });

                    // 将没有"status"键的元素插入到排序后的数组的开头
                    const sortedDiagnosisTestCases = TestCasesWithoutStatus.concat(TestCasesWithStatus);
                    DiagnosisTestCases.length = 0
                    DiagnosisTestCases.push(...sortedDiagnosisTestCases)
                    // console.log("排序后的 DiagnosisTestCases", JSON.stringify(DiagnosisTestCases))

                    // 优化DiagnosisTestCases，去掉 testcase 重复，且已有结果的那些元素
                    // 这里有个bug，DiagnosisTestCases 去重时 可能会去掉 testcase 相同，但是又有 testcaseResult 和 status 并且 该  testcase 又是唯一一个 有 status 的元素
                    //  待修复

                    let uniqueTestcases = DiagnosisTestCases.reduce((acc, current) => {
                        // 检查是否已经存在具有相同"testcase"键的元素
                        const existingIndex = acc.findIndex(item => JSON.stringify(item.testcase) === JSON.stringify(current.testcase));
                        if (existingIndex !== -1) {
                            // 如果已经存在，则判断是否已有元素具有 "testcaseResult" 键，若有，则保留该元素
                            if (acc[existingIndex].hasOwnProperty("testcaseResult")) {
                                // 如果当前元素是整个数组中唯一一个具有 "status" 这个键的元素，则保留该元素
                                const hasStatusKey = acc.filter(item => item.hasOwnProperty("status")).length;
                                if (hasStatusKey === 1 && !current.hasOwnProperty("status")) {
                                    acc.push(current);
                                }
                                return acc;
                            } else {
                                // 若没有，则替换为当前元素
                                acc.splice(existingIndex, 1, current);
                                return acc;
                            }
                        }
                        // 如果不存在具有相同"testcase"键的元素，则将当前元素添加到结果数组中。
                        else {
                            acc.push(current);
                            return acc;
                        }
                    }, []);
                    DiagnosisTestCases.length = 0
                    DiagnosisTestCases.push(...uniqueTestcases)
                    // console.log("去重优化后的 DiagnosisTestCases", JSON.stringify(DiagnosisTestCases))

                    // 新！ existed 和 new 不需要区分了，因为这样给一堆 testcase 会让人 confuse ，给一条就可以了
                    // 做法是 遍历 DiagnosisTestCases ，有 testcaseResult 这个 key 的元素都保留下来，遇到第一个有 status 这个 key 的元素 保留，然后终止循环
                    let NewDiagnosisTestCases = []
                    for (const testcase of DiagnosisTestCases) {
                        if (testcase.hasOwnProperty('testcaseResult')) {
                            NewDiagnosisTestCases.push(testcase)
                        }
                        if (testcase.hasOwnProperty('status')) {
                            NewDiagnosisTestCases.push(testcase)
                            break;
                        }
                    }

                    DiagnosisTestCases.length = 0
                    DiagnosisTestCases.push(...NewDiagnosisTestCases)

                    // console.log("新版本的 DiagnosisTestCases", JSON.stringify(DiagnosisTestCases))


                    // 记录下下一次发送的testcase
                    nextCriteriaArray.length = 0
                    nextCriteriaArray.push(...DiagnosisRes.next)

                    loadingInstance.close()

                }
            } catch (error) {
                loadingInstance.close()
                console.log("Diagnosis error", error)

            }
        }
    }

}
const CloseDiagnosis = () => {

    // 故障定位完成
    if (DignosisRunFlag.value == false) {
        ElNotification({
            title: 'Diagnosis Success!',
            type: 'success',
            message: 'Possible Fault Tuples has Displayed in Table.',

        })
    }
    // 故障定位中断
    else {
        ElNotification({
            title: 'Diagnosis is interrupted.',
            type: 'warning',
            message: 'Diagnosis has been cancelled.',

        })
    }


    // 关闭running的提示
    DignosisRunFlag.value = false
    // 置位为第一次发送请求
    InitDiagnosisTool.value = true
    // 关闭定位过程对话框
    DiagnosisNextFlag.value = false
}


// 将实际的参数取值数组，变为Parameter=Value的字符串数组的形式，以表示故障元组
const convertTestcaseToFaultTuples = (testcaseArray, PandVOBJ) => {
    // 用于存储转换后的字符串数组
    let resultArray = [];

    // 遍历参数取值数组中的每个一维数组
    testcaseArray.forEach(values => {
        // 用于存储当前一维数组的转换结果
        let tempResult = "";

        // 遍历当前一维数组中的每个值
        values.forEach((value, i) => {
            if (value !== null && value !== undefined) {
                // 获取对应的参数名
                const parameter = PandVOBJ[i].Parameter;
                // 拼接参数名和对应的取值
                tempResult += `${parameter}=${value}, `;
            }
        });

        // 去除末尾的逗号和空格
        tempResult = tempResult.slice(0, -2);

        // 将当前一维数组的转换结果添加到结果数组中
        resultArray.push(tempResult);
    });

    return resultArray;
}

// 将testcase中为null的元素 替换为对应索引位置的参数的参数取值

const FillNull = (PandVOBJ, testCaseNull) => {
    const combinations = [];

    // 递归函数，用于生成所有可能的组合
    function generate(index, currentCombination) {
        if (index === testCaseNull.length) { // 如果已经填充完毕，将当前组合加入结果数组
            combinations.push(currentCombination.slice());
            return;
        }

        if (testCaseNull[index] !== null) { // 如果当前位置不是 null，则跳过
            generate(index + 1, currentCombination);
            return;
        }

        // 如果当前位置是 null，则遍历该参数的所有取值，进行组合
        PandVOBJ[index].Value.forEach(value => {
            currentCombination[index] = value;
            generate(index + 1, currentCombination);
        });
    }

    generate(0, testCaseNull.slice()); // 调用递归函数开始生成组合

    return combinations;
}

function subtractArrays(arr1, arr2) {
    const subtractedArray = [];

    // 遍历arr1中的每个一维数组
    for (let i = 0; i < arr1.length; i++) {
        let found = false;

        // 在arr2中查找与当前arr1[i]相同的一维数组
        for (let j = 0; j < arr2.length; j++) {
            if (arraysAreEqual(arr1[i], arr2[j])) {
                found = true;
                break;
            }
        }

        // 如果arr1中的一维数组在arr2中没有找到相同的，则将其添加到结果数组中
        if (!found) {
            subtractedArray.push(arr1[i]);
        }
    }

    return subtractedArray;
}

// 辅助函数：检查两个一维数组是否相等
function arraysAreEqual(arr1, arr2) {
    if (arr1.length !== arr2.length) {
        return false;
    }

    for (let i = 0; i < arr1.length; i++) {
        if (arr1[i] !== arr2[i]) {
            return false;
        }
    }

    return true;
}





// ------------------------------------------------------------------------

// --------------------------Export TestSuite------------------------------
const ExportTestSuite = () => {
    // 将表格数据转换为CSV格式
    const csvContent = generateCSV();
    // 从CSV内容创建Blob
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });

    // 创建用于下载的链接元素
    const downloadLink = document.createElement('a');
    downloadLink.href = window.URL.createObjectURL(blob);
    downloadLink.download = currentTestSuite.currentTestSuites.testsuitesname + '_testSuite.csv'
    // 触发链接上的点击以开始下载
    downloadLink.click();
}
const generateCSV = () => {
    // 从表格数据生成CSV内容
    const header = ['#', ...currentModel.currentModel.PandVOBJ.map(parameter => parameter.Parameter)];
    const rows = CoveringArray.value.map((row, index) => [index + 1, ...row]);
    const allRows = [header, ...rows];

    return allRows.map(row => row.join(',')).join('\n');
}


// --------------------------------------------------------

const goBack = () => {
    window.history.back();
}




// 监听 currentTestSuite.currentTestSuites.testsuitescontents 的变化
watch(() => currentTestSuite.currentTestSuites.testsuitescontents, (newValue, oldValue) => {
    // console.log("newValue",newValue) 
    // console.log("oldValue",oldValue) 
    // 在值发生变化时执行 getValueFromArrays 函数
    let loadingInstance = ElLoading.service({ fullscreen: true })

    if (typeof newValue != "undefined") {
        getValueFromArrays(currentModel.currentModel.PandVOBJ, newValue);
    }

    loadingInstance.close()

});

// 监听 currentTestSuite.currentTestSuites.evaluationcontents 的变化 以绘制图
watch(() => currentTestSuite.currentTestSuites.evaluationcontents, (newValue, oldValue) => {
    // console.log("触发 watch evaluationcontents")
    // currentTestSuite.currentTestSuites.evaluationcontents=newValue
    try {
        if (currentTestSuite.currentTestSuites.evaluationtool == 'Coverage Checker' && typeof newValue != "undefined") {
            InitEvaluationChart();
        }
    } catch (error) {
        console.log("watch EvaContents Error", error)
    }

});




const UpdateTestSuiteInfo = async () => {

    // 重新加载模型信息
    await listModelInfoByModelID(currentTestSuite.currentTestSuites.modelid)
    // 重新加载testsuite
    await listAllTestSuitesByModelID([currentModel.currentModel])

    // 更新Testsuite Info区域的信息
    for (let index = 0; index < testSuitesStore.testSuitesList[0].testSuites.length; index++) {
        if (currentTestSuite.currentTestSuites.testsuitesid == testSuitesStore.testSuitesList[0].testSuites[index].testsuitesid) {
            // console.log("相同的 testsuitesid", currentTestSuite.currentTestSuites.testsuitesid)
            for (let key in testSuitesStore.testSuitesList[0].testSuites[index]) {

                if (testSuitesStore.testSuitesList[0].testSuites[index].hasOwnProperty(key)) {
                    currentTestSuite.currentTestSuites[key] = testSuitesStore.testSuitesList[0].testSuites[index][key];
                    // console.log("testSuitesStore.testSuitesList[0].testSuites[index][key]",testSuitesStore.testSuitesList[0].testSuites[index][key])
                    // console.log("currentTestSuite.currentTestSuites[key]",currentTestSuite.currentTestSuites[key])
                }
            }

        }
    }

    await getValueFromArrays(currentModel.currentModel.PandVOBJ, currentTestSuite.currentTestSuites.testsuitescontents)

    // console.log("UpdateTestSuiteInfo currentTestSuite.currentTestSuites", currentTestSuite.currentTestSuites)


    await InitEvaluationChart()
}

const getButtonName = (TestActivity) => {
    switch (TestActivity) {
        case 'Reduction':
            if (currentTestSuite.currentTestSuites.reductiontool) {
                return 'Re-Reduction'
            }
            if (!currentTestSuite.currentTestSuites.reductiontool) {
                return 'New Reduction'
            }

            break;

        case 'Prioritisation':
            if (currentTestSuite.currentTestSuites.prioritisationtool) {
                return 'Re-Prioritisation'
            }
            if (!currentTestSuite.currentTestSuites.prioritisationtool) {
                return 'New Prioritisation'
            }
            break;

        case 'Evaluation':
            if (currentTestSuite.currentTestSuites.evaluationtool) {
                return 'Re-Evaluation'
            }
            if (!currentTestSuite.currentTestSuites.evaluationtool) {
                return 'New Evaluation'
            }
            break;

        case 'Dignosis':
            if (currentTestSuite.currentTestSuites.diagnosiscontents) {
                return 'Re-Dignosis'
            }
            if (!currentTestSuite.currentTestSuites.diagnosiscontents) {
                return 'New Dignosis'
            }
            break;

        default:
            break;
    }

}

const DignosisInfo = {
    Tools: "Adaptive Diagnosis",
    url: "http://127.0.0.1:9977/",
}

const scrollToTestSuiteInfo = async () => {
    await nextTick()
    TestSuiteInfo.value.scrollIntoView({ behavior: 'smooth' });

};

onMounted(async () => {
    try {
        // console.log("testSuitesStore.testSuitesList", testSuitesStore.testSuitesList)

        let loadingInstance = ElLoading.service({ fullscreen: true })

        moduleStore.CurrentSubSystem = "Tools"
        moduleStore.CurrentSubSystemRoute = "Tools_Models"
        moduleStore.CurrentModule = 'TestSuites'
        moduleStore.CurrentModuleDetails = 'TestSuite Details'
        moduleStore.CurrentRoute = 'TestSuites_Home'


        currentTestSuite.currentTestSuites = {}

        // console.log("testsuitesid", route.query.testsuitesid)
        // console.log("modelid", route.query.modelid)

        // console.log("onMounted Before currentTestSuite.currentTestSuites", currentTestSuite.currentTestSuites)

        //  加载当前模型的 信息 
        await listModelInfoByModelID(route.query.modelid)
        // console.log("currentModel.currentModel", currentModel.currentModel)

        // 加载 Testsuites Table
        await listAllTestSuitesByModelID([currentModel.currentModel])

        // 加载所有生成算法
        await listAllGenerationAlgorithm()

        // 加载所有Prioritisation算法
        await listAllPrioritisationOptions()

        // 加载所有Reduction算法
        await listAllReductionOptions()

        // 加载所有 Conversion 工具
        await listAllConversionOptions()

        // 加载所有 Evaluation 工具
        await listAllEvaluationAlgorithm()

        // 加载所有 Diagnosis 工具
        await listAllDiagnosisAlgorithm()

        // 将 testsuite 转换成表格显示，初始化显示route.query.testsuitesid的testsuite
        if (route.query.testsuitesid) {
            for (let index = 0; index < testSuitesStore.testSuitesList.length; index++) {
                for (let i = 0; i < testSuitesStore.testSuitesList[index].testSuites.length; i++) {
                    if (testSuitesStore.testSuitesList[index].testSuites[i].testsuitesid == route.query.testsuitesid) {

                        for (let key in testSuitesStore.testSuitesList[index].testSuites[i]) {
                            if (testSuitesStore.testSuitesList[index].testSuites[i].hasOwnProperty(key)) {
                                currentTestSuite.currentTestSuites[key] = testSuitesStore.testSuitesList[index].testSuites[i][key];
                            }
                        }


                    }

                }

            }

            getValueFromArrays(currentModel.currentModel.PandVOBJ, currentTestSuite.currentTestSuites.testsuitescontents)
        }
        else {
            currentTestSuite.currentTestSuites = {}
        }
        // 没有选择 TestSuite，TestSuites Table 展开
        if (!currentTestSuite.currentTestSuites.testsuitesname) {
            TestTableListFlag.value = !TestTableListFlag.value
        }




        if (currentTestSuite.currentTestSuites.evaluationtool == 'Coverage Checker') {
            // 当 v-if 条件为真时执行的操作
            // console.log("执行onMount中的InitEvaluationChart")
            InitEvaluationChart();
        }

        // console.log("onMounted AfterInit currentTestSuite.currentTestSuites", currentTestSuite.currentTestSuites)
        loadingInstance.close()


    } catch (error) {
        console.log("onMounted error", error)

    }
})
</script>


<style scoped>
.borderGlow {
    border: 2px solid #2dce89;
    /* 设置边框样式，这里使用蓝色边框作为示例 */
    box-shadow: 0 0 10px #2dce89;
    /* 设置阴影效果，使边框发光 */
}



/* .line-chart-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
} */
</style>
