<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
</head>
<body>
<h3>Pushpak Mhatre - 1001439236</h3>
<br>

<br><br>
<hr>
<form action="SearchRangeServlet">
<h2>Please Enter :</h2>
<input type="text" name="STATE" placeholder="STATE"><br><br><br>
<input type="text" name="from" placeholder="sat From"><br><br><br>
<input type="text" name="to" placeholder="sat To">
<input type=submit value="Search">

</form>

<br><hr>
<form action="SearchServlet">

<h2>Please Enter :</h2>
<input type="text" name="zipfrom" placeholder="zipfrom"><br><br><br>
<input type="text" name="zipto" placeholder="zipto"><br><br><br><br>
<input type="text" name="from" placeholder="sat From"><br><br><br>
<input type="text" name="to" placeholder="sat To">
<input type=submit value="Search">

</form>



<br><hr>


</body>
</html>