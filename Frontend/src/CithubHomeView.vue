<template>
	<!-- Header -->
	<div id="header">
		<span class="logo icon fa-cube"></span>
		<h1>CitHub</h1>
		<p>An open platform for the study and use<br>
			of Combinatorial Interaction Testing (CIT)</p>
		<br>
		<div v-if="userStore.loginFlag == false">
			<input @click="jumpToUser('login')" type="submit" value="Sign in" />&nbsp;&nbsp;
			<input @click="jumpToUser('register')" type="submit" value="Sign Up" />
		</div>
		<div v-if="userStore.loginFlag == true">

			<h3>Welcome {{ userStore.name }} from {{ userStore.institution }}, enjoy cithub~</h3>

		</div>

	</div>

	<!-- Main -->
	<div id="main">
		<header class="major container medium">
			<h2>We provide services for
				<br />
				researches and practitioner of CIT
			</h2>
		</header>

		<div class="box alt container">
			<section class="feature left">
				<a class="image icon solid fa-university" @click="jumpToRepo" style="cursor:pointer">
					<img src="images/repository.jpg" alt="" /></a>
				<div class="content">
					<h3>Repository</h3>
					<p>A web application that provides a full coverage of publications in the literature of combinatorial
						interaction testing.</p>
				</div>
			</section>
			<section class="feature right">
				<a class="image icon solid fa-code" @click="jumpToTools" style="cursor:pointer"><img
						src="images/algorithm.jpg" alt="" /></a>
				<div class="content">
					<h3>Tools</h3>
					<p>A series of web services that implement automated test suite (covering array) generation,
						optimisation, and fault diagnosis activities.</p>
				</div>
			</section>
			<section class="feature left">
				<a class="image icon solid fa-stream" @click="jumpToBenchmark" style="cursor:pointer"><img
						src="images/benchmark.jpg" alt="" /></a>
				<div class="content">
					<h3>Benchmark</h3>
					<p>A collection of real-world test models and evaluation results for comparing combinatorial interaction
						testing algorithms.</p>
					<!-- <p><em>(under construction)</em></p> -->
				</div>
			</section>
			<!-- <section class="feature right">
						<a class="image icon solid fa-cube" @click="jumpToLLMModelling" style="cursor:pointer"><img src="./assets/images/LLMModelling.jpg" alt="" /></a>
						<div class="content">
							<h3>LLM Modelling</h3>
							<p>A web application that provides fully automatic and user-friendly graphic interface for modelling of Combinatorial Testing </p>
							<p><em>(under construction)</em></p>
						</div>
					</section> -->
		</div>


		<footer class="major container medium">
			<h2>Applications</h2>

			<header>
				<h3>RestCT</h3>
				<p>For RESTful APIs</p>
			</header>
			<p>
				<b>RestCT</b> is a systematic and automatic black-box testing tool for testing RESTful APIs. This tool takes
				a Swagger specification file (in JSON format) as the input, and adopts combinatorial interaction testing to
				automatically generate and send HTTP requests to exercise service behaviours <b>[ICSE 2022]</b>.
			</p>
			<ul class="actions special">
				<li><a href="https://github.com/GIST-NJU/RestCT" class="button">Code</a></li>
			</ul>
		</footer>

	</div>

	<!-- Footer -->
	<div id="footer">
		<div class="container medium">

			<header class="major last">
				<h2>About</h2>
			</header>

			<p>The CitHub project is developed and maintained by the <a href="https://gist.nju.edu.cn">GIST</a> team, which
				is affiliated with the Department of Computer Science and Technology at Nanjing University.</p>
			<p>Feel free to contract us for any issue or suggestion :-)</p>

			<ul class="copyright">
				<li>&copy; GIST. All rights reserved.</li>
			</ul>

		</div>
	</div>
</template>

<script  setup>

import { useModuleStore } from './store/module';
import { useRouter, useRoute } from 'vue-router';
import { onMounted, ref } from 'vue';
import { CheckLogin } from './request';
import pinia from './store/store'

import { ElNotification } from 'element-plus'
import { useUserStore } from './store/userStore';
const userStore = useUserStore(pinia)
const router = useRouter()
const route = useRoute()
const moduleStore = useModuleStore(pinia)

const jumpToRepo = () => {
	moduleStore.CurrentModule = 'Repository'
	moduleStore.CurrentModuleDetails = '',
	moduleStore.CurrentRoute = 'Repository_Home'
	router.push({
		name: 'Repository_Home'
	})
}
const jumpToTools = () => {
	moduleStore.CurrentModule = 'Tools'
	moduleStore.CurrentModuleDetails = '',
	moduleStore.CurrentRoute = ''
	router.push({
		path: '/tools/models'
	})
}
const jumpToBenchmark = () => {
	moduleStore.CurrentModule = 'Benchmark'
	moduleStore.CurrentModuleDetails = '',
	moduleStore.CurrentRoute = 'Benchmark_Home'
	router.push({
		path: '/benchmark/home'
	})
}


const jumpToUser = (value) => {
	// console.log("value是",value)
	if (value == 'login') {
		router.push({
			name: 'UserLogin'
		})
	}
	if (value == 'register') {
		router.push({
			name: 'UserRegister'
		})
	}
}


onMounted(async () => {


	await CheckLogin()


})
</script>


<style src="../public/CithubIndex.css" scoped>
/* 不能使用下面的写法，这样会污染其他视图的样式，一个大坑！ */
/* @import  url(/CithubIndex.css) */
</style>
