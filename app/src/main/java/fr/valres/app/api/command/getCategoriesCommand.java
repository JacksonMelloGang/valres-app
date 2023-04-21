package fr.valres.app.api.command;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import fr.valres.app.api.ValresAPI;
import fr.valres.app.model.Category;
import fr.valres.app.repository.CategoryRepository;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getCategoriesCommand implements Command {

    private String url;
    private String method;
    private Map<String, String> params;

    public getCategoriesCommand(String endpoint, String method, Map<String, String> params){
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
                        Log.i("getCategoriesCommand", "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(!response.isSuccessful()){
                            Log.i("getSallesCommand", "onResponse: " + response.body().string());
                            return;
                        }

                        try {
                            String responseBody = response.body().string();
                            JSONObject json = new JSONObject(responseBody);
                            // for each element in categories, create Category Object
                            // and add it to the list of categories
                            JSONArray categories = json.getJSONArray("categories");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject category = categories.getJSONObject(i);
                                int id = category.getInt("cat_id");
                                String libelle = category.getString("libelle");
                                Category c = new Category(id, libelle);

                                CategoryRepository.getInstance().addCategory(c);
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
