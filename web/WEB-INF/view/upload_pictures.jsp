<%-- 
    Document   : upload_pictures
    Created on : Feb 5, 2015, 9:18:07 AM
    Author     : aryner
--%>

<h2>Upload Pictures</h2>

<p>Upload pictures to be graded</p>

<form action="insert_pictures" method="POST" enctype="multipart/form-data">
	<input type="file" multiple="multiple" name="pictures">
	<input type="submit" value="Upload" class="btn">
</form>