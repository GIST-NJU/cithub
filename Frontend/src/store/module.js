import { defineStore } from "pinia";
export const useModuleStore = defineStore("ModuleStore", {
    state: () => {
        return {
            CurrentSubSystem: '',//记录子系统
            CurrentSubSystemRoute:'', //记录子系统 的路由

            CurrentModule: '',//记录子系统下的各功能模块
            CurrentRoute:'', //记录子系统 各模块 的路由

            CurrentModuleDetails:'',//记录各功能模块下的具体细节

        }
    },
    actions: {

    },
    getters: {
    
    }
})