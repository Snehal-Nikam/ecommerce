<template>
  <div class="container">
    <div class="row pt-5">
      <div class="col-md-1"></div>
      <!--            display image-->
      <div class="col-md-4 col-12">
        <img :src="product.imgURL" class="img-fluid">
      </div>
      <!--            display product details-->
      <div class="col-md-6 col-12 pt-3 pt-md-0">
        <h4>{{ product.name }}</h4>
        <h6 class="catgory font-italic"> {{category.categoryName}}</h6>
        <h6 class="font-weight-bold"> $ {{product.price}}</h6>
        <p>
          {{ product.description }}
        </p>
        <div class="d-flex flex-row justify-content-between">
          <div class="input-group col-md-3 col-4 p-0">
            <div class="input-group-prepend">
              <span class="input-group-text">Quantity</span>
            </div>
            <input type="number" class="form-control" v-model="quantity" />
          </div>

          <div class="input-group col-md-3 col-4 p-0">
            <button class="btn" id="add-to-cart-button" @click="addToCart">
              Add to Cart
            </button>
          </div>
        </div>
        <div class="features pt-3">
          <h5><strong>Features</strong></h5>
          <ul>
            <li>Lorem ipsum dolor sit amet consectetur adipisicing elit.</li>
            <li>Officia quas, officiis eius magni error magnam voluptatem</li>
            <li>nesciunt quod! Earum voluptatibus quaerat dolorem doloribus</li>
            <li>molestias ipsum ab, ipsa consectetur laboriosam soluta et</li>
            <li>ut doloremque dolore corrupti, architecto iusto beatae.</li>
          </ul>
        </div>
        <button id="wishlist-button" class="btn mr-3 p-1 py-0" @click="addToWishList()" > {{ wishListString }} </button>
      </div>
    </div>
  </div>
</template>
<script>
import swal from "sweetalert";
import axios from "axios";
const axios = require("axios");
const alert = require("sweetalert");
export default {
  name: "ShowDetails",
  data() {
    return {
      product: {},
      category: {},
      wishListString: "Add to Wishlist",
    };
  },
  props: ["baseURL", "products", "categories"],
  methods: {
    addToWishList() {
      if (!this.token) {
        swal({
          message: "Please log in to add item in Wishlist.",
          icon: "error",
        });
        return;
      }
      axios
          .post(`${this.baseURL}wishlist/add?token=${this.token}`, {
        id: this.product.id,
      })
          .then((res) => {
            if (res.status === 201) {
              this.wishListString = "Item added to Wishlist";
              swal({
                message: "Item added to Wishlist.",
                icon: "success",
              });
            }
          })
          .catch((err) => {
              console.log("err", err);
          });
    },
  },
  mounted() {
    this.id = this.$route.params.id;
    this.product = this.products.find((product) => product.id == this.id)
    this.category = this.categories.find(
        (category) => category.id == this.product.categoryId
    );

    // eslint-disable-next-line no-undef
    this.token = localStorage,getItem("token");
  },
};
      quantity: 1
    }
  },
  props: ["baseURL", "products", "categories"],

  mounted() {
    this.id = this.$route.params.id;
    this.product = this.products.find((product) => product.id == this.id)
    this.category = this.categories.find(category =>
        category.id == this.product.categoryId);
    this.token = localStorage.getItem("token");
  },
  methods :{
    addToCart() {
      if (!this.token) {
        // user is not logged in
        // show some error
        alert({
          text: "please login to add item in cart",
          icon: "error",
        });
        console.log("**Path : "+this.$route.fullPath);
        this.$router.push({ name: 'Signin', query: { redirect: this.$route.fullPath } });
        return;
      }

      // add to cart

      axios
          .post(`${this.baseURL}/cart/add?token=${this.token}`, {
            productId: this.id,
            quantity: this.quantity,
          })
          .then((res) => {
            if (res.status == 201) {
              alert({
                text: "Product added in cart",
                icon: "success",
              });
              this.$emit("fetchData");
            }
          })
          .catch((err) => console.log("err", err));
    }
  },
}
</script>

<style scoped>
.category {
  font-weight: 400;
}

#wishlist-button {
  background-color: #b9b9b9;
}
</style>