package com.hy.dyautoscroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils
import com.hy.dyautoscroll.utils.getNormalFloat
import com.lzf.easyfloat.EasyFloat.Companion.showAppFloat
import com.lzf.easyfloat.interfaces.OnInvokeView
import com.lzf.easyfloat.interfaces.OnPermissionResult
import com.lzf.easyfloat.permission.PermissionUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getNormalFloat(this,R.layout.float_app, OnInvokeView {
            it.findViewById<RadioButton>(R.id.rb_on).setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    //TODO
                    ToastUtils.showShort("选中")
                }else{
                    ToastUtils.showShort("取消")
                }
            }
        })
    }

    private fun countDown(){
        val count = 89
      /*  Observable.interval(1, TimeUnit.SECONDS)
            .take(count.toLong())
            .map {
                count - it
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { time.text = "${it}S后重新获取" },
                onComplete = {
                    time.text = "重新获取"
                    ll_verifi.isClickable = true
                    time.setTextColor(ColorUtils.getColor(R.color.text_color_red))
                    time.background = ContextCompat.getDrawable(this,R.drawable.border_radius_verifycode_red)
                }
            )*/
    }

    /**
     * 检测浮窗权限是否开启，若没有给与申请提示框（非必须，申请依旧是EasyFloat内部内保进行）
     */
    private fun checkPermission(tag: String? = null) {
        if (PermissionUtils.checkPermission(this)) {
            if (tag == null) showAppFloat() else {}
        } else {
            AlertDialog.Builder(this)
                .setMessage("使用浮窗功能，需要您授权悬浮窗权限。")
                .setPositiveButton("去开启") { _, _ ->
                    if (tag == null) showAppFloat() else {}
                }
                .setNegativeButton("取消") { _, _ -> }
                .show()
        }
    }

    /**
     * 主动申请浮窗权限
     */
    private fun requestPermission() {
        PermissionUtils.requestPermission(this, object : OnPermissionResult {
            override fun permissionResult(isOpen: Boolean) {

            }
        })
    }
}