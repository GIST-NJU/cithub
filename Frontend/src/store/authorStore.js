import { defineStore } from "pinia";
export const useAuthorStore = defineStore("AuthorsResultStore", {
    state: () => {
        return {
            authorsArray: [],
            country:''
            
        }
    },
    actions: {

    },
    getters: {
    

    }
})