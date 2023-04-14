package fr.valres.app.model;

public class Salle {

    private int id;
    private String nom;
    private Categorie categorie;

    public Salle(int id, String nom, Categorie categorie) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Categorie getCategorie() {
        return categorie;
    }
}
