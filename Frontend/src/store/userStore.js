import { defineStore } from "pinia";
export const useUserStore = defineStore("UserStore", {
    state: () => {
        return {
            userID:'',
            account: '',
            userToken: '',
            usertype:'',
            name:'',
            email:'',
            institution:'',

        }
    },
    actions: {

    },
    getters: {


    }
})