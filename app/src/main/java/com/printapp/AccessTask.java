package com.printapp;

import android.os.AsyncTask;
import android.util.Log;

import com.printapp.models.ServiceGenerator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class AccessTask extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        URL url = null;
        String token = "";
        try {
            url = new URL("http://pastebin.com/raw/4MHvPXBw");
            Reader is = new InputStreamReader(url.openConnection().getInputStream());
            int c;
            while((c = is.read())!=-1){
                token+=(char)c;
            }
            Log.d("doInBackground: ", token);
        } catch (IOException e) {
            Log.d("doInBackground: ", e.getMessage());
            e.printStackTrace();
        }


        return token;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ServiceGenerator.ACCESS_TOKEN = "58501e0430315fc227b2baac1fd9379cf6513bc0e18b4c0413f4f8b94a75d7e2c3743c2714e01e6db446b";
    }
}
