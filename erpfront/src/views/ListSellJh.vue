<template>
  <h2>客户销售过程列表</h2>
  <el-table :data="sellJhList" style="width: 100%">
    <el-table-column prop="id" label="销售编号" width="180" />
    <el-table-column prop="custName" label="客户姓名" width="180" />
    <el-table-column prop="channelId" label="销售渠道" >
        <template #default="scope">
            {{getChannelName(scope.row.channelId)}}
        </template>
    </el-table-column>
    <el-table-column prop="money" label="销售金额" />
    <el-table-column prop="nowStep" label="开发阶段" />
    <el-table-column prop="empId" label="业务员" />

    <el-table-column fixed="right" label="操作" width="120">
      <template #default="scope">
        <el-button link type="primary" size="small" @click="deleteSellJh(scope.row.id)">删除</el-button>
        <el-button link type="primary" size="small" @click="openSellJhDialog(scope.row)">修改</el-button>
      </template>
    </el-table-column>
  </el-table>
  <hr/>

  <el-pagination
    :page-size="6"
    :pager-count="10"
    layout="prev, pager, next"
    :total="total"
    class="mt-4" @current-change="sellJhPageChange"
  />
  <el-dialog
      v-model="dialogSellJhVisible"
      width="80%"
  >
  <h2>修改销售过程</h2>
  <el-form :model="sellJhForm" label-width="120px">
    <el-form-item label="客户姓名">
      <el-select
          v-model="sellJhForm.custid"
          class="m-2"
          placeholder="请选择客户"
          size="large"
          style="width: 80%"
      >
        <el-option
            v-for="item in custList"
            :key="item.id"
            :label="item.custName"
            :value="item.id"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="销售渠道">
      <el-select v-model="sellJhForm.channelId" placeholder="请选择渠道...." style="width: 80%">
        <el-option v-for="opt in channeList" 
        :value="opt.id"
        :label="opt.label"
        :key="opt.id"></el-option>
      </el-select>
    </el-form-item>
     <el-form-item label="销售金额">
      <el-input v-model="sellJhForm.money" style="width: 80%" />
    </el-form-item>
    <el-form-item label="开发阶段">
      <el-select v-model="sellJhForm.nowStep" placeholder="请选择...." style="width: 80%">
        <el-option label="解除" value="解除" />
        <el-option label="报价" value="报价" />
        <el-option label="签约" value="签约" />
      </el-select>
    </el-form-item>
     <el-form-item label="业务员">
      <el-input v-model="sellJhForm.empId" style="width: 80%" readonly="readonly"/>
    </el-form-item>
        <el-form-item>
      <el-button type="primary" @click="subSellJhFrom">保存</el-button>
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

const sellJhList=ref([])
const total=ref()
function sellJhListQeury(pageNum){
    axios.get("http://localhost:8081/sellJhList?pageNum="+pageNum)
    .then((response)=>{
        sellJhList.value=response.data.sellJhList
        total.value=response.data.total
    })
    .catch((error)=>{
        console.log(error)
    })
}
onMounted(function(){
    sellJhListQeury(1)
})
function sellJhPageChange(PageNum){
    sellJhListQeury(PageNum)
}

function getChannelName(channelId){
    switch(channelId){
        case 0:
            return "自媒体"
        case 1:
            return "网络推广"
        case 2:
            return "老客户介绍"
        case 3:
            return "陌拜"
        case 4:
            return "二次客户"
    }
}

const dialogSellJhVisible=ref(false)
const sellJhForm=reactive({
    custid:'',
    channelId:'',
    money:0.0,
    nowStep:'',
    empId:1001
})

const custList=ref([])


function openSellJhDialog(row){
    dialogSellJhVisible.value=true
    axios.get("http://localhost:8081/listAllCust")
    .then((response)=>{
        custList.value=response.data;
        sellJhForm.custid=row.custid
        sellJhForm.channelId=row.channelId
        sellJhForm.money=row.money
        sellJhForm.nowStep=row.nowStep
        sellJhForm.empId=row.empId
        sellJhForm.id=row.id
        console.log(sellJhForm)
    })
    .catch((error)=>{
        console.log(error)
    })
}

const channeList=ref([{id:0,label:'自媒体'},{id:1,label:'网络推广'},{id:2,label:'老客户介绍'},{id:3,label:'陌拜'},{id:4,label:'二次客户'}])

function subSellJhFrom(){
  axios.post("http://localhost:8081/updateSellJh",sellJhForm)
  .then((response)=>{
    if(response.data.code==200){
      ElMessage(response.data.msg)
      dialogSellJhVisible.value=false
      sellJhListQeury(1)
    }else{
      ElMessage(response.data.msg)
    }
})
.catch((error)=>{
    console.log(error)
})
}

function deleteSellJh(id){
  axios.get("http://localhost:8081/delSellJh?id="+id)
  .then((response)=>{
    if(response.data.code==200){
      ElMessage(response.data.msg)
      sellJhListQeury(1)
    }else{
      ElMessage(response.data.msg)
    }
})
.catch((error)=>{
    console.log(error)
})
}


</script>

<style>

</style>