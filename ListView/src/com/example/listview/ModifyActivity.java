package com.example.listview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyActivity extends ActionBarActivity {
	
	DBHelper db;
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_order);
		
		final Bundle extras=getIntent().getExtras();
		
		String vCommodity=extras.getString("commodity");
		String vPrice=extras.getString("price");
		String vCreatedBy=extras.getString("createdby");
		String vCreatedDate=extras.getString("createddate");
		String vId=extras.getString("id");
		
		
		TextView tvCreatedByAndDate=(TextView) findViewById(R.id.tvCreatedByAndDate);
		EditText etCommodity=(EditText) findViewById(R.id.etEditCommodity);
		EditText etPrice=(EditText) findViewById(R.id.etEditPrice);
		TextView tvId=(TextView) findViewById(R.id.tvId);
		
		Button bUpdate=(Button) findViewById(R.id.bUpdateOrder);
		Button bDelete=(Button) findViewById(R.id.bDelete);
		Button bCancel=(Button) findViewById(R.id.bCancel);
		
		
		tvCreatedByAndDate.setText(vCreatedBy+" on "+vCreatedDate);
		etCommodity.setText(vCommodity);
		etPrice.setText(vPrice);
		tvId.setText(vId);
		
		
		bUpdate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText etCommodity=(EditText) findViewById(R.id.etEditCommodity);
				EditText etPrice=(EditText) findViewById(R.id.etEditPrice);
				TextView tvId=(TextView) findViewById(R.id.tvId);
				
				ContentValues cv= new ContentValues();
				cv.put("commodity", etCommodity.getText().toString());
				cv.put("price", Double.parseDouble(etPrice.getText().toString()));
			db = new DBHelper(v.getContext());
			Log.d("ModifyActivity","DBHelper called");
			long flg=db.updateRow(cv, Integer.parseInt(tvId.getText().toString()));	
			Toast toast = Toast.makeText(v.getContext(),"Data Updated", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL,0, 0);
			toast.show();
			Intent intent=new Intent(v.getContext(),ListOrdersActivity.class);
			intent.putExtra("CALL_TYPE", extras.getString("CALL_TYPE"));
			startActivity(intent);
			}
		});	
		
		
	bDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				TextView tvId=(TextView) findViewById(R.id.tvId);

			db = new DBHelper(v.getContext());
			Log.d("ModifyActivity","DBHelper called");
			long flg=db.deleteRow(Integer.parseInt(tvId.getText().toString()));	
			Toast toast = Toast.makeText(v.getContext(),"Data deleted", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL,0, 0);
			toast.show();
			Intent intent=new Intent(v.getContext(),ListOrdersActivity.class);
			intent.putExtra("CALL_TYPE", extras.getString("CALL_TYPE"));
			startActivity(intent);
			}
		});	
	
	
	
	bCancel.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		Toast toast = Toast.makeText(v.getContext(),"Cancelled", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL,0, 0);
		toast.show();
		Intent intent=new Intent(v.getContext(),ListOrdersActivity.class);
		intent.putExtra("CALL_TYPE", extras.getString("CALL_TYPE"));
		startActivity(intent);
		}
	});	
		
		
	}	
}
