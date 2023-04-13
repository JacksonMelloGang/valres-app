package fr.valres.app.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fr.valres.app.R;
import fr.valres.app.api.ValresAPI;
import fr.valres.app.controller.ChoixDateSalle;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ValresWebsiteGet extends AsyncTask<String, Void, String> {

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
                .header("Authorization", ValresAPI.getInstance().getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    ChoixDateSalle choixDateSalle = (ChoixDateSalle) context;

                    String[] salles = {};
                    try {
                        String responseBody = response.body().string();
                        JSONArray json = new JSONArray(responseBody);
                        String[] jsonSalles = new String[json.length()];
                        for(int i = 0; i < json.length(); i++){
                            JSONObject salle = json.getJSONObject(i);
                            jsonSalles[i] = salle.getString("salle_nom");
                        }
                        salles = jsonSalles;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(choixDateSalle, android.R.layout.simple_list_item_1, salles);
                    ListView listeSalles = choixDateSalle.findViewById(R.id.listSalles);
                    listeSalles.setAdapter(adapter);
                }
            }
        });

        return null;
    }

}
