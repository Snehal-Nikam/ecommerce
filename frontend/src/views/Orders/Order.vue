<template>
  <div id="vieworders">
    <Table v-if="orders" :config="config"/>
  </div>
  <div>
    <h1>User Orders</h1>
  </div>
  <div class="awesome-table">
    <table align="center">
      <thead>
      <tr>
        <th v-for="(obj,ind) in config" :key="ind">{{ obj.title }}</th>
        <th> Order Status </th>
        <th>  </th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(row,index) in orders" :key="index">
        <td v-for="(obj,ind) in config" :key="ind">
          <span v-if="obj.type==='text'">{{row[obj.key]}}</span>
          <span v-if="obj.type==='date'">{{new Date(row[obj.key]).toLocaleDateString()}}</span>

        </td>
        <td>

          <span v-show="editRow!=index">{{row.status}} <a href="#" @click.prevent="startEditing(index)">
          <i class="fa fa-pen" style="color: grey"></i></a></span>
          <span v-show="editRow==index">
            <select v-model="row.status" name="status" id="status">
               <option value="Order Placed">Order Placed</option>
               <option value="Shipped">Shipped</option>
               <option value="Out For Delivery">Out For Delivery</option>
               <option value="Delivered">Delivered</option>
             </select>
          </span>
          <span v-show="editRow==index">
            <button type="button" class="btn btn-primary btn-sm" style="margin: 10px;" @click="update(row)">Save</button>
            <button type="button" class="btn btn-secondary btn-sm" @click="cancelEditing">Cancel</button>
          </span>
        </td>
        <td>
            <span class="m-3 form-row justify-content-center"><a href="#" @click="$router.push({name:'OrderDetails',params: {id: row.id}})" class="btn btn-light btn-sm border" >
                View Details </a></span>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';
import alert from "sweetalert";
export default{
  name: " OrderDetails",
  token: null,

  props: ["baseURL"],
  data: () => ({
    editRow: -1,
    orders: undefined,
    config:[
      {
        key:'id' ,
        title: 'Order ID',
        type: 'text'
      },
      {
        key: 'userId',
        title: 'User ID',
        type: 'text'
      },
      {
        key: 'userName',
        title: 'User Name',
        type: 'text'
      },
      {
        key: 'userEmail',
        title: 'User Email',
        type: 'text'
      },
      {
        key: 'totalPrice',
        title: 'Price',
        type: 'text'
      }
    ]
  }),
  methods:{
    fetchOrders(){
      axios
          .get(`${this.baseURL}order/?token=${this.token}`)
          .then((res) => {
            // if (res.status == 200) {
           this.orders = res.data[0];

            // }
          })
          .catch((err) => console.log("err", err));
    },
    async update(row) {
      this.editRow=-1;
      let editOrder={
        id: row.id,
        status: row.status
      }
      await axios
          .post(`${this.baseURL}order/update/${row.id}?token=${this.token}`, editOrder)
          .then(() => {
                this.fetchOrders();
                alert({
                  text: "Profile data has been updated successfully",
                  icon: "success"
                })
              }).catch(err => {
            console.log("error : " + err);
          })
    },
    startEditing(index){
      this.editRow = index;
    },
    cancelEditing(){
      this.editRow = -1;
      this.fetchOrders();
    }
  },
  mounted(){
    this.token = localStorage.getItem("token");
    this.fetchOrders();

  }
}

</script>
<style lang="scss">
.awesome-table {
  border: 1px solid #999;
  border-radius: 7px;
  color: #333;
  width: 100%;

  figure{
    img{
      border: 1px solid #bbb;

      overflow: hidden;
    }
  }


  table{
    border-collapse: collapse;

    th{
      background: #ccc;
      padding: 10px 5px;
      position: sticky;
      top: 0;
      text-align: left;
      border-bottom: 1px solid #999;
    }
    td{
      padding: 5px 5px;
      text-align: left;
    }
  }
}

</style>