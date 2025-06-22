<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Form</title>
</head>
<body>
<form:form action="/addTeam" modelAttribute="team">
ID:
<form:input path="id"/>
<form:errors path="id"/><br/>

Location:
<form:input path="location"/>
<form:errors path="location"/><br/>

League:
<form:input path="league"/>
<form:errors path="league"/><br/>
    <input type="submit"/><form:errors/>
</form:form>
</body>
</html>