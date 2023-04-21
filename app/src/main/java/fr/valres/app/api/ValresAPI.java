package fr.valres.app.api;

import fr.valres.app.api.command.Command;

public class ValresAPI {
    private String urlApi;

    private static ValresAPI instance = null;

    private String token;

    public ValresAPI(String token, String urlApi) {
        this.urlApi = urlApi;
        this.token = token;
        instance = this;
    }

    public static ValresAPI getInstance() {
        return instance;
    }

    public String getToken() {
        return token;
    }

    public String getUrlApi() {
        return urlApi;
    }

    public void executeCommand(Command command){
        command.execute();
    }

    public static void initInstance(String token, String urlApi){
        if(instance == null)
            instance = new ValresAPI(token, urlApi);
    }

    public static void destroyInstance(){
        if(instance != null)
            instance = null;
    }
}
