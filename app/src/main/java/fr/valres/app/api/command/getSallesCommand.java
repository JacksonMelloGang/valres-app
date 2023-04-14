package fr.valres.app.api.command;

import android.content.Context;

import java.io.IOException;
import java.util.UUID;

import fr.valres.app.api.ValresAPI;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getSallesCommand extends Command {

    public getSallesCommand(Context context){
        super(context);
    }
    @Override
    public String urlRequest() {
        return "http://172.16.225.170:8080/api/v1/salles";
    }

    @Override
    public String execute() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlRequest())
                .header("Authorization", ValresAPI.getInstance().getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void postRequest() {

    }
}
