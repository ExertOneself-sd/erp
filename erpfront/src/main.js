import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import axios from './utils/axios.js'

// 创建应用实例
const app = createApp(App)

// 挂载 axios 到全局
app.config.globalProperties.axios = axios

// 安装插件并挂载应用
app.use(ElementPlus).use(router).mount('#app')