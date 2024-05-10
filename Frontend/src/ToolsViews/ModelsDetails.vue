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
                    <div class="col-2">

                        <ArgonButton full-width class="mb-2" color="secondary" size="sm" @click="goBack">
                            <span class="ni ni-bold-left  me-1"> Back </span>
                        </ArgonButton>

                    </div>
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
                                    <h3 style="margin:0;text-align:center;">Parameters and Values</h3>
                                    <!-- 这里设置一个按钮，点击之后弹出动图+文字说明 -->


                                    <el-popover placement="right" :width="600" trigger="click">
                                        <template #reference>
                                            <ArgonButton size="sm"><i class="bi bi-question-circle-fill mx-1"></i>How to
                                                input Parameters and Values?</ArgonButton>
                                        </template>
                                        <div class="row ">
                                            <img src="../../public/images/Edit Model.gif" alt="Edit Model">
                                            <hr class="my-3 horizontal white" />

                                            <p style="text-align:left;color:rgb(0, 0, 0);"><b>Input Model Steps:</b></p>

                                            <p style="text-align:left;color:rgb(0, 0, 0);">1.Click <argon-button
                                                    color="success" size="sm">
                                                    <span class="ni ni-fat-add  ni-lg me-1" />
                                                    New Parameter</argon-button> to New a `<b><i>Parameter</i></b>`.
                                            </p>

                                            <p style="text-align:left;color:rgb(0, 0, 0);">2.Double click the cell of
                                                table to input
                                                `<b><i>Parameter</i></b>`.
                                            </p>
                                            <p style="text-align:left;color:rgb(0, 0, 0);">3.For Column
                                                `<b><i>Value</i></b>`,
                                                double click the cell to input , and use `<b><i>,</i></b>` to seperate
                                                each
                                                value.</p>

                                        </div>


                                    </el-popover>



                                    <hr class="my-3 horizontal white" />





                                    <!---------------------------------- 参数和参数取值 table，col index 从 参数 col开始 ，index 的col不计算在内 ---------------------------------------------->

                                    <!-- 输入参数 -->


                                    <el-table :data="tableData" border style="width: 100%;margin-top:10px"
                                        :row-class-name="tableRowClassName" @row-dblclick="handleRowDoubleClick"
                                        empty-text="No Parameters Now, Please click the button below to new a Params.">
                                        <!-- 第一列索引列 -->
                                        <el-table-column v-if="columnList.length > 0" type="index" :label="'index'"
                                            :width="80" />

                                        <!-- 参数和参数取值列 -->
                                        <el-table-column v-for="(col, idx) in columnList" :key="col.prop" :prop="col.prop"
                                            :label="col.label" :index="idx" :width="getColumnWidth(idx)">
                                            <template #default="{ row }">
                                                <!-- 根据当前单元格是否处于编辑状态显示不同内容 -->
                                                <template v-if="!row.editing">
                                                    <!-- 绑定整个单元格容器的点击事件 -->
                                                    <div @click="startEditing(row, col)">
                                                        <span>{{ row[col.prop] }}</span>
                                                    </div>
                                                </template>
                                                <template v-else>
                                                    <div>
                                                        <el-input v-model="row[col.prop]" clearable @keyup.enter="finishEditing(row, col)"></el-input>
                                                        <div style="margin-top: 8px;">
                                                            <ArgonButton size="sm" color="success"
                                                                @click="finishEditing(row, col)">Change</ArgonButton>
                                                            <ArgonButton style="margin-left: 5px;" size="sm" color="danger"
                                                                @click="cancelEditing(row, col)">Cancel</ArgonButton>

                                                        </div>

                                                    </div>
                                                </template>
                                            </template>
                                        </el-table-column>

                                        <!-- Operation 列 -->
                                        <el-table-column label="Operation" :index="2">
                                            <template #default="{ row }">
                                                <el-popconfirm title="Delete this Param?" @confirm="() => deleteParam(row)">
                                                    <template #reference>
                                                        <ArgonButton size="sm" color="danger" variant="gradient">
                                                            <span class="far fa-trash-alt me-2"></span>
                                                            Delete Parameter
                                                        </ArgonButton>

                                                    </template>
                                                </el-popconfirm>
                                            </template>
                                        </el-table-column>
                                    </el-table>





                                    <argon-button style="margin:10px 0px 10px 0px" color="success" size="sm" full-width
                                        @click="newParam">
                                        <span class="ni ni-fat-add  ni-lg me-1" />
                                        New Parameter</argon-button>






                                    <hr class="my-3 horizontal dark" />


                                    <!---------------------------------- 约束table 注意，约束table的col index 从参数取值col开始 ，index 和 param 的col不计算在内 ---------------------------------------------->
                                    <!-- 选择约束 -->
                                    <h3 style="margin:0;text-align:center;">Constraints</h3>


                                    <el-popover placement="right" :width="700" trigger="click">
                                        <template #reference>
                                            <ArgonButton size="sm"><i class="bi bi-question-circle-fill mx-1"></i>How to
                                                add Constraints?</ArgonButton>
                                        </template>
                                        <div class="row ">
                                            <img src="../../public/images/Edit Model.gif" alt="Edit Model">
                                            <hr class="my-3 horizontal white" />
                                            <p style="text-align:left;color:rgb(0, 0, 0);"><b>Add constraints Steps:</b></p>
                                            <p style="text-align:left;color:rgb(0, 0, 0);">1.Cithub tools use
                                                `<b><i>Forbidden
                                                        tuples</i></b>` to
                                                represent Constraints.
                                            </p>
                                            <p style="text-align:left;color:rgb(0, 0, 0);">2.Double click each cell of below
                                                table
                                                to choose every <b><i>invalid combination.</i></b> .
                                            </p>
                                            <p style="text-align:left;color:rgb(0, 0, 0);">3.Click the `<b><i>Add
                                                        Constraint</i></b>` button to put `<b><i>invalid
                                                        combination</i></b>` into the `<b><i>current Constraints
                                                        set</i></b>`.
                                            </p>

                                        </div>


                                    </el-popover>

                                    <hr class="my-3 horizontal white" />


                                    <el-table :data="constraintsTableData" border style="width: 100%;margin-top:10px"
                                        @cell-dblclick="cellDblclickCons" @cell-click="cellClickCons"
                                        empty-text="No Constraints Now, Please click the button below to new a Cons."
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


                                    <!-- 用表格的形式展示禁止元组 -->
                                    <h5 class="text-center my-3">Constraints Table</h5>
                                    <table class="table table-bordered">
                                        <thead class="table-light">
                                            <tr>
                                                <!-- 使用 col-auto 类使第一列只占据所需的空间 -->
                                                <th class="text-center">Constraint Index</th>
                                                <th class="text-center">Forbidden Tuples</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr v-for="(item, index) in formattedData" :key="index">
                                                <td class="text-center">{{ index + 1 }}</td>
                                                <td class="text-center">{{ item }}</td>
                                            </tr>

                                            <tr class="border-0">
                                                <td></td>
                                                <td></td>
                                            </tr>
                                        </tbody>
                                    </table>

                                    <argon-button style="margin:10px 0px 10px 0px" color="danger" size="sm" full-width
                                        @click="clearCons">
                                        <span class="ni ni-fat-add  ni-lg me-1" />
                                        Clear All ConstraintS</argon-button>






                                </div>
                            </div>

                            <hr class="horizontal dark" />
                            <div style="width:100%;margin-bottom: 5px;text-align: center;">
                                <argon-button style="margin:5px 5px 5px 5px;float: right" color="success" class="ms-auto"
                                    @click="Generation">Test suite</argon-button>

                                <el-popconfirm width="300" confirm-button-text="Save" cancel-button-text="No, Thanks"
                                    @confirm="SaveModel" :icon="InfoFilled" icon-color="#626AEF"
                                    title="Update Model will clear all testsuites under this model. Are you sure to Save?">
                                    <template #reference>
                                        <argon-button style="margin:5px 5px 5px 5px;float: right" color="primary"
                                            class="ms-auto">Save Model</argon-button>
                                    </template>
                                </el-popconfirm>


                                <ArgonButton style="margin:5px 5px 5px 5px;float: right" class="ms-auto" color="secondary"
                                    @click="goBack">
                                    <span class="ni ni-bold-left  me-1"> Back </span>
                                </ArgonButton>


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
import Foot from '../CustomizedComponents/Foot.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import SideNav from './components/SideNav.vue'
import ArgonButton from '../CustomizedComponents/ArgonButton.vue';
import ArgonInput from '../CustomizedComponents/ArgonInput.vue';
import ArgonBadge from '../CustomizedComponents/ArgonBadge.vue';
import { request } from '../request';
import { onMounted, reactive, ref, computed, defineProps, watch, onUnmounted, watchEffect } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { listModelInfoByModelID } from './commonFunction.js'
import pinia from '../store/store'
import { useCurrentModel } from '../store/ToolsStore/currentModel'
import { ElLoading } from 'element-plus'

