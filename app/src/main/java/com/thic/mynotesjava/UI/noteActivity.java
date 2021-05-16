package com.thic.mynotesjava.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thic.mynotesjava.ImageHelper;
import com.thic.mynotesjava.Model.NoteModel;
import com.thic.mynotesjava.Model.Notlar;
import com.thic.mynotesjava.Model.PaletteModels.ChooseState;
import com.thic.mynotesjava.R;
import com.thic.mynotesjava.Retrofit.API;
import com.thic.mynotesjava.Retrofit.ApiUtils;
import com.thic.mynotesjava.ViewModel.NoteActivityViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class noteActivity extends AppCompatActivity implements bottomSheetDialog.materialListener {

    // Define Variable
    private EditText noteTitle,noteContent;
    private ImageView done,noteImg, notePalette,noteBack;
    private TextView noteDate;
    private ProgressBar noteProgress;
    private bottomSheetDialog bottomSheet;

    private NoteActivityViewModel viewModel;
    private Notlar note;
    private Intent intent;
    private Uri imageUri;
    private final int ACTIVITY_CHOOSE_PHOTO = 1;
    private boolean isOld,img,imgIsUpdate,materialIsUpdate;
    private String currentBs64;
    private API api = ApiUtils.api();
    Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Initialize(); // Initialize Variable

        /**
         ** Check Bundle is empty or full ? If Bundle is full, this not is already created. (Bundle comes from MainActivity )
         * @author cemaltuysuz
         * @see MainActivity
         * @version 1.0
         * */

        if (!getIntent().getBooleanExtra("isEmpty",true)){
            isOld = true;
            // Create Note Instance with bundle's data
            createNote();
            // UI views set data
            setNote();
        }else{
            isOld = false;
            // if this note is new, currentDate SET
            noteDate.setText(current_date());
            // Default options
            note.setNoteBackColor("1");
            note.setNoteFontType("1");
            note.setNoteTextColor("1");
        }


        /**
        *  If user clicked palette button, open bottomSheet.
        * @author cemaltuysuz
        * @see bottomSheetDialog
        * @version 1.0
        * */

        notePalette.setOnClickListener(v -> bottomSheet.show(getSupportFragmentManager(),"toBottomSheet"));

        // Back button clicked ->
        noteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPress();
            }
        });
        /**
         * If user click to save button, this onClick execute.
         * @version 1.0
         * */
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ProgressBar Visible
                noteProgress.setVisibility(View.VISIBLE);
                // Get Arguments
                String content,title;
                content     = noteContent.getText().toString();
                title       = noteTitle.getText().toString();
                // Content is empty ?
                if (!content.trim().isEmpty()){
                    // Check note is already created or new ?
                    if (isOld){
                        /*
                        * Has the change been made ?
                        * */
                        if (!note.getNoteTitle().equals(title) || !note.getNoteContent().equals(content) || imgIsUpdate || materialIsUpdate){
                            // There is a change
                            note.setNoteTitle(title);
                            note.setNoteContent(content);
                            /*
                            * This Condition Structure , Queries the picture settings in the note desired to be updated
                            * if there was no picture and no picture is currently selected , defined : imgStat  = 0 , imgUrl = null
                            * */
                            if (!imgIsUpdate && !img){
                                note.setImgStat("0");
                                note.setImgUrl("!null");
                            }
                            /**
                            * @author cemaltuysuz
                            * @version 1.0
                            * If image already exists but not updated -> Executed this Condition Structure
                             * setImageUrl = null , because php services In this case, the php services will not update ImageUrl
                            * */
                            if (img) if (!imgIsUpdate) note.setImgUrl(null);
                            updateData(note);
                        }else {
                            // If this note same
                            Toast.makeText(getApplicationContext(),R.string.sameError,Toast.LENGTH_SHORT).show();
                            noteProgress.setVisibility(View.GONE);
                        }
                    }else{
                        // This note is new
                        note.setNoteTitle(title);
                        note.setNoteContent(content);
                        note.setNoteDate(current_date());
                        if (!img){
                            // If no image is selected
                            note.setImgStat("0");
                            note.setImgUrl("!null");
                        }
                        // If note title is empty, note title set noteDate
                        if (title.trim().isEmpty()){
                            note.setNoteTitle(note.getNoteDate());
                        }
                        insertData(note);
                    }
                }else {
                    Toast.makeText(getApplicationContext(),R.string.emptyError,Toast.LENGTH_SHORT).show();

                }
            }
        });

        // This method works for the user to upload images
        noteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           openGallery();
             }
        });
    }
    // Initialize View and others
    private void Initialize() {
        noteDate = findViewById(R.id.currentDate);
        done = findViewById(R.id.noteDone);
        noteBack = findViewById(R.id.noteBack);
        noteImg = findViewById(R.id.noteImg);
        notePalette = findViewById(R.id.notePalette);
        noteTitle = findViewById(R.id.EditNoteTitle);
        noteContent = findViewById(R.id.EditNoteContent);
        noteProgress = findViewById(R.id.noteProgressBar);
        intent = new Intent(getApplicationContext(), MainActivity.class);
        data = getIntent().getExtras().getBundle("data");
        bottomSheet = new bottomSheetDialog();
        note = new Notlar();
    }

    /**
     * If this note already created -> This method executed
     * */
    private void createNote() {
        note.setNoteId(data.getString("noteID"));
        note.setNoteTitle(data.getString("noteTitle"));
        note.setNoteContent(data.getString("noteContent"));
        note.setNoteDate(data.getString("noteDate"));
        note.setNoteFontType(data.getString("noteFontType"));
        note.setNoteTextColor(data.getString("noteTextColor"));
        note.setNoteBackColor(data.getString("noteBackColor"));
        note.setImgStat(data.getString("noteImageStat"));
        note.setImgUrl(data.getString("noteImageUrl"));

        // If this not is already exists , MaterialSettings send bottomSheet from constructor
        bottomSheet = new bottomSheetDialog(new ChooseState(Integer.valueOf(note.getNoteFontType())
                ,Integer.valueOf(note.getNoteTextColor()),Integer.valueOf(note.getNoteBackColor())));
    }
    /**
     * If this note already exists, MaterialSettings sends it from the constructor to the bottomSheet.
     * */
    private void setNote (){
        noteTitle.setText(note.getNoteTitle());
        noteContent.setText(note.getNoteContent());
        noteDate.setText(note.getNoteDate());

        if (data.get("noteImageStat").equals("1")){
            img = true;
            ImageHelper.forView(getApplicationContext(),noteImg,note.getImgUrl());
        }
    }

    // This method getting Current Date
    private String current_date(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    // If user clicked bacButton , go to MainActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPress();
    }

    // Open Gallery
    private void openGallery(){
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("image/*");
        startActivityForResult(Intent.createChooser(chooseFile, "Choose a photo"), ACTIVITY_CHOOSE_PHOTO);
    }

    // Selected Image result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ACTIVITY_CHOOSE_PHOTO && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                noteImg.setImageBitmap(bitmap);
                note.setImgStat("1");
                note.setImgUrl(imageConverter(bitmap));
                if (img) imgIsUpdate = true;
                currentBs64 = imageConverter(bitmap);
                img = true;

            }catch (IOException ex){
            ex.printStackTrace();
            }
        }
    }

    // Image to base64 method
    private String imageConverter(Bitmap bitmap){
        String bitmapEncoded;
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,75,arrayOutputStream);
        byte[] imageInByte = arrayOutputStream.toByteArray();
        bitmapEncoded = Base64.encodeToString(imageInByte,Base64.DEFAULT);
        return bitmapEncoded;
    }

    // Note insert method
    private  void insertData(Notlar note){

        api.noteInsert(note.getNoteTitle(),note.getNoteContent(),note.getNoteDate()
                ,note.getNoteFontType(),note.getNoteTextColor(),note.getNoteBackColor(),note.getImgStat(),note.getImgUrl())
                .enqueue(new Callback<NoteModel>() {
                    @Override
                    public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                        if (response.body().getSuccess()==1){
                            // İnsert Success
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Success :"+response.body().getSuccess(),Toast.LENGTH_SHORT).show();
                        }
                        noteProgress.setVisibility(View.GONE);
                    }
                    @Override
                    public void onFailure(Call<NoteModel> call, Throwable t) {
                        // Http Error
                        Toast.makeText(getApplicationContext(),"Error :"+t.getMessage(),Toast.LENGTH_SHORT).show();
                        noteProgress.setVisibility(View.GONE);
                    }

                });
    }

    // Note update method
    private  void updateData(Notlar note){
        api.noteUpdate(note.getNoteId(),note.getNoteTitle(),note.getNoteContent()
                ,note.getNoteFontType(),note.getNoteTextColor(),note.getNoteBackColor(),note.getImgStat(),note.getImgUrl())
                .enqueue(new Callback<NoteModel>() {
                    @Override
                    public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                        if (response.body().getSuccess()==1){
                            // Update Success
                            // imgIsupdate = false, because user can change picture again.
                            imgIsUpdate = false;
                        }
                        else{
                            // Update Fail
                            Toast.makeText(getApplicationContext(),"success :"+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                        }
                        noteProgress.setVisibility(View.GONE);
                    }
                    @Override
                    public void onFailure(Call<NoteModel> call, Throwable t) {
                        // Http Error
                        Toast.makeText(getApplicationContext(),"Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        noteProgress.setVisibility(View.GONE);
                    }
                });
    }

    // FontType picked in bottomSheet
    @Override
    public void fontType(int position, Typeface typeFace) {
        note.setNoteFontType(String.valueOf(position));
        if (!materialIsUpdate) materialIsUpdate = true;
    }
    // TextColor picked in bottomSheet
    @Override
    public void textColor(int position, int color) {
        note.setNoteTextColor(String.valueOf(position));
        if (!materialIsUpdate) materialIsUpdate = true;
    }
    // BackgroundColor picked in bottomSheet
    @Override
    public void backgroundColor(int position, int color) {
        note.setNoteBackColor(String.valueOf(position));
        if (!materialIsUpdate) materialIsUpdate = true;
    }
    // Backpress method
    private void backPress(){
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}