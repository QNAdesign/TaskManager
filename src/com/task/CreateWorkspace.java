package com.task;

import java.util.List;
import java.util.Random;

import com.util.DB;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View.OnClickListener;


public class CreateWorkspace extends Activity{
	
	private EditText name;
	private Button create;
	private DB db;
	private int userID;
	private String email;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_workspace);
		
		Bundle bundle = this.getIntent().getExtras();
		userID = bundle.getInt("userID");
		email = bundle.getString("email");
		
		name = (EditText)findViewById(R.id.workspaceName);
		create = (Button)findViewById(R.id.create);
		
		create.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String workspaceName = name.getText().toString();
				db = new DB(CreateWorkspace.this);
				db.create(userID, workspaceName);
				
				Toast.makeText(CreateWorkspace.this,
						"Create successful!",
						Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent();
				intent.setClass(CreateWorkspace.this, Mainpage.class);
				Bundle bundle = new Bundle();
				bundle.putString("email",email);
				intent.putExtras(bundle);
				startActivity(intent);
				CreateWorkspace.this.finish();
			}
			
		});
		
	}
	
}


