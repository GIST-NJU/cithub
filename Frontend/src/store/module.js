import { defineStore } from "pinia";
export const useModuleStore = defineStore("ModuleStore", {
    state: () => {
        return {
            CurrentModule: '',//记录子系统的名称
            CurrentModuleDetails:'',//记录各子系统下sidebar 模块的名称
            CurrentRoute:'' //记录路由名称，用于跳转
        }
    },
    actions: {

    },
    getters: {
    
    }
})