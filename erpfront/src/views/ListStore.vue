<template>
  <h2>仓库列表</h2>
  <div style="text-align: left">
    <el-form :model="condForm" label-width="80px" :inline="true" class="cond-form">
      <el-form-item>
        <el-button type="primary" @click="openStoreDialog">添加仓库</el-button>
      </el-form-item>
    </el-form>
  </div>
  <el-table :data="storeList"  style="width: 100%">
    <el-table-column prop="storeName" label="仓库名称"  />
    <el-table-column prop="storeNum" label="仓库编号" />
    <el-table-column prop="storeAddress" label="仓库地址" />
    <el-table-column prop="concat" label="联系人" />
    <el-table-column prop="phone" label="联系电话" />

    <el-table-column fixed="right" label="操作" width="200">
        <template #default="scope" >
            <el-button link type="primary" size="small" @click="openEditStore(scope.row)">修改</el-button>
            <el-button link type="primary" size="small" @click="deleteStore(scope.row)">删除</el-button>
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
      class="mt-4" @current-change="handlerStorePageChange"/>

    <el-dialog
      v-model="storeDialog"
      width="40%">
    <h2>仓库信息</h2>
    <el-form :model="storeForm" label-width="120px">
        <el-form-item label="仓库名称：">
          <el-input v-model="storeForm.storeName" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="仓库编号：">
          <el-input v-model="storeForm.storeNum" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="仓库地址：">
          <el-input v-model="storeForm.storeAddress" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="联系人：">
          <el-input v-model="storeForm.concat" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="联系电话：">
          <el-input v-model="storeForm.phone" style="width: 80%"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="subStore">保存</el-button>
          <el-button type="primary" >清空</el-button>
        </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { onMounted, reactive, ref } from 'vue';


const storeList=ref([])
const total=ref(0)
const currentPage = ref(1)

function queryStoreList(pageNum){
  currentPage.value = pageNum
    axios.get("http://localhost:8081/storePageList?pageNum="+pageNum)
    .then((response)=>{
        storeList.value=response.data.storeList
        total.value=response.data.total
    })
    .catch((error)=>{
        console.log(error)
    })
}

onMounted(function(){
    queryStoreList(1)
})

function handlerStorePageChange(pageNum){
    queryStoreList(pageNum)
}

const storeDialog=ref(false)
const storeForm=reactive({
    storeName:'',
    storeNum:'',
    storeAddress:'',
    concat:'',
    phone:'',
    storeId:''

})

var url=""

function openStoreDialog(){
    url="http://localhost:8081/saveStore"
    storeDialog.value=true
    storeForm.storeName=''
    storeForm.storeNum=''
    storeForm.storeAddress=''
    storeForm.concat=''
    storeForm.phone=''
    storeForm.storeId=''
}




function openEditStore(row){
    url="http://localhost:8081/updateStore"
    storeDialog.value=true
    storeForm.storeName=row.storeName
    storeForm.storeNum=row.storeNum
    storeForm.storeAddress=row.storeAddress
    storeForm.concat=row.concat
    storeForm.phone=row.phone
    storeForm.storeId=row.storeId
}

function subStore(){
    axios.post(url,storeForm)
    .then((response)=>{
        if(response.data.code==200){
            storeDialog.value=false
            queryStoreList(currentPage.value)

        }
        ElMessage(response.data.msg)
    })
    .catch((error)=>{
        console.log(error)
    })
}

function deleteStore(row){
  axios.get("http://localhost:8081/delStore/"+row.storeId)
  .then((response)=>{
      if(response.data.code==200){
          queryStoreList(currentPage.value)
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