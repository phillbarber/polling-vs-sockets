package com.github.phillbarber.job

import com.google.common.io.Resources
import java.io.File
import java.net.URISyntaxException

fun getFileFromClassPath(file: String): File {
    try {
        return File(Resources.getResource(file).toURI())
    } catch (e: URISyntaxException) {
        throw RuntimeException(e)
    }

}