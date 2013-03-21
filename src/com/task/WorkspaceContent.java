package com.task;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clazz.Entity;
import com.clazz.Workspace;
import com.util.DB;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WorkspaceContent extends Activity {

	private ListView list;
	private List<Map<String, Object>> contentList;
	private DB db;
	private Cursor cursor;
	private int userID;
	private String selectName;
	private Button addProject;
	private TextView workspaceName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workspace_content);
		list = (ListView) findViewById(R.id.workspace_content_lv);
		workspaceName = (TextView)findViewById(R.id.workspaceName);
		Bundle bundle = this.getIntent().getExtras();
		userID = bundle.getInt("userID");
		// email = bundle.getString("email");
		selectName = bundle.getString("name");
		workspaceName.setText(selectName);
		db = new DB(WorkspaceContent.this);

		contentList = loadContents();

		ListAdapter adapter = new SimpleAdapter(WorkspaceContent.this,
				contentList, R.layout.workspace_content_lv, new String[] {
						"name", "type", "complete" }, new int[] {
						R.id.workspace_content_lv_name,
						R.id.workspace_content_lv_type,
						R.id.workspace_content_lv_complete });

		list.setAdapter(adapter);
		
		addProject = (Button)findViewById(R.id.addproject);
		
		addProject.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(WorkspaceContent.this, CreateProject.class);
				Bundle bundle = new Bundle();
				bundle.putInt("userID", userID);
				bundle.putString("name", selectName);
				intent.putExtras(bundle);
				startActivity(intent);
				//WorkspaceContent.this.finish();
			}
		});

	}

	private List<Map<String, Object>> loadContents() {
		Cursor searchWorkspace = db.findWorkspaceByName(userID, selectName);
		int workspaceID = 0;
		List<Map<String, Object>> contentLists = new ArrayList<Map<String, Object>>();
		if (searchWorkspace.moveToFirst()) {
			workspaceID = searchWorkspace.getInt(searchWorkspace
					.getColumnIndex("_workspaceID"));
			Cursor searchProjects = db.findProjects(workspaceID);
			if (searchProjects.moveToFirst()) {
				for (int i = 0; i < searchProjects.getCount(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					
					 map.put("name",searchProjects.getString(searchProjects.getColumnIndex("project_name"))); 
					 map.put("type","project"); 
					 map.put("complete",searchProjects.getString(searchProjects.getColumnIndex("time_flag")));
					 
					/*map.put("name", "1234567");
					map.put("type", "project");
					map.put("complete", "(completed)");*/
					contentLists.add(map);
					searchProjects.moveToNext();
				}
			}
		}
		return contentLists;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workspace_content, menu);
		return true;
	}

}
