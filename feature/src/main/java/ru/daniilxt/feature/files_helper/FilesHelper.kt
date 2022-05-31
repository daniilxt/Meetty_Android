package ru.daniilxt.feature.files_helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.time.LocalDateTime

object FilesHelper {
    const val CHILD_DIR = "uploads"
    private val TAG = FilesHelper::class.simpleName

    private fun createCacheFolder(context: Context): File {
        return File(context.cacheDir, CHILD_DIR).also { cacheDir ->
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
        }
    }

    private fun getUriType(context: Context, uri: Uri) = context.contentResolver.getType(uri)

    private fun getExtensionFromUri(context: Context, uri: Uri) =
        MimeTypeMap.getSingleton().getExtensionFromMimeType(getUriType(context, uri))

    @SuppressLint("NewApi")
    fun copySelectedFile(context: Context, uri: Uri, createdFileCallback: (File) -> Unit) {
        val fileExtension = getExtensionFromUri(context, uri)
        val fileName = "file_temp" + LocalDateTime.now().toString()
        val cachePath = createCacheFolder(context)

        val filePath = "$cachePath/$fileName.$fileExtension"
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val out = FileOutputStream(filePath)
        val buf = ByteArray(1024)
        var len: Int
        if (inputStream != null) {
            while (inputStream.read(buf).also { len = it } > 0) {
                out.write(buf, 0, len)
            }
        }
        out.close()
        inputStream?.close()
        val createdFile = File(filePath)
        if (createdFile.exists()) {
            createdFileCallback(createdFile)
        }
    }

    fun createFileFromResultBody(
        parentPath: String = "",
        fileNameWithExtension: String,
        body: ResponseBody
    ): File {
        val file = File(fileNameWithExtension)
        body.byteStream().use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                val data = ByteArray(8192)
                var read: Int
                var progress = 0L
                while (inputStream.read(data).also { read = it } != -1) {
                    outputStream.write(data, 0, read)
                    progress += read
                }
            }
        }
        return file
    }

    fun copyFile() {
    }
}
