/**
 * 工具包
 */
/**
 * [dispatch description]
 * @param  {[type]} componentName [description] 组件名称
 * @param  {[type]} eventName     [description] 事件名称
 * @param  {[type]} params        [description] 参数
 * @return {[type]}               [description]
 */
export const dispatch = (componentName,eventName,params) => {
  let parent = this.$parent || this.$root
  let name = parent.$options.name
  while (parent && (!name || name !== componentName)) {
    parent = parent.$parent
    if (parent) {
      name = parent.$options.name
    }
  }
  if (parent) {
    parent.$emit.apply(parent,[eventName].concat(params))
  }
}

function broadFn (componentName,eventName,params) {
  this.$children.forEach(child => {
    const name = child.$options.name
    if (name === componentName) {
      child.$emit.apply(child,[eventName].concat(params))
    } else {
      broadcast.apply(child,[componentName,eventName].concat(params))
    }
  })
}
// 广播 调用所有子节点
export const broadcast = (componentName,eventName,params) => {
  broadFn.call(this,componentName,eventName,params)
}

// 向上查找组件
export const findComponentUpward = (context,componentName) => {
  let parent = context.$parent
  let name = parent.$options.name

  while (parent && (!name || [componentName].indexOf(name) < 0)) {
    parent = parent.$parent
    if (parent) name = parent.$options.name
  }
  return parent
}

// 向上查找所有组件
export const findComponentsUpward = (context,componentName) => {
  const parents = []
  const parent = context.$parent
  if (parent) {
    if (parent.$options.name === componentName) parents.push(parent)
    return parents.concat(findComponentsUpward(parent,componentName))
  } else {
    return []
  }
}

// 向下查找组件
export const findComponentDownward = (context,componentName) => {
  const children = context.$children
  let child = null
  if (children.length) {
    for (const item of children) {
      const name = item.$options.name
      if (name === componentName) {
        child = item
        break
      } else {
        child = findComponentDownward(item,componentName)
        if (child) break
      }
    }
  }
  return child
}

// 向下查找所有组件
export const findComponentsDownward = (context,componentName) => {
  return context.$children.reduce((components,child) => {
    if (child.$options.name === componentName) components.push(child)
    const foundChilds = findComponentsDownward(child,componentName)
    return components.concat(foundChilds)
  },[])
}

// 查找所有兄弟组件
export const findBrothersComponents = (context,componentName,exceptMe = true) => {
  const res = context.$parent.$children.filter(item => {
    return item.$options.name === componentName
  })
  const index = res.findIndex(item => item._uid === context._uid)
  if (exceptMe) res.splice(index,1)
  return res
}

