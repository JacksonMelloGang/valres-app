package fr.valres.app.model;

public class Salle {

    private int id;
    private String nom;
    private Category categorie;

    public Salle(int id, String nom, Category categorie) {
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

    public Category getCategorie() {
        return categorie;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", categorie=" + categorie +
                '}';
    }
}
