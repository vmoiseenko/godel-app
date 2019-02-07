package com.godeltech.simpleapp.di.component

import com.godeltech.simpleapp.di.module.DataModule
import dagger.Component

@Component(modules = [DataModule::class])
interface DataComponent