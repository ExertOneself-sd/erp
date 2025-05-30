<template>
  <!-- 后台主页布局 -->
  <div class="common-layout">
    <el-container>
      <el-header class="top">ERP系统</el-header>
      <el-container   >
        <el-aside width="240px" class="left">
          系统菜单

          <!--添加Menu菜单组件-->
          <el-menu
              class="el-menu-vertical-demo" @select="handlerSelect" >
            <el-sub-menu v-for="menu in menus" :index="String(menu.id)">
              <template #title>
                <span>{{ menu.label }}</span>
              </template>
              <el-menu-item v-for="subMenu in menu.subMenus"  :index="String(subMenu.id)">
                {{subMenu.label}}
              </el-menu-item>

            </el-sub-menu>

          </el-menu>
        </el-aside>
        <el-main class="right">
          <transition name="fade" mode="out-in">
            <el-card v-loading="loading" class="content-card">
              <component :is="currentComponent"></component>
            </el-card>
          </transition>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<script setup>
  import AddCustomer from "@/views/AddCustomer";
  import ListCustomer from "@/views/ListCustomer";
  import AddSellJh from "@/views/AddSellJh";
  import ListSellJh from "@/views/ListSellJh";
  import ListCustOrder from "@/views/ListCustOrder.vue";
  import ListAfter from "@/views/ListAfter.vue";
  import AddMenus from "@/views/AddMenus.vue";
  import RoleManager from "@/views/RoleManager.vue";
  import UserManager from "@/views/UserManager.vue";
  import Category from "@/views/Category.vue";
  import ItemManager from "@/views/ItemManager.vue";
  import BuyListManager from "@/views/BuyListManager.vue";
  import {onMounted, ref} from "vue";
  import axios from 'axios';
  import { ElMessage, ElLoading } from 'element-plus';


  // 声明数组保存所有组件
  const views = [AddCustomer, ListCustomer,ListAfter,ListCustOrder, AddSellJh,,, ListSellJh, AddMenus,UserManager,RoleManager,,Category,ItemManager,BuyListManager];
  // 声明变量保存当前需要显示的组件名
  const currentComponent = ref(views[0]);
  // 声明菜单对象集合数据
  const menus = ref([]);
  // 加载状态
  const loading = ref(false);

  /* 菜单选中处理函数 */
  const handlerSelect = async (id) => {
    loading.value = true;
    try {
      const { data } = await axios.get("http://localhost:8081/compIndex?id=" + id);
      currentComponent.value = views[data];
    } catch (error) {
      ElMessage.error('加载组件失败: ' + error.message);
      console.error('加载组件失败:', error);
    } finally {
      loading.value = false;
    }
  };

  // 页面加载时获取菜单数据
  onMounted(async () => {
    const loadingInstance = ElLoading.service({
      lock: true,
      text: '加载菜单中...',
      background: 'rgba(0, 0, 0, 0.7)'
    });
    
    try {
    const { data } = await axios.get("http://localhost:8081/listMenus");
    if (!Array.isArray(data)) { // 验证数据结构
      throw new Error("菜单数据格式错误");
    }
    menus.value = data;
  } catch (error) {
    ElMessage.error('加载菜单失败: ' + error.message);
  } finally {
    loadingInstance.close();
  }
  });
</script>

<style scoped>
  .top {
    background-color: #0b2e52;
    color: white;
    height: 60px;
    line-height: 60px;
    font-size: 20px;
    font-weight: bold;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12);
    text-align: center;
  }
  
  .left {
    background-color: #fff;
    height: calc(100vh - 60px);
    border-right: 1px solid #e6e6e6;
    box-shadow: 2px 0 4px rgba(0, 0, 0, 0.05);
  }
  
  .right {
    background-color: #ffff;
    padding: 20px;
    min-height: calc(100vh - 60px);
  }
  
  .content-card {
    min-height: calc(100vh - 100px);
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  
  .el-menu {
    border-right: none;
  }
  
  .el-menu-item.is-active {
    color: #1577d3;
    background-color: #ecf5ff;
  }

  /* 组件切换动画 */
  .fade-enter-active,
  .fade-leave-active {
    transition: all 0.3s cubic-bezier(0.55, 0, 0.1, 1);
  }
  
  .fade-enter-from {
    opacity: 0;
    transform: translateX(30px);
  }
  
  .fade-leave-to {
    opacity: 0;
    transform: translateX(-30px);
  }

/* 响应式设计 */
@media (max-width: 768px) {
  .el-aside {
    width: 180px !important;
  }
  
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 180px;
  }
  
  .top {
    font-size: 16px;
    padding: 0 10px;
  }
  
  .right {
    padding: 10px;
  }
}

</style>