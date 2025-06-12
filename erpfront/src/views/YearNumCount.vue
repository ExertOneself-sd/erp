<template>
  <h2>年销售量统计</h2>
  <el-form :model="yearForm" label-width="100px" >
        <el-form-item label="年份" >
          <el-select v-model="yearForm.year" placeholder="请选择年份" @change="handleYearNum" clearable>
            <el-option
            v-for="item in yearList"
            :key="item.year"
            :label="item.label"
            :value="item.year"
            />
          </el-select>
        </el-form-item>
  </el-form>
  <div id="numInfo" style="width: 100%;height: 450px;"></div>
</template>

<script setup>
<<<<<<< HEAD
// import axios from 'axios';
import { getCurrentInstance } from "vue";
const axios=getCurrentInstance().proxy.axios
=======
import axios from 'axios';
>>>>>>> 9b36866f743edc05e0f02f00103b929e0d29fc81
import * as echarts from 'echarts';
import { onMounted,reactive,ref } from 'vue';


const yearForm=reactive({
    year:''
})
const yearList=ref([])

function loadYear(){
    axios.get("http://localhost:8081/queryYear")
    .then((response)=>{
        yearList.value=response.data
    })
    .catch((error)=>{
        console.log(error)
    })
}

onMounted(function(){
    loadYear()
})

function handleYearNum(year){
    axios.get("http://localhost:8081/countNum?year="+year)
    .then((response)=>{
    var numDom = document.getElementById('numInfo')
    var numeChart=echarts.init(numDom)
    var option = {
        xAxis: {
            type: 'category',
            data: response.data.xdata
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: response.data.ydata,
                type: 'line'
            }
        ]
    }
    option && numeChart.setOption(option)
    })
    .catch((error)=>{
        console.log(error)
    })
}


</script>

<style>

</style>