import { ElNotification } from 'element-plus';
import { useModuleStore } from '../store/module';


const moduleStore = useModuleStore(pinia)
const route = useRoute()
const router = useRouter()
const currentModel = useCurrentModel(pinia)
const tbContainerRef = ref(null);

const model = reactive({})
const columnList = ref([
    { prop: "Parameter", label: 'Parameter' },
    { prop: "Value", label: 'Value' },

]);


const tableData = ref([]);

const startEditing = (row, col) => {
    // 如果单元格处于编辑状态，则不做任何操作
    if (row.editing) return;

    // 设置该单元格为编辑状态
    row.editing = true;
    // 保存编辑前的值，用于取消编辑时恢复原始值
    row.originalValue = row[col.prop];

    // 如果是新行，设置 isNew 属性为 true
    if (row.isNew) {
        row.isNew = true;
    }
};

const finishEditing = (row, col) => {
    // 取消编辑状态
    row.editing = false;
    // 更新单元格内容
    updTbCellOrHeader(row[col.prop]);
};

const cancelEditing = (row, col) => {
    // 取消编辑状态
    row.editing = false;
    // 恢复原始值
    row[col.prop] = row.originalValue;

    // 如果是新行，设置 isNew 属性为 false
    if (row.isNew) {
        row.isNew = false;
    }
};

