package androidsamples.java.journalapp;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.uiautomator.UiDevice;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;

import android.os.RemoteException;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Instrumented tests for the {@link EntryDetailsFragment}.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class InstrumentedTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable();
    }

    @Test
    public void testEntrySorting() throws InterruptedException {
        // Add entries with different dates and times...
        addEntry("Entry A", 2023, 11, 23, 10, 30); // Date: January 15, 2022, Time: 10:30 AM
        addEntry("Entry B", 2023, 11, 24, 14, 0); // Date: February 20, 2022, Time: 2:00 PM
        addEntry("Entry C", 2023, 11, 25, 8, 45); // Date: March 5, 2022, Time: 8:45 AM

        // Verify the order of entries in the RecyclerView...
        onView(withId(R.id.recyclerView)).check(new RecyclerViewItemOrderAssertion(
                new String[]{"Entry A", "Entry B", "Entry C"}));

        onView(anyOf(withText("Entry A"))).perform(click());
        onView(withId(R.id.delete_entry)).perform(click());
        onView(withText("Yes")).perform(click());
        onView(anyOf(withText("Entry B"))).perform(click());
        onView(withId(R.id.delete_entry)).perform(click());
        onView(withText("Yes")).perform(click());
        onView(anyOf(withText("Entry C"))).perform(click());
        onView(withId(R.id.delete_entry)).perform(click());
        onView(withText("Yes")).perform(click());
    }

    @Test
    public void testDeletion() throws InterruptedException {
        onView(withId(R.id.btn_add_entry)).perform(click());
        onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Testing Deletion"));
        onView(withId(R.id.btn_entry_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 11, 23));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_start_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(10, 0));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_end_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(11, 0));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_save)).perform(click());
        onView(anyOf(withText("Testing Deletion"))).perform(click());
        onView(withId(R.id.delete_entry)).perform(click());
        onView(withText("Yes")).perform(click());
        onView(withId(R.id.recyclerView)).check(new RecyclerViewItemCountAssertion(0));
    }

//    @Test
//    public void testInsertion() throws InterruptedException {
//        onView(withId(R.id.btn_add_entry)).perform(click());
//        onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Testing Insertion"));
//        onView(withId(R.id.btn_entry_date)).perform(click());
//        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
//                .perform(PickerActions.setDate(2023, 11, 23));
//        Thread.sleep(1000);
//        onView(withText("OK")).perform(click());
//        Thread.sleep(1000);
//        onView(withId(R.id.btn_start_time)).perform(click());
//        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
//                .perform(PickerActions.setTime(10, 0));
//        Thread.sleep(1000);
//        onView(withText("OK")).perform(click());
//        Thread.sleep(1000);
//        onView(withId(R.id.btn_end_time)).perform(click());
//        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
//                .perform(PickerActions.setTime(11, 0));
//        Thread.sleep(1000);
//        onView(withText("OK")).perform(click());
//        Thread.sleep(1000);
//        onView(withId(R.id.btn_save)).perform(click());
//        onView(withId(R.id.recyclerView)).check(new RecyclerViewItemCountAssertion(1));
//        onView(anyOf(withText("Testing Insertion"))).perform(click());
//        onView(withId(R.id.delete_entry)).perform(click());
//        onView(withText("Yes")).perform(click());
//    }

    @Test
    public void testAddEntry() throws InterruptedException {
        onView(withId(R.id.btn_add_entry)).perform(click());
        onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Testing Add Entry"));
        onView(withId(R.id.btn_entry_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(   PickerActions.setDate(2023, 11, 23));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_start_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(10, 0));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_end_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(11, 0));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_save)).perform(click());
        onView(anyOf(withText("Testing Add Entry"))).perform(click());
        onView(withId(R.id.delete_entry)).perform(click());
        onView(withText("Yes")).perform(click());
    }

    @Test
    public void testUpdateEntry() throws InterruptedException {
        onView(withId(R.id.btn_add_entry)).perform(click());
        onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Testing Update Entry"));
        onView(withId(R.id.btn_entry_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 11, 23));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_start_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(10, 0));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_end_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(11, 0));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_save)).perform(click());
        onView(anyOf(withText("Testing Update Entry"))).perform(click());
        onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Testing Update Entry 2"));

        onView(withId(R.id.btn_save)).perform(click());
