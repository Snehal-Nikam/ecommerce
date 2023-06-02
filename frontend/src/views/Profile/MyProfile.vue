<template>
  <section class="py-5 bg-light">
    <div class="container">
<!--      <div class="row">-->

        <main>
          <div class="card p-4 mb-0 shadow-0 border">
            <div class="content-body">
              <div class="d-flex align-items-center">
                <div class="me-3">
                  <img src="https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/avatars/avatar.webp" class="rounded-circle" style="height: 60px; width: 60px;" />
                </div>
                <div class="pt-2">
                  <h5> <b>Mr. Jackson Mike</b></h5>
                  <p class="m-0">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b> Email : </b>{{profile.email}} <a href="#" class="px-2"><i class="fa fa-pen"></i></a><br/>
                    &nbsp;&nbsp;<b> &nbsp;Phone : </b>&nbsp;657-234-8080&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="px-2"><i class="fa fa-pen"></i></a> <br/>
                  </p>
                </div>
              </div>

              <hr />
              <!-- Address view -->
              <div class="row g-2 mb-3">
                  <div v-for="address of profile.addresses" :key="address.id" class="col-md-6" style="margin-top: 20px;">
                      <AddressBox :address="address" />
                  </div>
              </div>

              <a href="#" @click="$router.push('AddAddress')" class="btn btn-light border"> <i class="me-2 fa fa-plus"></i> Add new address </a>

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

export default {
  name: "MyProfile",
  components: {AddressBox},
  props: ["baseURL"],
  data() {
    return {
      profile: {},
      token: null
    };
  },
  methods:{
    async fetchProfile() {
      await axios
          .get(`${this.baseURL}user/profile?token=${this.token}`)
          .then(res => {
            console.log(res.data);
            this.profile = res.data;
            console.log(this.profile.firstName);
          }).catch(err =>{
            console.log("error : "+err);
          })
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