package com.ynov.apprecipe.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.apprecipe.R;
import com.ynov.apprecipe.model.Measure;

import java.util.ArrayList;

public class RecyclerMeasure extends RecyclerView.Adapter<RecyclerMeasure.MeasureViewHolder> {

    private ArrayList<Measure> mListMeasure;
    private Context mContext;
    private RecyclerMeasure.IOnItemClickListener mListener;

    public interface IOnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener (RecyclerMeasure.IOnItemClickListener listener){
        mListener = listener;
    }

    public class MeasureViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView measure_v;
        public TextView measure_type_v;
        public ImageView mDeleteMeasure;


        public MeasureViewHolder(View itemView, final RecyclerMeasure.IOnItemClickListener listener) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            measure_v = itemView.findViewById(R.id.measure_v);
            mDeleteMeasure = itemView.findViewById(R.id.button_remove_m);
            measure_type_v = itemView.findViewById(R.id.measure_type_v);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        int postion = getAdapterPosition();
                        if (postion != RecyclerView.NO_POSITION){
                            listener.onItemClick(postion);
                        }
                    }
                }
            });
            mDeleteMeasure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    // Provide a suitable constructor
    public RecyclerMeasure(ArrayList<Measure> mListMeasure) {
        this.mListMeasure = mListMeasure;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MeasureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_measure_item, parent, false);
        return new MeasureViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerMeasure.MeasureViewHolder holder, int position) {
        Measure measure = mListMeasure.get(position);

        String myMeasure = Float.toString(measure.getValue());
        holder.date.setText(measure.getDate());
        holder.measure_type_v.setText(measure.getType());
        holder.measure_v.setText(myMeasure + " cm");

        Log.i("test", "onBindViewHolder: " + holder.measure_v.getText());
        /*Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.picture);
        holder.picture.setText(recipe.getPicture_url());*/

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mListMeasure.size();
    }
}

