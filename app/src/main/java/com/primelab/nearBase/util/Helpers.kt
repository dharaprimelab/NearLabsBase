package com.primelab.nearBase.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import com.primelab.nearBase.extensions.makeLinks
import java.text.SimpleDateFormat
import java.util.*

class Helpers {
    companion object {

        fun checkEmailPhone(text: String, usingEmail: Boolean): Boolean {
            return if (usingEmail) {
                val isLegitEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
                (text.isNotBlank() && isLegitEmail)
            } else {
                val isLegitNumber = android.util.Patterns.PHONE.matcher(text).matches()
                (text.isNotBlank() && isLegitNumber)
            }
        }

        fun setTermsConditions(textView: TextView, context: Context) {
            textView.makeLinks(Pair("Terms & Conditions", View.OnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse("https://terms.nftmakerapp.io/")
                context.startActivity(browserIntent)
            }), Pair("Privacy Policy", View.OnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse("https://privacy.nftmakerapp.io/")
                context.startActivity(browserIntent)
            }))
        }

        fun getDocumentUpdatedDate(dateInLong: Long): String{
            val date = Date(dateInLong)
            val format = SimpleDateFormat("yyyy/MM/dd hh:mm aa")
            return format.format(date)
        }

    }
}