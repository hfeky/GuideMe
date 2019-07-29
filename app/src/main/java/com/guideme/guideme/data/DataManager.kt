package com.guideme.guideme.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

class DataManager {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val source = Source.DEFAULT

}
