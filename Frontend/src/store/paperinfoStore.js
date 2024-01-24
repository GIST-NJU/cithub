import { defineStore } from "pinia";
export const usePaperInfoStore = defineStore("paperinforStore", {
    state: () => {
        return {
            paperinfos: [],
            TypeofPapers:"Combinatorial Testing",
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