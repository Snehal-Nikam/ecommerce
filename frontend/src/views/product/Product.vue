<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h3> Our Products</h3>
        <router-link :to="{name: 'AddProduct'}" v-show="$route.name=='AdminProduct'" style="float: right">
          <button class="btn" >
            Add Product
          </button> </router-link>
      </div>
    </div>
    <div class="row">
      <div v-for="product of products" :key="product.id"
           class="col-md-6 col-xl-4 col-12 pt-3 d-flex">
        <ProductBox :product="product"/>
      </div>
    </div>
  </div>
</template>
<script>
import ProductBox from "@/components/ProductBox.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Product",
  components: {ProductBox},
  props:["products"]
}
</script>

<style scoped>

</style>