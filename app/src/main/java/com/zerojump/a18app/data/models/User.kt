package com.zerojump.a18app.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @Transient
    val m_nama : String?=null,
    val m_email : String?=null,
    val m_alamat : String?=null,
    val m_pitcure : String?=null,
    val m_nohandphone : String? = null,
    val m_sebagai : Int? = null

) : Parcelable