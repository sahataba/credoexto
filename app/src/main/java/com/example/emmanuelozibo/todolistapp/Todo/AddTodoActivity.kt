package com.example.emmanuelozibo.todolistapp.Todo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.emmanuelozibo.todolistapp.R
import com.example.emmanuelozibo.todolistapp.data.local.TodoListDatabase
import com.example.emmanuelozibo.todolistapp.data.local.models.Todo
import kotlinx.android.synthetic.main.activity_add_todo.*

class AddTodoActivity: AppCompatActivity() {

    private var todoDatabase: TodoListDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        todoDatabase = TodoListDatabase.getInstance(this)

        val tId = intent.getIntExtra("tId", 0)

        if (tId == 0){
            add_todo.setOnClickListener{
                val todo = Todo(
                        name_ed.text.toString(),
                        start_ed.text.toString().toLong(),
                        end_ed.text.toString().toLong(),
                        tag_ed.text.toString()
                        )
                todoDatabase!!.getTodoDao().saveTodo(todo)
                finish()
            }
        }else{
            add_todo.text = getString(R.string.update)
            val item = todoDatabase!!.getTodoDao().getTodoItem(tId)
            name_ed.setText(item.name)
            start_ed.setText(item.start.toString())
            end_ed.setText(item.end.toString())
            tag_ed.setText(item.tag)
            add_todo.setOnClickListener {
                val todo = Todo(
                        name_ed.text.toString(),
                        start_ed.text.toString().toLong(),
                        end_ed.text.toString().toLong(),
                        tag_ed.text.toString(),
                        tId
                )
                todoDatabase!!.getTodoDao().updateTodo(todo)
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home){
            startActivity(Intent(this, TodoActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}