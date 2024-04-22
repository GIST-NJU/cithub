import { defineStore } from "pinia";
export const useTestSuitesStore = defineStore("TestSuitesStore", {
    state: () => {
        return {
            testSuitesList:[], 
            TotalTestSuitesNum:0

        }
    },
    actions: {

    },
    getters: {


    }
})