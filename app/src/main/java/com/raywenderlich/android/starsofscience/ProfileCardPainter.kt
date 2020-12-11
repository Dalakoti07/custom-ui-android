package com.raywenderlich.android.starsofscience

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import com.raywenderlich.android.starsofscience.utils.RectFFactory

class ProfileCardPainter (@ColorInt private val color:Int) :Painter{
    override fun paint(canvas: Canvas) {
        // this canvas is same as which needed to be painted, the magic of coding !!
        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()

        // create rectange from utils given left top width and height
        val shapeBounds = RectFFactory.fromLTWH(0f, 0f, width, height)

        val paint = Paint()
        paint.color = color

        //draw the painting finally
        canvas.drawRect(shapeBounds, paint)
    }

}