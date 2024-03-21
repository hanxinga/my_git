<template>

  <el-input
      v-model="input"
      style="width: 240px"
      placeholder="请输入药品编号"
      clearable
  />
  <!--  搜索药品按钮-->
  <el-button :icon="Search" circle @click="searchDrug()"/>

  <!--  购买记录按钮-->
  <el-button type="primary" :icon="Tickets" circle @click="purchaseRecordVisible = true"/>
  <!--  购物车按钮-->
  <el-button type="warning" :icon="ShoppingCart" circle @click="shoppingCartVisible = true,showShoppingCart()"/>

  <el-table :data="tableData" style="width: 100%" height="250">
    <el-table-column prop="id" label="ID"/>
    <el-table-column prop="name" label="药品名称"/>
    <el-table-column prop="price" label="药品价格"/>

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
            plain
            type="warning"
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
      <el-table-column property="name" label="药品名称" width="150"/>
      <el-table-column property="price" label="价格" width="200"/>
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
import {ShoppingCart, Tickets, Search} from '@element-plus/icons-vue'
import axios from "axios";
import {ref} from 'vue'
import {ElMessage} from 'element-plus'

const tableData = ref([])
const shoppingCartVisible = ref(false)
const purchaseRecordVisible = ref(false)
const shoppingCartData = ref([])
const input = ref()

function show() {
  axios.get('/api/drug').then((require) => {
    tableData.value = require.data
    console.log(require.data)
  })
}

show()

// 购买药品请求
function purchase(row) {
  console.log(row.id)

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

// 移除购物车数据
function removePurchase(row) {
  axios.get('/api/drug/removePurchase/' + row.id).then(() => {
    showShoppingCart()
  })
}

//搜索药品
function searchDrug() {
  console.log(input.value)
  if (input.value === undefined || input.value === "" ) {

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

</style>
