package com.yolopolo.fluidlayout

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.core.view.children
import com.yolopolo.fluidlayout.getRectF
import com.yolopolo.fluidlayout.log
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class FluidLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var paint: Paint = Paint()
    private var objectList = ArrayList<RectF>()
    private var path = Path()

    private var size = 500f
    private var xPos = 150f
    private var yPos = 150f
    private var index = 0

    init {
        log("Initiated")

        setWillNotDraw(false)

        val corEffect = CornerPathEffect(40f) // 60 Ideal

//        paint.pathEffect = SumPathEffect(corEffect, DiscretePathEffect(20f, 5f))
        paint.pathEffect = corEffect
        paint.strokeJoin = Paint.Join.ROUND    // set the join to round you want
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        path.close()
    }

    fun updatePath(){
        path.reset()
        path.apply {
            children.forEach {
                val newPath = Path()

                newPath.addRect(it.getRectF(), Path.Direction.CW)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    this.op(newPath, Path.Op.UNION)
                }
                else{
                    this.addPath(newPath)
                }
            }
        }
        glue()

        invalidate()
    }

    fun glue(){

        for(i in 0 until children.count()){

            for(j in 0 until children.count()){

                var obj1 = children.elementAt(i).getRectF()
                var obj2 = children.elementAt(j).getRectF()

                if(obj1 == obj2) continue
                if(obj1.intersect(obj2)) continue

                val l = max(obj1.left, obj2.left)
                val r = min(obj1.right, obj2.right)
                var t = min(obj1.bottom, obj2.bottom)
                var b = max(obj1.top, obj2.top)

                val newL = l + abs(t - b)
                val newR = r - abs(t - b)

                if(t - b > 0){
                    t -= abs(l - r)
                    b += abs(l - r)
                }

                if (t - b > 0 || newR - newL > 0) {
                    val newPath = Path()
                    newPath.addRect(newL, t, newR, b, Path.Direction.CW)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        path.op(newPath, Path.Op.UNION)
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        xPos = (event?.rawX ?: 0f) - 250f
        yPos = (event?.rawY ?: 0f) - 250f

        if(event?.actionMasked == MotionEvent.ACTION_DOWN){
            children.forEachIndexed { i, it ->
                if(it.getRectF().contains(event.rawX ?:0f, event.rawY ?:0f)){
                    index = i
                }
            }
        }
        else{
            val view = children.elementAt(index)

            view.left = xPos.toInt()
            view.top = yPos.toInt()
            view.right = xPos.toInt() + view.measuredWidth
            view.bottom = yPos.toInt() + view.measuredHeight
        }

        updatePath()

        return true
    }

    private fun addRect(x: Float,y: Float){
        objectList.add(RectF(x,y, x + size, y + size))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawPath(path, paint)

//        updatePath()
//        invalidate()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, left, top, right, bottom)
        path.apply {
            children.forEach {
                val newPath = Path()
                log(it.getRectF())
                newPath.addRect(it.getRectF(), Path.Direction.CW)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    this.op(newPath, Path.Op.UNION)
                }
                else{
                    this.addPath(newPath)
                }
            }
        }

        glue()
    }

}