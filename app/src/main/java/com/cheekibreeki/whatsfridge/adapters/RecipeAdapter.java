package com.cheekibreeki.whatsfridge.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cheekibreeki.whatsfridge.R;
import com.cheekibreeki.whatsfridge.Recipe;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private ArrayList<Recipe> recipes;

    public RecipeAdapter(ArrayList<Recipe> list){
        recipes = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Recipe recipe = recipes.get(i);

        myViewHolder.title.setText(recipe.title);
        myViewHolder.usedIngredients.setText(recipe.getUsedIngredientsText());
        myViewHolder.missingIgredients.setText(recipe.getMissingIngredientsText());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView usedIngredients;
        TextView missingIgredients;
        ImageView img;
        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            img = itemView.findViewById(R.id.iv_food);
            usedIngredients = itemView.findViewById(R.id.tv_used_ingredients);
            missingIgredients = itemView.findViewById(R.id.tv_missing_Ingredients);
        }
    }
}
