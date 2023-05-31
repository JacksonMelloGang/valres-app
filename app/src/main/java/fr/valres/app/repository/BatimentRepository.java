package fr.valres.app.repository;

import android.util.Log;

import java.util.HashMap;

import fr.valres.app.model.Batiment;

public class BatimentRepository {

    private HashMap<Integer, Batiment> batimentHashMap = new HashMap<>();
    private static final BatimentRepository INSTANCE = new BatimentRepository();

    public BatimentRepository() {
    }

    public static BatimentRepository getInstance() {
        return INSTANCE;
    }

    public HashMap<Integer, Batiment> getBatiments() {
        return batimentHashMap;
    }

    public void addBatiment(Batiment batiment){
        Log.i("CategoryRepository", "addCategory: " + batiment.getName() + " " + batiment.getCode());
        batimentHashMap.put(batimentHashMap.size(), batiment);
    }

    public void removeBatiment(Batiment batiment){
        int id = -1;
        int i = 0;

        while(id != -1 && i < batimentHashMap.size()){
            if(batimentHashMap.get(i).equals(batiment)){
                id = i;
            }
            i++;
        }
    }

    public void removeBatiment(int id){
        batimentHashMap.remove(id);
    }

    public Batiment getCategory(int id){
        return batimentHashMap.get(id);
    }



}
