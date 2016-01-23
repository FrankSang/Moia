<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 1/23/16
  Time: 1:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆验证</title>
</head>
<form action="<%=request.getContextPath()%>/LoginServlet" method="post">
      username:<input type="text" name="username"><br>
      password:<input type="password" name="password"><br>
      <input type="submit" value="Submit"><input type="reset" value="Reset">
</form>
<body>

</body>
</html>
