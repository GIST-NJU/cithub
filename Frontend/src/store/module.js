import { defineStore } from "pinia";
export const useModuleStore = defineStore("ModuleStore", {
    state: () => {
        return {
            CurrentModule: '',
            CurrentModuleDetails:'',
            CurrentRoute:''
        }
    },
    actions: {

    },
    getters: {
    
    }
})