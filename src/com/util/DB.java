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
		db.execSQL("create table t_userInfo (_userID integer primary key autoincrement, email varchar(50) unique not null, password varchar(50) not null)");
		db.execSQL("create table t_workspace (_workspaceID integer primary key autoincrement, userID integer, workspace_name varchar(10),FOREIGN KEY (userID) REFERENCES t_userInfo(_userID))");
		db.execSQL("create table t_sharedWorkspace (_shareID integer primary key autoincrement, workspaceID integer, userID integer, FOREIGN KEY (userID) REFERENCES t_userInfo(_userID), FOREIGN KEY (workspaceID) REFERENCES t_userInfo(_workspaceID))");
		db.execSQL("create table t_project (_projectID integer primary key autoincrement, note varchar(1000), project_name varchar(10), workspaceID integer, userID integer, duedate_time datetime,time_flag varchar(15),FOREIGN KEY(workspaceID) REFERENCES t_workspace(_workspaceID), FOREIGN KEY(userID) REFERENCES t_userInfo(_userID))");
		db.execSQL("create table t_setting (_userID integer primary key, autohide bit, theme integer, privacy char(10), FOREIGN KEY(userID) REFERENCES t_userInfo(_userID))");
		db.execSQL("create table t_sharedProject (_shareID integer primary key autoincrement, userID integer, projectID integer, FOREIGN KEY(userID) REFERENCES t_userInfo(_userID), FOREIGN KEY(projectID) REFERENCES t_project(_projectID))");
		db.execSQL("create table t_projectComment (_commentID integer primary key autoincrement, project_comment_text varchar(1000), projectID integer, FOREIGN KEY(projectID) REFERENCES t_project(_projectID))");
		db.execSQL("insert into t_userInfo (email,password) values('zyc19890921@163.com','123456')");
		db.execSQL("insert into t_workspace (userID,workspace_name) values(1,'First workspace')");
		db.execSQL("insert into t_workspace (userID,workspace_name) values(1,'Second workspace')");
		db.execSQL("insert into t_project (note,project_name,workspaceID,userID,duedate_time,time_flag) values ('this is a test','my first project',2,1,'2013-3-20 20:26','completed')");
		db.execSQL("insert into t_project (note,project_name,workspaceID,userID,duedate_time,time_flag) values ('this is a test again','my second project',2,1,'2013-3-20 20:26','incomplete')");
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
	
	
	public Cursor findUserByEmail(String email){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from t_userInfo where email ='"+ email +"'", null);
		return cursor;
	}
	
	
	public Cursor findSpaceByUserID(int userID){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from t_workspace where userID =" + userID, null);
		return cursor;
	}
	
	public void create(int userID,String name) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into t_workspace(userID,workspace_name) values('"
				+ userID + "','" + name + "')");
	}
	
	public void update(int userID, String oldName, String newName){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("update t_workspace set workspace_name = '" +newName+"' where userID = '"+userID+"' and workspace_name ='"+oldName+"'");
	}
	
	public void delete(int userID,String name){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from t_workspace where userID = '"+userID+"' and workspace_name ='" + name + "'");
	}
	
	public Cursor findWorkspaceByName(int userID,String name){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from t_workspace where userID = '"+userID+"' and workspace_name = '"+name+"'", null);
		return cursor;
	}
	
	public Cursor findProjects(int workspaceID){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from t_project where workspaceID =" + workspaceID, null);
		return cursor;
	}
	
	public void createProject(String note,String name,int workspaceID,int userID,String dueTime,String timeflag) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into t_project (note,project_name,workspaceID,userID,duedate_time,time_flag) values ('"
				+ note + "','" + name + "','" + workspaceID + "','" + userID + "','" + dueTime + "','" + timeflag +"')");
	}

}
