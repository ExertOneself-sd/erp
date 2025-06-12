<template>
  <h2>商品类目</h2>
    <el-tree
      :props="config"
      :data="categoryList"
      default-expand-all
      node-key="id"
      ref="treeRef"
      highlight-current="true"
      :expand-on-click-node="false"
      @node-click="handleNodeClick">
      <template #default="{ node, data }">
        <div class="custom-tree-node">
          <span>{{ node.label }}</span>
          <div>
            <el-button type="primary" link @click.stop="showCategory(node,data)">
              修改
            </el-button>
            <el-button style="margin-left: 4px" type="danger" link @click.stop="delCategory(node, data)">
              删除
            </el-button>
          </div>
          
        </div>
      </template>
    </el-tree>

      <hr/>
    <el-form :model="categoryForm" label-width="120px">
    <el-form-item label="分类编号">
      <el-input v-model="categoryForm.isbn" style="width: 100%" />
    </el-form-item>
    <el-form-item label="分类名称">
      <el-input v-model="categoryForm.cateName" style="width: 100%" /> 
    </el-form-item>
    <el-form-item label="分类描述">
      <el-input v-model="categoryForm.remark" style="width: 100%" /> 
    </el-form-item>

        <el-form-item>
      <el-button type="primary" @click="saveCategoryForm">保存</el-button>
      <el-button type="primary" @click="cancelCategoryForm">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
// import axios from 'axios';
import { getCurrentInstance } from "vue";
const axios=getCurrentInstance().proxy.axios
import { ElMessage } from 'element-plus';
import { onMounted, reactive, ref } from 'vue';


const config = {
  id: 'id',
  label: 'label',
  children: 'children'
}

const treeRef=ref(null)
const categoryList = ref([])

function queryCategoryTree(){
    axios.get('http://localhost:8081/categoryList')
    .then((response)=>{
        categoryList.value = response.data
    })
    .catch((error)=>{
        console.log(error)
    })
}

onMounted(function(){
    queryCategoryTree()
})

const categoryForm = reactive({
    id:0,
    isbn: '',
    cateName:'',
    remark:''
})

var pnode=null
function handleNodeClick(node) {
    pnode=node
    
}

function saveCategoryForm(){
    var url="http://localhost:8081/saveCategory"

    if(categoryForm.id==0){
    if(!pnode){
        ElMessage("必选选中某个父节点!")
        return
    }
    if(pnode.children.length==0){
        ElMessage("只能选中一级父节点!")
        return
    }
    categoryForm.pid=pnode.id
    }else{
        url="http://localhost:8081/updateCategory"
    }
    axios.post(url,categoryForm)
    .then((response)=>{
    if(response.data.code==200){
        queryCategoryTree()
        categoryForm.isbn=''
        categoryForm.cateName=''
        categoryForm.remark=''
        categoryForm.id=0
        pnode=null
    }
    ElMessage(response.data.msg)
    })
    .catch((error)=>{
        console.log(error)
    })

}

function cancelCategoryForm(){
    categoryForm.isbn=''
    categoryForm.cateName=''
    categoryForm.remark=''
    pnode=null
}


function showCategory(node,data){
    axios.get("http://localhost:8081/loadCategory/"+data.id)
    .then((response)=>{
        categoryForm.isbn=response.data.isbn
        categoryForm.cateName=response.data.cateName
        categoryForm.remark=response.data.remark
        categoryForm.id=data.id

    })
    .catch((error)=>{
        console.log(error)
    })

}

function delCategory(node,data){
    if(data.children.length!=0){
        ElMessage("该节点存在叶子节点，请选中叶子节点删除!")
        return
    }
    axios.get("http://localhost:8081/delCategory/"+data.id)
    .then((response)=>{
        ElMessage(response.data.msg)
        if(response.data.code==200){
            queryCategoryTree()
        }
    })
    .catch((error)=>{
        console.log(error)
    })
}

</script>

<style>

</style>