package com.example.emmanuelozibo.todolistapp.Todo

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.emmanuelozibo.todolistapp.R
import com.example.emmanuelozibo.todolistapp.data.local.TodoListDatabase
import com.example.emmanuelozibo.todolistapp.data.local.models.Todo
import kotlinx.android.synthetic.main.activity_main.*

class TodoActivity : AppCompatActivity(), TodoAdapter.OnTodoItemClickedListener {


    private var todoDatabase: TodoListDatabase? = null
    private var todoAdapter: TodoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoDatabase = TodoListDatabase.getInstance(this)
        todoAdapter = TodoAdapter()
        todoAdapter?.setTodoItemClickedListener(this)

        val dragAndDrop = ItemTouchHelper(DragDropHandler(todoAdapter!!, ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), ItemTouchHelper.LEFT))
        val todoItemDecoration = TodoItemDecoration(10)

        todo_rv.addItemDecoration(todoItemDecoration)
        dragAndDrop.attachToRecyclerView(todo_rv)

        add_todo.setOnClickListener { startActivity(Intent(this, AddTodoActivity::class.java)) }

    }


    override fun onResume() {
        super.onResume()
        val list = todoDatabase?.getTodoDao()?.getTodoList()
        val arrayList: ArrayList<Todo> = ArrayList<Todo>(list!!.size)
        arrayList.addAll(list)
        todoAdapter?.todoList=arrayList
        todo_rv.adapter = todoAdapter
        todo_rv.layoutManager = LinearLayoutManager(this)
        todo_rv.hasFixedSize()
    }

    override fun onTodoItemClicked(todo: Todo) {
        val alertDialog = AlertDialog.Builder(this)
                .setItems(R.array.dialog_list, DialogInterface.OnClickListener { dialog, which ->
                    if (which==0){
                        val intent = Intent(this@TodoActivity, AddTodoActivity::class.java)
                        intent.putExtra("tId", todo.tId)
                        startActivity(intent)
                    }else{
                        todoDatabase?.getTodoDao()?.removeTodo(todo)
                        onResume()
                    }
                    dialog.dismiss()
                })
                .create()
        alertDialog.show()
    }

}
