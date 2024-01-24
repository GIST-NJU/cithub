import { defineStore } from "pinia";
export const useUserStore = defineStore("UserStore", {
    state: () => {
        return {
            UserID:1,
            UserName: 'xjt',
            userToken: '',
            usertype:'',

        }
    },
    actions: {

    },
    getters: {


    }
})