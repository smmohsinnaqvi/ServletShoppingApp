<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
  <%
  	Cookie[] all = request.getCookies();
    if(all!=null)
    {
    	for(Cookie c : all)
    	{
    		if(c.getName().equals("loginerr"))
    			out.println("<p>" + c.getValue() + "</p>");
    	}
    }
  %>
  <h1>LOGIN TO CONTINUE</h1>
  <form action="http://localhost:8080/ShoppingApp/CheckLogin" method="post">
      UserID : <input type="text" name="uid" /> <br>
      Password : <input type="password" name="pwd" /> <br/>
      
      <input type="submit" value="SUBMIT"> 
  </form>
</body>
</html>