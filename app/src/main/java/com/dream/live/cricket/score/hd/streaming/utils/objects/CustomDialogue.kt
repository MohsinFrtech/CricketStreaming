package com.dream.live.cricket.score.hd.streaming.utils.objects

import android.content.Context
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.DialogListener

class CustomDialogue(var dialogListener: DialogListener) {

    fun showDialog(
        context: Context?, title: String?, message: String?,
        positiveText: String?, negativeText: String?,key: String
    ) {
        context?.let {
            androidx.appcompat.app.AlertDialog.Builder(it)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                    positiveText
                ) { _, _ -> dialogListener.onPositiveDialogText(key) }
                .setNegativeButton(
                    negativeText
                ) { _, _ -> dialogListener.onNegativeDialogText(key) }
                .show()
        }

    }

}