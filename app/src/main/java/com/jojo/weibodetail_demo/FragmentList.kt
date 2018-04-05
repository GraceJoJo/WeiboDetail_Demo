package com.jojo.weibodetail_demo

import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by JoJo on 2018/4/5.
wechat：18510829974
description：
 */
class FragmentList : BaseFragment() {
    override fun initEvents() {
        var mDatas = ArrayList<String>()
        for (i in 0..30) {
            mDatas.add("数据--" + i)
        }
        var mAdapter = MyListAdapter(this!!.activity, mDatas)
        listview.adapter = mAdapter
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_list
    }
}