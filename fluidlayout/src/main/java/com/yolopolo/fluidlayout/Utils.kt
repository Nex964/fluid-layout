package com.yolopolo.fluidlayout

import android.graphics.RectF
import android.util.Log
import android.view.View

fun log(message: Any){
    Log.d("nex964", message.toString())
}

fun View.getRectF(): RectF{
    return RectF(this.left.toFloat() + this.translationX, this.top.toFloat(), this.right.toFloat() + this.translationX, this.bottom.toFloat())
}