package fr.valres.app.repository;

import android.content.Intent;
import android.util.Log;

import java.util.HashMap;

import fr.valres.app.model.Category;

public class CategoryRepository {

    private HashMap<Integer, Category> categorieHashMap = new HashMap<>();
    private static final CategoryRepository INSTANCE = new CategoryRepository();

    public CategoryRepository() {
    }

    public static CategoryRepository getInstance() {
        return INSTANCE;
    }

    public HashMap<Integer, Category> getCategories() {
        return categorieHashMap;
    }

    public void addCategory(Category categorie){
        Log.i("CategoryRepository", "addCategory: " + categorie.getId() + " " + categorie.getNom());
        categorieHashMap.put(categorie.getId(), categorie);
    }

    public void removeCategory(Category categorie){
        categorieHashMap.remove(categorie.getId());
    }

    public void removeCategory(int id){
        categorieHashMap.remove(id);
    }

    public Category getCategory(int id){
        return categorieHashMap.get(id);
    }

}
