<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload File</title>
</head>
<body>
<h3>Pushpak Mhatre - 1001439236</h3>
 <form action = "UploadServlet" method = "post" enctype = "multipart/form-data">
         <br>
         <input type = "file" name = "file" size = "50" />
         <br><br>                
         <input type = "submit" value = "Upload File" />
      </form><br><br>
<a href="query.jsp">Query</a>
</body>
</html>