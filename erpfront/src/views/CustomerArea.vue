<template>
  <h2>客户地区分布</h2>
  <div id="main" style="width: 100%;height: 400px;"></div>
</template>

<script setup>
import axios from 'axios'
import * as echarts from 'echarts'
import { onMounted } from 'vue'

function countCustomerArea(){
    axios.get("http://localhost:8081/countCust")
    .then((response)=>{
    var custDom = document.getElementById('main')
    var custChart = echarts.init(custDom)
    var option = {
        title: {
            text: '客户统计',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        toolbox: {
            show: true,
            feature: {
                mark: { show: true },
                dataView: { show: true, readOnly: false },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        },
        series: [
            {
                name: '客户分布',
                type: 'pie',
                radius: [20, 140],
                center: ['50%', '50%'],
                roseType: 'area',
                itemStyle: {
                    borderRadius: 5
                },
                data:response.data
            }
        ]
    }
    option && custChart.setOption(option)
    })
    .catch((error)=>{
        console.log(error)
    })


}


onMounted(function(){
    countCustomerArea()
})

</script>

<style>

#main {
  margin: 0 auto;
}

</style>