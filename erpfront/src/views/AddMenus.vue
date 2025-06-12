<template>
  <h2>添加菜单</h2>
  <div>
    选择新增节点的父节点：
      <el-tree
      :props="props"
      :data="treeNodeList"
      node-key="id"
      ref="treeRef"
      @node-click="hanldNodeClick"
  >
      <template #default="{ node, data }">
        <div class="custom-tree-node">
          <span>{{ node.label }}</span>
          <div>
            <el-button type="primary" link @click.stop="showMenus(node,data)">
              修改
            </el-button>
            <el-button
              style="margin-left: 4px"
              type="danger"
              link
              @click.stop="delMenus(node, data)"
            >
              删除
            </el-button>
          </div>
          
        </div>
      </template>

    </el-tree>
  </div>
    <hr/>

  <el-form :model="menuForm" label-width="auto" style="max-width: 600px">
  <el-form-item label="菜单名称" style="margin-bottom: 24px">
    <el-input 
      v-model="menuForm.label" />
  </el-form-item>
  <el-form-item style="text-align: right; margin-top: 24px">
    <el-button type="primary" @click="subMenuForm">确定</el-button>
    <el-button >取消</el-button>
  </el-form-item>
</el-form>
</template>

<script setup>
// import axios from 'axios';
import { getCurrentInstance } from "vue";
const axios=getCurrentInstance().proxy.axios
import { ElMessage } from 'element-plus';
import { onMounted, reactive, ref } from 'vue';


const props = {
  id: 'id',
  label: 'label',
  children: 'subMenus'
}

const treeNodeList = ref([])
const menuForm = reactive({
    label:''
})

function loadMenuTree() {
  axios.get("http://localhost:8081/listMenus")
  .then((response)=>{
    treeNodeList.value = response.data
  })
  .catch((error)=>{
    console.log(error)
  })
}

onMounted(function(){
  loadMenuTree()
})

var id=0
var url=""


function hanldNodeClick(node){
    console.log(node)
    url="http://localhost:8081/saveMenus"
    id=node.id
}

function subMenuForm(){
    if(id==0){
        ElMessage("请选择当前新增菜单节点的父节点")
        return
    }

    if(!menuForm.label || menuForm.label.trim() === ''){
        ElMessage.error("菜单名称不能为空")
        return
    }
    menuForm.pid=id
    axios.post(url,menuForm)
    .then((response)=>{
     if(response.data.code==200){
        loadMenuTree()
        id=0

     }
     ElMessage(response.data.msg)
    })
    .catch((error)=>{
        console.log(error)
    })
}


function delMenus(node,data){
    console.log(data.subMenus)
    if(data.subMenus.length==0){
        let mid=data.id
        axios.get("http://localhost:8081/deleteMenus/"+mid)
        .then((response)=>{
            if(response.data.code==200){
                loadMenuTree()
            }
            ElMessage(response.data.msg)
        })
    }else{
        ElMessage(data.label+",节点存在子节点不能删除!")
    }
}

function showMenus(node,data){
    console.log()
    menuForm.label=data.label
    menuForm.id=data.id
    id=node.parent.data.id
    url="http://localhost:8081/updateMenus"

}

</script>

<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>