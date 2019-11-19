package com.example.agodahotel;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.agodahotel.Model.Hotel;

import org.w3c.dom.Text;

public class HotelDetail extends AppCompatActivity {
    Hotel current_data;
    TextView hotel_name_display;
    TextView hotel_area_display;
    TextView hotel_star_display;
    TextView hotel_num_room_display;
    RatingBar rating_display;
    ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle bundle = this.getIntent().getExtras();
        hotel_name_display = findViewById(R.id.hotel_name_display);
        hotel_area_display = findViewById(R.id.hotel_area_display);
        hotel_star_display = findViewById(R.id.hotel_star_display);
        hotel_num_room_display = findViewById(R.id.hotel_num_room_display);
        rating_display = findViewById(R.id.rating_display);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(bundle !=null)
        {
            current_data = new Hotel(
                                    bundle.getInt("id_value"),
                                    bundle.getString("hotel_name"),
                                    bundle.getString("area"),
                                    bundle.getInt("star"),
                                    bundle.getInt("number_of_room")
                                    );
        }
        else
        {
            current_data = new Hotel();
        }

        hotel_name_display.setText(current_data.getHotel_name());
        hotel_area_display.setText("Area: " + current_data.getArea());
        hotel_star_display.setText("Stars: " + current_data.getStar() + "");
        hotel_num_room_display.setText("Number of rooms: " + current_data.getNumber_of_room() + "");
        rating_display.setRating(current_data.getStar());
    }
}
