<%-- 
    Document   : result
    Created on : Oct 13, 2024, 2:23:30 PM
    Author     : ioana
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>File Contents</title>
</head>
<body>
    <h2>File Details</h2>
    
    <p><strong>File Name:</strong> ${sessionScope.fileLinesBean.fileName}</p>
    <p><strong>Description:</strong> ${sessionScope.fileLinesBean.description}</p>
    
    <h3>Shuffled File Lines</h3>
    <ul>
        <c:forEach var="line" items="${sessionScope.fileLinesBean.lines}">
            <li>${line}</li>
        </c:forEach>
    </ul>
</body>
</html>