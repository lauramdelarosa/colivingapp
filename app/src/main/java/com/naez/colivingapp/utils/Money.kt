package com.naez.colivingapp.utils

import java.text.NumberFormat
import java.util.*

class Money {

    companion object {

        fun format(amount: Int, isoCurrency: String): Float {
            return tryCurrency(isoCurrency) {
                val currency = Currency.valueOf(it)
                when (currency) {
                    Currency.COP -> amount / 1f
                }
            }
        }

        fun parse(amountString: String, isoCurrency: String): Int {
            val amount: Float =
                try {
                    amountString
                        .replace("$", "")
                        .replace(",", "")
                        .trim()
                        .toFloat()
                } catch (ex: NumberFormatException) {
                    0f
                }
            return tryCurrency(isoCurrency) {
                when (Currency.valueOf(it)) {
                    Currency.COP -> (amount * 100).toInt()
                }
            }
        }

        private fun <T> tryCurrency(isoCurrency: String, block: (isoCurrency: String) -> T): T {
            try {
                return block(isoCurrency)
            } catch (ex: IllegalArgumentException) {
                throw Exception("The currency $isoCurrency is not supported")
            }
        }

    }

    enum class Currency { COP }

}

fun Float.currencyFormat(): String {
    val amountCurrency = NumberFormat.getCurrencyInstance(Locale.US).format(this)
    return amountCurrency.replace(".00", "")
}