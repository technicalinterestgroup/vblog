import Axios from './interceptors.js'

class HTTP {
  constructor() {

  }

  /**
   * get方法，对应get请求
   * @param {String} url [请求的url地址]
   * @param {Object|String} params [请求时携带的参数]
   */
  get(url, params, config) {
    return new Promise((resolve, reject) => {
      Axios.get(url, {
          params
        }, config)
        .then(res => {
          resolve(res.data)
        }).catch(err => {
          reject(err.data)
        })
    })
  }
  /**
   * post方法，对应post请求
   * @param {String} url [请求的url地址]
   * @param {Object|String} data [请求时携带的参数]
   * @param {Object} config [请求时配置的参数] options.config 配置的头信息
   */
  post(url, data, config) {
    return new Promise((resolve, reject) => {
      Axios.post(url, data, config)
        .then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
    })
  }
  install(Vue, options) {
    Vue.prototype.$http = this
  }
}

export default new HTTP()
