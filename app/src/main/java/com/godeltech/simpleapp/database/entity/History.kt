package com.godeltech.simpleapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(

    @PrimaryKey
    val date: Long,

    val url: String

)