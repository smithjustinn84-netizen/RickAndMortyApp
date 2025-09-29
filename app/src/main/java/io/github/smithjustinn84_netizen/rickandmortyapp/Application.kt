package io.github.smithjustinn84_netizen.rickandmortyapp

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import dagger.hilt.android.HiltAndroidApp

/**
 * The main [Application] class for the Rick and Morty application.
 * This class is annotated with [HiltAndroidApp] to enable Hilt for dependency injection.
 */
@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        setStrictModePolicy()
    }

    /**
     * Return true if the application is debuggable.
     */
    private fun isDebuggable(): Boolean {
        return 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
    }

    /**
     * Set a thread policy that detects all potential problems on the main thread, such as network
     * and disk access.
     *
     * If a problem is found, the offending call will be logged and the application will be killed.
     */
    private fun setStrictModePolicy() {
        if (isDebuggable()) {
            StrictMode.setThreadPolicy(
                Builder().detectAll().penaltyLog().build(),
            )
        }
    }
}
