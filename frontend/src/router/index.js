import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
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

const routes = [
  {
    path: '/',
    name: 'home',
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
  // category detail page
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
    path: '/admin/product/:id',
    name: 'EditProduct',
    component: EditProduct
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
