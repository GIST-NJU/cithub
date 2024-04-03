import { defineStore } from "pinia";
export const useModelsStore = defineStore("ModelsStore", {
    state: () => {
        return {
            modelsList:[], //该用户下的所有models

        }
    },
    actions: {

    },
    getters: {


    }
})