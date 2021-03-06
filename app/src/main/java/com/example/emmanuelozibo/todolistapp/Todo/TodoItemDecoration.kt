package com.example.emmanuelozibo.todolistapp.Todo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

//todo: unsafe access by !!
class TodoItemDecoration : RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)

        val paint = Paint()
        paint.color = Color.BLUE

        val lm = parent!!.layoutManager!! as LinearLayoutManager


        val adapter = parent.adapter as TodoAdapter

        val windowStartX = 800f//todo: better screen size handling
        val windowEndX = 900f

        val tags = adapter.getTags()
        val keys = tags!!.keys

        val startI = lm.findFirstVisibleItemPosition()

        for ((key, value) in tags!!) {

            val start = value.min()!!
            val end = value.max()!!

            val w = (windowEndX - windowStartX) / keys.size

            val i = keys.indexOf(key)

            val X = windowStartX + i * w

            val startView: View? = parent.getChildAt(0)

            val padding = startView?.y!!

            val h: Float = ((startView?.measuredHeight)?.toFloat()) ?: 0f
            val sY = h * (start - startI) + h/2 + padding
            val eY = h * (end - startI) + h/2 + padding


            c?.drawLine(X, sY, X, eY,  paint)
        }

    }
}