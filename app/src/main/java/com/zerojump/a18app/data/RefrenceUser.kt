package com.zerojump.a18app.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

object RefrenceUser {
    fun refUser(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("users")
    }
}