<template>
  <h2>入库单列表</h2>
  <el-table :data="inStoreList"  style="width: 100%">
    <el-table-column prop="storeName" label="仓库名称"  />
    <el-table-column prop="itemName" label="商品名称" />
    <el-table-column prop="inNum" label="实际采购数量" />
    <el-table-column prop="createBy" label="采购人" />
    <el-table-column prop="createTime" label="采购时间" />
    <el-table-column prop="isIn" label="状态" width="100">
        <template #default="scope" >
            <span v-if="scope.row.isIn==1" style="color:red">未确认</span>
            <span v-else style="color:green">已确认</span>
        </template>
    </el-table-column>
    <el-table-column fixed="right" label="操作" width="200">
        <template #default="scope" >
            <el-button link type="primary"  v-if="scope.row.isIn==1" size="small" @click="confirmIsIn(scope.row.insId)">确认入库</el-button>

        </template>
    </el-table-column>
  </el-table>
  <hr/>
  <el-pagination
      small
      background
      :page-size="6"
      :pager-count="10"
      layout="prev, pager, next"
      :total="total"
      class="mt-4" @current-change="handlerInStorePageChange"/>

</template>

<script setup>
<<<<<<< HEAD
// import axios from 'axios';
import { getCurrentInstance } from "vue";
const axios=getCurrentInstance().proxy.axios
=======
import axios from 'axios';
>>>>>>> 9b36866f743edc05e0f02f00103b929e0d29fc81
import { ElMessage } from 'element-plus';
import { onMounted, ref } from 'vue';

const inStoreList=ref([])
const total=ref(0)

function queryInStoreList(pageNum){
    axios.get("http://localhost:8081/queryInList?pageNum="+pageNum)
    .then((response)=>{
        inStoreList.value=response.data.inStores
        total.value=response.data.total;
    })
    .catch((error)=>{
        console.log(error)
    })
}

onMounted(function(){
    queryInStoreList(1)
})

function handlerInStorePageChange(pageNum){
    queryInStoreList(pageNum)
}

function confirmIsIn(id){
    axios.post("http://localhost:8081/updateInStore/"+id)
    .then((response)=>{
        if(response.data.code==200){
            queryInStoreList(1)
        }
        ElMessage(response.data.msg)
    })
    .catch((error)=>{
        console.log(error)
    })
}

</script>

<style>

</style>