import Vue from 'vue'
import Router from 'vue-router'
import routes from './routes'
import store from '@/store/'

Vue.use(Router)

const router = new Router({
  routes
})
// 检测权限
router.beforeEach((to, from, next) => {
  // 1. 校验是否有sessionStorage ganinfo_user_info
  // 如果有那么登录过将 sesssionStorage 内容传给store
  // 如果没有那么 不传
  // 2. 验证store.state.token 如果没有 去登陆 如果有 继续
  if (sessionStorage.getItem('ganinfo_user_info')) {
    let userInfo = JSON.parse(sessionStorage.getItem('ganinfo_user_info'))
    store.state = Object.assign(store.state, userInfo)
    sessionStorage.removeItem('ganinfo_user_info')
  }
  // 需要登录
  if (to.matched.some(r => r.meta.requireAuth)) {
    if (store.state.token) {
      next()
    } else {
      next({
        path: '/login',
        query: {
          // 登录之后直接跳转到想要的页面
          redirect: to.fullPath
        }
      })
    }
  } else {
    next()
  }
})
// 跳转
router.afterEach((to, from) => {
  // console.log(to)
})

export default router
