package fr.valres.app.api;

import fr.valres.app.api.command.Command;

public class ValresAPI {
    public static String APIToken;

    private static ValresAPI instance = null;

    private String token;
    private Command command;

    public ValresAPI(String token) {
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

    public static void initInstance(String token){
        instance = new ValresAPI(token);
    }
}
