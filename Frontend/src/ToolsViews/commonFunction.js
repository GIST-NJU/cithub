// 一些公有函数，供多个组件复用
import pinia from '../store/store'
import { useModelsStore } from '../store/ToolsStore/modelsStore'
import { ElNotification } from 'element-plus'
import { request } from '../request';
import { useUserStore } from '../store/userStore';
import { useTestSuitesStore } from '../store/ToolsStore/testSuitesStore'
import { useCurrentModel } from '../store/ToolsStore/currentModel'

const userStore = useUserStore(pinia)
const modelStore = useModelsStore(pinia)
const testSuitesStore = useTestSuitesStore(pinia)
const currentModel = useCurrentModel(pinia)


const listAllModelsByUserID = async () => {

    try {
        const modelsRes = await request({
            method: "POST",
            url: '/tools/listByID',
            data: {
                searchkeywords: userStore.userID,
                column: "userid"
            }
        });
        // console.log("modelsRes", modelsRes)

        modelStore.modelsList = modelsRes.res

        for (let i = 0; i < modelStore.modelsList.length; i++) {

            const timestamp_created = modelStore.modelsList[i].createdtime
            const timestamp_lastupdated = modelStore.modelsList[i].lastupdatedtime
            const dateObject_created = new Date(timestamp_created);
            const dateObject_lastupdated = new Date(timestamp_lastupdated);

            // 获取可读的时间字符串
            modelStore.modelsList[i].createdtimeFormat = dateObject_created.toLocaleString();
            modelStore.modelsList[i].lastupdatedtimeFormat = dateObject_lastupdated.toLocaleString();


            modelStore.modelsList[i].PandVOBJ = JSON.parse(modelStore.modelsList[i].paramsvalues)
            modelStore.modelsList[i].ConsOBJ = JSON.parse(modelStore.modelsList[i].cons)
            modelStore.modelsList[i].NumofParams = modelStore.modelsList[i].PandVOBJ.length
            modelStore.modelsList[i].NumofCons = modelStore.modelsList[i].ConsOBJ.length
            let transformedData = modelStore.modelsList[i].PandVOBJ.map(item => {
                // 将逗号分隔的字符串转换为数组
                const valueArray = item.Value.split(',');

                // 更新对象的Value字段为数组
                return {
                    ...item,
                    Value: valueArray
                };
            });
            modelStore.modelsList[i].PandVOBJ = transformedData
            // 移除 row_index 属性
            let tableDataTmp = modelStore.modelsList[i].PandVOBJ.map(item => {
                const { row_index, ...rest } = item;
                return rest;
            });
            modelStore.modelsList[i].PandVOBJ = tableDataTmp


            const res = await request({
                url: '/tools/listByID',
                method: 'POST',
                data: {
                    searchkeywords: modelStore.modelsList[i].modelid,
                    column: "modelid"
                }
            });

            modelStore.modelsList[i].NumOfTestSuites = res.TestSutiesRES.length


        }





    } catch (error) {
        console.error("发生错误", error);
    }



}

const listModelInfoByModelID = async (modelid) => {
    try {
        const res = await request({
            url: '/tools/listByID',
            method: 'POST',
            data: {
                searchkeywords: modelid,
                column: "modelid"
            }
        });
        // console.log("res ModelInfo", res)
        currentModel.currentModel = {}
        currentModel.currentModel.modelid = res.ModelRES.modelid
        // currentModel.currentModel.strength = res.ModelRES.strength
        currentModel.currentModel.modelname = res.ModelRES.modelname
        currentModel.currentModel.modeldescriptions = res.ModelRES.modeldescriptions
        currentModel.currentModel.paramsvalues = res.ModelRES.paramsvalues
        currentModel.currentModel.cons = res.ModelRES.cons
        currentModel.currentModel.lastupdatedtime = res.ModelRES.lastupdatedtime
        currentModel.currentModel.createdtime = res.ModelRES.createdtime

        currentModel.currentModel.PandVOBJ = JSON.parse(currentModel.currentModel.paramsvalues)
        currentModel.currentModel.ConsOBJ = JSON.parse(currentModel.currentModel.cons)

        if (currentModel.currentModel.ConsOBJ) {
            // 将约束直接变为禁止元组的形式
            let ForbiddenTuples = [];

            currentModel.currentModel.ConsOBJ.forEach(constraintObj => {
                let constraintValues = [];

                currentModel.currentModel.PandVOBJ.forEach(parameter => {
                    let constraintItem = constraintObj.Constraint.find(item => item.Parameter === parameter.Parameter);
                    constraintValues.push(constraintItem ? constraintItem.Value : null);
                });

                ForbiddenTuples.push(constraintValues);
            });


            currentModel.currentModel.ForbiddenTuples = ForbiddenTuples
            currentModel.currentModel.NumofCons = currentModel.currentModel.ConsOBJ.length
        }

        if (currentModel.currentModel.PandVOBJ) {
            currentModel.currentModel.NumofParams = currentModel.currentModel.PandVOBJ.length

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

        }





        // console.log("listModelInfoByModelID currentModel.currentModel", currentModel.currentModel)


    } catch (err) {
        console.error(err);
    }
}


