package fr.valres.app.api;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import fr.valres.app.R;
import fr.valres.app.controller.ChoixSalleActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetDatabaseValresSalles extends AsyncTask<String, Void, String> {

    private String response;
    private final Context context;

    public GetDatabaseValresSalles(Context context) {
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

        ChoixSalleActivity choixDateSalle = (ChoixSalleActivity) context;

        // from this, we get a response like this: [{"salle_id":"1", "salle_nom": "Salle 1", "cat_id":1}, {"salle_id":"2", "salle_nom": "Salle 2", "cat_id":1}]
        // we want to convert this response into a json object
        // then, we want to get the value of the key "salle_nom" for each object
        // and put it in a String[]
        String[] salles = {};

        try {
            JSONArray json = new JSONArray(s);
            String[] jsonSalles = new String[json.length()];
            for(int i = 0; i < json.length(); i++){
                jsonSalles[i] = json.getJSONObject(i).getString("salle_nom");
            }
            salles = jsonSalles;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_single_choice, salles);
        ListView lvSalle = ((ChoixSalleActivity) context).findViewById(R.id.listSalles);
        lvSalle.setAdapter(adapter);
        lvSalle.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
