package fr.valres.app.repository;

import java.util.HashMap;

import fr.valres.app.model.Salle;

public class SalleRepository {

    private HashMap<Integer, Salle> salles = new HashMap<>();
    private static final SalleRepository INSTANCE = new SalleRepository();

    public SalleRepository(){

    }

    public static SalleRepository getInstance() {
        return INSTANCE;
    }

    public HashMap<Integer, Salle> getSalles() {
        return salles;
    }

    public void addSalle(Salle salle){
        salles.put(salle.getId(), salle);
    }

    public void removeSalle(Salle salle){
        salles.remove(salle.getId());
    }

    public void removeSalle(int id){
        salles.remove(id);
    }

    public Salle getSalle(int id){
        return salles.get(id);
    }
}
