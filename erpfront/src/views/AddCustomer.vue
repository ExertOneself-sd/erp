<template>
 <h2>添加客户</h2>
<el-form :model="custFrom" label-width="120px">
    <el-form-item label="客户姓名">
      <el-input v-model="custFrom.custName" />
    </el-form-item>
    <el-form-item label="地址">
      <el-input v-model="custFrom.address" />
    </el-form-item>
     <el-form-item label="联系电话">
      <el-input v-model="custFrom.phone" />
    </el-form-item>
    <el-form-item label="客户职业">
      <el-select v-model="custFrom.cutType" placeholder="请选择职业....">
        <el-option label="网络工程师" value="网络工程师" />
        <el-option label="架构师" value="架构师" />
        <el-option label="运维" value="运维" />
        <el-option label="销售" value="销售" />
        <el-option label="项目经理" value="项目经理" />
      </el-select>
    </el-form-item>
     <el-form-item label="客户等级">
      <el-input v-model="custFrom.graed" />
    </el-form-item>
        <el-form-item>
      <el-button type="primary" @click="subCustFrom">保存</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
// import axios from 'axios';
import { getCurrentInstance } from "vue";
const axios=getCurrentInstance().proxy.axios
import { ElMessage } from 'element-plus';
import { reactive } from 'vue'
const custFrom=reactive({
  custName:'',
  address:'',
  phone:'',
  cutType:'',
  graed:1,
  hitTotal:0,
})

function subCustFrom(){
  axios.post("http://localhost:8081/saveCust",custFrom)
  .then((respose)=>{
    console.log(respose.data);
    if(respose.data.code){
      ElMessage(respose.data.msg)
    }else{
      ElMessage(respose.data.msg)
    }
  })
  .catch((error)=>{
    console.log(error);
  })
}

</script>

<style scoped>
/* 组件样式 */
</style>
