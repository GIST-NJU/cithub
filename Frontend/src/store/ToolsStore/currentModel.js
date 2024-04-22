import { defineStore } from "pinia";
export const useCurrentModel = defineStore("CurrentModel", {
    state: () => {
        return {
            currentModel: {},
            currentModelTestSuites:{}
            
        }
    },
    actions: {

    },
    getters: {


    }
})