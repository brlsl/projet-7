package com.example.go4lunch.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.models.apiGooglePlace.SearchResult;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {
    private List<SearchResult> mRestaurantList;


    public RestaurantAdapter(List<SearchResult> items){
        mRestaurantList = items;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant,parent,false);


        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        SearchResult results = mRestaurantList.get(position);
        holder.mRestaurantName.setText(results.getName());
    }

    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }
}
