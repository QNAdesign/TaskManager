package com.task;

import com.util.DB;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Editpage extends Activity {

	private EditText name;
	private Button confirm;
	private Button cancel;
	private DB db;
	private int userID;
	private String email;
	private String selectName;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editpage);
		
		Bundle bundle = this.getIntent().getExtras();
		userID = bundle.getInt("userID");
		email = bundle.getString("email");
		selectName = bundle.getString("name");
		
		name = (EditText)findViewById(R.id.workspaceName);
		confirm = (Button)findViewById(R.id.confirm);
		cancel = (Button)findViewById(R.id.cancel);
		
		name.setText(selectName);
		
		
		confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String workspaceName = name.getText().toString();
				db = new DB(Editpage.this);
				db.update(userID,selectName,workspaceName);
				
				Toast.makeText(Editpage.this,
						"Edit successful!",
						Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent();
				intent.setClass(Editpage.this, Mainpage.class);
				Bundle bundle = new Bundle();
				bundle.putString("email",email);
				
				intent.putExtras(bundle);
				startActivity(intent);
				Editpage.this.finish();
			}
			
		});
		
		cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Editpage.this, Mainpage.class);
				Bundle bundle = new Bundle();
				bundle.putString("email",email);
				
				intent.putExtras(bundle);
				startActivity(intent);
				Editpage.this.finish();
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editpage, menu);
		return true;
	}

}
