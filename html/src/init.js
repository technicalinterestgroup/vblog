import Vue from 'vue'
import store from './store/'
import HTTP from './config/http'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import {
  RELOAD_EVENT
} from '@/assets/js/util'

/**
 * [description] 将state中的数据 添加到 sessionStroge 中
 * @return {[type]} [description]
 */
const _unload = function() {
  if (store.state.token) {
    let userInfo = {
      token: store.state.token,
      loginuser: store.state.loginuser,
      auth_params: store.state.auth_params,
      funclist: store.state.funclist
    }
    sessionStorage.setItem('ganinfo_user_info', JSON.stringify(userInfo))
  }
}

RELOAD_EVENT(null, _unload)
// 注册组件
Vue.use(HTTP)
Vue.use(Element)

export default Vue
