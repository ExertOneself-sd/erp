<template>
  <h2>商品信息</h2>

  <div style="text-align: left">
    <el-form :model="condForm" label-width="80px" :inline="true" class="cond-form">
      <el-form-item>
        <el-button type="primary" @click="openItemDialog">添加商品</el-button>
      </el-form-item>
      <el-form-item label="编号">
        <el-input v-model="condForm.itemNum" style="width: 160px" />
      </el-form-item>
      <el-form-item label="名称">
        <el-input v-model="condForm.itemName" style="width: 160px" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="condForm.statue" placeholder="请选择商品状态..." style="width: 160px" clearable>
          <el-option label="上架" value="0" />
          <el-option label="下架" value="1" />
          <el-option label="删除" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="queryCondItem">查询</el-button>
     </el-form-item>
    </el-form>
  </div>

  <el-dialog v-model="dialogItemVisible" width="80%" class="custom-dialog">
    
    <div style="text-align: left">
      商品图片:<!--  上传商品图片控件  -->

      <el-upload action="http://localhost:8081/uploadImg" list-type="picture-card" :auto-upload="true" method="post" :on-success="handlAvatarSuccess">


        
        <el-icon>
          <Plus/>
        </el-icon>

        <template #file="{ file }">
          <div>
            <img class="el-upload-list__item-thumbnail" :src="file.url" alt=""/>
            <span class="el-upload-list__item-actions">
          <span
              class="el-upload-list__item-preview"
              @click="handlePictureCardPreview(file)"
          >
            <el-icon><zoom-in/></el-icon>
          </span>
        </span>
          </div>
        </template>
      </el-upload>

      <el-image v-for="img in imageUrls" :src="img" style="width: 138px;height: 138px"></el-image>

    </div>

  <el-form :model="itemForm" label-width="100px" style="max-width: 800px; margin-top: 20px;">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="名称" required>
          <el-input v-model="itemForm.itemName" placeholder="请输入商品名称" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="编号" required>
          <el-input v-model="itemForm.itemNum" placeholder="请输入商品编号" />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="种类" required>
          <el-input v-model="itemForm.typeId" placeholder="选择类型" @click="openTypeDialog"/>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="库存">
          <el-input-number v-model="itemForm.store" :min="0" />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="品牌" required>
          <el-select v-model="itemForm.brandId" placeholder="请选择品牌">
            <el-option
            v-for="item in brandList"
            :key="item.brandId"
            :label="item.brandName"
            :value="item.brandId"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="仓库" required>
          <el-select v-model="itemForm.storeId" placeholder="请选择仓库">
            <el-option
            v-for="item in storeList"
            :key="item.storeId"
            :label="item.storeName"
            :value="item.storeId"
            />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="供应商" required>
          <el-select v-model="itemForm.supplyId" placeholder="请选择供应商">
            <el-option
            v-for="item in supplyList"
            :key="item.supplyId"
            :label="item.supplyName"
            :value="item.supplyId"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="产地" required>
          <el-select v-model="itemForm.placeId" placeholder="请选择产地">
            <el-option
            v-for="item in placeList"
            :key="item.placeId"
            :label="item.placeName"
            :value="item.placeId"
            />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>

    <el-form-item label="单位" required>
      <el-select v-model="itemForm.unitId" placeholder="请选择单位">
        <el-option
            v-for="item in unitList"
            :key="item.unitId"
            :label="item.unitName"
            :value="item.unitId"
            />
      </el-select>
    </el-form-item>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="进价">
          <el-input v-model="itemForm.price" placeholder="0">
            <template #append>元</template>
          </el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="售价">
          <el-input v-model="itemForm.sellPrice" placeholder="0">
            <template #append>元</template>
          </el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-form-item label="会员价">
      <el-input v-model="itemForm.vipPrice" placeholder="0">
        <template #append>元</template>
      </el-input>
    </el-form-item>

    <el-form-item label="详细介绍">
      <el-input v-model="itemForm.itemDesc" type="textarea" :rows="3" />
    </el-form-item>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="生产日期">
          <el-date-picker v-model="itemForm.itemDate" type="date" placeholder="年/月/日" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="保质期">
          <el-date-picker v-model="itemForm.endDate" type="date" placeholder="年/月/日" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-form-item>
      <el-button type="primary" @click="saveItemForm">保存</el-button>
      <el-button type="primary" @click="cancelItemForm">清空</el-button>
    </el-form-item>
  </el-form>
  
  </el-dialog>
  <el-dialog v-model="dialogVisible" class="preview-dialog">
    <img w-full :src="dialogImageUrl" alt="Preview Image" style="width: 100%" />
  </el-dialog>
  <el-dialog
      v-model="typeDialog"
      width="40%">
    <h2>选择商品类型</h2>
    <el-tree
        :props="config"
        :data="categoryList"
        default-expand-all
        node-key="id"
        ref="treeRef"
        highlight-current="true"
        :expand-on-click-node="false"
        @node-click="handleClickNode">
    </el-tree>
    <el-button type="primary" @click="saveType">确认</el-button>
  </el-dialog>

  <br/>

  <el-table :data="itemList" :border="parentBorder" style="width: 100%">
    <el-table-column type="expand">
      <template #default="props">
        <div m="4">
            <el-image :src="doImgs(props.row.imgs)" style="width: 200px;height: 100px"></el-image>

          <el-table :data="[props.row]" :border="childBorder">
            <el-table-column label="进价" prop="price" />
            <el-table-column label="销售价" prop="sellPrice" />
            <el-table-column label="会员价" prop="vipPrice" />
            <el-table-column label="生产日期" prop="itemDate" />
            <el-table-column label="过期日期" prop="endDate" />
            <el-table-column label="库存" prop="store" />
          </el-table>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="编号" prop="itemNum" />
    <el-table-column label="名称" prop="itemName" />
    <el-table-column label="品牌" prop="brandName" />
    <el-table-column label="产地" prop="placeName" />
    <el-table-column label="状态" >
      <template #default="scope">
        {{toStatue(scope.row.statue)}}
      </template>
    </el-table-column>
    <el-table-column label="单位" prop="unitName" width="120px"/>

    <el-table-column fixed="right" label="操作" width="130px">
      <template #default="scope">
        <el-button-group>
            <el-button link type="primary" size="small" @click="editItem(scope.row)">修改</el-button>
            <el-button link type="primary" size="small" @click="openBuyDialog(scope.row.id)">采购</el-button>
            <el-button link type="primary" size="small" @click="openCustDialog(scope.row)">出库</el-button>
        </el-button-group>
        <el-button-group>
            <el-button link type="primary" size="small" @click="upItem(scope.row.id)">上架</el-button>
            <el-button link type="primary" size="small" @click="downItem(scope.row.id)">下架</el-button>
            <el-button link type="primary" size="small" @click="delItem(scope.row.id)">删除</el-button>
        </el-button-group>
      </template>
    </el-table-column>
  </el-table>
