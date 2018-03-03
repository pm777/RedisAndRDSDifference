package com.search;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * Servlet implementation class Search1000Servlet
 */
@WebServlet("/Search1000Servlet")
public class Search1000Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search1000Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		/* In this line, replace <name> with your cache name: */
		JedisShardInfo shardInfo = new JedisShardInfo("******.redis.cache.windows.net", 6379);
		shardInfo.setPassword("******************"); /* Use your access key. */
		Jedis jedis = new Jedis(shardInfo);

		// First get hash value and search in Redis
		String strRedisKey = new String("SearchEducationId" + request.getParameter("id"));
        
		
		
		PrintWriter out = response.getWriter();
		out.print("<html><head><title>Search1000</title></head><body><h2>This is Searc page</h2>");
		String url = "jdbc:sqlserver://***.database.windows.net:1433;database=Quiz3DB;user=*****;password=*****";
		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(url);
			
			// Create and execute a SQL statement.

			Statement stmt = connection.createStatement();
			
			//Start time
			long startTime = System.nanoTime();
			
			for(int i=1;i<=1000;i++) {
			ResultSet rs = stmt.executeQuery("Select where id=");

			
			while (rs.next()) {
				
				  int id = rs.getInt("id");
				  String title=rs.getString("title");
				  
			}
			}//End for loop
			//End time
			long stopTime = System.nanoTime();
			System.out.println(stopTime - startTime);
			long time=stopTime - startTime;
			out.print("Time taken in nanosecond= "+time);
			out.print("<br><a href='query.jsp'>Query</a></body></html>");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("query.jsp");
		}  	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
