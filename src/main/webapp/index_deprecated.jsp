<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="<s:url value="/css/bootstrap.css"/>"/>
    <script type="text/javascript" src="<s:url value="/js/jquery-1.10.2.js"/>"></script>
<title>Users</title>
    <style type="text/css">
        body {
            padding-top: 100px;
        }
    </style>
</head>
<body class="container">
<s:url value="/resource/users" var="users_url"/>
<form id="form_add_user" class="col-xs-4 col-sm-offset-4" action="${users_url}" method="post">
    <div class="form-group">
        <label>Username: </label>
        <input class="form-control" type="text" name="username"/>
    </div>
    <div class="form-group">
        <label>Password: </label>
    <input class="form-control" type="password" name="password"/>
    </div>
    <button class="btn btn-default" type="submit">提交</button>
</form>
<script type="text/javascript" src="<s:url value="/js/bootstrap.js" />" ></script>
<script>
    $(function() {
        $("#form_add_user").submit(function(e) {
            e.preventDefault();
            var $this = $(this);
            var obj = Object.create(null);
            $this.serializeArray().forEach(function(el) {
                obj[el.name] = el.value;
            });/*
             $.post({
             url: $this.attr("action"),
             data: obj,
             dataType: "application/json"
             })*/
            $.ajax({
                type: "POST",
                url: $this.attr("action"),
                processData: false,
                data: JSON.stringify(obj),
                contentType: "application/json"
            })
                    .then(function(data) {
                        alert(JSON.stringify(data));
                    });
            return false;
        });
    })// end $
</script>
</body>
</html>