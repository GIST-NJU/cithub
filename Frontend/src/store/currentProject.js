import { defineStore } from "pinia";
export const useCurrentProject = defineStore("CurrentProject", {
    state: () => {
        return {
            projectid:'',
            projectname:'',
            userid:'',
            projectdescriptions:'',
            createdtime:'',
            lastupdatedtime:'',

        }
    },
    actions: {

    },
    getters: {


    }
})