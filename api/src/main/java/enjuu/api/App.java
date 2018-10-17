package enjuu.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.simple.parser.ParseException;


import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class App 
{
	
	public static Connection connection = null;
	
	/**
	 * Main void for starting the api
	 * 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	static Connection crunchifyConn = null;
	
    public static void main( String[] args ) throws FileNotFoundException, IOException, ParseException, ClassNotFoundException
    {
        Config.createConfig();
        Config.loadConfig();
        
        makeJDBCConnection();

        
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
				return JSON.createJSON(res, request);
			}
		});
    }
    
	private static void makeJDBCConnection() {
		 
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
 
		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.
			crunchifyConn = DriverManager.getConnection("jdbc:mysql://" + Config.getString("mysqlip") + ":" + Config.getString("mysqlport")+
					"/"+Config.getString("mysqldatabase"), Config.getString("mysqlusername"), Config.getString("mysqlpassword"));
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
 
	}
}
