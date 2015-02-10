<%-- 
    Document   : grade
    Created on : Feb 6, 2015, 11:42:54 AM
    Author     : aryner
--%>
<%@page import="Model.*"%>
<%@page import="java.util.*"%>
<%@page import="Utilities.*"%>

<%
	int patient_number = (Integer)request.getAttribute("patient_number");
	int grade_type = (Integer)request.getAttribute("grade_type");
	int side = (Integer)request.getAttribute("side");
	ArrayList<Picture> pictures = (ArrayList)request.getAttribute("pictures");
	out.print((side==Grade.RIGHT?"right":"left")+ ", grade_type="+grade_type);
%>

<h2>Grade</h2>

<%
	if(pictures.isEmpty()) {
		if(patient_number == -1) {
%>
<p>All uploaded images with meta data have already been graded by you</p>
<%
		}
		else {
			out.print("Images and\\or meta data for patient "+patient_number); 
			switch(grade_type) {
				case Grade.NOT_PLUS_ONE:
					out.print(" with cellscope and not plus one exposure");
					break;
				case Grade.PLUS_ONE:
					out.print(" with cellscope having plus one exposure");
					break;
				case Grade.HDR:
					out.print(" with cellscope having HDR");
					break;
				case Grade.DSLR:
					out.print(" with DSLR");
					break;
			}
			out.print(" on the "+(side==Grade.RIGHT?"right":"left")+" eye are missing</p>");
			out.print("<a href='/Cornea_Grader/grade'>Go to next</a>");
		}
	}
	else {
%>

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
	<div class='bigColumn'>
		<input type="submit" value="Submit" class="btn">
	</div>
</form>
	
<!-- Present images to be graded here -->
<div class="newDivLine"></div>
<div class="pictureBox">
<%for(Picture pic : pictures) { %>
	<img src="<%out.print(Constants.PICTURE_PATH+"?fileName="+pic.getName());%>" class="gradeImg">
<%}%>
</div>

<%}%>