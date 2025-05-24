<template>
  <h2>用户管理</h2>
  <div style="text-align: left">
    <el-button type="danger" @click="openUserDialog">添加用户</el-button>
  </div>
  
  <el-table :data="userList" style="width: 100%">
  <el-table-column prop="id" label="用户编号"  />
  <el-table-column prop="uname" label="用户名" />
  <el-table-column prop="phone" label="电话" />
  <el-table-column prop="edu" label="学历" />
  <el-table-column prop="age" label="年龄" />
  <el-table-column prop="title" label="部门" />
  
  <el-table-column fixed="right" label="操作">
    <template #default="scope">
      <el-button link type="primary" size="small" @click="showUserDialog(scope.row)">修改</el-button>
      <el-button link type="primary" size="small" @click="deleteUser(scope.row.id)">删除</el-button>
    </template>
  </el-table-column>
</el-table>
<hr/>
<el-pagination
:page-size="6"
:pager-count="10"
layout="prev, pager, next"
:total="total"
class="mt-4" @current-change="userPageChange"
/>

<el-dialog
  v-model="dialogUserVisible"
  width="80%"
  >
  <h2>修改客户信息</h2>
  <el-form :model="userForm" label-width="120px">
    <el-form-item label="用户名">
      <el-input v-model="userForm.uname" style="width: 100%" />
    </el-form-item>
    <el-form-item label="电话">
      <el-input v-model="userForm.phone" style="width: 100%" />
    </el-form-item>
    <el-form-item label="学历">
      <el-input v-model="userForm.edu" style="width: 100%" />
    </el-form-item>
    <el-form-item label="年龄">
      <el-input v-model="userForm.age" style="width: 100%" />
    </el-form-item>
        <el-form-item label="部门">
    <el-input v-model="userForm.title" style="width: 100%" />
    </el-form-item>
    <el-form-item label="角色">
      <el-select v-model="userForm.rids" placeholder="请选择部门...." style="width: 100%" multiple>
        <el-option v-for="opt in optRoles" :label="opt.rname" :value="opt.id" :key="opt.id" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="subUserForm">保存</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</el-dialog>  

</template>

<script setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { onMounted,reactive,ref } from 'vue';


const userList = ref([]);
const total = ref(0);

function queryUserList(pageNum){
    axios.get("http://localhost:8081/userList?pageNum="+pageNum)
    .then((response)=>{
        userList.value = response.data.userList
        total.value = response.data.total;
    })
    .catch((error) => {
        console.log(error);
    })
}

onMounted(function(){
    queryUserList(1)
})

function userPageChange(pageNum){
    queryUserList(pageNum)
}

const dialogUserVisible = ref(false);
const userForm=reactive({
    uname:'',
    phone:'',
    edu:'',
    age:'',
    title:'',
    rids:[]
})

const optRoles = ref([])

//声明变量保存添加用户信息和更新用户信息的接口地址
var url=""

function openUserDialog(){
    dialogUserVisible.value=true
    url="http://localhost:8081/saveUser"
    axios.get("http://localhost:8081/loadAllRoles")
    .then((response)=>{
        optRoles.value = response.data
        userForm.uname=''
        userForm.phone=''
        userForm.edu=''
        userForm.age=''
        userForm.title=''
        userForm.rids=[]
    })
    .catch((error) => {
        console.log(error);
    })

}



function subUserForm(){
    axios.post(url,userForm)
    .then((response)=>{
        if(response.data.code==200){
          dialogUserVisible.value=false
        }
        ElMessage(response.data.msg)

    })
    .catch((error) => {
        console.log(error);
    })
}

function showUserDialog(row){
  dialogUserVisible.value=true
  url="http://localhost:8081/updateUser"
  userForm.age=row.age
  userForm.edu=row.edu
  userForm.id=row.id
  userForm.uname=row.uname
  userForm.phone=row.phone
  userForm.title=row.title
  axios.get("http://localhost:8081/loadAllRoles")
    .then((response)=>{
        optRoles.value = response.data
        axios.get("http://localhost:8081/queryUserRids/"+row.id)
        .then((response)=>{
          userForm.rids=response.data
        })
    })
    .catch((error) => {
        console.log(error);
    })
}

function deleteUser(id){
  axios.get("http://localhost:8081/delUser/"+id)
  .then((response)=>{
    if(response.data.code==200){
      queryUserList(1)
    }
    ElMessage(response.data.msg)
  })
  .catch((error) => {
      console.log(error);
  })

}

</script>

<style>

</style>