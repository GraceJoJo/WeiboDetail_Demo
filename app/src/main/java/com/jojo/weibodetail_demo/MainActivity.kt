package com.jojo.weibodetail_demo

import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.jojo.weibodetail_demo.view.MyScrollView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*

class MainActivity : BaseActivity() {
    var mCurrentFragment: BaseFragment? = null
    var fragmentList: BaseFragment? = null
    var fragmentDetail: BaseFragment? = null
    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initEvents() {
        //解決ScrollView嵌套ListView时，（并且ListView上方还有其他UI控件）页面会自动滑动到顶部的问题
        iv_img.setFocusable(true)
        iv_img.setFocusableInTouchMode(true)
        iv_img.
                requestFocus()

        //添加默认显示的Fragment
        initFragment()

        scrollView.setOnScrollViewListener(object : MyScrollView.OnScrollViewListener {
            override fun onScrollChanged(scrollX: Int, scrollY: Int, oldx: Int, oldY: Int) {
                //如果向上滑动的距离>=iv_img.height - tv_title_text.height,隐藏的标题导航栏设置显示
                var distanceScrollY = iv_img.height - tv_title_text.height
                if (scrollY >= distanceScrollY) {
                    ll_sus_tab.visibility = View.VISIBLE
//                    ll_tab.visibility = View.INVISIBLE
                    title_divider.visibility = View.VISIBLE
                } else {
                    ll_sus_tab.visibility = View.INVISIBLE
//                    ll_tab.visibility = View.VISIBLE
                    title_divider.visibility = View.GONE
                }
                //设置标题栏渐变
                if (scrollY <= 0) {
                    //初始位置：未滑动时，设置标题背景透明
                    tv_title_text.setBackgroundColor(Color.TRANSPARENT)
                    tv_title_text.setTextColor(Color.WHITE)
                } else if (scrollY > 0 && scrollY <= distanceScrollY) {
                    var scale: Float = (scrollY.toFloat()) / distanceScrollY
                    var alpha: Float = 255 * scale
                    tv_title_text.setBackgroundColor(Color.argb(alpha.toInt(), 255, 255, 255))
                    tv_title_text.setTextColor(Color.argb(alpha.toInt(), 0, 0, 0))
                } else {
                    tv_title_text.setBackgroundColor(Color.argb(255, 255, 255, 255))
                    tv_title_text.setTextColor(Color.argb(255, 0, 0, 0))
                }
//
            }
        })
        //设置点击tab切换fragment
        setSelectTab()
    }

    private fun setSelectTab() {
        //点击主页
        ll_tab.findViewById<LinearLayout>(R.id.ll_tab_homepage)
                .setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        checkHomePage()
                    }
                })
        //点击微博
        ll_tab.findViewById<LinearLayout>(R.id.ll_tab_weibo)
                .setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        checkDetail()
                        listview.smoothScrollToPosition(0)
                    }
                })
        //悬浮标题点击
        ll_sus_tab.findViewById<LinearLayout>(R.id.ll_tab_homepage)
                .setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        checkHomePage()
                    }
                })
        ll_sus_tab.findViewById<LinearLayout>(R.id.ll_tab_weibo)
                .setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        checkDetail()
                    }
                })
    }

    //设置初始显示的Fragment
    private fun initFragment() {
        fragmentList = FragmentList()
        fragmentDetail = FragmentDetail()
        mCurrentFragment = fragmentList
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container, fragmentList)
        transaction.commitAllowingStateLoss()
    }

    /**
     * 选中首页
     */
    private fun checkHomePage() {
        switchContent(mCurrentFragment!!, fragmentList!!)

        ll_tab.findViewById<TextView>(R.id.tv_homepage).setTextColor(Color.parseColor("#e3ba97"))
        ll_tab.findViewById<TextView>(R.id.tv_weibo).setTextColor(Color.BLACK)
        ll_tab.findViewById<View>(R.id.view_homepage).visibility = View.VISIBLE
        ll_tab.findViewById<View>(R.id.view_weibo).visibility = View.INVISIBLE
        //悬浮导航栏选中效果
        ll_sus_tab.findViewById<TextView>(R.id.tv_homepage).setTextColor(Color.parseColor("#e3ba97"))
        ll_sus_tab.findViewById<TextView>(R.id.tv_weibo).setTextColor(Color.BLACK)
        ll_sus_tab.findViewById<View>(R.id.view_homepage).visibility = View.VISIBLE
        ll_sus_tab.findViewById<View>(R.id.view_weibo).visibility = View.INVISIBLE
    }

    /**
     * 选中微博
     */
    private fun checkDetail() {
        switchContent(mCurrentFragment!!, fragmentDetail!!)

        ll_tab.findViewById<TextView>(R.id.tv_weibo).setTextColor(Color.parseColor("#e3ba97"))
        ll_tab.findViewById<TextView>(R.id.tv_homepage).setTextColor(Color.BLACK)
        ll_tab.findViewById<View>(R.id.view_weibo).visibility = View.VISIBLE
        ll_tab.findViewById<View>(R.id.view_homepage).visibility = View.INVISIBLE
        //悬浮导航栏选中效果
        ll_sus_tab.findViewById<TextView>(R.id.tv_weibo).setTextColor(Color.parseColor("#e3ba97"))
        ll_sus_tab.findViewById<TextView>(R.id.tv_homepage).setTextColor(Color.BLACK)
        ll_sus_tab.findViewById<View>(R.id.view_weibo).visibility = View.VISIBLE
        ll_sus_tab.findViewById<View>(R.id.view_homepage).visibility = View.INVISIBLE
    }

    /**
     * Fragment的切换
     */
    fun switchContent(fromFragment: BaseFragment, toFragment: BaseFragment) {
        if (fromFragment != toFragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.hide(fromFragment)
            if (!toFragment!!.isAdded) {
                transaction.add(R.id.fl_container, toFragment)
            } else {
                transaction.show(toFragment)
            }
            transaction.commitAllowingStateLoss()
            mCurrentFragment = toFragment
        }
    }
}