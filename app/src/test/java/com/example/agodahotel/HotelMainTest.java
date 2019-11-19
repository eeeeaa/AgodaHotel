package com.example.agodahotel;

import com.example.agodahotel.Model.Hotel;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HotelMainTest {

    @Test
    public void clone_list() {
        HotelMain Main = new HotelMain();

        ArrayList<Hotel> A_list = new ArrayList<>();
        A_list.add(new Hotel(18,"A Hotel","Bangkok",2,100));
        A_list.add(new Hotel(12,"B Hotel","Chang Mai",3,200));
        ArrayList<Hotel> B_list = new ArrayList<>(Main.clone_list(A_list));
        assertEquals(A_list.get(0),B_list.get(0));
        assertEquals(A_list.get(1),B_list.get(1));
    }
}