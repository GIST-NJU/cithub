import { defineStore } from "pinia";
export const useVenueStore = defineStore("VenueStoreResult", {
    state: () => {
        return {
            VenueArray: [],
            VenueArrayPhd: [],
            VenueArrayBook: [],
            VenueArrayOther: [],
            
        }
    },
    actions: {

    },
    getters: {
    

    }
})