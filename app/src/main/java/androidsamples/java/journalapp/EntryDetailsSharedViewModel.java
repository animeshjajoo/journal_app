package androidsamples.java.journalapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

public class EntryDetailsSharedViewModel extends ViewModel {
    private static final String TAG = "EntryDetailsVM";
    private final JournalRepository mRepository;

    private final MutableLiveData<UUID> entryIdLiveData = new MutableLiveData<>();

    private int tYear;
    private int tMonth;
    private int tDayOfMonth;
    private int shr;
    private int sm;
    private int ehr;
    private int em;

    public boolean isOldEntry() {
        return oldEntry;
    }

    public void setOldEntry(boolean oldEntry) {
        this.oldEntry = oldEntry;
    }

    private boolean oldEntry;



    public EntryDetailsSharedViewModel() {
        mRepository = JournalRepository.getInstance();
    }

    void updateEntry(JournalEntry entry) {
        mRepository.update(entry);
    }

    void insertEntry(JournalEntry entry) {
        mRepository.insert(entry);
    }

    void deleteEntry(JournalEntry entry){ mRepository.delete(entry);}

    LiveData<JournalEntry> getEntryLiveData() {
        return Transformations.switchMap(entryIdLiveData, mRepository::getEntry);
    }

    void loadEntry(UUID entryId) { entryIdLiveData.setValue(entryId); }

    // this method will be called by DatePickerDialog in DatePickerFragment
    public void storeDate(int y, int m, int d) {
        tYear = y;
        tMonth = m;
        tDayOfMonth = d;
        Log.d(TAG, "day of month"+d);
    }

    public String setDate() {
        String dayOfWeek = "", month = "";
        Log.d(TAG, "year"+tYear+"month"+tMonth+"day_of_month"+tDayOfMonth);
        GregorianCalendar calendar = new GregorianCalendar(tYear, tMonth, tDayOfMonth);
        switch (tMonth) {
            case 0:
                month = "JAN";
                break;
            case 1:
                month = "FEB";
                break;
            case 2:
                month = "MAR";
                break;
            case 3:
                month = "MAY";
                break;
            case 4:
                month = "APRIL";
                break;
            case 5:
                month = "JUN";
                break;
            case 6:
                month = "JULY";
                break;
            case 7:
                month = "AUG";
                break;
            case 8:
                month = "SEP";
                break;
            case 9:
                month = "OCT";
                break;
            case 10:
                month = "NOV";
                break;
            case 11:
                month = "DEC";
                break;
        }
        Log.d(TAG, "day of week "+calendar.get(Calendar.DAY_OF_WEEK));
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                dayOfWeek = "SUN";
                break;
            case 2:
                dayOfWeek = "MON";
                break;
            case 3:
                dayOfWeek = "TUE";
                break;
            case 4:
                dayOfWeek = "WED";
                break;
            case 5:
                dayOfWeek = "THURS";
                break;
            case 6:
                dayOfWeek = "FRI";
                break;
            case 7:
                dayOfWeek = "SAT";
                break;
        }
        return "" + dayOfWeek + ", " + month + " " + tDayOfMonth + ", " + tYear;
    }

    public String getSTime(){
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String formattedHour = decimalFormat.format(shr);
        String formattedMinute = decimalFormat.format(sm);
        String formattedTime = formattedHour + ":" + formattedMinute;
        return formattedTime;
    }

    public String getETime(){
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String formattedHour = decimalFormat.format(ehr);
        String formattedMinute = decimalFormat.format(em);
        String formattedTime = formattedHour + ":" + formattedMinute;
        return formattedTime;
    }

    public void setStartTime(int hm, int m) {
        shr = hm;
        sm = m;
    }

    public void setEndTime(int hm, int m) {
        ehr = hm;
        em = m;
    }
}
