package com.search;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * Servlet implementation class SearchRangeServlet
 */
@WebServlet("/SearchRangeServlet")
public class SearchRangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchRangeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html");
		
		/* In this line, replace <name> with your cache name: */
		JedisShardInfo shardInfo = new JedisShardInfo("****.redis.cache.windows.net", 6379);
		shardInfo.setPassword("******"); /* Use your access key. */
		Jedis jedis = new Jedis(shardInfo);

		// First get hash value and search in Redis
		String strRedisKey = new String("SearchRangeOf" + request.getParameter("id"));

		PrintWriter out = response.getWriter();
		if (jedis.exists(Integer.toString(strRedisKey.hashCode()))) {
			// Get the stored data and print it
			List<String> list = jedis.lrange(Integer.toString(strRedisKey.hashCode()), 0, 1000);

			out.print("<html><head><title>test</title></head><body>");

			if (list != null && list.size() > 0) {

				for (int i = 0; i < list.size(); i++) {
					System.out.println("Stored string in redis:: " + list.get(i));

					out.println("<h4>" + list.get(i) + "</h4>");

				}

			}

		} else {
			System.out.println("no value found");

			// Retrieve from relational database
			String url = "jdbc:sqlserver://****.database.windows.net:1433;database=Quiz3DB;user=****;password=*****";
			Connection connection = null;
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connection = DriverManager.getConnection(url);
                String state=request.getParameter("STATE");
                String from=request.getParameter("from");
                String to=request.getParameter("to");
				// Create and execute a SQL statement.
                long startTime = System.nanoTime();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select * from education where state='"+state+"' and SAt_avg >"+from+"and sat_avg <"+to+" and sat_avg!='NULL'");
            int i=10;
				while (rs.next() && i>0) {
                   i=i-1;
					
					String insti = rs.getString("instnm");
					String city = rs.getString("city");
					// Create value for Redis
					String result = insti + "," + city + "," + state;

					out.print("<h2>" + result + "</h2>");

									}
				long stopTime = System.nanoTime();
				System.out.println(stopTime - startTime);
				long time=stopTime - startTime;
				out.print("<br>Time taken in nanosecond= "+time);
				connection.close();

			} catch (Exception e) {
				e.printStackTrace();
				// response.sendRedirect("Upload.jsp");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
