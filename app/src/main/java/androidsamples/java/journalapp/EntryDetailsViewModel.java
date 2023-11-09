package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EntryDetailsViewModel extends ViewModel {
    private static final MutableLiveData<String> selectedDate = new MutableLiveData<>();
    private final MutableLiveData<String> selectedTime = new MutableLiveData<>();

    public LiveData<String> getSelectedDate() {
        return selectedDate;
    }

    public LiveData<String> getSelectedTime() {
        return selectedTime;
    }

    public static void setSelectedDate(String date) {
        selectedDate.setValue(date);
    }

    public void setSelectedTime(String time) {
        selectedTime.setValue(time);
    }
}
