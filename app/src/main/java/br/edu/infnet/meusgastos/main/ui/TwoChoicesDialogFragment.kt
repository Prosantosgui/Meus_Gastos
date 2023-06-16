package br.edu.infnet.meusgastos.main.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import br.edu.infnet.meusgastos.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TwoChoicesDialogFragment(val listener: TwoChoicesAlertDialogFragmentListener) :
    DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let { fragmentActivity ->
            val builder = context?.let { itContext ->
                MaterialAlertDialogBuilder(itContext)
                    .setTitle(resources.getString(R.string.title_two_choices_alert_dialog))
                    .setMessage(resources.getString(R.string.supporting_text_two_choices_alert_dialog))
                    .setNegativeButton(resources.getString(R.string.decline_two_choices_alert_dialog)) { dialog, which ->
                        // Respond to negative button press
                        listener.onNegativeButtonClick()
                    }
                    .setPositiveButton(resources.getString(R.string.accept_two_choices_alert_dialog)) { dialog, which ->
                        // Respond to positive button press
                        listener.onPositiveButtonClick()
                    }
            }
            builder?.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}

interface TwoChoicesAlertDialogFragmentListener {
    fun onPositiveButtonClick()
    fun onNegativeButtonClick()
}