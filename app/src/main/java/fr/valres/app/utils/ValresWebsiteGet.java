package fr.valres.app.utils;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import fr.valres.app.api.ValresAPI;
import fr.valres.app.controller.ChoixDateSalle;
import fr.valres.app.model.Salle;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ValresWebsiteGet extends AsyncTask<String, Void, ArrayList<Salle>> {

    private final Context context;

    public ValresWebsiteGet(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Salle> doInBackground(String... strings) {
        String url = strings[0];

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", ValresAPI.getInstance().getToken())
                .build();

        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String responseString = response.body().string().trim();
                ChoixDateSalle choixDateSalle = (ChoixDateSalle) context;

                if(response.isSuccessful()){
                    String[] salles = {};
                    try {
                        JSONArray json = new JSONArray(responseString);
                        String[] jsonSalles = new String[json.length()];
                        for(int i = 0; i < json.length(); i++){
                            JSONObject salle = json.getJSONObject(i);
                            jsonSalles[i] = salle.getString("salle_nom");
                        }
                        salles = jsonSalles;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Salle> s) {
        super.onPostExecute(s);

        Salle[] salles = new Salle[s.size()];
        for(int i = 0; i < s.size(); i++){
            String salleNom = salles[i] = s.get(i).getNom();
        }

    }
}
