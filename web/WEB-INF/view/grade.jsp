<%-- 
    Document   : grade
    Created on : Feb 6, 2015, 11:42:54 AM
    Author     : aryner
--%>
<%@page import="Model.Grade"%>

<h2>Grade</h2>


<form action="submit_grade" method="POST">
	<div class='bigColumn'>
		<h3>Corneal Opacity?</h3>
		<p>
		<input type="radio" value="<%out.print(Grade.YES_CORNEAL_OPACITY);%>" name="grade"> Yes
		<input type="radio" value="<%out.print(Grade.NO_CORNEAL_OPACITY);%>" name="grade"> No
		</p>
	</div>
	<div class='bigColumn'>
		<h3>Quality</h3>
		<input type="radio" value="<%out.print(Grade.GOOD);%>" name="quality"> Good
		<input type="radio" value="<%out.print(Grade.ACCEPTABLE);%>" name="quality"> Acceptable
		<input type="radio" value="<%out.print(Grade.POOR);%>" name="quality"> Poor
	</div>
</form>
	
<!-- Present images to be graded here -->