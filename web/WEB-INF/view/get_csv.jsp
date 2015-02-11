<%-- 
    Document   : get_csv
    Created on : Feb 11, 2015, 10:35:28 AM
    Author     : aryner
--%>
<%@page import="java.util.*"%>
<%@page import="Model.*"%>

<div class="shiftRight">
<h2>CSV of grades</h2>

<div class="wideContainer">
	<div class="wideColumn">
		<a href="/Cornea_Grader/print_csv" class="btn">Print CSV to Desktop</a>
	</div>
</div>

<%
	ArrayList<String> csvLines = (ArrayList)request.getAttribute("csvLines");

	for(String line : csvLines) {
		out.print(line+"<br>");
	}
%>
</div>