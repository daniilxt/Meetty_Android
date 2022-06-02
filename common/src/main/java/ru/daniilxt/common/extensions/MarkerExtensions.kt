package ru.daniilxt.common.extensions

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import timber.log.Timber

fun Marker.loadIcon(context: Context, logoLink: String) {
    val loader = ImageLoader(context)
    val req = ImageRequest.Builder(context)
        .data(logoLink)
        .size(context.dpToPx(40F).toInt(), context.dpToPx(40F).toInt())
        .listener { request, metadata ->
            Timber.i("Error loading $request  $metadata")
        }
        .target { result ->
            val bitmap = (result as BitmapDrawable).bitmap
            val bitmapDescription = BitmapDescriptorFactory.fromBitmap(bitmap)
            this.setIcon(bitmapDescription)
        }
        .build()
    val disposable = loader.enqueue(req)
}


