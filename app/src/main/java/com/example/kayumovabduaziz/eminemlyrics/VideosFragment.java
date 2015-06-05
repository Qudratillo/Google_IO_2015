package com.example.kayumovabduaziz.eminemlyrics;
//Ismatov Kudratillo
//03.05.2015
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


public class VideosFragment extends Fragment implements AdapterView.OnItemClickListener{
    String VIDEOS_URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=PLOU2XLYxmsIKLNUPiFCWVtcO7mZRZ9MmS&key=AIzaSyAaYuqrDz7hSwoQwLi3FVs1hlezLjkTEgg";//

    // "https://gdata.youtube.com/feeds/api/playlists/PLOU2XLYxmsIKLNUPiFCWVtcO7mZRZ9MmS?v=2&alt=jsonc";
    VideosAdapter videosAdapter;
    ProgressBar progressBar;
    int videosCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.activity_videos, null);

        final ActionBarActivity activity = (ActionBarActivity)getActivity();

        //activity.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        progressBar = (ProgressBar)main.findViewById(R.id.progressBar);
        activity.getSupportActionBar().setTitle("Videos");

        ListView videosListView = (ListView)main.findViewById((R.id.videosListView));
        videosAdapter = new VideosAdapter(activity, R.layout.videos_row_layout, new ArrayList<VideoContent>());
        videosListView.setAdapter(videosAdapter);

        videosListView.setOnItemClickListener(this);
        setVideosListFromUrl(VIDEOS_URL);
        return main;
    }

    private class SetVideosListTask extends AsyncTask<String, Void, VideoContent> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected synchronized VideoContent doInBackground(String... contents) {
            VideoContent temp = new VideoContent();
            temp.name = contents[0];
            temp.thumbnail = getBitMapFromUrl(contents[1]);
            temp.views = contents[2];
            temp.duration = contents[3];
            temp.url = contents[4];
            return temp;
        }

        protected void onPostExecute(VideoContent result) {
            videosAdapter.add(result);
            if (videosCount <= videosAdapter.getCount()) {
                getActivity().setProgressBarIndeterminateVisibility(false);
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    public void  setVideosListFromUrl(String url) {
        getActivity().setProgressBarIndeterminateVisibility(true);
        progressBar.setVisibility(View.VISIBLE);
        new AsyncTask<String, Void, String>() {
            @Override
            protected synchronized String doInBackground(String... params) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpUriRequest request = new HttpGet(params[0]);
                    HttpResponse response = client.execute(request);
                    return EntityUtils.toString(response.getEntity());
                } catch (Exception ex) {

                }
                return "";
            }

            @Override
            protected void onPostExecute(String aVoid) {
                try {
                    if(aVoid == ""){
                        getActivity().setProgressBarIndeterminateVisibility(false);
                        progressBar.setVisibility(View.GONE);
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                        builder.setTitle("No Internet Connection\nPlease connect to the internet and retry").create().show();
                        return;
                    }
                    JSONObject json = new JSONObject(aVoid);
                    //System.out.println(aVoid);
                    JSONArray jsonArray = json.getJSONArray("items");
                    videosCount = jsonArray.length();
                    //videosList = new ArrayList<VideoContent>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        VideoContent temp = new VideoContent();
                        String[] contents = new String[5];
                        contents[0] = jsonArray.getJSONObject(i).getJSONObject("snippet").getString("title");
                        contents[1] = jsonArray.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url");
                        contents[2] = "Google Developers";
                        contents[3] = jsonArray.getJSONObject(i).getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                        contents[4] = "https://www.youtube.com/watch?v=" + contents[3];
                        //contents[3] = parseSeconds(jsonArray.getJSONObject(i).getJSONObject("snippet").getInt("duration"));
                        //contents[4] = jsonArray.getJSONObject(i).getJSONObject("snippet").getJSONObject("player").getString("mobile");

                        new AsyncTask<String[], Void, String[]>() {
                            @Override
                            protected synchronized String[] doInBackground(String[]... params) {
                                try {
                                    HttpClient client = new DefaultHttpClient();
                                    HttpUriRequest request = new HttpGet("https://www.googleapis.com/youtube/v3/videos?part=contentDetails&id=" +
                                            params[0][3] + "&key=AIzaSyAaYuqrDz7hSwoQwLi3FVs1hlezLjkTEgg");
                                    HttpResponse response = client.execute(request);
                                    params[0][3] = EntityUtils.toString(response.getEntity());
                                    return params[0];
                                } catch (Exception ex) {
                                    //////ex.printStackTrace();
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(String[] aVoid) {
                                try {
                                    JSONObject json = new JSONObject(aVoid[3]);
                                    //System.out.println(aVoid[3]);
                                    JSONArray jsonArray = json.getJSONArray("items");
                                    aVoid[3] = parseSeconds(jsonArray.getJSONObject(0).getJSONObject("contentDetails").getString("duration"));
                                    //aVoid[4] = jsonArray.getJSONObject(0).getJSONObject("snippet").getJSONObject("player").getString("mobile");
                                } catch (Exception ex) {ex.printStackTrace();}
                                new SetVideosListTask().execute(aVoid);
                            }
                        }.execute(contents);
                    }

                } catch (Exception ex) {
                    progressBar.setVisibility(View.GONE);
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("Sorry, there is a problem with Youtube server\nTry in a minute").create().show();
            //        ex.printStackTrace();
                    //  a.thumbnail = getBitMapFromUrl("https://i.ytimg.com/vi/7V-fIGMDsmE/default.jpg");
                }
            }
        }.execute(url);

    }

  /*  public void  setVideosListFromUrl(String url) {
        getActivity().setProgressBarIndeterminateVisibility(true);
        progressBar.setVisibility(View.VISIBLE);
        new AsyncTask<String, Void, String>() {
            @Override
            protected synchronized String doInBackground(String... params) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpUriRequest request = new HttpGet(params[0]);
                    HttpResponse response = client.execute(request);
                    return EntityUtils.toString(response.getEntity());
                } catch (Exception ex) {

                }
                return "";
            }

            @Override
            protected void onPostExecute(String aVoid) {
                try {
                    if(aVoid == ""){
                        getActivity().setProgressBarIndeterminateVisibility(false);
                        progressBar.setVisibility(View.GONE);
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                        builder.setTitle("No Internet Connection\nPlease connect to the internet and retry").create().show();
                        return;
                    }
                    JSONObject json = new JSONObject(aVoid);
                    //System.out.println(aVoid);
                    JSONArray jsonArray = json.getJSONObject("data").getJSONArray("items");
                    videosCount = jsonArray.length();
                    //videosList = new ArrayList<VideoContent>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        VideoContent temp = new VideoContent();
                        String[] contents = new String[5];
                        contents[0] = jsonArray.getJSONObject(i).getJSONObject("video").getString("title");
                        contents[1] = jsonArray.getJSONObject(i).getJSONObject("video").getJSONObject("thumbnail").getString("sqDefault");
                        contents[2] = String.format("%,d views", jsonArray.getJSONObject(i).getJSONObject("video").getInt("viewCount"));
                        contents[3] = parseSeconds(jsonArray.getJSONObject(i).getJSONObject("video").getInt("duration"));
                        contents[4] = jsonArray.getJSONObject(i).getJSONObject("video").getJSONObject("player").getString("mobile");

                        new SetVideosListTask().execute(contents);
                    }

                } catch (Exception ex) {
                    progressBar.setVisibility(View.GONE);
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("Sorry, there is a problem with Youtube server\nTry in a minute").create().show();
                    ex.printStackTrace();
                    //  a.thumbnail = getBitMapFromUrl("https://i.ytimg.com/vi/7V-fIGMDsmE/default.jpg");
                }
            }
        }.execute(url);

    }
*/
    private String parseSeconds(String time){
        String result = "";
        time = time.substring(2);
        int h = time.indexOf("H");
        int m = time.indexOf("M");
        int s = time.indexOf("S");

        if(h!=-1)
            result+=String.format("%s:%s:%s", time.substring(0, h),time.substring(h+1, m),time.substring(m+1, s));
        else if(m != -1)
            result+=String.format("%s:%s", time.substring(0, m),time.substring(m+1, s));
        else
            result+=String.format("00:%s", time.substring(0, s));

        return result;
    }

    private String parseSeconds(int sec){
        String result = "";
        int hour = sec / 3600;
        int min = (sec%3600)/60;
        int seconds = sec % 60;
        if (hour != 0)
            result += String.format("%d:%02d:%02d", hour, min, seconds);
        else
            result += String.format("%d:%02d", min, seconds);

        return result;
    }

    Bitmap getBitMapFromUrl(String url){
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    class VideosAdapter extends ArrayAdapter<VideoContent>{
        public VideosAdapter(Context context, int resource, ArrayList<VideoContent> videoContents) {
            super(context, resource, R.id.videoName, videoContents);
        }

        class RowTag
        {
            ImageView image;
            TextView name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = super.getView(position, convertView, parent);

            VideoContent item = getItem(position);
            ImageView image = (ImageView) row.findViewById(R.id.videoImage);
            TextView title = (TextView) row.findViewById(R.id.videoName);
            TextView views = (TextView)row.findViewById(R.id.videoViews);
            TextView duration = (TextView)row.findViewById(R.id.videoDuration);

            //System.out.println(position);
            title.setText(item.name);
            views.setText(item.views);
            image.setImageBitmap(item.thumbnail);
            duration.setText(item.duration);

            return row;
        }
    }

    class VideoContent{
        public Bitmap thumbnail;
        public String name;
        public String views;
        public String duration;
        public String url;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Uri webpage = Uri.parse(videosAdapter.getItem(position).url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }
}
