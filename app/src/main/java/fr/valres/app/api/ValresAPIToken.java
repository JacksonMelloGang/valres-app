package fr.valres.app.api;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import fr.valres.app.api.ValresAPI;
import fr.valres.app.controller.ChoixDateSalle;
import fr.valres.app.controller.LoginActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ValresAPIToken extends AsyncTask<String, Void, String> {

    Context context;
    String url;

    public ValresAPIToken(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        url = strings[0];
        String login = strings[1];
        String password = strings[2];
        String token = null;

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");

        // prepare request body in form data
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("login", login)
                .addFormDataPart("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url + "/token")
                .method("POST", body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                ResponseBody responseBody = response.body();
                String responseString = responseBody.string();

                if(responseString != null && !responseString.isEmpty()){
                    String json = responseString.trim();

                    try {
                        JSONObject resulttoken = new JSONObject(json);
                        if(resulttoken.getString("code").equals("1")){
                            token = resulttoken.getString("token");
                        } else {
                            token = "invalid";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    token = "invalid";
                }
            } else {
                token = "invalid";
            }

            response.close();
        } catch(IOException e){
            Log.e("ValresAPIToken", "doInBackground: " + e.getMessage());
            e.printStackTrace();
            // Afficher un message d'erreur à l'utilisateur
            Toast.makeText(context, "Erreur de connexion", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Erreur de connexion", Toast.LENGTH_SHORT).show();
        }


        return token;
    }

    @Override
    protected void onPostExecute(String token) {
        super.onPostExecute(token);

        if(token == null){
            Toast.makeText(context, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            return;
        }

        //ValresAPI.APIToken = token;
        if(token.equals("invalid")){
            Toast.makeText(context, "Login ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

        //durgan.tierra
        ValresAPI.initInstance(token, url);
    }
}
