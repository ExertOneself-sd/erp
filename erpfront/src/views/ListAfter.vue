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
      <el-button type="primary" @click="subQueryAfter">查询</el-button>
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
      <el-button link type="primary" size="small" @click="openReplayDialog(scope.row.id)">处理</el-button>
      <el-button link type="primary" size="small" @click="loadQuesttionReplayList(scope.row)">查看处理详情</el-button>
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

<el-dialog
v-model="dialogReplayVisible"
:width="dialogWidth"
>
<h2 style="margin-bottom: 20px">回复客户投诉</h2>
<el-form 
  :model="replayForm" 
  label-width="100px"
  label-position="top"
>
  <el-form-item label="回复内容" style="margin-bottom: 24px">
    <el-input 
      v-model="replayForm.content" 
      type="textarea"
      :rows="4"
      resize="none"
      placeholder="请输入回复内容"
    />
  </el-form-item>
  <el-form-item style="text-align: right; margin-top: 24px">
    <el-button @click="dialogReplayVisible = false">取消</el-button>
    <el-button type="primary" @click="subReplayForm">保存</el-button>
  </el-form-item>
</el-form>
</el-dialog>

<el-dialog
v-model="dialogReplayListVisible"
width="80%"
>
  <h2>回复列表</h2>
  <div style="text-align: left">  
    <el-text>投诉客户:{{question.custName}}</el-text>
    <br/>
    <el-text>投诉问题:{{question.quesDesc}}</el-text>
    <el-table :data="replaysList" style="width: 100%">
  <el-table-column prop="id" label="编号"  />
  <el-table-column prop="redate" label="时间" />
  <el-table-column prop="score" label="评分" />
  <el-table-column prop="content" label="内容" />
  
  <el-table-column fixed="right" label="操作">
    <template #default="scope">
      <el-button link type="primary" size="small" @click="openReplayDialog(scope.row.id)">处理</el-button>
      <el-button link type="primary" size="small" @click="loadQuesttionReplayList(scope.row)">查看处理详情</el-button>
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
          :total="totalReplay"
          class="mt-4" @current-change="handlerReplayPageChange"/>

  </div>


</el-dialog>
</template>

<script setup>
import { onMounted,reactive,ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'


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

function subQueryAfter(){
  queryAfterSaleList(1)
}

const dialogReplayVisible=ref(false)
const dialogWidth=ref('50%')
const replayForm=reactive({
  content:''
})

onMounted(() => {
  updateDialogWidth()
  window.addEventListener('resize', updateDialogWidth)
})

function updateDialogWidth() {
  dialogWidth.value = window.innerWidth < 768 ? '90%' : '50%'
}

function openReplayDialog(qid){
  dialogReplayVisible.value=true
  replayForm.quesId=qid
}

function subReplayForm(){
  axios.post("http://localhost:8081/saveReplay",replayForm)
  .then((response)=>{
    if(response.data.code==200){
      dialogReplayVisible.value=false
      replayForm.content=''
      ElMessage(response.data.msg)
    }else{
      ElMessage.error(response.data.msg)
    }
  })
  .catch((error)=>{
    console.log(error)
  })
}

const dialogReplayListVisible=ref(false)
const question=reactive({
  custName:'',
  quesDesc:''
})

const totalReplay=ref(0)
const replaysList=ref([])
let qid=0


function loadQuesttionReplayList(row){
    dialogReplayListVisible.value=true;
    question.custName=row.custName;
    question.quesDesc=row.question;
    qid=row.id;
    //发送ajax请求
    axios.get("http://localhost:8081/listReplay?id="+row.id)
    .then((response)=>{
      replaysList.value=response.data.replayList;
      totalReplay.value=response.data.total;
    })
    .catch((error)=>{
      console.log(error);
    })
  }

  //提交分页查询参数的请求
  function handlerReplayPageChange(pageNum){
    //发送ajax请求
    axios.get("http://localhost:8081/listReplay?id="+qid+"&pageNum="+pageNum)
        .then((response)=>{
          replaysList.value=response.data.replayList;
          totalReplay.value=response.data.total;
        })
        .catch((error)=>{
          console.log(error);
        })
  }

</script>

<style>

</style>