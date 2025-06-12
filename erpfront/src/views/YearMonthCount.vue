<template>
  <h2>年销售统计</h2>
  <el-form :model="yearForm" label-width="100px" >
        <el-form-item label="年份" >
          <el-select v-model="yearForm.year" placeholder="请选择年份" @change="handleYearMonthSell" clearable>
            <el-option
            v-for="item in yearList"
            :key="item.year"
            :label="item.label"
            :value="item.year"
            />
          </el-select>
        </el-form-item>
  </el-form>
  <div id="sellCharts" style="width: 100%;height: 400px;"></div>
</template>

<script setup>
// import axios from 'axios'
import { getCurrentInstance } from "vue";
const axios=getCurrentInstance().proxy.axios
import { onMounted, reactive, ref } from 'vue'
import * as echarts from 'echarts';


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

function handleYearMonthSell(year){
    console.log(year)
    axios.get("http://localhost:8081/countSell?year="+year)
    .then((response)=>{
        var sellDom = document.getElementById('sellCharts');
        var sellChart = echarts.init(sellDom);
        var option= {
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
                    type: 'bar',
                    showBackground: true,
                    backgroundStyle: {
                        color: 'rgba(180, 180, 180, 0.2)'
                    }
                }
            ]
        }
    option && sellChart.setOption(option);
    })
    .catch((error)=>{
        console.log(error)
    })
}

</script>

<style>

</style>