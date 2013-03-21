package com.clazz;

import java.util.List;
import java.util.Map;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.app.Activity; 
import android.content.Context;   
import android.util.Log;   
import android.view.LayoutInflater;   
import android.view.View;   
import android.view.ViewGroup;   
import android.widget.BaseAdapter;   
import android.widget.Button;   
import android.widget.CheckBox;   
import android.widget.CompoundButton;   
import android.widget.ListView;   
import android.widget.TextView;


public class contentAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> listItems;
	private LayoutInflater listContainer;

	public final class ListItemView { // 自定义控件集合
		public TextView name;
		public TextView type;
		public TextView complete;
	}
	
	public contentAdapter(Context context, List<Map<String, Object>> listItems){
		this.context = context;
		this.listItems = listItems;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final int selectID = position;
		ListItemView listItemView = null;
		if(convertView == null){
			listItemView = new ListItemView();
			//convertView = listContainer.inflate(R.layout.workspace_content_lv, null)
		}
		return null;
	}

}
