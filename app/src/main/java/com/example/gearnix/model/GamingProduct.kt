package com.example.gearnix.model

data class GamingProduct(
    var id: String = "",


    var timestamp: Long = System.currentTimeMillis()
) {
    // Empty constructor required for Firestore
    constructor() : this("", "", "", "", "", "", 0L)
}
