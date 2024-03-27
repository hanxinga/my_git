import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/indexDrug.vue';
import About from '../components/shoppingCar.vue';

const routes = [
  { path: '/', component: Home },
  { path: '/shoppingCar', component: About }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
