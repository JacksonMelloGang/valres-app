package fr.valres.app.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.valres.app.ChoixDateSalle;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ValresWebsiteGet extends AsyncTask<String, Void, String> {

    private String response;
    private final Context context;

    public ValresWebsiteGet(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String result = "";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ChoixDateSalle choixDateSalle = (ChoixDateSalle) context;

        String[] salles = {};
        try {
            JSONObject json = new JSONObject(s);
            String[] jsonSalles = new String[json.length()];
            for(int i = 0; i < json.length(); i++){
                jsonSalles[i] = json.getString(String.valueOf(i));
            }
            salles = jsonSalles;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        choixDateSalle.setSalles(salles);
    }
}
