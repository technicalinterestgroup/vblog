import store from '@/store'
/**
 * [RELOAD_EVENT description] 监听页面刷新事件
 * @param {[type]} args [description] 事件数组
 */
export const RELOAD_EVENT = (...args) => {
  const _BEFOREUNLOAD = typeof args[0] == 'function' && args[0]
  const _UNLOAD = typeof args[1] == 'function' && args[1]
  const _LOAD = typeof args[2] == 'function' && args[2]

  window.addEventListener('beforeunload', () => {
    _BEFOREUNLOAD && _BEFOREUNLOAD()
  })

  window.addEventListener('unload', function() {
    _UNLOAD && _UNLOAD()
  })

  window.addEventListener('load', function() {
    _LOAD && _LOAD()
  })

  // README
  // beforeunload 在离开当前页面、刷新页面、关闭页面之后都会触发 之后会触发 unload
  // 刷新之前执行 beforeunload事件，在新页面即将替换旧页面时 unload事件，最后 load事件
  // 在 ie chrome 360 都是在刷新页面 或 关闭页面都会触发 beforeunload、unload, 这样就无法确实具体是执行的什么操作
  // 火狐时刷新页面执行 unload，关闭页面时执行 beforeunload
  // 在页面加载完毕会发出 load, load 会在vue生命周期 beforeCreate 之前触发
}

/**
 * [GET_TOKEN description] 返回token没有返回false
 */
export const GET_TOKEN = () => {
  return store.state.token ?
    store.state.token :
    false
}
