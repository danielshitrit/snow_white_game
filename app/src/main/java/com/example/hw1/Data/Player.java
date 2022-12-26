package com.example.hw1.Data;

public class Player{

    private String name;
    private int score;
    private double latitude;
    private double longitude;

    public Player(String name, int score, double latitude, double longitude) {
        this.name = name;
        this.score = score;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLocation(double newLat, double newLon){
        this.latitude = newLat;
        this.longitude = newLon;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    @Override
    public String toString(){ return "Player Name: " + name + ", Score: " + score; }
}
