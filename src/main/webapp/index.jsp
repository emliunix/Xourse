<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href="${pageContext.servletContext.contextPath}">
    <meta charset="UTF-8">
    <title>Test</title>
</head>
<body>
<form action="/sys/login" method="post">
    <div>
    <label>Username:
    <input type="text" name="username" />
    </label>
    </div>
    <div>
    <label>Password:
    <input type="password" name="password" />
    </label>
    </div>
    <button type="submit">Submit</button>
</form>

</body>
</html>