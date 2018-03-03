package com.uploadfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		//DB Connection
		String url = "jdbc:sqlserver://***.database.windows.net:1433;database=Quiz3DB;user=****;password=*****";
		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(url);
			
		
		
		 System.out.println("******* ");
		 String feedUrl = "C:\\Users\\PM\\Desktop\\images\\test.csv";
		// URL url = new URL(feedUrl);
		// InputStreamReader isr = new InputStreamReader();
		 
		 FileReader  fr = new FileReader(feedUrl);
	        BufferedReader br = new BufferedReader(fr);
	        String line = "";  
	        String splitBy = ",";  
	        String selectSql = "INSERT INTO EducationTest(UNITID,OPEID,OPEID6,INSTNM,CITY,STATE,INSTURL,SAT_AVG,GRADDEBT) values (?, ?, ?,?,?,?,?,?,?)";
			  
			
	        while ((line = br.readLine()) != null) {  
	        	PreparedStatement statement = connection.prepareStatement(selectSql);
	            String[] data = line.split(splitBy);  
	         //   System.out.println("CARS [year= " + cars[0] + " , make="  
	           //   + cars[1] );  
	            statement.setInt(1, Integer.parseInt((data[0])));
	            statement.setInt(2, Integer.parseInt((data[1])));
	            statement.setInt(3, Integer.parseInt((data[2])));
	            statement.setString(4, (data[3]));
	            statement.setString(5, (data[4]));
	            statement.setString(6, (data[5]));
	            statement.setString(7, (data[6]));
	            statement.setFloat(8, Float.parseFloat((data[7])));
	            statement.setFloat(9, Float.parseFloat((data[8])));
	        
	            // sends the statement to the database server
				int row = statement.executeUpdate();
				if (row > 0) {
					System.out.print("File uploaded and saved into database");
				}
	           }
	        connection.close();
	        response.sendRedirect("Upload.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("Upload.jsp");
		} 
	}

}
