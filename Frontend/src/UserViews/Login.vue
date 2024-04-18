<template>
  <main class="mt-0 main-content">
    <section>
      <div class="page-header min-vh-100">
        <div class="container">
          <div class="row">
            <div class="mx-auto col-xl-4 col-lg-5 col-md-7 d-flex flex-column mx-lg-0">
              <div class="card card-plain">
                <div class="pb-0 card-header text-start">
                  <h4 class="font-weight-bolder">Log In</h4>
                  <p class="mb-0">Enter your Account and Password to log in</p>
                </div>
                <div class="card-body">
                  <form role="form" @submit.prevent="login">
                    <div class="mb-3">
                      <argon-input v-model="loginform.account" id="account" type="text" placeholder="Account"
                        name="account" size="lg" />
                    </div>
                    <div class="mb-3">
                      <argon-input v-model="loginform.password" id="password" type="password" placeholder="Password"
                        name="password" size="lg" />
                    </div>
                    <div class="text-center">
                      <argon-button @click="login" class="mt-4" variant="gradient" color="success" fullWidth size="lg">Log
                        in</argon-button>
                    </div>
                  </form>
                </div>
                <div class="px-1 pt-0 text-center card-footer px-lg-2">
                  <p class="mx-auto mb-4 text-sm">
                    Don't have an account?
                    <a style="cursor:pointer" class="text-success text-gradient font-weight-bold"
                      @click="JumpToRegister">Sign up</a>
                  </p>
                </div>
              </div>

            </div>
            <div
              class="top-0 my-auto text-center col-6 d-lg-flex d-none h-100 pe-0 position-absolute end-0 justify-content-center flex-column">
              <div
                class="position-relative bg-gradient-primary h-100 m-3 px-7 border-radius-lg d-flex flex-column justify-content-center overflow-hidden"
                style="background-image: url(/images/algorithm.jpg); background-size: cover;">
                <span class="mask bg-gradient-dark opacity-7"></span>
                <h4 class="mt-5 px-5 text-white font-weight-bolder position-relative">
                  "The test of the machine is the satisfaction it gives you. There isn’t any other test. If the machine
                  produces tranquility it’s right. If it disturbs you it’s wrong until either the machine or your mind is
                  changed."
                </h4>
                <p class="text-white position-relative">
                  Zen and the Art of Motorcycle Maintenance
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Foot></Foot>

    </section>
  </main>
</template>



<script setup>
import { onBeforeUnmount, onBeforeMount, reactive, ref } from "vue";
import { useStore } from "vuex";
import Foot from '../CustomizedComponents/Foot.vue';
import { useRouter } from 'vue-router';
import { requestAuth } from '../request'
import { useUserStore } from '../store/userStore'
import pinia from '../store/store';
import { ElNotification } from 'element-plus'
// import ArgonInput from "@/components/ArgonInput.vue";
// import ArgonButton from "@/components/ArgonButton.vue";
import ArgonInput from "../CustomizedComponents/ArgonInput.vue";
import ArgonButton from "../CustomizedComponents/ArgonButton.vue";
import { encryptPassword } from './commonFunction.js'


const userStore = useUserStore(pinia)
const router = useRouter()
const loginSuccessFlag = ref('')

const loginform = reactive({
  account: '',
  password: ''
})


const login = async () => {
  loginform.password = encryptPassword(loginform.password)
  try {
    const res = await requestAuth({
      url: '/user/users/login',
      method: 'POST',
      data: loginform,

    })
    loginSuccessFlag.value = res.status
    if (loginSuccessFlag.value == "LoginSuccess!") {

      // 登录成功，储存token
      localStorage.setItem("userToken", res.token)
      // 获取用户信息
      // console.log("登录成功，用户信息",res)
      userStore.userID = res.userid
      userStore.account = res.account
      userStore.userToken = res.token
      userStore.usertype = res.usertype
      userStore.name = res.name
      userStore.email = res.useremail
      userStore.institution = res.institution
      userStore.loginFlag = true
      console.log("try正确！")
      router.push({
        path: '/',
      })
    }
  }

  catch (error) {
    console.log(error)
    ElNotification({
      title: 'Error',
      message: 'please check Your inputs',
      type: 'error',
      position: 'top-left',

    })
  }
}
const JumpToRegister = () => {

  router.push({
    name: 'UserRegister'
  })
}
</script>

