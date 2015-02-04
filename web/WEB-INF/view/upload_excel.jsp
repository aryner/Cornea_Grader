<%-- 
    Document   : upload_excel
    Created on : Feb 4, 2015, 11:01:40 AM
    Author     : aryner
--%>

<h2>Upload Excel File</h2>

<p>Upload an excel file containing data about images<br>
	(more recently uploaded excel files will override older ones if they 
	 provide data for the same images)
</p>

<form action="upload_picture_data" method="POST" enctype="multipart/form-data">
	<input type="file" name="excel_file">
	<input type="submit" value="Upload" class="btn">
</form>