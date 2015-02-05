<%-- 
    Document   : home
    Created on : Feb 4, 2015, 9:46:52 AM
    Author     : aryner
--%>
<%@page import="Model.*"%>
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

	ArrayList<Picture> uploaded = (ArrayList)request.getAttribute("uploaded");
	ArrayList<Picture> not_uploaded = (ArrayList)request.getAttribute("not_uploaded");
%>

<div class="wideContainter">
	<%if(user.canUpload()) { %>
	<div class="column">
		<a href="/Cornea_Grader/upload_excel" class="btn menubtn">Upload Excel Data</a>
	</div>
	<div class="column">
		<a href="/Cornea_Grader/upload_pictures" class="btn menubtn">Upload Pictures</a>
	</div>
	<%}%>
</div>

<div class="container">
	<div class="fourthColumn">
		<h3>Uploaded Images</h3>
		<%
		for(Picture pic: uploaded) {
			out.print("<a href=#>"+pic.getName());
			if(pic.getRight_left() == -1) {
				out.print("*");
			}
			out.print("</a><br>");
		}
		%>
	</div>
	<div class="fourthColumn">
		<h3>Images that need to be uploaded</h3>
		<%
		for(Picture pic: not_uploaded) {
			out.print(pic.getName()+"<br>");
		}
		%>
	</div>
	<div class="fourthColumn">
		<h3>Uploaded but no meta data</h3>
		<%
		for(Picture pic : uploaded) {
			if(pic.getPatient_number() < 0) {
				out.print(pic.getName()+"<br>");
			}
		}
		%>
	</div>
</div>