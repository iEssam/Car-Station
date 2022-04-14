package com.example.login22_01_19_h1;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.ViewHolder> {
    ArrayList<com.example.login22_01_19_h1.Dates> dates;
    private int selectedItemPosition = -1;
    //    OnDateListener mOnDateListener;
    final private DatesAdapter.OnDateListener m0onDateListener;

    public DatesAdapter(ArrayList<com.example.login22_01_19_h1.Dates> dates, OnDateListener onDateListener) {
        this.dates = dates;
        this.m0onDateListener = onDateListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datecard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // set text to the design
        holder.datetext.setText(dates.get(position).getTextdate());
        holder.dayname.setText(dates.get(position).getDaysname());

        Resources res = MainActivity.getAppContext().getResources();
        if(selectedItemPosition==position){
            holder.shape.setBackgroundTintList(ColorStateList.valueOf(res.getColor(R.color.CarStation2, MainActivity.getAppContext().getTheme())));
//            holder.shape.setBackgroundColor(Color.parseColor("#567845"));
        }
        else
        {
            holder.shape.setBackgroundTintList(ColorStateList.valueOf(res.getColor(R.color.CarStation1, MainActivity.getAppContext().getTheme())));
//            holder.shape.setBackgroundColor(Color.parseColor("#A2B38B"));
        }

    }

    @Override
    public int getItemCount() {

        return dates.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Initialize variable
        TextView datetext;
        TextView dayname;
        LinearLayout shape ;

        //        OnDateListener onDateListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assigning variable
            datetext = itemView.findViewById(R.id.datetext);
            dayname = itemView.findViewById(R.id.dayname);
            shape = itemView.findViewById(R.id.shape);
//            this.onDateListener = onDateListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            m0onDateListener.onDateClick(getAdapterPosition());
            selectedItemPosition = getAdapterPosition();
            notifyDataSetChanged();
        }
    }

    // to link the recyclerview with buutons on action
    public interface OnDateListener {
        void onDateClick(int position);
    }
}
