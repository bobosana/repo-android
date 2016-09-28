package com.dylanonfb.android.bandhupdates;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Profile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddBandh extends AppCompatActivity {
    Profile profile;
    EditText etOrganizer;
    EditText mtDetail;
    EditText etDate;
    EditText etDuration;
    SimpleDateFormat dateFormatter;
    Calendar newCalendar;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bandh);
        profile = Profile.getCurrentProfile();
        etOrganizer=(EditText) findViewById(R.id.etOrganizer);
        mtDetail=(EditText) findViewById(R.id.mtDescription);
        etDate=(EditText) findViewById(R.id.etDate);
        etDuration=(EditText) findViewById(R.id.etDuration);
        dateFormatter=new SimpleDateFormat("dd/MM/yyyy");
        newCalendar=Calendar.getInstance();
        add=(Button) findViewById(R.id.btAddBandh);

        if(profile==null){
            Toast.makeText(this,"You must login first",Toast.LENGTH_LONG).show();
            finish();
        }

        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DatePickerDialog dateDialog;
                DatePickerDialog.OnDateSetListener datePickerListener=new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        etDate.setText(dateFormatter.format(newDate.getTime()));
                    }

                };
                dateDialog = new DatePickerDialog(v.getContext(), datePickerListener,newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                if(hasFocus==true)
                    dateDialog.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                   /* db.insertRow(etOrganizer.getText().toString(),
                            mtDetail.getText().toString(),
                            etDate.getText().toString(),
                            etDuration.getText().toString());

                    Toast.makeText(v.getContext(),"Bandh info added",Toast.LENGTH_SHORT).show();*/
                    try {
                        Bundle formParams=new Bundle();
                        formParams.putString("ORGANIZER",etOrganizer.getText().toString());
                        formParams.putString("DETAIL",mtDetail.getText().toString());
                        formParams.putString("DATE",etDate.getText().toString());
                        formParams.putString("DURATION",etDuration.getText().toString());
                        formParams.putString("FB_PROFILE_ID",profile.getId());
                        PostTask postTask=new PostTask(v.getContext(),new URL("http://dylanonfb.890m.com/insert.php"),formParams);
                        postTask.execute();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    //startActivity(new Intent(v.getContext(),MainActivity.class));
                } catch(SQLException e){
                    Log.d("SQL INSERT",e.getMessage());
                }
            }
        });
    }

    class PostTask extends AsyncTask<Void, Void, Void> {
        Context context;
        Bundle formParams;
        URL url;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        BufferedInputStream bufferedInputStream;
        ProgressDialog progressDialog;

        public PostTask(Context context, URL url, Bundle formParams){
            this.context=context;
            this.url=url;
            this.formParams=formParams;
        }

        @Override
        protected Void doInBackground(Void... params) {
            //JSONObject postDataParams = new JSONObject();

            String queryString= null;
            try {
                queryString = "ORGANIZER="+ URLEncoder.encode(formParams.getString("ORGANIZER"),"UTF-8")
                        +"&DETAIL="+URLEncoder.encode(formParams.getString("DETAIL"),"UTF-8")
                        +"&DATE="+URLEncoder.encode(formParams.getString("DATE"),"UTF-8")
                        +"&DURATION="+URLEncoder.encode(formParams.getString("DURATION"),"UTF-8")
                        +"&FB_PROFILE_ID="+URLEncoder.encode(formParams.getString("FB_PROFILE_ID"),"UTF-8");
                //  queryString =URLEncoder.encode("ORAGANIZER=abd&DETAIL=WER&DATE=2015-01-01&DURATION=12HRS","UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpURLConnection.setFixedLengthStreamingMode(queryString.getBytes().length);
            try {
                PrintWriter post=new PrintWriter(httpURLConnection.getOutputStream());
                post.write(queryString);
                post.close();
                int responseCode=httpURLConnection.getResponseCode();
                if(responseCode!=httpURLConnection.HTTP_OK){
                    progressDialog.setMessage("ERROR");
                    progressDialog.dismiss();
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch(Exception e){
                e.printStackTrace();
            }

            finally {
                if(httpURLConnection!=null){
                    httpURLConnection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(this.context);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            } catch (IOException e) {
                e.printStackTrace();
                httpURLConnection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog!=null){
                progressDialog.dismiss();
                Toast.makeText(this.context,"data loaded",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this.context,MainActivity.class));
            }
        }
    }


}
