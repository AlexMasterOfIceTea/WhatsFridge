package com.cheekibreeki.whatsfridge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recipe {
    public int id;
    public String title;
    public String imageURL;
    public ArrayList<Ingredient> missingIngredients;
    public ArrayList<Ingredient> usedIngredients;

    public Recipe(JSONObject data) throws JSONException {
        id = data.getInt("id");
        title = data.getString("title");
        imageURL = data.getString("image");
        missingIngredients = new ArrayList<>();
        usedIngredients = new ArrayList<>();
        JSONArray missedIngredientsJson = data.getJSONArray("missedIngredients");
        for(int i=0; i<missedIngredientsJson.length(); i++){
            missingIngredients.add(new Ingredient(missedIngredientsJson.getJSONObject(i)));
        }
        JSONArray usedIngredientsJson = data.getJSONArray("usedIngredients");
        for(int i=0; i<usedIngredientsJson.length(); i++){
            usedIngredients.add(new Ingredient(usedIngredientsJson.getJSONObject(i)));
        }
    }

    public String getMissingIngredientsText(){
        StringBuilder sb = new StringBuilder();
        for(Ingredient i: missingIngredients){
            sb.append("-").append(i.text);
        }
        return sb.toString();
    }

    public String getUsedIngredientsText(){
        StringBuilder sb = new StringBuilder();
        for(Ingredient i: usedIngredients){
            sb.append("-").append(i.text);
        }
        return sb.toString();
    }

    public static class Ingredient {
        public String aisle;
        public float amount;
        public int id;
        public String imageUrl;
        public String text;
        public String name;

        public Ingredient(JSONObject data) throws JSONException {
            aisle = data.getString("aisle");
            amount = (float) data.getDouble("amount");
            id = data.getInt("id");
            imageUrl = data.getString("image");
            text = data.getString("originalString");
            name = data.getString("name");
        }

        public Ingredient(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

