export default {
  state: {
    fooCount: 0,
    name: 'foo name'
  },
  mutation: {
    incrementFoo: (state, num=1) => {
        fooCount+=num
    }
  },
  actions: {
    handleFoo({commit}){
      commit({
        type: 'incrementFoo',
        num: 5
      })
    }
  },
  getters: {
    fooNamePlusCount(state){
      return state.fooCount + state.name
    }
  }
}
