<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h3 class="pt-3">
          Shopping cart
        </h3>
      </div>
    </div>
    <!-- loop over the cart items and display -->
    <div
        v-for="cartItem in cartItems"
        :key="cartItem.product.id"
        class="row mt-2 pt-3 justify-content-around"
    >
      <div class="col-2"></div>
      <div class="col-md-3 embed-responsive embed-responsive-16by9">
        <router-link
            :to="{ name: 'ShowDetails', params: { id: cartItem.product.id } }"
        >
        <img
            v-bind:src="cartItem.product.imgURL"
            alt=""
            class="w-100 card-image-top embed-responsive-item"
        />
        </router-link>
      </div>
      <!-- display product name, quantity -->
      <div class="col-md-5 px-3">
        <div class="card-block px-3">
          <h6 class="card-title">
<!--            {{cartItem}}-->
            <router-link
                :to="{ name: 'ShowDetails', params: { id: cartItem.product.id } }"
            >
              {{ cartItem.product.productName }}
            </router-link>
          </h6>
          <p class="mb-0 font-weight-bold" id="item-price">
            $ {{ cartItem.product.price }} per unit
          </p>
          <p class="mb-0" id="item-quantity">
            Quantity :
            <input
                size="1"
                class="p-0 h-25 border-bottom border-top-0 border-left-0 border-right-0"
                v-model="cartItem.quantity"
            />
          </p>
        </div>
        <p class="mb-0" id="item-total-price">
          Total Price :
          <span class="font-weight-bold">
            $ {{ cartItem.product.price * cartItem.quantity }}
          </span>
        </p>
        <br />
        <a href="#" class="text-right" @click="deleteItem(cartItem.id)"
        >Remove from cart
        </a>
      </div>
      <div class="col-2"></div>
      <div class="col-12"><hr /></div>
    </div>
    <!-- display the price -->
    <div class="total-cost pt-2 text-right">
      <h5>Total : ${{ totalCost.toFixed(2) }}</h5>
      <button
          :disabled="isDisabled()"
          type="button"
          class="btn btn-primary confirm"
          @click="checkout"
      >
        Confirm Order
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name : "Cart",
  data() {
    return {
      cartItems: [],
      token: null,
      totalCost: 0,
    };
  },
  props: ["baseURL","cartCount"],
  methods: {

    isDisabled() {
      if (this.cartItems.length === 0) {
        return true;
      }
      return false;
    },

    // fetch All items in cart
    listCartItems() {
      axios
          .get(`${this.baseURL}cart/?token=${this.token}`)
          .then((res) => {
                // if (res.status == 200) {
                  const result = res.data;
                  this.cartItems = result.cartItems;
                  this.totalCost = result.totalCost;
                // }
          })
          .catch((err) => console.log("err", err));
    },
    // go to checkout page
    // checkout() {
    //   this.$router.push({ name: 'Checkout' });
    // },
    deleteItem(itemId) {
      axios
          .delete(`${this.baseURL}cart/delete/${itemId}?token=${this.token}`)
          .then((res) => {
            if (res.status == 200) {
              this.$router.go(0);
            }
            this.$emit('fetchData');
          })
          .catch((err) => console.log("err", err));
    },
    // go to checkout page
    checkout() {
      this.$router.push({ name: 'Checkout' });
    },
    // showDetails(productId) {
    //   this.$router.push({
    //     name: 'ShowDetails',
    //     params: { id: productId },
    //   });
    // },
  },
  mounted() {
    this.token = localStorage.getItem("token");
    this.listCartItems();
  },
};
</script>
<style scoped>
h4,
h5 {
  font-family: 'Roboto', sans-serif;
  color: #484848;
  font-weight: 700;
}

.embed-responsive .card-img-top {
  object-fit: cover;
}

#item-price {
  color: #232f3e;
}

#item-quantity {
  float: left;
}

#item-total-price {
  float: right;
}

.confirm {
  font-weight: bold;
  font-size: larger;
}
</style>
