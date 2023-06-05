<template>
  <div class="d-flex mb-3 mb-xl-0">
    <div class="me-3">
      <img width="72" height="72" :src="orderItem.product.imgURL" class="img-sm rounded border"/>
    </div>
    <div class="">
      <p class="mb-7" style="font-size: small">{{orderItem.product.productName.substring(0, 15)}}..</p>
      <strong style="font-size: small" > {{orderItem.quantity}}x = {{orderItem.price}} </strong>
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