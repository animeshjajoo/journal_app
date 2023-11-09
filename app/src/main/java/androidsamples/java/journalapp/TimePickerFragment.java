package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {
  private static final String ARG_INITIAL_TIME = "initial_time";
  private Date selectedTime;

  @NonNull
  public static TimePickerFragment newInstance(Date time) {
    Bundle args = new Bundle();
    args.putSerializable(ARG_INITIAL_TIME, time);

    TimePickerFragment fragment = new TimePickerFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Additional initialization if needed
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    // Retrieve the initial time from arguments
    if (getArguments() != null) {
      selectedTime = (Date) getArguments().getSerializable(ARG_INITIAL_TIME);
    }

    // Use the current time as default if no initial time is provided
    final Calendar calendar = Calendar.getInstance();
    if (selectedTime != null) {
      calendar.setTime(selectedTime);
    }

    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);

    // Create and return the TimePickerDialog
    return new TimePickerDialog(requireContext(), (tp, hourOfDay, minuteOfDay) -> {
      // Handle the selected time
      Calendar selectedCalendar = Calendar.getInstance();
      selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
      selectedCalendar.set(Calendar.MINUTE, minuteOfDay);
      selectedTime = selectedCalendar.getTime();

    }, hour, minute, false);
  }
}
