<template>
  <h2>投诉统计</h2>
  <el-button type="success" @click="questionType">投诉类型统计</el-button>
  <el-button type="success" @click="questionState">回复状态统计</el-button>
  <hr/>
  <div id="questionInfo" style="width: 100%;height: 450px;"></div>
</template>

<script setup>
import axios from 'axios';
import * as echarts from 'echarts'
import { onMounted } from 'vue';


function questionType(){
    axios.get("http://localhost:8081/countQuestionType")
    .then((response)=>{
    var questionDao=document.getElementById("questionInfo")
    var typeECharts=echarts.init(questionDao)
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
                name: '投诉问题统计',
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
    option && typeECharts.setOption(option)
    })
    .catch((error)=>{
        console.log(error)
    })
}

onMounted(function(){
    questionType()
})

function questionState(){
    axios.get("http://localhost:8081/countQuestionState")
    .then((response)=>{
    var questionDao=document.getElementById("questionInfo")
    var stateECharts=echarts.init(questionDao)
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
                name: '回复状态统计',
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
    option && stateECharts.setOption(option)
    })
    .catch((error)=>{
        console.log(error)
    })
}

</script>

<style>

</style>