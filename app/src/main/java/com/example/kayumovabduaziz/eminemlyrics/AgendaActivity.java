package com.example.kayumovabduaziz.eminemlyrics;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class AgendaActivity extends ActionBarActivity {

    private Toolbar toolBar;
    ListView agendaList;
    AgendaListAdapter adapter;
    String[] times={"09:00 AM - 09:30 AM",
            "09:30 AM – 09:40 AM",
            "09:40 AM – 10:40 AM",
            "10:40 AM – 10:55 AM",
            "10:55 AM – 11:55 AM",
            "11:55 AM – 12:10 AM",
            "12:10 AM – 12:30 AM",
            "12:30 AM – 12:50 AM",
            "12:50 AM – 13:10 PM",
            "13:10 PM – 13:30 PM",
            "13:30 PM – 14:30 PM",
            "14:30 PM – 16:00 PM",
            "16:00 PM – 17:30 PM",
            "16:00 PM – 17:30 PM",
            "17:30 PM – 18:00 PM",
            "18:00 PM – 19:00 PM"};
    String[] titles={"Participants’ registration",
            "Welcoming speech on behalf of Inha University in Tashkent",
            "Google I/O Keynote",
            "COFFEE BREAK",
            "Android M",
            "COFFEE BREAK",
            "Presentation of Android-based product by Artel (TBC)",
            "Presentation of Android-based products by Samsung",
            "Daryo.uz: top android app of Uzbekistan",
            "Google’s Go language",
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        toolBar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Agenda");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agendaList = (ListView)findViewById(R.id.list_agenda);
        adapter = new AgendaListAdapter(getApplicationContext(),R.layout.agenda_row_layout);
        agendaList.setAdapter(adapter);

        int i=0;
        for(String mList: times){
            AgendaListProvider agendaListProvider = new AgendaListProvider(pics[i],titles[i],times[i],descriptions[i]);
            adapter.add(agendaListProvider);
            i++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agenda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }

        if(id==R.id.about)
        {
            startActivity(new Intent(this,AboutActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }
}
