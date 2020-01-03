package com.example.emmanuelozibo.todolistapp.Todo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class TodoItemDecoration(var spacing: Int) : RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)

        val paint = Paint()
        paint.color = Color.BLUE

        val lm = parent!!.layoutManager!! as LinearLayoutManager
        val start = lm.findFirstVisibleItemPosition()
        val end = lm.findLastVisibleItemPosition()

        val startView: View? = parent.getChildAt(start)
        val endView = parent.getChildAt(end)

        val padd = startView?.y!!

        val adapter = parent.adapter as TodoAdapter

        val windowStartX = 100f
        val windowEndX = 200f

        val tags = adapter.getTags()
        val keys = tags!!.keys

        for ((key, value) in tags!!) {

            val w = (windowEndX - windowStartX) / keys.size

            val i = keys.indexOf(key)

            val X = windowStartX + i * w

            val h: Float = ((startView?.measuredHeight)?.toFloat()) ?: 0f
            val sY = h * (start - start) + h/2 + padd
            val eY = h * (end - start) + h/2 + padd


            c?.drawLine(X, sY, X, eY,  paint)
        }

    }
}