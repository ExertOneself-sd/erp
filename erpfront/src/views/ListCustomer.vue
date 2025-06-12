<template>
  <h2>客户列表</h2>
  <el-table :data="custList" style="width: 100%">
    <el-table-column prop="id" label="客户编号" width="180" />
    <el-table-column prop="custName" label="客户姓名" width="180" />
    <el-table-column prop="address" label="地址" />
    <el-table-column prop="phone" label="联系电话" />
    <el-table-column prop="cutType" label="客户职业" />
    <el-table-column prop="graed" label="客户等级" />
    <el-table-column prop="hitTotal" label="消费总额" />
    
    <el-table-column fixed="right" label="操作" width="120">
      <template #default="scope">
        <el-button link type="primary" size="small" @click="deleteCust(scope.row.id)">删除</el-button>
        <el-button link type="primary" size="small" @click="openCustDialog(scope.$index,scope.row)">修改</el-button>
      </template>
    </el-table-column>
  </el-table>
  <hr/>
  <el-pagination
  :page-size="6"
  :pager-count="10"
  layout="prev, pager, next"
  :total="total"
  class="mt-4" @current-change="handlePageChange"
  />
  <el-dialog
  v-model="dialogCustVisible"
  width="80%"
  >
  <h2>修改客户信息</h2>
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
</el-dialog>
</template>

<script setup>
// import axios from 'axios';
import { getCurrentInstance } from "vue";
const axios=getCurrentInstance().proxy.axios
import {onMounted, ref ,reactive} from 'vue';
import {ElMessage} from "element-plus";

// 客户列表逻辑
const custList=ref([])
const total=ref()
function custListQeury(pageNum){
  axios.get("http://localhost:8081/listCust?pageNum="+pageNum)
  .then((response)=>{
    custList.value=response.data.custList
    total.value=response.data.total
  })
  .catch((error)=>{
    console.log(error);
  })
}
onMounted(function(){
  custListQeury(1)
})
function handlePageChange(value){
  custListQeury(value)
}

const dialogCustVisible = ref(false)
const custFrom=reactive({
  custName:'',
  address:'',
  phone:'',
  cutType:'',
  graed:1,
  hitTotal:0,
})

function openCustDialog(index,row) {
  dialogCustVisible.value = true;
  custFrom.custName = row.custName;
  custFrom.address = row.address;
  custFrom.phone = row.phone;
  custFrom.cutType=row.cutType;
  custFrom.graed=row.graed;
  custFrom.id=row.id;
}

function subCustFrom(){
  axios.post("http://localhost:8081/updatecCust",custFrom)
  .then((respose)=>{
    if(respose.data.code==200){
      ElMessage(respose.data.msg)
      dialogCustVisible.value=false;
    }else{
      ElMessage(respose.data.msg)
    }
  })
  .catch((error)=>{
    console.log(error);
  })
}

function deleteCust(id){
  axios.get("http://localhost:8081/delCust?id="+id)
  .then((respose)=>{
    if(respose.data.code==200){
      ElMessage(respose.data.msg)
      custListQeury(1)
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
