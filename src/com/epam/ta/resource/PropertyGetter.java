package com.epam.ta.resource;

import java.util.ResourceBundle;

public final class PropertyGetter {
	private static final ResourceBundle bundle = ResourceBundle
			.getBundle("appRes.properties");

	private PropertyGetter() {
	}
	
	public static String getProperty(String key) {
		return bundle.getString(key);
	}
}
