<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>List of Depots</title>
</head>
<body>
<c:forEach items="${depots}" var="dep">
    <p>${dep.toString()}</p>
</c:forEach>
</body>
</html>
