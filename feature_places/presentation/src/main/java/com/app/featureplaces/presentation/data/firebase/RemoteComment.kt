package com.app.featureplaces.presentation.data.firebase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RemoteComment(
    var id: String,
    var idPlace: Int,
    var idUser: Int,
    var timeRegister: String,
    var nameUser: String,
    var commentText: String
): Parcelable {
    constructor() : this(
        id = "",
        idPlace = 0,
        idUser = 0,
        timeRegister = "",
        nameUser = "",
        commentText = ""
    )
}