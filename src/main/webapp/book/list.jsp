<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>

<div align="center">
    <h1><a href="/books" style="color: red">Book management</a></h1>
    <h3><a href="/books?action=create" style="color: red">Create new Book</a></h3>
    <table border="1" class="table table-warning table-striped table-hover">
        <tr>
            <th><a href="/books?action=list">Name</a></th>
            <th>Description</th>
            <th>Category</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${bookList}" var="book">
            <tr>
                <td><a href="/books?action=view&id=${book.id}">${book.name}</a></td>
                <td>${book.description}</td>
                <td>${book.getCategory().name}</td>
                <td><a href="/books?action=edit&id=${book.id}">Edit</a></td>
                <td><a href="/books?action=delete&id=${book.id}">Delete</a></td>
            </tr>

        </c:forEach>
    </table>
</div>


</body>
</html>