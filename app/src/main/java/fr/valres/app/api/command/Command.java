package fr.valres.app.api.command;

import android.content.Context;

public abstract class Command {

    private Context context;

    public Command(Context context){
        this.context = context;
    }

    public abstract String urlRequest();

    public abstract String execute();

    public abstract void postRequest();

    public void executeContent(){
        execute();
        postRequest();
    }
}
