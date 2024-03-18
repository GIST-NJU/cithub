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
                            <!-- <p class=" text-lg">Model Infomation</p> -->
                            <div class="col-md-3">
                                <h4> Model Name</h4>
                                <argon-input v-model="model.modelname" type="text" />
                            </div>
                            <hr class="my-3 horizontal white" />

                            <div class="col-md-10">
                                <h4> Model Description</h4>
                                <argon-input type="text" v-model="model.modeldescriptions" />
                            </div>
                            <hr class="my-3 horizontal dark" />

                            <div>

                                <div class="tb-container" ref="tbContainerRef">
                                    <h3 style="margin:0;text-align:center;">Model Content</h3>

                                    <p style="text-align:left;color:#ccc;">Note:
                                    </p>
                                    <p style="text-align:left;color:#ccc;">1.Double click to change the content of each
                                        cell.
                                    </p>
                                    <p style="text-align:left;color:#ccc;">2.For Column `Value`, Use `,` to seperate each
                                        value.</p>
                                    <p style="text-align:left;color:#ccc;">3.Select a number as covering strength.</p>
                                    <p style="text-align:left;color:#ccc;">4.The Model you create will be displayed in
                                        `Model Preview` area.</p>
                                    <hr class="my-3 horizontal white" />




                                    <!-- 选择Strength -->
                                    <h4 style="margin:0;">Strength</h4>

                                    <div style="display: flex;">
                                        <!-- <argon-badge variant="gradient" color="warning"
                                            style="width:10%;height:50%;margin: 10px 0 0 0 ;">Strength:</argon-badge> -->

                                        <el-select clearable style="margin: 0 0 0 0;padding: 0;width: 250px"
                                            v-model="strength" class="m-2" placeholder="Select a covering strength">
                                            <el-option v-for="item in StrengthOptions" :key="item.value" :label="item.label"
                                                :value="item.value" />
                                        </el-select>
                                        <span style="margin: 12px 0 0 0;color: red;" v-if="strength == false">Must choose a
                                            strength!</span>

                                    </div>
                                    <hr class="my-3 horizontal white" />


                                    <!---------------------------------- 参数和参数取值 table，col index 从 参数 col开始 ，index 的col不计算在内 ---------------------------------------------->

                                    <h4 style="margin:0;">Parameters and Values</h4>
                                    <!-- 输入参数 -->
                                    <el-table :data="tableData" border style="width: 100%;margin-top:10px"
                                        @cell-dblclick="cellDblclick" @cell-click="cellClick"
                                        :row-class-name="tableRowClassName">
                                        <!-- 第一列索引列 -->
                                        <el-table-column v-if="columnList.length > 0" type="index" :label="'index'"
                                            :width="80" />

                                        <!-- 参数和参数取值列 -->
                                        <el-table-column v-for="(col, idx) in columnList" :key="col.prop" :prop="col.prop"
                                            :label="col.label" :index="idx" :width="getColumnWidth(idx)" />


                                        <!-- Operation 列 -->
                                        <el-table-column label="Operation" :index="2">
                                            <el-popconfirm title="Delete this Param?" @confirm="deleteParam">
                                                <template #reference>
                                                    <a class="btn btn-link text-danger text-gradient px-3 mb-0">
                                                        <i class="far fa-trash-alt me-2" aria-hidden="true"></i>Delete
                                                        Parameter
                                                    </a>
                                                </template>
                                            </el-popconfirm>
                                        </el-table-column>
                                    </el-table>

                                    <argon-button style="margin:10px 0px 10px 0px" color="success" size="sm" full-width
                                        @click="newParam">
                                        <span class="ni ni-fat-add  ni-lg me-1" />
                                        New Parameter</argon-button>
                                    <hr class="my-3 horizontal dark" />


                                    <!---------------------------------- 约束table 注意，约束table的col index 从参数取值col开始 ，index 和 param 的col不计算在内 ---------------------------------------------->
                                    <!-- 选择约束 -->
                                    <h4 style="margin:0;">Constraints</h4>
                                    <p style="text-align:left;color:#ccc;">Note:
                                    </p>
                                    <p style="text-align:left;color:#ccc;">1.Cithub tools use `Forbidden tuples` to
                                        represent Constraints.
                                    </p>
                                    <p style="text-align:left;color:#ccc;">2.Double click to choose the invalid combination.
                                    </p>
                                    <p style="text-align:left;color:#ccc;">3.Click the `Add Constraint` button to put
                                        combination into the Constraints set.
                                    </p>

                                    <el-table :data="constraintsTableData" border style="width: 100%;margin-top:10px"
                                        @cell-dblclick="cellDblclickCons" @cell-click="cellClickCons"
                                        :row-class-name="tableRowClassName">
                                        <!-- 索引列 -->
                                        <el-table-column v-if="columnList.length > 0" type="index" :label="'index'"
                                            :width="80" />

                                        <el-table-column prop="Parameter" label="Parameter"></el-table-column>

                                        <el-table-column v-for=" valueColumn, idx in getValueColumns()" :key="valueColumn"
                                            :label="valueColumn" :index="idx" align="center">
                                            <template v-slot="{ row }">
                                                <div style="cursor:pointer;"
                                                    :class="{ 'highlight-cell': isCellHighlighted(row, valueColumn) }"
                                                    @dblclick="toggleCellHighlight(row, valueColumn)">
                                                    {{ getCellValue(row, valueColumn) }}
                                                </div>
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                    <argon-button style="margin:10px 0px 10px 0px" color="warning" size="sm" full-width
                                        @click="addCons">
                                        <span class="ni ni-fat-add  ni-lg me-1" />
                                        Add Constraint</argon-button>
                                    <p style="margin:10px 0px 10px 0px">Current Constraints set: </p>
                                    <!-- 约束预览 -->
                                    <el-input disabled v-model="consPreview" autosize type="textarea"
                                        placeholder="All the constraints will be showed right here" />
                                    <argon-button style="margin:10px 0px 10px 0px" color="danger" size="sm" full-width
                                        @click="clearCons">
                                        <span class="ni ni-fat-add  ni-lg me-1" />
                                        Clear All ConstraintS</argon-button>

                                    <hr class="horizontal dark" />

                                    <!-- <div>
                                        <h3 style="text-align:center;">实时数据展示</h3>
                                        <label>当前目标：</label>
                                        <p>{{ JSON.stringify(curTargetCons) }}</p>
                                        <label>表头：</label>
                                        <p v-for="col in columnList" :key="col.prop">{{ JSON.stringify(col) }}</p>
                                        <label>数据：</label>
                                        <p v-for="(data, idx) in testDatas" :key="idx">{{ JSON.stringify(data) }}</p>
                                    </div> -->

                                    <h3 style="text-align:center;">Model Preview</h3>


                                    <!-- 模型预览 -->
                                    <el-input disabled v-model="modelPreview" autosize type="textarea"
                                        placeholder="Please input" />

                                    <!-- 单元格内容编辑框 -->
                                    <div v-show="showEditInput" id="editInput" @mouseleave="showEditInput = false">
                                        <el-input
                                            :placeholder="curTarget.colIdx == 1 ? 'Value1, Value2, Value3, ...... , ValueN' : 'Please input Parameter name'"
                                            v-model="curTarget.val" clearable @change="updTbCellOrHeader">
                                            <template #prepend>{{ curColumn.label || curColumn.prop }}</template>
                                        </el-input>
                                    </div>

                                </div>
                            </div>

                            <hr class="horizontal dark" />
                            <div style="width:100%;margin-bottom: 5px;text-align: center;">
                                <argon-button style="margin:5px 5px 5px 5px;float: right" color="success" class="ms-auto"
                                    @click="Generation">Test suite</argon-button>
                                <argon-button style="margin:5px 5px 5px 5px;float: right" color="primary" class="ms-auto"
                                    @click="SaveModel">Save Model</argon-button>


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
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import SideNav from './components/SideNav.vue'
import ArgonButton from '../ComponentCommon/ArgonButton.vue';
import ArgonInput from '../ComponentCommon/ArgonInput.vue';
import ArgonBadge from '../ComponentCommon/ArgonBadge.vue';
import { request } from '../request';
import { onMounted, reactive, ref, computed, defineProps, watch, onUnmounted, watchEffect } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import pinia from '../store/store'
import { useCurrentModel } from '../store/currentModel'