const newParam = () => {
    if (tableData.value.length === 0) {
        const obj = {};
        columnList.value.forEach(p => (obj[p.prop] = ''));
        obj.editing = true; // 设置新行的编辑状态为true
        obj.isNew = true; // 设置isNew属性为true
        tableData.value.push(obj);
    } else {
        let lastData = tableData.value[tableData.value.length - 1];
        const idx = lastData.row_index + 1;

        let obj = {};
        columnList.value.forEach(p => (obj[p.prop] = ''));
        obj.row_index = idx;  // Add row_index property for the new parameter
        obj.editing = true; // 设置新行的编辑状态为true
        obj.isNew = true; // 添加isNew属性标识新行
        tableData.value.splice(idx, 0, obj);
    }
}

const updTbCellOrHeader = (val) => {
    if (!curTarget.value || curTarget.value.isHead === undefined || curTarget.value.rowIdx === undefined || curTarget.value.colIdx === undefined) {
        console.error('curTarget is not properly defined or has missing properties');
        return;
    }

    if (!curTarget.value.isHead) {
        if (!tableData.value[curTarget.value.rowIdx]) {
            console.error('tableData row at index', curTarget.value.rowIdx, 'is not defined');
            return;
        }
        tableData.value[curTarget.value.rowIdx][curColumn.value.prop] = val;
    } else {
        if (!columnList.value[curTarget.value.colIdx]) {
            console.error('columnList column at index', curTarget.value.colIdx, 'is not defined');
            return;
        }
        if (!val) return;
        columnList.value[curTarget.value.colIdx].label = val;
    }
};

