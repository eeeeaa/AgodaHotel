package com.example.agodahotel.Model;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agodahotel.HotelDetail;
import com.example.agodahotel.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Hotel> hotelList;

    public HotelAdapter(Context context, ArrayList<Hotel> hotelList) {
        this.mContext = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_list_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.hotel_name.setText(hotelList.get(i).getHotel_name());
        viewHolder.hotel_area.setText("Area: " + hotelList.get(i).getArea());
        viewHolder.hotel_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putInt("id_value",hotelList.get(i).getId());
                data.putString("hotel_name",hotelList.get(i).getHotel_name());
                data.putString("area",hotelList.get(i).getArea());
                data.putInt("star",hotelList.get(i).getStar());
                data.putInt("number_of_room",hotelList.get(i).getNumber_of_room());
                Intent intent = new Intent(mContext, HotelDetail.class);
                intent.putExtras(data);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }
    public ArrayList<Hotel> getHotelList(){
        return hotelList;
    }

    public void printHotelList(){
        for(Hotel hotel : hotelList){
            Log.d(TAG, hotel.toString());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView hotel_name;
        TextView hotel_area;
        CardView hotel_card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotel_name = itemView.findViewById(R.id.hotel_name);
            hotel_area = itemView.findViewById(R.id.hotel_area);
            hotel_card_view = itemView.findViewById(R.id.hotel_card_view);
        }
    }
}