import { ElNotification } from 'element-plus';

const route = useRoute()
const router = useRouter()
const currentModel = useCurrentModel(pinia)
const tbContainerRef = ref(null);
const strength = ref(0)
const StrengthOptions = [
    {
        value: '1',
        label: '1',
    },
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
const model = reactive({})
const columnList = ref([
    { prop: "Parameter", label: 'Parameter' },
    { prop: "Value", label: 'Value' },

]);


const tableData = ref([]);
// 对 tableData 的数据做处理，转成被constraintsTableData
const constraintsTableData = ref()
const maxValueDomain = ref(0)
// 预览模型
const modelPreview = ref('')


const modelObject = reactive({
    system: "",
    strength: '',
    parameter: '',
    values: '',
    constraints: []

})
const showMenu = ref(false);
const showEditInput = ref(false);

const curTarget = ref({
    rowIdx: null,
    colIdx: null,
    val: null,
    isHead: undefined
});
const curTargetCons = ref({
    rowIdx: null,
    colIdx: null,
    val: null,
    isHead: undefined
});
const highlightedCells = ref([]);
const isCellHighlighted = (row, column) => {
    return highlightedCells.value.some((cell) => cell.row === row && cell.column === column);
};

const toggleCellHighlight = (row, column) => {
    const index = highlightedCells.value.findIndex((cell) => cell.row === row && cell.column === column);
    if (index === -1) {
        // 如果之前没有高亮，添加高亮
        highlightedCells.value.push({ row, column });
    } else {
        // 如果之前已经高亮，取消高亮
        highlightedCells.value.splice(index, 1);
    }
    // console.log("highlightedCells", highlightedCells.value)
};

const newParam = () => {
    if (tableData.value.length === 0) {
        const obj = {};
        columnList.value.forEach(p => (obj[p.prop] = ''));
        tableData.value.push(obj);
    } else {
        let lastData = tableData.value[tableData.value.length - 1];
        const idx = lastData.row_index + 1;

        let obj = {};
        columnList.value.forEach(p => (obj[p.prop] = ''));
        obj.row_index = idx;  // Add row_index property for the new parameter
        tableData.value.splice(idx, 0, obj);
        // console.log("tableData.value",tableData.value)
    }
}

// watch(tableData, (newTableData, oldTableData) => {
//     // 处理tableData变化的逻辑
//     console.log("观察器已触发。newTableData:", newTableData);
//     let tempArray = []
//     let param_count = 0
//     for (let i = 0, len = newTableData.length; i < len; i++) {
//         if (newTableData[i].Value != '') { tempArray.push(newTableData[i].Value.split(',').length) }
//         if (newTableData[i].Parameter != '') { param_count = param_count + 1 }

//     }
//     // 统计模型基本数据
//     modelObject.system = model.modelname
//     modelObject.strength = strength.value
//     modelObject.parameter = param_count
//     modelObject.values = JSON.stringify(tempArray)


//     // 对 tableData 的数据做处理，转成被constraintsTableData
//     let tempArrayCons = []
//     for (let i = 0, len = newTableData.length; i < len; i++) {
//         let tempObj = {}
//         tempObj.Parameter = newTableData[i].Parameter
//         tempObj.valueArray = newTableData[i].Value.split(',').map((value, index) => ({
//             [`Value${index + 1}`]: value
//         }));
//         tempObj.ValueDomain = tempObj.valueArray.length
//         tempArrayCons.push(tempObj)

//     }
//     constraintsTableData.value = tempArrayCons


//     // 得到最大的ValueDomain
//     const maxDomain = constraintsTableData.value.reduce((max, item) => {
//         return Math.max(max, item.ValueDomain);
//     }, -Infinity);


//     maxValueDomain.value = maxDomain
//     // console.log("maxValueDomain", maxValueDomain.value)
//     // console.log("constraintsTableData", constraintsTableData.value)


//     // 将模型数据显示在Model Preview区域
//     modelPreview.value = JSON.stringify(modelObject, null, 6).replace(/"/g, '')



// }, { deep: true });


const stopWatch = watchEffect(() => {
    // 处理tableData变化的逻辑
    const newTableData = tableData.value; // 使用 .value 获取响应式数据
    console.log("观察器已触发。newTableData:", newTableData);

    let tempArray = [];
    let param_count = 0;

    for (let i = 0, len = newTableData.length; i < len; i++) {
        if (newTableData[i].Value !== '') {
            tempArray.push(newTableData[i].Value.split(',').length);
        }
        if (newTableData[i].Parameter !== '') {
            param_count = param_count + 1;
        }
    }

    // 统计模型基本数据
    modelObject.system = model.modelname;
    modelObject.strength = strength.value;
    modelObject.parameter = param_count;
    modelObject.values = JSON.stringify(tempArray);

    // 对 tableData 的数据做处理，转成被constraintsTableData
    let tempArrayCons = [];

    for (let i = 0, len = newTableData.length; i < len; i++) {
        let tempObj = {};
        tempObj.Parameter = newTableData[i].Parameter;
        tempObj.valueArray = newTableData[i].Value.split(',').map((value, index) => ({
            [`Value${index + 1}`]: value,
        }));
        tempObj.ValueDomain = tempObj.valueArray.length;
        tempArrayCons.push(tempObj);
    }

    constraintsTableData.value = tempArrayCons;

    // 得到最大的ValueDomain
    const maxDomain = constraintsTableData.value.reduce((max, item) => {
        return Math.max(max, item.ValueDomain);
    }, -Infinity);

    maxValueDomain.value = maxDomain;

    // 将模型数据显示在Model Preview区域
    modelPreview.value = JSON.stringify(modelObject, null, 6).replace(/"/g, '');

}, { flush: 'sync' });


const deleteParam = () => {
    // 这里需要debug！
    showMenu.value = false;
    curTarget.value.rowIdx !== null && tableData.value.splice(curTarget.value.rowIdx, 1);
    clearCons()

}

const getColumnWidth = (index) => {
    if (tbContainerRef.value) {
        const tableWidth = tbContainerRef.value.clientWidth; // 获取当前表格宽度
        if (columnList.value[index].prop === 'Value' && tableWidth) {
            return `${tableWidth * 0.5}px`; // 将 "Value" 列的宽度设置为表格宽度的70%
        }
    }
    return columnList.value[index].width;
};


const curColumn = computed(() => {
    return columnList.value[curTarget.value.colIdx] ?? {};
});

const cellClick = (row, column, cell, $event) => {
    // console.log("单击！","row:", row, "column:", column, "cell", cell, "$event", $event)
    showEditInput.value = false;
    if (column.index == null) return;
    locateMenuOrEditInput('editInput', 300, $event);
    // showEditInput.value = true;
    curTarget.value = {
        rowIdx: row.row_index,
        colIdx: column.index,
        val: row[column.property],
        isHead: false
    };
};

const cellDblclick = (row, column, cell, $event) => {

    if (column.index != 2) {
        showEditInput.value = false;
        locateMenuOrEditInput('editInput', 300, $event);
        showEditInput.value = true;
    }

    if (column.index == null) return;


    curTarget.value = {
        rowIdx: row.row_index,
        colIdx: column.index,
        val: row[column.property],
        isHead: false
    };
};

const cellDblclickCons = (row, column, cell, $event) => {
    // console.log("Parameter", row.Parameter, "Value", row.valueArray[column.index])
    if (typeof row.valueArray[column.index] === 'undefined') {
        ElNotification({
            title: 'Invalid Choice',
            message: 'This value of ' + row.Parameter + ' is invalid!',
            type: 'error',
        })
    }


};
const cellClickCons = (row, column, cell, $event) => {
    // console.log("row", row, "column", column, "cell", cell, "$event", $event)
    // console.log("column.index", column.index)

    showEditInput.value = false;
    if (column.index == null) return;
    locateMenuOrEditInput('editInput', 300, $event);
    curTargetCons.value = {
        rowIdx: row.row_index,
        colIdx: column.index,
        val: row[column.property],
        isHead: false
    };
};

const getValueColumns = () => {
    const valueColumns = new Set();

    if (constraintsTableData.value && Array.isArray(constraintsTableData.value)) {
        constraintsTableData.value.forEach(item => {
            if (item.valueArray && Array.isArray(item.valueArray)) {
                item.valueArray.forEach(value => {
                    const valueKey = Object.keys(value)[0];
                    valueColumns.add(valueKey);
                });
            }
        });
    }

    return Array.from(valueColumns);
};


const getCellValue = (row, column) => {
    const cellValue = row.valueArray ? row.valueArray.find(item => item.hasOwnProperty(column)) : null;
    return cellValue ? cellValue[column] : '';
};

// 预览约束
const consPreview = ref('')
const consArray = ref([])

// 返回parameterName在tableData中的索引
// value在该parameterName中的索引
const findPosition = (parameterName, value) => {
    for (let i = 0; i < tableData.value.length; i++) {
        const parameter = tableData.value[i].Parameter;
        const values = tableData.value[i].Value.split(',');

        if (parameter === parameterName) {
            const valueIndex = values.indexOf(value);
            if (valueIndex !== -1) {
                return { parameterIndex: i, valueIndex };
            }
        }
    }

    return { parameterIndex: -1, valueIndex: -1 };
}
const addCons = () => {
    // console.log("consArray.value",consArray.value) 
    let ForbiddenTuple = []
    for (let i = 0, len = highlightedCells.value.length; i < len; i++) {
        let tempObj = {}
        let tempArray = highlightedCells.value[i].row.valueArray
        let key = highlightedCells.value[i].column
        for (const obj of tempArray) {
            if (obj.hasOwnProperty(key)) {
                let result = obj[key];
                tempObj.Parameter = highlightedCells.value[i].row.Parameter
                tempObj.Value = result
                break; // 找到匹配的键后可以提前结束循环
            }
        }
        // 存储单次的约束
        ForbiddenTuple.push(tempObj)
    }
    // 存储cons！
    let cons_count = consArray.value.length + 1
    consArray.value.push({ ["Constraint_" + cons_count]: ForbiddenTuple });
    consPreview.value = JSON.stringify(consArray.value, null, 6).replace(/"/g, '')

    // 清空本次的高亮cell
    highlightedCells.value = [];

    // 预览cons

    // 先将 consArray 转换成cithub格式的cons

    // 对每个约束进行处理，将其转换成 '参数索引'/'取值索引' 的形式
    let consArrayToAPI = []
    for (const constraint of consArray.value) {
        let consArrayTemp = []
        // 遍历元素中的键值对
        for (const key in constraint) {
            if (Object.hasOwnProperty.call(constraint, key)) {
                const elements = constraint[key];
                // elements 是一个数组，包含了多个键值对
                for (const element of elements) {
                    // 在这里访问 Parameter 和 Value
                    const parameter = element.Parameter;
                    const value = element.Value;

                    // 这里可以使用 parameter 和 value 进行其他操作
                    // console.log(`Key: ${key}, Parameter: ${parameter}, Value: ${value}`);
                    let constempObj = findPosition(parameter, value)
                    let consString = `\'${constempObj.parameterIndex}/${constempObj.valueIndex}\'`
                    consArrayTemp.push(consString)
                }
            }
            consArrayToAPI.push(consArrayTemp)
        }
    }
    modelObject.constraints = consArrayToAPI
    modelPreview.value = JSON.stringify(modelObject, null, 6).replace(/"/g, '')


}

const clearCons = () => {
    highlightedCells.value = [];
    consArray.value = []
    consPreview.value = ''
    modelObject.constraints = []
    modelPreview.value = JSON.stringify(modelObject, null, 6).replace(/"/g, '')

}

const updTbCellOrHeader = (val) => {
    if (!curTarget.value.isHead)
        tableData.value[curTarget.value.rowIdx][curColumn.value.prop] = val;
    else {
        if (!val) return;
        columnList.value[curTarget.value.colIdx].label = val;
    }
};



const tableRowClassName = ({ row, rowIndex }) => {
    row.row_index = rowIndex;
};

const locateMenuOrEditInput = (eleId, eleWidth, $event) => {
    const { x: tbX, y: tbY } = tbContainerRef.value.getBoundingClientRect();
    const { x: pX, y: pY } = $event;
    const ele = document.getElementById(eleId);
    ele.style.top = pY - tbY - 6 + 'px';
    ele.style.left = pX - tbX - 6 + 'px';
    if (window.innerWidth - eleWidth < pX - tbX) {
        ele.style.left = 'unset';
        ele.style.right = 0;
    }
};
const SaveModel = async () => {

    if (tableData.value.length != 0) {
        if (strength.value !== null && strength.value !== 0) {
            const currentDate = new Date();

            // // 如果没有约束
            // if (consArray.value.length == 0) {
            //     const SaveModelContentRes = await request({
            //         url: '/tools/models/SaveModel',
            //         method: 'POST',
            //         data: {
            //             modelid: route.query.modelid,
            //             modelname: model.modelname,
            //             modeldescriptions: model.modeldescriptions,
            //             strength: strength.value,
            //             ParametersAndValues: JSON.stringify(tableData.value),
            //             lastupdatedtime: currentDate
            //         }
            //     })

            //     if (SaveModelContentRes.SaveModelStatus == 'success') {


            //         currentModel.currentModel.modelid = route.query.modelid
            //         currentModel.currentModel.modelname = model.modelname
            //         currentModel.currentModel.modeldescriptions = model.modeldescriptions
            //         currentModel.currentModel.strength = strength.value
            //         currentModel.currentModel.paramsvalues = JSON.stringify(tableData.value)
            //         currentModel.currentModel.lastupdatedtime = currentDate


            //         currentModel.currentModel.PandVOBJ = JSON.parse(currentModel.currentModel.paramsvalues)

            //         currentModel.currentModel.NumofParams = currentModel.currentModel.PandVOBJ.length

            //         let transformedData = currentModel.currentModel.PandVOBJ.map(item => {
            //             // 将逗号分隔的字符串转换为数组
            //             const valueArray = item.Value.split(',');

            //             // 更新对象的Value字段为数组
            //             return {
            //                 ...item,
            //                 Value: valueArray
            //             };
            //         });
            //         currentModel.currentModel.PandVOBJ = transformedData
            //         // 移除 row_index 属性
            //         let tableDataTmp = currentModel.currentModel.PandVOBJ.map(item => {
            //             const { row_index, ...rest } = item;
            //             return rest;
            //         });
            //         currentModel.currentModel.PandVOBJ = tableDataTmp


            //         ElNotification({
            //             title: 'Save Success!',
            //             type: 'success',
            //         })
            //     }
            //     else {
            //         ElNotification({
            //             title: 'Save fail!',
            //             type: 'error',
            //         })
            //     }
            // }
            // 如果有约束
            // else {
                const SaveModelContentRes = await request({
                    url: '/tools/models/SaveModel',
                    method: 'POST',
                    data: {
                        modelid: route.query.modelid,
                        modelname: model.modelname,
                        modeldescriptions: model.modeldescriptions,
                        strength: strength.value,
                        ParametersAndValues: JSON.stringify(tableData.value),
                        Cons: JSON.stringify(consArray.value),
                        lastupdatedtime: currentDate
                    }
                })

                if (SaveModelContentRes.SaveModelStatus == 'success') {
                    currentModel.currentModel.modelid = route.query.modelid
                    currentModel.currentModel.modelname = model.modelname
                    currentModel.currentModel.modeldescriptions = model.modeldescriptions
                    currentModel.currentModel.strength = strength.value
                    currentModel.currentModel.paramsvalues = JSON.stringify(tableData.value)
                    currentModel.currentModel.cons = JSON.stringify(consArray.value)
                    currentModel.currentModel.lastupdatedtime = currentDate


                    currentModel.currentModel.PandVOBJ = JSON.parse(currentModel.currentModel.paramsvalues)
                    currentModel.currentModel.ConsOBJ = JSON.parse(currentModel.currentModel.cons)
                    currentModel.currentModel.NumofParams = currentModel.currentModel.PandVOBJ.length
                    currentModel.currentModel.NumofCons = currentModel.currentModel.ConsOBJ.length
                    let transformedData = currentModel.currentModel.PandVOBJ.map(item => {
                        // 将逗号分隔的字符串转换为数组
                        const valueArray = item.Value.split(',');

                        // 更新对象的Value字段为数组
                        return {
                            ...item,
                            Value: valueArray
                        };
                    });
                    currentModel.currentModel.PandVOBJ = transformedData
                    // 移除 row_index 属性
                    let tableDataTmp = currentModel.currentModel.PandVOBJ.map(item => {
                        const { row_index, ...rest } = item;
                        return rest;
                    });
                    currentModel.currentModel.PandVOBJ = tableDataTmp

                    ElNotification({
                        title: 'Save Success!',
                        type: 'success',
                    })

                    await listModelInfoByModelID()
                }
                else {
                    ElNotification({
                        title: 'Save fail!',
                        type: 'error',
                    })
                }
            // }




        }
        else {
            ElNotification({
                title: 'Save fail!',
                message: 'Must choose a Strength',
                type: 'error',
            })
        }
    }
    else {
        ElNotification({
            title: 'Save fail!',
            message: 'No Parameters!',
            type: 'error',
        })
    }



}


const listModelInfoByModelID = async () => {
    try {
        const res = await request({
            url: '/tools/models/info/' + route.query.modelid,
            method: 'POST'
        });

        model.modelid = res.models.modelid
        model.strength = res.models.strength
        strength.value = res.models.strength
        model.modelname = res.models.modelname
        model.modeldescriptions = res.models.modeldescriptions
        model.paramsvalues = res.models.paramsvalues
        model.cons = res.models.cons
        model.lastupdatedtime = res.models.lastupdatedtime
        model.createdtime = res.models.createdtime
        modelObject.system = model.modelname




        currentModel.currentModel.modelid = res.models.modelid
        currentModel.currentModel.strength = res.models.strength
        currentModel.currentModel.modelname = res.models.modelname
        currentModel.currentModel.modeldescriptions = res.models.modeldescriptions
        currentModel.currentModel.paramsvalues = res.models.paramsvalues
        currentModel.currentModel.cons = res.models.cons
        currentModel.currentModel.lastupdatedtime = res.models.lastupdatedtime
        currentModel.currentModel.createdtime = res.models.createdtime
        currentModel.currentModel.PandVOBJ = JSON.parse(model.paramsvalues)
        currentModel.currentModel.ConsOBJ = JSON.parse(model.cons)
        currentModel.currentModel.NumofParams = currentModel.currentModel.PandVOBJ.length
        currentModel.currentModel.NumofCons = currentModel.currentModel.ConsOBJ.length

        let transformedData = currentModel.currentModel.PandVOBJ.map(item => {
            // 将逗号分隔的字符串转换为数组
            const valueArray = item.Value.split(',');

            // 更新对象的Value字段为数组
            return {
                ...item,
                Value: valueArray
            };
        });
        currentModel.currentModel.PandVOBJ = transformedData
        // 移除 row_index 属性
        let tableDataTmp = currentModel.currentModel.PandVOBJ.map(item => {
            const { row_index, ...rest } = item;
            return rest;
        });
        currentModel.currentModel.PandVOBJ = tableDataTmp

        // 加载模型到table
        // 加载模型到预览区
        loadModel();
    } catch (err) {
        console.error(err);
    }
}

const loadModel = () => {
    // 解析参数和参数取值的Json字符串
    const parsedData = JSON.parse(model.paramsvalues)

    // 移除 row_index 属性
    const tableDataTmp = parsedData.map(item => {
        const { row_index, ...rest } = item;
        return rest;
    });

    // 加载参数和参数取值
    tableData.value = tableDataTmp;

    // 加载约束table
    let tempArray = []
    let param_count = 0
    for (let i = 0, len = tableData.value.length; i < len; i++) {
        if (tableData.value[i].Value != '') { tempArray.push(tableData.value[i].Value.split(',').length) }
        if (tableData.value[i].Parameter != '') { param_count = param_count + 1 }

    }
    // 统计模型基本数据

    if (model.modelname) { modelObject.system = model.modelname }
    modelObject.system = model.modelname
    modelObject.strength = model.strength
    modelObject.parameter = param_count
    modelObject.values = JSON.stringify(tempArray)


    // 对 tableData 的数据做处理，转成被constraintsTableData
    let tempArrayCons = []
    for (let i = 0, len = tableData.value.length; i < len; i++) {
        let tempObj = {}
        tempObj.Parameter = tableData.value[i].Parameter
        tempObj.valueArray = tableData.value[i].Value.split(',').map((value, index) => ({
            [`Value${index + 1}`]: value
        }));
        tempObj.ValueDomain = tempObj.valueArray.length
        tempArrayCons.push(tempObj)

    }
    constraintsTableData.value = tempArrayCons


    // 得到最大的ValueDomain
    const maxDomain = constraintsTableData.value.reduce((max, item) => {
        return Math.max(max, item.ValueDomain);
    }, -Infinity);


    maxValueDomain.value = maxDomain


    // 将cons显示在cons Preview区域
    consArray.value = JSON.parse(model.cons)
    consPreview.value = JSON.stringify(JSON.parse(model.cons), null, 6).replace(/"/g, '')
    // 对每个约束进行处理，将其转换成 '参数索引'/'取值索引' 的形式
    let consArrayToAPI = []
    for (const constraint of consArray.value) {
        let consArrayTemp = []
        // 遍历元素中的键值对
        for (const key in constraint) {
            if (Object.hasOwnProperty.call(constraint, key)) {
                const elements = constraint[key];
                // elements 是一个数组，包含了多个键值对
                for (const element of elements) {
                    // 在这里访问 Parameter 和 Value
                    const parameter = element.Parameter;
                    const value = element.Value;

                    // 这里可以使用 parameter 和 value 进行其他操作
                    // console.log(`Key: ${key}, Parameter: ${parameter}, Value: ${value}`);
                    let constempObj = findPosition(parameter, value)
                    let consString = `\'${constempObj.parameterIndex}/${constempObj.valueIndex}\'`
                    consArrayTemp.push(consString)
                }
            }
            consArrayToAPI.push(consArrayTemp)
        }
    }
    modelObject.constraints = consArrayToAPI

    // 将模型数据显示在Model Preview区域
    modelPreview.value = JSON.stringify(modelObject, null, 6).replace(/"/g, '')
    currentModel.currentModel.modelCithub = JSON.stringify(modelObject, null, 6).replace(/"/g, '')

}
watch(strength, (newStrength, oldStrength) => {
    // 处理strength变化的逻辑
    // console.log('strength changed:', newStrength);


    modelObject.system = model.modelname
    modelObject.strength = strength.value

    // 将模型数据显示在Model Preview区域
    modelPreview.value = JSON.stringify(modelObject, null, 6).replace(/"/g, '')
    currentModel.currentModel.modelCithub = JSON.stringify(modelObject, null, 6).replace(/"/g, '')


}, { deep: true });



const Generation = () => {
    loadModel()
    router.push({
        // path: '/tools/TestSuitesHome',
        path: '/tools/TestSuitesHomeNew',
        query:
            { modelid: route.query.modelid }
    })
}

onMounted(async () => {

    tbContainerRef.value = document.querySelector('.tb-container');

    await listModelInfoByModelID()




})

</script>


<style scoped>
.tb-container {
    position: relative;
}




#editInput,
#headereditInput {
    position: absolute;
    top: 0;
    left: 0;
    height: 50px;
    min-width: 400px;
    /* 调整编辑框的最小宽度 */
    padding: 0;
    z-index: 12;
}

#editInput .el-input,
#headereditInput .el-input {
    outline: 0;
    border: 1px solid #c0f2f9;
    border-radius: 5px;
    box-shadow: 0px 0px 10px 0px #c0f2f9;
    width: 100%;
    /* 让输入框充满父容器 */
    height: 100%;
    /* 让输入框充满父容器 */
}

.highlight-cell {
    background-color: red;
    color: white;
    /* 设置文本颜色，以确保可见性 */
}
</style>
