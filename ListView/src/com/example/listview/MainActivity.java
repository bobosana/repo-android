package com.example.listview;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	DBHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    
	    
		SharedPreferences sharedPref= getSharedPreferences(getString(R.string.shared_preference_key), 
				getApplicationContext().MODE_PRIVATE);
		
		final String user=sharedPref.getString("username", "-0213");
		
		Log.d("MainActivity","Shred pref value for username:"+ sharedPref.getString("username", "-0213"));
	    if(!sharedPref.contains("username"))
	    	startActivity(new Intent(this,UserSetActivity.class));
		
	    Button btSave= (Button) findViewById(R.id.btSave);
		db=new DBHelper(this);
		
		
		btSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
		
				EditText etCommodity=(EditText) findViewById(R.id.etCommodity);
				EditText etPrice=(EditText) findViewById(R.id.etPrice);
				
				String commodity=etCommodity.getText().toString();
				double price=0;
				
				try{
				price=Double.parseDouble(etPrice.getText().toString());
				
				
				String createdby=user;
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createddate= sdf.format(new Date());
				
				long flg=db.insertRow(commodity, price, createdby,createddate);
				etCommodity.setText(null);
				etPrice.setText(null);
				if(flg==0){
				Toast.makeText(v.getContext(), "Data saved", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(v.getContext(), "Something went wrong. Could not save the data", Toast.LENGTH_LONG).show();
				}
				
				}
				catch(NumberFormatException e){
					Toast.makeText(v.getContext(), "Invalid data for price", Toast.LENGTH_LONG).show();
				}
			}
		});
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		int id = item.getItemId();
		
		Intent intent=new Intent(this,ListOrdersActivity.class);
		switch(id){
		case R.id.action_all_orders:
			intent.putExtra("CALL_TYPE", "ALL");
			startActivity(intent);
			return true;
		case R.id.action_today_orders:
			intent.putExtra("CALL_TYPE", "TODAY");
			startActivity(intent);
			return true;
		case R.id.action_welcome:
			startActivity(new Intent(this,UserSetActivity.class));
			return true;
		default: 
			return super.onOptionsItemSelected(item);
		}
	}
}

class Orders{
	private String commodity;
	private double price;
	private String createdby;
	private String createddate;
	private int id;
	
	public Orders(String commodity, double price, String createdby,String createddate,int id) {

		this.commodity = commodity;
		this.price = price;
		this.createdby = createdby;
		this.createddate=createddate;
		this.id=id;
	}
	
	public String getCommodity() {
		return commodity;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getCreatedby() {
		return createdby;
	}
	
	public String getCreateddate() {
		return createddate;
	}
	
	public int getId() {
		return id;
	}
}


