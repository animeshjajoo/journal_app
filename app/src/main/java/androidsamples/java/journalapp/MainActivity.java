package androidsamples.java.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Fragment currentFragment = getSupportFragmentManager()
            .findFragmentById(R.id.nav_host_fragment);
    if (currentFragment == null) {
      Fragment fragment = new EntryListFragment();
      getSupportFragmentManager()
              .beginTransaction()
              .add(R.id.nav_host_fragment, fragment)
              .commit();
    }
  }
}