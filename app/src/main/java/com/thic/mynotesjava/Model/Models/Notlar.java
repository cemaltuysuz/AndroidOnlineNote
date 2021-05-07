
package com.thic.mynotesjava.Model.Models;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notlar implements Serializable {

    public Notlar(String noteTitle, String noteContent, String noteDate, String noteFontType, String noteTextColor, String noteBackColor) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteDate = noteDate;
        this.noteFontType = noteFontType;
        this.noteTextColor = noteTextColor;
        this.noteBackColor = noteBackColor;
    }

    @SerializedName("NoteId")
    @Expose
    private String noteId;
    @SerializedName("NoteTitle")
    @Expose
    private String noteTitle;
    @SerializedName("NoteContent")
    @Expose
    private String noteContent;
    @SerializedName("NoteDate")
    @Expose
    private String noteDate;
    @SerializedName("NoteFontType")
    @Expose
    private String noteFontType;
    @SerializedName("NoteTextColor")
    @Expose
    private String noteTextColor;
    @SerializedName("NoteBackColor")
    @Expose
    private String noteBackColor;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteFontType() {
        return noteFontType;
    }

    public void setNoteFontType(String noteFontType) {
        this.noteFontType = noteFontType;
    }

    public String getNoteTextColor() {
        return noteTextColor;
    }

    public void setNoteTextColor(String noteTextColor) {
        this.noteTextColor = noteTextColor;
    }

    public String getNoteBackColor() {
        return noteBackColor;
    }

    public void setNoteBackColor(String noteBackColor) {
        this.noteBackColor = noteBackColor;
    }

}
