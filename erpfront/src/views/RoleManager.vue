<template>
  <h2>角色管理</h2>
  <div style="text-align: left;">
    <el-button type="primary" @click="openRolerDialog">添加角色</el-button>
  </div>
  <el-table :data="rolerList" style="width: 100%">
    <el-table-column prop="id" label="编号"  />
    <el-table-column prop="rname" label="角色" >
        <template #default="scope">
          <el-input v-if="scope.row.edit" v-model="scope.row.rname"></el-input>
          <span v-else>{{ scope.row.rname }}</span>
      </template>
    </el-table-column>
    <el-table-column prop="rdesc" label="描述" >
        <template #default="scope">
          <el-input v-if="scope.row.edit" v-model="scope.row.rdesc"></el-input>
          <span v-else>{{ scope.row.rdesc }}</span>
      </template>
    </el-table-column>
    <el-table-column fixed="right" label="操作" width="160">
      <template #default="scope">
        <el-button v-if="!scope.row.edit" link type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button v-else link type="primary" size="small" @click="handleSave(scope.row)">保存</el-button>
        <el-button link type="primary" size="small" @click="deleteRoler(scope.row.id)">删除</el-button>
        <el-button link type="primary" size="small" @click="rolerAndMenus(scope.row.id)">授权</el-button>
      </template>
    </el-table-column>
  </el-table>
  <hr/>
   <el-pagination
    :page-size="6"
    :pager-count="10"
    layout="prev, pager, next"
    :total="total"
    class="mt-4" @current-change="rolerPageChange"
  />

   <el-dialog
      v-model="dialogRolerVisible"
      width="80%"
  >
  <h2>角色信息</h2>
  <el-form :model="rolerForm" label-width="120px">
    <el-form-item label="角色名称">
      <el-input v-model="rolerForm.rname" style="width: 100%" />
    </el-form-item>
    <el-form-item label="角色描述">
      <el-input v-model="rolerForm.rdesc" style="width: 100%" /> 
    </el-form-item>

        <el-form-item>
      <el-button type="primary" @click="saveRoleForm">保存</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
  </el-dialog>

  <el-dialog
      v-model="dialogRolerMenusVisible"
      width="80%"
  >
  <h2>角色授权</h2>

    <el-tree
      :props="props2"
      :data="treeNodeList2"
      show-checkbox
      default-expand-all
      node-key="id"
      ref="treeRef"
    />
    <el-button type="primary" @click="saveRoleMenus">保存权限</el-button>
    <el-button type="success">关闭授权</el-button>
  </el-dialog>
</template>

<script setup>
// import axios from 'axios';
import { getCurrentInstance } from "vue";
const axios=getCurrentInstance().proxy.axios
import { ElMessage } from 'element-plus';
import { onMounted,reactive,ref} from 'vue';

const rolerList=ref([]);
const total=ref(0);

function queryRoleList(pageNum) {
    axios.get("http://localhost:8081/rolerList?pageNum="+pageNum)
    .then((response)=>{
        rolerList.value = response.data.rolerList;
        total.value=response.data.total;
    })   
    .catch((error)=>{
        console.log(error);
    })
}

 onMounted(function() {
    queryRoleList(1);
})

function rolerPageChange(page) {
    queryRoleList(page);
}

 function handleEdit(row){
    row.edit=true
 }
 
 function handleSave(row){
    axios.post("http://localhost:8081/updateRoler",row)
    .then((response)=>{
        if(response.data.code==200){
            row.edit=false;
        }
        ElMessage(response.data.msg)
    })
    .catch((error)=>{
        console.log(error);
    })
 }
 
const dialogRolerVisible=ref(false);
const rolerForm=reactive({
    rname:'',
    rdesc:''
});

function openRolerDialog(){
    dialogRolerVisible.value=true;
}

function saveRoleForm(){
    axios.post("http://localhost:8081/saveRoler",rolerForm)
    .then((response)=>{
        if(response.data.code==200){
            dialogRolerVisible.value=false;
            rolerForm.rname='';
            rolerForm.rdesc='';
        }
        ElMessage(response.data.msg)
    })
    .catch((error)=>{
        console.log(error);
    })
}

function deleteRoler(id){
    axios.get("http://localhost:8081/delRoler?id="+id)
    .then((response)=>{
      if(response.data.code==200){
        ElMessage(response.data.msg)
        queryRoleList(1)
      }else{
        ElMessage(response.data.msg)
      }
    })
    .catch((error)=>{
        console.log(error);
    })
}

const dialogRolerMenusVisible=ref(false);
const props2 = {
  id: 'id',
  label: 'label',
  children: 'subMenus'
}
const treeNodeList2=ref([])
const treeRef=ref(null)
var global_rid=0
function rolerAndMenus(rid){
    global_rid=rid
    dialogRolerMenusVisible.value=true
    axios.get("http://localhost:8081/listMenus")
    .then((response)=>{
    treeNodeList2.value = response.data
    axios.get("http://localhost:8081/loadRoleMid/"+rid)
    .then((response)=>{
        treeRef.value.setCheckedKeys(response.data)
    })
    .catch((error)=>{
      console.log(error)
    })
    })
    .catch((error)=>{
      console.log(error)
    })
}

function saveRoleMenus(){
  var nodes=treeRef.value.getCheckedNodes(false,true)
  var arr=[global_rid]
  nodes.forEach((item)=>{
    arr.push(item.id)
  })
  console.log(arr)
  axios.post("http://localhost:8081/grantRolerMenus",arr)
  .then((response)=>{
      if(response.data.code==200){
        dialogRolerMenusVisible.value=false
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