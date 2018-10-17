package enjuu.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	 * Main void for starting the API
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
         * returns:
         * @return result as string
         * @return boolean as int
         * 
         * error:
         * @return null
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
        
        // routes:
        // /achievements?name=
        // /achievements?id=
        
        /**
         * returns:
         * @return id as string
         * @return name as string
         * @return description as string
         * @return icon as string
         * @return version as string
         * 
         * error:
         * @return result as string
         */
        Spark.get(new Route("/achievements") {
			
			@Override
			public Object handle(Request request, Response response) {
				if(request.queryParams("id") == null && request.queryParams("name") == null) {
					HashMap<String , Object> res = new HashMap<String, Object>();
					res.put("result", "Achievement null not found");
					return JSON.createJSON(res, request);
				}else {
					try {
						if(!(request.queryParams("name") == null)) {
							try {
							HashMap<String , Object> res = new HashMap<String, Object>();
							
							String sql = "SELECT * FROM `achievements` WHERE `name` = " + request.queryParams("name");
							Statement st = crunchifyConn.createStatement();
						    ResultSet rs = st.executeQuery(sql);
						    int i = 0;
						    while(rs.next()) {
						    	i++;
						    	res.put("id", rs.getInt("id"));
						    	res.put("name", rs.getString("name"));
						    	res.put("description", rs.getString("description"));
						    	res.put("icon", rs.getString("icon"));
						    	res.put("version", rs.getInt("version"));
						    }
						    if(i == 0) throw new Exception();
						    return JSON.createJSON(res, request);
						}catch (Exception e) {
							HashMap<String , Object> res = new HashMap<String, Object>();
							res.put("result", "Achievement " + request.queryParams("name")+" not found");
							return JSON.createJSON(res, request);
						}
						}
						HashMap<String , Object> res = new HashMap<String, Object>();
						
						String sql = "SELECT * FROM `achievements` WHERE `id` = " + request.queryParams("id");
						Statement st = crunchifyConn.createStatement();
					    ResultSet rs = st.executeQuery(sql);
					    int i = 0;
					    while(rs.next()) {
					    	i++;
					    	res.put("id", rs.getInt("id"));
					    	res.put("name", rs.getString("name"));
					    	res.put("description", rs.getString("description"));
					    	res.put("icon", rs.getString("icon"));
					    	res.put("version", rs.getInt("version"));
					    }
					    if(i == 0) throw new Exception();
					    return JSON.createJSON(res, request);
					}catch (Exception e) {
						HashMap<String , Object> res = new HashMap<String, Object>();
						res.put("result", "Achievement " + request.queryParams("id")+" not found");
						return JSON.createJSON(res, request);
					}
				}
			}
		});
        
        // routes:
        // /badges?name=
        // /badges?id=
        
        /**
         * returns:
         * @return id as string
         * @return name as string
         * @return icon as string
         * 
         * error:
         * @return result as string
         */
        Spark.get(new Route("/badges") {
			
			@Override
			public Object handle(Request request, Response response) {
				if(request.queryParams("id") == null && request.queryParams("name") == null) {
					HashMap<String , Object> res = new HashMap<String, Object>();
					res.put("result", "Badge null not found");
					return JSON.createJSON(res, request);
				}else {
					try {
						if(!(request.queryParams("name") == null)) {
							try {
							HashMap<String , Object> res = new HashMap<String, Object>();
							
							String sql = "SELECT * FROM `badges` WHERE `name` = " + request.queryParams("name");
							Statement st = crunchifyConn.createStatement();
						    ResultSet rs = st.executeQuery(sql);
						    int i = 0;
						    while(rs.next()) {
						    	i++;
						    	res.put("id", rs.getInt("id"));
						    	res.put("name", rs.getString("name"));
						    	res.put("icon", rs.getString("icon"));
						    }
						    if(i == 0) throw new Exception();
						    return JSON.createJSON(res, request);
						}catch (Exception e) {
							HashMap<String , Object> res = new HashMap<String, Object>();
							res.put("result", "Badge " + request.queryParams("name")+" not found");
							return JSON.createJSON(res, request);
						}
						}
						HashMap<String , Object> res = new HashMap<String, Object>();
						
						String sql = "SELECT * FROM `badges` WHERE `id` = " + request.queryParams("id");
						Statement st = crunchifyConn.createStatement();
					    ResultSet rs = st.executeQuery(sql);
					    int i = 0;
					    while(rs.next()) {
					    	i++;
					    	res.put("id", rs.getInt("id"));
					    	res.put("name", rs.getString("name"));
					    	res.put("icon", rs.getString("icon"));
					    }
					    if(i == 0) throw new Exception();
					    return JSON.createJSON(res, request);
					}catch (Exception e) {
						HashMap<String , Object> res = new HashMap<String, Object>();
						res.put("result", "Badge " + request.queryParams("id")+" not found");
						return JSON.createJSON(res, request);
					}
				}
			}
			
		}) ;
       
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
