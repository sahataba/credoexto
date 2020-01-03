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

class TodoAdapter(var todoList: ArrayList<Todo>? = ArrayList<Todo>()): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

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
        /*holder.view.setOnLongClickListener{
            onTodoItemClickedListener!!.onTodoItemLongClicked(todoList!!.get(position))
            true
        }*/
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
        private fun getImage(priority: Int): Int
        = if (priority == 1) R.drawable.low_priority else if(priority == 2) R.drawable.medium_priority else R.drawable.high_priority
    }

    fun swap (a: Int, b: Int) {
        val _a = todoList?.get(a)
        val _b = todoList?.get(b)
        todoList?.set(b, _a!!)
        todoList?.set(a, _b!!)
        this.notifyDataSetChanged()
    }

    fun setTodoItemClickedListener(onTodoItemClickedListener: OnTodoItemClickedListener){
        this.onTodoItemClickedListener = onTodoItemClickedListener
    }

    interface OnTodoItemClickedListener{
        fun onTodoItemClicked(todo: Todo)
        fun onTodoItemLongClicked(todo: Todo)
    }
}