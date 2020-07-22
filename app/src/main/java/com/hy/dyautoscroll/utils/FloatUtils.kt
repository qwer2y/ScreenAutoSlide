package com.hy.dyautoscroll.utils

import android.app.Activity
import android.view.Gravity
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.anim.AppFloatDefaultAnimator
import com.lzf.easyfloat.anim.DefaultAnimator
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.enums.SidePattern
import com.lzf.easyfloat.interfaces.OnDisplayHeight
import com.lzf.easyfloat.interfaces.OnInvokeView
import com.lzf.easyfloat.utils.DisplayUtils


public fun getNormalFloat(activity:Activity,layoutId: Int, invokeView: OnInvokeView? = null): EasyFloat.Builder {
    return EasyFloat.with(activity)
        // 设置浮窗xml布局文件，并可设置详细信息
        .setLayout(layoutId,invokeView)
        // 设置浮窗显示类型，默认只在当前Activity显示，可选一直显示、仅前台显示、仅后台显示
        .setShowPattern(ShowPattern.ALL_TIME)
        // 设置吸附方式，共15种模式，详情参考SidePattern
        .setSidePattern(SidePattern.RESULT_HORIZONTAL)
        // 设置浮窗的标签，用于区分多个浮窗
        .setTag("testFloat")
        // 设置浮窗是否可拖拽，默认可拖拽
        .setDragEnable(true)
        // 系统浮窗是否包含EditText，仅针对系统浮窗，默认不包含
        .hasEditText(false)
        // 设置浮窗固定坐标，ps：设置固定坐标，Gravity属性和offset属性将无效
        .setLocation(100, 200)
        // 设置浮窗的对齐方式和坐标偏移量
        .setGravity(Gravity.END or Gravity.CENTER_VERTICAL, 0, 200)
        // 设置宽高是否充满父布局，直接在xml设置match_parent属性无效
        .setMatchParent(widthMatch = false, heightMatch = false)
        // 设置Activity浮窗的出入动画，可自定义，实现相应接口即可（策略模式），无需动画直接设置为null
        .setAnimator(DefaultAnimator())
        // 设置系统浮窗的出入动画，使用同上
        .setAppFloatAnimator(AppFloatDefaultAnimator())
        // 设置系统浮窗的不需要显示的页面
//        .setFilter(MainActivity::class.java, SecondActivity::class.java)
        // 设置系统浮窗的有效显示高度（不包含虚拟导航栏的高度），基本用不到，除非有虚拟导航栏适配问题
        .setDisplayHeight(OnDisplayHeight { context -> DisplayUtils.rejectedNavHeight(context) })
        // 浮窗的一些状态回调，如：创建结果、显示、隐藏、销毁、touchEvent、拖拽过程、拖拽结束。
        // ps：通过Kotlin DSL实现的回调，可以按需复写方法，用到哪个写哪个
        .registerCallback {
            createResult { isCreated, msg, view ->  }
            show {  }
            hide {  }
            dismiss {  }
            touchEvent { view, motionEvent ->  }
            drag { view, motionEvent ->  }
            dragEnd {  }
        }
}