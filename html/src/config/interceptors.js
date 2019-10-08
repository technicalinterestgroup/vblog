import axios from 'axios'
// 引入用户状态
import store from '@/store/index'
// 引入默认配置
import { GET_TOKEN } from '@/assets/js/util'
// 配置信息
import { baseUrl } from '@/config/env'

axios.defaults.baseURL = baseUrl
// 设置默认的请求超时时间
// axios.defaults.timeout = 10000
// post 请求头设置
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
// axios 配置优先级
// lib/default.js 文件
// axios.default 对象
// config 参数
// 添加请求拦截器
const reqInterceptor = axios.interceptors.request.use(config => {
  // 数据请求前的处理
  const accessToken = GET_TOKEN()
  accessToken && (config.headers['accessToken'] = accessToken)
  return Promise.resolve(config)
  // return config
}, error => {
  // 对请求错误做些什么
  return Promise.reject(error)
})

// 添加响应拦截器
const resInterceptor = axios.interceptors.response.use(response => {
  // 做数据响应时候处理
  // console.log(response)
  return Promise.resolve(response)
  // return response
}, error => {
  // 对响应错误做点什么
  if (error.response.status) {
    // switch (error.response.status) {
    //   case 401:
    //     // ... 用户未登录
    //     break
    //   case 403:
    //     // ...token 过期
    //     // 1. 用户重新登陆
    //     // 2. 清空vuex和本地储存token
    //     break
    //
    //   case 404:
    //     // ...404请求不存在
    //     break
    //   default:
    //     // 其他错误，直接抛出错误提示
    //     console.log(error)
    // }
    return Promise.reject(error.response)
  }
})
// 移除拦截器
// axios.interceptors.request.eject(reqInterceptor)
axios._name = 'interceptors'

export default axios
