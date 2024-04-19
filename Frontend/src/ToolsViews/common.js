// 一些公有函数，供多个组件复用
import pinia from '../store/store'
import { useModelsStore } from '../store/modelsStore'
import { ElNotification } from 'element-plus'
import { request } from '../request';
import { useUserStore } from '../store/userStore';

const userStore = useUserStore(pinia)
const modelStore = useModelsStore(pinia)

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
        console.log("modelsRes", modelsRes)

        modelStore.modelsList = modelsRes.res

        for (let i = 0; i < modelStore.modelsList.length; i++) {

            const timestamp_created = modelStore.modelsList[i].createdtime
            const timestamp_lastupdated = modelStore.modelsList[i].lastupdatedtime
            const dateObject_created = new Date(timestamp_created);
            const dateObject_lastupdated = new Date(timestamp_lastupdated);

            // 获取可读的时间字符串
            modelStore.modelsList[i].createdtimeFortmat = dateObject_created.toLocaleString();
            modelStore.modelsList[i].lastupdatedtimeFortmat = dateObject_lastupdated.toLocaleString();


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
        }


        ElNotification({
            title: 'Choose a Model',
            message: 'Please choose a model to continue.',
            type: 'success',
        })

    } catch (error) {
        console.error("发生错误", error);
    }



}

export {listAllModelsByUserID}