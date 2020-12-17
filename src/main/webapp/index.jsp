<%--
  Created by IntelliJ IDEA.
  User: martin
  Date: 2020/12/1
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="account/findAll.action">查询</a>

    <hr>

    <form action="account/save" method="post">
        <label for="name">姓名：</label><input type="text" name="name" id="name"><br>
        <label for="money">金额：</label><input type="text" name="money" id="money"><br>
        <input type="submit" value="提交">
    </form>

</body>
</html>
