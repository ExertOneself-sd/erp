<template>
  <h2>客户投诉</h2>
  <el-form :model="condForm">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-form-item label="投诉单号">
          <el-input v-model="condForm.id" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="问题描述">
          <el-input v-model="condForm.question" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="紧急程度">
          <el-select
            v-model="condForm.grade"
            placeholder="请选择紧急程度..."
            clearable
          >
            <el-option label="普通" value="普通" />
            <el-option label="加急" value="加急" />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="处理状态">
          <el-select
            v-model="condForm.state"
            placeholder="请选择..."
            clearable
          >
            <el-option label="未处理" value="未处理" />
            <el-option label="已处理" value="已处理" />
            <el-option label="未回访" value="未回访" />
            <el-option label="已回访" value="已回访" />
          </el-select>
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

  <el-table :data="afterSaleList" style="width: 100%">
    <el-table-column prop="id" label="投诉编号"  />
    <el-table-column prop="custName" label="客户姓名" />
    <el-table-column prop="question" label="问题类型" />
    <el-table-column prop="state" label="处理状态" />
    <el-table-column prop="grade" label="紧急程度" />
    <el-table-column prop="level" label="投诉满意度" />

    <el-table-column fixed="right" label="操作">
      <template #default="scope">
        <el-button link type="primary" size="small" @click="deleteCust(scope.row.id)">处理</el-button>
        <el-button link type="primary" size="small" @click="openCustDialog(scope.$index,scope.row)">查看处理详情</el-button>
      </template>
    </el-table-column>
  </el-table>
  <hr/>
  <el-pagination
    :page-size="6"
    :pager-count="10"
    layout="prev, pager, next"
    :total="total"
    class="mt-4" @current-change="afterSalePageChange"
  />
</template>

<script setup>
import { onMounted,reactive,ref } from 'vue'
import axios from 'axios'


const afterSaleList = ref([])
const total = ref(0)
const condForm=reactive({
    id:'',
    question:'',
    grade:'',
    level:''
})

function queryAfterSaleList(pageNum){
    condForm.pageNum=pageNum
    axios.post("http://localhost:8081/listAfterSale",condForm)
    .then((response)=>{
        afterSaleList.value = response.data.afterSalesList
        total.value=response.data.total
    })
    .catch((error)=>{
        console.log(error)
    })
}

onMounted(function () {
    queryAfterSaleList(1)
})

function afterSalePageChange(pageNum){
    queryAfterSaleList(pageNum)
}

function subQueryCond(){
    queryAfterSaleList(1)
}


</script>

<style>

</style>