package com.cheekibreeki.whatsfridge;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Repository {
    private static final Repository ourInstance = new Repository();
    private final String BASE_STRING = "https://api.spoonacular.com/recipes/findByIngredients";
    private static final String API_KEY = "";   //get Ur own key, BLIN

    public static Repository getInstance() {
        return ourInstance;
    }

    public interface OnLoadingFinished{
        void loadingFinished(ArrayList<Recipe> recipes);
    }

    public interface OnLoadingIngredientsFinished{
        void loadingFinished(ArrayList<Recipe.Ingredient> ingredients);
    }

    public interface OnLoadingJsonFinished{
        void loadingFinished(JSONArray list);
    }

    private Repository() {
    }

    public void loadRecipes(String search, final OnLoadingFinished listener){
        new JsonLoader(new OnLoadingJsonFinished() {
            @Override
            public void loadingFinished(JSONArray list){
                ArrayList<Recipe> recipes = new ArrayList<>();
                for (int i=0; i<list.length() ; i++) {
                    try {
                        recipes.add(new Recipe(list.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.loadingFinished(recipes);
            }
        }).execute(search);
    }

    public void loadIngredientsAutocomplete(String query, final OnLoadingIngredientsFinished listener){
        new JsonLoader(new OnLoadingJsonFinished() {

            @Override
            public void loadingFinished(JSONArray list) {
                ArrayList<Recipe.Ingredient> ingredients = new ArrayList<>();
                for(int i=0; i<list.length(); i++){
                    try {
                        JSONObject obj = list.getJSONObject(i);
                        ingredients.add(new Recipe.Ingredient(obj.getString("name")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.loadingFinished(ingredients);
            }
        }).execute(query);
    }


    private static class JsonLoader extends AsyncTask<String, Void, JSONArray>{

        private OnLoadingJsonFinished listener;

        public JsonLoader(OnLoadingJsonFinished listener) {
            this.listener = listener;
        }

        @Override
        protected JSONArray doInBackground(String... strings) {
            try {
                StringBuilder sb = new StringBuilder();

                URL searchQuery = new URL(strings[0]);
                InputStream stream = searchQuery.openConnection().getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                String line;
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                stream.close();
                reader.close();

                return new JSONArray(sb.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray list) {
            super.onPostExecute(list);
            listener.loadingFinished(list);
        }
    }

    public static class RequestBuilder{
        private StringBuilder sb;
        public static final int SEARCH_BY_INGREDIENTS = 0;
        public static final int AUTOCOMPLETE_SEARCH_INGREDIENTS = 1;

        public RequestBuilder(int type){
            switch (type) {
                case SEARCH_BY_INGREDIENTS:
                    sb = new StringBuilder("https://api.spoonacular.com/recipes/findByIngredients?");
                    sb.append("apiKey=").append(API_KEY);
                    sb.append("&number=20&ranking=2");
                break;
                case AUTOCOMPLETE_SEARCH_INGREDIENTS:
                    sb = new StringBuilder("https://api.spoonacular.com/food/ingredients/autocomplete?");
                    sb.append("apiKey=").append(API_KEY);
                    sb.append("&number=7");
            }
        }

        public RequestBuilder addQuery(String query){
            sb.append("&query=").append(query);
            return this;
        }

        public RequestBuilder addIngredients(String ingredients){
            sb.append("&ingredients=").append(ingredients);
            return this;
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }
}
