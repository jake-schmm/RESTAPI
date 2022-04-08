package com.example.recipe.controller;


import com.example.recipe.model.Recipe;
import com.example.recipe.service.RecipeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @RequestMapping(value="/recipes", method=RequestMethod.GET)
    public ResponseEntity<Object> readRecipes() {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> list = recipeService.getRecipeNames();
        String label = "recipeNames";
        map.put(label, list);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @RequestMapping(value="/recipes", method=RequestMethod.POST)
    public ResponseEntity<Object> createRecipe(@RequestBody Map<String, Object> rec) {
        Recipe r = new Recipe();
        r.setIngredients(String.join(",", (ArrayList<String>)rec.get("ingredients")));
        r.setInstructions(String.join(",", (ArrayList<String>)rec.get("instructions")));
        r.setRecipe_name(rec.get("recipe_name").toString());
        Recipe newRec = recipeService.createRecipe(r);
        if(newRec == null) {
            Map<String, String> errorStatement = new HashMap<String, String>();
            errorStatement.put("error", "Recipe already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorStatement);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }


        @RequestMapping(value="/recipes/details/{recipe_name}", method=RequestMethod.GET)
        public ResponseEntity<Object> readRecipes(@PathVariable(value="recipe_name") String name) {
            JSONObject jsonObject = recipeService.getNumIngredientsAndSteps(name);
            if(jsonObject == null) {
                Map<String, String> errorMessage = new HashMap<String, String>();
                errorMessage.put("error", "Recipe does not exist");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }
            return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
}

    @RequestMapping(value="/recipes", method=RequestMethod.PUT) 
    public ResponseEntity<Object> readRecipes(String recipeName, @RequestBody Map<String, Object> map) {
        Recipe r  = recipeService.updateRecipe(recipeName, String.join(",", 
        (ArrayList<String>)map.get("ingredients")), String.join(",", (ArrayList<String>)map.get("instructions")));
        if(r == null) {
            Map<String, String> errorMessage = new HashMap<String, String>();
                errorMessage.put("error", "Recipe does not exist");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @RequestMapping(value="/recipes/{recipe_name}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteRecipes(@PathVariable(value = "recipe_name") String name) {
        recipeService.deleteRecipe(name);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
    
    

    
}
