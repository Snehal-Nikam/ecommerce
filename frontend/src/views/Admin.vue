<template>
  <div class="adminPanel container">
  <h2> Seller Page </h2>

  <router-link :to="{name: 'AdminCategory'}" >
    <button class="btn btn-primary btn-lg"> Admin Category</button>
  </router-link>
  <br />
  <router-link :to="{name: 'AdminProduct'}" >
    <button class="btn btn-primary btn-lg"> Admin Product</button>
  </router-link>
  <br />
  <router-link :to="{name: 'AdminOrder'}" >
    <button class="btn btn-primary btn-lg"> Orders</button>
  </router-link>
  </div>
</template>

<script>
import alert from 'sweetalert';
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Admin",
  mounted() {
    if (!localStorage.getItem('token')) {

      this.$router.push({name : 'Signin'});

    }
    else if(localStorage.getItem("Admin")==null || localStorage.getItem("Admin")=="false"){
      alert({
        text: "This is a seller portal. You don't have necessary privileges to access this page!",
        icon: "error",
        closeOnClickOutside: true,
      });
      this.$router.push({name : 'Home'});
    }

  }
}
</script>

<style>
.adminPanel{
  display : flex;
  flex-direction: column;
  align-items: center;
}
.adminPanel button{
  font-size: 20px;
  width: fit-content;
  margin: 10px 0;
}
</style>