<!-- 分页组件 -->
  <el-pagination
      small
      background
      :page-size="6"
      :pager-count="10"
      layout="prev, pager, next"
      :total="total"
      class="mt-4" @current-change="handlerPageChange"/>

    <el-dialog
      v-model="buyDialog"
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
        <el-form-item label="采购人：" required>
          <el-input v-model="buyForm.buyUser" style="width: 80%;"></el-input>
        </el-form-item>
        <el-form-item label="采购人电话：">
          <el-input v-model="buyForm.phone" style="width: 80%;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveBuyOrder">保存</el-button>
          <el-button type="primary" >清空</el-button>
        </el-form-item>
    </el-form>
  </el-dialog>
    
    <el-dialog
      v-model="itemOutDialog"
      width="40%">
    <h2>商品出库</h2>
    <el-form :model="outForm" label-width="120px">
        <el-form-item label="商品名称：">
          {{outForm.itemName}}
        </el-form-item>
        <el-form-item label="仓库：">
          {{outForm.storeName}}
        </el-form-item>
        <el-form-item label="商品库存：">
          {{outForm.store}}
        </el-form-item>
        <el-form-item label="出库数量：">
          <el-input v-model="outForm.outNum" style="width: 80%"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOutOrder">保存</el-button>
          <el-button type="primary" >清空</el-button>
        </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup>

import { onMounted, reactive, ref } from 'vue'
import { Plus, Delete, ZoomIn } from '@element-plus/icons-vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

