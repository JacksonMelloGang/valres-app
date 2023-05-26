package fr.valres.app.api.command;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getCodeSalleCommand {
    private String url;
    private String method;
    private Map<String, String> params;
    private String[] code = new String[1];

    public getCodeSalleCommand(String endpoint, String method, Map<String, String> params){
        this.url = ValresAPI.getInstance().getUrlApi() + "/" + endpoint;
        this.method = method;
        this.params = params;
    }

    public void execute(CommandCallback callback) {
        Log.i("getCategoriesCommand", "execute: " + url);

        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                // init GET okHTTP Request
                OkHttpClient client = new OkHttpClient();


                HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    Log.i("getCodeSalle", "doInBackground: " + entry.getKey() + " " + entry.getValue());
                    urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                }

                String url = urlBuilder.build().toString();
                Log.i("getCodeSalle", "doInBackground: " + url);

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
                            Log.i("getSallesCommand", "onResponse: " + response.code());
                            return;
                        }

                        try {
                            String responseBody = response.body().string();
                            JSONObject json = new JSONObject(responseBody);
                            code[0] = json.getString("code");

                            callback.onSuccess();

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

    public String getCode() {
        return code[0];
    }


}
