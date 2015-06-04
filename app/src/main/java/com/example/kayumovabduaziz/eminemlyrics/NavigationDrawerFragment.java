package com.example.kayumovabduaziz.eminemlyrics;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class NavigationDrawerFragment extends android.support.v4.app.Fragment {


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "testkey";
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private View containerView;

    ListView mainList;
    String[] main_list={"Agenda", "News", "Videos",/*"Sponsors",*/"Map"};
    int[] pics = {R.drawable.agenda,
            R.drawable.news,
            R.drawable.videos,
            /*R.drawable.sponsor,*/
            R.drawable.moscone_marker};

    MainListAdapter adapter;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));

        if(savedInstanceState!=null)
        {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        mainList = (ListView)layout.findViewById(R.id.mainList);
        adapter = new MainListAdapter(getActivity(), R.layout.row_layout);
        mainList.setAdapter(adapter);

        int i=0;
        for(String mList: main_list){
            MainListProvider mainListProvider = new MainListProvider(pics[i],main_list[i]);
            adapter.add(mainListProvider);
            i++;
        }

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawers();
                onClicksNavigationDrawerItems(position);
            }
        });
        return layout;
    }

    public void onClicksNavigationDrawerItems(int item)
    {
        FragmentManager fm = ((ActionBarActivity) getActivity()).getSupportFragmentManager();
        int id = item;
        switch (id)
        {
            case 0:
                fm.beginTransaction()
                        .replace(R.id.container, new AgendaFragment())
                        .commit();
                //startActivity(new Intent(getActivity(),AgendaActivity.class));
                break;
            case 1:
                fm.beginTransaction()
                        .replace(R.id.container, new NewsFragment())
                        .commit();
                //startActivity(new Intent(getActivity(),NewsActivity.class));
                break;
            case 2:
                fm.beginTransaction()
                        .replace(R.id.container, new VideosFragment())
                        .commit();
                //startActivity(new Intent(getActivity(),VideosActivity.class));
                break;
            case 3:
                /*startActivity(new Intent(getActivity(),SponsorsActivity.class));
                break;
            case 4:*/
                /*fm.beginTransaction()
                        .replace(R.id.container, new MapsFragment())
                        .commit();*/
                startActivity(new Intent(getActivity(),MapsActivity.class));
                break;
        }
    }


    public void setUp(int fragmentId,DrawerLayout drawerLayout, final Toolbar toolbar)
    {
        containerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar, R.string.drawer_open,R.string.drawer_close){
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer)
                {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                if(Build.VERSION_CODES.HONEYCOMB<=Build.VERSION.SDK_INT)
                    getActivity().invalidateOptionsMenu();
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(Build.VERSION_CODES.HONEYCOMB<=Build.VERSION.SDK_INT)
                    getActivity().invalidateOptionsMenu();
            }
        };

        if(!mUserLearnedDrawer && !mFromSavedInstanceState)
        {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static void saveToPreferences(Context context,String preferenceName,String preferenceValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context,String preferenceName,String defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }
}