const dialogItemVisible = ref(false)
const itemForm = reactive({
  id: '',
  itemNum:'',
  itemName:'',
  typeId:'',
  store:0,
  brandId:'',
  storeId:'',
  supplyId:'',
  placeId:'',
  unitId:'',
  price:0.0,
  sellPrice:0.0,
  vipPrice:0.0,
  itemDesc:'',
  itemDate:'',
  endDate: '',
  imgs:'',
  imgArr:[]


})

var url=""

function openItemDialog() {
    url="http://localhost:8081/addItem"
    dialogItemVisible.value = true
    loadSupplyList()
    loadPlaceList()
    loadStoreList()
    loadBrandList()
    loadUnitList()
    getItemCode()

    itemForm.id=0
    itemForm.itemNum=''
    itemForm.itemName=''
    itemForm.typeId=''
    itemForm.supplyId=''
    itemForm.placeId=''
    itemForm.storeId=''
    itemForm.brandId=''
    itemForm.unitId=''
    itemForm.price=0.0
    itemForm.sellPrice=0.0
    itemForm.vipPrice=0.0
    itemForm.store=''
    itemForm.itemDate=''
    itemForm.itemDesc=''

}

const dialogImageUrl = ref(null)
const dialogVisible = ref(false)

function handlePictureCardPreview(file){
  console.log("============"+file)
  dialogImageUrl.value = file.url
  dialogVisible.value = true
}

function handlAvatarSuccess(path){
  console.log(path)
  itemForm.imgArr=[]
  itemForm.imgArr.push(path)

  //将原来回显的图片路径清空
  imageUrls.value=null
}

const supplyList=ref([])
const placeList=ref([])
const storeList=ref([])
const brandList=ref([])
const unitList=ref([])

function loadSupplyList(){
  axios.get("http://localhost:8081/supplyList")
  .then((response)=>{
    supplyList.value=response.data
  })
  .catch((error)=>{
    console.log(error)
  })
}

function loadPlaceList(){
  axios.get("http://localhost:8081/placeList")
  .then((response)=>{
    placeList.value=response.data
  })
  .catch((error)=>{
    console.log(error)
  })
}


function loadStoreList(){
  axios.get("http://localhost:8081/storeList")
  .then((response)=>{
    storeList.value=response.data
  })
  .catch((error)=>{
    console.log(error)
  })
}

function loadBrandList(){
  axios.get("http://localhost:8081/brandList")
  .then((response)=>{
    brandList.value=response.data
  })
  .catch((error)=>{
    console.log(error)
  })
}

function loadUnitList(){
  axios.get("http://localhost:8081/unitList")
  .then((response)=>{
    unitList.value=response.data
  })
  .catch((error)=>{
    console.log(error)
  })
}

const typeDialog = ref(false)
function openTypeDialog() {
  typeDialog.value = true;
  itemCategoryTree()
}

const  config={
  id:'id',
  label:'label',
  children:'children'
}

const treeRef = ref(null)
const categoryList = ref([])

function itemCategoryTree() {
  axios.get("http://localhost:8081/categoryList")
  .then((response)=>{
    categoryList.value=response.data
  })
  .catch((error)=>{
    console.log(error)
  })
}


var pnode=null

function handleClickNode(node,data){
  pnode=node
}

function saveType(){
  if(!(pnode && pnode.children.length==0)){
    ElMessage("必选选中叶子节点")
    return
  }
  typeDialog.value=false
  itemForm.typeId=pnode.id
}

function getItemCode(){
  axios.get("http://localhost:8081/getCode")
      .then((response)=>{
        itemForm.itemNum=response.data;
      })
      .catch((error)=>{
        console.log(error);
      })
}


function saveItemForm(){
  //处理表单中的数组,将数组转化为字符，赋值给imgs
  itemForm.imgs=itemForm.imgArr.toString()
  console.log(itemForm)
  axios.post(url,itemForm)
  .then((response)=>{
    if(response.data.code==200){
      dialogItemVisible.value=false
      queryItemList(1)
    }
    ElMessage(response.data.msg)
  })
  .catch((error)=>{
    console.log(error)
  })
}


const parentBorder = ref(true)
const childBorder = ref(false)

const itemList = ref([])
const total = ref(0)

const condForm=reactive({
  itemNum:'',
  itemName:'',
  statue:''

})

function queryItemList(pageNum){
  condForm.pageNum=pageNum
  axios.post("http://localhost:8081/itemList",condForm)
  .then((response)=>{
    itemList.value=response.data.items
    total.value=response.data.total
  })
  .catch((error)=>{
    console.log(error)
  })
}

