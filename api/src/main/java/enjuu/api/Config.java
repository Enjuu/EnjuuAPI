package enjuu.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config {
	
	public static Object config = null;
	public static JSONObject objconfig = null;
	
	public static void loadConfig() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
    	Object obj = null;
		obj = parser.parse(new FileReader("config.json"));
		config = obj;
        JSONObject jsonObject = (JSONObject) obj;
        objconfig = jsonObject;
        System.out.println(u.info +"Successfully loaded Config.json");
	}
	
	@SuppressWarnings({ "unchecked" })
	public static void createConfig()  {
		File f = new File("config.json");
		if(f.exists()) {
			System.out.println(u.info +"Found Config! No one must be created");
		}else{
			System.out.println(u.error +"No Config Found! Create one...");
			JSONObject obj = new JSONObject();
			obj.put("sparkport", "80");
			obj.put("mysqlip", "localhost");
			obj.put("mysqlport", "3306");
			obj.put("mysqldatabase", "ripple");
			obj.put("mysqlusername", "root");
			obj.put("mysqlpassword", "");
			try (FileWriter file = new FileWriter("config.json")) {
				file.write(obj.toJSONString());
				System.out.println(u.info +"Successfully created Config...");
				
			} catch (IOException e) {
				System.err.println(u.error +"Can't create Config...");
				e.printStackTrace();
			}
			System.exit(0);
		}
		
    
	}
	
	public static String getString(String string) {
			String result = (String) objconfig.get(string);
			return result;
	}
	
	public static Boolean getBool(String string) {
			Boolean result = (Boolean) objconfig.get(string);
			return result;
	}
	
	public static long getLong(String string) {
			long result = (Long) objconfig.get(string);
			return result;
	}

}