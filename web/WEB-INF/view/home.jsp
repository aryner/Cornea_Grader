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

	<%if(user.canUpload()) { %>
	<div class="wideContainter">
		<div class="column">
			<a href="/Cornea_Grader/upload_excel" class="btn menubtn">Upload Excel Data</a>
		</div>
		<div class="column">
			<a href="/Cornea_Grader/upload_pictures" class="btn menubtn">Upload Pictures</a>
		</div>
		<div class="column">
			<a href="/Cornea_Grader/get_csv" class=" btn menubtn">Get CSV of Data</a>
		</div>
	</div>
	<%} if(user.canGrade()) {%>
	<div class="wideContainter">
		<div class="column">
			<a href="/Cornea_Grader/grade" class="btn menubtn">Grade</a>
		</div>
	</div>
	<%}%>

<div class="container">
	<div class="bigColumn">
		<h3>Uploaded Images</h3>
		<%
		for(Picture pic: uploaded) {
			out.print((user.canUpload()?"<a href='assign_right_left?fileName="+pic.getName()+"'>":"")+pic.getName());
			if(pic.getRight_left() == -1) {
				out.print("***");
			}
			out.print((user.canUpload()?"</a>":"")+"<br>");
		}
		%>
	</div>
	<div class="bigColumn">
		<h3>Images that need to be uploaded</h3>
		<%
		for(Picture pic: not_uploaded) {
			out.print(pic.getName()+"<br>");
		}
		%>
	</div>
	<div class="bigColumn">
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