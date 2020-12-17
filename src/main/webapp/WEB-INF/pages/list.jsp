<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: martin
  Date: 2020/12/1
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>查询所有账户</h1>

    <table>
        <tr>
            <th>ID</th>
            <th>姓名</th>
            <th>余额</th>
        </tr>
        <c:forEach items="${accountList}" var="account">
            <tr>
                <th>${account.id}</th>
                <th>${account.name}</th>
                <th>${account.money}</th>
            </tr>
        </c:forEach>
    </table>


</body>
</html>
