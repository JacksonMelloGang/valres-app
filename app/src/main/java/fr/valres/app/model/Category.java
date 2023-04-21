package fr.valres.app.model;

public class Category {

    private int id;
    private String nom;

    public Category(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String toString(){
        return "id: " + id + " nom: " + nom;
    }
}