const handleRowDoubleClick = (row) => {
    console.log("handleRowDoubleClick", row)
    // 检查当前行是否为空行（即 Parameter 和 Value 属性都为空）
    if (row.Parameter == "") {
        // 如果该行是新行且不处于编辑状态，则启动编辑
        if (!row.editing) {
            startEditing(row, columnList.value[0]); // 这里假设默认编辑第一列
        }
    }
};


const deleteParam = (row) => {
    console.log("row", row)
    // 找到要删除的行的索引
    const index = tableData.value.findIndex(item => item === row);

    // 如果未找到索引，则返回
    if (index === -1) {
        console.error('Row not found in tableData');
        return;
    }

    // 从 tableData 中删除对应的行
    tableData.value.splice(index, 1);

    // 清空约束
    clearCons()
};




const getColumnWidth = (index) => {
    if (tbContainerRef.value) {
        const tableWidth = tbContainerRef.value.clientWidth; // 获取当前表格宽度
        if (columnList.value[index].prop === 'Value' && tableWidth) {
            return `${tableWidth * 0.5}px`; // 将 "Value" 列的宽度设置为表格宽度的70%
        }
    }
    return columnList.value[index].width;
};


// 对 tableData 的数据做处理，转成被constraintsTableData
const constraintsTableData = ref()




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
    // 检查是否已经存在来自同一行的元素

    const hasSameRow = highlightedCells.value.some((cell) => cell.row === row);

    if (index === -1 && !hasSameRow) {
        // 如果之前没有高亮，并且同一行也没有其他高亮元素，添加高亮
        highlightedCells.value.push({ row, column });
    } else if (index !== -1) {
        // 如果之前已经高亮，取消高亮
        highlightedCells.value.splice(index, 1);
    } else {
        // 如果同一行已经有其他高亮元素，报错

        ElNotification({
            title: 'Wrong Choice',
            message: 'Only one Value can be selected for each Parameter',
            type: 'error',
        })

        console.error('同一行已经有其他单元格被高亮！');
    }
};




// stopWatch 不能删除！
const stopWatch = watchEffect(() => {
    // 处理tableData变化的逻辑
    const newTableData = tableData.value; // 使用 .value 获取响应式数据
    // console.log("观察器已触发。newTableData:", newTableData);



    // 对 tableData 的数据做处理，转成被constraintsTableData
    let tempArrayCons = [];

    for (let i = 0, len = newTableData.length; i < len; i++) {
        let tempObj = {};
        tempObj.Parameter = newTableData[i].Parameter;
        if (typeof newTableData[i].Value === 'string' && newTableData[i].Value !== '') {
            tempObj.valueArray = newTableData[i].Value.split(',').map((value, index) => ({
                [`Value${index + 1}`]: value,
            }));
        } else {
            tempObj.valueArray = [];
        }
        tempObj.ValueDomain = tempObj.valueArray.length;
        tempArrayCons.push(tempObj);
    }

    constraintsTableData.value = tempArrayCons;





}, { flush: 'sync' });






const curColumn = computed(() => {
    return columnList.value[curTarget.value.colIdx] ?? {};
});








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

    showEditInput.value = false;
    if (column.index == null) return;
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


const consArray = ref([])


const addCons = () => {
    if (tableData.value.length == 0) {
        ElNotification({
            title: 'No Parameters.',
            message: 'Please input Parameters first! ',
            type: 'error',
        })
    }
    else {
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
        if (ForbiddenTuple.length != 0) {
            consArray.value.push({ ["Constraint"]: ForbiddenTuple });
        }
        else{
            ElNotification({
            title: 'Constraint is empty',
            message: 'Please select a Forbidden Tuple as Constraint.',
            type: 'error',
        })
        }


        // 清空本次的高亮cell
        highlightedCells.value = [];

        formatData()

    }



}

