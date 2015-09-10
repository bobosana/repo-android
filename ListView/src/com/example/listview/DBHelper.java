package com.example.listview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper  {
	
	//Define database objects details
	//db name
	private static final String DB_NAME="Ledger.db";
	
	//table name
	private static final String ORDER_TABLE_NAME="orders";
	
	//column names
	private static final String ORDER_COLUMN_ID="id";
	private static final String ORDER_COLUMN_COMMODITY="commodity";
	private static final String ORDER_COLUMN_PRICE="price";
	private static final String ORDER_COLUMN_CREATEDBY="createdby";
	private static final String ORDER_COLUMN_CREATEDDATE="createddate";
	
	
	public DBHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context,DB_NAME,null,1);
		Log.d("DBHelper", "Constructor end");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d("DBHelper", "onCreate start");
		db.execSQL("create table if not exists orders ( "
				+ ORDER_COLUMN_ID + " integer primary key,"
				+ ORDER_COLUMN_COMMODITY +" text, "
				+ ORDER_COLUMN_PRICE + " decimal,"
				+ ORDER_COLUMN_CREATEDBY+" text,"
				+ ORDER_COLUMN_CREATEDDATE + " text)");
		Log.d("DBHelper", "onCreate end");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d("DBHelper", "onUpgrade start");
		 db.execSQL("DROP TABLE IF EXISTS orders");
	      onCreate(db);
	      Log.d("DBHelper", "onUpgrade end");
	}
	
	public long insertRow(String iCommodity,double iPrice,String iCreatedby,String iCreatedDate){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues contentValues=new ContentValues();
		
		contentValues.put(ORDER_COLUMN_COMMODITY, iCommodity);
		contentValues.put(ORDER_COLUMN_PRICE, iPrice);
		contentValues.put(ORDER_COLUMN_CREATEDBY, iCreatedby);
		contentValues.put(ORDER_COLUMN_CREATEDDATE, iCreatedDate);
		
		try{
			db.insert(ORDER_TABLE_NAME,null,contentValues);
			Log.d("DBHelper", "Insert row try section");
		}
		catch(SQLException e) {
			
			Log.d("DBHelper", "Insert row Exception catch section");
			return -999;
		}
				
		Log.d("DBHelper", "Insert row About to return 0");
		return 0;
	}
	
	public ArrayList<Orders> getAllRows(){
		Log.d("DBHelper", "getAll section");
		ArrayList<Orders> objs=new ArrayList<Orders>();
		try{
			SQLiteDatabase db=this.getReadableDatabase();
			
		Cursor rows;
		rows=db.rawQuery("select * from orders order by id", null);
		rows.moveToFirst();
		
		
		while(rows.isAfterLast()==false){
		objs.add(new Orders(
				rows.getString(rows.getColumnIndex(ORDER_COLUMN_COMMODITY))
				,Double.parseDouble(rows.getString(rows.getColumnIndex(ORDER_COLUMN_PRICE)))
				,rows.getString(rows.getColumnIndex(ORDER_COLUMN_CREATEDBY))
				,rows.getString(rows.getColumnIndex(ORDER_COLUMN_CREATEDDATE))
				,Integer.parseInt(rows.getString(rows.getColumnIndex(ORDER_COLUMN_ID)))
						)
				);
		rows.moveToNext();
		}
		
		/*for(int i=0;i<5;i++){
		objs.add(new Orders("abcde test",100,"Niteen","2015-01-01 12:11:11"));
		}*/
	
		return objs;
		}
		catch(SQLException e){
			Log.d("DBHelper", "SQL Exeption here"+e.getMessage());
		}
		return objs;
		
	}
	
	
	public ArrayList<Orders> getToday(){
		Log.d("DBHelper", "getToday section");
		ArrayList<Orders> objs=new ArrayList<Orders>();
		try{
			SQLiteDatabase db=this.getReadableDatabase();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String today=sdf.format(new Date());
		Cursor rows;
		rows=db.rawQuery("select * from orders where strftime('%Y-%m-%d',createddate) ='"+today+"' order by id", null);
		rows.moveToFirst();
		
		
		while(rows.isAfterLast()==false){
		objs.add(new Orders(
				rows.getString(rows.getColumnIndex(ORDER_COLUMN_COMMODITY))
				,Double.parseDouble(rows.getString(rows.getColumnIndex(ORDER_COLUMN_PRICE)))
				,rows.getString(rows.getColumnIndex(ORDER_COLUMN_CREATEDBY))
				,rows.getString(rows.getColumnIndex(ORDER_COLUMN_CREATEDDATE))
			    ,Integer.parseInt(rows.getString(rows.getColumnIndex(ORDER_COLUMN_ID)))
				)
				);
		rows.moveToNext();
		}
		
		/*for(int i=0;i<5;i++){
		objs.add(new Orders("abcde test",100,"Niteen","2015-01-01 12:11:11"));
		}*/
	
		return objs;
		}
		catch(SQLException e){
			Log.d("DBHelper", "SQL Exeption here"+e.getMessage());
		}
		return objs;
	}
	
	public double getTotal(String filter){
		SQLiteDatabase db=this.getReadableDatabase();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String today=sdf.format(new Date());
		Cursor rows;
		String where;
		double total;
		if(filter.equalsIgnoreCase("ALL"))
		where="1=1";	
		else
		where="strftime('%Y-%m-%d',createddate) ='"+today+"'";
		
		String q="select sum(price) as price from orders where "+where+" ";
		Log.d("Query","query is "+q);
		rows=db.rawQuery(q, null);
		rows.moveToFirst();
		total=rows.getDouble(rows.getColumnIndex("price"));
		return total;
	}
 
	public long updateRow(ContentValues cv,int rowId){
	   SQLiteDatabase db=this.getWritableDatabase();
	   long flg;
	   flg=db.update(ORDER_TABLE_NAME, cv, ORDER_COLUMN_ID+"="+rowId  , null);
	   return flg;
	}
	
	public long deleteRow(int rowId){
		   SQLiteDatabase db=this.getWritableDatabase();
		   long flg;
		   flg=db.delete(ORDER_TABLE_NAME, ORDER_COLUMN_ID+"="+rowId  , null);
		   return flg;
		}
}
