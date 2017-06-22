package com.afkl.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
	
	public static Properties getAllProperties(String path) {
		InputStream is = Utils.class.getClassLoader().getResourceAsStream(
				path);
		Properties config = new Properties();
		try {
			config.load(is);
		} catch (IOException e) {
			System.out.println("Could not load properties from " + path);
			e.printStackTrace();
			return null;
		}
		return config;
	}

}
