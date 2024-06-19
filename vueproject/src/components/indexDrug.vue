<template style="background-color: wheat">

  <el-breadcrumb separator="/">
    <el-breadcrumb-item :to="{ path: '/' }">药品详情</el-breadcrumb-item>
    <el-breadcrumb-item><router-link to="/shoppingCar">购物车</router-link></el-breadcrumb-item>
  </el-breadcrumb>
  <span>余额：{{ balance }} 元</span>
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
      <el-button type="primary" :icon="Tickets" circle @click="purchaseRecordVisible = true,showOrderData()"/>
      <!--  购物车按钮-->
      <el-button type="warning" :icon="ShoppingCart" circle @click="shoppingCartVisible = true,showShoppingCart()"/>
    </el-form-item>
  </el-form>

  <!--主页表格-->
  <el-table :data="tableData" style=  "width: 90%;border-radius:20px; background-color:wheat; margin: auto" height="400"  stripe border  row-class-name="my-row-class">
    <el-table-column label="图片" align="center">
      <img v-if="uploadedImageUrl" :src="uploadedImageUrl" alt="Uploaded Image" />
    </el-table-column>
    <el-table-column prop="id" label="ID" align="center"/>
    <el-table-column prop="name" label="药品名称" align="center"/>
    <el-table-column prop="price" label="药品价格" align="center"/>
    <el-table-column fixed="right" label="操作" align="center">
      <template #default="scope">

        <el-button
            plain
            type="warning"
            size="small"
            @click="addShoppingCart(scope.row)"
        >
          加入购物车
        </el-button>
        <el-upload
            action="api/drug/upload"
            :on-success="handleSuccess"
            :before-upload="beforeUpload"
        >
          <el-button >选择文件</el-button>
          <el-button type="primary" @click="submitUpload">上传</el-button>
        </el-upload>
      </template>
    </el-table-column>

  </el-table>

  <!--  购买记录界面-->
  <el-dialog v-model="purchaseRecordVisible" title="购买记录" width="800">
    <el-table :data="purchaseData">
      <el-table-column property="id" label="订单号" width="120"/>
      <el-table-column property="name" label="药品名称" width="350"/>
      <el-table-column property="finalPrice" label="总价" width="100"/>
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
      <el-table-column fixed="right">

        <template #default="{ row }">

          <el-button
              size="small"
              @click="disableButton(row)"
              :disabled="row.isButtonDisabled"
          >
            退单
          </el-button>

        </template>

      </el-table-column>
    </el-table>
  </el-dialog>
  <!--购物车界面-->
  <el-dialog v-model="shoppingCartVisible" title="购物车" width="800">
    <el-table :data="shoppingCartData">
      <el-table-column property="id" label="药品编号" width="150"/>
      <el-table-column property="name" label="药品名称" width="150"/>
      <el-table-column property="price" label="价格" width="100"/>
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button text @click="countDown(scope.row)">-</el-button>
          <span>{{scope.row.purchaseStatus}}</span>
          <el-button text @click="countUp(scope.row)">+</el-button>
        </template>
      </el-table-column>
      <el-table-column  fixed="right" label="数量" width="80">
        <template #default="scope">
          <span>{{scope.row.purchaseStatus}}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right">

        <template #default="scope">

          <el-button
              type="danger"
              size="small"
              @click="removePurchase(scope.row)"
          >
            >
            删除
          </el-button>

        </template>

      </el-table-column>
    </el-table>
    <div class="submitOrder">
      <span class="right" >总价：{{total}} 元</span>
      <el-button class="right" type="success" @click="purchaseAll()" >购买</el-button>
    </div>

  </el-dialog>


</template>

<script setup>
import {ShoppingCart, Tickets} from '@element-plus/icons-vue'
import axios from "axios";
import {ElMessage} from 'element-plus'
import { computed } from 'vue'
import {ref} from 'vue'
import { onUnmounted } from 'vue';

const tableData = ref([])
const shoppingCartVisible = ref(false)
const purchaseRecordVisible = ref(false)
const shoppingCartData = ref([])
const showSearch = ref(true);
const purchaseData = ref([])
const input = ref()
const  orderForm =ref({
  name:"",
  finalPrice:0
})

const balance = ref(200);


const disableButton = (row) => {
  // 点击退单按钮时，将当前行的 isButtonDisabled 属性设置为 true，禁用按钮
  row.isButtonDisabled = true;
  balance.value += row.finalPrice;
  ElMessage.success('退单成功！');
}

const total = computed(()=>{
  let to=0
  for (let i = 0; i < shoppingCartData.value.length; i++) {
    to+=shoppingCartData.value[i].price*shoppingCartData.value[i].purchaseStatus
  }
  return to
})



onUnmounted(() => {
  if (uploadedImageUrl.value) {
    URL.revokeObjectURL(uploadedImageUrl.value);
  }
});
const uploadedImageUrl = ref('');
const uploadedImages = ref({});

const handleSuccess = (response, file) => {
  ElMessage.success('文件上传成功');
  console.log('文件上传成功:', response);

  const localImageUrl = URL.createObjectURL(file.raw);
  uploadedImageUrl.value = localImageUrl;
  uploadedImages.value = localImageUrl;
};

const beforeUpload = (file) => {
  console.log('准备上传文件:', file);
};

const submitUpload = () => {
  return true; // 这里可以触发上传行为，具体逻辑根据需要编写
};

function showOrderData(){
  axios.get('/api/order').then((res)=>{
    purchaseData.value =res.data
  })
}

function purchaseAll(){
  if (total.value<=100){
    let d = ""
    for (const drug of shoppingCartData.value) {
      // eslint-disable-next-line no-unused-vars

      d+="-"+drug.name+"*"+drug.purchaseStatus
    }
    orderForm.value.name=d.slice(1)
    orderForm.value.finalPrice=total.value
    console.log(orderForm.value)

    axios.post('/api/order/add',orderForm.value).then((require) => {
      if (require!==""){
        ElMessage({
          message: '购买成功！',
          type: 'success',
        })

        for (const data of shoppingCartData.value) {
          axios.get('/api/drug/removePurchase/' + data.id).then(() => {

          })
        }
        purchaseData.value.push(shoppingCartData.value);
        shoppingCartData.value=[]
      }
    })

  }else {
    ElMessage({
      message: '余额不足，请充值！',
      type: 'error',
    })
  }
}

function show() {
  axios.get('/api/drug').then((require) => {
    tableData.value = require.data
  })
}
show()
// 重置按钮
function resetQuery() {
  show();
}


function countUp(row){
  row.purchaseStatus++



}
function countDown(row){
  if (row.purchaseStatus>1){
    row.purchaseStatus--
  }
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
.submitOrder{
  display: flex;
  justify-content: flex-end;
}
.right{
  margin-top: 10px;
  margin-right: 20px;
}
body {
  background-color: wheat /* 替换为您想要的颜色代码 */
}
</style>
