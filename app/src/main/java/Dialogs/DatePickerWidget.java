package Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.Calendar;


public  class DatePickerWidget extends DialogFragment
        implements android.app.DatePickerDialog.OnDateSetListener {

    private int d;
    private int m;
    private int y;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new android.app.DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        setDate(year,month,day);
    }

    private void setDate(int year, int month, int day) {
//        Calendar.getInstance().set(year,mo
        d=day;m=month;y=year;

    }
    public String getDate(){
//        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MMM yyyy");
//
//        formatter2.
//        System.out.println(dateTime.format(formatter2));

        return String.valueOf(d)+"/"+String.valueOf(m)+"/"+String.valueOf(y);

    }
}