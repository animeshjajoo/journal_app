package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.sql.Time;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class TimePickerFragment extends DialogFragment {

  private OnDialogCloseListener listener;
  private EntryDetailsSharedViewModel sharedVm;

  public TimePickerFragment(OnDialogCloseListener listener) {
    this.listener = listener;
  }

  @NonNull
  public static TimePickerFragment newInstance(Date time, OnDialogCloseListener listener, char check) {
    TimePickerFragment fragment = new TimePickerFragment(listener);
    Bundle args = new Bundle();
    args.putInt("Hour", time.getHours());
    args.putInt("Minute", time.getMinutes());
    args.putChar("StartOrEnd", check);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sharedVm = new ViewModelProvider(getActivity()).get(EntryDetailsSharedViewModel.class);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    Bundle bundle = getArguments();
    return new TimePickerDialog(requireContext(), (tp, hm, m)->{
      if(bundle.getChar("StartOrEnd") == 's') {
        sharedVm.setStartTime(hm, m);
        listener.onStartTimeDialogClose();
      }
      else {
        sharedVm.setEndTime(hm, m);
        listener.onEndTimeDialogClose();
      }
    },bundle.getInt("Hour") , bundle.getInt("minute"), false);
  }
}
