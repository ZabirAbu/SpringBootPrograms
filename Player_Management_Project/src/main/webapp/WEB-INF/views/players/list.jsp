<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
</head>
<body>


<c:forEach items="${players}" var="player">
    <p>Name: ${player.name}</p>
    <p>Description: ${player.description}</p>
    <p>Position: ${player.position}</p>
    <p>Age: ${player.age}</p>
</c:forEach>
<a href="/newPlayer?team=${team}">Link</a>
</body>


</body>
</html>