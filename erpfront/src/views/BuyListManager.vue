<template>
  <h2>采购单列表</h2>
  <div style="text-align: left">
    <el-button type="success" @click="exportData">导出数据</el-button>
  </div>
  
  <el-table :data="buyList"  style="width: 100%">
    <el-table-column prop="storeName" label="仓库名称"  />
    <el-table-column prop="itemName" label="商品名称" />
    <el-table-column prop="buyNum" label="预计采购数量"  />
    <el-table-column prop="factBuyNum" label="实际采购数量" />
    <el-table-column prop="buyUser" label="采购人" />
    <el-table-column prop="buyTime" label="采购时间" />
    <el-table-column prop="phone" label="采购人电话"  />
    <el-table-column prop="isIn" label="状态" width="100">
        <template #default="scope" >
            <span v-if="scope.row.isIn==0" style="color:red">未入库</span>
            <span v-else style="color:green">已入库</span>
        </template>
    </el-table-column>
    <el-table-column fixed="right" label="操作" width="200">
        <template #default="scope" >
            <el-button link type="primary" v-if="scope.row.factBuyNum==0" size="small" @click="showBuyListDialog(scope.row)">修改</el-button>
            <el-button link type="primary" v-if="scope.row.factBuyNum==0" size="small" @click="delBuyList(scope.row)">删除</el-button>
            <el-button link type="primary" v-if="scope.row.isIn==0 && scope.row.factBuyNum>0" size="small" @click="doInStore(scope.row)">生成入库单</el-button>
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
      class="mt-4" @current-change="buyListPageChange"/>

    <el-dialog
      v-model="buyListDialog"
      width="40%">
    <h2>商品采购</h2>
    <el-form :model="buyForm" label-width="120px">
        <el-form-item label="商品名称：">
          {{buyForm.itemName}}
        </el-form-item>
        <el-form-item label="仓库：">
          {{buyForm.storeName}}
        </el-form-item>
        <el-form-item label="供应商：">
          {{buyForm.supplyName}}
        </el-form-item>
        <el-form-item label="产地：">
          {{buyForm.placeName}}
        </el-form-item>
        <el-form-item label="预计采购量：" required>
          <el-input v-model="buyForm.buyNum" style="width: 80%;"></el-input>
        </el-form-item>
        <el-form-item label="实际采购量：" required>
          <el-input v-model="buyForm.factBuyNum" style="width: 80%;"></el-input>
        </el-form-item>
        <el-form-item label="采购人：" required>
          <el-input v-model="buyForm.buyUser" style="width: 80%;"></el-input>
        </el-form-item>
        <el-form-item label="采购人电话：">
          <el-input v-model="buyForm.phone" style="width: 80%;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateBuyOrder">保存</el-button>
          <el-button type="primary" >取消</el-button>
        </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup>
// import axios from 'axios';
import { getCurrentInstance } from "vue";
const axios=getCurrentInstance().proxy.axios
import { ElMessage } from 'element-plus';
import { onMounted,reactive, ref } from 'vue';


const buyList=ref([])
const total=ref(0)


function queryBuyList(pageNum){
    axios.get('http://localhost:8081/queryBuyList?pageNum='+pageNum)
    .then((response)=>{
        buyList.value=response.data.buyLists
        total.value=response.data.total
    })
    .catch((error)=>{
        console.log(error)
    })
}

onMounted(function(){
    queryBuyList(1)
})

function buyListPageChange(pageNum){
  queryBuyList(pageNum)
}

const buyForm=reactive({
  productId:'',
  storeId:'',
  supplyId:'',
  placeId:'',
  itemName:'',
  storeName:'',
  supplyName:'',
  placeName:'',
  buyUser:'',
  buyNum:'',
  factBuyNum:'',
  phone:''
})

const buyListDialog=ref(false)

function showBuyListDialog(row){
    var productId=row.productId
    buyListDialog.value=true
    axios.get("http://localhost:8081/buyAutoInfo/"+productId)
  .then((response)=>{
    var item=response.data
    buyForm.productId=item.id
    buyForm.itemName=item.itemName
    buyForm.storeId=item.storeId
    buyForm.storeName=item.storeName
    buyForm.supplyId=item.supplyId
    buyForm.supplyName=item.supplyName
    buyForm.placeId=item.placeId
    buyForm.placeName=item.placeName
    buyForm.buyUser=row.buyUser
    buyForm.buyNum=row.buyNum
    buyForm.factBuyNum=row.factBuyNum
    buyForm.phone=row.phone
    //表单中封装采购单id
    buyForm.buyId=row.buyId

  })
  .catch((error)=>{
    console.log(error);
  })
}

function updateBuyOrder(){
    axios.post("http://localhost:8081/updateBuyList",buyForm)
    .then((response)=>{
        if(response.data.code==200){
             buyListDialog.value=false
             queryBuyList(1)
        }
        ElMessage(response.data.msg)
    })
    .catch((error)=>{
        console.log(error)
    })

}

function delBuyList(row){
    axios.get("http://localhost:8081/deleteBuyList/"+row.buyId)
    .then((response)=>{
        if(response.data.code==200){
            queryBuyList(1)
        }
        ElMessage(response.data.msg)
    })
    .catch((error)=>{
        console.log(error)
    })
}


function doInStore(row){
    axios.post("http://localhost:8081/buyInStore",row)
    .then((response)=>{
        if(response.data.code==200){
            queryBuyList(1)
        }
        ElMessage(response.data.msg)
    })
    .catch((error)=>{
        console.log(error)
    })

}

function exportData(){
    location.href="http://localhost:8081/exportExcel"
}

</script>

<style>

</style>