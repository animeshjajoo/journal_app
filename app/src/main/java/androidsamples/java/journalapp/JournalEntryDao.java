package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

@Dao
public interface JournalEntryDao {
    @Insert
    void insert(JournalEntry entry);

    @Query("Select * from journal_table order by title asc")
    LiveData<List<JournalEntry>> getAllEntries();

    @Update
    void updateJournalEntry(JournalEntry entry);

    @Delete
    void deleteJournalEntry(JournalEntry entry);

    @Query("SELECT * from journal_table WHERE id=(:id)")
    LiveData<JournalEntry> getEntry(UUID id);
}
