package com.example.socialapp

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.Button
import android.widget.TextView


class Utils {
    companion object {
        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS

        fun getTimeAgo(time: Long): String? {
            val now: Long = System.currentTimeMillis()
            if (time > now || time <= 0) {
                return null
            }

            val diff = now - time
            return if (diff < MINUTE_MILLIS) {
                "just now"
            } else if (diff < 2 * MINUTE_MILLIS) {
                "a minute ago"
            } else if (diff < 50 * MINUTE_MILLIS) {
                (diff / MINUTE_MILLIS).toString() + " minutes ago"
            } else if (diff < 90 * MINUTE_MILLIS) {
                "an hour ago"
            } else if (diff < 24 * HOUR_MILLIS) {
                (diff / HOUR_MILLIS).toString() + " hours ago"
            } else if (diff < 48 * HOUR_MILLIS) {
                "yesterday"
            } else {
                (diff / DAY_MILLIS).toString() + " days ago"
            }
        }

        fun showAlert(
            activity: Activity?,
            title: String?,
            desc: String?,
            alertClick: AlertClick
        ): Dialog? {
            var dialog: Dialog? = null
            try {
                dialog = Dialog(activity!!)
                dialog.setCancelable(false)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.alert_temp)
                dialog.window?.setBackgroundDrawableResource(R.color.transparent)
                val txt1: TextView = dialog.findViewById(R.id.txt1)
                val txt2: TextView = dialog.findViewById(R.id.txt2)
                val bt1: Button = dialog.findViewById(R.id.bt1)
                val bt2: Button = dialog.findViewById(R.id.bt2)
                txt1.text = title
                txt2.text = desc
                val finalDialog: Dialog = dialog
                bt1.setOnClickListener{
                    try {
                        alertClick.yes()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    finalDialog.dismiss()
                }
                bt2.setOnClickListener{
                    try {
                        alertClick.no()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    finalDialog.dismiss()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return dialog
        }
    }
}