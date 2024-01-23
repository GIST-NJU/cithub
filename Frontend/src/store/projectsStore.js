import { defineStore } from "pinia";
export const useProjectsStore = defineStore("ProjectsStore", {
    state: () => {
        return {
            projectList:'' //该用户所有的project的list

        }
    },
    actions: {

    },
    getters: {


    }
})