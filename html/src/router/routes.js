// 这个写法 webpack打包的时候 是按需加载
const Home = () => import( /* webpackChunkName: "home" */ '@/views/Home')
const Login = () => import( /* webpackChunkName: "login" */ '@/views/Login')

const routes = [{
  path: "",
  redirect: "/home"
},
{
  path: "/home",
  component: Home,
  meta: {
    // 证明 用户访问该组件的时候需要登录
    requireAuth: true
  }
},
{
  path: '/login',
  component: Login
}
]

export default routes
