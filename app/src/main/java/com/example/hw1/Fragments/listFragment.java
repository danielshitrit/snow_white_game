package com.example.hw1.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//import com.example.hw1.Adapter;
import com.example.hw1.Data.DataManager;
import com.example.hw1.Data.Player;
import com.example.hw1.R;
import com.example.hw1.Interfaces.TableCallBack;
import com.google.gson.Gson;

public class listFragment extends Fragment {

    private ListView listView;
//    private TopTen topTen;
//    private ArrayList<String> playerNames;
//    private ArrayList<String> rates;
 //   private ArrayList<String> scores;
//    private Adapter adapter;
    private TableCallBack tableCallBack;
    private DataManager allPlayers;
    private final String PLAYER = "player";
//    private Gson gason;
//    private final int SIZE = 10;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                double lat = DataManager.topPlayers.get(position).getLatitude();
                double lon = DataManager.topPlayers.get(position).getLongitude();
                String name =DataManager.topPlayers.get(position).getName();
                tableCallBack.showLocation(lat, lon, name);
            }
        });

        return view;
    }

    private void initViews() {
        ArrayAdapter<Player> arrayAdapter =new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, DataManager.topPlayers);
        listView.setAdapter(arrayAdapter);
    }



//    private void initAdapter() {
//        for(int i =1; i<= SIZE; i++)
//            rates.add(i+"");
//
//        String gson_topTen = MySp.getInstance().getString(EndGameActivity.TOP_TEN, EndGameActivity.NOT_EXIST);
//
//        if(!gson_topTen.equals(EndGameActivity.NOT_EXIST)){
//            topTen = gason.fromJson(gson_topTen, TopTen.class);
//            int size = topTen.getPlayers().size();
//            ArrayList<Player> players = topTen.getPlayers();
//
//            for (Player p : players) {
//                playerNames.add(p.getName());
//                scores.add("score: "+p.getScore());
//            }
//
//            adapter=new Adapter(getActivity(), playerNames, scores,rates);
//            fragmentrecords_LV_list.setAdapter(adapter);
//        }
//    }

    private void findViews(View view) {
        listView = view.findViewById(R.id.fragmentList_tt);
    }


    public void setTableCallBack(TableCallBack tableCallBack) {
        this.tableCallBack = tableCallBack;
    }

}