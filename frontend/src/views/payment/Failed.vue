<template>
  <div class="text-center">
    <div class="spinner-grow text-danger" role="status">
      <span class="sr-only">Loading...</span>
      <h3>Payment Failed.</h3>
    </div>
  </div>
</template>

<script>
export default {
  name:'FailedPayment',
  mounted(){
    localStorage.removeItem("sessionId")
    window.location.href = '/order'
  }
}
</script>