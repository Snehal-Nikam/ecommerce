<template>
    <div class="border p-3 rounded-3 bg-light" >
      <b class="mx-2 text-muted"><i class="fa fa-map-marker-alt"></i></b>
      {{address.addressLine1}} {{address.addressLine2}} <br />
      {{address.city}} <br />
      {{address.state}} {{address.zipCode}}<br />

<!--      <a href="#" @click="$router.push('AddAddress')"> <i class="fa fa-pen" style="color: grey" /> </a>-->
    </div>


</template>

<script>

export default {
  name: "AddressBox",
  props : ["address"]
}
</script>

<style scoped>
@import "~@fortawesome/fontawesome-free/css/all.css";
</style>