<template>
  <navigationBar
      :cartCount="cartCount"
      @resetCartCount="resetCartCount"
      v-if="!['Signup', 'Signin'].includes($route.name)"
  />
<!--  <nav>-->
<!--    <router-link to="/">Home</router-link> |-->
<!--    <router-link to="/about">About</router-link>-->
<!--  </nav>-->
  <div style="min-height: 60vh">
  <router-view v-if="products && categories"
               :baseURL ="baseURL"
               :categories = "categories"
               :products="products"
               @fetchData="fetchData"
  >
  </router-view>
  </div>
  <footer-bar v-if="!['Signup', 'Signin'].includes($route.name)" />
</template>

<script>
import { mapState } from 'vuex';
import axios from "axios";
import navigationBar from "@/components/NavigationBar.vue";
import footerBar from "@/components/FooterBar.vue"
export default {
  components: { footerBar, navigationBar },
  computed: {
    ...mapState(['categories', 'products'])
  },
  data(){
    return {
      baseURL :"http://localhost:8090/",
      products :[],
      categories : []

    }
  },
  methods:{
    async fetchData() {
      await axios.get(this.baseURL + "/category/showAll")
          .then(res => {
            this.categories = res.data;
            console.log("app categories :: "+ JSON.stringify(this.categories));
          }).catch(err =>{
            console.log("error : "+err);
          })

      await axios.get(this.baseURL + "/product/showAll")
          .then(res => {
            this.products  = res.data;
          }).catch(err =>{
            console.log("error from app : "+ err);
          })
    }
  },
  async mounted() {
    await this.fetchData();
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
navigationBar