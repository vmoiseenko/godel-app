package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.di.module.NetworkModule
import dagger.Component

@Component(modules = [NetworkModule::class])
interface NetworkComponent