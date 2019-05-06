package com.ynov.apprecipe.service;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ynov.apprecipe.R;
import com.ynov.apprecipe.interfaces.IItemClickWeight;
import com.ynov.apprecipe.model.Weight;

import java.util.ArrayList;


public class RecyclerWeight extends RecyclerView.Adapter<RecyclerWeight.WeightViewHolder> {

    private ArrayList<Weight> mListWeight;
    private Context mContext;
    private IOnItemClickListener mListener;

    public interface IOnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);

        void onUpdateClick(int position);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }


    public class WeightViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView tv_weight;
        public ImageView mDeleteRow;


        public WeightViewHolder(View itemView, final IOnItemClickListener listener) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            tv_weight = itemView.findViewById(R.id.weight_v);
            mDeleteRow = itemView.findViewById(R.id.button_remove_weight);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int postion = getAdapterPosition();
                        if (postion != RecyclerView.NO_POSITION) {
                            listener.onItemClick(postion);
                        }
                    }
                }
            });
            mDeleteRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    // Provide a suitable constructor
    public RecyclerWeight(ArrayList<Weight> mListWeight) {
        this.mListWeight = mListWeight;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WeightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_weight_recycler, parent, false);
        return new WeightViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerWeight.WeightViewHolder holder, int position) {
        Weight weight = mListWeight.get(position);

        String myWeight = Float.toString(weight.getValue());
        holder.date.setText(weight.getDate());
        holder.tv_weight.setText(myWeight + " cm");

        Log.i("test", "onBindViewHolder: " + myWeight + holder.tv_weight.getText());
        /*Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.picture);
        holder.picture.setText(recipe.getPicture_url());*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mListWeight.size();
    }
}
