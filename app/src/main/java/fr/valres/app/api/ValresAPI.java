package fr.valres.app.api;

public class ValresAPI {

    public static String APIToken;

    private static ValresAPI instance = null;

    private String token;

    public ValresAPI(String token) {
        this.token = token;
        instance = this;
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
