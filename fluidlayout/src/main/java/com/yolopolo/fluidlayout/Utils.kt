package com.yolopolo.fluidlayout

import android.graphics.RectF
import android.util.Log
import android.view.View

/**
 * Converts passed object to string and logs to debug.
 * param [message] [Any] type of object which can we converted to string
 */
fun log(message: Any){
    if(BuildConfig.DEBUG) Log.d("nex964", message.toString())
}

fun View.getRectF(): RectF{
    return RectF(this.left.toFloat() + this.translationX, this.top.toFloat(), this.right.toFloat() + this.translationX, this.bottom.toFloat())
}