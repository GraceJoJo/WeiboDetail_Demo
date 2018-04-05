package com.jojo.weibodetail_demo.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * Created by JoJo on 2018/4/5.
wechat：18510829974
description： 解决ScrollView.setOnScrollChangeListener() API23以上可用问题,自定义一个接口将onScrollChanged()暴露出来
 */
class MyScrollView : ScrollView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        mListener!!.onScrollChanged(l, t, oldl, oldt)
    }

    var mListener: OnScrollViewListener? = null

    interface OnScrollViewListener {
        fun onScrollChanged(scrollX: Int, scrollY: Int, oldx: Int, oldY: Int)
    }

    public fun setOnScrollViewListener(listener: OnScrollViewListener) {
        mListener = listener
    }
}