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
import fr.valres.app.model.Salle;
import fr.valres.app.repository.CategoryRepository;
import fr.valres.app.repository.SalleRepository;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getSallesCommand implements Command {

    private String url;
    private String method;
    private Map<String, String> params;
    private Context context;

    public getSallesCommand(String endpoint, String method, Map<String, String> params, Context context){
        this.url = ValresAPI.getInstance().getUrlApi() + "/" + endpoint;
        this.method = method;
        this.params = params;
        this.context = context;
    }


    @Override
    public void execute() {
        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", ValresAPI.getInstance().getToken())
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(!response.isSuccessful()){
                            Log.i("getSallesCommand", "onResponse: " + response.body().string());
                            return;
                        }

                        try {
                            String reponseBody = response.body().string();
                            JSONArray json = new JSONArray(reponseBody);

                            for(int i = 0; i < json.length(); i++){
                                JSONObject salleJson = json.getJSONObject(i);
                                int salleId = salleJson.getInt("salle_id");
                                String salleName = salleJson.getString("salle_nom");
                                Category catId = CategoryRepository.getInstance().getCategory(salleJson.getInt("cat_id"));

                                Salle salle = new Salle(salleId, salleName, catId);
                                SalleRepository.getInstance().addSalle(salle);
                                Log.i("getSallesCommand", "adding salle: " + salle.toString());
                            }
                        } catch (JSONException e) {
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
