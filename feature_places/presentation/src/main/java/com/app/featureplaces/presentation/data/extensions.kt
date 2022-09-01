package com.app.featureplaces.presentation.data

import android.widget.ImageView
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.app.domain.Error
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toError(): Error = when (this) {
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "")
}

suspend fun <T> tryCall(action: suspend () -> T): Either<Error, T> = try {
    action().right()
} catch (e: Exception) {
    e.toError().left()
}

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).transition(DrawableTransitionOptions.withCrossFade()).transform(CenterCrop(), RoundedCorners(8)).into(this)
}