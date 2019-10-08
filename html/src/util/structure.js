/**
 * 创建一些简单的 javascript 不存在的数据结构
 */

export class Stack {
  constructor() {
    this.items = []
  }

  push (item) {
    this.items.push(item)
  }

  pop () {
    return this.items.pop()
  }

  size () {
    return this.items.length
  }

  top () {
    return this.items[this.items.length - 1]
  }

  isEmpty () {
    return this.items.length === 0
  }

  clear () {
    this.items = []
  }
}

export class Queue {
  constructor() {
    this.items = []
  }

  enqueue (item) {
    this.items.push(item)
  }

  dequeue () {
    return this.items.shift()
  }

  head () {
    return this.items[0]
  }

  size () {
    return this.items.length
  }

  clear () {
    this.items = []
  }

  isEmpty () {
    return this.items.length === 0
  }

  tail () {
    return this.items[this.item.length - 1]
  }
}