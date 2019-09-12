package com.cheekibreeki.whatsfridge.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cheekibreeki.whatsfridge.R;
import com.cheekibreeki.whatsfridge.Recipe;
import com.cheekibreeki.whatsfridge.adapters.RecipeAdapter;

import java.util.ArrayList;

public class ResultsFragment extends Fragment {

    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.results_frag, container, false);
        mRecyclerView = view.findViewById(R.id.recipes);
        return view;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        RecipeAdapter adapter = new RecipeAdapter(recipes);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }
}
