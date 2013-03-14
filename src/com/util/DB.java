package com.util;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper{

	public DB(Context ctx) {
		super(ctx, "db_task", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table t_userInfo (_id integer primary key autoincrement, email varchar(50) unique not null, password varchar(50) not null)");
		db.execSQL("insert into t_userInfo (email,password) values('zyc19890921@163.com','123456')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public Cursor login(String email,String password){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from t_userInfo where email='"+email+"' and password='"+password+"'", null);
		return cursor;
	}
	
	public Cursor findInfoByEmail(String email){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from t_userInfo where email='"+email+"'", null);
		return cursor;
	}
	
	public void register(String email,String password) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into t_userInfo(email,password) values('"
				+ email + "','" + password + "')");
	}

}
