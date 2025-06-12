<template>
  <div class="login-container">
    <div class="background-gradient"></div>
    <div class="login-content">
      <el-card class="login-card" shadow="hover">
        <div class="system-title">
          <div class="logo-container">
            <div class="logo-circle">
              <svg viewBox="0 0 24 24" class="logo-icon">
                <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5"></path>
              </svg>
            </div>
          </div>
          <div class="title-text">ERP系统</div>
          <div class="subtitle">企业资源管理系统</div>
        </div>

        <el-form
          ref="loginForm"
          :model="loginUser"
          size="large"
          label-position="top"
          style="width: 100%; margin-top: 15px"
        >
          <el-form-item label="账号" class="form-item">
            <el-input 
              v-model="loginUser.uname" 
              placeholder="请输入账号"
              prefix-icon="User"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" class="form-item">
            <el-input 
              type="password" 
              v-model="loginUser.upwd" 
              show-password 
              placeholder="请输入密码"
              prefix-icon="Lock"
            ></el-input>
          </el-form-item>

          <div class="remember-forgot">
            <el-checkbox v-model="rememberMe">记住我</el-checkbox>
            <el-link type="primary" :underline="false">忘记密码?</el-link>
          </div>
        </el-form>

        <div class="login-footer">
          <el-button
            @click="userLogin"
            type="primary"
            size="large"
            class="login-btn"
            :loading="loading"
          >登录</el-button>
          <!-- <div class="divider">或</div>
          <div class="social-login">
            <el-button circle><el-icon><Iphone /></el-icon></el-button>
            <el-button circle><el-icon><Wechat /></el-icon></el-button>
            <el-button circle><el-icon><Avatar /></el-icon></el-button>
          </div> -->
          <div class="register-link">
            还没有账号? <el-link type="primary" :underline="false">立即注册</el-link>
          </div>
        </div>
      </el-card>
      
      <div class="copyright">
        © 2023 企业ERP系统 | 科技驱动业务创新
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router"
import { ElMessage } from "element-plus";
import { getCurrentInstance } from "vue";
import { User, Lock, Iphone, Wechat, Avatar } from '@element-plus/icons-vue'

const axios = getCurrentInstance().proxy.axios
const router = useRouter()
const loginForm = ref()
const loading = ref(false)
const rememberMe = ref(false)

const loginUser = reactive({
  uname: '',
  upwd: ''
})

function userLogin() {
  axios.post("http://localhost:8081/userLogin", loginUser)
    .then((response) => {
      if (response.data.code == 200) {
        ElMessage.success('登录成功')
        // 进行路由跳转
        setTimeout(() => {
          router.push({ name: 'index' })
        }, 800)
      } else {
        ElMessage.error(response.data.msg)
      }
    })
    .catch((error) => {
      console.error(error)
    })
}
</script>

<style scoped>
/* 全局盒模型设置，确保内边距和边框不增加元素实际尺寸 */
* {
  box-sizing: border-box;
}

.login-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  position: relative;
  overflow: hidden;
  padding: 15px;
  margin: 0;
}

.background-gradient {
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, #e3eeff 0%, #f3f6ff 70%, transparent 100%);
  z-index: 0;
  opacity: 0.7;
}

.login-content {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 420px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-card {
  border-radius: 16px;
  overflow: hidden;
  border: none;
  padding: 28px 22px;
  box-shadow: 0 10px 25px rgba(64, 158, 255, 0.15);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  width: 100%;
  margin: 0;
}

.login-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 30px rgba(64, 158, 255, 0.25);
}

.system-title {
  text-align: center;
  margin-bottom: 18px;
}

.logo-container {
  display: flex;
  justify-content: center;
  margin-bottom: 12px;
}

.logo-circle {
  width: 75px;
  height: 75px;
  border-radius: 50%;
  background: linear-gradient(135deg, #409eff 0%, #79bbff 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 3px 10px rgba(64, 158, 255, 0.3);
}

.logo-icon {
  width: 44px;
  height: 44px;
  fill: white;
  stroke: white;
  stroke-width: 1.5;
}

.title-text {
  color: #303133;
  font-size: 26px;
  font-weight: 700;
  letter-spacing: 0.8px;
  margin-bottom: 4px;
  background: linear-gradient(135deg, #409eff, #79bbff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.subtitle {
  color: #909399;
  font-size: 13px;
  letter-spacing: 1.5px;
  margin-top: 3px;
}

.form-item {
  margin-bottom: 18px;
}

:deep(.el-form-item__label) {
  font-weight: 500;
  color: #606266;
  margin-bottom: 6px;
  font-size: 14px;
}

:deep(.el-input__wrapper) {
  border-radius: 8px;
  padding: 0 12px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  height: 42px;
}

:deep(.el-input__wrapper:hover),
:deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 2px 6px rgba(64, 158, 255, 0.25);
}

:deep(.el-input__inner) {
  height: 40px;
}

.remember-forgot {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 3px 0 12px;
  font-size: 13px;
}

.login-footer {
  margin-top: 12px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-btn {
  width: 100%;
  border-radius: 8px;
  height: 44px;
  font-size: 15px;
  font-weight: 600;
  letter-spacing: 1.5px;
  background: linear-gradient(135deg, #409eff, #79bbff);
  border: none;
  box-shadow: 0 3px 10px rgba(64, 158, 255, 0.3);
  transition: all 0.3s ease;
  margin-bottom: 12px;
  padding: 0;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
}

.divider {
  display: flex;
  align-items: center;
  color: #909399;
  margin: 12px 0;
  text-align: center;
  width: 100%;
  font-size: 14px;
}

.divider::before,
.divider::after {
  content: "";
  flex: 1;
  border-bottom: 1px solid #dcdfe6;
}

.divider::before {
  margin-right: 12px;
}

.divider::after {
  margin-left: 12px;
}

.social-login {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin-bottom: 12px;
  width: 100%;
}

:deep(.social-login .el-button) {
  width: 40px;
  height: 40px;
  font-size: 16px;
  background-color: #f5f7fa;
  border: 1px solid #e4e7ed;
  transition: all 0.3s ease;
}

:deep(.social-login .el-button:hover) {
  transform: translateY(-3px);
  background-color: #ecf5ff;
  border-color: #409eff;
  color: #409eff;
}

.register-link {
  text-align: center;
  color: #909399;
  font-size: 13px;
  margin-top: 5px;
  width: 100%;
}

.copyright {
  text-align: center;
  color: #909399;
  font-size: 12px;
  margin-top: 15px;
  letter-spacing: 0.5px;
  width: 100%;
}

@media (max-width: 400px) {
  .login-card {
    padding: 22px 18px;
  }
  
  .title-text {
    font-size: 24px;
  }
  
  .logo-circle {
    width: 70px;
    height: 70px;
  }
  
  .logo-icon {
    width: 40px;
    height: 40px;
  }
  
  .form-item {
    margin-bottom: 15px;
  }
  
  .login-btn {
    height: 40px;
    font-size: 14px;
  }
  
  :deep(.el-input__wrapper) {
    height: 38px;
  }
  
  :deep(.el-input__inner) {
    height: 36px;
  }
}
</style>