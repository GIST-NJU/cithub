import { defineStore } from "pinia";
export const usePaperInfoStore = defineStore("paperinforStore", {
    state: () => {
        return {
            paperinfos: [],
            TypeofPapers:"Combinatorial Testing",
            paginationOffset:0,
            listAllpaperFlag:'',
            searchKeyWords:'',
            total:0,
            ChartType:'',
            
            
        }
    },
    actions: {

    },
    getters: {
    

    }
})