//        onView(withId(R.id.edit_title)).check(matches(withText("Testing Update Entry 2")));
//        onView(withId(R.id.btn_entry_date)).check(matches(withText("THURS, NOV 23, 2023")));
//        onView(withId(R.id.btn_start_time)).check(matches(withText("10:00")));
//        onView(withId(R.id.btn_end_time)).check(matches(withText("11:00")));
        onView(anyOf(withText("Testing Update Entry 2"))).perform(click());
        onView(withId(R.id.delete_entry)).perform(click());
        onView(withText("Yes")).perform(click());
    }

    @Test
    public void findsColorContrastError() {
        onView(withId(R.id.btn_add_entry)).perform(click());
        onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Testing Color Contrast"));
        //Delete the entry...
//        onView(withId(R.id.delete_entry)).perform(click());
//        onView(withText("Yes")).perform(click());
    }


    @Test
    public void testRotationHandling() throws InterruptedException {
        onView(withId(R.id.btn_add_entry)).perform(click());
        onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Testing Rotation"));
        onView(withId(R.id.btn_entry_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 11, 23));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_start_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(10, 0));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_end_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(11, 0));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        // Rotate the screen...
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        try {
            device.setOrientationLeft();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        // Verify that the app retains data and UI state...
        onView(withId(R.id.edit_title)).check(matches(withText("Testing Rotation")));
        onView(withId(R.id.btn_entry_date)).check(matches(withText("THURS, NOV 23, 2023")));
        onView(withId(R.id.btn_start_time)).check(matches(withText("10:00")));
        onView(withId(R.id.btn_end_time)).check(matches(withText("11:00")));
        // Rotate the screen back...
        try {
            device.setOrientationNatural();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        // Verify that the app retains data and UI state...
        onView(withId(R.id.edit_title)).check(matches(withText("Testing Rotation")));
        onView(withId(R.id.btn_entry_date)).check(matches(withText("THURS, NOV 23, 2023")));
        onView(withId(R.id.btn_start_time)).check(matches(withText("10:00")));
        onView(withId(R.id.btn_end_time)).check(matches(withText("11:00")));
        // Delete the entry...
//        onView(withId(R.id.btn_save)).perform(click());
//        onView(anyOf(withText("Testing Rotation"))).perform(click());
//        onView(withId(R.id.delete_entry)).perform(click());
//        onView(withText("Yes")).perform(click());
    }

    private void addEntry(String title, int year, int month, int dayOfMonth, int hourOfDay, int minute) throws InterruptedException {
        onView(withId(R.id.btn_add_entry)).perform(click());
        onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText(title));
        onView(withId(R.id.btn_entry_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month, dayOfMonth));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_start_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(hourOfDay, minute));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_end_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(hourOfDay+1, minute));
        Thread.sleep(1000);
        onView(withText("OK")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_save)).perform(click());
    }

    @Test
    public void testNavigationToInfoFragment() {
        // Create a TestNavHostController
        TestNavHostController navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());

        FragmentScenario<EntryListFragment> infoFragmentFragmentScenario
                = FragmentScenario.launchInContainer(EntryListFragment.class, null, R.style.Theme_JournalApp, (FragmentFactory) null);

        infoFragmentFragmentScenario.onFragment(fragment -> {
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph);

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController);
        });
        assertThat(Objects.requireNonNull(navController.getCurrentDestination()).getId(), is(R.id.entryListFragment));
    }

    public static class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assert adapter != null;
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

    public static class RecyclerViewItemOrderAssertion implements ViewAssertion {
        private final String[] expectedOrder;

        public RecyclerViewItemOrderAssertion(String[] expectedOrder) {
            this.expectedOrder = expectedOrder;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assert adapter != null;

            // Check the order of items in the RecyclerView
            int itemCount = adapter.getItemCount();
            List<String> actualOrder = new ArrayList<>();
            for (int i = 0; i < itemCount; i++) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
                actualOrder.add(((TextView) Objects.requireNonNull(viewHolder.itemView.findViewById(R.id.txt_item_title)))
                        .getText().toString());
            }

            assertThat(actualOrder, is(Arrays.asList(expectedOrder)));
        }
    }
}
