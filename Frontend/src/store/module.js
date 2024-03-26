import { defineStore } from "pinia";
export const useModuleStore = defineStore("ModuleStore", {
    state: () => {
        return {
            CurrentModule: '',
            CurrentModuleDetails:'',
        }
    },
    actions: {

    },
    getters: {
    
    }
})