package com.zerojump.a18app.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @Transient
    val mNama : String?=null,
    val mEmail : String?=null,
    val mAlamat : String?=null,
    val mPitcure : String?=null,
    val mNohandphone : String? = null,
    val mSebagai : Int? = null

) : Parcelable