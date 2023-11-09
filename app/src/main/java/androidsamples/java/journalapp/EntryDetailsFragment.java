package androidsamples.java.journalapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment {
  private EntryDetailsViewModel EDVM;
  private Calendar selectedTimeCalendar; // Variable to store the selected time
  private Calendar selectedDateCalendar; // Variable to store the selected date
  private TextView txtSelectedDate, txtSelectedTime;
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EDVM = new ViewModelProvider(this).get(EntryDetailsViewModel.class);
    setHasOptionsMenu(true);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_details, container, false);

//    txtSelectedDate = view.findViewById(R.id.txt_selected_date);
//    txtSelectedTime = view.findViewById(R.id.txt_selected_time);

    Button dateButton = view.findViewById(R.id.btn_entry_date);
    dateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Navigate to DatePickerFragment when the button is clicked
        Navigation.findNavController(v).navigate(R.id.datePickerAction);
      }
    });

    // Initialize the selected time and date calendar with the current time
    selectedDateCalendar = Calendar.getInstance();
    selectedTimeCalendar = Calendar.getInstance();

    Button startTimeButton = view.findViewById(R.id.btn_start_time);
    startTimeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Navigate to TimePickerFragment with the selected time
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedTime", selectedTimeCalendar);
        Navigation.findNavController(v).navigate(R.id.timePickerAction, bundle);
      }
    });

    Button endTimeButton = view.findViewById(R.id.btn_end_time);
    endTimeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Navigate to TimePickerFragment with the selected time
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedTime", selectedTimeCalendar);
        Navigation.findNavController(v).navigate(R.id.timePickerAction, bundle);
      }
    });

//    Button saveButton = view.findViewById(R.id.btn_save);
//    saveButton.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        // Perform saving logic here
//        saveEntry();
//
//        // Navigate back to entryListFragment after saving
//        Navigation.findNavController(v).navigateUp();
//      }
//    });

    return view;
  }

//  // Method to save the entry details
//  private void saveEntry() {
//    String entryTitle = ((EditText) getView().findViewById(R.id.edit_title)).getText().toString();
//    String selectedDateString = DateFormat.getDateInstance().format(selectedDateCalendar.getTime());
//    String selectedTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(selectedTimeCalendar.getTime());
//
//    // Example: Log the entry details
//    Log.d("EntryDetailsFragment", "Title: " + entryTitle);
//    Log.d("EntryDetailsFragment", "Date: " + selectedDateString);
//    Log.d("EntryDetailsFragment", "Time: " + selectedTimeString);
//  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
}