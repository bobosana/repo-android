package com.example.listview;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OrdersListAdapter extends ArrayAdapter {
	ArrayList<Orders> ordersList;
	Context c;
	public OrdersListAdapter(Context context, int resource, ArrayList<Orders> objects) {
	super(context,resource,objects);
		// TODO Auto-generated constructor stub
		this.ordersList=objects;
		this.c=context;
		Log.d("Custom Orders Adapter", "Constructed");
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Log.d("Test", "getView Called");
		// TODO Auto-generated method stub
		View vw;

		LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vw = inflater.inflate(R.layout.item_orders, parent, false);
		
		Orders temp=ordersList.get(position);
		
		TextView tvCommodity=(TextView) vw.findViewById(R.id.rowTvCommodity);
		TextView tvPrice=(TextView) vw.findViewById(R.id.rowTvPrice);
		TextView tvCreatedBy=(TextView) vw.findViewById(R.id.rowTvCreatedBy);
		TextView tvCreatedDate=(TextView) vw.findViewById(R.id.rowTvDate);
		TextView tvId=(TextView) vw.findViewById(R.id.rowTvId);
		
		tvCommodity.setText(temp.getCommodity());
		tvPrice.setText(Double.toString(temp.getPrice()));
		tvCreatedBy.setText("By "+temp.getCreatedby());
		tvCreatedDate.setText(temp.getCreateddate());
		tvId.setText(Integer.toString(temp.getId()));
		
		Log.d("Test", "Custom Class last line before run");
		
		return vw;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ordersList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return ordersList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
		
}
