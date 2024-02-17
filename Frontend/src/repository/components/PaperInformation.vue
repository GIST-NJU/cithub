
<template>
  <div class="row">
    <div class="col-12">
      <div class="card mb-4">
        <div class="card-body pb-0">
          <h5>SamplingCA: effective and efficient sampling-based pairwise testing for highly configurable software systems
          </h5>
          <p class="text-muted mb-0"><a href="">Chuan Luo</a>, Qiyuan Zhao, Shaowei Cai, Hongyu Zhang, Chunming Hu</p>
          <p class="text-muted mb-2">ACM Joint European Software Engineering Conference and Symposium on the Foundations
            of Software Engineering. 2022: 1185-1197</p>

          <p>
            <span class="badge rounded-pill bg-primary">research field</span>&nbsp;
            <span class="badge rounded-pill bg-info"># tag 1</span>&nbsp;
            <span class="badge rounded-pill bg-info"># tag 2</span>
          </p>
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-12">
      <div class="card mb-4">
        <div class="card-body pb-0">
          <h5>Abstract</h5>
          <p>
            Combinatorial interaction testing (CIT) is an effective paradigm for testing highly configurable systems, and
            its
            goal is to generate a t-wise covering array (CA) as a test suite, where t is the strength of testing. It is
            recognized that pairwise testing (i.e., CIT with t=2) is the most common CIT technique, and has high fault
            detection capability in practice. The problem of pairwise CA generation (PCAG), which is a core problem in
            pairwise testing, aims at generating a pairwise CA (i.e., 2-wise CA) of minimum size, subject to hard
            constraints.
            The PCAG problem is a hard combinatorial optimization problem, which urgently requires practical methods for
            generating pairwise CAs (PCAs) of small sizes. However, existing PCAG algorithms suffer from the severe
            scalability issue; that is, when solving large-scale PCAG instances, existing state-of-the-art PCAG algorithms
            usually cost a fairly long time to generate large PCAs, which would make the testing of highly configurable
            systems both ineffective and inefficient. In this paper, we propose a novel and effective sampling-based
            approach
            dubbed SamplingCA for solving the PCAG problem. SamplingCA first utilizes sampling techniques to obtain a
            small
            test suite that covers valid pairwise tuples as many as possible, and then adds a few more test cases into the
            test suite to ensure that all valid pairwise tuples are covered. Extensive experiments on 125 public PCAG
            instances show that our approach can generate much smaller PCAs than its state-of-the-art competitors,
            indicating
            the effectiveness of SamplingCA. Also, our experiments show that SamplingCA runs one to two orders of
            magnitude
            faster than its competitors, demonstrating the efficiency of SamplingCA. Our results confirm that SamplingCA
            is
            able to address the scalability issue and considerably pushes forward the state of the art in PCAG solving.
          </p>
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-12">
      <div class="card mb-4">
        <div class="card-body pb-0">
          <h5>Paper Citation Relationships</h5>
          <p>...</p>
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-12">
      <div class="card mb-4">
        <div class="card-body pb-0">
          <h5>BibTex</h5>
          <pre class="mb-0">
@inproceedings{FirstAuthorFamilyName.PaperID,
  author={Chuan Luo, Qiyuan Zhao, Shaowei Cai, Hongyu Zhang, Chunming Hu},
  title={SamplingCA: effective and efficient sampling-based pairwise testing for highly configurable software systems},
  booktitle={SIGSOFT},
  pages={1185--1197},
  year={2022},
  doi={10.1145/3540250.3549155},
}
          </pre>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
import { defineProps, reactive, ref } from 'vue'
</script>


<style scoped></style>
