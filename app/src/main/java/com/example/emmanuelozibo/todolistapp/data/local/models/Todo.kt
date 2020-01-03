package com.example.emmanuelozibo.todolistapp.data.local.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "todo")
class Todo(
        @ColumnInfo(name = "name")
        var name:String = "",
        @ColumnInfo(name = "start")
        var start: Long = 0,
        @ColumnInfo(name = "end")
        var end: Long = 0,
        @ColumnInfo(name = "tag")
        var tag: String = "",
        @PrimaryKey(autoGenerate = true) var tId: Long = 0)