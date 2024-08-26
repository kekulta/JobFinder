package tech.kekulta.jobfinder

import android.app.Application
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tech.kekulta.jobfinder.di.appModules

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
        startKoin {
            androidContext(this@App)
            modules(appModules)
        }
    }
}