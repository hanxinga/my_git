<template>
  <div>
    <h1>用户登录</h1>
    <form @submit.prevent="submitForm">
      用户名：<input type="text" v-model="username" /><br />
      密&nbsp;&nbsp;&nbsp;码：<input type="password" v-model="password" /><br />
      验证码：<input type="text" v-model="code" /><img :src="validCodeUrl" width="100" height="40" @click="refreshCode" /><br />
      记住我：<input type="checkbox" v-model="rememberMe" /><br />
      <input type="submit" value="登录" /><br />
      <span style="color:red">{{ msg }}</span><br />
    </form>
  </div>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios'; // 确保已经安装并导入axios
import apiClient from '@/axios'; // 假设api.js位于src目录

// 使用apiClient发起请求
apiClient.get('/some-endpoint')
    .then(response => {
      // 处理响应
    })
    .catch(error => {
      // 处理错误
    });
export default {
  setup() {
    const username = ref('');
    const password = ref('');
    const code = ref('');
    const rememberMe = ref(false);
    const validCodeUrl = ref('api/validcode');
    const msg = ref('');

    const submitForm = async () => {
      try {
        // If you don't need to use the response, you can omit the variable assignment
        await axios.post('api/login', {
          username: username.value,
          password: password.value,
          code: code.value,
          rememberMe: rememberMe.value
        });
        // Handle successful login here, like redirecting the user
        // For example:
        // this.$router.push('/dashboard');
      } catch (error) {
        msg.value = '登录失败'; // Handle error
      }
    };

    const refreshCode = () => {
      validCodeUrl.value = 'api/validcode?' + new Date().getTime(); // 刷新验证码
    };

    return {
      username,
      password,
      code,
      rememberMe,
      validCodeUrl,
      msg,
      submitForm,
      refreshCode
    };
  }
};
</script>
