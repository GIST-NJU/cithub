<template>
    <div>
        <div class="min-height-300 bg-success position-absolute w-100"></div>
        <SideNav></SideNav>
        <main class="main-content position-relative border-radius-lg">
            <!-- Navbar -->
            <Navbar></Navbar>
            <!-- End Navbar -->

            <div class="container-fluid py-4">
                <div class="row">
                    <div class="col-12">
                        <div class="card mb-4">
                            <h2 style="margin:15px 0 0 0" class="text-center">User Guide & FAQ</h2>
                            <p class="text-lg text-center">This user guide gives a quick introduction to Tools of CitHub. If
                                any questions remain, please don't hesitate to contact us.</p>


                                      <!-- What is Combinatorial Testing? Section -->
                            <div class="accordion" id="combTestingAccordion">
                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="combTestingHeader">
                                        <button @click="CombTestingFlag = !CombTestingFlag" class="accordion-button"
                                            type="button" data-bs-toggle="collapse" data-bs-target="#combTestingCollapse"
                                            aria-expanded="true" aria-controls="combTestingCollapse">
                                            <i
                                                :class="CombTestingFlag ? 'bi bi-caret-down-fill' : 'bi bi-caret-right-fill'"></i>
                                            What is Combinatorial Testing?
                                        </button>
                                    </h2>
                                    <div id="combTestingCollapse" class="accordion-collapse collapse"
                                        aria-labelledby="combTestingHeader" data-bs-parent="#combTestingAccordion">
                                        <div class="accordion-body">
                                            Combinatorial testing is an approach to testing that fuses relatively small test
                                            sets (here called Covering Arrays) with mathematically guaranteed coverage.
                                            Unlike other measurements such as statement coverage, which usually require
                                            instrumentation or rigorous modelling of the system under test, combinatorial
                                            coverage is a more abstract concept: In a Covering Array, for all combinations
                                            of t parameters (often called strength), all possible value combinations (we
                                            usually call them t-tuples) occur at least once. The cartesian product of all
                                            parameter values is a Covering Array, although a very large (and thus
                                            undesirable) one. Pairwise testing is a special case of combinatorial testing
                                            where t is always 2.

                                            In practice, empirical studies have shown that strength 6 is sufficient to find
                                            almost all bugs while greatly reducing the size of test sets (and thus the cost
                                            of executing these tests).

                                            Note that Covering Arrays are the weaker form of another combinatorial structure
                                            called Orthogonal Arrays, which contain all t-tuples exactly once (instead of
                                            one or more times). This makes Orthogonal Arrays the optimal version of Covering
                                            Arrays; unfortunately, they can only be found for some specific configurations,
                                            whereas Covering Arrays exist for all valid configurations.
                                            <!-- Content for Combinatorial Testing -->
                                            <!-- Add your content here -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Overview Section -->
                            <div class="accordion" id="overviewAccordion">
                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="overviewHeader">
                                        <button @click="OverviewFlag = !OverviewFlag" class="accordion-button" type="button"
                                            data-bs-toggle="collapse" data-bs-target="#overviewCollapse"
                                            aria-expanded="true" aria-controls="overviewCollapse">
                                            <i
                                                :class="OverviewFlag ? 'bi bi-caret-down-fill' : 'bi bi-caret-right-fill'"></i>
                                            CitHub Tools Overview
                                        </button>
                                    </h2>
                                    <div id="overviewCollapse" class="accordion-collapse collapse"
                                        aria-labelledby="overviewHeader" data-bs-parent="#overviewAccordion">
                                        <div class="accordion-body">
                                            1
                                            <!-- Content for Overview -->
                                            <!-- Add your content here -->
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <!-- Feature Overview Section -->
                            <div class="accordion" id="featureOverviewAccordion">
                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="featureOverviewHeader">
                                        <button @click="FeatureOverviewFlag = !FeatureOverviewFlag" class="accordion-button"
                                            type="button" data-bs-toggle="collapse"
                                            data-bs-target="#featureOverviewCollapse" aria-expanded="true"
                                            aria-controls="featureOverviewCollapse">
                                            <i
                                                :class="FeatureOverviewFlag ? 'bi bi-caret-down-fill' : 'bi bi-caret-right-fill'"></i>
                                            Features In CitHub Tools
                                        </button>
                                    </h2>
                                    <div id="featureOverviewCollapse" class="accordion-collapse collapse"
                                        aria-labelledby="featureOverviewHeader" data-bs-parent="#featureOverviewAccordion">
                                        <div class="accordion-body">
                                     
                                            <!-- Content for Feature Overview -->
                                            <!-- Add your content here -->
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Web-GUI Section -->
                            <div class="accordion" id="webGuiAccordion">
                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="webGuiHeader">
                                        <button @click="WebGuiFlag = !WebGuiFlag" class="accordion-button" type="button"
                                            data-bs-toggle="collapse" data-bs-target="#webGuiCollapse" aria-expanded="true"
                                            aria-controls="webGuiCollapse">
                                            <i :class="WebGuiFlag ? 'bi bi-caret-down-fill' : 'bi bi-caret-right-fill'"></i>

                                            Web-GUI
                                        </button>
                                    </h2>
                                    <div id="webGuiCollapse" class="accordion-collapse collapse"
                                        aria-labelledby="webGuiHeader" data-bs-parent="#webGuiAccordion">
                                        <div class="accordion-body">
                                            4
                                            <!-- Content for Web-GUI -->
                                        <!-- Add your content here -->
                                    </div>
                                </div>
                            </div>
                            </div>

                            
                      

                    </div>
                </div>
            </div>

            <Foot></Foot>
        </div>
    </main>
</div></template>

<script setup>
import Foot from '../ComponentCommon/Foot.vue';
import Navbar from '../ComponentCommon/Navbar.vue';
import SideNav from './components/SideNav.vue'
import { ref } from 'vue';

const OverviewFlag = ref(false)
const CombTestingFlag = ref(false)
const FeatureOverviewFlag = ref(false);
const WebGuiFlag = ref(false);
</script>

<style scoped>/* Add any custom styles here */</style>
