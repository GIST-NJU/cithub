<template>
    <!-- <div class="container top-0 position-sticky z-index-sticky">
      <div class="row">
        <div class="col-12">
          <navbar
            isBlur="blur  border-radius-lg my-3 py-2 start-0 end-0 mx-4 shadow"
            v-bind:darkMode="true"
            isBtn="bg-gradient-success"
          />
        </div>
      </div>
    </div> -->
    <main class="mt-0 main-content">
        <section>
            <div class="page-header min-vh-100">
                <div class="container">
                    <div class="row">
                        <div class="mx-auto col-xl-4 col-lg-5 col-md-7 d-flex flex-column mx-lg-0">
                            <div class="card card-plain">
                                <div class="pb-0 card-header text-start">
                                    <h4 class="font-weight-bolder">Sign In</h4>
                                    <p class="mb-0">Enter your account and password to sign in</p>
                                </div>
                                <div class="card-body">

                                    <el-form label-position="top" label-width="80px" :model="loginform" style="width:100%">
                                        <el-form-item prop="account">
                                            <el-input v-model="loginform.account" placeholder="please input your account">
                                            </el-input>
                                        </el-form-item>
                                        <el-form-item prop="password">
                                            <el-input v-model="loginform.password" show-password
                                                placeholder="please input your password"></el-input>
                                        </el-form-item>

                                        <!-- <el-button
                                                    style="color:black;width:100%;margin:20px 0px 5px 0px;background-color:darkgray;font-size:1rem"
                                                    @click="handelLogin">
                                                    login</el-button> -->
                                        <div v-auto-animate>
                                            <span v-if="loginSuccessFlag == 'LoginSuccess!'"
                                                style="margin:0px;font:14px;color: green;">Sign In Successfully! Rendering
                                                into Cithub in {{ timecountdown }}s~</span>
                                            <span v-if="loginSuccessFlag == 'LoginFail!'"
                                                style="margin:0px;font:14px;color: red;">Login failed! pleas check
                                                your inputs
                                                above</span>
                                        </div>
                                    </el-form>
                                    <div class="text-center">
                                        <argon-button @click="login" class="mt-4" variant="gradient" color="primary"
                                            fullWidth size="lg">Sign in</argon-button>
                                    </div>

                                    <!-- <form role="form">
                                        <div class="mb-3">
                                            <argon-input type="text" placeholder="Account" name="Account" size="lg"
                                                v-model ="loginForm.account" />
                                        </div>
                                        <div class="mb-3">
                                            <argon-input type="password" placeholder="Password" name="password" size="lg"
                                                v-model="loginForm.password" />
                                        </div>
                                    

                                        <div class="text-center">
                                            <argon-button @click="login"  class="mt-4" variant="gradient" color="primary"
                                                fullWidth size="lg">Sign in</argon-button>
                                        </div>
                                    </form> -->
                                </div>
                                <div class="px-1 pt-0 text-center card-footer px-lg-2">
                                    <p class="mx-auto mb-4 text-sm">
                                        Don't have an account?
                                        <a @click="JumpToRegister" class="text-primary text-gradient font-weight-bold"
                                            style="cursor:pointer">Sign
                                            up</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div
                            class="top-0 my-auto text-center col-6 d-lg-flex d-none h-100 pe-0 position-absolute end-0 justify-content-center flex-column">
                            <div class="position-relative bg-gradient-primary h-100 m-3 px-7 border-radius-lg d-flex flex-column justify-content-center overflow-hidden"
                                style="background-image: url('https://raw.githubusercontent.com/creativetimofficial/public-assets/master/argon-dashboard-pro/assets/img/signin-ill.jpg');
                                                                                    background-size: cover;">
                                <span class="mask bg-gradient-primary opacity-6"></span>
                                <h4 class="mt-5 text-white font-weight-bolder position-relative">Cithub:Combinatorial
                                    Interaction Testing open platform</h4>
                                <p class="text-white position-relative">Cithub is a Combinatorial Interaction Testing open
                                    platform for Repo, Tools and Benchmark of CIT activities.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
    <Foot></Foot>
</template>
  
<script setup>
import Foot from '../ComponentCommon/Foot.vue';
import ArgonInput from "../ComponentCommon/ArgonInput.vue";
import ArgonButton from "../ComponentCommon/ArgonButton.vue";
import { useRouter } from 'vue-router';
import { reactive, ref } from 'vue';
import { requestAuth } from '../request'
import { useUserStore } from '../store/userStore'
import pinia from '../store/store';
import { ElNotification } from 'element-plus'

const userStore = useUserStore(pinia)
const router = useRouter()
const loginSuccessFlag = ref('')

const loginform = reactive({
    account: '',
    password: ''
})
let timecountdown = ref(3)

const login = () => {
    requestAuth({
        url: '/user/users/login',
        method: 'POST',
        data: loginform,

    }).then((res) => {
        loginSuccessFlag.value = res.status
        if (res.status == "LoginSuccess!") {

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

            // console.log("userStore", userStore.userID,
            // userStore.account ,
            // userStore.userToken,
            // userStore.usertype ,
            // userStore.name,
            // userStore.email ,
            // userStore.institution )



            ElNotification({
                title: 'Success',
                message: 'enjoy cithub!',
                type: 'success',
                position: 'top-left',

            })

            let countdown = setInterval(() => {
                timecountdown.value--;
                if (timecountdown.value == 0) clearInterval(countdown)
            }, 1000)
            // 成功获取到usertoken，回到主页
            setTimeout(() => {
                router.push({
                    path: '/',
                    query:{
                        loginFlag:true
                    }
                
                
                })
            }, 3000)
        }

        else {
            ElNotification({
                title: 'Error',
                message: 'please check the Your inputs',
                type: 'error',
                position: 'top-left',

            })
        }
    }).catch(() => {
        ElNotification({
            title: 'Error',
            message: 'please check Your inputs',
            type: 'error',
            position: 'top-left',

        })
    })
}
const JumpToRegister = () => {

    router.push({
        name: 'UserRegister'
    })
}
</script>
  