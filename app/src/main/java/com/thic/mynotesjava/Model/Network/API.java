package com.thic.mynotesjava.Model.Network;

import com.thic.mynotesjava.Model.Models.NoteModel;
import com.thic.mynotesjava.Model.Models.Notlar;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {

    @GET("services/all.php")
    Call<NoteModel> getData();

    @POST("services/insert.php")
    @FormUrlEncoded
    Call<NoteModel>noteInsert (@Field("noteTitle")       String noteTitle,
                               @Field("noteContent")     String noteContent,
                               @Field("noteDate")        String noteDate,
                               @Field("noteFontType")    String noteFontType,
                               @Field("noteTextColor")   String noteTextColor,
                               @Field("noteBackColor")   String noteBackColor);

    @POST("services/update.php")
    @FormUrlEncoded
    Call<NoteModel>noteUpdate (@Field("noteId")          String noteId,
                               @Field("noteTitle")       String noteTitle,
                               @Field("noteContent")     String noteContent,
                               @Field("noteDate")        String noteDate,
                               @Field("noteFontType")    String noteFontType,
                               @Field("noteTextColor")   String noteTextColor,
                               @Field("noteBackColor")   String noteBackColor);

}
