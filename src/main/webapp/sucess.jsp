<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 11/29/15
  Time: 1:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录成功</title>
</head>
<body>
登录成功. <br/>
你提交的信息是:<br/>
UserName:<%= request.getParameter("username") %> <br/>
PassWord:<%= request.getParameter("password") %> <br/>
<a href="<%= request.getContextPath()%>/login.jsp">返回登录界面</a><br/>
</body>
</html>
