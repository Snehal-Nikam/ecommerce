<template>
  <div class="text-center">
    <div class="spinner-border" role="status">
      <span class="sr-only">Loading...</span>
      <h3>Payment Successful! Order Placed</h3>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import router from "@/router";
export default {
  name:'PaymentSuccess',
  props:["baseURL"],
  data() {
    return {
      token: null,
      sessionId:null
    }
  },
  methods:{
    async saveOrder() {
      console.log("session "+ this.sessionId);

      await
      axios.post(this.baseURL+"order/add?token="+this.token+"&sessionId="+this.sessionId)
          .then(()=>{
            router.push({name: "Home"});
          }).catch((error)=>{
        console.log(error);
      })
    }
  },
  mounted(){
    this.token = localStorage.getItem("token");
    this.sessionId = localStorage.getItem("sessionId");
    this.saveOrder();
  }
}
</script>