package ru.daniilxt.feature.template

import android.annotation.SuppressLint
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@SuppressLint("NewApi")
object Utils {
    fun createFile(path: String, fileName: String, func: String) {
        Files.createDirectories(Paths.get(path))
        File("$path/$fileName").printWriter().use { out ->
            out.println(func)
        }
    }

    fun String.toSnakeCase() = this.map {
        if (it.isUpperCase()) "_${it.lowercaseChar()}" else "$it"
    }.joinToString(separator = "")
}
