<template>
  <h2>员工信息统计</h2>
  <el-button type="success" @click="countEmpAge">年龄段统计</el-button>
  <el-button type="success" @click="countEmpEdu">学历分布统计</el-button>
  <hr/>
  <div id="empInfo" style="width: 100%;height: 450px;"></div>
</template>

<script setup>
// import axios from 'axios'
import { getCurrentInstance } from "vue";
const axios=getCurrentInstance().proxy.axios
import * as echarts from 'echarts'
import { onMounted } from 'vue'

function countEmpAge(){
    axios.get("http://localhost:8081/countEmpAge")
    .then((response)=>{
    var empDao=document.getElementById("empInfo")
    var ageECharts=echarts.init(empDao)
    var option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            top: '5%',
            left: 'center'
        },
        series: [
            {
                name: '员工年龄分布',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                padAngle: 5,
                itemStyle: {
                    borderRadius: 10
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: 40,
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: response.data

            }
        ]
    }
    option && ageECharts.setOption(option)
    })
    .catch((error)=>{
        console.log(error)
    })
   
}


onMounted(function(){
    countEmpAge()
})


function countEmpEdu(){
    axios.get("http://localhost:8081/countEmpEdu")
    .then((response)=>{
    var empDao=document.getElementById("empInfo")
    var ageECharts=echarts.init(empDao)
    var option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            top: '5%',
            left: 'center'
        },
        series: [
            {
                name: '员工学历分布',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                padAngle: 5,
                itemStyle: {
                    borderRadius: 10
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: 40,
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: response.data

            }
        ]
    }
    option && ageECharts.setOption(option)
    })
    .catch((error)=>{
        console.log(error)
    })
   
}

</script>

<style>

</style>