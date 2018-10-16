package enjuu.api;

import java.util.HashMap;
import java.util.Map;

public class JSON {
	
	public static String createJSON(HashMap<String, Object> map) {
		String end = "{";
		System.out.println(map.size());
		int i = 0;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			i++;
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
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
