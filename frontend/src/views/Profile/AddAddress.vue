<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h3 class="pt-3">Add Address</h3>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6">
        <form>
          <div class="form-group">
            <label>Address Line 1</label>
            <input type="text" class="form-control" v-model="addressLine1" required/>
          </div>
          <div class="form-group">
            <label>Address Line 2</label>
            <input type="text" class="form-control" v-model="addressLine2" />
          </div>
          <div class="form-group">
            <label>City</label>
            <input type="text" class="form-control" v-model="city" />
          </div>
          <div class="form-group">
            <label>State</label>
            <input type="text" class="form-control" v-model="state" />
          </div>
          <div class="form-group">
            <label>ZipCode</label>
            <input type="text" class="form-control" v-model="zipCode" required/>
          </div>
          <div class="form-group">
            <label>Phone</label>
            <input type="text" class="form-control" v-model="phone" />
          </div>
          <button type="button" class="btn btn-primary" @click="addAddress">
            Submit
          </button>
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
  name : "AddAddress",
  props : ["baseURL"],
  data() {
    return {
      addressLine1: "",
      addressLine2: "",
      city: "",
      state: "",
      zipCode: "",
      phone:"",
      token: null
    };
  },
  methods: {
    addAddress() {
      const newAddress = {
        addressLine1: this.addressLine1,
        addressLine2: this.addressLine2,
        city: this.city,
        state: this.state,
        zipCode: this.zipCode,
        phone: this.phone,
        primary: false
      };

      axios({
        method: "post",
        url: `${this.baseURL}user/profile/createAddress?token=${this.token}`,
        data: JSON.stringify(newAddress),
        headers: {
          "Content-Type": "application/json",
        },
      })
          .then(() => {
            alert({
              text: "Address added successfully",
              icon: "success",
            });
            this.$router.push({ name: "MyProfile" });
          })
          .catch((err) => {
            console.log(err);
          });
    },
  },
  mounted() {
    this.token = localStorage.getItem("token");
  }
};
</script>
<style scoped></style>