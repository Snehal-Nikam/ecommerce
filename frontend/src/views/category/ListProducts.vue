<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3"> {{category.categoryName}}</h4>
        <h5> {{msg}} </h5>
      </div>
    </div>

    <div class="row">
      <img v-show="len == 0" class="img-fluid" src="../../assets/sorry.jpg" alt="Sorry">
      <div v-for="product of category.products" :key="product.id"
           class="col-md-6 col-xl-4 col-12 pt-3 justify-content-around d-flex">
        <ProductBox :product="product" />
      </div>
    </div>
  </div>
</template>

<script>
import ProductBox from "@/components/ProductBox.vue";
import axios from "axios";
export default {
  name: "ListProducts",
  components: {ProductBox},
  props : ["categories", "baseURL" ],
  data() {
    return {
      id: null,
      category: {},
      msg: '',
      cats: {}
    }
  },
  methods: {
    //temporary code need to move into mounted
    async getCategories(){
      await axios.get(this.baseURL + "/category/showAll")
          .then(res => {
            this.cats = res.data;
            console.log("cats :: "+ JSON.stringify(this.cats));
            this.id = this.$route.params.id;
            console.log("id : "+ this.id);
            //console.log(this.categories);
            this.category = this.cats.find(category => category.id == this.id)
            if (this.category.products.length == 0) {
              this.msg = "no products found";
            } else if (this.category.products.length == 1) {
              this.msg = "Only 1 product found";
            } else {
              this.msg = this.category.products.length + " products found";
            }
          }).catch(err =>{
            console.log("error : "+err);
          })


    }
  },
  mounted() {
    this.getCategories();
  }
}
</script>

<style scoped>
h4 {
  font-family: 'Roboto', sans-serif;
  color: #484848;
  font-weight: 700;
}

h5 {
  font-family: 'Roboto', sans-serif;
  color: #686868;
  font-weight: 300;
}
</style>