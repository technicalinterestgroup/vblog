/**
 * 配置编译环境和线上环境之间的切换
 *
 * baseUrl: 域名地址
 * routerMode: 路由模式
 * imgBaseUrl: 图片所在域名地址
 *
 */

let baseUrl = ''
let routerMode = 'hash'
let imgBaseUrl = ''

// 开发环境
if (process.env.NODE_ENV == 'development') {
  // imgBaseUrl = '/img/'
  // baseUrl = 'http://127.0.0.1:3302'

// 上线环境
} else if (process.env.NODE_ENV == 'production') {
  baseUrl = ''
  imgBaseUrl = ''
}

export {
  baseUrl,
  routerMode,
  imgBaseUrl,
}
