package com.dabong.paint

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        var STROKE_WIDTH = 12f
        var ERASER_WIDTH = 12f
        lateinit var paint:Paint
        var backgroundColor = Color.parseColor("#FFFFFF")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = PaintFragment()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.frame, fragment, "PAINT_FRAGMENT").commit()

        var drawColor = ResourcesCompat.getColor(resources, R.color.White,null)
        paintStroke(drawColor, STROKE_WIDTH)
        stroke_width.progress = STROKE_WIDTH.toInt()/2
        eraser_width.progress = ERASER_WIDTH.toInt()
        stroke_width.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                STROKE_WIDTH = progress.toFloat() * 2
                paintStroke(drawColor, STROKE_WIDTH)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        eraser_width.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                ERASER_WIDTH = progress.toFloat()
                paintStroke(Color.parseColor("#FFFFFF"), ERASER_WIDTH)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        eraser.setOnClickListener {
            eraser_width.progress = ERASER_WIDTH.toInt()
            paintStroke(Color.parseColor("#FFFFFF"), ERASER_WIDTH)
        }

//        save_btn.setOnClickListener {
//            requestStoragePermission()
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED
//            ) {
//                saveImage(PaintCanvas.extraBitmap, this)
//                if (Build.VERSION.SDK_INT >= 29) {
//                    Toast.makeText(this, "saved at /Pictures/Paint/", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, "saved at /Paint/", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }

        stroke_color.setOnColorChangeListener { _, _, color ->
            drawColor = color
            paintStroke(drawColor, STROKE_WIDTH)
        }
    }
    private fun paintStroke(drawColor:Int, STROKE_WIDTH:Float){
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