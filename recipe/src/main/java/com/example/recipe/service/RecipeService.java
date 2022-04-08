package com.example.recipe.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.recipe.model.Recipe;
import com.example.recipe.repository.RecipeRepository;

@Service
public class RecipeService {
    
    @Autowired
    RecipeRepository recRepository; 


    public List<Recipe> getRecipes() {
        return recRepository.findAll();
    }

    public List<String> getRecipeNames() {
        List<Recipe> list = getRecipes();
        List<String> retList = new ArrayList<String>();
        for(Recipe r : list) {
            retList.add(r.getRecipe_name());
        }
        return retList;
    }


    public JSONObject getNumIngredientsAndSteps(String recipeName) {
        JSONObject jsonObject = new JSONObject();
        Long id = getIdFromName(recipeName);
        if(id == -1) {
            return null;
        }
        Recipe r  = recRepository.findById(id).get();
        JSONObject innerObj = new JSONObject();
        innerObj.put("numIngredients", r.getIngredients().split(",").length);
        innerObj.put("numInstructions", r.getInstructions().split(",").length);
        jsonObject.put("details", innerObj);
        return jsonObject;
        
    }


    public Recipe createRecipe(Recipe rec) {
        List<Recipe> list = getRecipes();
        String recipeName = rec.getRecipe_name();
        for(int i = 0; i< list.size(); i++) {
            if(list.get(i).getRecipe_name().equals(recipeName)) {
                return null;
            }
        }
        return recRepository.save(rec);
    }

    public Recipe updateRecipe(String recipeName, String ingredients, String instructions) {
        Long id = getIdFromName(recipeName);
        if(id != -1) {
            Recipe r = recRepository.findById(id).get();
            r.setIngredients(ingredients);
            r.setInstructions(instructions);
            return recRepository.save(r);
        }
        return null;
    }

    public void deleteRecipe(String recipeName) {
        Long id = getIdFromName(recipeName);
        recRepository.deleteById(id);
    }

    private Long getIdFromName(String recipeName) {
        Long id = -1L;
        List<Recipe> list = getRecipes();
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getRecipe_name().equals(recipeName)) {
                id = list.get(i).getId();
                return id;
            }
        }
        return -1L;
    }

}
