package androidsamples.java.journalapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "journal_table")
public class JournalEntry {


    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private UUID mUid;


    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "start_time")
    private String msTime;

    @ColumnInfo(name = "end_time")
    private String meTime;

    public JournalEntry(String mTitle, String mDate, String msTime, String meTime){
        mUid = UUID.randomUUID();
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.msTime = msTime;
        this.meTime = meTime;
    }
    public JournalEntry() {
        this(null, null, null, null);
    }

    @NonNull
    public UUID getMUid() {
        return mUid;
    }

    public void setMUid(@NonNull UUID mUid) {
        this.mUid = mUid;
    }

    public String getMTitle() {
        return mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMDate() {
        return mDate;
    }

    public void setMDate(String mDate) {
        this.mDate = mDate;
    }

    public String getMsTime() {
        return msTime;
    }

    public void setMsTime(String msTime) {
        this.msTime = msTime;
    }

    public String getMeTime() {
        return meTime;
    }

    public void setMeTime(String meTime) {
        this.meTime = meTime;
    }
}