function doImgs(path){
  return (path+"").split(",")[0]
}

function toStatue(statue){
  switch(statue){
    case 0: 
      return "上架"
    case 1: 
      return "下架"
    case 2: 
      return "删除"
  }
}

function handlerPageChange(pageNum){
  queryItemList(pageNum)
}

onMounted(function(){
  queryItemList(1)
})

function queryCondItem(){
  queryItemList(1)
}

function upItem(id){
  axios.get("http://localhost:8081/upItem/"+id)
  .then((response)=>{
    if(response.data.code==200){
    queryItemList(1)
    }
    ElMessage(response.data.msg)
  })
  .catch((error)=>{
    console.log(error)
  })
}

function downItem(id){
  axios.get("http://localhost:8081/downItem/"+id)
  .then((response)=>{
    if(response.data.code==200){
    queryItemList(1)
  }
    ElMessage(response.data.msg)
  })
  .catch((error)=>{
    console.log(error)
  })
}

function delItem(id){
  axios.get("http://localhost:8081/delItem/"+id)
  .then((response)=>{
    if(response.data.code==200){
    queryItemList(1)
  }
    ElMessage(response.data.msg)
  })
  .catch((error)=>{
    console.log(error)
  })
}

const imageUrls=ref([])


function editItem(row){
  url="http://localhost:8081/updateItem"


  dialogItemVisible.value=true
  loadSupplyList()
  loadPlaceList()
  loadStoreList()
  loadBrandList()
  loadUnitList()
  getItemCode()
  //imgUrl.value=row.imgs.split(",") [0]

  imageUrls.value=row.imgs.split(",")
  itemForm.imgArr=row.imgs.split(",") 
  //清空表单
  itemForm.id=row.id
  itemForm.itemNum=row.itemNum
  itemForm.itemName=row.itemName
  itemForm.typeId=row.typeId
  itemForm.supplyId=row.supplyId
  itemForm.placeId=row.placeId
  itemForm.storeId=row.storeId
  itemForm.brandId=row.brandId
  itemForm.unitId=row.unitId
  itemForm.price=row.price
  itemForm.sellPrice=row.sellPrice
  itemForm.vipPrice=row.vipPrice
  itemForm.store=row.store
  itemForm.itemDate=row.itemDate
  itemForm.itemDesc=row.itemDesc


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
  phone:''
})

const buyDialog=ref(false)

function openBuyDialog(id){
  buyDialog.value=true
  axios.get("http://localhost:8081/buyAutoInfo/"+id)
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
  })
  .catch((error)=>{
    console.log(error);
  })
}

function saveBuyOrder(){
  axios.post("http://localhost:8081/saveBuyList",buyForm)
  .then((response)=>{
    if(response.data.code==200){
      buyDialog.value=false
      buyForm.productId=''
      buyForm.itemName=''
      buyForm.storeId=''
      buyForm.storeName=''
      buyForm.supplyId=''
      buyForm.supplyName=''
      buyForm.placeId=''
      buyForm.placeName=''
      buyForm.buyUser=''
      buyForm.phone=''
      buyForm.buyNum=''
    }
    ElMessage(response.data.msg)

  })
  .catch((error)=>{
    console.log(error);
  })
}

const itemOutDialog=ref(false)
const outForm=reactive({
  itemName:'',
  storeName:'',
  store:0,
  outNum:0
})

function openCustDialog(row){
  itemOutDialog.value=true
  outForm.itemName=row.itemName
  outForm.store=row.store
  outForm.storeName=row.storeName
  outForm.productId=row.id
}

function saveOutOrder(){
  axios.post("http://localhost:8081/doItemOutStore",outForm)
  .then((response)=>{
    if(response.data.code==200){
      itemOutDialog.value=false
    }
    ElMessage(response.data.msg)

  })
  .catch((error)=>{
    console.log(error);
  })
}

</script>

<style>
.custom-dialog {
    max-width: 900px; /* 适当调小宽度，可根据实际需求微调 */
    width: 90%; /* 让其相对父容器有合适比例宽度 */
    height: 80vh; /* 设置合适的高度，可以根据页面整体视觉效果调整 */
    overflow-y: auto; /* 当内容超出高度时出现垂直滚动条 */
    overflow-x: hidden; /* 隐藏水平滚动条，保持界面整洁 */
}
</style>