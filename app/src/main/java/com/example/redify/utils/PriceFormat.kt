package com.example.redify.utils

import java.text.DecimalFormat

object PriceFormat {
    fun thousandSeparator(number: Int): String {
        val decimalFormat = DecimalFormat("#,###.###")
        decimalFormat.isDecimalSeparatorAlwaysShown = false
        return decimalFormat.format(number).replace(",", ".")
    }
}