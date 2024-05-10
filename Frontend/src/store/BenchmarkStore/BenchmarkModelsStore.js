import { defineStore } from "pinia";
export const useBenchmarkModelsStore = defineStore("BenchmarkModelsStore", {
    state: () => {
        return {
            models: [],
            paginationOffset:0,
            searchKeyWords:'',
            total:0,
            
        }
    },
    actions: {

    },
    getters: {
    

    }
})