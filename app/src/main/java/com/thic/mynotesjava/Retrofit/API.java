package com.thic.mynotesjava.Retrofit;

import com.thic.mynotesjava.Model.NoteModel;
import com.thic.mynotesjava.UI.MainActivity;
import com.thic.mynotesjava.UI.noteActivity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    /**
     * Get All Notes
     * @see MainActivity
     * */
    @GET("services/all.php")
    Call<NoteModel> getData();

    /**
     * Insert Note
     * @see noteActivity
     * */

    @POST("services/insert.php")
    @FormUrlEncoded
    Call<NoteModel>noteInsert (@Field("noteTitle")       String noteTitle,
                               @Field("noteContent")     String noteContent,
                               @Field("noteDate")        String noteDate,
                               @Field("noteFontType")    String noteFontType,
                               @Field("noteTextColor")   String noteTextColor,
                               @Field("noteBackColor")   String noteBackColor,
                               @Field("noteImgStat")     String noteImgStat,
                               @Field("image")           String encodedImg);

    /**
     * update note
     * @see noteActivity
     * */

    @POST("services/update.php")
    @FormUrlEncoded
    Call<NoteModel>noteUpdate (@Field("noteId")          String noteId,
                               @Field("noteTitle")       String noteTitle,
                               @Field("noteContent")     String noteContent,
                               @Field("noteFontType")    String noteFontType,
                               @Field("noteTextColor")   String noteTextColor,
                               @Field("noteBackColor")   String noteBackColor,
                               @Field("noteImgStat")     String noteImgStat,
                               @Field("image")           String encodedImg);

    /**
     * Delete note
     * @see MainActivity
     * */

    @POST("services/delete.php")
    @FormUrlEncoded
    Call<NoteModel>noteDelete (@Field("NoteId") String noteId);

    /**
     * Search Note
     * @see MainActivity
     * */

    @POST("services/search.php")
    @FormUrlEncoded
    Call<NoteModel>noteSearch (@Field("note") String word);

}
