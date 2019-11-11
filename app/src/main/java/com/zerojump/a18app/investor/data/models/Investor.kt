package com.zerojump.a18app.investor.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Investor(
    @Transient
    val mNama : String?=null,
    val mAlamat : String?=null,
    val mPitcure : String?=null,
    val mNohandphone : String? = null

) : Parcelable