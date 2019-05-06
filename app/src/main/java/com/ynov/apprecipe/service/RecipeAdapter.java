package com.ynov.apprecipe.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ynov.apprecipe.interfaces.IRecyclerViewListener;
import com.ynov.apprecipe.R;
import com.ynov.apprecipe.model.Recipe;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private ArrayList<Recipe> mListRecipe;
    private IRecyclerViewListener listener;
    private Context mContext;

    // Provide a suitable constructor
    public RecipeAdapter(ArrayList<Recipe> mListRecipe, IRecyclerViewListener listener) {
        this.listener = listener;
        this.mListRecipe = mListRecipe;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_recipe_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Recipe recipe = mListRecipe.get(position);
        holder.title.setText(recipe.getTitle());
        holder.portions.setText(recipe.getPortions());

        Picasso.get().load(recipe.getPicture_url()).fit().centerInside().into(holder.picture);
        /*Picasso.with(mContext).load(imageUrl).into(holder.picture);
        holder.picture.setText(recipe.getPicture_url());*/
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mListRecipe.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView portions;
        public ImageView picture;
        private LinearLayout cell;


        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            portions = itemView.findViewById(R.id.portions);
            picture = itemView.findViewById(R.id.picture);
            cell = itemView.findViewById(R.id.cell);

            this.cell.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Recipe recipe = mListRecipe.get(getAdapterPosition());
            listener.onItemClick(recipe);
        }
    }
}

