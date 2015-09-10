package com.example.listview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserSetActivity extends ActionBarActivity {

	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("UserSetActivity","Start of onCreate");
		setContentView(R.layout.activity_set_user);
		
		
		final EditText etUser=(EditText) findViewById(R.id.etSetUser);
		Button etSave=(Button) findViewById(R.id.bSetUser);
		
		etSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
				getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
				SharedPreferences.Editor ed= sharedPref.edit();
				ed.putString("username",etUser.getText().toString());
				ed.commit();
				
				Intent intent=new Intent(v.getContext(),MainActivity.class);
				startActivity(intent);
			}
		});
	}
}
