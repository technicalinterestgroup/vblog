import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import actions from './actions'
import mutations from './mutations'

Vue.use(Vuex)
// vuex 运行流程
// vueComponent -> action -> mutation(devtools 捕获状态插件) -> state -> vueComponent

// 解决vuex 持久化问题
// 1. 监听页面刷新事件将state内容存入本地session
// 2. 监听路由跳转将本地内容传入state, 删除session内容
const state = {
  token: '',
  loginuser: {},
  auth_params: '',
  funclist: []
}

const store = new Vuex.Store({
  // 数据
  state,
  // 修改数据的方法
  mutations,
  // 调用 mutation
  actions,
  // 需要计算的复杂数据
  getters,
  // 分割多个模块
  // modules
})

export default store
