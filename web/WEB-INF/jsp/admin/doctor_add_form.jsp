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
                            <input type="hidden" name="command" value="insertDoctor"/>

                            <label for="login"><fmt:message key="login"/>:</label>
                            <input type="text" class="form-control" name="login" id="login" required>

                            <label for="password"><fmt:message key="password"/>:</label>
                            <input type="password" class="form-control" name="password" id="password" required>

                            <label for="first-name"><fmt:message key="firstName"/>:</label>
                            <input type="text" class="form-control" name="firstName" id="first-name" required>

                            <label for="last-name"><fmt:message key="lastName"/>:</label>
                            <input type="text" class="form-control" name="lastName" id="last-name" required>

                            <label for="inputDate"><fmt:message key="insertDate"/>:</label>
                            <input name="birthDate" type="date" id="inputDate" class="form-control">

                            <label for="positionChoose"><fmt:message key="choosePosition"/>:</label>
                            <select id="positionChoose" class="form-control form-control-sm" name="role">
                                <option name="role" value="0"><fmt:message key="syrgery"/></option>
                                <option name="role" value="1"><fmt:message key="ocylist"/></option>
                                <option name="role" value="2"><fmt:message key="therapist"/></option>
                            </select>
                            <br>
                            <input class="btn btn-light" type="submit" value="<fmt:message key="submit"/>">
                        </form>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
