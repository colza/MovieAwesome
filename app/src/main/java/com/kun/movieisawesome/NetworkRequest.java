package com.kun.movieisawesome;

import okhttp3.OkHttpClient;

/**
 * Created by TsaiKunYu on 14/05/16.
 */
public class NetworkRequest {
    public static OkHttpClient okHttpClient;
    public static OkHttpClient instantiateClient(){
        if( okHttpClient == null ){
            okHttpClient = new OkHttpClient();
        }

        return okHttpClient;
    }
}
