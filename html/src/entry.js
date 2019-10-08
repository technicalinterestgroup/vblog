import Vuex from 'vuex'
import router from './router'
import App from './App.vue'
import store from './store/'
import Vue from './init'

var vm = new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
