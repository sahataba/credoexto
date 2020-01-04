package com.example.emmanuelozibo.todolistapp.data.local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.emmanuelozibo.todolistapp.data.local.models.Todo
import java.util.concurrent.Executors

//You can also check out type converters
@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoListDatabase: RoomDatabase(){

    /**
     * This is an abstract method that returns a dao for the Db
     * */
    abstract fun getTodoDao(): TodoDao

    /**
     * A singleton design pattern is used to ensure that the database instance created is one
     * */
    companion object {
        val databaseName = "tododatabase"
        var todoListDatabase: TodoListDatabase? = null

        val initialData = (1..24).map { id -> Todo("item $id", 1, 2, "", 0) }

        fun getInstance(context: Context): TodoListDatabase?{
            if (todoListDatabase == null){
                todoListDatabase = Room.databaseBuilder(context,
                        TodoListDatabase::class.java,
                        TodoListDatabase.databaseName)
                        .addCallback(object : Callback() {
                            override fun onCreate( db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                //race condition with loading initial data from UI
                                Executors.newSingleThreadScheduledExecutor().execute(object : Runnable {
                                    override fun run() {
                                        val dao = getInstance(context)?.getTodoDao()
                                        initialData.forEach{todo -> dao?.saveTodo(todo)}
                                    }
                                })
                            }
                        })
                        .allowMainThreadQueries()//i will remove this later, database are not supposed to be called on main thread
                        .build()
            }
            return todoListDatabase
        }
    }
}