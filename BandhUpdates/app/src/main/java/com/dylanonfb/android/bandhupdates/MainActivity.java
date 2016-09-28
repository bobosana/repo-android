package com.dylanonfb.android.bandhupdates;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    boolean taskRunning=false;
    SwipeRefreshLayout swipeRefreshLayout;
    CallbackManager callbackManager;
    ProfileTracker profileTracker;
    ProfileTracker  mProfileTracker;
    AccessTokenTracker accessTokenTracker;
    Profile profile;
    AccessToken accessToken;
    FetchListTask fetchListTask;
    LoginButton userLogin;
    ProfilePictureView userPicture;
    Dialog dialog;
    RecyclerView recyclerView;
    CustomRecyclerAdapter recyclerAdapter;
    VerticalSpaceItemDecoration itemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager=CallbackManager.Factory.create();


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        itemDecoration = new VerticalSpaceItemDecoration(5);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        userPicture=(ProfilePictureView) findViewById(R.id.ppvUser);
        recyclerAdapter=new CustomRecyclerAdapter(null);
        recyclerView=(RecyclerView) findViewById(R.id.rvList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(recyclerAdapter);

        setSupportActionBar(toolbar);
        profileTracker=new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                profile=currentProfile;
                if(profile==null)
                    userPicture.setProfileId(null);
                else
                userPicture.setProfileId(profile.getId());

            }
        };
        profileTracker.startTracking();
        profile=Profile.getCurrentProfile();
        accessTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
            accessToken=currentAccessToken;
            }
        };
        accessTokenTracker.startTracking();
        accessToken=AccessToken.getCurrentAccessToken();

        dialog = new Dialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog);
        dialog.setTitle("Login");
        dialog.setContentView(R.layout.fb_login_layout);
        dialog.setCancelable(false);
        LoginButton loginButton = (LoginButton) dialog.findViewById(R.id.btnFbLogin);
        fetchListTask=new FetchListTask(getApplicationContext());

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken=loginResult.getAccessToken();
                fetchRefresh(taskRunning);
                dialog.dismiss();
            }

            @Override
            public void onCancel() {
                if(dialog.isShowing()!=true)
                    dialog.show();
            }

            @Override
            public void onError(FacebookException error) {
                dialog.dismiss();
            }
        });
        userLogin=loginButton;

        if(accessToken==null){
        dialog.show();
        }
        else{
            userPicture.setProfileId(profile.getId());
            fetchRefresh(taskRunning);
        }
       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(accessToken==null)
                {
                dialog.show();
            }
                else{
                    startActivity(new Intent(view.getContext(),AddBandh.class));
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
               fetchRefresh(taskRunning);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            if(swipeRefreshLayout.isRefreshing()==false)
                swipeRefreshLayout.setRefreshing(true);
            fetchRefresh(taskRunning);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchRefresh(taskRunning);
    }

    class FetchListTask extends AsyncTask<Void,String,JSONArray> {

        HttpURLConnection connect;
        Context context;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;

        ListView lvList;


        Calendar calendar;
        SimpleDateFormat sdf;
        SimpleDateFormat sdft;
        BandhList bandhList;
        ArrayList<BandhList> data;


        Date tempDate;

        BandhListAdapter adapter;
        CustomRecyclerAdapter recyclerAdapter;
        RecyclerView.LayoutManager layoutManager;
        FetchListTask(Context context){
            this.context=context;
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            try {
                URL url=new URL("http://dylanonfb.890m.com");
                connect= (HttpURLConnection) url.openConnection();
                inputStream=connect.getInputStream();
                inputStreamReader=new InputStreamReader(inputStream);
                bufferedReader=new BufferedReader(inputStreamReader);
                stringBuilder=new StringBuilder();
                String line;
                String online;

                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                    ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    boolean isOnline = (networkInfo != null && networkInfo.isConnected());
                    if(!isOnline){
                        online="no";
                    }else{
                        online="yes";
                    }

                    publishProgress(online);
                }

                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                connect.disconnect();

                final JSONArray jsonArray=new JSONArray(stringBuilder.toString());
                JSONArray a=new JSONArray();
                for(int i=0;i<jsonArray.length();i++) {
                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
                    final String fbId=jsonObject.getString("FB_PROFILE_ID");
                    new GraphRequest(AccessToken.getCurrentAccessToken()
                            ,
                            "/"+fbId,
                            null,
                            HttpMethod.GET,
                            new GraphRequest.Callback() {
                                public void onCompleted(GraphResponse response) {
                                    JSONObject jsonName = response.getJSONObject();
                                    try {
                                        jsonObject.put("name", jsonName.getString("name"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    ).executeAndWait();
                   // jsonObject.put("name", "test");
                   // publishProgress(jsonObject);
                    a.put(jsonObject);
                }

                return a;



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(values[0]=="no"){
                Toast.makeText(context, "No network access", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                onPostExecute(null);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            taskRunning=true;
            //lvList=(ListView) findViewById(R.id.lvList);
            recyclerView=(RecyclerView) findViewById(R.id.rvList);
            calendar=Calendar.getInstance();
            sdf=new SimpleDateFormat("yyyy-MM-dd");
            sdft=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            data=new ArrayList<BandhList>();
        }

        @Override
        protected void onPostExecute(JSONArray aVoid) {
            data=new ArrayList<BandhList>();
            if(aVoid!=null) {
                try {
                    for (int i = 0; i < aVoid.length(); i++) {
                        JSONObject v = aVoid.getJSONObject(i);
                        calendar.setTime(sdf.parse(v.getString("DATE")));
                        tempDate = calendar.getTime();
                        bandhList = new BandhList();
                        bandhList.setOrganizer(v.getString("ORGANIZER"));
                        bandhList.setDate(tempDate);
                        bandhList.setDetail(v.getString("DETAIL"));
                        bandhList.setDuration(v.getString("DURATION"));
                        bandhList.setProfileId(v.getString("FB_PROFILE_ID"));
                        calendar.setTime(sdft.parse(v.getString("UPDATE_TIME")));
                        tempDate = calendar.getTime();
                        bandhList.setProfileName(v.getString("name"));
                        bandhList.setUpdateTime(tempDate);
                        data.add(bandhList);
                    }
                 //   adapter = new BandhListAdapter(context, R.layout.list_item, data);
                   recyclerAdapter=new CustomRecyclerAdapter(data);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorDivider));
                    recyclerAdapter.notifyDataSetChanged();
                //    lvList.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            taskRunning=false;
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    void fetchRefresh(boolean taskRunning){
        if(accessToken==null){
            dialog.show();
            return;
        }
        if(taskRunning==false){
            swipeRefreshLayout.setRefreshing(true);
            FetchListTask t=new FetchListTask(MainActivity.this);
            t.execute();
        }
    }
}
class BandhList {
    private String organizer;
    private Date date;
    private String detail;
    private String duration;
    private String profileId;
    private String profileName;
    private Date updateTime;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public BandhList() {

    }

    public BandhList(String org, String det, Date dt) {
        this.organizer = org;
        this.detail = det;
        this.date = dt;
    }

    public BandhList(String org, String det, Date dt, String dur) {
        this.organizer = org;
        this.detail = det;
        this.date = dt;
        this.duration = dur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getDuration() {
        return duration;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int mVerticalSpaceHeight;

    public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
        this.mVerticalSpaceHeight = mVerticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = mVerticalSpaceHeight;
    }
}
