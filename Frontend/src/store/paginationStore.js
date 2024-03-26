import { defineStore } from "pinia";
export const usePaginationStore = defineStore("PaginationStore", {
    state: () => {
        return {
            //分页查询的传递的对象 
            pagesize: 25,
            // total: PaperInfoStore.total,
            total: 0,
            pagecount: 8,
            pagenum: 1,
            searchkeywords: "",
            column:'',
        }
    },
    actions: {

    },
    getters: {


    }
})