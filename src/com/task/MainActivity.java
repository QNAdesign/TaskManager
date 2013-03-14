package com.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.DB;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText email;
	private EditText pwd;
	private CheckBox cb;
	private Button login;
	private Button register;
	private DB db;
	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		email = (EditText) findViewById(R.id.myemail);
		pwd = (EditText) findViewById(R.id.mypassword);
		cb = (CheckBox) findViewById(R.id.mCheck);
		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);
		// email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		cb.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (cb.isChecked()) {
					pwd.setTransformationMethod(HideReturnsTransformationMethod
							.getInstance());
				} else {
					pwd.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
				}
			}
		});

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String emailIn = email.getText().toString();
				boolean check = emailValidator(emailIn);
				String passwordIn = pwd.getText().toString();
				db = new DB(MainActivity.this);
				cursor = db.login(emailIn,passwordIn);
				boolean loginCheck = cursor.getCount()==1;
				if (check == false) {
					Toast.makeText(MainActivity.this,
							"Please input the right email address!",
							Toast.LENGTH_LONG).show();
				} else if(loginCheck==false){
					Toast.makeText(MainActivity.this,
							"Wrong email/password!",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(MainActivity.this,
							"Login successful!",
							Toast.LENGTH_LONG).show();
				}

			}

		});
		
		register.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
			
		});
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0,1,1,"Exit");
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		int itemId = item.getItemId();
		if (itemId == 1) {
			MainActivity.this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean emailValidator(String email) {
		Pattern pattern;
		Matcher matcher;
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
