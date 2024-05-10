<template>
  <main class="main-content mt-0">
    <div class="page-header align-items-start min-vh-50 pt-5 pb-11 m-3 border-radius-lg"
      style="background-image: url(images/header.jpg); background-size: cover;">
      <span class="mask bg-gradient-dark opacity-6"></span>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-5 text-center mx-auto">
            <h1 class="text-white mb-2 mt-5">Welcome!</h1>
            <p class="text-lead text-white">
              CitHub is free to use, but we kindly ask you to provide your name and the name of your institution,
              so that we could understand where the platform is being used.
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row mt-lg-n10 mt-md-n11 mt-n10 justify-content-center">
        <div class="col-xl-4 col-lg-5 col-md-7 mx-auto">
          <div class="card z-index-0">
            <div class="card-header text-center pt-4">
              <h5>Register with</h5>
            </div>
            <div class="card-body">
              <form role="form" @submit.prevent="register">
                <argon-input v-model="registerform.account" id="account" type="text" placeholder="Account"
                  aria-label="Account" />
                <div class="my-2">
                  <span style="margin:0px;font:14px;color: green;" v-if="displayCheckResult()">This
                    user name is available</span>
                  <span style="margin:0px;font:14px;color: red;" v-else-if="displayCheckResult() == false">This user name
                    is already been
                    taken</span>
                </div>
                <argon-input v-model="registerform.password" id="password" type="password" placeholder="Password"
                  aria-label="Password" />
                <argon-input v-model="registerform.name" id="name" type="text" placeholder="Full Name"
                  aria-label="Name" />
                <argon-input v-model="registerform.institution" id="institution" type="text"
                  placeholder="Current Institution" aria-label="Institution" />
                <argon-input v-model="registerform.email" id="email" type="email" placeholder="Email"
                  aria-label="email" />
                <argon-input v-model="registerform.country" id="country" type="text" placeholder="Country"
                  aria-label="Country" />
                <argon-checkbox checked>
                  <label class="form-check-label" for="flexCheckDefault">
                    I agree the
                    <a href="#" class="text-dark font-weight-bolder">Terms and Conditions</a>
                  </label>
                </argon-checkbox>
                <div class="text-center">
                  <argon-button fullWidth color="dark" variant="gradient" class="my-4 mb-2" @click="register">Sign
                    up</argon-button>
                </div>
                <p class="text-sm mt-3 mb-0">
                  Already have an account?
                  <a style="cursor:pointer" @click="jumpToLogin" class="text-dark font-weight-bolder">Sign in</a>
                </p>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Foot></Foot>
  </main>
</template>

<script setup>
import Foot from '../CustomizedComponents/Foot.vue';
import ArgonInput from "../CustomizedComponents/ArgonInput.vue";
import ArgonButton from "../CustomizedComponents/ArgonButton.vue";
import ArgonCheckbox from "../CustomizedComponents/ArgonCheckbox.vue";
import { ref, reactive, watch } from 'vue'
import { requestAuth } from '../request'
import { useRouter } from 'vue-router';
import { ElNotification } from 'element-plus'
import {encryptPassword} from './commonFunction.js'

const router = useRouter()
let registerform = reactive({
  name: '',
  institution: '',
  account: '',
  password: '',
  country: '',
  email: '',
})

const jumpToLogin = () => {
  router.push({
    name: 'UserLogin'
  })
}
const usernamecheckResult = ref('')

watch(async () => registerform.account, async (newVal, oldVal) => {
  try {
    const resolvedVal = await newVal;

    const res = await requestAuth({
      url: 'user/users/CheckUnique',
      method: 'POST',
      data: {
        account: resolvedVal
      }
    });
    // console.log("res", res)
    usernamecheckResult.value = res.checkresult;
  } catch (error) {
    console.error("Error:", error);
  }
});

const displayCheckResult = () => {
  if (usernamecheckResult.value == "account is available" && registerform.account != '') {
    return true
  }
  else if (usernamecheckResult.value == "account is unavailable" && registerform.account != '') {

    return false
  }

}


const registerFlag = ref()

// 检查邮箱是否合法的函数
const isValidEmail = (email) => {
  // 使用正则表达式进行验证
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}


const register = async () => {
  try {

    let errorMessage = ''; // 用于存储错误消息

    // 检查字段是否为空，并构建错误消息
    if (!registerform.account) {
      errorMessage += 'Account is required.<br> ';
    }
    if (!registerform.password) {
      errorMessage += 'Password is required.<br> ';
    }
    if (!registerform.name) {
      errorMessage += 'Full Name is required.<br> ';
    }
    if (!registerform.institution) {
      errorMessage += 'Current Institution is required.<br> ';
    }

    if (!registerform.email) {
      errorMessage += 'Email is required.<br> ';
    } else if (!isValidEmail(registerform.email)) {
      errorMessage += 'Invalid email format.<br> ';
    }

    if (!registerform.country) {
      errorMessage += 'Country is required.<br> ';
    }


    // 如果有任何字段为空或者邮箱格式不正确，则显示错误消息，并返回不发送请求
    if (errorMessage) {
      ElNotification({
        title: 'Error',
        message: errorMessage,
        type: 'error',
        position: 'top-left',
        dangerouslyUseHTMLString: true
      });
      return;
    }

    registerform.password=encryptPassword(registerform.password)
    const res = await requestAuth({
      url: '/user/users/RegisterUser',
      method: 'POST',
      data: registerform
    });

    registerFlag.value = res.msg
    if (res.msg === "success") {

      ElNotification({
        title: 'Sign Up Success!',
        message: 'please sign in!',
        type: 'success',
        position: 'top-left',

      })

      router.push(
        {
          name: 'UserLogin'
        }
      )

    }
    else {

      ElNotification({
        title: 'Error',
        message: 'please check the Your inputs',
        type: 'error',
        position: 'top-left',

      })
    }
  } catch (error) {
    // 处理错误
  }
};


</script>


<style scoped></style>