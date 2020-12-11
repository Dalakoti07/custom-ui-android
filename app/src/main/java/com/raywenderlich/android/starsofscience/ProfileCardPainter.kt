package com.raywenderlich.android.starsofscience

import android.graphics.*
import androidx.annotation.ColorInt
import com.raywenderlich.android.starsofscience.utils.*

class ProfileCardPainter (@ColorInt private val color:Int,private val avatarRadius: Float,
                          private val avatarMargin: Float) :Painter{
    override fun paint(canvas: Canvas) {
        // this canvas is same as which needed to be painted, the magic of coding !!
        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()

        // create rectange from utils given left top width and height
        val shapeBounds = RectFFactory.fromLTWH(0f, 0f, width, height-avatarRadius)

        // You create a PointF object for the center point of the avatar, where x is the shapeBounds.centerX()
        // and y is the bottom of the shapeBounds.
        val centerAvatar = PointF(shapeBounds.centerX(), shapeBounds.bottom)

        // Then, you create a RectF object from the avatar circle using fromCircle(). The center is centerAvatar,
        // which you just created, and the radius is the avatarRadius.
        val avatarBounds = RectFFactory.fromCircle(center = centerAvatar, radius = avatarRadius).inflate(avatarMargin)

        // Finally, you call drawBackground() and pass the canvas with rest of the parameters to draw your first path.
        drawBackground(canvas, shapeBounds, avatarBounds)

        //Create a RectF that is similar to the shapeBounds rect, except you've shifted its top slightly to the
        // bottom by 35% of the shapeBounds' height: This is the red dashed RectF in the image above.
        val curvedShapeBounds = RectFFactory.fromLTRB(
                shapeBounds.left,
                shapeBounds.top + shapeBounds.height() * 0.35f,
                shapeBounds.right,
                shapeBounds.bottom
        )
        // Call drawCurvedShape() and pass the canvas object, the curved shape bounds and the avatar bounds to it.
        drawCurvedShape(canvas, curvedShapeBounds, avatarBounds)
    }

    private fun drawBackground(canvas: Canvas, bounds: RectF, avatarBounds: RectF) {
        //You create a Paint object and set its color.
        val paint = Paint()
        paint.color = color

        // we are creating this https://koenig-media.raywenderlich.com/uploads/2020/01/custom_shape1-457x320.png

        // Then, you create a Path object.
        val backgroundPath = Path().apply {
            // You move to the top-left corner, P1, without drawing a line.
            // This is like moving a pencil to a starting point without touching the paper.
            moveTo(bounds.left, bounds.top)

            // Next, you add a straight line that starts at P1 and ends at P2.
            lineTo(bounds.bottomLeft.x, bounds.bottomLeft.y)

            // Then, you add a straight line that starts at P2 and ends at P3:
            // The point at the edge of where you will start drawing the arc.
            lineTo(avatarBounds.centerLeft.x, avatarBounds.centerLeft.y)

            // Then, starting from P3, add an arc in the upper half region of the avatar bounds: T
            // he arc starts from the angle -180 degrees and sweeps by 180 degrees ending at P4.
            //You pass false as the last parameter to prevent starting a new sub-path for the arc. This tells Android that you want the arc on the same path.
            arcTo(avatarBounds, -180f, 180f, false)

            // Next, you add a straight line that starts from the current point and ends at P5 at the bottom-right corner.
            lineTo(bounds.bottomRight.x, bounds.bottomRight.y)

            // You finish by adding a straight line that starts from the current point P5 and ends
            // at the given point P6 at the top-right corner.
            lineTo(bounds.topRight.x, bounds.topRight.y)

            // Then you close the path by adding a straight line that starts at the current point P6 and ends at the beginning point on the path, P1.
            close()
        }

        //drawing the path
        canvas.drawPath(backgroundPath, paint);
    }

    private fun drawCurvedShape(canvas: Canvas, bounds: RectF, avatarBounds: RectF) {
        // we are creating this https://koenig-media.raywenderlich.com/uploads/2020/05/custom_shape2_black_dashed-449x320.png

        // You create a Paint object and set its color to a darker shade of the profile color.
        val paint = Paint()
        paint.color = color.darkerShade()

        // Then, you create a handle point at the top left corner of the RectF, shifted to the
        // right by 25% of the width of the RectF. This is P6 in the guide image.
        val handlePoint = PointF(bounds.left + (bounds.width() * 0.25f), bounds.top)

        // you create path object
        val curvePath = Path().apply {

            moveTo(bounds.bottomLeft.x, bounds.bottomLeft.y)

            lineTo(avatarBounds.centerLeft.x, avatarBounds.centerLeft.y)

            arcTo(avatarBounds, -180f, 180f, false)

            lineTo(bounds.bottomRight.x, bounds.bottomRight.y)

            lineTo(bounds.topRight.x, bounds.topRight.y)

            // You add a quadratic Bézier curve that starts from the current point, P5,
            // and ends at the bottom-left corner, P1, using the handle point you created in step two.
            quadTo(handlePoint.x, handlePoint.y, bounds.bottomLeft.x, bounds.bottomLeft.y)

            // closing the path
            close()
        }

        // drawing the path
        canvas.drawPath(curvePath, paint)
    }


}