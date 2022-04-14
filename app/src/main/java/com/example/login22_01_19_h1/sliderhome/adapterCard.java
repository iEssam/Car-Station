package com.example.login22_01_19_h1.sliderhome;


import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login22_01_19_h1.MainActivity;
import com.example.login22_01_19_h1.Menu.HomeFragment;
import com.example.login22_01_19_h1.R;

import java.util.ArrayList;

public class adapterCard extends RecyclerView.Adapter<adapterCard.PhoneViewHold>  {

    ArrayList<CardHelper> Cardinfo;
    final private ListItemClickListener mOnClickListener;
    private int selectedItemPosition = -1;


    public adapterCard(ArrayList<CardHelper> phoneLaocations, HomeFragment listener) {
        this.Cardinfo = phoneLaocations;
        mOnClickListener = listener;
    }

    @NonNull

    @Override
    public PhoneViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddesign, parent, false);
        return new PhoneViewHold(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHold holder, int position) {
        CardHelper carhelper = Cardinfo.get(position);
        holder.image.setImageBitmap(carhelper.getmCarImage());
        holder.title.setText(carhelper.getCarName());

        Resources res = MainActivity.getAppContext().getResources();
        if(selectedItemPosition==position){
            holder.relativeLayout.setBackgroundTintList(ColorStateList.valueOf(res.getColor(R.color.CarStation4, MainActivity.getAppContext().getTheme())));
//            holder.shape.setBackgroundColor(Color.parseColor("#567845"));
        }
        else
        {
            holder.relativeLayout.setBackgroundTintList(ColorStateList.valueOf(res.getColor(R.color.CarStation5, MainActivity.getAppContext().getTheme())));
//            holder.shape.setBackgroundColor(Color.parseColor("#A2B38B"));
        }
    }

    @Override
    public int getItemCount() {
        return Cardinfo.size();

    }

    public interface ListItemClickListener {
        void onphoneListClick(int clickedItemIndex, String title,ArrayList<CardHelper> Cardinfo);
    }

    public class PhoneViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView title;
        RelativeLayout relativeLayout;

        public PhoneViewHold(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //hooks
            //background_color
            image = itemView.findViewById(R.id.car_image);
            title = itemView.findViewById(R.id.phone_title);
            relativeLayout = itemView.findViewById(R.id.background_color);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onphoneListClick(clickedPosition, title.getText().toString(),Cardinfo);
            selectedItemPosition = getAdapterPosition();
            notifyDataSetChanged();
        }
    }

}
