package com.thic.mynotesjava.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thic.mynotesjava.Model.Notlar;

public class NoteActivityViewModel extends ViewModel {

    private MutableLiveData<Notlar> myNote = new MutableLiveData<>();
    private MutableLiveData<String> noteTitle = new MutableLiveData<>();
    private MutableLiveData<String> noteContent = new MutableLiveData<>();

    public MutableLiveData<Notlar> getMyNote() {
        return myNote;
    }

    public void setMyNote(MutableLiveData<Notlar> myNote) {
        this.myNote = myNote;
    }

    public MutableLiveData<String> getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(MutableLiveData<String> noteTitle) {
        this.noteTitle = noteTitle;
    }

    public MutableLiveData<String> getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(MutableLiveData<String> noteContent) {
        this.noteContent = noteContent;
    }
}
