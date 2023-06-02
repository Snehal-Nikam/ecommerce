<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h3 class="pt-3">Our Categories</h3>
        <router-link :to="{ name: 'AddCategory' }" v-show="$route.name=='AdminCategory'">
          <button class="btn" style="float:right">Add Category</button>
        </router-link>
      </div>
    </div>
    <div class="row">
      <div
          v-for="category of categories"
          :key="category.id"
          class="col-xl-4 col-md-6 col-12 pt-3 d-flex">

          <CategoryBox :category="category"> </CategoryBox>
      </div>
    </div>
  </div>
</template>
<script>
//const axios = require("axios");
import CategoryBox from "../../components/CategoryBox.vue";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Category",
  components: { CategoryBox },
  props : ["categories" ],
  data() {
    return {
      baseURL: "http://localhost:8090",
    };
  },
  methods: {


  },
  mounted() {
    //this.getCategories();
    if (this.$route.name=='AdminCategory' && !localStorage.getItem('token')) {
      this.$router.push({name : 'Signin'});
    }
  }
};
</script>
<style scoped></style>