const listAllTestSuitesByModelID = async (ModelList) => {
    // console.log("ModelList", ModelList)
    try {
        let testSuitesArray = []

        // for (let model of modelStore.modelsList) {
        for (let model of ModelList) {
            const res = await request({
                url: '/tools/listByID',
                method: 'POST',
                data: {
                    searchkeywords: model.modelid,
                    column: "modelid"
                }
            })
            // console.log("listAllTestSuitesByModelID",res)
            let obj = {
                "model": model,
                "testSuites": res.TestSutiesRES
            }
            testSuitesArray.push(obj)
        }
        testSuitesStore.testSuitesList.length = 0
        testSuitesStore.TotalTestSuitesNum = 0

        testSuitesStore.testSuitesList.push(...testSuitesArray)
        for (let index = 0; index < testSuitesStore.testSuitesList.length; index++) {
            let element = testSuitesStore.testSuitesList[index];
            // console.log("element",element)
            testSuitesStore.TotalTestSuitesNum = testSuitesStore.TotalTestSuitesNum + element["testSuites"].length

            for (let i = 0; i < testSuitesStore.testSuitesList[index].testSuites.length; i++) {

                const timestamp_created = testSuitesStore.testSuitesList[index].testSuites[i].createdtime
                const timestamp_lastupdated = testSuitesStore.testSuitesList[index].testSuites[i].lastupdatedtime
                const dateObject_created = new Date(timestamp_created);
                const dateObject_lastupdated = new Date(timestamp_lastupdated);

                // 获取可读的时间字符串
                testSuitesStore.testSuitesList[index].testSuites[i].createdtimeFormat = dateObject_created.toLocaleString();
                testSuitesStore.testSuitesList[index].testSuites[i].lastupdatedtimeFormat = dateObject_lastupdated.toLocaleString();

                // 将字符串的testsuite转化为Json Obj
                testSuitesStore.testSuitesList[index].testSuites[i].testsuitescontents = JSON.parse(testSuitesStore.testSuitesList[index].testSuites[i].testsuitescontents)

                // 将EvaluationContents 转为Json obj
                testSuitesStore.testSuitesList[index].testSuites[i].evaluationcontents = JSON.parse(testSuitesStore.testSuitesList[index].testSuites[i].evaluationcontents)

                // 将 Diagnosiscontents 转为Json obj
                testSuitesStore.testSuitesList[index].testSuites[i].diagnosiscontents = JSON.parse(testSuitesStore.testSuitesList[index].testSuites[i].diagnosiscontents)



                // console.log("testSuitesStore.testSuitesList[index].testSuites[i]",testSuitesStore.testSuitesList[index].testSuites[i])
            }

        }
        // console.log("listAllTestSuitesByModelID testSuitesStore.testSuitesList",testSuitesStore.testSuitesList)



    } catch (error) {

    }

}


// 返回parameterName在tableData中的索引
// value在该parameterName中的索引
const findPosition = (paramsvalues, parameterName, value) => {
    for (let i = 0; i < paramsvalues.length; i++) {
        const parameter = paramsvalues[i].Parameter;
        const values = paramsvalues[i].Value.split(',');

        if (parameter === parameterName) {
            const valueIndex = values.indexOf(value);
            if (valueIndex !== -1) {
                return { parameterIndex: i, valueIndex };
            }
        }
    }

    return { parameterIndex: -1, valueIndex: -1 };
}

// 读取CurrentModel，构造其为CitHub Model
const CitHubModel = (model, strength) => {
    // 解析参数和参数取值的Json字符串
    // console.log("构造CitHubModel!")
    try {
        let APIObj = {}
        let paramsvalues = JSON.parse(model.paramsvalues)
        // 加载约束table
        let tempArray = []
        let param_count = 0
        for (let i = 0, len = paramsvalues.length; i < len; i++) {
            if (paramsvalues[i].Value != '') { tempArray.push(paramsvalues[i].Value.split(',').length) }
            if (paramsvalues[i].Parameter != '') { param_count = param_count + 1 }

        }
        // 统计模型基本数据

        if (model.modelname) { APIObj.system = model.modelname }
        APIObj.system = model.modelname
        APIObj.strength = strength
        APIObj.parameter = param_count
        APIObj.values = tempArray



        let consArray = JSON.parse(model.cons)
        // 对每个约束进行处理，将其转换成 '参数索引'/'取值索引' 的形式
        let consArrayToAPI = []
        for (const constraint of consArray) {
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

                        let constempObj = findPosition(paramsvalues, parameter, value)
                        let consString = `\'${constempObj.parameterIndex}/${constempObj.valueIndex}\'`
                        consArrayTemp.push(consString)
                    }
                }
                consArrayToAPI.push(consArrayTemp)
            }
        }
        APIObj.constraints = consArrayToAPI
        // 这里先返回对象，在实际调用API时再转为Json 
        // return JSON.stringify(APIObj).replace(/"/g, '')
        return APIObj

    } catch (error) {
        console.log("error", error)
    }
}

// 读取 CitHubModel 和 CurrentTestSuite 构造 CitHub Testsuite
const CitHubTestSuite = (CitHubModel, testsuite) => {
    CitHubModel.testsuite = testsuite
    return CitHubModel
}


export { listAllModelsByUserID, listAllTestSuitesByModelID, listModelInfoByModelID, CitHubModel, CitHubTestSuite }