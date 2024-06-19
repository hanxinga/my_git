import {createRouter, createWebHistory} from 'vue-router'
import LoginView from '../components/LoginView.vue';
import indexDrug from "@/components/indexDrug.vue";

const routes = [ // 这里定义了routes，ESLint提示这个变量没有被使用
  {
    path: '/login',
    name: 'login',
    component: LoginView,
  },
  // 其他路由定义
  {
    path: '/',
    name:'index',
    component: indexDrug,
  }
];

// 创建router实例时确保使用了`routes`配置
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes, // 这里使用了`routes`变量
});

export default router;
