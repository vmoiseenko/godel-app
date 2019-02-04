package com.godeltech.simpleapp.utils

import androidx.test.espresso.IdlingResource.ResourceCallback
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor
import androidx.test.espresso.IdlingResource
import androidx.test.runner.lifecycle.Stage


open class WaitActivityIsResumedIdlingResource(private val activityToWaitClassName: String) : IdlingResource {

    private val instance: ActivityLifecycleMonitor = ActivityLifecycleMonitorRegistry.getInstance()

    @Volatile
    private var resourceCallback: ResourceCallback? = null
    internal var resumed = false

    private val isActivityLaunched: Boolean
        get() {
            val activitiesInStage = instance.getActivitiesInStage(Stage.RESUMED)
            for (activity in activitiesInStage) {
                if (activity.javaClass.name == activityToWaitClassName) {
                    return true
                }
            }
            return false
        }

    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        resumed = isActivityLaunched
        if (resumed && resourceCallback != null) {
            resourceCallback!!.onTransitionToIdle()
        }

        return resumed
    }

    override fun registerIdleTransitionCallback(resourceCallback: ResourceCallback) {
        this.resourceCallback = resourceCallback
    }
}