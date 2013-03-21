package com.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.clazz.Entity;
import com.util.DB;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View.OnClickListener;

public class Mainpage extends Activity {

	private Button add;
	private ListView lv;
	private DB db;
	private Cursor cursor;
	private String email;
	private List<Map<String, String>> workspaces;
	private int userID;
	private String selectName;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		lv = (ListView) findViewById(R.id.list);
		add = (Button) findViewById(R.id.add);

		Bundle bundle = this.getIntent().getExtras();
		email = bundle.getString("email");

		db = new DB(Mainpage.this);
		cursor = db.findUserByEmail(email);

		/*
		 * if(cursor.moveToFirst()){ Toast.makeText(Mainpage.this, "YES!",
		 * Toast.LENGTH_LONG).show(); }else { Toast.makeText(Mainpage.this,
		 * "NO!", Toast.LENGTH_LONG).show(); }
		 */
		
		if (cursor.moveToFirst()) {
			userID = cursor.getInt(cursor.getColumnIndex("_id"));
		}

		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (cursor.moveToFirst()) {
					userID = cursor.getInt(cursor.getColumnIndex("_id"));
				}
				Intent intent = new Intent();
				intent.setClass(Mainpage.this, CreateWorkspace.class);
				Bundle bundle = new Bundle();
				bundle.putInt("userID", userID);
				bundle.putString("email", email);
				intent.putExtras(bundle);
				startActivity(intent);
				cursor.close();
				// Mainpage.this.finish();

			}
		});
		// int userID = cursor.getInt(0);
		// int userID = cursor.getInt(cursor.getColumnIndex("_id"));
		Cursor searchWorkspace = db.findSpaceByUserID(userID);

		String[] workspaceList = new String[searchWorkspace.getCount()];
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		if (searchWorkspace.moveToFirst()) {
			for (int i = 0; i < searchWorkspace.getCount(); i++) {
				workspaceList[i] = searchWorkspace.getString(searchWorkspace
						.getColumnIndex("workspace_name"));

				Map workspace = Entity.newWorkspace(workspaceList[i]);

				// HashMap<String,String> map = new HashMap<String, String>();
				// map.put("name",searchWorkspace.getString(searchWorkspace.getColumnIndex("workspace_name")));

				/*
				 * Toast.makeText(Mainpage.this, map.get("name"),
				 * Toast.LENGTH_LONG).show();
				 */
				list.add(workspace);
				searchWorkspace.moveToNext();
			}
		}

		ListAdapter adapter = new ArrayAdapter<String>(Mainpage.this,
				android.R.layout.simple_list_item_1, workspaceList);
		lv.setAdapter(adapter);

		workspaces = list;
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				selectName = workspaces.get(position).get("name");
				String[] operate = { "Edit", "Delete" };

				new AlertDialog.Builder(Mainpage.this)
						.setTitle("Operate " + selectName)
						.setItems(operate,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										if (which == 0) {
											
											Intent intent = new Intent(Mainpage.this, Editpage.class);
											Bundle bundle = new Bundle();
											bundle.putInt("userID", userID);
											bundle.putString("email", email);
											bundle.putString("name", selectName);
											intent.putExtras(bundle);
											
											startActivity(intent);
											Mainpage.this.finish();
											
										} else {
											new AlertDialog.Builder(Mainpage.this).setTitle("Confirm Delete? ")
											.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
												
												
												public void onClick(DialogInterface dialog, int which) {
													
													db.delete(userID, selectName);
													Intent refresh = new Intent(Mainpage.this, Mainpage.class);
													
													Bundle bundle = new Bundle();
													bundle.putString("email",email);
													refresh.putExtras(bundle);
													
													startActivity(refresh);
													Mainpage.this.finish();
													Toast.makeText(Mainpage.this, "Deleted!",
															 Toast.LENGTH_LONG).show();
													
													
												}
											}).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
												
												@Override
												public void onClick(DialogInterface dialog, int which) {
													// TODO Auto-generated method stub
													
												}
											}).show();
										}

									}

									
								}).show();

				return false;
			}

		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				selectName = workspaces.get(position).get("name");
				if (position == 1){
					Intent intent = new Intent(Mainpage.this, WorkspaceContent.class);
					Bundle bundle = new Bundle();
					bundle.putInt("userID", userID);
					bundle.putString("email", email);
					bundle.putString("name", selectName);
					intent.putExtras(bundle);
					
					startActivity(intent);
				}
				
				
			}
		});

	}

}
