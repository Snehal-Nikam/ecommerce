<template>
  <section class="py-5 bg-light">
    <div class="container">
      <!--      <div class="row">-->
      <main>
        <div class="card p-4 mb-0 shadow-0 border">
          <div class="content-body">
            <div >
              <div>
                <div class="m-3 form-row justify-content-center">
                  <img src="https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/avatars/avatar.webp" class="rounded-circle" style="height: 60px; width: 60px;" />
                </div>
                <div class="m-3 form-row justify-content-center">
                  <h5 v-show="editOffset != 'name'">
                    <b>{{profile.firstName}} {{profile.lastName}}</b>
                    <a href="#" @click.prevent="startEditing('name')" class="px-2">
                      <i class="fa fa-pen" style="color: grey;"></i>
                    </a>
                  </h5>
                  <div v-show="editOffset=='name'" style="display : inline-block">
                    <b>First Name : </b>
                    <input type="text" :id="'item-user-name'" class="form-control" v-model="profile.firstName">
                    <b>Last Name : </b>
                    <input type="text" :id="'item-user-name'" class="form-control" v-model="profile.lastName">
                  </div>
                </div>
              </div>
              <div class="pt-2">
                <div class="m-3 form-row justify-content-center">
                  <b> Email : </b>
                  <span v-show="editOffset != 'email'">{{profile.email}}
                    <a href="#" @click.prevent="startEditing('email')" class="px-2">
                      <i class="fa fa-pen" style="color: grey"></i>
                    </a>
                  </span>
                  <span v-show="editOffset=='email'">
                    <input type="text" :id="'item-user-email'" class="form-control" v-model="profile.email">
                  </span>
                  <br />
                </div>
                <div class="m-3 form-row justify-content-center">
                  <b> Phone : </b>
                  <span v-show="editOffset != 'phone'">{{profile.phone}}
                    <a href="#" @click.prevent="startEditing('phone')" class="px-2">
                      <i class="fa fa-pen" style="color: grey"></i>
                    </a>
                  </span>
                  <span v-show="editOffset=='phone'">
                    <input type="text" :id="'item-user-phone'" class="form-control" v-model="profile.phone">
                  </span>
                </div>
              </div>
              <div class="pt-2" v-show="showButtons" style="display : inline-block; margin: 10px;">
                <button type="button" class="btn btn-primary btn-sm" style="margin: 10px;" @click="update">Save</button>
                <button type="button" class="btn btn-secondary btn-sm" @click="cancelEditing">Cancel</button>
              </div>
            </div>
            <hr />
            <!-- Address view -->
            <div>
              <h4 class="m-3 form-row justify-content-center">
                <b> Your Address </b>
              </h4>
              <div class="row g-2 mb-3">
                <div v-for="address of profile.addresses" :key="address.id" class="col-md-6" style="margin-top: 20px;">
                  <AddressBox :address="address" />
                </div>
              </div>
              <span class="m-3 form-row justify-content-center"><a href="#" @click="$router.push('AddAddress')" class="btn btn-light border" >
                <i class="me-2 fa fa-plus"></i> Add new address </a></span>
            </div>
            <hr />
            <!-- Order view -->
            <div>
              <h4 class="m-3 form-row justify-content-center">
                <b> Your Orders </b>
              </h4>
              <div class="row g-2 mb-3">
                <div v-for="order of profile.orders" :key="order.id" class="col-md-6" style="margin-top: 20px;">
                  <OrderDetailsBox :order="order" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
    <!--    </div>-->
  </section>
</template>


<script>
import axios from "axios";
import AddressBox from "@/components/AddressBox.vue";
import OrderDetailsBox from "@/components/OrderDetailsBox.vue";
import alert from "sweetalert";

export default {
  name: "MyProfile",
  components: {AddressBox, OrderDetailsBox},
  props: ["baseURL"],
  data() {
    return {
      profile: {},
      editProfile: {},
      token: null,
      showButtons: false,
      editOffset: ''
    };
  },
  methods:{
    async fetchProfile() {
      await axios
          .get(`${this.baseURL}user/profile?token=${this.token}`)
          .then(res => {
            console.log(res.data);
            this.profile = res.data;
            this.editProfile = this.profile;
            console.log(this.profile.firstName);
          }).catch(err =>{
            console.log("error : "+err);
          })
    },
    startEditing(index) {
      this.editOffset = index;
      this.showButtons=true;
      // set focus ke element input
      this.$nextTick(function(){
        console.log('item-user-'+this.editOffset)
        document.getElementById('item-user-'+this.editOffset).focus()
      }.bind(this))
    },
    async update() {
      this.editOffset = '';
      this.showButtons = false;
      console.log(this.profile);
      await axios
          .post(`${this.baseURL}user/profile/updateUser?token=${this.token}`),
          this.profile
          .then(() => {
            this.$emit("fetchData");
            alert({
              text: "Profile data has been updated successfully",
              icon: "success"
            })
          }).catch(err =>{
            console.log("error : "+err);
          })


    },
    cancelEditing() {
      this.editOffset = '';
      this.showButtons = false;
      this.fetchProfile();
    }

  },
  mounted() {
    this.token = localStorage.getItem("token");
    this.fetchProfile();
  }

}
</script>

<style scoped>
@import "~@fortawesome/fontawesome-free/css/all.css";

</style>