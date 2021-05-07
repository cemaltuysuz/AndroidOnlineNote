package com.thic.mynotesjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.thic.mynotesjava.Model.Models.Notlar;
import com.thic.mynotesjava.ViewModel.NoteActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class noteActivity extends AppCompatActivity {

    // Define Variable
    private EditText noteTitle,noteContent;
    private ImageView done;
    private TextView noteDate;
    private Notlar note;
    private boolean isOld;
    private ProgressBar noteProgress;
    public static int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Initialize Variable
        noteDate = findViewById(R.id.currentDate);
        done = findViewById(R.id.noteDone);
        noteTitle = findViewById(R.id.EditNoteTitle);
        noteContent = findViewById(R.id.EditNoteContent);
        noteProgress = findViewById(R.id.noteProgressBar);

        // isEmpty ?
        if (!getIntent().getBooleanExtra("isEmpty",true)){
            isOld = true;
            Bundle data = getIntent().getExtras().getBundle("data");
            noteID = Integer.parseInt(data.getString("noteID"));
            noteTitle.setText(data.getString("noteTitle"));
            noteContent.setText(data.getString("noteContent"));
            noteDate.setText(data.getString("noteDate"));
        }else{
            isOld = false;
            noteDate.setText(current_date());
        }

        // Save Action
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOld){
                    if (!noteContent.getText().toString().isEmpty()){
                        noteProgress.setVisibility(View.VISIBLE);
                        note = new Notlar(noteTitle.getText().toString(),noteContent.getText().toString(),
                                current_date(),"1","1","1");
                        NetworkHelper.insertData(note);
                    }
                }else {

                }
            }
        });
            // Insert or Update Response
        NoteActivityViewModel.isSucces.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 0:
                        Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                        isOld = false;
                        break;
                    case 1:
                        isOld = true;
                        NetworkHelper.getData();
                        Toast.makeText(getApplicationContext(),String.valueOf(noteID),Toast.LENGTH_SHORT).show();
                    default:
                        Toast.makeText(getApplicationContext(),"Unknow Eror",Toast.LENGTH_SHORT).show();
                        break;
                }
                noteProgress.setVisibility(View.GONE);
            }
        });

    }

    // Current Date
    public String current_date(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }
}