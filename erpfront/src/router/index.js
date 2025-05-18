import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import index from '../views/index.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
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
