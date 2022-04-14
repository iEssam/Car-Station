package com.example.login22_01_19_h1.SliderUpcoming_Resrv;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
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

public class adapterCardUpcoming extends RecyclerView.Adapter<adapterCardUpcoming.ViewHolder> {

    private Context context;
    private int selectedItemPosition = -1;



    ArrayList<UpcomingCardHelper> CardlocationsU;
    final private ListItemClickListenerReserv OnClickListener;

    public adapterCardUpcoming(Context context,ArrayList<UpcomingCardHelper> Cardlocations, HomeFragment listener) {
        this.CardlocationsU = Cardlocations;
        OnClickListener = listener;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        ImageView ImageLink;
        TextView Date;
        TextView Time;
        TextView Location;
        TextView CarName;
        TextView MCenterName;

        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //hooks

            image = itemView.findViewById(R.id.car_image_upcoming);
            ImageLink = itemView.findViewById(R.id.Center_Link_upcoming);
            Date = itemView.findViewById(R.id.Date_upcoming);
            Time = itemView.findViewById(R.id.Time_upcoming);
            Location = itemView.findViewById(R.id.Location_upcoming);
            CarName = itemView.findViewById(R.id.CarName_upcoming);
            MCenterName = itemView.findViewById(R.id.Center_upcoming);

            relativeLayout = itemView.findViewById(R.id.background_color_upcoming);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            OnClickListener.onViewListClick(clickedPosition, Date.getText().toString());
            selectedItemPosition = getAdapterPosition();
            notifyDataSetChanged();
        }


    }


    public interface ListItemClickListenerReserv {
        void onViewListClick(int clickedItemIndex, String title);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_card, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { // Resonseable for Up And Down Movement

        UpcomingCardHelper upcomingCardHelper = CardlocationsU.get(position);

        holder.image.setImageBitmap(upcomingCardHelper.getmCarImage());
        holder.ImageLink.setImageResource(R.drawable.ic_directions);
        holder.ImageLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                Toast.makeText(v.getContext(), "You Have Pressed Image ", Toast.LENGTH_SHORT).show();
                Intent openlink = new Intent(Intent.ACTION_VIEW);
                openlink.setData(Uri.parse(upcomingCardHelper.getUrls()));
                context.startActivity(openlink);
            }
        });
        holder.Date.setText(upcomingCardHelper.getDate());
        holder.Time.setText(upcomingCardHelper.getTime());
        holder.Location.setText(upcomingCardHelper.getLocation());
        holder.CarName.setText(upcomingCardHelper.getCarName());
        holder.MCenterName.setText(upcomingCardHelper.getMCenterName());

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
        return CardlocationsU.size();
    }

}