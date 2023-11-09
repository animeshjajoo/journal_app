package androidsamples.java.journalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {
  @NonNull
  public static EntryListFragment newInstance() {
    EntryListFragment fragment = new EntryListFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_list, container, false);

    FloatingActionButton addButton = view.findViewById(R.id.btn_add_entry);
    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Navigate to entryDetailsFragment when the button is clicked
        Navigation.findNavController(v).navigate(R.id.addEntryAction);
      }
    });

    return view;
  }
}