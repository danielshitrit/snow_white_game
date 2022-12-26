package com.example.hw1.Data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class DataManager {
    public static ArrayList<Player> topPlayers = new ArrayList<>();

    private static final String DB_FILE = "DB_FILE";

    private DataManager(){}

    public static void saveTopPlayers(Context context) {
        SharedPreferences sp = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < topPlayers.size(); i++) {
            editor.putString("PlayerName" + i, topPlayers.get(i).getName());
            editor.putInt("PlayerScore" + i, topPlayers.get(i).getScore());
            editor.putFloat("PlayerLatitude" + i, (float)topPlayers.get(i).getLatitude());
            editor.putFloat("PlayerLongitude" + i, (float)topPlayers.get(i).getLongitude());
        }
        editor.commit();
        topPlayers = new ArrayList<>();
    }

    public static void loadTopPlayers(Context context) {
        SharedPreferences sp = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        for (int i = 0; i < 10; i++) {
            String name = sp.getString("PlayerName" + i, "");  // The default value is an empty string if the name is not found
            int score = sp.getInt("PlayerScore" + i, 0);  // The default value is 0 if the score is not found
            float latitude = sp.getFloat("PlayerLatitude" + i, 0);  // The default value is 0 if the latitude is not found
            float longitude = sp.getFloat("PlayerLongitude" + i, 0);  // The default value is 0 if the longitude is not found
            if (!name.equals("")){
                addPlayer(new Player(name, score, latitude, longitude));
            }

        }
    }

    public static void addPlayer(Player player) {
        if (topPlayers.size() < 10) {
            topPlayers.add(player);
            sortPlayers();
        }
        else {
            for (int i = 0; i < topPlayers.size(); i++) {
                if (player.getScore() > topPlayers.get(i).getScore()) {
                    topPlayers.set(i, player);
                    sortPlayers();
                    break;
                }
            }
        }
    }

    private static void sortPlayers() {
        topPlayers.sort((player1, player2) -> player2.getScore() - player1.getScore());
    }


}
