<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<html>
<head>
    <title>Choose patient</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf"%>
    <%@ include file="/WEB-INF/jspf/nav-bar.jspf"%>
    <div class="main">
        <div class="inner-content-center">
            <form action="controller" method="post">
                <input type="hidden" name="command" value="medSisGetMedCard"/>
                <select class="styled-select" name="patient">
                    <c:forEach items="${patients}" var="patient">
                        <option name="choice" value="${patient.id}">${patient}</option>
                    </c:forEach>
                </select>
                <input class="btn btn-light" type="submit" value="<fmt:message key="choose"/>">
            </form>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
</div>
</body>
</html>