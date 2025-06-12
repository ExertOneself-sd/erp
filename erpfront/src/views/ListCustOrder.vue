<template>
  <h2>客户订单</h2>
  
  <el-form :model="condForm">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-form-item label="订单号">
          <el-input v-model="condForm.id" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="订单状态">
          <el-select
          v-model="condForm.state"
          placeholder="请选择订单状态..."
          clearable
          >
          <el-option label="未出库" value="未出库" />
          <el-option label="已出库" value="已出库" />
          <el-option label="配送中" value="配送中" />
          <el-option label="已支付" value="已支付" />
          <el-option label="待支付" value="待支付" />
        </el-select>
      </el-form-item>
    </el-col>
    <el-col :span="6">
      <el-form-item label="客户姓名">
        <el-input v-model="condForm.custName" />
      </el-form-item>
    </el-col>
    <el-col :span="6">
      <el-form-item label="商品名称">
        <el-input v-model="condForm.itemName" />
      </el-form-item>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="24" style="text-align: right; margin-top: 10px">
      <el-form-item>
        <el-button type="primary" @click="subQueryCond">查询</el-button>
      </el-form-item>
    </el-col>
  </el-row>
</el-form>
<hr/>

<el-table :data="orderList" style="width: 100%">
  <el-table-column prop="id" label="订单编号"  />
  <el-table-column prop="custName" label="客户姓名"  />
  <el-table-column prop="itemName" label="商品名称" />
  <el-table-column prop="orderDate" label="订单日期" width="180"/>
  <el-table-column prop="state" label="订单状态" />
  <el-table-column prop="num" label="数量" />
  <el-table-column prop="pay" label="支付方式" />
  <el-table-column prop="payMoney" label="支付金额" />
  
  <!-- <el-table-column fixed="right" label="操作" width="120">
    <template #default="scope">
      <el-button link type="primary" size="small" @click="deleteSellJh(scope.row.id)">删除</el-button>
      <el-button link type="primary" size="small" @click="openSellJhDialog(scope.row)">修改</el-button>
    </template>
  </el-table-column> -->
</el-table>
<hr/>

<el-pagination
:page-size="6"
:pager-count="10"
layout="prev, pager, next"
:total="total"
class="mt-4" @current-change="orderPageChange"
/>
</template>

<script setup>
import { onMounted,reactive,ref } from 'vue'
// import axios from 'axios'
import { getCurrentInstance } from "vue";
const axios=getCurrentInstance().proxy.axios


const orderList = ref([])
const total = ref(0)
function loadOrderList(pageNum){
  condForm.pageNum = pageNum
  axios.post("http://localhost:8081/listOrder",condForm)
  .then((response)=>{
    orderList.value = response.data.orderList
    total.value = response.data.total
  })
  .catch((error)=>{
    console.log(error)
  })
}

onMounted(function(){
  loadOrderList(1)
})

const condForm = reactive({
  id:'',
  state:'',
  custName:'',
  itemName:''
  
})

function subQueryCond(){
  condForm.pageNum = 1
  axios.post("http://localhost:8081/listOrder",condForm)
  .then((response)=>{
    orderList.value = response.data.orderList
    total.value = response.data.total
  })
  .catch((error)=>{
    console.log(error)
  })
}

function orderPageChange(pageNum){
  loadOrderList(pageNum)
}

</script>

<style>

</style>