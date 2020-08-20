<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th>URL</th>
            <th>View</th>
        </tr>
        </thead>
        <tbody>
        <jsp:useBean id="imagesIds" scope="request" type="java.util.List"/>
        <c:forEach items="${imagesIds}" var="imgId" varStatus="status">
            <tr valign="top">
                <td>
                    <a href="<c:url value='/image/download?imgId=${imgId}'/>">Download</a>
                    <a href="<c:url value='/image/delete?imgId=${imgId}'/>">Delete</a>
                </td>
                <td>
                    <img src="<c:url value='/image/download?imgId=${imgId}'/> " width="100px" height="100px"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>Upload image</h2>
    <form action="<c:url value='/image/upload.do'/>" method="post" enctype="multipart/form-data">
        <div class="checkbox">
            <input type="file" name="image">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
    </form>
</div>

</body>
</html>