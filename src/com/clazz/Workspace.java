package com.clazz;

import java.util.HashMap;
import java.util.Map;

public class Workspace {

	private String name;

	public Workspace() {
	}

	public Workspace(String name) {
		this.name = name;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		return map;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
