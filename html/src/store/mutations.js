import TYPE from './mutation-types'

const MUTATIONS = {
  // 储存TOKEN
  SAVE_TOKEN(state, {
    loginuser,
    login2page
  }) {
    state.loginuser = loginuser
    state.token = loginuser.accessToken
    state.auth_params = loginuser.auth_params
    state.funclist = loginuser.leftFunctionList
    login2page()
  },
  LOGO_OUT(state, router) {
    state.token = null
    router.push('/login')
  }
}




export default MUTATIONS
