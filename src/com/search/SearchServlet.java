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
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
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
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		/* In this line, replace <name> with your cache name: */
		JedisShardInfo shardInfo = new JedisShardInfo("*****.redis.cache.windows.net", 6379);
		shardInfo.setPassword("H******"); /* Use your access key. */
		Jedis jedis = new Jedis(shardInfo);

		// First get hash value and search in Redis
		String strRedisKey = new String("SearchEducationId" + request.getParameter("id"));

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
			String url = "jdbc:sqlserver://****.database.windows.net:1433;database=Quiz3DB;user=*****;password=*****";
			Connection connection = null;
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connection = DriverManager.getConnection(url);
				String from=request.getParameter("zipfrom");
                String to=request.getParameter("zipto");
				// Create and execute a SQL statement.

				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select city from USZipcodes where zip>="+from+" and zip<"+to);
				 String from1=request.getParameter("from");
	                String to1=request.getParameter("to");

				while (rs.next()) {

									
					String city = rs.getString("city");
					// Create value for Redis
							
					Statement stmt2 = connection.createStatement();
					ResultSet rs2 = stmt.executeQuery("select * from education where sat_avg>="+from1+" and sat_avg<"+to1+ "and city="+city);
					String insti = rs.getString("instnm");
					out.print("<h2>" + insti + "</h2>");
					out.print("<br><a href='query.jsp'>Query</a></body></html>");
				}
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
