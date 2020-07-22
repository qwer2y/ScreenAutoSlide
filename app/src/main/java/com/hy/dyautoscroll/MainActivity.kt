package com.hy.dyautoscroll

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.blankj.utilcode.util.ToastUtils
import com.hy.dyautoscroll.config.TAG_FLOAT_GLOBAL
import com.hy.dyautoscroll.utils.getNormalFloat
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.EasyFloat.Companion.isShow
import com.lzf.easyfloat.interfaces.OnInvokeView
import com.lzf.easyfloat.interfaces.OnPermissionResult
import com.lzf.easyfloat.permission.PermissionUtils
import kotlinx.android.synthetic.main.activity_main.*


@SuppressLint("CheckResult")
class MainActivity : AppCompatActivity() {

    private var float: EasyFloat.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFloat()

        initListener()
    }


    private fun initListener() {
        toggle.checkedChanges().subscribe {
            if (it && !isShow(this, TAG_FLOAT_GLOBAL)!!) {
                showFloat()
            }else if (!it){
                EasyFloat.hideAppFloat(TAG_FLOAT_GLOBAL)
            }
        }
    }

    private fun initFloat() {
        float = getNormalFloat(this, R.layout.float_app, OnInvokeView {
            it.findViewById<TextView>(R.id.rb_on)
                .clicks().subscribe {
                    ToastUtils.showShort("float clicks")
                }

        }).registerCallback {
            show {
                ToastUtils.showShort("Float show")
            }
            hide {
                ToastUtils.showShort("Float hide")
            }
            dismiss {
                ToastUtils.showShort("Float dismiss")
            }
        }
    }

    private fun showFloat() {
        if (!PermissionUtils.checkPermission(this)) {
            val pDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .apply {
                    titleText = "需要权限"
                    contentText = "请在列表中找到DYAutoScroll并开启显示在其他应用上层选项"
                    setCancelable(false)
                    confirmText = "去开启"
                    setConfirmClickListener {
                        PermissionUtils.requestPermission(this@MainActivity,
                            object : OnPermissionResult {
                                override fun permissionResult(isOpen: Boolean) {
                                    if (isOpen) {
                                        if (float == null) {
                                            initFloat()
                                        }
                                        float?.show()
                                        toggle.isChecked = true
                                    } else {
                                        ToastUtils.showShort("请开启悬浮窗权限")
                                    }
                                }
                            })
                    }
                    show()
                }
        } else {
            if (float == null) {
                initFloat()
                float?.show()
            }else{
                EasyFloat.showAppFloat(TAG_FLOAT_GLOBAL)
            }
            toggle.isChecked = true
        }
    }

    private fun countDown() {
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


}