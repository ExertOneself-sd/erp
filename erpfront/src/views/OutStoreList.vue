<template>
  <h2>出库单列表</h2>
  <el-table :data="outStoreList"  style="width: 100%">
    <el-table-column prop="storeName" label="仓库名称"  />
    <el-table-column prop="itemName" label="商品名称" />
    <el-table-column prop="inNum" label="出库数量" />
    <el-table-column prop="createBy" label="出库人" />
    <el-table-column prop="createTime" label="出库时间" />
    <el-table-column prop="isOut" label="状态" width="100">
        <template #default="scope" >
            <span v-if="scope.row.isOut==0" style="color:red">未确认</span>
            <span v-else style="color:green">已确认</span>
        </template>
    </el-table-column>
    <el-table-column fixed="right" label="操作" width="200">
        <template #default="scope" >
            <el-button link type="primary"  v-if="scope.row.isOut==0" size="small" @click="confirmIsOut(scope.row.outsId)">确认出库</el-button>

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
      class="mt-4" @current-change="handlerOutStorePageChange"/>
</template>

<script setup>

import axios from 'axios'
import { ElMessage } from 'element-plus'
import { onMounted, ref } from 'vue'

const outStoreList = ref([])
const total = ref(0)

function queryOutStoreList(pageNum){
  axios.get("http://localhost:8081/queryOutList?pageNum="+pageNum)
  .then((response)=>{
      outStoreList.value = response.data.outStoreList
      total.value = response.data.total

  })
  .catch((error)=>{
    console.log(error)
  })
}

onMounted(function(){
  queryOutStoreList(1)
})

function handlerOutStorePageChange(pageNum){
  queryOutStoreList(pageNum)
}

function confirmIsOut(id){
  axios.post("http://localhost:8081/updateOutStore/"+id)
  .then((response)=>{
    if(response.data.code==200){
      queryOutStoreList(1)
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