<%-- 
    Document   : home
    Created on : Feb 4, 2015, 9:46:52 AM
    Author     : aryner
--%>
<%@page import="Model.User"%>
<%@page import="java.util.*"%>

<h2>Home</h2>

<%
	User user = (User)session.getAttribute("user");

	ArrayList<String> errors = (ArrayList)session.getAttribute("errors");
	if(errors != null && !errors.isEmpty()) {
		out.print("<div class='error'><p>");
		for(String error : errors) out.print(error+"<br>");
		out.print("</p></div>");

		session.removeAttribute("errors");
	}
%>

<div class="wideContainter">
	<%if(user.canUpload()) { %>
	<div class="column">
		<a href="/Cornea_Grader/upload_excel" class="btn menubtn">Upload Excel Data</a>
	</div>
	<%}%>
</div>