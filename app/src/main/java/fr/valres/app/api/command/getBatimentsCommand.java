package fr.valres.app.api.command;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import fr.valres.app.api.ValresAPI;
import fr.valres.app.model.Batiment;
import fr.valres.app.model.Category;
import fr.valres.app.repository.BatimentRepository;
import fr.valres.app.repository.CategoryRepository;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getBatimentsCommand implements Command {

    private String url;
    private String method;
    private Map<String, String> params;

    public getBatimentsCommand(String endpoint, String method, Map<String, String> params){
        this.url = ValresAPI.getInstance().getUrlApi() + "/" + endpoint;
        this.method = method;
        this.params = params;
    }



    @Override
    public void execute() {
        Log.i("getCategoriesCommand", "execute: " + url);

        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                // init GET okHTTP Request
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", ValresAPI.getInstance().getToken())
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.i("getBatimentsCommand", "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(!response.isSuccessful()){
                            Log.i("getBatimentsCommand", "onResponse: " + response.body().string());
                            return;
                        }

                        try {
                            String responseBody = response.body().string();
                            JSONObject json = new JSONObject(responseBody);
                            // for each element in batiments, create Batiment Object
                            // and add it to the list of batiments
                            JSONArray batiments = json.getJSONArray("batiments");
                            for (int i = 0; i < batiments.length(); i++) {
                                JSONObject batimentsJSONObject = batiments.getJSONObject(i);

                                String name = batimentsJSONObject.getString("name");
                                int code = batimentsJSONObject.getInt("code");

                                Batiment b = new Batiment(name, code);

                                BatimentRepository.getInstance().addBatiment(b);
                                Log.i("getBatimentsCommand", "adding: " + b.toString());
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });

                return null;
            }
        };

        task.execute();

    }
}
