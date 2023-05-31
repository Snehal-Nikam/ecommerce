<template>
  <div class="container">
    <div class="row pt-5">
      <div class="col-md-1"></div>
      <!--            display image-->
      <div class="col-md-4 col-12">
        <img :src="product.imgURL" class="img-fluid" />
      </div>
      <!--            display product details-->
      <div class="col-md-6 col-12 pt-3 pt-md-0">
        <h4>{{ product.productName }}</h4>
        <h6 class="catgory font-italic">{{ category.categoryName }}</h6>
        <h6 class="font-weight-bold">$ {{ product.price }}</h6>
        <p>
          {{ product.description }}
        </p>
        <div class="d-flex flex-row justify-content-between">
          <div class="input-group col-md-3 col-4 p-0">
            <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon1">Quantity</span>
            </div>
            <input type="number" class="form-control" v-bind:value="quantity" />
          </div>

          <div class="input-group col-md-3 col-4 p-0">
            <button
                class="btn"
                type="button"
                id="add-to-cart-button"
                @click="addToCart(this.id)"
            >
              Add to Cart
              <ion-icon name="cart-outline" v-pre></ion-icon>
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
        <button
            id="wishlist-button"
            class="btn mr-3 p-1 py-0"
            :class="{ product_added_wishlist: isAddedToWishlist }"
            @click="addToWishlist()"
        >
          {{ wishListString }}
        </button>
        <button
            id="show-cart-button"
            type="button"
            class="btn mr-3 p-1 py-0"
            @click="listCartItems()"
        >
          Show Cart

          <ion-icon name="cart-outline" v-pre></ion-icon>
        </button>
      </div>
      <div class="col-md-1"></div>
    </div>
  </div>
</template>

<script>
const axios = require("axios");
const alert = require("sweetalert");
export default {
  data() {
    return {
      product: {},
      category: {},
      id: null,
      token: null,
      isAddedToWishlist: false,
      quantity: 1,
      wishListString: "Add to wishlist",
    };
  },
  props: ["baseURL", "products", "categories", "cartCount"],
  methods: {
    addToWishlist() {
      if (!this.token) {
        // user is not logged in
        // show some error
        alert({
          text: "please login to add item in wishlist",
          icon: "error",
        });
        console.log("**Path : "+this.$route.fullPath);
        this.$router.push({ name: 'Signin', query: { redirect: this.$route.fullPath } });
        return;
      }
      // add item to wishlist
      axios
          .post(`${this.baseURL}wishlist/add?token=${this.token}`, {
            id: this.product.id,
          })
          .then((res) => {
            if (res.status === 201) {
              this.isAddedToWishlist = true;
              this.wishListString = "Added to Wishlist";
              alert({
                text: "Added to Wishlist",
                icon: "success",
              });
            }
          })
          .catch((err) => {
            console.log("err", err);
          });
    },

    // add to cart

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
                closeOnClickOutside: false,
              });
              this.$emit("fetchData");
            }
          })
          .catch((err) => console.log("err", err));
    },
  listCartItems(){
    axios.get(`${this.baseURL}cart/?token=${this.token}`).then(
        (response) => {
          if (response.status === 200) {
            this.$router.push("/cart");
          }
        },
        (error) => {
          console.log(error);
        }
    );
  },
  },
  mounted() {
    this.id = this.$route.params.id;
    this.product = this.products.find((product) => product.id == this.id);
    this.category = this.categories.find(
        (category) => category.id == this.product.categoryId
    );
    this.token = localStorage.getItem("token");
  },
};
</script>
<style scoped>
.category {
  font-weight: 400;
}

/* Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* Firefox */
input[type="number"] {
  -moz-appearance: textfield;
}

#add-to-cart-button {
  background-color: #febd69;
}

#wishlist-button {
  background-color: #b9b9b9;
  border-radius: 0;
}

#show-cart-button {
  background-color: #131921;
  color: white;
  border-radius: 0;
}

#add-to-cart-button {
  background-color: #febd69;
}
</style>
