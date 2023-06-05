<template>
  <div class="card border border-primary ">
    <div class="card-body">
      <header class="d-lg-flex">
        <div class="flex-grow-1">
          <h6 class="mb-0">Order ID: {{ order.id }} <i class="dot"></i></h6>
          <span class="text-success"> {{ order.status }}</span> <br/>
          <span class="text-muted">Date: {{ order.createdDate.split("T")[0]}} </span>
        </div>
        <div style="margin: 10px; position: absolute;
right: 10px;
top: 10px;">
          <a v-show="order.status!='Cancel'" href="#" class="btn btn-sm btn-outline-danger" style="margin: 10px;" @click="cancelOrder(order.id)">Cancel order</a>
<!--          <a href="#" class="btn btn-sm btn-primary shadow-0">Track order</a>-->
        </div>
      </header>
      <hr />
      <div class="row">
        <div class="col-lg-4">
          <p class="mb-0 text-muted">Contact</p>
          <p class="m-0">
            Mike Johnatan <br />
            Phone: 371-295-9131 <br />
            Email: info@mywebsite.com
          </p>
        </div>
        <div class="col-lg-4 border-start">
          <p class="mb-0 text-muted">Shipping address</p>
          <p class="m-0">
            United States <br />
            3601 Old Capitol Trail, Unit A-7, Suite 170777, Wilmington, DE 19808
          </p>
        </div>
        <div class="col-lg-4 border-start">
          <p class="mb-0 text-muted">Payment</p>
          <p class="m-0">
            <span class="text-success"> Visa **** 4216 </span> <br />
            Shipping fee: $56 <br />
            Total paid: $456
          </p>
        </div>
      </div>
      <hr />
      <ul class="row list-unstyled">
        <div v-for="orderItem of order.orderItems" :key="orderItem.id">
          <li class="col-xl-4 col-lg-6">
            <OrderItemBox :orderItem="orderItem" />
          </li>
        </div>

      </ul>
    </div>
  </div>

</template>

<script>
import OrderItemBox from "@/components/OrderItemBox.vue";
import axios from "axios";
export default {
  name: "OrderDetailsBox",
  components: {OrderItemBox},
  props: ["order"],
  data(){
    return {
      token: null,
      editorder: {
        status: '',
        id: '',
      },
    };
  },
  methods:{
    cancelOrder(itemId) {
      console.log(itemId);
      if(confirm("Do you really want to cancel this order?")){
        this.editorder.status = "Cancel";
        this.editorder.id=itemId;
        //console.log(this.baseURL);
        axios
            .post(`http://localhost:8090/order/update/${itemId}?token=${this.token}`,this.editorder)
            .then((res) => {
              if (res.status == 200) {
                this.$router.go(0);
              }
              this.$emit('fetchData');
            })
            .catch((err) => console.log("err", err));
      }
    },
  },
  mounted() {
    this.token = localStorage.getItem("token");
  }
}
</script>

<style scoped>

</style>