package com.example.agodahotel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.example.agodahotel.Model.Hotel;
import com.example.agodahotel.Model.HotelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HotelMain extends AppCompatActivity {

    private ArrayList<Hotel> hotelList = new ArrayList<>();//list to populates the recycler view
    private ArrayList<Hotel> hotelList_origin = new ArrayList<>();//original list to store all data from JSON
    public RecyclerView hotel_list_view;
    private static String TAG = "HOTEL_MAIN";
    RecyclerView.Adapter hotelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_main);

        /**setting up navigation view and toolbar**/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ViewCompat.setLayoutDirection(toolbar, ViewCompat.LAYOUT_DIRECTION_RTL);
        toolbar.setNavigationIcon(R.drawable.ic_filter);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

        /**sidebar menu setup**/
        MenuItem switchItem = navigationView.getMenu().findItem(R.id.nav_sort_switch);
        MenuItem star_one = navigationView.getMenu().findItem(R.id.filter_star_one);
        MenuItem star_two = navigationView.getMenu().findItem(R.id.filter_star_two);
        MenuItem star_three = navigationView.getMenu().findItem(R.id.filter_star_three);
        MenuItem star_four = navigationView.getMenu().findItem(R.id.filter_star_four);
        MenuItem star_five = navigationView.getMenu().findItem(R.id.filter_star_five);

        /**sorting switch**/
        CompoundButton switchView = (CompoundButton) switchItem.getActionView();
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //sort alphabetically
                if (isChecked == true){
                    Collections.sort(hotelList);
                    hotelAdapter.notifyDataSetChanged();
                }else {
                    //sort by ID
                    Collections.sort(hotelList, new Comparator<Hotel>() {
                        @Override
                        public int compare(Hotel o1, Hotel o2) {
                            //compare ID,Descending
                            if (o2.getId() > o1.getId()){
                                return -1;
                            }else{
                                return  1;
                            }
                        }
                    }
                    );
                    hotelAdapter.notifyDataSetChanged();
                }

            }
        });

        /**filter star rating**/
        final ArrayList<Integer> conditons = new ArrayList<>();//arraylist that store conditon values for filtering
        CompoundButton filter_one = (CompoundButton) star_one.getActionView();
        CompoundButton filter_two = (CompoundButton) star_two.getActionView();
        CompoundButton filter_three = (CompoundButton) star_three.getActionView();
        CompoundButton filter_four = (CompoundButton) star_four.getActionView();
        CompoundButton filter_five = (CompoundButton) star_five.getActionView();
        filter_one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    conditons.add(1);
                    Log.d(TAG, "onCheckedChanged: " + conditons.toString());
                    filter_list(conditons);
                }else {
                    conditons.remove(Integer.valueOf(1));
                    Log.d(TAG, "onCheckedChanged: " + conditons.toString());
                    filter_list(conditons);
                }
            }
        });
        filter_two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    conditons.add(2);
                    Log.d(TAG, "onCheckedChanged: " + conditons.toString());
                    filter_list(conditons);
                }else {
                    conditons.remove(Integer.valueOf(2));
                    Log.d(TAG, "onCheckedChanged: " + conditons.toString());
                    filter_list(conditons);
                }
            }
        });
        filter_three.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    conditons.add(3);
                    Log.d(TAG, "onCheckedChanged: " + conditons.toString());
                    filter_list(conditons);
                }else {
                    conditons.remove(Integer.valueOf(3));
                    Log.d(TAG, "onCheckedChanged: " + conditons.toString());
                    filter_list(conditons);
                }

            }
        });
        filter_four.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    conditons.add(4);
                    Log.d(TAG, "onCheckedChanged: " + conditons.toString());
                    filter_list(conditons);
                }else {
                    conditons.remove(Integer.valueOf(4));
                    Log.d(TAG, "onCheckedChanged: " + conditons.toString());
                    filter_list(conditons);
                }

            }
        });
        filter_five.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    conditons.add(5);
                    Log.d(TAG, "onCheckedChanged: " + conditons.toString());
                    filter_list(conditons);
                }else {
                    conditons.remove(Integer.valueOf(5));
                    Log.d(TAG, "onCheckedChanged: " + conditons.toString());
                    filter_list(conditons);
                }

            }
        });

        /**loading and populating recycler view**/
        ///////////////////////////////////////////////////////
        hotel_list_view = findViewById(R.id.hotel_list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        hotel_list_view.setLayoutManager(linearLayoutManager);
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("hotels");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                int id_value = jo_inside.getInt("id");
                String hotelName = jo_inside.getString("hotelName");
                String area = jo_inside.getString("area");
                int star = jo_inside.getInt("star");
                int numberOfRoom = jo_inside.getInt("numberOfRoom");

                //Add hotel
                hotelList.add(new Hotel(id_value,hotelName,area,star,numberOfRoom));
                //store original list
                hotelList_origin.add(new Hotel(id_value,hotelName,area,star,numberOfRoom));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hotelAdapter = new HotelAdapter(this,hotelList);
        hotel_list_view.setAdapter(hotelAdapter);
        ///////////////////////////////////////////////////////
    }
    /**read data from JSON**/
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("hotels.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**Filter arraylist according to set conditions**/
    public void filter_list(ArrayList<Integer> conditions){
        ArrayList<Hotel> filtered_list = new ArrayList<Hotel>();

        //if there is no condition,return original list
        if (conditions.size() == 0){
            //clear the main list,then add back the original list
            hotelList.clear();
            hotelList.addAll(clone_list(hotelList_origin));
            hotelAdapter.notifyDataSetChanged();
        }else{
            for (int i = 0; i < hotelList_origin.size();i++){
                for (int j = 0; j < conditions.size(); j++) {
                    if (hotelList_origin.get(i).getStar() == conditions.get(j)) {
                        filtered_list.add(hotelList_origin.get(i));
                    }
                }
            }
            //clear tha main list,then add the filtered list
            hotelList.clear();
            hotelList.addAll(clone_list(filtered_list));
            hotelAdapter.notifyDataSetChanged();
        }
    }

    /**method for cloning array list**/
    public ArrayList<Hotel> clone_list(ArrayList<Hotel> list_to_clone){
        ArrayList<Hotel> list = new ArrayList<Hotel>();
        for (int i = 0; i < list_to_clone.size();i++){
            list.add(list_to_clone.get(i));
        }
        return list;
    }
}
