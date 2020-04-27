<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <title>Doctor add form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <%@ include file="/WEB-INF/jspf/nav-bar.jspf" %>
    <div class="main">
        <div class="inner-content-center">
            <form id="login_form" action="controller" method="post">
                <input type="hidden" name="command" value="insertMedSis"/>

                <label for="login"><fmt:message key="login"/>:</label>
                <input type="text" class="form-control" name="login" id="login" required>

                <label for="password"><fmt:message key="password"/>:</label>
                <input type="password" class="form-control" name="password" id="password" required>

                <label for="first-name"><fmt:message key="password"/>:</label>
                <input type="text" class="form-control" name="firstName" id="first-name" required>

                <label for="last-name"><fmt:message key="lastName"/>:</label>
                <input type="text" class="form-control" name="lastName" id="last-name" required>

                <br>
                <input class="btn btn-light" type="submit" value="<fmt:message key="submit"/>">
            </form>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
