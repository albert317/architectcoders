package com.app.featureplaces.presentation.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


object Example {
    const val CURRENCY_LIST = "currencyList"
    const val CURRENCY_PM_LIST = "currencyPmList"
    const val DATA_REFERENCE_TEMPORAL = "temporal"

    fun referenceCurrenciesList(): DatabaseReference {
        val databaseFirebase = Firebase.database.reference
        return databaseFirebase.child(CURRENCY_LIST)
    }
}