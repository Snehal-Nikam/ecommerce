<template>
  <div class="card border border-primary mb-4 shadow-0">
    <div class="card-body pb-0">
      <header class="d-lg-flex">
        <div class="flex-grow-1">
          <h6 class="mb-0">Order ID: 8924 <i class="dot"></i>
            <span class="text-success"> Shipped</span>
          </h6>
          <span class="text-muted">Date: 16 December 2022</span>
        </div>
        <div>
          <a href="#" class="btn btn-sm btn-outline-danger">Cancel order</a>
          <a href="#" class="btn btn-sm btn-primary shadow-0">Track order</a>
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
export default {
  name: "OrderDetailsBox",
  components: {OrderItemBox},
  props: ["order"]
}
</script>

<style scoped>

</style>