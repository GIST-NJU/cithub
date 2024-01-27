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
                                    <form role="form">
                                        <div class="mb-3">
                                            <argon-input type="text" placeholder="Account" name="Account" size="lg" />
                                        </div>
                                        <div class="mb-3">
                                            <argon-input type="password" placeholder="Password" name="password" size="lg" />
                                        </div>
                                        <!-- <argon-switch id="rememberMe">Remember me</argon-switch> -->

                                        <div class="text-center">
                                            <argon-button class="mt-4" variant="gradient" color="primary" fullWidth
                                                size="lg">Sign in</argon-button>
                                        </div>
                                    </form>
                                </div>
                                <div class="px-1 pt-0 text-center card-footer px-lg-2">
                                    <p class="mx-auto mb-4 text-sm">
                                        Don't have an account?
                                        <a @click="JumpToRegister" class="text-primary text-gradient font-weight-bold" style="cursor:pointer">Sign
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

const router = useRouter()

const JumpToRegister = () => {

    router.push({
        name: 'UserRegister'
    })
}
</script>
  