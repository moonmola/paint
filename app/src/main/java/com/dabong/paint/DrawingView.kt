package com.dabong.paint

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import java.lang.IllegalArgumentException

class DrawingView(context: Context):View(context) {
    private var paths = ArrayList<Path>()
    private var undonePaths = ArrayList<Path>()
    companion object{
        lateinit var extraCanvas:Canvas
        lateinit var extraBitmap: Bitmap
        var path = Path()
    }
    var currentX:Float = 0F
    var currentY:Float = 0F


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        try{
            if(width> 0 && height > 0){
                extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                extraCanvas = Canvas(extraBitmap)
                extraCanvas.drawColor(MainActivity.backgroundColor)
            }
        } catch (e:IllegalArgumentException){
            Toast.makeText(
                context,
                "Some Thing is not right please restart the app",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val mX = event.x
        val mY = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> touchStart(mX, mY)
            MotionEvent.ACTION_MOVE -> touchMove(mX, mY)
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }
    private fun touchStart(mX:Float, mY:Float){
        path.reset()
        path.moveTo(mX,mY)
        currentX = mX
        currentY = mY
    }
    private fun touchMove(mX:Float, mY:Float){
        path.quadTo(
            currentX,
            currentY,
            (mX+currentX)/2,
            (mY+currentY)/2
        )
        currentX = mX
        currentY = mY
        extraCanvas.drawPath(path,MainActivity.paint)
        invalidate() //??이건 왜
    }
    private fun touchUp(){
        paths.add(path)
        path.reset()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap,0f,0f,null)
        for(path in paths){
            canvas.drawPath(path,MainActivity.paint)
        }
    }
    fun undoPath() {
        when {
            paths.size > 0 -> {
                undonePaths.add(paths.removeAt(paths.size - 1))
                invalidate()
            }
        }
    }

    fun redoPath() {
        when {
            undonePaths.size > 0 -> {
                paths.add(undonePaths.removeAt(undonePaths.size - 1))
                invalidate()
            }
        }
    }
}