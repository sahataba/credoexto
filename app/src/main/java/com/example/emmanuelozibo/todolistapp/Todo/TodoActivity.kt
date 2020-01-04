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

        val list = todoDatabase?.getTodoDao()?.getTodoList()
        val arrayList: ArrayList<Todo> = ArrayList<Todo>(list!!.size)
        arrayList.addAll(list)//todo: extra transformation

        todoAdapter = TodoAdapter(arrayList)
        todoAdapter?.setTodoItemClickedListener(this)

        todo_rv.adapter = todoAdapter
        todo_rv.layoutManager = LinearLayoutManager(this)
        todo_rv.hasFixedSize()

        //todo: bug with reordering only by 1 position
        val dragAndDrop = ItemTouchHelper(DragDropHandler(todoAdapter!!, ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), -1))
        val todoItemDecoration = TodoItemDecoration()

        todo_rv.addItemDecoration(todoItemDecoration)
        dragAndDrop.attachToRecyclerView(todo_rv)

        add_todo.setOnClickListener { startActivityForResult(Intent(this, AddTodoActivity::class.java), 2) }

    }

    override fun onTodoItemClicked(todo: Todo) {
        val alertDialog = AlertDialog.Builder(this)
                .setItems(R.array.dialog_list, DialogInterface.OnClickListener { dialog, which ->
                    if (which==0){
                        val intent = Intent(this@TodoActivity, AddTodoActivity::class.java)
                        intent.putExtra("tId", todo.tId)
                        startActivityForResult(intent, 2)
                    }else{
                        todoDatabase?.getTodoDao()?.removeTodo(todo)
                        todoAdapter?.remove(todo)
                    }
                    dialog.dismiss()
                })
                .create()
        alertDialog.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        //todo: handle result code
        if(requestCode==2)
        {
            val todo = data.getParcelableExtra<Todo>("todo")

            if(todo.tId == 0L) {
                val tId = todoDatabase?.getTodoDao()?.saveTodo(todo)
                todo.tId = tId!!
                todoAdapter?.saveTodo(todo)
            } else {
                todoDatabase?.getTodoDao()?.updateTodo(todo)
                todoAdapter?.updateTodo(todo)
            }
        }
    }

}
