package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class DatePickerFragment extends DialogFragment {

  private EntryDetailsSharedViewModel sharedVm;
  private static final String TAG = "DatePickerFragment";
  private OnDialogCloseListener listener;

  public DatePickerFragment(OnDialogCloseListener listener) {
    this.listener = listener;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    sharedVm = new ViewModelProvider(getActivity()).get(EntryDetailsSharedViewModel.class);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @NonNull
  public static DatePickerFragment newInstance(Date date, OnDialogCloseListener listener) {
    DatePickerFragment fragment = new DatePickerFragment(listener);
    Bundle args = new Bundle();
    args.putInt("Year", date.getYear() + 1900);
    args.putInt("Month", date.getMonth());
    args.putInt("Date", date.getDate());
    fragment.setArguments(args);
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Bundle bundle = this.getArguments();
    return new DatePickerDialog(requireContext(), (dp, y, m, d) -> {
      // store the date in the shared view model
      sharedVm.storeDate(y, m, d);
      listener.onDateDialogClose();
    },bundle.getInt("Year"),bundle.getInt("Month"),bundle.getInt("Date"));
  }
}
