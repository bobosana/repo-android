package com.example.listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class ListOrdersActivity extends ActionBarActivity {
		DBHelper db;
		Bundle extras;
	@Override
		protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_orders);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		db=new DBHelper(this);
			
		ListView lvList=(ListView) findViewById(R.id.lvListView);
		TextView tvTotal=(TextView) findViewById(R.id.tvTotal);
		extras= getIntent().getExtras();
		Log.d("Extra Value","This is extra:"+extras.getString("CALL_TYPE"));
		
		final OrdersListAdapter adapter;
		double total=0;
		
		if( extras.getString("CALL_TYPE").equalsIgnoreCase("ALL")){
			adapter=new OrdersListAdapter(this,R.layout.item_orders,db.getAllRows());
			total=db.getTotal("ALL");
		}
		else{
			adapter=new OrdersListAdapter(this,R.layout.item_orders,db.getToday());
			total=db.getTotal("TODAY");
		}
		tvTotal.setText("Total:Rs. "+Double.toString(total));
		lvList.setAdapter(adapter);
		
		lvList.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
		    	TextView vCommodity=(TextView) adapter.getView(position, null, null).findViewById(R.id.rowTvCommodity);
		    	TextView vPrice=(TextView) adapter.getView(position, null, null).findViewById(R.id.rowTvPrice);
		    	TextView vCreatedBy=(TextView) adapter.getView(position, null, null).findViewById(R.id.rowTvCreatedBy);
		    	TextView vCreatedDate=(TextView) adapter.getView(position, null, null).findViewById(R.id.rowTvDate);
		    	TextView vId=(TextView) adapter.getView(position, null, null).findViewById(R.id.rowTvId);
		    	
		    	Intent intent=new Intent(parent.getContext(),ModifyActivity.class);
		    	intent.putExtra("commodity", vCommodity.getText().toString());
		    	intent.putExtra("price", vPrice.getText().toString());
		    	intent.putExtra("createddate", vCreatedDate.getText().toString());
		    	intent.putExtra("createdby", vCreatedBy.getText().toString());
		    	intent.putExtra("id", vId.getText().toString());
		    	intent.putExtra("CALL_TYPE",extras.getString("CALL_TYPE") );
		    	startActivity(intent);
		    	finish();
		    	//Toast.makeText(view.getContext(), "Commodity:"+v.getText().toString(), Toast.LENGTH_SHORT).show();
		    }

		});
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == android.R.id.home){
			 NavUtils.navigateUpFromSameTask(this);
			 return true;

		}
		return super.onOptionsItemSelected(item);
	}
	
}
