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
import android.location.Address;

public class CreateWorkspace extends Activity{
	
	

	private static final int REQUEST_CHOOSE_ADDRESS = 0;
	private EditText name;
	private Button create;
	private DB db;
	private int userID;
	private String email;
	private Address address;
	private Button addLocationButton;
	private TextView addressText;
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		if (null == address) { //check if there is an address
			addLocationButton.setVisibility(View.VISIBLE);
			addressText.setVisibility(View.GONE);
		} else {
			addLocationButton.setVisibility(View.GONE);
			addressText.setVisibility(View.VISIBLE);
			addressText.setText(address.getAddressLine(0));
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (REQUEST_CHOOSE_ADDRESS == requestCode && RESULT_OK == resultCode) {
			address = data.getParcelableExtra(AddLocationMapActivity.ADDRESS_RESULT);
		}else{
		super.onActivityResult(requestCode, resultCode, data);
	  }
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_workspace);
		
		Bundle bundle = this.getIntent().getExtras();
		userID = bundle.getInt("userID");
		email = bundle.getString("email");
		
		name = (EditText)findViewById(R.id.workspaceName);
		create = (Button)findViewById(R.id.create);
		
		addLocationButton = (Button)findViewById(R.id.add_location_button);
		addressText = (TextView)findViewById(R.id.address_text);
		
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
		
		public void addLocationButtonClicked(View v){
		Intent intent = new Intent(this, AddLocationMapActivity.class);
		startActivityForResult(intent, REQUEST_CHOOSE_ADDRESS);
	}
	
}


