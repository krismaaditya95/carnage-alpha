package com.snister.carnagealpha.core.utils

import java.text.NumberFormat
import java.util.Locale

class CurrencyFormatter{
    companion object{
        fun formatToRupiah(number: Long): String{
            val localeID =  Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            return numberFormat.format(number).toString()
        }
    }
}