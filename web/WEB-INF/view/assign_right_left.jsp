<%-- 
    Document   : assign_right_left
    Created on : Feb 5, 2015, 3:42:02 PM
    Author     : aryner
--%>

<%@page import="Model.Picture"%>
<%@page import="Utilities.Constants"%>
<%@page import="java.util.*"%>

<%
	String fileName = request.getParameter("fileName");
	ArrayList<Picture> neighbors = (ArrayList)request.getAttribute("neighbors");

	String nextName = neighbors.isEmpty() ? fileName : neighbors.get(1).getName();
	String previousName = neighbors.isEmpty() ? fileName : neighbors.get(0).getName();
%>

<div id="content">
	<h2>Right or Left?</h2>

	<div class="container">
		<img src="<%out.print(Constants.PICTURE_PATH+"?fileName="+fileName);%>" class="img smImg">
	</div>

	<form action="update_right_left" method="POST">
		<div class="container">
			<input type="hidden" name="nextFile" value="<%out.print(fileName);%>">
			<input type="hidden" name="file" value="<%out.print(fileName);%>">
			<input type="radio" name="side" value="left"> Left 
			<input type="radio" name="side" value="right"> Right 
		</div>
		<div class="container">
			<input type="submit" class="btn" value="Previous">
			<input type="submit" class="btn" value="Next">
		</div>
	</form>
</div>