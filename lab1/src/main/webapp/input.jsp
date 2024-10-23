<%-- 
    Document   : input
    Created on : Oct 13, 2024, 1:52:30 PM
    Author     : ioana
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>File Upload</title>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>
    <h2>Upload a Text File</h2>
    <form action="${pageContext.request.contextPath}/FileUploadServlet" method="post" enctype="multipart/form-data">
        <label for="fileName">File Name:</label>
        <input type="text" name="fileName" id="fileName" required><br><br>

        <label for="description">File Description:</label>
        <textarea name="description" id="description" required></textarea><br><br>

        <label for="file">Select a file:</label>
        <input type="file" name="file" accept=".txt" required><br><br>

        <!-- reCAPTCHA widget -->
        <div class="g-recaptcha" data-sitekey="6LeTEWoqAAAAAHy2-FQM0JLJAyINCnJu0-1x8mfj"></div><br><br>
        
        <c:if test="${not empty captchaError}">
            <p style="color: red;">${captchaError}</p>
        </c:if>
        
        <input type="submit" value="Upload File and Submit">
    </form>
</body>
</html>
