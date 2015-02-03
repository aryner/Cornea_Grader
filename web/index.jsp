<%-- 
    Document   : index
    Created on : Feb 2, 2015, 4:56:54 PM
    Author     : aryner
--%>


<h2>Log in</h2>

<%
	if(session.getAttribute("error") != null){
		out.print("<p style='color:red;'>Incorrect password or user name");
		session.removeAttribute("error");
	}
	if(session.getAttribute("user") != null) {
		response.sendRedirect("/Cornea_Grader/home"); 
	}
%>

<form action="login" method="POST">
	<p>User name: 
		<input type="text" name="userName">
	</p>
	<p>Password: 
		<input type="password" name="password">
	</p>
	<p>
		<input type="submit" value="Log in" class="btn">
	</p>
</form>

<a href="register">Register</a>