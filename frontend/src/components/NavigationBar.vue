<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <!-- Navbar content -->
      <!--    Logo-->
      <router-link class="navbar-brand" :to="{ name: 'Home' }">
        <img id="logo" src="../assets/sitelogo.png" />
      </router-link>
      <!--    Burger Button-->
      <button
          class="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <!--      Search Bar-->
        <form class="form-inline ml-auto mr-auto">
          <div class="input-group">
            <input
                size="100"
                type="text"
                class="form-control"
                placeholder="Search Items"
                aria-label="Username"
                aria-describedby="basic-addon1"
            />
            <div class="input-group-prepend">
            <span class="input-group-text" id="search-button-navbar">
              <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  class="bi bi-search"
                  viewBox="0 0 16 16"
              >
                <path
                    d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"
                />
              </svg>
            </span>
            </div>
          </div>
        </form>
        <!-- dropdown for browse -->
        <ul class="navbar-nav ml-auto">
          <li class="nav-item dropdown">
            <a
                class="nav-link text-dark dropdown-toggle"
                href="#"
                id="navbarDropdown"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
            >
              Browse
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <router-link class="dropdown-item" :to="{ name: 'Home' }"
              >Home
              </router-link>
              <router-link class="dropdown-item" :to="{ name: 'Product' }"
              >Product
              </router-link>
              <router-link class="dropdown-item" :to="{ name: 'Category' }"
              >Category
              </router-link>
            </div>
          </li>
          <li class="nav-item  dropdown">
            <a
                class="nav-link text-dark dropdown-toggle"
                href="#"
                id="navbarDropdown"
                role="button"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
            >
              Accounts
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <router-link
                  v-if="token"
                  class="dropdown-item"
                  :to="{ name: 'MyProfile' }"> My Profile</router-link>

              <router-link
                  v-if="token"
                  class="dropdown-item"
                  :to="{ name: 'WishList' }"
              >Wishlist
              </router-link>
              <router-link
                  v-if="!token"
                  class="dropdown-item"
                  :to="{ name: 'Signup' }"
              >Sign up
              </router-link>
              <router-link
                  v-if="!token"
                  class="dropdown-item"
                  :to="{ name: 'Signin' }"
              >Sign in
              </router-link>
              <router-link
                  v-show="admin"
                  class="dropdown-item"
                  :to="{ name: 'Admin' }">Seller</router-link>

              <a class="dropdown-item" v-if="token" href="#" @click="signout"
              >Sign out
              </a>
            </div>
          </li>
          <li class="nav-item">
            <div id="cart">
              <span id="nav-cart-count">{{ cartCount }}</span>
              <router-link class="text-light" :to="{ name: 'Cart' }">
                <i class="fa fa-shopping-cart" style="font-size:36px; color: rgba(0,0,0,.5)"></i>
              </router-link>
            </div>
          </li>
        </ul>
        <!-- dropdown for account -->
      </div>
    </nav>
  </template>


<script>
//const axios = require("axios");
const alert = require("sweetalert");
export default {
  name: "NavigationBar",
  props: ["cartCount"],
  data() {
    return {
      token: null,
      admin: false
    };
  },
  methods: {
    signout() {
      localStorage.removeItem("token");
      localStorage.removeItem("Admin")
      this.token = null;

      alert({
        text: "Logged you out. Visit again",
        icon: "success",
        closeOnClickOutside: false,
      });
      this.$emit("resetCartCount");
      this.$router.push({ name: "Home" });
    }
  },
  mounted() {
    console.log("cart navbar : "+ this.cartCount);
    this.token = localStorage.getItem("token");
    this.admin = localStorage.getItem("Admin")==="true"?true:false;
  },
};
</script>

<style scoped>
#logo {
  width: 50px;
  margin-left: 20px;
  margin-right: 20px;
}

.nav-link {
  color: rgba(250, 250, 250);
}

#search-button-navbar {
  background-color: #febd69;
  border-color: #febd69;
  border-top-right-radius: 2px;
  border-bottom-right-radius: 2px;
}

#nav-cart-count {
  background-color: red;
  color: white;
  border-radius: 50%;
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 15px;
  height: 15px;
  font-size: 15px;
  margin-left: 10px;
}
#cart {
  position: relative;
}
</style>