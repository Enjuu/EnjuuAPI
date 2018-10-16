package enjuu.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import org.json.simple.parser.ParseException;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Hello world!
 *
 */
public class App 
{
	/**
	 * Main void for starting the api
	 * 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
    public static void main( String[] args ) throws FileNotFoundException, IOException, ParseException
    {
        Config.createConfig();
        Config.loadConfig();
        
        Spark.setPort(Integer.parseInt(Config.getString("sparkport")));
        
        /**
         * Home Screen for API
         */
        Spark.get(new Route("/") {
			
			@Override
			public Object handle(Request request, Response response) {
				return "The Enjuu API is working";
			}
		});
        
        /**
         * Ping if the API is working
         */
        Spark.get(new Route("/ping") {
			
			@Override
			public Object handle(Request request, Response response) {
				HashMap<String , Object> res = new HashMap<String, Object>();
				res.put("result", "The API is working");
				res.put("boolean", 1);
				return JSON.createJSON(res);
			}
		});
    }
}
