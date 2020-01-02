package com.example.emmanuelozibo.todolistapp.Todo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class TodoItemDecoration(var spacing: Int) : RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)

        val paint = Paint()
        paint.color = Color.BLUE

        c?.drawLine(100f, 0f, 100f, 800f,  paint)
    }
}