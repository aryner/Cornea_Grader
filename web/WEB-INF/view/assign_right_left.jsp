<%-- 
    Document   : assign_right_left
    Created on : Feb 5, 2015, 3:42:02 PM
    Author     : aryner
--%>

<%@page import="Model.Picture"%>
<%@page import="Utilities.*"%>
<%@page import="Utilities.Constants"%>
<%@page import="java.util.*"%>

<%
	Picture picture = (Picture)request.getAttribute("picture");
	String fileName = picture.getName();
	ArrayList<Picture> neighbors = (ArrayList)request.getAttribute("neighbors");

	String nextName = Tools.escapePlus(neighbors.isEmpty() ? fileName : neighbors.get(1).getName());
	String previousName = Tools.escapePlus(neighbors.isEmpty() ? fileName : neighbors.get(0).getName());
	int side = picture.getRight_left();
%>

<div id="content">
	<h2>Right or Left? Patient <%out.print(picture.getPatient_number());%></h2>

	<div class="container">
		<img src="<%out.print(Constants.PICTURE_PATH+"?fileName="+Tools.escapePlus(fileName));%>" class="img bgImg">
	</div>

	<form action="update_right_left" class="right_left_form" method="POST">
		<div class="container">
			<input type="hidden" name="previous" value="<%out.print(previousName);%>">
			<input type="hidden" name="next" value="<%out.print(nextName);%>">
			<input type="hidden" name="nextFile" value="<%out.print(fileName);%>">
			<input type="hidden" name="file" value="<%out.print(fileName);%>">
			<input type="radio" name="side" value="left" <%if(side==Picture.LEFT)out.print("checked='true'");%>> Left 
			<input type="radio" name="side" value="delete"> Delete
			<input type="radio" name="side" value="right"<%if(side==Picture.RIGHT)out.print("checked='true'");%>> Right 
		</div>
		<div class="container">
			<input type="submit" class="btn" value="Previous">
			<input type="submit" class="btn" value="Next">
		</div>
	</form>
</div>

<script src="javascripts/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="javascripts/previous_next.js" type="text/javascript"></script>