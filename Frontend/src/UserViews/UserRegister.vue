<template>
    <main class="main-content mt-0">
        <div class="page-header align-items-start min-vh-50 pt-5 pb-11 m-3 border-radius-lg"
            style="background-image: url('https://raw.githubusercontent.com/creativetimofficial/public-assets/master/argon-dashboard-pro/assets/img/signup-cover.jpg'); background-position: top;">
            <span class="mask bg-gradient-primary opacity-6"></span>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5 text-center mx-auto">
                        <h1 class="text-white mb-2 mt-5">Welcome!</h1>
                        <p class="text-lead text-white">Cithub is a Combinatorial Interaction Testing open platform for
                            Repo, Tools and Benchmark of CIT activities.</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row mt-lg-n10 mt-md-n11 mt-n10 justify-content-center">
                <div class="col-xl-4 col-lg-5 col-md-7 mx-auto">
                    <div class="card z-index-0">
                        <div class="card-header text-center pt-4">
                            <h5>Sign Up</h5>
                        </div>

                        <div class="card-body">

                            <el-form label-position="top" label-width="80px" :model="registerform" style="width:100%">
                                <el-form-item prop="name" required>
                                    <el-input v-model="registerform.name" placeholder="please input your name">
                                    </el-input>
                                </el-form-item>
                                <el-form-item prop="institution" required>
                                    <el-input v-model="registerform.institution"
                                        placeholder="please input your institution">
                                    </el-input>
                                </el-form-item>
                                <el-form-item prop="email" required>
                                    <el-input v-model="registerform.email" placeholder="please input your email">
                                    </el-input>
                                </el-form-item>
                                <el-form-item prop="account" required>
                                    <el-input v-model="registerform.account" placeholder="please input your account">
                                    </el-input>
                                    <div v-auto-animate>
                                        <span style="margin:0px;font:14px;color: green;" v-if="displayCheckResult()">This
                                            user name is available</span>
                                        <span style="margin:0px;font:14px;color: red;"
                                            v-else-if="displayCheckResult() == false">This user name is already been
                                            taken</span>
                                    </div>
                                </el-form-item>


                                <el-form-item prop="password" required>
                                    <el-input v-model="registerform.password" show-password
                                        placeholder="please input your password"></el-input>
                                </el-form-item>
                                <el-form-item prop="passwordagain" required>
                                    <el-input v-model="registerform.passwordagain" show-password
                                        placeholder="please input your password again"></el-input>
                                </el-form-item>

                            </el-form>
                            <div v-auto-animate>
                                <span v-if="registerFlag == 'success'" style="margin:0px;font:14px;color: green;">Sign up
                                    Success! Rendering into SignIn Page in {{ timecountdown }}s~</span>

                            </div>
                            <div class="text-center">
                                <argon-button @click="register" class="mt-4" variant="gradient" color="primary" fullWidth
                                    size="lg">Sign in</argon-button>
                            </div>
                            <p class="text-sm mt-3 mb-0">
                                Already have an account?
                                <a @click="jumpToLogin" style="cursor: pointer;" class="text-dark font-weight-bolder">Sign
                                    in</a>
                            </p>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <Foot></Foot>
</template>

<script  setup>
import Foot from '../CustomizedComponents/Foot.vue';
import ArgonInput from "../CustomizedComponents/ArgonInput.vue";
import ArgonButton from "../CustomizedComponents/ArgonButton.vue";
import ArgonCheckbox from "../CustomizedComponents/ArgonCheckbox.vue";
import { ref, reactive, watch } from 'vue'
import { requestAuth } from '../request'
import { useRouter } from 'vue-router';
import { ElNotification } from 'element-plus'

const router = useRouter()

let registerform = reactive({
    name: '',
    institution: '',
    account: '',
    password: '',
    passwordagain: null,
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
        // console.log('Account changed:', resolvedVal);

        const res = await requestAuth({
            url: 'user/users/CheckUnique',
            method: 'POST',
            data: {
                account: resolvedVal
            }
        });

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
let timecountdown = ref(3)

const registerFlag = ref()
const register = async () => {
    try {
        const res = await requestAuth({
            url: '/user/users/RegisterUser',
            method: 'POST',
            data: registerform
        });

        timecountdown.value = 3;
        registerFlag.value = res.msg
        if (res.msg === "success") {

            ElNotification({
                title: 'Sign Up Success!',
                message: 'please sign in!',
                type: 'success',
                position: 'top-left',

            })
            let countdown = setInterval(() => {
                timecountdown.value--;
                if (timecountdown.value == 0) clearInterval(countdown)
            }, 1000)

            setTimeout(() => {
                router.push(
                    {
                        name: 'UserLogin'
                    }
                )
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
    } catch (error) {
        // 处理错误
    }
};


</script>


<style scoped></style>
