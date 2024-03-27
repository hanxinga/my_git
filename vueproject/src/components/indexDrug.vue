<template style="background-color: wheat">
  <el-breadcrumb separator="/">
    <el-breadcrumb-item :to="{ path: '/' }">药品详情</el-breadcrumb-item>
    <el-breadcrumb-item><router-link to="/shoppingCar">购物车</router-link></el-breadcrumb-item>
  </el-breadcrumb>
  <el-divider border-style:solid style="border-color: black"/>
  <el-form :model="input" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px" style="margin-top: 30px">

    <el-form-item label="药品编号" prop="orderNumber" style="margin-left: 500px">
      <el-input
          v-model="input"
          placeholder="请输入药品编号"
          clearable
          @keyup.enter="searchDrug()"
      />
    </el-form-item>
    <el-form-item style="align-items: center">
      <el-button type="primary" @click="searchDrug"> 搜索</el-button>
      <el-button type="primary" @click="resetQuery"> 重置</el-button>
      <!--  购买记录按钮-->
      <el-button type="primary" :icon="Tickets" circle @click="purchaseRecordVisible = true,fetchPurchaseRecord()"/>
      <!--  购物车按钮-->
      <el-button type="warning" :icon="ShoppingCart" circle @click="shoppingCartVisible = true,showShoppingCart()"/>
    </el-form-item>
  </el-form>

  <!--    <el-button :icon="Search" circle @click="searchDrug()"/>-->

  <!--    购买记录按钮-->
  <!--    <el-button type="primary" :icon="Tickets" circle @click="purchaseRecordVisible = true"/>-->
  <!--    &lt;!&ndash;  购物车按钮&ndash;&gt;-->
  <!--    <el-button type="warning" :icon="ShoppingCart" circle @click="shoppingCartVisible = true,showShoppingCart()"/>-->

  <el-table :data="tableData" style=  "width: 90%;border-radius:20px; background-color:wheat; margin: auto" height="280"  stripe border  row-class-name="my-row-class">
    <el-table-column prop="id" label="ID" align="center"/>
    <el-table-column prop="name" label="药品名称" align="center"/>
    <el-table-column prop="price" label="药品价格" align="center"/>

    <el-table-column fixed="right" label="操作" align="center">
      <template #default="scope">
        <el-button
            type="success"
            round
            size="small"
            @click="purchase(scope.row)"
        >
          购买
        </el-button>
        <el-button
            plain
            type="danger"
            round
            size="small"
            @click="addShoppingCart(scope.row)"
        >
          加入购物车
        </el-button>
      </template>

    </el-table-column>

  </el-table>

  <!--  购买记录界面-->
  <el-dialog v-model="purchaseRecordVisible" title="购买记录" width="800">
    <el-table :data="purchaseData">
      <el-table-column property="id" label="药品id" width="150"/>
      <el-table-column property="name" label="药品名称" width="150"/>
      <el-table-column property="price" label="价格" width="200"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button
              type="danger"
              size="small"
              @click="RemovePurchase(scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
  <!--购物车界面-->
  <el-dialog v-model="shoppingCartVisible" title="购物车" width="800">
    <el-table :data="shoppingCartData">
      <el-table-column property="name" label="药品名称" width="150"/>
      <el-table-column property="price" label="价格" width="200"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button
              type="success"
              size="small"
              @click="purchase(scope.row)"
          >
            购买
          </el-button>
          <el-button
              type="danger"
              size="small"
              @click="removePurchase(scope.row)"
          >
            删除
          </el-button>

        </template>

      </el-table-column>
    </el-table>
  </el-dialog>

</template>

<script setup>
import {ShoppingCart, Tickets} from '@element-plus/icons-vue'
import axios from "axios";
import {ElMessage} from 'element-plus'

import {ref} from 'vue'


const tableData = ref([])
const shoppingCartVisible = ref(false)
const purchaseRecordVisible = ref(false)
const shoppingCartData = ref([])
const input = ref()
const showSearch = ref(true);
const purchaseData = ref([])

function show() {
  axios.get('/api/drug').then((require) => {
    tableData.value = require.data
    console.log(require.data)
  })
}

show()

// 重置按钮
function resetQuery() {
  show();
}

// 购买药品请求
function purchase(row) {
  console.log(row.id)
  axios.get('/api/drug/checkPurchaseRecord/' + row.id).then((require) => {
    if (require.data === 0) {
      axios.get(`/api/drug/purchase/${row.id}`).then(() => {
        // 弹出购买成功提示
        ElMessage({
          message: '购买成功！',
          type: 'success',
        })
      })
    }
  })
  // axios.get(`/api/drug/purchase/${row.id}`).then(() => {
  //   // 弹出购买成功提示
  //   ElMessage({
  //     message: '购买成功！',
  //     type: 'success',
  //   })
  // })
}

// 加入购物车请求
function addShoppingCart(row) {
  axios.get('/api/drug/checkShoppingCart/' + row.id).then((require) => {
    if (require.data === 0) {
      axios.get('/api/drug/purchase/' + row.id).then(() => {
        ElMessage({
          message: '添加购物车成功！',
          type: 'success',
        })
      })
    } else {
      ElMessage({
        message: '已经添加到购物车！',
        type: 'warning',
      })
    }

  })


}

// 购物车数据请求
function showShoppingCart() {
  axios.get('/api/drug/shoppingCart').then((require) => {
    shoppingCartData.value = require.data
  })
}

function fetchPurchaseRecord() {
  axios.get('/api/drug/purchaseRecord').then((require) => {
    purchaseData.value = require.data
  })
}

// 移除购物车数据
function removePurchase(row) {
  axios.get('/api/drug/removePurchase/' + row.id).then(() => {
    showShoppingCart()
  })
}

function RemovePurchase(row) {
  axios.get('/api/drug/removePurchase/' + row.id).then(() => {
    fetchPurchaseRecord()
  })
}

//搜索药品
function searchDrug() {
  console.log(input.value)
  if (input.value === undefined || input.value === "") {

    ElMessage({
      message: '请输入编号！',
      type: 'danger',

    })
    show()
  } else {

    axios.get('/api/drug/' + input.value).then((require) => {
      console.log(require.data)
      tableData.value = [require.data]
    })
  }


}


</script>

<style lang="scss">
.my-row-class{
  background-color: wheat;
}
</style>
