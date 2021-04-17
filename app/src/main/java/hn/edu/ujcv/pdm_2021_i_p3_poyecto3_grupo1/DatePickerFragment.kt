package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.app.DatePickerDialog
import androidx.fragment.app.DialogFragment
import android.app.Dialog
import android.content.Context
import android.widget.DatePicker
import android.os.Bundle
import java.util.*

class DatePickerFragment (val listener: (day:Int, month:Int, year:Int)-> Unit): DialogFragment(),
        DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val picker = DatePickerDialog(activity as Context, this, year, month, day)
        return picker
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth, month, year)
    }
}