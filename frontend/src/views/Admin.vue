<template>

  <h2> Welcome back.. </h2>
  <router-link :to="{name: 'AdminCategory'}" >
    <button class="btn btn-primary btn-lg"> Admin Category</button>
  </router-link>
  <br />
  <router-link :to="{name: 'AdminProduct'}" >
    <button class="btn btn-primary btn-lg"> Admin Product</button>
  </router-link>
  <br />
  <router-link :to="{name: 'AdminProduct'}" >
    <button class="btn btn-primary btn-lg"> Orders</button>
  </router-link>

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

<style scoped>
.btn {
  margin-bottom: 20px;
}
</style>