package com.cheekibreeki.whatsfridge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cheekibreeki.whatsfridge.R;
import com.cheekibreeki.whatsfridge.Recipe;
import com.cheekibreeki.whatsfridge.Repository;

import java.util.ArrayList;

public class IngredientsAutocompleteAdapter extends BaseAdapter implements Filterable {

    private LayoutInflater mInflater;
    private ArrayList<Recipe.Ingredient> list;

    public IngredientsAutocompleteAdapter(@NonNull Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Recipe.Ingredient getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateResults(String query){
        Repository repo = Repository.getInstance();
        Repository.RequestBuilder builder = new Repository.RequestBuilder(Repository.RequestBuilder.AUTOCOMPLETE_SEARCH_INGREDIENTS);
        builder.addQuery(query);

        repo.loadIngredientsAutocomplete(builder.toString(), new Repository.OnLoadingIngredientsFinished() {
            @Override
            public void loadingFinished(ArrayList<Recipe.Ingredient> ingredients) {
                list = ingredients;
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = mInflater.inflate(R.layout.ingredients_short, parent, false);

        Recipe.Ingredient ingredient = getItem(position);

        TextView tv = convertView.findViewById(R.id.tv_tab);
        tv.setText(ingredient.name);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                results.values = list;
                results.count = list.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }
}
