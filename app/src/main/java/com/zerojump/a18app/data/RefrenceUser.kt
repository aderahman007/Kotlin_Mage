package com.zerojump.a18app.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

object RefrenceUser {
    fun refUser(mFirestore: FirebaseFirestore): CollectionReference {
        return mFirestore.collection("users")
    }
}