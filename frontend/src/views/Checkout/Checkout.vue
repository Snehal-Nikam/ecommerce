<template>
  <div class="div_class">
    <h3>You will be redirected to payment page</h3>

    <div class="alert alert-primary" role="alert">
      While making payment use card number 4242 4242 4242 4242 and enter random
      cvv(3 digit)
    </div>

    <button
        class="checkout_button"
        id="proceed-to-checkout"
        @click="goToCheckout()"
    >
      Make payment
    </button>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      stripeAPIToken: 'pk_test_51NDbmUJyO5bWrjXvLHquMSWuluJBdRiCpbsrOx1Og6E11PlQbFJREk29HlOtkVpOvKj7z0OnvYWGIaxwmqKvWWJm00ewFtP8Lt',
      stripe: '',
      token: null,
      checkoutBodyArray: [],
    };
  },
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Checkout',
  props: ['baseURL'],
  methods: {
    /*
      Configures Stripe by setting up the elements and
      creating the card element.
    */

    getAllItems() {
      axios.get(`${this.baseURL}cart/?token=${this.token}`)
          .then((response) => {
            if (response.status == 200) {
              let products = response.data;
              for (let i = 0; i < products.cartItems.length; i++)
                this.checkoutBodyArray.push({
                  price: products.cartItems[i].product.price,
                  quantity: products.cartItems[i].quantity,
                  productName: products.cartItems[i].product.productName,
                  productId: products.cartItems[i].product.id,
                });
            }
          },
          (err) => {
            console.log(err);
          }
      );
    },

    async goToCheckout() {
      console.log("checkoutBodyArray", this.checkoutBodyArray);
      await axios
          .post(
              this.baseURL + 'order/create-checkout-session',
              this.checkoutBodyArray
          )
          .then((response) => {
            localStorage.setItem('sessionId', response.data.sessionId);
            console.log("session", response.data);
            const stripe = window.Stripe(this.stripeAPIToken);
            stripe.redirectToCheckout({ sessionId: response.data.sessionId })
          }),
          (err) => {
            console.log(err);
          }
    },
  },
  mounted() {
    // get the token
    this.token = localStorage.getItem('token');
    // get all the cart items
    this.getAllItems();
  },
};
</script>

<style>
.alert {
  width: 50%;
}

.div_class {
  margin-top: 5%;
  margin-left: 30%;
}

.checkout_button {
  background-color: #5d3dec;
  border: none;
  color: white;
  margin-left: 15%;
  padding: 15px 30px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 15px;
  font-weight: bold;
  border-radius: 15px;
}

.checkout_button:focus {
  outline: none;
  box-shadow: none;
}

.checkout_button:disabled {
  background-color: #9b86f7;
  border: none;
  color: white;
  margin-left: 15%;
  padding: 15px 30px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 15px;
  font-weight: bold;
  border-radius: 15px;
  cursor: not-allowed;
}
</style>