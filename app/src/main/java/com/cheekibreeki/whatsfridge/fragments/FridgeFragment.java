package com.cheekibreeki.whatsfridge.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cheekibreeki.whatsfridge.R;
import com.cheekibreeki.whatsfridge.Recipe;
import com.cheekibreeki.whatsfridge.Repository;
import com.cheekibreeki.whatsfridge.adapters.SectionPageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class FridgeFragment extends Fragment {

    private FloatingActionButton mAudioButton;
    private Repository repository;
    private ViewPager mViewPager;
    private SectionPageAdapter mAdapter;

    public static FridgeFragment newInstance() {
        return new FridgeFragment();
    }

    public FridgeFragment(){
        repository = Repository.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fridge_fragment, container, false);
        mAudioButton = view.findViewById(R.id.floatingActionButton);
        mViewPager = view.findViewById(R.id.viewPager);
        setupViewPager(mViewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabView);
        tabLayout.setupWithViewPager(mViewPager);

        mAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(intent, 1);
                }else{
                    Toast.makeText(getContext(), "Feature not in device cheeki breeki", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new SectionPageAdapter(getChildFragmentManager());
        mAdapter.addFragment(new IngredientsFragment(), "Ingredients");
        mAdapter.addFragment(new CuisineFragment(), "Cuisines");
        mAdapter.addFragment(new ResultsFragment(), "Recipes");
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setIngredients(String ingredients){
        mViewPager.setCurrentItem(2);
        final ResultsFragment fragment = (ResultsFragment)mAdapter.getItem(2);

        ingredients = ingredients.toLowerCase();
        repository.loadRecipes(new Repository.RequestBuilder(Repository.RequestBuilder.SEARCH_BY_INGREDIENTS).addIngredients(ingredients).toString(), new Repository.OnLoadingFinished() {
            @Override
            public void loadingFinished(ArrayList<Recipe> recipies) {
                fragment.setRecipes(recipies);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK && data != null){
                setIngredients(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
            }
        }
    }
}
