<template>
  <h1>HelloWorldComponent</h1>
  <ArgonButton @click="clickButton">getByid</ArgonButton>
 <div v-if="flag">
  <ArgonAlert>{{ result.id }}</ArgonAlert>
  <ArgonAlert>{{result.name}}</ArgonAlert>
  <ArgonAlert>{{ result.addr }}</ArgonAlert>
  <ArgonAlert>{{ result.salary }}</ArgonAlert>
 </div>
</template>
  
<script setup>
import { request } from '../../request'
import { onMounted, ref, reactive } from 'vue';
import ArgonButton from '../../components/ArgonButton.vue';
import ArgonAlert from '../../components/ArgonAlert.vue'

let result = reactive({})
let flag=ref(false)
const clickButton = () => {
  console.log(123)
  request(
    {
      url: "/userid/1",
      method: 'GET',
      // params: {
      //   q: '1',
      //   stx: 'stx'
      // }

    }
  ).then((res) => {
     console.log(res) 
     result.id=res.id
     result.name=res.name
     result.addr=res.addr
     result.salary=res.salary
     flag.value=true
  }).catch((err) => { console.log(err) })
}


</script>
  