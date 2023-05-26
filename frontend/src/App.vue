<template>
  <navigationBar />
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </nav>
  <router-view :baseURL ="baseURL" :categories = "categories" :products="products"></router-view>
</template>

<script>
import axios from "axios";
import navigationBar from "@/components/NavigationBar.vue";
export default {
  components: { navigationBar },
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
  mounted() {
    this.fetchData();
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
