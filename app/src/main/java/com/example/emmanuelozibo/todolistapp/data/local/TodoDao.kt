package com.example.emmanuelozibo.todolistapp.data.local

import android.arch.persistence.room.*
import com.example.emmanuelozibo.todolistapp.data.local.models.Todo

@Dao
interface TodoDao{

    @Query("SELECT*FROM todo ORDER BY start ASC")
    fun getTodoList(): List<Todo>

    @Query("SELECT*FROM todo WHERE tId=:tid")
    fun getTodoItem(tid: Long): Todo

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun saveTodo(todo: Todo): Long

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun removeTodo(todo: Todo)
}