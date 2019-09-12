package com.cheekibreeki.whatsfridge.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cheekibreeki.whatsfridge.R;
import com.cheekibreeki.whatsfridge.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private ArrayList<Recipe.Ingredient> ingredients;
    private OnClicked listener;
    public interface OnClicked{
        void btnClicked(Recipe.Ingredient ingredient);
        void textClicked(Recipe.Ingredient ingredient);
    }

    public IngredientsAdapter(ArrayList<Recipe.Ingredient> list, OnClicked listener){
        ingredients = list;
        this.listener = listener;
    }

    public IngredientsAdapter(Recipe.Ingredient[] array, OnClicked listener){
        ingredients = new ArrayList<>(Arrays.asList(array));
        this.listener = listener;
    }

    public ArrayList<Recipe.Ingredient> getItems(){
        return ingredients;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_short, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder ingredientsViewHolder, int i) {
        String name = ingredients.get(i).name;
        ingredientsViewHolder.name.setText(name);
        ingredientsViewHolder.ingredient = ingredients.get(i);
        ingredientsViewHolder.listener = listener;
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientsViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name;
        Recipe.Ingredient ingredient;
        OnClicked listener;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            img =  itemView.findViewById(R.id.iv_tab);
            name = itemView.findViewById(R.id.tv_tab);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.textClicked(ingredient);
                }
            });
            ImageButton btn = itemView.findViewById(R.id.btn_cancel_tab);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.btnClicked(ingredient);
                }
            });
        }
    }
}
