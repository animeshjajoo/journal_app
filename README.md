[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/XNhTFXWh)
# JournalApp
Name of the Project: Journal App
Team Members: Bharath Kalyan B (2020B4A71354G) (f20201354@goa.bits-pilani.ac.in) and Animesh Jajoo (2020B3A71260G) (f20201260@goa.bits-pilani.ac.in)

#About the App
This is an Android application designed for users to document their daily activities in a journal format. Users can create new entries by selecting the 'plus' button, initiating a detailed journal entry view. Within this view, users can input the entry title and specify the date, start time, and end time. The entries are securely stored in a Room database and can be conveniently accessed in a scrollable list view. Additionally, users have the flexibility to edit, delete, or share entries directly within the app.

#Implementing Room Database
Created the JournalEntry class as the entity to define column data types, such as UUID for the ID column, String for the title, and Date/Time for start and end times.
Developed the JournalEntryDao and JournalRoomDatabase classes. Added functionalities for inserting and deleting entries in the database.
Introduced the JournalRepository to establish a connection with the JournalRoomDatabase.
Created distinct ViewModels for the EntryDetails and EntryList fragments to manage and retrieve data specific to each fragment.
Integrated a RecyclerView into the EntryListFragment to display entries dynamically.

Actions were implemented to  transition between the EntryList fragment and the Details fragment. The Fragment Manager handled creation of dialogs and passing listeners to be invoked. The navigation process to the Entry Details Fragment differed based on whether it involved a new or existing entry, with the UID being passed accordingly. 

#Delete and Share Menus
We added a delete option to the menu in the Entry Details section. When someone clicks on delete, a pop-up box asks if they are sure they want to delete. On confirmation" a method called deleteEntry was invoked to remove the entry. This method talked to the database and used a special command (SQL DELETE) to take out the entry from the database.

 In the Entry Details part, there's also a "Share" button. When you click it, you can easily send the date and time of your journal entry to others.

#InfoFragment
We created a new fragment called InfoFragment that displays information about what the app does. 

We did not use the Monkey Tool and the app did not crash on any run for us.

Time taken for the assignment - 20 hours
Assignment Difficulty - 9





