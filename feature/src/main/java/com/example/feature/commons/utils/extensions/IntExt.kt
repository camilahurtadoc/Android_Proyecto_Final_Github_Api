package com.example.feature.commons.utils.extensions

import kotlin.math.log10
import kotlin.math.pow

object IntExt {
    fun Int.formatNumberToString(): String {
        val suffixes = arrayOf(EMPTY, THOUSAND, MILLION, BILLION, TRILLION)
        val index = (log10(this.toDouble()) / 3).coerceAtMost((suffixes.size - 1).toDouble()).toInt()
        val divisor = 10.0.pow(index * 3)
        val formattedNumber = (this / divisor).toInt()
        return formattedNumber.toString() + suffixes[index]
    }

    private const val EMPTY = ""
    private const val THOUSAND = "K"
    private const val MILLION = "M"
    private const val BILLION = "B"
    private const val TRILLION = "T"
}