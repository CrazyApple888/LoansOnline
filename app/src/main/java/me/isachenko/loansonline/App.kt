package me.isachenko.loansonline

import android.app.Application
import me.isachenko.loansonline.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            koin.loadModules(listOf(appModule))
        }
    }

}