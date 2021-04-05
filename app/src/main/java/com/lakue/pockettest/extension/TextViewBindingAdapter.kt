package com.lakue.pockettest.extension

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lakue.pockettest.utils.LogUtil
import kotlin.math.max

/**
 * TextView Adapter
 */
@BindingAdapter("keyword","name")
fun TextView.setSpanText(keyword: String, name: String){
    //키워드가 포함되었을 경우
    if(name.contains(keyword)){
        var span = SpannableStringBuilder(name)
        var startIdx = max(name.indexOf(keyword),0)     //keyword 시작 Idx
        var endIdx = startIdx + keyword.length              //keyword 끝 Idx
        span.setSpan(BackgroundColorSpan(Color.YELLOW), startIdx, endIdx, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        this.text = span
    } else {
        this.text = name
    }
}