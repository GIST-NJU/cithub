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
    persist: {
        enabled: true // true 表示开启持久化保存
    },
    actions: {

    },
    getters: {


    }
})