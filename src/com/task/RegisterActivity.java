package com.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.DB;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity{

	private EditText email;
	private EditText password;
	private EditText confirmPwd;
	private Button register;
	private DB db;
	private Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		email = (EditText)findViewById(R.id.email);
		password = (EditText)findViewById(R.id.password);
		confirmPwd = (EditText)findViewById(R.id.confirmPwd);
		register = (Button)findViewById(R.id.register);
		
		register.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String emailIn = email.getText().toString();
				boolean checkEmail = emailValidator(emailIn);
				String pwdIn = password.getText().toString();
				String confirmPwdIn = confirmPwd.getText().toString();
				boolean checkPwd = pwdIn.equals(confirmPwdIn);
				db = new DB(RegisterActivity.this);
				
				
				if(checkEmail==false){
					Toast.makeText(RegisterActivity.this, "Wrong email address!",
							Toast.LENGTH_LONG).show();
				} else if(checkPwd==false){
					Toast.makeText(RegisterActivity.this, "Please input the same password!",
							Toast.LENGTH_LONG).show();
				} else{
					cursor = db.findInfoByEmail(emailIn);
					boolean checkExist = cursor.getCount()==1;
					if(checkExist == true){
						Toast.makeText(RegisterActivity.this, "Email has been used!",
								Toast.LENGTH_LONG).show();
					}else{
						db.register(emailIn, pwdIn);
						Toast.makeText(RegisterActivity.this, "Register Successful!",
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent();
						intent.setClass(RegisterActivity.this, MainActivity.class);
						startActivity(intent);
						RegisterActivity.this.finish();
					}
							
					
				}
				
			}
			
		});
		
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
