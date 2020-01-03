package com.example.emmanuelozibo.todolistapp.Todo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.emmanuelozibo.todolistapp.R
import com.example.emmanuelozibo.todolistapp.data.local.models.Todo
import java.util.ArrayList

class TodoAdapter(val todoList: ArrayList<Todo>): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    private var onTodoItemClickedListener: OnTodoItemClickedListener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val layout = if (itemCount == 0) R.layout.empty_view else R.layout.todo_item_view
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TodoViewHolder(view, todoList!!)
    }

    override fun getItemCount(): Int {
        return if(todoList!!.isEmpty()) 0 else todoList!!.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int){
        holder.view.setOnClickListener{onTodoItemClickedListener!!.onTodoItemClicked(todoList!!.get(position))}
        holder.onBindViews(position)
    }

    inner class TodoViewHolder(val view: View, val todoList: List<Todo>): RecyclerView.ViewHolder(view){
        fun onBindViews(position: Int){
            if (itemCount != 0){
                view.findViewById<TextView>(R.id.name).text = todoList.get(position).name
                view.findViewById<TextView>(R.id.start).text = todoList.get(position).start.toString()
                view.findViewById<TextView>(R.id.end).text = todoList.get(position).end.toString()
            }

        }
    }

    fun swap (a: Int, b: Int) {
        val _a = todoList?.get(a)
        val _b = todoList?.get(b)
        todoList?.set(b, _a!!)
        todoList?.set(a, _b!!)
        this.notifyDataSetChanged()
    }

    fun remove(todo: Todo) {
        val i = todoList?.indexOf(todo)
        todoList?.removeAt(i)
        this.notifyDataSetChanged()
    }


    fun updateTodo(todo: Todo) {
        val i = todoList?.indexOfFirst{it.tId == todo.tId}
        todoList?.set(i, todo)
        this.notifyDataSetChanged()
    }

    fun saveTodo(todo: Todo) {
        todoList?.add(todo)
        this.notifyDataSetChanged()
    }

    fun getTags(): Map<String, List<Int>>? {
        return todoList?.groupBy({ it.tag }, {todoList.indexOf(it)})
    }

    fun setTodoItemClickedListener(onTodoItemClickedListener: OnTodoItemClickedListener){
        this.onTodoItemClickedListener = onTodoItemClickedListener
    }

    interface OnTodoItemClickedListener{
        fun onTodoItemClicked(todo: Todo)
    }
}