<%--
  Created by IntelliJ IDEA.
  User: Keesung
  Date: 2023-09-10
  Time: 오후 3:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
  <thead>
  <th>id</th>
  <th>username</th>
  <th>age</th>
  </thead>
  <tbody>
  <c:forEach var="member" items="${members}">
    <tr>
      <td>${member.id}</td>
      <td>${member.username}</td>
      <td>${member.age}</td>
    </tr>
  </c:forEach>

  </tbody>
</table>
</body>
</html>
