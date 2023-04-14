package fr.valres.app.api;

import fr.valres.app.api.command.Command;

public class ValresAPI {
    public String urlApi;

    private static ValresAPI instance = null;

    private String token;
    private Command command;

    public ValresAPI(String token, String urlApi) {
        this.urlApi = urlApi;
        this.token = token;
        instance = this;
    }

    public void setCommand(Command command){
        this.command = command;
    }

    public void executeRequest(){
        this.command.executeContent();
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

    public static void initInstance(String token, String urlApi){
        if(instance == null)
            instance = new ValresAPI(token, urlApi);
    }

    public static void destroyInstance(){
        if(instance != null)
            instance = null;
    }
}
