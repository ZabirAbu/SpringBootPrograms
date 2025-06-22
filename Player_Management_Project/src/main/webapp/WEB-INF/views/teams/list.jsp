<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
</head>
<body>


<c:forEach items="${teams}" var="team">
    <p>Location: ${team.location}</p>
    <p>League: ${team.league}</p>
    <a href="/players?team=${team.id}">Link</a>
</c:forEach>
</body>


</html>