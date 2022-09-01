package com.app.featureplaces.presentation.ui.common

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.app.presentation.databinding.DialogAddReviewBinding
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.showCustomDialog(listener: (String) -> Unit) {
    val dialog = Dialog(requireContext())
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    val binding: DialogAddReviewBinding =
        DialogAddReviewBinding.inflate(LayoutInflater.from(requireContext()))
    dialog.setContentView(binding.root)

    val lp = WindowManager.LayoutParams()
    lp.copyFrom(dialog.window!!.attributes)
    lp.width = WindowManager.LayoutParams.WRAP_CONTENT
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT

    binding.btCancel.setOnClickListener { dialog.dismiss() }
    binding.btSubmit.setOnClickListener {
        listener(binding.postEditText.text.toString())
        dialog.dismiss()
    }
    dialog.show()
    dialog.window!!.attributes = lp
}

fun getDateTime() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date()) ?: ""

fun loadBitmapFromView(v: View): Bitmap? {
    val b: Bitmap = Bitmap.createBitmap(
        v.layoutParams.width,
        v.layoutParams.height,
        Bitmap.Config.ARGB_8888
    )
    val c = Canvas(b)
    v.layout(v.left, v.top, v.right, v.bottom)
    v.draw(c)
    return b
}