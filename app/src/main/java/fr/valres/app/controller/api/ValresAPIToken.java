package fr.valres.app.controller.api;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fr.valres.app.api.ValresAPI;
import fr.valres.app.controller.ChoixDateSalle;
import fr.valres.app.controller.LoginActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ValresAPIToken extends AsyncTask<String, Void, String> {


    Context context;

    public ValresAPIToken(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String login = strings[1];
        String password = strings[2];
        String token = null;

        // prepare request body in form data
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("login", login)
                .addFormDataPart("password", password)
                .build();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                try {
                    JSONObject resulttoken = new JSONObject(response.body().string());
                    if(resulttoken.getString("code").equals("1")){
                        ValresAPI.initInstance(resulttoken.getString("token"));

                        Log.d("ValresAPIToken", resulttoken.getString("token")); // token
                        token = resulttoken.getString("token");
                        ValresAPI.APIToken = token;

                        return token;
                    } else {
                        Toast.makeText(context, "Login ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return token;
    }

    @Override
    protected void onPostExecute(String token) {
        super.onPostExecute(token);
        //ValresAPI.APIToken = token;


        Log.i("ValresAPIToken", "onPostExecute: " + ValresAPI.getInstance());
        Log.i("ValresAPIToken", "onPostExecute: " + token);


        Intent intent = new Intent(context, ChoixDateSalle.class);
        context.startActivity(intent);
    }
}
