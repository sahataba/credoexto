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


        val adapter = parent.adapter as TodoAdapter

        val windowStartX = 800f
        val windowEndX = 900f

        val tags = adapter.getTags()
        val keys = tags!!.keys

        val startI = lm.findFirstVisibleItemPosition()
        val endI = lm.findLastVisibleItemPosition()

        for ((key, value) in tags!!) {

            val start = Math.max(startI, value.min()!!)
            val end = Math.min(endI, value.max()!!)

            val w = (windowEndX - windowStartX) / keys.size

            val i = keys.indexOf(key)

            val X = windowStartX + i * w

            val startView: View? = parent.getChildAt(start)
            val padd = startView?.y!!

            val h: Float = ((startView?.measuredHeight)?.toFloat()) ?: 0f
            val sY = h * (start - start) + h/2 + padd
            val eY = h * (end - start) + h/2 + padd


            c?.drawLine(X, sY, X, eY,  paint)
        }

    }
}