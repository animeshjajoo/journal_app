package androidsamples.java.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
//import androidsamples.java.journalapp.EntryDetailsFragmentDirections.PutEntry;


import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment implements OnDialogCloseListener {

  private static final String TAG = "EntryDetailsFragment";
  private JournalEntry mEntry;
  private EntryDetailsSharedViewModel mEntryDetailsSharedViewModel;
  private EditText txtTitle;
  private Button btnSTime;
  private Button btnETime;
  private Button btnDate;
  private Button btnSave;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    mEntryDetailsSharedViewModel = new ViewModelProvider(getActivity()).get(EntryDetailsSharedViewModel.class);
    UUID entryId = UUID.fromString(EntryDetailsFragmentArgs.fromBundle(getArguments()).getEntryId());
    if(! (entryId == null)) {
      mEntryDetailsSharedViewModel.loadEntry(entryId);
      Log.d(TAG, "old uuid "+ entryId);
      mEntryDetailsSharedViewModel.setOldEntry(true); // for updating old entry on save
    }
    else {
      mEntry = new JournalEntry();
      Log.d(TAG, "New uuid "+mEntry.getMUid() + ", mentry : "+mEntry);
      mEntryDetailsSharedViewModel.setOldEntry(false); // for creating new entry on save
    }

    Log.d(TAG, "isOldEntry()"+ mEntryDetailsSharedViewModel.isOldEntry());
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_details, container, false);

    txtTitle = view.findViewById(R.id.edit_title);
    btnDate = view.findViewById(R.id.btn_entry_date);
    btnSTime = view.findViewById(R.id.btn_start_time);
    btnETime = view.findViewById(R.id.btn_end_time);
    btnSave = view.findViewById(R.id.btn_save);

    if(mEntryDetailsSharedViewModel.isOldEntry())
    {
      mEntryDetailsSharedViewModel.getEntryLiveData().observe(getActivity(),
              entry -> {
                this.mEntry = entry;
                if(this.mEntry != null) {
                  updateUI();
                  Log.d(TAG, "in observe in entrydetailsfragment and mentry is not null, " +mEntry.getMTitle());
                }
              });
    }
    btnSave.setOnClickListener(mEntryDetailsSharedViewModel.isOldEntry()? this::saveOldEntry: this::saveNewEntry);
    btnDate.setOnClickListener(this::setDate);
    btnSTime.setOnClickListener(this::setStartTime);
    btnETime.setOnClickListener(this::setEndTime);

    return view;
  }

  private void setEndTime(View view) {
    TimePickerFragment newFragment = TimePickerFragment.newInstance(new Date(), this, 'e');
    newFragment.show(getParentFragmentManager(), "endTimePicker");
  }

  private void setStartTime(View view) {
    TimePickerFragment newFragment = TimePickerFragment.newInstance(new Date(), this, 's');
    newFragment.show(getParentFragmentManager(), "startTimePicker");
  }

  // for menu
  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.entry_details_fragment_menus, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if(item.getItemId() == R.id.delete_entry) {
      if(mEntryDetailsSharedViewModel.isOldEntry())
        new DeleteEntryFragment(this).show(getParentFragmentManager(), "DELETE");
      else
        Toast.makeText(getContext(), "Save Entry Before Delete!", Toast.LENGTH_SHORT).show();

      return true;
    }

    if(item.getItemId() == R.id.share_event) {
      if(mEntryDetailsSharedViewModel.isOldEntry()) {
        String send = "Look what I have been up to: " + mEntry.getMTitle() + " on " + mEntry.getMDate() + ", " + mEntry.getMsTime() + " to " + mEntry.getMeTime();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, send);
        intent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(intent, getResources().getString(R.string.share_with));
        if(shareIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
          startActivity(shareIntent);
        }
        else {
          Log.d(TAG, "Not apps available to share");
        }

        return true;
      }
    }
    return super.onOptionsItemSelected(item);
  }


  @Override
  public void onDateDialogClose() {
    btnDate.setText(mEntryDetailsSharedViewModel.setDate());
  }

  @Override
  public void onStartTimeDialogClose() {
    setSTime();
  }

  private void setSTime() {
    btnSTime.setText(mEntryDetailsSharedViewModel.getSTime());
  }

  @Override
  public void onEndTimeDialogClose() {
    setETime();
  }

  @Override
  public void onDeleteEntryDialogClose() {
    mEntryDetailsSharedViewModel.deleteEntry(mEntry);
    NavDirections action = EntryDetailsFragmentDirections.onDeleteToEntryList();
    Navigation.findNavController(requireView()).navigate(action);
  }

  private void setETime() {
    btnETime.setText(mEntryDetailsSharedViewModel.getETime());
  }

  private void setDate(View view) {
    DatePickerFragment newFragment = DatePickerFragment.newInstance(new Date(), this);
    newFragment.show(getParentFragmentManager(), "datePicker");
  }


  private void updateUI() {
    txtTitle.setText(mEntry.getMTitle());
    btnDate.setText(mEntry.getMDate());
    btnSTime.setText(mEntry.getMsTime());
    btnETime.setText(mEntry.getMeTime());
  }

  private void saveNewEntry(View v) {
    if(txtTitle.getText().toString().equals("Title") || btnDate.getText().toString().equals("Date") || btnSTime.getText().toString().equals("Start Time") || btnETime.getText().toString().equals("End Time")) {
      Toast.makeText(getContext(), "Fill all entries", Toast.LENGTH_SHORT).show();
    }
    else {
      String sTime = btnSTime.getText().toString();
      String eTime = btnETime.getText().toString();

      int shr, sm, ehr, em;

      shr = Integer.parseInt(sTime.substring(0, sTime.indexOf(":")));
      sm = Integer.parseInt(sTime.substring(sTime.indexOf(":")+1));

      ehr = Integer.parseInt(eTime.substring(0, eTime.indexOf(":")));
      em = Integer.parseInt(eTime.substring(eTime.indexOf(":")+1));

      if(shr > ehr || ((shr == ehr) && (sm > em))) {
        Toast.makeText(getContext(), "EndTime must be greater than start time", Toast.LENGTH_SHORT).show();
      }
      else {
        mEntry.setMTitle(txtTitle.getText().toString());
        mEntry.setMDate(btnDate.getText().toString());
        mEntry.setMsTime(btnSTime.getText().toString());
        mEntry.setMeTime(btnETime.getText().toString());
        mEntryDetailsSharedViewModel.insertEntry(mEntry);

        Navigation.findNavController(v).navigate(EntryDetailsFragmentDirections.putEntry());
      }
    }
  }

  private void saveOldEntry(View v) {

    if(txtTitle.getText().toString().equals("") || btnDate.getText().toString().equals("") || btnSTime.getText().toString().equals("") || btnETime.getText().toString().equals("")) {
      Toast.makeText(getContext(), "Fill all entries", Toast.LENGTH_SHORT).show();
    }
    else{
      String sTime = btnSTime.getText().toString();
      String eTime = btnETime.getText().toString();

      int shr, sm, ehr, em;

      shr = Integer.parseInt(sTime.substring(0, sTime.indexOf(":")));
      sm = Integer.parseInt(sTime.substring(sTime.indexOf(":") + 1));

      ehr = Integer.parseInt(eTime.substring(0, eTime.indexOf(":")));
      em = Integer.parseInt(eTime.substring(eTime.indexOf(":") + 1));

      if (shr > ehr || ((shr == ehr) && (sm > em))) {
        Toast.makeText(getContext(), "EndTime must be greater than start time", Toast.LENGTH_SHORT).show();
      }
      else {
        mEntry.setMTitle(txtTitle.getText().toString());
        mEntry.setMDate(btnDate.getText().toString());
        mEntry.setMsTime(btnSTime.getText().toString());
        mEntry.setMeTime(btnETime.getText().toString());
        mEntryDetailsSharedViewModel.updateEntry(mEntry);
        Navigation.findNavController(v).navigate(EntryDetailsFragmentDirections.putEntry());
      }
    }
  }
}