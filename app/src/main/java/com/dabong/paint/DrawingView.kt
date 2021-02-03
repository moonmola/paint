package com.dabong.paint

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private var lines = ArrayList<lineData>()
    private var undoneLines = ArrayList<lineData>()

    companion object {
        var path = Path()
    }

    var currentX: Float = 0F
    var currentY: Float = 0F


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val mX = event.x
        val mY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart(mX, mY)
            MotionEvent.ACTION_MOVE -> touchMove(mX, mY)
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private fun touchStart(mX: Float, mY: Float) {
        path.reset()
        path.moveTo(mX, mY)
        currentX = mX
        currentY = mY
        lines.add(lineData(path, MainActivity.paint))
    }

    private fun touchMove(mX: Float, mY: Float) {
        path.quadTo(
            currentX,
            currentY,
            (mX + currentX) / 2,
            (mY + currentY) / 2
        )
        currentX = mX
        currentY = mY

        invalidate()
    }

    private fun touchUp() {
        path = Path()
        path.reset()
        Log.e("er", lines.size.toString())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in lines) {
            canvas.drawPath(i.path, i.color)
        }
    }

    fun undoPath() {
        when {
            lines.size > 0 -> {
                undoneLines.add(lines.removeAt(lines.size - 1))
                invalidate()
            }
        }
    }

    fun redoPath() {
        when {
            undoneLines.size > 0 -> {
                lines.add(undoneLines.removeAt(undoneLines.size - 1))
                invalidate()
            }
        }
    }

    inner class lineData(val path: Path, val color: Paint) {

    }
}