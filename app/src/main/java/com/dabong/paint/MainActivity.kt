package com.dabong.paint

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        var STROKE_WIDTH = 12f
        var ERASER_WIDTH = 12f
        lateinit var paint: Paint
        var backgroundColor = Color.parseColor("#FFFFFF")
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var drawColor = ResourcesCompat.getColor(resources, R.color.Black, null)


        stroke_width.progress = STROKE_WIDTH.toInt()
        eraser_width.progress = ERASER_WIDTH.toInt()
        paintStroke(drawColor, STROKE_WIDTH)
        customPanel.visibility = View.GONE


        blushButton.isSelected = true
        blushButton.setOnClickListener {
            turnBrush()
            paintStroke(drawColor, STROKE_WIDTH)
        }
        eraserButton.setOnClickListener {
            turnEraser()
        }
        detailButton.setOnClickListener {
            detailButton.isSelected = !detailButton.isSelected
            if (detailButton.isSelected) {
                customPanel.visibility = View.VISIBLE
            } else {
                customPanel.visibility = View.GONE
            }
        }
        undoButton.setOnClickListener {
            frame.undoPath()
        }
        redoButton.setOnClickListener {
            frame.redoPath()
        }
        saveButton.setOnClickListener {
            Log.e("??","?")
            Toast.makeText(this,"곧 지원될 예정입니다^^;",Toast.LENGTH_LONG).show()
        }
        stroke_width.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                STROKE_WIDTH = progress.toFloat() * 2
                paintStroke(drawColor, STROKE_WIDTH)
                turnBrush()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        eraser_width.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                ERASER_WIDTH = progress.toFloat() * 2
                turnEraser()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        stroke_color.setOnColorChangeListener { _, _, color ->
            drawColor = color
            paintStroke(drawColor, STROKE_WIDTH)
            turnBrush()
        }
    }

    private fun turnBrush() {
        eraserButton.isSelected = false
        blushButton.isSelected = true
    }

    private fun turnEraser() {
        eraserButton.isSelected = true
        blushButton.isSelected = false
        paintStroke(Color.parseColor("#FFFFFF"), ERASER_WIDTH)
    }

    private fun paintStroke(drawColor: Int, STROKE_WIDTH: Float) {
        paint = Paint().apply {
            color = drawColor
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = STROKE_WIDTH
        }
    }

}