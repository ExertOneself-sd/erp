import { createRouter, createWebHashHistory } from 'vue-router'
import index from '../views/index.vue'
import login from '../views/login.vue'

const routes = [
  {
    path: '/',
    name: 'login',
    component: login
  },
  {
    path: '/index',
    name: 'index',
    component: index
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
