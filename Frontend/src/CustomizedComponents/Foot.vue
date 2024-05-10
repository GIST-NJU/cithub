
<template>
  <footer class="footer pt-3">
    <div class="container-fluid">
      <div class="row align-items-center justify-content-lg-between">
        <div class="col-lg-6 mb-lg-0 mb-4">
          <div class="copyright text-center text-sm text-muted text-lg-start" style="cursor:pointer;"
          @click="JumpToGist">
            GIST @ Nanjing Univeristy
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<script setup>
const JumpToGist=()=>{
  window.open('https://gist.nju.edu.cn/',"_blank")
}
</script>
<style scoped></style>
