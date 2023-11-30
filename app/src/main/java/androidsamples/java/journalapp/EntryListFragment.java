package androidsamples.java.journalapp;

import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import androidsamples.java.journalapp.EntryListFragmentDirections.AddEntryAction;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {

  private static final String TAG = "EntryListFragment";
  private EntryListViewModel mEntryListViewModel;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    mEntryListViewModel = new ViewModelProvider(this).get(EntryListViewModel.class);

  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_list, container, false);

    RecyclerView entriesList = view.findViewById(R.id.recyclerView);
    entriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    EntryListAdapter adapter = new EntryListAdapter(getActivity());
    entriesList.setAdapter(adapter);

    mEntryListViewModel.getAllEntries().observe(requireActivity(), adapter::setEntries);

    view.findViewById(R.id.btn_add_entry).setOnClickListener(this::addNewEntry);

    return view;
  }

  private void addNewEntry(View view) {
    EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
    Navigation.findNavController(view).navigate(action);
  }

  class EntryListAdapter extends RecyclerView.Adapter<EntryListAdapter.EntryViewHolder> {

    private final LayoutInflater mInflater;
    private List<JournalEntry> mEntries;

    public EntryListAdapter(Context context) {
      mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EntryListAdapter.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = mInflater.inflate(R.layout.fragment_entry, parent, false);
      return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryListAdapter.EntryViewHolder holder, int position) {
      if (mEntries != null) {
        JournalEntry current = mEntries.get(position);
        holder.mTxtTitle.setText(current.getMTitle());
        holder.mTxtDate.setText(current.getMDate());
        holder.mSTime.setText(current.getMsTime());
        holder.mETime.setText(current.getMeTime());
        holder.itemView.setOnClickListener(v -> {
          EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
          action.setEntryId(current.getMUid());
          Navigation.findNavController(v).navigate(action);
        });
      }
    }

    @Override
    public int getItemCount() {
      return (mEntries == null) ? 0 : mEntries.size();
    }

    public void setEntries(List<JournalEntry> entries) {
      mEntries = entries;
      notifyDataSetChanged();
    }

    class EntryViewHolder extends RecyclerView.ViewHolder {
      private final TextView mTxtTitle;
      private final TextView mTxtDate;
      private final TextView mSTime;
      private final TextView mETime;


      public EntryViewHolder(@NonNull View itemView) {
        super(itemView);
        mTxtTitle = itemView.findViewById(R.id.txt_item_title);
        mTxtDate = itemView.findViewById(R.id.txt_item_date);
        mSTime = itemView.findViewById(R.id.txt_item_start_time);
        mETime = itemView.findViewById(R.id.txt_item_end_time);
      }
    }
  }

  @NonNull
  public static EntryListFragment newInstance() {
    EntryListFragment fragment = new EntryListFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.info_entry_list_fragment, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.app_info) {
      NavDirections action = EntryListFragmentDirections.actionGetInfo();
      Navigation.findNavController(getView()).navigate(action);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}