const clearCons = () => {
    highlightedCells.value = [];
    consArray.value = []
    formatData()

}






const tableRowClassName = ({ row, rowIndex }) => {
    row.row_index = rowIndex;
};

const SaveModel = async () => {

    // 参数与参数取值，移除无用属性
    let tempTableData = tableData.value.map(({ Parameter, Value }) => ({
        Parameter,
        Value
    }));
    tableData.value = tempTableData

    if (tableData.value.length != 0) {

        const currentDate = new Date();
        const SaveModelContentRes = await request({
            url: '/tools/Save',
            method: 'POST',
            data: {
                column: "model",
                modelid: route.query.modelid,
                modelname: model.modelname,
                modeldescriptions: model.modeldescriptions,
                ParametersAndValues: JSON.stringify(tableData.value),
                Cons: JSON.stringify(consArray.value),
                lastupdatedtime: currentDate
            }
        })

        if (SaveModelContentRes.SaveModelStatus == 'success') {
            currentModel.currentModel.modelid = route.query.modelid
            currentModel.currentModel.modelname = model.modelname
            currentModel.currentModel.modeldescriptions = model.modeldescriptions
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


            ElNotification({
                title: 'Save Success!',
                type: 'success',
            })

            await listModelInfoByModelID(route.query.modelid)


            model.modelid = currentModel.currentModel.modelid
            model.modelname = currentModel.currentModel.modelname
            model.modeldescriptions = currentModel.currentModel.modeldescriptions
            model.paramsvalues = currentModel.currentModel.paramsvalues
            model.cons = currentModel.currentModel.cons
            model.lastupdatedtime = currentModel.currentModel.lastupdatedtime
            model.createdtime = currentModel.currentModel.createdtime

            // 加载模型到table
            loadModelToTable();
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
            message: 'No Parameters!',
            type: 'error',
        })
    }



}


const loadModelToTable = () => {
    // 解析参数和参数取值的Json字符串
    if (model.paramsvalues) {
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


        // 将cons显示在cons Preview区域
        consArray.value = JSON.parse(model.cons)
    }

}




const Generation = () => {
    currentModel.currentModel = {}
    router.push(
        {
            path: '/tools/TestSuiteDetails',
            query: {
                modelid: route.query.modelid,
                index: route.query.index
            }
        }
    )
}
const goBack = () => {
    window.history.back();
}

// 将约束转化为禁止元组的形式展示
const formattedData = ref([]);

const formatData = () => {
    const formatted = consArray.value.map((item, index) => {
        const tupleStr = item.Constraint.map(constraint => {
            return `${constraint.Parameter}=${constraint.Value}`;
        }).join(" , ");
        return `${tupleStr}`;
    });
    formattedData.value = formatted;
};
onMounted(async () => {

    let loadingInstance = ElLoading.service({ fullscreen: true })
    
    moduleStore.CurrentSubSystem = "Tools"
    moduleStore.CurrentSubSystemRoute = "Tools_Models"
    moduleStore.CurrentModule = 'Models'
    moduleStore.CurrentModuleDetails = 'Model Details'
    moduleStore.CurrentRoute = 'Tools_Models'

    tbContainerRef.value = document.querySelector('.tb-container');

    await listModelInfoByModelID(route.query.modelid)


    model.modelid = currentModel.currentModel.modelid
    model.modelname = currentModel.currentModel.modelname
    model.modeldescriptions = currentModel.currentModel.modeldescriptions
    model.paramsvalues = currentModel.currentModel.paramsvalues
    model.cons = currentModel.currentModel.cons
    model.lastupdatedtime = currentModel.currentModel.lastupdatedtime
    model.createdtime = currentModel.currentModel.createdtime

    // 加载模型到table
    loadModelToTable();

    // 加载约束为禁止元组的表格
    formatData()

    loadingInstance.close()


})

</script>


<style scoped>
.tb-container {
    position: relative;
}

.el-popover {
    height: 500px;
    overflow: auto;
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