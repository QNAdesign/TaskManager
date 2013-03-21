package com.task;

import java.util.Calendar;

import com.util.DB;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateProject extends Activity {
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;
	private Button setDate;
	private Button setTime;
	private TextView displayTime;
	private EditText projectName;
	private EditText projectNote;
	private Button createProjectBtn;
	private DB db;
	private String dTime;
	private int userID;
	private String selectName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_project);
		setDate = (Button) findViewById(R.id.setDate);
		setTime = (Button) findViewById(R.id.setTime);
		displayTime = (TextView)findViewById(R.id.displayTime);
		projectName = (EditText)findViewById(R.id.projectName);
		projectNote = (EditText)findViewById(R.id.projectNote);
		createProjectBtn = (Button)findViewById(R.id.createProjectBtn);
		
		Bundle bundle = this.getIntent().getExtras();
		userID = bundle.getInt("userID");
		selectName = bundle.getString("name");
		
		
		
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		setDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(CreateProject.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// TODO Auto-generated method stub
								mYear = year;
								mMonth = monthOfYear;
								mDay = dayOfMonth;
								StringBuilder sb = new StringBuilder().append(mYear).append("/").append(format(mMonth+1))
										.append("/").append(format(mDay)).append(" ").append(format(mHour)).append(":").append(format(mMinute));
								String dueTime = sb.toString();
								dTime = dueTime;
								displayTime.setText(dueTime);
							}
						}, mYear, mMonth, mDay).show();

			}
		});
		
		setTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimePickerDialog(CreateProject.this, new TimePickerDialog.OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						mHour = hourOfDay;
						mMinute = minute;
						StringBuilder sb = new StringBuilder().append(mYear).append("/").append(format(mMonth+1))
								.append("/").append(format(mDay)).append(" ").append(format(mHour)).append(":").append(format(mMinute));
						String dueTime = sb.toString();
						dTime = dueTime;
						displayTime.setText(dueTime);
					}
				}, mHour, mMinute,true).show();
			}
		});
		
		
		
		
		createProjectBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db = new DB(CreateProject.this);
				Cursor searchWorkspace = db.findWorkspaceByName(userID, selectName);
				int workspaceID = 0;
				if (searchWorkspace.moveToFirst()) {
					workspaceID = searchWorkspace.getInt(searchWorkspace
							.getColumnIndex("_workspaceID"));
				}
				String name = projectName.getText().toString();
				String note = projectNote.getText().toString();
				
				
				
				db.createProject(note, name, workspaceID, userID, dTime, "incomplete");
				Toast.makeText(CreateProject.this,
						"Create successful!",
						Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent();
				intent.setClass(CreateProject.this, WorkspaceContent.class);
				Bundle bundle = new Bundle();
				bundle.putInt("userID", userID);
				bundle.putString("name", selectName);
				intent.putExtras(bundle);
				startActivity(intent);
				CreateProject.this.finish();
			}
		});
		
		
		
		
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_project, menu);
		return true;
	}
	
	private String format(int x){
		String s = ""+x;
		if(s.length()==1) 
			s="0"+s;
		return s;
	}

}
