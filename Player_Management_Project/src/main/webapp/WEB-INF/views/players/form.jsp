<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Form</title>
</head>
<body>
<form:form action="/addPlayer?team=${team}" modelAttribute="player">
<%--    <input type="hidden" name="teamId" value="${team}"/>--%>
    Name:
    <form:input path="name"/>
    <form:errors path="name"/><br/>

    Description:
    <form:input path="description"/>
    <form:errors path="description"/><br/>

    Position:
    <form:input path="position"/>
    <form:errors path="position"/><br/>

    Age:
    <form:input path="age"/>
    <form:errors path="age"/><br/>
    <input type="submit"/><form:errors/>
</form:form>
</body>
</html>