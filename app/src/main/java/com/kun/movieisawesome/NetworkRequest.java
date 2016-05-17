package com.kun.movieisawesome;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    public static void fetchRemoteJsonAndSaveIntoPref(final Context context, String reqUrl, final String[] childKeys, final String prefKey){
        if( PreferenceManager.getDefaultSharedPreferences(context).contains(prefKey))
            return;

        Request request = new Request.Builder().url(reqUrl).build();

        NetworkRequest.instantiateClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseStr);
                    if( childKeys != null){
                        for( String key : childKeys)
                            jsonObject = jsonObject.getJSONObject(key);
                    }

                    String resultStr = jsonObject.toString();
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putString(prefKey, resultStr).commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void fetchGenreList(Context context, String reqUrl, String prefFile){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
//        if( sharedPreferences != null )
//            return;

        Request request = new Request.Builder().url(reqUrl).build();

        NetworkRequest.instantiateClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseStr);
                    JSONArray genreArr = jsonObject.getJSONArray("genres");
                    for( int i = 0 ; i < genreArr.length() ; i++ ){
                        JSONObject child = genreArr.getJSONObject(i);
                        sharedPreferences.edit().putString(String.valueOf(child.getInt("id")), child.getString("name")).commit();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
