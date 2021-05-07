package com.thic.mynotesjava.Model.Network;

public class ApiUtils {

    public static final String base_url = "http://10.0.2.2:8888/";

    public static API api (){
        return RetrofitClient.getClient(base_url).create(API.class);
    }
}
