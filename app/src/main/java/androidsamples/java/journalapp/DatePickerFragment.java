package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
  private static final String ARG_INITIAL_DATE = "initial_date";
  private Date selectedDate;

  @NonNull
  public static DatePickerFragment newInstance(Date date) {
    Bundle args = new Bundle();
    args.putSerializable(ARG_INITIAL_DATE, date);

    DatePickerFragment fragment = new DatePickerFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Retrieve the initial date from arguments
    if (getArguments() != null) {
      selectedDate = (Date) getArguments().getSerializable(ARG_INITIAL_DATE);
    }

    // Use the current date as default if no initial date is provided
    final Calendar calendar = Calendar.getInstance();
    if (selectedDate != null) {
      calendar.setTime(selectedDate);
    }

    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    // Create and return the DatePickerDialog
    return new DatePickerDialog(requireContext(), (dp, y, m, d) -> {
      // Handle the selected date
      Calendar selectedCalendar = Calendar.getInstance();
      selectedCalendar.set(y, m, d);
      selectedDate = selectedCalendar.getTime();

    }, year, month, day);
  }

  @Override
  public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
    EntryDetailsViewModel.setSelectedDate(year + "-" + (month + 1) + "-" + dayOfMonth);
  }
}
