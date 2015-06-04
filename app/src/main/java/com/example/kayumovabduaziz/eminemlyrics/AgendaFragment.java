package com.example.kayumovabduaziz.eminemlyrics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class AgendaFragment extends Fragment {

    private Toolbar toolBar;
    ListView agendaList;
    AgendaListAdapter adapter;
    String[] times={"09:00 AM - 09:30 AM",
            "09:30 AM � 09:40 AM",
            "09:40 AM � 10:40 AM",
            "10:40 AM � 10:55 AM",
            "10:55 AM � 11:55 AM",
            "11:55 AM � 12:10 AM",
            "12:10 AM � 12:30 AM",
            "12:30 AM � 12:50 AM",
            "12:50 AM � 13:10 PM",
            "13:10 PM � 13:30 PM",
            "13:30 PM � 14:30 PM",
            "14:30 PM � 16:00 PM",
            "16:00 PM � 17:30 PM",
            "16:00 PM � 17:30 PM",
            "17:30 PM � 18:00 PM",
            "18:00 PM � 19:00 PM"};
    String[] titles={"Participants� registration",
            "Welcoming speech on behalf of Inha University in Tashkent",
            "Google I/O Keynote",
            "COFFEE BREAK",
            "Android M",
            "COFFEE BREAK",
            "Presentation of Android-based product by Artel (TBC)",
            "Presentation of Android-based products by Samsung",
            "Daryo.uz: top android app of Uzbekistan",
            "Google�s Go language",
            "BREAK",
            "Cardboard 2, VR applications ",
            "Animations on Android",
            "Front-end frameworks of 2015",
            "BREAK",
            "Various activities "};
    String[] descriptions={"Inha University in Tashkent Entrance Hall",
            "Room #202, 2nd floor",
            "Google VP, top managers and developers - Video streaming from the main Google I/O event",
            "",
            "Representatives of Google - Video streaming from the main Google I/O event",
            "",
            "Representative of Artel",
            "Mr. Mavlon Mamarasulov, Mr. Sherzod Aliev, managers from Samsung Electronics representative office in Uzbekistan",
            "Mr. Farkhod Fayzullaev, founder of Daryo.uz",
            "React & Angular by Mr. Mirolim Mirzakhmedov, NewMax Technologies",
            "",
            "Mr. Shakhruz Ashirov, Inha University in Tashkent\n" +
                    "Room #202, 2nd floor\n",
            "Mr. Aziz Murtazaev, Mobile software developer\n" +
                    "Room #101, 1st floor",
            "Mr. Abdullo Xidoyatov & Mr. Maksadbek Axmedov, NewMax Technologies\n" +
                    "Room #203, 2nd floor",
            "",
            "by IUT Students (e.g. contest on Fast Coding, Fast Typing) \n" +
                    "Room #202, 2nd floor"};
    int[] pics = {R.drawable.registration,
               R.drawable.sundar,
            R.drawable.keynote,
            R.drawable.coffee,
            R.drawable.androidm,
            R.drawable.shortbreak,
            R.drawable.artel,
            R.drawable.samsung,
            R.drawable.daryo,
            R.drawable.go,
            R.drawable.coffee,
            R.drawable.vr,
            R.drawable.anim,
            R.drawable.angular,
            R.drawable.shortbreak,
            R.drawable.iocolor};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View main = View.inflate(getActivity(), R.layout.activity_agenda, null);


        toolBar = (Toolbar)main.findViewById(R.id.app_bar);
        ActionBarActivity activity = (ActionBarActivity)getActivity();
/*
        activity.setSupportActionBar(toolBar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/

        activity.getSupportActionBar().setTitle("Agenda");

        agendaList = (ListView)main.findViewById(R.id.list_agenda);
        adapter = new AgendaListAdapter(activity, R.layout.agenda_row_layout);
        agendaList.setAdapter(adapter);

        int i=0;
        for(String mList: times){
            AgendaListProvider agendaListProvider = new AgendaListProvider(pics[i],titles[i],times[i],descriptions[i]);
            adapter.add(agendaListProvider);
            i++;
        }
        return main;
    }
}
