package com.cheekibreeki.whatsfridge.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cheekibreeki.whatsfridge.CustomLayoutManager;
import com.cheekibreeki.whatsfridge.R;
import com.cheekibreeki.whatsfridge.Recipe;
import com.cheekibreeki.whatsfridge.adapters.IngredientsAdapter;

public class CuisineFragment extends Fragment implements IngredientsAdapter.OnClicked {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_cousine_frag, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_cuisine);

Recipe.Ingredient[] ingredients = new Recipe.Ingredient[]{
        new Recipe.Ingredient("African"), new Recipe.Ingredient("American"), new Recipe.Ingredient("British"),
        new Recipe.Ingredient("Cajun"), new Recipe.Ingredient("Caribbean"), new Recipe.Ingredient("Chinese"),
        new Recipe.Ingredient("Eastern European"), new Recipe.Ingredient("European"), new Recipe.Ingredient("French"),
        new Recipe.Ingredient("German"), new Recipe.Ingredient("Greek"), new Recipe.Ingredient("Indian"),
        new Recipe.Ingredient("Irish"), new Recipe.Ingredient("Italian"), new Recipe.Ingredient("Japanese"),
        new Recipe.Ingredient("Jewish"), new Recipe.Ingredient("Korean"), new Recipe.Ingredient("Latin American"),
        new Recipe.Ingredient("Mediterranean"), new Recipe.Ingredient("Mexican"), new Recipe.Ingredient("Middle Eastern"),
        new Recipe.Ingredient("Nordic"), new Recipe.Ingredient("Southern"), new Recipe.Ingredient("Spanish"),
        new Recipe.Ingredient("Thai"), new Recipe.Ingredient("Vietnamese")};

        IngredientsAdapter adapter = new IngredientsAdapter(ingredients, this);
        CustomLayoutManager layoutManager = new CustomLayoutManager(2000);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void btnClicked(Recipe.Ingredient ingredient) {

    }

    @Override
    public void textClicked(Recipe.Ingredient ingredient) {

    }
}
