package fr.valres.app.model;

public class Batiment {

    private String name;
    private int code;

    public Batiment(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "name: " + name + " code: " + code;
    }
}
