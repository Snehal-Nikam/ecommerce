<template>
  <div class="d-flex mb-3 mb-xl-0">
    <div class="me-3">
      <img width="72" height="72" src="https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/10.webp" class="img-sm rounded border" />
    </div>
    <div class="">
      <p class="mb-0">T-shirts with multiple colors</p>
      <strong> 2x = $25.98 </strong>
    </div>
  </div>
</template>

<script>
export default {
  name: "OrderItemBox",
  props : ["orderItem"]
}
</script>

<style scoped>

</style>