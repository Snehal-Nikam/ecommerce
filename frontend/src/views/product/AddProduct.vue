<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4>Add New Product</h4>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form>
          <div class="form-group">
            <label>Category</label>
            <select class="form-control" v-model="categoryId" required>
              <option v-for="category in categories" :key="category.id"
                      :value="category.id">{{ category.categoryName }}</option>
            </select>
          </div>
          <div class="form-group">
            <label>Name</label>
            <input type="text" v-model="name" class="form-control" required>
          </div>
          <div class="form-group">
            <label>Description</label>
            <input type="text" v-model="description" class="form-control" required>
          </div>
          <div class="form-group">
            <label>Image Url</label>
            <input type="text"  v-model="imageURL" class="form-control" required>
          </div>
          <div class="form-group">
            <label>Price</label>
            <input type="number" v-model="price" class="form-control" required>
          </div>
          <button type="button" class="btn btn-primary" @click="addProduct">Add Product</button>
        </form>
      </div>
      <div class="col-3"></div>
    </div>

  </div>
</template>

<script>
const axios = require("axios");
const alert = require("sweetalert");
export default {
  name: "AddProduct",
  props: ["baseURL", "categories"],
  data() {
    return {
      id: null,
      categoryId: null,
      name: null,
      description: null,
      imgURL: null,
      price: null,
      active: true,
      unitsInStock: 0
    }
  },
  methods:{
    addProduct() {
      const newProduct = {
        categoryId: this.categoryId,
        description: this.description,
        name: this.name,
        imgURL: this.imgURL,
        price: this.price,
        active: this.active,
        unitsInStock: this.unitsInStock
      };
      axios.post(this.baseURL+"product/create", newProduct)
          .then(() => {
            this.$emit("fetchData");
            this.$router.push({name: 'AdminProduct'});
            alert({
              text: "Product added",
              icon: "success"
            })
          })
          .catch((err)=> {
            console.log("err", err);
          })
    }
  },
  mounted() {
    if (!localStorage.getItem('token')) {
      this.$router.push({name : 'Signin'});
    }
  }
}
</script>

<style scoped>
h4 {
  font-family: 'Roboto', sans-serif;
  color: #484848;
  font-weight: 700;
}

</style>