export default {
  getTodosDone: state => state.todos.filter(item => item.done).length,
  getTodoById: (state) => (id) => state.todos.find(item => item.id === id)
}
