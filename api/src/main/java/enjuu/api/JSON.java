package enjuu.api;

import java.util.HashMap;
import java.util.Map;

import spark.Request;

public class JSON {
	
	public static String createJSON(HashMap<String, Object> map, Request req) {
		System.out.println("GET "+req.ip()+" "+req.url());
		String end = "{";
		int i = 0;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			i++;
		    if(i == map.size()) {
		    	end = end + "\n \""+entry.getKey()+"\": \""+entry.getValue()+"\"";
		    }else {
		    	end = end + "\n \""+entry.getKey()+"\": \""+entry.getValue()+"\",";
		    }
		    
		}
		end = end + "\n}";
		return end;
	}

}
