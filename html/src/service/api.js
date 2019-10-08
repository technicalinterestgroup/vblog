import HTTP from '../config/http'

const {
  get,
  post
} = HTTP

/**
 * [login description]
 * @param  {[String]} url [description]
 * @param {[Object]} params [description]
 * @return {[Promise]} 返回一个promise对象
 */
// http://192.168.8.181:8080/system/authority/authoritys
// export const login = (data, config = {}) => post('/api/user/login', data, config)
export const login = (data, config = {}) => get('http://192.168.8.181:8080/system/authority/user/login', data, config)

// 请求数据接口
// export const initList = data => post('/user/init', data)
