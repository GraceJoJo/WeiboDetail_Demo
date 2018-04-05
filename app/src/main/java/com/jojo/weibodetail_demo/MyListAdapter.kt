package com.jojo.weibodetail_demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by JoJo on 2018/4/5.
wechat：18510829974
description：
 */
class MyListAdapter(internal var mContext: Context, datas: List<String>) : BaseAdapter() {
    var mDatas = ArrayList<String>()

    init {
        mDatas = datas as ArrayList<String>
    }


    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getItem(p0: Int): Any {
        return mDatas.get(p0)
    }

    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder?
        var itemView: View? = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false)
            holder = ViewHolder(itemView)
            itemView.setTag(holder)
        } else {
            holder = itemView!!.getTag() as ViewHolder
        }
        holder.title!!.text = mDatas.get(position)
        return itemView!!
    }

    class ViewHolder(convertView: View) {
        var title: TextView? = null

        init {
            title = convertView.findViewById(R.id.tv)
        }
    }
}