<template>

		<!-- Header -->
        <div id="header">
				<span class="logo icon fa-cube"></span>
				<h1>CitHub</h1>
				<p>An open platform for the study and use<br>
				of Combinatorial Interaction Testing (CIT)</p>
				<br>
				<input @click="jumpToUser('login')" type="submit" value="Login"/>&nbsp;&nbsp;
				<input @click="jumpToUser('register')" type="submit" value="Register"/>
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
						<a  class="image icon solid fa-university" @click="jumpToRepo" style="cursor:pointer">
                            <img src="./assets/images/repository.jpg" alt="" /></a>
						<div class="content">
							<h3>Repository</h3>
							<p>A web application that provides a full coverage of publications in the literature of combinatorial interaction testing.</p>
						</div>
					</section>
					<section class="feature right">
						<a class="image icon solid fa-code" @click="jumpToTools" style="cursor:pointer"><img src="./assets/images/algorithm.jpg" alt="" /></a>
						<div class="content">
							<h3>Tools</h3>
							<p>A series of web services that implement automated test suite (covering array) generation, optimisation, and fault diagnosis activities.</p>
						</div>
					</section>
					<section class="feature left">
						<a class="image icon solid fa-stream" @click="jumpToBenchmark" style="cursor:pointer"><img src="./assets/images/benchmark.jpg" alt="" /></a>
						<div class="content">
							<h3>Benchmark</h3>
							<p>A collection of real-world test models and evaluation results for comparing combinatorial interaction testing algorithms.</p>
							<p><em>(under construction)</em></p>
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
						<b>RestCT</b> is a systematic and automatic black-box testing tool for testing RESTful APIs. This tool takes a Swagger specification file (in JSON format) as the input, and adopts combinatorial interaction testing to automatically generate and send HTTP requests to exercise service behaviours <b>[ICSE 2022]</b>.
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

					<p>The CitHub project is developed and maintained by the <a href="https://gist.nju.edu.cn">GIST</a> team, which is affiliated with the Department of Computer Science and Technology at Nanjing University.</p>
				  <p>Feel free to contract us for any issue or suggestion :-)</p>

					<ul class="copyright">
						<li>&copy; GIST. All rights reserved.</li>
					</ul>

				</div>
			</div>
            
</template>

<script  setup>
import { useRouter  } from 'vue-router';
import { useModuleStore } from './store/module';
const moduleStore=useModuleStore()
const router=useRouter()
const jumpToRepo =()=>{
    moduleStore.CurrentModule='Repository'
    router.push({
        path:'/repo/home'
    })
}
const jumpToTools =()=>{
    moduleStore.CurrentModule='Tools'
    router.push({
        path:'/tools/home'
    })
}
const jumpToBenchmark =()=>{
    moduleStore.CurrentModule='Benchmark'
    router.push({
        path:'/benchmark/home'
    })
}


const jumpToUser =(value)=>{
    // console.log("value是",value)
    router.push({
        path:'/user/home'
    })
}
</script>


<style scoped>

</style>
