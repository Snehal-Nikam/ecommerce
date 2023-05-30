import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/Home.vue'
import AddCategory from "@/views/category/AddCategory.vue";
import Category from "@/views/category/Category.vue";
import Admin from "@/views/Admin.vue";
import AddProduct from "@/views/product/AddProduct.vue";
import Product from "@/views/product/Product.vue";
import ListProducts from "@/views/category/ListProducts.vue";
import EditCategory from "@/views/category/EditCategory.vue";
import ShowDetails from "@/views/product/ShowDetails.vue";
import EditProduct from "@/views/product/EditProduct.vue";
import Signup from "@/views/SignupView.vue";
import Signin from "@/views/SigninView.vue";
import Cart from "@/views/Cart.vue";
import WishList from "@/views/product/WishList.vue";

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path:'/admin',
    name:'Admin',
    component: Admin

  },
  {
    path: '/admin/category/add',
    name: 'AddCategory',
    component: AddCategory
  },
  //http://localhost:8080/admin/category
  {
    path: '/admin/category',
    name: 'AdminCategory',
    component: Category
  },
  {
    path: '/admin/product/add',
    name: 'AddProduct',
    component: AddProduct
  },
  {
    path: '/admin/product',
    name: 'AdminProduct',
    component: Product
  },
  {
    path: '/product',
    name: 'Product',
    component: Product
  },
  {
    path: '/category',
    name: 'Category',
    component: Category
  },
  // category detail page : http://localhost:8080/category/show/2
  {
    path: '/category/show/:id',
    name: 'ListProducts',
    component: ListProducts
  },
  {
    path: '/admin/category/:id',
    name: 'EditCategory',
    component: EditCategory
  },
  {
    path: '/product/show/:id',
    name: 'ShowDetails',
    component: ShowDetails
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/signin',
    name: 'Signin',
    component: Signin
  },
  {
    path: '/admin/product/:id',
    name: 'EditProduct',
    component: EditProduct
  },
  {
    path: '/cart',
    name: 'Cart',
    component: Cart
  },
  {
    path: '/wishlist',
    name: 'WishList',
    component: WishList
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
