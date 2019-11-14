package com.example.agodahotel.Model;

public class Hotel {
    private int id;
    private String hotel_name;
    private String area;
    private int star;
    private int number_of_room;

    public Hotel(int id, String hotel_name, String area, int star, int number_of_room) {
        this.id = id;
        this.hotel_name = hotel_name;
        this.area = area;
        this.star = star;
        this.number_of_room = number_of_room;
    }
    public Hotel(){
        this.id = -1;
        this.hotel_name = "dummy";
        this.area = "N/A";
        this.star = 0;
        this.number_of_room = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getNumber_of_room() {
        return number_of_room;
    }

    public void setNumber_of_room(int number_of_room) {
        this.number_of_room = number_of_room;
    }
}
