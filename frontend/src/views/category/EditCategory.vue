<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3"> Edit Category</h4>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form v-if="category">
          <div class="form-group">
            <label>Category Name</label>
            <input type="text" class="form-control"
                   v-model="category.categoryName"  required/>
          </div>
          <div class="form-group">
            <label>Description</label>
            <input type="text" class="form-control"
                   v-model="category.description"  required/>
          </div>
          <div class="form-group">
            <label>Image URL</label>
            <input type="url" class="form-control"
                   v-model="category.imageUrl"  required/>
          </div>
          <button type="button" class="btn btn-primary" @click="editCategory" >Submit</button>
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
  name: "EditCategory",
  data() {
    return {
      category: null,
      id: null
    }
  },
  props: ["baseURL", "categories"],
  methods: {
    async editCategory() {
      delete this.category["products"]
      console.log('category', this.category)
      await axios.post(`${this.baseURL}category/edit/${this.id}`,
          this.category)
          .then(() => {
            this.$emit("fetchData");
            this.$router.push({name: 'AdminCategory'})
            alert({
              text: "category has been updated successfully",
              icon: "success"
            })
          }).catch(err => console.log('err', err));
    }
  },
  mounted() {
    if (!localStorage.getItem('token')) {
      this.$router.push({name : 'Signin'});
      return;
    }
    this.id = this.$route.params.id;
    this.category = this.categories.find(category => category.id == this.id)
    console.log('category', this.category);
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