package com.cheekibreeki.whatsfridge.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cheekibreeki.whatsfridge.CustomLayoutManager;
import com.cheekibreeki.whatsfridge.R;
import com.cheekibreeki.whatsfridge.Recipe;
import com.cheekibreeki.whatsfridge.adapters.IngredientsAdapter;
import com.cheekibreeki.whatsfridge.adapters.IngredientsAutocompleteAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * attribution
 * <a href="https://www.freepik.com/free-photos-vectors/food">Food vector created by katemangostar - www.freepik.com</a>
 */
public class IngredientsFragment extends Fragment implements IngredientsAdapter.OnClicked {

    private IngredientsAdapter adapter;
    private ArrayList<Recipe.Ingredient> ingredientList;
    private ArrayList<Recipe.Ingredient> selected;
    private IngredientsAdapter adapterSelected;

    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_ingredients_frag, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_ingredients);
        RecyclerView selectedView = view.findViewById(R.id.rv_selected);
        AutoCompleteTextView searchView = view.findViewById(R.id.search_bar);

        final IngredientsAutocompleteAdapter autoAdapter = new IngredientsAutocompleteAdapter(getContext());

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoAdapter.updateResults(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        searchView.setAdapter(autoAdapter);


        selected = new ArrayList<>();
        Recipe.Ingredient[] ingredients = new Recipe.Ingredient[]{
                new Recipe.Ingredient("carrot"), new Recipe.Ingredient("onion"), new Recipe.Ingredient("red onion"),
                new Recipe.Ingredient("cabbage"), new Recipe.Ingredient("salad"), new Recipe.Ingredient("chicken"),
                new Recipe.Ingredient("eggs"), new Recipe.Ingredient("sausage"), new Recipe.Ingredient("butter"),
                new Recipe.Ingredient("sugar"), new Recipe.Ingredient("potato"), new Recipe.Ingredient("pork"),
                new Recipe.Ingredient("cheese"), new Recipe.Ingredient("paprica"), new Recipe.Ingredient("chilly"),
                new Recipe.Ingredient("kale"), new Recipe.Ingredient("cucumber"), new Recipe.Ingredient("pumpkin")
        };
        adapter = new IngredientsAdapter(ingredients, this);
        adapterSelected = new IngredientsAdapter(selected, this);

        recyclerView.setAdapter(adapter);
        selectedView.setAdapter(adapterSelected);
        ingredientList = adapter.getItems();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//new CustomLayoutManager(2000));
        selectedView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        return view;
    }

    @Override
    public void btnClicked(Recipe.Ingredient ingredient) {
        if(ingredientList.contains(ingredient)){
            ingredientList.remove(ingredient);
            adapter.notifyDataSetChanged();
        }else if(selected.contains(ingredient)){
            selected.remove(ingredient);
            adapterSelected.notifyDataSetChanged();
            ingredientList.add(ingredient);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void textClicked(Recipe.Ingredient ingredient) {
        if(ingredientList.contains(ingredient)){
            ingredientList.remove(ingredient);
            adapter.notifyDataSetChanged();
            selected.add(ingredient);
            adapterSelected.notifyDataSetChanged();
        }
    }
}
