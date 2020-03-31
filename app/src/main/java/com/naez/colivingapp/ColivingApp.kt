package com.naez.colivingapp

import android.app.Application
import com.naez.colivingapp.di.initDI

class ColivingApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}