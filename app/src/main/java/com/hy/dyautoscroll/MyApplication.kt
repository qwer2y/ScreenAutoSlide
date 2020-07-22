package com.hy.dyautoscroll

import android.app.Application
import com.lzf.easyfloat.EasyFloat

open class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()


        EasyFloat.init(this,true)


    }

}