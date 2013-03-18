package com.clazz;

import java.util.HashMap;
import java.util.Map;

public class Entity {
	public static Map<String, String> newWorkspace(String name) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		return map;
	}
}
