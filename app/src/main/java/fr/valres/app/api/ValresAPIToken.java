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

public class ValresAPIToken extends AsyncTask<String, Void, String> {

    Context context;

    public ValresAPIToken(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.i("ValresAPIToken", "doInBackground: " + strings[0] + " " + strings[1] + " " + strings[2]);

        String url = strings[0];
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
                .url(url)
                .addHeader("Cookie", "XSRF-TOKEN=eyJpdiI6IjZWYTZuTGlJZ1Rha1ZTdHFWeWVMQUE9PSIsInZhbHVlIjoiVmpTSTFHV29aUXdwdVgydUxZQndTUFk3eGhNMjdyQkl2ZHpZUExWWXAxWU1WR0ljb09jMXc3RWtMa3F6YXhRc0VhNVM5OUlvKytxYkF5SkRMRXNaZzR2K253MEt0ckZySENMRW42Q3hhU0pNeGd5Mk1TS0wyOXN6aU94MGwrNGciLCJtYWMiOiI2YzdjZmFiZGU1NTY5YTFkZmQ1YTRkMWViMjJkOGUwNGIzMWFhNGZiNjAxZGE3ZDZiNmUzMTRjMDlmNTM5NDgxIiwidGFnIjoiIn0%3D; valres_session=eyJpdiI6ImxvZURibTE0eGFSS0YvOVFRTi94VkE9PSIsInZhbHVlIjoicGFUdXp1ZWswYVE4OHdTTlhKcWRza0NMTkJaUm1IMHhOdHFwSFVVMnZvZkJ4VG9GOStPSnM3QVNDWEppY3VxK0pXaVNPeXBDbWxiNkwwYWUzUXQxRTROZVpMMlpWWkJzMDhmWWs4RldhTzlKV21VK1ZxQ1N4NWh5SmN6Y2dBTnciLCJtYWMiOiI2MDdlNzdiMmE1OWI4OTk2MTJmNDdlYjNlNTBjODZiMmQ3ZTZkYTRkYWEyMjkzOTI5YzIxZjI2MmE3MjNlMGFiIiwidGFnIjoiIn0%3D")
                .method("POST", body)
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
                    } else {
                        token = "invalid";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i("ValresAPIToken", "doInBackground: " + response.code() + " " + response.message());
                token = "invalid";
            }
        } catch(SocketTimeoutException e){
            Log.e("ValresAPIToken", "doInBackground: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Erreur");
            alertDialog.setMessage("Erreur de connexion");
            alertDialog.show();

            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
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

        Log.i("ValresAPIToken", "onPostExecute: " + token);

        //ValresAPI.APIToken = token;
        if(token.equals("invalid")){
            Toast.makeText(context, "Login ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(context, ChoixDateSalle.class);
        context.startActivity(intent);
    }
}
