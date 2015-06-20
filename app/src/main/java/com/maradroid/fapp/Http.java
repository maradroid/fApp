package com.maradroid.fapp;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by mara on 4/1/15.
 */
public class Http {
    private static Http ourInstance = new Http();

    public static Http getInstance() {
        return ourInstance;
    }

    public String post(String url){

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);

            StatusLine sl = httpResponse.getStatusLine();

            if(sl.getStatusCode()==200){
                InputStream is = httpResponse.getEntity().getContent();

                BufferedReader reader= new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line;
                while((line = reader.readLine()) != null){
                    sb.append(line);
                }
                is.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
